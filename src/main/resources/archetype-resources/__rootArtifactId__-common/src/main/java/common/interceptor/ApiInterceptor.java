package ${package}.common.interceptor;

import ${package}.common.constants.ApplicationProperties;
import com.blackuio.base.exception.AppException;
import com.blackuio.base.model.Response;
import cn.hutool.core.util.RandomUtil;
import ${package}.common.enums.ErrorCodeEnum;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ApiInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        long executionTime = 0L;
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        Object result = null;
        Logger LOGGER = LoggerFactory.getLogger(target.getClass());
        String methodName = target.getClass().getSimpleName() + "." + method.getName();
        boolean hasInvokeNo = StringUtils.isNotBlank(MDC.get(ApplicationProperties.STR_INVOKENO));

        try {
            try {
                if (!hasInvokeNo) {
                    MDC.put(ApplicationProperties.STR_INVOKENO, RandomUtil.simpleUUID());
                }

                Object[] args = invocation.getArguments();
                if (args.length > 0) {
                    LOGGER.info("enter method:{},argument0:{}", methodName, args[0]);
                }

                result = invocation.proceed();
            } catch (AppException var19) {
                LOGGER.info("appException occoured,responseCode:{},responseMsg:{}", var19.getResponseCode(), var19.getErrorMessage());
                LOGGER.error("appException occoured", var19);
                result = new Response<String>(var19.getResponseCode(), var19.getErrorMessage());
            } catch (Throwable var20) {
                LOGGER.info("unknow excepiton occoured");
                LOGGER.error("unknown exception occoued", var20);
                result = new Response<String>(ErrorCodeEnum.SYSTEM_ERROR.getResponseCode(),ErrorCodeEnum.SYSTEM_ERROR.getResponseMsg());
            }
        } finally {
            executionTime = System.currentTimeMillis() - startTime;
            if (result != null) {
                Response<String> response = (Response)result;
                LOGGER.info("exit method:{},code:{},msg:{},spend:{}", new Object[]{methodName, response.getCode(), response.getMsg(), executionTime});
            }

            if (!hasInvokeNo) {
                MDC.remove(ApplicationProperties.STR_INVOKENO);
            }

        }

        return result;
    }
}
