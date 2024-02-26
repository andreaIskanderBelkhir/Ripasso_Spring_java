package com.example.demo.controller.utility;

import com.example.demo.controller.UserController;
import com.example.demo.dto.user.UserMapper;
import com.example.demo.dto.user.request.CreateUserRequestDTO;
import com.example.demo.dto.user.request.PutUserRequestDTO;
import com.example.demo.dto.user.response.CreateUserResponseDTO;
import com.example.demo.dto.user.response.GetAllUserResponseDTO;
import com.example.demo.dto.user.response.GetUserResponseDTO;
import com.example.demo.dto.user.response.PutUserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Optional;

@Component
public class UserUtilityController {
    @Autowired
    private UserService userService;


    private final HttpHeaders headers = new HttpHeaders();


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public  ResponseEntity<CreateUserResponseDTO> supportCreateUser(CreateUserRequestDTO userRequestDTO){
        try {
            userRequestDTO.isValid();
            boolean presId    = userService.getuserById(userRequestDTO.getId()).isPresent();
            boolean presName  = userService.getuserByNameOrEmail(userRequestDTO.getName()).isPresent();
            boolean presEmail = userService.getuserByNameOrEmail(userRequestDTO.getEmail()).isPresent();
            boolean res = presId   ||
                    presName ||
                    presEmail;

            if (!res) {
                User useradd = userService.createUser(UserMapper.mapper(userRequestDTO));
                CreateUserResponseDTO userResponseDTO=UserMapper.mapperToCreate(useradd);
                return ResponseEntity.ok(userResponseDTO);
            } else {
                if(presId){
                    logger.warn("id gia presente");
                }
                if(presName){
                    logger.warn("nome gia presente");
                }
                if(presEmail){
                    logger.warn("email gia presente");
                }
                return ResponseEntity.badRequest().build();
            }
        } catch (IllegalArgumentException e) {
            logger.info("illegal ARGS");
            return ResponseEntity.internalServerError().body(null);
        } catch (DataIntegrityViolationException a) {
            logger.info("dataIntegrityViolation");
            logger.warn(a.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    public ResponseEntity<GetAllUserResponseDTO> supportGetAllUsers() {
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
    public ResponseEntity<GetUserResponseDTO> supportGetUserByNameOrMail(@PathVariable String string) {
        User user;
        if (string.matches("\\b\\d+\\b")) {
            user = userService.getuserById(Long.parseLong(string)).orElseGet(() -> null);
        } else {
            user = userService.getuserByNameOrEmail(string).orElseGet(() -> null);
        }
        ResponseEntity<GetUserResponseDTO> rep = null;
        headers.clear();
        Optional<GetUserResponseDTO> userResponse = UserMapper.mapperToGet(user);
        if (userResponse.isPresent()) {
            headers.set("Custom-Header", "YEP custom header");
            rep = new ResponseEntity<>(userResponse.get(), headers, HttpStatus.OK);

        } else {
            headers.set("Failing", "YEP thsts a failing hjeader");
            rep = new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);

        }
        return rep;
    }

        public ResponseEntity<PutUserResponseDTO> supportUpdateUser( Long id,  PutUserRequestDTO userDetails) {

            try {
                userDetails.isValid();
                Optional<User> useropt = userService.updateUser(id, UserMapper.mapper(userDetails));
                Optional<PutUserResponseDTO> userResponseDTO = UserMapper.mapperToPut(useropt);

                return userResponseDTO
                        .map(ResponseEntity::ok)
                        .orElseGet(() ->
                                ResponseEntity.noContent().build());
            }catch (DataIntegrityViolationException a) {
                logger.info("dataIntegrityViolation");
                logger.warn(a.getMessage());
                return ResponseEntity.internalServerError().body(null);
            }
        }

    public ResponseEntity<String> supportDeleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok().body("All users have been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error in deleting all");
        }
    }

    public ResponseEntity<Object> supportDeleteUser(Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("the user have been deleted successfully.");
        } catch (Error e) {
            return ResponseEntity.internalServerError().body("Error in deleting that user");
        }
    }

}
