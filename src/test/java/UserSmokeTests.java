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
        userController.addDefaultUsersWithArray()
                .statusCodeIs(HttpStatus.SC_OK);
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
    public void validUserLoginTest() {
        userController.validUserLogin().statusCodeIs(HttpStatus.SC_OK);
    }

    @Test
    @Description("Delete Existing User Test")
    public void deleteExistingUserTest() {
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
                .jsonValueIs("id", String.valueOf(UPDATED_USER.getId()))
                .jsonValueIs("username", UPDATED_USER.getUsername())
                .jsonValueIs("firstName", UPDATED_USER.getFirstName())
                .jsonValueIs("lastName", UPDATED_USER.getLastName())
                .jsonValueIs("email", UPDATED_USER.getEmail())
                .jsonValueIs("password", UPDATED_USER.getPassword())
                .jsonValueIs("phone", UPDATED_USER.getPhone())
                .jsonValueIs("userStatus", String.valueOf(UPDATED_USER.getUserStatus()));
    }

    @Test
    @Description("Get User By Valid Username Test")
    public void getUserByValidUsernameTest() {
        userController.addDefaultUser()
                .statusCodeIs(HttpStatus.SC_OK);

        String username = DEFAULT_USER.getUsername();

        userController.findUser(username)
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(DEFAULT_USER.getId()))
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                .jsonValueIs("firstName", DEFAULT_USER.getFirstName())
                .jsonValueIs("lastName", DEFAULT_USER.getLastName())
                .jsonValueIs("email", DEFAULT_USER.getEmail())
                .jsonValueIs("password", DEFAULT_USER.getPassword())
                .jsonValueIs("phone", DEFAULT_USER.getPhone())
                .jsonValueIs("userStatus", String.valueOf(DEFAULT_USER.getUserStatus()));
    }

    @Test
    @Description("Get Existing User Test")
    public void getExistingUserTest() {
        userController.addDefaultUser()
                .statusCodeIs(HttpStatus.SC_OK);

        userController.findUser(DEFAULT_USER.getUsername())
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(DEFAULT_USER.getId()))
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                .jsonValueIs("firstName", DEFAULT_USER.getFirstName())
                .jsonValueIs("lastName", DEFAULT_USER.getLastName())
                .jsonValueIs("email", DEFAULT_USER.getEmail())
                .jsonValueIs("password", DEFAULT_USER.getPassword())
                .jsonValueIs("phone", DEFAULT_USER.getPhone())
                .jsonValueIs("userStatus", String.valueOf(DEFAULT_USER.getUserStatus()));
    }
}