package controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;
import static utils.Constants.*;

public class PetController {
    private static final String PET_ENDPOINT = BASE_URL + "/pet";
    private static final String STATUS_ENDPOINT = PET_ENDPOINT + "/findByStatus?status=";
    private static final String UPLOAD_IMAGE_ENDPOINT = PET_ENDPOINT + "/" ;

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

    public Response findPet(long petId) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .request(Method.GET, PET_ENDPOINT + "/" + petId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response findByStatusPet(String status) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .when()
                .request(Method.GET, STATUS_ENDPOINT + status)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response deletePet(long petId) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .when()
                .request(Method.DELETE, PET_ENDPOINT + "/" + petId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response updatePet() {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .body(UPDATED_PET)
                .request(Method.PUT, PET_ENDPOINT)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response updatePetByIdFormData(long petId) {
        String body = "name=" + UPDATED_PET.getName() + "&status=" + UPDATED_PET.getStatus();
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded")
                .when()
                .body(body)
                .request(Method.POST, PET_ENDPOINT + "/" + petId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response updatePetUploadImage (long petId) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, "multipart/form-data")
                .when()
                .multiPart("file", new File("I:\\idea-workspace\\PetStoreAPITesting\\cat.jpeg"))
                .request(Method.POST, UPLOAD_IMAGE_ENDPOINT + petId + "/uploadImage")
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
