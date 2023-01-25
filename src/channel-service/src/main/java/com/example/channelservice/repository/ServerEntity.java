package com.example.channelservice.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "server")
public class ServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String Info;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String serverId;
}
