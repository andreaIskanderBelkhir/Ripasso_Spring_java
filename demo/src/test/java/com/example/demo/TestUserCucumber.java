package com.example.demo;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.example.demo.Service.UserService;
import com.example.demo.entita.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.glassfish.jaxb.runtime.v2.runtime.output.Encoded;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserCucumber {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private UserService userService;
    private ResultActions resultActions;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Given("a user with ID {long exists}")
    public void givenUserExists(long userId){

        User existUser=new User(userId,"John Doe", "john.doe@example.com",null);
        existUser.setPassword(passwordEncoder.encode("test1"));
        when(userService.getuserById(userId)).thenReturn(Optional.of(existUser));
    }
    //funzione mappata su due when
    @When("the client sends a POST to {string} with following JSON")
    @When("the client sends a POST request to {string} with the following JSON:")
    public void whenCliendSendsPostRequest(String url,String json) throws Exception{
        User newuser= objectMapper.readValue(json, User.class);
        when(userService.createUser(any(User.class))).thenReturn(newuser);

        resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @When("the client sends a PUT request to {string} with the following JSON:")
    public void whenClientSendsPutRequest(String url, String json) throws Exception {
        // Convert JSON string to User object
        User updatedUser = objectMapper.readValue(json, User.class);

        // Mock the behavior of the service
        when(userService.createUser(any(User.class))).thenReturn(updatedUser);

        // Perform the PUT request
        resultActions = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Then("responce should be status code {int}")
    public void thenResponseStatusCodeShouldBe(int expectedStatusCode) throws Exception{
        resultActions.andExpect(MockMvcResultMatchers.status().is(expectedStatusCode));
    }
    @And("the responce JSON should contain:")
    public void andResponceJsoncontain(String expectedJson ) throws Exception{
        resultActions.andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }

}
