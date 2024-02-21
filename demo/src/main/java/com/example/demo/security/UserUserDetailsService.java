package com.example.demo.security;

import com.example.demo.repository.UserReadOnlyRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.entity.UserUserDetails;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

public class UserUserDetailsService implements UserDetailsService {

    @Autowired
    private UserReadOnlyRepository userR;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user= userR.findByNameOrEmail(username,username);
        return user.map(UserUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("not found"));


    }
}
