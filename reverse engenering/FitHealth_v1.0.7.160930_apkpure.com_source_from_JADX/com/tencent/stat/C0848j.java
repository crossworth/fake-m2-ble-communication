package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.C0837k;
import com.tencent.stat.p021a.C0824e;
import com.tencent.stat.p021a.C1741i;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;

class C0848j implements Runnable {
    private Context f2928a = null;
    private Map<String, Integer> f2929b = null;

    public C0848j(Context context, Map<String, Integer> map) {
        this.f2928a = context;
        if (map != null) {
            this.f2929b = map;
        }
    }

    private NetworkMonitor m2773a(String str, int i) {
        NetworkMonitor networkMonitor = new NetworkMonitor();
        Socket socket = new Socket();
        int i2 = 0;
        try {
            networkMonitor.setDomain(str);
            networkMonitor.setPort(i);
            long currentTimeMillis = System.currentTimeMillis();
            SocketAddress inetSocketAddress = new InetSocketAddress(str, i);
            socket.connect(inetSocketAddress, 30000);
            networkMonitor.setMillisecondsConsume(System.currentTimeMillis() - currentTimeMillis);
            networkMonitor.setRemoteIp(inetSocketAddress.getAddress().getHostAddress());
            if (socket != null) {
                socket.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Object th) {
                    StatService.f2834i.m2680e(th);
                }
            }
        } catch (Exception e) {
            Exception exception = e;
            i2 = -1;
            StatService.f2834i.m2679e(exception);
            if (socket != null) {
                socket.close();
            }
        } catch (Object th2) {
            StatService.f2834i.m2680e(th2);
        }
        networkMonitor.setStatusCode(i2);
        return networkMonitor;
    }

    private Map<String, Integer> m2774a() {
        Map<String, Integer> hashMap = new HashMap();
        String a = StatConfig.m2621a("__MTA_TEST_SPEED__", null);
        if (!(a == null || a.trim().length() == 0)) {
            for (String a2 : a2.split(";")) {
                String[] split = a2.split(SeparatorConstants.SEPARATOR_ADS_ID);
                if (split != null && split.length == 2) {
                    String str = split[0];
                    if (!(str == null || str.trim().length() == 0)) {
                        try {
                            hashMap.put(str, Integer.valueOf(Integer.valueOf(split[1]).intValue()));
                        } catch (Exception e) {
                            StatService.f2834i.m2679e(e);
                        }
                    }
                }
            }
        }
        return hashMap;
    }

    public void run() {
        try {
            if (C0837k.m2734h(this.f2928a)) {
                if (this.f2929b == null) {
                    this.f2929b = m2774a();
                }
                if (this.f2929b == null || this.f2929b.size() == 0) {
                    StatService.f2834i.m2683w("empty domain list.");
                    return;
                }
                JSONArray jSONArray = new JSONArray();
                for (Entry entry : this.f2929b.entrySet()) {
                    String str = (String) entry.getKey();
                    if (str == null || str.length() == 0) {
                        StatService.f2834i.m2683w("empty domain name.");
                    } else if (((Integer) entry.getValue()) == null) {
                        StatService.f2834i.m2683w("port is null for " + str);
                    } else {
                        jSONArray.put(m2773a((String) entry.getKey(), ((Integer) entry.getValue()).intValue()).toJSONObject());
                    }
                }
                if (jSONArray.length() != 0) {
                    C0824e c1741i = new C1741i(this.f2928a, StatService.m2645a(this.f2928a, false));
                    c1741i.m4871a(jSONArray.toString());
                    if (StatService.m2653c(this.f2928a) != null) {
                        StatService.m2653c(this.f2928a).post(new C0849k(c1741i));
                    }
                }
            }
        } catch (Object th) {
            StatService.f2834i.m2680e(th);
        }
    }
}
