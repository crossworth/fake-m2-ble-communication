package com.droi.sdk.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.text.TextUtils;
import android.util.Pair;
import com.droi.sdk.push.data.C0987b;
import com.droi.sdk.push.utils.C1010e;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.push.utils.C1016k;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

class ad {
    static C1016k m2989a(C1016k c1016k) {
        LocalSocket localSocket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader2;
        LocalSocket localSocket2;
        BufferedWriter bufferedWriter2;
        Throwable th;
        if (c1016k == null) {
            return null;
        }
        try {
            localSocket = new LocalSocket();
            try {
                localSocket.setSoTimeout(5000);
                localSocket.connect(new LocalSocketAddress("com.droi.server.UNIX_DOMAIN_SOCKET_NAME"));
                bufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream(), "UTF-8"));
            } catch (Exception e) {
                bufferedWriter = null;
                bufferedReader2 = null;
                localSocket2 = localSocket;
                if (localSocket2 != null) {
                    try {
                        localSocket2.close();
                    } catch (Exception e2) {
                        C1012g.m3139b(e2);
                    }
                }
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (Exception e3) {
                        C1012g.m3139b(e3);
                    }
                }
                if (bufferedWriter == null) {
                    return null;
                }
                try {
                    bufferedWriter.close();
                    return null;
                } catch (Exception e4) {
                    C1012g.m3139b(e4);
                    return null;
                }
            } catch (Throwable th2) {
                bufferedWriter2 = null;
                bufferedReader = null;
                th = th2;
                if (localSocket != null) {
                    try {
                        localSocket.close();
                    } catch (Exception e42) {
                        C1012g.m3139b(e42);
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e422) {
                        C1012g.m3139b(e422);
                    }
                }
                if (bufferedWriter2 != null) {
                    try {
                        bufferedWriter2.close();
                    } catch (Exception e4222) {
                        C1012g.m3139b(e4222);
                    }
                }
                throw th;
            }
            try {
                bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream(), "UTF-8"));
                try {
                    bufferedWriter2.write(c1016k.m3176b() + "\n");
                    bufferedWriter2.write(c1016k.m3175a() + "\n");
                    bufferedWriter2.flush();
                    C1016k c1016k2 = new C1016k(bufferedReader.readLine(), bufferedReader.readLine());
                    if (localSocket != null) {
                        try {
                            localSocket.close();
                        } catch (Exception e5) {
                            C1012g.m3139b(e5);
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e52) {
                            C1012g.m3139b(e52);
                        }
                    }
                    if (bufferedWriter2 != null) {
                        try {
                            bufferedWriter2.close();
                        } catch (Exception e522) {
                            C1012g.m3139b(e522);
                        }
                    }
                    return c1016k2;
                } catch (Exception e6) {
                    bufferedWriter = bufferedWriter2;
                    bufferedReader2 = bufferedReader;
                    localSocket2 = localSocket;
                    if (localSocket2 != null) {
                        localSocket2.close();
                    }
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    if (bufferedWriter == null) {
                        return null;
                    }
                    bufferedWriter.close();
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    if (localSocket != null) {
                        localSocket.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (bufferedWriter2 != null) {
                        bufferedWriter2.close();
                    }
                    throw th;
                }
            } catch (Exception e7) {
                bufferedWriter = null;
                bufferedReader2 = bufferedReader;
                localSocket2 = localSocket;
                if (localSocket2 != null) {
                    localSocket2.close();
                }
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
                if (bufferedWriter == null) {
                    return null;
                }
                bufferedWriter.close();
                return null;
            } catch (Throwable th22) {
                bufferedWriter2 = null;
                th = th22;
                if (localSocket != null) {
                    localSocket.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter2 != null) {
                    bufferedWriter2.close();
                }
                throw th;
            }
        } catch (Exception e8) {
            bufferedWriter = null;
            bufferedReader2 = null;
            localSocket2 = null;
            if (localSocket2 != null) {
                localSocket2.close();
            }
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            if (bufferedWriter == null) {
                return null;
            }
            bufferedWriter.close();
            return null;
        } catch (Throwable th222) {
            bufferedWriter2 = null;
            bufferedReader = null;
            localSocket = null;
            th = th222;
            if (localSocket != null) {
                localSocket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter2 != null) {
                bufferedWriter2.close();
            }
            throw th;
        }
    }

    static Runnable m2990a(Context context, String str, String str2, String str3, String str4, String str5, C0994n c0994n) {
        if (context == null) {
            C1012g.m3140b("getRunnable: context is null");
            return null;
        } else if (!C1015j.m3168d(str2)) {
            C1012g.m3140b("getRunnable: appId invalid(" + str2 + ")");
            return null;
        } else if (!C1015j.m3168d(str5)) {
            C1012g.m3140b("getRunnable: deviceId invalid(" + str5 + ")");
            return null;
        } else if (C1015j.m3168d(str4)) {
            return new ae(c0994n, context, str, str3, str4, str2);
        } else {
            C1012g.m3140b("getRunnable: desKey invalid(" + str4 + ")");
            return null;
        }
    }

    static String m2991a(byte[] bArr, String str) {
        Object a = C1015j.m3159a(str, bArr);
        if (a == null) {
            return null;
        }
        String str2;
        int length = a.length - 1;
        while (length >= 0 && a[length] == (byte) 0) {
            length--;
        }
        Object obj = new byte[(length + 1)];
        System.arraycopy(a, 0, obj, 0, length + 1);
        try {
            str2 = new String(obj, "UTF-8");
        } catch (Exception e) {
            C1012g.m3139b(e);
            str2 = null;
        }
        return str2;
    }

    static void m2992a(Context context) {
        String secret = DroiPush.getSecret(context);
        if (secret == null || "".equals(secret.trim())) {
            throw new RuntimeException("'com.droi.sdk.secret_key' is invalid, check it in AndroidManifest.xml! ");
        }
    }

    static void m2993a(Context context, C1004t c1004t) {
        if (c1004t != null && c1004t.f3336j && context != null) {
            C0987b a = C0987b.m3029a(context);
            if (aa.m2958a(context).m2981e(c1004t.f3338l)) {
                int[] d;
                if (c1004t.m3095b()) {
                    d = aa.m2958a(context).m2979d(c1004t.f3338l);
                    if (d.length == 4 && d[0] > 0 && d[1] > 0 && d[2] > 0 && d[3] > 0) {
                        c1004t.f3331e = m2997a(d[0], d[1], d[2], d[3])[1];
                        c1004t.f3332f = c1004t.f3331e + 600000;
                    }
                } else if (c1004t.m3096c()) {
                    d = aa.m2958a(context).m2979d(c1004t.f3338l);
                    if (d.length == 4 && d[0] > 0 && d[1] > 0 && d[2] > 0 && d[3] > 0) {
                        long[] a2 = m2997a(d[0], d[1], d[2], d[3]);
                        if (c1004t.f3332f <= a2[1]) {
                            return;
                        }
                        if (c1004t.f3331e < a2[1]) {
                            c1004t.f3332f = (a2[1] + c1004t.f3332f) - c1004t.f3331e;
                            c1004t.f3331e = a2[1];
                        }
                    }
                }
                a.m3037a(c1004t);
            } else if (c1004t.m3096c() || c1004t.m3097d()) {
                a.m3037a(c1004t);
            }
        }
    }

    static void m2994a(Context context, String str) {
        if (str != null && !"".equals(str.trim())) {
            String appId = DroiPush.getAppId(context);
            String secret = DroiPush.getSecret(context);
            aa a = aa.m2958a(context);
            if (appId != null && !"".equals(appId.trim())) {
                String a2 = C1015j.m3151a(context, str, a.m2974b());
                try {
                    C1012g.m3138a("SendAppInfo: " + a2);
                    Object a3 = C1015j.m3152a(context, "http://push_data.droibaas.com:2400/data/appInfo", a2, secret, appId);
                    C1012g.m3138a("uploadAppInfo:" + a3);
                    if (!TextUtils.isEmpty(a3) && new JSONObject(a3).getInt("rCode") == 200) {
                        a.m2978d();
                        Editor edit = context.getSharedPreferences("droi_pushsdk_sharedpref", 0).edit();
                        edit.putLong("last_update_app_info_time", System.currentTimeMillis());
                        edit.putString("last_update_app_id", appId);
                        edit.putString("last_update_device_id", str);
                        edit.commit();
                    }
                } catch (Exception e) {
                    C1012g.m3139b(e);
                } catch (Exception e2) {
                    C1012g.m3139b(e2);
                }
            }
        }
    }

    static void m2995a(Context context, JSONObject jSONObject) {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("ipList");
            if (jSONArray != null) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = (JSONObject) jSONArray.get(i);
                    int i2 = jSONObject2.getInt("moduleId");
                    String string = jSONObject2.getString("host");
                    String string2 = jSONObject2.getString("port");
                    if (C1015j.m3168d(string) && C1015j.m3168d(string2) && i2 == 1) {
                        if (arrayList2.isEmpty()) {
                            arrayList2.add(String.valueOf(1));
                        }
                        arrayList2.add(string + ":" + string2);
                    }
                }
                arrayList.add(arrayList2);
                aa.m2958a(context).m2970a(arrayList);
            }
        } catch (Exception e) {
            C1012g.m3137a(e);
        }
    }

    static boolean m2996a(String str) {
        return !C1015j.m3168d(str) ? false : Pattern.compile("[0-9]{1,4}(\\.[0-9]{1,4})*").matcher(str).matches();
    }

    static long[] m2997a(int i, int i2, int i3, int i4) {
        long[] jArr = new long[]{-1, -1};
        Date date = new Date(System.currentTimeMillis());
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(11, i);
        instance.set(12, i2);
        jArr[0] = instance.getTimeInMillis();
        date = instance.getTime();
        instance.set(11, i3);
        instance.set(12, i4);
        if (date.after(instance.getTime())) {
            instance.add(5, 1);
        }
        jArr[1] = instance.getTimeInMillis();
        return jArr;
    }

    static C1016k m2998b(Context context) {
        if (context == null) {
            return null;
        }
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        Intent intent = new Intent("com.droi.sdk.push.action.START");
        intent.setPackage(context.getPackageName());
        List queryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (queryIntentServices == null || queryIntentServices.isEmpty()) {
            return null;
        }
        return ((ResolveInfo) queryIntentServices.get(0)) != null ? new C1016k(m3003e(context), packageName) : null;
    }

    static void m2999b(Context context, String str) {
        if (str != null && !"".equals(str.trim())) {
            String a = C1010e.m3133a(context, str);
            String appId = DroiPush.getAppId(context);
            try {
                Object a2 = C1015j.m3152a(context, "http://push_data.droibaas.com:2400/data/deviceInfo", a, DroiPush.getSecret(context), appId);
                C1012g.m3141c("uploadDeviceInfo:" + a2);
                if (!TextUtils.isEmpty(a2) && new JSONObject(a2).getInt("rCode") == 200) {
                    Editor edit = context.getSharedPreferences("droi_pushsdk_sharedpref", 0).edit();
                    edit.putLong("last_update_dev_info_time", System.currentTimeMillis());
                    edit.putString("last_update_app_id", appId);
                    edit.putString("last_update_device_id", str);
                    edit.commit();
                }
            } catch (Exception e) {
                C1012g.m3139b(e);
            } catch (Exception e2) {
                C1012g.m3139b(e2);
            }
        }
    }

    static boolean m3000b(C1016k c1016k) {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        boolean z;
        Throwable th;
        try {
            LocalSocket localSocket = new LocalSocket();
            localSocket.connect(new LocalSocketAddress("com.droi.server.UNIX_DOMAIN_SOCKET_NAME"));
            bufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream(), "UTF-8"));
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream(), "UTF-8"));
                try {
                    bufferedWriter.write(c1016k.f3360a + "\n");
                    bufferedWriter.write(c1016k.f3361b + "\n");
                    bufferedWriter.flush();
                    bufferedReader.readLine();
                    bufferedReader.readLine();
                    localSocket.close();
                    z = true;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                        }
                    }
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e2) {
                        }
                    }
                } catch (Exception e3) {
                    z = false;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e4) {
                        }
                    }
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e5) {
                        }
                    }
                    return z;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e6) {
                        }
                    }
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e7) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e8) {
                bufferedWriter = null;
                z = false;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                return z;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                bufferedWriter = null;
                th = th4;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                throw th;
            }
        } catch (Exception e9) {
            bufferedWriter = null;
            bufferedReader = null;
            z = false;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            return z;
        } catch (Throwable th32) {
            bufferedReader = null;
            th = th32;
            bufferedWriter = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            throw th;
        }
        return z;
    }

    static Pair m3001c(Context context) {
        List queryIntentServices = context.getApplicationContext().getPackageManager().queryIntentServices(new Intent("com.droi.sdk.push.action.START"), 0);
        if (queryIntentServices == null || queryIntentServices.isEmpty()) {
            return null;
        }
        List arrayList = new ArrayList();
        for (int i = 0; i < queryIntentServices.size(); i++) {
            arrayList.add(new C1016k(m3003e(context), ((ResolveInfo) queryIntentServices.get(i)).serviceInfo.packageName));
        }
        Collections.sort(arrayList, new af());
        C1016k c1016k = (C1016k) arrayList.get(arrayList.size() - 1);
        return c1016k != null ? new Pair(c1016k.m3175a(), DroiPushService.class.getName()) : null;
    }

    static synchronized void m3002d(Context context) {
        synchronized (ad.class) {
            aa a = aa.m2958a(context);
            SharedPreferences sharedPreferences = context.getSharedPreferences("droi_pushsdk_sharedpref", 0);
            String string = sharedPreferences.getString("last_update_app_id", null);
            String string2 = sharedPreferences.getString("last_update_device_id", null);
            String appId = DroiPush.getAppId(context);
            String a2 = DroiPush.m2876a(context);
            if (appId == null || "".equals(appId.trim()) || a2 == null || "".equals(a2.trim())) {
                C1012g.m3142d("uploadAppDeviceInfo failed: curAppId(" + appId + ")-curDeviceId(" + a2 + ")");
            } else if (appId.equals(string) && a2.equals(string2)) {
                long j = sharedPreferences.getLong("last_update_app_info_time", -1);
                long j2 = sharedPreferences.getLong("last_update_dev_info_time", -1);
                if (Math.abs(System.currentTimeMillis() - j) >= 1296000000) {
                    a.m2986i();
                } else if (a.m2976c()) {
                    a.m2986i();
                }
                if (Math.abs(System.currentTimeMillis() - j2) >= 1296000000) {
                    a.m2987j();
                }
            } else {
                C1012g.m3141c("appId(" + string + ":" + appId + ")");
                C1012g.m3141c("deviceId(" + string2 + ":" + a2 + ")");
                a.m2986i();
                a.m2987j();
            }
        }
    }

    private static String m3003e(Context context) {
        String str = null;
        if (context == null) {
            return null;
        }
        ServiceInfo serviceInfo;
        Context applicationContext = context.getApplicationContext();
        try {
            serviceInfo = applicationContext.getPackageManager().getServiceInfo(new ComponentName(applicationContext.getApplicationContext(), DroiPushService.class), 128);
        } catch (Exception e) {
            C1012g.m3139b(e);
            serviceInfo = null;
        }
        if (!(serviceInfo == null || serviceInfo.metaData == null)) {
            str = serviceInfo.metaData.getString("SERVICE_VERSION");
        }
        return !m2996a(str) ? "0.0.0" : str;
    }
}
