package com.alivc.vod.pojo;


/**
 * ClassName: transcodeRequest <br/> 
 * Function: TODO 存放转码请求信息实体类. <br/> 
 * Reason:   TODO 存放转码请求信息实体类. <br/> 
 * Date:     2018年2月11日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see        
 */
public class TranscodeRequest {
	
	/**
     * id
     */
    private String id = "";
    /**
     * 任务id
     */
    private String jobId = "";
    /**
     * 转码类型
     */
    private String transcodeType = "";
    /**
     * 创建时间
     */
    private String createTime = "";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTranscodeType() {
		return transcodeType;
	}
	public void setTranscodeType(String transcodeType) {
		this.transcodeType = transcodeType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	
    

}
