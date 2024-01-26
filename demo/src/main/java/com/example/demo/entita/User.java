package com.example.demo.entita;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users",schema="Steam")
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(nullable = false)
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
}
