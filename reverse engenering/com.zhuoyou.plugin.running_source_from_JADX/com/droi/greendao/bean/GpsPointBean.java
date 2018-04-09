package com.droi.greendao.bean;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

@SuppressLint({"ParcelCreator"})
public class GpsPointBean extends DroiObject {
    @DroiExpose
    private String account;
    @DroiExpose
    private String address;
    @DroiExpose
    private int cadence;
    @DroiExpose
    private int heart;
    private Long id;
    @DroiExpose
    private double latitude;
    @DroiExpose
    private double longitude;
    @DroiExpose
    private String sportId;
    private int sync;
    @DroiExpose
    private String time;

    public GpsPointBean(Long id) {
        this.id = id;
    }

    public GpsPointBean(Long id, String sportId, String account, double latitude, double longitude, String time, String address, int heart, int cadence, int sync) {
        this.id = id;
        this.sportId = sportId;
        this.account = account;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.address = address;
        this.heart = heart;
        this.cadence = cadence;
        this.sync = sync;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSportId() {
        return this.sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHeart() {
        return this.heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getCadence() {
        return this.cadence;
    }

    public void setCadence(int cadence) {
        this.cadence = cadence;
    }

    public int getSync() {
        return this.sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public String toString() {
        return "GpsPointBean{id=" + this.id + ", sportId='" + this.sportId + '\'' + ", account='" + this.account + '\'' + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", time='" + this.time + '\'' + ", address='" + this.address + '\'' + ", heart=" + this.heart + ", cadence=" + this.cadence + ", sync=" + this.sync + '}';
    }
}
