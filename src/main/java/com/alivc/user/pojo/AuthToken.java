package com.alivc.user.pojo;

import org.apache.commons.pool2.BaseObject;
/** 
 * ClassName: AuthToken <br/> 
 * Function: TODO Token实体类. <br/> 
 * Reason:   TODO Token实体类. <br/> 
 * Date:     2018年12月12日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see        
 */
public class AuthToken extends BaseObject {
        /**
         * token
         */
	    private String key;

	    /**
	     * 创建时间
	     */
	    private String createtime;

	    /**
	     * 用户id
	     */
	    private String userId;

	    public String getKey() {
	        return key;
	    }

	    public void setKey(String key) {
	        this.key = key;
	    }

	    public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }
	 
	    
}