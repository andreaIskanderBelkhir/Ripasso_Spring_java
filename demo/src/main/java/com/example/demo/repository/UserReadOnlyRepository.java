package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.Optional;
/**
 * class based on spring jpa-data, in this class the methods interact with the db, used only for reading from the db for better performance
 */
public interface UserReadOnlyRepository extends ReadOnlyRepository<User,Long>{
    Optional<User> findByNameOrEmail(String username, String email);
    Optional<User> findByNameAndEmail(String username, String email);
}
