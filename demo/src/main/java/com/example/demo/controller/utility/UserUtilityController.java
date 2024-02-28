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
import lombok.AllArgsConstructor;
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

/**
 * Bean with the business logic for UserController
 */
@Component
@AllArgsConstructor
public class UserUtilityController {

    /**
     * Bean with the Service design pattern fpr the user
     */

    private UserService userService;


    private final HttpHeaders headers = new HttpHeaders();


    private static final Logger logger = LoggerFactory.getLogger(UserUtilityController.class);


    /**
     * Business logic for the endpoint Create User
     * @param userRequestDTO  request body of a user under the DTO design pattern
     * @return response entity with a user under the DTO design pattern for the body, and a response code "200" if the used is added to the db or else a code "400" if a user with some of the parameter are the the same of another user already present or else the code "500"
     *
     */
    public  ResponseEntity<CreateUserResponseDTO> supportCreateUser(CreateUserRequestDTO userRequestDTO){
        try {
            userRequestDTO.isValid();
            Optional<User> presId    = userService.getuserById(userRequestDTO.getId());
            if(presId.isPresent()){
                logger.warn("id gia presente");
                return ResponseEntity.badRequest().build();
            }
            else {
                boolean presName = userService.getuserByNameOrEmail(userRequestDTO.getName()).isPresent();
                boolean presEmail = userService.getuserByNameOrEmail(userRequestDTO.getEmail()).isPresent();
                boolean res =
                        presName ||
                                presEmail;

                if (!res) {
                    User useradd = userService.createUser(UserMapper.mapper(userRequestDTO));
                    CreateUserResponseDTO userResponseDTO = UserMapper.mapperToCreate(useradd);
                    return ResponseEntity.ok(userResponseDTO);
                } else {

                    if (presName) {
                        logger.warn("nome gia presente");
                    }
                    if (presEmail) {
                        logger.warn("email gia presente");
                    }
                    return ResponseEntity.badRequest().build();
                }
            }
        } catch (IllegalArgumentException e) {
            logger.warn("UserUtilityController::supportCreateUser {} {}",e.getClass(),e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        } catch (DataIntegrityViolationException a) {

            logger.warn("UserUtilityController::supportCreateUser {} {}",a.getMessage(),a.getClass());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Business logic for the endpoint gettAllUsers
     * @return response entity with an object under the DTO design pattern having a list of user, and a response code "200"  if the list is not empty or else a code "204"
     */
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

    /**
     * Business logic for the endpoint getUserByNameOrMail
     * @param string a string refering to a name, an email or the id of the user you want to retrive
     * @return response entity with a user under the DTO design pattern for the body, and a response code "200" if the used is found in the db or else a code "400"
     */
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
            //header setting was just part of learning how to use header
            headers.set("Custom-Header", "YEP custom header");
            rep = new ResponseEntity<>(userResponse.get(), headers, HttpStatus.OK);

        } else {
            headers.set("Failing", "YEP thsts a failing hjeader");
            rep = new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);

        }
        return rep;
    }

    /**
     * Business logic for the endpoint UpdateUser
     * @param id the id of the user you want to update
     * @param userDetails request body of a user under the DTO design pattern with the new information
     * @return response entity with a user under the DTO design pattern for the body, and a response code "200" if the user is updated in the db or else a code "204" if the user isn't found in the db or the code "500" if the request body is not valid
     */
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
                logger.warn("UserUtilityController::supportUpdateUser {} {}",a.getClass(),a.getMessage());
                return ResponseEntity.internalServerError().body(null);
            }
        }

    /**
     * Business logic for the endpoint deleteAllUsers
     * @return response entity with a string that specified the result of the operation, and a code "200" if the users are removed or "500"
     */
    public ResponseEntity<String> supportDeleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok().body("All users have been deleted successfully.");
        } catch (Exception e) {
            logger.warn("{} {}",e.getClass(),e.getMessage());
            return ResponseEntity.internalServerError().body("Error in deleting all ");
        }
    }
    /**
     * business logic for the endpoint deleteUser
     * @param id the id of the user you want to remove from the db
     * @return  response entity with a string that specified the result of the operation, and a code "200" if the user is removed or "500"
     */
    public ResponseEntity<Object> supportDeleteUser(Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("the user have been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error in deleting that user");
        }
    }

}
