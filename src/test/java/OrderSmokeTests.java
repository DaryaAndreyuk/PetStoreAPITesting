import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Constants;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static utils.Constants.*;

public class OrderSmokeTests extends BaseTest {

    private static final String ORDER_ENDPOINT = "/store/order";
    private static int orderId;

    @BeforeMethod
    public void setupOrder() {
        createOrder(2, 3, 4, "2024-08-29T08:58:30.874Z", "placed", true);
    }

    @Test
    void createOrderTest() {
        Response response = createOrder(2, 3, 4, "2024-08-29T08:58:30.874Z", "placed", true);
        verifyStatusCode(response, SUCCESS_STATUS_CODE);
        verifyOrder(response, 2, 3, 4, "2024-08-29T08:58:30.874Z", "placed", true);
    }

    @Test
    public void getExistingOrderTest() {
        Response response = getOrder(2);
        verifyStatusCode(response, SUCCESS_STATUS_CODE);
        verifyOrder(response, 2, 3, 4, "2024-08-29T08:58:30.874Z", "placed", true);
    }

    @Test
    public void getNonExistingOrderTest() {
        Response response = sendRequest("GET", ORDER_ENDPOINT + "/" + 1, null);
        verifyStatusCode(response, NOT_FOUND_STATUS_CODE);
        verifyResponse(response, 1, "error", "Order not found");
    }

    private Response sendRequest(String method, String endpoint, String body) {
        var request = given()
                .header("accept", ACCEPT);

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

    @Test
    public void deleteOrderByIdTest() {
        int orderId = 2;
        deleteOrder(orderId);
        given()
                .pathParam("orderId", orderId)
                .when()
                .get(ORDER_ENDPOINT + "/{orderId}")
                .then()
                .statusCode(NOT_FOUND_STATUS_CODE); // Expecting 404 as the order should be deleted
    }

    private void deleteOrder(int orderId) {
        given()
                .pathParam("orderId", orderId)
                .when()
                .delete(ORDER_ENDPOINT + "/{orderId}")
                .then()
                .statusCode(SUCCESS_STATUS_CODE); // Expecting 200 or 204 as the order should be deleted
    }


    private Response createOrder(int id, int petId, int quantity, String shipDate, String status, boolean complete) {
        String orderRequestBody = String.format("""
            {
              "id": %d,
              "petId": %d,
              "quantity": %d,
              "shipDate": "%s",
              "status": "%s",
              "complete": %s
            }
            """, id, petId, quantity, shipDate, status, complete);

        Response response = given()
                .header("Content-Type", Constants.CONTENT_TYPE)
                .body(orderRequestBody)
                .when()
                .post(ORDER_ENDPOINT)
                .then()
                .log().ifError()
                .statusCode(SUCCESS_STATUS_CODE)
                .extract()
                .response();

        int orderId = response.jsonPath().getInt("id");
        System.out.println("Created Order ID: " + orderId);
        return response;
    }


    private Response getOrder(int orderId) {
        return given()
                .pathParam("orderId", orderId)
                .when()
                .get(ORDER_ENDPOINT + "/{orderId}")
                .then()
                .log().all()
                .statusCode(SUCCESS_STATUS_CODE)
                .extract()
                .response();
    }

    private void verifyOrder(Response response, int id, int petId, int quantity, String expectedShipDate, String status, boolean complete) {
        String actualShipDate = response.jsonPath().getString("shipDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        OffsetDateTime actualDateTime = OffsetDateTime.parse(actualShipDate, formatter);
        String formattedActualDateTime = actualDateTime.withOffsetSameInstant(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        assertEquals(formattedActualDateTime, expectedShipDate);

        response.then()
                .body("id", equalTo(id))
                .body("petId", equalTo(petId))
                .body("quantity", equalTo(quantity))
                .body("status", equalTo(status))
                .body("complete", equalTo(complete));
    }
}