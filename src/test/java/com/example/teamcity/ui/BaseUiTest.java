package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class BaseUiTest extends BaseTest {

    @BeforeSuite
    public void setupTests() {
        Configuration.browser = "firefox";
        Configuration.baseUrl = format("http://%s", Config.getProperty("host"));
        Configuration.remote = Config.getProperty("remote");

        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder ="target/downloads";

        Map<String, Boolean> options = new HashMap<>();
        options.put("enableVNC", true);
        options.put("enableLog", true);

        Configuration.browserCapabilities = new FirefoxOptions();
        Configuration.browserCapabilities.setCapability("selenoid:options", options);
    }
}
