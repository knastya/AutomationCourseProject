package com.example.teamcity.api;

import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        var project = checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());

        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }
}
