package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.vo.RequestServer;

public interface ServerService {
    ServerDto createServer(RequestServer serverDetails, String userId);
//    Iterable<ServerEntity> getServersByUserId(String userId);
    ServerDto getServerById(Long serverId);

    ServerDto deleteServer(Long serverId);

    ServerDto updateServer(Long serverId, RequestServer newServer);

    ServerDto addUser(Long serverId, String userEmail);


    ServerDto deleteUserInServer(Long serverId, String userEmail);
}
