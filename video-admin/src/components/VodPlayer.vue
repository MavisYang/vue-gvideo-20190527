<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="650px"
    @open="open"
    @close="close"> 
<!--   <div class='player-container'> -->
    <p class="video-description">{{description}}</p>
    <div class="player-container" id="player-container" v-loading="loading"></div>
  </el-dialog>
</template>
<script>

  /* loadByUrl */
  export default {
    props: ['title', 'vid', 'description'],
    data () {
      return {
        visible: false,
        player: null,
        loading: false,
      }
    },
    methods: {
      open() {
        let params = { videoId: this.vid }
        this.loading = true
        this.axios.get('/vodVideo/getVideoById', { params })
          .then(({data}) => {
            let playInfoList = data.data.playInfoList
            let source = {}
            playInfoList.forEach(item => {
              let sourceItem = {}
              source[item.definition] = item.playURL
            })
            source = JSON.stringify(source)
            // let source = playInfoList.find(item => item.definition === 'OD').playURL
            this.player = this.createPlayer(source)
          })
          .catch(this.serviceError)
          .finally(() => {
            this.loading = false
          })
      },
      close () {
        if (this.player) {
          this.player.dispose()
          // this.player.pause()
        }
      },
      createPlayer (source) {
        var player = new Aliplayer({
          "id": "player-container",
          "source": source,
          "width": "100%",
          "height": "400px",
          "autoplay": true,
        }, function (player) {});
        return player
      }
    }
  }
</script>
<style lang="scss">
  .player-container {
    min-height: 400px;
  }
  .video-description {
    font-size: 14px;
    color: #666;
    margin-bottom: 15px;
  }
</style>