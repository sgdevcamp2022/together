package com.example.channelservice.repository;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "channel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHANNEL_ID")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String info;
    @Column(nullable = false)
    private Integer type;

    @ManyToOne
    @JoinColumn(name = "SERVER_ID")
    private ServerEntity server;

    @Builder
    public ChannelEntity(String channelName,
                         String channelInfo,
                         Integer type,
                         ServerEntity server){
        this.name = channelName;
        this.info = channelInfo;
        this.type = type;
        this.server = server;
    }

    public static ChannelEntity createChannel(String channelName,
                                              String channelInfo,
                                              Integer type,
                                              ServerEntity server) {
        return ChannelEntity.builder()
                .channelName(channelName)
                .channelInfo(channelInfo)
                .type(type)
                .server(server)
                .build();
    }
}