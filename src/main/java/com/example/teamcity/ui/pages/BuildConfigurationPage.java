package com.example.teamcity.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byId;
import static java.time.Duration.ofSeconds;

public class BuildConfigurationPage extends Page {
    private final SelenideElement generalSettingsButton = element(byId("general_Tab"));

    public GeneralSettingsTab clickOnGeneralSettingsTabButton() {
        generalSettingsButton.shouldBe(visible, ofSeconds(10)).click();
        return new GeneralSettingsTab();
    }

}
