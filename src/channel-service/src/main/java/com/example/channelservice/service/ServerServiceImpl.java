package com.example.channelservice.service;

import com.example.channelservice.client.UserServiceClient;
import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.repository.ChannelEntity;
import com.example.channelservice.repository.ServerEntity;
import com.example.channelservice.repository.ServerRepository;
import com.example.channelservice.repository.UserInServerEntity;
import com.example.channelservice.vo.RequestEmail;
import com.example.channelservice.vo.RequestServer;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class ServerServiceImpl implements ServerService{
    ServerRepository serverRepository;
    UserServiceClient userServiceClient;

    ModelMapper mapper = new ModelMapper();
    @Autowired
    public ServerServiceImpl(ServerRepository serverRepository,
                             UserServiceClient userServiceClient){

        this.serverRepository = serverRepository;
        this.userServiceClient = userServiceClient;
    }
    @Override
    public ServerDto createServer(RequestServer serverDetails, String userId) {
        ServerEntity serverEntity = ServerEntity.createServer(serverDetails.getName(), serverDetails.getInfo());
        UserInServerEntity userEntity = UserInServerEntity.createUserInServerEntity(userId, serverEntity);
        ChannelEntity defaultChannel = ChannelEntity.createChannel("default",
                "기본 채널입니다.",
                0,
                serverEntity);
        serverEntity.addChannel(defaultChannel);
        serverEntity.addUser(userEntity);

        serverRepository.save(serverEntity);

        ServerDto returnValue = mapper.map(serverEntity, ServerDto.class);
        return returnValue;
    }

    @Override
    public ServerDto getServerById(Long serverId) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(()->new NoSuchElementException());
        ServerDto result = new ModelMapper().map(server, ServerDto.class);
        return result;
    }

    @Override
    public ServerDto deleteServer(Long serverId) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(() -> new NoSuchElementException());
        ServerDto res = new ModelMapper().map(server, ServerDto.class);
        serverRepository.delete(server);
        return res;
    }

    @Override
    public ServerDto updateServer(Long serverId, RequestServer newServer) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(()->new NoSuchElementException());
        server.setName(newServer.getName());
        server.setInfo(newServer.getInfo());

        ServerDto res = new ModelMapper().map(server, ServerDto.class);
        return res;
    }

    @Override
    public ServerDto addUser(Long serverId, String userEmail) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(()->new NoSuchElementException());
        RequestEmail email = new RequestEmail();
        email.setUserEmail(userEmail);
        String userId = userServiceClient.getUserIdByEmail(userEmail);
        UserInServerEntity user = UserInServerEntity.createUserInServerEntity(userId, server);

        server.addUser(user);

        serverRepository.save(server);

        ServerDto res = new ModelMapper().map(server, ServerDto.class);
        return res;
    }

    @Override
    public ServerDto deleteUserInServer(Long serverId, String userEmail) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(()->new NoSuchElementException());
        RequestEmail email = new RequestEmail();
        email.setUserEmail(userEmail);
        String userId = userServiceClient.getUserIdByEmail(userEmail);

        server.deleteUser(userId);

        serverRepository.save(server);

        ServerDto res = new ModelMapper().map(server, ServerDto.class);
        return res;
    }

}
