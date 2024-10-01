import io.qameta.allure.Description;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;

import static utils.Constants.BASE_URL;

@Slf4j
public class BaseTest {

    @BeforeClass
    public void setupBaseURI() {
        RestAssured.baseURI = BASE_URL;
        log.info("Base URI set to {}", BASE_URL);
    }

    @BeforeMethod
    public void logBeforeEachTest() {
        log.info("Starting a new test");
    }
}