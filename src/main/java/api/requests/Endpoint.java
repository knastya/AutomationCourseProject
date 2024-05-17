package api.requests;

import api.models.BuildType;
import api.models.NewProjectDescription;
import api.models.User;
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
