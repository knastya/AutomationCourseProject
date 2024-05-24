package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.models.Modules;
import com.example.teamcity.api.requests.checked.CheckedAuthSettings;
import org.testng.annotations.BeforeSuite;

import java.util.Arrays;

import static com.example.teamcity.api.models.Module.*;
import static com.example.teamcity.api.spec.Specifications.superUserSpec;

public class BaseApiTest extends BaseTest {

    @BeforeSuite
    public void setup() {
        Modules modules = Modules.builder()
                .module(Arrays.asList(
                        httpBasic(), defaultModule(), tokenAuthModule(), ldapModule()
                )).build();
        AuthSettings authSettings = new AuthSettings(true, modules);
        new CheckedAuthSettings(superUserSpec()).update(authSettings);
    }

}
