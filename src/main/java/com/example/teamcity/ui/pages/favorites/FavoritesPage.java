package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class FavoritesPage extends Page {
    private final SelenideElement header =
            element(Selectors.byClass("ProjectPageHeader__title--ih"));

    @Step("Wait until favorite page ls loaded")
    public void waitUntilFavoritePageIsLoaded() {
        waitUntilPageIsLoaded();
        header.shouldBe(Condition.visible, Duration.ofSeconds(10));
        waitUntilLoadingIsAbsent();
    }
}
