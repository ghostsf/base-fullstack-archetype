package ${package}.common.exception;

import ${package}.common.enums.ErrorCodeEnum;
import java.io.Serializable;

public class AppException extends RuntimeException {
    private String responseCode;
    private String errorMessage;

    public AppException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getResponseCode() + ":" + errorCodeEnum.getResponseMsg());
        this.responseCode = errorCodeEnum.getResponseCode();
        this.errorMessage = errorCodeEnum.getResponseMsg();
    }

    public AppException(String responseCode, String responseMsg) {
        super(responseCode + ":" + responseMsg);
        this.responseCode = responseCode;
        this.errorMessage = responseMsg;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public Throwable fillInStackTrace() {
        return this;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}