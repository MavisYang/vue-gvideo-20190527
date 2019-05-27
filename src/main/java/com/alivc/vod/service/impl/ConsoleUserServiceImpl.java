package com.alivc.vod.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alivc.vod.dao.ConsoleUserDao;
import com.alivc.vod.pojo.ConsoleToken;
import com.alivc.vod.pojo.ConsoleUser;
import com.alivc.vod.service.ConsoleUserService;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * ClassName: VodVideoServiceImpl <br/> 
 * Function: TODO 视频impl层. <br/> 
 * Reason:   TODO 用于实现视频service层的方法. <br/> 
 * Date:     2018年12月14日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Service
public class ConsoleUserServiceImpl implements ConsoleUserService {
	private static final Logger LOG = LoggerFactory.getLogger(ConsoleUserServiceImpl.class);
	@Autowired
	private ConsoleUserDao consoleUserDao;
	
	/**
     * 用户登录
     * 
     * @param   consoleUser
     * @return result
     */
    @Override
    public Map<String, Object> login(ConsoleUser consoleUser) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<String, Object>(1);
        try {
            String userId = consoleUser.getUserId();
            String token = userId + consoleUser.getPassword() + System.currentTimeMillis();
            //插入数据库token
            ConsoleToken tokenInsert = new ConsoleToken();
            tokenInsert.setToken(token);
            tokenInsert.setCreateTime(format.format(System.currentTimeMillis()));
            tokenInsert.setUserId(userId);
            consoleUserDao.insert(tokenInsert);
            //返回客户端token
            map.put("consoleToken", token);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---控制台登陆异常", e);
        }
        return map;
    }
	@Override
	public ConsoleUser getConsoleUser(ConsoleUser consoleUser) {
		ConsoleUser consoleUserResult = null ;
        try {
        	consoleUserResult = consoleUserDao.getConsoleUser(consoleUser);
           } catch (Exception e) {
               e.printStackTrace();
               LOG.error("---查询控制台用户信息异常", e);
           }
        return consoleUserResult;
    }
	@Override
    public boolean getUserByToken(String token) {
        boolean isUser = true;
        try { 
            if(StringUtils.isNullOrEmpty(token)){
            isUser = false;
            LOG.error("token不能为空");
        }else{
        	List<ConsoleToken> userResult = consoleUserDao.getInfoByToken(token);
        	
            if(userResult.size() > 0){
                isUser = true;
                LOG.info("获取用户成功");
               }else{
                  isUser = false;
                  LOG.info("该token无法查到用户");
               }
        }} catch (Exception e) {
            e.printStackTrace();
            LOG.error("---判断token异常", e);
        }
        return  isUser;
    
    }
	@Override
	public void updateToken(String userId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ConsoleUser consoleUserResult = null ;
        try {
        	ConsoleUser consoleUser = new ConsoleUser();
        	consoleUser.setUserId(userId);
        	consoleUserResult = consoleUserDao.getConsoleUser(consoleUser);
        	String token = userId + consoleUserResult.getPassword() + System.currentTimeMillis();
        	ConsoleToken consoleToken = new ConsoleToken();
        	consoleToken.setToken(token);
        	consoleToken.setCreateTime(format.format(System.currentTimeMillis()));
			consoleUserDao.updateToken(consoleToken);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("---更新token异常", e);
		}
	}
	@Override
	public void deleteConsoleToken(String consoleToken) {
        try {
        	consoleUserDao.deleteConsoleToken(consoleToken);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---删除consoleToken异常", e);
        }
 
    }
	

}
