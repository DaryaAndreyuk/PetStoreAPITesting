package controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static utils.Constants.*;
import static utils.Constants.APP_JSON_TYPE;

public class OrderController {

    private static final String ORDER_ENDPOINT = BASE_URL + "/store/order";

    RequestSpecification requestSpecification = given();

    public OrderController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.baseUri(ORDER_ENDPOINT);
    }

    public Response addDefaultOrder() {
        return given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .body(DEFAULT_ORDER)
                .request(Method.POST, ORDER_ENDPOINT)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

}
