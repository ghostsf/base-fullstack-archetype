package ${package}.controller.base;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 过滤HTML标签等，防注入
 */
public class StringEditor extends PropertyEditorSupport {
    
    @Override
    public void setAsText(String text) {
        String strText = StringUtils.trim(text);
        setValue(strText);
    }
}