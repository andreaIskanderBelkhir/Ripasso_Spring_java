package com.example.demo.dto.game.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGameResponseDTO {
    private long id;
    private String title;
    private String year;
    private int rating;
    private double cost;
}
