package com.alivc.vod.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.alivc.base.ConfigMapUtil;
import com.alivc.base.VodOpenAPI;
import com.alivc.vod.pojo.PersonageVideo;
import com.alivc.vod.pojo.RecommendVideo;
import com.alivc.vod.pojo.TranscodeRequest;
import com.alivc.vod.pojo.VideoFile;
import com.alivc.vod.pojo.VideoSnapshot;
import com.alivc.vod.service.VodVideoService;
import com.aliyuncs.vod.model.v20170321.SubmitTranscodeJobsResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: VodCallBackController <br/>
 * Function: 回调事件控制器. <br/>
 * Reason: 回调通知事件. <br/>
 * Date: 2018年1月3日 <br/>
 *
 * @author tz
 * @version v0.0.1
 * @since JDK 1.8
 * @see
 */
@RestController
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class VodCallBackController {

	private static final Logger LOG = LoggerFactory.getLogger(VodCallBackController.class);
	@Autowired
	private VodVideoService vodService;


	/**
	 * 智能审核完成
	 */
	private static final String AIVIDEOCENSORCOMPLETE = "AIVideoCensorComplete";
	/**
	 * 截图完成
	 */

	private static final String SNAPSHOTCOMPLETE = "SnapshotComplete";
	/**
	 * 转码完成
	 */
	private static final String TRANSCODECOMPLETE = "TranscodeComplete";

	/**
	 * 回调通知事件（视频点播）
	 *
	 * @param callbackMessage
	 * @return result
	 * @throws Exception
	 */
	@RequestMapping(value = "/vodcallback/callback", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> callback(@RequestBody(required = false) String callbackMessage,HttpServletRequest request,
														@RequestHeader("X-VOD-TIMESTAMP") String vodTimestamp,
														@RequestHeader("X-VOD-SIGNATURE") String vodSignature) throws Exception {
		LOG.info("callbackMessage: {}", callbackMessage);
		Map<String, Object> map = new HashMap<String, Object>(1);
		if (!isLegal(vodSignature, vodTimestamp)) {
			map.put("message", "非法请求！");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.FORBIDDEN);
		}

		if (StringUtils.isNotEmpty(callbackMessage)) {
			JSONObject result = JSONObject.parseObject(callbackMessage);
			if (result != null) {
				String eventType = result.getString("EventType");
				if (null != eventType) {
					if (AIVIDEOCENSORCOMPLETE.equals(eventType)) {
						this.censorHandler(result);
						LOG.debug(AIVIDEOCENSORCOMPLETE);
						map.put("message", "审核完成回调处理完成");
						return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
					}

					if (SNAPSHOTCOMPLETE.equals(eventType)) {
						this.snapshotCompleteHandler(result);
						LOG.debug(SNAPSHOTCOMPLETE);
						map.put("message", "截图完成回调处理完成");
						return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
					}

					if (TRANSCODECOMPLETE.equals(eventType)) {
						this.transcodeCompleteHandler(result);
						LOG.debug(TRANSCODECOMPLETE);
						map.put("message", "转码完成回调处理完成");
						return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
					}
				}
			}
		}
		map.put("message", "参数为空！");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 审核完成回调处理逻辑
	 *
	 * @param result
	 */

	private void censorHandler(JSONObject result) {
		JSONObject resultData = result.getJSONObject("Data");
		String status = result.getString("Status");
		String mediaId = result.get("MediaId").toString();
		PersonageVideo personageVideo = new PersonageVideo();
		personageVideo.setVideoId(mediaId);
		try {
		// 判断作业状态
		if (StringUtils.isNotBlank(status) && "success".equals(status)) {
			String suggestion = resultData.get("Suggestion").toString();
			// 根据审核命中标签，判断是否审核通过
			if (StringUtils.isNotBlank(suggestion) && "pass".equals(suggestion)) {
				personageVideo.setCensorStatus("success");
				vodService.updateVideo(personageVideo);
				personageVideo.setTranscodeStatus("onTranscode");
				// 智能审核成功后，人工审核，提交非窄带高清转码
				VodOpenAPI.createAudit(mediaId, "Normal","审核通过", "视频无异样，判断为正常视频");
				PersonageVideo video = vodService.getVideoById(mediaId);
				String content=video.getUser().getUserId();
				SubmitTranscodeJobsResponse transcodeJobsResponse = VodOpenAPI.submitTranscodeJobs(mediaId, ConfigMapUtil.getValueByKey("TEMPLATEGROUP_ID").trim(),content);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TranscodeRequest transcodeRequest = new TranscodeRequest();
				if(transcodeJobsResponse.getTranscodeJobs().size() > 0){
					transcodeRequest.setJobId(transcodeJobsResponse.getTranscodeJobs().get(0).getJobId());
				}
				transcodeRequest.setTranscodeType("common");
				transcodeRequest.setCreateTime(format.format(System.currentTimeMillis()));
				vodService.insertTranscodeRequest(transcodeRequest);
			} else if (StringUtils.isNotBlank(suggestion) && "block".equals(suggestion)){
				String abnormalModules = resultData.get("AbnormalModules").toString();
				String label = resultData.get("Label").toString();
				VodOpenAPI.createAudit(mediaId, "Blocked","审核不通过", "审核结果类别："+label+"/违规内容："+abnormalModules);
				personageVideo.setCensorStatus("fail");
				vodService.updateVideo(personageVideo);
			}else if(StringUtils.isNotBlank(suggestion) && "review".equals(suggestion)){
				personageVideo.setCensorStatus("check");
				vodService.updateVideo(personageVideo);
			}
			
		} else {
			personageVideo.setCensorStatus("fail");
			vodService.updateVideo(personageVideo);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.info("审核回调处理异常！");
		}
		LOG.info("审核回调处理完成！");
	}
	/**
	 * 转码回调处理逻辑
	 *
	 * @param result
	 */

	private void transcodeCompleteHandler(JSONObject result) {
		String status = result.getString("Status");
		String videoId = result.getString("VideoId");
		JSONArray streamInfos = result.getJSONArray("StreamInfos");
		PersonageVideo personageVideo = new PersonageVideo();
		RecommendVideo recommendVideo = new RecommendVideo();
		try {
		if ("success".equals(status)) {
			String jobId = streamInfos.getJSONObject(0).getString("JobId");
			TranscodeRequest transcodeRequest = vodService.getTranscodeByJobId(jobId);
			if(StringUtils.isNotEmpty(transcodeRequest.getTranscodeType()) && "common".equals(transcodeRequest.getTranscodeType())){
				personageVideo.setTranscodeStatus("success");
			}else if(StringUtils.isNotEmpty(transcodeRequest.getTranscodeType()) && "tab".equals(transcodeRequest.getTranscodeType())){
				personageVideo.setNarrowTranscodeStatus("success");
			}
			RecommendVideo video = vodService.getRecommendVideoById(videoId);
			if(video != null){
				recommendVideo.setVideoId(videoId);
				recommendVideo.setNarrowTranscodeStatus("success");
				vodService.updateRecommendVideo(recommendVideo);
			}
			VideoFile videoFile = new VideoFile();
			for(int i = 0;i< streamInfos.size(); i++){
				videoFile.setFileUrl(streamInfos.getJSONObject(i).getString("FileUrl"));
				videoFile.setVideoId(videoId);
				vodService.addVideoFileUrl(videoFile);
			}
		} else {
			personageVideo.setTranscodeStatus("fail");
		}
		personageVideo.setVideoId(videoId);
		vodService.updateVideo(personageVideo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.info("转码回调处理异常！");
		}
		LOG.info("转码回调处理完成！");
	}

	/**
	 * 截图回调处理逻辑
	 *
	 * @param result
	 */
	private void snapshotCompleteHandler(JSONObject result) {
		String status = result.getString("Status");
		String videoId = result.getString("VideoId");
		String subType = result.getString("SubType");
		JSONArray snapshotInfos = result.getJSONArray("SnapshotInfos");
		try {
		if(StringUtil.isNotEmpty(subType) && "SpecifiedTime".equals(subType) && snapshotInfos.size()>0){
			String snapshotRegular = snapshotInfos.getJSONObject(0).getString("SnapshotRegular");
			PersonageVideo personageVideo = new PersonageVideo();
			personageVideo.setVideoId(videoId);
			int snapSize = Integer.parseInt(snapshotInfos.getJSONObject(0).getString("SnapshotCount"));
			// 截图成功
			if ("success".equals(status)) {
				if(snapSize > 4){
					VideoSnapshot videoSnapshot = new VideoSnapshot(); 
					for(int i = 1;i <= snapSize;i ++){
						videoSnapshot.setVideoId(videoId);
						videoSnapshot.setSnapshotUrl(snapshotRegular.replace("{SnapshotCount}", "0000"+i));
						vodService.addVideoSnapshot(videoSnapshot);
					}
					LOG.info("多张截图回调处理！");
				}else{
						JSONObject job = snapshotInfos.getJSONObject(0);  
						personageVideo.setFirstFrameUrl(job.getString("SnapshotRegular").replace("{SnapshotCount}", "0000"+job.getString("SnapshotCount")));
					LOG.info("首帧截图回调处理！");
					personageVideo.setSnapshotStatus("success");
					vodService.updateVideo(personageVideo);
				}
			} else if ("fail".equals(status)) {
				personageVideo.setSnapshotStatus("fail");
				vodService.updateVideo(personageVideo);
			}
			
		}else{
			LOG.info("非普通截图！");
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("截图回调处理异常！");
		}
		LOG.info("截图回调处理完成！");
	}

	private String getLocalSignature(String timestamp) {
		return DigestUtils.md5Hex(
				ConfigMapUtil.getValueByKey("CALLBACK_NAME") + "|" + timestamp + "|" + ConfigMapUtil.getValueByKey("CALLBACK_PRIVETEKEY"));
	}

	private boolean isLegal(String vodSignature, String vodTimestamp) {
		String localSignature = this.getLocalSignature(vodTimestamp);
		LOG.debug("localSignature:{} ,vodSignature:{} ", localSignature, vodSignature);
		if (StringUtils.isNotEmpty(vodSignature) && StringUtils.isNotEmpty(vodTimestamp)
			&& StringUtils.isNotEmpty(localSignature)) {
			return localSignature.equals(vodSignature);
		} else {
			return false;
		}
	}
	
	/**
	 * 构建审核内容
	 */
	public  String buildAuditContent(String videoId,String status,String reason) throws Exception {
	        List<JSONObject> auditContents = new ArrayList<JSONObject>();
	        JSONObject auditContent = new JSONObject();
	        auditContent.put("VideoId", videoId);  
	        auditContent.put("Status", status); 
	        auditContent.put("Reason", reason);  
	        auditContents.add(auditContent);
	        return auditContents.toString();
	}


}