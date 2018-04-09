package com.tencent.map.p028b;

import android.net.wifi.ScanResult;
import com.umeng.socialize.common.SocializeConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public final class C1201c {
    private static C1201c f3767a;
    private long f3768b = 0;
    private List<C1199a> f3769c = new ArrayList();
    private List<C1200b> f3770d = new ArrayList();
    private String f3771e;

    static class C1199a {
        public int f3762a;
        public int f3763b;
        public int f3764c;
        public int f3765d;

        private C1199a() {
            this.f3762a = -1;
            this.f3763b = -1;
            this.f3764c = -1;
            this.f3765d = -1;
        }
    }

    static class C1200b {
        public String f3766a;

        private C1200b() {
            this.f3766a = null;
        }
    }

    public static C1201c m3510a() {
        if (f3767a == null) {
            f3767a = new C1201c();
        }
        return f3767a;
    }

    private static boolean m3511a(StringBuffer stringBuffer) {
        try {
            return new JSONObject(stringBuffer.toString()).getJSONObject(SocializeConstants.KEY_LOCATION).getDouble("accuracy") < 5000.0d;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean m3512a(List<ScanResult> list) {
        if (list == null) {
            return false;
        }
        int i;
        if (this.f3770d != null) {
            i = 0;
            for (int i2 = 0; i2 < this.f3770d.size(); i2++) {
                String str = ((C1200b) this.f3770d.get(i2)).f3766a;
                int i3 = 0;
                while (str != null && i3 < list.size()) {
                    if (str.equals(((ScanResult) list.get(i3)).BSSID)) {
                        i++;
                        break;
                    }
                    i3++;
                }
            }
        } else {
            i = 0;
        }
        int size = list.size();
        return (size < 6 || i < (size / 2) + 1) ? (size >= 6 || i < 2) ? this.f3770d.size() <= 2 && list.size() <= 2 && Math.abs(System.currentTimeMillis() - this.f3768b) <= StatisticConfig.MIN_UPLOAD_INTERVAL : true : true;
    }

    public final void m3513a(int i, int i2, int i3, int i4, List<ScanResult> list) {
        this.f3768b = System.currentTimeMillis();
        this.f3771e = null;
        this.f3769c.clear();
        C1199a c1199a = new C1199a();
        c1199a.f3762a = i;
        c1199a.f3763b = i2;
        c1199a.f3764c = i3;
        c1199a.f3765d = i4;
        this.f3769c.add(c1199a);
        if (list != null) {
            this.f3770d.clear();
            for (int i5 = 0; i5 < list.size(); i5++) {
                C1200b c1200b = new C1200b();
                c1200b.f3766a = ((ScanResult) list.get(i5)).BSSID;
                int i6 = ((ScanResult) list.get(i5)).level;
                this.f3770d.add(c1200b);
            }
        }
    }

    public final void m3514a(String str) {
        this.f3771e = str;
    }

    public final String m3515b(int i, int i2, int i3, int i4, List<ScanResult> list) {
        if (this.f3771e == null || this.f3771e.length() < 10) {
            return null;
        }
        String str = this.f3771e;
        if (str == null || list == null) {
            str = null;
        } else {
            long abs = Math.abs(System.currentTimeMillis() - this.f3768b);
            if ((abs > StatisticConfig.MIN_UPLOAD_INTERVAL && list.size() > 2) || ((abs > 45000 && list.size() <= 2) || !C1201c.m3511a(new StringBuffer(str)))) {
                str = null;
            }
        }
        this.f3771e = str;
        if (this.f3771e == null) {
            return null;
        }
        if (this.f3769c != null && this.f3769c.size() > 0) {
            C1199a c1199a = (C1199a) this.f3769c.get(0);
            if (c1199a.f3762a != i || c1199a.f3763b != i2 || c1199a.f3764c != i3 || c1199a.f3765d != i4) {
                return null;
            }
            if ((this.f3770d == null || this.f3770d.size() == 0) && (list == null || list.size() == 0)) {
                return this.f3771e;
            }
            if (m3512a((List) list)) {
                return this.f3771e;
            }
        }
        return m3512a((List) list) ? this.f3771e : null;
    }

    public final void m3516b() {
        this.f3771e = null;
    }
}
