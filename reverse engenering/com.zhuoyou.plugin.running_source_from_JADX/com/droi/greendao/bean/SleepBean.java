package com.droi.greendao.bean;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.zhuoyou.plugin.running.baas.BaseObject;

@SuppressLint({"ParcelCreator"})
public class SleepBean extends BaseObject<SleepBean> {
    @DroiExpose
    private int BTtype;
    @DroiExpose
    private String account;
    @DroiExpose
    private String endTime;
    @DroiExpose
    private String sleepData;
    @DroiExpose
    private String startTime;
    private int sync;

    public SleepBean(String startTime) {
        this.startTime = startTime;
    }

    public SleepBean(String account, String startTime, String endTime, String sleepData, int BTtype, int sync) {
        this.account = account;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sleepData = sleepData;
        this.BTtype = BTtype;
        this.sync = sync;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSleepData() {
        return this.sleepData;
    }

    public void setSleepData(String sleepData) {
        this.sleepData = sleepData;
    }

    public int getBTtype() {
        return this.BTtype;
    }

    public void setBTtype(int BTtype) {
        this.BTtype = BTtype;
    }

    public int getSync() {
        return this.sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SleepBean bean = (SleepBean) o;
        if (this.BTtype == bean.BTtype && this.account.equals(bean.account) && this.startTime.equals(bean.startTime) && this.endTime.equals(bean.endTime)) {
            return this.sleepData.equals(bean.sleepData);
        }
        return false;
    }

    public int hashCode() {
        return (((((((this.account.hashCode() * 31) + this.startTime.hashCode()) * 31) + this.endTime.hashCode()) * 31) + this.sleepData.hashCode()) * 31) + this.BTtype;
    }

    public SleepBean getInstance() {
        SleepBean value = new SleepBean();
        value.setAccount(this.account);
        value.setStartTime(this.startTime);
        value.setEndTime(this.endTime);
        value.setBTtype(this.BTtype);
        value.setSleepData(this.sleepData);
        value.setSync(this.sync);
        return value;
    }

    public String toString() {
        return "SleepBean{account='" + this.account + '\'' + ", startTime='" + this.startTime + '\'' + ", endTime='" + this.endTime + '\'' + ", sleepData='" + this.sleepData + '\'' + ", BTtype=" + this.BTtype + ", sync=" + this.sync + '}';
    }

    public void copy(SleepBean e) {
        setAccount(e.getAccount());
        setStartTime(e.getStartTime());
        setSleepData(e.getSleepData());
        setEndTime(e.getEndTime());
        setBTtype(e.getBTtype());
        setSync(e.getSync());
    }

    public boolean ifequals(SleepBean object) {
        return getStartTime().equals(object.getStartTime()) && getAccount().equals(object.getAccount());
    }
}
