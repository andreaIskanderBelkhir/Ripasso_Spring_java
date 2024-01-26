package com.example.demo.Repository;

import com.example.demo.entita.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game,Long> {
}
