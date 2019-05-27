package com.alivc.resource.pojo;  
/** 
 * ClassName: Paster <br/> 
 * Function: TODO 动图/字幕类. <br/> 
 * Reason:   TODO Font. <br/> 
 * Date:     2019年3月8日 下午2:18:53 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class Paster {
	
	/**
     * id
     */
    private int id ;
    /**
     * 图标
     */
    private String icon = "";
    /**
     * 描述
     */
    private String description = "";
    /**
     * 名称
     */
    private String name = "";
    /**
     * 等级
     */
    private int level ;
    /**
     * 预览图
     */
    private String preview;
    /**
     * 序号
     */
    private int sort ;
    /**
     * 类型
     */
    private int type;
    /**
     * 创建时间
     */
    private String createTime = "";
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

    

}
 