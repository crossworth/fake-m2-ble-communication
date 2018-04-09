package com.zhuoyi.account.authenticator;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class NetworkUtilities {
    public static final String AUTH_URI = "https://samplesyncadapter2.appspot.com/auth";
    public static final String BASE_URL = "https://samplesyncadapter2.appspot.com";
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30000;
    public static final String PARAM_AUTH_TOKEN = "authtoken";
    public static final String PARAM_CONTACTS_DATA = "contacts";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_SYNC_STATE = "syncstate";
    public static final String PARAM_USERNAME = "username";
    public static final String SYNC_CONTACTS_URI = "https://samplesyncadapter2.appspot.com/sync";
    private static final String TAG = "NetworkUtilities";

    private NetworkUtilities() {
    }

    public static HttpClient getHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        ConnManagerParams.setTimeout(params, 30000);
        return httpClient;
    }

    public static String authenticate(String username, String password) {
        ArrayList<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        try {
            HttpEntity entity = new UrlEncodedFormEntity(params);
            Log.i(TAG, "Authenticating to: https://samplesyncadapter2.appspot.com/auth");
            HttpPost post = new HttpPost(AUTH_URI);
            post.addHeader(entity.getContentType());
            post.setEntity(entity);
            try {
                HttpResponse resp = getHttpClient().execute(post);
                String authToken = null;
                if (resp.getStatusLine().getStatusCode() == 200) {
                    InputStream istream;
                    if (resp.getEntity() != null) {
                        istream = resp.getEntity().getContent();
                    } else {
                        istream = null;
                    }
                    if (istream != null) {
                        authToken = new BufferedReader(new InputStreamReader(istream)).readLine().trim();
                    }
                }
                if (authToken == null || authToken.length() <= 0) {
                    Log.e(TAG, "Error authenticating" + resp.getStatusLine());
                    Log.v(TAG, "getAuthtoken completing");
                    return null;
                }
                Log.v(TAG, "Successful authentication");
                return authToken;
            } catch (IOException e) {
                Log.e(TAG, "IOException when getting authtoken", e);
                return null;
            } finally {
                Log.v(TAG, "getAuthtoken completing");
            }
        } catch (UnsupportedEncodingException e2) {
            throw new IllegalStateException(e2);
        }
    }
}
