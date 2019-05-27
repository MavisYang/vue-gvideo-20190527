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

      <el-col :span="4">
        <el-select placeholder="选择审核状态" v-model="censorStatus" clearable>
          <el-option v-for="item in checkOptions" :key="item.vlaue" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-col>

      <el-col :span="9">
        <el-date-picker
          v-model="createTime"
          type="datetimerange"
          :picker-options="pickerOptions"
          range-separator="-"
          start-placeholder="创建日期"
          end-placeholder="创建日期"
          align="right">
        </el-date-picker>
      </el-col>

      <el-col :span="3" style="text-align: right;">
        <el-button type="primary" icon="el-icon-search" @click="loadData(1)">搜索</el-button>
      </el-col>

    </el-row>

    <el-table class="table" :data="list" v-loading="loading">
      <el-table-column type="selection" width="50"></el-table-column>

      <el-table-column label="资源名称" width="480" align="center">
        <template slot-scope="scope">
          <el-row>
            <el-col :span="9" class="video-img">
              <div class="video-img-container" @click="play(scope.row)">
                <img :src="scope.row.coverUrl || require('../../assets/images/cover_empty.png')" >
                <img v-if="scope.row.censorStatus === 'success'" :src="require('../../assets/images/play.png')" class="play-btn" />
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

      <el-table-column label="截图" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" width="660">
            <el-row :gutter="10">
              <el-col v-for="item in scope.row.snapshotList" :span="6">
                <el-card shadow="never" class="screen-shot">
                  <img :src="item">
                </el-card>
              </el-col>
              <el-col class="empty-data" :span="24" v-show="scope.row.snapshotList && !scope.row.snapshotList.length">暂无截图</el-col>
            </el-row>
            <el-button slot="reference">显示截图</el-button>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="用户名称" align="center">
        <template slot-scope="scope">
          {{scope.row.user.userName}}
        </template>
      </el-table-column>

      <el-table-column label="审核状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.censorStatus === 'fail'" type="danger">审核不通过</el-tag>
          <el-tag v-else-if="scope.row.censorStatus === 'success'" type="success">审核通过</el-tag>
          <el-tag v-else-if="scope.row.censorStatus === 'check'" type="warning">待审核</el-tag>
          <el-tag v-else>审核中</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="转码状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.censorStatus === 'fail'" type="danger">转码失败</el-tag>
          <el-tag v-else-if="scope.row.censorStatus === 'success'" type="success">转码成功</el-tag>
          <el-tag v-else-if="scope.row.censorStatus === 'onTranscode'">转码中</el-tag>
          <el-tag v-else type="info">未转码</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            @click="recommend(scope.row)"
            :disabled="scope.row.isRecommend === 'true' || scope.row.censorStatus !== 'success'">{{ scope.row.isRecommend === 'true' ? '已推荐' : '推荐'}}
          </el-button>

