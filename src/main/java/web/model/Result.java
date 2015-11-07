package web.model;

/**
 * Created by wannabe on 07.11.15.
 */
public class Result {
    private boolean success;
    private String message;
    private Object data;

    public Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }

    public static Result success(Object data) {
        return new Result(true, "OK", data);
    }

    public static Result fail(String message) {
        return new Result(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
