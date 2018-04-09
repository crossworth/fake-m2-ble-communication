package com.droi.greendao.bean;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.zhuoyou.plugin.running.baas.BaseObject;

@SuppressLint({"ParcelCreator"})
public class SportBean extends BaseObject<SportBean> {
    @DroiExpose
    private int BTtype;
    @DroiExpose
    private String account;
    @DroiExpose
    private float cal;
    @DroiExpose
    private int complete;
    @DroiExpose
    private String date;
    @DroiExpose
    private float distance;
    @DroiExpose
    private String sportData;
    @DroiExpose
    private int stepDev;
    @DroiExpose
    private int stepPhone;
    @DroiExpose
    private int stepTarget;
    private int sync;

    public SportBean(String date) {
        this.date = date;
    }

    public SportBean(String date, String account, int stepPhone, int stepDev, int stepTarget, int complete, float distance, float cal, String sportData, int BTtype, int sync) {
        this.date = date;
        this.account = account;
        this.stepPhone = stepPhone;
        this.stepDev = stepDev;
        this.stepTarget = stepTarget;
        this.complete = complete;
        this.distance = distance;
        this.cal = cal;
        this.sportData = sportData;
        this.BTtype = BTtype;
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

    public int getStepPhone() {
        return this.stepPhone;
    }

    public void setStepPhone(int stepPhone) {
        this.stepPhone = stepPhone;
    }

    public int getStepDev() {
        return this.stepDev;
    }

    public void setStepDev(int stepDev) {
        this.stepDev = stepDev;
    }

    public int getStepTarget() {
        return this.stepTarget;
    }

    public void setStepTarget(int stepTarget) {
        this.stepTarget = stepTarget;
    }

    public int getComplete() {
        return this.complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
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

    public String getSportData() {
        return this.sportData;
    }

    public void setSportData(String sportData) {
        this.sportData = sportData;
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
        SportBean bean = (SportBean) o;
        if (this.stepPhone == bean.stepPhone && this.stepDev == bean.stepDev && this.stepTarget == bean.stepTarget && this.complete == bean.complete && Float.compare(bean.distance, this.distance) == 0 && Float.compare(bean.cal, this.cal) == 0 && this.BTtype == bean.BTtype && this.date.equals(bean.date) && this.account.equals(bean.account)) {
            return this.sportData.equals(bean.sportData);
        }
        return false;
    }

    public int hashCode() {
        int floatToIntBits;
        int i = 0;
        int hashCode = ((((((((((this.date.hashCode() * 31) + this.account.hashCode()) * 31) + this.stepPhone) * 31) + this.stepDev) * 31) + this.stepTarget) * 31) + this.complete) * 31;
        if (this.distance != 0.0f) {
            floatToIntBits = Float.floatToIntBits(this.distance);
        } else {
            floatToIntBits = 0;
        }
        floatToIntBits = (hashCode + floatToIntBits) * 31;
        if (this.cal != 0.0f) {
            i = Float.floatToIntBits(this.cal);
        }
        return ((((floatToIntBits + i) * 31) + this.sportData.hashCode()) * 31) + this.BTtype;
    }

    public SportBean getInstance() {
        SportBean value = new SportBean();
        value.setDate(this.date);
        value.setAccount(this.account);
        value.setStepPhone(this.stepPhone);
        value.setStepDev(this.stepDev);
        value.setStepTarget(this.stepTarget);
        value.setComplete(this.complete);
        value.setDistance(this.distance);
        value.setCal(this.cal);
        value.setSportData(this.sportData);
        value.setBTtype(this.BTtype);
        value.setSync(this.sync);
        return value;
    }

    public String toString() {
        return "SportBean{date='" + this.date + '\'' + ", account='" + this.account + '\'' + ", stepPhone=" + this.stepPhone + ", stepDev=" + this.stepDev + ", stepTarget=" + this.stepTarget + ", complete=" + this.complete + ", distance=" + this.distance + ", cal=" + this.cal + ", sportData='" + this.sportData + '\'' + ", BTtype=" + this.BTtype + ", sync=" + this.sync + '}';
    }

    public void copy(SportBean e) {
        setAccount(e.getAccount());
        setDate(e.getDate());
        setSync(e.getSync());
        setBTtype(e.getBTtype());
        setCal(e.getCal());
        setComplete(e.getComplete());
        setSportData(e.getSportData());
        setDistance(e.getDistance());
        setStepDev(e.getStepDev());
        setStepPhone(e.getStepPhone());
        setStepTarget(e.getStepTarget());
    }

    public boolean ifequals(SportBean object) {
        return getDate().equals(object.getDate()) && getAccount().equals(object.getAccount());
    }
}
