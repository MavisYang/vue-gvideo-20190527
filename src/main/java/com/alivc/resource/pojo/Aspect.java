package com.alivc.resource.pojo;  
/** 
 * ClassName: Aspect <br/> 
 * Function: TODO  mv的下载信息. <br/> 
 * Reason:   TODO  mv的下载信息. <br/> 
 * Date:     2019年3月8日 下午2:18:53 <br/> 
 * @author   176xiangkou 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class Aspect {
	
	/**
     * aspect
     */
    private int aspect ;
    /**
     * 下载地址
     */
    private String download = "";
    /**
     * md5
     */
    private String md5 = "";
	public int getAspect() {
		return aspect;
	}
	public void setAspect(int aspect) {
		this.aspect = aspect;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}

    
}
 