package com.example.channelservice.repository;

import javax.persistence.*;

@Entity
public class UserServer {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "USER_IN_SERVER_ID")
    private UserInServerEntity user;

    @ManyToOne
    @JoinColumn(name = "SERVER_ID")
    private ServerEntity server;

}
