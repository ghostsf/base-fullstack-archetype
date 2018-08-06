package ${package}.support.convertor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;


/**
 * created by zhuxi
 */
public class XssFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XssFastJsonHttpMessageConverter.class);

    public static final String NoBreakChar = "\u00A0";

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return super.readInternal(clazz, inputMessage);
    }


    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream in = inputMessage.getBody();


        BufferedReader reader = new BufferedReader(new InputStreamReader(in, getFastJsonConfig().getCharset()));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            LOGGER.error("read input stream error");
        }

        String text = sb.toString();

        if (text.startsWith("{")) {
            JSONObject jsonObject =
                    JSON.parseObject(text, JSONObject.class, getFastJsonConfig().getFeatures());
            checkObject(jsonObject);
            return JSON.parseObject(jsonObject.toString(), type, getFastJsonConfig().getFeatures());
        } else if (text.startsWith("[")) {
            JSONArray jsonArray =
                    JSON.parseObject(text, JSONArray.class, getFastJsonConfig().getFeatures());
            checkArray(jsonArray);
            return JSON.parseObject(jsonArray.toString(), type, getFastJsonConfig().getFeatures());
        } else {
            return super.read(type, contextClass, inputMessage);
        }
    }

    private void checkObject(JSONObject jsonObject) {
        if (jsonObject != null) {
            for (String key : jsonObject.keySet()) {
                Object obj = jsonObject.get(key);
                if (obj instanceof Collection) {
                    checkArray((JSONArray) obj);
                } else if (obj instanceof Map) {
                    checkObject((JSONObject) obj);
                } else if (obj instanceof String) {
                    String strText = trim((String) obj);
                    jsonObject.put(key, strText);
                }
            }
        }
    }

    private void checkArray(JSONArray jsonArray) {
        if (jsonArray != null) {
            for (int i = 0, l = jsonArray.size(); i < l; i++) {
                Object obj = jsonArray.get(i);
                if (obj instanceof Collection) {
                    checkArray((JSONArray) obj);
                } else if (obj instanceof Map) {
                    checkObject((JSONObject) obj);
                } else if (obj instanceof String) {
                    String strText = trim((String) obj);
                    jsonArray.set(i, strText);
                }
            }
        }
    }


    private String trim(String origin) {
        return StringUtils.trim(origin).replace(NoBreakChar, "");
    }
}
