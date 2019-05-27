package com.alivc.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** 
 * ClassName: User <br/> 
 * Function: TODO 用户实体类. <br/> 
 * Reason:   TODO 用户实体类. <br/> 
 * Date:     2018年11月10日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 *  修改记录：时间  2018年11月29日    内容  字段默认为"" 
 */
public class User {
    /**
     *  id
     */          
	  private String id = "";
	  /**
       * 用户id
       */
	  private String userId = "";
	  /**
	   * 用户昵称
	   */
	  private String nickName = "";
	  /**
       * 用户头像
       */
	  private String avatarUrl = "";
	  /**
       * 推流地址
       */
	  private String pushUrl = "";
	  /**
       * flv播放地址
       */
	  private String playFlv = "";
	  /**
       * hls播放地址
       */
	  private String playHls = "";
	  /**
       * rtmp播放地址
       */
	  private String playRtmp = "";
	  /**
       * 创建时间
       */
	  private String gmtCreate = "";
	  /**
       *修改时间 
       */
	  private String gmtModified = "";
	  /**
	   * token
	   */
	  private String token = "";
	  
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    @JsonIgnore
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	@JsonIgnore
	public String getPlayFlv() {
		return playFlv;
	}
	public void setPlayFlv(String playFlv) {
		this.playFlv = playFlv;
	}
	@JsonIgnore
	public String getPlayHls() {
		return playHls;
	}
	public void setPlayHls(String playHls) {
		this.playHls = playHls;
	}
	@JsonIgnore
	public String getPlayRtmp() {
		return playRtmp;
	}
	public void setPlayRtmp(String playRtmp) {
		this.playRtmp = playRtmp;
	}
	public String getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public String getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
	  
	  
	  

}
