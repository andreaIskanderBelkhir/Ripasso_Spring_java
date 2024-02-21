package com.example.demo.controller;

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
    private GameService gameS;
    @Autowired
    private HoursService hoursS;

    @PostMapping
    public ResponseEntity<Game> Addgame(@RequestBody Game game){
        try {
            Game gameADDED=gameS.Addgame(game);
            if(gameADDED!=null) {
                return ResponseEntity.ok(gameADDED);
            }
            else {
                return ResponseEntity.internalServerError().build();
            }
        }catch (Error e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> Getbyid(@PathVariable Long id){
        Game gametrovato= gameS.GetById(id).orElseGet(()->null);
        if(gametrovato!=null)
        {
            return ResponseEntity.ok().body(gametrovato);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> Deletbyid(@PathVariable Long id){
        try{
            gameS.Deletegame(id);
            return ResponseEntity.ok().body("eliminato");
        }catch (Error error){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/allGameByHours")
    public ResponseEntity<String> Allgamebyhours(){
        try{
            hoursS.allhourallgame();
            return ResponseEntity.ok("stamapti su console");
        }catch (Error e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
