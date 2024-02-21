package com.example.demo.dto.user.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserResponseDTO {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String role;

}
