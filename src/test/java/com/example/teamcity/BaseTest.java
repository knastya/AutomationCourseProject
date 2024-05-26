package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedRequests;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.example.teamcity.api.spec.Specifications.superUserSpec;

public class BaseTest {
    protected SoftAssertions softy;
    public TestDataStorage testDataStorage;

    public CheckedRequests checkedWithSuperUser = new CheckedRequests(superUserSpec());

    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(superUserSpec());

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
        testDataStorage.delete();
    }
}