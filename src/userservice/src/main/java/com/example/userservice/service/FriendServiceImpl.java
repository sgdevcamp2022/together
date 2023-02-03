package com.example.userservice.service;

import com.example.userservice.exception.CustomException;
import com.example.userservice.exception.ErrorCode;
import com.example.userservice.repository.FriendEntity;
import com.example.userservice.repository.FriendRepository;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendServiceImpl implements FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    public FriendServiceImpl(UserRepository userRepository,
                             FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    @Transactional
    @Override
    public void addFriend(String myMail, String followerMail) {
        UserEntity myInfo = userRepository.findByEmail(myMail)
                .orElseThrow(()-> new CustomException(ErrorCode.CANNOT_FIND_USER));
        UserEntity followerInfo = userRepository.findByEmail(followerMail)
                .orElseThrow(()-> new CustomException(ErrorCode.CANNOT_FIND_USER));

        FriendEntity friend = FriendEntity.addFriend(followerInfo.getName(),
                followerInfo.getEmail());

        myInfo.addFriend(friend);
        friendRepository.save(friend);
        userRepository.save(myInfo);
    }

    @Override
    @Transactional
    public void deleteFriend(String myMail, String followerMail) {
        UserEntity userEntity = userRepository.findByEmail(myMail)
                .orElseThrow(()-> new CustomException(ErrorCode.CANNOT_FIND_USER));
        FriendEntity friendEntity = friendRepository.findByEmail(followerMail)
                .orElseThrow(()->new CustomException(ErrorCode.CANNOT_FIND_USER));

        friendRepository.delete(friendEntity);
    }
}
