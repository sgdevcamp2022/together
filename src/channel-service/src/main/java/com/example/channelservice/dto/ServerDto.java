package com.example.channelservice.dto;

import com.example.channelservice.vo.ResponseUser;
import lombok.Data;

import java.util.List;


@Data
public class ServerDto {
    private Long id;
    private String name;
    private String info;
    private String userId;
    private List<ResponseUser> userList;
}
