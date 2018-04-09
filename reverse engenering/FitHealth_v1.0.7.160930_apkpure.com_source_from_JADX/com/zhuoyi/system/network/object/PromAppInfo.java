package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;
import java.util.ArrayList;

public class PromAppInfo implements Serializable {
    private static final long serialVersionUID = 3556600226728638207L;
    @ByteField(index = 8)
    private String action;
    @ByteField(index = 19)
    private byte actionType;
    @ByteField(index = 18)
    private byte adType;
    @ByteField(index = 1)
    private String appName;
    @ByteField(index = 4)
    private String desc;
    @ByteField(index = 15)
    private ArrayList<String> detailPic = new ArrayList();
    @ByteField(index = 10)
    private String displayTime;
    @ByteField(index = 13)
    private int downloadNum;
    @ByteField(index = 14)
    private int fileSize;
    @ByteField(index = 16)
    private String fileVerifyCode;
    @ByteField(index = 2)
    private int iconId;
    @ByteField(index = 0)
    private int id;
    @ByteField(index = 11)
    private String packageName;
    @ByteField(index = 9)
    private short position;
    @ByteField(index = 22)
    private String reserved1;
    @ByteField(index = 23)
    private String reserved2;
    @ByteField(index = 20)
    private int showIconId;
    @ByteField(index = 21)
    private String showPicUrl;
    @ByteField(index = 6)
    private String tip;
    @ByteField(index = 5)
    private String title;
    @ByteField(index = 7)
    private byte type;
    @ByteField(index = 3)
    private String url;
    @ByteField(index = 12)
    private int ver;
    @ByteField(index = 17)
    private String versionName;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTip() {
        return this.tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public short getPosition() {
        return this.position;
    }

    public void setPosition(short position) {
        this.position = position;
    }

    public String getDisplayTime() {
        return this.displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getDownloadNum() {
        return this.downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public ArrayList<String> getDetailPic() {
        return this.detailPic;
    }

    public void setDetailPic(ArrayList<String> detailPic) {
        this.detailPic = detailPic;
    }

    public String getFileVerifyCode() {
        return this.fileVerifyCode;
    }

    public void setFileVerifyCode(String fileVerifyCode) {
        this.fileVerifyCode = fileVerifyCode;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public byte getAdType() {
        return this.adType;
    }

    public void setAdType(byte adType) {
        this.adType = adType;
    }

    public byte getActionType() {
        return this.actionType;
    }

    public void setActionType(byte actionType) {
        this.actionType = actionType;
    }

    public int getShowIconId() {
        return this.showIconId;
    }

    public void setShowIconId(int showIconId) {
        this.showIconId = showIconId;
    }

    public String getShowPicUrl() {
        return this.showPicUrl;
    }

    public void setShowPicUrl(String showPicUrl) {
        this.showPicUrl = showPicUrl;
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
        return "PromAppInfo [id=" + this.id + ", appName=" + this.appName + ", iconId=" + this.iconId + ", url=" + this.url + ", desc=" + this.desc + ", title=" + this.title + ", tip=" + this.tip + ", type=" + this.type + ", action=" + this.action + ", position=" + this.position + ", displayTime=" + this.displayTime + ", packageName=" + this.packageName + ", ver=" + this.ver + ", downloadNum=" + this.downloadNum + ", fileSize=" + this.fileSize + ", detailPic=" + this.detailPic + ", fileVerifyCode=" + this.fileVerifyCode + ", versionName=" + this.versionName + ", adType=" + this.adType + ", actionType=" + this.actionType + ", showIconId=" + this.showIconId + ", showPicUrl=" + this.showPicUrl + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
