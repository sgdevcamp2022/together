package com.example.channelservice.repository;

import org.springframework.data.repository.CrudRepository;

public interface ServerRepository extends CrudRepository<ServerEntity, Long> {
    Iterable<ServerEntity> findByUserId(String userId);
    ServerEntity findByServerId(String serverId);
}
