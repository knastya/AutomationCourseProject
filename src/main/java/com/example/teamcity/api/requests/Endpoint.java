package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoint {
    PROJECT("/app/rest/projects", NewProjectDescription.class),
    USER("/app/rest/users", User.class),
    BUILD_CONFIG("/app/rest/buildTypes", BuildType.class);

    private final String url;
    private final Class<?> clazz;
}
