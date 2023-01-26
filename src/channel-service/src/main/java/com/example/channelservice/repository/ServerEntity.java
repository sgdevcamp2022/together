package com.example.channelservice.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "server")
public class ServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVER_ID")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String Info;

    @OneToMany(mappedBy = "server")
    private List<ChannelEntity> channelList = new ArrayList<>();
    @OneToMany(mappedBy = "server")
    private List<UserServer> userServers = new ArrayList<>();
}
