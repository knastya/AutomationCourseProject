package api.spec;

import api.config.Config;
import api.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static java.lang.String.format;

public final class Specifications {

    private static RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri(format("http://%s", Config.getProperty("host")));
        requestBuilder.addFilter(new RequestLoggingFilter());
        requestBuilder.addFilter(new ResponseLoggingFilter());
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public static RequestSpecification unAuthSpec() {
        var requestBuilder = reqBuilder();
        return requestBuilder.build();
    }

    public static RequestSpecification authSpec(User user) {
        var requestBuilder = reqBuilder();
        var url = format("http://%s:%s@%s", user.getUsername(), user.getPassword(), Config.getProperty("host"));
        requestBuilder.setBaseUri(url);
        return requestBuilder.build();
    }

    public static RequestSpecification superUserSpec() {
        var requestBuilder = reqBuilder();
        var url = format("http://:%s@%s", Config.getProperty("superUserToken"), Config.getProperty("host"));
        requestBuilder.setBaseUri(url);
        return requestBuilder.build();
    }
}
