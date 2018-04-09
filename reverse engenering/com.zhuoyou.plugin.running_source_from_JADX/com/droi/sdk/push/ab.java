package com.droi.sdk.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.push.utils.C1016k;

class ab extends Thread {
    Context f3231a;
    final /* synthetic */ aa f3232b;

    public ab(aa aaVar, Context context) {
        this.f3232b = aaVar;
        this.f3231a = context;
    }

    public void run() {
        try {
            C1016k b = ad.m2998b(this.f3231a);
            C1016k a = ad.m2989a(b);
            if (b != null) {
                String appId = DroiPush.getAppId(this.f3231a);
                String secret = DroiPush.getSecret(this.f3231a);
                String packageName = this.f3231a.getPackageName();
                Intent intent = new Intent("com.droi.sdk.push.action.START");
                intent.putExtra("app_id", appId);
                intent.putExtra("secret", secret);
                intent.putExtra("package", packageName);
                intent.putExtra("CMD", "RESET_UDP");
                if (a != null && C1015j.m3168d(a.f3361b) && C1015j.m3168d(a.f3360a)) {
                    Object obj = (a.f3360a == null || a.m3174a(b) == -1) ? null : 1;
                    if (obj != null) {
                        intent.setClassName(a.f3361b, DroiPushService.class.getName());
                        this.f3231a.startService(intent);
                    }
                } else {
                    Pair c = ad.m3001c(this.f3231a);
                    if (c == null || b.f3361b == null || b.f3361b.equals(c.first)) {
                        intent.setComponent(new ComponentName(packageName, DroiPushService.class.getName()));
                    } else {
                        intent.setComponent(new ComponentName((String) c.first, (String) c.second));
                    }
                    this.f3231a.startService(intent);
                }
                this.f3232b.f3228c.removeMessages(3);
                this.f3232b.f3230e = null;
            }
        } catch (Throwable th) {
            C1012g.m3137a(new Exception(th));
        } finally {
            this.f3232b.f3228c.removeMessages(3);
            this.f3232b.f3230e = null;
        }
    }
}
