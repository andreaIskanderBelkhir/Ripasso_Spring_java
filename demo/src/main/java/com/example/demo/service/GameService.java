package com.example.demo.service;

import com.example.demo.repository.GameRepository;
import com.example.demo.entity.Game;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Bean connection the controller to the repository
 */
@Service
@AllArgsConstructor
public class GameService {

    private GameRepository gameR;

    /**
     * Method for adding a game to db
     * @param game the entity to save
     * @return the entity that got saved
     */
    public Game Addgame(Game game){
        if(gameR.findById(game.getId()).isPresent()){
            return null;
        }
        return gameR.save(game);
    }
    /**
     * Method that retrive a game by his id
     * @param id the id of the game you want to retrive
     * @return an optional version of the user retrived, it can be empty
     */
    public  Optional<Game> GetById(Long id){
        return gameR.findById(id);
    }
    /**
     * Method for removing a single game from the db
     * @param id the id of the game to remove
     */
    public void Deletegame(Long id){
        gameR.deleteById(id);
    }


    //dati id da equavalenti giochi,non serve nel controller
    public List<Game> Getallbyid(List<Long> ids){
        return (List<Game>) gameR.findAllById(ids);
    }
}
