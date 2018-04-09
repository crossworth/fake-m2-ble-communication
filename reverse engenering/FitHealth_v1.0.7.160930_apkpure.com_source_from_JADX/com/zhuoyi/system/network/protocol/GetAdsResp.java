package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(messageCode = 211001)
public class GetAdsResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = -2715794289013349853L;
    @ByteField(index = 2)
    private ArrayList<PromAppInfo> adInfos = new ArrayList();
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

    public ArrayList<PromAppInfo> getAdInfos() {
        return this.adInfos;
    }

    public void setAdInfos(ArrayList<PromAppInfo> adInfos) {
        this.adInfos = adInfos;
    }

    public void addAdInfo(PromAppInfo adInfo) {
        this.adInfos.add(adInfo);
    }

    public String toString() {
        return "GetAdsResp [adInfos=" + this.adInfos + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + "]";
    }
}
