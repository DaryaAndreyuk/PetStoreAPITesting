import controller.UserController;
import io.restassured.response.Response;
import models.APIResponse;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.Constants.*;

public class UserSmokeTests extends BaseTest {
    UserController userController = new UserController();

    @Test
    public void createUserTest() {
        Response response = userController.addDefaultUser();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void createUsersWithArrayTest() {
        Response response = userController.addDefaultUsersWithArray();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void logoutUserTest() {
        Response response = userController.logoutUser();
        APIResponse actualResponse = response.as(APIResponse.class);
        response.prettyPrint();
        Assert.assertEquals(actualResponse.getCode(), SUCCESS_API_RESPONSE.getCode());
        Assert.assertEquals(actualResponse, SUCCESS_API_RESPONSE);
    }

    @Test
    public void loginUserTest() {
        Response response = userController.loginUser();
        Assert.assertEquals(response.statusCode(), 200, "Login operation failed!");
        String expiresAfter = response.getHeader("X-Expires-After");
        String rateLimit = response.getHeader("X-Rate-Limit");
        Assert.assertNotNull(expiresAfter, "X-Expires-After header is missing!");
        Assert.assertNotNull(rateLimit, "X-Rate-Limit header is missing!");
        System.out.println("Login successful. X-Expires-After: " + expiresAfter + ", X-Rate-Limit: " + rateLimit);
        response.prettyPrint();
    }

    @Test
    public void deleteExistingUserTest() {
        Response addUserResponse = userController.addDefaultUser();
        addUserResponse.prettyPrint();

        String username = DEFAULT_USER.getUsername();
        Response deleteResponse = userController.deleteUser(username);
        deleteResponse.prettyPrint();

        APIResponse actualResponse = deleteResponse.as(APIResponse.class);
        Assert.assertEquals(actualResponse.getCode(), 200);
        Assert.assertEquals(actualResponse.getMessage(), DEFAULT_USER.getUsername());
    }

    @Test
    public void updateExistingUserTest() {
        Response addUserResponse = userController.addDefaultUser();
        addUserResponse.prettyPrint();
        APIResponse actualResponse = addUserResponse.as(APIResponse.class);
        Integer addedUserId = Integer.parseInt(actualResponse.getMessage());
        Assert.assertEquals(addedUserId, DEFAULT_USER.getId());

        Response updateResponse = userController.updateUser(DEFAULT_USER.getUsername());
        updateResponse.prettyPrint();
        APIResponse actualResponse1 = updateResponse.as(APIResponse.class);
        Integer updatedUserId = Integer.parseInt(actualResponse1.getMessage());
        Assert.assertEquals(updatedUserId, UPDATED_USER.getId());

        Response getUserResponse = userController.findUser(UPDATED_USER.getUsername());
        getUserResponse.prettyPrint();
        User actualUser = getUserResponse.as(User.class);
        Assert.assertEquals(actualUser, UPDATED_USER);
    }

    @Test
    public void getUserByValidUsernameTest() {
        Response addUserResponse = userController.addDefaultUser();
        addUserResponse.prettyPrint();
        APIResponse actualResponse = addUserResponse.as(APIResponse.class);
        Assert.assertEquals(actualResponse.getCode(), 200);

        String username = DEFAULT_USER.getUsername();

        Response getUserResponse = userController.findUser(username);

        Assert.assertEquals(getUserResponse.statusCode(), 200, "Failed to fetch the user!");
        getUserResponse.prettyPrint();

        User retrievedUser = getUserResponse.as(User.class);
        Assert.assertEquals(retrievedUser.getUsername(), username, "Username mismatch!");
        Assert.assertEquals(retrievedUser, DEFAULT_USER);
    }

    @Test
    public void getExistingUserTest() {
        Response addResponse = userController.addDefaultUser();
        Response getResponse = userController.findUser(DEFAULT_USER.getUsername());
        User getUser = getResponse.as(User.class);
        getResponse.prettyPrint();
        Assert.assertEquals(getResponse.statusCode(), 200);
        Assert.assertEquals(DEFAULT_USER, getUser);
    }
}