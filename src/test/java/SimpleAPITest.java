import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SimpleAPITest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PET_ID = "9223372016900028000";

    @Test
    void getPetTest() {
        String endpoint = BASE_URL + "/pet/" + PET_ID;

        var response = given().
                header("accept", "application/json").
                when().
                get(endpoint).
                then();

        response.log().body();
        response.statusCode(200);
    }

    @Test
    void createPetTest() {
        String endpoint = BASE_URL + "/pet";
        String body = """
                {
                  "id": 0,
                  "category": {
                    "id": 0,
                    "name": "cat"
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
        var response = given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(body).
                when().
                post(endpoint).
                then();
        response.log().body();
        response.statusCode(200);
    }
}
