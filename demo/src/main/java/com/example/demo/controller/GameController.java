package com.example.demo.controller;

import com.example.demo.controller.utility.GameUtilityController;
import com.example.demo.dto.game.GameMapper;
import com.example.demo.dto.game.request.CreateGameRequestDTO;
import com.example.demo.dto.game.response.CreateGameResponseDTO;
import com.example.demo.dto.game.response.GetGameResponseDTO;
import com.example.demo.service.GameService;
import com.example.demo.service.HoursService;
import com.example.demo.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * GameController is the RestController class that expose endpoint for all the api refer to a Game
 */
@RestController
@RequestMapping("/game")
public class GameController {
    /**
     * Bean with the business for the controller
     */
    @Autowired
    private GameUtilityController gameUtility;


    /**
     * Endpoint for the api that create a new game in the db
     * @param game  request body of a game under the DTO design pattern
     * @return response entity with a game under the DTO design pattern for the body, and a response code "200" if the game is added to the db or else a code "500"
     */
    @PostMapping
    public ResponseEntity<CreateGameResponseDTO> addGame(@RequestBody CreateGameRequestDTO game){
        return gameUtility.supportAddGame(game);
    }

    /**
     * Endpoint for the api that retrive a specif game using  id as query
     * @param id a long of the game you want to retrive
     * @return response entity with a game under the DTO design pattern for the body, and a response code "200" if the game is updated in the db or else a code "204" if the game isn't found in the db
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetGameResponseDTO> getById(@PathVariable Long id){
        return gameUtility.supportGetbyid(id);


    }

    /**
     * Endpoint for the api that remove a specific game from the db
     * @param id a long of the game you want to remove
     * @return response entity with a string that specified the result of the operation, and a code "200" if the game is removed or "500"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return gameUtility.supportDeletbyid(id);
    }


}
