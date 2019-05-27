module.exports = {
  // 设置前端开发时的代理
  devServer: {
    // proxy: 'https://example.com',    // 替换成你的接口域名地址, 后面不要加 '/'

    proxy:'http://gvideo.molimami.com:8080'
  },
  productionSourceMap: false,
  // 设置生产环境和发布环境时的静态资源路径
  publicPath: process.env.NODE_ENV === 'production'
    ? 'https://alivc-demo.aliyuncs.com/resource/'
    : '/',
}
