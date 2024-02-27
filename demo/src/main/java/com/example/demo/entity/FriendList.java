package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * entity class that represent a many to many for the user entity \n also this class is dismissed/ ignored for now
 */
@Entity
@Table(name="friendlist",schema="steam")
@Getter @Setter @NoArgsConstructor
public class FriendList {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="from_user_fk")
    private User from;

    @ManyToOne
    @JoinColumn(name="to_user_fk")
    private User to;
}
