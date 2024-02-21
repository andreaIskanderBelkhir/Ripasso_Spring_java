package com.example.demo.dto.user.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PutUserResponseDTO {
    private long id;
    private String name;
    private String email;
    private String phone;

}
