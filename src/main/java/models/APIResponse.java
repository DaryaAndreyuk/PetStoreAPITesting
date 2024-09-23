package models;

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
        int result = 1;
        result = ((result * 31) + ((this.code == null) ? 0 : this.code.hashCode()));
        result = ((result * 31) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((result * 31) + ((this.message == null) ? 0 : this.message.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof APIResponse)) {
            return false;
        }
        APIResponse apiResponse = ((APIResponse) other);
        return ((((this.code == apiResponse.code) || ((this.code != null) && this.code.equals(apiResponse.code))) && ((this.type == apiResponse.type) || ((this.type != null) && this.type.equals(apiResponse.type)))) && ((this.message == apiResponse.message) || ((this.message != null) && this.message.equals(apiResponse.message))));
    }

}
