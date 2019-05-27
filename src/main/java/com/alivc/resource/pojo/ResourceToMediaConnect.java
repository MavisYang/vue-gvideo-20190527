package com.alivc.resource.pojo;  
/** 
 * ClassName: ResourceToMediaConnect <br/> 
 * Function: TODO 资源id与media关系信息. <br/> 
 * Reason:   TODO  资源id与media关系. <br/> 
 * Date:     2019年3月12日 下午10:23:08 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class ResourceToMediaConnect {
	
	/**
     * id
     */
    private int id ;
    /**
     * 资源id
     */
    private int resourceId;
    /**
     * 媒资id
     */
    private int mediaId ;
    /**
     * 类型
     */
    private String type = "" ;
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
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

    
    

}
 