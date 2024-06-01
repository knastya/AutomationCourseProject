package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByAttribute;
import com.example.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byClass;
import static com.example.teamcity.ui.Selectors.byId;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;

public class CreateNewEntityPage<T> extends Page {
    private final SelenideElement urlInput = element(byId("url"));
    private final SelenideElement connectionSuccessful = element(byClass("connectionSuccessful"));
    private final SelenideElement manuallyOption = element(new ByAttribute("href", "#createManually"));

    protected void open(String parentProjectId, String showMode) {
        Selenide.open(format("/admin/createObjectMenu.html?projectId=%s&showMode=%s",
                parentProjectId, showMode));
    }

    protected void createEntityByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        connectionSuccessful.shouldBe(visible, ofSeconds(15));
    }

    public T clickOnManuallyOption() {
        manuallyOption.shouldBe(visible, ofSeconds(10)).click();
        return (T) this;
    }

}
