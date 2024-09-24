package models;

import java.util.Objects;

public class APIResponse {

    private Integer code;
    private String type;
    private String message;

    public APIResponse() {
    }

    public APIResponse(Integer code, String type, String message) {
        super();
        this.code = code;
        this.type = type;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getType(), getMessage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIResponse that = (APIResponse) o;
        return Objects.equals(getCode(), that.getCode()) && Objects.equals(getType(), that.getType()) && Objects.equals(getMessage(), that.getMessage());
    }
}