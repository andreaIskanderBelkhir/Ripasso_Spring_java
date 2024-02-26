package com.example.demo.controller.utility;

import com.example.demo.controller.HoursController;
import com.example.demo.dto.game.GameMapper;
import com.example.demo.dto.game.response.GetGamePlayedResponseDTO;
import com.example.demo.entity.Game;
import com.example.demo.service.HoursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Component
public class HoursUtilityController {
    @Autowired
    private HoursService hoursService;
    private final HttpHeaders headers = new HttpHeaders();

    private static final Logger logger = LoggerFactory.getLogger(HoursController.class);


    public ResponseEntity<Object> supportAddGame( Long id_u,  Long id_g) {
        try {
            hoursService.addGame(id_u, id_g);
            return ResponseEntity.ok().body("added");
        } catch (Error e) {
            return ResponseEntity.internalServerError().body("Error ");
        }
    }


    public ResponseEntity<String> supportUpdateHour( Long id,  Long id_g,  Long h) {
        try {
            hoursService.modifyhour(id, id_g, h);
            return ResponseEntity.ok().body("added");
        } catch (Error e) {
            return ResponseEntity.internalServerError().body("Error ");
        }
    }


    public ResponseEntity<GetGamePlayedResponseDTO> supportGetGamePlayed( Long id) {
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

    public ResponseEntity<String> supportAllGameByHours(){
        try{
            hoursService.allhourallgame();
            return ResponseEntity.ok("stamapti su console");
        }catch (Error e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
