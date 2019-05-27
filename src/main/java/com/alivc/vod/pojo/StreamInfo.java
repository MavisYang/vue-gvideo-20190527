
package com.alivc.vod.pojo;  
/** 
 * ClassName:SnapshotInfo <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2019年1月4日 下午8:57:33 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class StreamInfo {
	/**
	 * 视频截图任务状态
	 */
	private String status = "";
	/**
	 * 视频流码率，单位Kbps
	 */
	private String bitrate = "";
	/**
	 * 视频流清晰度定义, 取值：FD(流畅)，LD(标清)，SD(高清)，HD(超清)，OD(原画)，2K(2K)，4K(4K)
	 */
	private String definition = "";
	/**
	 * 视频流长度，单位秒
	 */
	private String duration = "";
	/**
	 * 视频流是否加密流
	 */
	private String encrypt = "";
	/**
	 * 视频流转码出错的时候，会有该字段表示出错代码
	 */
	private String errorCode = "";
	/**
	 * 视频流转码出错的时候，会有该字段表示出错信息
	 */
	private String errorMessage = "";
	/**
	 * 视频流的播放地址，不带鉴权的auth_key，如果开启了播放鉴权，此地址会无法访问
	 */
	private String fileUrl = "";
	/**
	 * 视频流格式，取值：mp4, m3u8
	 */
	private String format = "";
	/**
	 * 视频流帧率，每秒多少帧
	 */
	private String fps = "";
	/**
	 * 视频流高度，单位px
	 */
	private String height = "";
	/**
	 * 视频流大小，单位Byte
	 */
	private String size = "";
	/**
	 * 视频流宽度，单位px
	 */
	private String width = "";
	/**
	 * 转码作业ID
	 */
	private String jobId = "";
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBitrate() {
		return bitrate;
	}
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getFps() {
		return fps;
	}
	public void setFps(String fps) {
		this.fps = fps;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	 

	
}
 