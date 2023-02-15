package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.dto.UserDto;
import com.example.channelservice.repository.ServerEntity;
import com.example.channelservice.repository.ServerRepository;
import com.example.channelservice.repository.UserInServerEntity;
import com.example.channelservice.repository.UserInServerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserInServerServiceImpl implements UserInServerService{
    UserInServerRepository userInServerRepository;
    ServerRepository serverRepository;

    @Autowired
    public UserInServerServiceImpl(UserInServerRepository userInServerRepository, ServerRepository serverRepository) {
        this.userInServerRepository = userInServerRepository;
        this.serverRepository = serverRepository;
    }

    @Override
    public List<ServerDto> getAllServersByUserId(String userId) {
        Iterable<UserInServerEntity> userList = userInServerRepository.findAllByUserId(userId);
        List<ServerEntity> serverEntityList = new ArrayList<>();
        List<ServerDto> res = new ArrayList<>();

        userList.forEach(e -> {
            serverEntityList.add(e.getServer());
        });
        serverEntityList.forEach(e -> {
            res.add(new ModelMapper().map(e, ServerDto.class));
        });

        return res;
    }

    @Override
    public List<UserDto> getAllUsersByServerId(Long serverId) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(()->new NoSuchElementException());
        List<UserInServerEntity> userList = server.getUserList();
        List<UserDto> res = new ArrayList<>();
        userList.forEach(e->{
            res.add(new ModelMapper().map(e, UserDto.class));
        });

        return res;
    }
}
