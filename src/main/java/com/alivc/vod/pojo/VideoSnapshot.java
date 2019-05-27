package com.alivc.vod.pojo;


/**
 * ClassName: ConsoleUser <br/> 
 * Function: TODO 控制台用户实体类. <br/> 
 * Reason:   TODO 控制台用户实体类. <br/> 
 * Date:     2018年12月12日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see        
 */
public class VideoSnapshot {
	
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
    private String snapshotUrl = "";
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
	public String getSnapshotUrl() {
		return snapshotUrl;
	}
	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}
    
    

}
