package com.alivc.resource.pojo;  
/** 
 * ClassName: VodMedia <br/> 
 * Function: TODO 资源信息. <br/> 
 * Reason:   TODO 资源信息. <br/> 
 * Date:     2019年3月8日 下午2:18:53 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class VodMedia {
	
	/**
     * id
     */
    private int id ;
    /**
     * 图标
     */
    private String icon = "";
    /**
     * 媒资id
     */
    private int mediaId ;
    /**
     * 下载地址
     */
    private String url = "";
    /**
     * 预览
     */
    private String preview = "";
    /**
     * 名称
     */
    private String name = "";
    /**
     * 时长
     */
    private int duration;
    /**
     * 描述
     */
    private String desc = "";
    /**
     * 排序
     */
    private int sort  ;
    /**
     * 比例
     */
    private int aspect ;
   
	/**
     * 素材类型
     */
    private int type ;
    /**
     * 字体id
     */
    private int fontId;
    
    /**
     * 创建时间
     */
    private String createTime = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getAspect() {
		return aspect;
	}

	public void setAspect(int aspect) {
		this.aspect = aspect;
	}


	public int getFontId() {
		return fontId;
	}

	public void setFontId(int fontId) {
		this.fontId = fontId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

    

}
 