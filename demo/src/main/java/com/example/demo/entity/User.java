package com.example.demo.entity;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users",schema="Steam")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    private String phone;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String role;

    @OneToMany(mappedBy = "to")
    private Set<FriendList> friendList;

    @OneToMany(mappedBy = "from")
    private Set<FriendList> friendOfList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<HourGame> hours;


    public User(long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.friendList= new HashSet<FriendList>();
        this.friendOfList= new HashSet<FriendList>();
        this.hours=new HashSet<HourGame>();
    }

    public User(long id, String name, String email, String phone,String psw) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password=psw;
        this.friendList= new HashSet<FriendList>();
        this.friendOfList= new HashSet<FriendList>();
        this.hours=new HashSet<HourGame>();
    }
}
