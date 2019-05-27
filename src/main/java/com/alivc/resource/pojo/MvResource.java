package com.alivc.resource.pojo;

import java.util.List;

/** 
 * ClassName: MvResource <br/> 
 * Function: TODO mv资源类. <br/> 
 * Reason:   TODO mv信息. <br/> 
 * Date:     2019年3月8日 下午2:18:53 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class MvResource {
	
	/**
     * id
     */
    private int id;
    /**
     * previewPic
     */
    private String previewPic = "";
    /**
     * previewMp4
     */
    private String previewMp4 = "";
    /**
     * 图标
     */
    private String icon = "";
    /**
     * 时长
     */
    private int duration  ;
    /**
     * 名称
     */
    private String name = "";
    /**
     * 等级
     */
    private int level ;
    /**
     * 序号
     */
    private int sort ;
    /**
     * 序号
     */
    private String createTime = "";
    
    private List<Aspect> aspectList = null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPreviewPic() {
		return previewPic;
	}
	public void setPreviewPic(String previewPic) {
		this.previewPic = previewPic;
	}
	public String getPreviewMp4() {
		return previewMp4;
	}
	public void setPreviewMp4(String previewMp4) {
		this.previewMp4 = previewMp4;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<Aspect> getAspectList() {
		return aspectList;
	}
	public void setAspectList(List<Aspect> aspectList) {
		this.aspectList = aspectList;
	}
    

     
    
    

}
 