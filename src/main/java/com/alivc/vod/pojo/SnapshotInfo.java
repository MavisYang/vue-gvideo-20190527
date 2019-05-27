
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
public class SnapshotInfo {
	/**
	 * 视频截图任务状态
	 */
	private String status = "";
	/**
	 * 截图类型：CoverSnapshot(封面截图)、NormalSnapshot(普通截图)、SpriteSnapshot(雪碧截图)
	 */
	private String snapshotType = "";
	/**
	 * 截图总数
	 */
	private String snapshotCount = "";
	/**
	 * 截图名称格式，可使用OSS存储地址或CDN域名和该字段信息生成截图地址
	 */
	private String snapshotFormat = "";
	/**
	 * 截图地址规则，可根据规则生成截图地址 (推荐使用该字段生成截图地址)
	 */
	private String snapshotRegular = "";
	/**
	 * 截图任务ID
	 */
	private String jobId = "";
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSnapshotType() {
		return snapshotType;
	}
	public void setSnapshotType(String snapshotType) {
		this.snapshotType = snapshotType;
	}
	public String getSnapshotCount() {
		return snapshotCount;
	}
	public void setSnapshotCount(String snapshotCount) {
		this.snapshotCount = snapshotCount;
	}
	public String getSnapshotFormat() {
		return snapshotFormat;
	}
	public void setSnapshotFormat(String snapshotFormat) {
		this.snapshotFormat = snapshotFormat;
	}
	public String getSnapshotRegular() {
		return snapshotRegular;
	}
	public void setSnapshotRegular(String snapshotRegular) {
		this.snapshotRegular = snapshotRegular;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	

}
 