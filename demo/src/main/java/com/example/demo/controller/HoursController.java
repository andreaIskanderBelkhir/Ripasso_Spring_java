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

@RestController
@RequestMapping("/hours")
public class HoursController {
    @Autowired
    private HoursUtilityController hoursUtility;

    @PostMapping("/addgame/{id_u}/{id_g}")
    public ResponseEntity<Object> addGame(@PathVariable Long id_u, @PathVariable Long id_g) {
        return hoursUtility.supportAddGame(id_u, id_g);
    }

    @PutMapping("/updatehour/{id}/{id_g}/{h}")
    public ResponseEntity<String> updateHour(@PathVariable Long id, @PathVariable Long id_g, @PathVariable Long h) {
        return hoursUtility.supportUpdateHour(id, id_g, h);
    }

    @GetMapping("/playedGame/{id}")
    public ResponseEntity<GetGamePlayedResponseDTO> getGamePlayed(@PathVariable Long id) {
        return hoursUtility.supportGetGamePlayed(id);
    }

    @GetMapping("/allGameByHours")
    public ResponseEntity<String> AllGameByHours(){
        return hoursUtility.supportAllGameByHours();

}
