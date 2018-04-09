package com.droi.sdk.push;

import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.Looper;
import com.droi.sdk.push.data.C0990e;
import com.droi.sdk.push.data.ClientPushableBean;
import com.droi.sdk.push.data.HeartBeatBean;
import com.droi.sdk.push.data.RecoveryTimeBean;
import com.droi.sdk.push.data.ServerAddressBean;
import com.droi.sdk.push.data.SlienceTimeBean;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

class aa {
    private static aa f3226a;
    private Context f3227b;
    private ac f3228c;
    private Looper f3229d;
    private Thread f3230e = null;

    private aa(Context context) {
        this.f3227b = context.getApplicationContext();
        if (this.f3229d == null) {
            HandlerThread handlerThread = new HandlerThread("upload_info");
            handlerThread.start();
            this.f3229d = handlerThread.getLooper();
        }
        this.f3228c = new ac(this, this.f3229d);
    }

    static aa m2958a(Context context) {
        aa aaVar;
        synchronized (aa.class) {
            if (f3226a == null) {
                f3226a = new aa(context.getApplicationContext());
            }
            aaVar = f3226a;
        }
        return aaVar;
    }

    private boolean m2960a(int i) {
        if (i <= 0 || i >= 180) {
            C1012g.m3140b("Invalid heartbeat: " + i);
            return false;
        }
        try {
            C1015j.m3158a(new HeartBeatBean(i), C1015j.m3153a("droiServerHeartBeat"));
            return true;
        } catch (Exception e) {
            C1012g.m3139b(e);
            return false;
        }
    }

    public static long m2963f(String str) {
        long j = -1;
        try {
            return Long.parseLong(str) * 60000;
        } catch (Exception e) {
            C1012g.m3139b(e);
            return j;
        }
    }

