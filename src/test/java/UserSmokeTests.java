import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserSmokeTests extends BaseTest {

    private static final String USER_ENDPOINT = "/user";
    private static final String USERNAME = "user_1";

    @Test
    public void createUserTest() {
        String endpoint = BASE_URL + USER_ENDPOINT;
        String body = """
                {
                  "id": 2,
                  "username": "user_1",
                  "firstName": "firstName1",
                  "lastName": "lastName1",
                  "email": "email1@gmail.com",
                  "password": "qwerty",
                  "phone": "12345678",
                  "userStatus": 0
                }
                """;
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .response();

        response.then()
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo("2"));
    }

}
