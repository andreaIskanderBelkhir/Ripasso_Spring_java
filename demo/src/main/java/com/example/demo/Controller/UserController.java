package com.example.demo.Controller;

import com.example.demo.Service.HoursService;
import com.example.demo.Service.UserService;

import com.example.demo.entita.Game;
import com.example.demo.entita.User;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HoursService hoursService;

    private final HttpHeaders headers = new HttpHeaders();
    // Create a new user



    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)  {
        try {
            User useradd=userService.createUser(user);
            if(useradd!=null){
                return ResponseEntity.ok(userService.createUser(user));
            }else{
                return ResponseEntity.internalServerError().build();
            }
        }catch (IllegalArgumentException e){
            //System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }catch (DataIntegrityViolationException a){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    @GetMapping
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        headers.clear();
        List<User> lista=userService.getallUser().orElseGet(()->null);
        ResponseEntity risposta=null;
        if(lista!=null){
            headers.set("Custom","Yep thats the success header");
            risposta=new ResponseEntity(lista,headers,HttpStatus.OK);
        }
        else{
            headers.set("Failed","Failing header");
            risposta=new ResponseEntity<>(null,headers,HttpStatus.NO_CONTENT);
        }
        return risposta;
    }

    // Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user =userService.getuserById( (id)).orElseGet(()->null);
        ResponseEntity<User> rep=null;
        headers.clear();
        if(user!=null){
            headers.set("Custom-Header", "YEP custom header");
            rep= new ResponseEntity<>(user, headers,HttpStatus.OK);
            //rep=ResponseEntity.ok().header("Custom-Header", "YEP custom header").body(user); DA usare non con Responce Entity ma con elementi che usnao tipo Mono<Repon..>
        }
        else {
            headers.set("Failing", "YEP thsts a failing hjeader");
            rep= new ResponseEntity<>(user, headers,HttpStatus.NO_CONTENT);
            // rep=ResponseEntity.noContent().header("failed header","its header for failing").build();
        }
        return rep;
    }

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {

        //Optional.of(userDetails).orElseThrow(); inutile fa da solo il check della presenza body con request body

        return userService.updateUser(id, userDetails)
                .map(ResponseEntity::ok)
                .orElseGet(()->
                        ResponseEntity.noContent().build());
    }
    /*@GetMapping("/friend/{id}")
    public Set<FriendList> getfriendofid(@PathVariable Long id){
        return userService.getfriends(id);
    }*/
    // Delete all users
    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok().body("All users have been deleted successfully.");
        }catch (Error e){
            return  ResponseEntity.internalServerError().body("Error in deleting all");
        }

    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("the user have been deleted successfully.");
        }catch (Error e){
            return  ResponseEntity.internalServerError().body("Error in deleting that user");
        }

    }

    @PostMapping("/addgame/{id_u}/{id_g}")
    public ResponseEntity<Object> addGame(@PathVariable Long id_u,@PathVariable Long id_g){
        try {
            hoursService.addGame(id_u,id_g);
            return ResponseEntity.ok().body("added");
        }catch (Error e){
            return  ResponseEntity.internalServerError().body("Error ");
        }
    }


    @PutMapping("/updatehour/{id}/{id_g}/{h}")
    public ResponseEntity<String> updatehour(@PathVariable Long id, @PathVariable Long id_g, @PathVariable Long h){
        try {
            hoursService.modifyhour(id,id_g,h);
            return ResponseEntity.ok().body("added");
        }catch (Error e){
            return  ResponseEntity.internalServerError().body("Error ");
        }
    }

    @GetMapping("/playedGame/{id}")
    public ResponseEntity<List<Game>> getgameplayed(@PathVariable Long id){
        List<Game> lista= hoursService.getplayedG(id);
        ResponseEntity<List<Game>> response=null;
        headers.clear();
        if((lista!=null)&&(!lista.isEmpty())){
            headers.set("steamlib" , "leggi a sinistra");
            response= new ResponseEntity<>(lista,headers,HttpStatus.OK);
        }
        else if (lista==null){
            headers.set("error","non presente l'utente");
            response=new ResponseEntity<>(null,headers,HttpStatus.NOT_FOUND);
        }else
        {
            headers.set("error","lista vuota");
            response=new ResponseEntity<>(null,headers,HttpStatus.NO_CONTENT);
        }
        return response;
    }

}


