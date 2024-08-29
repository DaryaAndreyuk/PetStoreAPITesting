import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.equalTo;
import static utils.Constants.BASE_URL;

public class BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    public void verifyStatusCode(Response response, int statusCode) {
        response.then().statusCode(statusCode);
    }

    public void verifyResponse(Response response, int code, String type, String message) {
        response.then()
                .body("code", equalTo(code))
                .body("type", equalTo(type))
                .body("message", equalTo(message));
    }
}
