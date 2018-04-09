package com.droi.sdk.selfupdate;

import com.droi.sdk.core.DroiHttpRequest;
import com.droi.sdk.core.DroiHttpRequest.Request;
import com.droi.sdk.core.DroiHttpRequest.Response;
import com.droi.sdk.core.SelfUpdateCoreHelper;
import com.droi.sdk.core.priv.C0939m;
import com.droi.sdk.internal.DroiLog;
import org.json.JSONObject;

public class C1024a {
    static DroiUpdateResponse m3195a(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        if (jSONObject != null) {
            DroiLog.m2871i("DroiUpdateConnection", jSONObject.toString());
            jSONObject2 = C1024a.m3197c(jSONObject);
        }
        if (jSONObject2 == null) {
            return null;
        }
        DroiLog.m2871i("DroiUpdateConnection", jSONObject2.toString());
        return DroiUpdateResponse.m3192a(jSONObject2);
    }

    static DroiInappUpdateResponse m3196b(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        if (jSONObject != null) {
            DroiLog.m2871i("DroiUpdateConnection", jSONObject.toString());
            jSONObject2 = C1024a.m3197c(jSONObject);
        }
        if (jSONObject2 == null) {
            return null;
        }
        DroiLog.m2871i("DroiUpdateConnection", jSONObject2.toString());
        return DroiInappUpdateResponse.m3181a(jSONObject2);
    }

    private static JSONObject m3197c(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        DroiHttpRequest instance = DroiHttpRequest.instance();
        String appId = SelfUpdateCoreHelper.getAppId();
        String deviceId = SelfUpdateCoreHelper.getDeviceId(C1032g.f3423a);
        Request make = Request.make("/droiupdate/1/app", jSONObject.toString().getBytes());
        make.addHeader("X-Droi-AppID", appId);
        make.addHeader(C0939m.f3062b, appId);
        make.addHeader(C0939m.f3063c, deviceId);
        Response request = instance.request(make);
        if (request == null) {
            return null;
        }
        int errorCode = request.getErrorCode();
        int statusCode = request.getStatusCode();
        DroiLog.m2870e("DroiUpdateConnection", "errCode:" + errorCode);
        DroiLog.m2870e("DroiUpdateConnection", "statusCode:" + statusCode);
        if (errorCode != 0 || statusCode != 200) {
            return null;
        }
        try {
            return new JSONObject(new String(request.getData()));
        } catch (Exception e) {
            DroiLog.m2869e("DroiUpdateConnection", e);
            return null;
        }
    }
}
