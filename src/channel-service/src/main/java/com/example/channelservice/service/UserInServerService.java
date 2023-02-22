package com.example.channelservice.service;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.dto.UserDto;

import java.util.List;

public interface UserInServerService {
    List<ServerDto> getAllServersByUserId(String userId);
    List<UserDto> getAllUsersByServerId(Long serverId);
    String getUserIdByToken(String atk);
}
