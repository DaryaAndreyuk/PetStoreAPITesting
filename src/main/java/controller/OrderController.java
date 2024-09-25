package controller;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import static utils.Constants.*;

public class OrderController {

    private static final String ORDER_ENDPOINT = BASE_URL + "/store/order";

    RequestSpecification requestSpecification = given();

    public OrderController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification =
                given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .baseUri(ORDER_ENDPOINT)
                .filter(new AllureRestAssured());
    }

    @Step("Create default order")
    public Response addDefaultOrder() {
        return requestSpecification
                .body(DEFAULT_ORDER)
                .when()
                .post()
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    @Step("Get default order")
    public Response findOrder(int orderId) {
        return requestSpecification
                .when()
                .get("/" + orderId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    @Step("Delete order by ID {orderId}")
    public Response deleteOrder(int orderId) {
        return requestSpecification
                .when()
                .delete("/" + orderId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
