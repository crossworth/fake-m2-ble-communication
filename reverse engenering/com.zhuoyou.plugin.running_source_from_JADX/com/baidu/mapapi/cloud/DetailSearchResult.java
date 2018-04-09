package com.baidu.mapapi.cloud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailSearchResult extends BaseSearchResult {
    public CloudPoiInfo poiInfo;

    void mo1758a(JSONObject jSONObject) throws JSONException {
        super.mo1758a(jSONObject);
        JSONArray optJSONArray = jSONObject.optJSONArray("contents");
        if (optJSONArray != null) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(0);
            if (optJSONObject != null) {
                this.poiInfo = new CloudPoiInfo();
                this.poiInfo.m1043a(optJSONObject);
            }
        }
    }
}
