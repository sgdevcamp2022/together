package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.vo.RequestCreateServer;
import com.example.channelservice.vo.RequestServer;

public interface ServerService {
    ServerDto createServer(RequestCreateServer serverDetails);
//    Iterable<ServerEntity> getServersByUserId(String userId);
    ServerDto getServerById(Long serverId);

    ServerDto deleteServer(Long serverId);

    ServerDto updateServer(Long serverId, RequestServer newServer);

    ServerDto addUser(Long serverId, String userId);


    ServerDto deleteUserInServer(Long serverId, String userId);
}
