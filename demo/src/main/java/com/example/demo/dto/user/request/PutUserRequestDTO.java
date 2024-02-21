package com.example.demo.dto.user.request;

import lombok.Builder;
import lombok.Data;

@Data
//Api updateUser cambia solo nome e email
public class PutUserRequestDTO {
    private String name;
    private String email;


}
