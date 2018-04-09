package com.zhuoyou.plugin.selfupdate;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.stat.DeviceInfo;
import com.zhuoyi.system.promotion.util.PromConstants;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApkCheckSelfUpdateCodec implements DataCodec {
    public HashMap<String, Object> splitMySelfData(String result) {
        JSONException e;
        String headResult = "";
        String bodyResult = "";
        HashMap<String, Object> map = null;
        if (TextUtils.isEmpty(result)) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            headResult = jsonObject.getString("head");
            bodyResult = jsonObject.getString("body");
            JSONObject bodyJSONObject = new JSONObject(bodyResult);
            if (bodyJSONObject != null && bodyJSONObject.has("errorCode") && bodyJSONObject.getInt("errorCode") == 0) {
                Log.i("gchk", "bodyResult = " + bodyResult);
                if (bodyJSONObject.getInt("policy") == 3) {
                    return null;
                }
                HashMap<String, Object> map2 = new HashMap();
                try {
                    map2.put("title", bodyJSONObject.getString("title"));
                    map2.put("content", bodyJSONObject.getString("content"));
                    map2.put("policy", Integer.valueOf(bodyJSONObject.getInt("policy")));
                    map2.put("pName", bodyJSONObject.getString("pName"));
                    map2.put(DeviceInfo.TAG_VERSION, bodyJSONObject.getString(DeviceInfo.TAG_VERSION));
                    map2.put("fileUrl", bodyJSONObject.getString("fileUrl"));
                    map2.put(PromConstants.PROM_HTML5_INFO_MD5, bodyJSONObject.getString(PromConstants.PROM_HTML5_INFO_MD5));
                    map2.put("errorCode", Integer.valueOf(bodyJSONObject.getInt("errorCode")));
                    map = map2;
                } catch (JSONException e2) {
                    e = e2;
                    map = map2;
                    e.printStackTrace();
                    return null;
                }
            }
            return map;
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }
}
