package com.baidu.mapapi.search.district;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0540b {
    public static DistrictResult m1490a(String str) {
        List list = null;
        if (str == null || "".equals(str)) {
            return list;
        }
        DistrictResult districtResult = new DistrictResult();
        try {
            JSONObject jSONObject = new JSONObject(str);
            districtResult.m1465a(jSONObject.optInt("city_code"));
            String optString = jSONObject.optString("city_name");
            districtResult.m1467a(optString);
            districtResult.m1466a(CoordUtil.decodeLocation(jSONObject.optString("center")));
            int optInt = jSONObject.optInt("polylines_isgeo");
            List arrayList = new ArrayList();
            if (optInt == 0) {
                JSONArray optJSONArray = jSONObject.optJSONArray("polylines");
                if (optJSONArray == null || optJSONArray.length() == 0) {
                    districtResult.error = ERRORNO.RESULT_NOT_FOUND;
                    return districtResult;
                }
                for (int i = 0; i < optJSONArray.length(); i++) {
                    List arrayList2 = new ArrayList();
                    String optString2 = optJSONArray.optString(i);
                    if (!(optString2 == null || optString2.length() == 0)) {
                        String[] split = optString2.split("\\,");
                        for (int i2 = 0; i2 < split.length; i2 += 2) {
                            try {
                                arrayList2.add(CoordUtil.mc2ll(new GeoPoint(Double.valueOf(split[i2 + 1]).doubleValue(), Double.valueOf(split[i2]).doubleValue())));
                            } catch (Exception e) {
                            }
                        }
                        arrayList.add(arrayList2);
                    }
                }
            } else if (optInt == 1) {
                if (optString != null) {
                    if ((optString.indexOf("福建") > -1 || optString.indexOf("浙江") > -1) && optString.length() <= 3) {
                        list = C0540b.m1491b(jSONObject.optString("polylines"));
                    } else {
                        try {
                            list = CoordUtil.decodeLocationList2D(jSONObject.optString("polylines"));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                if (r0 != null) {
                    for (List<LatLng> list2 : r0) {
                        List arrayList3 = new ArrayList();
                        for (LatLng add : list2) {
                            arrayList3.add(add);
                        }
                        arrayList.add(arrayList3);
                    }
                }
            }
            if (arrayList.size() > 0) {
                districtResult.m1468a(arrayList);
            }
            return districtResult;
        } catch (JSONException e3) {
            e3.printStackTrace();
            districtResult.error = ERRORNO.RESULT_NOT_FOUND;
            return districtResult;
        }
    }

    private static List<List<LatLng>> m1491b(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String[] split = str.split("\\|");
        if (split.length < 3) {
            return null;
        }
        String[] split2 = split[2].split("\\;");
        if (split2 == null || split2.length == 0) {
            return null;
        }
        List<List<LatLng>> arrayList = new ArrayList();
        for (String split3 : split2) {
            String[] split4 = split3.split("\\,");
            List arrayList2 = new ArrayList();
            for (int i = 0; i < split4.length; i += 2) {
                try {
                    arrayList2.add(CoordUtil.mc2ll(new GeoPoint(Double.valueOf(split4[i + 1]).doubleValue(), Double.valueOf(split4[i]).doubleValue())));
                } catch (Exception e) {
                }
            }
            arrayList.add(arrayList2);
        }
        return arrayList;
    }
}
