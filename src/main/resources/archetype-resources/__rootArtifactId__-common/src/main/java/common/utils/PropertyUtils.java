package ${package}.common.utils;

import ${package}.common.exception.AppException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

    private PropertyUtils() {
    }

    public static Properties getPropertiesByPath(String propertiesPath) throws IOException {
        InputStream is = PropertyUtils.class.getResourceAsStream("/".concat(propertiesPath));
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }

    public static Properties propertyString2Property(String str) {
        StringReader stringReader = new StringReader(str);
        Properties properties = new Properties();

        try {
            properties.load(stringReader);
            return properties;
        } catch (IOException var4) {
            LOGGER.error("propertyString2Property fail", var4);
            throw new AppException("9999", "propertyString2Property fail");
        }
    }

    public static void propertyAutoFill(Properties properties, Class<?> clazz) {
        try {
            Iterator var3 = properties.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<Object, Object> property = (Entry)var3.next();
                String key = (String)property.getKey();
                String value = (String)property.getValue();
                String attrName = propertyKey2JAttrName(key);
                Field filed = null;

                try {
                    filed = clazz.getDeclaredField(attrName);
                    filed.setAccessible(true);
                    LOGGER.info("propertyAutoFill,key:{},value:{}", filed.getName(), value);
                    filed.set(filed.getName(), value);
                } catch (NoSuchFieldException var9) {
                    LOGGER.info("{} not exist in {}", attrName, clazz.getName());
                }
            }

        } catch (Exception var10) {
            LOGGER.error("propertyAutoFill fail", var10);
            throw new AppException("9999", "propertyAutoFill fail");
        }
    }

    public static void propertyAutoFill(String propertyPath, Class<?> clazz) {
        try {
            Properties properties = getPropertiesByPath(propertyPath);
            propertyAutoFill(properties, clazz);
        } catch (IOException var3) {
            LOGGER.error("propertyAutoFill fail", var3);
            throw new AppException("9999", "propertyAutoFill fail");
        }
    }

    private static String propertyKey2JAttrName(String properyKey) {
        if (StringUtils.isBlank(properyKey)) {
            return null;
        } else {
            StringBuffer attrName = new StringBuffer(50);
            String[] partNames = properyKey.split("\\.");

            for(int index = 0; index < partNames.length; ++index) {
                String partName = partNames[index];
                if (index == 0) {
                    attrName.append(partName);
                } else {
                    attrName.append(StringUtils.upperCase(StringUtils.substring(partName, 0, 1)));
                    attrName.append(StringUtils.substring(partName, 1));
                }
            }

            return attrName.toString();
        }
    }
}
