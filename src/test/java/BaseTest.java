import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}