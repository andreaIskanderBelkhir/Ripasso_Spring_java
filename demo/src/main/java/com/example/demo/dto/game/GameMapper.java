package com.example.demo.dto.game;

import com.example.demo.dto.game.request.CreateGameRequestDTO;
import com.example.demo.dto.game.response.CreateGameResponseDTO;
import com.example.demo.dto.game.response.GetGameResponseDTO;
import com.example.demo.entity.Game;

import java.util.Optional;

/**
 * class used to map the entity game to a game DTO and viceversa
 */
public class GameMapper {
    public static Game mapper(CreateGameRequestDTO gameDTO){
        Game game                     = Game.builder()
                .id(gameDTO.getId())
                .title(gameDTO.getTitle())
                .year(gameDTO.getYear())
                .rating(gameDTO.getRating())
                .cost(gameDTO.getCost()).build();
        return game;
    }

    public static CreateGameResponseDTO mapperToCreate(Game gameDTO){
        CreateGameResponseDTO game = CreateGameResponseDTO.builder()
                .id(gameDTO.getId())
                .title(gameDTO.getTitle())
                .year(gameDTO.getYear())
                .rating(gameDTO.getRating())
                .cost(gameDTO.getCost()).build();
        return game;
    }

    public static Optional<GetGameResponseDTO> mapperToGet(Game gameDTO) {
        if(gameDTO !=null) {
            GetGameResponseDTO game = GetGameResponseDTO.builder()
                    .id(gameDTO.getId())
                    .title(gameDTO.getTitle())
                    .year(gameDTO.getYear())
                    .rating(gameDTO.getRating())
                    .cost(gameDTO.getCost()).build();
            return Optional.of(game);
        }
        else
        {
            return Optional.empty();
        }
    }
}
