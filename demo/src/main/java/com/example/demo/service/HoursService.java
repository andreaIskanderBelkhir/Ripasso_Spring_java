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
import java.util.Optional;

/**
 * Bean connection the controller to the repository
 */
@Service
@AllArgsConstructor
public class HoursService {

    private final HoursRepository hoursR;
    private final UserRepository userR;
    private final GameRepository gameR;


    /**
     * Method to add a game to a user
     * @param idUser the id of the user
     * @param idGame the id of the game
     */
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

        }
    }

    /**
     * Method to modify by adding hours to a game of a specific user
     * @param idUSer id of the user
     * @param idGame id of the game
     * @param h quantity of hours to add
     */
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

    /**
     * Method to get all gamed actuality played buy a user
     * @param iduser id of the user
     * @return a list of games played by the user, its a optional so it can be empty
     */
    //sempre user
    public Optional<List<Game>> getplayedG(Long iduser){
        if(userR.findById(iduser).isPresent()){
            User user=userR.findById(iduser).get();
            List<Long> findings=hoursR.findplayedgame(user.getId());
            ArrayList<Game> list = (ArrayList<Game>) gameR.findAllById(findings);
            if(!(list.isEmpty())){
                return Optional.of(list);
            }
            else
            {
                return Optional.empty();
            }
        }
        else
        {
            return Optional.empty();
        }
    }

    /**
     * Method that print all the games by the total of hours played by all the users
     */
    //su game controller
    public void allhourallgame(){
        List<Hourssumm> allgameforh=hoursR.summhourBygame();

        for (Hourssumm gs: allgameforh){
            System.out.print("il gioco "+ gs.getId_game().toString()+ " ha un totale di ore pari a " +gs.getHourtotal().toString()+"\n");
        }
    }
}
