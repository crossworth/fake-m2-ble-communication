package com.baidu.location.p007b;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.baidu.location.C0455f;
import com.baidu.location.p005a.C0351i;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p012f.C0448d;
import com.zhuoyou.plugin.running.app.Permissions;

public class C0367b {
    private static C0367b f346a = null;
    private boolean f347b = false;
    private Handler f348c = null;
    private AlarmManager f349d = null;
    private C0366a f350e = null;
    private PendingIntent f351f = null;
    private long f352g = 0;

    private class C0366a extends BroadcastReceiver {
        final /* synthetic */ C0367b f345a;

        private C0366a(C0367b c0367b) {
            this.f345a = c0367b;
        }

        public void onReceive(Context context, Intent intent) {
            if (this.f345a.f347b && intent.getAction().equals("com.baidu.location.autonotifyloc_7.0.1") && this.f345a.f348c != null) {
                this.f345a.f351f = null;
                this.f345a.f348c.sendEmptyMessage(1);
            }
        }
    }

    private C0367b() {
    }

    public static synchronized C0367b m381a() {
        C0367b c0367b;
        synchronized (C0367b.class) {
            if (f346a == null) {
                f346a = new C0367b();
            }
            c0367b = f346a;
        }
        return c0367b;
    }

    private void m386f() {
        if (System.currentTimeMillis() - this.f352g >= 1000) {
            if (this.f351f != null) {
                this.f349d.cancel(this.f351f);
                this.f351f = null;
            }
            if (this.f351f == null) {
                this.f351f = PendingIntent.getBroadcast(C0455f.getServiceContext(), 0, new Intent("com.baidu.location.autonotifyloc_7.0.1"), 134217728);
                this.f349d.set(0, System.currentTimeMillis() + ((long) C0468j.f892T), this.f351f);
            }
            Message message = new Message();
            message.what = 22;
            if (System.currentTimeMillis() - this.f352g >= ((long) C0468j.f893U)) {
                this.f352g = System.currentTimeMillis();
                if (!C0448d.m886a().m925i()) {
                    C0351i.m280c().m297b(message);
                }
            }
        }
    }

    private void m387g() {
        if (this.f347b) {
            try {
                if (this.f351f != null) {
                    this.f349d.cancel(this.f351f);
                    this.f351f = null;
                }
                C0455f.getServiceContext().unregisterReceiver(this.f350e);
            } catch (Exception e) {
            }
            this.f349d = null;
            this.f350e = null;
            this.f348c = null;
            this.f347b = false;
        }
    }

    public void m388b() {
        if (!this.f347b && C0468j.f892T >= 10000) {
            if (this.f348c == null) {
                this.f348c = new C0368c(this);
            }
            this.f349d = (AlarmManager) C0455f.getServiceContext().getSystemService("alarm");
            this.f350e = new C0366a();
            C0455f.getServiceContext().registerReceiver(this.f350e, new IntentFilter("com.baidu.location.autonotifyloc_7.0.1"), Permissions.PERMISSION_LOCATION, null);
            this.f351f = PendingIntent.getBroadcast(C0455f.getServiceContext(), 0, new Intent("com.baidu.location.autonotifyloc_7.0.1"), 134217728);
            this.f349d.set(0, System.currentTimeMillis() + ((long) C0468j.f892T), this.f351f);
            this.f347b = true;
            this.f352g = System.currentTimeMillis();
        }
    }

    public void m389c() {
        if (this.f347b && this.f348c != null) {
            this.f348c.sendEmptyMessage(2);
        }
    }

    public void m390d() {
        if (this.f347b && this.f348c != null) {
            this.f348c.sendEmptyMessage(1);
        }
    }

    public void m391e() {
        if (this.f347b && this.f348c != null) {
            this.f348c.sendEmptyMessage(1);
        }
    }
}
