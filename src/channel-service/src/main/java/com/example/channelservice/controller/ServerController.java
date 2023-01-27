package com.example.channelservice.controller;

import com.example.channelservice.dto.ChannelDto;
import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.service.ChannelService;
import com.example.channelservice.service.ServerService;
import com.example.channelservice.vo.RequestServer;
import com.example.channelservice.vo.ResponseChannel;
import com.example.channelservice.vo.ResponseServer;
import com.example.channelservice.vo.ResponseServerDetail;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

    @Autowired
    public ServerController(ChannelService channelService, ServerService serverService){
        this.channelService = channelService;
        this.serverService = serverService;
    }

    @PostMapping("/server")
    public ResponseEntity<ResponseServer> createServer(@RequestBody RequestServer serverDetails) {
//        TODO 선행조건으로 만드는 유저를 UserInServer로 생성하기
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

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

        result.setChannelList(channelRes);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
