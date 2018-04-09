package com.tencent.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.p010a.C0687a;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.tauth.IRequestListener;
import com.tencent.utils.Util.Statistic;
import com.zhuoyi.account.constant.UrlConstant;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.bluetooth.data.BMessage;
import java.io.ByteArrayOutputStream;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.InvalidPropertiesFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class HttpUtils {
    private static final String f2978a = HttpUtils.class.getName();

    /* compiled from: ProGuard */
    public static class HttpStatusException extends Exception {
        public static final String ERROR_INFO = "http status code error:";

        public HttpStatusException(String str) {
            super(str);
        }
    }

    /* compiled from: ProGuard */
    public static class MyX509TrustManager implements X509TrustManager {
        X509TrustManager f2977a;

        MyX509TrustManager() throws Exception {
            KeyStore instance;
            TrustManager[] trustManagers;
            try {
                instance = KeyStore.getInstance("JKS");
            } catch (Exception e) {
                instance = null;
            }
            TrustManager[] trustManagerArr = new TrustManager[0];
            if (instance != null) {
                instance.load(new FileInputStream("trustedCerts"), "passphrase".toCharArray());
                TrustManagerFactory instance2 = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
                instance2.init(instance);
                trustManagers = instance2.getTrustManagers();
            } else {
                TrustManagerFactory instance3 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                instance3.init((KeyStore) null);
                trustManagers = instance3.getTrustManagers();
            }
            for (int i = 0; i < trustManagers.length; i++) {
                if (trustManagers[i] instanceof X509TrustManager) {
                    this.f2977a = (X509TrustManager) trustManagers[i];
                    return;
                }
            }
            throw new Exception("Couldn't initialize");
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            try {
                this.f2977a.checkClientTrusted(x509CertificateArr, str);
            } catch (CertificateException e) {
            }
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            try {
                this.f2977a.checkServerTrusted(x509CertificateArr, str);
            } catch (CertificateException e) {
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.f2977a.getAcceptedIssuers();
        }
    }

    /* compiled from: ProGuard */
    public static class NetworkProxy {
        public final String host;
        public final int port;

        private NetworkProxy(String str, int i) {
            this.host = str;
            this.port = i;
        }
    }

    /* compiled from: ProGuard */
    public static class NetworkUnavailableException extends Exception {
        public static final String ERROR_INFO = "network unavailable";

        public NetworkUnavailableException(String str) {
            super(str);
        }
    }

    /* compiled from: ProGuard */
    public static class CustomSSLSocketFactory extends SSLSocketFactory {
        private SSLContext f5572a = SSLContext.getInstance(SSLSocketFactory.TLS);

        public CustomSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            MyX509TrustManager myX509TrustManager;
            super(keyStore);
            try {
                myX509TrustManager = new MyX509TrustManager();
            } catch (Exception e) {
                myX509TrustManager = null;
            }
            this.f5572a.init(null, new TrustManager[]{myX509TrustManager}, null);
        }

        public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException, UnknownHostException {
            return this.f5572a.getSocketFactory().createSocket(socket, str, i, z);
        }

        public Socket createSocket() throws IOException {
            return this.f5572a.getSocketFactory().createSocket();
        }
    }

    private HttpUtils() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject request(com.tencent.connect.auth.QQToken r19, android.content.Context r20, java.lang.String r21, android.os.Bundle r22, java.lang.String r23) throws java.io.IOException, org.json.JSONException, com.tencent.utils.HttpUtils.NetworkUnavailableException, com.tencent.utils.HttpUtils.HttpStatusException {
        /*
        r4 = "openSDK_LOG";
        r5 = "OpenApi request";
        com.tencent.p004a.p005a.C1711d.m4636a(r4, r5);
        r4 = r21.toLowerCase();
        r5 = "http";
        r4 = r4.startsWith(r5);
        if (r4 != 0) goto L_0x01db;
    L_0x0013:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = com.tencent.utils.ServerSetting.getInstance();
        r6 = "https://openmobile.qq.com/";
        r0 = r20;
        r5 = r5.getEnvUrl(r0, r6);
        r4 = r4.append(r5);
        r0 = r21;
        r4 = r4.append(r0);
        r4 = r4.toString();
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = com.tencent.utils.ServerSetting.getInstance();
        r7 = "https://openmobile.qq.com/";
        r0 = r20;
        r6 = r6.getEnvUrl(r0, r7);
        r5 = r5.append(r6);
        r0 = r21;
        r5 = r5.append(r0);
        r7 = r5.toString();
    L_0x0051:
        r0 = r20;
        r1 = r19;
        r2 = r21;
        m2818a(r0, r1, r2);
        r10 = 0;
        r8 = android.os.SystemClock.elapsedRealtime();
        r6 = 0;
        r5 = r19.getAppId();
        r0 = r20;
        r5 = com.tencent.utils.OpenConfig.getInstance(r0, r5);
        r11 = "Common_HttpRetryCount";
        r5 = r5.getInt(r11);
        r11 = "OpenConfig_test";
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "config 1:Common_HttpRetryCount            config_value:";
        r12 = r12.append(r13);
        r12 = r12.append(r5);
        r13 = "   appid:";
        r12 = r12.append(r13);
        r13 = r19.getAppId();
        r12 = r12.append(r13);
        r13 = "     url:";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r12 = r12.toString();
        android.util.Log.d(r11, r12);
        if (r5 != 0) goto L_0x00a3;
    L_0x00a2:
        r5 = 3;
    L_0x00a3:
        r11 = "OpenConfig_test";
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "config 1:Common_HttpRetryCount            result_value:";
        r12 = r12.append(r13);
        r12 = r12.append(r5);
        r13 = "   appid:";
        r12 = r12.append(r13);
        r13 = r19.getAppId();
        r12 = r12.append(r13);
        r13 = "     url:";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r12 = r12.toString();
        android.util.Log.d(r11, r12);
    L_0x00d3:
        r6 = r6 + 1;
        r0 = r20;
        r1 = r23;
        r2 = r22;
        r12 = openUrl2(r0, r4, r1, r2);	 Catch:{ ConnectTimeoutException -> 0x01d5, SocketTimeoutException -> 0x01cf, HttpStatusException -> 0x0155, NetworkUnavailableException -> 0x0182, MalformedURLException -> 0x0187, IOException -> 0x019e, JSONException -> 0x01b8 }
        r11 = r12.response;	 Catch:{ ConnectTimeoutException -> 0x01d5, SocketTimeoutException -> 0x01cf, HttpStatusException -> 0x0155, NetworkUnavailableException -> 0x0182, MalformedURLException -> 0x0187, IOException -> 0x019e, JSONException -> 0x01b8 }
        r15 = com.tencent.utils.Util.parseJson(r11);	 Catch:{ ConnectTimeoutException -> 0x01d5, SocketTimeoutException -> 0x01cf, HttpStatusException -> 0x0155, NetworkUnavailableException -> 0x0182, MalformedURLException -> 0x0187, IOException -> 0x019e, JSONException -> 0x01b8 }
        r10 = "ret";
        r14 = r15.getInt(r10);	 Catch:{ JSONException -> 0x00fe, ConnectTimeoutException -> 0x0101, SocketTimeoutException -> 0x012f, HttpStatusException -> 0x0155, NetworkUnavailableException -> 0x0182, MalformedURLException -> 0x0187, IOException -> 0x019e }
    L_0x00eb:
        r10 = r12.reqSize;	 Catch:{ ConnectTimeoutException -> 0x0101, SocketTimeoutException -> 0x012f, HttpStatusException -> 0x0155, NetworkUnavailableException -> 0x0182, MalformedURLException -> 0x0187, IOException -> 0x019e, JSONException -> 0x01b8 }
        r12 = r12.rspSize;	 Catch:{ ConnectTimeoutException -> 0x0101, SocketTimeoutException -> 0x012f, HttpStatusException -> 0x0155, NetworkUnavailableException -> 0x0182, MalformedURLException -> 0x0187, IOException -> 0x019e, JSONException -> 0x01b8 }
        r4 = r15;
    L_0x00f0:
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        return r4;
    L_0x00fe:
        r10 = move-exception;
        r14 = -4;
        goto L_0x00eb;
    L_0x0101:
        r10 = move-exception;
        r16 = r10;
    L_0x0104:
        r16.printStackTrace();
        r14 = -7;
        r10 = 0;
        r12 = 0;
        if (r6 >= r5) goto L_0x0121;
    L_0x010e:
        r8 = android.os.SystemClock.elapsedRealtime();
        r17 = r10;
        r11 = r14;
        r10 = r15;
        r14 = r17;
    L_0x0118:
        if (r6 < r5) goto L_0x00d3;
    L_0x011a:
        r4 = r10;
        r17 = r11;
        r10 = r14;
        r14 = r17;
        goto L_0x00f0;
    L_0x0121:
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r16;
    L_0x012f:
        r10 = move-exception;
        r16 = r10;
    L_0x0132:
        r16.printStackTrace();
        r14 = -8;
        r10 = 0;
        r12 = 0;
        if (r6 >= r5) goto L_0x0147;
    L_0x013c:
        r8 = android.os.SystemClock.elapsedRealtime();
        r17 = r10;
        r11 = r14;
        r10 = r15;
        r14 = r17;
        goto L_0x0118;
    L_0x0147:
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r16;
    L_0x0155:
        r4 = move-exception;
        r4.printStackTrace();
        r5 = r4.getMessage();
        r6 = "http status code error:";
        r10 = "";
        r5 = r5.replace(r6, r10);	 Catch:{ Exception -> 0x017b }
        r14 = java.lang.Integer.parseInt(r5);	 Catch:{ Exception -> 0x017b }
    L_0x0169:
        r10 = 0;
        r12 = 0;
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r4;
    L_0x017b:
        r5 = move-exception;
        r5.printStackTrace();
        r14 = -9;
        goto L_0x0169;
    L_0x0182:
        r4 = move-exception;
        r4.printStackTrace();
        throw r4;
    L_0x0187:
        r4 = move-exception;
        r4.printStackTrace();
        r14 = -3;
        r10 = 0;
        r12 = 0;
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r4;
    L_0x019e:
        r4 = move-exception;
        r4.printStackTrace();
        r14 = m2816a(r4);
        r10 = 0;
        r12 = 0;
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r4;
    L_0x01b8:
        r4 = move-exception;
        r4.printStackTrace();
        r14 = -4;
        r10 = 0;
        r12 = 0;
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r4;
    L_0x01cf:
        r11 = move-exception;
        r16 = r11;
        r15 = r10;
        goto L_0x0132;
    L_0x01d5:
        r11 = move-exception;
        r16 = r11;
        r15 = r10;
        goto L_0x0104;
    L_0x01db:
        r7 = r21;
        r4 = r21;
        goto L_0x0051;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.utils.HttpUtils.request(com.tencent.connect.auth.QQToken, android.content.Context, java.lang.String, android.os.Bundle, java.lang.String):org.json.JSONObject");
    }

    public static void requestAsync(QQToken qQToken, Context context, String str, Bundle bundle, String str2, IRequestListener iRequestListener) {
        C1711d.m4636a("openSDK_LOG", "OpenApi requestAsync");
        final QQToken qQToken2 = qQToken;
        final Context context2 = context;
        final String str3 = str;
        final Bundle bundle2 = bundle;
        final String str4 = str2;
        final IRequestListener iRequestListener2 = iRequestListener;
        new Thread() {
            public void run() {
                try {
                    JSONObject request = HttpUtils.request(qQToken2, context2, str3, bundle2, str4);
                    if (iRequestListener2 != null) {
                        iRequestListener2.onComplete(request);
                        C1711d.m4638b("openSDK_LOG", "OpenApi onComplete");
                    }
                } catch (Throwable e) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onMalformedURLException(e);
                        C1711d.m4637a("openSDK_LOG", "OpenApi requestAsync MalformedURLException", e);
                    }
                } catch (Throwable e2) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onConnectTimeoutException(e2);
                        C1711d.m4637a("openSDK_LOG", "OpenApi requestAsync onConnectTimeoutException", e2);
                    }
                } catch (Throwable e22) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onSocketTimeoutException(e22);
                        C1711d.m4637a("openSDK_LOG", "OpenApi requestAsync onSocketTimeoutException", e22);
                    }
                } catch (Throwable e222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onNetworkUnavailableException(e222);
                        C1711d.m4637a("openSDK_LOG", "OpenApi requestAsync onNetworkUnavailableException", e222);
                    }
                } catch (Throwable e2222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onHttpStatusException(e2222);
                        C1711d.m4637a("openSDK_LOG", "OpenApi requestAsync onHttpStatusException", e2222);
                    }
                } catch (Throwable e22222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onIOException(e22222);
                        C1711d.m4637a("openSDK_LOG", "OpenApi requestAsync IOException", e22222);
                    }
                } catch (Throwable e222222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onJSONException(e222222);
                        C1711d.m4637a("openSDK_LOG", "OpenApi requestAsync JSONException", e222222);
                    }
                } catch (Throwable e2222222) {
                    if (iRequestListener2 != null) {
                        iRequestListener2.onUnknowException(e2222222);
                        C1711d.m4637a("openSDK_LOG", "OpenApi requestAsync onUnknowException", e2222222);
                    }
                }
            }
        }.start();
    }

    private static void m2818a(Context context, QQToken qQToken, String str) {
        if (str.indexOf("add_share") > -1 || str.indexOf("upload_pic") > -1 || str.indexOf("add_topic") > -1 || str.indexOf("set_user_face") > -1 || str.indexOf("add_t") > -1 || str.indexOf("add_pic_t") > -1 || str.indexOf("add_pic_url") > -1 || str.indexOf("add_video") > -1) {
            C0687a.m2306a(context, qQToken, "requireApi", str);
        }
    }

    private static int m2816a(IOException iOException) {
        if (iOException instanceof CharConversionException) {
            return -20;
        }
        if (iOException instanceof MalformedInputException) {
            return -21;
        }
        if (iOException instanceof UnmappableCharacterException) {
            return -22;
        }
        if (iOException instanceof HttpResponseException) {
            return -23;
        }
        if (iOException instanceof ClosedChannelException) {
            return -24;
        }
        if (iOException instanceof ConnectionClosedException) {
            return -25;
        }
        if (iOException instanceof EOFException) {
            return -26;
        }
        if (iOException instanceof FileLockInterruptionException) {
            return -27;
        }
        if (iOException instanceof FileNotFoundException) {
            return -28;
        }
        if (iOException instanceof HttpRetryException) {
            return -29;
        }
        if (iOException instanceof ConnectTimeoutException) {
            return -7;
        }
        if (iOException instanceof SocketTimeoutException) {
            return -8;
        }
        if (iOException instanceof InvalidPropertiesFormatException) {
            return -30;
        }
        if (iOException instanceof MalformedChunkCodingException) {
            return -31;
        }
        if (iOException instanceof MalformedURLException) {
            return -3;
        }
        if (iOException instanceof NoHttpResponseException) {
            return -32;
        }
        if (iOException instanceof InvalidClassException) {
            return -33;
        }
        if (iOException instanceof InvalidObjectException) {
            return -34;
        }
        if (iOException instanceof NotActiveException) {
            return -35;
        }
        if (iOException instanceof NotSerializableException) {
            return -36;
        }
        if (iOException instanceof OptionalDataException) {
            return -37;
        }
        if (iOException instanceof StreamCorruptedException) {
            return -38;
        }
        if (iOException instanceof WriteAbortedException) {
            return -39;
        }
        if (iOException instanceof ProtocolException) {
            return -40;
        }
        if (iOException instanceof SSLHandshakeException) {
            return -41;
        }
        if (iOException instanceof SSLKeyException) {
            return -42;
        }
        if (iOException instanceof SSLPeerUnverifiedException) {
            return -43;
        }
        if (iOException instanceof SSLProtocolException) {
            return -44;
        }
        if (iOException instanceof BindException) {
            return -45;
        }
        if (iOException instanceof ConnectException) {
            return -46;
        }
        if (iOException instanceof NoRouteToHostException) {
            return -47;
        }
        if (iOException instanceof PortUnreachableException) {
            return -48;
        }
        if (iOException instanceof SyncFailedException) {
            return -49;
        }
        if (iOException instanceof UTFDataFormatException) {
            return -50;
        }
        if (iOException instanceof UnknownHostException) {
            return -51;
        }
        if (iOException instanceof UnknownServiceException) {
            return -52;
        }
        if (iOException instanceof UnsupportedEncodingException) {
            return -53;
        }
        if (iOException instanceof ZipException) {
            return -54;
        }
        return -2;
    }

    public static Statistic openUrl2(Context context, String str, String str2, Bundle bundle) throws MalformedURLException, IOException, NetworkUnavailableException, HttpStatusException {
        Bundle bundle2;
        HttpUriRequest httpUriRequest;
        int i;
        int size;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                    throw new NetworkUnavailableException(NetworkUnavailableException.ERROR_INFO);
                }
            }
        }
        if (bundle != null) {
            bundle2 = new Bundle(bundle);
        } else {
            bundle2 = new Bundle();
        }
        String str3 = "";
        str3 = bundle2.getString("appid_for_getting_config");
        bundle2.remove("appid_for_getting_config");
        HttpClient httpClient = getHttpClient(context, str3, str);
        int length;
        if (str2.equals("GET")) {
            String encodeUrl = encodeUrl(bundle2);
            length = 0 + encodeUrl.length();
            C1711d.m4638b(f2978a, "-->openUrl2 before url =" + str);
            if (str.indexOf("?") == -1) {
                str3 = str + "?";
            } else {
                str3 = str + "&";
            }
            C1711d.m4638b(f2978a, "-->openUrl2 encodedParam =" + encodeUrl + " -- url = " + str3);
            HttpUriRequest httpGet = new HttpGet(str3 + encodeUrl);
            httpGet.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
            int i2 = length;
            httpUriRequest = httpGet;
            i = i2;
        } else if (str2.equals("POST")) {
            Object obj;
            HttpPost httpPost = new HttpPost(str);
            httpPost.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
            Bundle bundle3 = new Bundle();
            for (String str32 : bundle2.keySet()) {
                obj = bundle2.get(str32);
                if (obj instanceof byte[]) {
                    bundle3.putByteArray(str32, (byte[]) obj);
                }
            }
            if (!bundle2.containsKey("method")) {
                bundle2.putString("method", str2);
            }
            httpPost.setHeader("Content-Type", "multipart/form-data; boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
            httpPost.setHeader("Connection", HTTP.CONN_KEEP_ALIVE);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
            byteArrayOutputStream.write(encodePostBody(bundle2, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f").getBytes());
            if (!bundle3.isEmpty()) {
                size = bundle3.size();
                byteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
                length = -1;
                for (String str322 : bundle3.keySet()) {
                    length++;
                    byteArrayOutputStream.write(("Content-Disposition: form-data; name=\"" + str322 + "\"; filename=\"" + str322 + "\"" + BMessage.CRLF).getBytes());
                    byteArrayOutputStream.write("Content-Type: content/unknown\r\n\r\n".getBytes());
                    byte[] byteArray = bundle3.getByteArray(str322);
                    if (byteArray != null) {
                        byteArrayOutputStream.write(byteArray);
                    }
                    if (length < size - 1) {
                        byteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
                    }
                }
            }
            byteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f--\r\n".getBytes());
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            i = toByteArray.length + 0;
            byteArrayOutputStream.close();
            httpPost.setEntity(new ByteArrayEntity(toByteArray));
            obj = httpPost;
        } else {
            httpUriRequest = null;
            i = 0;
        }
        HttpResponse execute = httpClient.execute(httpUriRequest);
        size = execute.getStatusLine().getStatusCode();
        if (size == 200) {
            return new Statistic(m2817a(execute), i);
        }
        throw new HttpStatusException(HttpStatusException.ERROR_INFO + size);
    }

    private static String m2817a(HttpResponse httpResponse) throws IllegalStateException, IOException {
        InputStream inputStream;
        String str = "";
        InputStream content = httpResponse.getEntity().getContent();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Header firstHeader = httpResponse.getFirstHeader("Content-Encoding");
        if (firstHeader == null || firstHeader.getValue().toLowerCase().indexOf("gzip") <= -1) {
            inputStream = content;
        } else {
            inputStream = new GZIPInputStream(content);
        }
        byte[] bArr = new byte[512];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return new String(byteArrayOutputStream.toByteArray());
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static HttpClient getHttpClient(Context context, String str, String str2) {
        OpenConfig instance;
        int i;
        int i2 = 0;
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
        if (VERSION.SDK_INT < 16) {
            try {
                KeyStore instance2 = KeyStore.getInstance(KeyStore.getDefaultType());
                instance2.load(null, null);
                SocketFactory customSSLSocketFactory = new CustomSSLSocketFactory(instance2);
                customSSLSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                schemeRegistry.register(new Scheme("https", customSSLSocketFactory, 443));
            } catch (Exception e) {
                schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            }
        } else {
            schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        }
        HttpParams basicHttpParams = new BasicHttpParams();
        if (context != null) {
            instance = OpenConfig.getInstance(context, str);
        } else {
            instance = null;
        }
        if (instance != null) {
            i = instance.getInt("Common_HttpConnectionTimeout");
            i2 = instance.getInt("Common_SocketConnectionTimeout");
        } else {
            i = 0;
        }
        if (i == 0) {
            i = UrlConstant.SEDN_MMS_DELAY;
        }
        if (i2 == 0) {
            i2 = 30000;
        }
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, i);
        HttpConnectionParams.setSoTimeout(basicHttpParams, i2);
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
        HttpProtocolParams.setUserAgent(basicHttpParams, "AndroidSDK_" + VERSION.SDK + "_" + Build.DEVICE + "_" + VERSION.RELEASE);
        HttpClient defaultHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        NetworkProxy proxy = getProxy(context);
        if (proxy != null) {
            defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxy.host, proxy.port));
        }
        return defaultHttpClient;
    }

    public static String encodeUrl(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (String str : bundle.keySet()) {
            Object obj2 = bundle.get(str);
            if ((obj2 instanceof String) || (obj2 instanceof String[])) {
                Object obj3;
                if (obj2 instanceof String[]) {
                    if (obj != null) {
                        obj = null;
                    } else {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(URLEncoder.encode(str) + "=");
                    String[] stringArray = bundle.getStringArray(str);
                    for (int i = 0; i < stringArray.length; i++) {
                        if (i == 0) {
                            stringBuilder.append(URLEncoder.encode(stringArray[i]));
                        } else {
                            stringBuilder.append(URLEncoder.encode(SeparatorConstants.SEPARATOR_ADS_ID + stringArray[i]));
                        }
                    }
                    obj3 = obj;
                } else {
                    if (obj != null) {
                        obj = null;
                    } else {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(bundle.getString(str)));
                    obj3 = obj;
                }
                obj = obj3;
            }
        }
        return stringBuilder.toString();
    }

    public static String encodePostBody(Bundle bundle, String str) {
        if (bundle == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int size = bundle.size();
        int i = -1;
        for (String str2 : bundle.keySet()) {
            int i2 = i + 1;
            Object obj = bundle.get(str2);
            if (obj instanceof String) {
                stringBuilder.append("Content-Disposition: form-data; name=\"" + str2 + "\"" + BMessage.CRLF + BMessage.CRLF + ((String) obj));
                if (i2 < size - 1) {
                    stringBuilder.append("\r\n--" + str + BMessage.CRLF);
                }
                i = i2;
            } else {
                i = i2;
            }
        }
        return stringBuilder.toString();
    }

    public static NetworkProxy getProxy(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return null;
        }
        if (activeNetworkInfo.getType() == 0) {
            Object b = m2819b(context);
            int a = m2815a(context);
            if (!TextUtils.isEmpty(b) && a >= 0) {
                return new NetworkProxy(b, a);
            }
        }
        return null;
    }

    private static int m2815a(Context context) {
        int i = -1;
        if (VERSION.SDK_INT >= 11) {
            Object property = System.getProperty("http.proxyPort");
            if (TextUtils.isEmpty(property)) {
                return i;
            }
            try {
                return Integer.parseInt(property);
            } catch (NumberFormatException e) {
                return i;
            }
        } else if (context == null) {
            return Proxy.getDefaultPort();
        } else {
            i = Proxy.getPort(context);
            if (i < 0) {
                return Proxy.getDefaultPort();
            }
            return i;
        }
    }

    private static String m2819b(Context context) {
        if (VERSION.SDK_INT >= 11) {
            return System.getProperty("http.proxyHost");
        }
        if (context == null) {
            return Proxy.getDefaultHost();
        }
        String host = Proxy.getHost(context);
        if (TextUtils.isEmpty(host)) {
            return Proxy.getDefaultHost();
        }
        return host;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject upload(com.tencent.connect.auth.QQToken r19, android.content.Context r20, java.lang.String r21, android.os.Bundle r22) throws java.io.IOException, org.json.JSONException, com.tencent.utils.HttpUtils.NetworkUnavailableException, com.tencent.utils.HttpUtils.HttpStatusException {
        /*
        r4 = r21.toLowerCase();
        r5 = "http";
        r4 = r4.startsWith(r5);
        if (r4 != 0) goto L_0x01d2;
    L_0x000c:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = com.tencent.utils.ServerSetting.getInstance();
        r6 = "https://openmobile.qq.com/";
        r0 = r20;
        r5 = r5.getEnvUrl(r0, r6);
        r4 = r4.append(r5);
        r0 = r21;
        r4 = r4.append(r0);
        r4 = r4.toString();
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = com.tencent.utils.ServerSetting.getInstance();
        r7 = "https://openmobile.qq.com/";
        r0 = r20;
        r6 = r6.getEnvUrl(r0, r7);
        r5 = r5.append(r6);
        r0 = r21;
        r5 = r5.append(r0);
        r7 = r5.toString();
    L_0x004a:
        r0 = r20;
        r1 = r19;
        r2 = r21;
        m2818a(r0, r1, r2);
        r10 = 0;
        r8 = android.os.SystemClock.elapsedRealtime();
        r6 = 0;
        r5 = r19.getAppId();
        r0 = r20;
        r5 = com.tencent.utils.OpenConfig.getInstance(r0, r5);
        r11 = "Common_HttpRetryCount";
        r5 = r5.getInt(r11);
        r11 = "OpenConfig_test";
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "config 1:Common_HttpRetryCount            config_value:";
        r12 = r12.append(r13);
        r12 = r12.append(r5);
        r13 = "   appid:";
        r12 = r12.append(r13);
        r13 = r19.getAppId();
        r12 = r12.append(r13);
        r13 = "     url:";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r12 = r12.toString();
        android.util.Log.d(r11, r12);
        if (r5 != 0) goto L_0x009c;
    L_0x009b:
        r5 = 3;
    L_0x009c:
        r11 = "OpenConfig_test";
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "config 1:Common_HttpRetryCount            result_value:";
        r12 = r12.append(r13);
        r12 = r12.append(r5);
        r13 = "   appid:";
        r12 = r12.append(r13);
        r13 = r19.getAppId();
        r12 = r12.append(r13);
        r13 = "     url:";
        r12 = r12.append(r13);
        r12 = r12.append(r7);
        r12 = r12.toString();
        android.util.Log.d(r11, r12);
    L_0x00cc:
        r6 = r6 + 1;
        r0 = r20;
        r1 = r22;
        r12 = com.tencent.utils.Util.upload(r0, r4, r1);	 Catch:{ ConnectTimeoutException -> 0x01cc, SocketTimeoutException -> 0x01c6, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0179, MalformedURLException -> 0x017e, IOException -> 0x0195, JSONException -> 0x01af }
        r11 = r12.response;	 Catch:{ ConnectTimeoutException -> 0x01cc, SocketTimeoutException -> 0x01c6, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0179, MalformedURLException -> 0x017e, IOException -> 0x0195, JSONException -> 0x01af }
        r15 = com.tencent.utils.Util.parseJson(r11);	 Catch:{ ConnectTimeoutException -> 0x01cc, SocketTimeoutException -> 0x01c6, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0179, MalformedURLException -> 0x017e, IOException -> 0x0195, JSONException -> 0x01af }
        r10 = "ret";
        r14 = r15.getInt(r10);	 Catch:{ JSONException -> 0x00f5, ConnectTimeoutException -> 0x00f8, SocketTimeoutException -> 0x0126, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0179, MalformedURLException -> 0x017e, IOException -> 0x0195 }
    L_0x00e2:
        r10 = r12.reqSize;	 Catch:{ ConnectTimeoutException -> 0x00f8, SocketTimeoutException -> 0x0126, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0179, MalformedURLException -> 0x017e, IOException -> 0x0195, JSONException -> 0x01af }
        r12 = r12.rspSize;	 Catch:{ ConnectTimeoutException -> 0x00f8, SocketTimeoutException -> 0x0126, HttpStatusException -> 0x014c, NetworkUnavailableException -> 0x0179, MalformedURLException -> 0x017e, IOException -> 0x0195, JSONException -> 0x01af }
        r4 = r15;
    L_0x00e7:
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        return r4;
    L_0x00f5:
        r10 = move-exception;
        r14 = -4;
        goto L_0x00e2;
    L_0x00f8:
        r10 = move-exception;
        r16 = r10;
    L_0x00fb:
        r16.printStackTrace();
        r14 = -7;
        r10 = 0;
        r12 = 0;
        if (r6 >= r5) goto L_0x0118;
    L_0x0105:
        r8 = android.os.SystemClock.elapsedRealtime();
        r17 = r10;
        r11 = r14;
        r10 = r15;
        r14 = r17;
    L_0x010f:
        if (r6 < r5) goto L_0x00cc;
    L_0x0111:
        r4 = r10;
        r17 = r11;
        r10 = r14;
        r14 = r17;
        goto L_0x00e7;
    L_0x0118:
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r16;
    L_0x0126:
        r10 = move-exception;
        r16 = r10;
    L_0x0129:
        r16.printStackTrace();
        r14 = -8;
        r10 = 0;
        r12 = 0;
        if (r6 >= r5) goto L_0x013e;
    L_0x0133:
        r8 = android.os.SystemClock.elapsedRealtime();
        r17 = r10;
        r11 = r14;
        r10 = r15;
        r14 = r17;
        goto L_0x010f;
    L_0x013e:
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r16;
    L_0x014c:
        r4 = move-exception;
        r4.printStackTrace();
        r5 = r4.getMessage();
        r6 = "http status code error:";
        r10 = "";
        r5 = r5.replace(r6, r10);	 Catch:{ Exception -> 0x0172 }
        r14 = java.lang.Integer.parseInt(r5);	 Catch:{ Exception -> 0x0172 }
    L_0x0160:
        r10 = 0;
        r12 = 0;
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r4;
    L_0x0172:
        r5 = move-exception;
        r5.printStackTrace();
        r14 = -9;
        goto L_0x0160;
    L_0x0179:
        r4 = move-exception;
        r4.printStackTrace();
        throw r4;
    L_0x017e:
        r4 = move-exception;
        r4.printStackTrace();
        r14 = -3;
        r10 = 0;
        r12 = 0;
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r4;
    L_0x0195:
        r4 = move-exception;
        r4.printStackTrace();
        r14 = m2816a(r4);
        r10 = 0;
        r12 = 0;
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r4;
    L_0x01af:
        r4 = move-exception;
        r4.printStackTrace();
        r14 = -4;
        r10 = 0;
        r12 = 0;
        r5 = com.tencent.open.p019a.C0799b.m2544a();
        r15 = r19.getAppId();
        r6 = r20;
        r5.m2556a(r6, r7, r8, r10, r12, r14, r15);
        throw r4;
    L_0x01c6:
        r11 = move-exception;
        r16 = r11;
        r15 = r10;
        goto L_0x0129;
    L_0x01cc:
        r11 = move-exception;
        r16 = r11;
        r15 = r10;
        goto L_0x00fb;
    L_0x01d2:
        r7 = r21;
        r4 = r21;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.utils.HttpUtils.upload(com.tencent.connect.auth.QQToken, android.content.Context, java.lang.String, android.os.Bundle):org.json.JSONObject");
    }
}
