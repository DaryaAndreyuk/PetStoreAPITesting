package utils;

import models.*;

import java.util.List;

public final class Constants {
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int NOT_FOUND_STATUS_CODE = 404;
    public static final int DEFAULT_USER_ID = 2;
    public static final int DEFAULT_USER_STATUS = 0;

    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final String APP_JSON_TYPE = "application/json";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String ACCEPT_HEADER = "Accept";
    public static final String UNKNOWN_TYPE = "unknown";
    public static final String PLACED_STATUS = "placed";
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";
    public static final String ERROR_TYPE = "error";

    public static final String DEFAULT_USERNAME = "user_1";
    public static final String DEFAULT_FIRST_NAME = "firstName1";
    public static final String DEFAULT_LAST_NAME = "lastName1";
    public static final String DEFAULT_EMAIL = "email1@gmail.com";
    public static final String DEFAULT_PASSWORD = "qwerty";
    public static final String DEFAULT_PHONE = "12345678";

    public static final String PET_ID_FIELD = "petId";
    public static final String QUANTITY_FIELD = "quantity";
    public static final String SHIP_DATE_FIELD = "shipDate";
    public static final String STATUS_FIELD = "status";
    public static final String COMPLETE_FIELD = "complete";
    public static final String CODE_FIELD = "code";
    public static final String MESSAGE_FIELD = "message";

    public static final String ID_FIELD = "id";
    public static final String USERNAME_FIELD = "username";
    public static final String FIRST_NAME_FIELD = "firstName";
    public static final String LAST_NAME_FIELD = "lastName";
    public static final String EMAIL_FIELD = "email";
    public static final String PASSWORD_FIELD = "password";
    public static final String PHONE_FIELD = "phone";
    public static final String USER_STATUS_FIELD = "userStatus";

    public static final String UPDATED_USERNAME = "updated_user_1";
    public static final String UPDATED_FIRST_NAME = "updated_firstName1";
    public static final String UPDATED_LASTNAME = "updated_lastName1";
    public static final String UPDATED_EMAIL = "updated_email1@gmail.com";
    public static final String UPDATED_PASSWORD = "updated_qwerty";
    public static final String UPDATED_PHONE = "87654321";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    public static final int PET_ID = 3;
    public static final int TEST_PET_ID = 41025555;
    public static final int PET_CATEGORY_ID = 2;
    public static final int UPDATED_PET_CATEGORY_ID = 1;
    public static final String PET_NAME = "Rex";
    public static final String UPDATED_PET_NAME = "Max";
    public static final String PET_PHOTO_URL = "http://example.com/rex.jpg";
    public static final String UPDATED_PET_PHOTO_URL = "http://example.com/max.jpg";
    public static final String AVAILABLE_STATUS = "available";
    public static final String UPDATED_PET_STATUS = "sold";
    public static final String PET_NOT_FOUND_MESSAGE = "Pet not found";

    public static final Category DEFAULT_CATEGORY = new Category(2, "Dogs");

    public static final Pet DEFAULT_PET = new Pet(3, DEFAULT_CATEGORY, "Rex",
            List.of("http://example.com/rex.jpg"), List.of(new Tag(1, "Good dogs")),
            "available");
    public static final Order DEFAULT_ORDER = new Order(2, 3, 4, "2024-08-29T08:58:30.874Z", "placed", true);

    public static final User DEFAULT_USER = new User(2, "user_1",
            "firstName1", "lastName1", "email1@gmail.com", "qwerty", "12345678", 0);

    public static final String PET_CATEGORY_ID_FIELD = "category.id";
    public static final String PET_CATEGORY_NAME_FIELD = "category.name";
    public static final String PET_NAME_FIELD = "name";
    public static final String PET_PHOTO_URL_FIELD = "photoUrls[0]";
    public static final String PET_TAG_ID_FIELD = "tags[0].id";
    public static final String PET_TAG_NAME_FIELD = "tags[0].name";

    public static final String NON_EXIST_ID = "99999999";
    public static final String DOGS_CATEGORY_NAME = "Dogs";
    public static final int FRIENDLY_TAG_ID = 1;
    public static final String FRIENDLY_TAG_NAME = "friendly";

    public static final int TEST_ORDER_ID = 2;
    public static final int QUANTITY = 4;
    public static final boolean COMPLETE = true;
    public static final String ORDER_ID_PARAMETER = "orderId";
    public static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    public static final String SHIP_DATE = "2024-08-29T08:58:30.874Z";
    public static final String SHIP_DATE_DOES_NOT_MATCH_THE_EXPECTED_VALUE = "Ship date does not match the expected value.";

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
