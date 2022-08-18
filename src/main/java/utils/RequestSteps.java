package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.commons.lang.StringUtils;
import org.apache.hc.core5.http.HttpStatus;
import java.util.List;
import static io.restassured.RestAssured.given;

public class RequestSteps {

    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");

    public RequestSteps() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(TEST_DATA.getValue("/baseUri").toString())
                .setContentType(ContentType.JSON)
                .build();
    }

    public <T> List<T> getAllResources(String uri, Class<T> clazz) {
        return given()
                .basePath(uri)
                .get()
                .then().statusCode(HttpStatus.SC_OK)
                .extract().body().jsonPath().getList(StringUtils.EMPTY, clazz);
    }

    public <T> T getValidResource(String uri, Integer validId, Class<T> clazz) {
        return given()
                .basePath(uri + validId)
                .get()
                .then().statusCode(HttpStatus.SC_OK)
                .extract().body().jsonPath().getObject(StringUtils.EMPTY, clazz);
    }

    public <T> T getInvalidResource(String uri, Integer invalidId, Class<T> clazz) {
        return given()
                .basePath(uri + invalidId)
                .get()
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .extract().body().jsonPath().getObject(StringUtils.EMPTY, clazz);
    }

    public <T> T createResource(String uri, T resource, Class<T> clazz) {
        return given()
                .basePath(uri)
                .body(resource)
                .when().post()
                .then().statusCode(HttpStatus.SC_CREATED)
                .extract().body().jsonPath().getObject(StringUtils.EMPTY, clazz);
    }
}