package com.example.demo.cucumberTest.stepDefinition;

import io.cucumber.java.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class TestHooks {
    @Before("@WithAdminRole")
    public void setupAdminUser() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "ADMIN",
                        "N/A",
                        createAuthorityList("ROLE_ADMIN")));
    }
}
