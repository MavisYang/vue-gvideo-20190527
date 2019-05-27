package com.alivc.resource.pojo;  
/** 
 * ClassName: Font <br/> 
 * Function: TODO 字体类. <br/> 
 * Reason:   TODO 字体. <br/> 
 * Date:     2019年3月8日 下午2:18:53 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class Font {
	
	/**
     * id
     */
    private int id;
    /**
     * 名称
     */
    private String name = "";
    /**
     * 等级
     */
    private int level ;
    /**
     * 横幅图片
     */
    private String banner = "";
    /**
     * 图标
     */
    private String icon = "";
    /**
     * url
     */
    private String url = "";
    /**
     * 序号
     */
    private int sort ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}

	

}
 