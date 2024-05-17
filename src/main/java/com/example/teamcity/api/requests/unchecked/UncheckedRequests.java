package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.Endpoint;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequests {
    private final UncheckedRequestGenerator userRequest;
    private final UncheckedRequestGenerator projectRequest;
    private final UncheckedRequestGenerator buildConfigRequest;

    public UncheckedRequests(RequestSpecification spec) {
        this.userRequest = new UncheckedRequestGenerator(Endpoint.USER, spec);
        this.buildConfigRequest = new UncheckedRequestGenerator(Endpoint.BUILD_CONFIG, spec);
        this.projectRequest = new UncheckedRequestGenerator(Endpoint.PROJECT, spec);
   }
}
