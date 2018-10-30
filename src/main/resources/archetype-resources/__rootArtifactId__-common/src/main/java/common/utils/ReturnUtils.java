package ${package}.common.utils;

import com.blackuio.base.model.Response;
import ${package}.common.enums.ErrorCodeEnum;

import java.io.Serializable;

public class ReturnUtils {


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
