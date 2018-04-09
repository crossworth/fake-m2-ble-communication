package com.amap.api.services.proguard;

import android.text.TextUtils;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.BusinessArea;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.help.Tip;
import com.amap.api.services.nearby.NearbyInfo;
import com.amap.api.services.poisearch.IndoorData;
import com.amap.api.services.poisearch.SubPoiItem;
import com.amap.api.services.road.Crossroad;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.District;
import com.amap.api.services.route.Doorway;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteBusWalkItem;
import com.amap.api.services.route.RouteSearchCity;
import com.amap.api.services.route.TMC;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherLive;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: JSONHelper */
public class C0391n {
    public static ArrayList<NearbyInfo> m1602a(JSONObject jSONObject, boolean z) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray("datas");
        if (jSONArray == null || jSONArray.length() == 0) {
            return new ArrayList();
        }
        ArrayList<NearbyInfo> arrayList = new ArrayList();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            String a = C0391n.m1600a(jSONObject2, "userid");
            String a2 = C0391n.m1600a(jSONObject2, "location");
            double d = 0.0d;
            double d2 = 0.0d;
            if (a2 != null) {
                String[] split = a2.split(SeparatorConstants.SEPARATOR_ADS_ID);
                if (split.length == 2) {
                    d = C0391n.m1635l(split[0]);
                    d2 = C0391n.m1635l(split[1]);
                }
            }
            a2 = C0391n.m1600a(jSONObject2, "distance");
            long m = C0391n.m1637m(C0391n.m1600a(jSONObject2, "updatetime"));
            int j = C0391n.m1631j(a2);
            LatLonPoint latLonPoint = new LatLonPoint(d2, d);
            NearbyInfo nearbyInfo = new NearbyInfo();
            nearbyInfo.setUserID(a);
            nearbyInfo.setTimeStamp(m);
            nearbyInfo.setPoint(latLonPoint);
            if (z) {
                nearbyInfo.setDrivingDistance(j);
            } else {
                nearbyInfo.setDistance(j);
            }
            arrayList.add(nearbyInfo);
        }
        return arrayList;
    }

    public static ArrayList<SuggestionCity> m1601a(JSONObject jSONObject) throws JSONException, NumberFormatException {
        ArrayList<SuggestionCity> arrayList = new ArrayList();
        if (!jSONObject.has("cities")) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("cities");
        if (optJSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(new SuggestionCity(C0391n.m1600a(optJSONObject, "name"), C0391n.m1600a(optJSONObject, "citycode"), C0391n.m1600a(optJSONObject, "adcode"), C0391n.m1631j(C0391n.m1600a(optJSONObject, "num"))));
            }
        }
        return arrayList;
    }

    public static ArrayList<String> m1611b(JSONObject jSONObject) throws JSONException {
        ArrayList<String> arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("keywords");
        if (optJSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            arrayList.add(optJSONArray.optString(i));
        }
        return arrayList;
    }

    public static ArrayList<PoiItem> m1615c(JSONObject jSONObject) throws JSONException {
        ArrayList<PoiItem> arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("pois");
        if (optJSONArray == null || optJSONArray.length() == 0) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(C0391n.m1618d(optJSONObject));
            }
        }
        return arrayList;
    }

    public static PoiItem m1618d(JSONObject jSONObject) throws JSONException {
        int i = 0;
        PoiItem poiItem = new PoiItem(C0391n.m1600a(jSONObject, "id"), C0391n.m1609b(jSONObject, "location"), C0391n.m1600a(jSONObject, "name"), C0391n.m1600a(jSONObject, "address"));
        poiItem.setAdCode(C0391n.m1600a(jSONObject, "adcode"));
        poiItem.setProvinceName(C0391n.m1600a(jSONObject, "pname"));
        poiItem.setCityName(C0391n.m1600a(jSONObject, "cityname"));
        poiItem.setAdName(C0391n.m1600a(jSONObject, "adname"));
        poiItem.setCityCode(C0391n.m1600a(jSONObject, "citycode"));
        poiItem.setProvinceCode(C0391n.m1600a(jSONObject, "pcode"));
        poiItem.setDirection(C0391n.m1600a(jSONObject, "direction"));
        if (jSONObject.has("distance")) {
            String a = C0391n.m1600a(jSONObject, "distance");
            if (!C0391n.m1630i(a)) {
                try {
                    poiItem.setDistance((int) Float.parseFloat(a));
                } catch (Throwable e) {
                    C0390i.m1594a(e, "JSONHelper", "parseBasePoi");
                } catch (Throwable e2) {
                    C0390i.m1594a(e2, "JSONHelper", "parseBasePoi");
                }
            }
        }
        poiItem.setTel(C0391n.m1600a(jSONObject, "tel"));
        poiItem.setTypeDes(C0391n.m1600a(jSONObject, "type"));
        poiItem.setEnter(C0391n.m1609b(jSONObject, "entr_location"));
        poiItem.setExit(C0391n.m1609b(jSONObject, "exit_location"));
        poiItem.setWebsite(C0391n.m1600a(jSONObject, "website"));
        poiItem.setPostcode(C0391n.m1600a(jSONObject, "postcode"));
        poiItem.setBusinessArea(C0391n.m1600a(jSONObject, "business_area"));
        poiItem.setEmail(C0391n.m1600a(jSONObject, "email"));
        if (C0391n.m1628h(C0391n.m1600a(jSONObject, "indoor_map"))) {
            poiItem.setIndoorMap(false);
        } else {
            poiItem.setIndoorMap(true);
        }
        poiItem.setParkingType(C0391n.m1600a(jSONObject, "parking_type"));
        List arrayList = new ArrayList();
        if (jSONObject.has("children")) {
            JSONArray optJSONArray = jSONObject.optJSONArray("children");
            if (optJSONArray == null) {
                poiItem.setSubPois(arrayList);
            } else {
                while (i < optJSONArray.length()) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        arrayList.add(C0391n.m1649w(optJSONObject));
                    }
                    i++;
                }
                poiItem.setSubPois(arrayList);
            }
        }
        poiItem.setIndoorDate(C0391n.m1619d(jSONObject, "indoor_data"));
        return poiItem;
    }

    private static SubPoiItem m1649w(JSONObject jSONObject) throws JSONException {
        SubPoiItem subPoiItem = new SubPoiItem(C0391n.m1600a(jSONObject, "id"), C0391n.m1609b(jSONObject, "location"), C0391n.m1600a(jSONObject, "name"), C0391n.m1600a(jSONObject, "address"));
        subPoiItem.setSubName(C0391n.m1600a(jSONObject, "sname"));
        subPoiItem.setSubTypeDes(C0391n.m1600a(jSONObject, "subtype"));
        if (jSONObject.has("distance")) {
            String a = C0391n.m1600a(jSONObject, "distance");
            if (!C0391n.m1630i(a)) {
                try {
                    subPoiItem.setDistance((int) Float.parseFloat(a));
                } catch (Throwable e) {
                    C0390i.m1594a(e, "JSONHelper", "parseSubPoiItem");
                } catch (Throwable e2) {
                    C0390i.m1594a(e2, "JSONHelper", "parseSubPoiItem");
                }
            }
        }
        return subPoiItem;
    }

    public static ArrayList<BusStationItem> m1622e(JSONObject jSONObject) throws JSONException {
        ArrayList<BusStationItem> arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("busstops");
        if (optJSONArray == null || optJSONArray.length() == 0) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(C0391n.m1623f(optJSONObject));
            }
        }
        return arrayList;
    }

    public static BusStationItem m1623f(JSONObject jSONObject) throws JSONException {
        BusStationItem g = C0391n.m1625g(jSONObject);
        if (g == null) {
            return g;
        }
        g.setAdCode(C0391n.m1600a(jSONObject, "adcode"));
        g.setCityCode(C0391n.m1600a(jSONObject, "citycode"));
        JSONArray optJSONArray = jSONObject.optJSONArray("buslines");
        List arrayList = new ArrayList();
        if (optJSONArray == null) {
            g.setBusLineItems(arrayList);
            return g;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(C0391n.m1627h(optJSONObject));
            }
        }
        g.setBusLineItems(arrayList);
        return g;
    }

    public static BusStationItem m1625g(JSONObject jSONObject) throws JSONException {
        BusStationItem busStationItem = new BusStationItem();
        busStationItem.setBusStationId(C0391n.m1600a(jSONObject, "id"));
        busStationItem.setLatLonPoint(C0391n.m1609b(jSONObject, "location"));
        busStationItem.setBusStationName(C0391n.m1600a(jSONObject, "name"));
        return busStationItem;
    }

    public static BusLineItem m1627h(JSONObject jSONObject) throws JSONException {
        BusLineItem busLineItem = new BusLineItem();
        busLineItem.setBusLineId(C0391n.m1600a(jSONObject, "id"));
        busLineItem.setBusLineType(C0391n.m1600a(jSONObject, "type"));
        busLineItem.setBusLineName(C0391n.m1600a(jSONObject, "name"));
        busLineItem.setDirectionsCoordinates(C0391n.m1616c(jSONObject, "polyline"));
        busLineItem.setCityCode(C0391n.m1600a(jSONObject, "citycode"));
        busLineItem.setOriginatingStation(C0391n.m1600a(jSONObject, "start_stop"));
        busLineItem.setTerminalStation(C0391n.m1600a(jSONObject, "end_stop"));
        return busLineItem;
    }

    public static ArrayList<BusLineItem> m1629i(JSONObject jSONObject) throws JSONException {
        ArrayList<BusLineItem> arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("buslines");
        if (optJSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(C0391n.m1632j(optJSONObject));
            }
        }
        return arrayList;
    }

    public static BusLineItem m1632j(JSONObject jSONObject) throws JSONException {
        BusLineItem h = C0391n.m1627h(jSONObject);
        if (h == null) {
            return h;
        }
        h.setFirstBusTime(C0390i.m1597c(C0391n.m1600a(jSONObject, "start_time")));
        h.setLastBusTime(C0390i.m1597c(C0391n.m1600a(jSONObject, "end_time")));
        h.setBusCompany(C0391n.m1600a(jSONObject, "company"));
        h.setDistance(C0391n.m1633k(C0391n.m1600a(jSONObject, "distance")));
        h.setBasicPrice(C0391n.m1633k(C0391n.m1600a(jSONObject, "basic_price")));
        h.setTotalPrice(C0391n.m1633k(C0391n.m1600a(jSONObject, "total_price")));
        h.setBounds(C0391n.m1616c(jSONObject, "bounds"));
        List arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("busstops");
        if (optJSONArray == null) {
            h.setBusStations(arrayList);
            return h;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(C0391n.m1625g(optJSONObject));
            }
        }
        h.setBusStations(arrayList);
        return h;
    }

    public static DistrictItem m1634k(JSONObject jSONObject) throws JSONException {
        DistrictItem districtItem = new DistrictItem();
        districtItem.setCitycode(C0391n.m1600a(jSONObject, "citycode"));
        districtItem.setAdcode(C0391n.m1600a(jSONObject, "adcode"));
        districtItem.setName(C0391n.m1600a(jSONObject, "name"));
        districtItem.setLevel(C0391n.m1600a(jSONObject, LogColumns.LEVEL));
        districtItem.setCenter(C0391n.m1609b(jSONObject, "center"));
        if (jSONObject.has("polyline")) {
            String string = jSONObject.getString("polyline");
            if (string != null && string.length() > 0) {
                districtItem.setDistrictBoundary(string.split("\\|"));
            }
        }
        C0391n.m1607a(jSONObject.optJSONArray("districts"), new ArrayList(), districtItem);
        return districtItem;
    }

    public static void m1607a(JSONArray jSONArray, ArrayList<DistrictItem> arrayList, DistrictItem districtItem) throws JSONException {
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject optJSONObject = jSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    arrayList.add(C0391n.m1634k(optJSONObject));
                }
            }
            if (districtItem != null) {
                districtItem.setSubDistrict(arrayList);
            }
        }
    }

    public static ArrayList<GeocodeAddress> m1636l(JSONObject jSONObject) throws JSONException {
        ArrayList<GeocodeAddress> arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("geocodes");
        if (optJSONArray == null || optJSONArray.length() == 0) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                GeocodeAddress geocodeAddress = new GeocodeAddress();
                geocodeAddress.setFormatAddress(C0391n.m1600a(optJSONObject, "formatted_address"));
                geocodeAddress.setProvince(C0391n.m1600a(optJSONObject, DistrictSearchQuery.KEYWORDS_PROVINCE));
                geocodeAddress.setCity(C0391n.m1600a(optJSONObject, DistrictSearchQuery.KEYWORDS_CITY));
                geocodeAddress.setDistrict(C0391n.m1600a(optJSONObject, DistrictSearchQuery.KEYWORDS_DISTRICT));
                geocodeAddress.setTownship(C0391n.m1600a(optJSONObject, "township"));
                geocodeAddress.setNeighborhood(C0391n.m1600a(optJSONObject.optJSONObject("neighborhood"), "name"));
                geocodeAddress.setBuilding(C0391n.m1600a(optJSONObject.optJSONObject("building"), "name"));
                geocodeAddress.setAdcode(C0391n.m1600a(optJSONObject, "adcode"));
                geocodeAddress.setLatLonPoint(C0391n.m1609b(optJSONObject, "location"));
                geocodeAddress.setLevel(C0391n.m1600a(optJSONObject, LogColumns.LEVEL));
                arrayList.add(geocodeAddress);
            }
        }
        return arrayList;
    }

    public static ArrayList<Tip> m1638m(JSONObject jSONObject) throws JSONException {
        ArrayList<Tip> arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("tips");
        if (optJSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            Tip tip = new Tip();
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                tip.setName(C0391n.m1600a(optJSONObject, "name"));
                tip.setDistrict(C0391n.m1600a(optJSONObject, DistrictSearchQuery.KEYWORDS_DISTRICT));
                tip.setAdcode(C0391n.m1600a(optJSONObject, "adcode"));
                tip.setID(C0391n.m1600a(optJSONObject, "id"));
                tip.setAddress(C0391n.m1600a(optJSONObject, "address"));
                Object a = C0391n.m1600a(optJSONObject, "location");
                if (!TextUtils.isEmpty(a)) {
                    String[] split = a.split(SeparatorConstants.SEPARATOR_ADS_ID);
                    if (split.length == 2) {
                        tip.setPostion(new LatLonPoint(Double.parseDouble(split[1]), Double.parseDouble(split[0])));
                    }
                }
                arrayList.add(tip);
            }
        }
        return arrayList;
    }

    public static void m1606a(JSONArray jSONArray, RegeocodeAddress regeocodeAddress) throws JSONException {
        List arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            Crossroad crossroad = new Crossroad();
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                crossroad.setId(C0391n.m1600a(optJSONObject, "id"));
                crossroad.setDirection(C0391n.m1600a(optJSONObject, "direction"));
                crossroad.setDistance(C0391n.m1633k(C0391n.m1600a(optJSONObject, "distance")));
                crossroad.setCenterPoint(C0391n.m1609b(optJSONObject, "location"));
                crossroad.setFirstRoadId(C0391n.m1600a(optJSONObject, "first_id"));
                crossroad.setFirstRoadName(C0391n.m1600a(optJSONObject, "first_name"));
                crossroad.setSecondRoadId(C0391n.m1600a(optJSONObject, "second_id"));
                crossroad.setSecondRoadName(C0391n.m1600a(optJSONObject, "second_name"));
                arrayList.add(crossroad);
            }
        }
        regeocodeAddress.setCrossroads(arrayList);
    }

    public static void m1613b(JSONArray jSONArray, RegeocodeAddress regeocodeAddress) throws JSONException {
        List arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            RegeocodeRoad regeocodeRoad = new RegeocodeRoad();
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                regeocodeRoad.setId(C0391n.m1600a(optJSONObject, "id"));
                regeocodeRoad.setName(C0391n.m1600a(optJSONObject, "name"));
                regeocodeRoad.setLatLngPoint(C0391n.m1609b(optJSONObject, "location"));
                regeocodeRoad.setDirection(C0391n.m1600a(optJSONObject, "direction"));
                regeocodeRoad.setDistance(C0391n.m1633k(C0391n.m1600a(optJSONObject, "distance")));
                arrayList.add(regeocodeRoad);
            }
        }
        regeocodeAddress.setRoads(arrayList);
    }

    public static void m1608a(JSONObject jSONObject, RegeocodeAddress regeocodeAddress) throws JSONException {
        regeocodeAddress.setProvince(C0391n.m1600a(jSONObject, DistrictSearchQuery.KEYWORDS_PROVINCE));
        regeocodeAddress.setCity(C0391n.m1600a(jSONObject, DistrictSearchQuery.KEYWORDS_CITY));
        regeocodeAddress.setCityCode(C0391n.m1600a(jSONObject, "citycode"));
        regeocodeAddress.setAdCode(C0391n.m1600a(jSONObject, "adcode"));
        regeocodeAddress.setDistrict(C0391n.m1600a(jSONObject, DistrictSearchQuery.KEYWORDS_DISTRICT));
        regeocodeAddress.setTownship(C0391n.m1600a(jSONObject, "township"));
        regeocodeAddress.setNeighborhood(C0391n.m1600a(jSONObject.optJSONObject("neighborhood"), "name"));
        regeocodeAddress.setBuilding(C0391n.m1600a(jSONObject.optJSONObject("building"), "name"));
        StreetNumber streetNumber = new StreetNumber();
        JSONObject optJSONObject = jSONObject.optJSONObject("streetNumber");
        streetNumber.setStreet(C0391n.m1600a(optJSONObject, "street"));
        streetNumber.setNumber(C0391n.m1600a(optJSONObject, "number"));
        streetNumber.setLatLonPoint(C0391n.m1609b(optJSONObject, "location"));
        streetNumber.setDirection(C0391n.m1600a(optJSONObject, "direction"));
        streetNumber.setDistance(C0391n.m1633k(C0391n.m1600a(optJSONObject, "distance")));
        regeocodeAddress.setStreetNumber(streetNumber);
        regeocodeAddress.setBusinessAreas(C0391n.m1639n(jSONObject));
        regeocodeAddress.setTowncode(C0391n.m1600a(jSONObject, "towncode"));
    }

    public static List<BusinessArea> m1639n(JSONObject jSONObject) throws JSONException {
        List<BusinessArea> arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("businessAreas");
        if (optJSONArray == null || optJSONArray.length() == 0) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            BusinessArea businessArea = new BusinessArea();
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                businessArea.setCenterPoint(C0391n.m1609b(optJSONObject, "location"));
                businessArea.setName(C0391n.m1600a(optJSONObject, "name"));
                arrayList.add(businessArea);
            }
        }
        return arrayList;
    }

    public static BusRouteResult m1599a(String str) throws AMapException {
        BusRouteResult busRouteResult = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("route")) {
                busRouteResult = new BusRouteResult();
                jSONObject = jSONObject.optJSONObject("route");
                if (jSONObject != null) {
                    busRouteResult.setStartPos(C0391n.m1609b(jSONObject, "origin"));
                    busRouteResult.setTargetPos(C0391n.m1609b(jSONObject, "destination"));
                    busRouteResult.setTaxiCost(C0391n.m1633k(C0391n.m1600a(jSONObject, "taxi_cost")));
                    if (jSONObject.has("transits")) {
                        JSONArray optJSONArray = jSONObject.optJSONArray("transits");
                        if (optJSONArray != null) {
                            busRouteResult.setPaths(C0391n.m1603a(optJSONArray));
                        }
                    }
                }
            }
            return busRouteResult;
        } catch (JSONException e) {
            throw new AMapException("协议解析错误 - ProtocolException");
        }
    }

    public static List<BusPath> m1603a(JSONArray jSONArray) throws JSONException {
        List<BusPath> arrayList = new ArrayList();
        if (jSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            BusPath busPath = new BusPath();
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                busPath.setCost(C0391n.m1633k(C0391n.m1600a(optJSONObject, "cost")));
                busPath.setDuration(C0391n.m1637m(C0391n.m1600a(optJSONObject, "duration")));
                busPath.setNightBus(C0391n.m1640n(C0391n.m1600a(optJSONObject, "nightflag")));
                busPath.setWalkDistance(C0391n.m1633k(C0391n.m1600a(optJSONObject, "walking_distance")));
                JSONArray optJSONArray = optJSONObject.optJSONArray("segments");
                if (optJSONArray != null) {
                    List arrayList2 = new ArrayList();
                    float f = 0.0f;
                    float f2 = 0.0f;
                    for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                        JSONObject optJSONObject2 = optJSONArray.optJSONObject(i2);
                        if (optJSONObject2 != null) {
                            BusStep o = C0391n.m1641o(optJSONObject2);
                            if (o != null) {
                                arrayList2.add(o);
                                if (o.getWalk() != null) {
                                    f += o.getWalk().getDistance();
                                }
                                if (o.getBusLine() != null) {
                                    f2 += o.getBusLine().getDistance();
                                }
                            }
                        }
                    }
                    busPath.setSteps(arrayList2);
                    busPath.setBusDistance(f2);
                    busPath.setWalkDistance(f);
                    arrayList.add(busPath);
                }
            }
        }
        return arrayList;
    }

    public static BusStep m1641o(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        BusStep busStep = new BusStep();
        JSONObject optJSONObject = jSONObject.optJSONObject("walking");
        if (optJSONObject != null) {
            busStep.setWalk(C0391n.m1642p(optJSONObject));
        }
        optJSONObject = jSONObject.optJSONObject("bus");
        if (optJSONObject != null) {
            busStep.setBusLines(C0391n.m1643q(optJSONObject));
        }
        optJSONObject = jSONObject.optJSONObject("entrance");
        if (optJSONObject != null) {
            busStep.setEntrance(C0391n.m1644r(optJSONObject));
        }
        optJSONObject = jSONObject.optJSONObject("exit");
        if (optJSONObject == null) {
            return busStep;
        }
        busStep.setExit(C0391n.m1644r(optJSONObject));
        return busStep;
    }

    public static RouteBusWalkItem m1642p(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        RouteBusWalkItem routeBusWalkItem = new RouteBusWalkItem();
        routeBusWalkItem.setOrigin(C0391n.m1609b(jSONObject, "origin"));
        routeBusWalkItem.setDestination(C0391n.m1609b(jSONObject, "destination"));
        routeBusWalkItem.setDistance(C0391n.m1633k(C0391n.m1600a(jSONObject, "distance")));
        routeBusWalkItem.setDuration(C0391n.m1637m(C0391n.m1600a(jSONObject, "duration")));
        if (!jSONObject.has("steps")) {
            return routeBusWalkItem;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("steps");
        if (optJSONArray == null) {
            return routeBusWalkItem;
        }
        List arrayList = new ArrayList();
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(C0391n.m1645s(optJSONObject));
            }
        }
        routeBusWalkItem.setSteps(arrayList);
        return routeBusWalkItem;
    }

    public static List<RouteBusLineItem> m1643q(JSONObject jSONObject) throws JSONException {
        List<RouteBusLineItem> arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("buslines");
        if (optJSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(C0391n.m1646t(optJSONObject));
            }
        }
        return arrayList;
    }

    public static Doorway m1644r(JSONObject jSONObject) throws JSONException {
        Doorway doorway = new Doorway();
        doorway.setName(C0391n.m1600a(jSONObject, "name"));
        doorway.setLatLonPoint(C0391n.m1609b(jSONObject, "location"));
        return doorway;
    }

    public static WalkStep m1645s(JSONObject jSONObject) throws JSONException {
        WalkStep walkStep = new WalkStep();
        walkStep.setInstruction(C0391n.m1600a(jSONObject, "instruction"));
        walkStep.setOrientation(C0391n.m1600a(jSONObject, "orientation"));
        walkStep.setRoad(C0391n.m1600a(jSONObject, "road"));
        walkStep.setDistance(C0391n.m1633k(C0391n.m1600a(jSONObject, "distance")));
        walkStep.setDuration(C0391n.m1633k(C0391n.m1600a(jSONObject, "duration")));
        walkStep.setPolyline(C0391n.m1616c(jSONObject, "polyline"));
        walkStep.setAction(C0391n.m1600a(jSONObject, "action"));
        walkStep.setAssistantAction(C0391n.m1600a(jSONObject, "assistant_action"));
        return walkStep;
    }

    public static RouteBusLineItem m1646t(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        RouteBusLineItem routeBusLineItem = new RouteBusLineItem();
        routeBusLineItem.setDepartureBusStation(C0391n.m1648v(jSONObject.optJSONObject("departure_stop")));
        routeBusLineItem.setArrivalBusStation(C0391n.m1648v(jSONObject.optJSONObject("arrival_stop")));
        routeBusLineItem.setBusLineName(C0391n.m1600a(jSONObject, "name"));
        routeBusLineItem.setBusLineId(C0391n.m1600a(jSONObject, "id"));
        routeBusLineItem.setBusLineType(C0391n.m1600a(jSONObject, "type"));
        routeBusLineItem.setDistance(C0391n.m1633k(C0391n.m1600a(jSONObject, "distance")));
        routeBusLineItem.setDuration(C0391n.m1633k(C0391n.m1600a(jSONObject, "duration")));
        routeBusLineItem.setPolyline(C0391n.m1616c(jSONObject, "polyline"));
        routeBusLineItem.setFirstBusTime(C0390i.m1597c(C0391n.m1600a(jSONObject, "start_time")));
        routeBusLineItem.setLastBusTime(C0390i.m1597c(C0391n.m1600a(jSONObject, "end_time")));
        routeBusLineItem.setPassStationNum(C0391n.m1631j(C0391n.m1600a(jSONObject, "via_num")));
        routeBusLineItem.setPassStations(C0391n.m1647u(jSONObject));
        return routeBusLineItem;
    }

    public static List<BusStationItem> m1647u(JSONObject jSONObject) throws JSONException {
        List<BusStationItem> arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("via_stops");
        if (optJSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(C0391n.m1648v(optJSONObject));
            }
        }
        return arrayList;
    }

    public static BusStationItem m1648v(JSONObject jSONObject) throws JSONException {
        BusStationItem busStationItem = new BusStationItem();
        busStationItem.setBusStationName(C0391n.m1600a(jSONObject, "name"));
        busStationItem.setBusStationId(C0391n.m1600a(jSONObject, "id"));
        busStationItem.setLatLonPoint(C0391n.m1609b(jSONObject, "location"));
        return busStationItem;
    }

    public static DriveRouteResult m1610b(String str) throws AMapException {
        DriveRouteResult driveRouteResult = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("route")) {
                driveRouteResult = new DriveRouteResult();
                jSONObject = jSONObject.optJSONObject("route");
                if (jSONObject != null) {
                    driveRouteResult.setStartPos(C0391n.m1609b(jSONObject, "origin"));
                    driveRouteResult.setTargetPos(C0391n.m1609b(jSONObject, "destination"));
                    driveRouteResult.setTaxiCost(C0391n.m1633k(C0391n.m1600a(jSONObject, "taxi_cost")));
                    if (jSONObject.has("paths")) {
                        JSONArray optJSONArray = jSONObject.optJSONArray("paths");
                        if (optJSONArray != null) {
                            List arrayList = new ArrayList();
                            for (int i = 0; i < optJSONArray.length(); i++) {
                                DrivePath drivePath = new DrivePath();
                                jSONObject = optJSONArray.optJSONObject(i);
                                if (jSONObject != null) {
                                    drivePath.setDistance(C0391n.m1633k(C0391n.m1600a(jSONObject, "distance")));
                                    drivePath.setDuration(C0391n.m1637m(C0391n.m1600a(jSONObject, "duration")));
                                    drivePath.setStrategy(C0391n.m1600a(jSONObject, "strategy"));
                                    drivePath.setTolls(C0391n.m1633k(C0391n.m1600a(jSONObject, "tolls")));
                                    drivePath.setTollDistance(C0391n.m1633k(C0391n.m1600a(jSONObject, "toll_distance")));
                                    drivePath.setTotalTrafficlights(C0391n.m1631j(C0391n.m1600a(jSONObject, "traffic_lights")));
                                    JSONArray optJSONArray2 = jSONObject.optJSONArray("roads");
                                    if (optJSONArray2 != null) {
                                        List arrayList2 = new ArrayList();
                                        for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                                            JSONArray optJSONArray3 = optJSONArray2.optJSONObject(i2).optJSONArray("steps");
                                            if (optJSONArray3 != null) {
                                                for (int i3 = 0; i3 < optJSONArray3.length(); i3++) {
                                                    DriveStep driveStep = new DriveStep();
                                                    JSONObject optJSONObject = optJSONArray3.optJSONObject(i3);
                                                    if (optJSONObject != null) {
                                                        driveStep.setInstruction(C0391n.m1600a(optJSONObject, "instruction"));
                                                        driveStep.setOrientation(C0391n.m1600a(optJSONObject, "orientation"));
                                                        driveStep.setRoad(C0391n.m1600a(optJSONObject, "road"));
                                                        driveStep.setDistance(C0391n.m1633k(C0391n.m1600a(optJSONObject, "distance")));
                                                        driveStep.setTolls(C0391n.m1633k(C0391n.m1600a(optJSONObject, "tolls")));
                                                        driveStep.setTollDistance(C0391n.m1633k(C0391n.m1600a(optJSONObject, "toll_distance")));
                                                        driveStep.setTollRoad(C0391n.m1600a(optJSONObject, "toll_road"));
                                                        driveStep.setDuration(C0391n.m1633k(C0391n.m1600a(optJSONObject, "duration")));
                                                        driveStep.setPolyline(C0391n.m1616c(optJSONObject, "polyline"));
                                                        driveStep.setAction(C0391n.m1600a(optJSONObject, "action"));
                                                        driveStep.setAssistantAction(C0391n.m1600a(optJSONObject, "assistant_action"));
                                                        C0391n.m1604a(driveStep, optJSONObject);
                                                        C0391n.m1612b(driveStep, optJSONObject);
                                                        arrayList2.add(driveStep);
                                                    }
                                                }
                                            }
                                        }
                                        drivePath.setSteps(arrayList2);
                                        arrayList.add(drivePath);
                                    } else {
                                        continue;
                                    }
                                }
                            }
                            driveRouteResult.setPaths(arrayList);
                        }
                    }
                }
            }
            return driveRouteResult;
        } catch (Throwable e) {
            C0390i.m1594a(e, "JSONHelper", "parseDriveRoute");
            throw new AMapException("协议解析错误 - ProtocolException");
        } catch (Throwable e2) {
            C0390i.m1594a(e2, "JSONHelper", "parseDriveRouteThrowable");
            AMapException aMapException = new AMapException("协议解析错误 - ProtocolException");
        }
    }

    private static void m1612b(DriveStep driveStep, JSONObject jSONObject) throws AMapException {
        try {
            List arrayList = new ArrayList();
            JSONArray optJSONArray = jSONObject.optJSONArray("tmcs");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    TMC tmc = new TMC();
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        tmc.setDistance(C0391n.m1631j(C0391n.m1600a(optJSONObject, "distance")));
                        tmc.setStatus(C0391n.m1600a(optJSONObject, "status"));
                        arrayList.add(tmc);
                    }
                }
                driveStep.setTMCs(arrayList);
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "JSONHelper", "parseTMCs");
            throw new AMapException("协议解析错误 - ProtocolException");
        }
    }

    public static void m1604a(DriveStep driveStep, JSONObject jSONObject) throws AMapException {
        try {
            List arrayList = new ArrayList();
            JSONArray optJSONArray = jSONObject.optJSONArray("cities");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    RouteSearchCity routeSearchCity = new RouteSearchCity();
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        routeSearchCity.setSearchCityName(C0391n.m1600a(optJSONObject, "name"));
                        routeSearchCity.setSearchCitycode(C0391n.m1600a(optJSONObject, "citycode"));
                        routeSearchCity.setSearchCityhAdCode(C0391n.m1600a(optJSONObject, "adcode"));
                        C0391n.m1605a(routeSearchCity, optJSONObject);
                        arrayList.add(routeSearchCity);
                    }
                }
                driveStep.setRouteSearchCityList(arrayList);
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "JSONHelper", "parseCrossCity");
            throw new AMapException("协议解析错误 - ProtocolException");
        }
    }

    public static void m1605a(RouteSearchCity routeSearchCity, JSONObject jSONObject) throws AMapException {
        if (jSONObject.has("districts")) {
            try {
                List arrayList = new ArrayList();
                JSONArray optJSONArray = jSONObject.optJSONArray("districts");
                if (optJSONArray == null) {
                    routeSearchCity.setDistricts(arrayList);
                    return;
                }
                for (int i = 0; i < optJSONArray.length(); i++) {
                    District district = new District();
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        district.setDistrictName(C0391n.m1600a(optJSONObject, "name"));
                        district.setDistrictAdcode(C0391n.m1600a(optJSONObject, "adcode"));
                        arrayList.add(district);
                    }
                }
                routeSearchCity.setDistricts(arrayList);
            } catch (Throwable e) {
                C0390i.m1594a(e, "JSONHelper", "parseCrossDistricts");
                throw new AMapException("协议解析错误 - ProtocolException");
            }
        }
    }

    public static WalkRouteResult m1614c(String str) throws AMapException {
        WalkRouteResult walkRouteResult = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("route")) {
                walkRouteResult = new WalkRouteResult();
                jSONObject = jSONObject.optJSONObject("route");
                walkRouteResult.setStartPos(C0391n.m1609b(jSONObject, "origin"));
                walkRouteResult.setTargetPos(C0391n.m1609b(jSONObject, "destination"));
                if (jSONObject.has("paths")) {
                    List arrayList = new ArrayList();
                    JSONArray optJSONArray = jSONObject.optJSONArray("paths");
                    if (optJSONArray == null) {
                        walkRouteResult.setPaths(arrayList);
                    } else {
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            WalkPath walkPath = new WalkPath();
                            jSONObject = optJSONArray.optJSONObject(i);
                            if (jSONObject != null) {
                                walkPath.setDistance(C0391n.m1633k(C0391n.m1600a(jSONObject, "distance")));
                                walkPath.setDuration(C0391n.m1637m(C0391n.m1600a(jSONObject, "duration")));
                                if (jSONObject.has("steps")) {
                                    JSONArray optJSONArray2 = jSONObject.optJSONArray("steps");
                                    List arrayList2 = new ArrayList();
                                    if (optJSONArray2 != null) {
                                        for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                                            WalkStep walkStep = new WalkStep();
                                            JSONObject optJSONObject = optJSONArray2.optJSONObject(i2);
                                            if (optJSONObject != null) {
                                                walkStep.setInstruction(C0391n.m1600a(optJSONObject, "instruction"));
                                                walkStep.setOrientation(C0391n.m1600a(optJSONObject, "orientation"));
                                                walkStep.setRoad(C0391n.m1600a(optJSONObject, "road"));
                                                walkStep.setDistance(C0391n.m1633k(C0391n.m1600a(optJSONObject, "distance")));
                                                walkStep.setDuration(C0391n.m1633k(C0391n.m1600a(optJSONObject, "duration")));
                                                walkStep.setPolyline(C0391n.m1616c(optJSONObject, "polyline"));
                                                walkStep.setAction(C0391n.m1600a(optJSONObject, "action"));
                                                walkStep.setAssistantAction(C0391n.m1600a(optJSONObject, "assistant_action"));
                                                arrayList2.add(walkStep);
                                            }
                                        }
                                        walkPath.setSteps(arrayList2);
                                    }
                                }
                                arrayList.add(walkPath);
                            }
                        }
                        walkRouteResult.setPaths(arrayList);
                    }
                }
            }
            return walkRouteResult;
        } catch (Throwable e) {
            C0390i.m1594a(e, "JSONHelper", "parseWalkRoute");
            throw new AMapException("协议解析错误 - ProtocolException");
        }
    }

    public static LocalWeatherLive m1620d(String str) throws AMapException {
        LocalWeatherLive localWeatherLive = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("lives")) {
                localWeatherLive = new LocalWeatherLive();
                JSONArray optJSONArray = jSONObject.optJSONArray("lives");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    jSONObject = optJSONArray.optJSONObject(0);
                    if (jSONObject != null) {
                        localWeatherLive.setAdCode(C0391n.m1600a(jSONObject, "adcode"));
                        localWeatherLive.setProvince(C0391n.m1600a(jSONObject, DistrictSearchQuery.KEYWORDS_PROVINCE));
                        localWeatherLive.setCity(C0391n.m1600a(jSONObject, DistrictSearchQuery.KEYWORDS_CITY));
                        localWeatherLive.setWeather(C0391n.m1600a(jSONObject, "weather"));
                        localWeatherLive.setTemperature(C0391n.m1600a(jSONObject, "temperature"));
                        localWeatherLive.setWindDirection(C0391n.m1600a(jSONObject, "winddirection"));
                        localWeatherLive.setWindPower(C0391n.m1600a(jSONObject, "windpower"));
                        localWeatherLive.setHumidity(C0391n.m1600a(jSONObject, "humidity"));
                        localWeatherLive.setReportTime(C0391n.m1600a(jSONObject, "reporttime"));
                    }
                }
            }
            return localWeatherLive;
        } catch (Throwable e) {
            C0390i.m1594a(e, "JSONHelper", "WeatherForecastResult");
            throw new AMapException("协议解析错误 - ProtocolException");
        }
    }

    public static LocalWeatherForecast m1621e(String str) throws AMapException {
        LocalWeatherForecast localWeatherForecast = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("forecasts")) {
                localWeatherForecast = new LocalWeatherForecast();
                JSONArray jSONArray = jSONObject.getJSONArray("forecasts");
                if (jSONArray != null && jSONArray.length() > 0) {
                    jSONObject = jSONArray.optJSONObject(0);
                    if (jSONObject != null) {
                        localWeatherForecast.setCity(C0391n.m1600a(jSONObject, DistrictSearchQuery.KEYWORDS_CITY));
                        localWeatherForecast.setAdCode(C0391n.m1600a(jSONObject, "adcode"));
                        localWeatherForecast.setProvince(C0391n.m1600a(jSONObject, DistrictSearchQuery.KEYWORDS_PROVINCE));
                        localWeatherForecast.setReportTime(C0391n.m1600a(jSONObject, "reporttime"));
                        if (jSONObject.has("casts")) {
                            List arrayList = new ArrayList();
                            jSONArray = jSONObject.optJSONArray("casts");
                            if (jSONArray == null || jSONArray.length() <= 0) {
                                localWeatherForecast.setWeatherForecast(arrayList);
                            } else {
                                for (int i = 0; i < jSONArray.length(); i++) {
                                    LocalDayWeatherForecast localDayWeatherForecast = new LocalDayWeatherForecast();
                                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                                    if (optJSONObject != null) {
                                        localDayWeatherForecast.setDate(C0391n.m1600a(optJSONObject, "date"));
                                        localDayWeatherForecast.setWeek(C0391n.m1600a(optJSONObject, "week"));
                                        localDayWeatherForecast.setDayWeather(C0391n.m1600a(optJSONObject, "dayweather"));
                                        localDayWeatherForecast.setNightWeather(C0391n.m1600a(optJSONObject, "nightweather"));
                                        localDayWeatherForecast.setDayTemp(C0391n.m1600a(optJSONObject, "daytemp"));
                                        localDayWeatherForecast.setNightTemp(C0391n.m1600a(optJSONObject, "nighttemp"));
                                        localDayWeatherForecast.setDayWindDirection(C0391n.m1600a(optJSONObject, "daywind"));
                                        localDayWeatherForecast.setNightWindDirection(C0391n.m1600a(optJSONObject, "nightwind"));
                                        localDayWeatherForecast.setDayWindPower(C0391n.m1600a(optJSONObject, "daypower"));
                                        localDayWeatherForecast.setNightWindPower(C0391n.m1600a(optJSONObject, "nightpower"));
                                        arrayList.add(localDayWeatherForecast);
                                    }
                                }
                                localWeatherForecast.setWeatherForecast(arrayList);
                            }
                        }
                    }
                }
            }
            return localWeatherForecast;
        } catch (Throwable e) {
            C0390i.m1594a(e, "JSONHelper", "WeatherForecastResult");
            throw new AMapException("协议解析错误 - ProtocolException");
        }
    }

    public static String m1600a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null) {
            return "";
        }
        String str2 = "";
        if (!jSONObject.has(str) || jSONObject.getString(str).equals("[]")) {
            return str2;
        }
        return jSONObject.optString(str).trim();
    }

    public static LatLonPoint m1609b(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject != null && jSONObject.has(str)) {
            return C0391n.m1626g(jSONObject.optString(str));
        }
        return null;
    }

    public static ArrayList<LatLonPoint> m1616c(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject.has(str)) {
            return C0391n.m1624f(jSONObject.getString(str));
        }
        return null;
    }

    public static ArrayList<LatLonPoint> m1624f(String str) {
        ArrayList<LatLonPoint> arrayList = new ArrayList();
        String[] split = str.split(";");
        for (String g : split) {
            arrayList.add(C0391n.m1626g(g));
        }
        return arrayList;
    }

    public static LatLonPoint m1626g(String str) {
        if (str == null || str.equals("") || str.equals("[]")) {
            return null;
        }
        String[] split = str.split(SeparatorConstants.SEPARATOR_ADS_ID);
        if (split.length != 2) {
            return null;
        }
        return new LatLonPoint(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
    }

    public static boolean m1628h(String str) {
        if (str == null || str.equals("") || str.equals("0")) {
            return true;
        }
        return false;
    }

    public static boolean m1630i(String str) {
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    public static int m1631j(String str) {
        int i = 0;
        if (!(str == null || str.equals("") || str.equals("[]"))) {
            try {
                i = Integer.parseInt(str);
            } catch (Throwable e) {
                C0390i.m1594a(e, "JSONHelper", "str2int");
            }
        }
        return i;
    }

    public static float m1633k(String str) {
        float f = 0.0f;
        if (!(str == null || str.equals("") || str.equals("[]"))) {
            try {
                f = Float.parseFloat(str);
            } catch (Throwable e) {
                C0390i.m1594a(e, "JSONHelper", "str2float");
            }
        }
        return f;
    }

    public static double m1635l(String str) {
        double d = 0.0d;
        if (!(str == null || str.equals("") || str.equals("[]"))) {
            try {
                d = Double.parseDouble(str);
            } catch (Throwable e) {
                C0390i.m1594a(e, "JSONHelper", "str2float");
            }
        }
        return d;
    }

    public static long m1637m(String str) {
        long j = 0;
        if (!(str == null || str.equals("") || str.equals("[]"))) {
            try {
                j = Long.parseLong(str);
            } catch (Throwable e) {
                C0390i.m1594a(e, "JSONHelper", "str2long");
            }
        }
        return j;
    }

    public static boolean m1640n(String str) {
        if (str == null || str.equals("") || str.equals("[]") || str.equals("0") || !str.equals("1")) {
            return false;
        }
        return true;
    }

    private static IndoorData m1619d(JSONObject jSONObject, String str) throws JSONException {
        String str2 = "";
        int i = 0;
        String str3 = "";
        if (jSONObject.has(str)) {
            JSONObject optJSONObject = jSONObject.optJSONObject(str);
            if (optJSONObject != null && optJSONObject.has("cpid") && optJSONObject.has("floor")) {
                str2 = C0391n.m1600a(optJSONObject, "cpid");
                i = C0391n.m1631j(C0391n.m1600a(optJSONObject, "floor"));
                str3 = C0391n.m1600a(optJSONObject, "truefloor");
            }
        }
        return new IndoorData(str2, i, str3);
    }

    public static void m1617c(JSONArray jSONArray, RegeocodeAddress regeocodeAddress) throws JSONException {
        List arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            AoiItem aoiItem = new AoiItem();
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                aoiItem.setId(C0391n.m1600a(optJSONObject, "id"));
                aoiItem.setName(C0391n.m1600a(optJSONObject, "name"));
                aoiItem.setAdcode(C0391n.m1600a(optJSONObject, "adcode"));
                aoiItem.setLocation(C0391n.m1609b(optJSONObject, "location"));
                aoiItem.setArea(Float.valueOf(C0391n.m1633k(C0391n.m1600a(optJSONObject, "area"))));
                arrayList.add(aoiItem);
            }
        }
        regeocodeAddress.setAois(arrayList);
    }
}
