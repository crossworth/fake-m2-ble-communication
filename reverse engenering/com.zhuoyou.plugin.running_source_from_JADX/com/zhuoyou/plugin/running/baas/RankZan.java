package com.zhuoyou.plugin.running.baas;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiReference;

public class RankZan extends BaseObject<RankZan> {
    @DroiExpose
    private String account;
    @DroiExpose
    private String date;
    @DroiExpose
    private String toAccount;
    @DroiReference
    private User toUser;
    @DroiReference
    private User user;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToAccount() {
        return this.toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
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

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void copy(RankZan e) {
        this.account = e.account;
        this.toAccount = e.toAccount;
        this.user = e.user;
        this.toUser = e.toUser;
        this.date = e.date;
    }

    public boolean ifequals(RankZan object) {
        return this.account.equals(object.account) && this.toAccount.equals(object.toAccount) && this.date.equals(object.date);
    }
}
