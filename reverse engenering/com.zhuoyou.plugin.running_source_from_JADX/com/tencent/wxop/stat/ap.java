package com.tencent.wxop.stat;

import android.content.Context;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.tencent.wxop.stat.p040a.C1416e;
import com.tencent.wxop.stat.p040a.C1425j;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;

class ap implements Runnable {
    private Context f4677a = null;
    private Map<String, Integer> f4678b = null;
    private StatSpecifyReportedInfo f4679c = null;

    public ap(Context context, Map<String, Integer> map, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4677a = context;
        this.f4679c = statSpecifyReportedInfo;
        if (map != null) {
            this.f4678b = map;
        }
    }

    private NetworkMonitor m4314a(String str, int i) {
        Throwable th;
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
            socket.close();
            try {
                socket.close();
            } catch (Throwable th2) {
                StatServiceImpl.f4581q.m4375e(th2);
            }
        } catch (Throwable e) {
            th2 = e;
            i2 = -1;
            StatServiceImpl.f4581q.m4375e(th2);
            socket.close();
        } catch (Throwable th22) {
            StatServiceImpl.f4581q.m4375e(th22);
        }
        networkMonitor.setStatusCode(i2);
        return networkMonitor;
    }

    private Map<String, Integer> m4315a() {
        Map<String, Integer> hashMap = new HashMap();
        String a = StatConfig.m4221a("__MTA_TEST_SPEED__", null);
        if (!(a == null || a.trim().length() == 0)) {
            for (String a2 : a2.split(";")) {
                String[] split = a2.split(",");
                if (split != null && split.length == 2) {
                    String str = split[0];
                    if (!(str == null || str.trim().length() == 0)) {
                        try {
                            hashMap.put(str, Integer.valueOf(Integer.valueOf(split[1]).intValue()));
                        } catch (Throwable e) {
                            StatServiceImpl.f4581q.m4375e(e);
                        }
                    }
                }
            }
        }
        return hashMap;
    }

    public void run() {
        try {
            if (this.f4678b == null) {
                this.f4678b = m4315a();
            }
            if (this.f4678b == null || this.f4678b.size() == 0) {
                StatServiceImpl.f4581q.m4376i("empty domain list.");
                return;
            }
            JSONArray jSONArray = new JSONArray();
            for (Entry entry : this.f4678b.entrySet()) {
                String str = (String) entry.getKey();
                if (str == null || str.length() == 0) {
                    StatServiceImpl.f4581q.m4378w("empty domain name.");
                } else if (((Integer) entry.getValue()) == null) {
                    StatServiceImpl.f4581q.m4378w("port is null for " + str);
                } else {
                    jSONArray.put(m4314a((String) entry.getKey(), ((Integer) entry.getValue()).intValue()).toJSONObject());
                }
            }
            if (jSONArray.length() != 0) {
                C1416e c1425j = new C1425j(this.f4677a, StatServiceImpl.m4238a(this.f4677a, false, this.f4679c), this.f4679c);
                c1425j.m4292a(jSONArray.toString());
                new aq(c1425j).m4323a();
            }
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
        }
    }
}
