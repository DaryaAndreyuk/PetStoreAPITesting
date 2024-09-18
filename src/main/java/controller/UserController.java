package controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static utils.Constants.*;
import static utils.Constants.DEFAULT_PET;

public class UserController {
    private static final String USER_ENDPOINT = BASE_URL + "/user";
    private static final String NON_EXIST_USERNAME_ENDPOINT = USER_ENDPOINT + "/non_existing_username";
    private static final String NON_EXIST_USER_ENDPOINT  = USER_ENDPOINT + "/user_not_existed";

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


}
