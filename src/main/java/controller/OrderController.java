package controller;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static utils.Constants.BASE_URL;
import static utils.Constants.DEFAULT_ORDER;

public class OrderController {

    private static final String ORDER_ENDPOINT = BASE_URL + "/store/order";

    RequestSpecification requestSpecification;

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
    public HttpResponse addDefaultOrder() {
        this.requestSpecification.body(DEFAULT_ORDER);
        return new HttpResponse(given(requestSpecification).post().then());
    }

    @Step("Get default order")
    public HttpResponse findOrder(int orderId) {
        return new HttpResponse(given(requestSpecification).get("/" + orderId).then());
    }

    @Step("Delete order by ID {orderId}")
    public HttpResponse deleteOrder(int orderId) {
        return new HttpResponse(given(requestSpecification).delete("/" + orderId).then());
    }
}