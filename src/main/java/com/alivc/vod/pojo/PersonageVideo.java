package com.alivc.vod.pojo;

import java.util.List;

/**
 * ClassName: PersonageVideo <br/> 
 * Function: TODO 个人视频实体类. <br/> 
 * Reason:   TODO 个人视频实体类. <br/> 
 * Date:     2018年12月14日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see 
 */
public class PersonageVideo extends BaseVideoInfo {
	
	/**
	 * 是否推荐视频
	 */
	private String isRecommend;
	/**
	 * 视频地址list
	 */
	private List<String> fileUrlList;
	/**
	 * 视频地址list
	 */
	private List<String> snapshotList;
	public List<String> getFileUrlList() {
		return fileUrlList;
	}
	public void setFileUrlList(List<String> fileUrlList) {
		this.fileUrlList = fileUrlList;
	}
	public List<String> getSnapshotList() {
		return snapshotList;
	}
	public void setSnapshotList(List<String> snapshotList) {
		this.snapshotList = snapshotList;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	
	



}
