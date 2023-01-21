package com.example.userservice.service;

import com.example.userservice.dto.UserDto;

public interface UserService {
    /**
     * @param userDto
     * @return
     * user 생성
     */
    UserDto createUser(UserDto userDto);
}
