package com.example.teamcity.api;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Step;
import com.example.teamcity.api.models.Steps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.models.Step.buildCommandLineStep;
import static com.example.teamcity.api.models.Step.buildPowerShellStep;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.hamcrest.Matchers.containsString;

public class BuildConfigurationTest extends BaseApiTest {

    private BuildType buildConfigTestData;
    private NewProjectDescription projectTestData;

    @BeforeMethod
    public void before() {
        var testData = testDataStorage.addTestData();
        buildConfigTestData = testData.getBuildType();
        projectTestData = testData.getProject();
        checkedWithSuperUser.getProjectRequest().create(projectTestData);
    }

    @DataProvider
    public static Object[][] correctNamesProvider() {
        return new Object[][]{
                {randomAlphabetic(80)}, // max on UI
                {randomAlphabetic(1)},
                {" Spaces Case  "},
                {" "}, // looks like a bug :)
                {randomNumeric(15)},
                {random(6, 'ю', 'ь', 'ö', 'é', '漢', '€')}, // localisation, unicode symbols
                {"specialSymbols~!@#$%^&*()-_=+[]\\{}|;':\",./<>?"},
                {"\uD83D\uDE00"} //emoji
        };
    }

    @Test(dataProvider = "correctNamesProvider")
    public void createBuildConfigRequestWithCorrectNameShouldPass(String nameToCheck) {
        buildConfigTestData.setName(nameToCheck);
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);
        softy.assertThat(buildConfig.getName()).isEqualTo(nameToCheck);
    }

    @Test
    public void createBuildConfigRequestWithExistedNameInSameProjectShouldFail() {
        var buildConfigTestData2 = testDataStorage.addTestData().getBuildType();
        buildConfigTestData2.getProject().setId(buildConfigTestData.getProject().getId());
        buildConfigTestData2.setName(buildConfigTestData.getName());

       checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);

        uncheckedWithSuperUser.getBuildConfigRequest()
                .create(buildConfigTestData2)
                .then().assertThat().statusCode(SC_CLIENT_ERROR)
                .body(containsString(format(
                        "DuplicateBuildTypeNameException: Build configuration with name \"%s\" already exists in project: \"%s\"",
                        buildConfigTestData.getName(), projectTestData.getName()
                )));
    }

    @Test
    public void createBuildConfigRequestWithExistedNameInOtherProjectShouldPass() {
        checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);

        var testData2 = testDataStorage.addTestData();
        var projectTestData2 = testData2.getProject();

        checkedWithSuperUser.getProjectRequest().create(projectTestData2);

        var buildConfigTestData2 = testData2.getBuildType();
        buildConfigTestData2.getProject().setId(projectTestData2.getId());
        buildConfigTestData2.setName(buildConfigTestData.getName());

        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData2);
        softy.assertThat(buildConfig.getName()).isEqualTo(buildConfigTestData.getName());
    }

    @Test
    public void createBuildConfigRequestWithoutNameShouldFail() {
        buildConfigTestData.setName(null);
        uncheckedWithSuperUser.getBuildConfigRequest()
                .create(buildConfigTestData)
                .then().assertThat().statusCode(SC_CLIENT_ERROR)
                .body(containsString(
                        "BadRequestException: When creating a build type, non empty name should be provided.\n" +
                                "Invalid request. Please check the request URL and data are correct."));
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
    public void createBuildConfigRequestWithCorrectIdShouldPass(String idToCheck) {
        buildConfigTestData.setId(idToCheck);
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);
        softy.assertThat(buildConfig.getId()).isEqualTo(buildConfigTestData.getId());
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
    public void createBuildConfigRequestWithIncorrectIdShouldFail(String idToCheck) {
        buildConfigTestData.setId(idToCheck);
        uncheckedWithSuperUser.getBuildConfigRequest()
                .create(buildConfigTestData)
                .then().assertThat().statusCode(SC_SERVER_ERROR)
                .body(containsString(
                        "ID should start with a latin letter and contain only latin letters," +
                                " digits and underscores (at most 225 characters)"
                ));
    }

    @Test
    public void createBuildConfigRequestWithExistedIdInSameProjectShouldFail() {
        var buildConfigTestData2 = testDataStorage.addTestData().getBuildType();
        buildConfigTestData2.getProject().setId(buildConfigTestData.getProject().getId());
        buildConfigTestData2.setId(buildConfigTestData.getId());

        checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);

        uncheckedWithSuperUser.getBuildConfigRequest()
                .create(buildConfigTestData2)
                .then().assertThat().statusCode(SC_CLIENT_ERROR)
                .body(containsString(format(
                        "DuplicateExternalIdException: The build configuration / template ID \"%s\" is already used by another configuration or template",
                        buildConfigTestData2.getId()
                )));
    }

    @Test
    public void createBuildConfigRequestWithExistedIdInDifferentProjectShouldFail() {
        var testData2 = testDataStorage.addTestData();
        var projectTestData2 = testData2.getProject();

        checkedWithSuperUser.getProjectRequest().create(projectTestData2);

        var buildConfigTestData2 = testData2.getBuildType();
        buildConfigTestData2.getProject().setId(projectTestData2.getId());
        buildConfigTestData2.setId(buildConfigTestData.getId());

        checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);

        uncheckedWithSuperUser.getBuildConfigRequest()
                .create(buildConfigTestData2)
                .then().assertThat().statusCode(SC_CLIENT_ERROR)
                .body(containsString(format(
                        "DuplicateExternalIdException: The build configuration / template ID \"%s\" is already used by another configuration or template",
                        buildConfigTestData2.getId()
                )));
    }

    @Test
    public void createBuildConfigRequestWithoutIdShouldPass() {
        buildConfigTestData.setId(null);
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);
        softy.assertThat(buildConfig.getId()).isNotEmpty();
    }

    @Test
    public void createBuildConfigRequestWithNonExistedProjectShouldFail() {
        var id = "notExisted";
        buildConfigTestData.getProject().setId(id);

        uncheckedWithSuperUser.getBuildConfigRequest()
                .create(buildConfigTestData)
                .then().assertThat().statusCode(SC_NOT_FOUND)
                .body(containsString(format("NotFoundException: No project found by locator 'count:1,id:%s'." +
                                " Project cannot be found by external id '%s'." +
                                "\nCould not find the entity requested." +
                                " Check the reference is correct and the user has permissions to access the entity.",
                        id, id)
                ));
    }

    @Test
    public void createBuildConfigRequestWithoutStepsShouldPass() {
        buildConfigTestData.setSteps(null);
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);
        softy.assertThat(buildConfig.getSteps().getCount()).isEqualTo(0);
        softy.assertThat(buildConfig.getSteps().getStep()).isNull();
    }


    @Test
    public void createBuildConfigRequestWithCommandLineStepsShouldPass() {
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);

        softy.assertThat(buildConfig.getSteps().getCount()).isEqualTo(1);
        checkStep(buildConfig.getSteps().getStep().get(0), buildConfigTestData.getSteps().getStep().get(0));
    }

    @Test
    public void createBuildConfigRequestWithPowerShellStepsShouldPass() {
        buildConfigTestData.setSteps(new Steps(buildPowerShellStep()));
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);

        softy.assertThat(buildConfig.getSteps().getCount()).isEqualTo(1);
        checkStep(buildConfig.getSteps().getStep().get(0), buildConfigTestData.getSteps().getStep().get(0));
    }

    @Test
    public void createBuildConfigRequestWithTwoStepsShouldPass() {
        var steps = Steps.builder()
                .step(Arrays.asList(buildCommandLineStep(), buildPowerShellStep()))
                .build();
        buildConfigTestData.setSteps(steps);
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(buildConfigTestData);

        softy.assertThat(buildConfig.getSteps().getCount()).isEqualTo(2);
    }

    private void checkStep(Step actualStep, Step expectedStep) {
        softy.assertThat(actualStep.getType()).isEqualTo(expectedStep.getType());
        softy.assertThat(actualStep.getName()).isEqualTo(expectedStep.getName());

        var stepPropActual = actualStep.getProperties().getProperty().get(0);
        var stepPropExpected = expectedStep.getProperties().getProperty().get(0);
        softy.assertThat(stepPropActual.getName()).isEqualTo(stepPropExpected.getName());
        softy.assertThat(stepPropActual.getValue()).isEqualTo(stepPropExpected.getValue());
    }

}