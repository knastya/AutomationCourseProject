package com.example.teamcity.api;

import api.models.AuthSettings;
import api.models.Module;
import api.requests.checked.CheckedAuthSettings;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.*;

import static api.models.Module.*;
import static api.spec.Specifications.superUserSpec;

public class BaseTest {
    protected SoftAssertions softy;

    @BeforeSuite
    public void setup() {
        Map<String, List<Module>> modules = new HashMap<>() {{
            put("module", Arrays.asList(
                    httpBasic(), defaultModule(), tokenAuthModule(), ldapModule())
            );
        }};
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