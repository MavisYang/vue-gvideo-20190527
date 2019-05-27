package com.alivc.vod.pojo;

/** 
 * ClassName: recommendVideo <br/> 
 * Function: TODO 推荐视频实体类. <br/> 
 * Reason:   TODO 推荐视频实体类. <br/> 
 * Date:     2018年12月27日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see 
 */
public class RecommendVideo extends BaseVideoInfo {
	
	/**
	 * 播放地址
	 */
	 private String fileUrl = "";

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	 
	

}
