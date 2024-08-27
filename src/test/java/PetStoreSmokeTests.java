import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetStoreSmokeTests extends BaseTest {

    private static long petId;

    @Test(priority = 1)
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

        Response response = given().
                header("Content-Type", "application/json").
                body(requestBody).
                when().
                post("/pet").
                then().
                log().ifError().
                        statusCode(200).
                        extract().
                response();

        System.out.println("Response Body: " + response.getBody().asString());

        petId = response.jsonPath().getLong("id");
        System.out.println("Created Pet ID: " + petId);
    }

    @Test(dependsOnMethods = "createPetTest")
    void getPetTest() {
        given().
                pathParam("petId", petId).
                when().
                get("/pet/{petId}").
                then().
                log().all().
                statusCode(200).
                body("id", equalTo((int) petId)).
                body("name", equalTo("Rex"));
    }

    @Test(dependsOnMethods = "getPetTest")
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

        given().
                header("Content-Type", "application/json").
                body(updatedRequestBody).
                when().
                put("/pet").
                then().
                statusCode(200).
                body("id", equalTo((int) petId)).
                body("name", equalTo("Max")).
                body("status", equalTo("sold"));
    }

    @Test(dependsOnMethods = "updatePetTest")
    void deletePetTest() {
        given().
                pathParam("petId", petId).
                when().
                delete("/pet/{petId}").
                then().
                statusCode(200).
                body("message", equalTo(Long.toString(petId)));
    }

    @Test(dependsOnMethods = "deletePetTest")
    void getDeletedPetTest() {
        given().
                pathParam("petId", petId).
                when().
                get("/pet/{petId}").
                then().
                statusCode(404).
                body("message", equalTo("Pet not found"));
    }
}
