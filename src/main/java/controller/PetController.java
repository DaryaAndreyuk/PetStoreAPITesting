package controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static utils.Constants.ACCEPT_HEADER;
import static utils.Constants.APP_JSON_TYPE;
import static utils.Constants.BASE_URL;
import static utils.Constants.CONTENT_TYPE_HEADER;
import static utils.Constants.DEFAULT_PET;
import static utils.Constants.TEST_PET_ID;

public class PetController {
    private static final String PET_ENDPOINT = BASE_URL + "/pet";

    RequestSpecification requestSpecification = given();

    public PetController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.baseUri(PET_ENDPOINT);
    }

    public Response addDefaultPet() {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .body(DEFAULT_PET)
                .request(Method.POST, PET_ENDPOINT)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response findPet(int id) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .request(Method.GET, PET_ENDPOINT + "/" + id)
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
