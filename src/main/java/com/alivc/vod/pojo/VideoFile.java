package com.alivc.vod.pojo;


/**
 * ClassName: VideoFile <br/> 
 * Function: TODO 控制台转码文件实体类. <br/> 
 * Reason:   TODO 控制台转码文件实体类. <br/> 
 * Date:     2018年1月17日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see        
 */
public class VideoFile {
	
	/**
     * id
     */
    private String id = "";
    /**
     * 视频id
     */
    private String videoId = "";
    /**
     * 截图地址
     */
    private String fileUrl = "";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
    

}
