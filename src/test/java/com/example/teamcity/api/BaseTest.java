package com.example.teamcity.api;

import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.models.Modules;
import com.example.teamcity.api.requests.checked.CheckedAuthSettings;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.*;

import static com.example.teamcity.api.spec.Specifications.superUserSpec;

public class BaseTest {
    protected SoftAssertions softy;

    @BeforeSuite
    public void setup() {
        Modules modules = Modules.builder()
                .module(Arrays.asList(
                        httpBasic(), defaultModule(), tokenAuthModule(), ldapModule()
                )).build();
        AuthSettings authSettings = new AuthSettings(true, modules);
        new CheckedAuthSettings(superUserSpec()).update(authSettings);
    }

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
    }
}