package com.example.channelservice.service;

import com.example.channelservice.dto.ChannelDto;
import com.example.channelservice.repository.ChannelEntity;
import com.example.channelservice.repository.ChannelRepository;
import com.example.channelservice.repository.ServerEntity;
import com.example.channelservice.repository.ServerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService{

    ServerRepository serverRepository;
    ChannelRepository channelRepository;
    final ModelMapper mapper = new ModelMapper();
    @Autowired
    public ChannelServiceImpl(ServerRepository serverRepository, ChannelRepository channelRepository){
        this.serverRepository = serverRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public ChannelDto createChannel(ChannelDto channelDetails, Long serverId) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(()->new NoSuchElementException());

            ChannelEntity channelEntity = ChannelEntity.createChannel(channelDetails.getName(),
                    channelDetails.getInfo(),
                    channelDetails.getType(),
                    server);
            server.addChannel(channelEntity);

            serverRepository.save(server);
            log.info("서버에 채널 생성 성공");

            ChannelDto returnValue = mapper.map(channelEntity, ChannelDto.class);

            return returnValue;
    }

    @Override
    public List<ChannelDto> getChannelsByServerId(Long serverId) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(()->new NoSuchElementException());
        Iterable<ChannelEntity> channelEntities = server.getChannelList();

        List<ChannelDto> channelList = new ArrayList<>();
        channelEntities.forEach(channel -> {
            channelList.add(new ModelMapper().map(channel,ChannelDto.class));
        });
        return channelList;
    }

    @Override
    public ChannelDto deleteChannel(Long serverId, String channelName) {
        ServerEntity server = serverRepository.findById(serverId).orElseThrow(()-> new NoSuchElementException());

        server.deleteChannel(channelName);
        log.info(channelName+"채널이 삭제됨");
        serverRepository.save(server);

        return null;
    }
}
