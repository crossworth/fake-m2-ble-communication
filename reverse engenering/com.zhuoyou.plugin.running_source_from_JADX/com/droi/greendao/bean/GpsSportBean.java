package com.droi.greendao.bean;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;

@SuppressLint({"ParcelCreator"})
public class GpsSportBean extends DroiObject {
    @DroiExpose
    private String account;
    @DroiExpose
    private float cal;
    @DroiExpose
    private float distance;
    @DroiExpose
    private int duration;
    @DroiExpose
    private int heart;
    @DroiExpose
    private String id;
    @DroiExpose
    private DroiFile locus;
    @DroiExpose
    private String startTime;
    @DroiExpose
    private int step;
    @DroiExpose
    private int stop;
    @DroiExpose
    private String stopTime;
    private int sync;

    public GpsSportBean(String id) {
        this.id = id;
    }

    public GpsSportBean(String id, String account, String startTime, String stopTime, int duration, float distance, float cal, int step, int heart, int stop, int sync) {
        this.id = id;
        this.account = account;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.duration = duration;
        this.distance = distance;
        this.cal = cal;
        this.step = step;
        this.heart = heart;
        this.stop = stop;
        this.sync = sync;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStopTime() {
        return this.stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getCal() {
        return this.cal;
    }

    public void setCal(float cal) {
        this.cal = cal;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getHeart() {
        return this.heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getStop() {
        return this.stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getSync() {
        return this.sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public DroiFile getLocus() {
        return this.locus;
    }

    public void setLocus(DroiFile locus) {
        this.locus = locus;
    }

    public String toString() {
        return "GpsSportBean{id='" + this.id + '\'' + ", account='" + this.account + '\'' + ", startTime='" + this.startTime + '\'' + ", stopTime='" + this.stopTime + '\'' + ", duration=" + this.duration + ", distance=" + this.distance + ", cal=" + this.cal + ", step=" + this.step + ", heart=" + this.heart + ", stop=" + this.stop + ", sync=" + this.sync + ", locus=" + this.locus + '}';
    }
}
