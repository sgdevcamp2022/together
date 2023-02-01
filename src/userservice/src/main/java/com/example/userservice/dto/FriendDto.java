package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendDto {
    private String email;
    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
