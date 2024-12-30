package controller;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;
import static utils.Constants.*;

public class PetController {
    private static final String PET_ENDPOINT = BASE_URL + "/pet";
    private static final String STATUS_ENDPOINT = PET_ENDPOINT + "/findByStatus?status=";
    private static final String UPLOAD_IMAGE_ENDPOINT = PET_ENDPOINT + "/";

    RequestSpecification requestSpecification;

    public PetController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification =
                given()
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .baseUri(PET_ENDPOINT)
                        .filter(new AllureRestAssured());
    }

    @Step("Create default pet")
    public HttpResponse addDefaultPet() {
        this.requestSpecification.body(DEFAULT_PET);
        return new HttpResponse(given(requestSpecification).post().then());
    }

    @Step("Get pet by ID {petId}")
    public HttpResponse findPet(long petId) {
        return new HttpResponse(given(requestSpecification).get("/" + petId).then());
    }

    @Step("Create pet by status")
    public HttpResponse findByStatusPet(String status) {
        return new HttpResponse(given(requestSpecification).get(STATUS_ENDPOINT + status).then());
    }

    @Step("Delete pet by ID: {petId}")
    public HttpResponse deletePet(long petId) {
        return new HttpResponse(given(requestSpecification).delete(PET_ENDPOINT + "/" + petId).then());
    }

    @Step("Update pet")
    public HttpResponse updatePet() {
        this.requestSpecification.body(UPDATED_PET);
        return new HttpResponse(given(requestSpecification).put().then());
    }

    @Step("Update pet by ID: {petId}")
    public HttpResponse updatePetByIdFormData(long petId) {

        return new HttpResponse(given()

                .header(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded")
                .formParam("name", UPDATED_PET.getName())
                .formParam("status", UPDATED_PET.getStatus())
                .post(PET_ENDPOINT + "/" + petId)
                .then());
    }

    @Step("Update pet by ID: {petId} with some image: {filePath}")
    public HttpResponse updatePetUploadImage(long petId, String filePath) {
        File imageFile = new File(filePath);
        if (!imageFile.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        this.requestSpecification
                .header(CONTENT_TYPE_HEADER, "multipart/form-data")
                .multiPart("file", imageFile)
                .when();
        return new HttpResponse(given(requestSpecification).post(UPLOAD_IMAGE_ENDPOINT + petId + "/uploadImage").then());
    }
}
