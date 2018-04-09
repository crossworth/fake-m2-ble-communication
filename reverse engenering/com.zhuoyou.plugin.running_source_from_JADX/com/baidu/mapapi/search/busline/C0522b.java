package com.baidu.mapapi.search.busline;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.busline.BusLineResult.BusStation;
import com.baidu.mapapi.search.busline.BusLineResult.BusStep;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0522b {
    public static BusLineResult m1432a(String str) {
        boolean z = true;
        int i = 0;
        if (str == null || "".equals(str)) {
            return null;
        }
        BusLineResult busLineResult = new BusLineResult();
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt(ParamKey.COUNT);
            JSONArray optJSONArray = jSONObject.optJSONArray("details");
            if (optJSONArray == null || optInt <= 0) {
                busLineResult.error = ERRORNO.RESULT_NOT_FOUND;
                return busLineResult;
            }
            JSONObject optJSONObject = optJSONArray.optJSONObject(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            try {
                busLineResult.m1388a(simpleDateFormat.parse(optJSONObject.optString(LogBuilder.KEY_START_TIME)));
                busLineResult.m1392b(simpleDateFormat.parse(optJSONObject.optString(LogBuilder.KEY_END_TIME)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            busLineResult.m1387a(optJSONObject.optString("name"));
            if (optJSONObject.optInt("ismonticket") != 1) {
                z = false;
            }
            busLineResult.m1390a(z);
            busLineResult.m1391b(optJSONObject.optString("uid"));
            busLineResult.setBasePrice(((float) optJSONObject.optInt("ticketPrice")) / 100.0f);
            busLineResult.setLineDirection(optJSONObject.optString("lineDirection"));
            busLineResult.setMaxPrice(((float) optJSONObject.optInt("maxprice")) / 100.0f);
            List arrayList = new ArrayList();
            List<List> decodeLocationList2D = CoordUtil.decodeLocationList2D(optJSONObject.optString("geo"));
            if (decodeLocationList2D != null) {
                for (List list : decodeLocationList2D) {
                    BusStep busStep = new BusStep();
                    busStep.setWayPoints(list);
                    arrayList.add(busStep);
                }
            }
            if (arrayList.size() > 0) {
                busLineResult.m1393b(arrayList);
            }
            JSONArray optJSONArray2 = optJSONObject.optJSONArray("stations");
            if (optJSONArray2 != null) {
                arrayList = new ArrayList();
                while (i < optJSONArray2.length()) {
                    optJSONObject = optJSONArray2.optJSONObject(i);
                    if (optJSONObject != null) {
                        BusStation busStation = new BusStation();
                        busStation.setTitle(optJSONObject.optString("name"));
                        busStation.setLocation(CoordUtil.decodeLocation(optJSONObject.optString("geo")));
                        busStation.setUid(optJSONObject.optString("uid"));
                        arrayList.add(busStation);
                    }
                    i++;
                }
                if (arrayList.size() > 0) {
                    busLineResult.m1389a(arrayList);
                }
            }
            return busLineResult;
        } catch (JSONException e2) {
            e2.printStackTrace();
            busLineResult.error = ERRORNO.RESULT_NOT_FOUND;
            return busLineResult;
        }
    }
}
