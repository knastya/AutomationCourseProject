package com.example.teamcity.ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.selector.ByAttribute;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewProjectTest extends BaseUiTest {

    @Test
    public void authorizedUserShouldBeAbleCreateNewProject() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        Selenide.open("/login.html");

        var usernameInput = element(new ByAttribute("id", "username"));
        var passwordInput = element(new ByAttribute("id", "password"));
        var logInButton = element(new ByAttribute("type", "submit"));

        usernameInput.sendKeys(testData.getUser().getUsername());
        passwordInput.sendKeys(testData.getUser().getPassword());

        logInButton.click();
    }
}
