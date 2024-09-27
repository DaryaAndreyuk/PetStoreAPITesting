package controller;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import org.assertj.core.api.Assertions;

@Getter
public class HttpResponse {
    private final ValidatableResponse response;

    public HttpResponse(ValidatableResponse response) {
        this.response = response;
    }

    @Step("Check status code")
    public HttpResponse statusCodeIs(int status) {
        this.response.statusCode(status);
        return this;
    }

    @Step("Check json value")
    public HttpResponse jsonValueIs(String path, String expectedValue) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue)
                .as("Actual value '%s' is not equals to expected '%s' for the path '%s' and response: \n%s",
                        actualValue, expectedValue, path, this.response.extract()
                                .response().andReturn().asPrettyString()).isEqualTo(expectedValue);
        return this;
    }

    @Step("Check json value is not null")
    public HttpResponse jsonValueIsNotNull(String path) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue)
                .as("Expected non-null value for path '%s', but got null in response: \n%s", path, this.response.extract().response().asPrettyString())
                .isNotNull();
        return this;
    }

    @Step("Check json value is null")
    public HttpResponse jsonValueIsNull(String path) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue)
                .as("Expected null value for path '%s', but got '%s' in response: \n%s", path, actualValue, this.response.extract().response().asPrettyString())
                .isNull();
        return this;
    }


    public String getJsonValue(String path) {
        String value = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(value).isNotNull();
        return value;
    }

    @Override
    public String toString() {
        return String.format("Status code: %s and response: \n%s", response.extract().response().statusCode(), response.extract().response().asPrettyString());
    }
}