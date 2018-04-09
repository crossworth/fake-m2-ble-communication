package com.droi.sdk.feedback;

import android.util.Log;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.core.DroiHttpRequest;
import com.droi.sdk.core.DroiHttpRequest.Request;
import com.droi.sdk.core.DroiHttpRequest.Response;
import com.droi.sdk.core.FeedbackCoreHelper;
import com.droi.sdk.core.priv.C0939m;
import com.droi.sdk.internal.DroiLog;
import java.util.List;
import org.json.JSONObject;

public class C0955e {
    static boolean m2825a(String str, String str2, List<String> list) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("userId", C0958h.m2831a());
            jSONObject.put(MessageObj.APPID, FeedbackCoreHelper.getAppId());
            jSONObject.put("did", FeedbackCoreHelper.getDeviceId());
            jSONObject.put("platform", "android");
            jSONObject.put("contact", str);
            jSONObject.put("content", str2);
            DroiLog.m2871i("DroiFeedbackConnection", "sendFeedback:" + jSONObject.toString());
            String a = C0955e.m2824a(jSONObject, "/droifeedback/1/add");
            if (a != null) {
                Log.w("DroiFeedbackConnection", a);
                JSONObject jSONObject2 = new JSONObject(a);
                if (jSONObject2 != null && jSONObject2.getInt("code") == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    static String m2823a() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("userId", C0958h.m2831a());
        jSONObject.put(MessageObj.APPID, FeedbackCoreHelper.getAppId());
        return C0955e.m2824a(jSONObject, "/droifeedback/1/list");
    }

    private static String m2824a(JSONObject jSONObject, String str) {
        DroiHttpRequest instance = DroiHttpRequest.instance();
        String appId = FeedbackCoreHelper.getAppId();
        String deviceId = FeedbackCoreHelper.getDeviceId();
        Request make = Request.make(str, jSONObject.toString().getBytes());
        make.addHeader("X-Droi-AppID", appId);
        make.addHeader(C0939m.f3062b, appId);
        make.addHeader(C0939m.f3063c, deviceId);
        Response request = instance.request(make);
        if (request != null) {
            int errorCode = request.getErrorCode();
            int statusCode = request.getStatusCode();
            DroiLog.m2870e("DroiFeedbackConnection", "errCode:" + errorCode);
            DroiLog.m2870e("DroiFeedbackConnection", "statusCode:" + statusCode);
            if (errorCode == 0 && statusCode == 200) {
                return new String(request.getData());
            }
        }
        return null;
    }
}
