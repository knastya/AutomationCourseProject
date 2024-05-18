package com.example.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.PageElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.*;
import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofSeconds;

public class Page {
    private final SelenideElement submitButton = element(byType("submit"));
    private final SelenideElement savingWaitingMarker = element(byId("saving"));
    private final SelenideElement pageWaitingMarker = element(byDataTest("ring-loader"));
    private final SelenideElement loadingWarning = element(byId("loadingWarning"));

    public void submit() {
        submitButton.click();
        waitUntilDataIsSaved();
    }

    public void waitUntilPageIsLoaded() {
        pageWaitingMarker.shouldNotBe(visible, ofMinutes(1));
    }

    public void waitUntilLoadingIsAbsent() {
        loadingWarning.shouldNotBe(visible, ofMinutes(1));
    }

    public void waitUntilDataIsSaved() {
        savingWaitingMarker.shouldNotBe(visible, ofSeconds(30));
    }

    public <T extends PageElement> List<T> generatePageElements(
            ElementsCollection collection,
            Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();
        collection.forEach(webElement -> elements.add(creator.apply(webElement)));
        return elements;
    }
}
