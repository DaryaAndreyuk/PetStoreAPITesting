package models;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Setter
@Getter
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