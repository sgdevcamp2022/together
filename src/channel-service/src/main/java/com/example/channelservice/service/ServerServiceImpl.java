package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.repository.ChannelEntity;
import com.example.channelservice.repository.ServerEntity;
import com.example.channelservice.repository.ServerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class ServerServiceImpl implements ServerService{
    ServerRepository serverRepository;

    ModelMapper mapper = new ModelMapper();
    @Autowired
    public ServerServiceImpl(ServerRepository serverRepository){

        this.serverRepository = serverRepository;
    }
    @Override
    public ServerDto createServer(ServerDto serverDetails) {
        ServerEntity serverEntity = ServerEntity.createServer(serverDetails.getName(), serverDetails.getInfo());

        ChannelEntity defaultChannel = ChannelEntity.createChannel("default",
                "기본 채널입니다.",
                0,
                serverEntity);
        serverEntity.addChannel(defaultChannel);

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

}
