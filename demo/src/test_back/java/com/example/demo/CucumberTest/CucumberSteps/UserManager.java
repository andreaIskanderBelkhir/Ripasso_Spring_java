package com.example.demo.CucumberTest.CucumberSteps;

import com.example.demo.Service.UserService;
import com.example.demo.entita.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
//TODO: check metodi vecchi
public class UserManager {
    private static final Logger logger= LoggerFactory.getLogger(UserManager.class);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //TODO: provare autowired per accedere a vedo db
    @MockBean
    private UserService userService;

    private ResultActions resultActions;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void givenUserExistType(String type,String value){
        switch (type.toLowerCase()){
            case "id":
                givenUserExist(Long.valueOf(value));
                break;

            case "name":
                givenUserExist("name",value);
                break;
            case "email","mail":
                givenUserExist("email",value);
                break;
            default:
        }
    }
    public void givenUserNotExistType(String type,String value){
        switch (type.toLowerCase()){
            case "id":
                givenUserNotExist(Long.valueOf(value));
                break;

            case "name", "email","mail":
                givenUserNotExist(value);
                break;
            default:
        }
    }
    /*
    in questa funzione creo un utente mock che verra riportato ogni volta che eseguo getUserById al posto del vero utente
    quindi crea un utente su cui faremo i test
     */
    public  void givenUserExist(Long id)  {
        User existUser=new User(id,"John Doe", "john.doe@example.com",null,passwordEncoder.encode("test1"));
        when(userService.getuserById(id)).thenReturn(Optional.of(existUser));
    }
    //TODO: usare switch per controllo type DONE
    //TODO:
    public void givenUserExist(String type,String value) {

        String name=null;
        String mail=null;
        switch (type.toLowerCase()){
            case "email","mail":
                name=value.split("@")[0];
                name=name.replace("."," ");
                mail=value;
                break;
            case "name":
                name=value;
                value=value.replace(" ",".");
                mail=value.concat("@example.com");
                break;

        }

        User existUser=new User(3,name, mail,null,passwordEncoder.encode("test1"));
        when(userService.getuserByNameOrEmail(value)).thenReturn(Optional.of(existUser));
    }
    public  void givenUserNotExist(Long id)  {
        when(userService.getuserById(id)).thenReturn(Optional.empty());
    }
    public  void givenUserNotExist(String value)  {
        when(userService.getuserByNameOrEmail(value)).thenReturn(Optional.empty());
    }

    // MockMVC non fa vere call del servizio perche va su un servizio mock -> @mockBean sopra quindi non accede veramnete al db

    public boolean givenSaveUser(long id)throws Exception{
        User existUser=new User(id,"John Doe", "john.doe@example",null,"test1");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(existUser);
        when(userService.createUser(eq(existUser))).thenReturn(existUser);
        resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        int status=resultActions.andReturn().getResponse().getStatus();
        switch (status){
            case 200:
                return true;
            case 400:
                logger.info("id already exixst");
                return false;

            default:
                return false;

        }


    }

    public User getUser(String value) throws Exception {
        String url="/user/".concat(value);
        resultActions=mockMvc.perform(MockMvcRequestBuilders.get(url));
        MvcResult userresult = resultActions.andReturn();
        String userjson = userresult.getResponse().getContentAsString();

        if (resultActions.andReturn().getResponse().getContentAsString().isEmpty()){
            return null;
        }
        else {
            User userfound= new ObjectMapper().readValue(userjson, User.class);
            return userfound;
        }

    }

    public boolean saveUser(String nome, String email) throws Exception{
        User newuser= new User(1,nome,email,null,"test1");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(newuser);

        //when(userService.createUser(any(User.class))).thenReturn(newuser);
        resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        int status=resultActions.andReturn().getResponse().getStatus();

        switch (status){
            case 200:
                when(userService.getuserById(any(int.class))).thenReturn(Optional.of(newuser));
                when(userService.getuserByNameOrEmail(any(String.class))).thenReturn(Optional.of(newuser));
                return true;

            case 400:
                logger.info("id fault");
                return false;

            default:
                return false;

        }
    }

    public boolean saveUserJson(String url, String json) throws Exception{
        User newuser= objectMapper.readValue(json, User.class);
        //controllare se si puo togliere
        when(userService.createUser(any(User.class))).thenReturn(newuser);
        resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        int status=resultActions.andReturn().getResponse().getStatus();
        switch (status){
            case 200:
                return true;

            default:
                return false;

        }
    }

    public boolean updateEmailUser(String name,String email) throws  Exception{
        User user=userService.getuserByNameOrEmail(name).get();
        long id=user.getId();
        user.setEmail(email);
        String url="/user/".concat(String.valueOf(id));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);
        when(userService.updateUser(eq(id),any(User.class))).thenReturn(Optional.of(user));

        resultActions = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        int status=resultActions.andReturn().getResponse().getStatus();
        switch (status) {
            case 200:
                when(userService.getuserById(any(int.class))).thenReturn(Optional.of(user));
                when(userService.getuserByNameOrEmail(any(String.class))).thenReturn(Optional.of(user));
                return true;

            default:
                return false;
        }
    }

    public boolean updateUser(String value, String json) throws Exception{
        // Convert JSON string to User object
        String url="/user/".concat(value);
        User updatedUser = objectMapper.readValue(json, User.class);
        long idup=updatedUser.getId();
        // Mock the behavior of the service
        when(userService.updateUser(eq(idup),any(User.class))).thenReturn(Optional.of(updatedUser));

        // Perform the PUT request
        resultActions = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        int status=resultActions.andReturn().getResponse().getStatus();
        switch (status){
            case 200:
                return true;

            default:
                return false;

        }
    }

    public boolean deleteUser(String name) throws Exception {
        long id = userService.getuserByNameOrEmail(name).get().getId();
        String url ="/user/".concat(String.valueOf(id));
        resultActions=mockMvc.perform(MockMvcRequestBuilders.delete(url));
        int status=resultActions.andReturn().getResponse().getStatus();
        switch (status) {
            case 200:
                return true;

            default:
                return false;
        }
    }

    public boolean deleteUserUrl(String url) throws Exception {
        resultActions=mockMvc.perform(MockMvcRequestBuilders.delete("/user/".concat(url)));
        int status=resultActions.andReturn().getResponse().getStatus();
        switch (status){
            case 200:
                return true;

            default:
                return false;

        }

    }

    public void reponceCode(String expectedStatusCode) throws Exception {
        switch (expectedStatusCode.toLowerCase()) {
            case "ok":
                resultActions.andExpect(MockMvcResultMatchers.status().is(200));
                break;
            case "non trovato", "not found":
                resultActions.andExpect(MockMvcResultMatchers.status().is(204));
        }

    }

    public void reponceJson(String expectedJson) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }
    //TODO: aggiungere controllo su found se null
    public void responceUserFound(String name) {
        User found = null;
        try {
            found = getUser(name);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(found);
            resultActions.andExpect(MockMvcResultMatchers.content().json(json));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void responceUserNotFound(String name)  {
        User found= null;
        try {
            found = getUser(name);
            if(found==null){
                resultActions.andExpect(MockMvcResultMatchers.status().is(204));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void responcecheckonemail(String name,String email){
        User found= null;
        try {
            found = getUser(name);
            if(found.getEmail().equals(email)){
                resultActions.andExpect(MockMvcResultMatchers.status().is(200));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}