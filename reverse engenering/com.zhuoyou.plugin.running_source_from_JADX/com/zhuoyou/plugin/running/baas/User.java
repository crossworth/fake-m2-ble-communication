package com.zhuoyou.plugin.running.baas;

import android.annotation.SuppressLint;
import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;
import java.util.Calendar;

@SuppressLint({"ParcelCreator"})
public class User extends DroiUser {
    private static final int YEAR = Calendar.getInstance().get(1);
    @DroiExpose
    private String address = "";
    @DroiExpose
    private DroiFile back;
    @DroiExpose
    private String birth = ((YEAR - 18) + "0101000000");
    @DroiExpose
    private String cityCode = "";
    @DroiExpose
    private DroiFile head;
    @DroiExpose
    private int height = 170;
    @DroiExpose
    private String nickName = "";
    @DroiExpose
    private int sex = 0;
    @DroiExpose
    private String signature = "";

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return this.birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DroiFile getHead() {
        return this.head;
    }

    public void setHead(DroiFile head) {
        this.head = head;
    }

    public DroiFile getBack() {
        return this.back;
    }

    public void setBack(DroiFile back) {
        this.back = back;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (this.sex != user.sex) {
            return false;
        }
        if (this.height != user.height) {
            return false;
        }
        if (!this.nickName.equals(user.nickName)) {
            return false;
        }
        if (!this.signature.equals(user.signature)) {
            return false;
        }
        if (!this.birth.equals(user.birth)) {
            return false;
        }
        if (!this.cityCode.equals(user.cityCode)) {
            return false;
        }
        if (this.address.equals(user.address)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int hashCode;
        int i = 0;
        if (this.nickName != null) {
            result = this.nickName.hashCode();
        } else {
            result = 0;
        }
        int i2 = ((result * 31) + this.sex) * 31;
        if (this.birth != null) {
            hashCode = this.birth.hashCode();
        } else {
            hashCode = 0;
        }
        i2 = (((i2 + hashCode) * 31) + this.height) * 31;
        if (this.cityCode != null) {
            hashCode = this.cityCode.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.address != null) {
            i = this.address.hashCode();
        }
        return hashCode + i;
    }

    public User getBackUp() {
        User user = new User();
        user.setNickName(this.nickName);
        user.setSignature(this.signature);
        user.setAddress(this.address);
        user.setBirth(this.birth);
        user.setHeight(this.height);
        user.setSex(this.sex);
        user.setCityCode(this.cityCode);
        return user;
    }

    public void setBackUp(User user) {
        this.nickName = user.getNickName();
        this.signature = user.getSignature();
        this.address = user.getAddress();
        this.birth = user.getBirth();
        this.height = user.getHeight();
        this.sex = user.getSex();
        this.cityCode = user.getCityCode();
    }
}
