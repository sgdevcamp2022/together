package com.example.channelservice.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHANNEL_ID")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String Info;
    @Column(nullable = false)
    private Integer type;

    @ManyToOne
    @JoinColumn(name = "SERVER_ID")
    private ServerEntity server;

}