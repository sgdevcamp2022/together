package com.example.userservice.service;

import com.example.userservice.dto.UserDto;

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
}
