package com.tencent.utils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class OpenConfig {
    private static HashMap<String, OpenConfig> f2981a = null;
    private static String f2982b = null;
    private Context f2983c = null;
    private String f2984d = null;
    private JSONObject f2985e = null;
    private long f2986f = 0;
    private int f2987g = 0;
    private boolean f2988h = true;

    public static OpenConfig getInstance(Context context, String str) {
        if (f2981a == null) {
            f2981a = new HashMap();
        }
        if (str != null) {
            f2982b = str;
        }
        if (str == null) {
            if (f2982b != null) {
                str = f2982b;
            } else {
                str = "0";
            }
        }
        OpenConfig openConfig = (OpenConfig) f2981a.get(str);
        if (openConfig != null) {
            return openConfig;
        }
        openConfig = new OpenConfig(context, str);
        f2981a.put(str, openConfig);
        return openConfig;
    }

    private OpenConfig(Context context, String str) {
        this.f2983c = context;
        this.f2984d = str;
        m2823a();
        m2827b();
    }

    private void m2823a() {
        try {
            this.f2985e = new JSONObject(m2822a("com.tencent.open.config.json"));
        } catch (JSONException e) {
            this.f2985e = new JSONObject();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m2822a(java.lang.String r6) {
        /*
        r5 = this;
        r1 = "";
        r0 = r5.f2984d;	 Catch:{ FileNotFoundException -> 0x004c }
        if (r0 == 0) goto L_0x004a;
    L_0x0006:
        r0 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x004c }
        r0.<init>();	 Catch:{ FileNotFoundException -> 0x004c }
        r0 = r0.append(r6);	 Catch:{ FileNotFoundException -> 0x004c }
        r2 = ".";
        r0 = r0.append(r2);	 Catch:{ FileNotFoundException -> 0x004c }
        r2 = r5.f2984d;	 Catch:{ FileNotFoundException -> 0x004c }
        r0 = r0.append(r2);	 Catch:{ FileNotFoundException -> 0x004c }
        r0 = r0.toString();	 Catch:{ FileNotFoundException -> 0x004c }
    L_0x001f:
        r2 = r5.f2983c;	 Catch:{ FileNotFoundException -> 0x004c }
        r0 = r2.openFileInput(r0);	 Catch:{ FileNotFoundException -> 0x004c }
    L_0x0025:
        r3 = new java.io.BufferedReader;
        r2 = new java.io.InputStreamReader;
        r2.<init>(r0);
        r3.<init>(r2);
        r2 = new java.lang.StringBuffer;
        r2.<init>();
    L_0x0034:
        r4 = r3.readLine();	 Catch:{ IOException -> 0x003e }
        if (r4 == 0) goto L_0x005e;
    L_0x003a:
        r2.append(r4);	 Catch:{ IOException -> 0x003e }
        goto L_0x0034;
    L_0x003e:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0076 }
        r0.close();	 Catch:{ IOException -> 0x0070 }
        r3.close();	 Catch:{ IOException -> 0x0070 }
        r0 = r1;
    L_0x0049:
        return r0;
    L_0x004a:
        r0 = r6;
        goto L_0x001f;
    L_0x004c:
        r0 = move-exception;
        r0 = r5.f2983c;	 Catch:{ IOException -> 0x0058 }
        r0 = r0.getAssets();	 Catch:{ IOException -> 0x0058 }
        r0 = r0.open(r6);	 Catch:{ IOException -> 0x0058 }
        goto L_0x0025;
    L_0x0058:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x0049;
    L_0x005e:
        r1 = r2.toString();	 Catch:{ IOException -> 0x003e }
        r0.close();	 Catch:{ IOException -> 0x006a }
        r3.close();	 Catch:{ IOException -> 0x006a }
        r0 = r1;
        goto L_0x0049;
    L_0x006a:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x0049;
    L_0x0070:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x0049;
    L_0x0076:
        r1 = move-exception;
        r0.close();	 Catch:{ IOException -> 0x007e }
        r3.close();	 Catch:{ IOException -> 0x007e }
    L_0x007d:
        throw r1;
    L_0x007e:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x007d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.utils.OpenConfig.a(java.lang.String):java.lang.String");
    }

    private void m2825a(String str, String str2) {
        try {
            if (this.f2984d != null) {
                str = str + "." + this.f2984d;
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.f2983c.openFileOutput(str, 0));
            outputStreamWriter.write(str2);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void m2827b() {
        if (this.f2987g != 0) {
            m2828b("update thread is running, return");
            return;
        }
        this.f2987g = 1;
        final Bundle bundle = new Bundle();
        bundle.putString("appid", this.f2984d);
        bundle.putString("appid_for_getting_config", this.f2984d);
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        bundle.putString("status_version", VERSION.SDK);
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        new Thread(this) {
            final /* synthetic */ OpenConfig f2980b;

            public void run() {
                try {
                    this.f2980b.m2826a(Util.parseJson(HttpUtils.openUrl2(this.f2980b.f2983c, "http://cgi.connect.qq.com/qqconnectopen/openapi/policy_conf", "GET", bundle).response));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.f2980b.f2987g = 0;
            }
        }.start();
    }

    private void m2826a(JSONObject jSONObject) {
        m2828b("cgi back, do update");
        this.f2985e = jSONObject;
        m2825a("com.tencent.open.config.json", jSONObject.toString());
        this.f2986f = SystemClock.elapsedRealtime();
    }

    private void m2829c() {
        int optInt = this.f2985e.optInt("Common_frequency");
        if (optInt == 0) {
            optInt = 1;
        }
        if (SystemClock.elapsedRealtime() - this.f2986f >= ((long) (optInt * 3600000))) {
            m2827b();
        }
    }

    public String getString(String str) {
        m2828b("get " + str);
        m2829c();
        return this.f2985e.optString(str);
    }

    public int getInt(String str) {
        m2828b("get " + str);
        m2829c();
        return this.f2985e.optInt(str);
    }

    public long getLong(String str) {
        m2828b("get " + str);
        m2829c();
        return this.f2985e.optLong(str);
    }

    public double getDouble(String str) {
        m2828b("get " + str);
        m2829c();
        return this.f2985e.optDouble(str, 0.0d);
    }

    public boolean getBoolean(String str) {
        m2828b("get " + str);
        m2829c();
        Object opt = this.f2985e.opt(str);
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

    public Object get(String str) {
        m2828b("get " + str);
        m2829c();
        return this.f2985e.opt(str);
    }

    private void m2828b(String str) {
        if (this.f2988h) {
            Log.i("OpenConfig", str + "; appid: " + this.f2984d);
        }
    }
}
