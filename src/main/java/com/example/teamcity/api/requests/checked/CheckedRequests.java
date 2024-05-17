package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.Endpoint;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private final CheckedRequestGenerator<User> userRequest;
    private final CheckedRequestGenerator<NewProjectDescription> projectRequest;
    private final CheckedRequestGenerator<BuildType> buildConfigRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.userRequest = new CheckedRequestGenerator<>(Endpoint.USER, spec);
        this.buildConfigRequest = new CheckedRequestGenerator<>(Endpoint.BUILD_CONFIG, spec);
        this.projectRequest = new CheckedRequestGenerator<>(Endpoint.PROJECT, spec);
    }
}
