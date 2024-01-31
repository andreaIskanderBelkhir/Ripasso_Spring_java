package com.example.demo.CucumberTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources",
glue="com.example.demo.CucumberTest")
public class TestCucumber extends SpringIntegrationTest {

}
