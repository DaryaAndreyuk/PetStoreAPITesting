import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderSmokeTests extends BaseTest {

    @Test(priority = 1)
    void createOrderTest() {
        String orderRequestBody = """
            {
              "id": 3,
              "petId": 41025555,
              "quantity": 1,
              "shipDate": "2024-08-27T09:51:27.257Z",
              "status": "placed",
              "complete": true
            }
            """;

        Response response = given().
                header("Content-Type", "application/json").
                body(orderRequestBody).
                when().
                post("/store/order").
                then().
                log().ifError().
                statusCode(200).
                extract().
                response();

        System.out.println("Response Body: " + response.getBody().asString());

        // Verify response body content
        response.then().
                body("id", equalTo(3)).
                body("petId", equalTo(41025555)).
                body("quantity", equalTo(1)).
                body("status", equalTo("placed")).
                body("complete", equalTo(true));
    }

    @Test(priority = 1)
    void getOrderByIdTest() {
        int orderId = 3; // Change this ID as needed for different tests

        Response response = given().
                pathParam("orderId", orderId).
                when().
                get("/store/order/{orderId}").
                then().
                log().ifError().
                statusCode(200).
                extract().
                response();

        System.out.println("Response Body: " + response.getBody().asString());

        // Verify response body content
        response.then().
                body("id", equalTo(orderId)).
                body("petId", equalTo(41025555)).
                body("quantity", equalTo(1)).
                body("status", equalTo("placed")).
                body("complete", equalTo(true));

        // Verify shipDate using date parsing
        String expectedShipDate = "2024-08-27T09:51:27.257+0000";
        String actualShipDate = response.jsonPath().getString("shipDate");
        try {
            ZonedDateTime expectedDate = ZonedDateTime.parse(expectedShipDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
            ZonedDateTime actualDate = ZonedDateTime.parse(actualShipDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
            if (!expectedDate.equals(actualDate)) {
                throw new AssertionError("Expected shipDate: " + expectedDate + " but got: " + actualDate);
            }
        } catch (DateTimeParseException e) {
            throw new AssertionError("Date parsing failed: " + e.getMessage());
        }
    }

    @Test() // Ensure that this test runs after the creation and retrieval tests
    void deleteOrderByIdTest() {
        int orderId = 3; // Change this ID as needed for different tests

        Response response = given().
                pathParam("orderId", orderId).
                when().
                delete("/store/order/{orderId}").
                then().
                log().ifError().
                statusCode(200).
                extract().
                response();

        System.out.println("Response Body: " + response.getBody().asString());

        // Verify response body content
        response.then().
                body("code", equalTo(200)).
                body("type", equalTo("unknown")).
                body("message", equalTo(String.valueOf(orderId)));
    }
}
