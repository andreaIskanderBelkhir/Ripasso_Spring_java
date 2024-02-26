package com.example.demo.controller;

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

@RestController
@RequestMapping("/hours")
public class HoursController {
    @Autowired
    private HoursService hoursService;
    private final HttpHeaders headers = new HttpHeaders();

    private static final Logger logger = LoggerFactory.getLogger(HoursController.class);

    @PostMapping("/addgame/{id_u}/{id_g}")
    public ResponseEntity<Object> addGame(@PathVariable Long id_u, @PathVariable Long id_g) {
        try {
            hoursService.addGame(id_u, id_g);
            return ResponseEntity.ok().body("added");
        } catch (Error e) {
            return ResponseEntity.internalServerError().body("Error ");
        }
    }

    @PutMapping("/updatehour/{id}/{id_g}/{h}")
    public ResponseEntity<String> updateHour(@PathVariable Long id, @PathVariable Long id_g, @PathVariable Long h) {
        try {
            hoursService.modifyhour(id, id_g, h);
            return ResponseEntity.ok().body("added");
        } catch (Error e) {
            return ResponseEntity.internalServerError().body("Error ");
        }
    }

    @GetMapping("/playedGame/{id}")
    public ResponseEntity<List<Game>> getGamePlayed(@PathVariable Long id) {
        List<Game> lista = hoursService.getplayedG(id);
        ResponseEntity<List<Game>> response = null;
        headers.clear();
        if ((lista != null) && (!lista.isEmpty())) {
            headers.set("steamlib", "leggi a sinistra");
            response = new ResponseEntity<>(lista, headers, HttpStatus.OK);
        } else if (lista == null) {
            headers.set("error", "non presente l'utente");
            response = new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        } else {
            headers.set("error", "lista vuota");
            response = new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
        }
        return response;
    }

    @GetMapping("/allGameByHours")
    public ResponseEntity<String> AllGameByHours(){
        try{
            hoursService.allhourallgame();
            return ResponseEntity.ok("stamapti su console");
        }catch (Error e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
