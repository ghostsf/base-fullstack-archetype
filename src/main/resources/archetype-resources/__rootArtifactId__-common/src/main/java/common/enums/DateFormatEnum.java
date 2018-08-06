package ${package}.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举日期<br>
 */
public enum DateFormatEnum  {
    
    /**
     * 精确到年
     */
    yyyy("yyyy"),
    /**
     * 精确到日
     */
    yyyy_MM_dd("yyyy-MM-dd"),
    /**
     * 精确到日，中文
     */
    yyyy_MM_dd_ch("yyyy年MM月dd日"),
    /**
     * 精确到小时
     */
    yyyy_MM_dd_HH("yyyy-MM-dd HH"),
    /**
     * 精确到分
     */
    yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
    
    /**
     * 精确到秒
     */
    yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    
    /**
     * 精确到秒，不加分隔符
     */
    yyyyMMddHHmmss("yyyyMMddHHmmss"),
    /**
     * 精确到分
     */
    yyyyMMddHHmm("yyyyMMddHHmm"),
    ;
    
    /**
     * 日期格式
     */
    private String format;
    /**
     * 日期格式列表
     */
    private static List<String> formats = new ArrayList<String>();
    
    static {
        
        for(DateFormatEnum dateFormatEnum :DateFormatEnum.values()){
            
            formats.add(dateFormatEnum.getFormat());
        }
    }
    
    DateFormatEnum(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }

    public String getFormat() {
        return format;
    }
    
    public static String[] getFormats(){
        
        return formats.toArray(new String[formats.size()]);
    }
}
