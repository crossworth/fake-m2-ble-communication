package com.umeng.socialize.net.base;

import com.umeng.socialize.net.utils.UClient;
import com.umeng.socialize.net.utils.UResponse;
import com.umeng.socialize.utils.Log;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class StatsClient extends UClient {
    public static final String BASE_URL = "https://stats.umsns.com/";
    private static final String TAG = "StatsClient";

    public SocializeReseponse execute(SocializeRequest socializeRequest) {
        socializeRequest.setBaseUrl(BASE_URL);
        return (SocializeReseponse) super.execute(socializeRequest, socializeRequest.mResponseClz);
    }

    protected <T extends UResponse> T createResponse(ResponseObj responseObj, Class<T> cls) {
        if (responseObj == null) {
            return null;
        }
        try {
            return (UResponse) cls.getConstructor(new Class[]{Integer.class, JSONObject.class}).newInstance(new Object[]{Integer.valueOf(responseObj.httpResponseCode), responseObj.jsonObject});
        } catch (Exception e) {
            Log.m4550e(TAG, "SecurityException", e);
            return null;
        } catch (Exception e2) {
            Log.m4550e(TAG, "NoSuchMethodException", e2);
            return null;
        } catch (Exception e22) {
            Log.m4550e(TAG, "IllegalArgumentException", e22);
            return null;
        } catch (Exception e222) {
            Log.m4550e(TAG, "InstantiationException", e222);
            return null;
        } catch (Exception e2222) {
            Log.m4550e(TAG, "IllegalAccessException", e2222);
            return null;
        } catch (Exception e22222) {
            Log.m4550e(TAG, "InvocationTargetException", e22222);
            return null;
        }
    }

    protected JSONObject parseResult(String str, String str2, InputStream inputStream) {
        Closeable wrapStream;
        IOException e;
        Throwable th;
        JSONException e2;
        try {
            wrapStream = wrapStream(str2, inputStream);
            try {
                String convertStreamToString = convertStreamToString(wrapStream);
                Log.m4546d(TAG, str + ";origin data:" + convertStreamToString);
                JSONObject jSONObject = new JSONObject(convertStreamToString);
                closeQuietly(wrapStream);
                return jSONObject;
            } catch (IOException e3) {
                e = e3;
                try {
                    Log.m4549e(TAG, e.getMessage());
                    closeQuietly(wrapStream);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    closeQuietly(wrapStream);
                    throw th;
                }
            } catch (JSONException e4) {
                e2 = e4;
                Log.m4549e(TAG, e2.getMessage());
                closeQuietly(wrapStream);
                return null;
            }
        } catch (IOException e5) {
            e = e5;
            wrapStream = null;
            Log.m4549e(TAG, e.getMessage());
            closeQuietly(wrapStream);
            return null;
        } catch (JSONException e6) {
            e2 = e6;
            wrapStream = null;
            Log.m4549e(TAG, e2.getMessage());
            closeQuietly(wrapStream);
            return null;
        } catch (Throwable th3) {
            th = th3;
            wrapStream = null;
            closeQuietly(wrapStream);
            throw th;
        }
    }
}
