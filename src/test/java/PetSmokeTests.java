import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utils.Constants.*;

public class PetSmokeTests extends BaseTest {

    private static final String PET_ENDPOINT = BASE_URL + "/pet";
    private static final String NON_EXIST_PET_ENDPOINT = PET_ENDPOINT + "/" + NON_EXIST_ID;

    @BeforeMethod
    public void setUp() {
        if (!petExists(TEST_PET_ID)) {
            createPet(TEST_PET_ID, PET_NAME, PET_PHOTO_URL, AVAILABLE_STATUS, PET_CATEGORY_ID);
        }
    }

    @Test
    public void createPetTest() {
        String requestBody = createPetJson(TEST_PET_ID, PET_NAME, PET_PHOTO_URL, AVAILABLE_STATUS, PET_CATEGORY_ID);
        Response response = sendRequest(POST_METHOD, PET_ENDPOINT, requestBody);
        verifyPetResponse(response, TEST_PET_ID, PET_NAME, PET_PHOTO_URL, AVAILABLE_STATUS, PET_CATEGORY_ID);
    }

    @Test
    public void getExistingPetTest() {
        Response response = sendRequest(GET_METHOD, PET_ENDPOINT + "/" + TEST_PET_ID, null);
        verifyStatusCode(response, SUCCESS_STATUS_CODE);
        verifyPetResponse(response, TEST_PET_ID, PET_NAME, PET_PHOTO_URL, AVAILABLE_STATUS, PET_CATEGORY_ID);
    }

    @Test
    public void getNonExistingPetTest() {
        Response response = sendRequest(GET_METHOD, NON_EXIST_PET_ENDPOINT, null);
        response.then().statusCode(NOT_FOUND_STATUS_CODE);
        response.then()
                .body(CODE_FIELD, equalTo(1))
                .body(MESSAGE_FIELD, equalTo(PET_NOT_FOUND_MESSAGE));
    }

    @Test
    public void updatePetTest() {
        String requestBody = createPetJson(TEST_PET_ID, UPDATED_PET_NAME, UPDATED_PET_PHOTO_URL, UPDATED_PET_STATUS, UPDATED_PET_CATEGORY_ID);
        Response response = sendRequest(PUT_METHOD, PET_ENDPOINT, requestBody);
        response.then().statusCode(SUCCESS_STATUS_CODE);
        verifyPetResponse(response, TEST_PET_ID, UPDATED_PET_NAME, UPDATED_PET_PHOTO_URL, UPDATED_PET_STATUS, UPDATED_PET_CATEGORY_ID);
    }

    @Test
    public void deleteExistingPetTest() {
        Response response = sendRequest(DELETE_METHOD, PET_ENDPOINT + "/" + TEST_PET_ID, null);
        verifyResponse(response, SUCCESS_STATUS_CODE, UNKNOWN_TYPE, Integer.toString(TEST_PET_ID));
    }

    @Test
    public void deleteNonExistingPetTest() {
        Response response = sendRequest(DELETE_METHOD, NON_EXIST_PET_ENDPOINT, null);
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
    private String createPetJson(int id, String name, String photoUrl, String status, int categoryId) {
        return """
                {
                  "id": %d,
                  "category": {
                    "id": %d,
                    "name": "Dogs"
                  },
                  "name": "%s",
                  "photoUrls": [
                    "%s"
                  ],
                  "tags": [
                    {
                      "id": 1,
                      "name": "friendly"
                    }
                  ],
                  "status": "%s"
                }
                """.formatted(id, categoryId, name, photoUrl, status);
    }

    private void verifyPetResponse(Response response, int petId, String name, String photoUrl, String status, int categoryId) {
        response.then()
                .body(ID_FIELD, equalTo(petId))
                .body(PET_CATEGORY_ID_FIELD, equalTo(categoryId))
                .body(PET_CATEGORY_NAME_FIELD, equalTo(DOGS_CATEGORY_NAME))
                .body(PET_NAME_FIELD, equalTo(name))
                .body(PET_PHOTO_URL_FIELD, equalTo(photoUrl))
                .body(PET_TAG_ID_FIELD, equalTo(FRIENDLY_TAG_ID))
                .body(PET_TAG_NAME_FIELD, equalTo(FRIENDLY_TAG_NAME))
                .body(STATUS_FIELD, equalTo(status));
    }

    private boolean petExists(int petId) {
        Response response = sendRequest(GET_METHOD, PET_ENDPOINT + "/" + petId, null);
        return response.getStatusCode() == SUCCESS_STATUS_CODE;
    }

    private void createPet(int id, String name, String photoUrl, String status, int categoryId) {
        String requestBody = createPetJson(id, name, photoUrl, status, categoryId);
        sendRequest(POST_METHOD, PET_ENDPOINT, requestBody);
    }
}