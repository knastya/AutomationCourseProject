package com.example.teamcity.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byId;
import static java.time.Duration.ofSeconds;

public class GeneralSettingsTab extends BuildConfigurationPage {
    private final SelenideElement nameInput = element(byId("name"));

    public GeneralSettingsTab checkBuildName(String buildName) {
        nameInput.shouldBe(visible, ofSeconds(10))
                .shouldHave(exactValue(buildName));
        return this;
    }

}
