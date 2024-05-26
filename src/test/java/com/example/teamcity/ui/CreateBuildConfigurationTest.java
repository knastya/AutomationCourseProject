package com.example.teamcity.ui;

import com.example.teamcity.ui.pages.BuildConfigurationPage;
import com.example.teamcity.ui.pages.BuildStepsTab;
import com.example.teamcity.ui.pages.NewVcsRootPage;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage;
import org.testng.annotations.Test;

import static java.lang.String.format;

public class CreateBuildConfigurationTest extends BaseUiTest {

    @Test(groups = {"UI_regression"})
    public void userShouldBeAbleCreateBuildConfigByUrl() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/knastya/AutomationCourseProject";
        String buildName = testData.getBuildType().getName();

        loginAsUser(testData.getUser());
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        new CreateBuildConfigurationPage()
                .open(testData.getProject().getId())
                .createBuildConfigByUrl(url)
                .setupBuildConfig(buildName);

        new BuildStepsTab()
                .checkSuccessMessage(buildName, url + "#refs/heads/main");

        String configId = getBuildConfigId(testData.getProject().getId(), buildName);
        checkedWithSuperUser.getBuildConfigRequest().get(configId);

        new BuildConfigurationPage()
                .clickOnGeneralSettingsTabButton()
                .checkBuildName(buildName);
    }

    @Test(groups = {"UI_regression"})
    public void userShouldBeAbleCreateBuildConfigWithManualOption() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/knastya/AutomationCourseProject";
        String buildName = testData.getBuildType().getName();

        loginAsUser(testData.getUser());
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        new CreateBuildConfigurationPage()
                .open(testData.getProject().getId())
                .clickOnManuallyOption()
                .createBuildConfigManually(buildName);

        new NewVcsRootPage()
                .addNewVcsRoot(url);

        String configId = getBuildConfigId(testData.getProject().getId(), buildName);
        checkedWithSuperUser.getBuildConfigRequest().get(configId);

        new BuildConfigurationPage()
                .clickOnGeneralSettingsTabButton()
                .checkBuildName(buildName);
    }

    private String getBuildConfigId(String projectId, String buildName) {
        String modifiedBuildName = buildName.replace("_", "")
                .replaceFirst("t", "T");
        return format("%s_%s", projectId, modifiedBuildName);
    }
}
