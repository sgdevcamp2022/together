package com.example.channelservice.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_in_server")
public class UserInServerEntity {
    @Id
    @GeneratedValue
    @Column(name = "USER_IN_SERVER_ID")
    private long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "user")
    private List<UserServer> userServers = new ArrayList<>();
}
