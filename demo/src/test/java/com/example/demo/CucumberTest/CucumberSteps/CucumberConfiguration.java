package com.example.demo.CucumberTest.CucumberSteps;

import com.example.demo.DemoApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@CucumberContextConfiguration
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//TODO: creare cartella test runner in cucumberTest con questo e test runner. nomi cartelle minusculi
public class CucumberConfiguration {
}
