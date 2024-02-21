package com.example.demo.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class GetAllUserResponseDTO {
    private List<GetUserResponseDTO> allUserDTO;
}
