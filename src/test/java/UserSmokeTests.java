import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.Constants.*;

public class UserSmokeTests extends BaseTest {

    private static final String USER_ENDPOINT = BASE_URL + "/user";
    private static final String USERNAME = "user_1";

    @Test
    public void createUserTest() {
        String requestBody = createUserJson(2, USERNAME, "firstName1", "lastName1", "email1@gmail.com", "qwerty", "12345678", 0);
        Response response = sendRequest("POST", USER_ENDPOINT, requestBody);
        verifyResponse(response, SUCCESS_STATUS_CODE, UNKNOWN_TYPE, "2");
    }

    @Test
    public void getExistingUserTest() {
        Response response = sendRequest("GET", USER_ENDPOINT + "/" + USERNAME, null);
        verifyStatusCode(response, SUCCESS_STATUS_CODE);
        verifyUser(response, 2, USERNAME, "firstName1", "lastName1", "email1@gmail.com", "qwerty", "12345678", 0);
    }

    @Test
    public void getNonExistingUserTest() {
        Response response = sendRequest("GET", USER_ENDPOINT + "/user_not_existed", null);
        verifyResponse(response, 1, "error", "User not found");
    }

    @Test
    public void updateUserTest() {
        String updatedUsername = "updated_user_1";
        String requestBody = createUserJson(2, updatedUsername, "updated_firstName1", "updated_lastName1", "updated_email1@gmail.com", "updated_qwerty", "12345678", 0);
        Response response = sendRequest("PUT", USER_ENDPOINT + "/" + USERNAME, requestBody);
        verifyResponse(response, SUCCESS_STATUS_CODE, UNKNOWN_TYPE, "2");
    }

    @Test
    public void deleteExistingUserTest() {
        Response response = sendRequest("DELETE", USER_ENDPOINT + "/" + USERNAME, null);
        verifyResponse(response, SUCCESS_STATUS_CODE, UNKNOWN_TYPE, USERNAME);
    }

    @Test
    public void deleteNonExistingUserTest() {
        Response response = sendRequest("DELETE", USER_ENDPOINT + "/non_existing_username", null);
        verifyStatusCode(response, NOT_FOUND_STATUS_CODE);
    }

    private Response sendRequest(String method, String endpoint, String body) {
        var request = given()
                .header("accept", ACCEPT)
                .header("Content-Type", CONTENT_TYPE);

        if (body != null && !body.isEmpty() && ("POST".equals(method) || "PUT".equals(method))) {
            request.body(body);
        }

        return request
                .when()
                .request(method, endpoint)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    private String createUserJson(int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        return """
                {
                  "id": %d,
                  "username": "%s",
                  "firstName": "%s",
                  "lastName": "%s",
                  "email": "%s",
                  "password": "%s",
                  "phone": "%s",
                  "userStatus": %d
                }
                """.formatted(id, username, firstName, lastName, email, password, phone, userStatus);
    }

    private void verifyUser(Response response, int userId, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        response.then()
                .body("id", equalTo(userId))
                .body("username", equalTo(username))
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName))
                .body("email", equalTo(email))
                .body("password", equalTo(password))
                .body("phone", equalTo(phone))
                .body("userStatus", equalTo(userStatus));
    }
}
