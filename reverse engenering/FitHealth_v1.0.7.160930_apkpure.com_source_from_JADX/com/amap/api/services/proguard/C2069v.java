package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.ServiceSettings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PoiSearchIdHandler */
public class C2069v extends C2055u<String, PoiItem> {
    public /* synthetic */ Object mo3042a(String str) throws AMapException {
        return m6310e(str);
    }

    public C2069v(Context context, String str) {
        super(context, str);
    }

    public String mo1759g() {
        return C0389h.m1585a() + "/place/detail?";
    }

    public PoiItem m6310e(String str) throws AMapException {
        PoiItem poiItem = null;
        try {
            poiItem = m6307a(new JSONObject(str));
        } catch (Throwable e) {
            C0390i.m1594a(e, "PoiSearchIdHandler", "paseJSONJSONException");
        } catch (Throwable e2) {
            C0390i.m1594a(e2, "PoiSearchIdHandler", "paseJSONException");
        }
        return poiItem;
    }

    private PoiItem m6307a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("pois");
        if (optJSONArray == null || optJSONArray.length() <= 0) {
            return null;
        }
        JSONObject optJSONObject = optJSONArray.optJSONObject(0);
        if (optJSONObject != null) {
            return C0391n.m1618d(optJSONObject);
        }
        return null;
    }

    protected String mo3048e() {
        return m6308h();
    }

    private String m6308h() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=").append((String) this.a);
        stringBuilder.append("&output=json");
        stringBuilder.append("&extensions=all");
        stringBuilder.append("&children=1");
        stringBuilder.append("&language=").append(ServiceSettings.getInstance().getLanguage());
        stringBuilder.append("&key=" + as.m1215f(this.d));
        return stringBuilder.toString();
    }
}
