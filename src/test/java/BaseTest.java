import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.equalTo;
import static utils.Constants.BASE_URL;

public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        logger.info("Base URI set to {}", BASE_URL);
    }

    @BeforeMethod
    public void beforeEachTest() {
        logger.info("Starting a new test");
    }

    public void verifyStatusCode(Response response, int statusCode) {
        logger.info("Verifying status code: expected {}, actual {}", statusCode, response.getStatusCode());
        response.then().statusCode(statusCode);
    }

    public void verifyResponse(Response response, int code, String type, String message) {
        logger.info("Verifying response: expected code {}, type {}, message {}", code, type, message);
        response.then()
                .body("code", equalTo(code))
                .body("type", equalTo(type))
                .body("message", equalTo(message));
    }
}
