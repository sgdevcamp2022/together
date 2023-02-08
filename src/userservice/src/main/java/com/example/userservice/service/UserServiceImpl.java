package com.example.userservice.service;

import com.example.userservice.client.ChannelServiceClient;
import com.example.userservice.dto.FriendDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.exception.CustomException;
import com.example.userservice.exception.ErrorCode;
import com.example.userservice.repository.FriendEntity;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseDetailUser;
import com.example.userservice.vo.ResponseServer;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    ChannelServiceClient channelServiceClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           ChannelServiceClient channelServiceClient){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.channelServiceClient = channelServiceClient;
    }

    @Override
    @Transactional(rollbackFor = {CustomException.class})
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsUserEntityByEmail(userDto.getEmail())) {
            log.info("이미 가입된 유저");
            throw new CustomException(ErrorCode.CANNOT_CREATE_USER);
        }

        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);
        return returnUserDto;
    }

    @Override
    public ResponseDetailUser getUserDetailsByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));
        List<FriendEntity> friendEntityList = userEntity.getFriendList();
        List<FriendDto> friendList = new ArrayList<>();

        friendEntityList.forEach(entity -> {
            friendList.add(new ModelMapper().map(entity,FriendDto.class));
        });
        ResponseDetailUser userDetail = new ModelMapper().map(userEntity,ResponseDetailUser.class);
        userDetail.setFriendList(friendList);
        log.info("followerList:"+userEntity.getFriendList().toString());

        List<ResponseServer> serverList =channelServiceClient.getServers(userId);
        userDetail.setServerList(serverList);

        return userDetail;
    }

    @Override
    public UserDto updateUser(String userId, RequestUser userInfo) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));

        userEntity.setName(userInfo.getName());
        userEntity.setEmail(userInfo.getEmail());
        userEntity.setEncryptedPwd(userInfo.getPwd());

        userRepository.save(userEntity);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));

        userRepository.delete(userEntity);
    }

    @Override
    public Iterable<UserEntity> getAllUser() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public List<FriendDto> getFriendList(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));
        List<FriendDto> res = new ArrayList<>();

        List<FriendEntity> dbList = userEntity.getFriendList();

        dbList.forEach(friend -> res.add(new ModelMapper().map(friend, FriendDto.class)));

        return res;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.CANNOT_FIND_USER));

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(()-> new CustomException(ErrorCode.CANNOT_FIND_USER));

        if (userEntity == null)
            throw new CustomException(ErrorCode.CANNOT_FIND_USER);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true,true,true,true,
                new ArrayList<>());
    }
}
