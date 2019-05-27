package com.alivc.vod.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alivc.base.TraceIdContext;
import com.alivc.vod.dao.VodVideoDao;
import com.alivc.vod.pojo.PersonageVideo;
import com.alivc.vod.pojo.RecommendVideo;
import com.alivc.vod.pojo.TranscodeRequest;
import com.alivc.vod.pojo.VideoFile;
import com.alivc.vod.pojo.VideoSnapshot;
import com.alivc.vod.pojo.VideosParam;
import com.alivc.vod.service.VodVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * ClassName: VodVideoServiceImpl <br/> 
 * Function: TODO 视频impl层. <br/> 
 * Reason:   TODO 用于实现视频service层的方法. <br/> 
 * Date:     2018年12月14日  <br/> 
 * @author   tz 
 * @version   v0.0.1
 * @since    JDK 1.8 
 * @see       
 */
@Service
public class VodVideoServiceImpl implements VodVideoService {
	private static final Logger LOG = LoggerFactory.getLogger(VodVideoServiceImpl.class);
	@Autowired
	private VodVideoDao vodVideoDao;
	
	/**
     * 视频发布
     * 
     * @param personageVideo
     *            
     * @return  int
     */
	@Override
	public int insertVideo(PersonageVideo personageVideo) {
	   
		return vodVideoDao.insert(personageVideo);
	}
	/**
	 * 插入推荐视频
	 * 
	 * @param recommendVideo
	 *            
	 * @return  int
	 */
	@Override
	public int insertRecommendVideo(RecommendVideo recommendVideo) {
		
		return vodVideoDao.insertRecommendVideo(recommendVideo);
	}

