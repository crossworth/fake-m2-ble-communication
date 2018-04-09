package com.baidu.mapapi.search.sug;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0584c {
    public static SuggestionResult m1776a(String str) {
        SuggestionResult suggestionResult = new SuggestionResult();
        if (str != null) {
            if (!str.equals("")) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    JSONArray optJSONArray = jSONObject.optJSONArray("cityname");
                    JSONArray optJSONArray2 = jSONObject.optJSONArray(ParamKey.POINAME);
                    JSONArray optJSONArray3 = jSONObject.optJSONArray("districtname");
                    JSONArray optJSONArray4 = jSONObject.optJSONArray(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON);
                    JSONArray optJSONArray5 = jSONObject.optJSONArray(ParamKey.POIID);
                    if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                        ArrayList arrayList = new ArrayList();
                        suggestionResult.m1752a(arrayList);
                        int length = optJSONArray2.length();
                        for (int i = 0; i < length; i++) {
                            SuggestionInfo suggestionInfo = new SuggestionInfo();
                            if (optJSONArray != null) {
                                suggestionInfo.city = optJSONArray.optString(i);
                            }
                            if (optJSONArray2 != null) {
                                suggestionInfo.key = optJSONArray2.optString(i);
                            }
                            if (optJSONArray3 != null) {
                                suggestionInfo.district = optJSONArray3.optString(i);
                            }
                            JSONObject optJSONObject = optJSONArray4.optJSONObject(i);
                            if (optJSONObject.has("x") && optJSONObject.has("y")) {
                                GeoPoint geoPoint = new GeoPoint(0.0d, 0.0d);
                                geoPoint.setLongitudeE6((double) ((int) optJSONObject.optDouble("x")));
                                geoPoint.setLatitudeE6((double) ((int) optJSONObject.optDouble("y")));
                                suggestionInfo.pt = CoordUtil.mc2ll(geoPoint);
                            }
                            if (optJSONArray5 != null) {
                                suggestionInfo.uid = optJSONArray5.optString(i);
                            }
                            arrayList.add(suggestionInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    suggestionResult.error = ERRORNO.RESULT_NOT_FOUND;
                }
                return suggestionResult;
            }
        }
        suggestionResult.error = ERRORNO.RESULT_NOT_FOUND;
        return suggestionResult;
    }
}
