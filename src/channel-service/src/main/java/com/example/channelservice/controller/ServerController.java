package com.example.channelservice.controller;

import com.example.channelservice.dto.ServerDto;
import com.example.channelservice.repository.ServerEntity;
import com.example.channelservice.service.ServerService;
import com.example.channelservice.vo.RequestServer;
import com.example.channelservice.vo.ResponseServer;
import com.netflix.discovery.converters.Auto;
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
public class ServerController {
    ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    @PostMapping("/{user_id}/servers")
    public ResponseEntity<ResponseServer> createServer(@PathVariable("user_id") String userId,
                                                       @RequestBody RequestServer serverDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ServerDto serverDto = mapper.map(serverDetails, ServerDto.class);
        serverDto.setUserId(userId);
        ServerDto createdServer = serverService.createServer(serverDto);

        ResponseServer responseServer = mapper.map(createdServer, ResponseServer.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseServer);
    }

    @GetMapping("/{user_id}/servers")
    public ResponseEntity<List<ResponseServer>> getServer(@PathVariable("user_id") String userId) {
        Iterable<ServerEntity> serverList = serverService.getServersByUserId(userId);

        List<ResponseServer> result = new ArrayList<>();
        serverList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseServer.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
