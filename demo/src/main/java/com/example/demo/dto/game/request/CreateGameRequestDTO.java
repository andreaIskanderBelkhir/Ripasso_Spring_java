package com.example.demo.dto.game.request;

import lombok.Data;

@Data
public class CreateGameRequestDTO {
    private long id;
    private String title;
    private String year;
    private int rating;
    private double cost;
}
