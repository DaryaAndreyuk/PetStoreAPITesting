package controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static utils.Constants.*;

public class UserController {
    private static final String USER_ENDPOINT = BASE_URL + "/user";
    private static final String NON_EXIST_USERNAME_ENDPOINT = USER_ENDPOINT + "/non_existing_username";
    private static final String NON_EXIST_USER_ENDPOINT = USER_ENDPOINT + "/user_not_existed";

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
        User user1 = new User(1, "user1", "Sam", "Smith", "sam.smith@example.com", "password123", "1234567890", 1);
        User user2 = new User(2, "user2", "Harry", "Styles", "harry.styles@example.com", "password456", "0987654321", 2);
        List<User> usersArray = Arrays.asList(DEFAULT_USER, user1, user2);

        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .body(usersArray)
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

    public Response loginUser() {
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

    public Response updateUser(String username) {
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
