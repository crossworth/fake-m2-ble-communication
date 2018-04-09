package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ReverseGeocodingHandler */
public class aa extends C1972b<RegeocodeQuery, RegeocodeAddress> {
    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public aa(Context context, RegeocodeQuery regeocodeQuery) {
        super(context, regeocodeQuery);
    }

    public String mo1759g() {
        return C0389h.m1585a() + "/geocode/regeo?";
    }

    protected RegeocodeAddress mo3703d(String str) throws AMapException {
        RegeocodeAddress regeocodeAddress = new RegeocodeAddress();
        try {
            JSONObject optJSONObject = new JSONObject(str).optJSONObject("regeocode");
            if (optJSONObject != null) {
                regeocodeAddress.setFormatAddress(C0391n.m1600a(optJSONObject, "formatted_address"));
                JSONObject optJSONObject2 = optJSONObject.optJSONObject("addressComponent");
                if (optJSONObject2 != null) {
                    C0391n.m1608a(optJSONObject2, regeocodeAddress);
                }
                regeocodeAddress.setPois(C0391n.m1615c(optJSONObject));
                JSONArray optJSONArray = optJSONObject.optJSONArray("roads");
                if (optJSONArray != null) {
                    C0391n.m1613b(optJSONArray, regeocodeAddress);
                }
                optJSONArray = optJSONObject.optJSONArray("roadinters");
                if (optJSONArray != null) {
                    C0391n.m1606a(optJSONArray, regeocodeAddress);
                }
                JSONArray optJSONArray2 = optJSONObject.optJSONArray("aois");
                if (optJSONArray2 != null) {
                    C0391n.m1617c(optJSONArray2, regeocodeAddress);
                }
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "ReverseGeocodingHandler", "paseJSON");
        }
        return regeocodeAddress;
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("output=json").append("&extensions=all").append("&location=").append(((RegeocodeQuery) this.a).getPoint().getLongitude()).append(SeparatorConstants.SEPARATOR_ADS_ID).append(((RegeocodeQuery) this.a).getPoint().getLatitude());
        stringBuffer.append("&radius=").append(((RegeocodeQuery) this.a).getRadius());
        stringBuffer.append("&coordsys=").append(((RegeocodeQuery) this.a).getLatLonType());
        stringBuffer.append("&key=" + as.m1215f(this.d));
        stringBuffer.append("&language=").append(C0389h.m1588c());
        return stringBuffer.toString();
    }
}
