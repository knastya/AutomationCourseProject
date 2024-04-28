package api.requests.checked;

import api.models.User;
import api.requests.CrudInterface;
import api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedUser implements CrudInterface {

    private final RequestSpecification spec;

    public CheckedUser(RequestSpecification spec) {
        this.spec = spec;
    }

    @Override
    public User create(Object obj) {
        return new UncheckedUser(spec)
                .create(obj)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(User.class);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(String id, Object obj) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedUser(spec)
                .delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
