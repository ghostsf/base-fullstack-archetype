package ${package}.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import ${package}.common.models.Response;
import ${package}.common.enums.ErrorCodeEnum;
import com.alibaba.fastjson.JSON;

/**
 * 框架统一异常处理器
 *
 * @author robot
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);
    
    @Override
    public ModelAndView resolveException (HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        
        String code = "";
        
        String message = "";
        //已知异常处理
        if (ex instanceof AppException) {
            AppException appException = (AppException) ex;
            code = appException.getResponseCode();
            message = appException.getErrorMessage();
            LOGGER.info("AppException occoured,code:{},message:{}",code,message);
        }else {
            //未知异常处理
            LOGGER.info("unknown exception occoured");
            LOGGER.error("unknown exception occoured",ex);
            code = ErrorCodeEnum.SYSTEM_ERROR.getResponseCode();
            message = ErrorCodeEnum.SYSTEM_ERROR.getResponseMsg();
        }
        
        
        return isAjaxRequest(request) ? processAjaxResponse(response, code, message) : processNormalResponse(code, message);
    }
    
    
    /**
     * 
     * 判断是否是Ajax请求<br>
     *
     */
    private static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) ||
                "true".equals(request.getParameter("ajax")) || (request.getContentType() != null && request.getContentType().contains("application/json"));
    }
    
    /**
     * 处理ajax的返回结果
     */
    private ModelAndView processAjaxResponse(HttpServletResponse response,String code,String message){
        
        response.setContentType("text/plain; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        
        try {
            Response<String> result = new Response<>(code, message);
            out = response.getWriter();
            out.write(JSON.toJSONString(result));
        } catch (IOException e) {
            LOGGER.info(" response write error");
            LOGGER.error("response write error", e);
        } finally {
            
            if (out!=null) {
                
                out.close();
            }
        }
        
        return null;
    }
    
    /**
     * 处理普通响应
     */
    private ModelAndView processNormalResponse(String code,String message){
        Map<String, Object> result = new HashMap<>(2);
        result.put("code", code);
        result.put("msg", message);
        return new ModelAndView("/exception/internal_error", result);
    }
}
