package com.alivc.resource.service;


import java.util.List;

import com.alivc.resource.pojo.Font;
import com.alivc.resource.pojo.MvResource;
import com.alivc.resource.pojo.Paster;
import com.alivc.resource.pojo.VodMedia;


/** 
 * ClassName: ResourceService <br/> 
 * Function: TODO 资源service层. <br/> 
 * Reason:   TODO 资源相关功能的接口. <br/> 
 * Date:     2018年3月8日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
public interface ResourceService {
	
	
	/**
	 * 根据type获取动图或字幕信息
	 * @param type
	 * @return  List<Paster>
	 */
	public  List<Paster> getPasterList(String type);
	/**
	 * 获取mv信息
	 * @param 
	 * @return  List<MvResource>
	 */
	public  List<MvResource> getMvList();
	/**
	 * 根据字幕id查询字体列表
	 * @param textPasterId
	 * @return  List<MvResource>
	 */
	public  List<VodMedia> getTextPasterMediaList(String textPasterId);
	/**
	 * 根据id查询动图列表
	 * @param pasterId
	 * @return  List<MvResource>
	 */
	public  List<VodMedia> getPasterMediaList(String pasterId);
	/**
	 * 获取前置动图媒资信息
	 * @param 
	 * @return  List<VodMedia>
	 */
	public  List<VodMedia> getFrontMedia();
	
	/**
	 * 根据id获取字体
	 * @param id
	 * @return  List<VodMedia>
	 */
	public  Font getFontById(String id);
	
	
	
	

}
