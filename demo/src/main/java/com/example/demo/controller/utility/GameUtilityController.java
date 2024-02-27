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

/**
 * Bean with the logic for GameController
 */
@Component
public class GameUtilityController {
    /**
     * Bean with the Service design pattern fpr the game
     */
    @Autowired
    private GameService gameS;

    /**
     * Business logic for the endpoint addGame
     * @param game  request body of a game under the DTO design pattern
     * @return response entity with a game under the DTO design pattern for the body, and a response code "200" if the game is added to the db or else a code "500"
     */
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

    /**
     * Business logic for the endpoint getById
     * @param id a long of the game you want to retrive
     * @return response entity with a game under the DTO design pattern for the body, and a response code "200" if the game is updated in the db or else a code "204" if the game isn't found in the db
     */
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

    /**
     * Business logic for the endpoint deleteById
     * @param id a long of the game you want to remove
     * @return response entity with a string that specified the result of the operation, and a code "200" if the game is removed or "500"
     */
    public ResponseEntity<String> supportDeletbyid(@PathVariable Long id){
        try{
            gameS.Deletegame(id);
            return ResponseEntity.ok().body("eliminato");
        }catch (Error error){
            return ResponseEntity.internalServerError().build();
        }
    }

}
