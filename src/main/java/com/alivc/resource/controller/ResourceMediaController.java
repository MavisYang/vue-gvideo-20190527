package com.alivc.resource.controller;

import java.util.ArrayList;
import java.util.List;

import com.alivc.base.ConstanData;
import com.alivc.base.ResponseResult;
import com.alivc.base.TraceIdContext;
import com.alivc.resource.pojo.Font;
import com.alivc.resource.pojo.MvResource;
import com.alivc.resource.pojo.Paster;
import com.alivc.resource.pojo.VodMedia;
import com.alivc.resource.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ResourceMediaController <br/>
 * Function: 媒资管理控制器. <br/>
 * Reason:   媒资管理. <br/>
 * Date:     2019年3月8日  <br/>
 *
 * @author tz
 * @version v0.0.1
 * @see
 * @since JDK 1.8
 */
@RestController
public class ResourceMediaController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceMediaController.class);
    @Autowired
	private ResourceService resourceService;
    
    /**
     * 获取动图类别信息
     *
     * @param 
     * @return result
     */
    
    @RequestMapping(value = "/resource/getPasterInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getPasterInfo() {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	ResponseResult result = new ResponseResult(traceId);
    	List<Paster> pasterList = new ArrayList<Paster>();
    	try {
    		pasterList = resourceService.getPasterList("1");
    		result.setData(pasterList);
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 获取动图类别信息", e);
    	}
    	return result;
    }
    
    /**
     * 获取字幕类别信息
     *
     * @param 
     * @return result
     */
    
    @RequestMapping(value = "/resource/getTextPaster", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getTextPasterInfo() {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	ResponseResult result = new ResponseResult(traceId);
    	List<Paster> textPasterList = new ArrayList<Paster>();
    	try {
    		textPasterList = resourceService.getPasterList("2");
    		result.setData(textPasterList);
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 获取字幕类别信息", e);
    	}
    	return result;
    }
    /**
     * 获取mv信息
     *
     * @param 
     * @return result
     */
    
    @RequestMapping(value = "/resource/getMv", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getMv() {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	ResponseResult result = new ResponseResult(traceId);
    	List<MvResource> mvList = new ArrayList<MvResource>();
    	try {
    		mvList = resourceService.getMvList();
    		result.setData(mvList);
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 获取mv信息", e);
    	}
    	return result;
    }
    /**
     * 根据字幕id查询字体列表
     *
     * @param textPasterId
     * @return result
     */
    @CrossOrigin
    @RequestMapping(value = "/resource/getTextPasterList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getTextPasterList(String textPasterId) {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	ResponseResult result = new ResponseResult(traceId);
    	List<VodMedia> textPasterMediaList = new ArrayList<VodMedia>();
    	try {
    		textPasterMediaList = resourceService.getTextPasterMediaList(textPasterId);
    		result.setData(textPasterMediaList);
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 根据字幕id查询字体列表", e);
    	}
    	return result;
    }
    /**
     * 根据动图id查询动图包
     *
     * @param pasterId
     * @return result
     */
    @CrossOrigin
    @RequestMapping(value = "/resource/getPasterList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getPasterList(String pasterId) {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	ResponseResult result = new ResponseResult(traceId);
    	List<VodMedia> pasterMediaList = new ArrayList<VodMedia>();
    	try {
    		pasterMediaList = resourceService.getPasterMediaList(pasterId);
    		result.setData(pasterMediaList);
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 根据动图id查询动图包", e);
    	}
    	return result;
    }
    
    
    /**
     * 查询前置动图包
     *
     * @param 
     * @return result
     */
    
    @RequestMapping(value = "/resource/getFrontPasterList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getFrontPasterList() {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	ResponseResult result = new ResponseResult(traceId);
    	List<VodMedia> frontMediaList = new ArrayList<VodMedia>();
    	try {
    		frontMediaList = resourceService.getFrontMedia();
    		result.setData(frontMediaList);
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 查询前置动图包", e);
    	}
    	return result;
    }
    
    /**
     * 根据id获取字体
     *
     * @param fontId
     * @return result
     */
    @RequestMapping(value = "/resource/getFont", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getFont(String fontId) {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	ResponseResult result = new ResponseResult(traceId);
    	Font fontMedia = new Font();
    	try {
    		fontMedia = resourceService.getFontById(fontId);
    		result.setData(fontMedia);
    		
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 根据id获取字体", e);
    	}
    	return result;
    }

    
}
