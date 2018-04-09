package com.baidu.location.p007b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.baidu.location.C0455f;
import com.tencent.connect.common.Constants;

public class C0370d {
    private static C0370d f355d = null;
    private boolean f356a = false;
    private String f357b = null;
    private C0369a f358c = null;
    private int f359e = -1;

    public class C0369a extends BroadcastReceiver {
        final /* synthetic */ C0370d f354a;

        public C0369a(C0370d c0370d) {
            this.f354a = c0370d;
        }

        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                    this.f354a.f356a = false;
                    int intExtra = intent.getIntExtra("status", 0);
                    int intExtra2 = intent.getIntExtra("plugged", 0);
                    int intExtra3 = intent.getIntExtra("level", -1);
                    int intExtra4 = intent.getIntExtra("scale", -1);
                    if (intExtra3 <= 0 || intExtra4 <= 0) {
                        this.f354a.f359e = -1;
                    } else {
                        this.f354a.f359e = (intExtra3 * 100) / intExtra4;
                    }
                    switch (intExtra) {
                        case 2:
                            this.f354a.f357b = "4";
                            break;
                        case 3:
                        case 4:
                            this.f354a.f357b = "3";
                            break;
                        default:
                            this.f354a.f357b = null;
                            break;
                    }
                    switch (intExtra2) {
                        case 1:
                            this.f354a.f357b = Constants.VIA_SHARE_TYPE_INFO;
                            this.f354a.f356a = true;
                            return;
                        case 2:
                            this.f354a.f357b = "5";
                            this.f354a.f356a = true;
                            return;
                        default:
                            return;
                    }
                }
            } catch (Exception e) {
                this.f354a.f357b = null;
            }
        }
    }

    private C0370d() {
    }

    public static synchronized C0370d m393a() {
        C0370d c0370d;
        synchronized (C0370d.class) {
            if (f355d == null) {
                f355d = new C0370d();
            }
            c0370d = f355d;
        }
        return c0370d;
    }

    public void m396b() {
        this.f358c = new C0369a(this);
        C0455f.getServiceContext().registerReceiver(this.f358c, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    public void m397c() {
        if (this.f358c != null) {
            try {
                C0455f.getServiceContext().unregisterReceiver(this.f358c);
            } catch (Exception e) {
            }
        }
        this.f358c = null;
    }

    public String m398d() {
        return this.f357b;
    }

    public boolean m399e() {
        return this.f356a;
    }

    public int m400f() {
        return this.f359e;
    }
}