    void m2964a(String str, String str2, String str3) {
        if (C1015j.m3168d(str2) && C1015j.m3168d(str3) && str2.length() == 32) {
            long f = "0".equals(str3) ? Long.MAX_VALUE : "resume".equals(str3) ? 0 : m2963f(str3);
            Serializable recoveryTimeBean = new RecoveryTimeBean(f);
            try {
                if ("00000000000000000000000000000000".equals(str2)) {
                    C1015j.m3158a(recoveryTimeBean, C1015j.m3153a("droiServerRecoveryTime"));
                } else if (C1015j.m3168d(str)) {
                    C1015j.m3154a(this.f3227b, recoveryTimeBean, C1015j.m3153a(str + "droiServerRecoveryTime"));
                }
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
    }

    public boolean m2965a() {
        C0990e c0990e;
        try {
            c0990e = (C0990e) C1015j.m3160b(this.f3227b, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
        } catch (Exception e) {
            c0990e = null;
        }
        if (c0990e == null) {
            return false;
        }
        Serializable c0990e2 = new C0990e(new String[0]);
        c0990e2.m3045a(true);
        try {
            C1015j.m3154a(this.f3227b, c0990e2, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
            m2986i();
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    boolean m2966a(C1004t c1004t) {
        int parseInt;
        int parseInt2;
        Exception e;
        int i;
        int i2;
        int i3;
        int i4 = -1;
        if (c1004t == null) {
            return false;
        }
        String str = c1004t.f3334h;
        String str2 = c1004t.f3338l;
        if (!C1015j.m3168d(str) || !C1015j.m3168d(str2)) {
            return false;
        }
        String[] split = str.split(",");
        if (split.length != 4) {
            return false;
        }
        try {
            parseInt = Integer.parseInt(split[0]);
            try {
                parseInt2 = Integer.parseInt(split[1]);
            } catch (Exception e2) {
                e = e2;
                i = i4;
                parseInt2 = i4;
                C1012g.m3139b(e);
                i2 = parseInt;
                i3 = i;
                i = parseInt2;
                parseInt2 = i4;
                i4 = i3;
                return (i2 < 0 || i < 0 || i4 < 0 || parseInt2 < 0 || i2 >= 24 || i >= 60 || i4 >= 24 || parseInt2 >= 60) ? false : m2969a(str2, true, i2, i, i4, parseInt2);
            }
            try {
                i = Integer.parseInt(split[2]);
            } catch (Exception e3) {
                e = e3;
                i = i4;
                C1012g.m3139b(e);
                i2 = parseInt;
                i3 = i;
                i = parseInt2;
                parseInt2 = i4;
                i4 = i3;
                if (i2 < 0) {
                }
            }
            try {
                i4 = Integer.parseInt(split[3]);
                i2 = parseInt;
                i3 = i;
                i = parseInt2;
                parseInt2 = i4;
                i4 = i3;
            } catch (Exception e4) {
                e = e4;
                C1012g.m3139b(e);
                i2 = parseInt;
                i3 = i;
                i = parseInt2;
                parseInt2 = i4;
                i4 = i3;
                if (i2 < 0) {
                }
            }
        } catch (Exception e5) {
            e = e5;
            i = i4;
            parseInt2 = i4;
            parseInt = i4;
            C1012g.m3139b(e);
            i2 = parseInt;
            i3 = i;
            i = parseInt2;
            parseInt2 = i4;
            i4 = i3;
            if (i2 < 0) {
            }
        }
        return (i2 < 0 || i < 0 || i4 < 0 || parseInt2 < 0 || i2 >= 24 || i >= 60 || i4 >= 24 || parseInt2 >= 60) ? false : m2969a(str2, true, i2, i, i4, parseInt2);
    }

    public boolean m2967a(String str) {
        if (!C1015j.m3168d(str)) {
            return false;
        }
        RecoveryTimeBean recoveryTimeBean;
        try {
            recoveryTimeBean = (RecoveryTimeBean) C1015j.m3161b(C1015j.m3153a(str + "droiServerRecoveryTime"));
        } catch (Exception e) {
            C1012g.m3139b(e);
            recoveryTimeBean = null;
        }
        return recoveryTimeBean != null ? recoveryTimeBean.isRecovered() : true;
    }

    public boolean m2968a(String str, boolean z) {
        if (!C1015j.m3168d(str)) {
            return false;
        }
        Serializable slienceTimeBean = new SlienceTimeBean(-1, -1, -1, -1);
        if (z) {
            try {
                C1015j.m3154a(this.f3227b, slienceTimeBean, C1015j.m3153a(str + "droiServerSlienceTime"));
            } catch (Exception e) {
                return false;
            }
        }
        C1015j.m3154a(this.f3227b, slienceTimeBean, C1015j.m3153a(str + "droiUserSlienceTime"));
        return true;
    }

    public boolean m2969a(String str, boolean z, int i, int i2, int i3, int i4) {
        if (i < 0 || i2 < 0 || i3 < 0 || i4 < 0) {
            return false;
        }
        if ((i == 0 && i2 == 0 && i3 == 0 && i4 == 0) || i >= 24 || i2 >= 60 || i3 >= 24 || i4 >= 60 || !C1015j.m3168d(str)) {
            return false;
        }
        Serializable slienceTimeBean = new SlienceTimeBean(i, i2, i3, i4);
        if (z) {
            try {
                C1015j.m3154a(this.f3227b, slienceTimeBean, C1015j.m3153a(str + "droiServerSlienceTime"));
            } catch (Exception e) {
                return false;
            }
        }
        C1015j.m3154a(this.f3227b, slienceTimeBean, C1015j.m3153a(str + "droiUserSlienceTime"));
        return true;
    }

    public boolean m2970a(ArrayList arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            C1012g.m3142d("save address failed: address list is null or empty!");
            return false;
        }
        String a;
        Serializable serverAddressBean = new ServerAddressBean(arrayList);
        try {
            a = C1015j.m3153a("droiServerIpList");
        } catch (Exception e) {
            a = null;
        }
        if (serverAddressBean == null || a == null) {
            return false;
        }
        C1015j.m3158a(serverAddressBean, a);
        String b = C1015j.m3162b(this.f3227b);
        Intent intent = new Intent();
        intent.setAction("com.droi.sdk.push.action.START");
        if (b == null || !b.equals("TCP")) {
            intent.putExtra("CMD", "RESET_UDP");
        } else {
            intent.putExtra("CMD", "RESET_TCP");
        }
        try {
            this.f3227b.startService(intent);
        } catch (Throwable th) {
            C1012g.m3137a(new Exception(th));
        }
        return true;
    }

    public boolean m2971a(boolean z) {
        try {
            C1015j.m3154a(this.f3227b, new ClientPushableBean(z), C1015j.m3153a(this.f3227b.getPackageName() + "droiClientPushable"));
            return true;
        } catch (Exception e) {
            C1012g.m3139b(e);
            return false;
        }
    }

    public boolean m2972a(String[] strArr) {
        if (strArr == null || strArr.length <= 0) {
            return false;
        }
        C0990e c0990e;
        try {
            c0990e = (C0990e) C1015j.m3160b(this.f3227b, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
        } catch (Exception e) {
            c0990e = null;
        }
        Set hashSet = new HashSet();
        if (c0990e == null || c0990e.m3046a() == null) {
            return false;
        }
        int length = c0990e.m3046a().length;
        hashSet.addAll(Arrays.asList(c0990e.m3046a()));
        hashSet.removeAll(Arrays.asList(strArr));
        if (length == hashSet.size()) {
            return false;
        }
        try {
            String[] strArr2 = new String[hashSet.size()];
            hashSet.toArray(strArr2);
            Serializable c0990e2 = new C0990e(strArr2);
            c0990e2.m3045a(true);
            C1015j.m3154a(this.f3227b, c0990e2, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
            m2986i();
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    public boolean m2973a(String[] strArr, boolean z) {
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        C0990e c0990e;
        try {
            c0990e = (C0990e) C1015j.m3160b(this.f3227b, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
        } catch (Exception e) {
            c0990e = null;
        }
        Set hashSet = new HashSet();
        if (z || c0990e == null || c0990e.m3046a() == null) {
            boolean z2 = false;
        } else {
            int length = c0990e.m3046a().length;
            hashSet.addAll(Arrays.asList(c0990e.m3046a()));
            int i = length;
        }
        hashSet.addAll(Arrays.asList(strArr));
        if (i == hashSet.size()) {
            return false;
        }
        String[] strArr2 = new String[hashSet.size()];
        hashSet.toArray(strArr2);
        Serializable c0990e2 = new C0990e(strArr2);
        c0990e2.m3045a(true);
        try {
            C1015j.m3154a(this.f3227b, c0990e2, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
            m2986i();
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    public String m2974b() {
        C0990e c0990e;
        try {
            c0990e = (C0990e) C1015j.m3160b(this.f3227b, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
        } catch (Exception e) {
            c0990e = null;
        }
        return (c0990e == null || c0990e.m3046a() == null || c0990e.m3046a().length == 0) ? "" : c0990e.toString();
    }

    public int[] m2975b(String str) {
        SlienceTimeBean slienceTimeBean;
        int[] iArr;
        try {
            slienceTimeBean = (SlienceTimeBean) C1015j.m3160b(this.f3227b, C1015j.m3153a(str + "droiUserSlienceTime"));
        } catch (Exception e) {
            C1012g.m3140b("App setting silent time for " + str + " do not exist!");
            slienceTimeBean = null;
        }
        if (slienceTimeBean != null) {
            int startHour = slienceTimeBean.getStartHour();
            int startMin = slienceTimeBean.getStartMin();
            int endHour = slienceTimeBean.getEndHour();
            int endMin = slienceTimeBean.getEndMin();
            if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23 || startMin < 0 || startMin > 59 || endMin < 0 || endMin > 59) {
                return null;
            }
            iArr = new int[]{startHour, startMin, endHour, endMin};
        } else {
            iArr = null;
        }
        return iArr;
    }

    public boolean m2976c() {
        try {
            C0990e c0990e = (C0990e) C1015j.m3160b(this.f3227b, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
            return c0990e == null ? false : c0990e.m3047b();
        } catch (Exception e) {
            return false;
        }
    }

    public int[] m2977c(String str) {
        if (!C1015j.m3168d(str)) {
            return null;
        }
        SlienceTimeBean slienceTimeBean;
        int[] iArr;
        try {
            slienceTimeBean = (SlienceTimeBean) C1015j.m3160b(this.f3227b, C1015j.m3153a(str + "droiServerSlienceTime"));
        } catch (Exception e) {
            C1012g.m3140b("Server silent time for " + str + " do not exist!");
            slienceTimeBean = null;
        }
        if (slienceTimeBean != null) {
            int startHour = slienceTimeBean.getStartHour();
            int startMin = slienceTimeBean.getStartMin();
            int endHour = slienceTimeBean.getEndHour();
            int endMin = slienceTimeBean.getEndMin();
            if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23 || startMin < 0 || startMin > 59 || endMin < 0 || endMin > 59) {
                return null;
            }
            iArr = new int[]{startHour, startMin, endHour, endMin};
        } else {
            iArr = null;
        }
        return iArr;
    }

    public void m2978d() {
        try {
            C0990e c0990e = (C0990e) C1015j.m3160b(this.f3227b, C1015j.m3153a(this.f3227b.getPackageName() + "droiUserTags.txt"));
            if (c0990e != null && c0990e.m3047b()) {
                c0990e.m3045a(false);
            }
        } catch (Exception e) {
            C1012g.m3139b(e);
        }
    }

    public int[] m2979d(String str) {
        if (!C1015j.m3168d(str)) {
            return null;
        }
        int[] c = m2977c(str);
        return c == null ? m2975b(str) : c;
    }

    public boolean m2980e() {
        return m2982f();
    }

    public boolean m2981e(String str) {
        int i = 24;
        boolean z = true;
        if (!C1015j.m3168d(str)) {
            return false;
        }
        int[] d = m2979d(str);
        if (d == null) {
            return false;
        }
        boolean z2;
        boolean z3;
        d[0] = d[0] == 0 ? 24 : d[0];
        if (d[2] != 0) {
            i = d[2];
        }
        d[2] = i;
        int i2 = d[0];
        int i3 = d[1];
        i = d[2];
        int i4 = d[3];
        if (d[0] > d[2] || (d[0] == d[2] && d[1] > d[3])) {
            i2 = d[2];
            i3 = d[3];
            i = d[0];
            i4 = d[1];
            z2 = true;
        } else {
            z2 = false;
        }
        Calendar instance = Calendar.getInstance();
        int i5 = instance.get(11);
        int i6 = instance.get(12);
        if (i5 < i2 || i5 > i) {
            z3 = false;
        } else {
            boolean z4 = i5 == i2 ? z2 ? i6 > i3 : i6 >= i3 : true;
            z3 = (i5 == i && z4) ? z2 ? i6 < i4 : i6 <= i4 : z4;
        }
        if (!z2) {
            return z3;
        }
        if (z3) {
            z = false;
        }
        return z;
    }

    public boolean m2982f() {
        ClientPushableBean clientPushableBean;
        try {
            clientPushableBean = (ClientPushableBean) C1015j.m3160b(this.f3227b, C1015j.m3153a(this.f3227b.getPackageName() + "droiClientPushable"));
        } catch (Exception e) {
            C1012g.m3139b(e);
            clientPushableBean = null;
        }
        return clientPushableBean != null ? clientPushableBean.isPushable() : true;
    }

    public boolean m2983g() {
        RecoveryTimeBean recoveryTimeBean;
        try {
            recoveryTimeBean = (RecoveryTimeBean) C1015j.m3161b(C1015j.m3153a("droiServerRecoveryTime"));
        } catch (Exception e) {
            C1012g.m3139b(e);
            recoveryTimeBean = null;
        }
        return recoveryTimeBean != null ? recoveryTimeBean.isRecovered() : true;
    }

    boolean m2984g(String str) {
        boolean z = false;
        if (C1015j.m3168d(str)) {
            int parseInt;
            try {
                parseInt = Integer.parseInt(str);
            } catch (Exception e) {
                parseInt = -1;
            }
            if (parseInt > 0) {
                z = true;
                if (parseInt != m2985h()) {
                    z = m2960a(parseInt);
                    try {
                        Intent intent = new Intent();
                        intent.setAction("com.droi.sdk.push.action.START");
                        intent.putExtra("CMD", "HEART_BEAT");
                        intent.setPackage(this.f3227b.getPackageName());
                        this.f3227b.startService(intent);
                        C1012g.m3141c("setServiceHB set successful...");
                    } catch (Throwable th) {
                        C1012g.m3137a(new Exception(th));
                    }
                }
            }
        }
        return z;
    }

    public int m2985h() {
        HeartBeatBean heartBeatBean;
        try {
            heartBeatBean = (HeartBeatBean) C1015j.m3161b(C1015j.m3153a("droiServerHeartBeat"));
        } catch (Exception e) {
            heartBeatBean = null;
        }
        return (heartBeatBean == null || heartBeatBean.getTime() == 0) ? 100 : heartBeatBean.getTime();
    }

    public void m2986i() {
        this.f3228c.sendMessage(this.f3228c.obtainMessage(1));
    }

    public void m2987j() {
        this.f3228c.sendMessage(this.f3228c.obtainMessage(2));
    }

    void m2988k() {
        this.f3228c.sendMessage(this.f3228c.obtainMessage(3));
    }
}
