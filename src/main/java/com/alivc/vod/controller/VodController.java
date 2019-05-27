package com.alivc.vod.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import com.alivc.base.AuthTokenGetUtil;
import com.alivc.base.ConfigMapUtil;
import com.alivc.base.ConstanData;
import com.alivc.base.ResponseResult;
import com.alivc.base.TraceIdContext;
import com.alivc.base.VodOpenAPI;
import com.alivc.user.pojo.BaseUserProfile;
import com.alivc.vod.pojo.PersonageVideo;
import com.alivc.vod.pojo.RecommendVideo;
import com.alivc.vod.service.VodVideoService;
import com.aliyuncs.vod.model.v20170321.SubmitAIJobResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:StsController <br/>
 * Function: 视频管理控制器. <br/>
 * Reason:   视频管理. <br/>
 * Date:     2018年12月17日  <br/>
 *
 * @author tz
 * @version v0.0.1
 * @see
 * @since JDK 1.8
 */
@RestController
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class VodController {

    private static final Logger LOG = LoggerFactory.getLogger(VodController.class);
    @Autowired
    private VodVideoService vodService;
    

    /**
     * 视频发布
     *
     * @param token, videoId,  title,  description,  duration,  coverUrl,  size,  tags,  cateId,  cateName
     * @return result
     */
    @RequestMapping(value = "/vod/videoPublish", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult videoPublish(String token, String videoId, String title, String description, String duration,
                                       String coverUrl, String size, String tags, String cateId, String cateName) {

        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {}, title: {},description: {}  ", traceId, videoId, title, description);

        ResponseResult result = new ResponseResult(traceId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	PersonageVideo video = vodService.getVideoById(videoId);
        	if(video != null){
        		result.setResult("false");
                result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
                result.setMessage("该视频已经发布，无法重复发布！");
        	}else{
        		if (StringUtils.isEmpty(videoId)) {
                    result.setResult("false");
                    result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
                    result.setMessage("videoId为必填项");
                }   
                if (StringUtils.isEmpty(coverUrl)) {
                    result.setResult("false");
                    result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
                    result.setMessage("coverUrl为必填项");
                }

                BaseUserProfile baseUserProfile = new BaseUserProfile();
                String userId = AuthTokenGetUtil.getUserIdFormToken(token);
                baseUserProfile.setUserId(userId);
                //配置参数
                PersonageVideo personageVideo = new PersonageVideo();
                personageVideo.setUser(baseUserProfile);
                if(StringUtils.isNotEmpty(duration)){
                	personageVideo.setDuration(Float.parseFloat(duration));
                }
                if(StringUtils.isNotEmpty(size)){
                	personageVideo.setSize(Integer.parseInt(size));
                }
                if(StringUtils.isNotEmpty(cateId)){
                	personageVideo.setSize(Integer.parseInt(cateId));
                }
                if(StringUtils.isNotEmpty(description)){
                	personageVideo.setDescription(URLEncoder.encode(description, "utf-8"));
                }
                personageVideo.setVideoId(videoId);
                personageVideo.setTitle(title);
                personageVideo.setCoverUrl(coverUrl);
                personageVideo.setCreationTime(format.format(System.currentTimeMillis()));
                personageVideo.setCensorStatus("onCensor");
                personageVideo.setTags(tags);
                personageVideo.setCateName(cateName);
                vodService.insertVideo(personageVideo);
                PersonageVideo videoRespond = vodService.getVideoById(videoId);
                if(StringUtils.isNotEmpty(videoRespond.getDescription())){
                	videoRespond.setDescription(URLDecoder.decode(videoRespond.getDescription(), "utf-8"));
                }
                result.setData(videoRespond);
                result.setMessage("发布视频成功！");

                //发布完视频之后，进入审核阶段，如果提交审核失败，sleep3秒后继续进入提交审核
               final String mediaId = videoId;
               ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
               cachedThreadPool.execute(new Runnable() {
            	     @Override
            	     public void run() {

            	        	boolean flag = true;
            	        	int sleepTime = 3000;
            	        	 //发布完视频之后，提交审核
            	            	while(flag && sleepTime <= 9000){
            	            		try {
            	                 		VodOpenAPI.submitSnapshotJobs(mediaId, 0L, 0L, 1L);
            	                         VodOpenAPI.submitSnapshotJobs(mediaId, 0L, 0L, 8L);
            	                        	VodOpenAPI.submitAIJob(mediaId, "AIVideoCensor");
            	                        	flag = false;
            	                         SubmitAIJobResponse aiJobResponse = VodOpenAPI.submitAIJob(mediaId, "AIVideoCensor");
            	                        	flag = false;
            	                        	LOG.info("videoId：" + mediaId  + "requestId:" + aiJobResponse.getRequestId());
            	                        	LOG.info("--- 提交截图，审核完成");
            	                        	Thread.yield();
            	            		} catch (Exception e) {
            	 						e.printStackTrace();
            	 					}
            	                	try {
            	                		LOG.info("--- 等待再次审核");
            	                		LOG.info("--- 提交截图，审核中");
            	 						Thread.sleep(sleepTime);
            	 						sleepTime = sleepTime + 3000;
            	 					} catch (InterruptedException e) {
            	 						e.printStackTrace();
            	 					}
            	            	}
            	        
            	     }
            	});
               //sumbitJobs(mediaId);
        	}
             
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 视频发布异常", e);
        }
        return result;
    }
    
   

    /**
     * 获取推荐视频
     *
     * @param token，pageIndex，pageSize
     * @return result
     */
    @RequestMapping(value = "/vod/getRecommendVideoList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getRecommendVideo(String token, int pageIndex, int pageSize, Integer id) {

        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,pageIndex: {}, pageSize: {}", traceId, pageIndex, pageSize);

        ResponseResult result = new ResponseResult(traceId);
        try {
            List<RecommendVideo> videoList = new ArrayList<RecommendVideo>();
            Map<String, Object> map = new HashMap<String, Object>(2);
            videoList = vodService.getRecommendVideo(id, pageIndex, pageSize);
            int total = vodService.selectReommendNums();
            map.put("total", total);
            for(int i = 0;i < videoList.size();i ++){
            	videoList.get(i).setFileUrl(ConfigMapUtil.getValueByKey("DOMAIN_NAME") + videoList.get(i).getFileUrl());
            	
            	if(StringUtils.isNotEmpty(videoList.get(i).getDescription())){
            		videoList.get(i).setDescription(URLDecoder.decode(videoList.get(i).getDescription(), "utf-8"));
                }
            }
            map.put("videoList", videoList);
            result.setData(map);

        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 获取推荐视频异常", e);
        }
        return result;
    }

    /**
     * 获取个人中心视频
     *
     * @param token，pageIndex，pageSize
     * @return result
     */
    @RequestMapping(value = "/vod/getPersonalVideoList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getPersonalVideo(String token, int pageIndex, int pageSize, Integer id) {
        String userId = AuthTokenGetUtil.getUserIdFormToken(token);
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,pageIndex: {}, pageSize: {}", traceId, pageIndex, pageSize);
        ResponseResult result = new ResponseResult(traceId);
        List<PersonageVideo> videoList = new ArrayList<PersonageVideo>();
        try {
            Map<String, Object> map = new HashMap<String, Object>(2);
            videoList = vodService.getPersonageVideo(userId, id, pageIndex, pageSize);
            int total = vodService.selectPersonageNums(userId);
            for(int i = 0;i < videoList.size();i ++){
            	if(StringUtils.isNotEmpty(videoList.get(i).getDescription())){
            		videoList.get(i).setDescription(URLDecoder.decode(videoList.get(i).getDescription(), "utf-8"));
                }
            }
            map.put("total", total);
            map.put("videoList", videoList);
            result.setData(map);
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 获取个人中心视频异常", e);
        }
        return result;
    }

    /**
     * 删除视频
     *
     * @param token，videoId, userId
     * @return result
     */
    @RequestMapping(value = "/vod/deleteVideoById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteVideoById(String token, String videoId, String userId) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {} ,userId: {}", traceId, videoId, userId);
        ResponseResult result = new ResponseResult(traceId);

        String tokenUid = AuthTokenGetUtil.getUserIdFormToken(token);

        if (StringUtils.isEmpty(tokenUid) || !tokenUid.equals(userId)){
            result.setMessage("删除视频，权限不足");
            result.setCode(ConstanData.FORBIDDEN);
            return result;
        }

        try {
            vodService.deleteVideoById(videoId, userId);
            vodService.deleteRecommendById(videoId, userId);
            result.setMessage("删除完成！");
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 删除视频异常", e);
        }
        return result;
    }

}