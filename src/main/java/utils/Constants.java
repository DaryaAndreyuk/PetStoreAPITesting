package utils;

public final class Constants {
    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final String CONTENT_TYPE = "application/json";
    public static final String ACCEPT = "application/json";
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int NOT_FOUND_STATUS_CODE = 404;
    public static final String UNKNOWN_TYPE = "unknown";

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
