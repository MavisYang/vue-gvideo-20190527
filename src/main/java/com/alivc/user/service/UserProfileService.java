package com.alivc.user.service;

import java.util.Map;

import com.alivc.user.pojo.UserProfile;


/** 
 * ClassName: UserProfileService <br/> 
 * Function: TODO 用户service层. <br/> 
 * Reason:   TODO 用于用户相关功能的接口. <br/> 
 * Date:     2018年12月12日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
public interface UserProfileService {
	
	 
	/**
	 * 根据用户username获取用户信息
	 * @param userName
	 * @return ResponseResult
	 */
	public UserProfile findUserByUserName(String userName);
	/**
	 * 添加用户
	 * @param userProfile
	 * @return ResponseResult
	 */
	public int addUserProfile(UserProfile userProfile);
	/**
	 * 用户登陆
	 * @param userProfile
	 * @return ResponseResult
	 */
	public Map<String, Object> login(UserProfile userProfile);
	/**
     * 根据
     * @param userProfile
     * @return ResponseResult
     */
	public UserProfile  getUserByProfile(UserProfile userProfile);
	/**
	 * 根据token判断是否存在该用户
	 * @param token
	 * @return boolean
	 */
	public boolean getUserByToken(String token);

}
