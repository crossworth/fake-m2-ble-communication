package com.baidu.lbsapi.auth;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class LBSAuthManager {
    public static final int CODE_AUTHENTICATE_SUCC = 0;
    public static final int CODE_AUTHENTICATING = 602;
    public static final int CODE_UNAUTHENTICATE = 601;
    public static final String VERSION = "1.0.6.42126";
    private static Context f62a;
    private static C0325l f63d = null;
    private static int f64e = 0;
    private static Hashtable<String, LBSAuthManagerListener> f65f = new Hashtable();
    private static LBSAuthManager f66g;
    private C0315c f67b = null;
    private C0318e f68c = null;
    private final Handler f69h = new C0321h(this, Looper.getMainLooper());

    private LBSAuthManager(Context context) {
        f62a = context;
        if (!(f63d == null || f63d.isAlive())) {
            f63d = null;
        }
        m119d();
    }

    private int m103a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("status")) {
                jSONObject.put("status", -1);
            }
            int i = jSONObject.getInt("status");
            if (jSONObject.has("current") && i == 0) {
                long j = jSONObject.getLong("current");
                long currentTimeMillis = System.currentTimeMillis();
                if (((double) (currentTimeMillis - j)) / 3600000.0d >= 24.0d) {
                    i = CODE_UNAUTHENTICATE;
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Tools.BIRTH_FORMAT);
                    if (!simpleDateFormat.format(Long.valueOf(currentTimeMillis)).equals(simpleDateFormat.format(Long.valueOf(j)))) {
                        i = CODE_UNAUTHENTICATE;
                    }
                }
            }
            if (jSONObject.has("current") && i == CODE_AUTHENTICATING) {
                if (((double) ((System.currentTimeMillis() - jSONObject.getLong("current")) / 1000)) > 180.0d) {
                    return CODE_UNAUTHENTICATE;
                }
            }
            return i;
        } catch (JSONException e) {
            JSONException jSONException = e;
            int i2 = -1;
            jSONException.printStackTrace();
            return i2;
        }
    }

    private String m104a(int i) throws IOException {
        FileInputStream fileInputStream;
        Object obj;
        Object obj2;
        BufferedReader bufferedReader;
        Throwable th;
        Throwable th2;
        String str = null;
        InputStreamReader inputStreamReader;
        try {
            fileInputStream = new FileInputStream(new File("/proc/" + i + "/cmdline"));
            try {
                inputStreamReader = new InputStreamReader(fileInputStream);
            } catch (FileNotFoundException e) {
                obj = str;
                obj2 = str;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return str;
            } catch (IOException e2) {
                obj = str;
                obj2 = str;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return str;
            } catch (Throwable th3) {
                obj2 = str;
                String str2 = str;
                th = th3;
                obj = str2;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    str = bufferedReader.readLine();
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (FileNotFoundException e3) {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return str;
                } catch (IOException e4) {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return str;
                } catch (Throwable th4) {
                    th = th4;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                obj = str;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return str;
            } catch (IOException e6) {
                obj = str;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return str;
            } catch (Throwable th32) {
                th2 = th32;
                obj = str;
                th = th2;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e7) {
            bufferedReader = str;
            inputStreamReader = str;
            fileInputStream = str;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return str;
        } catch (IOException e8) {
            bufferedReader = str;
            inputStreamReader = str;
            fileInputStream = str;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return str;
        } catch (Throwable th322) {
            inputStreamReader = str;
            fileInputStream = str;
            th2 = th322;
            bufferedReader = str;
            th = th2;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
        return str;
    }

    private String m105a(Context context) {
        int myPid = Process.myPid();
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        String str = null;
        try {
            str = m104a(myPid);
        } catch (IOException e) {
        }
        return str == null ? f62a.getPackageName() : str;
    }

    private String m106a(Context context, String str) {
        String str2 = "";
        LBSAuthManagerListener lBSAuthManagerListener;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                lBSAuthManagerListener = (LBSAuthManagerListener) f65f.get(str);
                if (lBSAuthManagerListener != null) {
                    lBSAuthManagerListener.onAuthResult(-1, ErrorMessage.m102a("AndroidManifest.xml的application中没有meta-data标签"));
                }
                return str2;
            }
            str2 = applicationInfo.metaData.getString("com.baidu.lbsapi.API_KEY");
            if (str2 == null || str2.equals("")) {
                lBSAuthManagerListener = (LBSAuthManagerListener) f65f.get(str);
                if (lBSAuthManagerListener != null) {
                    lBSAuthManagerListener.onAuthResult(-1, ErrorMessage.m102a("无法在AndroidManifest.xml中获取com.baidu.android.lbs.API_KEY的值"));
                }
            }
            return str2;
        } catch (NameNotFoundException e) {
            lBSAuthManagerListener = (LBSAuthManagerListener) f65f.get(str);
            if (lBSAuthManagerListener != null) {
                lBSAuthManagerListener.onAuthResult(-1, ErrorMessage.m102a("无法在AndroidManifest.xml中获取com.baidu.android.lbs.API_KEY的值"));
            }
        }
    }

    private synchronized void m111a(String str, String str2) {
        if (str == null) {
            str = m120e();
        }
        Message obtainMessage = this.f69h.obtainMessage();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("status")) {
                jSONObject.put("status", -1);
            }
            if (!jSONObject.has("current")) {
                jSONObject.put("current", System.currentTimeMillis());
            }
            m118c(jSONObject.toString());
            if (jSONObject.has("current")) {
                jSONObject.remove("current");
            }
            obtainMessage.what = jSONObject.getInt("status");
            obtainMessage.obj = jSONObject.toString();
            Bundle bundle = new Bundle();
            bundle.putString("listenerKey", str2);
            obtainMessage.setData(bundle);
            this.f69h.sendMessage(obtainMessage);
        } catch (JSONException e) {
            e.printStackTrace();
            obtainMessage.what = -1;
            obtainMessage.obj = new JSONObject();
            bundle = new Bundle();
            bundle.putString("listenerKey", str2);
            obtainMessage.setData(bundle);
            this.f69h.sendMessage(obtainMessage);
        }
        f63d.m162c();
        f64e--;
        if (C0311a.f70a) {
            C0311a.m122a("httpRequest called mAuthCounter-- = " + f64e);
        }
        if (f64e == 0) {
            f63d.m160a();
            if (f63d != null) {
                f63d = null;
            }
        }
    }

    private void m112a(boolean z, String str, Hashtable<String, String> hashtable, String str2) {
        String a = m106a(f62a, str2);
        if (a != null && !a.equals("")) {
            HashMap hashMap = new HashMap();
            hashMap.put("url", "https://api.map.baidu.com/sdkcs/verify");
            hashMap.put("output", "json");
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_AK, a);
            hashMap.put("mcode", C0313b.m126a(f62a));
            hashMap.put("from", "lbs_yunsdk");
            if (hashtable != null && hashtable.size() > 0) {
                for (Entry entry : hashtable.entrySet()) {
                    String str3 = (String) entry.getKey();
                    a = (String) entry.getValue();
                    if (!(TextUtils.isEmpty(str3) || TextUtils.isEmpty(a))) {
                        hashMap.put(str3, a);
                    }
                }
            }
            CharSequence charSequence = "";
            try {
                charSequence = CommonParam.m69a(f62a);
            } catch (Exception e) {
            }
            C0311a.m122a("cuid:" + charSequence);
            if (TextUtils.isEmpty(charSequence)) {
                hashMap.put("cuid", "");
            } else {
                hashMap.put("cuid", charSequence);
            }
            hashMap.put("pcn", f62a.getPackageName());
            hashMap.put("version", VERSION);
            charSequence = "";
            try {
                charSequence = C0313b.m132c(f62a);
            } catch (Exception e2) {
            }
            if (TextUtils.isEmpty(charSequence)) {
                hashMap.put("macaddr", "");
            } else {
                hashMap.put("macaddr", charSequence);
            }
            charSequence = "";
            try {
                charSequence = C0313b.m125a();
            } catch (Exception e3) {
            }
            if (TextUtils.isEmpty(charSequence)) {
                hashMap.put("language", "");
            } else {
                hashMap.put("language", charSequence);
            }
            if (z) {
                hashMap.put("force", z ? "1" : "0");
            }
            if (str == null) {
                hashMap.put("from_service", "");
            } else {
                hashMap.put("from_service", str);
            }
            this.f67b = new C0315c(f62a);
            this.f67b.m141a(hashMap, new C0323j(this, str2));
        }
    }

    private void m113a(boolean z, String str, Hashtable<String, String> hashtable, String[] strArr, String str2) {
        String a = m106a(f62a, str2);
        if (a != null && !a.equals("")) {
            HashMap hashMap = new HashMap();
            hashMap.put("url", "https://api.map.baidu.com/sdkcs/verify");
            hashMap.put("output", "json");
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_AK, a);
            hashMap.put("from", "lbs_yunsdk");
            if (hashtable != null && hashtable.size() > 0) {
                for (Entry entry : hashtable.entrySet()) {
                    String str3 = (String) entry.getKey();
                    a = (String) entry.getValue();
                    if (!(TextUtils.isEmpty(str3) || TextUtils.isEmpty(a))) {
                        hashMap.put(str3, a);
                    }
                }
            }
            CharSequence charSequence = "";
            try {
                charSequence = CommonParam.m69a(f62a);
            } catch (Exception e) {
            }
            if (TextUtils.isEmpty(charSequence)) {
                hashMap.put("cuid", "");
            } else {
                hashMap.put("cuid", charSequence);
            }
            hashMap.put("pcn", f62a.getPackageName());
            hashMap.put("version", VERSION);
            charSequence = "";
            try {
                charSequence = C0313b.m132c(f62a);
            } catch (Exception e2) {
            }
            if (TextUtils.isEmpty(charSequence)) {
                hashMap.put("macaddr", "");
            } else {
                hashMap.put("macaddr", charSequence);
            }
            charSequence = "";
            try {
                charSequence = C0313b.m125a();
            } catch (Exception e3) {
            }
            if (TextUtils.isEmpty(charSequence)) {
                hashMap.put("language", "");
            } else {
                hashMap.put("language", charSequence);
            }
            if (z) {
                hashMap.put("force", z ? "1" : "0");
            }
            if (str == null) {
                hashMap.put("from_service", "");
            } else {
                hashMap.put("from_service", str);
            }
            this.f68c = new C0318e(f62a);
            this.f68c.m148a(hashMap, strArr, new C0324k(this, str2));
        }
    }

    private boolean m116b(String str) {
        String a = m106a(f62a, str);
        String str2 = "";
        try {
            JSONObject jSONObject = new JSONObject(m120e());
            if (!jSONObject.has(SocializeProtocolConstants.PROTOCOL_KEY_AK)) {
                return true;
            }
            Object string = jSONObject.getString(SocializeProtocolConstants.PROTOCOL_KEY_AK);
            return (a == null || string == null || a.equals(string)) ? false : true;
        } catch (JSONException e) {
            e.printStackTrace();
            String str3 = str2;
        }
    }

    private void m118c(String str) {
        f62a.getSharedPreferences("authStatus_" + m105a(f62a), 0).edit().putString("status", str).commit();
    }

    private void m119d() {
        synchronized (LBSAuthManager.class) {
            if (f63d == null) {
                f63d = new C0325l("auth");
                f63d.start();
                while (f63d.f95a == null) {
                    try {
                        if (C0311a.f70a) {
                            C0311a.m122a("wait for create auth thread.");
                        }
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String m120e() {
        return f62a.getSharedPreferences("authStatus_" + m105a(f62a), 0).getString("status", "{\"status\":601}");
    }

    public static LBSAuthManager getInstance(Context context) {
        if (f66g == null) {
            synchronized (LBSAuthManager.class) {
                if (f66g == null) {
                    f66g = new LBSAuthManager(context);
                }
            }
        } else {
            f62a = context;
        }
        return f66g;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int authenticate(boolean r10, java.lang.String r11, java.util.Hashtable<java.lang.String, java.lang.String> r12, com.baidu.lbsapi.auth.LBSAuthManagerListener r13) {
        /*
        r9 = this;
        r0 = -1;
        r7 = com.baidu.lbsapi.auth.LBSAuthManager.class;
        monitor-enter(r7);
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00c5 }
        r1.<init>();	 Catch:{ all -> 0x00c5 }
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x00c5 }
        r1 = r1.append(r2);	 Catch:{ all -> 0x00c5 }
        r2 = "";
        r1 = r1.append(r2);	 Catch:{ all -> 0x00c5 }
        r4 = r1.toString();	 Catch:{ all -> 0x00c5 }
        if (r13 == 0) goto L_0x0022;
    L_0x001d:
        r1 = f65f;	 Catch:{ all -> 0x00c5 }
        r1.put(r4, r13);	 Catch:{ all -> 0x00c5 }
    L_0x0022:
        r1 = f62a;	 Catch:{ all -> 0x00c5 }
        r1 = r9.m106a(r1, r4);	 Catch:{ all -> 0x00c5 }
        if (r1 == 0) goto L_0x0032;
    L_0x002a:
        r2 = "";
        r1 = r1.equals(r2);	 Catch:{ all -> 0x00c5 }
        if (r1 == 0) goto L_0x0035;
    L_0x0032:
        monitor-exit(r7);	 Catch:{ all -> 0x00c5 }
        r2 = r0;
    L_0x0034:
        return r2;
    L_0x0035:
        r1 = f64e;	 Catch:{ all -> 0x00c5 }
        r1 = r1 + 1;
        f64e = r1;	 Catch:{ all -> 0x00c5 }
        r1 = com.baidu.lbsapi.auth.C0311a.f70a;	 Catch:{ all -> 0x00c5 }
        if (r1 == 0) goto L_0x0057;
    L_0x003f:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00c5 }
        r1.<init>();	 Catch:{ all -> 0x00c5 }
        r2 = " mAuthCounter  ++ = ";
        r1 = r1.append(r2);	 Catch:{ all -> 0x00c5 }
        r2 = f64e;	 Catch:{ all -> 0x00c5 }
        r1 = r1.append(r2);	 Catch:{ all -> 0x00c5 }
        r1 = r1.toString();	 Catch:{ all -> 0x00c5 }
        com.baidu.lbsapi.auth.C0311a.m122a(r1);	 Catch:{ all -> 0x00c5 }
    L_0x0057:
        r1 = r9.m120e();	 Catch:{ all -> 0x00c5 }
        r2 = com.baidu.lbsapi.auth.C0311a.f70a;	 Catch:{ all -> 0x00c5 }
        if (r2 == 0) goto L_0x0075;
    L_0x005f:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00c5 }
        r2.<init>();	 Catch:{ all -> 0x00c5 }
        r3 = "getAuthMessage from cache:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x00c5 }
        r2 = r2.append(r1);	 Catch:{ all -> 0x00c5 }
        r2 = r2.toString();	 Catch:{ all -> 0x00c5 }
        com.baidu.lbsapi.auth.C0311a.m122a(r2);	 Catch:{ all -> 0x00c5 }
    L_0x0075:
        r2 = r9.m103a(r1);	 Catch:{ all -> 0x00c5 }
        r1 = 601; // 0x259 float:8.42E-43 double:2.97E-321;
        if (r2 != r1) goto L_0x0091;
    L_0x007d:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x00c0 }
        r1.<init>();	 Catch:{ JSONException -> 0x00c0 }
        r3 = "status";
        r5 = 602; // 0x25a float:8.44E-43 double:2.974E-321;
        r1 = r1.put(r3, r5);	 Catch:{ JSONException -> 0x00c0 }
        r1 = r1.toString();	 Catch:{ JSONException -> 0x00c0 }
        r9.m118c(r1);	 Catch:{ JSONException -> 0x00c0 }
    L_0x0091:
        r9.m119d();	 Catch:{ all -> 0x00c5 }
        r1 = com.baidu.lbsapi.auth.C0311a.f70a;	 Catch:{ all -> 0x00c5 }
        if (r1 == 0) goto L_0x00b2;
    L_0x0098:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00c5 }
        r1.<init>();	 Catch:{ all -> 0x00c5 }
        r3 = "mThreadLooper.mHandler = ";
        r1 = r1.append(r3);	 Catch:{ all -> 0x00c5 }
        r3 = f63d;	 Catch:{ all -> 0x00c5 }
        r3 = r3.f95a;	 Catch:{ all -> 0x00c5 }
        r1 = r1.append(r3);	 Catch:{ all -> 0x00c5 }
        r1 = r1.toString();	 Catch:{ all -> 0x00c5 }
        com.baidu.lbsapi.auth.C0311a.m122a(r1);	 Catch:{ all -> 0x00c5 }
    L_0x00b2:
        r1 = f63d;	 Catch:{ all -> 0x00c5 }
        if (r1 == 0) goto L_0x00bc;
    L_0x00b6:
        r1 = f63d;	 Catch:{ all -> 0x00c5 }
        r1 = r1.f95a;	 Catch:{ all -> 0x00c5 }
        if (r1 != 0) goto L_0x00c8;
    L_0x00bc:
        monitor-exit(r7);	 Catch:{ all -> 0x00c5 }
        r2 = r0;
        goto L_0x0034;
    L_0x00c0:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x00c5 }
        goto L_0x0091;
    L_0x00c5:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x00c5 }
        throw r0;
    L_0x00c8:
        r0 = f63d;	 Catch:{ all -> 0x00c5 }
        r8 = r0.f95a;	 Catch:{ all -> 0x00c5 }
        r0 = new com.baidu.lbsapi.auth.i;	 Catch:{ all -> 0x00c5 }
        r1 = r9;
        r3 = r10;
        r5 = r11;
        r6 = r12;
        r0.<init>(r1, r2, r3, r4, r5, r6);	 Catch:{ all -> 0x00c5 }
        r8.post(r0);	 Catch:{ all -> 0x00c5 }
        monitor-exit(r7);	 Catch:{ all -> 0x00c5 }
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.lbsapi.auth.LBSAuthManager.authenticate(boolean, java.lang.String, java.util.Hashtable, com.baidu.lbsapi.auth.LBSAuthManagerListener):int");
    }

    public String getPublicKey(Context context) throws NameNotFoundException {
        String str = "";
        return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("com.baidu.lbsapi.API_KEY");
    }
}
