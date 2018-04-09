package com.amap.api.location;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import com.amap.api.location.C0182a.C0181a;
import com.amap.api.location.core.AMapLocException;
import com.amap.api.location.core.C0186a;
import com.amap.api.location.core.C0188c;
import com.aps.C0441b;
import com.aps.C0442c;
import com.aps.C0454j;
import com.aps.C0455k;
import com.aps.C1613a;
import com.tencent.open.SocialConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: IAPSManager */
public class C0185c implements Runnable {
    C0455k f76a = null;
    boolean f77b = true;
    private boolean f78c = true;
    private Thread f79d = null;
    private Context f80e;
    private long f81f = 2000;
    private C0181a f82g;
    private C0182a f83h;

    protected C0185c(Context context, C0181a c0181a, C0182a c0182a) {
        this.f83h = c0182a;
        this.f78c = true;
        this.f80e = context;
        this.f76a = new C1613a();
        C0188c.m84a(context);
        this.f76a.mo1795a(context, c0182a.f56a);
        this.f76a.mo1798a("api_serverSDK_130905##S128DF1572465B890OE3F7A13167KLEI##" + C0188c.m88b(context) + SeparatorConstants.SEPARATOR_ADS_ID + C0188c.m87b());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("key", C0188c.m88b(context));
            jSONObject.put("X-INFO", C0188c.m84a(context).m98a("loc"));
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("ex", C0441b.m1837a(C0188c.m84a(context).m100c().getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            jSONObject.put("X-BIZ", jSONObject2);
            jSONObject.put("User-Agent", "AMAP Location SDK Android 1.3.0");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        this.f76a.mo1799a(jSONObject);
        this.f82g = c0181a;
    }

    void m69a() {
        this.f78c = false;
        if (this.f79d != null) {
            this.f79d.interrupt();
        }
        this.f76a.mo1800b();
        this.f76a = null;
    }

    public void run() {
        Looper.prepare();
        this.f79d = Thread.currentThread();
        while (this.f78c && !Thread.currentThread().isInterrupted()) {
            this.f79d = Thread.currentThread();
            Message message;
            try {
                if ((!this.f83h.f58c || m68d()) && this.f83h.f60e) {
                    Object a;
                    C0442c b = m66b();
                    if (b != null) {
                        a = m63a(b);
                    } else {
                        a = null;
                    }
                    if (a != null) {
                        if (this.f83h.f60e && (!this.f83h.f58c || m68d())) {
                            Message message2 = new Message();
                            message2.obj = a;
                            message2.what = 100;
                            this.f82g.sendMessage(message2);
                        }
                    }
                    if (C0186a.m76a() == -1) {
                        C0186a.m78a(this.f80e);
                    }
                    if (this.f77b) {
                        Thread.sleep(this.f81f);
                    } else {
                        Thread.sleep(30000);
                    }
                } else {
                    try {
                        this.f77b = true;
                        Thread.sleep(this.f81f);
                        if (null != null) {
                            if (this.f83h.f60e && (!this.f83h.f58c || m68d())) {
                                message = new Message();
                                message.obj = null;
                                message.what = 100;
                                this.f82g.sendMessage(message);
                            }
                        }
                        if (C0186a.m76a() == -1) {
                            C0186a.m78a(this.f80e);
                        }
                        if (this.f77b) {
                            Thread.sleep(this.f81f);
                        } else {
                            Thread.sleep(30000);
                        }
                    } catch (Throwable th) {
                        if (null != null) {
                            if (this.f83h.f60e && (!this.f83h.f58c || m68d())) {
                                message = new Message();
                                message.obj = null;
                                message.what = 100;
                                this.f82g.sendMessage(message);
                            }
                        }
                        if (C0186a.m76a() == -1) {
                            C0186a.m78a(this.f80e);
                        }
                        if (this.f77b) {
                            Thread.sleep(this.f81f);
                            return;
                        } else {
                            Thread.sleep(30000);
                            return;
                        }
                    }
                }
            } catch (Throwable th2) {
                return;
            }
        }
    }

    void m70a(long j) {
        if (j > this.f81f) {
            this.f81f = j;
        }
    }

    private C0442c m66b() throws Exception {
        C0442c c = m67c();
        if (c != null) {
            return c;
        }
        c = new C0442c();
        c.m1856a(new AMapLocException("未知的错误"));
        this.f77b = false;
        return c;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.aps.C0442c m67c() {
        /*
        r4 = this;
        r2 = 0;
        r1 = 0;
        r0 = r4.f76a;	 Catch:{ AMapLocException -> 0x0016, Throwable -> 0x0023 }
        if (r0 == 0) goto L_0x002f;
    L_0x0006:
        r0 = r4.f76a;	 Catch:{ AMapLocException -> 0x0016, Throwable -> 0x0023 }
        r0 = r0.mo1793a();	 Catch:{ AMapLocException -> 0x0016, Throwable -> 0x0023 }
    L_0x000c:
        if (r0 != 0) goto L_0x0012;
    L_0x000e:
        r1 = 0;
        r4.f77b = r1;	 Catch:{ AMapLocException -> 0x0016, Throwable -> 0x002d }
    L_0x0011:
        return r0;
    L_0x0012:
        r1 = 1;
        r4.f77b = r1;	 Catch:{ AMapLocException -> 0x0016, Throwable -> 0x002d }
        goto L_0x0011;
    L_0x0016:
        r0 = move-exception;
        r1 = r0;
        r0 = new com.aps.c;
        r0.<init>();
        r0.m1856a(r1);
        r4.f77b = r2;
        goto L_0x0011;
    L_0x0023:
        r0 = move-exception;
        r3 = r0;
        r0 = r1;
        r1 = r3;
    L_0x0027:
        r4.f77b = r2;
        r1.printStackTrace();
        goto L_0x0011;
    L_0x002d:
        r1 = move-exception;
        goto L_0x0027;
    L_0x002f:
        r0 = r1;
        goto L_0x000c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.location.c.c():com.aps.c");
    }

    private boolean m68d() {
        if (System.currentTimeMillis() - this.f83h.f59d <= 5 * this.f81f) {
            return false;
        }
        this.f83h.f58c = false;
        return true;
    }

    private AMapLocation m63a(C0442c c0442c) {
        AMapLocation aMapLocation = new AMapLocation("");
        aMapLocation.setProvider(LocationProviderProxy.AMapNetwork);
        aMapLocation.setLatitude(c0442c.m1866e());
        aMapLocation.setLongitude(c0442c.m1864d());
        aMapLocation.setAccuracy(c0442c.m1868f());
        aMapLocation.setTime(c0442c.m1870g());
        aMapLocation.setPoiId(c0442c.m1859b());
        aMapLocation.setFloor(c0442c.m1862c());
        aMapLocation.setAMapException(c0442c.m1852a());
        Bundle bundle = new Bundle();
        bundle.putString("citycode", c0442c.m1876j());
        bundle.putString(SocialConstants.PARAM_APP_DESC, c0442c.m1878k());
        bundle.putString("adcode", c0442c.m1880l());
        aMapLocation.setExtras(bundle);
        try {
            m64a(aMapLocation, c0442c.m1876j(), c0442c.m1880l(), c0442c.m1878k());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return aMapLocation;
    }

    private void m64a(AMapLocation aMapLocation, String str, String str2, String str3) {
        String[] split = str3.split(" ");
        aMapLocation.setCityCode(str);
        aMapLocation.setAdCode(str2);
        if (str.equals("") || !m65a(str)) {
            if (split.length > 3) {
                aMapLocation.setProvince(split[0]);
                aMapLocation.setCity(split[1]);
                aMapLocation.setDistrict(split[2]);
                aMapLocation.m29a(split[3]);
            }
            if (split.length > 4) {
                aMapLocation.m29a(split[3] + split[4]);
            }
        } else {
            if (split.length > 2) {
                aMapLocation.setCity(split[0]);
                aMapLocation.setDistrict(split[1]);
                aMapLocation.m29a(split[2]);
            }
            if (split.length > 3) {
                aMapLocation.m29a(split[2] + split[3]);
            }
        }
        aMapLocation.m30b(str3.replace(" ", ""));
    }

    private boolean m65a(String str) {
        if (str.endsWith("010") || str.endsWith("021") || str.endsWith("022") || str.endsWith("023")) {
            return true;
        }
        return false;
    }

    void m72a(C0454j c0454j, PendingIntent pendingIntent) {
        this.f76a.mo1797a(c0454j, pendingIntent);
    }

    void m74b(C0454j c0454j, PendingIntent pendingIntent) {
        this.f76a.mo1802b(c0454j, pendingIntent);
    }

    void m71a(PendingIntent pendingIntent) {
        this.f76a.mo1794a(pendingIntent);
    }

    void m73b(PendingIntent pendingIntent) {
        this.f76a.mo1801b(pendingIntent);
    }
}
