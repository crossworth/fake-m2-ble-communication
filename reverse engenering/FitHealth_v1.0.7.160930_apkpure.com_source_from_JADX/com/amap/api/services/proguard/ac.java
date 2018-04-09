package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkRouteResult;

/* compiled from: WalkRouteSearchHandler */
public class ac extends C1972b<WalkRouteQuery, WalkRouteResult> {
    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public ac(Context context, WalkRouteQuery walkRouteQuery) {
        super(context, walkRouteQuery);
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=").append(as.m1215f(this.d));
        stringBuffer.append("&origin=").append(C0390i.m1591a(((WalkRouteQuery) this.a).getFromAndTo().getFrom()));
        stringBuffer.append("&destination=").append(C0390i.m1591a(((WalkRouteQuery) this.a).getFromAndTo().getTo()));
        stringBuffer.append("&multipath=0");
        stringBuffer.append("&output=json");
        return stringBuffer.toString();
    }

    protected WalkRouteResult mo3703d(String str) throws AMapException {
        return C0391n.m1614c(str);
    }

    public String mo1759g() {
        return C0389h.m1585a() + "/direction/walking?";
    }
}
