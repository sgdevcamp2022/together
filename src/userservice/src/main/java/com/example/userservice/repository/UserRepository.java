package com.example.userservice.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Long, UserEntity> {
    UserEntity findByUserId(String userId);
    UserEntity findByEmail(String email);
}
