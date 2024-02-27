package com.example.demo.cucumberTest.testRunner;

import com.example.demo.DemoApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * this class set cucumber in spring, defing the enviroment and the main class
 */
@CucumberContextConfiguration
@SpringBootTest(classes = DemoApplication.class , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class CucumberConfiguration {
}
