import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static utils.Constants.*;

public class PetSmokeTests extends BaseTest {

    private static int petId;

    @Test
    void createPetTest() {
        String requestBody = """
            {
              "id": 41025555,
              "category": {
                "id": 2,
                "name": "Dogs"
              },
              "name": "Rex",
              "photoUrls": [
                "http://example.com/rex.jpg"
              ],
              "tags": [
                {
                  "id": 1,
                  "name": "friendly"
                }
              ],
              "status": "available"
            }
            """;

        Response response = createPet(requestBody);
        petId = response.jsonPath().getInt("id");
        System.out.println("Created Pet ID: " + petId);
        verifyPet(response, petId, "Rex", "http://example.com/rex.jpg", "available", 2);
    }

    @Test
    void getPetTest() {
        Response response = getPet(petId);
        verifyPet(response, petId, "Rex", "http://example.com/rex.jpg", "available", 2);
    }

    @Test
    void updatePetTest() {
        String updatedRequestBody = """
            {
              "id": %d,
              "category": {
                "id": 1,
                "name": "Dogs"
              },
              "name": "Max",
              "photoUrls": [
                "http://example.com/max.jpg"
              ],
              "tags": [
                {
                  "id": 1,
                  "name": "friendly"
                }
              ],
              "status": "sold"
            }
            """.formatted(petId);

        Response response = updatePet(updatedRequestBody);

        verifyPet(response, petId, "Max", "http://example.com/max.jpg", "sold", 1);
    }

    @Test
    void deletePetTest() {
        deletePet(petId);

        given().
                pathParam("petId", petId).
                when().
                get("/pet/{petId}").
                then().
                statusCode(NOT_FOUND_STATUS_CODE).
                body("message", equalTo("Pet not found"));
    }

    private Response createPet(String requestBody) {
        return given().
                header("Content-Type", CONTENT_TYPE).
                body(requestBody).
                when().
                post("/pet").
                then().
                log().ifError().
                statusCode(SUCCESS_STATUS_CODE).
                extract().
                response();
    }

    private Response getPet(int petId) {
        return given().
                pathParam("petId", petId).
                when().
                get("/pet/{petId}").
                then().
                log().all().
                statusCode(SUCCESS_STATUS_CODE).
                extract().
                response();
    }

    private Response updatePet(String requestBody) {
        return given().
                header("Content-Type", CONTENT_TYPE).
                body(requestBody).
                when().
                put("/pet").
                then().
                statusCode(SUCCESS_STATUS_CODE).
                extract().
                response();
    }

    private void deletePet(int petId) {
        given().
                pathParam("petId", petId).
                when().
                delete("/pet/{petId}").
                then().
                statusCode(SUCCESS_STATUS_CODE).
                body("message", equalTo(Integer.toString(petId)));
    }

    private void verifyPet(Response response, int petId, String name, String photoUrl, String status, int categoryId) {
        response.then().
                body("id", equalTo(petId)).
                body("category.id", equalTo(categoryId)).
                body("category.name", equalTo("Dogs")).
                body("name", equalTo(name)).
                body("photoUrls", hasItems(photoUrl)).
                body("tags[0].id", equalTo(1)).
                body("tags[0].name", equalTo("friendly")).
                body("status", equalTo(status));
    }
}