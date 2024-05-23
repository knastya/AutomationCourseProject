package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedRequests;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.RoleId.PROJECT_ADMIN;
import static com.example.teamcity.api.enums.RoleId.SYSTEM_ADMIN;
import static com.example.teamcity.api.generators.TestDataGenerator.generateRoles;
import static com.example.teamcity.api.generators.TestDataGenerator.projectScope;
import static com.example.teamcity.api.spec.Specifications.authSpec;
import static com.example.teamcity.api.spec.Specifications.unAuthSpec;
import static java.lang.String.format;
import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.Matchers.containsString;

public class RolesTest extends BaseApiTest {

    @Test
    public void unauthorizedUserShouldNotHaveRightToCreateProject() {
        var testData = testDataStorage.addTestData();

        new UncheckedRequests(unAuthSpec()).getProjectRequest()
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED);
                //.body(containsString("Authentication required"));

        uncheckedWithSuperUser.getProjectRequest()
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(SC_NOT_FOUND)
                .body(containsString(
                        format("No project found by locator 'count:1,id:%s'", testData.getProject().getId())
                ));
    }

    @Test
    public void systemAdminShouldHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();

        testData.getUser().setRoles(generateRoles(SYSTEM_ADMIN, "g"));

        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        var project = new CheckedRequests(authSpec(testData.getUser()))
                .getProjectRequest()
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigToHisProject() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        testData.getUser().setRoles(
                generateRoles(PROJECT_ADMIN, projectScope(testData.getProject().getId()))
        );

        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        var buildConfig = new CheckedRequests(authSpec(testData.getUser()))
                .getBuildConfigRequest()
                .create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProject() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());
        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        firstTestData.getUser().setRoles(
                generateRoles(PROJECT_ADMIN, projectScope(firstTestData.getProject().getId()))
        );

        checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

        secondTestData.getUser().setRoles(
                generateRoles(PROJECT_ADMIN, projectScope(secondTestData.getProject().getId()))
        );

        checkedWithSuperUser.getUserRequest().create(secondTestData.getUser());

        new UncheckedRequests(authSpec(secondTestData.getUser()))
                .getBuildConfigRequest()
                .create(firstTestData.getBuildType())
                .then().assertThat().statusCode(SC_FORBIDDEN);
    }

}
