package com.example.demo.Service;

import com.example.demo.Controller.UserController;
import com.example.demo.Repository.GameRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entita.FriendList;
import com.example.demo.entita.Game;
import com.example.demo.entita.User;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userR;
    @Autowired
    private GameService gameS;
    @Autowired
    private PasswordEncoder encoder;
    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    //TODO: creao 2 metodi uno per vedere se posso e uno per creare
    public User createUser(User user) {
            user.setPassword(encoder.encode(user.getPassword()));
            return userR.save(user);

    }


    public Optional<ArrayList<User>> getallUser() {
        ArrayList<User> list= (ArrayList<User>) userR.findAll();
        if(list.isEmpty()){
            return Optional.empty();
        }
        else
            return Optional.of(list);
    }

    public Optional<User> getuserById(long id) {
        return userR.findById(id);
    }
    public Optional<User> getuserByNameOrEmail(String name){
            return userR.findByNameOrEmail(name,name);

    }
    public Optional<User> getuserByNameAndEmail(String name,String email){
            return userR.findByNameAndEmail(name,email);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        Optional<User> user = userR.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            return Optional.of(userR.save(existingUser));
        }
        return Optional.empty();
    }

    public Set<FriendList> getfriends(Long id){
        if(userR.findById(id).isPresent()){
            Set<FriendList> friends=userR.findById(id).get().getFriendList();
            return friends;
        }
        else
            return null;
    }

    public void deleteAllUsers() {
        userR.deleteAll();
    }


    // Delete user
    public void deleteUser(Long id) {
        userR.deleteById(id);
    }


    public void addGame(Long idUser,Long idGame){
        if((getuserById(idUser).isPresent()) ||(gameS.GetById(idGame).isPresent())){
            User user=getuserById(idUser).get();
            Game game=gameS.GetById(idGame).get();

        }
        else return;
    }
}
