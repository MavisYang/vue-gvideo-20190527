package com.alivc.resource.service.impl;

import java.util.List;
import com.alivc.resource.dao.ResourceDao;
import com.alivc.resource.pojo.Aspect;
import com.alivc.resource.pojo.Font;
import com.alivc.resource.pojo.MvResource;
import com.alivc.resource.pojo.Paster;
import com.alivc.resource.pojo.VodMedia;
import com.alivc.resource.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * ClassName: ResourceServiceImpl <br/> 
 * Function: TODO impl层. <br/> 
 * Reason:   TODO 用于实现资源service层的方法. <br/> 
 * Date:     2019年3月13日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceServiceImpl.class);
	@Autowired
	private ResourceDao resourceDao;
	
	/**
	 * 根据type获取动图或字幕信息
	 * @param type
	 * @return  List<Paster>
	 */
	@Override
	public List<Paster> getPasterList(String type) {
		List<Paster> pasterList = resourceDao.getPasterList(type);
		return pasterList;
	}

	/**
	 * 获取mv信息
	 * @param 
	 * @return  List<MvResource>
	 */
	@Override
	public List<MvResource> getMvList() {
		List<MvResource> mvList = resourceDao.getMvList();
		for(int i = 0;i < mvList.size(); i ++){
			List<Aspect> mediaList = resourceDao.getMvMediaById(mvList.get(i).getId());
			mvList.get(i).setAspectList(mediaList);
		}
		return mvList;
	}
	/**
	 * 根据字幕id获取媒资信息
	 * @param textId
	 * @return  List<VodMedia>
	 */
	@Override
	public List<VodMedia> getTextPasterMediaList(String textId) {
		List<VodMedia> textPasterMediaList = resourceDao.getTextMediaById(textId);
		return textPasterMediaList;
	}
	/**
	 * 根据动图id获取媒资信息
	 * @param pasterId
	 * @return  List<VodMedia>
	 */
	@Override
	public List<VodMedia> getPasterMediaList(String pasterId) {
		List<VodMedia> pasterMediaList = resourceDao.getPasterMediaById(pasterId);
		return pasterMediaList;
	}
	/**
	 * 获取前置动图媒资信息
	 * @param 
	 * @return  List<VodMedia>
	 */
	@Override
	public List<VodMedia> getFrontMedia() {
		List<VodMedia> frontMediaList = resourceDao.getFrontMedia();
		return frontMediaList;
	}
	
	/**
	 * 根据id获取字体
	 * @param 
	 * @return  List<VodMedia>
	 */
	@Override
	public Font getFontById(String id) {
		Font frontMedia = resourceDao.getFontById(id);
		return frontMedia;
	}
	
}
