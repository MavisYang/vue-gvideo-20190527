<template>
  <div class="video-list">
    <el-row class="operator-header" :gutter="15">
      <el-col :span="8">
        <el-input placeholder="输入查询内容" v-model="searchKeyWord" clearable>
          <el-select placeholder="选择查询条件" v-model="searchKey" slot="prepend" class="input-with-select">
            <el-option v-for="item in searchKeyOptions" :key="item.vlaue" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-input>
      </el-col>

<!--       <el-col :span="4">
        <el-select placeholder="选择审核状态" v-model="censorStatus" clearable>
          <el-option v-for="item in checkOptions" :key="item.vlaue" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-col> -->

      <el-col :span="9">
        <el-date-picker
          v-model="createTime"
          type="datetimerange"
          :picker-options="pickerOptions"
          range-separator="-"
          start-placeholder="推荐日期"
          end-placeholder="推荐日期"
          align="right">
        </el-date-picker>
      </el-col>

      <el-col :span="7" style="text-align: right;">
        <el-button type="primary" icon="el-icon-search" @click="loadData(1)">搜索</el-button>
      </el-col>

    </el-row>

    <el-table class="table" :data="list" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>

      <el-table-column label="资源名称" width="480" align="center">
        <template slot-scope="scope">
          <el-row>
            <el-col :span="9" class="video-img">
              <div class="video-img-container" @click="play(scope.row.title, scope.row.videoId, scope.row.description)">
                <img :src="scope.row.coverUrl || require('../../assets/images/cover_empty.png')" >
                <img :src="require('../../assets/images/play.png')" class="play-btn" />
              </div>
            </el-col>
            <el-col :span="15" class="video-info">
              <el-row class="video-title">{{scope.row.title}}</el-row>
              <el-row>
                <el-col :span="6">视频ID:</el-col>
                <el-col :span="18">{{scope.row.videoId}}</el-col>
              </el-row>
              <el-row>
                <el-col :span="6">视频时长:</el-col>
                <el-col :span="18">{{getVideoTime(scope.row.duration)}}</el-col>
              </el-row>
              <el-row>
                <el-col :span="6">视频大小:</el-col>
                <el-col :span="18">{{getFileSize(scope.row.size)}}</el-col>
              </el-row>
            </el-col>
          </el-row>
        </template>
      </el-table-column>

      <el-table-column label="用户名称"  align="center">
        <template slot-scope="scope">
          {{scope.row.user.userName}}
        </template>
      </el-table-column>

      <el-table-column label="审核状态"  align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.censorStatus === 'fail'" type="danger">审核不通过</el-tag>
          <el-tag v-else-if="scope.row.censorStatus === 'onCensor'">审核中</el-tag>
          <el-tag v-else-if="scope.row.censorStatus === 'check'" type="warning">待审核</el-tag>
          <el-tag v-else type="success">审核通过</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="窄带高清转码状态"  align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.narrowTranscodeStatus === 'fail'" type="danger">转码失败</el-tag>
          <el-tag v-else-if="scope.row.narrowTranscodeStatus === 'success'" type="success">转码成功</el-tag>
          <el-tag v-else>转码中</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="250"  align="center">
        <template slot-scope="scope">
          <el-button
            :loading="scope.row.warmLoading"
            type="primary"
            @click="warmVideo(scope.row)"
            :disabled="false">预热
          </el-button>

          <el-dropdown class="more-operator">
            <el-button>
              更多<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                v-if="scope.row.narrowTranscodeStatus === 'fail'"
                @click.native="transNarrowVideo(scope.row.videoId)">窄带高清转码</el-dropdown-item>
              <el-dropdown-item @click.native="deleteVideo(scope.row)">取消推荐</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>

    </el-table>

    <el-row class="main-pagination">
      <el-pagination
        @size-change="loadData(1)"
        @current-change="loadData"
        :current-page="page"
        :page-sizes="[5, 10, 20]"
        :page-size.sync="size"
        background
        layout="total, sizes, prev, pager, next"
        :total="total">
      </el-pagination>
    </el-row>
    <VodPlayer :title="videoTitle" :description="videoDescription" :vid="videoId" ref="vodPlayer" />
    <RecommentVideo @loadData="loadData" ref="dialogRecomment" :video="recommendVideo" />
  </div>
