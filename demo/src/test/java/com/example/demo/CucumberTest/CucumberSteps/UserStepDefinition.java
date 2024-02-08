package com.example.demo.CucumberTest.CucumberSteps;

import com.example.demo.CucumberTest.TestSecurityConfig;
import com.example.demo.DemoApplication;
import com.example.demo.Service.UserService;
import com.example.demo.entita.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.test.context.ContextConfiguration;



@ContextConfiguration(classes = {DemoApplication.class, TestSecurityConfig.class})
@CucumberContextConfiguration
///TODO:non deve estender deve essere classe apparte
//TODO: spring confg che abbia quetse tag queste sopra
public class UserStepDefinition extends UserManager {


    private static final Logger logger= LoggerFactory.getLogger(UserStepDefinition.class);
//TODO: variabile user manager da inizializzare
   // private final UserManager userM=new UserManager();


    //@When("nuovo adduser")
    @When("creo utente con nome {string} e email {string}")
    public void saveUserS(String nome,String email){
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




    @When("aggiorno email {string} ad {string}")
    public void updateEmailUserS(String email,String name){
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



    @When("rimuovo utente {string} dal db")
    public void deleteUserS(String name){
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




    @Then("utente {string} è presente nel db")
    public void userIsFound(String name){
        userFound(name);
    }


    @Then("utente {string} non è presente nel db")
    public void userIsNotFound(String name){
        userNotFound(name);
    }


    @Then("utente {string} ha email {string}")
    public void checkOnEmailWhen(String name,String email){
        checkOnEmail(name,email);
    }



}
