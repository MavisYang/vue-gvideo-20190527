package com.alivc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
/** 
 * ClassName: Application <br/> 
 * Function: TODO 启动入口. <br/> 
 * Reason:   TODO 用于启动spring boot工程. <br/> 
 * Date:     2018年11月10日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@EnableTransactionManagement
@SpringBootApplication
public class Application  implements EmbeddedServletContainerCustomizer {
 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 动态设置端口
     */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8080);
		
	}
 
}
