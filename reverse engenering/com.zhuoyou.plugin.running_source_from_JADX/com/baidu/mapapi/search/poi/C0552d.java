package com.baidu.mapapi.search.poi;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.PoiInfo.POITYPE;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.droi.btlib.connection.MapConstants;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class C0552d {
    public static PoiResult m1566a(String str) {
        PoiResult poiResult = new PoiResult();
        if (str == null || "".equals(str)) {
            poiResult.error = ERRORNO.RESULT_NOT_FOUND;
            return poiResult;
        }
        try {
            JSONArray optJSONArray = new JSONObject(str).optJSONArray("citys");
            if (optJSONArray == null || optJSONArray.length() <= 0) {
                poiResult.error = ERRORNO.RESULT_NOT_FOUND;
                return poiResult;
            }
            List arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject = (JSONObject) optJSONArray.opt(i);
                if (jSONObject != null) {
                    CityInfo cityInfo = new CityInfo();
                    cityInfo.num = jSONObject.optInt("num");
                    cityInfo.city = jSONObject.optString("name");
                    arrayList.add(cityInfo);
                }
            }
            if (arrayList.size() > 0) {
                poiResult.m1536c(arrayList);
            }
            poiResult.error = ERRORNO.AMBIGUOUS_KEYWORD;
            return poiResult;
        } catch (JSONException e) {
            e.printStackTrace();
            poiResult.error = ERRORNO.RESULT_NOT_FOUND;
            return poiResult;
        }
    }

    public static PoiResult m1567a(String str, int i, int i2) {
        int i3 = 0;
        PoiResult poiResult = new PoiResult();
        if (str == null || str.equals("")) {
            poiResult.error = ERRORNO.RESULT_NOT_FOUND;
            return poiResult;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("total");
            int optInt2 = jSONObject.optInt(ParamKey.COUNT);
            if (optInt2 == 0) {
                poiResult.error = ERRORNO.RESULT_NOT_FOUND;
                return poiResult;
            }
            JSONObject optJSONObject;
            poiResult.m1533b(optInt);
            poiResult.m1537d(optInt);
            poiResult.m1535c(optInt2);
            poiResult.m1530a(i);
            if (optInt2 != 0) {
                poiResult.m1533b((optInt % i2 > 0 ? 1 : 0) + (optInt / i2));
            }
            JSONObject optJSONObject2 = jSONObject.optJSONObject("current_city");
            String str2 = null;
            if (optJSONObject2 != null) {
                str2 = optJSONObject2.optString("name");
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("pois");
            List arrayList = new ArrayList();
            if (optJSONArray != null) {
                for (optInt2 = 0; optInt2 < optJSONArray.length(); optInt2++) {
                    JSONObject optJSONObject3 = optJSONArray.optJSONObject(optInt2);
                    PoiInfo poiInfo = new PoiInfo();
                    if (optJSONObject3 != null) {
                        poiInfo.name = optJSONObject3.optString("name");
                        poiInfo.address = optJSONObject3.optString("addr");
                        poiInfo.uid = optJSONObject3.optString("uid");
                        poiInfo.phoneNum = optJSONObject3.optString("tel");
                        poiInfo.type = POITYPE.fromInt(optJSONObject3.optInt("type"));
                        poiInfo.isPano = optJSONObject3.optInt("pano") == 1;
                        if (!(poiInfo.type == POITYPE.BUS_LINE || poiInfo.type == POITYPE.SUBWAY_LINE)) {
                            poiInfo.location = CoordUtil.decodeLocation(optJSONObject3.optString("geo"));
                        }
                        poiInfo.city = str2;
                        optJSONObject = optJSONObject3.optJSONObject("place");
                        if (optJSONObject != null) {
                            if ("cater".equals(optJSONObject.optString("src_name")) && optJSONObject3.optBoolean("detail")) {
                                poiInfo.hasCaterDetails = true;
                            }
                        }
                        arrayList.add(poiInfo);
                    }
                }
            }
            if (arrayList.size() > 0) {
                poiResult.m1531a(arrayList);
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("addrs");
            List arrayList2 = new ArrayList();
            if (optJSONArray2 != null) {
                while (i3 < optJSONArray2.length()) {
                    optJSONObject = optJSONArray2.optJSONObject(i3);
                    PoiAddrInfo poiAddrInfo = new PoiAddrInfo();
                    if (optJSONObject != null) {
                        poiAddrInfo.name = optJSONObject.optString("name");
                        poiAddrInfo.address = optJSONObject.optString("addr");
                        poiAddrInfo.location = CoordUtil.decodeLocation(optJSONObject.optString("geo"));
                        arrayList2.add(poiAddrInfo);
                    }
                    i3++;
                }
            }
            if (arrayList2.size() > 0) {
                poiResult.m1534b(arrayList2);
                poiResult.m1532a(true);
            }
            return poiResult;
        } catch (JSONException e) {
            e.printStackTrace();
            poiResult.error = ERRORNO.RESULT_NOT_FOUND;
            return poiResult;
        }
    }

    public static PoiIndoorResult m1568b(String str) {
        PoiIndoorResult poiIndoorResult = new PoiIndoorResult();
        if (str == null || "".equals(str)) {
            poiIndoorResult.error = ERRORNO.RESULT_NOT_FOUND;
            return poiIndoorResult;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONArray optJSONArray = jSONObject.optJSONArray("poi_list");
            if (optJSONArray == null || optJSONArray.length() <= 0) {
                poiIndoorResult.error = ERRORNO.RESULT_NOT_FOUND;
            } else {
                List arrayList = new ArrayList();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(i);
                    if (jSONObject2 != null) {
                        PoiIndoorInfo poiIndoorInfo = new PoiIndoorInfo();
                        poiIndoorInfo.address = jSONObject2.optString(MapConstants.ADDRESS);
                        poiIndoorInfo.bid = jSONObject2.optString("bd_id");
                        poiIndoorInfo.cid = jSONObject2.optInt("cid");
                        poiIndoorInfo.discount = jSONObject2.optInt("discount");
                        poiIndoorInfo.floor = jSONObject2.optString("floor");
                        poiIndoorInfo.name = jSONObject2.optString("name");
                        poiIndoorInfo.phone = jSONObject2.optString("phone");
                        poiIndoorInfo.price = (double) jSONObject2.optInt("price");
                        poiIndoorInfo.starLevel = jSONObject2.optInt("star_level");
                        poiIndoorInfo.tag = jSONObject2.optString("tag");
                        poiIndoorInfo.uid = jSONObject2.optString("uid");
                        poiIndoorInfo.groupNum = jSONObject2.optInt("tuan_nums");
                        poiIndoorInfo.isGroup = jSONObject2.optBoolean("t_flag");
                        poiIndoorInfo.isTakeOut = jSONObject2.optBoolean("w_flag");
                        poiIndoorInfo.isWaited = jSONObject2.optBoolean("p_flag");
                        poiIndoorInfo.latLng = CoordUtil.mc2ll(new GeoPoint(jSONObject2.optDouble("pt_y"), jSONObject2.optDouble("pt_x")));
                        arrayList.add(poiIndoorInfo);
                    }
                }
                poiIndoorResult.error = ERRORNO.NO_ERROR;
                poiIndoorResult.setmArrayPoiInfo(arrayList);
            }
            poiIndoorResult.f1634b = jSONObject.optInt("page_num");
            poiIndoorResult.f1633a = jSONObject.optInt("poi_num");
            return poiIndoorResult;
        } catch (JSONException e) {
            e.printStackTrace();
            poiIndoorResult.error = ERRORNO.RESULT_NOT_FOUND;
            return poiIndoorResult;
        }
    }
}
