package com.example.demo.CucumberTest.CucumberSteps;

import com.example.demo.CucumberTest.TestSecurityConfig;
import com.example.demo.DemoApplication;
import com.example.demo.entita.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.test.context.ContextConfiguration;




@ContextConfiguration(classes = {DemoApplication.class, TestSecurityConfig.class})
@CucumberContextConfiguration
///TODO:non deve estender deve essere classe apparte
public class UserStepDefinition extends UserManager {

    private static final Logger logger= LoggerFactory.getLogger(UserStepDefinition.class);
//TODO: variabile user manager da inizializzare

    @Given("a user with ID {long} exists")
    public void userExistStep(Long userId){
        givenUserExist(userId);
    }

    //versione di sopra con generalizzazione tipo
    @Given("a user with {string} {string}")//old
    @Given("un utente avente il campo :{string} con valore : {string}")
    public void userWithTypeExist(String type,String value) {
        givenUserExistType(type,value);
    }


    @Given("a user with ID {long} doesnt exists")
    public void userNotExist(Long userId){
        givenUserNotExist(userId);
    }

    //versione di sopra con generalizzazione tipo

    @Given("un utente avente il campo :{string} con valore : {string} non esiste")
    public void userWithTypeNotExist(String type,String value) {
        givenUserNotExistType(type,value);

    }

    @Given("un utente avente il campo id con valore : {long} deve essere presente")
    public void saveUserToDatabase(long id) {
        try{
            boolean res=givenSaveUser(id);
            if(res){
                logger.info("user saved in the db");
            }
            else {
                logger.info("error in the process of saving the user in the db");
            }
        } catch (Exception e) { // modificare per capire meglio errore eg. stampare almeno log
            logger.info("exeption found : "+e.getMessage());
            throw new RuntimeException(e);
        }

    }


    //@When("nuovo adduser")
    @When("creo utente con nome {string} e email {string}")
    public void saveUserStep(String nome,String email){
        try{
            boolean res=saveUser(nome,email);
            if(res){
                logger.info("user saved");
            }
            else {
                logger.info("error in the process of saving the user");
            }
        } catch (Exception e) { // modificare per capire meglio errore eg. stampare almeno log
            logger.info("exeption found : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @When("creo un utente con i dati:")
    public void saveUserJsonStep(String json) {
        try{
            boolean res=saveUserJson("/user", json);
            if(res){
                logger.info("user saved");
            }
            else {
                logger.info("error in the process of saving the user");
            }
        } catch (Exception e) { // modificare per capire meglio errore eg. stampare almeno log
            logger.info("exeption found : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @When("il cliente vuole vedere un user con campo : {string} e valore : {string}")
    public void getUserStep(String type,String value) throws Exception {
        User user=getUser(value);
        if(user!=null){
            //TODO: check how to use logger
            logger.info("user found");
        }
        else{
            logger.info("user not found");
        }
    }


    @When("aggiorno email {string} ad {string}")
    public void updateEmailUserStep(String email,String name){
        try{
            boolean res=updateEmailUser(name, email);
            if(res){
                logger.info("user updated");
            }
            else {
                logger.info("error in the process of updating the user");
            }
        }catch (Exception e){
            logger.info("exeption found : "+e.getMessage());
            throw  new RuntimeException(e);
        }
    }

    @When("il cliente vuole aggiornare un user con campo : {string} e valore : {string} usando i dati:")
    public void updateUserStep(String type,String value, String json) throws Exception {
        try{
            boolean res=updateUser(value, json);
            if(res){
                logger.info("user updated");
            }
            else {
                logger.info("error in the process of updating the user");
            }
        }catch (Exception e){
            logger.info("exeption found : "+e.getMessage());
            throw  new RuntimeException(e);
        }
    }


    @When("rimuovo utente {string} dal db")
    public void deleteUserStep(String name){
        try {
            boolean res = deleteUser(name);

            if(res){
                logger.info("user deleted");
            }
            else {
                logger.info("error in the process of deleting the user");
            }

        } catch (Exception e) {
            logger.info("exception found : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @When("cancello utente con id : {string}")
    public void deleteUserUrlStep(String url){

        try {
            boolean res = deleteUserUrl(url);

            if(res){
                logger.info("user deleted");
            }
            else {
                logger.info("error in the process of deleting the user");
            }

        } catch (Exception e) {
            logger.info("exception found : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }



    @Then("la risposta è {string}")
    public void thenResponseStatusCodeStep(String expectedStatusCode) throws Exception{
        reponceCode(expectedStatusCode);

    }


    @Then("utente {string} è presente nel db")
    public void userIsFound(String name){
        responceUserFound(name);
    }
    @Then("utente {string} non è presente nel db")
    public void userIsNotFound(String name){
        responceUserNotFound(name);
    }

    @Then("la risposta del server è:")
    public void andResponceJsoncontainStep(String expectedJson ) throws Exception{
        reponceJson(expectedJson);
    }

    @Then("utente {string} ha email {string}")
    public void checkOnEmailWhen(String name,String email){
        responcecheckonemail(name,email);
    }



}
