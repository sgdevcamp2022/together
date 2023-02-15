package com.example.channelservice.repository;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    // TODO 지우거나 수정할 때 쉽게 찾으려 hashmap으로 변경할지 고민
    private List<ChannelEntity> channelList = new ArrayList<>();
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInServerEntity> userList = new ArrayList<>();

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

    public void addUser(UserInServerEntity user) {
        this.userList.add(user);
    }
    public void deleteUser(String userId) {
        this.userList.stream()
                .filter(o->o.getUserId().equals(userId))
                .collect(Collectors.toList())
                .forEach(us->this.userList.remove(us));
    }

    public void addChannel(ChannelEntity channel) {
        this.channelList.add(channel);
    }
    public void deleteChannel(String channelName) {
        this.channelList.stream()
                .filter(o->o.getName().equals(channelName))
                .collect(Collectors.toList())
                .forEach(li->{this.channelList.remove(li);});
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
