package com.example.channelservice.repository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_in_server")
public class UserInServerEntity {
    @Id
    @GeneratedValue
    @Column(name = "USER_IN_SERVER_ID")
    private long id;
    @Column
    private String userId;

    @ManyToOne
    @JoinColumn(name = "SERVER_ID")
    private ServerEntity server;

    @Builder
    public UserInServerEntity(String userId,
                         ServerEntity server){
        this.userId = userId;
        this.server = server;
    }

    public static UserInServerEntity createUserInServerEntity(String userId,
                                                              ServerEntity server) {
        return UserInServerEntity.builder()
                .userId(userId)
                .server(server)
                .build();
    }
}
