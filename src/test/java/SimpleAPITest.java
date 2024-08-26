import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SimpleAPITest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static String PET_ID;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    void createPetTest() {
        String endpoint = BASE_URL + "/pet";
        String body = """
                {
                  "id": 1500000,
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

        // Сохраняем ID питомца
        PET_ID = "1500000";

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

    @Test(dependsOnMethods = "createPetTest")
    void getPetTest() {
        String endpoint = BASE_URL + "/pet/" + PET_ID;

        var response = given().
                header("accept", "application/json").
                when().
                get(endpoint).
                then();

        response.log().body();
        response.statusCode(200);
        response.body("id", equalTo(Integer.parseInt(PET_ID)));
        response.body("name", equalTo("cattie"));
    }
}