package com.example.userservice.client;

import com.example.userservice.vo.ResponseServer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "channel-service")
public interface ChannelServiceClient {


    @GetMapping("{user_id}/servers")
    List<ResponseServer> getServers(@PathVariable("user_id") String userId);
}
