package com.tencent.wxop.stat;

import android.content.Context;
import android.content.IntentFilter;
import com.tencent.wxop.stat.common.C1436e;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1448q;
import com.tencent.wxop.stat.common.StatConstants;
import com.tencent.wxop.stat.common.StatLogger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import org.apache.http.HttpHost;
import org.json.JSONObject;

public class C1428a {
    private static C1428a f4636g = null;
    private List<String> f4637a = null;
    private volatile int f4638b = 2;
    private volatile String f4639c = "";
    private volatile HttpHost f4640d = null;
    private C1436e f4641e = null;
    private int f4642f = 0;
    private Context f4643h = null;
    private StatLogger f4644i = null;

    private C1428a(Context context) {
        this.f4643h = context.getApplicationContext();
        this.f4641e = new C1436e();
        C1454i.m4484a(context);
        this.f4644i = C1442k.m4416b();
        m4304l();
        m4301i();
        m4312g();
    }

    public static C1428a m4298a(Context context) {
        if (f4636g == null) {
            synchronized (C1428a.class) {
                if (f4636g == null) {
                    f4636g = new C1428a(context);
                }
            }
        }
        return f4636g;
    }

    private boolean m4300b(String str) {
        return Pattern.compile("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})").matcher(str).matches();
    }

    private void m4301i() {
        this.f4637a = new ArrayList(10);
        this.f4637a.add("117.135.169.101");
        this.f4637a.add("140.207.54.125");
        this.f4637a.add("180.153.8.53");
        this.f4637a.add("120.198.203.175");
        this.f4637a.add("14.17.43.18");
        this.f4637a.add("163.177.71.186");
        this.f4637a.add("111.30.131.31");
        this.f4637a.add("123.126.121.167");
        this.f4637a.add("123.151.152.111");
        this.f4637a.add("113.142.45.79");
        this.f4637a.add("123.138.162.90");
        this.f4637a.add("103.7.30.94");
    }

    private String m4302j() {
        try {
            String str = StatConstants.MTA_SERVER_HOST;
            if (!m4300b(str)) {
                return InetAddress.getByName(str).getHostAddress();
            }
        } catch (Throwable e) {
            this.f4644i.m4375e(e);
        }
        return "";
    }

    private void m4303k() {
        String j = m4302j();
        if (StatConfig.isDebugEnable()) {
            this.f4644i.m4376i("remoteIp ip is " + j);
        }
        if (C1442k.m4420c(j)) {
            String str;
            if (this.f4637a.contains(j)) {
                str = j;
            } else {
                str = (String) this.f4637a.get(this.f4642f);
                if (StatConfig.isDebugEnable()) {
                    this.f4644i.m4378w(j + " not in ip list, change to:" + str);
                }
            }
            StatConfig.setStatReportUrl("http://" + str + ":80/mstat/report");
        }
    }

    private void m4304l() {
        this.f4638b = 0;
        this.f4640d = null;
        this.f4639c = null;
    }

    public HttpHost m4305a() {
        return this.f4640d;
    }

    public void m4306a(String str) {
        if (StatConfig.isDebugEnable()) {
            this.f4644i.m4376i("updateIpList " + str);
        }
        try {
            if (C1442k.m4420c(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.length() > 0) {
                    Iterator keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String string = jSONObject.getString((String) keys.next());
                        if (C1442k.m4420c(string)) {
                            for (String str2 : string.split(";")) {
                                String str22;
                                if (C1442k.m4420c(str22)) {
                                    String[] split = str22.split(":");
                                    if (split.length > 1) {
                                        str22 = split[0];
                                        if (m4300b(str22) && !this.f4637a.contains(str22)) {
                                            if (StatConfig.isDebugEnable()) {
                                                this.f4644i.m4376i("add new ip:" + str22);
                                            }
                                            this.f4637a.add(str22);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            this.f4644i.m4375e(e);
        }
        this.f4642f = new Random().nextInt(this.f4637a.size());
    }

    public String m4307b() {
        return this.f4639c;
    }

    public int m4308c() {
        return this.f4638b;
    }

    public void m4309d() {
        this.f4642f = (this.f4642f + 1) % this.f4637a.size();
    }

    public boolean m4310e() {
        return this.f4638b == 1;
    }

    public boolean m4311f() {
        return this.f4638b != 0;
    }

    void m4312g() {
        if (C1448q.m4471f(this.f4643h)) {
            if (StatConfig.f4540g) {
                m4303k();
            }
            this.f4639c = C1442k.m4434l(this.f4643h);
            if (StatConfig.isDebugEnable()) {
                this.f4644i.m4376i("NETWORK name:" + this.f4639c);
            }
            if (C1442k.m4420c(this.f4639c)) {
                if ("WIFI".equalsIgnoreCase(this.f4639c)) {
                    this.f4638b = 1;
                } else {
                    this.f4638b = 2;
                }
                this.f4640d = C1442k.m4411a(this.f4643h);
            }
            if (StatServiceImpl.m4245a()) {
                StatServiceImpl.m4255d(this.f4643h);
                return;
            }
            return;
        }
        if (StatConfig.isDebugEnable()) {
            this.f4644i.m4376i("NETWORK TYPE: network is close.");
        }
        m4304l();
    }

    public void m4313h() {
        this.f4643h.getApplicationContext().registerReceiver(new C1430b(this), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
