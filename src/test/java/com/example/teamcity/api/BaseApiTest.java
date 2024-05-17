package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedRequests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.example.teamcity.api.spec.Specifications.superUserSpec;

public class BaseApiTest extends BaseTest {
    public TestDataStorage testDataStorage;

    public CheckedRequests checkedWithSuperUser = new CheckedRequests(superUserSpec());

    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(superUserSpec());

    @BeforeMethod
    public void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }
}
