package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.ApkInfoNew;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(messageCode = 211016)
public class GetSilentResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 1;
    @ByteField(index = 2)
    private ArrayList<ApkInfoNew> apkList = new ArrayList();
    @ByteField(index = 3)
    private String reserved1;
    @ByteField(index = 4)
    private String reserved2;
    @ByteField(index = 5)
    private String reserved3;
    @ByteField(index = 6)
    private String reserved4;
    @ByteField(index = 7)
    private String reserved5;
    @ByteField(index = 8)
    private String reserved6;

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

    public String getReserved4() {
        return this.reserved4;
    }

    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }

    public String getReserved5() {
        return this.reserved5;
    }

    public void setReserved5(String reserved5) {
        this.reserved5 = reserved5;
    }

    public String getReserved6() {
        return this.reserved6;
    }

    public void setReserved6(String reserved6) {
        this.reserved6 = reserved6;
    }

    public ArrayList<ApkInfoNew> getApkList() {
        return this.apkList;
    }

    public void setApkList(ArrayList<ApkInfoNew> apkList) {
        this.apkList = apkList;
    }

    public String toString() {
        return "GetSilentResp [apkList=" + this.apkList + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + ", reserved3=" + this.reserved3 + ", reserved4=" + this.reserved4 + ", reserved5=" + this.reserved5 + ", reserved6=" + this.reserved6 + "]";
    }
}
