package com.example.demo.CucumberTest.CucumberSteps;

import com.example.demo.CucumberTest.SpringIntegrationTest;
import com.example.demo.CucumberTest.TestSecurityConfig;
import com.example.demo.DemoApplication;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.entita.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@SpringBootTest
@CucumberContextConfiguration
@AutoConfigureMockMvc
@ContextConfiguration(classes = {DemoApplication.class,TestSecurityConfig.class})
//rinominare in testuserStepDefinition
public class TestUserStepDefinition extends SpringIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    private ResultActions resultActions;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Given("a user with ID {long} exists")
    public void givenUserExists(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.isAuthenticated());
        User existUser=new User( userId,"John Doe", "john.doe@example.com",null,passwordEncoder.encode("test1"));
        when(userService.getuserById(userId)).thenReturn(Optional.of(existUser));
    }

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
        //long id= Long.parseLong(url.split("/")[2]);
        User updatedUser = objectMapper.readValue(json, User.class);

        // Mock the behavior of the service
        when(userService.updateUser(eq(updatedUser.getId()),any(User.class))).thenReturn(Optional.of(updatedUser));

        // Perform the PUT request
        resultActions = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Then("the response status code should be {int}")
    public void thenResponseStatusCodeShouldBe(int expectedStatusCode) throws Exception{
        resultActions.andExpect(MockMvcResultMatchers.status().is(expectedStatusCode));
    }
    @And("the response JSON should contain:")
    public void andResponceJsoncontain(String expectedJson ) throws Exception{
        resultActions.andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }

}
