package com.example.demo.controller;

import com.example.demo.dto.user.UserMapper;
import com.example.demo.dto.user.request.CreateUserRequestDTO;
import com.example.demo.dto.user.request.PutUserRequestDTO;
import com.example.demo.dto.user.response.CreateUserResponseDTO;
import com.example.demo.dto.user.response.GetAllUserResponseDTO;
import com.example.demo.dto.user.response.GetUserResponseDTO;
import com.example.demo.dto.user.response.PutUserResponseDTO;
import com.example.demo.service.HoursService;
import com.example.demo.service.UserService;

import com.example.demo.entity.Game;
import com.example.demo.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HoursService hoursService;

    private final HttpHeaders headers = new HttpHeaders();
    // Create a new user

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO userRequestDTO) {

        try {
            boolean res = (userService.getuserById(userRequestDTO.getId()).isPresent()) ||
                    (userService.getuserByNameOrEmail(userRequestDTO.getName()).isPresent()) ||
                    (userService.getuserByNameOrEmail(userRequestDTO.getEmail()).isPresent());

            if (!res) {
                User useradd = userService.createUser(UserMapper.mapper(userRequestDTO));
                CreateUserResponseDTO userResponseDTO=UserMapper.mapperToCreate(useradd);
                return ResponseEntity.ok(userResponseDTO);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (IllegalArgumentException e) {
            logger.info("illegal ARGS");
            return ResponseEntity.internalServerError().body(null);
        } catch (DataIntegrityViolationException a) {
            logger.info("dataIntegrityViolation");
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<GetAllUserResponseDTO> getAllUsers() {
        headers.clear();
        List<User> lista                               = userService.getallUser().orElseGet(() -> null);
        ResponseEntity<GetAllUserResponseDTO> risposta = null;
        if (lista != null) {
            GetAllUserResponseDTO listadto             = GetAllUserResponseDTO.builder()
                    .allUserDTO(lista.stream().map(UserMapper::mapperToGet)
                            .map(u -> u.orElse(null)).toList())
                            .build();

            headers.set("Custom", "Yep thats the success header");
            risposta = new ResponseEntity<>(listadto, headers, HttpStatus.OK);
        } else {
            headers.set("Failed", "Failing header");
            risposta = new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
        }
        return risposta;
    }

    // Get user by a string o long
    @GetMapping("/{string}")

    public ResponseEntity<GetUserResponseDTO> getUserByNameOrMail(@PathVariable String string) {
        User user;
        if (string.matches("\\b\\d+\\b")) {
            user = userService.getuserById(Long.parseLong(string)).orElseGet(() -> null);
        } else {
            user = userService.getuserByNameOrEmail(string).orElseGet(() -> null);
        }
        ResponseEntity<GetUserResponseDTO> rep = null;
        headers.clear();
        Optional<GetUserResponseDTO> userResponse    = UserMapper.mapperToGet(user);
        if (userResponse.isPresent()) {
            headers.set("Custom-Header", "YEP custom header");
            rep = new ResponseEntity<>(userResponse.get(), headers, HttpStatus.OK);
            //rep=ResponseEntity.ok().header("Custom-Header", "YEP custom header").body(user); DA usare non con Responce Entity ma con elementi che usnao tipo Mono<Repon..>
        } else {
            headers.set("Failing", "YEP thsts a failing hjeader");
            rep = new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);

        }
        return rep;
    }

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<PutUserResponseDTO> updateUser(@PathVariable Long id, @RequestBody PutUserRequestDTO userDetails) {

        Optional<User> useropt                       = userService.updateUser(id, UserMapper.mapper(userDetails));
        Optional<PutUserResponseDTO> userResponseDTO = UserMapper.mapperToPut(useropt);

        return userResponseDTO
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.noContent().build());
    }


    // Delete all users
    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok().body("All users have been deleted successfully.");
        } catch (Error e) {
            return ResponseEntity.internalServerError().body("Error in deleting all");
        }

    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("the user have been deleted successfully.");
        } catch (Error e) {
            return ResponseEntity.internalServerError().body("Error in deleting that user");
        }

    }

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
    public ResponseEntity<String> updatehour(@PathVariable Long id, @PathVariable Long id_g, @PathVariable Long h) {
        try {
            hoursService.modifyhour(id, id_g, h);
            return ResponseEntity.ok().body("added");
        } catch (Error e) {
            return ResponseEntity.internalServerError().body("Error ");
        }
    }

    @GetMapping("/playedGame/{id}")
    public ResponseEntity<List<Game>> getgameplayed(@PathVariable Long id) {
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

}

