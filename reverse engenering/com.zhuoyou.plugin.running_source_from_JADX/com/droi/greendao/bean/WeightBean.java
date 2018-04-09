package com.droi.greendao.bean;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.zhuoyou.plugin.running.baas.BaseObject;

@SuppressLint({"ParcelCreator"})
public class WeightBean extends BaseObject<WeightBean> {
    @DroiExpose
    private String account;
    @DroiExpose
    private String date;
    private int sync;
    @DroiExpose
    private float weight;

    public WeightBean(String date) {
        this.date = date;
    }

    public WeightBean(String date, String account, float weight, int sync) {
        this.date = date;
        this.account = account;
        this.weight = weight;
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

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getSync() {
        return this.sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public String toString() {
        return "WeightBean{, date='" + this.date + '\'' + ", account='" + this.account + '\'' + ", weight=" + this.weight + ", sync=" + this.sync + '}';
    }

    public void copy(WeightBean e) {
        setAccount(e.getAccount());
        setDate(e.getDate());
        setWeight(e.getWeight());
        setSync(e.getSync());
    }

    public boolean ifequals(WeightBean object) {
        return getDate().equals(object.getDate()) && getAccount().equals(object.getAccount());
    }
}
