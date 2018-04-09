package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class AdInfo implements Serializable {
    private static final long serialVersionUID = 3400845986346562220L;
    @ByteField(index = 8)
    private byte actionType;
    @ByteField(index = 9)
    private String actionUrl;
    @ByteField(index = 13)
    private String appName;
    @ByteField(index = 11)
    private int appVerCode;
    @ByteField(index = 12)
    private String appVerName;
    @ByteField(index = 3)
    private String desc;
    @ByteField(index = 14)
    private String fileVerifyCode;
    @ByteField(index = 15)
    private int iconId;
    @ByteField(index = 16)
    private String iconUrl;
    @ByteField(index = 0)
    private int id;
    @ByteField(index = 10)
    private String packageName;
    @ByteField(index = 6)
    private int remainTime;
    @ByteField(index = 17)
    private String reserved1;
    @ByteField(index = 18)
    private String reserved2;
    @ByteField(index = 19)
    private String reserved3;
    @ByteField(index = 5)
    private String showTime;
    @ByteField(index = 4)
    private byte showType;
    @ByteField(index = 2)
    private String title;
    @ByteField(index = 7)
    private byte type;
    @ByteField(index = 1)
    private String url;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte getShowType() {
        return this.showType;
    }

    public void setShowType(byte showType) {
        this.showType = showType;
    }

    public String getShowTime() {
        return this.showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public int getRemainTime() {
        return this.remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getActionType() {
        return this.actionType;
    }

    public void setActionType(byte actionType) {
        this.actionType = actionType;
    }

    public String getActionUrl() {
        return this.actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getAppVerCode() {
        return this.appVerCode;
    }

    public void setAppVerCode(int appVerCode) {
        this.appVerCode = appVerCode;
    }

    public String getAppVerName() {
        return this.appVerName;
    }

    public void setAppVerName(String appVerName) {
        this.appVerName = appVerName;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFileVerifyCode() {
        return this.fileVerifyCode;
    }

    public void setFileVerifyCode(String fileVerifyCode) {
        this.fileVerifyCode = fileVerifyCode;
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public String getReserved3() {
        return this.reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public PromAppInfo thisToPromAppInfo() {
        PromAppInfo ret = new PromAppInfo();
        ret.setId(this.id);
        ret.setUrl(this.iconUrl);
        ret.setIconId(this.iconId);
        ret.setTitle(this.title);
        ret.setIconId(this.id);
        ret.setDesc(this.desc);
        ret.setDisplayTime(this.showTime);
        ret.setActionType((byte) 1);
        ret.setAction(this.actionUrl);
        ret.setPackageName(this.packageName);
        ret.setVer(this.appVerCode);
        ret.setVersionName(this.appVerName);
        ret.setAppName(this.appName);
        ret.setFileVerifyCode(this.fileVerifyCode);
        ret.setType(this.actionType);
        ret.setAdType(this.type);
        return ret;
    }

    public String toString() {
        return "AdInfo [id=" + this.id + ", url=" + this.url + ", title=" + this.title + ", desc=" + this.desc + ", showType=" + this.showType + ", showTime=" + this.showTime + ", remainTime=" + this.remainTime + ", type=" + this.type + ", actionType=" + this.actionType + ", actionUrl=" + this.actionUrl + ", packageName=" + this.packageName + ", appVerCode=" + this.appVerCode + ", appVerName=" + this.appVerName + ", appName=" + this.appName + ", fileVerifyCode=" + this.fileVerifyCode + ", iconId=" + this.iconId + ", iconUrl=" + this.iconUrl + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + ", reserved3=" + this.reserved3 + "]";
    }
}
