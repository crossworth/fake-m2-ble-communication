package com.baidu.mapapi.search.share;

import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import org.json.JSONException;
import org.json.JSONObject;

public class C0579b {
    public static ShareUrlResult m1751a(String str) {
        ShareUrlResult shareUrlResult = new ShareUrlResult();
        if (str == null) {
            shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject != null) {
                    shareUrlResult.m1726a(jSONObject.optString("url"));
                    shareUrlResult.m1725a(jSONObject.optInt("type"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return shareUrlResult;
    }
}
