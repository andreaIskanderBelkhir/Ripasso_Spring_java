package com.example.demo.dto.game.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetGamePlayedResponseDTO {
private List<GetGameResponseDTO> allGameplayedDTO;
}
