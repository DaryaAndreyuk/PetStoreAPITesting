package controller;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static utils.Constants.*;

public class UserController {
    private static final String USER_ENDPOINT = BASE_URL + "/user";
    RequestSpecification requestSpecification;

    public UserController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .baseUri(USER_ENDPOINT)
                .filter(new AllureRestAssured());
    }

    @Step("Create default user")
    public HttpResponse addDefaultUser() {
        this.requestSpecification.body(DEFAULT_USER);
        return new HttpResponse(given(requestSpecification).post().then());
    }

    @Step("Create default users based on array of users")
    public HttpResponse addDefaultUsersWithArray() {
        this.requestSpecification.body(DEFAULT_USERS_ARRAY);
        return new HttpResponse(given(requestSpecification).post("/createWithArray").then());
    }

    @Step("Log out user")
    public HttpResponse logoutUser() {
        return new HttpResponse(given(requestSpecification).get("/logout").then());
    }

    @Step("Log in of valid user")
    public HttpResponse validUserLogin() {
        String username = "valid_username";
        String password = "valid_password";
        return new HttpResponse(given(requestSpecification)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/login")
                .then());
    }

    @Step("Delete user with username: {username}")
    public HttpResponse deleteUser(String username) {
        return new HttpResponse(given(requestSpecification).delete("/" + username).then());
    }

    @Step("Get user by username: {username}")
    public HttpResponse findUser(String username) {
        return new HttpResponse(given(requestSpecification).get("/" + username).then());
    }

    @Step("Update user by username: {username} with default data")
    public HttpResponse updateUserWithDefaultData(String username) {
        this.requestSpecification.body(UPDATED_USER);
        return new HttpResponse(given(requestSpecification).put("/" + username).then());
    }
}