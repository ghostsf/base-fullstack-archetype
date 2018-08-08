package ${package}.common.models;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private String code;
    private String msg;
    private T data;

    public Response() {
    }

    public Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public Response<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> Response success(T data) {
        return new Response(ErrorCodeEnum.SUCCESS.getResponseCode(), "", data);
    }

    public static Response success(String msg) {
        return new Response(ErrorCodeEnum.SUCCESS.getResponseCode(), msg);
    }

    public static <T> Response success(String msg, T data) {
        return new Response(ErrorCodeEnum.SUCCESS.getResponseCode(), msg, data);
    }
}
