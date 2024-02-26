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

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameUtilityController gameUtility;


    @PostMapping
    public ResponseEntity<CreateGameResponseDTO> Addgame(@RequestBody CreateGameRequestDTO game){
        return gameUtility.supportAddGame(game);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetGameResponseDTO> Getbyid(@PathVariable Long id){
        return gameUtility.supportGetbyid(id);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> Deletbyid(@PathVariable Long id){
        return gameUtility.supportDeletbyid(id);
    }


}