<!--           <el-popover
            placement="top"
            class="delete-propper"
            width="160"
            v-model="scope.row.deleteVisible">
            <p class="delete-confirm-text">确定删除吗？</p>
            <div class="delete-confirm">
              <el-button size="mini" type="text" @click="scope.row.deleteVisible = false">取消</el-button>
              <el-button type="text" size="mini" @click="handleDelete(scope.row)">确定</el-button>
            </div>
            <el-button slot="reference" size="mini" type="danger" :loading="scope.row.deleteLoading">删除</el-button>
          </el-popover> -->

          <el-popover
            placement="top"
            class="delete-propper"
            width="160"
            v-model="scope.row.checkVisible">
            <p class="check-confirm-text">选择审核操作</p>
            <div class="check-confirm">
              <el-button plain type="primary" :loading="scope.row.ckeckPassLoading" @click="checkVideo(scope.row, 1)">通过</el-button>
              <el-button plain type="danger" @click="rejectVideo(scope.row)">屏蔽</el-button>
            </div>
            <el-button
              slot="reference"
              :disabled="scope.row.censorStatus === 'onCensor'"
              type="warning"
              :loading="scope.row.checkLoading">审核</el-button>
          </el-popover>

          <el-dropdown class="more-operator">
            <el-button>
              更多<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                v-if="scope.row.transcodeStatus === 'fail'"
                @click.native="transVideo(scope.row.videoId)">转码</el-dropdown-item>
              <el-dropdown-item @click.native="deleteVideo(scope.row)">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

          <!-- <el-button plain type="primary" :disabled="scope.row.censorStatus !== 'fail'">转码</el-button> -->
          <!-- <el-button type="warning" :disabled="scope.row.censorStatus !== 'fail'">审核</el-button> -->

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
    <VodPlayer :title="videoTitle" :vid="videoId" :description="videoDescription" ref="vodPlayer" />
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

        censorStatus: '',
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
        if (this.censorStatus) {
          params.censorStatus = this.censorStatus
        }
        if (this.createTime) {
          params.startTime = this.getFormatDate(this.createTime[0])
          params.endTime = this.getFormatDate(this.createTime[1])
        }
        this.page = page
        this.loading = true
        this.axios.get('/console/vod/getVideos', { params })
          .then(({data}) => {
            let resData = data.data
            this.total = resData.total
            this.list = resData.videoList
          })
          .catch(this.serviceError)
          .finally(() => {
            this.loading = false
          })
      },
      // 显示删除视频的对话框
      deleteVideo(videoInfo) {
        this.$msgbox({
          title: '消息',
          message: '确定删除吗?',
          type: 'warning',
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          beforeClose: (action, instance, done) => {
            if (action === 'confirm') {
              instance.confirmButtonLoading = true
              instance.confirmButtonText = '删除中...'
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
      // 删除接口
      handleDelete(videoInfo, cb) {
        let { videoId } = videoInfo
        let { userId } = videoInfo.user
        this.axios.post('/console/vod/deleteVideoById', qs.stringify({
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
      recommend(videoInfo) {
        this.recommendVideo = videoInfo
        this.$refs.dialogRecomment.visible = true
      },
      // 显示填写屏蔽的理由, 优化功能
      showCheckReject() {
        this.$prompt('请输入屏蔽理由', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputErrorMessage: '请不要超过128个字'
        }).then(({ value }) => {
          this.$message({

          })
        }).catch(() => {
          this.$message({

          })
        })
      },
      transVideo(videoId) {
        this.axios.post('/console/vod/submitTranscode', qs.stringify({
          mediaId: videoId
        }))
          .then(({data}) => {
            this.$message.success('转码成功!')
          })
          .catch(this.serviceError)
      },
      rejectVideo(videoInfo) {
        this.$msgbox({
          title: '消息',
          message: '确定屏蔽吗?',
          type: 'warning',
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          beforeClose: (action, instance, done) => {
            if (action === 'confirm') {
              instance.confirmButtonLoading = true
              instance.confirmButtonText = '执行中...'
              this.checkVideo(videoInfo, 0, () => {
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
      checkVideo(videoInfo, code, cb) {
        videoInfo.ckeckPassLoading = code === 1 && true
        let videoId = videoInfo.videoId
        let params = {
          mediaId: videoId
        }
        params.status = code === 1 ? 'Normal' : 'Blocked'
        this.axios.post('/console/vod/createAudit', qs.stringify(params))
          .then(({data}) => {
            videoInfo.checkVisible = false
            this.$message.success(data.message)
            this.loadData()
          })
          .catch(this.serviceError)
          .finally(() => {
            videoInfo.ckeckPassLoading = code === 1 && false;
            cb && cb();
          })
      },
      computeTranscodeStatus(code) {
        // 审核状态onCensor（审核中），success（审核成功），fail（审核不通过）
        let text = ''
        switch (code) {
          case 'onTranscode':
            text = '转码中';
            break;
          case 'success':
            text = '转码成功';
            break;
          case 'fail':
            text = '转码失败';
            break;
        }
        return text
      },
      play(videoInfo) {
        let { title, videoId, description, censorStatus} = videoInfo
        if (censorStatus !== 'success') {
          return
        }
        this.videoTitle = title
        this.videoId = videoId
        this.videoDescription = description
        this.$refs.vodPlayer.visible = true
      },
      pickerHandle(dayRange = 1) {
        console.log(dayRange,'dayRange')
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
<style lang="scss">
  .video-list {
    .el-button + .el-button {
      margin-left: 5px;
    }
  }
  .input-with-select .el-input {
    width: 120px;
  }
  .main-pagination {
    padding: 20px;
    .el-pagination {
      text-align: right;
    }
  }
  .video-img {
    .video-img-container {
      position: relative;
      width: 150px;
      height: 85px;
      background-color: #000;
    }
    img {
      max-width: 150px;
      max-height: 85px;
    }
    .play-btn {
      width: 50px;
      height: 50px;
      left:0px;
      right: 0px;
      top:0px;
      bottom: 0px;
      margin: auto;
      position: absolute;
      visibility: hidden;
    }
    &:hover {
      cursor:pointer;
      .play-btn {
        visibility: visible;
      }
    }
  }
  .video-info {
    text-align: left;
    font-size: 12px;
    color: #909399;
    line-height: 20px;
    .video-title {
      font-size: 14px;
      color: #303133;
      margin-bottom: 5px;
    }
  }

  .screen-shot {
    margin: 3px 0;
    .el-card__body {
      padding: 5px;
      display: flex;
      height: 85px;
      align-items: center;
      justify-content: center;
      img {
        max-width: 100%;
        max-height: 100%;
      }
    }
  }
  .empty-data {
    padding-top: 15px;
    padding-bottom: 15px;
    text-align: center;
    font-size: 12px;
    color: #999;
  }

  .more-operator {
    margin-left: 5px;
    .el-button {
      padding-left: 10px;
      padding-right: 10px;
    }
  }

  .delete-confirm-text {
    font-size: 12px;
  }
  .delete-confirm {
    text-align: right;
    margin: 0;
  }
  .delete-propper {
    margin-left: 5px;
  }

  .check-confirm-text {
    margin-bottom: 10px;
    font-size: 12px;
  }

  .check-confirm {
    text-align: right;
    margin: 0;
    .el-button {
      padding: 5px 12px;      
    }
  }
</style>
