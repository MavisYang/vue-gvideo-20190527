package com.alivc.resource.dao;

import java.util.List;

import com.alivc.resource.pojo.Aspect;
import com.alivc.resource.pojo.Font;
import com.alivc.resource.pojo.MvResource;
import com.alivc.resource.pojo.Paster;
import com.alivc.resource.pojo.ResourceToMediaConnect;
import com.alivc.resource.pojo.VodMedia;
import org.apache.ibatis.annotations.Mapper;


/** 
 * ClassName: ResourceDao <br/> 
 * Function: TODO 资源的dao层. <br/> 
 * Reason:   TODO 资源的dao层. <br/> 
 * Date:     2018年3月8日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Mapper
public interface  ResourceDao {
	
	
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
	 * 根据mvId获取媒资信息
	 * @param mvId
	 * @return  List<VodMedia>
	 */
	public  List<Aspect> getMvMediaById(int mvId);
	/**
	 * 根据字幕id获取媒资信息
	 * @param textId
	 * @return  List<VodMedia>
	 */
	public  List<VodMedia> getTextMediaById(String textId);
	/**
	 * 根据动图id获取媒资信息
	 * @param pasterId
	 * @return  List<VodMedia>
	 */
	public  List<VodMedia> getPasterMediaById(String pasterId);
	/**
	 * 获取前置动图媒资信息
	 * @param 
	 * @return  List<VodMedia>
	 */
	public  List<VodMedia> getFrontMedia();
	/**
	 * 根据id获取字体
	 * @param id
	 * @return  Font
	 */
	public  Font getFontById(String id);
	 
	
	
	

}
