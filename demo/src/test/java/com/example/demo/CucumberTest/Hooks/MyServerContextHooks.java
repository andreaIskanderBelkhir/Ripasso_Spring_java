package com.example.demo.CucumberTest.Hooks;


import com.example.demo.DemoApplication;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@AutoConfigureCache
public class MyServerContextHooks {

    @Before
    public void onBeforeScenario(final Scenario scenario) {
        // This method does nothing - context setup is done with annotations
    }
}
