import controller.UserController;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.APIResponse;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.Constants.*;

public class UserSmokeTests extends BaseTest {
    UserController userController = new UserController();

    @Test
    @Description("Create User Test")
    public void createUserTest() {
        Response response = userController.addDefaultUser();
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    @Description("Create Users With Array Of Default Users Test")
    public void createUsersWithArrayTest() {
        Response response = userController.addDefaultUsersWithArray();
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    @Description("Log Out User Test")
    public void logoutUserTest() {
        Response response = userController.logoutUser();
        APIResponse actualResponse = response.as(APIResponse.class);

        Assert.assertEquals(actualResponse.getCode(), SUCCESS_API_RESPONSE.getCode());
        Assert.assertEquals(actualResponse, SUCCESS_API_RESPONSE);
    }

    @Test
    @Description("Log In User Test")
    public void loginUserTest() {
        Response response = userController.validUserLogin();
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
        String expiresAfter = response.getHeader("X-Expires-After");
        String rateLimit = response.getHeader("X-Rate-Limit");
        Assert.assertNotNull(expiresAfter, "X-Expires-After header is missing!");
        Assert.assertNotNull(rateLimit, "X-Rate-Limit header is missing!");
        System.out.println("Login successful. X-Expires-After: " + expiresAfter + ", X-Rate-Limit: " + rateLimit);
    }

    @Test
    @Description("Delete Existing User Test")
    public void deleteExistingUserTest() {
        Response addUserResponse = userController.addDefaultUser();
        Response deleteResponse = userController.deleteUser(DEFAULT_USER.getUsername());

        APIResponse actualResponse = deleteResponse.as(APIResponse.class);
        Assert.assertEquals(actualResponse.getCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(actualResponse.getMessage(), DEFAULT_USER.getUsername());
    }

    @Test
    @Description("Update Existing User Test")
    public void updateExistingUserTest() {
        Response addUserResponse = userController.addDefaultUser();
        APIResponse apiResponse = addUserResponse.as(APIResponse.class);
        Integer addedUserId = Integer.parseInt(apiResponse.getMessage());
        Assert.assertEquals(addedUserId, DEFAULT_USER.getId());

        Response updateResponse = userController.updateUserWithDefaultData(DEFAULT_USER.getUsername());
        APIResponse actualResponse = updateResponse.as(APIResponse.class);
        Integer updatedUserId = Integer.parseInt(actualResponse.getMessage());
        Assert.assertEquals(updatedUserId, UPDATED_USER.getId());

        Response getUserResponse = userController.findUser(UPDATED_USER.getUsername());
        User actualUser = getUserResponse.as(User.class);
        Assert.assertEquals(actualUser, UPDATED_USER);
    }

    @Test
    @Description("Get User By Valid Username Test")
    public void getUserByValidUsernameTest() {
        Response addUserResponse = userController.addDefaultUser();
        APIResponse actualResponse = addUserResponse.as(APIResponse.class);

        Assert.assertEquals(actualResponse.getCode(), SUCCESS_STATUS_CODE);

        String username = DEFAULT_USER.getUsername();
        Response getUserResponse = userController.findUser(username);

        Assert.assertEquals(getUserResponse.statusCode(), SUCCESS_STATUS_CODE);

        User retrievedUser = getUserResponse.as(User.class);
        Assert.assertEquals(retrievedUser.getUsername(), username);
        Assert.assertEquals(retrievedUser, DEFAULT_USER);
    }

    @Test
    @Description("Get Existing User Test")
    public void getExistingUserTest() {
        Response addResponse = userController.addDefaultUser();
        Response getResponse = userController.findUser(DEFAULT_USER.getUsername());
        User getUser = getResponse.as(User.class);
        Assert.assertEquals(getResponse.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(DEFAULT_USER, getUser);
    }
}