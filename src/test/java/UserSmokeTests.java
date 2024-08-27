import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserSmokeTests extends BaseTest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/user";
    private static final String USERNAME = "user_1";

    @Test
    void createUserTest() {
        String endpoint = BASE_URL;
        String body = """
                {
                  "id": 0,
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

        verifyUser(response, 0, "user_1", "firstName1", "lastName1", "email1@gmail.com", "qwerty", "12345678", 0);
    }

    @Test(dependsOnMethods = "createUserTest")
    void getUserTest() {
        String endpoint = BASE_URL + "/" + USERNAME;
        Response response = given()
                .when()
                .get(endpoint)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .response();

        verifyUser(response, 0, "user_1", "firstName1", "lastName1", "email1@gmail.com", "qwerty", "12345678", 0);
    }

    @Test(dependsOnMethods = "getUserTest")
    void updateUserTest() {
        String endpoint = BASE_URL + "/" + USERNAME;
        String body = """
                {
                  "id": 0,
                  "username": "user_2",
                  "firstName": "firstName2",
                  "lastName": "lastName2",
                  "email": "email2@gmail.com",
                  "password": "sfdsvdsc",
                  "phone": "987654321",
                  "userStatus": 0
                }
                """;
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .response();

        verifyUser(response, 0, "user_2", "firstName2", "lastName2", "email2@gmail.com", "sfdsvdsc", "987654321", 0);
    }

    @Test(dependsOnMethods = "updateUserTest")
    void deleteUserTest() {
        String endpoint = BASE_URL + "/" + "user_2"; // Updated username after update
        Response response = given()
                .when()
                .delete(endpoint)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .response();

        // Ensure the user is deleted by trying to retrieve it
        given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(404);
    }

    private void verifyUser(Response response, int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        response.then()
                .body("id", equalTo(id))
                .body("username", equalTo(username))
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName))
                .body("email", equalTo(email))
                .body("password", equalTo(password))
                .body("phone", equalTo(phone))
                .body("userStatus", equalTo(userStatus));
    }
}
