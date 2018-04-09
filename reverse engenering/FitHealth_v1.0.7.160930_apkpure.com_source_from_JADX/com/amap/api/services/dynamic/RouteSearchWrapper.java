package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IRouteSearch;
import com.amap.api.services.proguard.ao;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkRouteResult;

public class RouteSearchWrapper implements IRouteSearch {
    private IRouteSearch f4271a;

    public RouteSearchWrapper(Context context) {
        this.f4271a = new ao(context);
    }

    public void setRouteSearchListener(OnRouteSearchListener onRouteSearchListener) {
        if (this.f4271a != null) {
            this.f4271a.setRouteSearchListener(onRouteSearchListener);
        }
    }

    public WalkRouteResult calculateWalkRoute(WalkRouteQuery walkRouteQuery) throws AMapException {
        if (this.f4271a != null) {
            return this.f4271a.calculateWalkRoute(walkRouteQuery);
        }
        return null;
    }

    public void calculateWalkRouteAsyn(WalkRouteQuery walkRouteQuery) {
        if (this.f4271a != null) {
            this.f4271a.calculateWalkRouteAsyn(walkRouteQuery);
        }
    }

    public BusRouteResult calculateBusRoute(BusRouteQuery busRouteQuery) throws AMapException {
        if (this.f4271a != null) {
            return this.f4271a.calculateBusRoute(busRouteQuery);
        }
        return null;
    }

    public void calculateBusRouteAsyn(BusRouteQuery busRouteQuery) {
        if (this.f4271a != null) {
            this.f4271a.calculateBusRouteAsyn(busRouteQuery);
        }
    }

    public DriveRouteResult calculateDriveRoute(DriveRouteQuery driveRouteQuery) throws AMapException {
        if (this.f4271a != null) {
            return this.f4271a.calculateDriveRoute(driveRouteQuery);
        }
        return null;
    }

    public void calculateDriveRouteAsyn(DriveRouteQuery driveRouteQuery) {
        if (this.f4271a != null) {
            this.f4271a.calculateDriveRouteAsyn(driveRouteQuery);
        }
    }
}
