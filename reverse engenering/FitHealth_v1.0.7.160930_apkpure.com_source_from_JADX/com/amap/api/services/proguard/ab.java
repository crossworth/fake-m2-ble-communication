package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: ShareUrlSearchHandler */
public class ab extends C1608a<String, String> {
    private String f5410h;

    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return m5779b(str);
    }

    public ab(Context context, String str) {
        super(context, str);
        this.f5410h = str;
    }

    public Map<String, String> mo1756b() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("channel=open_api").append("&flag=1").append("&address=" + URLEncoder.encode(this.f5410h));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("open_api").append("1").append(this.f5410h).append("@").append("8UbJH6N2szojnTHONAWzB6K7N1kaj7Y0iUMarxac");
        stringBuilder.append("&sign=").append(ay.m1282b(stringBuffer.toString()).toUpperCase(Locale.US));
        stringBuilder.append("&output=json");
        byte[] bArr = null;
        try {
            bArr = ag.m1202a(stringBuilder.toString().getBytes("utf-8"), "Yaynpa84IKOfasFx".substring(0, 16).getBytes("utf-8"));
        } catch (Throwable e) {
            C0390i.m1594a(e, "ShareUrlSearchHandler", "getParams");
        }
        Map<String, String> hashMap = new HashMap();
        hashMap.put("ent", "2");
        hashMap.put("in", ax.m1272a(bArr));
        hashMap.put("keyt", "openapi");
        return hashMap;
    }

    protected String m5779b(String str) throws AMapException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String a = C0391n.m1600a(jSONObject, "code");
            if ("1".equals(a)) {
                return C0391n.m1600a(jSONObject, "transfer_url");
            }
            if ("0".equals(a)) {
                throw new AMapException(AMapException.AMAP_SERVICE_UNKNOWN_ERROR);
            } else if ("2".equals(a)) {
                throw new AMapException(AMapException.AMAP_SHARE_FAILURE);
            } else if ("3".equals(a)) {
                throw new AMapException(AMapException.AMAP_SERVICE_INVALID_PARAMS);
            } else if ("4".equals(a)) {
                throw new AMapException(AMapException.AMAP_SIGNATURE_ERROR);
            } else {
                if ("5".equals(a)) {
                    throw new AMapException(AMapException.AMAP_SHARE_LICENSE_IS_EXPIRED);
                }
                return null;
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "ShareUrlSearchHandler", "paseJSON");
        }
    }

    public String mo1759g() {
        return "http://m5.amap.com/ws/mapapi/shortaddress/transform";
    }

    protected byte[] mo3043a(int i, cv cvVar, cw cwVar) throws ar {
        return cvVar.m4501d(cwVar);
    }
}
