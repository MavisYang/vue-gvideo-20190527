package com.alivc.vod.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alivc.base.ConfigMapUtil;
import com.alivc.base.ConstanData;
import com.alivc.base.ResponseResult;
import com.alivc.base.TraceIdContext;
import com.alivc.base.VodOpenAPI;
import com.alivc.user.pojo.BaseUserProfile;
import com.alivc.vod.pojo.PersonageVideo;
import com.alivc.vod.pojo.RecommendVideo;
import com.alivc.vod.pojo.TranscodeRequest;
import com.alivc.vod.pojo.VideoFile;
import com.alivc.vod.pojo.VideoSnapshot;
import com.alivc.vod.pojo.VideosParam;
import com.alivc.vod.service.VodVideoService;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.SubmitTranscodeJobsResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ConsoleController <br/>
 * Function: 控制台控制器. <br/>
 * Reason:   控制台管理. <br/>
 * Date:     2018年1月14日  <br/>
 *
 * @author tz
 * @version v0.0.1
 * @see
 * @since JDK 1.8
 */
@RestController
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class ConsoleController {

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleController.class);
    @Autowired
    private VodVideoService vodService;

    /**
     * 根据条件查询视频列表
     *
     * @param token，String videoId,
    		 userId, title, startTime, endTime, userName, censorStatus
     * @return result
     */
    @RequestMapping(value = "/console/vod/getVideos", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getVideos(String consoleToken,int pageIndex, int pageSize,String videoId,
    		String userId, String title, String startTime, String endTime, String userName, String censorStatus) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {}", traceId, videoId);

        ResponseResult result = new ResponseResult(traceId);
        VideosParam videosParam  = new VideosParam();
        videosParam.setPageIndex(pageIndex-1);
        videosParam.setPageSize(pageSize);
        videosParam.setVideoId(videoId);
        videosParam.setUserId(userId);
        videosParam.setTitle(title);
        videosParam.setStartTime(startTime);
        videosParam.setEndTime(endTime);
        videosParam.setCensorStatus(censorStatus);
        videosParam.setUserName(userName);
        List<PersonageVideo> videoList = new ArrayList<PersonageVideo>();
        
         try {
                Map<String, Object> map = new HashMap<String, Object>(2);
                videoList = vodService.getVideos(videosParam);
                int total = vodService.selectVideoNums(videosParam);
                for(int i = 0;i < videoList.size();i ++){
                	if(StringUtils.isNotEmpty(videoList.get(i).getDescription())){
                		videoList.get(i).setDescription(URLDecoder.decode(videoList.get(i).getDescription(), "utf-8"));
                    }
                	List<String> fileUrlList = new ArrayList<String>();
                    List<String> snapshotList = new ArrayList<String>();
                	List<VideoSnapshot> videoSnapshotList = vodService.getSnapshotById(videoList.get(i).getVideoId());
                	for(int j = 0;j < videoSnapshotList.size();j ++){
                    	snapshotList.add(videoSnapshotList.get(j).getSnapshotUrl());
                    }
                	List<VideoFile> videoFileList = vodService.getFileById(videoList.get(i).getVideoId());
                	for(int k = 0;k < videoFileList.size();k ++){
                    	fileUrlList.add(videoFileList.get(k).getFileUrl());
                    }
                	videoList.get(i).setSnapshotList(snapshotList);
                	videoList.get(i).setFileUrlList(fileUrlList);
                }
                map.put("total", total);
                map.put("videoList", videoList);
                result.setData(map);
            } catch (Exception e) {
                result.setResult("false");
                result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
                result.setMessage("业务异常");
                result.setData(null);
                LOG.error("--- 获取视频列表异常", e);
            }
            return result;
    }
    /**
     * 发起非窄带高清转码
     *
     * @param 
     * @return result
     */
    @RequestMapping(value = "/console/vod/submitTranscode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult submitTranscode(String consoleToken, String mediaId) {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	LOG.info("traceId: {} ", traceId);
    	ResponseResult result = new ResponseResult(traceId);
    	try {
    		PersonageVideo video = vodService.getVideoById(mediaId);
    		String content = video.getUser().getUserId();
    		SubmitTranscodeJobsResponse transcodeJobsResponse = VodOpenAPI.submitTranscodeJobs(mediaId, ConfigMapUtil.getValueByKey("TEMPLATEGROUP_ID"),content);
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TranscodeRequest transcodeRequest = new TranscodeRequest();
			if(transcodeJobsResponse.getTranscodeJobs().size() > 0){
				transcodeRequest.setJobId(transcodeJobsResponse.getTranscodeJobs().get(0).getJobId());
			}
			transcodeRequest.setTranscodeType("common");
			transcodeRequest.setCreateTime(format.format(System.currentTimeMillis()));
			vodService.insertTranscodeRequest(transcodeRequest);
    		result.setMessage("发起非窄带高清转码作业完成！");
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 发起非窄带高清转码异常", e);
    	}
    	return result;
    }
    
    /**
     * 人工审核
     *
     * @param 
     * @return result
     */
    @RequestMapping(value = "/console/vod/createAudit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult createAudit(String consoleToken, String mediaId, String status, String reason, String comment) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ", traceId);
        ResponseResult result = new ResponseResult(traceId);
        try {
        	VodOpenAPI.createAudit(mediaId, status,reason, comment);
        	PersonageVideo personageVideo = new PersonageVideo();
    		personageVideo.setVideoId(mediaId);
        	if(StringUtils.isNotEmpty(status) && "Normal".equals(status)){
        		personageVideo.setCensorStatus("success");
        		vodService.updateVideo(personageVideo);
        	}else if(StringUtils.isNotEmpty(status) && "Blocked".equals(status)){
        		personageVideo.setCensorStatus("fail");
        		vodService.updateVideo(personageVideo);
        	}
    		
            result.setMessage("人工审核完成！");
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 人工审核异常", e);
        }
        return result;
    }
    /**
     * 发起窄带高清转码
     *
     * @param 
     * @return result
     */
    @RequestMapping(value = "/console/vod/submitTabTranscode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult submitTabTranscode(String consoleToken, String mediaId) {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	LOG.info("traceId: {} ", traceId);
    	ResponseResult result = new ResponseResult(traceId);
    	try {
    		PersonageVideo video = vodService.getVideoById(mediaId);
    		String content = video.getUser().getUserId();
    		SubmitTranscodeJobsResponse transcodeJobsResponse = VodOpenAPI.submitTranscodeJobs(mediaId, ConfigMapUtil.getValueByKey("TAB_TEMPLATEGROUP_ID"),content);
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TranscodeRequest transcodeRequest = new TranscodeRequest();
			if(transcodeJobsResponse.getTranscodeJobs().size() > 0){
				transcodeRequest.setJobId(transcodeJobsResponse.getTranscodeJobs().get(0).getJobId());
			}
			transcodeRequest.setTranscodeType("tab");
			transcodeRequest.setCreateTime(format.format(System.currentTimeMillis()));
			vodService.insertTranscodeRequest(transcodeRequest);
    		result.setMessage("发起窄带高清转码完成！");
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 发起窄带高清转码异常", e);
    	}
    	return result;
    }
    
    
    /**
     * 根据videoid获取视频详情
     *
     * @param token，videoId, userId
     * @return result
     */
    @RequestMapping(value = "/console/vod/getVideoById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult  getVideoById(String consoleToken, String videoId) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {} ", traceId, videoId);
        ResponseResult result = new ResponseResult(traceId);
        List<String> fileUrlList = new ArrayList<String>();
        List<String> snapshotList = new ArrayList<String>();
        try {
        	PersonageVideo video = vodService.getVideoById(videoId);
        	
            List<VideoSnapshot> videoSnapshotList = vodService.getSnapshotById(videoId);
            List<VideoFile> videoFileList = vodService.getFileById(videoId);
            for(int i = 0;i < videoSnapshotList.size();i ++){
            	snapshotList.add(videoSnapshotList.get(i).getSnapshotUrl());
            }
            for(int i = 0;i < videoFileList.size();i ++){
            	fileUrlList.add(videoFileList.get(i).getFileUrl());
            }        
            video.setFileUrlList(fileUrlList);
            video.setSnapshotList(snapshotList);
        	result.setData(video);
            result.setMessage("根据videoid获取视频详情完成！");
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 根据videoid获取视频详情异常", e);
        }
        return result;
    }
    /**
     * 推荐视频
     *
     * @param token, userId, videoId,  title,  description,  duration,  coverUrl,  
     * size,  tags,  cateId,  cateName, firstFrameUrl, censorStatus, snapshotStatus,
     *  transcodeStatus, transcodeStatus, isNarrow, isCache
     * @return result
     */
    @RequestMapping(value = "/console/vod/recommendVideo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult videoPublish(String consoleToken, String userId,String videoId, String title, String description, String duration,
                                       String coverUrl, String size, String tags, String cateId, String cateName, String firstFrameUrl,
                                       String censorStatus, String snapshotStatus, String transcodeStatus,String isNarrow, String isCache) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {}, title: {},description: {}  ", traceId, videoId, title, description);

        ResponseResult result = new ResponseResult(traceId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            BaseUserProfile baseUserProfile = new BaseUserProfile();
            baseUserProfile.setUserId(userId);
            //配置参数
            RecommendVideo recommendVideo = new RecommendVideo();
            PersonageVideo personageVideo = new PersonageVideo();
            recommendVideo.setUser(baseUserProfile);
            if(StringUtils.isNotEmpty(duration)){
            	recommendVideo.setDuration(Float.parseFloat(duration));
            }
            if(StringUtils.isNotEmpty(size)){
            	recommendVideo.setSize(Integer.parseInt(size));
            }
            if(StringUtils.isNotEmpty(cateId)){
            	recommendVideo.setSize(Integer.parseInt(cateId));
            }
            recommendVideo.setVideoId(videoId);
            recommendVideo.setTitle(title);
            if(StringUtils.isNotEmpty(description)){
            	recommendVideo.setDescription(URLEncoder.encode(description, "utf-8"));
            }
            recommendVideo.setCoverUrl(coverUrl);
            recommendVideo.setCreationTime(format.format(System.currentTimeMillis()));
            recommendVideo.setFirstFrameUrl(firstFrameUrl);
            recommendVideo.setCensorStatus(censorStatus);
            recommendVideo.setTags(tags);
            recommendVideo.setCateName(cateName);
            recommendVideo.setTranscodeStatus(transcodeStatus);
            recommendVideo.setSnapshotStatus(snapshotStatus);

            //推荐视频之后，更新播放地址，提交窄带高清转码，预热缓存
            GetPlayInfoResponse playInfoResponse = VodOpenAPI.getPlayInfo(videoId);
        	String filrUrl = playInfoResponse.getPlayInfoList().get(0).getPlayURL().replace("https://alivc-demo-vod.aliyuncs.com/","");
        	recommendVideo.setFileUrl(filrUrl);
            if(StringUtils.isNotEmpty(isNarrow) && "true".equals(isNarrow)){
            	PersonageVideo video = vodService.getVideoById(videoId);
            	String content = video.getUser().getUserId();
            	SubmitTranscodeJobsResponse transcodeJobsResponse = VodOpenAPI.submitTranscodeJobs(videoId, ConfigMapUtil.getValueByKey("TAB_TEMPLATEGROUP_ID"),content);
    			TranscodeRequest transcodeRequest = new TranscodeRequest();
    			if(transcodeJobsResponse.getTranscodeJobs().size() > 0){
    				transcodeRequest.setJobId(transcodeJobsResponse.getTranscodeJobs().get(0).getJobId());
    			}
    			transcodeRequest.setTranscodeType("tab");
    			transcodeRequest.setCreateTime(format.format(System.currentTimeMillis()));
    			vodService.insertTranscodeRequest(transcodeRequest);
            	personageVideo.setNarrowTranscodeStatus("onTranscode");
            }
            
            if(StringUtils.isNotEmpty(isCache) && "true".equals(isCache)){
            	VodOpenAPI.pushObjectCache(firstFrameUrl);
            }
            vodService.insertRecommendVideo(recommendVideo);
            personageVideo.setVideoId(videoId);
            personageVideo.setIsRecommend("true");
            vodService.updateVideo(personageVideo);
            result.setMessage("插入推荐视频成功！");
            
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
     * 删除视频
     *
     * @param token，videoId, userId
     * @return result
     */
    @RequestMapping(value = "/console/vod/deleteVideoById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteVideoById(String consoleToken, String videoId, String userId) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {} ,userId: {}", traceId, videoId, userId);
        ResponseResult result = new ResponseResult(traceId);
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
    
    /**
     * 根据条件查询推荐视频列表
     *
     * @param token，String videoId,
    		 userId, title, startTime, endTime, userName, censorStatus
     * @return result
     */
    @RequestMapping(value = "/console/vod/getRecommendVideos", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getRecommendVideos(String consoleToken, int pageIndex, int pageSize,String videoId,
    		String userId, String title, String startTime, String endTime, String userName, String censorStatus) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {}", traceId, videoId);

        ResponseResult result = new ResponseResult(traceId);
        VideosParam videosParam  = new VideosParam();
        videosParam.setPageIndex(pageIndex-1);
        videosParam.setPageSize(pageSize);
        videosParam.setVideoId(videoId);
        videosParam.setUserId(userId);
        videosParam.setTitle(title);
        videosParam.setStartTime(startTime);
        videosParam.setEndTime(endTime);
        videosParam.setCensorStatus(censorStatus);
        videosParam.setUserName(userName);
        List<RecommendVideo> videoList = new ArrayList<RecommendVideo>();
        
         try {
                Map<String, Object> map = new HashMap<String, Object>(2);
                videoList = vodService.getRecommendVideos(videosParam);
                for(int i = 0;i < videoList.size();i ++){
                	if(StringUtils.isNotEmpty(videoList.get(i).getDescription())){
                		videoList.get(i).setDescription(URLDecoder.decode(videoList.get(i).getDescription(), "utf-8"));
                    }
                }
                int total = vodService.selectReommendNums();
                int nowTotal = vodService.selectVideoReommendNums(videosParam);
                
                map.put("total", total);
                map.put("nowTotal", nowTotal);
                map.put("videoList", videoList);
                result.setData(map);
            } catch (Exception e) {
                result.setResult("false");
                result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
                result.setMessage("业务异常");
                result.setData(null);
                LOG.error("--- 获取推荐视频列表异常", e);
            }
            return result;
    }
    
    /**
     * 预热缓存
     *
     * @param 
     * @return result
     */
    @RequestMapping(value = "/console/vod/pushObjectCache", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult pushObjectCache(String consoleToken, String objectPath) {
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	LOG.info("traceId: {} ", traceId);
    	ResponseResult result = new ResponseResult(traceId);
    	try {
    		VodOpenAPI.pushObjectCache(objectPath);
    		result.setMessage("预热缓存完成！");
    	} catch (Exception e) {
    		result.setResult("false");
    		result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
    		result.setMessage("业务异常");
    		result.setData(null);
    		LOG.error("--- 预热缓存异常", e);
    	}
    	return result;
    }
    
    /**
     * 根据videoId获取推荐视频详情
     *
     * @param token，videoId, userId
     * @return result
     */
    @RequestMapping(value = "/console/vod/getRecommendVideoById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult  getRecommendVideoById(String consoleToken, String videoId) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {} ", traceId, videoId);
        ResponseResult result = new ResponseResult(traceId);
        try {
        	RecommendVideo video = vodService.getRecommendVideoById(videoId);
        	result.setData(video);
            result.setMessage("根据videoId获取视频详情完成！");
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 根据videoiId获取视频详情异常", e);
        }
        return result;
    }
    /**
     * 取消推荐
     *
     * @param token，videoId, userId
     * @return result
     */
    @RequestMapping(value = "/console/vod/deleteRecommendById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteRecommendById(String consoleToken, String videoId, String userId) {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("traceId: {} ,videoId: {} ,userId: {}", traceId, videoId, userId);
        ResponseResult result = new ResponseResult(traceId);
        try {
            vodService.deleteRecommendById(videoId, userId);
            PersonageVideo personageVideo = new PersonageVideo();
    		personageVideo.setVideoId(videoId);
    		personageVideo.setIsRecommend("false");
    		vodService.updateVideo(personageVideo);
            result.setMessage("取消推荐完成！");
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 取消推荐异常", e);
        }
        return result;
    }
    
}