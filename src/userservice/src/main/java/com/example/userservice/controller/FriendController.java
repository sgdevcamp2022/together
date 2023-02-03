package com.example.userservice.controller;

import com.example.userservice.service.FriendService;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestFriend;
import com.example.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
public class FriendController {
    private final UserService userService;
    private final FriendService friendService;
    public FriendController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseUser> addFriend(@RequestBody RequestFriend req) {
        friendService.addFriend(req.getMyEmail(),req.getFriendEmail());

        ResponseUser response = new ModelMapper().map(userService.getUserByEmail(req.getMyEmail()), ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseUser> deleteFriend(@RequestBody RequestFriend req) {
        friendService.deleteFriend(req.getMyEmail(),req.getFriendEmail());

        ResponseUser response = new ModelMapper().map(userService.getUserByEmail(req.getMyEmail()), ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
