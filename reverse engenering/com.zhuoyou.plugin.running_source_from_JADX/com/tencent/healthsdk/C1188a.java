package com.tencent.healthsdk;

import android.content.Context;
import android.text.TextUtils;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.tencent.connect.common.Constants;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: QQHealthHttpUtils */
class C1188a {
    static final String f3701a = "QQHealthHttpUtils";
    static ExecutorService f3702b = Executors.newFixedThreadPool(3);
    static String f3703c = "qlDFDfnbma!@23DKEd[";
    static final String f3704d = "https://openmobile.qq.com/v3/health/report_health_data";
    static DateFormat f3705e = new SimpleDateFormat(Tools.BIRTH_FORMAT);
    private static final char[] f3706f = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* compiled from: QQHealthHttpUtils */
    static class C1187a {
        String f3695a;
        String f3696b;
        String f3697c;
        String f3698d;
        int f3699e;
        String f3700f;

        C1187a() {
        }

        boolean m3477a() {
            boolean z = (TextUtils.isEmpty(this.f3695a) || TextUtils.isEmpty(this.f3696b) || TextUtils.isEmpty(this.f3697c) || TextUtils.isEmpty(this.f3698d) || TextUtils.isEmpty(this.f3700f)) ? false : true;
            if (!z) {
                return false;
            }
            try {
                JSONArray jSONArray = new JSONArray(this.f3700f);
                if (jSONArray == null || jSONArray.length() == 0) {
                    return false;
                }
                for (int i = 0; i < jSONArray.length(); i++) {
                    if (!jSONArray.getJSONObject(i).has("type")) {
                        return false;
                    }
                }
                return z;
            } catch (Throwable e) {
                QQHealthManager.m3473a(C1188a.f3701a, "Health data is wrong", e);
                return false;
            }
        }
    }

    C1188a() {
    }

    static void m3480a(Context context, QQHealthCallback qQHealthCallback) {
        f3702b.submit(new C1189b(context, qQHealthCallback));
    }

