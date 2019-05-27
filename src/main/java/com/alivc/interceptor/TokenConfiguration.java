  
package com.alivc.interceptor;  

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/** 
 * ClassName:SessionConfiguration <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年12月28日 下午5:15:56 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
@Configuration
public class TokenConfiguration extends WebMvcConfigurerAdapter
{
    @Bean
    TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
        .addPathPatterns("/live/**")
        .addPathPatterns("/vod/**")
        .addPathPatterns("/user/updateUser")
        .addPathPatterns("/console/vod/**");
    }
 }