package com.tencent.healthsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.healthsdk.C1188a.C1187a;
import java.util.Calendar;
import org.json.JSONObject;

/* compiled from: QQHealthHttpUtils */
final class C1189b implements Runnable {
    final /* synthetic */ Context f3707a;
    final /* synthetic */ QQHealthCallback f3708b;

    C1189b(Context context, QQHealthCallback qQHealthCallback) {
        this.f3707a = context;
        this.f3708b = qQHealthCallback;
    }

    public void run() {
        C1187a c1187a = null;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ret", QQHealthManager.RET_UNKNOWN_ERROR);
            jSONObject.put("msg", "unknown error");
            if (this.f3707a == null) {
                QQHealthManager.m3474b("QQHealthHttpUtils", "context is null, upload data fail!");
            } else if (this.f3708b == null) {
                QQHealthManager.m3474b("QQHealthHttpUtils", "call back is null, upload data fail!");
            } else {
                String str = "";
                SharedPreferences sharedPreferences = this.f3707a.getSharedPreferences("Local_Health", 0);
                try {
                    String healthData = this.f3708b.getHealthData();
                    if (TextUtils.isEmpty(healthData)) {
                        jSONObject.put("ret", QQHealthManager.RET_HEALTH_DATA_IS_NULL);
                        jSONObject.put("msg", "health data is null!");
                        this.f3708b.onComplete(jSONObject);
                        return;
                    }
                    C1187a a = C1188a.m3478a(healthData);
                    if (a == null) {
                        jSONObject.put("ret", QQHealthManager.RET_HEALTH_DATA_IS_WRONG);
                        jSONObject.put("msg", "health data is wrong!");
                        this.f3708b.onComplete(jSONObject);
                        return;
                    }
                    Calendar instance = Calendar.getInstance();
                    String format = C1188a.f3705e.format(instance.getTime());
                    boolean a2 = C1188a.m3482a(a, this.f3708b);
                    if (sharedPreferences == null) {
                        return;
                    }
                    if (a2) {
                        C1187a a3;
                        sharedPreferences.edit().remove(format).commit();
                        instance.add(5, -1);
                        healthData = C1188a.f3705e.format(instance.getTime());
                        if (sharedPreferences.contains(healthData)) {
                            a3 = C1188a.m3478a(sharedPreferences.getString(healthData, ""));
                        } else {
                            a3 = null;
                        }
                        instance.add(5, -1);
                        String format2 = C1188a.f3705e.format(instance.getTime());
                        if (sharedPreferences.contains(format2)) {
                            c1187a = C1188a.m3478a(sharedPreferences.getString(format2, ""));
                        }
                        sharedPreferences.edit().clear().commit();
                        if (!(C1188a.m3481a(c1187a) || c1187a == null)) {
                            sharedPreferences.edit().putString(format2, c1187a.toString()).commit();
                        }
                        if (!C1188a.m3481a(a3) && a3 != null) {
                            sharedPreferences.edit().putString(healthData, a3.toString()).commit();
                            return;
                        }
                        return;
                    }
                    sharedPreferences.edit().putString(format, healthData).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    jSONObject.put("ret", QQHealthManager.RET_UNKNOWN_ERROR);
                    jSONObject.put("msg", e.toString());
                    this.f3708b.onComplete(jSONObject);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
