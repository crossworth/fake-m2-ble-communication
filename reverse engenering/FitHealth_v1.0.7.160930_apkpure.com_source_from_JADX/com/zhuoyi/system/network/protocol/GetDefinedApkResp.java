package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.DefinedApkInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(messageCode = 211015)
public class GetDefinedApkResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = -6599949433136094076L;
    @ByteField(index = 2)
    private ArrayList<DefinedApkInfo> apkInfoList = new ArrayList();

    public ArrayList<DefinedApkInfo> getApkInfoList() {
        return this.apkInfoList;
    }

    public void setApkInfoList(ArrayList<DefinedApkInfo> apkInfoList) {
        this.apkInfoList = apkInfoList;
    }

    public String toString() {
        return "GetDefinedApkResp [apkInfoList=" + this.apkInfoList + "]";
    }
}
