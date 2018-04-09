package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;

/* compiled from: BusRouteSearchHandler */
public class C2045c extends C1972b<BusRouteQuery, BusRouteResult> {
    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public C2045c(Context context, BusRouteQuery busRouteQuery) {
        super(context, busRouteQuery);
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=").append(as.m1215f(this.d));
        stringBuffer.append("&origin=").append(C0390i.m1591a(((BusRouteQuery) this.a).getFromAndTo().getFrom()));
        stringBuffer.append("&destination=").append(C0390i.m1591a(((BusRouteQuery) this.a).getFromAndTo().getTo()));
        String city = ((BusRouteQuery) this.a).getCity();
        if (!C0391n.m1630i(city)) {
            stringBuffer.append("&city=").append(m5789b(city));
        }
        stringBuffer.append("&strategy=").append("" + ((BusRouteQuery) this.a).getMode());
        stringBuffer.append("&nightflag=").append(((BusRouteQuery) this.a).getNightFlag());
        stringBuffer.append("&output=json");
        return stringBuffer.toString();
    }

    protected BusRouteResult mo3703d(String str) throws AMapException {
        return C0391n.m1599a(str);
    }

    public String mo1759g() {
        return C0389h.m1585a() + "/direction/transit/integrated?";
    }
}
