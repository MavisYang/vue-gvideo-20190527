package com.alivc.user.pojo;

import com.alivc.base.ConfigMapUtil;

/**
 * 用户基本信息实体类
 *
 * @author haihua.whh
 * @date 2018-12-29
 */

public class BaseUserProfile {

    /**
     * 用户id
     */
    private String userId = "";
    /**
     * 用户名
     */
    private String userName = "";

    /**
     * 用户头像
     */
    private String avatarUrl = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = ConfigMapUtil.getValueByKey("AVATAR_DOMAIN_NAME") + avatarUrl;
    }
}
