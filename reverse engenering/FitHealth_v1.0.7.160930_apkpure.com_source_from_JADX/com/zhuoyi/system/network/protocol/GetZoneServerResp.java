package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.object.ZoneServerBto;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;
import java.util.ArrayList;

@SignalCode(encrypt = true, messageCode = 298001)
public class GetZoneServerResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 2225714706119399540L;
    @ByteField(index = 2)
    private ArrayList<ZoneServerBto> zoneServerList = new ArrayList();

    public ArrayList<ZoneServerBto> getZoneServerList() {
        return this.zoneServerList;
    }

    public void setZoneServerList(ArrayList<ZoneServerBto> zoneServerList) {
        this.zoneServerList = zoneServerList;
    }

    public String toString() {
        return "GetZoneServerResp [zoneServerList=" + this.zoneServerList + "]";
    }
}
