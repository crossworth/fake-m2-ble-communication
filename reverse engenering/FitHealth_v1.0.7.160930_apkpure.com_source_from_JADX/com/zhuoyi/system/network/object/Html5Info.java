package com.zhuoyi.system.network.object;

import com.zhuoyi.system.network.serializer.ByteField;
import java.io.Serializable;

public class Html5Info implements Serializable {
    private static final long serialVersionUID = 4015071568907887403L;
    @ByteField(index = 3)
    private String desc;
    @ByteField(index = 8)
    private String downloadInfo;
    @ByteField(index = 0)
    private int id;
    @ByteField(index = 6)
    private int remainTime;
    @ByteField(index = 9)
    private String reserved2;
    @ByteField(index = 10)
    private String reserved3;
    @ByteField(index = 5)
    private String showTime;
    @ByteField(index = 4)
    private byte showType;
    @ByteField(index = 2)
    private String title;
    @ByteField(index = 1)
    private String url;
    @ByteField(index = 7)
    private String zipMd5;

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

    public String getDownloadInfo() {
        return this.downloadInfo;
    }

    public void setDownloadInfo(String downloadInfo) {
        this.downloadInfo = downloadInfo;
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

    public String getZipMd5() {
        return this.zipMd5;
    }

    public void setZipMd5(String zipMd5) {
        this.zipMd5 = zipMd5;
    }

    public PromAppInfo thisToPromAppInfo() {
        PromAppInfo ret = new PromAppInfo();
        ret.setId(this.id);
        ret.setUrl(this.url);
        ret.setTitle(this.title);
        ret.setDesc(this.desc);
        ret.setDisplayTime(this.showTime);
        ret.setType((byte) 1);
        ret.setFileVerifyCode(this.zipMd5);
        return ret;
    }

    public String toString() {
        return "Html5Info [id=" + this.id + ", url=" + this.url + ", title=" + this.title + ", desc=" + this.desc + ", showType=" + this.showType + ", showTime=" + this.showTime + ", remainTime=" + this.remainTime + ", zipMd5=" + this.zipMd5 + ", downloadInfo=" + this.downloadInfo + ", reserved2=" + this.reserved2 + ", reserved3=" + this.reserved3 + "]";
    }
}
