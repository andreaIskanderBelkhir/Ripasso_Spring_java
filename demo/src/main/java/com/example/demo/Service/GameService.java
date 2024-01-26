package com.example.demo.Service;

import com.example.demo.Repository.GameRepository;
import com.example.demo.entita.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    private GameRepository gameR;

    public Game Addgame(Game game){
        if(gameR.findById(game.getId()).isPresent()){
            return null;
        }
        return gameR.save(game);
    }
    public Optional<Game> GetById(Long id){
        return gameR.findById(id);
    }
    public void Deletegame(Long id){
        gameR.deleteById(id);
    }


    //dati id da equavalenti giochi,non serve nel controller
    public List<Game> Getallbyid(List<Long> ids){
        return (List<Game>) gameR.findAllById(ids);
    }
}
