import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class SimpleAPITest {

    private static final String BASE_URL = "https://petstore.swagger.io/#/";

    @Test
    public void simpleGetInventoryTest() {
        String endpoint = BASE_URL + "store/getInventory";
        given().when().get(endpoint).then().log().all();
    }

}
