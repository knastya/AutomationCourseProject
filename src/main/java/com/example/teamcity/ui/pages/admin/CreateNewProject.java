package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byId;

@Getter
public class CreateNewProject extends CreateNewEntityPage<CreateNewProject> {
    private final SelenideElement projectNameInput = element(byId("projectName"));
    private final SelenideElement buildTypeNameInput = element(byId("buildTypeName"));
    private final SelenideElement nameInput = element(byId("name"));

    public CreateNewProject open(String parentProjectId) {
        open(parentProjectId, "createProjectMenu");
        return this;
    }

    public CreateNewProject createProjectByUrl(String url) {
        createEntityByUrl(url);
        return this;
    }

    public CreateNewProject createProjectManually(String name) {
        nameInput.sendKeys(name);
        submit();
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
