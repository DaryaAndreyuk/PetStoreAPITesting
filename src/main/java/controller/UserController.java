package controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static utils.Constants.*;

public class UserController {
    private static final String USER_ENDPOINT = BASE_URL + "/user";

    RequestSpecification requestSpecification = given();

    public UserController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.baseUri(USER_ENDPOINT);
    }

    public Response addDefaultUser() {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .body(DEFAULT_USER)
                .request(Method.POST, USER_ENDPOINT)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response addDefaultUsersWithArray() {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .body(DEFAULT_USERS_ARRAY)
                .when()
                .post(BASE_URL + "/user/createWithArray")
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response logoutUser() {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .when()
                .get(BASE_URL + "/user/logout")
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response validUserLogin() {
        String username = "valid_username";
        String password = "valid_password";

        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get(BASE_URL + "/user/login")
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response deleteUser(String username) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .delete(BASE_URL + "/user/" + username)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response findUser(String username) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .request(Method.GET, USER_ENDPOINT + "/" + username)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response updateUserWithDefaultData(String username) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .body(UPDATED_USER)
                .when()
                .put(BASE_URL + "/user/" + username)
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
