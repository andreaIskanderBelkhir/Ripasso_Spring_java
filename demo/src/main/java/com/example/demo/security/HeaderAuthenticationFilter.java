package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class HeaderAuthenticationFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            // Extract authentication information from request headers
            String authToken = request.getHeader("Authorization");
          //  logger.info(authToken);
            if (authToken != null && authToken.startsWith("Basic ")) {
                // Remove "Bearer " prefix
                authToken = authToken.substring(6);
               // logger.info(authToken);
                // Perform authentication (example logic)
                if (isValidToken(authToken)) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            "ADMIN",
                            "N/A",
                            createAuthorityList("ROLE_ADMIN"));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);
        }

        private boolean isValidToken(String token) {
            // Implement token validation logic (e.g., check token validity against a database or external service)
            return true; // Placeholder implementation
        }

    }
