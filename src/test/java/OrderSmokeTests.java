import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static utils.Constants.*;

public class OrderSmokeTests extends BaseTest {

    private static final String ORDER_ENDPOINT = BASE_URL + "/store/order";

    @BeforeMethod
    public void setUp() {
        if (!orderExists(TEST_ORDER_ID)) {
            createOrder(TEST_ORDER_ID, PET_ID, QUANTITY, SHIP_DATE, PLACED_STATUS, COMPLETE);
        }
    }

    @Test
    public void createOrderTest() {
        Response response = createOrder(TEST_ORDER_ID, PET_ID, QUANTITY, SHIP_DATE, PLACED_STATUS, COMPLETE);
        verifyStatusCode(response, SUCCESS_STATUS_CODE);
        verifyOrder(response, TEST_ORDER_ID, PET_ID, QUANTITY, SHIP_DATE, PLACED_STATUS, COMPLETE);
    }

    @Test
    public void getExistingOrderTest() {
        Response response = getOrder(TEST_ORDER_ID);
        verifyStatusCode(response, SUCCESS_STATUS_CODE);
        verifyOrder(response, TEST_ORDER_ID, PET_ID, QUANTITY, SHIP_DATE, PLACED_STATUS, COMPLETE);
    }

    @Test
    public void getNonExistingOrderTest() {
        Response response = sendRequest(GET_METHOD, ORDER_ENDPOINT + "/" + NON_EXIST_ID, null);
        verifyStatusCode(response, NOT_FOUND_STATUS_CODE);
        verifyResponse(response, 1, ERROR_TYPE, ORDER_NOT_FOUND_MESSAGE);
    }

    @Test
    public void deleteOrderByIdTest() {
        int orderId = TEST_ORDER_ID;
        deleteOrder(orderId);
        Response response = given()
                .pathParam(ORDER_ID_PARAMETER, orderId)
                .when()
                .get(ORDER_ENDPOINT + "/{orderId}");
        verifyStatusCode(response, NOT_FOUND_STATUS_CODE); // Expecting 404 as the order should be deleted
    }

    private boolean orderExists(int orderId) {
        Response response = getOrder(orderId);
        return response.getStatusCode() == SUCCESS_STATUS_CODE;
    }

    private Response createOrder(int id, int petId, int quantity, String shipDate, String status, boolean complete) {
        String orderRequestBody = String.format("""
        {
          "%s": %d,
          "%s": %d,
          "%s": %d,
          "%s": "%s",
          "%s": "%s",
          "%s": %b
        }
        """,
                ID_FIELD, id,
                PET_ID_FIELD, petId,
                QUANTITY_FIELD, quantity,
                SHIP_DATE_FIELD, shipDate,
                STATUS_FIELD, status,
                COMPLETE_FIELD, complete
        );

        return sendRequest(POST_METHOD, ORDER_ENDPOINT, orderRequestBody);
    }


    private Response getOrder(int orderId) {
        return given()
                .pathParam(ORDER_ID_PARAMETER, orderId)
                .when()
                .get(ORDER_ENDPOINT + "/{orderId}")
                .then()
                .log().all()
                .extract()
                .response();
    }

    private void deleteOrder(int orderId) {
        sendRequest(DELETE_METHOD, ORDER_ENDPOINT + "/" + orderId, null)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);
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

    private void verifyOrder(Response response, int id, int petId, int quantity, String expectedShipDate, String status, boolean complete) {
        verifyOrderDate(response, expectedShipDate);
        verifyOrderDetails(response, id, petId, quantity, status, complete);
    }

    private void verifyOrderDate(Response response, String expectedShipDate) {
        String actualShipDate = response.jsonPath().getString(SHIP_DATE_FIELD);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        OffsetDateTime actualDateTime = OffsetDateTime.parse(actualShipDate, formatter);
        String formattedActualDateTime = actualDateTime.withOffsetSameInstant(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        assertEquals(formattedActualDateTime, expectedShipDate, SHIP_DATE_DOES_NOT_MATCH_THE_EXPECTED_VALUE);
    }

    private void verifyOrderDetails(Response response, int id, int petId, int quantity, String status, boolean complete) {
        response.then()
                .body(ID_FIELD, equalTo(id))
                .body(PET_ID_FIELD, equalTo(petId))
                .body(QUANTITY_FIELD, equalTo(quantity))
                .body(STATUS_FIELD, equalTo(status))
                .body(COMPLETE_FIELD, equalTo(complete));
    }
}