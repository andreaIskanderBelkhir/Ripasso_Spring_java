package com.example.demo.cucumberTest.stepDefinition;

import com.example.demo.Service.UserService;
import com.example.demo.cucumberTest.manager.UserManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
@AllArgsConstructor //forza i costruttoti non null quindi fa injection  per service e con notnull si assicura che non ci sia null nel manager
public class UserStepDefinition  {
@Autowired
private UserService userService;

private final UserManager userManager;

    private static final Logger logger= LoggerFactory.getLogger(UserStepDefinition.class);



    //@When("nuovo adduser")
    @When("creo utente con nome {string} e email {string}")
    public void saveUserS(String nome,String email){
        try{

            boolean res=userManager.saveUser(nome,email);

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
            boolean res=userManager.updateEmailUser(name, email);
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
            boolean res = userManager.deleteUser(name);

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
        userManager.userFound(name);
    }


    @Then("utente {string} non è presente nel db")
    public void userIsNotFound(String name){
        userManager.userNotFound(name);
    }


    @Then("utente {string} ha email {string}")
    public void checkOnEmailWhen(String name,String email){
        userManager.checkOnEmail(name,email);
    }



}
