package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.repository.ServerEntity;
import com.example.channelservice.repository.ServerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServerServiceImpl implements ServerService{
    ServerRepository serverRepository;
    @Autowired
    public ServerServiceImpl(ServerRepository serverRepository){
        this.serverRepository = serverRepository;
    }
    @Override
    public ServerDto createServer(ServerDto serverDetails) {
        serverDetails.setServerId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ServerEntity serverEntity = mapper.map(serverDetails, ServerEntity.class);

        serverRepository.save(serverEntity);

        ServerDto returnValue = mapper.map(serverEntity, ServerDto.class);

        return returnValue;
    }

    @Override
    public Iterable<ServerEntity> getServersByUserId(String userId) {
        return serverRepository.findByUserId(userId);
    }

    @Override
    public ServerDto getServerByServerId(String serverId) {
        ServerEntity serverEntity = serverRepository.findByServerId(serverId);
        ServerDto serverDto = new ModelMapper().map(serverEntity, ServerDto.class);

        return serverDto;
    }
}
