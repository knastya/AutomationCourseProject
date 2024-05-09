package api.requests.checked;

import api.models.BuildType;
import api.models.NewProjectDescription;
import api.models.User;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import static api.requests.Endpoint.*;

@Getter
public class CheckedRequests {
    private final CheckedRequestGenerator<User> userRequest;
    private final CheckedRequestGenerator<NewProjectDescription> projectRequest;
    private final CheckedRequestGenerator<BuildType> buildConfigRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.userRequest = new CheckedRequestGenerator<>(USER, spec);
        this.buildConfigRequest = new CheckedRequestGenerator<>(BUILD_CONFIG, spec);
        this.projectRequest = new CheckedRequestGenerator<>(PROJECT, spec);
    }
}
