package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.elements.ProjectElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.elements;
import static java.time.Duration.ofSeconds;

public class ProjectsPage  extends FavoritesPage {
    private static final String FAVORITE_PROJECTS_URL = "/favorite/projects";
    private final ElementsCollection subprojects =
            elements(byXpath(".//*[contains(@class, 'Subproject__container')]"));

    @Step("Open Favorite Projects page")
    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        waitUntilFavoritePageIsLoaded();
        return this;
    }

    public List<ProjectElement> getSubprojects() {
        subprojects.filterBy(visible).shouldHave(sizeGreaterThanOrEqual(1), ofSeconds(10));
        return generatePageElements(subprojects, ProjectElement::new);
    }
}
