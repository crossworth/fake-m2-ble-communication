package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import java.util.ArrayList;
import org.json.JSONObject;

/* compiled from: GeocodingHandler */
public class C2050l extends C1972b<GeocodeQuery, ArrayList<GeocodeAddress>> {
    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public C2050l(Context context, GeocodeQuery geocodeQuery) {
        super(context, geocodeQuery);
    }

    protected ArrayList<GeocodeAddress> mo3703d(String str) throws AMapException {
        ArrayList<GeocodeAddress> arrayList = new ArrayList();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("count") && jSONObject.getInt("count") > 0) {
                arrayList = C0391n.m1636l(jSONObject);
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "GeocodingHandler", "paseJSONJSONException");
        } catch (Throwable e2) {
            C0390i.m1594a(e2, "GeocodingHandler", "paseJSONException");
        }
        return arrayList;
    }

    public String mo1759g() {
        return C0389h.m1585a() + "/geocode/geo?";
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("output=json").append("&address=").append(m5789b(((GeocodeQuery) this.a).getLocationName()));
        String city = ((GeocodeQuery) this.a).getCity();
        if (!C0391n.m1630i(city)) {
            stringBuffer.append("&city=").append(m5789b(city));
        }
        stringBuffer.append("&key=" + as.m1215f(this.d));
        stringBuffer.append("&language=").append(C0389h.m1588c());
        return stringBuffer.toString();
    }
}
