package com.example.channelservice.repository;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "server")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVER_ID")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String info;

    @OneToMany(mappedBy = "server",
    cascade = CascadeType.ALL)
    private List<ChannelEntity> channelList = new ArrayList<>();
    @OneToMany(mappedBy = "server",
    cascade = CascadeType.ALL)
    private List<UserServer> userServers = new ArrayList<>();

    @Builder
    public ServerEntity(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public static ServerEntity createServer(String serverName, String serverInfo) {
        return ServerEntity.builder()
                .name(serverName)
                .info(serverInfo)
                .build();
    }

    public void addChannel(ChannelEntity channel) {
        this.channelList.add(channel);
    }


}
