package com.example.channelservice.vo;

import lombok.Data;

import java.util.List;

@Data
public class ResponseServerDetail {
    private String name;
    private String info;
    private List<ResponseChannel> channelList;
}
