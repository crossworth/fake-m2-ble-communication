package com.amap.api.services.interfaces;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkRouteResult;

public interface IRouteSearch {
    BusRouteResult calculateBusRoute(BusRouteQuery busRouteQuery) throws AMapException;

    void calculateBusRouteAsyn(BusRouteQuery busRouteQuery);

    DriveRouteResult calculateDriveRoute(DriveRouteQuery driveRouteQuery) throws AMapException;

    void calculateDriveRouteAsyn(DriveRouteQuery driveRouteQuery);

    WalkRouteResult calculateWalkRoute(WalkRouteQuery walkRouteQuery) throws AMapException;

    void calculateWalkRouteAsyn(WalkRouteQuery walkRouteQuery);

    void setRouteSearchListener(OnRouteSearchListener onRouteSearchListener);
}
