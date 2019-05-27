package com.alivc.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.alivc.base.AuthTokenGetUtil;
import com.alivc.base.HasherUtils;
import com.alivc.base.JwtUtil;
import com.alivc.base.RandomString;
import com.alivc.user.dao.AuthTokenDao;
import com.alivc.user.dao.UserProfileDao;
import com.alivc.user.pojo.AuthToken;
import com.alivc.user.pojo.User;
import com.alivc.user.pojo.UserProfile;
import com.alivc.user.service.UserProfileService;
import com.alivc.user.service.UserService;
import com.mysql.jdbc.StringUtils;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * ClassName: UserServiceImpl <br/> 
 * Function:   用户impl层. <br/> 
 * Reason:     用于实现用户service层的方法. <br/> 
 * Date:     2018年11月10日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {
	private static final Logger LOG = LoggerFactory.getLogger(UserProfileServiceImpl.class);
	@Autowired
	private UserProfileDao userProfileDao;
	@Autowired
	private AuthTokenDao tokenDao;
	@Autowired
    private  UserService userService;

	/**
     * 根据userName查询用户信息
     * 
     * @param  userName
     * @return UserProfile
     */
	@Override
	public UserProfile findUserByUserName(String userName) {
	    UserProfile userPorfileResult = null;
		try {
		 // 配置参数信息
		    UserProfile userProfile = new UserProfile();
		    userProfile.setUserName(userName);
		    userPorfileResult = userProfileDao.getUserByProfile(userProfile);
		} catch (Exception e) {
		    e.printStackTrace();
			LOG.error("---根据userName查询用户信息异常", e);
		}
		return userPorfileResult;
	}
	/**
	 * 添加用户
	 * 
	 * @param  userProfile
	 * @return int
	 */
	@Override
	public int addUserProfile(UserProfile userProfile) {
	    RandomString rs = new RandomString();
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    int insertResult = 0;
 	    try {
	        userProfile.setPassword(HasherUtils.encode(userProfile.getPassword()));
	        userProfile.setUserId(rs.getNum());
	        userProfile.setCreateTime(format.format(System.currentTimeMillis()));
	        userProfile.setBirthday(null);
	        userProfile.setLastLogin(null);
	         insertResult = userProfileDao.addUserProfile(userProfile);
	    } catch (Exception e) {
	        e.printStackTrace();
	        LOG.error("---添加用户信息异常", e);
	    }
	    return insertResult;
	}
	/**
     * 用户登录
     * 
     * @param   userProfile
     * @return result
     */
    @Override
    public Map<String, Object> login(UserProfile userProfile) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<String, Object>(1);
        try {
                    String userId = userProfile.getUserId();
                    JSONObject json = new JSONObject();
                    json.put("useId", userId);
                    String subject = json.toJSONString();
                    String token = JwtUtil.createJWT(userId, subject, (long) (30 * 24 * 60 * 60 * 1000));
                    //检查token重复
                    AuthToken istoken = tokenDao.selectByPrimaryKey(token);
                    while (istoken != null) {
                        token = JwtUtil.createJWT(userId, subject, (long) (30 * 24 * 60 * 60 * 1000));
                        istoken = tokenDao.selectByPrimaryKey(token);
                    }
                    //插入数据库token
                    AuthToken tokenInsert = new AuthToken();
                    tokenInsert.setKey(token);
                    tokenInsert.setCreatetime(format.format(System.currentTimeMillis()));
                    tokenInsert.setUserId(userId);
                    tokenDao.insert(tokenInsert);
                    //返回客户端token
                    map.put("token", token);
                 
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---登陆异常", e);
        }
        return map;
    }
    @Override
    public UserProfile getUserByProfile(UserProfile userProfile) {
        UserProfile userPorfileResult = null ;
        try {
             userPorfileResult = userProfileDao.getUserByProfile(userProfile);
           } catch (Exception e) {
               e.printStackTrace();
               LOG.error("---查询用户信息异常", e);
           }
        
        return userPorfileResult;
    }
    @Override
    public boolean getUserByToken(String token) {
        boolean isUser = true;
        try { 
            if(StringUtils.isNullOrEmpty(token)){
            isUser = false;
            LOG.error("token不能为空");
        }else{
            String userId = AuthTokenGetUtil.getUserIdFormToken(token);
            if(userId != null && !"".equals(userId)){
                User userResult = userService.getUserById(userId);
            if(userResult != null){
                isUser = true;
                LOG.info("获取用户成功");
               }else{
                  isUser = false;
                  LOG.info("该token无法查到用户");
               }
            }else{
                isUser = false;
                LOG.error("无法查到userId");
            }
            
        }} catch (Exception e) {
            e.printStackTrace();
            LOG.error("---判断token异常", e);
        }
        return  isUser;
    
    }
    

}
