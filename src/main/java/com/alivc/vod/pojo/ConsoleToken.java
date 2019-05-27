package com.alivc.vod.pojo;


/**
 * ClassName: ConsoleUser <br/> 
 * Function: TODO 控制台用户实体类. <br/> 
 * Reason:   TODO 控制台用户实体类. <br/> 
 * Date:     2018年12月12日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see        
 */
public class ConsoleToken {
	
	/**
     * id
     */
    private String id = "";
    /**
     * token
     */
    private String token = "";
    /**
     * 用户id
     */
    private String userId = "";
     /**
      * 创建时间
      */
    private String createTime = "";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    
    


}
