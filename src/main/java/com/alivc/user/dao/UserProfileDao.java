package com.alivc.user.dao;

import com.alivc.user.pojo.UserProfile;
import org.apache.ibatis.annotations.Mapper;


/** 
 * ClassName: UserProfileDao <br/> 
 * Function: TODO 用户的dao层. <br/> 
 * Reason:   TODO 用户的dao层. <br/> 
 * Date:     2018年12月12日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Mapper
public interface  UserProfileDao {
	
    /**
     * 根据username获取用户信息
     * @param  userProfile
     * @return  UserPorfile
     */
	public 	UserProfile getUserByProfile(UserProfile userProfile);
	/**
	 * 添加用户
	 * @param userProfile
	 * @return  UserPorfile
	 */
	public 	int addUserProfile(UserProfile userProfile);

}
