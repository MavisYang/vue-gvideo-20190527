package com.alivc.vod.controller;


import java.util.HashMap;
import java.util.Map;

import com.alivc.base.ConstanData;
import com.alivc.base.ResponseResult;
import com.alivc.base.TraceIdContext;
import com.alivc.vod.pojo.ConsoleUser;
import com.alivc.vod.service.ConsoleUserService;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ConsoleUserController <br/>
 * Function: TODO 控制台用户管理控制器. <br/>
 * Reason: TODO 控制台用户管理. <br/>
 * Date: 2018年1月17日 <br/>
 * 
 * @author tz
 * @version v0.0.1
 * @since JDK 1.8
 * @see
 */
@RestController
public class ConsoleUserController {

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleUserController.class);
    @Autowired
    private ConsoleUserService consoleUserService;

    
    /**
     * 用户登陆
     * 
     * @param   
     *      userName, password
     * @return result
     */
    @RequestMapping(value = "/console/user/login", method = RequestMethod.POST)
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
            	ConsoleUser consoleUserParam = new ConsoleUser();
            	consoleUserParam.setUserName(userName);
            	consoleUserParam.setPassword(password);
                //验证用户信息
            	ConsoleUser consoleUserResult = consoleUserService.getConsoleUser(consoleUserParam);
                if (consoleUserResult != null ) {
                    map = consoleUserService.login(consoleUserResult);
                    result.setData(map);
                }else {
                    result.setResult("false");
                    result.setCode(ConstanData.UNAUTHORIZED);
                    result.setMessage("用户名,密码错误!");
                }
            }
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("---控制台用户登陆异常", e);
        }
        return result;
    }
    /**
     * 刷新token
     *
     * @param  userId
     * @return result
     */
    @RequestMapping(value = "/console/user/refreshToken", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult refreshToken(String userId) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,userId: {}", traceId, userId);
        ResponseResult result = new ResponseResult(traceId);
        try {
        	consoleUserService.updateToken(userId);
            result.setMessage("刷新完成！");
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 刷新token异常", e);
        }
        return result;
    }
    /**
     * 退出登录
     *
     * @param  consoleToken
     * @return result
     */
    @RequestMapping(value = "/console/user/signOut", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult signOut(String consoleToken) {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	LOG.info("traceId: {} ,consoleToken: {}", traceId,consoleToken);
    	ResponseResult result = new ResponseResult(traceId);
    	try {
    		consoleUserService.deleteConsoleToken(consoleToken);
    		result.setMessage("退出完成！");
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 退出登录异常", e);
    	}
    	return result;
    }
    
    
}
