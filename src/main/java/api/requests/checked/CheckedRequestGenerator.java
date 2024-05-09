package api.requests.checked;

import api.requests.CrudInterface;
import api.requests.Endpoint;
import api.requests.Request;
import api.requests.unchecked.UncheckedRequestGenerator;
import io.restassured.specification.RequestSpecification;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;

public class CheckedRequestGenerator<T> extends Request implements CrudInterface {

    public CheckedRequestGenerator(Endpoint endpoint, RequestSpecification spec) {
        super(endpoint, spec);
    }

    @Override
    public T create(Object obj) {
        return (T) new UncheckedRequestGenerator(endpoint, spec).create(obj)
                .then().assertThat().statusCode(SC_OK)
                .extract().as(endpoint.getClazz());
    }

    @Override
    public Object get(String id) {
        return new UncheckedRequestGenerator(endpoint, spec).get(id)
                .then().assertThat().statusCode(SC_OK)
                .extract().as(endpoint.getClazz());
    }

    @Override
    public Object update(String id, Object obj) {
        return new UncheckedRequestGenerator(endpoint, spec).update(id, obj)
                .then().assertThat().statusCode(SC_OK)
                .extract().as(endpoint.getClazz());
    }

    @Override
    public String delete(String id) {
        return new UncheckedRequestGenerator(endpoint, spec).delete(id)
                .then().assertThat().statusCode(SC_NO_CONTENT)
                .extract().asString();
    }

    public String deleteByUsername(String username) {
        return new UncheckedRequestGenerator(endpoint, spec).deleteByUsername(username)
                .then().assertThat().statusCode(SC_NO_CONTENT)
                .extract().asString();
    }
}
