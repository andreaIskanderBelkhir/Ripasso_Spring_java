package com.example.demo.entita;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class Library implements Serializable {
    @Column(name = "users_id")
    private Long usersId;
    @Column(name="games_id")
    private Long gamesId;

    public Library(Long users_id, Long games_id) {
        this.usersId = users_id;
        this.gamesId = games_id;
    }
    @Override
    public int hashCode(){

        return (int) (this.gamesId+this.usersId);
    }

    //cosi a caso non imp per ora
    @Override
    public boolean equals(final Object obj){
        if ((this==obj)||(this.hashCode()==obj.hashCode())){
            return true;
        }
        if (obj==null){
            return false;
        }
        return false;
    }

}
