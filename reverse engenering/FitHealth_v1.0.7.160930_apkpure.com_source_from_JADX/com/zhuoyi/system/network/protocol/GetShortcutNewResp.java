package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.SerApkInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(messageCode = 211008)
public class GetShortcutNewResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 4297287344777592555L;
    @ByteField(index = 2)
    private ArrayList<SerApkInfo> apkInfoList = new ArrayList();
    @ByteField(index = 3)
    private String reserved1;
    @ByteField(index = 4)
    private String reserved2;

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

    public ArrayList<SerApkInfo> getApkInfoList() {
        return this.apkInfoList;
    }

    public void setApkInfoList(ArrayList<SerApkInfo> apkInfoList) {
        this.apkInfoList = apkInfoList;
    }

    public String toString() {
        return "GetShortcutNewResp [apkInfoList=" + this.apkInfoList + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}