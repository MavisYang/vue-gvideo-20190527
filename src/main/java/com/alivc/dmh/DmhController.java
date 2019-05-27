package com.alivc.dmh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alivc.base.ConstanData;
import com.alivc.base.OpenApiDig;
import com.alivc.base.ResponseResult;
import com.alivc.base.TraceIdContext;
import com.alivc.dmh.pojo.MusicInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
 * ClassName: DmhController <br/> 
 * Function: 太和音乐管理控制器. <br/> 
 * Reason:   太和音乐管理. <br/> 
 * Date:     2018年3月5日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@RestController
public class DmhController {
    
    private static final Logger LOG = LoggerFactory.getLogger(DmhController.class);
	/**
	 * 获取热门歌曲
	 * @param  
	 * @return result
	 */
   
	@RequestMapping(value = "/music/getRecommendMusic", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getHotMusic(String pageNo, String pageSize) {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ",traceId);
		ResponseResult result = new ResponseResult(traceId);
		Map<String, Object> dataMap = new HashMap<String, Object>(2);
		List<MusicInfo> musicList = new ArrayList<MusicInfo>();
	    try {
	    	String[] resultData ;
	    	String total = "";
	    		Map<String, String> paramsHot = new HashMap<String, String>(2);
				paramsHot.put("pageNo", pageNo);
				paramsHot.put("pageSize", pageSize);
				resultData = OpenApiDig.getResultData("/TRACKLIST/songGetHot.json", paramsHot);
	    	String dataResult = resultData[0];
			JSONObject jsonDataResult = JSONObject.parseObject(dataResult);
			if(StringUtils.isNotEmpty(jsonDataResult.getString("state")) && "true".equals(jsonDataResult.getString("state"))){
				String data = jsonDataResult.getString("data");
				JSONObject jsonData = JSONObject.parseObject(data);
				total = jsonData.getString("total");
				JSONArray jsonArray = jsonData.getJSONArray("result");
				
				for(int i = 0;i < jsonArray.size(); i ++){
					MusicInfo musicInfo = new MusicInfo();
					musicInfo.setArtistName(jsonArray.getJSONObject(i).containsKey("artist") ? jsonArray.getJSONObject(i).getJSONArray("artist").getJSONObject(0).getString("name") : "");
					musicInfo.setDuration(jsonArray.getJSONObject(i).getString("duration"));
					musicInfo.setTitle(jsonArray.getJSONObject(i).getString("title"));
					musicInfo.setMusicId(jsonArray.getJSONObject(i).getString("TSID"));
					musicInfo.setSource("TaiHe");
					musicInfo.setImage(jsonArray.getJSONObject(i).getString("pic"));
					musicList.add(musicInfo);
				}
    	}
			
			dataMap.put("musicList", musicList);
			dataMap.put("total", total);
            result.setData(dataMap);
        } catch (Exception e) {
            result.setResult("false");
            result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
            result.setMessage("业务异常");
            result.setData(null);
            LOG.error("--- 获取热门歌曲", e);
        }
        return result;
    
    }
	/**
	 * 根据音乐id获取播放地址
	 * 
	 * @param  
	 *             
	 * @return result
	 */
	
	@RequestMapping(value = "/music/getPlayPath", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getPlayPath(String musicId) {
		String traceId = TraceIdContext.ctx.get().getTraceId();
		LOG.info("traceId: {} ",traceId);
		ResponseResult result = new ResponseResult(traceId);
		Map<String, Object> dataMap = new HashMap<String, Object>(2); 
		try {
				Map<String, String> paramsPlay = new HashMap<String, String>(3);
				paramsPlay.put("TSID", musicId);
				paramsPlay.put("rate", "64");
				paramsPlay.put("https", "1");
				String [] resultPlayData = OpenApiDig.getResultData("/SONG/trackLink.json", paramsPlay);
				JSONObject jsonDataPlayResult = JSONObject.parseObject(resultPlayData[0]);
				dataMap.put("playPath", jsonDataPlayResult.containsKey("data") ?  jsonDataPlayResult.getJSONObject("data").getString("path") : "");
				dataMap.put("expireTime", jsonDataPlayResult.containsKey("data") ? jsonDataPlayResult.getJSONObject("data").getString("expireTime") : "");
			result.setData(dataMap);
		} catch (Exception e) {
			result.setResult("false");
			result.setCode(ConstanData.INTERNAL_SERVER_ERROR);
			result.setMessage("业务异常");
			result.setData(null);
			LOG.error("--- 根据音乐id获取播放地址", e);
		}
		return result;
		
	}
	 


}
