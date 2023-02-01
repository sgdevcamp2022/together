package com.example.userservice.service;

import com.example.userservice.dto.FriendDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.FriendEntity;
import com.example.userservice.repository.FriendRepository;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseDetailUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final FriendRepository friendRepository;
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           FriendRepository friendRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.friendRepository = friendRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsUserEntityByEmail(userDto.getEmail()))
            throw new IllegalArgumentException("이미 가입된 email입니다.");

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
        UserEntity userEntity = checkValidUser(userId);
        List<FriendEntity> friendEntityList = userEntity.getFriendList();
        List<FriendDto> friendList = new ArrayList<>();

        friendEntityList.forEach(entity -> {
            friendList.add(new ModelMapper().map(entity,FriendDto.class));
        });
        ResponseDetailUser userDetail = new ModelMapper().map(userEntity,ResponseDetailUser.class);
        userDetail.setFriendList(friendList);
        log.info("followerList:"+userEntity.getFriendList().toString());

        return userDetail;
    }

    @Override
    public UserDto updateUser(String userId, RequestUser userInfo) {
        UserEntity userEntity = checkValidUser(userId);

        userEntity.setName(userInfo.getName());
        userEntity.setEmail(userInfo.getEmail());
        userEntity.setEncryptedPwd(userInfo.getPwd());

        userRepository.save(userEntity);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = checkValidUser(userId);

        userRepository.delete(userEntity);
    }

    @Override
    public Iterable<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void addFriend(String myMail, String followerMail) {
        UserEntity myInfo = userRepository.findByEmail(myMail);
        UserEntity followerInfo = userRepository.findByEmail(followerMail);

        FriendEntity friend = FriendEntity.addFriend(followerInfo.getName(),
                followerInfo.getEmail());

        myInfo.addFriend(friend);
        friendRepository.save(friend);
        userRepository.save(myInfo);
    }

    @Override
    @Transactional
    public void deleteFriend(String myMail, String followerMail) {
        UserEntity myInfo = userRepository.findByEmail(myMail);
        FriendEntity friendEntity = friendRepository.findByEmail(followerMail).orElseThrow(()->new NoSuchElementException());

        friendRepository.delete(friendEntity);
    }

    @Override
    @Transactional
    public List<FriendDto> getFriendList(String userId) {
        UserEntity myInfo = userRepository.findByUserId(userId);
        List<FriendDto> res = new ArrayList<>();

        List<FriendEntity> dbList = myInfo.getFriendList();

        dbList.forEach(friend -> res.add(new ModelMapper().map(friend, FriendDto.class)));

        return res;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }

    private UserEntity checkValidUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException("User를 찾을 수 없습니다.");
        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null)
            throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true,true,true,true,
                new ArrayList<>());
    }
}