    static C1187a m3478a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        QQHealthManager.m3472a(f3701a, "health data: \n" + str);
        C1187a c1187a = new C1187a();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("access_token")) {
                c1187a.f3695a = jSONObject.getString("access_token");
            }
            if (jSONObject.has("oauth_consumer_key")) {
                c1187a.f3696b = jSONObject.getString("oauth_consumer_key");
            }
            if (jSONObject.has("openid")) {
                c1187a.f3697c = jSONObject.getString("openid");
            }
            if (jSONObject.has(Constants.PARAM_PLATFORM_ID)) {
                c1187a.f3698d = jSONObject.getString(Constants.PARAM_PLATFORM_ID);
            }
            if (jSONObject.has("data")) {
                c1187a.f3700f = jSONObject.getString("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!c1187a.m3477a()) {
            c1187a = null;
        }
        return c1187a;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean m3482a(com.tencent.healthsdk.C1188a.C1187a r9, com.tencent.healthsdk.QQHealthCallback r10) {
        /*
        r2 = 1;
        r1 = 0;
        r5 = new org.json.JSONObject;
        r5.<init>();
        r0 = "ret";
        r3 = -20001; // 0xffffffffffffb1df float:NaN double:NaN;
        r5.put(r0, r3);	 Catch:{ Exception -> 0x0069 }
        r0 = "msg";
        r3 = "unknown error";
        r5.put(r0, r3);	 Catch:{ Exception -> 0x0069 }
    L_0x0015:
        r0 = 2;
    L_0x0016:
        r4 = r0 + -1;
        if (r0 <= 0) goto L_0x00a8;
    L_0x001a:
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x012c }
        r3 = "https://openmobile.qq.com/v3/health/report_health_data";
        r0.<init>(r3);	 Catch:{ Exception -> 0x012c }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x012c }
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ Exception -> 0x012c }
        r3 = "TLS";
        r3 = javax.net.ssl.SSLContext.getInstance(r3);	 Catch:{ Exception -> 0x012c }
        r6 = 0;
        r7 = 0;
        r8 = new java.security.SecureRandom;	 Catch:{ Exception -> 0x012c }
        r8.<init>();	 Catch:{ Exception -> 0x012c }
        r3.init(r6, r7, r8);	 Catch:{ Exception -> 0x012c }
        r3 = r3.getSocketFactory();	 Catch:{ Exception -> 0x012c }
        r0.setSSLSocketFactory(r3);	 Catch:{ Exception -> 0x012c }
        r3 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0.setReadTimeout(r3);	 Catch:{ Exception -> 0x012c }
        r3 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0.setConnectTimeout(r3);	 Catch:{ Exception -> 0x012c }
        r3 = "POST";
        r0.setRequestMethod(r3);	 Catch:{ Exception -> 0x012c }
        r3 = 1;
        r0.setDoInput(r3);	 Catch:{ Exception -> 0x012c }
        r3 = r0.getOutputStream();	 Catch:{ Exception -> 0x012c }
        r6 = new java.io.BufferedWriter;	 Catch:{ Exception -> 0x012c }
        r7 = new java.io.OutputStreamWriter;	 Catch:{ Exception -> 0x012c }
        r8 = "UTF-8";
        r7.<init>(r3, r8);	 Catch:{ Exception -> 0x012c }
        r6.<init>(r7);	 Catch:{ Exception -> 0x012c }
        r7 = com.tencent.healthsdk.C1188a.m3485b(r9);	 Catch:{ Exception -> 0x012c }
        if (r7 != 0) goto L_0x006e;
    L_0x0067:
        r0 = r1;
    L_0x0068:
        return r0;
    L_0x0069:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0015;
    L_0x006e:
        r7 = com.tencent.healthsdk.C1188a.m3479a(r7);	 Catch:{ Exception -> 0x012c }
        r6.write(r7);	 Catch:{ Exception -> 0x012c }
        r6.flush();	 Catch:{ Exception -> 0x012c }
        r6.close();	 Catch:{ Exception -> 0x012c }
        r3.close();	 Catch:{ Exception -> 0x012c }
        r0.connect();	 Catch:{ Exception -> 0x012c }
        r3 = r0.getResponseCode();	 Catch:{ Exception -> 0x012c }
        r6 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r3 == r6) goto L_0x00ad;
    L_0x0089:
        r0 = "ret";
        r6 = -20002; // 0xffffffffffffb1de float:NaN double:NaN;
        r5.put(r0, r6);	 Catch:{ Exception -> 0x012c }
        r0 = "msg";
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x012c }
        r6.<init>();	 Catch:{ Exception -> 0x012c }
        r7 = "response code : ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x012c }
        r3 = r6.append(r3);	 Catch:{ Exception -> 0x012c }
        r3 = r3.toString();	 Catch:{ Exception -> 0x012c }
        r5.put(r0, r3);	 Catch:{ Exception -> 0x012c }
    L_0x00a8:
        r10.onComplete(r5);
        r0 = r1;
        goto L_0x0068;
    L_0x00ad:
        r3 = "";
        r0 = r0.getInputStream();	 Catch:{ Exception -> 0x012c }
        r6 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x012c }
        r7 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x012c }
        r7.<init>(r0);	 Catch:{ Exception -> 0x012c }
        r6.<init>(r7);	 Catch:{ Exception -> 0x012c }
        r0 = r3;
    L_0x00be:
        r3 = r6.readLine();	 Catch:{ Exception -> 0x012c }
        if (r3 == 0) goto L_0x00d6;
    L_0x00c4:
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x012c }
        r7.<init>();	 Catch:{ Exception -> 0x012c }
        r0 = r7.append(r0);	 Catch:{ Exception -> 0x012c }
        r0 = r0.append(r3);	 Catch:{ Exception -> 0x012c }
        r0 = r0.toString();	 Catch:{ Exception -> 0x012c }
        goto L_0x00be;
    L_0x00d6:
        r3 = "QQHealthHttpUtils";
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x012c }
        r6.<init>();	 Catch:{ Exception -> 0x012c }
        r7 = "upload result: ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x012c }
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x012c }
        r6 = r6.toString();	 Catch:{ Exception -> 0x012c }
        com.tencent.healthsdk.QQHealthManager.m3472a(r3, r6);	 Catch:{ Exception -> 0x012c }
        r3 = new org.json.JSONObject;	 Catch:{ Exception -> 0x012c }
        r3.<init>(r0);	 Catch:{ Exception -> 0x012c }
        r0 = -1;
        r6 = "";
        r6 = "ret";
        r6 = r3.has(r6);	 Catch:{ Exception -> 0x012c }
        if (r6 == 0) goto L_0x0109;
    L_0x00fe:
        r0 = "ret";
        r0 = r3.getInt(r0);	 Catch:{ Exception -> 0x012c }
        r6 = "ret";
        r5.put(r6, r0);	 Catch:{ Exception -> 0x012c }
    L_0x0109:
        r6 = "msg";
        r6 = r3.has(r6);	 Catch:{ Exception -> 0x012c }
        if (r6 == 0) goto L_0x011c;
    L_0x0111:
        r6 = "msg";
        r3 = r3.getString(r6);	 Catch:{ Exception -> 0x012c }
        r6 = "msg";
        r5.put(r6, r3);	 Catch:{ Exception -> 0x012c }
    L_0x011c:
        if (r0 != 0) goto L_0x0124;
    L_0x011e:
        r10.onComplete(r5);	 Catch:{ Exception -> 0x012c }
        r0 = r2;
        goto L_0x0068;
    L_0x0124:
        r6 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        java.lang.Thread.sleep(r6);	 Catch:{ Exception -> 0x012c }
        r0 = r4;
        goto L_0x0016;
    L_0x012c:
        r0 = move-exception;
        r0.printStackTrace();
        r3 = "msg";
        r0 = r0.toString();	 Catch:{ JSONException -> 0x013c }
        r5.put(r3, r0);	 Catch:{ JSONException -> 0x013c }
    L_0x0139:
        r0 = r4;
        goto L_0x0016;
    L_0x013c:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0139;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.healthsdk.a.a(com.tencent.healthsdk.a$a, com.tencent.healthsdk.QQHealthCallback):boolean");
    }

    static boolean m3481a(C1187a c1187a) {
        if (c1187a == null) {
            return false;
        }
        int i = 2;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return false;
            }
            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(f3704d).openConnection();
                SSLContext instance = SSLContext.getInstance("TLS");
                instance.init(null, null, new SecureRandom());
                httpsURLConnection.setSSLSocketFactory(instance.getSocketFactory());
                httpsURLConnection.setReadTimeout(m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT);
                httpsURLConnection.setConnectTimeout(m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT);
                httpsURLConnection.setRequestMethod(Constants.HTTP_POST);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                List b = C1188a.m3485b(c1187a);
                if (b == null) {
                    return false;
                }
                bufferedWriter.write(C1188a.m3479a(b));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpsURLConnection.connect();
                QQHealthManager.m3472a(f3701a, "response code: " + httpsURLConnection.getResponseCode());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String str = "";
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    str = str + readLine;
                }
                QQHealthManager.m3472a(f3701a, "upload result : " + str);
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ret") && jSONObject.getInt("ret") == 0) {
                    return true;
                }
                Thread.sleep(StatisticConfig.MIN_UPLOAD_INTERVAL);
                i = i2;
            } catch (Throwable e) {
                e.printStackTrace();
                QQHealthManager.m3473a(f3701a, "error", e);
                i = i2;
            }
        }
    }

    static List m3485b(C1187a c1187a) {
        List arrayList = new ArrayList();
        arrayList.add(new BasicNameValuePair("access_token", c1187a.f3695a));
        arrayList.add(new BasicNameValuePair("oauth_consumer_key", c1187a.f3696b));
        arrayList.add(new BasicNameValuePair("openid", c1187a.f3697c));
        arrayList.add(new BasicNameValuePair(Constants.PARAM_PLATFORM_ID, c1187a.f3698d));
        arrayList.add(new BasicNameValuePair("format", "json"));
        try {
            arrayList.add(new BasicNameValuePair("key", C1188a.m3484b(C1188a.m3483a((c1187a.f3700f + f3703c).getBytes("UTF-8")))));
            arrayList.add(new BasicNameValuePair("data", URLEncoder.encode(c1187a.f3700f, "UTF-8")));
            arrayList.add(new BasicNameValuePair("version", "1.1"));
            return arrayList;
        } catch (Throwable e) {
            e.printStackTrace();
            QQHealthManager.m3473a(f3701a, "error", e);
            return null;
        }
    }

    static byte[] m3483a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Exception e) {
            return null;
        }
    }

    static String m3484b(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            cArr[(i * 2) + 1] = f3706f[b & 15];
            cArr[(i * 2) + 0] = f3706f[((byte) (b >>> 4)) & 15];
        }
        return new String(cArr);
    }

    static String m3479a(List list) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (NameValuePair nameValuePair : list) {
            if (obj != null) {
                obj = null;
            } else {
                stringBuilder.append("&");
            }
            stringBuilder.append(URLEncoder.encode(nameValuePair.getName(), "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(nameValuePair.getValue(), "UTF-8"));
        }
        return stringBuilder.toString();
    }
}
