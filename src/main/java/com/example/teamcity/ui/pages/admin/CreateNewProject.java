package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byClass;
import static com.example.teamcity.ui.Selectors.byId;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;

@Getter
public class CreateNewProject extends Page {
    private final SelenideElement urlInput = element(byId("url"));
    private final SelenideElement projectNameInput = element(byId("projectName"));
    private final SelenideElement buildTypeNameInput = element(byId("buildTypeName"));
    private final SelenideElement connectionSuccessful = element(byClass("connectionSuccessful"));

    public CreateNewProject open(String parentProjectId) {
        Selenide.open(format(
                "/admin/createObjectMenu.html?projectId=%s&showMode=createProjectMenu",
                parentProjectId));
        return this;
    }

    public CreateNewProject createProjectByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        connectionSuccessful.shouldBe(visible, ofSeconds(15));
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
    }
}
