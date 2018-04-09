package com.baidu.location.p007b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Handler;
import com.baidu.location.C0455f;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p011e.C0426h;

public class C0376g {
    private static C0376g f382b = null;
    final Handler f383a = new Handler();
    private C0374a f384c = null;
    private boolean f385d = false;
    private boolean f386e = false;
    private boolean f387f = false;
    private boolean f388g = true;
    private boolean f389h = false;

    private class C0374a extends BroadcastReceiver {
        final /* synthetic */ C0376g f380a;

        private C0374a(C0376g c0376g) {
            this.f380a = c0376g;
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null && this.f380a.f383a != null) {
                this.f380a.m434f();
            }
        }
    }

    private class C0375b implements Runnable {
        final /* synthetic */ C0376g f381a;

        private C0375b(C0376g c0376g) {
            this.f381a = c0376g;
        }

        public void run() {
            if (this.f381a.f385d && C0370d.m393a().m399e() && C0426h.m767a().m779d()) {
                this.f381a.m435g();
            }
            if (this.f381a.f385d && this.f381a.f388g) {
                this.f381a.f383a.postDelayed(this, (long) C0468j.f886N);
                this.f381a.f387f = true;
                return;
            }
            this.f381a.f387f = false;
        }
    }

    private C0376g() {
    }

    public static synchronized C0376g m428a() {
        C0376g c0376g;
        synchronized (C0376g.class) {
            if (f382b == null) {
                f382b = new C0376g();
            }
            c0376g = f382b;
        }
        return c0376g;
    }

    private void m434f() {
        State state;
        State state2 = State.UNKNOWN;
        try {
            state = ((ConnectivityManager) C0455f.getServiceContext().getSystemService("connectivity")).getNetworkInfo(1).getState();
        } catch (Exception e) {
            state = state2;
        }
        if (State.CONNECTED != state) {
            this.f385d = false;
        } else if (!this.f385d) {
            this.f385d = true;
            this.f383a.postDelayed(new C0375b(), (long) C0468j.f886N);
            this.f387f = true;
        }
    }

    private void m435g() {
        new C0377h(this).start();
    }

    public synchronized void m436b() {
        if (C0455f.isServing) {
            if (!this.f389h) {
                try {
                    this.f384c = new C0374a();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    C0455f.getServiceContext().registerReceiver(this.f384c, intentFilter);
                    this.f386e = true;
                    m434f();
                } catch (Exception e) {
                }
                this.f388g = true;
                this.f389h = true;
            }
        }
    }

    public synchronized void m437c() {
        if (this.f389h) {
            try {
                C0455f.getServiceContext().unregisterReceiver(this.f384c);
            } catch (Exception e) {
            }
            this.f388g = false;
            this.f389h = false;
            this.f387f = false;
            this.f384c = null;
        }
    }

    public void m438d() {
        if (this.f389h) {
            this.f388g = true;
            if (!this.f387f && this.f388g) {
                this.f383a.postDelayed(new C0375b(), (long) C0468j.f886N);
                this.f387f = true;
            }
        }
    }

    public void m439e() {
        this.f388g = false;
    }
}
