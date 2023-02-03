package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseFriend {
    private String email;
    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
