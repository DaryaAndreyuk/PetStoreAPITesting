import controller.UserController;
import io.restassured.response.Response;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;
import static utils.Constants.*;

public class UserSmokeTests extends BaseTest {

    private static final String USER_ENDPOINT = BASE_URL + "/user";
    private static final String NON_EXIST_USERNAME_ENDPOINT = USER_ENDPOINT + "/non_existing_username";
    private static final String NON_EXIST_USER_ENDPOINT = USER_ENDPOINT + "/user_not_existed";
    UserController userController = new UserController();

    @Test
    public void createUserAAATest() {
        Response response = userController.addDefaultUser();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void createUsersWithArrayTest() {
        User user1 = new User(1, "user1", "Sam", "Smith", "sam.smith@example.com", "password123", "1234567890", 1);
        User user2 = new User(2, "user2", "Harry", "Styles", "harry.styles@example.com", "password456", "0987654321", 2);
        List<User> usersArray = Arrays.asList(user1, user2);

        Response response = given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .body(usersArray)
                .when()
                .post(BASE_URL + "/user/createWithArray")
                .then()
                .log().ifError()
                .extract()
                .response();

        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200, "User creation with array failed!");
    }

    @Test
    public void logoutUserTest() {
        Response response = given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .when()
                .get(BASE_URL + "/user/logout")
                .then()
                .log().ifError()
                .extract()
                .response();

        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200, "Logout operation failed!");

        Assert.assertEquals(response.jsonPath().getInt("code"), 200);
        Assert.assertEquals(response.jsonPath().getString("message"), "ok");
    }

    @Test
    public void successfulLoginTest() {
        String username = "valid_username";
        String password = "valid_password";

        Response response = given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get(BASE_URL + "/user/login")
                .then()
                .log().ifError()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), 200, "Login operation failed!");

        String expiresAfter = response.getHeader("X-Expires-After");
        String rateLimit = response.getHeader("X-Rate-Limit");

        Assert.assertNotNull(expiresAfter, "X-Expires-After header is missing!");
        Assert.assertNotNull(rateLimit, "X-Rate-Limit header is missing!");

        System.out.println("Login successful. X-Expires-After: " + expiresAfter + ", X-Rate-Limit: " + rateLimit);
        response.prettyPrint();
    }

    @Test
    public void deleteExistingUserAAATest() {
        Response addUserResponse = userController.addDefaultUser();
        addUserResponse.prettyPrint();

        String username = addUserResponse.jsonPath().getString("username");

        Response deleteResponse = given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .when()
                .delete(BASE_URL + "/user/" + username)
                .then()
                .log().ifError()
                .extract()
                .response();

        Assert.assertEquals(deleteResponse.statusCode(), 200, "User deletion failed!");
        deleteResponse.prettyPrint();
    }

    @Test
    public void updateExistingUserTest() {
        Response addUserResponse = userController.addDefaultUser();
        addUserResponse.prettyPrint();

        String username = addUserResponse.jsonPath().getString("username");

        Response updateResponse = given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .body(UPDATED_USER)
                .when()
                .put(BASE_URL + "/user/" + username)
                .then()
                .log().ifError()
                .extract()
                .response();

        Assert.assertEquals(updateResponse.statusCode(), 200, "Failed to update the user!");
        updateResponse.prettyPrint();
    }

    @Test
    public void getUserByValidUsernameTest() {
        Response addUserResponse = userController.addDefaultUser();
        addUserResponse.prettyPrint();

        String username = addUserResponse.jsonPath().getString("username");

        Response getUserResponse = userController.findUser(username);

        Assert.assertEquals(getUserResponse.statusCode(), 200, "Failed to fetch the user!");
        getUserResponse.prettyPrint();

        User retrievedUser = getUserResponse.as(User.class);
        Assert.assertEquals(retrievedUser.getUsername(), username, "Username mismatch!");
    }
    @Test
    public void createUsersWithListTest() {
        User user1 = new User(1, "user1", "First1", "Last1", "user1@example.com", "password1", "1234567890", 0);
        User user2 = new User(2, "user2", "First2", "Last2", "user2@example.com", "password2", "0987654321", 1);
        List<User> users = Arrays.asList(user1, user2);

        Response response = given()
                .header(ACCEPT_HEADER, APP_JSON_TYPE)
                .header(CONTENT_TYPE_HEADER, APP_JSON_TYPE)
                .body(users) // Pass the list of users as the body
                .when()
                .post(BASE_URL + "/user/createWithList")
                .then()
                .log().ifError()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), 200, "Failed to create users from list!");
        response.prettyPrint();

        String responseMessage = response.jsonPath().getString("message");
        Assert.assertNotNull(responseMessage, "Response message should not be null!");
    }

    @Test
    public void getExistingUserAAATest() {
        Response addResponse = userController.addDefaultUser();

        Response getResponse = userController.findUser(DEFAULT_USER.getUsername());
        User getUser = getResponse.as(User.class);
        getResponse.prettyPrint();
        Assert.assertEquals(getResponse.statusCode(), 200);
        Assert.assertEquals(DEFAULT_USER, getUser);
    }
}