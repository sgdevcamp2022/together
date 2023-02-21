package com.example.channelservice.client;

import com.example.channelservice.vo.RequestEmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserServiceClient {


    @GetMapping("/token/{atk}")
    String requestParsingToken(@PathVariable("atk") String token);

    @RequestMapping(method = RequestMethod.GET, value = "id/{email}")
    String getUserIdByEmail(@PathVariable("email") String email);
}
