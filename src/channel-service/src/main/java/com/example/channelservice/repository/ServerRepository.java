package com.example.channelservice.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServerRepository extends CrudRepository<ServerEntity, Long> {
    Optional<Iterable<ServerEntity>> findByUserId(String userId);
    Optional<ServerEntity> findByServerId(String serverId);
}
