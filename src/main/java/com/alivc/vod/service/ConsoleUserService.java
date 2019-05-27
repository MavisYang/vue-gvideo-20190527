package com.alivc.vod.service;


import java.util.Map;

import com.alivc.vod.pojo.ConsoleUser;


/** 
 * ClassName: ConsoleUserService <br/> 
 * Function: TODO 控制台用户service层. <br/> 
 * Reason:   TODO 用于控制台用户相关功能的接口. <br/> 
 * Date:     2018年1月17日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
public interface ConsoleUserService {
	
	/**
     * 获取用户信息
     * @param  consoleUser
     * @return  ConsoleUser
     */
	public 	ConsoleUser getConsoleUser(ConsoleUser consoleUser);
	/**
	 * 控制台用户登陆
	 * @param consoleUser
	 * @return ResponseResult
	 */
	public Map<String, Object> login(ConsoleUser consoleUser);
	/**
	 * 根据token判断是否存在该用户
	 * @param token
	 * @return boolean
	 */
	public boolean getUserByToken(String token);
	/**
	 * 更新token
	 * @param consoleToken
	 */
	public void updateToken(String userId);
	/**
	 * 删除consoleToken
	 * @param consoleToken
	 */
	public void deleteConsoleToken(String consoleToken);
	

}
