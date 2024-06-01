package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.ToDelete;

import java.util.ArrayList;
import java.util.List;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private final List<ToDelete> addedEntityList;

    private TestDataStorage() {
        this.addedEntityList = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    public TestData addTestData() {
        return TestDataGenerator.generate();
    }

    public void addCreatedEntity(ToDelete objectToDelete) {
        addedEntityList.add(objectToDelete);
    }

    public void delete() {
        addedEntityList.forEach(ToDelete::delete);
        addedEntityList.clear();
    }
}
