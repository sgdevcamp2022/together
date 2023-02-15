package com.example.channelservice.dto;

import lombok.Data;

@Data
public class ChannelDto {
    private Long id;
    private String name;
    private String info;
    private Integer type;
}
