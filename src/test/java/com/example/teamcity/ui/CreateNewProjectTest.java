package com.example.teamcity.ui;

import com.example.teamcity.ui.pages.admin.CreateNewProject;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.example.teamcity.api.generators.TestDataGenerator.ROOT;

public class CreateNewProjectTest extends BaseUiTest {

    @Test
    public void userShouldBeAbleCreateNewProject() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/knastya/AutomationCourseProject";

        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(ROOT)
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second)
                .orElseThrow(() -> new AssertionError("Element wasn't found"))
                .getHeader().shouldHave(text(testData.getProject().getName()));
    }

    @Test
    public void userShouldBeAbleCreateNewProjectWithManualOption() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(ROOT)
                .clickOnManuallyOption()
                .createProjectManually(testData.getProject().getName());

        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second)
                .orElseThrow(() -> new AssertionError("Element wasn't found"))
                .getHeader().shouldHave(text(testData.getProject().getName()));
    }

}
