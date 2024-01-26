package com.example.demo.Service;

import com.example.demo.Repository.HoursRepository;
import com.example.demo.entita.*;
import netscape.javascript.JSObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Service
public class HoursService {
    @Autowired
    private HoursRepository hoursR;
    @Autowired
    private UserService userS;
    @Autowired
    private GameService gameS;


    //usato in user c
    public void addGame(Long idUser,Long idGame){
        if((userS.getuserById(idUser).isPresent()) ||(gameS.GetById(idGame).isPresent())){
            User user=userS.getuserById(idUser).get();
            Game game=gameS.GetById(idGame).get();
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
        if ((userS.getuserById(idUSer).isPresent())||(gameS.GetById(idGame).isPresent())){
            User user=userS.getuserById(idUSer).get();
            Game game=gameS.GetById(idGame).get();
            if (hoursR.findById(new Library(idUSer,idGame)).isPresent()) {
                HourGame instgame = hoursR.findById(new Library(idUSer, idGame)).get();
                instgame.setHour(instgame.getHour()+h);
                hoursR.save(instgame);
            }
            else{
                System.out.println("game not owned");
                return;
            }
        }
        else {
            System.out.println("failed one of the is not found");
            return;
        }

    }

    //sempre user
    public List<Game> getplayedG(Long iduser){
        if(userS.getuserById(iduser).isPresent()){
            User user=userS.getuserById(iduser).get();
            List<Long> findings=hoursR.findplayedgame(user.getId());
            return gameS.Getallbyid(findings);
        }
        else return null;
    }
    //su game controller
    public void allhourallgame(){
        List<Hourssumm> allgameforh=hoursR.summhourBygame();

        for (Hourssumm gs: allgameforh){
            System.out.print("il gioco "+ gs.getId_game().toString()+ " ha un totale di ore pari a " +gs.getHourtotal().toString()+"\n");
        }
    }
}
