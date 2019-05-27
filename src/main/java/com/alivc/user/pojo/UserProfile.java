package com.alivc.user.pojo;

import org.codehaus.jackson.map.MapperConfig.Base;

/**
 * ClassName: UserPorfile <br/> 
 * Function: TODO 用户实体类. <br/> 
 * Reason:   TODO 用户实体类. <br/> 
 * Date:     2018年12月12日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see        
 */
public class UserProfile extends BaseUserProfile{
    /**
     *  id
     */          
	  private String id = "";
	  /**
       * 密码
       */
	  private String password = "";
	  /**
       * 角色
       */
	  private String role = "";
	  /**
       * 状态
       */
	  private String level = "";
	  /**
       * hls播放地址
       */
	  private String sex = "";
	  /**
       * rtmp播放地址
       */
	  private String status = "";
	  /**
       * 电话
       */
	  private String mobile = "";
	  /**
       * 邮箱
       */
	  private String email = "";
	  /**
       * 生日
       */
      private String birthday = "";
      /**
       * 最后登录时间
       */
      private String lastLogin = "";
      /**
       * 创建时间
       */
      private String createTime = "";
      /**
       * 头像
       */
      private String avatar = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
 
	  

}
