package com.baidu.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Handler;

class C1618d implements am {
    private static C1618d f4480a = null;
    final Handler f4481do = new Handler();
    private boolean f4482for = false;
    private boolean f4483if = true;
    private boolean f4484int = false;
    private boolean f4485new = false;
    private C0519a f4486try = null;

    private class C0519a extends BroadcastReceiver {
        final /* synthetic */ C1618d f2255a;

        private C0519a(C1618d c1618d) {
            this.f2255a = c1618d;
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null && this.f2255a.f4481do != null) {
                this.f2255a.m4604try();
            }
        }
    }

    private class C0520b implements Runnable {
        final /* synthetic */ C1618d f2256a;

        private C0520b(C1618d c1618d) {
            this.f2256a = c1618d;
        }

        public void run() {
            if (this.f2256a.f4484int && this.f2256a.f4483if) {
                C1979l.m5994u().m5999z();
                this.f2256a.f4481do.postDelayed(this, (long) C1974b.ao);
                this.f2256a.f4485new = true;
                return;
            }
            this.f2256a.f4485new = false;
        }
    }

    private C1618d() {
    }

    public static C1618d m4603new() {
        if (f4480a == null) {
            f4480a = new C1618d();
        }
        return f4480a;
    }

    private void m4604try() {
        State state;
        State state2 = State.UNKNOWN;
        try {
            state = ((ConnectivityManager) C1976f.getServiceContext().getSystemService("connectivity")).getNetworkInfo(1).getState();
        } catch (Exception e) {
            state = state2;
        }
        if (State.CONNECTED != state) {
            this.f4484int = false;
        } else if (!this.f4484int) {
            this.f4484int = true;
            this.f4481do.postDelayed(new C0520b(), (long) C1974b.ao);
            this.f4485new = true;
        }
    }

    public void mo1836a() {
        this.f4483if = false;
    }

    public void m4606do() {
        try {
            this.f4486try = new C0519a();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            C1976f.getServiceContext().registerReceiver(this.f4486try, intentFilter);
            this.f4482for = true;
            m4604try();
        } catch (Exception e) {
        }
        this.f4483if = true;
    }

    public void m4607for() {
        if (this.f4486try == null) {
            this.f4486try = new C0519a();
        }
        try {
            if (!this.f4482for) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                C1976f.getServiceContext().registerReceiver(this.f4486try, intentFilter);
                m4604try();
                this.f4482for = true;
            }
        } catch (Exception e) {
        }
    }

    public void mo1837if() {
        this.f4483if = true;
        if (!this.f4485new && this.f4483if) {
            this.f4481do.postDelayed(new C0520b(), (long) C1974b.ao);
            this.f4485new = true;
        }
    }

    public void m4609int() {
        try {
            C1976f.getServiceContext().unregisterReceiver(this.f4486try);
        } catch (Exception e) {
        }
        this.f4483if = false;
        this.f4486try = null;
    }
}
