package api.requests.unchecked;

import api.requests.CrudInterface;
import api.requests.Endpoint;
import api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedRequestGenerator extends Request implements CrudInterface {

    public UncheckedRequestGenerator(Endpoint endpoint, RequestSpecification spec) {
        super(endpoint, spec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(endpoint.getUrl());
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
                .delete(endpoint + "/username:" + username);
    }
}