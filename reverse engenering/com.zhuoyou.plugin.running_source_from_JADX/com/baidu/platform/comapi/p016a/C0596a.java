package com.baidu.platform.comapi.p016a;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.baidu.mapapi.http.AsyncHttpClient;
import com.baidu.mapapi.http.HttpClient;
import com.baidu.mapapi.http.HttpClient.HttpStateError;
import com.baidu.platform.comjni.util.AppMD5;
import com.umeng.socialize.handler.TwitterPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0596a {
    Context f1882a;
    AsyncHttpClient f1883b;

    public interface C0592a<T> {
        void mo1826a(HttpStateError httpStateError);

        void mo1827a(T t);
    }

    public C0596a(Context context) {
        this.f1882a = context;
        this.f1883b = new AsyncHttpClient(context);
    }

    private C0598c m1843a(String str) {
        if (str == null || str.equals("")) {
            return new C0598c(C0599d.PANO_NOT_FOUND);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject("result");
            if (optJSONObject == null) {
                return new C0598c(C0599d.PANO_NOT_FOUND);
            }
            if (optJSONObject.optInt("error") != 0) {
                return new C0598c(C0599d.PANO_UID_ERROR);
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("content");
            if (optJSONArray == null) {
                return new C0598c(C0599d.PANO_NOT_FOUND);
            }
            C0598c c0598c = null;
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject2 = optJSONArray.optJSONObject(i).optJSONObject("poiinfo");
                if (optJSONObject2 != null) {
                    c0598c = new C0598c(C0599d.PANO_NO_ERROR);
                    c0598c.m1849a(optJSONObject2.optString("PID"));
                    c0598c.m1848a(optJSONObject2.optInt("hasstreet"));
                }
            }
            return c0598c;
        } catch (JSONException e) {
            e.printStackTrace();
            return new C0598c(C0599d.PANO_NOT_FOUND);
        }
    }

    private String m1844a(Builder builder) {
        Builder buildUpon = Uri.parse(builder.build().toString() + HttpClient.getPhoneInfo()).buildUpon();
        buildUpon.appendQueryParameter("sign", AppMD5.getSignMD5String(buildUpon.build().getEncodedQuery()));
        return buildUpon.build().toString();
    }

    private void m1845a(Builder builder, String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            builder.appendQueryParameter(str, str2);
        }
    }

    public void m1846a(String str, C0592a<C0598c> c0592a) {
        Builder builder = new Builder();
        builder.scheme("http");
        builder.encodedAuthority("api.map.baidu.com");
        builder.path("/sdkproxy/lbs_androidsdk/pano/v1/");
        m1845a(builder, "qt", "poi");
        m1845a(builder, "uid", str);
        m1845a(builder, "action", "0");
        String authToken = HttpClient.getAuthToken();
        if (authToken == null) {
            c0592a.mo1827a(new C0598c(C0599d.PANO_NO_TOKEN));
            return;
        }
        m1845a(builder, TwitterPreferences.TOKEN, authToken);
        this.f1883b.get(m1844a(builder), new C0597b(this, c0592a));
    }
}
