package ${package}.common.plugins;


import java.util.Iterator;
import java.util.Map;
import ${package}.common.utils.PropertyUtils;

public class PropertyPlugin {
    public PropertyPlugin(Map<String, Class<?>> propertyConfig) {
        if (propertyConfig != null) {
            Iterator var3 = propertyConfig.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                PropertyUtils.propertyAutoFill(key, (Class)propertyConfig.get(key));
            }
        }

    }
}
