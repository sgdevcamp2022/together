package com.example.userservice.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
    UserEntity findByEmail(String email);
    Boolean existsUserEntityByEmail(String email);
}