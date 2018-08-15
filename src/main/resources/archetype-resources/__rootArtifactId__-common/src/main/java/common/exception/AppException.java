package ${package}.common.exception;

import ${package}.common.enums.ErrorCodeEnum;

/**
 * system app exception
 */
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

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}