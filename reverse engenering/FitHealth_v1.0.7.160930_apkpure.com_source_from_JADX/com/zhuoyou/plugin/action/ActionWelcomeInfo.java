package com.zhuoyou.plugin.action;

import java.io.Serializable;

public class ActionWelcomeInfo implements Serializable {
    private int mId;
    private String mImgUrl;

    public ActionWelcomeInfo(String imgurl, int id) {
        this.mImgUrl = imgurl;
        this.mId = id;
    }

    public String GetImgUrl() {
        return this.mImgUrl;
    }

    public int GetID() {
        return this.mId;
    }
}
