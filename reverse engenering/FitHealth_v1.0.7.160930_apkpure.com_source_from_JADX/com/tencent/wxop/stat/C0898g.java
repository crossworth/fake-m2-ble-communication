package com.tencent.wxop.stat;

import android.content.Context;
import android.content.IntentFilter;
import com.tencent.wxop.stat.p023b.C0877b;
import com.tencent.wxop.stat.p023b.C0881f;
import com.tencent.wxop.stat.p023b.C0885l;
import com.tencent.wxop.stat.p023b.C0891r;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import org.apache.http.HttpHost;
import org.json.JSONObject;

public class C0898g {
    private static C0898g bg = null;
    private List<String> bc = null;
    private volatile HttpHost bd = null;
    private C0881f be = null;
    private int bf = 0;
    private Context bh = null;
    private C0877b bi = null;
    private volatile String f3067c = "";
    private volatile int f3068g = 2;

    private C0898g(Context context) {
        this.bh = context.getApplicationContext();
        this.be = new C0881f();
        ak.m2845j(context);
        this.bi = C0885l.av();
        m3009Y();
        this.bc = new ArrayList(10);
        this.bc.add("117.135.169.101");
        this.bc.add("140.207.54.125");
        this.bc.add("180.153.8.53");
        this.bc.add("120.198.203.175");
        this.bc.add("14.17.43.18");
        this.bc.add("163.177.71.186");
        this.bc.add("111.30.131.31");
        this.bc.add("123.126.121.167");
        this.bc.add("123.151.152.111");
        this.bc.add("113.142.45.79");
        this.bc.add("123.138.162.90");
        this.bc.add("103.7.30.94");
        m3018Z();
    }

    private String m3008O() {
        try {
            String str = "pingma.qq.com";
            if (!C0898g.m3011d(str)) {
                return InetAddress.getByName(str).getHostAddress();
            }
        } catch (Throwable e) {
            this.bi.m2852b(e);
        }
        return "";
    }

    private void m3009Y() {
        this.f3068g = 0;
        this.bd = null;
        this.f3067c = null;
    }

    private static boolean m3011d(String str) {
        return Pattern.compile("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})").matcher(str).matches();
    }

    public static C0898g m3012r(Context context) {
        if (bg == null) {
            synchronized (C0898g.class) {
                if (bg == null) {
                    bg = new C0898g(context);
                }
            }
        }
        return bg;
    }

    public final int m3013D() {
        return this.f3068g;
    }

    public final void m3014I() {
        this.bf = (this.bf + 1) % this.bc.size();
    }

    public final HttpHost m3015V() {
        return this.bd;
    }

    public final boolean m3016W() {
        return this.f3068g == 1;
    }

    public final boolean m3017X() {
        return this.f3068g != 0;
    }

    final void m3018Z() {
        if (C0891r.m2916W(this.bh)) {
            if (C0894c.ad) {
                String O = m3008O();
                if (C0894c.m2949k()) {
                    this.bi.m2851b("remoteIp ip is " + O);
                }
                if (C0885l.m2894e(O)) {
                    String str;
                    if (this.bc.contains(O)) {
                        str = O;
                    } else {
                        str = (String) this.bc.get(this.bf);
                        if (C0894c.m2949k()) {
                            this.bi.m2853c(O + " not in ip list, change to:" + str);
                        }
                    }
                    C0894c.m2957o("http://" + str + ":80/mstat/report");
                }
            }
            this.f3067c = C0885l.m2871E(this.bh);
            if (C0894c.m2949k()) {
                this.bi.m2851b("NETWORK name:" + this.f3067c);
            }
            if (C0885l.m2894e(this.f3067c)) {
                if ("WIFI".equalsIgnoreCase(this.f3067c)) {
                    this.f3068g = 1;
                } else {
                    this.f3068g = 2;
                }
                this.bd = C0885l.m2898v(this.bh);
            }
            if (C0896e.m2986a()) {
                C0896e.m2996n(this.bh);
                return;
            }
            return;
        }
        if (C0894c.m2949k()) {
            this.bi.m2851b((Object) "NETWORK TYPE: network is close.");
        }
        m3009Y();
    }

    public final void aa() {
        this.bh.getApplicationContext().registerReceiver(new C0914z(this), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public final String m3019b() {
        return this.f3067c;
    }

    public final void m3020b(String str) {
        if (C0894c.m2949k()) {
            this.bi.m2851b("updateIpList " + str);
        }
        try {
            if (C0885l.m2894e(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.length() > 0) {
                    Iterator keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String string = jSONObject.getString((String) keys.next());
                        if (C0885l.m2894e(string)) {
                            for (String str2 : string.split(";")) {
                                String str22;
                                if (C0885l.m2894e(str22)) {
                                    String[] split = str22.split(":");
                                    if (split.length > 1) {
                                        str22 = split[0];
                                        if (C0898g.m3011d(str22) && !this.bc.contains(str22)) {
                                            if (C0894c.m2949k()) {
                                                this.bi.m2851b("add new ip:" + str22);
                                            }
                                            this.bc.add(str22);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            this.bi.m2852b(e);
        }
        this.bf = new Random().nextInt(this.bc.size());
    }
}
