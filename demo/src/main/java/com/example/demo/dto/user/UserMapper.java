package com.example.demo.dto.user;

import com.example.demo.dto.user.request.CreateUserRequestDTO;
import com.example.demo.dto.user.request.PutUserRequestDTO;
import com.example.demo.dto.user.response.CreateUserResponseDTO;
import com.example.demo.dto.user.response.GetUserResponseDTO;
import com.example.demo.dto.user.response.PutUserResponseDTO;
import com.example.demo.entity.FriendList;
import com.example.demo.entity.HourGame;
import com.example.demo.entity.User;

import java.util.HashSet;
import java.util.Optional;

public class UserMapper {

    public static User mapper(CreateUserRequestDTO userRequestDTO){
    User user                              = User.builder()
                .id(userRequestDTO.getId())
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .phone(userRequestDTO.getPhone())
                .password(userRequestDTO.getPassword())
                .role(null)
                .hours(new HashSet<HourGame>())
                .friendList(new HashSet<FriendList>())
                .friendOfList(new HashSet<FriendList>())
                    .build();

        return user;
    }
    public static User mapper(PutUserRequestDTO userRequestDTO){
        User user                              = User.builder()
                .id(0)
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .phone(null)
                .password(null)
                .role(null)
                .hours(new HashSet<HourGame>())
                .friendList(new HashSet<FriendList>())
                .friendOfList(new HashSet<FriendList>())
                .build();

        return user;
    }

    public static CreateUserResponseDTO mapperToCreate(User user){
        CreateUserResponseDTO userResponseDTO = CreateUserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();

        return userResponseDTO;
    }

    public static Optional<GetUserResponseDTO> mapperToGet(User user){
        if(user != null) {
            GetUserResponseDTO userResponseDTO = GetUserResponseDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .role(user.getRole())
                    .build();

            return Optional.of(userResponseDTO);
        }
        else {
            return Optional.empty();
        }
    }

    public static Optional<PutUserResponseDTO> mapperToPut(Optional<User> user){
        if(user.isPresent()){
            PutUserResponseDTO userResponseDTO = PutUserResponseDTO.builder()
                    .id(user.get().getId())
                    .name(user.get().getName())
                    .email(user.get().getEmail())
                    .phone(user.get().getPhone())
                    .build();
            return Optional.of(userResponseDTO);
        }
        else{
            return Optional.empty();
        }
    }
}
