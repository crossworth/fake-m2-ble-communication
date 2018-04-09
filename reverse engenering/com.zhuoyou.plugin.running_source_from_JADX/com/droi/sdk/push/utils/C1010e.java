package com.droi.sdk.push.utils;

import android.content.Context;
import android.text.TextUtils;
import com.droi.sdk.internal.DroiDataCollector;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONObject;

public class C1010e {
    public static String m3133a(Context context, String str) {
        String str2 = null;
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("deviceId", str);
                jSONObject.put("imsi", DroiDataCollector.getImsi(context));
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, DroiDataCollector.getImei(context));
                jSONObject.put("handsetMan", DroiDataCollector.getBuildBrand());
                jSONObject.put("handsetType", DroiDataCollector.getBuildModel());
                jSONObject.put("osVer", DroiDataCollector.getCustomVersion());
                jSONObject.put("screenWidth", DroiDataCollector.getLcdWidth(context));
                jSONObject.put("screenHeight", DroiDataCollector.getLcdHeight(context));
                jSONObject.put("cpu", DroiDataCollector.getBuildHardware());
                jSONObject.put("ram", DroiDataCollector.getTatalRAMSize());
                jSONObject.put("rom", DroiDataCollector.getTotalRomSize());
                jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, DroiDataCollector.getWifiMAC(context));
                jSONObject.put("networkType", DroiDataCollector.getCurNetworkType(context));
                str2 = jSONObject.toString();
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return str2;
    }
}
