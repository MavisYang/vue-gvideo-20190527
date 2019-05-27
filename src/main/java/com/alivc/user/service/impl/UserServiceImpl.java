package com.alivc.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

import com.alivc.base.ConfigMapUtil;
import com.alivc.base.JwtUtil;
import com.alivc.base.RandomString;
import com.alivc.base.ResponseResult;
import com.alivc.user.dao.AuthTokenDao;
import com.alivc.user.dao.UserDao;
import com.alivc.user.pojo.AuthToken;
import com.alivc.user.pojo.User;
import com.alivc.user.service.UserService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * ClassName: UserServiceImpl <br/> 
 * Function: TODO 用户impl层. <br/> 
 * Reason:   TODO 用于实现用户service层的方法. <br/> 
 * Date:     2018年11月10日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
    private AuthTokenDao tokenDao;


	/**
     * 生成随机用户
     * 
     * @param param
     *            查询参数
     * @return result
     */
	@Override
	public ResponseResult randomUser() {
	    ResponseResult result = new ResponseResult();
		String[] arrayAvatar = {"heads/01.png", 
					  "heads/02.png",
					  "heads/03.png",
					  "heads/04.png",
					  "heads/05.png",
					  "heads/06.png",
					  "heads/07.png",
					  "heads/08.png",
					  "heads/09.png",
					  "heads/10.png",
					  "heads/11.png"};
		Random rand = new Random();
		int num = rand.nextInt(11);
		// 配置随机用户参数
		User user = new User();
		user.setUserId(RandomString.getData());
		user.setNickName(RandomString.getRandomName());
		user.setAvatarUrl(arrayAvatar[num]);

		/*user.setPlayFlv(UUID.randomUUID().toString());
		user.setPlayHls(UUID.randomUUID().toString());
		user.setPlayRtmp(UUID.randomUUID().toString());
		user.setPushUrl(UUID.randomUUID().toString());*/
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(System.currentTimeMillis());
		user.setGmtCreate(time);
		try {
			userDao.insertGuest(user);
			//生成token
			String userId = user.getUserId();
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
			User guestUser = userDao.getUserById(user);
			guestUser.setAvatarUrl(ConfigMapUtil.getValueByKey("AVATAR_DOMAIN_NAME") + guestUser.getAvatarUrl());
			guestUser.setToken(token);
			result.setData(guestUser);
		} catch (Exception e) {
			result.setResult("false");
			result.setMessage("业务异常");
			result.setData(null);
			LOG.error("---生成随机用户异常", e);
		}
		return result;
	}

	/**
     * 修改用户信息
     * 
     * @param param
     *            userId,nickname
     * @return result
     */
	@Override
	public ResponseResult updateUser(String userId, String nickname) {
	    ResponseResult result = new ResponseResult();
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result.setResult("true");
		result.setRequestId(UUID.randomUUID().toString());
		try {
		 // 配置参数信息
	        User user = new User();
	        user.setUserId(userId);
	        user.setNickName(nickname);
	        user.setGmtModified(format.format(System.currentTimeMillis()));
			userDao.updateUser(user);
			result.setMessage("修改完成！");
		} catch (Exception e) {
			result.setResult("false");
			result.setMessage("业务异常");
			result.setData(null);
			LOG.error("---修改用户信息异常", e);
		}
		return result;
	}


	 

    @Override
    public User getUserById(String userId) {
        User user = new User();
        user.setUserId(userId);
        try {
            user = userDao.getUserById(user);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---获取用户信息异常", e);
        }
        return user;
    }

}
