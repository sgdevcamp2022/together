package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.RequestUser;

public interface UserService {
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
    UserDto getUserByUserId(String userId);

    /**
     * @param userId
     * @param userInfo
     * @return
     * userID와 변경할 유저 정보로 유저 정보 업데이트
     */
    UserDto updateUser(String userId, RequestUser userInfo);
}
