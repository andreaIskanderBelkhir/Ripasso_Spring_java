package com.example.demo.CucumberTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
//TODO TestRunner
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources",
glue="com.example.demo.CucumberTest.CucumberSteps")
public class TestCucumber {

}