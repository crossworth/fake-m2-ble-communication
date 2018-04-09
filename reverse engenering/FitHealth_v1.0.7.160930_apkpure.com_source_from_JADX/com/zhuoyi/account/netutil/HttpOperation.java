package com.zhuoyi.account.netutil;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpOperation {
    private static final String AUTH_TOKEN_KEY = "microohclient_authtoken_key";
    private static final String AUTH_TOKEN_VALUE = "21232f297a57a5a743894a0e";
    private static final String JSON_KEY_NAME = "microohclient_requestkey";
    private static HttpClient httpClient = null;

    public static String getRequest(String url) throws Exception {
        httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.valueOf(60000));
        try {
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                return result;
            }
            httpClient.getConnectionManager().shutdown();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public static String postRequest(String url, Map<String, String> rawParams) {
        Log.i("chenxin", "postRequest url=" + url);
        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> params = new ArrayList();
            for (String key : rawParams.keySet()) {
                params.add(new BasicNameValuePair(key, (String) rawParams.get(key)));
                Log.i("chenxin", "params = " + key + ":" + ((String) rawParams.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.valueOf(10000));
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Integer.valueOf(10000));
            HttpResponse httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                Log.i("chenxin", "postRequest result=" + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postRequestJSONFormat(String url, String requestJSONStr) {
        HashMap<String, String> mapParams = new HashMap();
        mapParams.put(JSON_KEY_NAME, requestJSONStr);
        mapParams.put(AUTH_TOKEN_KEY, AUTH_TOKEN_VALUE);
        return postRequest(url, mapParams);
    }
}
