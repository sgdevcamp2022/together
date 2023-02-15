package com.example.userservice.service;

public interface FriendService {
    void addFriend(String myMail, String followerMail);
    void deleteFriend(String myMail, String followerMail);
}
