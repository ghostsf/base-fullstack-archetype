package ${package}.filter;

import ${package}.common.constants.ApplicationProperties;

import com.google.common.collect.Sets;
import com.blackuio.base.utils.NetUtils;
import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;

/**
 * log filter
 */
@Component("accessLogFilter")
public class AccessLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger("ActionAccessLogger");

    private static final String STR_IP = "ip";

    private static final String STR_SESSION_ID = "sessionId";

    private static final String STR_INVOKENO = "invokeNo";

    private static final String STR_EQ = "=";

    private static final String STR_AND = "&";

    private static final String MEDIA_FORM = "application/x-www-form-urlencoded";

    private static final String MEDIA_JSON = "application/json";

    /**
     * 截取参数的最大长度
     */
    private int maxLength = 32;

    /**
     * 不允许记录的action参数列表
     */
    private static final Set<String> excludeParams = Sets.newHashSet();

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("accessLog init");
    }

    static {
        excludeParams.add("password");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setAttribute("jsVersion", ApplicationProperties.jsVersion);

        String path = request.getRequestURI();

        if (path.startsWith("/static/")) {

            chain.doFilter(request, response);

            return;
        }


        // 获取用户登录IP
        String ip = NetUtils.getIpAddress(request);

        // 向MDC里面set ip、user
        MDC.put(STR_IP, ip);

        // 调用流水号
        MDC.put(STR_INVOKENO, RandomUtil.simpleUUID());

        // 计算action method执行方法
        long startTime = System.currentTimeMillis();

        long executionTime = 0L;

        try {// 调用用户访问的CONTROLLER

            ServletRequest requestWrapper = null;
            String contentType = request.getContentType();
            String params = "";
            if (contentType != null) {
                String lowerCaseContentType = contentType.toLowerCase();
                if (lowerCaseContentType.contains(MEDIA_FORM)) {
                    // form表单提交
                    params = getFormParam(request);
                } else if (lowerCaseContentType.contains(MEDIA_JSON)) {
                    requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
                    // json方式提交
                    params = getJsonParam(requestWrapper);
                }
            }

            LOGGER.info("Controller:{}|Params:{}", path, params);

            if (null == requestWrapper) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(requestWrapper, response);
            }

            executionTime = System.currentTimeMillis() - startTime;
        } finally {
            // 记录日志
            LOGGER.info("Controller:{} spend:{}", path, executionTime);
            // 清除MDC里面的历史信息
            MDC.remove(STR_IP);

            MDC.remove(STR_SESSION_ID);

            MDC.remove(STR_INVOKENO);
        }
    }

    private String getFormParam(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        // 取parameters
        Enumeration parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String param = (String) parameters.nextElement();
            if (excludeParams.contains(param)) {
                continue;
            }
            String value = request.getParameter(param);
            params.append(param);
            params.append(STR_EQ);
            params.append(StringUtils.substring(value, 0, this.maxLength));
            params.append(STR_AND);
        }
        return params.toString();
    }

    private String getJsonParam(ServletRequest request) {
        int contentLength = request.getContentLength();
        byte[] buffer = new byte[contentLength];
        String returnStr = "";
        try {
            int read = request.getInputStream().read(buffer);
            if (read > 0) {
                returnStr = new String(buffer, request.getCharacterEncoding());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return returnStr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        LOGGER.info("accessLog destroy");
    }
}
