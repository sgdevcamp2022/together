package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.vo.RequestServer;

public interface ServerService {
    ServerDto createServer(ServerDto serverDetails);
//    Iterable<ServerEntity> getServersByUserId(String userId);
    ServerDto getServerById(Long serverId);

    ServerDto deleteServer(Long serverId);

    ServerDto updateServer(Long serverId, RequestServer newServer);
}
