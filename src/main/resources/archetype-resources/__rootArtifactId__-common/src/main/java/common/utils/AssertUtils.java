package ${package}.common.utils;

import ${package}.common.enums.ErrorCodeEnum;
import ${package}.common.exception.AppException;
import org.apache.commons.lang3.StringUtils;

public class AssertUtils {
    private AssertUtils() {
    }

    public static void isNotBlank(String str, ErrorCodeEnum errorCodeEnum) {
        if (StringUtils.isBlank(str)) {
            throw new AppException(errorCodeEnum);
        }
    }

    public static void isAllNotBlank(String[] strs, ErrorCodeEnum errorCodeEnum) {
        int var4 = strs.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String str = strs[var3];
            isNotBlank(str, errorCodeEnum);
        }

    }

    public static void isNotBlank(String str, ErrorCodeEnum errorCodeEnum, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new AppException(errorCodeEnum, msg);
        }
    }

    public static void isTrue(boolean bool, ErrorCodeEnum errorCodeEnum) {
        if (!bool) {
            throw new AppException(errorCodeEnum);
        }
    }

    public static void isTrue(boolean bool, ErrorCodeEnum errorCodeEnum, String message) {
        if (!bool) {
            throw new AppException(errorCodeEnum, message);
        }
    }

    public static void isTrue(boolean bool, String responseCode, String responseMsg) {
        if (!bool) {
            throw new AppException(responseCode, responseMsg);
        }
    }

    public static void isNotNull(Object object, ErrorCodeEnum errorCodeEnum) {
        if (object == null) {
            throw new AppException(errorCodeEnum);
        }
    }

    public static void isNotNull(Object object, ErrorCodeEnum errorCodeEnum, String message) {
        if (object == null) {
            throw new AppException(errorCodeEnum, message);
        }
    }
}
