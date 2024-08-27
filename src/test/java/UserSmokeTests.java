import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserSmokeTests extends BaseTest {

    @Test
    void createUserTest() {
        String endpoint = "https://petstore.swagger.io/v2/user";
        String body = """
                {
                  "id": 0,
                  "username": "user_1",
                  "firstName": "firstName1",
                  "lastName": "lastName1",
                  "email": "email1@gmail.com",
                  "password": "qwerty",
                  "phone": "12345678",
                  "userStatus": 0
                }
                """;
        var response = given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(body).
                when().
                post(endpoint).
                then();
        response.log().body();
        response.statusCode(200);
    }

    @Test
    void getUserWithPathParamTest() {
        String username = "user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;
        given().
                when().
                get(endpoint).
                then().
                log().
                all();
    }

    @Test
    void simplePutTest() {
        String username = "user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;
        String body = """
                {
                  "id": 0,
                  "username": "user_2",
                  "firstName": "firstName2",
                  "lastName": "lastName2",
                  "email": "email2@gmail.com",
                  "password": "sfdsvdsc",
                  "phone": "987654321",
                  "userStatus": 0
                }
                """;
        var response = given().
                header("accept", "application/json").
                header("Content-Type", "application/json").
                body(body).
                when().
                put(endpoint).
                then();
        response.log().body();
        response.statusCode(200);
    }

    @Test
    void simpleDeleteTest() {
        String username = "user_1";
        String endpoint = "https://petstore.swagger.io/v2/user/" + username;
        var response = given().
                when().
                delete(endpoint).
                then();
        response.log().body();
        response.statusCode(200);
    }

}
