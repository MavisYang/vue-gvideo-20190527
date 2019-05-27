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
public class BaseVideoInfo {

    /**
     * id
     */
    private String id = "";

    /**
     * 视频id
     */
    private String videoId = "";
    /**
     * 视频标题
     */
    private String title = "";
    /**
     * 视频描述
     */
    private String description = "";
    /**
     * 视频时长（秒）
     */
    private float duration = 0f;
    /**
     * 封面url
     */
    private String coverUrl = "";
    /**
     * 创建时间
     */
    private String creationTime = "";
    /**
     * 状态
     */
    private String status = "";
    /**
     * 首帧地址
     */
    private String firstFrameUrl = "";
    /**
     * 视频大小
     */
    private int size = 0;
    /**
     * 分类id
     */
    private int cateId = 0;
    /**
     * 分类名称
     */
    private String cateName = "";
    /**
     * 标签
     */
    private String tags = "";

    /**
     * 分享链接
     */
    private String shareUrl = "";

    /**
     * 视频作者
     */

    private BaseUserProfile user;
    /**
     * 转码状态
     */
    private String transcodeStatus = "";
    /**
     * 截图状态
     */
    private String snapshotStatus = "";
    /**
     * 审核状态
     */
    private String censorStatus = "";
    /**
     * 窄带转码状态
     */
    private String narrowTranscodeStatus = "";

    public BaseUserProfile getUser() {
        return user;
    }

    public void setUser(BaseUserProfile user) {

        this.user = user;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstFrameUrl() {
        return firstFrameUrl;
    }

    public void setFirstFrameUrl(String firstFrameUrl) {
        this.firstFrameUrl = firstFrameUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

	public String getTranscodeStatus() {
		return transcodeStatus;
	}

	public void setTranscodeStatus(String transcodeStatus) {
		this.transcodeStatus = transcodeStatus;
	}

	public String getSnapshotStatus() {
		return snapshotStatus;
	}

	public void setSnapshotStatus(String snapshotStatus) {
		this.snapshotStatus = snapshotStatus;
	}

	public String getCensorStatus() {
		return censorStatus;
	}

	public void setCensorStatus(String censorStatus) {
		this.censorStatus = censorStatus;
	}

	public String getNarrowTranscodeStatus() {
		return narrowTranscodeStatus;
	}

	public void setNarrowTranscodeStatus(String narrowTranscodeStatus) {
		this.narrowTranscodeStatus = narrowTranscodeStatus;
	}
    
    
}
