package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;

public interface ServerService {
    ServerDto createServer(ServerDto serverDetails);
//    Iterable<ServerEntity> getServersByUserId(String userId);
    ServerDto getServerById(Long serverId);
}
