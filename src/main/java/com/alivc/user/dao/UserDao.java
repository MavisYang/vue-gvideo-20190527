package com.alivc.user.dao;

import com.alivc.user.pojo.User;
import org.apache.ibatis.annotations.Mapper;


/** 
 * ClassName: UserDao <br/> 
 * Function: TODO 用户的dao层. <br/> 
 * Reason:   TODO 用户的dao层. <br/> 
 * Date:     2018年11月10日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Mapper
public interface  UserDao {
	
    /**
     * 根据id获取用户信息
     * @param user
     * @return  User
     */
	public 	User getUserById(User user);
	/**
	 * 插入游客信息
	 * @param user
	 */
	public 	void insertGuest(User user);
	/**
	 * 更新用户信息
	 * @param user
	 */
	public 	void updateUser(User user);

}
