# 项目说明

本项目是趣视频管理后台`web`前端项目, 用于直观地查看和管理视频。包括播放视频, 查看截图, 人工审核, 推荐视频等功能。使用 `vue-cli 3` 构建, `vue-cli` 选择的是 `babel` 和 `scss` 配置, 打包之后轻量快速。

## 环境说明

需要 `node`, 很多依赖需要 `node` 环境, 例如用于编译 `sass` 的 `node-sass`。需要 `npm`, 这是 `node` 的包管理器用于安装, 更新, 删除 `node` 依赖。可以到[node官网](https://nodejs.org)下载对应系统的 `node` 安装包, `npm` 会随着 `node` 安装而安装。

## 主要技术栈

主要技术栈使用:

- [vue](https://vuejs.org/)  前端 `MVVM` 框架
- [vue-router](https://router.vuejs.org/)  前端 `vue` 路由框架
- [element](http://element.eleme.io/)  `UI` 框架
- [axios](https://github.com/axios/axios)  基于 `promise` 的 `HTTP` 库
- [Aliplayer](https://player.alicdn.com)  `web` 播放器 `sdk`

## 源码下载

趣视频控制台源码和`趣视频AppServer`源码在同一个文件夹中, 所以要先下载[趣视频AppServer源码](https://help.aliyun.com/document_detail/51992.html), 下载完源码之后, 趣视频控制台前端代码在 `video-admin` 文件夹中, `video-admin` 便是趣视频控制台前端代码的工作目录, 以下的命令都要进入到 `video-admin` 目录执行。

## 功能说明

主要功能有如下:

- 登录, 以及登录过期时, 设置请求拦截器以重新登录
- 全部视频列表
    + 根据视频ID, 用户ID, 用户名称, 视频标题, 审核状态, 创建日期查询视频
    + 查看视频信息(时长, 视频大小, 视频ID)
    + 视频不同清晰度的播放(同时可查看视频描述)
    + 显示截图
    + 审核状态, 转码状态的查看
    + 操作主要有推荐, 审核, 转码(转码失败时), 删除操作
- 推荐视频列表
    + 根据视频ID, 用户ID, 用户名称, 视频标题, 创建日期查询视频
    + 查看视频信息(时长, 视频大小, 视频ID)
    + 视频不同清晰度的播放(同时可查看视频描述)
    + 审核状态, 转码状态的查看
    + 操作主要有预热, 取消推荐, 窄带高清转码(窄带高清转码失败时)

## 目录结构说明

下载源码之后, 打开 `video-admin` 文件夹, `video-admin` 便是趣视频控制台前端的工作目录。以下是 `video-admin` 的目录结构: 

```
├── node_modules                  // node 依赖安装目录, 执行 npm install 之后各种依赖会安装在这儿
├── dist                          // 打包之后的文件目录  
├── public                        
│   ├── favicon.ico               
│   └── index.html                // 网页主入口, 在这儿引用 cdn 等在线 js 和 css
├── src                           // 业务逻辑以及 Vue 组件目录
│   ├── assets                    // 放置一些静态资源 css, images, fonts 放置在这儿
│   │   ├── images
│   │   └── scss
│   ├── components                // vue 组件目录
│   │   ├── RecommendVideo.vue    // 推荐视频弹框组件
│   │   └── VodPlayer.vue         // 播放器组件
│   ├── mixin                     // vue mixin 目录
│   │   └── index.js
│   ├── views                     // vue-router 对应的渲染组件所在目录
│   │   ├── list                  // 视频列表路由对应渲染组件
│   │   │  └── index.vue
│   │   ├── login                 // 登录路由对应渲染组件
│   │   │   └── index.vue
│   │   ├── recommend             // 推荐视频列表对应渲染组件
│   │   │   └── index.vue
│   │   └── videos                // 主界面侧边栏头部渲染组件
│   │       └── index.vue
│   ├── App.vue                   // 渲染主组件
│   ├── main.js                   // 主入口文件
│   └── router.js                 // vue-router 路由配置文件
├── .browserslistrc               // 浏览器兼容列表
├── .gitignore
├── babel.config.js               // babel 配置文件
├── package-lock.json
├── package.json                  // 包说明文件, 包括各种依赖, 作者, 描述等
├── postcss.config.js             // postcss 配置文件, 加上 css 的厂商前缀
├── README.md
├── README_zh.md
└── vue.config.js                 // vue 配置文件
```

# 编译发布

## 编译

首先安装好环境之后, 打开命令行工具, 切换到项目目录即 `video-admin` 下, 安装项目依赖, 执行: 

```bash
npm install
```

> 修改 `vue.config.js` 中的配置将 `proxy` 设置成你的后端接口地址, 完整的配置如下: 

```js
module.exports = {
  // 设置前端开发时的代理
  devServer: {
    proxy: 'https://example.com',    // 替换成你的接口域名地址, 后面不要加 '/'
  },
  productionSourceMap: false,
  // 设置生产环境和开发环境时的静态资源路径
  publicPath: process.env.NODE_ENV === 'production'
    ? 'https://example.com/resource/'
    : '/',
}

```

安装依赖完毕并修改配置之后, 执行: 

```bash
npm run serve
```

这个命令会在本地开启一个服务, 默认地址为 `http://localhost:8080/` 在浏览器中打开, 就能预览项目。

## 发布

切换到项目目录即 `video-admin` 下, 执行打包命令: 

```bash
npm run build
```

> 生产环境即发布时 `vue.config.js` 中 `publicPath` 要设置成为你放置静态资源的地址, 例如本项目后端使用的 `spring boot` 框架, 静态资源目录为 `webapp/resource/`, 那么 `vue.config.js` 中的 `publicPath` 配置如下: 

```js
module.exports = {
  // 设置前端开发时的代理
  devServer: {
    proxy: 'https://example.com',    // 替换成你的接口域名地址, 后面不要加 '/'
  },
  productionSourceMap: false,
  // 设置生产环境和开发环境时的静态资源路径
  publicPath: process.env.NODE_ENV === 'production'
    ? 'https://example.com/resource/'
    : '/',
}

```

实际上就是执行 `vue-cli` 封装的 `webpack` 来进行打包, `webpack` 的个性化设置可以在 `vue.config.js` 中设置, 配置文档可以查看[vue-cli 配置参考](https://cli.vuejs.org/zh/config/)

执行完打包命令之后, 项目根目录会生成打包文件, 全部放置在 `dist/` 目录中, 这个目录下的文件就是需要放置到 `webapp/resource` 中的文件, 其中的文件目录结构为: 

```
├──dist
   ├── css
   │   ├── app.[hash].css             // vue 组件中的 css, 以及自定义的 css
   │   └── chunk-vendors.[hash].css   // 依赖中所引入的 css
   ├── fonts
   ├── img
   ├── js
   │   ├── app.[hash].css             // vue 组件中的 js, 以及自定义的 js
   │   └── chunk-vendors.[hash].css   // 依赖中所引入的 js
   ├── favicon.ico
   └── index.html
```

其中 `webpack` 已经帮我们把依赖中的 `css`, `js` 和自己写的 `css`, `js` 抽离并拆分成了不通的文件, 这样不仅能加快页面响应速度, 另外在迭代时, 依赖没有更改的话我们只需要更新 `app.[hash].js` 和 `app.[hash].css`

之后将 `dist` 中的文件放置到服务器中

### 服务器发布

实质上就是在服务器中放置静态资源, 例如本项目后端使用的是 `spring boot` 框架, 后端要修改 `pom` 文件中 `<resources>` 标签下的 `targetPath` 配置, 具体的可以查看[趣视频AppServer文档](https://help.aliyun.com/document_detail/51992.html)。最后在 `dist/` 目录中的文件放到 `webapp/resource` 文件夹中, 输入 `<你的域名地址>/resource/index.html` 访问成功, 说明部署成功!
