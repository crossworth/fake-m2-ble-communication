package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;

/* compiled from: DriveRouteSearchHandler */
public class C2049k extends C1972b<DriveRouteQuery, DriveRouteResult> {
    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public C2049k(Context context, DriveRouteQuery driveRouteQuery) {
        super(context, driveRouteQuery);
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=").append(as.m1215f(this.d));
        stringBuffer.append("&origin=").append(C0390i.m1591a(((DriveRouteQuery) this.a).getFromAndTo().getFrom()));
        if (!C0391n.m1630i(((DriveRouteQuery) this.a).getFromAndTo().getStartPoiID())) {
            stringBuffer.append("&originid=").append(((DriveRouteQuery) this.a).getFromAndTo().getStartPoiID());
        }
        stringBuffer.append("&destination=").append(C0390i.m1591a(((DriveRouteQuery) this.a).getFromAndTo().getTo()));
        if (!C0391n.m1630i(((DriveRouteQuery) this.a).getFromAndTo().getDestinationPoiID())) {
            stringBuffer.append("&destinationid=").append(((DriveRouteQuery) this.a).getFromAndTo().getDestinationPoiID());
        }
        stringBuffer.append("&strategy=").append("" + ((DriveRouteQuery) this.a).getMode());
        stringBuffer.append("&extensions=all");
        if (((DriveRouteQuery) this.a).hasPassPoint()) {
            stringBuffer.append("&waypoints=").append(((DriveRouteQuery) this.a).getPassedPointStr());
        }
        if (((DriveRouteQuery) this.a).hasAvoidpolygons()) {
            stringBuffer.append("&avoidpolygons=").append(((DriveRouteQuery) this.a).getAvoidpolygonsStr());
        }
        if (((DriveRouteQuery) this.a).hasAvoidRoad()) {
            stringBuffer.append("&avoidroad=").append(m5789b(((DriveRouteQuery) this.a).getAvoidRoad()));
        }
        stringBuffer.append("&roadaggregation=true");
        stringBuffer.append("&output=json");
        return stringBuffer.toString();
    }

    protected DriveRouteResult mo3703d(String str) throws AMapException {
        return C0391n.m1610b(str);
    }

    public String mo1759g() {
        return C0389h.m1585a() + "/direction/driving?";
    }
}
