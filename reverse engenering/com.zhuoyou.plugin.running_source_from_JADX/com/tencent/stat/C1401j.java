package com.tencent.stat;

import android.content.Context;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.tencent.stat.common.C1389k;
import com.tencent.stat.p039a.C1365e;
import com.tencent.stat.p039a.C1373i;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;

class C1401j implements Runnable {
    private Context f4464a = null;
    private Map<String, Integer> f4465b = null;

    public C1401j(Context context, Map<String, Integer> map) {
        this.f4464a = context;
        if (map != null) {
            this.f4465b = map;
        }
    }

    private NetworkMonitor m4180a(String str, int i) {
        NetworkMonitor networkMonitor = new NetworkMonitor();
        Socket socket = new Socket();
        int i2 = 0;
        try {
            networkMonitor.setDomain(str);
            networkMonitor.setPort(i);
            long currentTimeMillis = System.currentTimeMillis();
            SocketAddress inetSocketAddress = new InetSocketAddress(str, i);
            socket.connect(inetSocketAddress, m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT);
            networkMonitor.setMillisecondsConsume(System.currentTimeMillis() - currentTimeMillis);
            networkMonitor.setRemoteIp(inetSocketAddress.getAddress().getHostAddress());
            if (socket != null) {
                socket.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Object th) {
                    StatService.f4336i.m4085e(th);
                }
            }
        } catch (Exception e) {
            Exception exception = e;
            i2 = -1;
            StatService.f4336i.m4084e(exception);
            if (socket != null) {
                socket.close();
            }
        } catch (Object th2) {
            StatService.f4336i.m4085e(th2);
        }
        networkMonitor.setStatusCode(i2);
        return networkMonitor;
    }

    private Map<String, Integer> m4181a() {
        Map<String, Integer> hashMap = new HashMap();
        String a = StatConfig.m4005a("__MTA_TEST_SPEED__", null);
        if (!(a == null || a.trim().length() == 0)) {
            for (String a2 : a2.split(";")) {
                String[] split = a2.split(",");
                if (split != null && split.length == 2) {
                    String str = split[0];
                    if (!(str == null || str.trim().length() == 0)) {
                        try {
                            hashMap.put(str, Integer.valueOf(Integer.valueOf(split[1]).intValue()));
                        } catch (Exception e) {
                            StatService.f4336i.m4084e(e);
                        }
                    }
                }
            }
        }
        return hashMap;
    }

    public void run() {
        try {
            if (C1389k.m4141h(this.f4464a)) {
                if (this.f4465b == null) {
                    this.f4465b = m4181a();
                }
                if (this.f4465b == null || this.f4465b.size() == 0) {
                    StatService.f4336i.m4088w("empty domain list.");
                    return;
                }
                JSONArray jSONArray = new JSONArray();
                for (Entry entry : this.f4465b.entrySet()) {
                    String str = (String) entry.getKey();
                    if (str == null || str.length() == 0) {
                        StatService.f4336i.m4088w("empty domain name.");
                    } else if (((Integer) entry.getValue()) == null) {
                        StatService.f4336i.m4088w("port is null for " + str);
                    } else {
                        jSONArray.put(m4180a((String) entry.getKey(), ((Integer) entry.getValue()).intValue()).toJSONObject());
                    }
                }
                if (jSONArray.length() != 0) {
                    C1365e c1373i = new C1373i(this.f4464a, StatService.m4029a(this.f4464a, false));
                    c1373i.m4063a(jSONArray.toString());
                    if (StatService.m4037c(this.f4464a) != null) {
                        StatService.m4037c(this.f4464a).post(new C1402k(c1373i));
                    }
                }
            }
        } catch (Object th) {
            StatService.f4336i.m4085e(th);
        }
    }
}
