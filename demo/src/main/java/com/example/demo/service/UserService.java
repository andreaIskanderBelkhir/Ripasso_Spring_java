package com.example.demo.service;

import com.example.demo.configuration.HazelCastConfiguration;
import com.example.demo.repository.UserReadOnlyRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.FriendList;
import com.example.demo.entity.Game;
import com.example.demo.entity.User;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userR;

    private final GameService gameS;

    private final PasswordEncoder encoder;

    private final UserReadOnlyRepository userReadOnlyRepository;
    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    @CacheEvict(allEntries = true,cacheNames = "user")
    public User createUser(User user) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            User useradd=userR.save(user);

            return useradd;
    }

    @Cacheable(cacheNames = "user")
    public Optional<ArrayList<User>> getallUser() {
        ArrayList<User> list= (ArrayList<User>) userReadOnlyRepository.findAll();

        if(list.isEmpty()){
            return Optional.empty();
        }
        else {

            return Optional.of(list);
        }
    }
    @Cacheable("user")
    public Optional<User> getuserById(long id) {
        return userR.findById(id);
    }
    @Cacheable("user")
    public Optional<User> getuserByNameOrEmail(String name){
            return userReadOnlyRepository.findByNameOrEmail(name,name);

    }
    public Optional<User> getuserByNameAndEmail(String name,String email){
            return userReadOnlyRepository.findByNameAndEmail(name,email);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        Optional<User> user = userReadOnlyRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            return Optional.of(userR.save(existingUser));
        }
        return Optional.empty();
    }

    public Set<FriendList> getfriends(Long id){
        if(userReadOnlyRepository .findById(id).isPresent()){
            Set<FriendList> friends=userReadOnlyRepository.findById(id).get().getFriendList();
            return friends;
        }
        else
            return null;
    }
    @CacheEvict(allEntries = true,cacheNames = "user")
    public void deleteAllUsers() {
        userR.deleteAll();
    }


    // Delete user
    @CacheEvict(allEntries = true,cacheNames = "user")
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
