package com.example.demo.CucumberTest.CucumberSteps;


import com.example.demo.Service.UserService;
import com.example.demo.entita.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;

import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RequiredArgsConstructor
@Service // service perche ha al suo intero logica e provvede a dei servizzi
public class UserManager {
    private static final Logger logger= LoggerFactory.getLogger(UserManager.class);
    private static final HttpManager httpManager=new HttpManager();

    private final UserService userService;

    String userUrl = "http://localhost:8080/user";

    private CloseableHttpResponse response;


/*    private String authToken;

    @Before
    public void setUp() {
        // Set up authentication credentials in the context
        String username = "test3";
        String password = "a";
        String credentials = username + ":" + password;
        authToken = Base64.getEncoder().encodeToString(credentials.getBytes());
    }*/



    public User getUser(String value) throws Exception {

        String url=userUrl.concat("/"+value);
        response=httpManager.httpGet(url);

        if(response.getEntity()!=null) {
            HttpEntity userEntity = response.getEntity();
            String userjson = EntityUtils.toString(userEntity);

            //check if no content http status, thtas mean get ha dato utente null
            User userfound = new ObjectMapper().readValue(userjson, User.class);
            return userfound;
        }
        else{
            return null;
        }

    }

    public boolean saveUser(String nome, String email) throws Exception{

        String requestBody = createUserAddOn(nome, email);

        response = httpManager.httpPost(userUrl,requestBody);
        int status= response.getStatusLine().getStatusCode();

        switch (status){
            case 200:

                return true;

            case 400:
                logger.info("utente gia presente");
                return false;

            default:
                logger.info("altro problema status: "+String.valueOf(status));
                return false;

        }
    }


    public boolean updateEmailUser(String name,String email) throws  Exception{
        User user=userService.getuserByNameOrEmail(name).get();
        long id=user.getId();
        user.setEmail(email);
        String url=userUrl.concat("/"+String.valueOf(id));
        String requestBody=createUserAddOn(user);
        logger.info("user modified");
        response = httpManager.httpPut(url,requestBody);
        int status=response.getStatusLine().getStatusCode();
        switch (status) {
            case 200:

                return true;

            default:
                return false;
        }
    }




    public boolean deleteUser(String name) throws Exception {
        long id = userService.getuserByNameOrEmail(name).get().getId();
        String url =userUrl.concat("/"+String.valueOf(id));
        response = httpManager.httpDelete(url);
        int status=response.getStatusLine().getStatusCode();
        switch (status) {
            case 200:
                return true;

            default:
                return false;
        }
    }


    //TODO: aggiungere controllo su found se null
    public void userFound(String name) {
        try {
            User found = getUser(name);
            if(found!=null) {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(found);
                //TODO:chiedere qui
                assertEquals(name,found.getName());
                assertNotNull(response.getEntity());
                assertEquals(200,response.getStatusLine().getStatusCode());

            }
            else logger.info("not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void userNotFound(String name)  {

        try {
            User found = getUser(name);
            if(found==null){

                assertEquals(204,response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void checkOnEmail(String name,String email){

        try {
            User found = getUser(name);
            String emailget= found.getEmail();

            if(emailget.equals(email)){
                assertEquals(200,response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createUserAddOn(String nome,String email){
        String pswd="test1";
        String role="ROLE_UTENTE";
        String res= "{ \"name\": \"" + nome + "\"" +
                ", \"email\": \"" + email + "\"" +
                ", \"phone\": \"" + null + "\"" +
                ", \"password\": \""+pswd+"\"" +
                ", \"role\" :\""+role+"\"}";
        return res;
    }
    private String createUserAddOn(User user) {
        String res = "{\"name\": \"" + user.getName() + "\"" +
                ", \"email\": \"" + user.getEmail() + "\"" +
                ", \"phone\": \"" + user.getPhone() + "\"" +
                ", \"password\": \""+user.getPassword()+"\"" +
                ", \"role\" :\""+user.getRole()+"\"}";
        return res;
    }
}