package com.example.demo.service;


import com.example.demo.repository.UserReadOnlyRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.FriendList;
import com.example.demo.entity.Game;
import com.example.demo.entity.User;

import de.qaware.tools.collectioncacheableforspring.CollectionCacheable;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Bean connection the controller to the repository
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userR;

    private final PasswordEncoder encoder;

    private final UserReadOnlyRepository userReadOnlyRepository;
    private static final Logger logger= LoggerFactory.getLogger(UserService.class);

    /**
     * Method that insert a new user in the db, it also encript the password and set a base role, \n also update the cache named users
     * @param user the entity to save
     * @return the entity that got saved
     */
    @CachePut(cacheNames = "users")
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        User useradd=userR.save(user);

        return useradd;
    }

    /**
     * Method that retrive an optional of the list having all the user in the db, its also cached in the cachelist named users
     * @return a list of all users, its an optional so it can be empty
     */
    @CollectionCacheable(value = "users")
    public Optional<ArrayList<User>> getallUser() {
        ArrayList<User> list= (ArrayList<User>) userReadOnlyRepository.findAll();
        if(list.isEmpty()){
            return Optional.empty();
        }
        else {

            return Optional.of(list);
        }
    }

    /**
     * Method that retrive a user by his id
     * @param id the id of the user you want to retrive
     * @return an optional version of the user retrived, it can be empty
     */
    @Cacheable(value = "user",key = "#id")
    public Optional<User> getuserById(long id) {
        return userR.findById(id);
    }
    /**
     * Method that retrieve a user by his name or email
     * @param name the name or email of the user you want to retrive
     * @return an optional version of the user retrived, it can be empty
     */

    public Optional<User> getuserByNameOrEmail(String name){
        Optional<User> userfound = userReadOnlyRepository.findByNameOrEmail(name,name);
        return userfound;

    }
    public Optional<User> getuserByNameAndEmail(String name,String email){
        return userReadOnlyRepository.findByNameAndEmail(name,email);
    }

    /**
     * Method that update a user in the db
     * @param id the id of the user to update
     * @param userDetails The new details of the user to update
     * @return an optional version of the user updated, it can be empty
     */
    @CachePut(value = "user",key = "#id")
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

    /**
     * Method for removing all the users from the db
     */
    @CacheEvict(allEntries = true,cacheNames = "user")
    public void deleteAllUsers() {
        userR.deleteAll();
    }


    /**
     * Method for removing a single user from the db
     * @param id the id of the user to remove
     */
    // Delete user
    @CacheEvict(cacheNames = {"user","users"},key = "#id")
    public void deleteUser(Long id) {
        userR.deleteById(id);
    }


    // pulisce la cache ogni 20 secondi
   /* @Scheduled(cron = "0,20 * * * * ?")
    @CacheEvict(cacheNames = "users",allEntries = true)
    public void deleteCache(){
    logger.info("cache cleared");
    }*/

}