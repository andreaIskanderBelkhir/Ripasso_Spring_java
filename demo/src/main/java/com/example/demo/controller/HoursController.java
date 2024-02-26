package com.example.demo.controller;

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
    public ResponseEntity<GetGamePlayedResponseDTO> getGamePlayed(@PathVariable Long id) {
        headers.clear();
        List<Game> lista = hoursService.getplayedG(id).orElseGet(()->null);
        ResponseEntity<GetGamePlayedResponseDTO> response = null;
        if ((lista != null)) {
            GetGamePlayedResponseDTO listaDTO = GetGamePlayedResponseDTO.builder()
                    .allGameplayedDTO(lista.stream().map(GameMapper::mapperToGet)
                                    .map(g -> g.orElse(null)).toList())
                            .build();
            response = new ResponseEntity<>(listaDTO, headers, HttpStatus.OK);
        } else  {
            response = new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
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
