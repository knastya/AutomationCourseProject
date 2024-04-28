package com.example.teamcity.api;

import api.requests.checked.CheckedProject;

import org.testng.annotations.Test;

import static api.spec.Specifications.authSpec;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var project = new CheckedProject(authSpec(testData.getUser()))
                .create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
