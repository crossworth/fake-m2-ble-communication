package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(messageCode = 211005)
public class GetApkResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 1272508889078047149L;
    @ByteField(index = 10)
    private String content;
    @ByteField(index = 3)
    private String desciption;
    @ByteField(index = 11)
    private ArrayList<String> detailPic = new ArrayList();
    @ByteField(index = 7)
    private int downloadNum;
    @ByteField(index = 8)
    private int fileSize;
    @ByteField(index = 12)
    private String fileVerifyCode;
    @ByteField(index = 2)
    private String name;
    @ByteField(index = 5)
    private String packageName;
    @ByteField(index = 13)
    private String reserved1;
    @ByteField(index = 14)
    private String reserved2;
    @ByteField(index = 9)
    private String title;
    @ByteField(index = 4)
    private String url;
    @ByteField(index = 6)
    private int ver;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return this.desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileVerifyCode() {
        return this.fileVerifyCode;
    }

    public void setFileVerifyCode(String fileVerifyCode) {
        this.fileVerifyCode = fileVerifyCode;
    }

    public String toString() {
        return "GetApkResp [name=" + this.name + ", desciption=" + this.desciption + ", url=" + this.url + ", packageName=" + this.packageName + ", ver=" + this.ver + ", downloadNum=" + this.downloadNum + ", fileSize=" + this.fileSize + ", title=" + this.title + ", content=" + this.content + ", detailPic=" + this.detailPic + ", fileVerifyCode=" + this.fileVerifyCode + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
