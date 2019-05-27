package com.alivc.vod.dao;

import java.util.List;

import com.alivc.user.pojo.AuthToken;
import com.alivc.vod.pojo.PersonageVideo;
import com.alivc.vod.pojo.RecommendVideo;
import com.alivc.vod.pojo.TranscodeRequest;
import com.alivc.vod.pojo.VideoFile;
import com.alivc.vod.pojo.VideoSnapshot;
import com.alivc.vod.pojo.VideosParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/** 
 * ClassName: VodVideoDao <br/> 
 * Function: TODO 视频管理的dao层. <br/> 
 * Reason:   TODO 视频管理的dao层. <br/> 
 * Date:     2018年12月17日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Mapper
public interface  VodVideoDao {
	
    /**
     * 插入视频
     * @return int
     * @param  personageVideo
     */
	public  int insert(PersonageVideo personageVideo);
	/**
	 * 插入推荐视频
	 * @return int
	 * @param  recommendVideo
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
	 * 获取个人中心视频条数
	 * @return int
	 * @param  userId
	 */
	public  int selectPersonageNums(@Param(value="userId")String userId);
	/**
	 * 根据条件获取视频条数
	 * @return int
	 * @param  videosParam
	 */
	public  int selectVideoNums(VideosParam videosParam);
	/**
     * 根据条件获取推荐视频条数
     * @return int
     * @param  videosParam
     */
    public  int selectVideoReommendNums(VideosParam videosParam);
	/**
	 * 删除视频
	 * @return  
	 * @param  videoId  userId
	 */
	public  void deleteVideoById(@Param(value="videoId")String videoId,@Param(value="userId")String userId);
	/**
	 * 删除推荐视频
	 * @return 
	 * @param  videoId userId
	 */
	public  void deleteRecommendById(@Param(value="videoId")String videoId,@Param(value="userId")String userId);
	/**
	 * 获取推荐视频条数
	 * @return int
	 * @param  
	 */
	public  int selectReommendNums();
	 /**
     * 获取个人中心视频
     * @return List<PersonageVideo>
     * @param  pageIndex, pageSize
     */
    public List<PersonageVideo> getPersonageVideo(@Param(value="userId")String userId,@Param(value="id")Integer id,@Param(value="pageIndex")int pageIndex, @Param(value="pageSize")int pageSize);
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
     * 获取推荐视频
     * @return List<RecommendVideo>
     * @param  id  pageIndex  pageSize
     */
    public List<RecommendVideo> getRecommendVideo(@Param(value="id")Integer  id, @Param(value="pageIndex")int pageIndex, @Param(value="pageSize")int pageSize);

	 /**
     * 根据条件获取视频
     * @return List<PersonageVideo>
     * @param  videosParam
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
     * @param   videoSnapshot
     */
	public  int addVideoSnapshot(VideoSnapshot videoSnapshot);
	/**
     * 插入转码视频地址
     * @return int
     * @param  videoFile
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
	 * @return   TranscodeRequest
	 */
	public 	TranscodeRequest getTranscodeByJobId(String jobId);
    

}
