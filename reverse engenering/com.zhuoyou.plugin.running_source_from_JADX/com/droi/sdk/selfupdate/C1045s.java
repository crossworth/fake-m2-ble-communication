package com.droi.sdk.selfupdate;

import android.content.Context;
import android.util.Log;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.SelfUpdateCoreHelper;
import com.droi.sdk.internal.DroiDataCollector;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.util.C1047b;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class C1045s {
    private static JSONObject m3252b(Context context) {
        JSONObject jSONObject = new JSONObject();
        C1045s.m3251a(jSONObject, "deviceId", SelfUpdateCoreHelper.getDeviceId(context));
        C1045s.m3251a(jSONObject, MessageObj.APPID, SelfUpdateCoreHelper.getAppId());
        C1045s.m3251a(jSONObject, "hman", DroiDataCollector.getBuildManufacturer());
        C1045s.m3251a(jSONObject, "htype", DroiDataCollector.getBuildModel());
        C1045s.m3251a(jSONObject, "osVer", DroiDataCollector.getBuildVersionRelease());
        C1045s.m3251a(jSONObject, "freemeVer", DroiDataCollector.getCustomVersion());
        jSONObject.put("sWidth", DroiDataCollector.getLcdWidth(context));
        jSONObject.put("sHeight", DroiDataCollector.getLcdHeight(context));
        C1045s.m3251a(jSONObject, "cpu", DroiDataCollector.getBuildHardware());
        C1045s.m3251a(jSONObject, "ramSize", DroiDataCollector.getTatalRAMSize());
        C1045s.m3251a(jSONObject, "romSize", DroiDataCollector.getTotalRomSize());
        C1045s.m3251a(jSONObject, "imsi", DroiDataCollector.getImsi(context));
        C1045s.m3251a(jSONObject, SocializeProtocolConstants.PROTOCOL_KEY_IMEI, DroiDataCollector.getImei(context));
        C1045s.m3251a(jSONObject, "baseStation", DroiDataCollector.getBaseStationInfo(context));
        C1045s.m3251a(jSONObject, "netType", DroiDataCollector.getCurNetworkType(context));
        String m = C1047b.m3279m(context);
        String str = "chId";
        if (m == null) {
            m = Core.getChannelName(context);
        }
        C1045s.m3251a(jSONObject, str, m);
        jSONObject.put("sdkVer", "1.0.008");
        C1045s.m3251a(jSONObject, "appVer", C1047b.m3274h(context));
        C1045s.m3251a(jSONObject, "appVerName", C1047b.m3273g(context));
        C1045s.m3251a(jSONObject, "pName", C1047b.m3272f(context));
        C1045s.m3251a(jSONObject, "lang", DroiDataCollector.getLang(context));
        C1045s.m3251a(jSONObject, "md5", C1047b.m3275i(context));
        return jSONObject;
    }

    public static void m3251a(JSONObject jSONObject, String str, String str2) {
        if (str2 != null && !str2.equals("")) {
            jSONObject.put(str, str2);
        }
    }

    protected static JSONObject m3249a(Context context) {
        JSONObject b;
        Exception e;
        try {
            b = C1045s.m3252b(context);
            try {
                b.put("deltaError", C1047b.m3270e(context));
            } catch (JSONException e2) {
                e = e2;
                DroiLog.m2869e("UpdateRequest", e);
                return b;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            b = null;
            e = exception;
            DroiLog.m2869e("UpdateRequest", e);
            return b;
        }
        return b;
    }

    protected static JSONObject m3250a(Context context, String str, int i) {
        JSONObject jSONObject = null;
        if (str == null) {
            try {
                Log.w("UpdateRequest", "fileName is null");
            } catch (Exception e) {
                DroiLog.m2869e("UpdateRequest", e);
            }
        } else {
            jSONObject = C1045s.m3252b(context);
            String trim = str.trim();
            jSONObject.put("appVer", i);
            jSONObject.put("fileName", trim);
        }
        return jSONObject;
    }
}
