package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.pages.LoginPage;
import org.testng.annotations.BeforeSuite;

import static java.lang.String.format;

public class BaseUiTest extends BaseTest {

    @BeforeSuite
    public void setupTests() {
        Configuration.baseUrl = format("http://%s", Config.getProperty("host"));
        Configuration.remote = Config.getProperty("remote");

        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder ="target/downloads";

        BrowserSettings.setup(Config.getProperty("browser"));
    }

    public void loginAsUser(User user) {
        checkedWithSuperUser.getUserRequest().create(user);
        new LoginPage().open().login(user);
    }
}