</template>
<script>
  import VodPlayer from '@/components/VodPlayer'
  import RecommentVideo from '@/components/RecommendVideo'

  import qs from 'qs'

  export default {
    components: {
      VodPlayer,
      RecommentVideo,
    },
    data() {
      return {
        searchKeyOptions: [{
          label: '视频ID',
          value: 'videoId'
        }, {
          label: '用户ID',
          value: 'userId'
        }, {
          label: '用户名称',
          value: 'userName'
        }, {
          label: '视频标题',
          value: 'title'
        }],
        searchKey: '',
        searchKeyWord: '',

        // censorStatus: '',
        checkOptions: [{
          label: '审核中',
          value: 'onCensor'
        }, {
          label: '审核通过',
          value: 'success'
        }, {
          label: '待审核',
          value: 'check'
        }, {
          label: '审核不通过',
          value: 'fail'
        }],

        createTime: '',
        pickerOptions: {
          shortcuts: [{
            text: '最近一天',
            onClick: this.pickerHandle()
          }, {
            text: '最近一周',
            onClick: this.pickerHandle(7)
          }, {
            text: '最近一个月',
            onClick: this.pickerHandle(30)
          }]
        },

        loading: false,
        list: [],

        page: 1,
        size: 5,
        total: 0,

        videoTitle: '',
        videoId: '',
        videoDescription: '',

        recommendVideo: null,
      }
    },
    created() {
      this.loadData()
    },
    methods: {
      loadData(page = this.page) {
        let params = {
          pageIndex: page,
          pageSize: this.size
        }
        if (this.searchKey && this.searchKeyWord) {
          params[this.searchKey] = this.searchKeyWord
        }
        // if (this.censorStatus) {
        //   params.censorStatus = this.censorStatus
        // }
        if (this.createTime) {
          params.startTime = this.getFormatDate(this.createTime[0])
          params.endTime = this.getFormatDate(this.createTime[1])
        }
        this.page = page
        this.loading = true
        this.axios.get('/console/vod/getRecommendVideos', { params })
          .then(({data}) => {
            let resData = data.data
            this.total = resData.nowTotal
            this.list = resData.videoList
          })
          .catch(this.serviceError)
          .finally(() => {
            this.loading = false
          })
      },
      deleteVideo(videoInfo) {
        this.$msgbox({
          title: '消息',
          message: '确定取消推荐吗?',
          type: 'warning',
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          beforeClose: (action, instance, done) => {
            if (action === 'confirm') {
              instance.confirmButtonLoading = true
              instance.confirmButtonText = '操作中...'
              this.handleDelete(videoInfo, () => {
                done()
                instance.confirmButtonLoading = false
              })
            } else {
              done()
            }
          }
        })
        .catch(() => {})
      },
      handleDelete(videoInfo, cb) {
        let { videoId } = videoInfo
        let { userId } = videoInfo.user
        this.axios.post('/console/vod/deleteRecommendById', qs.stringify({
          videoId,
          userId
        }))
          .then(({data}) => {
            this.$message.success('删除成功!')
            this.loadData()
          })
          .catch(this.serviceError)
          .finally(() => {
            cb && cb()
          })
      },
      warmVideo(videoInfo) {
        let params = {
          objectPath: 'https://alivc-demo-vod.aliyuncs.com/' + videoInfo.fileUrl
        }
        videoInfo.warmLoading = true
        this.axios.post('/console/vod/pushObjectCache', qs.stringify(params))
          .then(({data}) => {
            this.$message.success('预热成功!')
          })
          .catch(this.serviceError)
          .finally(() => {
            videoInfo.warmLoading = false
          })
      },
      transNarrowVideo(videoId) {
        this.axios.post('/console/vod/submitTabTranscode', qs.stringify({
          mediaId: videoId
        }))
          .then(({data}) => {
            this.$message.success('转码成功!')
          })
          .catch(this.serviceError)
      },
      play(title, vid, description) {
        this.videoTitle = title
        this.videoId = vid
        this.videoDescription = description
        this.$refs.vodPlayer.visible = true
      },
      pickerHandle(dayRange = 1) {
        let range = 3600 * 1000 * 24 * dayRange
        return function(picker) {
          const end = new Date();
          const start = new Date();
          start.setTime(start.getTime() - range);
          picker.$emit('pick', [start, end]);
        }
      }
    }
  }
</script>
