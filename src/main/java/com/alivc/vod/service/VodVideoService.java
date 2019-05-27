package com.alivc.vod.service;

import java.util.List;

import com.alivc.vod.pojo.PersonageVideo;
import com.alivc.vod.pojo.RecommendVideo;
import com.alivc.vod.pojo.TranscodeRequest;
import com.alivc.vod.pojo.VideoFile;
import com.alivc.vod.pojo.VideoSnapshot;
import com.alivc.vod.pojo.VideosParam;


/** 
 * ClassName: VodVideoService <br/> 
 * Function: TODO 视频service层. <br/> 
 * Reason:   TODO 用于视频相关功能的接口. <br/> 
 * Date:     2018年11月10日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
public interface VodVideoService {
	
	/**
	 * 插入视频
	 * @param personageVideo
	 * @return int
	 */
	public int insertVideo(PersonageVideo personageVideo);
	/**
	 * 插入推荐视频
	 * @param  recommendVideo
	 * @return int
	 */
	public  int insertRecommendVideo(RecommendVideo recommendVideo);
	/**
	 * 更新视频
	 * @param personageVideo
	 * @return int
	 */
	public int updateVideo(PersonageVideo personageVideo);
	/**
	 * 更新推荐视频
	 * @param recommendVideo
	 * @return int
	 */
	public int updateRecommendVideo(RecommendVideo recommendVideo);
	/**
	 * 获取个人中心视频
	 * @param 
	 * @return List<PersonageVideo>
	 */
	public List<PersonageVideo> getPersonageVideo(String userId,Integer id, int pageIndex, int pageSize);
	/**
	 * 获取推荐视频
	 * @param 
	 * @return List<RecommondVideo>
	 */
	public List<RecommendVideo> getRecommendVideo(Integer id, int pageIndex, int pageSize);
	 /**
     * 根据id获取视频
     * @return  PersonageVideo 
     * @param  videoId
     */
    public PersonageVideo  getVideoById(String videoId);
    /**
     * 根据id获取推荐视频详情
     * @return  RecommendVideo 
     * @param  videoId
     */
    public RecommendVideo  getRecommendVideoById(String videoId);
    /**
     * 获取个人中心视频条数
     * @return int
     * @param  
     */
    public  int selectPersonageNums(String userId);
    /**
	 * 根据条件获取视频条数
	 * @return int
	 * @param  videosParam
	 */
	public  int selectVideoNums(VideosParam videosParam);
    /**
     * 获取推荐视频条数
     * @return int
     * @param  
     */
    public  int selectReommendNums();
    /**
     * 根据条件获取推荐视频条数
     * @return int
     * @param  
     */
    public  int selectVideoReommendNums(VideosParam videosParam);
    /**
	 * 删除视频
	 * @return videoId
	 * @param  
	 */
	public  void deleteVideoById(String videoId,String userId);
	/**
	 * 删除推荐视频
	 * @return videoId
	 * @param  
	 */
	public  void deleteRecommendById(String videoId,String userId);
	/**
	 * 根据条件查询视频
	 * @param    videosParam 
	 * @return List<PersonageVideo>
	 */
	public List<PersonageVideo> getVideos(VideosParam videosParam );
	/**
	 * 根据条件查询推荐视频
	 * @param    videosParam 
	 * @return List<RecommendVideo>
	 */
	public List<RecommendVideo> getRecommendVideos(VideosParam videosParam );
	/**
     * 插入截图
     * @return int
     * @param  
     */
	public  int addVideoSnapshot(VideoSnapshot videoSnapshot);
	/**
     * 插入转码视频地址
     * @return int
     * @param  
     */
	public  int addVideoFileUrl(VideoFile videoFile);
	/**
     * 根据videoId获取视频截图列表信息
     * @param videoId
     * @return  List<VideoSnapshot>
     */
	public 	List<VideoSnapshot> getSnapshotById(String videoId);
	/**
	 * 根据videoId获取视频地址列表
	 * @param videoId
	 * @return  List<VideoFile>
	 */
	public 	List<VideoFile> getFileById(String videoId);
	/**
	 * 插入转码请求信息
	 * @param  transcodeRequest
	 * @return int
	 */
	public  int insertTranscodeRequest(TranscodeRequest transcodeRequest);
	/**
	 * 根据jobId查询转码请求信息
	 * @param jobId
	 * @return   
	 */
	public 	TranscodeRequest getTranscodeByJobId(String jobId);

}
