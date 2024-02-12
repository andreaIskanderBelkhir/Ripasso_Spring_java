package com.example.demo.cucumberTest.manager;


import com.example.demo.Service.UserService;
import com.example.demo.entita.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RequiredArgsConstructor
@Service // service perche ha al suo intero logica e provvede a dei servizi
public class UserManager {
    private static final Logger logger= LoggerFactory.getLogger(UserManager.class);
    private static final HttpManager httpManager=new HttpManager();
    private static final Gson gson = new GsonBuilder().create();
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
            JSONObject userjson = httpManager.httpGetResponseBodyasJson(response);

            //check if no content http status, thtas mean get ha dato utente null
            User  userfound=gson.fromJson(userjson.toString(), User.class);
            //User userfound = new ObjectMapper().readValue(userjson.toString(), User.class);
            return userfound;
        }
        else{
            return null;
        }

    }

    public boolean saveUser(String nome, String email) throws Exception{

        JSONObject requestBody = createUserAddOn(nome, email);

        response = httpManager.httpPost(userUrl,requestBody);
        int status= httpManager.httpGetResponseCode(response);

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
        JSONObject requestBody=createUserAddOn(user);
        logger.info("user modified");
        response = httpManager.httpPut(url,requestBody);
        int status=httpManager.httpGetResponseCode(response);
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
        int status=httpManager.httpGetResponseCode(response);
        switch (status) {
            case 200:
                return true;

            default:
                return false;
        }
    }



    public void userFound(String name) {
        try {
            User found = getUser(name);
            if(found!=null) {

                assertEquals(name,found.getName());
                assertNotNull(response.getEntity());
                assertEquals(200,httpManager.httpGetResponseCode(response));

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

                assertEquals(204,httpManager.httpGetResponseCode(response));
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
                assertEquals(200,httpManager.httpGetResponseCode(response));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject createUserAddOn(String nome,String email) throws JSONException {
        String pswd="test1";
        String role="ROLE_UTENTE";
        JSONObject res= new JSONObject();
/*        String res= "{ \"name\": \"" + nome + "\"" +
                ", \"email\": \"" + email + "\"" +
                ", \"phone\": \"" + null + "\"" +
                ", \"password\": \""+pswd+"\"" +
                ", \"role\" :\""+role+"\"}";*/
        res.put("name",nome);
        res.put("email",email);
        res.put("phone",null);
        res.put("password",pswd);
        res.put("role",role);

        return res;
    }
    public JSONObject createUserAddOn(User user) throws JSONException {
 /*       String res = "{\"name\": \"" + user.getName() + "\"" +
                ", \"email\": \"" + user.getEmail() + "\"" +
                ", \"phone\": \"" + user.getPhone() + "\"" +
                ", \"password\": \""+user.getPassword()+"\"" +
                ", \"role\" :\""+user.getRole()+"\"}";*/
        JSONObject res =new JSONObject();
        res.put("name",user.getName());
        res.put("email",user.getEmail());
        res.put("phone",user.getPhone());
        res.put("password",user.getPassword());
        res.put("role",user.getRole());
        return res;
    }
}