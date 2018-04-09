package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p022a.C0873d;
import com.tencent.wxop.stat.p022a.C1758g;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;

final class C0906o implements Runnable {
    private C0897f bM = null;
    private Map<String, Integer> bO = null;
    private Context f3076e = null;

    public C0906o(Context context) {
        this.f3076e = context;
        this.bM = null;
    }

    private static C0893b m3021a(String str, int i) {
        Throwable th;
        C0893b c0893b = new C0893b();
        Socket socket = new Socket();
        int i2 = 0;
        try {
            c0893b.setDomain(str);
            c0893b.setPort(i);
            long currentTimeMillis = System.currentTimeMillis();
            SocketAddress inetSocketAddress = new InetSocketAddress(str, i);
            socket.connect(inetSocketAddress, 30000);
            c0893b.m2924a(System.currentTimeMillis() - currentTimeMillis);
            c0893b.m2926k(inetSocketAddress.getAddress().getHostAddress());
            socket.close();
            try {
                socket.close();
            } catch (Throwable th2) {
                C0896e.aV.m2852b(th2);
            }
        } catch (Throwable e) {
            th2 = e;
            i2 = -1;
            C0896e.aV.m2852b(th2);
            socket.close();
        } catch (Throwable th22) {
            C0896e.aV.m2852b(th22);
        }
        c0893b.setStatusCode(i2);
        return c0893b;
    }

    private static Map<String, Integer> ag() {
        Map<String, Integer> hashMap = new HashMap();
        String l = C0894c.m2950l("__MTA_TEST_SPEED__");
        if (!(l == null || l.trim().length() == 0)) {
            for (String l2 : l2.split(";")) {
                String[] split = l2.split(SeparatorConstants.SEPARATOR_ADS_ID);
                if (split != null && split.length == 2) {
                    String str = split[0];
                    if (!(str == null || str.trim().length() == 0)) {
                        try {
                            hashMap.put(str, Integer.valueOf(Integer.valueOf(split[1]).intValue()));
                        } catch (Throwable e) {
                            C0896e.aV.m2852b(e);
                        }
                    }
                }
            }
        }
        return hashMap;
    }

    public final void run() {
        try {
            if (this.bO == null) {
                this.bO = C0906o.ag();
            }
            if (this.bO == null || this.bO.size() == 0) {
                C0896e.aV.m2851b((Object) "empty domain list.");
                return;
            }
            JSONArray jSONArray = new JSONArray();
            for (Entry entry : this.bO.entrySet()) {
                String str = (String) entry.getKey();
                if (str == null || str.length() == 0) {
                    C0896e.aV.m2853c("empty domain name.");
                } else if (((Integer) entry.getValue()) == null) {
                    C0896e.aV.m2853c("port is null for " + str);
                } else {
                    jSONArray.put(C0906o.m3021a((String) entry.getKey(), ((Integer) entry.getValue()).intValue()).m2925i());
                }
            }
            if (jSONArray.length() != 0) {
                C0873d c1758g = new C1758g(this.f3076e, C0896e.m2981a(this.f3076e, false, this.bM), this.bM);
                c1758g.m4893b(jSONArray.toString());
                new C0907p(c1758g).ah();
            }
        } catch (Throwable th) {
            C0896e.aV.m2852b(th);
        }
    }
}
