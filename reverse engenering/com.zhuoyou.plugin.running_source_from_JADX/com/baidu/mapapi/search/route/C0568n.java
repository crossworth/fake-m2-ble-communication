package com.baidu.mapapi.search.route;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.BusInfo;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.CoachInfo;
import com.baidu.mapapi.search.core.PlaneInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.PriceInfo;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.core.TaxiInfo;
import com.baidu.mapapi.search.core.TrainInfo;
import com.baidu.mapapi.search.core.TransitResultNode;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.BikingRouteLine.BikingStep;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.mapapi.search.route.IndoorRouteLine.IndoorRouteStep;
import com.baidu.mapapi.search.route.IndoorRouteLine.IndoorRouteStep.IndoorStepNode;
import com.baidu.mapapi.search.route.MassTransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.MassTransitRouteLine.TransitStep.StepVehicleInfoType;
import com.baidu.mapapi.search.route.MassTransitRouteLine.TransitStep.TrafficCondition;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep.TransitRouteStepType;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import com.droi.btlib.connection.MapConstants;
import com.umeng.socialize.common.SocializeConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0568n {
    private static TransitStep m1684a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        TransitStep transitStep = new TransitStep();
        transitStep.setDistance((int) jSONObject.optDouble("distance"));
        transitStep.setDuration((int) jSONObject.optDouble("duration"));
        transitStep.m1601a(jSONObject.optString("instructions"));
        transitStep.m1603b(jSONObject.optString("path"));
        transitStep.setTrafficConditions(C0568n.m1691b(jSONObject.optJSONArray("traffic_condition")));
        JSONObject optJSONObject = jSONObject.optJSONObject("start_location");
        transitStep.m1599a(new LatLng(optJSONObject.optDouble("lat"), optJSONObject.optDouble("lng")));
        optJSONObject = jSONObject.optJSONObject("end_location");
        transitStep.m1602b(new LatLng(optJSONObject.optDouble("lat"), optJSONObject.optDouble("lng")));
        optJSONObject = jSONObject.optJSONObject("vehicle_info");
        int optInt = optJSONObject.optInt("type");
        optJSONObject = optJSONObject.optJSONObject("detail");
        switch (optInt) {
            case 1:
                transitStep.m1600a(StepVehicleInfoType.ESTEP_TRAIN);
                if (optJSONObject == null) {
                    return transitStep;
                }
                TrainInfo trainInfo = new TrainInfo();
                trainInfo.setName(optJSONObject.optString("name"));
                trainInfo.m1435a(optJSONObject.optDouble("price"));
                trainInfo.m1436a(optJSONObject.optString("booking"));
                trainInfo.setDepartureStation(optJSONObject.optString("departure_station"));
                trainInfo.setArriveStation(optJSONObject.optString("arrive_station"));
                trainInfo.setDepartureTime(optJSONObject.optString("departure_time"));
                trainInfo.setArriveTime(optJSONObject.optString("arrive_time"));
                transitStep.setTrainInfo(trainInfo);
                return transitStep;
            case 2:
                transitStep.m1600a(StepVehicleInfoType.ESTEP_PLANE);
                if (optJSONObject == null) {
                    return transitStep;
                }
                PlaneInfo planeInfo = new PlaneInfo();
                planeInfo.setName(optJSONObject.optString("name"));
                planeInfo.setPrice(optJSONObject.optDouble("price"));
                planeInfo.setDiscount(optJSONObject.optDouble("discount"));
                planeInfo.setAirlines(optJSONObject.optString("airlines"));
                planeInfo.setBooking(optJSONObject.optString("booking"));
                planeInfo.setDepartureStation(optJSONObject.optString("departure_station"));
                planeInfo.setArriveStation(optJSONObject.optString("arrive_station"));
                planeInfo.setDepartureTime(optJSONObject.optString("departure_time"));
                planeInfo.setArriveTime(optJSONObject.optString("arrive_time"));
                transitStep.setPlaneInfo(planeInfo);
                return transitStep;
            case 3:
                transitStep.m1600a(StepVehicleInfoType.ESTEP_BUS);
                if (optJSONObject == null) {
                    return transitStep;
                }
                BusInfo busInfo = new BusInfo();
                busInfo.setName(optJSONObject.optString("name"));
                busInfo.setType(optJSONObject.optInt("type"));
                busInfo.setStopNum(optJSONObject.optInt("stop_num"));
                busInfo.setDepartureStation(optJSONObject.optString("on_station"));
                busInfo.setArriveStation(optJSONObject.optString("off_station"));
                busInfo.setDepartureTime(optJSONObject.optString("first_time"));
                busInfo.setArriveTime(optJSONObject.optString("last_time"));
                transitStep.setBusInfo(busInfo);
                return transitStep;
            case 4:
                transitStep.m1600a(StepVehicleInfoType.ESTEP_DRIVING);
                return transitStep;
            case 5:
                transitStep.m1600a(StepVehicleInfoType.ESTEP_WALK);
                return transitStep;
            case 6:
                transitStep.m1600a(StepVehicleInfoType.ESTEP_COACH);
                if (optJSONObject == null) {
                    return transitStep;
                }
                CoachInfo coachInfo = new CoachInfo();
                coachInfo.setName(optJSONObject.optString("name"));
                coachInfo.setPrice(optJSONObject.optDouble("price"));
                coachInfo.setBooking(optJSONObject.optString("booking"));
                coachInfo.setProviderName(optJSONObject.optString("provider_name"));
                coachInfo.setProviderUrl(optJSONObject.optString("provider_url"));
                coachInfo.setDepartureStation(optJSONObject.optString("departure_station"));
                coachInfo.setArriveStation(optJSONObject.optString("arrive_station"));
                coachInfo.setDepartureTime(optJSONObject.optString("departure_time"));
                coachInfo.setArriveTime(optJSONObject.optString("arrive_time"));
                transitStep.setCoachInfo(coachInfo);
                return transitStep;
            default:
                return transitStep;
        }
    }

    public static TransitRouteResult m1685a(String str) {
        TransitRouteResult transitRouteResult = new TransitRouteResult();
        if (str == null || str.length() <= 0) {
            transitRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return transitRouteResult;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optJSONObject("taxi") != null) {
                transitRouteResult.m1645a(C0568n.m1707h(jSONObject.optString("taxi")));
            }
            RouteNode c = C0568n.m1694c(jSONObject, "start_point");
            RouteNode c2 = C0568n.m1694c(jSONObject, "end_point");
            JSONArray optJSONArray = jSONObject.optJSONArray("routes");
            if (optJSONArray == null || optJSONArray.length() <= 0) {
                transitRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
                return transitRouteResult;
            }
            List arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                jSONObject = ((JSONObject) optJSONArray.opt(i)).optJSONObject("legs");
                if (jSONObject != null) {
                    TransitRouteLine transitRouteLine = new TransitRouteLine();
                    transitRouteLine.setDistance(jSONObject.optInt("distance"));
                    transitRouteLine.setDuration(jSONObject.optInt(LogColumns.TIME));
                    transitRouteLine.setStarting(c);
                    transitRouteLine.setTerminal(c2);
                    JSONArray optJSONArray2 = jSONObject.optJSONArray("steps");
                    if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                        List arrayList2 = new ArrayList();
                        for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                            JSONArray optJSONArray3 = optJSONArray2.optJSONObject(i2).optJSONArray("busline");
                            if (optJSONArray3 != null && optJSONArray3.length() > 0) {
                                JSONObject optJSONObject = optJSONArray3.optJSONObject(0);
                                TransitRouteLine.TransitStep transitStep = new TransitRouteLine.TransitStep();
                                transitStep.m1639a(RouteNode.location(CoordUtil.decodeLocation(optJSONObject.optString("start_location"))));
                                transitStep.m1643b(RouteNode.location(CoordUtil.decodeLocation(optJSONObject.optString("end_location"))));
                                if (optJSONObject.optInt("type") == 5) {
                                    transitStep.m1641a(TransitRouteStepType.WAKLING);
                                } else {
                                    transitStep.m1641a(TransitRouteStepType.WAKLING);
                                }
                                transitStep.m1642a(C0568n.m1710k(optJSONObject.optString("instructions")));
                                transitStep.setDistance(optJSONObject.optInt("distance"));
                                transitStep.setDuration(optJSONObject.optInt("duration"));
                                transitStep.m1644b(optJSONObject.optString("path_geo"));
                                if (optJSONObject.has("vehicle")) {
                                    transitStep.m1640a(C0568n.m1708i(optJSONObject.optString("vehicle")));
                                    optJSONObject = optJSONObject.optJSONObject("vehicle");
                                    transitStep.getEntrance().setUid(optJSONObject.optString("start_uid"));
                                    transitStep.getEntrance().setTitle(optJSONObject.optString("start_name"));
                                    transitStep.getExit().setUid(optJSONObject.optString("end_uid"));
                                    transitStep.getExit().setTitle(optJSONObject.optString("end_name"));
                                    Integer valueOf = Integer.valueOf(optJSONObject.optInt("type"));
                                    if (valueOf == null) {
                                        transitStep.m1641a(TransitRouteStepType.BUSLINE);
                                    } else if (valueOf.intValue() == 1) {
                                        transitStep.m1641a(TransitRouteStepType.SUBWAY);
                                    } else {
                                        transitStep.m1641a(TransitRouteStepType.BUSLINE);
                                    }
                                }
                                arrayList2.add(transitStep);
                            }
                        }
                        transitRouteLine.setSteps(arrayList2);
                        arrayList.add(transitRouteLine);
                    }
                }
            }
            transitRouteResult.m1647a(arrayList);
            return transitRouteResult;
        } catch (JSONException e) {
            transitRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            e.printStackTrace();
            return transitRouteResult;
        }
    }

    private static List<List<TransitStep>> m1686a(JSONArray jSONArray) {
        List<List<TransitStep>> arrayList = new ArrayList();
        if (jSONArray == null || jSONArray.length() < 0) {
            return null;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                JSONArray optJSONArray = optJSONObject.optJSONArray("scheme");
                List arrayList2 = new ArrayList();
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                        JSONObject optJSONObject2 = optJSONArray.optJSONObject(i2);
                        if (optJSONObject2 != null) {
                            arrayList2.add(C0568n.m1684a(optJSONObject2));
                        }
                    }
                }
                arrayList.add(arrayList2);
            }
        }
        return arrayList;
    }

    public static List<CityInfo> m1687a(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || str.equals("")) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null || optJSONArray.length() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(i);
            if (jSONObject2 != null) {
                CityInfo cityInfo = new CityInfo();
                cityInfo.num = jSONObject2.optInt("num");
                cityInfo.city = jSONObject2.optString("name");
                arrayList.add(cityInfo);
            }
        }
        arrayList.trimToSize();
        return arrayList;
    }

    private static List<PoiInfo> m1688a(JSONObject jSONObject, String str, String str2) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray != null) {
            List<PoiInfo> arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(i);
                if (jSONObject2 != null) {
                    PoiInfo poiInfo = new PoiInfo();
                    PlanNode.withCityNameAndPlaceName(str2, jSONObject2.optString("name"));
                    poiInfo.address = jSONObject2.optString("addr");
                    poiInfo.uid = jSONObject2.optString("uid");
                    poiInfo.name = jSONObject2.optString("name");
                    poiInfo.location = CoordUtil.decodeLocation(jSONObject2.optString("geo"));
                    poiInfo.city = str2;
                    arrayList.add(poiInfo);
                }
            }
            if (arrayList.size() > 0) {
                return arrayList;
            }
        }
        return null;
    }

    private static TransitResultNode m1689b(JSONObject jSONObject) {
        LatLng latLng = null;
        if (jSONObject == null) {
            return null;
        }
        String optString = jSONObject.optString("wd");
        String optString2 = jSONObject.optString("city_name");
        int optInt = jSONObject.optInt("city_code");
        JSONObject optJSONObject = jSONObject.optJSONObject(SocializeConstants.KEY_LOCATION);
        if (optJSONObject != null) {
            latLng = new LatLng(optJSONObject.optDouble("lat"), optJSONObject.optDouble("lng"));
        }
        return new TransitResultNode(optInt, optString2, latLng, optString);
    }

    public static MassTransitRouteResult m1690b(String str) {
        MassTransitRouteResult massTransitRouteResult = new MassTransitRouteResult();
        if (str == null || str.length() <= 0) {
            massTransitRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return massTransitRouteResult;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("type");
            jSONObject = jSONObject.optJSONObject("result");
            if (jSONObject != null) {
                if (optInt == 1) {
                    massTransitRouteResult.m1608a(C0568n.m1689b(jSONObject.optJSONObject("origin_info")));
                    massTransitRouteResult.m1611b(C0568n.m1689b(jSONObject.optJSONObject("destination_info")));
                    massTransitRouteResult.m1609a(C0568n.m1696c(jSONObject));
                    massTransitRouteResult.error = ERRORNO.AMBIGUOUS_ROURE_ADDR;
                } else if (optInt == 2) {
                    TransitResultNode b = C0568n.m1689b(jSONObject.optJSONObject("origin"));
                    massTransitRouteResult.m1608a(b);
                    TransitResultNode b2 = C0568n.m1689b(jSONObject.optJSONObject("destination"));
                    massTransitRouteResult.m1611b(b2);
                    massTransitRouteResult.m1607a(jSONObject.optInt("total"));
                    massTransitRouteResult.setTaxiInfo(C0568n.m1707h(jSONObject.optString("taxi")));
                    JSONArray optJSONArray = jSONObject.optJSONArray("routes");
                    if (optJSONArray == null || optJSONArray.length() <= 0) {
                        massTransitRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
                        return massTransitRouteResult;
                    }
                    List arrayList = new ArrayList();
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        MassTransitRouteLine massTransitRouteLine = new MassTransitRouteLine();
                        massTransitRouteLine.setDistance(optJSONObject.optInt("distance"));
                        massTransitRouteLine.setDuration(optJSONObject.optInt("duration"));
                        massTransitRouteLine.m1605a(optJSONObject.optString("arrive_time"));
                        massTransitRouteLine.m1604a(optJSONObject.optDouble("price"));
                        massTransitRouteLine.m1606a(C0568n.m1697c(optJSONObject.optJSONArray("price_detail")));
                        RouteNode routeNode = new RouteNode();
                        routeNode.setLocation(b.getLocation());
                        massTransitRouteLine.setStarting(routeNode);
                        routeNode = new RouteNode();
                        routeNode.setLocation(b2.getLocation());
                        massTransitRouteLine.setTerminal(routeNode);
                        JSONArray optJSONArray2 = optJSONObject.optJSONArray("steps");
                        if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                            massTransitRouteLine.setNewSteps(C0568n.m1686a(optJSONArray2));
                            arrayList.add(massTransitRouteLine);
                        }
                    }
                    massTransitRouteResult.m1610a(arrayList);
                    massTransitRouteResult.error = ERRORNO.NO_ERROR;
                }
                return massTransitRouteResult;
            }
            massTransitRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return massTransitRouteResult;
        } catch (JSONException e) {
            massTransitRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            e.printStackTrace();
            return massTransitRouteResult;
        }
    }

    private static List<TrafficCondition> m1691b(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() < 0) {
            return null;
        }
        List<TrafficCondition> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                TrafficCondition trafficCondition = new TrafficCondition();
                trafficCondition.setTrafficStatus(optJSONObject.optInt("status"));
                trafficCondition.setTrafficGeoCnt(optJSONObject.optInt("geo_cnt"));
                arrayList.add(trafficCondition);
            }
        }
        return arrayList;
    }

    public static List<CityInfo> m1692b(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || str.equals("")) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null || optJSONArray.length() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(i);
            if (jSONObject2 != null) {
                CityInfo cityInfo = new CityInfo();
                cityInfo.num = jSONObject2.optInt("number");
                cityInfo.city = jSONObject2.optString("name");
                arrayList.add(cityInfo);
            }
        }
        arrayList.trimToSize();
        return arrayList;
    }

    private static List<PoiInfo> m1693b(JSONObject jSONObject, String str, String str2) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray != null) {
            List<PoiInfo> arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(i);
                if (jSONObject2 != null) {
                    PoiInfo poiInfo = new PoiInfo();
                    if (jSONObject2.has(MapConstants.ADDRESS)) {
                        poiInfo.address = jSONObject2.optString(MapConstants.ADDRESS);
                    }
                    poiInfo.uid = jSONObject2.optString("uid");
                    poiInfo.name = jSONObject2.optString("name");
                    poiInfo.location = new LatLng(jSONObject2.optDouble("lat"), jSONObject2.optDouble("lng"));
                    poiInfo.city = str2;
                    arrayList.add(poiInfo);
                }
            }
            if (arrayList.size() > 0) {
                return arrayList;
            }
        }
        return null;
    }

    private static RouteNode m1694c(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        RouteNode routeNode = new RouteNode();
        routeNode.setTitle(optJSONObject.optString("name"));
        routeNode.setUid(optJSONObject.optString("uid"));
        routeNode.setLocation(CoordUtil.decodeLocation(optJSONObject.optString("geo")));
        return routeNode;
    }

    public static DrivingRouteResult m1695c(String str) {
        DrivingRouteResult drivingRouteResult = new DrivingRouteResult();
        if (str == null || "".equals(str)) {
            drivingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return drivingRouteResult;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            List arrayList = new ArrayList();
            JSONArray optJSONArray = jSONObject.optJSONArray("routes");
            if (optJSONArray == null) {
                return drivingRouteResult;
            }
            for (int i = 0; i < optJSONArray.length(); i++) {
                DrivingRouteLine drivingRouteLine = new DrivingRouteLine();
                drivingRouteLine.setStarting(C0568n.m1702e(jSONObject, "start_point"));
                drivingRouteLine.setTerminal(C0568n.m1702e(jSONObject, "end_point"));
                drivingRouteLine.m1588a(C0568n.m1705f(jSONObject, "waypoints"));
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    optJSONObject = optJSONObject.optJSONObject("legs");
                    if (optJSONObject == null) {
                        return drivingRouteResult;
                    }
                    drivingRouteLine.setDistance(optJSONObject.optInt("distance"));
                    drivingRouteLine.setDuration(optJSONObject.optInt("duration"));
                    drivingRouteLine.setCongestionDistance(optJSONObject.optInt("congestion_length"));
                    drivingRouteLine.setLightNum(optJSONObject.optInt("light_num"));
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("steps");
                    if (optJSONArray2 == null) {
                        return drivingRouteResult;
                    }
                    List arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        DrivingStep drivingStep = new DrivingStep();
                        JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                        if (optJSONObject2 != null) {
                            JSONArray optJSONArray3 = optJSONObject2.optJSONArray("paths");
                            if (optJSONArray3 == null) {
                                return drivingRouteResult;
                            }
                            if (optJSONArray3.length() >= 2) {
                                List arrayList3 = new ArrayList();
                                for (int i3 = 0; i3 < optJSONArray3.length(); i3++) {
                                    JSONObject optJSONObject3 = optJSONArray3.optJSONObject(i3);
                                    GeoPoint geoPoint = new GeoPoint(0.0d, 0.0d);
                                    geoPoint.setLongitudeE6((double) optJSONObject3.optInt("loc_x"));
                                    geoPoint.setLatitudeE6((double) optJSONObject3.optInt("loc_y"));
                                    if (i3 == 0) {
                                        drivingStep.m1580a(RouteNode.location(CoordUtil.mc2ll(geoPoint)));
                                    } else if (i3 == optJSONArray3.length() - 1) {
                                        drivingStep.m1585b(RouteNode.location(CoordUtil.mc2ll(geoPoint)));
                                    }
                                    arrayList3.add(CoordUtil.mc2ll(geoPoint));
                                }
                                drivingStep.m1582a(arrayList3);
                            }
                            JSONArray optJSONArray4 = optJSONObject2.optJSONArray("traffics");
                            if (optJSONArray4 != null && optJSONArray4.length() > 0) {
                                int length = optJSONArray4.length();
                                int[] iArr = new int[length];
                                for (int i4 = 0; i4 < length; i4++) {
                                    iArr[i4] = Integer.parseInt((String) optJSONArray4.opt(i4));
                                }
                                drivingStep.m1583a(iArr);
                            }
                            drivingStep.m1579a(optJSONObject2.optInt("direction") * 30);
                            drivingStep.setDistance(optJSONObject2.optInt("distance"));
                            drivingStep.setDuration(optJSONObject2.optInt("duration"));
                            drivingStep.m1587c(optJSONObject2.optString("description"));
                            drivingStep.m1581a(optJSONObject2.optString("start_desc"));
                            drivingStep.m1586b(optJSONObject2.optString("end_desc"));
                            drivingStep.m1584b(optJSONObject2.optInt("turn"));
                            arrayList2.add(drivingStep);
                        }
                    }
                    drivingRouteLine.setSteps(arrayList2);
                    arrayList.add(drivingRouteLine);
                }
            }
            drivingRouteResult.m1590a(arrayList);
            drivingRouteResult.setTaxiInfos(C0568n.m1706g(jSONObject.optString("taxis")));
            return drivingRouteResult;
        } catch (JSONException e) {
            e.printStackTrace();
            drivingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return drivingRouteResult;
        }
    }

    private static SuggestAddrInfo m1696c(JSONObject jSONObject) {
        SuggestAddrInfo suggestAddrInfo = new SuggestAddrInfo();
        suggestAddrInfo.m1633a(C0568n.m1701d(jSONObject.optJSONArray("origin_list")));
        suggestAddrInfo.m1634b(C0568n.m1701d(jSONObject.optJSONArray("destination_list")));
        return suggestAddrInfo;
    }

    private static List<PriceInfo> m1697c(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        List<PriceInfo> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            PriceInfo priceInfo = new PriceInfo();
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                priceInfo.setTicketType(optJSONObject.optInt("ticket_type"));
                priceInfo.setTicketPrice(optJSONObject.optDouble("ticket_price"));
            }
            arrayList.add(priceInfo);
        }
        return arrayList;
    }

    private static RouteNode m1698d(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        RouteNode routeNode = new RouteNode();
        routeNode.setTitle(optJSONObject.optString("name"));
        routeNode.setUid(optJSONObject.optString("uid"));
        routeNode.setLocation(new LatLng(optJSONObject.optDouble("lat"), optJSONObject.optDouble("lng")));
        return routeNode;
    }

    public static IndoorRouteResult m1699d(String str) {
        IndoorRouteResult indoorRouteResult = new IndoorRouteResult();
        if (str == null || "".equals(str)) {
            indoorRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return indoorRouteResult;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            switch (jSONObject.optInt("error")) {
                case 0:
                    jSONObject = jSONObject.optJSONArray("routes").optJSONObject(0);
                    if (jSONObject == null) {
                        return indoorRouteResult;
                    }
                    List arrayList = new ArrayList();
                    JSONArray optJSONArray = jSONObject.optJSONArray("legs");
                    if (optJSONArray == null) {
                        return indoorRouteResult;
                    }
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        IndoorRouteLine indoorRouteLine = new IndoorRouteLine();
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        if (optJSONObject != null) {
                            GeoPoint geoPoint;
                            RouteNode routeNode;
                            indoorRouteLine.setDistance(optJSONObject.optInt("distance"));
                            indoorRouteLine.setDuration(optJSONObject.optInt("duration"));
                            JSONArray optJSONArray2 = optJSONObject.optJSONArray("sstart_location");
                            if (optJSONArray2 != null) {
                                geoPoint = new GeoPoint(0.0d, 0.0d);
                                geoPoint.setLatitudeE6(optJSONArray2.optDouble(1));
                                geoPoint.setLongitudeE6(optJSONArray2.optDouble(0));
                                routeNode = new RouteNode();
                                routeNode.setLocation(CoordUtil.mc2ll(geoPoint));
                                indoorRouteLine.setStarting(routeNode);
                            }
                            optJSONArray2 = optJSONObject.optJSONArray("send_location");
                            if (optJSONArray2 != null) {
                                geoPoint = new GeoPoint(0.0d, 0.0d);
                                geoPoint.setLatitudeE6(optJSONArray2.optDouble(1));
                                geoPoint.setLongitudeE6(optJSONArray2.optDouble(0));
                                routeNode = new RouteNode();
                                routeNode.setLocation(CoordUtil.mc2ll(geoPoint));
                                indoorRouteLine.setTerminal(routeNode);
                            }
                            JSONArray optJSONArray3 = optJSONObject.optJSONArray("steps");
                            if (optJSONArray3 != null) {
                                List arrayList2 = new ArrayList();
                                for (int i2 = 0; i2 < optJSONArray3.length(); i2++) {
                                    IndoorRouteStep indoorRouteStep = new IndoorRouteStep();
                                    JSONObject optJSONObject2 = optJSONArray3.optJSONObject(i2);
                                    if (optJSONObject2 != null) {
                                        indoorRouteStep.setDistance(optJSONObject2.optInt("distance"));
                                        indoorRouteStep.setDuration(optJSONObject2.optInt("duration"));
                                        indoorRouteStep.m1595b(optJSONObject2.optString("buildingid"));
                                        indoorRouteStep.m1596c(optJSONObject2.optString("floorid"));
                                        optJSONArray2 = optJSONObject2.optJSONArray("sstart_location");
                                        if (optJSONArray2 != null) {
                                            geoPoint = new GeoPoint(0.0d, 0.0d);
                                            geoPoint.setLatitudeE6(optJSONArray2.optDouble(1));
                                            geoPoint.setLongitudeE6(optJSONArray2.optDouble(0));
                                            routeNode = new RouteNode();
                                            routeNode.setLocation(CoordUtil.mc2ll(geoPoint));
                                            indoorRouteStep.setEntrace(routeNode);
                                        }
                                        optJSONArray2 = optJSONObject2.optJSONArray("send_location");
                                        if (optJSONArray2 != null) {
                                            geoPoint = new GeoPoint(0.0d, 0.0d);
                                            geoPoint.setLatitudeE6(optJSONArray2.optDouble(1));
                                            geoPoint.setLongitudeE6(optJSONArray2.optDouble(0));
                                            routeNode = new RouteNode();
                                            routeNode.setLocation(CoordUtil.mc2ll(geoPoint));
                                            indoorRouteStep.setExit(routeNode);
                                        }
                                        JSONArray optJSONArray4 = optJSONObject2.optJSONArray("spath");
                                        if (optJSONArray4 != null) {
                                            int i3;
                                            ArrayList arrayList3 = new ArrayList();
                                            if (optJSONArray4.length() > 6) {
                                                double optDouble = optJSONArray4.optDouble(6);
                                                double optDouble2 = optJSONArray4.optDouble(5);
                                                GeoPoint geoPoint2 = new GeoPoint(0.0d, 0.0d);
                                                geoPoint2.setLatitudeE6(optDouble);
                                                geoPoint2.setLongitudeE6(optDouble2);
                                                LatLng mc2ll = CoordUtil.mc2ll(geoPoint2);
                                                arrayList3.add(Double.valueOf(mc2ll.latitude));
                                                arrayList3.add(Double.valueOf(mc2ll.longitude));
                                                for (i3 = 7; i3 < optJSONArray4.length(); i3 += 2) {
                                                    optDouble += optJSONArray4.optDouble(i3 + 1);
                                                    optDouble2 += optJSONArray4.optDouble(i3);
                                                    geoPoint2.setLatitudeE6(optDouble);
                                                    geoPoint2.setLongitudeE6(optDouble2);
                                                    LatLng mc2ll2 = CoordUtil.mc2ll(geoPoint2);
                                                    arrayList3.add(Double.valueOf(mc2ll2.latitude));
                                                    arrayList3.add(Double.valueOf(mc2ll2.longitude));
                                                }
                                            }
                                            indoorRouteStep.setPath(arrayList3);
                                            indoorRouteStep.m1594a(optJSONObject2.optString("instructions"));
                                            JSONArray optJSONArray5 = optJSONObject2.optJSONArray("pois");
                                            if (optJSONArray5 != null) {
                                                List arrayList4 = new ArrayList();
                                                for (i3 = 0; i3 < optJSONArray5.length(); i3++) {
                                                    JSONObject optJSONObject3 = optJSONArray5.optJSONObject(i3);
                                                    if (optJSONObject3 != null) {
                                                        IndoorStepNode indoorStepNode = new IndoorStepNode();
                                                        indoorStepNode.setDetail(optJSONObject3.optString("detail"));
                                                        indoorStepNode.setName(optJSONObject3.optString("name"));
                                                        indoorStepNode.setType(optJSONObject3.optInt("type"));
                                                        JSONArray optJSONArray6 = optJSONObject3.optJSONArray(SocializeConstants.KEY_LOCATION);
                                                        if (optJSONArray6 != null) {
                                                            GeoPoint geoPoint3 = new GeoPoint(0.0d, 0.0d);
                                                            geoPoint3.setLatitudeE6(optJSONArray6.optDouble(1));
                                                            geoPoint3.setLongitudeE6(optJSONArray6.optDouble(0));
                                                            indoorStepNode.setLocation(CoordUtil.mc2ll(geoPoint3));
                                                        }
                                                        arrayList4.add(indoorStepNode);
                                                    }
                                                }
                                                indoorRouteStep.setStepNodes(arrayList4);
                                            }
                                            arrayList2.add(indoorRouteStep);
                                        }
                                    }
                                }
                                if (arrayList2.size() > 0) {
                                    indoorRouteLine.setSteps(arrayList2);
                                }
                            }
                            arrayList.add(indoorRouteLine);
                        }
                    }
                    indoorRouteResult.m1597a(arrayList);
                    return indoorRouteResult;
                case 6:
                    indoorRouteResult.error = ERRORNO.INDOOR_ROUTE_NO_IN_BUILDING;
                    return indoorRouteResult;
                case 7:
                    indoorRouteResult.error = ERRORNO.INDOOR_ROUTE_NO_IN_SAME_BUILDING;
                    return indoorRouteResult;
                default:
                    indoorRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
                    return indoorRouteResult;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            indoorRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return indoorRouteResult;
        }
    }

    private static SuggestAddrInfo m1700d(JSONObject jSONObject) {
        SuggestAddrInfo suggestAddrInfo = new SuggestAddrInfo();
        JSONObject optJSONObject = jSONObject.optJSONObject("start_sug");
        JSONObject optJSONObject2 = jSONObject.optJSONObject("end_sug");
        if (optJSONObject != null) {
            int optInt = optJSONObject.optInt("listType");
            String optString = optJSONObject.optString("cityName");
            if (optInt == 1) {
                suggestAddrInfo.m1636d(C0568n.m1692b(optJSONObject, "list"));
            } else if (optInt == 0) {
                suggestAddrInfo.m1633a(C0568n.m1693b(optJSONObject, "list", optString));
            }
        }
        if (optJSONObject2 != null) {
            int optInt2 = optJSONObject2.optInt("listType");
            String optString2 = optJSONObject2.optString("cityName");
            if (optInt2 == 1) {
                suggestAddrInfo.m1637e(C0568n.m1692b(optJSONObject2, "list"));
            } else if (optInt2 == 0) {
                suggestAddrInfo.m1634b(C0568n.m1693b(optJSONObject2, "list", optString2));
            }
        }
        return suggestAddrInfo;
    }

    private static List<PoiInfo> m1701d(JSONArray jSONArray) {
        if (jSONArray != null) {
            List<PoiInfo> arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = (JSONObject) jSONArray.opt(i);
                if (jSONObject != null) {
                    PoiInfo poiInfo = new PoiInfo();
                    poiInfo.address = jSONObject.optString(MapConstants.ADDRESS);
                    poiInfo.uid = jSONObject.optString("uid");
                    poiInfo.name = jSONObject.optString("name");
                    jSONObject = jSONObject.optJSONObject(SocializeConstants.KEY_LOCATION);
                    poiInfo.location = new LatLng(jSONObject.optDouble("lat"), jSONObject.optDouble("lng"));
                    arrayList.add(poiInfo);
                }
            }
            if (!arrayList.isEmpty()) {
                return arrayList;
            }
        }
        return null;
    }

    private static RouteNode m1702e(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        RouteNode routeNode = new RouteNode();
        routeNode.setTitle(optJSONObject.optString("name"));
        GeoPoint geoPoint = new GeoPoint(0.0d, 0.0d);
        geoPoint.setLongitudeE6((double) optJSONObject.optInt("loc_x"));
        geoPoint.setLatitudeE6((double) optJSONObject.optInt("loc_y"));
        routeNode.setLocation(CoordUtil.mc2ll(geoPoint));
        return routeNode;
    }

    public static WalkingRouteResult m1703e(String str) {
        WalkingRouteResult walkingRouteResult = new WalkingRouteResult();
        if (str == null || "".equals(str)) {
            walkingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return walkingRouteResult;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("taxi")) {
                walkingRouteResult.m1655a(C0568n.m1707h(jSONObject.optString("taxi")));
            }
            WalkingRouteLine walkingRouteLine = new WalkingRouteLine();
            walkingRouteLine.setStarting(C0568n.m1694c(jSONObject, "start_point"));
            walkingRouteLine.setTerminal(C0568n.m1694c(jSONObject, "end_point"));
            jSONObject = jSONObject.optJSONObject("routes");
            if (jSONObject == null) {
                return walkingRouteResult;
            }
            jSONObject = jSONObject.optJSONObject("legs");
            if (jSONObject == null) {
                return walkingRouteResult;
            }
            walkingRouteLine.setDistance(jSONObject.optInt("distance"));
            walkingRouteLine.setDuration(jSONObject.optInt("duration"));
            JSONArray optJSONArray = jSONObject.optJSONArray("steps");
            if (optJSONArray != null) {
                List arrayList = new ArrayList();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    WalkingStep walkingStep = new WalkingStep();
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        walkingStep.m1648a(optJSONObject.optInt("direction") * 30);
                        walkingStep.setDistance(optJSONObject.optInt("distance"));
                        walkingStep.setDuration(optJSONObject.optInt("duration"));
                        walkingStep.m1649a(RouteNode.location(CoordUtil.decodeLocation(optJSONObject.optString("start_loc"))));
                        walkingStep.m1651b(RouteNode.location(CoordUtil.decodeLocation(optJSONObject.optString("end_loc"))));
                        walkingStep.m1654d(optJSONObject.optString("description"));
                        walkingStep.m1652b(optJSONObject.optString("start_desc"));
                        walkingStep.m1653c(optJSONObject.optString("end_desc"));
                        walkingStep.m1650a(optJSONObject.optString("path"));
                        arrayList.add(walkingStep);
                    }
                }
                if (arrayList.size() > 0) {
                    walkingRouteLine.setSteps(arrayList);
                }
            }
            List arrayList2 = new ArrayList();
            arrayList2.add(walkingRouteLine);
            walkingRouteResult.m1657a(arrayList2);
            return walkingRouteResult;
        } catch (JSONException e) {
            e.printStackTrace();
            walkingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return walkingRouteResult;
        }
    }

    public static BikingRouteResult m1704f(String str) {
        BikingRouteResult bikingRouteResult = new BikingRouteResult();
        if (str == null || "".equals(str)) {
            bikingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return bikingRouteResult;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("type");
            if (optInt == 1) {
                bikingRouteResult.m1577a(C0568n.m1700d(jSONObject));
                bikingRouteResult.error = ERRORNO.AMBIGUOUS_ROURE_ADDR;
            } else if (optInt == 2) {
                JSONArray optJSONArray = jSONObject.optJSONArray("routes");
                List arrayList = new ArrayList();
                optInt = 0;
                while (optInt < optJSONArray.length()) {
                    BikingRouteLine bikingRouteLine = new BikingRouteLine();
                    try {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(optInt);
                        bikingRouteLine.setStarting(C0568n.m1698d(jSONObject, "start"));
                        bikingRouteLine.setTerminal(C0568n.m1698d(jSONObject, "end"));
                        if (optJSONObject == null) {
                            return bikingRouteResult;
                        }
                        bikingRouteLine.setDistance(optJSONObject.optInt("distance"));
                        bikingRouteLine.setDuration(optJSONObject.optInt("duration"));
                        JSONArray optJSONArray2 = optJSONObject.optJSONArray("steps");
                        if (optJSONArray2 != null) {
                            List arrayList2 = new ArrayList();
                            for (int i = 0; i < optJSONArray2.length(); i++) {
                                JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i);
                                BikingStep bikingStep = new BikingStep();
                                if (optJSONObject2 != null && optJSONObject2.length() > 0) {
                                    bikingStep.m1570a(optJSONObject2.optInt("direction") * 30);
                                    bikingStep.setDistance(optJSONObject2.optInt("distance"));
                                    bikingStep.setDuration(optJSONObject2.optInt("duration"));
                                    JSONObject optJSONObject3 = optJSONObject2.optJSONObject("start_pt");
                                    JSONObject optJSONObject4 = optJSONObject2.optJSONObject("end_pt");
                                    bikingStep.m1571a(RouteNode.location(new LatLng(optJSONObject3.optDouble("lat"), optJSONObject3.optDouble("lng"))));
                                    bikingStep.m1573b(RouteNode.location(new LatLng(optJSONObject4.optDouble("lat"), optJSONObject4.optDouble("lng"))));
                                    bikingStep.m1576d(optJSONObject2.optString("instructions"));
                                    bikingStep.m1574b(optJSONObject2.optString("start_instructions"));
                                    bikingStep.m1575c(optJSONObject2.optString("end_instructions"));
                                    bikingStep.m1572a(optJSONObject2.optString("path"));
                                    arrayList2.add(bikingStep);
                                }
                            }
                            if (arrayList2.size() > 0) {
                                bikingRouteLine.setSteps(arrayList2);
                            }
                        }
                        arrayList.add(bikingRouteLine);
                        optInt++;
                    } catch (Exception e) {
                    }
                }
                bikingRouteResult.m1578a(arrayList);
                return bikingRouteResult;
            } else {
                bikingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
            return bikingRouteResult;
        } catch (JSONException e2) {
            e2.printStackTrace();
            bikingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return bikingRouteResult;
        }
    }

    private static List<RouteNode> m1705f(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < optJSONArray.length(); i++) {
            RouteNode routeNode = new RouteNode();
            try {
                routeNode.setTitle(optJSONArray.getJSONObject(i).optString("name"));
                routeNode.setUid(optJSONArray.getJSONObject(i).optString("uid"));
                GeoPoint geoPoint = new GeoPoint(0.0d, 0.0d);
                geoPoint.setLongitudeE6((double) optJSONArray.getJSONObject(i).optInt("loc_x"));
                geoPoint.setLatitudeE6((double) optJSONArray.getJSONObject(i).optInt("loc_y"));
                routeNode.setLocation(CoordUtil.mc2ll(geoPoint));
                arrayList.add(routeNode);
            } catch (JSONException e) {
            }
        }
        return arrayList;
    }

    public static List<TaxiInfo> m1706g(String str) {
        List<TaxiInfo> arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray == null) {
                return null;
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject != null) {
                    TaxiInfo taxiInfo = new TaxiInfo();
                    String optString = jSONObject.optString("total_price");
                    if (optString == null || optString.equals("")) {
                        taxiInfo.setTotalPrice(0.0f);
                    } else {
                        taxiInfo.setTotalPrice(Float.parseFloat(optString));
                    }
                    arrayList.add(taxiInfo);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return arrayList;
        }
    }

    public static TaxiInfo m1707h(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            return null;
        }
        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setDesc(jSONObject.optString("remark"));
        taxiInfo.setDistance(jSONObject.optInt("distance"));
        taxiInfo.setDuration(jSONObject.optInt("duration"));
        taxiInfo.setTotalPrice((float) jSONObject.optDouble("total_price"));
        taxiInfo.setStartPrice((float) jSONObject.optDouble("start_price"));
        taxiInfo.setPerKMPrice((float) jSONObject.optDouble("km_price"));
        return taxiInfo;
    }

    public static VehicleInfo m1708i(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            return null;
        }
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setZonePrice(jSONObject.optInt("zone_price"));
        vehicleInfo.setTotalPrice(jSONObject.optInt("total_price"));
        vehicleInfo.setTitle(jSONObject.optString("name"));
        vehicleInfo.setPassStationNum(jSONObject.optInt("stop_num"));
        vehicleInfo.setUid(jSONObject.optString("uid"));
        return vehicleInfo;
    }

    public static SuggestAddrInfo m1709j(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject("address_info");
            if (optJSONObject == null) {
                return null;
            }
            SuggestAddrInfo suggestAddrInfo = new SuggestAddrInfo();
            String optString = optJSONObject.optString("st_cityname");
            String optString2 = optJSONObject.optString("en_cityname");
            if (jSONObject.optBoolean("hasAddrList")) {
                suggestAddrInfo.m1636d(C0568n.m1687a(jSONObject, "startcitys"));
                suggestAddrInfo.m1633a(C0568n.m1688a(jSONObject, "startpoints", optString));
                suggestAddrInfo.m1637e(C0568n.m1687a(jSONObject, "endcitys"));
                suggestAddrInfo.m1634b(C0568n.m1688a(jSONObject, "endpoints", optString2));
                JSONArray optJSONArray = jSONObject.optJSONArray("waypoints_result");
                if (optJSONArray == null || optJSONArray.length() <= 0) {
                    return suggestAddrInfo;
                }
                List arrayList = new ArrayList();
                List arrayList2 = new ArrayList();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                    List a = C0568n.m1687a(jSONObject2, "waypointcitys");
                    if (a != null) {
                        arrayList.add(a);
                    }
                    List a2 = C0568n.m1688a(jSONObject2, "waypoints", "");
                    if (a2 != null) {
                        arrayList2.add(a2);
                    }
                }
                if (arrayList.size() > 0) {
                    suggestAddrInfo.m1638f(arrayList);
                }
                if (arrayList2.size() > 0) {
                    suggestAddrInfo.m1635c(arrayList2);
                }
            }
            return suggestAddrInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String m1710k(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        char[] toCharArray = str.toCharArray();
        Object obj = null;
        for (int i = 0; i < toCharArray.length; i++) {
            if (toCharArray[i] == '<') {
                obj = 1;
            } else if (toCharArray[i] == '>') {
                obj = null;
            } else if (obj == null) {
                stringBuilder.append(toCharArray[i]);
            }
        }
        return stringBuilder.toString();
    }
}
