import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class OrderSmokeTests extends BaseTest {

    private static final String ORDER_ENDPOINT = "/store/order";
    private static int orderId;

    @Test
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

        Response response = given()
                .header("Content-Type", "application/json")
                .body(orderRequestBody)
                .when()
                .post(ORDER_ENDPOINT)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .response();

        orderId = response.jsonPath().getInt("id");
        System.out.println("Created Order ID: " + orderId);

        verifyOrder(response, 3, 41025555, 1, "2024-08-27T09:51:27.257Z", "placed", true);
    }

    @Test(dependsOnMethods = "createOrderTest")
    public void getOrderTest() {
        Response response = getOrder(orderId);
        verifyOrder(response, orderId, 41025555, 1, "2024-08-27T09:51:27.257Z", "placed", true);
    }

    @Test(dependsOnMethods = "getOrderTest")
    public void deleteOrderByIdTest() {
        deleteOrder(orderId);

        given()
                .pathParam("orderId", orderId)
                .when()
                .get(ORDER_ENDPOINT + "/{orderId}")
                .then()
                .statusCode(404); // Expecting 404 as the order should be deleted
    }

    private Response getOrder(int orderId) {
        return given()
                .pathParam("orderId", orderId)
                .when()
                .get(ORDER_ENDPOINT + "/{orderId}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    private void deleteOrder(int orderId) {
        given()
                .pathParam("orderId", orderId)
                .when()
                .delete(ORDER_ENDPOINT + "/{orderId}")
                .then()
                .statusCode(200)
                .body("message", equalTo(Integer.toString(orderId)));
    }

    private void verifyOrder(Response response, int id, int petId, int quantity, String expectedShipDate, String status, boolean complete) {
        String actualShipDate = response.jsonPath().getString("shipDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        OffsetDateTime actualDateTime = OffsetDateTime.parse(actualShipDate, formatter);
        String formattedActualDateTime = actualDateTime.withOffsetSameInstant(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        assertEquals(expectedShipDate, formattedActualDateTime);

        response.then()
                .body("id", equalTo(id))
                .body("petId", equalTo(petId))
                .body("quantity", equalTo(quantity))
                .body("status", equalTo(status))
                .body("complete", equalTo(complete));
    }
}