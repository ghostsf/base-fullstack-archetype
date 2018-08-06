package ${package}.common.enums;

public enum ErrorCodeEnum {

    /**
     * 成功
     */
    SUCCESS("0000","success"),
    /**
     * 参数非法
     */
    ILLEGAL_ARGUMENT("9998","illegal parameter"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR("9999","system error");
	
	 /**
     * 响应码
     */ 
	private String responseCode;
    /**
     * 简述
     */
    private String responseMsg;
    /**
     * 私有构造函数
     * 
     * @param responseCode
     * @param responseMsg
     */
    private ErrorCodeEnum(String responseCode, String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}
	

}
