package com.alivc.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alivc.base.AkUtil;
import com.alivc.base.ConfigMapUtil;
import com.alivc.base.ConstanData;
import com.alivc.base.ResponseResult;
import com.alivc.base.TraceIdContext;
import com.alivc.base.VodOpenAPI;
import com.alivc.demo.pojo.Video;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.CreateUploadImageRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadImageResponse;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoListRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoListResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** 
 * ClassName: DemoController <br/> 
 * Function: 权限管理控制器. <br/> 
 * Reason:   权限管理. <br/> 
 * Date:     2018年1月3日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@RestController
public class DemoController {
    
    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);
    /**
	 * 获取sts
	 * 
	 * @param  
	 *             
	 * @return result
	 * @throws Exception 
	 */
   
	@RequestMapping(value = "/demo/getSts", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getSts() throws Exception {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ",traceId);
		ResponseResult result = new ResponseResult(traceId);
	    Map<String, String> stsMap = new HashMap<String, String>(4);
	    try {
            stsMap.put("expiration", AkUtil.getAkInfo().getString("Expiration"));
            stsMap.put("accessKeyId", AkUtil.getAkInfo().getString("AccessKeyId"));
            stsMap.put("accessKeySecret", AkUtil.getAkInfo().getString("AccessKeySecret"));
            stsMap.put("securityToken", AkUtil.getAkInfo().getString("SecurityToken"));
            result.setData(stsMap);
        } catch (ClientException e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 获取STS异常", e);
        }
        return result;
    
    }
	/**
	 * 获取图片上传地址和凭证
	 * 
	 * @param  
	 *                imageType,  imageExt,   title,  tags
	 * @return result
	 * @throws Exception 
	 */
	@RequestMapping(value = "/demo/getImageUploadAuth", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getImageUploadAuth(String imageType,String imageExt, String title,String tags) throws Exception {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ,imageType: {},imageType: {},title: {},tags: {}",traceId,imageType, imageExt,  title, tags);
		ResponseResult result = new ResponseResult(traceId);
	    Map<String, String> imageMap = new HashMap<String, String>(4);
	    CreateUploadImageResponse response = new CreateUploadImageResponse();
		try {
			CreateUploadImageRequest request = new CreateUploadImageRequest();
			request.setImageType(imageType);
			request.setImageExt(imageExt);
			request.setAppId(ConfigMapUtil.getValueByKey("APP_ID"));
			DefaultAcsClient defaultAcsClient = VodOpenAPI.initVodClient(
					AkUtil.getAkInfo().getString("AccessKeyId"), 
	    		      AkUtil.getAkInfo().getString("AccessKeySecret"),
	    		      AkUtil.getAkInfo().getString("SecurityToken"));
			response = defaultAcsClient.getAcsResponse(request);
			imageMap.put("uploadAddress", response.getUploadAddress());
			imageMap.put("uploadAuth", response.getUploadAuth());
			imageMap.put("imageURL", response.getImageURL());
			imageMap.put("imageId", response.getImageId());

			result.setData(imageMap);
		} catch (ClientException e) {
	        result.setResult("false");
	        result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
	        result.setMessage("业务异常");
	        result.setData(null);
	        LOG.error("--- 获取图片上传地址和凭证异常", e);
	    }
	    return result;
	}
	/**
	 * 获取视频上传地址和凭证
	 * 
	 * @param  
	 *             
	 * @return result
	 * @throws Exception 
	 */
	@RequestMapping(value = "/demo/getVideoUploadAuth", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getVideoUploadAuth(String title, String fileName, Long fileSize, String description,
											 String coverURL, String tags) throws Exception {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ,title: {},fileName: {},fileSize: {},description: {},coverURL: {},tags: {},",traceId,title, fileName,  fileSize, description, coverURL, tags);
		ResponseResult result = new ResponseResult(traceId);
	    Map<String, String> videoMap = new HashMap<String, String>(3);
	    CreateUploadVideoResponse  response = new CreateUploadVideoResponse ();
	    try {
	       
	            CreateUploadVideoRequest  request = new CreateUploadVideoRequest();
	            request.setTitle(title);
	            request.setFileName(fileName);
	            request.setFileSize(fileSize);
	            request.setDescription(description);
	            request.setCoverURL(coverURL);
	            request.setTags(tags);
	            request.setAppId(ConfigMapUtil.getValueByKey("APP_ID"));
	            DefaultAcsClient defaultAcsClient = VodOpenAPI.initVodClient(
						AkUtil.getAkInfo().getString("AccessKeyId"), 
		    		      AkUtil.getAkInfo().getString("AccessKeySecret"),
		    		      AkUtil.getAkInfo().getString("SecurityToken"));
	            response = defaultAcsClient.getAcsResponse(request);
	            videoMap.put("videoId", response.getVideoId());
	            videoMap.put("uploadAddress", response.getUploadAddress());
	            videoMap.put("uploadAuth", response.getUploadAuth());
	            result.setData(videoMap);
	        
	    } catch (ClientException e) {
	        result.setResult("false");
	        result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
	        result.setMessage("业务异常");
	        result.setData(null);
	        LOG.error("--- 获取视频上传地址和凭证异常", e);
	    }
	    return result;
	}
	
	/**
	 * 刷新视频上传地址和凭证
	 * 
	 * @param videoId
	 *             
	 * @return result
	 * @throws Exception 
	 */
	@RequestMapping(value = "/demo/refreshVideoUploadAuth", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult refreshVideoUploadAuth(String videoId) throws Exception {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ,videoId: {}",traceId,videoId);
	    ResponseResult result = new ResponseResult(traceId);
	    Map<String, String> newVideoMap = new HashMap<String, String>(2);
	    
	    RefreshUploadVideoResponse response = new RefreshUploadVideoResponse();
	    try {
	            RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
	            request.setVideoId(videoId);
	            DefaultAcsClient defaultAcsClient = VodOpenAPI.initVodClient(
						AkUtil.getAkInfo().getString("AccessKeyId"), 
		    		      AkUtil.getAkInfo().getString("AccessKeySecret"),
		    		      AkUtil.getAkInfo().getString("SecurityToken"));
	            response = defaultAcsClient.getAcsResponse(request);
	            newVideoMap.put("uploadAddress", response.getUploadAddress());
	            newVideoMap.put("uploadAuth", response.getUploadAuth());
	            result.setData(newVideoMap);
	    } catch (ClientException e) {
	        result.setResult("false");
	        result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
	        result.setMessage("业务异常");
	        result.setData(null);
	        LOG.error("--- 刷新视频上传地址和凭证异常", e);
	        LOG.error("---e.getLocalizedMessage() " + e.getLocalizedMessage(), e);
	    }
	    return result;
	}
	/**
	 * 获取视频播放凭证
	 * 
	 * @param videoId
	 *             
	 * @return result
	 * @throws Exception 
	 */
	@RequestMapping(value = "/demo/getVideoPlayAuth", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getVideoPlayAuth (String videoId) throws Exception {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ,videoId: {}",traceId,videoId);
		ResponseResult result = new ResponseResult(traceId);
		Map<String, String> videoPlayAuthMap = new HashMap<String, String>(2);
		GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
		try {
			GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
			request.setVideoId(videoId);
			DefaultAcsClient defaultAcsClient = VodOpenAPI.initVodClient(
					AkUtil.getAkInfo().getString("AccessKeyId"), 
	    		      AkUtil.getAkInfo().getString("AccessKeySecret"),
	    		      AkUtil.getAkInfo().getString("SecurityToken"));
			response = defaultAcsClient.getAcsResponse(request);
			videoPlayAuthMap.put("playAuth", response.getPlayAuth());
			videoPlayAuthMap.put("coverURL", response.getVideoMeta().getCoverURL());
			videoPlayAuthMap.put("videoId", response.getVideoMeta().getVideoId());
			videoPlayAuthMap.put("duration", response.getVideoMeta().getDuration().toString());
			result.setData(videoPlayAuthMap);
		} catch (ClientException e) {
			result.setResult("false");
			result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
			result.setMessage("业务异常");
			result.setData(null);
			LOG.error("--- 获取视频播放凭证异常", e);
			LOG.error("---e.getLocalizedMessage() " + e.getLocalizedMessage(), e);
		}
		return result;
	}
	/**
	 * 获取视频列表
	 * 
	 * @param videoId
	 *             
	 * @return result
	 * @throws Exception 
	 */
	@RequestMapping(value = "/demo/getVideoList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getVideoList (String status,String startTime,String endTime,
			String cateId,String storageLocation,String pageNo,String pageSize,String sortBy) throws Exception {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info(
			"traceId: {} ,params:status:{},startTime:{},endTime:{},cateId:{},storageLocation:{},pageNo:{},pageSize:{},"
				+ "sortBy:{}", status, startTime, endTime, cateId, storageLocation, pageNo, pageSize, sortBy, traceId);
		ResponseResult result = new ResponseResult(traceId);
		GetVideoListResponse response = new GetVideoListResponse();
		Map<String, Object> videosMap = new HashMap<String, Object>(2);
		List<Video> videoList = new ArrayList<Video>();
		try {
			GetVideoListRequest request = new GetVideoListRequest();
		    if(StringUtils.isNotEmpty(status)){
		    	request.setStatus(status);
            }
		    if(StringUtils.isNotEmpty(startTime)){
		    	request.setStartTime(startTime);
		    }
		    if(StringUtils.isNotEmpty(endTime)){
		    	request.setEndTime(endTime);
		    }
		    if(StringUtils.isNotEmpty(cateId)){
		    	request.setCateId(Long.parseLong(cateId));
		    }
		    if(StringUtils.isNotEmpty(storageLocation)){
		    	request.setStorageLocation(storageLocation);
		    }
		    if(StringUtils.isNotEmpty(pageNo)){
		    	request.setPageNo(Integer.parseInt(pageNo));
		    }
		    if(StringUtils.isNotEmpty(pageSize)){
		    	request.setPageSize(Integer.parseInt(pageSize));
		    }
		    if(StringUtils.isNotEmpty(sortBy)){
		    	request.setSortBy(sortBy);
		    }
		    DefaultAcsClient defaultAcsClient = VodOpenAPI.initVodClient(
					AkUtil.getAkInfo().getString("AccessKeyId"), 
	    		      AkUtil.getAkInfo().getString("AccessKeySecret"),
	    		      AkUtil.getAkInfo().getString("SecurityToken"));
		    response = defaultAcsClient.getAcsResponse(request);
			videosMap.put("total", response.getTotal());
			GetPlayInfoResponse getPlayInfoResponse = new GetPlayInfoResponse();
			
			for(int i = 0;i < response.getVideoList().size();i ++){
				Video video = new Video();
				video.setCateId(response.getVideoList().get(i).getCateId());
				video.setVideoId(response.getVideoList().get(i).getVideoId());
				video.setTitle(response.getVideoList().get(i).getTitle());
				video.setDuration(response.getVideoList().get(i).getDuration());
				video.setCoverUrl(response.getVideoList().get(i).getCoverURL());
				video.setCreateTime(response.getVideoList().get(i).getCreateTime());
				video.setDescription(response.getVideoList().get(i).getDescription());
				video.setModificationTime(response.getVideoList().get(i).getModificationTime());
				video.setModifyTime(response.getVideoList().get(i).getModifyTime());
				video.setStatus(response.getVideoList().get(i).getStatus());
				video.setStorageLocation(response.getVideoList().get(i).getStorageLocation());
				video.setTags(response.getVideoList().get(i).getTags());
				if(VodOpenAPI.getVideoInfo(response.getVideoList().get(i).getVideoId()).getVideo().getStatus().equals("Normal")){
					getPlayInfoResponse = VodOpenAPI.getPlayInfo(response.getVideoList().get(i).getVideoId());
					video.setFileUrl(getPlayInfoResponse.getPlayInfoList().get(0).getPlayURL());
					videoList.add(video);
				}
				
			}
			videosMap.put("videoList", videoList);
			result.setData(videosMap);
		} catch (ClientException e) {
			result.setResult("false");
			result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
			result.setMessage("业务异常");
			result.setData(null);
			LOG.error("--- 获取视频列表异常", e);
			LOG.error("---e.getLocalizedMessage() " + e.getLocalizedMessage(), e);
		}
		return result;
	}

	/**
	 * 根据videoId查询视频播放地址(从vod中获取视频播放地址)
	 *
	 * @param videoId
	 * @return result
	 */
	@RequestMapping(value = "/vodVideo/getVideoById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getVideoById(String videoId) {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ,videoId: {} ", traceId, videoId);
		ResponseResult result = new ResponseResult(traceId);
		try {
			GetPlayInfoResponse playInfoResponse = new GetPlayInfoResponse();
			if (StringUtils.isNotBlank(videoId)) {
				playInfoResponse = VodOpenAPI.getPlayInfo(videoId);
				result.setData(playInfoResponse);
				result.setMessage("操作完成！");
			}else{
				result.setMessage("videoId不能为空!");
			}
		} catch (Exception e) {
			result.setResult("false");
			result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
			result.setMessage("业务异常");
			result.setData(null);
			LOG.error("--- 查询视频播放地址异常", e);
		}
		return result;
	}
	/**
	 * 根据videoId获取视频播放凭证
	 *
	 * @param videoId
	 * @return result
	 */
	@RequestMapping(value = "/vodVideo/GetVideoPlayAuth", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getVideoPlayAuthById(String videoId) {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ,videoId: {} ", traceId, videoId);
		ResponseResult result = new ResponseResult(traceId);
		try {
			GetVideoPlayAuthResponse  playAuthResponse = new GetVideoPlayAuthResponse ();
			if (StringUtils.isNotBlank(videoId)) {
				playAuthResponse = VodOpenAPI.getVideoPlayAuth(videoId);
				result.setData(playAuthResponse);
				result.setMessage("操作完成！");
			}else{
				result.setMessage("videoId不能为空!");
			}
		} catch (Exception e) {
			result.setResult("false");
			result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
			result.setMessage("业务异常");
			result.setData(null);
			LOG.error("--- 根据videoId获取视频播放凭证", e);
		}
		return result;
	}
	
	
	/**
	 * 根据ak信息获取sts信息
	 * 
	 * @param  
	 *             
	 * @return result
	 */
	@RequestMapping(value = "/demo/getStsByAkInfo", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getStsByAkInfo(String accessKeyId,String accessKeySecret,String roleArn) {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ",traceId);
		ResponseResult result = new ResponseResult(traceId);
	    Map<String, String> stsMap = new HashMap<String, String>(4);

	    // 此处必须为 HTTPS
	    ProtocolType protocolType = ProtocolType.HTTPS;

	    try {
			IClientProfile profile = DefaultProfile.getProfile(ConfigMapUtil.getValueByKey("REGION_CN_HANGZHOU"), accessKeyId,
					accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);
	        final AssumeRoleRequest request = new AssumeRoleRequest();
	        request.setMethod(MethodType.POST);
	        request.setVersion(ConfigMapUtil.getValueByKey("STS_API_VERSION"));
	        request.setProtocol(protocolType);
	        request.setRoleArn(roleArn);
	        request.setRoleSessionName(ConfigMapUtil.getValueByKey("roleSessionName"));
	        request.setPolicy(ConfigMapUtil.getValueByKey("policy"));
	        final AssumeRoleResponse response = client.getAcsResponse(request);
            stsMap.put("expiration", response.getCredentials().getExpiration());
            stsMap.put("accessKeyId", response.getCredentials().getAccessKeyId());
            stsMap.put("accessKeySecret", response.getCredentials().getAccessKeySecret());
            stsMap.put("securityToken", response.getCredentials().getSecurityToken());
            result.setData(stsMap);
            LOG.info("RequestId: " + response.getRequestId());
             
        } catch (ClientException e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 获取STS异常", e);
        }
        return result;
    
    }




}
