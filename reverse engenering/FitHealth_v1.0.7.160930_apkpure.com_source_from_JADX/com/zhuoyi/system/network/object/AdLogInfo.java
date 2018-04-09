package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class AdLogInfo implements Serializable {
    private static final long serialVersionUID = -7644774695821030003L;
    @ByteField(index = 0)
    private int action;
    @ByteField(index = 2)
    private int appVer;
    @ByteField(index = 7)
    private int iconId;
    @ByteField(index = 4)
    private int num;
    @ByteField(index = 1)
    private String packageName;
    @ByteField(index = 9)
    private String reserved1;
    @ByteField(index = 10)
    private String reserved2;
    @ByteField(index = 3)
    private int sdkVer;
    @ByteField(index = 5)
    private short source1;
    @ByteField(index = 6)
    private short source2;
    @ByteField(index = 8)
    private long stayTime;

    public int getAction() {
        return this.action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getAppVer() {
        return this.appVer;
    }

    public void setAppVer(int appVer) {
        this.appVer = appVer;
    }

    public int getSdkVer() {
        return this.sdkVer;
    }

    public void setSdkVer(int sdkVer) {
        this.sdkVer = sdkVer;
    }

    public short getSource1() {
        return this.source1;
    }

    public void setSource1(short source1) {
        this.source1 = source1;
    }

    public short getSource2() {
        return this.source2;
    }

    public void setSource2(short source2) {
        this.source2 = source2;
    }

    public long getStayTime() {
        return this.stayTime;
    }

    public void setStayTime(long stayTime) {
        this.stayTime = stayTime;
    }

    public String getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String toString() {
        return "AdLogInfo [action=" + this.action + ", packageName=" + this.packageName + ", appVer=" + this.appVer + ", sdkVer=" + this.sdkVer + ", num=" + this.num + ", source1=" + this.source1 + ", source2=" + this.source2 + ", iconId=" + this.iconId + ", stayTime=" + this.stayTime + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
