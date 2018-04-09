package com.baidu.mapapi.cloud;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudSearchResult extends BaseSearchResult {
    public List<CloudPoiInfo> poiList;

    void mo1758a(JSONObject jSONObject) throws JSONException {
        super.mo1758a(jSONObject);
        this.poiList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("contents");
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    CloudPoiInfo cloudPoiInfo = new CloudPoiInfo();
                    cloudPoiInfo.m1043a(optJSONObject);
                    this.poiList.add(cloudPoiInfo);
                }
            }
        }
    }
}
