package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.User;
import lombok.Getter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byId;

@Getter
public class LoginPage extends Page {
    private static final String LOGIN_PAGE_URL = "/login.html";
    private final SelenideElement usernameInput = element(byId("username"));
    private final SelenideElement passwordInput = element(byId("password"));
    private final SelenideElement header = element(byId("header"));

    public LoginPage open() {
        Selenide.open(LOGIN_PAGE_URL);
        return this;
    }

    public void login(User user) {
        usernameInput.sendKeys(user.getUsername());
        passwordInput.sendKeys(user.getPassword());
        submit();
    }

    public LoginPage checkHeader(String title) {
        header.shouldHave(text(title));
        return this;
    }
}
