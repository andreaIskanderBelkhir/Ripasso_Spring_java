package com.example.demo.cucumberTest.testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

/**
 * this class set the cucumber test
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources",
glue="com.example.demo.cucumberTest",
extraGlue = "com.example.demo.cumberTest.testRunner")
public class TestRunner {

}
