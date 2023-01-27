package com.example.channelservice.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServerRepository extends CrudRepository<ServerEntity, Long> {
    @Override
    Optional<ServerEntity> findById(Long aLong);
}
