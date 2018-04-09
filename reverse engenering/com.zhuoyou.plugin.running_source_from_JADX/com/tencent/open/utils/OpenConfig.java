package com.tencent.open.utils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import com.tencent.connect.common.Constants;
import com.tencent.open.p036a.C1314f;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class OpenConfig {
    private static Map<String, OpenConfig> f4206a = Collections.synchronizedMap(new HashMap());
    private static String f4207b = null;
    private Context f4208c = null;
    private String f4209d = null;
    private JSONObject f4210e = null;
    private long f4211f = 0;
    private int f4212g = 0;
    private boolean f4213h = true;

    public static OpenConfig getInstance(Context context, String str) {
        OpenConfig openConfig;
        synchronized (f4206a) {
            C1314f.m3864a("openSDK_LOG.OpenConfig", "getInstance begin");
            if (str != null) {
                f4207b = str;
            }
            if (str == null) {
                if (f4207b != null) {
                    str = f4207b;
                } else {
                    str = "0";
                }
            }
            openConfig = (OpenConfig) f4206a.get(str);
            if (openConfig == null) {
                openConfig = new OpenConfig(context, str);
                f4206a.put(str, openConfig);
            }
            C1314f.m3864a("openSDK_LOG.OpenConfig", "getInstance end");
        }
        return openConfig;
    }

    private OpenConfig(Context context, String str) {
        this.f4208c = context.getApplicationContext();
        this.f4209d = str;
        m3950a();
        m3954b();
    }

    private void m3950a() {
        try {
            this.f4210e = new JSONObject(m3949a("com.tencent.open.config.json"));
        } catch (JSONException e) {
            this.f4210e = new JSONObject();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m3949a(java.lang.String r6) {
        /*
        r5 = this;
        r1 = "";
        r0 = r5.f4209d;	 Catch:{ FileNotFoundException -> 0x0052 }
        if (r0 == 0) goto L_0x0050;
    L_0x0006:
        r0 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0052 }
        r0.<init>();	 Catch:{ FileNotFoundException -> 0x0052 }
        r0 = r0.append(r6);	 Catch:{ FileNotFoundException -> 0x0052 }
        r2 = ".";
        r0 = r0.append(r2);	 Catch:{ FileNotFoundException -> 0x0052 }
        r2 = r5.f4209d;	 Catch:{ FileNotFoundException -> 0x0052 }
        r0 = r0.append(r2);	 Catch:{ FileNotFoundException -> 0x0052 }
        r0 = r0.toString();	 Catch:{ FileNotFoundException -> 0x0052 }
    L_0x001f:
        r2 = r5.f4208c;	 Catch:{ FileNotFoundException -> 0x0052 }
        r0 = r2.openFileInput(r0);	 Catch:{ FileNotFoundException -> 0x0052 }
    L_0x0025:
        r3 = new java.io.BufferedReader;
        r2 = new java.io.InputStreamReader;
        r4 = "UTF-8";
        r4 = java.nio.charset.Charset.forName(r4);
        r2.<init>(r0, r4);
        r3.<init>(r2);
        r2 = new java.lang.StringBuffer;
        r2.<init>();
    L_0x003a:
        r4 = r3.readLine();	 Catch:{ IOException -> 0x0044 }
        if (r4 == 0) goto L_0x0064;
    L_0x0040:
        r2.append(r4);	 Catch:{ IOException -> 0x0044 }
        goto L_0x003a;
    L_0x0044:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x007c }
        r0.close();	 Catch:{ IOException -> 0x0076 }
        r3.close();	 Catch:{ IOException -> 0x0076 }
        r0 = r1;
    L_0x004f:
        return r0;
    L_0x0050:
        r0 = r6;
        goto L_0x001f;
    L_0x0052:
        r0 = move-exception;
        r0 = r5.f4208c;	 Catch:{ IOException -> 0x005e }
        r0 = r0.getAssets();	 Catch:{ IOException -> 0x005e }
        r0 = r0.open(r6);	 Catch:{ IOException -> 0x005e }
        goto L_0x0025;
    L_0x005e:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x004f;
    L_0x0064:
        r1 = r2.toString();	 Catch:{ IOException -> 0x0044 }
        r0.close();	 Catch:{ IOException -> 0x0070 }
        r3.close();	 Catch:{ IOException -> 0x0070 }
        r0 = r1;
        goto L_0x004f;
    L_0x0070:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x004f;
    L_0x0076:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x004f;
    L_0x007c:
        r1 = move-exception;
        r0.close();	 Catch:{ IOException -> 0x0084 }
        r3.close();	 Catch:{ IOException -> 0x0084 }
    L_0x0083:
        throw r1;
    L_0x0084:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0083;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.OpenConfig.a(java.lang.String):java.lang.String");
    }

    private void m3952a(String str, String str2) {
        try {
            if (this.f4209d != null) {
                str = str + "." + this.f4209d;
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.f4208c.openFileOutput(str, 0), Charset.forName("UTF-8"));
            outputStreamWriter.write(str2);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void m3954b() {
        if (this.f4212g != 0) {
            m3955b("update thread is running, return");
            return;
        }
        this.f4212g = 1;
        final Bundle bundle = new Bundle();
        bundle.putString("appid", this.f4209d);
        bundle.putString("appid_for_getting_config", this.f4209d);
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        bundle.putString("status_version", VERSION.SDK);
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        new Thread(this) {
            final /* synthetic */ OpenConfig f4205b;

            public void run() {
                try {
                    this.f4205b.m3953a(Util.parseJson(HttpUtils.openUrl2(this.f4205b.f4208c, "http://cgi.connect.qq.com/qqconnectopen/openapi/policy_conf", Constants.HTTP_GET, bundle).response));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.f4205b.f4212g = 0;
            }
        }.start();
    }

    private void m3953a(JSONObject jSONObject) {
        m3955b("cgi back, do update");
        this.f4210e = jSONObject;
        m3952a("com.tencent.open.config.json", jSONObject.toString());
        this.f4211f = SystemClock.elapsedRealtime();
    }

    private void m3956c() {
        int optInt = this.f4210e.optInt("Common_frequency");
        if (optInt == 0) {
            optInt = 1;
        }
        if (SystemClock.elapsedRealtime() - this.f4211f >= ((long) (optInt * 3600000))) {
            m3954b();
        }
    }

    public int getInt(String str) {
        m3955b("get " + str);
        m3956c();
        return this.f4210e.optInt(str);
    }

    public long getLong(String str) {
        m3955b("get " + str);
        m3956c();
        return this.f4210e.optLong(str);
    }

    public boolean getBoolean(String str) {
        m3955b("get " + str);
        m3956c();
        Object opt = this.f4210e.opt(str);
        if (opt == null) {
            return false;
        }
        if (opt instanceof Integer) {
            return !opt.equals(Integer.valueOf(0));
        } else if (opt instanceof Boolean) {
            return ((Boolean) opt).booleanValue();
        } else {
            return false;
        }
    }

    private void m3955b(String str) {
        if (this.f4213h) {
            C1314f.m3864a("openSDK_LOG.OpenConfig", str + "; appid: " + this.f4209d);
        }
    }
}
