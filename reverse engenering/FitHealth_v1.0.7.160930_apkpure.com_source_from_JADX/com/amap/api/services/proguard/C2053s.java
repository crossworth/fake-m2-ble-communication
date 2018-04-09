package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.nearby.NearbySearch.NearbyQuery;
import com.amap.api.services.nearby.NearbySearchResult;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.List;
import org.json.JSONObject;

/* compiled from: NearbySearchHandler */
public class C2053s extends C1972b<NearbyQuery, NearbySearchResult> {
    private Context f5566h;
    private NearbyQuery f5567i;

    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public C2053s(Context context, NearbyQuery nearbyQuery) {
        super(context, nearbyQuery);
        this.f5566h = context;
        this.f5567i = nearbyQuery;
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=").append(as.m1215f(this.f5566h));
        LatLonPoint centerPoint = this.f5567i.getCenterPoint();
        stringBuffer.append("&center=").append(centerPoint.getLongitude()).append(SeparatorConstants.SEPARATOR_ADS_ID).append(centerPoint.getLatitude());
        stringBuffer.append("&radius=").append(this.f5567i.getRadius());
        stringBuffer.append("&searchtype=").append(this.f5567i.getType());
        stringBuffer.append("&timerange=").append(this.f5567i.getTimeRange());
        return stringBuffer.toString();
    }

    protected NearbySearchResult mo3703d(String str) throws AMapException {
        boolean z = true;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (this.f5567i.getType() != 1) {
                z = false;
            }
            List a = C0391n.m1602a(jSONObject, z);
            NearbySearchResult nearbySearchResult = new NearbySearchResult();
            nearbySearchResult.setNearbyInfoList(a);
            return nearbySearchResult;
        } catch (Throwable e) {
            C0390i.m1594a(e, "NearbySearchHandler", "paseJSON");
            return null;
        }
    }

    public String mo1759g() {
        return C0389h.m1587b() + "/nearby/around";
    }
}
