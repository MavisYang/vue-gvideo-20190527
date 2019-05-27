## 一、项目
### 1. 项目描述
项目名称：视频云工具包AppServer。
该项目是视频工具包对应的业务服务器，主要提供用户注册，登录，视频点播的视频上传，视频列表获取（个人中心和推荐列表），互动直播房间管理，白板等一系列业务逻辑整合。
这个代码会作为一个SampleCode提供给客户，未来这个服务会部署到用户的ECS上提供给用户；
### 2. 如何运行
项目为spring boot微服务结构，使用的是mysql数据库，可通过入口类Application启动项目，接口调试方式可用swagger
### 3. 业务介绍
视频云解决方案主要包括：视频直播，视频点播，视频服务等模块，现有的接口主要是视频直播的互动直播模块：live，互动白板模块：whiteboard，登陆模块：user，点播模块：vod，它们分别对应项目结构中的包
### 4. 项目备注
严格遵守阿里代码规范，添加或修改项目需写明注释，修改人，修改时间等信息，增强可维护性。
### 5. 版本：v1.0.0
### 6. 创建时间：
  2018/11/09
### 7. 邮箱：
wb-tz483797@alibaba-inc.com   如有意见或者建议，可发送邮件。

## 二，数据库表

### 1. 登陆模块
* user_profile（用户信息表），
* user_authtoken（token表）

### 2. 点播模块
* vod_videos（视频表，用户个人视频和推荐视频），
* vod_front_moving_figure_resource（前置动图资源），
* vod_front_moving_figure_paster（前置动图资源子表），
* vod_rear_moving_figure_resource（后置动图资源），
* vod_rear_moving_figure_paster（后置动图资源子表），
* vod_subtitle_resource（字幕资源资源），
* vod_subtitle_paster（字幕资源子表），
* vod_mv_resource（mv资源表），
* vod_mv_aspect（mv资源子表），
* vod_music_resource（音乐资源表），
* vod_font_resource（字体资源表）

### 3.互动直播模块
* live_room（直播房间），
* live_userinfo（直播用户），
* live_user_room（用户房间关系表）

### 4. 互动白板模块
* white_board_file（互动白板文件表），
* white_board_image（互动白板图片表）

> 具体建表sql详见：AlivcSolution_AppServer/src/main/webapp/WEB-INF/sql 下面的sql文件。

