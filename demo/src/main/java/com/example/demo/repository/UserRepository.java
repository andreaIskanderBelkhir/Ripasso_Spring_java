package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
/**
 * class based on spring jpa-data, in this class the methods interact with the db
 */
public interface UserRepository extends CrudRepository<User,Long> {

}
