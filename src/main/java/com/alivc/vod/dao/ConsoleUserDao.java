package com.alivc.vod.dao;

import java.util.List;

import com.alivc.user.pojo.AuthToken;
import com.alivc.vod.pojo.ConsoleToken;
import com.alivc.vod.pojo.ConsoleUser;
import com.alivc.vod.pojo.PersonageVideo;
import org.apache.ibatis.annotations.Mapper;


/** 
 * ClassName: ConsoleUserDao <br/> 
 * Function: TODO 控制台用户的dao层. <br/> 
 * Reason:   TODO 控制台用户的dao层. <br/> 
 * Date:     2018年1月17日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Mapper
public interface  ConsoleUserDao {
	
    /**
     * 获取用户信息
     * @param  consoleUser
     * @return  ConsoleUser
     */
	public 	ConsoleUser getConsoleUser(ConsoleUser consoleUser);
	/**
	 * 根据token判断是否存在该用户
	 * @param token
	 * @return ConsoleToken
	 */
	public List<ConsoleToken> getInfoByToken(String token);
	/**
	 * 更新token
	 * @param consoleToken
	 * @return int
	 */
	public int updateToken(ConsoleToken consoleToken);
	/**
	 * 插入token
	 * @param consoleToken
	 * @return  int
	 */
	public  int insert(ConsoleToken consoleToken);
	/**
	 * 删除consoleToken
	 * @param consoleToken
	 */
	public void deleteConsoleToken(String consoleToken);

}
