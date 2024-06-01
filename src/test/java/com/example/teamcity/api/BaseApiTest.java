package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class BaseApiTest extends BaseTest {

    @BeforeSuite
    public void init() {
        RestAssured.given().filter(new SwaggerCoverageRestAssured());
    }

}
