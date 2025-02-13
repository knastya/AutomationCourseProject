package com.example.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;

public class Request {
    protected RequestSpecification spec;
    protected Endpoint endpoint;

    public Request(RequestSpecification spec) {
        this.spec = spec;
    }

    public Request(Endpoint endpoint, RequestSpecification spec) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
