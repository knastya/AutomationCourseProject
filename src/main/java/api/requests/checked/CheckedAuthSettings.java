package api.requests.checked;

import api.config.Config;
import api.models.AuthSettings;
import api.requests.Request;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CheckedAuthSettings extends Request {
    private static final String AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings";

    public CheckedAuthSettings(RequestSpecification spec) {
        super(spec);
    }

    public void update(AuthSettings authSettings) {
        given()
                .spec(spec)
                .body(authSettings)
                .put(AUTH_SETTINGS_ENDPOINT)
                .then().assertThat().statusCode(SC_OK);
    }
}
