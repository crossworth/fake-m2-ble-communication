package com.droi.greendao.bean;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.zhuoyou.plugin.running.baas.BaseObject;

@SuppressLint({"ParcelCreator"})
public class HeartBean extends BaseObject<HeartBean> {
    @DroiExpose
    private String account;
    @DroiExpose
    private int count;
    @DroiExpose
    private String date;
    private int sync;

    public HeartBean(String date) {
        this.date = date;
    }

    public HeartBean(String date, String account, int count, int sync) {
        this.date = date;
        this.account = account;
        this.count = count;
        this.sync = sync;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSync() {
        return this.sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public String toString() {
        return "HeartBean{, date='" + this.date + '\'' + ", account='" + this.account + '\'' + ", count=" + this.count + ", sync=" + this.sync + '}';
    }

    public void copy(HeartBean e) {
        setAccount(e.getAccount());
        setDate(e.getDate());
        setCount(e.getCount());
        setSync(e.getSync());
    }

    public boolean ifequals(HeartBean object) {
        return getDate().equals(object.getDate()) && getAccount().equals(object.getAccount());
    }
}
