package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="hours",schema="steam")
@Getter @Setter @NoArgsConstructor
public class HourGame {
    @EmbeddedId
    private Library id;

    @ManyToOne
    @MapsId("usersId")
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @MapsId("gamesId")
    @JsonIgnore
    @JoinColumn(name="game_id")
    private Game game;

    private Long hour;

    public HourGame(Library id, User user, Game game, Long hour) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.hour = hour;
    }
}
