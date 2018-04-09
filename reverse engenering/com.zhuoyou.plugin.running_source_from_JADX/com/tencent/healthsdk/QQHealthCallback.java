package com.tencent.healthsdk;

import org.json.JSONObject;

public interface QQHealthCallback {
    String getHealthData();

    void onComplete(JSONObject jSONObject);
}
