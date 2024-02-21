package com.example.demo.dto.user.request;

import lombok.Data;

@Data
public class CreateUserRequestDTO  {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String password;

}
