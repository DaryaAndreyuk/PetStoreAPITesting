import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SimpleAPITest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PET_ID = "9223372036854769000";

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    void getPetTest() {
        String endpoint = BASE_URL + "/pet/" + PET_ID;
        System.out.println(endpoint);

        var response = given()
                .header("accept", "application/json")
                .when()
                .get(endpoint)
                .then()
                .log().body();

        // Check if the pet exists
        if (response.extract().statusCode() == 200) {
            response.statusCode(200)
                    .body("id", equalTo(Long.parseLong(PET_ID)))
                    .body("name", not(emptyOrNullString()))
                    .body("status", anyOf(equalTo("available"), equalTo("pending"), equalTo("sold")));
        } else {
            System.out.println("Pet not found. Consider creating it first.");
            response.statusCode(404);
        }
    }

    @Test
    void createPetTest() {
        String endpoint = BASE_URL + "/pet";
        String body = """
                {
                  "id": 0,
                  "category": {
                    "id": 0,
                    "name": "string"
                  },
                  "name": "cattie",
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "string"
                    }
                  ],
                  "status": "available"
                }
                """;
        var response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("cattie"))
                .body("status", equalTo("available"));

        // Extract the created pet's ID for potential use in other tests
        long createdPetId = response.extract().path("id");
        System.out.println("Created Pet ID: " + createdPetId);
    }
}