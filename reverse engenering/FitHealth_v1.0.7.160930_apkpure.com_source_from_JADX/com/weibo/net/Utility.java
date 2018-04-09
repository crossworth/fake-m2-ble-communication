package com.weibo.net;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.umeng.socialize.editorpage.ShareActivity;
import com.zhuoyou.plugin.bluetooth.data.BMessage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    public static final String BOUNDARY = "7cd4a6d158c";
    public static final String END_MP_BOUNDARY = "--7cd4a6d158c--";
    public static final String HTTPMETHOD_DELETE = "DELETE";
    public static final String HTTPMETHOD_GET = "GET";
    public static final String HTTPMETHOD_POST = "POST";
    public static final String MP_BOUNDARY = "--7cd4a6d158c";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final int SET_CONNECTION_TIMEOUT = 50000;
    private static final int SET_SOCKET_TIMEOUT = 200000;
    private static HttpHeaderFactory mAuth;
    private static WeiboParameters mRequestHeader = new WeiboParameters();
    private static Token mToken = null;

    public static class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);

        class C10171 implements X509TrustManager {
            C10171() {
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);
            TrustManager tm = new C10171();
            this.sslContext.init(null, new TrustManager[]{tm}, null);
        }

        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return this.sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        public Socket createSocket() throws IOException {
            return this.sslContext.getSocketFactory().createSocket();
        }
    }

    public static void setTokenObject(Token token) {
        mToken = token;
    }

    public static void setAuthorization(HttpHeaderFactory auth) {
        mAuth = auth;
    }

    public static void setHeader(String httpMethod, HttpUriRequest request, WeiboParameters authParam, String url, Token token) throws WeiboException {
        if (!isBundleEmpty(mRequestHeader)) {
            for (int loc = 0; loc < mRequestHeader.size(); loc++) {
                String key = mRequestHeader.getKey(loc);
                request.setHeader(key, mRequestHeader.getValue(key));
            }
        }
        if (!(isBundleEmpty(authParam) || mAuth == null)) {
            String authHeader = mAuth.getWeiboAuthHeader(httpMethod, url, authParam, Weibo.getAppKey(), Weibo.getAppSecret(), token);
            if (authHeader != null) {
                request.setHeader("Authorization", authHeader);
            }
        }
        request.setHeader("User-Agent", new StringBuilder(String.valueOf(System.getProperties().getProperty("http.agent"))).append(" WeiboAndroidSDK").toString());
    }

    public static boolean isBundleEmpty(WeiboParameters bundle) {
        if (bundle == null || bundle.size() == 0) {
            return true;
        }
        return false;
    }

    public static void setRequestHeader(String key, String value) {
        mRequestHeader.add(key, value);
    }

    public static void setRequestHeader(WeiboParameters params) {
        mRequestHeader.addAll(params);
    }

    public static void clearRequestHeader() {
        mRequestHeader.clear();
    }

    public static String encodePostBody(Bundle parameters, String boundary) {
        if (parameters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String key : parameters.keySet()) {
            if (parameters.getByteArray(key) == null) {
                sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n" + parameters.getString(key));
                sb.append("\r\n--" + boundary + BMessage.CRLF);
            }
        }
        return sb.toString();
    }

    public static String encodeUrl(WeiboParameters parameters) {
        if (parameters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int loc = 0; loc < parameters.size(); loc++) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(new StringBuilder(String.valueOf(URLEncoder.encode(parameters.getKey(loc)))).append("=").append(URLEncoder.encode(parameters.getValue(loc))).toString());
        }
        return sb.toString();
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            for (String parameter : s.split("&")) {
                String[] v = parameter.split("=");
                params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
            }
        }
        return params;
    }

    public static Bundle parseUrl(String url) {
        try {
            URL u = new URL(url.replace("weiboconnect", HttpHost.DEFAULT_SCHEME_NAME));
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    public static UrlEncodedFormEntity getPostParamters(Bundle bundle) throws WeiboException {
        if (bundle == null || bundle.isEmpty()) {
            return null;
        }
        try {
            List<NameValuePair> form = new ArrayList();
            for (String key : bundle.keySet()) {
                form.add(new BasicNameValuePair(key, bundle.getString(key)));
            }
            return new UrlEncodedFormEntity(form, "UTF-8");
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    public static String openUrl(Context context, String url, String method, WeiboParameters params, Token token) throws WeiboException {
        String rlt = "";
        String file = "";
        for (int loc = 0; loc < params.size(); loc++) {
            String key = params.getKey(loc);
            if (key.equals(ShareActivity.KEY_PIC)) {
                file = params.getValue(key);
                params.remove(key);
            }
        }
        if (TextUtils.isEmpty(file)) {
            return openUrl(context, url, method, params, null, token);
        }
        return openUrl(context, url, method, params, file, token);
    }

    public static String openUrl(Context context, String url, String method, WeiboParameters params, String file, Token token) throws WeiboException {
        String result = "";
        try {
            HttpClient client = getNewHttpClient(context);
            HttpUriRequest request = null;
            if (method.equals("GET")) {
                url = new StringBuilder(String.valueOf(url)).append("?").append(encodeUrl(params)).toString();
                request = new HttpGet(url);
            } else if (method.equals("POST")) {
                HttpPost post = new HttpPost(url);
                ByteArrayOutputStream bos = new ByteArrayOutputStream(51200);
                if (TextUtils.isEmpty(file)) {
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    bos.write(encodeParameters(params).getBytes("UTF-8"));
                } else {
                    if (file.contains("content")) {
                        file = Uri.parse(file).getPath();
                    }
                    paramToUpload(bos, params);
                    post.setHeader("Content-Type", "multipart/form-data; boundary=7cd4a6d158c");
                    imageContentToUpload(bos, BitmapFactory.decodeFile(file));
                }
                byte[] data = bos.toByteArray();
                bos.close();
                post.setEntity(new ByteArrayEntity(data));
                Object request2 = post;
            } else if (method.equals("DELETE")) {
                HttpUriRequest httpDelete = new HttpDelete(url);
            }
            setHeader(method, request, params, url, token);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                return read(response);
            }
            String str = null;
            int errCode = 0;
            try {
                JSONObject json = new JSONObject(read(response));
                str = json.getString("error");
                errCode = json.getInt("error_code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            throw new WeiboException(String.format(str, new Object[0]), errCode);
        } catch (Exception e2) {
            throw new WeiboException(e2);
        }
    }

    public static HttpClient getNewHttpClient(Context context) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "UTF-8");
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            HttpConnectionParams.setConnectionTimeout(params, SET_CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, SET_SOCKET_TIMEOUT);
            HttpClient client = new DefaultHttpClient(ccm, params);
            if (((WifiManager) context.getSystemService("wifi")).isWifiEnabled()) {
                return client;
            }
            Cursor mCursor = context.getContentResolver().query(Uri.parse("content://telephony/carriers/preferapn"), null, null, null, null);
            if (mCursor == null || !mCursor.moveToFirst()) {
                return client;
            }
            String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
            if (proxyStr != null && proxyStr.trim().length() > 0) {
                client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxyStr, 80));
            }
            mCursor.close();
            return client;
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    public static HttpClient getHttpClient(Context context) {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, SET_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParameters, SET_SOCKET_TIMEOUT);
        HttpClient client = new DefaultHttpClient(httpParameters);
        if (!((WifiManager) context.getSystemService("wifi")).isWifiEnabled()) {
            Cursor mCursor = context.getContentResolver().query(Uri.parse("content://telephony/carriers/preferapn"), null, null, null, null);
            if (mCursor != null && mCursor.moveToFirst()) {
                String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
                if (proxyStr != null && proxyStr.trim().length() > 0) {
                    client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxyStr, 80));
                }
                mCursor.close();
            }
        }
        return client;
    }

    private static void imageContentToUpload(OutputStream out, Bitmap imgpath) throws WeiboException {
        StringBuilder temp = new StringBuilder();
        temp.append(MP_BOUNDARY).append(BMessage.CRLF);
        temp.append("Content-Disposition: form-data; name=\"pic\"; filename=\"").append("news_image").append("\"\r\n");
        temp.append("Content-Type: ").append("image/png").append("\r\n\r\n");
        BufferedInputStream bis = null;
        try {
            out.write(temp.toString().getBytes());
            imgpath.compress(CompressFormat.PNG, 75, out);
            out.write(BMessage.CRLF.getBytes());
            out.write("\r\n--7cd4a6d158c--".getBytes());
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    throw new WeiboException(e);
                }
            }
        } catch (Exception e2) {
            throw new WeiboException(e2);
        } catch (Throwable th) {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e22) {
                    throw new WeiboException(e22);
                }
            }
        }
    }

    private static void paramToUpload(OutputStream baos, WeiboParameters params) throws WeiboException {
        String key = "";
        int loc = 0;
        while (loc < params.size()) {
            key = params.getKey(loc);
            StringBuilder temp = new StringBuilder(10);
            temp.setLength(0);
            temp.append(MP_BOUNDARY).append(BMessage.CRLF);
            temp.append("content-disposition: form-data; name=\"").append(key).append("\"\r\n\r\n");
            temp.append(params.getValue(key)).append(BMessage.CRLF);
            try {
                baos.write(temp.toString().getBytes());
                loc++;
            } catch (Exception e) {
                throw new WeiboException(e);
            }
        }
    }

    private static String read(HttpResponse response) throws WeiboException {
        String result = "";
        try {
            InputStream inputStream = response.getEntity().getContent();
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            Header header = response.getFirstHeader("Content-Encoding");
            if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
                inputStream = new GZIPInputStream(inputStream);
            }
            byte[] sBuffer = new byte[512];
            while (true) {
                int readBytes = inputStream.read(sBuffer);
                if (readBytes == -1) {
                    return new String(content.toByteArray());
                }
                content.write(sBuffer, 0, readBytes);
            }
        } catch (Exception e) {
            throw new WeiboException(e);
        } catch (Exception e2) {
            throw new WeiboException(e2);
        }
    }

    private static String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    public static void clearCookies(Context context) {
        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
        CookieManager.getInstance().removeAllCookie();
    }

    public static void showAlert(Context context, String title, String text) {
        Builder alertBuilder = new Builder(context);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(text);
        alertBuilder.create().show();
    }

    public static String encodeParameters(WeiboParameters httpParams) {
        if (httpParams == null || isBundleEmpty(httpParams)) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        int j = 0;
        for (int loc = 0; loc < httpParams.size(); loc++) {
            String key = httpParams.getKey(loc);
            if (j != 0) {
                buf.append("&");
            }
            try {
                buf.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(httpParams.getValue(key), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
            j++;
        }
        return buf.toString();
    }

    public static char[] base64Encode(byte[] data) {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
        char[] out = new char[(((data.length + 2) / 3) * 4)];
        int i = 0;
        int index = 0;
        while (i < data.length) {
            int i2;
            boolean quad = false;
            boolean trip = false;
            int val = (data[i] & 255) << 8;
            if (i + 1 < data.length) {
                val |= data[i + 1] & 255;
                trip = true;
            }
            val <<= 8;
            if (i + 2 < data.length) {
                val |= data[i + 2] & 255;
                quad = true;
            }
            out[index + 3] = alphabet[quad ? val & 63 : 64];
            val >>= 6;
            int i3 = index + 2;
            if (trip) {
                i2 = val & 63;
            } else {
                i2 = 64;
            }
            out[i3] = alphabet[i2];
            val >>= 6;
            out[index + 1] = alphabet[val & 63];
            out[index + 0] = alphabet[(val >> 6) & 63];
            i += 3;
            index += 4;
        }
        return out;
    }
}
