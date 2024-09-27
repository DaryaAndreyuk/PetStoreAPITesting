import controller.UserController;
import io.qameta.allure.Description;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static utils.Constants.*;

public class UserSmokeTests extends BaseTest {
    UserController userController = new UserController();

    @Test
    @Description("Create User Test")
    public void createUserTest() {
        userController.addDefaultUser()
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("message", String.valueOf(DEFAULT_USER.getId()));
    }

    @Test
    @Description("Create Users With Array Of Default Users Test")
    public void createUsersWithArrayTest() {
        userController.addDefaultUsersWithArray().statusCodeIs(HttpStatus.SC_OK);
    }

    @Test
    @Description("Log Out User Test")
    public void logoutUserTest() {
        userController.logoutUser()
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("code", String.valueOf(SUCCESS_API_RESPONSE.getCode()));
    }

    @Test
    @Description("Log In User Test")
    public void loginUserTest() {
        userController.validUserLogin().statusCodeIs(HttpStatus.SC_OK);

       /* String expiresAfter = response.getHeader("X-Expires-After");
        String rateLimit = response.getHeader("X-Rate-Limit");
        Assert.assertNotNull(expiresAfter, "X-Expires-After header is missing!");
        Assert.assertNotNull(rateLimit, "X-Rate-Limit header is missing!");*/
    }

    @Test
    @Description("Delete Existing User Test")
    public void deleteExistingUserTest() {
        //get username from added user?
        userController.addDefaultUser().statusCodeIs(HttpStatus.SC_OK);

        userController.deleteUser(DEFAULT_USER.getUsername())
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("message", DEFAULT_USER.getUsername());
    }

    @Test
    @Description("Update Existing User Test")
    public void updateExistingUserTest() {
        userController.addDefaultUser()
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("message", String.valueOf(DEFAULT_USER.getId()));

        userController.updateUserWithDefaultData(DEFAULT_USER.getUsername())
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("message", String.valueOf(UPDATED_USER.getId()));

        userController.findUser(UPDATED_USER.getUsername())
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("username", UPDATED_USER.getUsername());
    }

    @Test
    @Description("Get User By Valid Username Test")
    public void getUserByValidUsernameTest() {
        userController.addDefaultUser()
                .statusCodeIs(HttpStatus.SC_OK);

        String username = DEFAULT_USER.getUsername();
        userController.findUser(username)
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("username", DEFAULT_USER.getUsername());
    }

    @Test
    @Description("Get Existing User Test")
    public void getExistingUserTest() {
        userController.addDefaultUser().statusCodeIs(HttpStatus.SC_OK);

        userController.findUser(DEFAULT_USER.getUsername())
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("username", DEFAULT_USER.getUsername());
    }
}