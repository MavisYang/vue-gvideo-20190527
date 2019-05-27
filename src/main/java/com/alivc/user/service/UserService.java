package com.alivc.user.service;

import com.alivc.base.ResponseResult;
import com.alivc.user.pojo.User;

/** 
 * ClassName: UserService <br/> 
 * Function: TODO 用户service层. <br/> 
 * Reason:   TODO 用于用户相关功能的接口. <br/> 
 * Date:     2018年11月10日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
public interface UserService {
	
	/**
	 * 新增随机用户
	 * @return ResponseResult
	 */
	public ResponseResult randomUser();
	/**
	 * 更新用户信息
	 * @param userId
	 * @param nickName
	 * @return ResponseResult
	 */
	public ResponseResult updateUser(String userId,String nickName);
	
	/**
     * 根据用户id获取用户信息
     * @param userId
     * @return User
     */
    public User getUserById(String userId);

}
