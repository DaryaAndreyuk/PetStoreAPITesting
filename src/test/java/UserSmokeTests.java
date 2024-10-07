import controller.UserController;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static utils.Constants.*;

public class UserSmokeTests extends BaseTest {
    UserController userController = new UserController();

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Create a valid user")
    @Feature("API: Create a valid user")
    @Description("Create User Test")
    public void createUserTest() {
        userController.addDefaultUser()
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("message", String.valueOf(DEFAULT_USER.getId()));
    }

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Create multiple users with an array of default users")
    @Feature("API: Create multiple users with an array of default users")
    @Description("Create Users With Array Of Default Users Test")
    public void createUsersWithArrayTest() {
        userController.addDefaultUsersWithArray()
                .statusCodeIs(HttpStatus.SC_OK);
    }

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Log Out User")
    @Feature("API: Log Out User")
    @Description("Log Out User Test")
    public void logoutUserTest() {
        userController.logoutUser()
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("code", String.valueOf(SUCCESS_API_RESPONSE.getCode()));
    }

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Log In Existing User")
    @Feature("API: Log In Existing User")
    @Description("Log In Existing User Test")
    public void validUserLoginTest() {
        userController.validUserLogin().statusCodeIs(HttpStatus.SC_OK);
    }

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Delete Existing User")
    @Feature("API: Delete Existing User")
    @Description("Delete Existing User Test")
    public void deleteExistingUserTest() {
        userController.addDefaultUser().statusCodeIs(HttpStatus.SC_OK);

        userController.deleteUser(DEFAULT_USER.getUsername())
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("message", DEFAULT_USER.getUsername());
    }

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Update Existing User")
    @Feature("API: Update Existing User")
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
    @Owner("Darya Andreyuk")
    @Epic("Get User By Valid Username")
    @Feature("API: Get User By Valid Username")
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
    @Owner("Darya Andreyuk")
    @Epic("Get Existing User")
    @Feature("API: Get Existing User")
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