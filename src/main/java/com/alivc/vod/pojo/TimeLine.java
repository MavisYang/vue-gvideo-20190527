  
package com.alivc.vod.pojo;  
/** 
 * ClassName: TimeLine <br/> 
 * Function: TODO 时间线实体类. <br/> 
 * Reason:   TODO 用于配置添加水印的时间线的参数. <br/> 
 * Date:     2018年12月21日 上午11:01:39 <br/> 
 * @author   tz 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class TimeLine {
    
    /**
     * 水印开始显示时间
     */          
    private String  start = "";
    /**
     * 水印结束显示时间
     */
    private String duration = "";
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
     
    
    

}
 