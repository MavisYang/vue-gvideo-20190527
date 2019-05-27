

DROP TABLE IF EXISTS  `vod_front_moving_figure_paster`;
CREATE TABLE `vod_front_moving_figure_paster` (
  `id` int(11) DEFAULT NULL,
  `paster_id` varchar(50) DEFAULT '',
  `font_id` varchar(50) DEFAULT '',
  `icon` varchar(100) DEFAULT '',
  `name` varchar(50) DEFAULT '',
  `level` varchar(10) DEFAULT '',
  `url` varchar(200) DEFAULT '',
  `md5` varchar(100) DEFAULT '',
  `preview` varchar(200) DEFAULT '',
  `sort` varchar(10) DEFAULT '',
  `type` varchar(10) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_mv_resource`;
CREATE TABLE `vod_mv_resource` (
  `id` int(11) DEFAULT NULL,
  `aspect_id` varchar(20) DEFAULT '',
  `key` varchar(20) DEFAULT '',
  `name` varchar(20) DEFAULT '' COMMENT '名称',
  `level` varchar(10) DEFAULT '' COMMENT '等级',
  `icon` varchar(200) DEFAULT '',
  `duration` varchar(20) DEFAULT '',
  `mv_type` varchar(10) DEFAULT '',
  `previewPic` varchar(100) DEFAULT '',
  `previewMp4` varchar(100) DEFAULT '',
  `tagSort` varchar(10) DEFAULT '',
  `tag` varchar(50) DEFAULT '',
  `sort` varchar(10) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_snapshot`;
CREATE TABLE `vod_snapshot` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `video_id` varchar(100) DEFAULT '' COMMENT '视频id',
  `snapshot_url` varchar(200) DEFAULT '' COMMENT '截图url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5752 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `white_board_file`;
CREATE TABLE `white_board_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(50) DEFAULT '' COMMENT '用户id',
  `title` varchar(200) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '标题',
  `size` varchar(50) DEFAULT '' COMMENT '文件大小',
  `create` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `type` varchar(50) DEFAULT '' COMMENT '文件类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_profile`;
CREATE TABLE `user_profile` (
  `id` int(15) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(200) DEFAULT NULL,
  `username` varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '用户名',
  `password` varchar(300) DEFAULT '' COMMENT '密码',
  `role` varchar(10) DEFAULT '' COMMENT '角色--1（管理员）,2(主播),3(观众)',
  `level` varchar(10) DEFAULT '' COMMENT '用户等级',
  `sex` varchar(10) DEFAULT '' COMMENT '性别--1(男),2(女),3(其他)',
  `status` varchar(30) DEFAULT '' COMMENT '状态',
  `mobile` varchar(15) DEFAULT '' COMMENT '电话',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像',
  `birthday` datetime(6) DEFAULT NULL COMMENT '生日',
  `last_login` datetime(6) DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime(6) DEFAULT NULL COMMENT '加入时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `live_room`;
CREATE TABLE `live_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `room_id` varchar(20) DEFAULT '' COMMENT '房间id',
  `room_title` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '房间标题',
  `room_screen_shot` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '房间封面',
  `room_viewer_count` varchar(12) DEFAULT '' COMMENT '房间观众人数',
  `streamer_id` varchar(100) DEFAULT '' COMMENT '主播ID',
  `streamer_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '主播名称',
  `play_flv` varchar(200) DEFAULT '' COMMENT 'flv播放地址',
  `play_hls` varchar(200) DEFAULT '' COMMENT 'hls播放地址',
  `play_rtmp` varchar(200) DEFAULT '' COMMENT 'rtmp播放地址',
  `push_url` varchar(200) DEFAULT '' COMMENT '推流地址',
  `live_state` varchar(50) DEFAULT '' COMMENT '直播状态（offLine：不在线，onLine：在线）',
  `gmt_create` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_font_resource`;
CREATE TABLE `vod_font_resource` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL,
  `banner` varchar(200) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `md5` varchar(200) DEFAULT NULL,
  `sort` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_videos`;
CREATE TABLE `vod_videos` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(20) DEFAULT '' COMMENT '用户id',
  `video_id` varchar(50) DEFAULT '' COMMENT '视频id',
  `title` varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '标题',
  `description` varchar(200) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '描述',
  `duration` float(20,0) DEFAULT NULL COMMENT '视频时长（秒）',
  `cover_url` varchar(300) DEFAULT '' COMMENT '视频封面URL',
  `status` varchar(20) DEFAULT '' COMMENT '视频状态（Uploading上传中，Normal正常，UploadFail失败，UploadSucc上传完成，Transcoding转码中，TranscodeFail转码失败，Checking审核中，Blocked屏蔽）',
  `creation_time` datetime(6) DEFAULT NULL COMMENT '视频创建时间',
  `size` int(10) DEFAULT NULL COMMENT '视频源文件大小（字节）',
  `cate_id` int(20) DEFAULT NULL COMMENT '视频分类ID',
  `cate_name` varchar(20) DEFAULT '' COMMENT '视频分类名称',
  `tags` varchar(50) DEFAULT '' COMMENT '视频标签.多个用逗号分隔',
  `first_frame_url` varchar(300) DEFAULT '' COMMENT '首帧图地址',
  `type` varchar(10) DEFAULT NULL COMMENT '视频类型',
  `share_url` varchar(200) DEFAULT NULL COMMENT '分享链接',
  `censor_status` varchar(100) DEFAULT NULL COMMENT '审核状态',
  `snapshot_status` varchar(100) DEFAULT NULL COMMENT '截图状态',
  `transcode_status` varchar(100) DEFAULT NULL COMMENT '转码状态',
  `narrow_transcode_status` varchar(100) DEFAULT NULL COMMENT '窄带转码状态 onTranscode（转码中），success（转码成功），fail（转码失败）',
  `isRecommend` varchar(20) DEFAULT '' COMMENT '是否推荐视频（是：true，否：false）',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1230 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_music_resource`;
CREATE TABLE `vod_music_resource` (
  `id` int(11) DEFAULT NULL,
  `uuid` varchar(20) DEFAULT '',
  `business_type` varchar(20) DEFAULT '',
  `terminal_type` varchar(20) DEFAULT '',
  `device_model` varchar(20) DEFAULT '',
  `app_version` varchar(20) DEFAULT '',
  `play_info_get` varchar(100) DEFAULT '',
  `lrc_url` varchar(200) DEFAULT '',
  `refrain_url` varchar(200) DEFAULT '',
  `listen_file_url` varchar(200) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_subtitle_paster`;
CREATE TABLE `vod_subtitle_paster` (
  `id` int(11) DEFAULT NULL,
  `paster_id` varchar(50) DEFAULT '',
  `font_id` varchar(50) DEFAULT '',
  `icon` varchar(100) DEFAULT '',
  `name` varchar(50) DEFAULT '',
  `level` varchar(10) DEFAULT '',
  `url` varchar(200) DEFAULT '',
  `md5` varchar(100) DEFAULT '',
  `preview` varchar(200) DEFAULT '',
  `sort` varchar(10) DEFAULT '',
  `type` varchar(10) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `live_user_room`;
CREATE TABLE `live_user_room` (
  `id` int(13) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT '' COMMENT '用户id',
  `room_id` varchar(100) DEFAULT '' COMMENT '房间id',
  `streamer_id` varchar(100) DEFAULT '' COMMENT '主播id',
  `event_id` varchar(30) DEFAULT '' COMMENT '事件id(进入房间e_enter_room ，退出出房间e_leave_room)',
  `gmt_create` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `room_id` (`room_id`) USING BTREE,
  KEY `streamer_id` (`streamer_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_recommend_video`;
CREATE TABLE `vod_recommend_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(20) DEFAULT '' COMMENT '用户id',
  `video_id` varchar(50) DEFAULT '' COMMENT '视频id',
  `title` varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '标题',
  `description` varchar(200) DEFAULT '' COMMENT '描述',
  `duration` float(20,0) DEFAULT NULL COMMENT '视频时长（秒）',
  `cover_url` varchar(300) DEFAULT '' COMMENT '视频封面URL',
  `status` varchar(20) DEFAULT '' COMMENT '视频状态（Uploading上传中，Normal正常，UploadFail失败，UploadSucc上传完成，Transcoding转码中，TranscodeFail转码失败，Checking审核中，Blocked屏蔽）',
  `creation_time` datetime(6) DEFAULT NULL COMMENT '视频创建时间',
  `size` int(10) DEFAULT NULL COMMENT '视频源文件大小（字节）',
  `cate_id` int(20) DEFAULT NULL COMMENT '视频分类ID',
  `cate_name` varchar(20) DEFAULT '' COMMENT '视频分类名称',
  `tags` varchar(50) DEFAULT '' COMMENT '视频标签.多个用逗号分隔',
  `first_frame_url` varchar(300) DEFAULT '' COMMENT '首帧图片地址',
  `share_url` varchar(200) DEFAULT '' COMMENT '分享链接',
  `censor_status` varchar(100) DEFAULT '' COMMENT '审核状态',
  `snapshot_status` varchar(100) DEFAULT '' COMMENT '截图状态',
  `transcode_status` varchar(100) DEFAULT '' COMMENT '转码状态',
  `narrow_transcode_status` varchar(100) DEFAULT '' COMMENT '窄带转码状态 onTranscode（转码中），success（转码成功），fail（转码失败）',
  `file_url` varchar(300) DEFAULT '' COMMENT '播放地址',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_mv_aspect`;
CREATE TABLE `vod_mv_aspect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `aspect_id` varchar(20) DEFAULT '',
  `aspect` varchar(20) DEFAULT '',
  `download` varchar(100) DEFAULT '',
  `md5` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_subtitle_resource`;
CREATE TABLE `vod_subtitle_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `icon` varchar(100) DEFAULT '' COMMENT 'icon',
  `description` varchar(50) DEFAULT '',
  `is_new` varchar(10) DEFAULT '',
  `name` varchar(50) DEFAULT '' COMMENT '名字',
  `level` varchar(10) DEFAULT '' COMMENT '等级',
  `paster_id` varchar(20) DEFAULT '',
  `preview` varchar(100) DEFAULT '',
  `sort` varchar(10) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_front_moving_figure_resource`;
CREATE TABLE `vod_front_moving_figure_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `icon` varchar(100) DEFAULT '' COMMENT 'icon',
  `description` varchar(50) DEFAULT '',
  `is_new` varchar(10) DEFAULT '',
  `name` varchar(50) DEFAULT '' COMMENT '名字',
  `level` varchar(10) DEFAULT '' COMMENT '等级',
  `paster_id` varchar(20) DEFAULT '',
  `preview` varchar(100) DEFAULT '',
  `sort` varchar(10) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `live_userinfo`;
CREATE TABLE `live_userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `token` varchar(300) DEFAULT '' COMMENT 'token',
  `user_id` varchar(100) DEFAULT '' COMMENT '用户ID',
  `nick_name` varchar(200) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '用户昵称',
  `avatar_url` varchar(200) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '用户头像',
  `push_url` varchar(200) DEFAULT '' COMMENT '推流地址',
  `play_flv` varchar(200) DEFAULT '' COMMENT 'flv播放地址',
  `play_hls` varchar(200) DEFAULT '' COMMENT 'hls播放地址',
  `play_rtmp` varchar(200) DEFAULT '' COMMENT 'rtmp播放地址',
  `gmt_create` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(6) DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17686 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `console_token`;
CREATE TABLE `console_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `token` varchar(300) DEFAULT '' COMMENT '控制台token',
  `user_id` varchar(200) DEFAULT '' COMMENT '用户id',
  `createtime` datetime(6) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_rear_moving_figure_resource`;
CREATE TABLE `vod_rear_moving_figure_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `icon` varchar(100) DEFAULT '' COMMENT 'icon',
  `description` varchar(50) DEFAULT '',
  `is_new` varchar(10) DEFAULT '',
  `name` varchar(50) DEFAULT '' COMMENT '名字',
  `level` varchar(10) DEFAULT '' COMMENT '等级',
  `paster_id` varchar(20) DEFAULT '',
  `preview` varchar(100) DEFAULT '',
  `sort` varchar(10) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `user_authtoken`;
CREATE TABLE `user_authtoken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(500) DEFAULT NULL COMMENT 'token',
  `createtime` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `user_id` varchar(300) DEFAULT NULL COMMENT '用户id(对应用户表的user_id)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17558 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `console_userinfo`;
CREATE TABLE `console_userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(100) DEFAULT '' COMMENT '用户id',
  `user_name` varchar(200) DEFAULT '' COMMENT '用户名',
  `avatar_url` varchar(200) DEFAULT '' COMMENT '控制台用户头像',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `create_time` datetime(6) DEFAULT NULL,
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `vod_file`;
CREATE TABLE `vod_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `video_id` varchar(200) DEFAULT '' COMMENT '视频id',
  `file_url` varchar(200) DEFAULT '' COMMENT '文件地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2002 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `white_board_image`;
CREATE TABLE `white_board_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_id` varchar(50) DEFAULT '' COMMENT '文件id',
  `image_url` varchar(300) DEFAULT '' COMMENT '图片url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `vod_mv_resource`;
CREATE TABLE `vod_mv_resource` (
  `id` int(11) NOT NULL,
  `key` varchar(20) DEFAULT '',
  `name` varchar(20) DEFAULT '' COMMENT '名称',
  `level` varchar(10) DEFAULT '' COMMENT '等级',
  `icon` varchar(200) DEFAULT '',
  `duration` varchar(20) DEFAULT '',
  `previewPic` varchar(100) DEFAULT '',
  `previewMp4` varchar(100) DEFAULT '',
  `sort` varchar(10) DEFAULT '',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `vod_media`;
CREATE TABLE `vod_media` (
  `id` int(15) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `media_id` int(50) DEFAULT NULL COMMENT '媒资id',
  `url` varchar(200) DEFAULT '' COMMENT '下载地址',
  `icon` varchar(200) DEFAULT '' COMMENT '图标',
  `preview` varchar(200) DEFAULT '' COMMENT '预览',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `desc` varchar(100) DEFAULT '' COMMENT '描述',
  `sort` varchar(10) DEFAULT NULL COMMENT '排序',
  `type` varchar(20) DEFAULT '' COMMENT '素材类型（1：前置动图，2：后置动图，3：mv，4：字幕，5：字体）',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `aspect` int(11) DEFAULT NULL COMMENT '比例',
  `font_id` int(11) DEFAULT NULL COMMENT '字体id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1569 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `vod_font_resource`;
CREATE TABLE `vod_font_resource` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL,
  `banner` varchar(200) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  `md5` varchar(200) DEFAULT NULL,
  `sort` varchar(100) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `vod_paster_resource`;
CREATE TABLE `vod_paster_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `icon` varchar(100) DEFAULT '' COMMENT 'icon',
  `description` varchar(50) DEFAULT '',
  `is_new` varchar(10) DEFAULT '',
  `name` varchar(50) DEFAULT '' COMMENT '名字',
  `level` varchar(10) DEFAULT '' COMMENT '等级',
  `paster_id` varchar(20) DEFAULT '',
  `preview` varchar(100) DEFAULT '',
  `type` varchar(10) DEFAULT NULL COMMENT '类型（1：动图，2：字幕）',
  `sort` int(10) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=575 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `vod_paster_media`;
CREATE TABLE `vod_paster_media` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `paster_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '类型（1：动图，2：字幕）',
  `media_id` int(11) DEFAULT NULL COMMENT '媒资id',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1201 DEFAULT CHARSET=utf8;



