package api.requests.unchecked;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import static api.requests.Endpoint.*;

@Getter
public class UncheckedRequests {
    private final UncheckedRequestGenerator userRequest;
    private final UncheckedRequestGenerator projectRequest;
    private final UncheckedRequestGenerator buildConfigRequest;

    public UncheckedRequests(RequestSpecification spec) {
        this.userRequest = new UncheckedRequestGenerator(USER, spec);
        this.buildConfigRequest = new UncheckedRequestGenerator(BUILD_CONFIG, spec);
        this.projectRequest = new UncheckedRequestGenerator(PROJECT, spec);
   }
}
