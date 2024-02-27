package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class that represent a game
 */
@Entity
@Table(name="game",schema="steam")
@Getter @Setter  @AllArgsConstructor @NoArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String year;
    private int rating;
    private double cost;

    @OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<HourGame> hours;




    public Game(long id, String title, String year, int rating, double cost) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.cost = cost;
        this.hours=new HashSet<HourGame>();
    }
}
