package com.example.demo.Repository;

import com.example.demo.entita.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByNameOrEmail(String username, String email);
}
