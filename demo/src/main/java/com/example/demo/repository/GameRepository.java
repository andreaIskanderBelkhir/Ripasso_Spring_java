package com.example.demo.repository;

import com.example.demo.entity.Game;
import org.springframework.data.repository.CrudRepository;

/**
 * class based on spring jpa-data, in this class the methods interact with the db
 */
public interface GameRepository extends CrudRepository<Game,Long> {
}
