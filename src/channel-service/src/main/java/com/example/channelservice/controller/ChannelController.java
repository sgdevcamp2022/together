package com.example.channelservice.controller;

import com.example.channelservice.dto.ChannelDto;
import com.example.channelservice.service.ChannelService;
import com.example.channelservice.vo.RequestChannel;
import com.example.channelservice.vo.ResponseChannel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ChannelController {
        ChannelService channelService;

        @Autowired
        public ChannelController(ChannelService channelService){
            this.channelService = channelService;
        }

        @PostMapping("/{server_id}/channel")
        public ResponseEntity<ResponseChannel> createChannel(@PathVariable("server_id") Long serverId,
                                             @RequestBody RequestChannel channelDetails) {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            ChannelDto channelDto = mapper.map(channelDetails, ChannelDto.class);
            ChannelDto createdChannel = channelService.createChannel(channelDto, serverId);

            ResponseChannel responseChannel= mapper.map(createdChannel, ResponseChannel.class);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseChannel);
        }

        @DeleteMapping("/{server_id}/channel/{channel_name}")
        public ResponseEntity<ResponseChannel> deleteChannel(@PathVariable("server_id") Long serverId,
                                                             @PathVariable("channel_name") String channelName) {
            ChannelDto channelDto = channelService.deleteChannel(serverId,channelName);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseChannel());
        }
}
