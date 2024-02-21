package com.example.demo.junitTest;


import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
// Assuming your User class is in a package named "user"
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objmapper;
    @Autowired
    private PasswordEncoder encoder;

    @MockBean
    private UserService userS;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAddUser() throws Exception{
        User newUser = new User(1L, "John Doe", "john.doe@example.com",null);
        User createdUser = new User(1L, "John Doe", "john.doe@example.com",null);
        createdUser.setPassword(encoder.encode("test1"));

        Mockito.when(userS.createUser(Mockito.any(User.class))).thenReturn(createdUser);

        // Act
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objmapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
    }
    @Test
    @WithMockUser(username="test3",password = "a",roles = "ADMIN")
    public void testUpdateUser() throws Exception {
        // Arrange
        long userId = 1L;
        User existingUser = new User(userId, "John Doe", "john.doe@example.com",null);
        User updatedUser = new User(userId, "Updated John Doe", "updated.john.doe@example.com",null);
        updatedUser.setPassword(encoder.encode("test1"));

        //al posto di fare getUserById mi restituisce exi user same per sotto
        Mockito.when(userS.getuserById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userS.updateUser(eq(userId),Mockito.any(User.class))).thenReturn(Optional.of(updatedUser));

        // Act
        mockMvc.perform(put("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objmapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated John Doe")))
                .andExpect(jsonPath("$.email", is("updated.john.doe@example.com")));
    }

}