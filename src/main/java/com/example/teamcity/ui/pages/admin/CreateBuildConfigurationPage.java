package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.element;
import static com.example.teamcity.ui.Selectors.byId;

public class CreateBuildConfigurationPage extends CreateNewEntityPage<CreateBuildConfigurationPage> {
    private final SelenideElement buildConfigNameInput = element(byId("buildTypeName"));

    public CreateBuildConfigurationPage open(String parentProjectId) {
        open(parentProjectId, "createBuildTypeMenu");
        return this;
    }

    public CreateBuildConfigurationPage createBuildConfigByUrl(String url) {
        createEntityByUrl(url);
        return this;
    }

    public CreateBuildConfigurationPage createBuildConfigManually(String name) {
        buildConfigNameInput.sendKeys(name);
        submit();
        return this;
    }

    public void setupBuildConfig(String buildConfigName) {
        buildConfigNameInput.clear();
        buildConfigNameInput.sendKeys(buildConfigName);
        submit();
    }
}
