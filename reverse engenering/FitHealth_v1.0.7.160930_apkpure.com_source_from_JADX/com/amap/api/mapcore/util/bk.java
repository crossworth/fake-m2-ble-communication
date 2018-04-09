package com.amap.api.mapcore.util;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.MapsInitializer;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: OfflineInitHandler */
public class bk extends cj<String, bj> {
    protected /* synthetic */ Object mo3002b(String str) throws AMapException {
        return m5730a(str);
    }

    public bk(Context context, String str) {
        super(context, str);
        getClass();
        m972a(5000);
        getClass();
        m975b(50000);
    }

    public String mo1630a() {
        return "http://restapi.amap.com/v3/config/version";
    }

    protected bj m5730a(String str) throws AMapException {
        bj bjVar = new bj();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("offlinemap")) {
                jSONObject = jSONObject.getJSONObject("offlinemap");
                String optString = jSONObject.optString(MessageObj.ACTION_UPDATE, "");
                if (optString.equals("0")) {
                    bjVar.m307a(false);
                } else if (optString.equals("1")) {
                    bjVar.m307a(true);
                }
                bjVar.m306a(jSONObject.optString("version", ""));
            }
        } catch (Throwable th) {
            ee.m4243a(th, "OfflineInitHandler", "loadData parseJson");
        }
        return bjVar;
    }

    public Map<String, String> mo1631b() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("mapver", this.a);
        hashMap.put("output", "json");
        if (!TextUtils.isEmpty(MapsInitializer.KEY)) {
            dm.m611a(MapsInitializer.KEY);
        }
        hashMap.put("key", dl.m607f(this.d));
        hashMap.put("opertype", "offlinemap_with_province_vfour");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("mapver=").append((String) this.a);
        stringBuffer.append("&output=json");
        stringBuffer.append("&key=").append(dl.m607f(this.d));
        stringBuffer.append("&opertype=offlinemap_with_province_vfour");
        String d = dx.m727d(stringBuffer.toString());
        String a = dn.m616a();
        hashMap.put("ts", a);
        hashMap.put("scode", dn.m621a(this.d, a, d));
        return hashMap;
    }
}