	/**
     * 获取个人中心视频
     * 
     * @param  
     *              pageIndex,   pageSize
     * @return List<PersonageVideo>
     */
    @Override
    public List<PersonageVideo> getPersonageVideo(String userId,Integer id, int pageIndex, int pageSize) {

        String traceId = TraceIdContext.ctx.get().getTraceId();

        List<PersonageVideo> videoList = new ArrayList<PersonageVideo>();
        try {
            videoList = vodVideoDao.getPersonageVideo(userId, id, (pageIndex-1)*pageSize, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("获取个人中心视频异常!traceId:{},e:{}",traceId, e);
        }
        LOG.error("traceId:{},pageIndex:{},pageSize,{} ,videoList.size:{}",traceId,pageIndex,pageSize,videoList.size());
        return videoList;
    }


    /**
     * 获取推荐视频
     * 
     * @param  
     *               pageIndex,   pageSize
     * @return List<RecommendVideo>
     */
    @Override
    public List<RecommendVideo> getRecommendVideo(Integer id, int pageIndex, int pageSize) {
        List<RecommendVideo> videoList = new ArrayList<RecommendVideo>();
        try {
            videoList = vodVideoDao.getRecommendVideo(id, (pageIndex-1)*pageSize, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---获取推荐视频异常", e);
        }
        return videoList;
        }

    @Override
    public PersonageVideo getVideoById(String videoId) {
        PersonageVideo video = new PersonageVideo();
        try {
            video = vodVideoDao.getVideoById(videoId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---根据id获取视频异常", e);
        }
        return video;
    }
    @Override
    public RecommendVideo getRecommendVideoById(String videoId) {
    	RecommendVideo video = new RecommendVideo();
    	try {
    		video = vodVideoDao.getRecommendVideoById(videoId);
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOG.error("---根据id获取视频异常", e);
    	}
    	return video;
    }

    @Override
    public int selectPersonageNums(String userId) {
        int count = 0 ;
        try {
            count = vodVideoDao.selectPersonageNums(userId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---获取个人中心视频总条数异常", e);
        }
        return count;
    }
    @Override
    public int selectVideoNums(VideosParam videosParam) {
    	int count = 0 ;
    	try {
    		count = vodVideoDao.selectVideoNums(videosParam);
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOG.error("---根据条件获取视频总条数异常", e);
    	}
    	return count;
    }
    
    @Override
    public int selectVideoReommendNums(VideosParam videosParam) {
    	int count = 0 ;
    	try {
    		count = vodVideoDao.selectVideoReommendNums(videosParam);
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOG.error("---根据条件获取推荐视频总条数异常", e);
    	}
    	return count;
    }

    @Override
    public int selectReommendNums() {
        int count = 0 ;
        try {
            count = vodVideoDao.selectReommendNums();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---获取推荐视频总条数异常", e);
        }
        return count;
 
    }

	@Override
	public void deleteVideoById(String videoId,String userId) {
        try {
             vodVideoDao.deleteVideoById(videoId,userId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---删除视频异常", e);
        }
 
    }
	@Override
	public void deleteRecommendById(String videoId,String userId) {
		try {
			vodVideoDao.deleteRecommendById(videoId,userId);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("---删除推荐视频异常", e);
		}
		
	}
	@Override
	public int updateVideo(PersonageVideo personageVideo) {
		int update = 0;
		try {
			update = vodVideoDao.updateVideo(personageVideo);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("---更新视频异常", e);
		}
		return update;
		
	}
	@Override
	public int updateRecommendVideo(RecommendVideo recommendVideo) {
		int update = 0;
		try {
			update = vodVideoDao.updateRecommendVideo(recommendVideo);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("---更新推荐视频异常", e);
		}
		return update;
		
	}

	/**
     * 根据条件获取视频
     * 
     * @param  
     *               videosParam 
     * @return List<PersonageVideo>
     */
    @Override
    public List<PersonageVideo> getVideos(VideosParam videosParam ) {

        String traceId = TraceIdContext.ctx.get().getTraceId();
        videosParam.setPageIndex(videosParam.getPageIndex()*videosParam.getPageSize());
        List<PersonageVideo> videoList = new ArrayList<PersonageVideo>();
        try {
            videoList = vodVideoDao.getVideos(videosParam);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("根据条件获取视频异常!traceId:{},e:{}",traceId, e);
        }
        LOG.error("traceId:{},videoId,{} ",traceId, videosParam .getVideoId());
        return videoList;
    }
    /**
     * 根据条件获取推荐视频
     * 
     * @param  
     *               videosParam 
     * @return List<RecommendVideo>
     */
    @Override
    public List<RecommendVideo> getRecommendVideos(VideosParam videosParam ) {
    	
    	String traceId = TraceIdContext.ctx.get().getTraceId();
    	videosParam.setPageIndex(videosParam.getPageIndex()*videosParam.getPageSize());
    	List<RecommendVideo> videoList = new ArrayList<RecommendVideo>();
    	try {
    		videoList = vodVideoDao.getRecommendVideos(videosParam);
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOG.error("根据条件获取推荐视频异常!traceId:{},e:{}",traceId, e);
    	}
    	LOG.error("traceId:{},videoId,{} ",traceId, videosParam .getVideoId());
    	return videoList;
    }
	@Override
	public int addVideoSnapshot(VideoSnapshot videoSnapshot) {
		int addSnapshot = 0;
		try {
			addSnapshot = vodVideoDao.addVideoSnapshot(videoSnapshot);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("---插入截图异常", e);
		}
		return addSnapshot;
	}
	@Override
	public int addVideoFileUrl(VideoFile videoFile) {
		int addFileUrl = 0;
		try {
			addFileUrl = vodVideoDao.addVideoFileUrl(videoFile);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("---插入视频地址异常", e);
		}
		return addFileUrl;
	}
	/**
     * 根据videoId获取视频截图列表信息
     * @param videoId
     * @return  List<VideoSnapshot>
     */
	@Override
	public 	List<VideoSnapshot> getSnapshotById(String videoId){
		List<VideoSnapshot> snapshotList = new ArrayList<VideoSnapshot>();
		try {
			snapshotList = vodVideoDao.getSnapshotById(videoId);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("---查询视频截图地址异常", e);
		}
		return snapshotList;
	}
	/**
	 * 根据videoId获取视频地址列表
	 * @param videoId
	 * @return  List<VideoFile>
	 */
	@Override
	public 	List<VideoFile> getFileById(String videoId) {
		List<VideoFile> fileList = new ArrayList<VideoFile>();
		try {
			fileList = vodVideoDao.getFileById(videoId);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("---查询视频地址异常", e);
		}
		return fileList;
	}
	/**
	 * 插入推荐视频
	 * 
	 * @param transcodeRequest
	 *            
	 * @return  int
	 */
	@Override
	public int insertTranscodeRequest(TranscodeRequest transcodeRequest) {
		
		return vodVideoDao.insertTranscodeRequest(transcodeRequest);
	}

	@Override
    public TranscodeRequest getTranscodeByJobId(String jobId) {
		TranscodeRequest transcodeRequest = new TranscodeRequest();
        try {
        	transcodeRequest = vodVideoDao.getTranscodeByJobId(jobId);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("---根据jobId获取转码请求信息异常", e);
        }
        return transcodeRequest;
    }
}
