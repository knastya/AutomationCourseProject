package api.requests.checked;

import api.models.User;

import static api.spec.Specifications.authSpec;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class AuthRequest {
    private final User user;

    public AuthRequest(User user) {
        this.user = user;
    }

    public String getCsrfToken() {
        return given()
                .spec(authSpec(user))
                .get("/authenticationTest.html?csrf")
                .then().assertThat().statusCode(SC_OK)
                .extract().asString();
    }
}
