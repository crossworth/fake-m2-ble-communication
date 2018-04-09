package com.tencent.map.p028b;

import android.location.Location;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.umeng.socialize.common.SocializeConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class C1198b {
    private static C1198b f3751b;
    public String f3752a = "";
    private double f3753c = 0.0d;
    private double f3754d = 0.0d;
    private double f3755e = 0.0d;
    private double f3756f = 0.0d;
    private double f3757g = 0.0d;
    private double f3758h = 0.0d;
    private C1196a f3759i;
    private C1197b f3760j = null;
    private boolean f3761k = false;

    public interface C1196a {
        void mo2153a(double d, double d2);
    }

    public class C1197b extends Thread {
        private /* synthetic */ C1198b f3750a;

        public C1197b(C1198b c1198b) {
            this.f3750a = c1198b;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
            r8 = this;
            r6 = 4645040803167600640; // 0x4076800000000000 float:0.0 double:360.0;
            r1 = 0;
            r0 = r8.f3750a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.f3752a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.getBytes();	 Catch:{ Exception -> 0x0034 }
            r0 = com.tencent.map.p028b.C1224j.m3632a(r0);	 Catch:{ Exception -> 0x0034 }
            r2 = r8.f3750a;	 Catch:{ Exception -> 0x0034 }
            r3 = 1;
            r2.f3761k = r3;	 Catch:{ Exception -> 0x0034 }
            r2 = "http://ls.map.soso.com/deflect?c=1";
            r3 = "SOSO MAP LBS SDK";
            r0 = com.tencent.map.p028b.C1198b.m3505a(r2, r3, r0);	 Catch:{ Exception -> 0x0034 }
            r2 = r8.f3750a;	 Catch:{ Exception -> 0x0034 }
            r3 = 0;
            r2.f3761k = r3;	 Catch:{ Exception -> 0x0034 }
            r2 = r0.f3927a;	 Catch:{ Exception -> 0x0034 }
            r2 = com.tencent.map.p028b.C1224j.m3633b(r2);	 Catch:{ Exception -> 0x0034 }
            r3 = r8.f3750a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.f3928b;	 Catch:{ Exception -> 0x0034 }
            com.tencent.map.p028b.C1198b.m3506a(r3, r2, r0);	 Catch:{ Exception -> 0x0034 }
        L_0x0033:
            return;
        L_0x0034:
            r0 = move-exception;
            r0 = r1;
        L_0x0036:
            r0 = r0 + 1;
            r2 = 3;
            if (r0 > r2) goto L_0x006a;
        L_0x003b:
            r2 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
            com.tencent.map.p028b.C1198b.C1197b.sleep(r2);	 Catch:{ Exception -> 0x0068 }
            r2 = r8.f3750a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.f3752a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.getBytes();	 Catch:{ Exception -> 0x0068 }
            r2 = com.tencent.map.p028b.C1224j.m3632a(r2);	 Catch:{ Exception -> 0x0068 }
            r3 = "http://ls.map.soso.com/deflect?c=1";
            r4 = "SOSO MAP LBS SDK";
            r2 = com.tencent.map.p028b.C1198b.m3505a(r3, r4, r2);	 Catch:{ Exception -> 0x0068 }
            r3 = r8.f3750a;	 Catch:{ Exception -> 0x0068 }
            r4 = 0;
            r3.f3761k = r4;	 Catch:{ Exception -> 0x0068 }
            r3 = r2.f3927a;	 Catch:{ Exception -> 0x0068 }
            r3 = com.tencent.map.p028b.C1224j.m3633b(r3);	 Catch:{ Exception -> 0x0068 }
            r4 = r8.f3750a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.f3928b;	 Catch:{ Exception -> 0x0068 }
            com.tencent.map.p028b.C1198b.m3506a(r4, r3, r2);	 Catch:{ Exception -> 0x0068 }
            goto L_0x0033;
        L_0x0068:
            r2 = move-exception;
            goto L_0x0036;
        L_0x006a:
            r0 = r8.f3750a;
            r0.f3761k = r1;
            r0 = r8.f3750a;
            r0 = r0.f3759i;
            if (r0 == 0) goto L_0x0033;
        L_0x0077:
            r0 = r8.f3750a;
            r0 = r0.f3759i;
            r0.mo2153a(r6, r6);
            goto L_0x0033;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.b.b.run():void");
        }
    }

    public static C1198b m3504a() {
        if (f3751b == null) {
            f3751b = new C1198b();
        }
        return f3751b;
    }

    static /* synthetic */ void m3506a(C1198b c1198b, byte[] bArr, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new String(bArr, str));
        } catch (Exception e) {
            if (c1198b.f3759i != null) {
                c1198b.f3759i.mo2153a(360.0d, 360.0d);
            }
        }
        try {
            JSONObject jSONObject = new JSONObject(stringBuffer.toString()).getJSONObject(SocializeConstants.KEY_LOCATION);
            double d = jSONObject.getDouble(ParamKey.LATITUDE);
            double d2 = jSONObject.getDouble(ParamKey.LONGITUDE);
            c1198b.f3757g = d - c1198b.f3755e;
            c1198b.f3758h = d2 - c1198b.f3756f;
            c1198b.f3753c = c1198b.f3755e;
            c1198b.f3754d = c1198b.f3756f;
            if (c1198b.f3759i != null) {
                c1198b.f3759i.mo2153a(d, d2);
            }
        } catch (JSONException e2) {
            if (c1198b.f3759i != null) {
                c1198b.f3759i.mo2153a(360.0d, 360.0d);
            }
        }
    }

    public final void m3509a(double d, double d2, C1196a c1196a) {
        this.f3759i = c1196a;
        if (!(this.f3757g == 0.0d || this.f3758h == 0.0d)) {
            float[] fArr = new float[10];
            Location.distanceBetween(d, d2, this.f3753c, this.f3754d, fArr);
            if (fArr[0] < 1500.0f) {
                this.f3759i.mo2153a(this.f3757g + d, this.f3758h + d2);
                return;
            }
        }
        if (!this.f3761k) {
            this.f3752a = "{\"source\":101,\"access_token\":\"160e7bd42dec9428721034e0146fc6dd\",\"location\":{\"latitude\":" + d + ",\"longitude\":" + d2 + "}\t}";
            this.f3755e = d;
            this.f3756f = d2;
            this.f3760j = new C1197b(this);
            this.f3760j.start();
        }
    }

    public static boolean m3508a(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static C1229n m3505a(String str, String str2, byte[] bArr) throws C1230o, C1233r, Exception {
        Object obj = 1;
        if (C1227l.m3645b() == null) {
            obj = null;
        }
        if (obj == null) {
            throw new C1230o();
        }
        try {
            return C1232q.m3651a(false, str, str2, null, bArr, false, true);
        } catch (Exception e) {
            throw e;
        }
    }
}
