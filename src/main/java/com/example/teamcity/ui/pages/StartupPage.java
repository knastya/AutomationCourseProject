package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byId;
import static com.example.teamcity.ui.Selectors.byName;
import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofSeconds;

public class StartupPage extends Page {

    private final SelenideElement proceedButton = element(byId("proceedButton"));
    private final SelenideElement acceptCheckbox = element(byId("accept"));
    private final SelenideElement continueButton = element(byName("Continue"));

    @Step("Open initial step")
    public StartupPage open() {
        Selenide.open("/");
        return this;
    }

    public StartupPage setUpTeamcity() {
        waitUntilPageIsLoaded();
        proceedButton.shouldBe(visible, ofSeconds(15))
                .click();
        waitUntilPageIsLoaded();
        proceedButton.shouldBe(visible, ofSeconds(15))
                .click();
        waitUntilPageIsLoaded();
        acceptCheckbox.shouldBe(enabled, ofMinutes(5))
                .scrollIntoView(true)
                .click();
        continueButton.shouldBe(enabled, ofSeconds(5)).
                click();
        return this;
    }

}
