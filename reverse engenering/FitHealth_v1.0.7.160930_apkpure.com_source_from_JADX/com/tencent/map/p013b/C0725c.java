package com.tencent.map.p013b;

import android.net.wifi.ScanResult;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public final class C0725c {
    private static C0725c f2512a;
    private long f2513b = 0;
    private List<C0724b> f2514c = new ArrayList();
    private List<C0723a> f2515d = new ArrayList();
    private String f2516e;

    static class C0723a {
        public String f2507a;

        private C0723a() {
            this.f2507a = null;
        }
    }

    static class C0724b {
        public int f2508a;
        public int f2509b;
        public int f2510c;
        public int f2511d;

        private C0724b() {
            this.f2508a = -1;
            this.f2509b = -1;
            this.f2510c = -1;
            this.f2511d = -1;
        }
    }

    public static C0725c m2411a() {
        if (f2512a == null) {
            f2512a = new C0725c();
        }
        return f2512a;
    }

    private static boolean m2412a(StringBuffer stringBuffer) {
        try {
            return new JSONObject(stringBuffer.toString()).getJSONObject("location").getDouble(DataBaseContants.ACCURACY) < 5000.0d;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean m2413a(List<ScanResult> list) {
        if (list == null) {
            return false;
        }
        int i;
        if (this.f2515d != null) {
            i = 0;
            for (int i2 = 0; i2 < this.f2515d.size(); i2++) {
                String str = ((C0723a) this.f2515d.get(i2)).f2507a;
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
        return (size < 6 || i < (size / 2) + 1) ? (size >= 6 || i < 2) ? this.f2515d.size() <= 2 && list.size() <= 2 && Math.abs(System.currentTimeMillis() - this.f2513b) <= 30000 : true : true;
    }

    public final void m2414a(int i, int i2, int i3, int i4, List<ScanResult> list) {
        this.f2513b = System.currentTimeMillis();
        this.f2516e = null;
        this.f2514c.clear();
        C0724b c0724b = new C0724b();
        c0724b.f2508a = i;
        c0724b.f2509b = i2;
        c0724b.f2510c = i3;
        c0724b.f2511d = i4;
        this.f2514c.add(c0724b);
        if (list != null) {
            this.f2515d.clear();
            for (int i5 = 0; i5 < list.size(); i5++) {
                C0723a c0723a = new C0723a();
                c0723a.f2507a = ((ScanResult) list.get(i5)).BSSID;
                int i6 = ((ScanResult) list.get(i5)).level;
                this.f2515d.add(c0723a);
            }
        }
    }

    public final void m2415a(String str) {
        this.f2516e = str;
    }

    public final String m2416b(int i, int i2, int i3, int i4, List<ScanResult> list) {
        if (this.f2516e == null || this.f2516e.length() < 10) {
            return null;
        }
        String str = this.f2516e;
        if (str == null || list == null) {
            str = null;
        } else {
            long abs = Math.abs(System.currentTimeMillis() - this.f2513b);
            if ((abs > 30000 && list.size() > 2) || ((abs > 45000 && list.size() <= 2) || !C0725c.m2412a(new StringBuffer(str)))) {
                str = null;
            }
        }
        this.f2516e = str;
        if (this.f2516e == null) {
            return null;
        }
        if (this.f2514c != null && this.f2514c.size() > 0) {
            C0724b c0724b = (C0724b) this.f2514c.get(0);
            if (c0724b.f2508a != i || c0724b.f2509b != i2 || c0724b.f2510c != i3 || c0724b.f2511d != i4) {
                return null;
            }
            if ((this.f2515d == null || this.f2515d.size() == 0) && (list == null || list.size() == 0)) {
                return this.f2516e;
            }
            if (m2413a((List) list)) {
                return this.f2516e;
            }
        }
        return m2413a((List) list) ? this.f2516e : null;
    }

    public final void m2417b() {
        this.f2516e = null;
    }
}
