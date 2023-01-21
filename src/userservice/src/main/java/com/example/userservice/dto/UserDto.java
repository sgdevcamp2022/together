package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Data createdAt;

    private String encryptedPwd;
}
