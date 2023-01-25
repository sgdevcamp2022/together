package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.repository.ServerEntity;

public interface ServerService {
    ServerDto createServer(ServerDto serverDetails);
    Iterable<ServerEntity> getServersByUserId(String userId);
    ServerDto getServerByServerId(String serverId);
}
