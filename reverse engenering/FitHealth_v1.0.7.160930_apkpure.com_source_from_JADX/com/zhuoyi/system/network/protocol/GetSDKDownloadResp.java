package com.zhuoyi.system.network.protocol;

import android.text.TextUtils;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;

@SignalCode(messageCode = 211017)
public class GetSDKDownloadResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 1;
    @ByteField(index = 6)
    private String activity;
    @ByteField(index = 3)
    private String downloadUrl;
    @ByteField(index = 4)
    private String fileVerifyCode;
    @ByteField(index = 2)
    private String packageName;
    @ByteField(index = 7)
    private String reserved1;
    @ByteField(index = 8)
    private String reserved2;
    @ByteField(index = 5)
    private int versionCode;

    public String toString() {
        return "GetSDKDownloadResp [packageName=" + this.packageName + ", downloadUrl=" + this.downloadUrl + ", fileVerifyCode=" + this.fileVerifyCode + ", versionCode=" + this.versionCode + ", activity=" + this.activity + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileVerifyCode() {
        return this.fileVerifyCode;
    }

    public void setFileVerifyCode(String fileVerifyCode) {
        this.fileVerifyCode = fileVerifyCode;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.packageName) && this.versionCode == 0 && TextUtils.isEmpty(this.downloadUrl) && TextUtils.isEmpty(this.fileVerifyCode);
    }
}
