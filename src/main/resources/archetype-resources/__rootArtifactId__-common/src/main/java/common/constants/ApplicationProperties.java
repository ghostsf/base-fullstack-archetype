/**
 * 
 */
package ${package}.common.constants;

import java.util.Date;

import ${package}.common.enums.DateFormatEnum;
import ${package}.common.utils.DateUtils;

/**
 * @author Administrator
 *
 */
public class ApplicationProperties {
	/**
	 *  js版本号
	 */
	public static String jsVersion = DateUtils.format(new Date(), DateFormatEnum.yyyyMMddHHmm);

}
