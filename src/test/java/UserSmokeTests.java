import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.Constants.*;

public class UserSmokeTests extends BaseTest {

    private static final String USER_ENDPOINT = BASE_URL + "/user";
    private static final String NON_EXIST_USERNAME_ENDPOINT = USER_ENDPOINT + "/non_existing_username";
    private static final String NON_EXIST_USER_ENDPOINT  = USER_ENDPOINT + "/user_not_existed";

    @BeforeMethod
    public void setUp() {
        if (!userExists(DEFAULT_USERNAME))
            createUser(DEFAULT_USERNAME, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PASSWORD, DEFAULT_PHONE, DEFAULT_USER_STATUS);
    }

    @Test
    public void createUserTest() {
        String requestBody = createUserJson(
                DEFAULT_USER_ID,
                DEFAULT_USERNAME,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PASSWORD,
                DEFAULT_PHONE,
                DEFAULT_USER_STATUS
        );
        Response response = sendRequest(POST_METHOD, USER_ENDPOINT, requestBody);
        verifyResponse(response, SUCCESS_STATUS_CODE, UNKNOWN_TYPE, Integer.toString(DEFAULT_USER_ID));
    }

    @Test
    public void getExistingUserTest() {
        Response response = sendRequest(GET_METHOD, USER_ENDPOINT + "/" + DEFAULT_USERNAME, null);
        verifyStatusCode(response, SUCCESS_STATUS_CODE);
        verifyUser(response,
                DEFAULT_USER_ID,
                DEFAULT_USERNAME,
                DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_EMAIL,
                DEFAULT_PASSWORD,
                DEFAULT_PHONE,
                DEFAULT_USER_STATUS);
    }

    @Test
    public void getNonExistingUserTest() {
        Response response = sendRequest(GET_METHOD, NON_EXIST_USER_ENDPOINT, null);
        verifyResponse(response, 1, ERROR_TYPE, USER_NOT_FOUND_MESSAGE);
    }

    @Test
    public void updateUserTest() {
        String requestBody = createUserJson(DEFAULT_USER_ID, UPDATED_USERNAME, UPDATED_FIRST_NAME, UPDATED_LASTNAME, UPDATED_EMAIL, UPDATED_PASSWORD, UPDATED_PHONE, DEFAULT_USER_STATUS);
        Response response = sendRequest(PUT_METHOD, USER_ENDPOINT + "/" + DEFAULT_USERNAME, requestBody);
        verifyResponse(response, SUCCESS_STATUS_CODE, UNKNOWN_TYPE, "2");
    }

    @Test
    public void deleteExistingUserTest() {
        Response response = sendRequest(DELETE_METHOD, USER_ENDPOINT + "/" + DEFAULT_USERNAME, null);
        verifyResponse(response, SUCCESS_STATUS_CODE, UNKNOWN_TYPE, DEFAULT_USERNAME);
    }

    @Test
    public void deleteNonExistingUserTest() {
        Response response = sendRequest(DELETE_METHOD, NON_EXIST_USERNAME_ENDPOINT, null);
        verifyStatusCode(response, NOT_FOUND_STATUS_CODE);
    }

    private Response sendRequest(String method, String endpoint, String body) {
        var request = given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE);

        if (body != null && !body.isEmpty() && (POST_METHOD.equals(method) || PUT_METHOD.equals(method))) {
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
        return String.format("""
        {
          "%s": %d,
          "%s": "%s",
          "%s": "%s",
          "%s": "%s",
          "%s": "%s",
          "%s": "%s",
          "%s": "%s",
          "%s": %d
        }
        """,
                ID_FIELD, id,
                USERNAME_FIELD, username,
                FIRST_NAME_FIELD, firstName,
                LAST_NAME_FIELD, lastName,
                EMAIL_FIELD, email,
                PASSWORD_FIELD, password,
                PHONE_FIELD, phone,
                USER_STATUS_FIELD, userStatus
        );
    }


    private void verifyUser(Response response, int userId, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        response.then()
                .body(ID_FIELD, equalTo(userId))
                .body(USERNAME_FIELD, equalTo(username))
                .body(FIRST_NAME_FIELD, equalTo(firstName))
                .body(LAST_NAME_FIELD, equalTo(lastName))
                .body(EMAIL_FIELD, equalTo(email))
                .body(PASSWORD_FIELD, equalTo(password))
                .body(PHONE_FIELD, equalTo(phone))
                .body(USER_STATUS_FIELD, equalTo(userStatus));
    }

    private boolean userExists(String username) {
        Response response = sendRequest(GET_METHOD, USER_ENDPOINT + "/" + username, null);
        return response.getStatusCode() == SUCCESS_STATUS_CODE;
    }

    private void createUser(String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        String requestBody = createUserJson(DEFAULT_USER_ID, username, firstName, lastName, email, password, phone, userStatus);
        sendRequest(POST_METHOD, USER_ENDPOINT, requestBody);
    }
}