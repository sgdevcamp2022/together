package com.example.channelservice.vo;

import lombok.Data;

@Data
public class RequestEmail {
    // name : dto로 mapper시 여기에 userId가 설정됨을 방지
    private String userEmail;
}
