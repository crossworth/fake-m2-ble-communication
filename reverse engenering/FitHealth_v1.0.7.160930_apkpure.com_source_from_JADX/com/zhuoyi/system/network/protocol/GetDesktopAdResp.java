package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.AdInfo;
import com.zhuoyi.system.network.object.Html5Info;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(messageCode = 211011)
public class GetDesktopAdResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 5690191607552079389L;
    @ByteField(index = 2)
    private ArrayList<AdInfo> adInfoList;
    @ByteField(index = 3)
    private ArrayList<Html5Info> html5List;

    public ArrayList<AdInfo> getAdInfoList() {
        return this.adInfoList;
    }

    public void setAdInfoList(ArrayList<AdInfo> adInfoList) {
        this.adInfoList = adInfoList;
    }

    public ArrayList<Html5Info> getHtml5List() {
        return this.html5List;
    }

    public void setHtml5List(ArrayList<Html5Info> html5List) {
        this.html5List = html5List;
    }

    public String toString() {
        return "GetDesktopAdResp [adInfoList=" + this.adInfoList + ", html5List=" + this.html5List + "]";
    }
}
