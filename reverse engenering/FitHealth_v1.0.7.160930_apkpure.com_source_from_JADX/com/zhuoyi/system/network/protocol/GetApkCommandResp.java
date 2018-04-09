package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.SerApkInfo;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(messageCode = 211007)
public class GetApkCommandResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = -5609274769163021542L;
    @ByteField(index = 2)
    private ArrayList<SerApkInfo> apkInfoList = new ArrayList();

    public ArrayList<SerApkInfo> getApkInfoList() {
        return this.apkInfoList;
    }

    public void setApkInfoList(ArrayList<SerApkInfo> apkInfoList) {
        this.apkInfoList = apkInfoList;
    }

    public String toString() {
        return "GetApkCommandResp [apkInfoList=" + this.apkInfoList + "]";
    }
}
