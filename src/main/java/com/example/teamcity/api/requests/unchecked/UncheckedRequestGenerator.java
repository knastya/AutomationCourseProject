package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.ToDelete;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Endpoint;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class UncheckedRequestGenerator extends Request implements CrudInterface {

    public UncheckedRequestGenerator(Endpoint endpoint, RequestSpecification spec) {
        super(endpoint, spec);
    }

    @Override
    public Response create(Object obj) {
        var response = given()
                .spec(spec)
                .body(obj)
                .post(endpoint.getUrl());

        if (response.statusCode() == SC_OK) {
            var castedObject = response.then().extract().as(endpoint.getClazz());
            if (castedObject instanceof ToDelete) {
                TestDataStorage.getStorage().addCreatedEntity((ToDelete) castedObject);
            }
        }
        return response;
    }

    @Override
    public Response get(String id) {
        return given().spec(spec).get(endpoint.getUrl() + "/id:" + id);
    }

    @Override
    public Response update(String id, Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .get(endpoint.getUrl() + "/id:" + id);
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(spec)
                .delete(endpoint.getUrl() + "/id:" + id);
    }

    public Response deleteByUsername(String username) {
        return given()
                .spec(spec)
                .delete(endpoint.getUrl() + "/username:" + username);
    }
}