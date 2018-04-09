package com.zhuoyou.plugin.running.baas;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

@SuppressLint({"ParcelCreator"})
public class CommentData extends DroiObject {
    public static final int TYPE_REPORTED = 2;
    public static final int TYPE_SELECTED = 1;
    public static final int TYPE_UNSELECTED = 0;
    @DroiExpose
    private String comment;
    @DroiExpose
    private String commentAdd;
    @DroiExpose
    private String date;
    @DroiExpose
    private int selected = 0;
    @DroiReference
    private User toUser;
    @DroiExpose
    private String topicId;
    @DroiReference
    private User user;
    @DroiExpose
    private int zanSum = 0;

    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public User getToUser() {
        return this.toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentAdd() {
        return this.commentAdd;
    }

    public void setCommentAdd(String commentAdd) {
        this.commentAdd = commentAdd;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
