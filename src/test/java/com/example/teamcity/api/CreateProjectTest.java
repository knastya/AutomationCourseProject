package com.example.teamcity.api;


import api.models.NewProjectDescription;
import api.models.Project;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import static api.generators.TestDataGenerator.ROOT;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.hamcrest.Matchers.containsString;

public class CreateProjectTest extends BaseApiTest {
    //TODO: revise method names
    private NewProjectDescription projectTestData;

    @BeforeMethod
    public void before(){
        projectTestData = testDataStorage.addTestData().getProject();
    }

    @DataProvider
    public static Object[][] correctNamesProvider() {
        return new Object[][]{
                {randomAlphabetic(80)}, // max on UI
                {randomAlphabetic(1)},
                {" Spaces Case  "},
                {randomNumeric(15)},
                {random(6, 'ю', 'ь', 'ö', 'é', '漢', '€')}, // localisation, unicode symbols
                {"specialSymbols~!@#$%^&*()-_=+[]\\{}|;':\",./<>?"},
                {"\uD83D\uDE00"} //emoji
        };
    }

    @Test(dataProvider = "correctNamesProvider")
    public void checkNameFieldInCreateProjectRequest(String nameToCheck) {
        projectTestData.setName(nameToCheck);
        var project = checkedWithSuperUser.getProjectRequest().create(projectTestData);
        softy.assertThat(project.getName()).isEqualTo(nameToCheck);
    }

    @Test
    public void createProjectWithEmptyNameFails() {
        projectTestData.setName(" ");
        uncheckedWithSuperUser.getProjectRequest()
                .create(projectTestData)
                .then().assertThat().statusCode(SC_SERVER_ERROR)
                .body(containsString("InvalidNameException: Given project name is empty"));
    }

    @Test
    public void createProjectWithExistedNameFails() {
        var projectTestData2 = testDataStorage.addTestData().getProject();
        projectTestData2.setName(projectTestData.getName());

        checkedWithSuperUser.getProjectRequest().create(projectTestData);

        uncheckedWithSuperUser.getProjectRequest()
                .create(projectTestData2)
                .then().assertThat().statusCode(SC_CLIENT_ERROR)
                .body(containsString(format(
                        "DuplicateProjectNameException: Project with this name already exists: %s",
                        projectTestData.getName()
                )));
    }

    @Test
    public void checkCreateProjectWithoutNameFails() {
        projectTestData.setName(null);
        uncheckedWithSuperUser.getProjectRequest()
                .create(projectTestData)
                .then().assertThat().statusCode(SC_CLIENT_ERROR)
                .body(containsString("BadRequestException: Project name cannot be empty."));
    }

    @DataProvider
    public static Object[][] correctIdsProvider() {
        return new Object[][]{
                {randomAlphabetic(225)},
                {randomAlphabetic(1)},
                {randomAlphabetic(4) + "_" + randomNumeric(5)},
                {"l" + randomNumeric(5)}
        };
    }

    @Test(dataProvider = "correctIdsProvider")
    public void checkIdFieldInCreateProjectRequest(String idToCheck) {
        projectTestData.setId(idToCheck);
        var project = checkedWithSuperUser.getProjectRequest().create(projectTestData);
        softy.assertThat(project.getId()).isEqualTo(projectTestData.getId());
    }

    @DataProvider
    public static Object[][] incorrectIdsProvider() {
        return new Object[][]{
                {randomAlphabetic(226)},
                {" " + randomAlphabetic(4)},
                {randomNumeric(4) + randomAlphabetic(4)},
                {"_" + randomAlphabetic(4)},
                {randomAlphabetic(4) + "слово"},
                {randomAlphabetic(4) + "~"},
        };
    }

    @Test(dataProvider = "incorrectIdsProvider")
    public void checkIdFieldInCreateProjectRequestFails(String idToCheck) {
        projectTestData.setId(idToCheck);
        uncheckedWithSuperUser.getProjectRequest()
                .create(projectTestData)
                .then().assertThat().statusCode(SC_SERVER_ERROR)
                .body(containsString(
                        "ID should start with a latin letter and contain only latin letters," +
                                " digits and underscores (at most 225 characters)"
                ));
    }

    @Test
    public void createProjectWithExistedIdFails() {
        var projectTestData2 = testDataStorage.addTestData().getProject();
        projectTestData2.setId(projectTestData.getId());

        checkedWithSuperUser.getProjectRequest().create(projectTestData);

        uncheckedWithSuperUser.getProjectRequest()
                .create(projectTestData2)
                .then().assertThat().statusCode(SC_CLIENT_ERROR)
                .body(containsString(format(
                        "DuplicateExternalIdException: Project ID \"%s\" is already used by another project",
                        projectTestData.getId()
                )));
    }

    @Test
    public void checkCreateProjectWithoutIdPasses() {
        projectTestData.setId(null);
        var project = checkedWithSuperUser.getProjectRequest().create(projectTestData);
        softy.assertThat(project.getId()).isNotEmpty();
    }

    @Test
    public void checkCopyAllAssociatedSettingsFieldInCreateProjectRequest() {
        projectTestData.setCopyAllAssociatedSettings(false);
        uncheckedWithSuperUser.getProjectRequest()
                .create(projectTestData)
                .then().assertThat().statusCode(SC_SUCCESS);
    }

    @Test
    public void checkCreateProjectWithRootProjectParentId() {
        var project = checkedWithSuperUser.getProjectRequest().create(projectTestData);
        softy.assertThat(project.getParentProjectId()).isEqualTo(ROOT);
        softy.assertThat(project.getParentProject().getId()).isEqualTo(ROOT);
    }


    @Test
    public void checkCreateProjectWithOtherProjectParentId() {
        checkedWithSuperUser.getProjectRequest().create(projectTestData);

        var projectTestData2 = testDataStorage.addTestData().getProject();
        var locator = "id:" + projectTestData.getId();
        projectTestData2.setParentProject(Project.builder().locator(locator).build());

        var project = checkedWithSuperUser.getProjectRequest().create(projectTestData2);
        softy.assertThat(project.getParentProjectId()).isEqualTo(projectTestData.getId());
        softy.assertThat(project.getParentProject().getId()).isEqualTo(projectTestData.getId());
    }

    @Test
    public void checkCreateProjectWithOtherProjectParentName() {
        checkedWithSuperUser.getProjectRequest().create(projectTestData);

        var projectTestData2 = testDataStorage.addTestData().getProject();
        var locator = "name:" + projectTestData.getName();
        projectTestData2.setParentProject(Project.builder().locator(locator).build());

        var project = checkedWithSuperUser.getProjectRequest().create(projectTestData2);
        softy.assertThat(project.getParentProjectId()).isEqualTo(projectTestData.getId());
        softy.assertThat(project.getParentProject().getName()).isEqualTo(projectTestData.getName());
    }

    @Test
    public void checkCreateProjectWithNonExistedProjectParentNegative() {
        var id = "notExisted";
        var locator = "id:" + id;
        projectTestData.setParentProject(Project.builder().locator(locator).build());

        uncheckedWithSuperUser.getProjectRequest()
                .create(projectTestData)
                .then().assertThat().statusCode(SC_NOT_FOUND)
                .body(containsString(format("NotFoundException: No project found by locator 'count:1,%s'." +
                        " Project cannot be found by external id '%s'." +
                        "\nCould not find the entity requested." +
                        " Check the reference is correct and the user has permissions to access the entity.",
                        locator, id)
                ));
    }
}
