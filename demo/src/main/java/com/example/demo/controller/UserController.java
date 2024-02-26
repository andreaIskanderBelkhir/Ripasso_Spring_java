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


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserUtilityController userUtility;

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO userRequestDTO) {
    return userUtility.supportCreateUser(userRequestDTO);

    }

    @GetMapping
    public ResponseEntity<GetAllUserResponseDTO> getAllUsers() {
        return userUtility.supportGetAllUsers();
    }

    // Get user by a string o long
    @GetMapping("/{string}")
    public ResponseEntity<GetUserResponseDTO> getUserByNameOrMail(@PathVariable String string) {
        return userUtility.supportGetUserByNameOrMail(string);
    }

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<PutUserResponseDTO> updateUser(@PathVariable Long id, @RequestBody PutUserRequestDTO userDetails) {
        return userUtility.supportUpdateUser(id, userDetails);
    }


    // Delete all users
    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        return userUtility.supportDeleteAllUsers();
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userUtility.supportDeleteUser(id);

    }

}


