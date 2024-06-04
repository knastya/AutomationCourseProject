package com.example.teamcity.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byId;

public class NewVcsRootPage extends Page {
    private final SelenideElement urlInput = element(byId("repositoryUrl"));

    public NewVcsRootPage addNewVcsRoot(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }
}
