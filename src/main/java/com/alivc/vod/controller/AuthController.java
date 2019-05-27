package com.alivc.vod.controller;

import java.util.HashMap;
import java.util.Map;

import com.alivc.base.AkUtil;
import com.alivc.base.ConfigMapUtil;
import com.alivc.base.ConstanData;
import com.alivc.base.ResponseResult;
import com.alivc.base.TraceIdContext;
import com.alivc.base.VodOpenAPI;
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
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:StsController <br/>
 * Function: sts权限管理控制器. <br/>
 * Reason:   sts权限管理. <br/>
 * Date:     2018年12月10日  <br/>
 *
 * @author tz
 * @version v0.0.1
 * @see
 * @since JDK 1.8
 */
@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    

    /**
     * 获取sts
     *
     * @param token
     * @return result
     * @throws Exception 
     */

    @RequestMapping(value = "/vod/getSts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getSts(String token) throws Exception {
        String traceId = TraceIdContext.ctx.get().getTraceId();
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
     * @param token, imageType,  imageExt,   title,  tags
     * @return result
     * @throws Exception
     */
    @RequestMapping(value = "/vod/getImageUploadAuth", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getImageUploadAuth(String token, String imageType, String imageExt, String title, String
        tags)
        throws Exception {
        String traceId = TraceIdContext.ctx.get().getTraceId();
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
            LOG.info("traceId:{},uploadAddress:{},imageURL:{},imageId:{}",
                traceId, response.getUploadAddress(), response.getImageURL(), response.getImageId());
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
     * @param token
     * @return result
     * @throws Exception 
     */
    @RequestMapping(value = "/vod/getVideoUploadAuth", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getVideoUploadAuth(String token, String title, String fileName, Long fileSize,
                                             String description, String coverURL, String tags) throws Exception {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        ResponseResult result = new ResponseResult(traceId);
        Map<String, String> videoMap = new HashMap<String, String>(3);
        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
        try {

            CreateUploadVideoRequest request = new CreateUploadVideoRequest();
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
            LOG.info("traceId:{},uploadAddress:{},videoId:{}",
                traceId, response.getUploadAddress(), response.getVideoId());
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
     * @param videoId ,token
     * @return result
     * @throws Exception 
     */
    @RequestMapping(value = "/vod/refreshVideoUploadAuth", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult refreshVideoUploadAuth(String token, String videoId) throws Exception {
        String traceId = TraceIdContext.ctx.get().getTraceId();
        LOG.info("Begin refreshVideoUploadAuth : traceId:{},videoId:{}",traceId,videoId);
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
            LOG.info("traceId:{},uploadAddress:{},videoId:{}",
                traceId, response.getUploadAddress(), response.getVideoId());
        } catch (ClientException e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 刷新视频上传地址和凭证异常", e);
        }
        return result;
    }


}
