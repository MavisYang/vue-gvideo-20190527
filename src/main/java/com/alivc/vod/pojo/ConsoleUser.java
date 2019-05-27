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
public class ConsoleUser {
	
	/**
     * id
     */
    private String id = "";
    /**
     * 用户id
     */
    private String userId = "";
    /**
     * 用户名
     */
    private String userName = "";

    /**
     * 用户头像
     */
    private String avatarUrl = "";
	  /**
       * 密码
       */
	  private String password = "";
	  
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
      
      

    
 
	  

}
