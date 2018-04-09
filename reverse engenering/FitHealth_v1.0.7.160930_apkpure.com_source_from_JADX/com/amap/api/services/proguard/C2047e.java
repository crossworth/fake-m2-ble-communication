package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.cloud.CloudImage;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpHeaders;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CloudHandler */
public abstract class C2047e<T, V> extends C1972b<T, V> {
    public C2047e(Context context, T t) {
        super(context, t);
    }

    protected CloudItemDetail m6220a(JSONObject jSONObject) throws JSONException {
        CloudItemDetail cloudItemDetail = new CloudItemDetail(C0391n.m1600a(jSONObject, "_id"), C0391n.m1609b(jSONObject, "_location"), C0391n.m1600a(jSONObject, "_name"), C0391n.m1600a(jSONObject, "_address"));
        cloudItemDetail.setCreatetime(C0391n.m1600a(jSONObject, "_createtime"));
        cloudItemDetail.setUpdatetime(C0391n.m1600a(jSONObject, "_updatetime"));
        if (jSONObject.has("_distance")) {
            String optString = jSONObject.optString("_distance");
            if (!mo3703d(optString)) {
                cloudItemDetail.setDistance(Integer.parseInt(optString));
            }
        }
        List arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("_image");
        if (optJSONArray == null || optJSONArray.length() == 0) {
            cloudItemDetail.setmCloudImage(arrayList);
            return cloudItemDetail;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
            arrayList.add(new CloudImage(C0391n.m1600a(jSONObject2, "_id"), C0391n.m1600a(jSONObject2, "_preurl"), C0391n.m1600a(jSONObject2, "_url")));
        }
        cloudItemDetail.setmCloudImage(arrayList);
        return cloudItemDetail;
    }

    public Map<String, String> mo1757c() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("Content-Type", "application/x-www-form-urlencoded");
        hashMap.put(HttpHeaders.ACCEPT_ENCODING, "gzip");
        hashMap.put("User-Agent", "AMAP SDK Android Search 3.3.0");
        hashMap.put("X-INFO", av.m1227a(this.d, C0389h.m1586b(false), null, false));
        hashMap.put("platinfo", String.format("platform=Android&sdkversion=%s&product=%s", new Object[]{"3.3.0", DataBaseContants.TABLE_DELETE_NAME}));
        hashMap.put("logversion", "2.1");
        return hashMap;
    }

    protected void m6221a(CloudItem cloudItem, JSONObject jSONObject) {
        Iterator keys = jSONObject.keys();
        HashMap hashMap = new HashMap();
        if (keys != null) {
            while (keys.hasNext()) {
                Object next = keys.next();
                if (!(next == null || next.toString().startsWith("_"))) {
                    hashMap.put(next.toString(), jSONObject.optString(next.toString()));
                }
            }
            cloudItem.setCustomfield(hashMap);
        }
    }

    protected boolean mo3703d(String str) {
        if (str == null || str.equals("") || str.equals("[]")) {
            return true;
        }
        return false;
    }
}
