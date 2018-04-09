package com.adroi.sdk;

import org.json.JSONObject;

public interface NativeAdsListener {
    void onAdFailed(JSONObject jSONObject);

    void onAdReady(JSONObject jSONObject);
}
