package com.zhuoyou.plugin.running.baas;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

public class CommentZan extends DroiObject {
    @DroiExpose
    private String account;
    @DroiExpose
    private String date;
    @DroiExpose
    boolean ifZan = false;
    @DroiExpose
    private String toAccount;
    @DroiExpose
    private String toComment;
    @DroiReference
    private User toUser;
    @DroiReference
    private User user;

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToAccount() {
        return this.toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getToComment() {
        return this.toComment;
    }

    public void setToComment(String toComment) {
        this.toComment = toComment;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getToUser() {
        return this.toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public boolean isIfZan() {
        return this.ifZan;
    }

    public void setIfZan(boolean ifZan) {
        this.ifZan = ifZan;
    }
}
