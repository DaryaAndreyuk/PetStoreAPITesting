package utils;

import models.*;

import java.util.List;

public final class Constants {
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int NOT_FOUND_STATUS_CODE = 404;

    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final String APP_JSON_TYPE = "application/json";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String ACCEPT_HEADER = "Accept";

    public static final String NON_EXIST_ID = "99999999";
    public static final int NON_EXIST_ID_INT = 99999999;

    public static final Category DEFAULT_CATEGORY = new Category(2, "Dogs");
    public static final Category UPDATED_CATEGORY = new Category(5, "Dogs with puppies");

    public static final Pet DEFAULT_PET = new Pet(3, DEFAULT_CATEGORY, "Rex",
            List.of("http://example.com/rex.jpg"), List.of(new Tag(1, "Good dogs")), "pending");
    public static final Pet UPDATED_PET = new Pet(3, UPDATED_CATEGORY, "Max",
            List.of("http://example.com/max.jpg"), List.of(new Tag(1, "Very Good dogs")), "sold");

    public static final Order DEFAULT_ORDER = new Order(2, 3, 4, "2024-08-29T08:58:30.874Z", "placed", true);

    public static final User DEFAULT_USER = new User(2, "user_1",
            "firstName1", "lastName1", "email1@gmail.com", "qwerty", "12345678", 0);
    public static final User UPDATED_USER = new User(2, "updated_user_1",
            "updated_firstName1", "updated_lastName1", "updated_email1@gmail.com", "updated_qwerty", "87654321", 0);

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}