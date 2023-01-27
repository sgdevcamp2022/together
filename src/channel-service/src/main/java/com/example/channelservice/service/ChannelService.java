package com.example.channelservice.service;

import com.example.channelservice.dto.ChannelDto;

import java.util.List;

public interface ChannelService {
    ChannelDto createChannel(ChannelDto channelDetails, Long serverId);
    List<ChannelDto> getChannelsByServerId(Long serverId);
}
