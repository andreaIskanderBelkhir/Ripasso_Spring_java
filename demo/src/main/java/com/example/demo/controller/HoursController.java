package com.example.demo.controller;

import com.example.demo.controller.utility.HoursUtilityController;
import com.example.demo.dto.game.GameMapper;
import com.example.demo.dto.game.response.GetGamePlayedResponseDTO;
import com.example.demo.dto.game.response.GetGameResponseDTO;
import com.example.demo.entity.Game;
import com.example.demo.service.HoursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HoursController is a restController class that expose endpoint for all the api referred to the association game-user
 */
@RestController
@RequestMapping("/hours")
public class HoursController {
    /**
     * Bean with the business for the controller
     */
    @Autowired
    private HoursUtilityController hoursUtility;

    /**
     * Endpoint for the api that add a game to an user
     * @param id_u the id of the user you want to add the game
     * @param id_g the id of the game you want to add
     * @return response entity with a string that specified the result of the operation, and a code "200" if the user is removed or "500"
     */
    @PostMapping("/addGame/{id_u}/{id_g}")
    public ResponseEntity<Object> addGame(@PathVariable Long id_u, @PathVariable Long id_g) {
        return hoursUtility.supportAddGame(id_u, id_g);
    }

    /**
     * Endpoint for the api that update the parameter of the relationship of a specif game and a user
     * @param id id of the user of the relationship
     * @param id_g id of the game of the relationship
     * @param h a long value that indicate how much the parameter is updated
     * @return response entity with a string that specified the result of the operation, and a code "200" if the user is removed or "500"
     */
    @PutMapping("/updateHour/{id}/{id_g}/{h}")
    public ResponseEntity<String> updateHour(@PathVariable Long id, @PathVariable Long id_g, @PathVariable Long h) {
        return hoursUtility.supportUpdateHour(id, id_g, h);
    }

    /**
     * Endpoint for the api that retrieve all the games associated to a specific user
     * @param id id of the user you want all the games
     * @return response entity with an object under the DTO design pattern having a list of game, and a response code "200"  if the list is not empty or else a code "204"
     */
    @GetMapping("/playedGame/{id}")
    public ResponseEntity<GetGamePlayedResponseDTO> getGamePlayed(@PathVariable Long id) {
        return hoursUtility.supportGetGamePlayed(id);
    }

    /**
     * Endpoint for the api that print all the games in the db with the summs of all the hours from all users
     * @return response entity with a string that specified the result of the operation, and a code "200" if the user is removed or "500"
     */
    @GetMapping("/allGameByHours")
    public ResponseEntity<String> allGameByHours() {
        return hoursUtility.supportAllGameByHours();
    }
}
