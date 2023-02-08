package com.example.channelservice.controller;

import com.example.channelservice.dto.ChannelDto;
import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.dto.UserDto;
import com.example.channelservice.service.ChannelService;
import com.example.channelservice.service.ServerService;
import com.example.channelservice.service.UserInServerService;
import com.example.channelservice.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
@Slf4j
public class ServerController {
    ServerService serverService;
    ChannelService channelService;
    UserInServerService userInServerService;

    @Autowired
    public ServerController(ChannelService channelService,
                            ServerService serverService,
                            UserInServerService userInServerService){
        this.channelService = channelService;
        this.serverService = serverService;
        this.userInServerService = userInServerService;
    }

    @PostMapping("/server")
    public ResponseEntity<ResponseServer> createServer(@RequestBody RequestCreateServer serverDetails) {
        ModelMapper mapper = new ModelMapper();

        ServerDto serverDto = mapper.map(serverDetails, ServerDto.class);
        ServerDto createdServer = serverService.createServer(serverDto);

        ResponseServer responseServer = mapper.map(createdServer, ResponseServer.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseServer);
    }

    @GetMapping("server/{id}")
    public ResponseEntity<ResponseServerDetail> getServer(@PathVariable("id") Long serverId) {
        ServerDto server = serverService.getServerById(serverId);

        ResponseServerDetail result = new ModelMapper().map(server, ResponseServerDetail.class);
        List<ChannelDto> channelList = channelService.getChannelsByServerId(serverId);

        List<ResponseChannel> channelRes = new ArrayList<>();
        channelList.forEach(c ->{
            channelRes.add(new ModelMapper().map(c, ResponseChannel.class));
        });

        List<UserDto> userList = userInServerService.getAllUsersByServerId(serverId);
        List<ResponseUser> userRes = new ArrayList<>();
        userList.forEach(u->{
            userRes.add(new ModelMapper().map(u,ResponseUser.class));
        });


        result.setChannelList(channelRes);
        result.setUserList(userRes);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("server/{id}")
    public ResponseEntity<ResponseServer> deleteServer(@PathVariable("id") Long serverId) {
//        TODO 삭제할때는 연관 객체를 null하고 삭제해야함
        ServerDto deletedServer = serverService.deleteServer(serverId);

        ResponseServer responseServer = new ModelMapper().map(deletedServer, ResponseServer.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseServer);
    }

    @PostMapping("server/{id}")
    public ResponseEntity<ResponseServer> updateServer(@PathVariable("id") Long serverId,
                                                       @RequestBody RequestServer newServer) {
        ServerDto updatedServer = serverService.updateServer(serverId, newServer);

        ResponseServer responseServer = new ModelMapper().map(updatedServer, ResponseServer.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseServer);
    }

    @DeleteMapping("server/{id}/kick")
    public ResponseEntity<ResponseServer> deleteUserInServer(@PathVariable("id")Long serverId,
                                                             @RequestBody RequestUser oldUser) {
        ServerDto updatedServer = serverService.deleteUserInServer(serverId,oldUser.getReq_user());

        ResponseServer responseServer = new ModelMapper().map(updatedServer, ResponseServer.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseServer);
    }

    @PostMapping("server/{id}/join")
    public ResponseEntity<ResponseServer> addUserInServer(@PathVariable("id")Long serverId,
                                                          @RequestBody RequestUser newUser) {
        ServerDto updatedServer = serverService.addUser(serverId, newUser.getReq_user());

        ResponseServer responseServer = new ModelMapper().map(updatedServer, ResponseServer.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseServer);
    }

    @GetMapping("servers/{user_id}")
    public ResponseEntity<List<ResponseServer>> getServersByUserId(@PathVariable("user_id") String userId) {
        List<ServerDto> serverList = userInServerService.getAllServersByUserId(userId);
        List<ResponseServer> res = new ArrayList<>();

        serverList.forEach(e->{
            res.add(new ModelMapper().map(e,ResponseServer.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
