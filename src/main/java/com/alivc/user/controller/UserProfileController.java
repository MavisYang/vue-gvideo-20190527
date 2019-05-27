package com.alivc.user.controller;


import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alivc.base.ConstanData;
import com.alivc.base.HasherUtils;
import com.alivc.base.ResponseResult;
import com.alivc.user.pojo.UserProfile;
import com.alivc.user.service.UserProfileService;
import com.alivc.user.service.UserService;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: UserProfileController <br/>
 * Function: TODO 用户管理控制器. <br/>
 * Reason: TODO 用户管理. <br/>
 * Date: 2018年12月12日 <br/>
 * 
 * @author tz
 * @version v0.0.1
 * @since JDK 1.8
 * @see
 */
@RestController
public class UserProfileController {

    private static final Logger LOG = LoggerFactory.getLogger(UserProfileController.class);
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * 
     * @param  
     *      userName, password
     * @return result
     */
    //@RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult register(String userName,String password) {
        ResponseResult result = new ResponseResult();
        try {
            if (StringUtils.isNullOrEmpty(password)   || StringUtils.isNullOrEmpty(userName)) {
                result.setResult("false");
                result.setCode(ConstanData.UNAUTHORIZED);
                result.setMessage("用户名或密码不能为空!");
            } else {
                // 根据用户名判断用户是否已注册
                UserProfile userProfile = userProfileService.findUserByUserName(userName);
                // 已注册
                if (userProfile != null) {
                    result.setResult("false");
                    result.setCode(ConstanData.FORBIDDEN);
                    result.setMessage("该用户已注册!");
                } else {
                 // 未注册
                    UserProfile userProfileParam = new UserProfile();
                    userProfileParam.setUserName(userName);
                    userProfileParam.setPassword(password);
                    int resultInt = userProfileService.addUserProfile(userProfileParam);
                    if(resultInt != 0){
                        result.setMessage("注册成功!");
                    }else{
                        result.setResult("false");
                        result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
                        result.setMessage("注册失败!");
                    }
                }
            }
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("---用户注册异常", e);
        }
        return result;
    }

    
    /**
     * 用户登陆
     * 
     * @param   
     *      userName, password
     * @return result
     */
    //@RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(String userName,String password) {
        ResponseResult result = new ResponseResult();
        Map<String, Object> map = new HashMap<String, Object>(1);
        try {
            
            if (StringUtils.isNullOrEmpty(password)   || StringUtils.isNullOrEmpty(userName)) {
                result.setResult("false");
                result.setCode(ConstanData.UNAUTHORIZED);
                result.setMessage("用户名或密码不能为空!");
            } else {
                // 校验并登陆
                UserProfile userProfile = new UserProfile();
                userProfile.setUserName(userName);
                userProfile.setPassword(password);
                UserProfile userProfileParam = new UserProfile();
                userProfileParam.setUserName(userProfile.getUserName());
                //通过userName验证用户信息
                UserProfile userPorfileResult = userProfileService.getUserByProfile(userProfileParam);
                if (userPorfileResult != null ) {
                    String[] strings = userPorfileResult.getPassword().split("\\$");
                    List<String> list = Arrays.asList(strings);
                    String encode = HasherUtils.encode(userProfile.getPassword(), list.get(2), Integer.parseInt(list.get(1)));
                    if (encode.equals(userPorfileResult.getPassword()) == true) {
                        userProfile.setUserId(userPorfileResult.getUserId());
                        map = userProfileService.login(userProfile);
                        result.setData(map);
                    }else {
                        result.setResult("false");
                        result.setCode(ConstanData.UNAUTHORIZED);
                        result.setMessage("密码错误!");
                    }
                }else {
                    result.setResult("false");
                    result.setCode(ConstanData.UNAUTHORIZED);
                    result.setMessage("用户名错误!");
                }
                
            }
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("---用户登陆异常", e);
        }
        return result;
    }
    
    
    /**
     * 生成随机用户,并返回用户信息
     * 
     * @param   
     * @return result
     * @throws UnknownHostException 
     */
    @RequestMapping(value = "/user/randomUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult randomUser() throws UnknownHostException {
        return userService.randomUser();

    }


    /**
     * 修改用户名
     * 
     * @param   userId,nickname
     * @return result
     * @throws UnknownHostException 
     */
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateUser(String token,String userId, String nickname) throws UnknownHostException {
    	return userService.updateUser(userId,nickname);
    	
    }
    
    
    
    
    
    
}
