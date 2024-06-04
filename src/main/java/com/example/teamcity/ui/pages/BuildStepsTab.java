package com.example.teamcity.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byClass;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;

public class BuildStepsTab extends BuildConfigurationPage {
    private final SelenideElement successMessage = element(byClass("successMessage "));

    public BuildStepsTab checkSuccessMessage(String buildConfigName, String vcsRoot) {
        String text = format("New build configuration \"%s\" and VCS root \"%s\" have been successfully created.",
                buildConfigName, vcsRoot);
        successMessage.shouldBe(visible, ofSeconds(10))
                .shouldHave(text(text));
        return this;
    }

}
