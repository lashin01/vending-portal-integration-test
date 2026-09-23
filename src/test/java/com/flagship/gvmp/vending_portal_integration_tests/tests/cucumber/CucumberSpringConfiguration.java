package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class CucumberSpringConfiguration {
}
