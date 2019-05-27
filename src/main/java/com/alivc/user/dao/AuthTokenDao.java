package com.alivc.user.dao;

import com.alivc.user.pojo.AuthToken;
import org.apache.ibatis.annotations.Mapper;

/** 
 * ClassName: AuthTokenDao <br/> 
 * Function: TODO token管理的dao层. <br/> 
 * Reason:   TODO token管理的dao层. <br/> 
 * Date:     2018年12月13日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Mapper
public interface  AuthTokenDao {
	
    /**
     * 根据token获取信息
     * @param key
     * @return  AuthToken
     */
	public 	AuthToken selectByPrimaryKey(String key);
	/**
	 * 插入token
	 * @param authToken
	 * @return  int
	 */
	public  int insert(AuthToken authToken);

}
