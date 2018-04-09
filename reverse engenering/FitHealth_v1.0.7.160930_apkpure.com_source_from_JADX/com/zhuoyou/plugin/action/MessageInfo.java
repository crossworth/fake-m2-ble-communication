package com.zhuoyou.plugin.action;

import java.io.Serializable;

public class MessageInfo implements Serializable {
    private static final long serialVersionUID = 1;
    private int activityId;
    private int id;
    private String mContent;
    private int mMsgId;
    private String mMsgTime;
    private int mMsgType;
    private int mState;

    public MessageInfo(int msgid, String content, int activityid, int type) {
        this.mContent = content;
        this.mMsgId = msgid;
        this.mMsgType = type;
        this.activityId = activityid;
    }

    public MessageInfo(int _id, int msgid, String content, int type, String time, int state) {
        this.id = _id;
        this.mContent = content;
        this.mMsgId = msgid;
        this.mMsgType = type;
        this.mMsgTime = time;
        this.mState = state;
    }

    public String GetMsgContent() {
        return this.mContent;
    }

    public int GetMsgId() {
        return this.mMsgId;
    }

    public int getId() {
        return this.id;
    }

    public String getmMsgTime() {
        return this.mMsgTime;
    }

    public int getmMsgType() {
        return this.mMsgType;
    }

    public int getmState() {
        return this.mState;
    }

    public int getActivityId() {
        return this.activityId;
    }
}
