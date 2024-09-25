package controller;

import io.qameta.allure.Step;
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

    @Step("Create default pet")
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

    @Step("Get pet by ID {petId}")
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

    @Step("Create pet by status")
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

    @Step("Delete pet by ID: {petId}")
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

    @Step("Update pet")
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

    @Step("Update pet by ID: {petId}")
    public Response updatePetByIdFormData(long petId) {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded")
                .when()
                .formParam("name", UPDATED_PET.getName())
                .formParam("status", UPDATED_PET.getStatus())
                .request(Method.POST, PET_ENDPOINT + "/" + petId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    @Step("Update pet by ID: {petId} with some image: {filePath}")
    public Response updatePetUploadImage(long petId, String filePath) {
        File imageFile = new File(filePath);
        if (!imageFile.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, "multipart/form-data")
                .multiPart("file", imageFile)
                .when()
                .post(UPLOAD_IMAGE_ENDPOINT + petId + "/uploadImage")
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
