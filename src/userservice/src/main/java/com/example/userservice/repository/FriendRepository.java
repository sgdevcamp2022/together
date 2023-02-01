package com.example.userservice.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FriendRepository extends CrudRepository<FriendEntity, Long> {
    Optional<FriendEntity> findByEmail(String email);
}
