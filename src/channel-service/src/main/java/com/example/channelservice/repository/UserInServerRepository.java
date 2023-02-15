package com.example.channelservice.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserInServerRepository extends CrudRepository<UserInServerEntity, Long> {
    Iterable<UserInServerEntity> findAllByUserId(String userId);
}
