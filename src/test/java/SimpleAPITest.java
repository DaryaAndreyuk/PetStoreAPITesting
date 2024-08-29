import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static utils.Constants.*;

public class SimpleAPITest extends BaseTest {

    private static String PET_ID;

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

        PET_ID = "1500000";

        var response = given().
                header("accept", ACCEPT).
                header("Content-Type", CONTENT_TYPE).
                body(body).
                when().
                post(endpoint).
                then();

        response.log().body();
        response.statusCode(SUCCESS_STATUS_CODE);
    }

    @Test(dependsOnMethods = "createPetTest")
    void getPetTest() {
        String endpoint = BASE_URL + "/pet/" + PET_ID;

        var response = given().
                header("accept", ACCEPT).
                when().
                get(endpoint).
                then();

        response.log().body();
        response.statusCode(SUCCESS_STATUS_CODE);
        response.body("id", equalTo(Integer.parseInt(PET_ID)));
        response.body("name", equalTo("cattie"));
    }
}