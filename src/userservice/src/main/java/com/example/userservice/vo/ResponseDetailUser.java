package com.example.userservice.vo;

import com.example.userservice.dto.FriendDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDetailUser {
    private String email;
    private String name;
    private String userId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<FriendDto> friendList;
    private List<ResponseServer> serverList;
}
