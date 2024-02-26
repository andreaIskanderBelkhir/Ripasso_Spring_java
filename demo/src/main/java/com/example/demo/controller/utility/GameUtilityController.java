package com.example.demo.controller.utility;

import com.example.demo.dto.game.GameMapper;
import com.example.demo.dto.game.request.CreateGameRequestDTO;
import com.example.demo.dto.game.response.CreateGameResponseDTO;
import com.example.demo.dto.game.response.GetGameResponseDTO;
import com.example.demo.entity.Game;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
public class GameUtilityController {
    @Autowired
    private GameService gameS;


    public ResponseEntity<CreateGameResponseDTO> supportAddGame( CreateGameRequestDTO game){
        try {
            Game gameADDED=gameS.Addgame(GameMapper.mapper(game));
            if(gameADDED!=null) {
                return ResponseEntity.ok(GameMapper.mapperToCreate(gameADDED));
            }
            else {
                return ResponseEntity.internalServerError().build();
            }
        }catch (Error e){
            return ResponseEntity.internalServerError().build();
        }
    }


    public ResponseEntity<GetGameResponseDTO> supportGetbyid(Long id){
        Game gametrovato= gameS.GetById(id).orElseGet(()->null);
        if(gametrovato!=null)
        {
            return ResponseEntity.ok().body(GameMapper.mapperToGet(gametrovato).get());
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }


    public ResponseEntity<String> supportDeletbyid(@PathVariable Long id){
        try{
            gameS.Deletegame(id);
            return ResponseEntity.ok().body("eliminato");
        }catch (Error error){
            return ResponseEntity.internalServerError().build();
        }
    }

}
