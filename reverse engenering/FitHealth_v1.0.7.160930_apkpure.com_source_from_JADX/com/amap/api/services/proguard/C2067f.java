package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.core.AMapException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CloudSearchIdHandler */
public class C2067f extends C2047e<C0409y, CloudItemDetail> {
    public /* synthetic */ Object mo3042a(String str) throws AMapException {
        return m6297e(str);
    }

    public C2067f(Context context, C0409y c0409y) {
        super(context, c0409y);
    }

    public String mo1759g() {
        return C0389h.m1587b() + "/datasearch/id?";
    }

    public CloudItemDetail m6297e(String str) throws AMapException {
        CloudItemDetail cloudItemDetail = null;
        if (!(str == null || str.equals(""))) {
            try {
                cloudItemDetail = m6295b(new JSONObject(str));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return cloudItemDetail;
    }

    private CloudItemDetail m6295b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("datas")) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("datas");
        if (optJSONArray.length() <= 0) {
            return null;
        }
        JSONObject jSONObject2 = optJSONArray.getJSONObject(0);
        CloudItemDetail a = m6220a(jSONObject2);
        m6221a(a, jSONObject2);
        return a;
    }

    protected String mo3048e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("key=" + as.m1215f(this.d));
        stringBuilder.append("&tableid=" + ((C0409y) this.a).f1576a);
        stringBuilder.append("&output=json");
        stringBuilder.append("&_id=" + ((C0409y) this.a).f1577b);
        return stringBuilder.toString();
    }
}
