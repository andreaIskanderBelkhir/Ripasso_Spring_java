package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.Optional;

public interface UserReadOnlyRepository extends ReadOnlyRepository<User,Long>{
    Optional<User> findByNameOrEmail(String username, String email);
    Optional<User> findByNameAndEmail(String username, String email);
}
