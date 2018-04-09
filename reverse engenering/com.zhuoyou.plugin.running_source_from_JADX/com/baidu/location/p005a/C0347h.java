package com.baidu.location.p005a;

import android.content.Context;
import android.util.Log;
import com.baidu.lbsapi.auth.LBSAuthManager;
import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.location.p006h.C0458a;

public class C0347h implements LBSAuthManagerListener {
    private static Object f233a = new Object();
    private static C0347h f234b = null;
    private int f235c = 0;

    public static C0347h m268a() {
        C0347h c0347h;
        synchronized (f233a) {
            if (f234b == null) {
                f234b = new C0347h();
            }
            c0347h = f234b;
        }
        return c0347h;
    }

    public static String m269b(Context context) {
        try {
            return LBSAuthManager.getInstance(context).getPublicKey(context);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void m270a(Context context) {
        LBSAuthManager.getInstance(context).authenticate(false, "lbs_locsdk", null, this);
    }

    public boolean m271b() {
        return this.f235c == 0;
    }

    public void onAuthResult(int i, String str) {
        this.f235c = i;
        Log.i(C0458a.f826a, "LocationAuthManager status = " + i);
    }
}
