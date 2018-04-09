package com.tencent.map.p013b;

import android.location.Location;
import org.json.JSONException;
import org.json.JSONObject;

public class C0722b {
    private static C0722b f2496b;
    public String f2497a = "";
    private double f2498c = 0.0d;
    private double f2499d = 0.0d;
    private double f2500e = 0.0d;
    private double f2501f = 0.0d;
    private double f2502g = 0.0d;
    private double f2503h = 0.0d;
    private C0720a f2504i;
    private C0721b f2505j = null;
    private boolean f2506k = false;

    public interface C0720a {
        void mo2097a(double d, double d2);
    }

    public class C0721b extends Thread {
        private /* synthetic */ C0722b f2495a;

        public C0721b(C0722b c0722b) {
            this.f2495a = c0722b;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
            r8 = this;
            r6 = 4645040803167600640; // 0x4076800000000000 float:0.0 double:360.0;
            r1 = 0;
            r0 = r8.f2495a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.f2497a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.getBytes();	 Catch:{ Exception -> 0x0034 }
            r0 = com.tencent.map.p013b.C0752r.m2485a(r0);	 Catch:{ Exception -> 0x0034 }
            r2 = r8.f2495a;	 Catch:{ Exception -> 0x0034 }
            r3 = 1;
            r2.f2506k = r3;	 Catch:{ Exception -> 0x0034 }
            r2 = "http://ls.map.soso.com/deflect?c=1";
            r3 = "SOSO MAP LBS SDK";
            r0 = com.tencent.map.p013b.C0722b.m2406a(r2, r3, r0);	 Catch:{ Exception -> 0x0034 }
            r2 = r8.f2495a;	 Catch:{ Exception -> 0x0034 }
            r3 = 0;
            r2.f2506k = r3;	 Catch:{ Exception -> 0x0034 }
            r2 = r0.f2630a;	 Catch:{ Exception -> 0x0034 }
            r2 = com.tencent.map.p013b.C0752r.m2486b(r2);	 Catch:{ Exception -> 0x0034 }
            r3 = r8.f2495a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.f2631b;	 Catch:{ Exception -> 0x0034 }
            com.tencent.map.p013b.C0722b.m2407a(r3, r2, r0);	 Catch:{ Exception -> 0x0034 }
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
            com.tencent.map.p013b.C0722b.C0721b.sleep(r2);	 Catch:{ Exception -> 0x0068 }
            r2 = r8.f2495a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.f2497a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.getBytes();	 Catch:{ Exception -> 0x0068 }
            r2 = com.tencent.map.p013b.C0752r.m2485a(r2);	 Catch:{ Exception -> 0x0068 }
            r3 = "http://ls.map.soso.com/deflect?c=1";
            r4 = "SOSO MAP LBS SDK";
            r2 = com.tencent.map.p013b.C0722b.m2406a(r3, r4, r2);	 Catch:{ Exception -> 0x0068 }
            r3 = r8.f2495a;	 Catch:{ Exception -> 0x0068 }
            r4 = 0;
            r3.f2506k = r4;	 Catch:{ Exception -> 0x0068 }
            r3 = r2.f2630a;	 Catch:{ Exception -> 0x0068 }
            r3 = com.tencent.map.p013b.C0752r.m2486b(r3);	 Catch:{ Exception -> 0x0068 }
            r4 = r8.f2495a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.f2631b;	 Catch:{ Exception -> 0x0068 }
            com.tencent.map.p013b.C0722b.m2407a(r4, r3, r2);	 Catch:{ Exception -> 0x0068 }
            goto L_0x0033;
        L_0x0068:
            r2 = move-exception;
            goto L_0x0036;
        L_0x006a:
            r0 = r8.f2495a;
            r0.f2506k = r1;
            r0 = r8.f2495a;
            r0 = r0.f2504i;
            if (r0 == 0) goto L_0x0033;
        L_0x0077:
            r0 = r8.f2495a;
            r0 = r0.f2504i;
            r0.mo2097a(r6, r6);
            goto L_0x0033;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.b.b.run():void");
        }
    }

    public static C0722b m2405a() {
        if (f2496b == null) {
            f2496b = new C0722b();
        }
        return f2496b;
    }

    static /* synthetic */ void m2407a(C0722b c0722b, byte[] bArr, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new String(bArr, str));
        } catch (Exception e) {
            if (c0722b.f2504i != null) {
                c0722b.f2504i.mo2097a(360.0d, 360.0d);
            }
        }
        try {
            JSONObject jSONObject = new JSONObject(stringBuffer.toString()).getJSONObject("location");
            double d = jSONObject.getDouble("latitude");
            double d2 = jSONObject.getDouble("longitude");
            c0722b.f2502g = d - c0722b.f2500e;
            c0722b.f2503h = d2 - c0722b.f2501f;
            c0722b.f2498c = c0722b.f2500e;
            c0722b.f2499d = c0722b.f2501f;
            if (c0722b.f2504i != null) {
                c0722b.f2504i.mo2097a(d, d2);
            }
        } catch (JSONException e2) {
            if (c0722b.f2504i != null) {
                c0722b.f2504i.mo2097a(360.0d, 360.0d);
            }
        }
    }

    public final void m2410a(double d, double d2, C0720a c0720a) {
        this.f2504i = c0720a;
        if (!(this.f2502g == 0.0d || this.f2503h == 0.0d)) {
            float[] fArr = new float[10];
            Location.distanceBetween(d, d2, this.f2498c, this.f2499d, fArr);
            if (fArr[0] < 1500.0f) {
                this.f2504i.mo2097a(this.f2502g + d, this.f2503h + d2);
                return;
            }
        }
        if (!this.f2506k) {
            this.f2497a = "{\"source\":101,\"access_token\":\"160e7bd42dec9428721034e0146fc6dd\",\"location\":{\"latitude\":" + d + ",\"longitude\":" + d2 + "}\t}";
            this.f2500e = d;
            this.f2501f = d2;
            this.f2505j = new C0721b(this);
            this.f2505j.start();
        }
    }

    public static boolean m2409a(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static C0755u m2406a(String str, String str2, byte[] bArr) throws C0740k, C0737h, Exception {
        Object obj = 1;
        if (C0754t.m2499b() == null) {
            obj = null;
        }
        if (obj == null) {
            throw new C0740k();
        }
        try {
            return C0738i.m2464a(false, str, str2, null, bArr, false, true);
        } catch (Exception e) {
            throw e;
        }
    }
}
