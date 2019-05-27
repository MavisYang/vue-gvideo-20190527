<template>
  <el-dialog class="recommend" title="确定推荐吗?" :visible.sync="visible" @close="close" width="400px">
    <div>
      <p class="recomment-tip">选择推荐视频选项</p>
      <el-checkbox v-model="isNarrow" label="窄带高清" border></el-checkbox>
      <el-checkbox v-model="isCache" label="预热" border></el-checkbox>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取 消</el-button>
      <el-button type="primary" @click="confirm" :loading="loading">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
  import qs from 'qs'

  export default {
    props: ['video'],
    data() {
      return {
        visible: false,
        isNarrow: true,
        isCache: true,
        loading: false,
      }
    },
    methods: {
      confirm() {
        this.loading = true
        let {
          title,
          videoId,
          description,
          duration,
          coverUrl,
          size,
          tags,
          cateId,
          cateName,
          firstFrameUrl,
          transcodeStatus,
          snapshotStatus,
          censorStatus,
        } = this.video
        let params = {
          title,
          videoId,
          description,
          duration,
          coverUrl,
          size,
          tags,
          cateId,
          cateName,
          firstFrameUrl,
          transcodeStatus,
          snapshotStatus,
          censorStatus,
        }
        params.userId = this.video.user.userId
        params.isNarrow = this.isNarrow
        params.isCache = this.isCache
        this.axios.post('/console/vod/recommendVideo', qs.stringify(params))
          .then(({data}) => {
            this.$message.success('推荐成功!')
            this.$emit('loadData')
            this.visible = false
          })
          .catch(this.serviceError)
          .finally(() => {
            this.loading = false
          })
      },
      close() {
        this.isNarrow = true
        this.isCache = true
      }
    }
  }
</script>
<style lang="scss">
  .recommend {
    .el-dialog__body {
      padding-top: 10px;
      padding-bottom: 10px;
    }
  }
  .recomment-tip {
    font-size: 12px;
    margin-bottom: 15px;
  }
</style>