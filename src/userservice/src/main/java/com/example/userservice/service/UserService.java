package com.example.userservice.service;

import com.example.userservice.dto.FriendDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseDetailUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    /**
     * @param userDto
     * @return
     * user 생성
     */
    UserDto createUser(UserDto userDto);

    /**
     * @param userId
     * @return
     * userID(UUID)로 찾은 user 반환
     */
    ResponseDetailUser getUserDetailsByUserId(String userId);

    /**
     * @param userId
     * @param userInfo
     * @return
     * userID와 변경할 유저 정보로 유저 정보 업데이트
     */
    UserDto updateUser(String userId, RequestUser userInfo);

    void deleteUser(String userId);

    Iterable<UserEntity> getAllUser();


    List<FriendDto> getFriendList(String userId);

    UserDto getUserByEmail(String email);
}
