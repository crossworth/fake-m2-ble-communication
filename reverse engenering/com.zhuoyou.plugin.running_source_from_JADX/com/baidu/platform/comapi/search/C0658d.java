package com.baidu.platform.comapi.search;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.baidu.mapapi.MessageCenter;
import com.baidu.mapapi.model.inner.MapBound;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.platform.comjni.map.search.C0679a;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import java.util.Map;

public class C0658d {
    private static final String f2150a = C0658d.class.getSimpleName();
    private C0679a f2151b;
    private long f2152c;
    private C0657c f2153d;
    private Handler f2154e;
    private int f2155f;
    private Bundle f2156g;

    public C0658d() {
        this.f2151b = null;
        this.f2153d = null;
        this.f2154e = null;
        this.f2155f = 10;
        this.f2156g = null;
        this.f2151b = new C0679a();
        this.f2152c = this.f2151b.m2280a();
        this.f2153d = new C0657c();
        this.f2154e = new C0659e(this);
        MessageCenter.registMessage(2000, this.f2154e);
        this.f2153d.m2105a(this);
    }

    private Bundle m2107a(C0656a c0656a) {
        if (c0656a == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("type", c0656a.f2142a);
        bundle.putString("uid", c0656a.f2146e);
        if (c0656a.f2143b != null) {
            bundle.putInt("x", c0656a.f2143b.getmPtx());
            bundle.putInt("y", c0656a.f2143b.getmPty());
        }
        bundle.putString("keyword", c0656a.f2145d);
        return bundle;
    }

    private Bundle m2109c() {
        if (this.f2156g == null) {
            this.f2156g = new Bundle();
        } else {
            this.f2156g.clear();
        }
        return this.f2156g;
    }

    public void m2110a() {
        MessageCenter.unregistMessage(2000, this.f2154e);
        this.f2151b.m2287b();
        this.f2153d.m2102a();
        this.f2154e = null;
        this.f2151b = null;
        this.f2156g = null;
        this.f2153d = null;
        this.f2152c = 0;
    }

    public void m2111a(int i) {
        if (i > 0 && i <= 50) {
            this.f2155f = i;
        }
    }

    public void m2112a(C0518b c0518b) {
        this.f2153d.m2104a(c0518b);
    }

    public boolean m2113a(Point point) {
        if (point == null) {
            return false;
        }
        int i = point.getmPty();
        return this.f2151b.m2282a(point.getmPtx(), i);
    }

    public boolean m2114a(Point point, Point point2, String str, String str2, int i, int i2, int i3, int i4, int i5) {
        if (this.f2151b == null) {
            return false;
        }
        Bundle c = m2109c();
        if (i3 == 3) {
            c.putInt("cityCode", i4);
            c.putInt("pn", i5);
        } else {
            if (i >= 0) {
                c.putInt("startCode", i);
            }
            if (i >= 0) {
                c.putInt("endCode", i2);
            }
        }
        c.putInt("tn", i3);
        if (point != null) {
            c.putInt("startX", point.getmPtx());
            c.putInt("startY", point.getmPty());
        }
        if (point2 != null) {
            c.putInt("endX", point2.getmPtx());
            c.putInt("endY", point2.getmPty());
        }
        c.putString("strName", str);
        c.putString("endName", str2);
        return this.f2151b.m2300k(c);
    }

    public boolean m2115a(Point point, String str, String str2) {
        return (point == null || str == null || str2 == null) ? false : this.f2151b.m2283a(point.getmPtx(), point.getmPty(), str, str2);
    }

    public boolean m2116a(PlanNode planNode, PlanNode planNode2, String str, int i, int i2, int i3, int i4) {
        Bundle c = m2109c();
        if (planNode.getLocation() != null) {
            c.putString("origin", planNode.getLocation().latitude + "," + planNode.getLocation().longitude);
            if (!(planNode.getCity() == null || planNode.getCity() == "")) {
                c.putString("origin_region", planNode.getCity());
            }
        } else if (planNode.getName() == null || planNode.getName() == "" || planNode.getCity() == null || planNode.getCity() == "") {
            return false;
        } else {
            c.putString("origin", planNode.getName());
            c.putString("origin_region", planNode.getCity());
        }
        if (planNode2.getLocation() != null) {
            c.putString("destination", planNode2.getLocation().latitude + "," + planNode2.getLocation().longitude);
            if (!(planNode2.getCity() == null || planNode2.getCity() == "")) {
                c.putString("destination_region", planNode2.getCity());
            }
        } else if (planNode2.getName() == null || planNode2.getName() == "" || planNode2.getCity() == null || planNode2.getCity() == "") {
            return false;
        } else {
            c.putString("destination", planNode2.getName());
            c.putString("destination_region", planNode2.getCity());
        }
        if (!(str == null || str == "")) {
            c.putString("coord_type", str);
        }
        if (i < 0 || i > 5) {
            return false;
        }
        c.putInt("tactics_incity", i);
        if (i2 < 0 || i2 > 2) {
            return false;
        }
        c.putInt("tactics_intercity", i2);
        if (i3 < 0 || i3 > 2) {
            return false;
        }
        c.putInt("trans_type_intercity", i3);
        c.putInt("page_size", this.f2155f);
        c.putInt("page_index", i4);
        return this.f2151b.m2294e(c);
    }

    public boolean m2117a(C0656a c0656a, C0656a c0656a2, String str, MapBound mapBound, int i, int i2, Map<String, Object> map) {
        if (str == null || str.equals("")) {
            return false;
        }
        Bundle c = m2109c();
        Bundle a = m2107a(c0656a);
        Bundle a2 = m2107a(c0656a2);
        if (a == null || a2 == null) {
            return false;
        }
        c.putBundle("start", a);
        c.putBundle("end", a2);
        if (i2 < 3 || i2 > 6) {
            return false;
        }
        c.putInt("strategy", i2);
        c.putString("cityid", str);
        if (!(mapBound == null || mapBound.ptLB == null || mapBound.ptRT == null)) {
            Bundle bundle = new Bundle();
            bundle.putInt("level", i);
            bundle.putInt("ll_x", mapBound.ptLB.getmPtx());
            bundle.putInt("ll_y", mapBound.ptLB.getmPty());
            bundle.putInt("ru_x", mapBound.ptRT.getmPtx());
            bundle.putInt("ru_y", mapBound.ptRT.getmPty());
            c.putBundle("mapbound", bundle);
        }
        if (map != null) {
            bundle = new Bundle();
            for (Object next : map.keySet()) {
                bundle.putString(next.toString(), map.get(next).toString());
            }
            c.putBundle("extparams", bundle);
        }
        return this.f2151b.m2293d(c);
    }

    public boolean m2118a(C0656a c0656a, C0656a c0656a2, String str, String str2) {
        if (c0656a == null || c0656a2 == null) {
            return false;
        }
        if (c0656a.f2144c == null && (c0656a.f2145d == null || c0656a.f2145d.equals(""))) {
            return false;
        }
        if (c0656a2.f2144c == null && (c0656a2.f2145d == null || c0656a2.f2145d.equals(""))) {
            return false;
        }
        Bundle c = m2109c();
        c.putInt("starttype", c0656a.f2142a);
        if (c0656a.f2144c != null) {
            c.putDouble("startptx", c0656a.f2144c.longitude);
            c.putDouble("startpty", c0656a.f2144c.latitude);
        }
        c.putString("startkeyword", c0656a.f2145d);
        c.putString("startcity", str);
        c.putInt("endtype", c0656a2.f2142a);
        if (c0656a2.f2144c != null) {
            c.putDouble("endptx", c0656a2.f2144c.longitude);
            c.putDouble("endpty", c0656a2.f2144c.latitude);
        }
        c.putString("endkeyword", c0656a2.f2145d);
        c.putString("endcity", str2);
        return this.f2151b.m2297h(c);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean m2119a(com.baidu.platform.comapi.search.C0656a r13, com.baidu.platform.comapi.search.C0656a r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, com.baidu.mapapi.model.inner.MapBound r18, int r19, int r20, int r21, java.util.ArrayList<com.baidu.platform.comapi.search.C0660f> r22, java.util.Map<java.lang.String, java.lang.Object> r23) {
        /*
        r12 = this;
        if (r13 == 0) goto L_0x0004;
    L_0x0002:
        if (r14 != 0) goto L_0x0006;
    L_0x0004:
        r1 = 0;
    L_0x0005:
        return r1;
    L_0x0006:
        r1 = r13.f2143b;
        if (r1 != 0) goto L_0x0018;
    L_0x000a:
        if (r16 == 0) goto L_0x0016;
    L_0x000c:
        r1 = "";
        r0 = r16;
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0018;
    L_0x0016:
        r1 = 0;
        goto L_0x0005;
    L_0x0018:
        r1 = r14.f2143b;
        if (r1 != 0) goto L_0x002a;
    L_0x001c:
        if (r17 == 0) goto L_0x0028;
    L_0x001e:
        r1 = "";
        r0 = r17;
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x002a;
    L_0x0028:
        r1 = 0;
        goto L_0x0005;
    L_0x002a:
        r6 = r12.m2109c();
        r1 = "starttype";
        r2 = r13.f2142a;
        r6.putInt(r1, r2);
        r1 = r13.f2143b;
        if (r1 == 0) goto L_0x004f;
    L_0x0039:
        r1 = "startptx";
        r2 = r13.f2143b;
        r2 = r2.getmPtx();
        r6.putInt(r1, r2);
        r1 = "startpty";
        r2 = r13.f2143b;
        r2 = r2.getmPty();
        r6.putInt(r1, r2);
    L_0x004f:
        r1 = "startkeyword";
        r2 = r13.f2145d;
        r6.putString(r1, r2);
        r1 = "startuid";
        r2 = r13.f2146e;
        r6.putString(r1, r2);
        r1 = "endtype";
        r2 = r14.f2142a;
        r6.putInt(r1, r2);
        r1 = r14.f2143b;
        if (r1 == 0) goto L_0x007e;
    L_0x0068:
        r1 = "endptx";
        r2 = r14.f2143b;
        r2 = r2.getmPtx();
        r6.putInt(r1, r2);
        r1 = "endpty";
        r2 = r14.f2143b;
        r2 = r2.getmPty();
        r6.putInt(r1, r2);
    L_0x007e:
        r1 = "endkeyword";
        r2 = r14.f2145d;
        r6.putString(r1, r2);
        r1 = "enduid";
        r2 = r14.f2146e;
        r6.putString(r1, r2);
        r1 = "level";
        r0 = r19;
        r6.putInt(r1, r0);
        if (r18 == 0) goto L_0x00d5;
    L_0x0095:
        r0 = r18;
        r1 = r0.ptLB;
        if (r1 == 0) goto L_0x00d5;
    L_0x009b:
        r0 = r18;
        r1 = r0.ptRT;
        if (r1 == 0) goto L_0x00d5;
    L_0x00a1:
        r1 = "ll_x";
        r0 = r18;
        r2 = r0.ptLB;
        r2 = r2.getmPtx();
        r6.putInt(r1, r2);
        r1 = "ll_y";
        r0 = r18;
        r2 = r0.ptLB;
        r2 = r2.getmPty();
        r6.putInt(r1, r2);
        r1 = "ru_x";
        r0 = r18;
        r2 = r0.ptRT;
        r2 = r2.getmPtx();
        r6.putInt(r1, r2);
        r1 = "ru_y";
        r0 = r18;
        r2 = r0.ptRT;
        r2 = r2.getmPty();
        r6.putInt(r1, r2);
    L_0x00d5:
        r1 = "strategy";
        r0 = r20;
        r6.putInt(r1, r0);
        r1 = "cityid";
        r6.putString(r1, r15);
        r1 = "st_cityid";
        r0 = r16;
        r6.putString(r1, r0);
        r1 = "en_cityid";
        r0 = r17;
        r6.putString(r1, r0);
        r1 = "traffic";
        r0 = r21;
        r6.putInt(r1, r0);
        if (r22 == 0) goto L_0x01f4;
    L_0x00f8:
        r7 = r22.size();
        r3 = 0;
        r4 = "";
        r2 = "";
        r1 = 0;
        r5 = r1;
    L_0x0103:
        if (r5 >= r7) goto L_0x01ea;
    L_0x0105:
        r8 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x01e1 }
        r8.<init>();	 Catch:{ JSONException -> 0x01e1 }
        r0 = r22;
        r1 = r0.get(r5);	 Catch:{ JSONException -> 0x01e1 }
        r1 = (com.baidu.platform.comapi.search.C0660f) r1;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.f2158a;	 Catch:{ JSONException -> 0x01e1 }
        if (r1 == 0) goto L_0x01d9;
    L_0x0116:
        r1 = "type";
        r9 = 1;
        r8.put(r1, r9);	 Catch:{ JSONException -> 0x01e1 }
    L_0x011c:
        r9 = "keyword";
        r0 = r22;
        r1 = r0.get(r5);	 Catch:{ JSONException -> 0x01e1 }
        r1 = (com.baidu.platform.comapi.search.C0660f) r1;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.f2159b;	 Catch:{ JSONException -> 0x01e1 }
        r8.put(r9, r1);	 Catch:{ JSONException -> 0x01e1 }
        r0 = r22;
        r1 = r0.get(r5);	 Catch:{ JSONException -> 0x01e1 }
        r1 = (com.baidu.platform.comapi.search.C0660f) r1;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.f2158a;	 Catch:{ JSONException -> 0x01e1 }
        if (r1 == 0) goto L_0x0174;
    L_0x0137:
        r9 = "xy";
        r10 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x01e1 }
        r10.<init>();	 Catch:{ JSONException -> 0x01e1 }
        r0 = r22;
        r1 = r0.get(r5);	 Catch:{ JSONException -> 0x01e1 }
        r1 = (com.baidu.platform.comapi.search.C0660f) r1;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.f2158a;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.f1465x;	 Catch:{ JSONException -> 0x01e1 }
        r1 = java.lang.String.valueOf(r1);	 Catch:{ JSONException -> 0x01e1 }
        r1 = r10.append(r1);	 Catch:{ JSONException -> 0x01e1 }
        r10 = ",";
        r10 = r1.append(r10);	 Catch:{ JSONException -> 0x01e1 }
        r0 = r22;
        r1 = r0.get(r5);	 Catch:{ JSONException -> 0x01e1 }
        r1 = (com.baidu.platform.comapi.search.C0660f) r1;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.f2158a;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.f1466y;	 Catch:{ JSONException -> 0x01e1 }
        r1 = java.lang.String.valueOf(r1);	 Catch:{ JSONException -> 0x01e1 }
        r1 = r10.append(r1);	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.toString();	 Catch:{ JSONException -> 0x01e1 }
        r8.put(r9, r1);	 Catch:{ JSONException -> 0x01e1 }
    L_0x0174:
        r1 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x01e1 }
        r1.<init>();	 Catch:{ JSONException -> 0x01e1 }
        r9 = r1.append(r2);	 Catch:{ JSONException -> 0x01e1 }
        r0 = r22;
        r1 = r0.get(r5);	 Catch:{ JSONException -> 0x01e1 }
        r1 = (com.baidu.platform.comapi.search.C0660f) r1;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.f2160c;	 Catch:{ JSONException -> 0x01e1 }
        r1 = r9.append(r1);	 Catch:{ JSONException -> 0x01e1 }
        r1 = r1.toString();	 Catch:{ JSONException -> 0x01e1 }
        r2 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x022e }
        r2.<init>();	 Catch:{ JSONException -> 0x022e }
        r2 = r2.append(r4);	 Catch:{ JSONException -> 0x022e }
        r8 = r8.toString();	 Catch:{ JSONException -> 0x022e }
        r2 = r2.append(r8);	 Catch:{ JSONException -> 0x022e }
        r2 = r2.toString();	 Catch:{ JSONException -> 0x022e }
        r4 = r7 + -1;
        if (r3 == r4) goto L_0x01d0;
    L_0x01a8:
        r4 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x022c }
        r4.<init>();	 Catch:{ JSONException -> 0x022c }
        r4 = r4.append(r2);	 Catch:{ JSONException -> 0x022c }
        r8 = "|";
        r4 = r4.append(r8);	 Catch:{ JSONException -> 0x022c }
        r2 = r4.toString();	 Catch:{ JSONException -> 0x022c }
        r4 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x022c }
        r4.<init>();	 Catch:{ JSONException -> 0x022c }
        r4 = r4.append(r1);	 Catch:{ JSONException -> 0x022c }
        r8 = "|";
        r4 = r4.append(r8);	 Catch:{ JSONException -> 0x022c }
        r1 = r4.toString();	 Catch:{ JSONException -> 0x022c }
    L_0x01d0:
        r3 = r3 + 1;
    L_0x01d2:
        r4 = r5 + 1;
        r5 = r4;
        r4 = r2;
        r2 = r1;
        goto L_0x0103;
    L_0x01d9:
        r1 = "type";
        r9 = 2;
        r8.put(r1, r9);	 Catch:{ JSONException -> 0x01e1 }
        goto L_0x011c;
    L_0x01e1:
        r1 = move-exception;
        r11 = r1;
        r1 = r2;
        r2 = r4;
        r4 = r11;
    L_0x01e6:
        r4.printStackTrace();
        goto L_0x01d2;
    L_0x01ea:
        r1 = "wp";
        r6.putString(r1, r4);
        r1 = "wpc";
        r6.putString(r1, r2);
    L_0x01f4:
        if (r23 == 0) goto L_0x0224;
    L_0x01f6:
        r1 = new android.os.Bundle;
        r1.<init>();
        r2 = r23.keySet();
        r2 = r2.iterator();
    L_0x0203:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x021f;
    L_0x0209:
        r3 = r2.next();
        r0 = r23;
        r4 = r0.get(r3);
        r3 = r3.toString();
        r4 = r4.toString();
        r1.putString(r3, r4);
        goto L_0x0203;
    L_0x021f:
        r2 = "extparams";
        r6.putBundle(r2, r1);
    L_0x0224:
        r1 = r12.f2151b;
        r1 = r1.m2295f(r6);
        goto L_0x0005;
    L_0x022c:
        r4 = move-exception;
        goto L_0x01e6;
    L_0x022e:
        r2 = move-exception;
        r11 = r2;
        r2 = r4;
        r4 = r11;
        goto L_0x01e6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.search.d.a(com.baidu.platform.comapi.search.a, com.baidu.platform.comapi.search.a, java.lang.String, java.lang.String, java.lang.String, com.baidu.mapapi.model.inner.MapBound, int, int, int, java.util.ArrayList, java.util.Map):boolean");
    }

    public boolean m2120a(C0656a c0656a, C0656a c0656a2, String str, String str2, String str3, MapBound mapBound, int i, Map<String, Object> map) {
        if (c0656a == null || c0656a2 == null) {
            return false;
        }
        if (c0656a.f2143b == null && (str2 == null || str2.equals(""))) {
            return false;
        }
        if (c0656a2.f2143b == null && (str3 == null || str3.equals(""))) {
            return false;
        }
        Bundle c = m2109c();
        c.putInt("starttype", c0656a.f2142a);
        if (c0656a.f2143b != null) {
            c.putInt("startptx", c0656a.f2143b.getmPtx());
            c.putInt("startpty", c0656a.f2143b.getmPty());
        }
        c.putString("startkeyword", c0656a.f2145d);
        c.putString("startuid", c0656a.f2146e);
        c.putInt("endtype", c0656a2.f2142a);
        if (c0656a2.f2143b != null) {
            c.putInt("endptx", c0656a2.f2143b.getmPtx());
            c.putInt("endpty", c0656a2.f2143b.getmPty());
        }
        c.putString("endkeyword", c0656a2.f2145d);
        c.putString("enduid", c0656a2.f2146e);
        c.putInt("level", i);
        if (!(mapBound == null || mapBound.ptLB == null || mapBound.ptRT == null)) {
            c.putInt("ll_x", mapBound.ptLB.getmPtx());
            c.putInt("ll_y", mapBound.ptLB.getmPty());
            c.putInt("ru_x", mapBound.ptRT.getmPtx());
            c.putInt("ru_y", mapBound.ptRT.getmPty());
        }
        c.putString("cityid", str);
        c.putString("st_cityid", str2);
        c.putString("en_cityid", str3);
        if (map != null) {
            Bundle bundle = new Bundle();
            for (Object next : map.keySet()) {
                bundle.putString(next.toString(), map.get(next).toString());
            }
            c.putBundle("extparams", bundle);
        }
        return this.f2151b.m2296g(c);
    }

    public boolean m2121a(String str) {
        if (str == null) {
            return false;
        }
        String trim = str.trim();
        return (trim.length() == 0 || trim.length() > 99) ? false : this.f2151b.m2285a(trim);
    }

    public boolean m2122a(String str, int i, int i2, int i3, MapBound mapBound, MapBound mapBound2, Map<String, Object> map, int i4) {
        if (str == null) {
            return false;
        }
        String trim = str.trim();
        if (trim.length() == 0 || trim.length() > 99) {
            return false;
        }
        Bundle c = m2109c();
        c.putString("keyword", trim);
        c.putInt("pagenum", i2);
        c.putInt(ParamKey.COUNT, this.f2155f);
        c.putInt("cityid", i);
        c.putInt("level", i3);
        c.putInt("sortType", i4);
        if (mapBound2 != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("ll_x", mapBound2.ptLB.getmPtx());
            bundle.putInt("ll_y", mapBound2.ptLB.getmPty());
            bundle.putInt("ru_x", mapBound2.ptRT.getmPtx());
            bundle.putInt("ru_y", mapBound2.ptRT.getmPty());
            c.putBundle("mapbound", bundle);
        }
        if (mapBound != null) {
            c.putInt("ll_x", mapBound.ptLB.getmPtx());
            c.putInt("ll_y", mapBound.ptLB.getmPty());
            c.putInt("ru_x", mapBound.ptRT.getmPtx());
            c.putInt("ru_y", mapBound.ptRT.getmPty());
            c.putInt("loc_x", (mapBound.ptLB.getmPtx() + mapBound.ptRT.getmPtx()) / 2);
            c.putInt("loc_y", (mapBound.ptLB.getmPty() + mapBound.ptRT.getmPty()) / 2);
        }
        if (map != null) {
            bundle = new Bundle();
            for (Object next : map.keySet()) {
                bundle.putString(next.toString(), map.get(next).toString());
            }
            c.putBundle("extparams", bundle);
        }
        return this.f2151b.m2288b(c);
    }

    public boolean m2123a(String str, int i, int i2, MapBound mapBound, int i3, Point point, Map<String, Object> map) {
        if (mapBound == null || str == null) {
            return false;
        }
        String trim = str.trim();
        if (trim.length() == 0 || trim.length() > 99) {
            return false;
        }
        Bundle c = m2109c();
        c.putString("keyword", trim);
        c.putInt("pagenum", i2);
        c.putInt(ParamKey.COUNT, this.f2155f);
        c.putString("cityid", String.valueOf(i));
        c.putInt("level", i3);
        if (mapBound != null) {
            c.putInt("ll_x", mapBound.ptLB.getmPtx());
            c.putInt("ll_y", mapBound.ptLB.getmPty());
            c.putInt("ru_x", mapBound.ptRT.getmPtx());
            c.putInt("ru_y", mapBound.ptRT.getmPty());
        }
        if (point != null) {
            c.putInt("loc_x", point.f1465x);
            c.putInt("loc_y", point.f1466y);
        }
        if (map != null) {
            Bundle bundle = new Bundle();
            for (Object next : map.keySet()) {
                bundle.putString(next.toString(), map.get(next).toString());
            }
            c.putBundle("extparams", bundle);
        }
        return this.f2151b.m2301l(c);
    }

    public boolean m2124a(String str, int i, String str2, MapBound mapBound, int i2, Point point) {
        if (str == null) {
            return false;
        }
        if (i != 0 && i != 2) {
            return false;
        }
        String trim = str.trim();
        if (trim.length() == 0 || trim.length() > 99) {
            return false;
        }
        Bundle c = m2109c();
        c.putString("keyword", str);
        c.putInt("type", i);
        c.putString("cityid", str2);
        Bundle bundle = new Bundle();
        bundle.putInt("level", i2);
        c.putBundle("mapbound", bundle);
        if (point != null) {
            c.putInt("loc_x", point.f1465x);
            c.putInt("loc_y", point.f1466y);
        }
        return this.f2151b.m2299j(c);
    }

    public boolean m2125a(String str, String str2) {
        if (str2 == null || str == null || str.equals("")) {
            return false;
        }
        String trim = str2.trim();
        return (trim.length() == 0 || trim.length() > 99) ? false : this.f2151b.m2286a(str, trim);
    }

    public boolean m2126a(String str, String str2, int i, int i2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("bid", str);
        bundle.putString("wd", str2);
        bundle.putInt("currentPage", i);
        bundle.putInt("pageSize", i2);
        if (str3 != null && str3.length() > 0) {
            bundle.putString("floor", str3);
        }
        return this.f2151b.m2291c(bundle);
    }

    public boolean m2127a(String str, String str2, int i, MapBound mapBound, int i2, Map<String, Object> map) {
        if (str == null) {
            return false;
        }
        String trim = str.trim();
        if (trim.length() == 0 || trim.length() > 99) {
            return false;
        }
        Bundle c = m2109c();
        c.putString("keyword", trim);
        c.putInt("pagenum", i);
        c.putInt(ParamKey.COUNT, this.f2155f);
        c.putString("cityid", str2);
        c.putInt("level", i2);
        if (mapBound != null) {
            c.putInt("ll_x", mapBound.ptLB.getmPtx());
            c.putInt("ll_y", mapBound.ptLB.getmPty());
            c.putInt("ru_x", mapBound.ptRT.getmPtx());
            c.putInt("ru_y", mapBound.ptRT.getmPty());
        }
        if (map != null) {
            Bundle bundle = new Bundle();
            for (Object next : map.keySet()) {
                bundle.putString(next.toString(), map.get(next).toString());
            }
            c.putBundle("extparams", bundle);
        }
        return this.f2151b.m2284a(c);
    }

    public boolean m2128a(String str, String str2, Bundle bundle) {
        if (str == null || str2 == null) {
            return false;
        }
        Bundle c = m2109c();
        c.putString("start", str);
        c.putString("end", str2);
        if (bundle != null) {
            c.putBundle("extparams", bundle);
        }
        return this.f2151b.m2298i(c);
    }

    public int m2129b() {
        return this.f2155f;
    }

    String m2130b(int i) {
        String a = this.f2151b.m2281a(i);
        return (a == null || a.trim().length() > 0) ? a : null;
    }

    public boolean m2131b(String str) {
        return str == null ? false : this.f2151b.m2289b(str);
    }

    public boolean m2132b(String str, String str2) {
        return this.f2151b.m2290b(str, str2);
    }

    public boolean m2133c(String str, String str2) {
        return (this.f2151b == null || TextUtils.isEmpty(str)) ? false : this.f2151b.m2292c(str, str2);
    }
}
