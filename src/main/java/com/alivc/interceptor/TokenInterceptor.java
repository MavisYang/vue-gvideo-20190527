/** 
 * Project Name:sdk-api 
 * File Name:SessionInterceptor.java 
 * Package Name:com.alivc.base 
 * Date:2018年12月28日下午4:57:54 
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved. 
 * 
*/  
  
package com.alivc.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alivc.base.ConstanData;
import com.alivc.base.ResponseResult;
import com.alivc.base.TraceIdContext;
import com.alivc.user.service.UserProfileService;
import com.alivc.vod.service.ConsoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 
 * ClassName:SessionInterceptor <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年12月28日 下午4:57:54 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
@Component 
public class TokenInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(TokenInterceptor.class);
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private ConsoleUserService consoleUserService;
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        // 线程绑定变量（该数据只有当前请求的线程可见）
        TraceIdContext.ctx.remove();
        String traceId = TraceIdContext.ctx.get().getTraceId();

        boolean  isToken = false;
        if (userProfileService == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(
                request.getServletContext());
            userProfileService = (UserProfileService)factory.getBean("userProfileService");
        }
        if (consoleUserService == null) {
        	BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(
        			request.getServletContext());
        	consoleUserService = (ConsoleUserService)factory.getBean("consoleUserService");
        }
        String token = request.getParameter("token");
        String consoleToken = request.getParameter("consoleToken");
        
        if (consoleToken != null && consoleUserService.getUserByToken(consoleToken)) {
            isToken = true;
        } else if (token != null && userProfileService.getUserByToken(token)) {
            isToken = true;
        } else {
            PrintWriter out = null;
            out = response.getWriter();
            String resultJson = "{\"code\":\"403\",\"message\":\"Please log in the system!\"}";
            out.append(resultJson);
            isToken = false;
        }
        LOG.info("isToken : {},token : {}, isToken: {}",traceId,token,isToken);
        return isToken;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}