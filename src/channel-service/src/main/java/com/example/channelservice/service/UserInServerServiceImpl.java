package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.repository.ServerEntity;
import com.example.channelservice.repository.UserInServerEntity;
import com.example.channelservice.repository.UserInServerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInServerServiceImpl implements UserInServerService{
    UserInServerRepository userInServerRepository;

    @Autowired
    public UserInServerServiceImpl(UserInServerRepository userInServerRepository) {
        this.userInServerRepository = userInServerRepository;
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
}
