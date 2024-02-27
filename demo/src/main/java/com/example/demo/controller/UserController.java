package com.example.demo.controller;

import com.example.demo.controller.utility.UserUtilityController;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * UserController is the RestController class that expose endpoint for all the api refer to a User
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Bean with the business for the controller
     */
    @Autowired
    private UserUtilityController userUtility;


    /**
     * Endpoint for the api that create a new user in the db
     * @param userRequestDTO  request body of a user under the DTO design pattern
     * @return response entity with a user under the DTO design pattern for the body, and a response code "200" if the used is added to the db or else a code "400" if a user with some of the parameter are the the same of another user already present or else the code "500"
     *
     */
    @Operation(summary = "Create a new user on the db")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200" ,description ="User got added on the db"),
            @ApiResponse(responseCode ="400" ,description ="user cant be added to the db ")
    })
    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO userRequestDTO) {
    return userUtility.supportCreateUser(userRequestDTO);

    }

    /**
     * Endpoint for the api that retrive all tghe user in the db
     * @return response entity with an object under the DTO design pattern having a list of user, and a response code "200"  if the list is not empty or else a code "204"
     */
    @GetMapping
    public ResponseEntity<GetAllUserResponseDTO> getAllUsers() {
        return userUtility.supportGetAllUsers();
    }

    /**
     * Endpoint for the api that retrive a specif user using the name, the email or the id as query
     * @param string a string refering to a name, an email or the id of the user you want to retrive
     * @return response entity with a user under the DTO design pattern for the body, and a response code "200" if the used is found in the db or else a code "400"
     */
    // Get user by a string or long
    @GetMapping("/{string}")
    public ResponseEntity<GetUserResponseDTO> getUserByNameOrMail(@PathVariable String string) {
        return userUtility.supportGetUserByNameOrMail(string);
    }

    /**
     * Endpoint for the api that update a specif user retrived by his id
     * @param id the id of the user you want to update
     * @param userDetails request body of a user under the DTO design pattern with the new information
     * @return response entity with a user under the DTO design pattern for the body, and a response code "200" if the user is updated in the db or else a code "204" if the user isn't found in the db or the code "500" if the request body is not valid
     */
    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<PutUserResponseDTO> updateUser(@PathVariable Long id, @RequestBody PutUserRequestDTO userDetails) {
        return userUtility.supportUpdateUser(id, userDetails);
    }


    /**
     * Endpoint for the api that remove all the users from the db
     * @return response entity with a string that specified the result of the operation, and a code "200" if the users are removed or "500"
     */
    // Delete all users
    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        return userUtility.supportDeleteAllUsers();
    }

    /**
     * Endpoint for the api that remove a specific user from the db
     * @param id the id of the user you want to remove from the db
     * @return  response entity with a string that specified the result of the operation, and a code "200" if the user is removed or "500"
     */
    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userUtility.supportDeleteUser(id);

    }

}


