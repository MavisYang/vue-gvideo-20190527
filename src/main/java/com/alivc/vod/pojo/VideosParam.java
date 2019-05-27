package com.alivc.vod.pojo;

import com.alivc.base.ConstanData;
import com.alivc.user.pojo.BaseUserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 视频信息实体类
 *
 * @author haihua.whh
 * @date 2018-12-29
 */
public class VideosParam {


    /**
     * 用户id
     */
    private String userId = "";
    /**
     * 视频id
     */
    private String videoId = "";
    /**
     * 视频标题
     */
    private String title = "";
    /**
     * 用户名
     */
    private String userName = "";
    
    /**
	 * 开始时间
	 */
    private String startTime = "";
	/**
	 * 结束时间
	 */
    private String endTime = "";
    /**
     * 审核状态
     */
    private String censorStatus = "";
    /**
     * 开始页
     */
    private int pageIndex = 1;
    /**
     * 总页数
     */
    private int pageSize = 10;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCensorStatus() {
		return censorStatus;
	}
	public void setCensorStatus(String censorStatus) {
		this.censorStatus = censorStatus;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    
}
