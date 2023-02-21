package com.example.channelservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {


    @GetMapping("/token/{atk}")
    String requestParsingToken(@PathVariable("atk") String token);
}
