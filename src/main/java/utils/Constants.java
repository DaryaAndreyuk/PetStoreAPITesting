package utils;

import models.*;
import java.util.Arrays;
import java.util.List;

public final class Constants {

    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    public static final int NON_EXIST_ID_INT = 99999999;

    public static final Category DEFAULT_CATEGORY = new Category(2, "Dogs");
    public static final Category UPDATED_CATEGORY = new Category(5, "Dogs with puppies");

    public static final Pet DEFAULT_PET = new Pet(3, DEFAULT_CATEGORY, "Rex",
            List.of("http://example.com/rex.jpg"), List.of(new Tag(1, "Good dogs")), "pending");
    public static final Pet UPDATED_PET = new Pet(3, UPDATED_CATEGORY, "Max",
            List.of("http://example.com/max.jpg"), List.of(new Tag(1, "Very Good dogs")), "sold");

    public static final Order DEFAULT_ORDER = new Order(2, 3, 4, "2024-08-29T08:58:30.874Z", "placed", true);

    public static final User DEFAULT_USER = new User(5, "user_1",
            "firstName1", "lastName1", "email1@gmail.com", FileConfig.getProperty("default_user_password"), "12345678", 0);
    public static final User UPDATED_USER = new User(2, "updated_user_1",
            "updated_firstName1", "updated_lastName1", "updated_email1@gmail.com", FileConfig.getProperty("updated_user_password"), "87654321", 0);

    public static final User user1 = new User(1, "user1", "Sam", "Smith", "sam.smith@example.com", "password123", "1234567890", 1);
    public static final User user2 = new User(2, "user2", "Harry", "Styles", "harry.styles@example.com", "password456", "0987654321", 2);
    public static final List<User> DEFAULT_USERS_ARRAY = Arrays.asList(DEFAULT_USER, user1, user2);

    public static final APIResponse SUCCESS_API_RESPONSE = new APIResponse(200, "unknown", "ok");

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}