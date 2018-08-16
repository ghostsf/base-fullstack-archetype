package ${package}.common.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.blackuio.base.exception.AppException;
import ${package}.common.enums.DateFormatEnum;


/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils{
    /**
     * 禁止实例化
     */
    private DateUtils(){
        
    }
    
    /**
     * 日期格式化
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param date
     * @param dateFormatEnum:日期格式枚举类，大写
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String format(Date date,DateFormatEnum dateFormatEnum){
        
        return DateFormatUtils.format(date, dateFormatEnum.getFormat());
    }
    /**
     * 重载format，字符串形式的格式转换之后，字符串输出
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param date
     * @param dateFormatEnum
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String format(String date,DateFormatEnum dateFormatEnum){
        
        return format(parseDate(date), dateFormatEnum);
    }
    /**
     * 字符串日期转换
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param date
     * @param dateFormatEnum
     * @return
     * @throws ParseException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date parseDate(String date,DateFormatEnum dateFormatEnum) {
        
        try {
            
            return parseDate(date, dateFormatEnum.getFormat());
            
        } catch (ParseException e) {
            
            throw new AppException("9999","日期转换错误");
        }
    }
    
    /**
     * 字符串转日期，其中字符串的格式需在枚举中
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param date
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date parseDate(String date){
        
        try {
            
            return parseDate(date, DateFormatEnum.getFormats());
            
        } catch (ParseException e) {
            
            throw new AppException("9999","日期转换错误");
        }
    }
    
    /**
     * 
     * 计算时间差（天数）
     * 〈功能详细描述〉
     *
     * @param date1
     * @param date2
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static long calculateIntervalDays(Date date1,Date date2){
        
        Date dt1 = parseDate(format(date1, DateFormatEnum.yyyy_MM_dd));
        
        Date dt2 = parseDate(format(date2, DateFormatEnum.yyyy_MM_dd));
        
        long intervals = Math.abs(dt1.getTime()-dt2.getTime());
        
        return intervals/MILLIS_PER_DAY;
    }
    /**
     * 
     * 取当前月的第一天
     * @return
     */
    public static Date getFirstDayOfMonth(){
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.add(Calendar.MONTH, 0);
        
        calendar.set(Calendar.DAY_OF_MONTH,1);
        
        return DateUtils.parseDate(DateUtils.format(calendar.getTime(), DateFormatEnum.yyyy_MM_dd));
    }
    
    /**
     * 取当前月的最后一天
     * @return
     */
    public static Date getLastDayOfMonth(){
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        return DateUtils.parseDate(DateUtils.format(calendar.getTime(), DateFormatEnum.yyyy_MM_dd));
    }
    
    
}
