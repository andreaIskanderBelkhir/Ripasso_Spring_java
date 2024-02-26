package com.example.demo.service;

import com.example.demo.repository.GameRepository;
import com.example.demo.repository.HoursRepository;
import com.example.demo.entity.*;

import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HoursService {

    private final HoursRepository hoursR;
    private final UserRepository userR;
    private final GameRepository gameR;


    //usato in user c
    public void addGame(Long idUser,Long idGame){
        if((userR.findById(idUser).isPresent()) && (gameR.findById(idGame).isPresent())){
            User user=userR.findById(idUser).get();
            Game game=gameR.findById(idGame).get();
            HourGame instergame=new HourGame(new Library(idUser,idGame),user,game,(long)0);
            hoursR.save(instergame);
            System.out.println("done");
        }

        else {
            System.out.println("failed one of the is not found");
            return;
        }
    }
//same as  sopra
    public void modifyhour(Long idUSer,Long idGame,long h){
        if ((userR.findById(idUSer).isPresent()) && (gameR.findById(idGame).isPresent())){
            User user=userR.findById(idUSer).get();
            Game game=gameR.findById(idGame).get();
            if (hoursR.findById(new Library(idUSer,idGame)).isPresent()) {
                HourGame instgame = hoursR.findById(new Library(idUSer, idGame)).get();
                instgame.setHour(instgame.getHour()+h);
                hoursR.save(instgame);
            }
            else{
                System.out.println("game not owned");

            }
        }
        else {
            System.out.println("failed one of the is not found");

        }

    }

    //sempre user
    public List<Game> getplayedG(Long iduser){
        if(userR.findById(iduser).isPresent()){
            User user=userR.findById(iduser).get();
            List<Long> findings=hoursR.findplayedgame(user.getId());
            return (ArrayList<Game>) gameR.findAllById(findings);
        }
        else
        {
            return null;
        }
    }
    //su game controller
    public void allhourallgame(){
        List<Hourssumm> allgameforh=hoursR.summhourBygame();

        for (Hourssumm gs: allgameforh){
            System.out.print("il gioco "+ gs.getId_game().toString()+ " ha un totale di ore pari a " +gs.getHourtotal().toString()+"\n");
        }
    }
}
