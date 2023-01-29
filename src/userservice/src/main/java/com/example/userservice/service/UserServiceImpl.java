package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.RequestUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = checkValidUser(userId);

        UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);

//        TODO 사용자 추가 정보(채널, 친구 목록 등) 가져와서 userDto에 추가


        return userDto;
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

    @Override
    public UserDto getUserDetailsByEmail(String email) {
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
