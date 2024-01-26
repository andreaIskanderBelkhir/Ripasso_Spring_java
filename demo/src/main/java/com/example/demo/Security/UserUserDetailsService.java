package com.example.demo.Security;

import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.entity.UserUserDetails;
import com.example.demo.entita.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userR;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user= userR.findByNameOrEmail(username,username);
        return user.map(UserUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("not found"));


    }
}
