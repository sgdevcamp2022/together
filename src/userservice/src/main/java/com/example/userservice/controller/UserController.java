package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.exception.CustomException;
import com.example.userservice.exception.ErrorCode;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.security.TokenProvider;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUpdateUser;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseDetailUser;
import com.example.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public UserController(UserService userService,
                          TokenProvider tokenProvider,
                          RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
//        변환 객체간 필드 명이 정확이 일치해야함
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map
                (user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String,Object>> getUser(@PathVariable("id") String userId) {
        Map<String, Object> res = new HashMap<>();
        ResponseDetailUser userDetail = userService.getUserDetailsByUserId(userId);

        ResponseDetailUser returnValue = new ModelMapper().map(userDetail, ResponseDetailUser.class);
        res.put("user",returnValue);


        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<ResponseUser> updateUser(@PathVariable("id") String userId,
                                                   @RequestBody RequestUpdateUser userInfo) {
        UserDto userDto = userService.updateUser(userId, userInfo);

        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body("사용자 삭제 완료" + userId);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getAllUser();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @PostMapping("/user/refresh")
    public Map<String, Object> requestAccessToken(@RequestBody Map<String, String> m){
        String userId = null;
        Map<String, Object> map = new HashMap<>();
        String expiredAccessToken = m.get("accessToken");
        String refreshToken = m.get("refreshToken");

//        만료된 accessToken에서 email 가져오기 시도
        userId = tokenProvider.getIdFromAccessToken(expiredAccessToken);

        String email = userService.getUserDetailsByUserId(userId).getEmail();
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();

        String refreshTokenFromDb = (String) vop.get(email);
        if(refreshTokenFromDb == null) throw new CustomException(ErrorCode.BAD_TOKEN);

//        저장된 refreshToken와 body의 refreshToken이 같은지 확인
        if (!refreshToken.equals(refreshTokenFromDb)){
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

//        refresh 토큰 만료 확인
        tokenProvider.checkValidToken(refreshToken);


//          다 통과했다면 accessToken 반환
        String newAccessToken = tokenProvider.buildAccessToken(userId);

        map.put("accessToken", newAccessToken);
        map.put("refreshToken", refreshToken);

        return map;
    }
}
