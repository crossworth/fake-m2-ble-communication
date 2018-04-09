package com.droi.sdk.selfupdate;

import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.selfupdate.util.C1047b;
import com.droi.sdk.selfupdate.util.C1050d;
import java.io.File;

public class C1044q {

    static class C1043a {
        protected Context f3469a;
        protected Notification f3470b = new Notification();
        protected Builder f3471c;
        private String f3472d = "";
        private String f3473e = "";

        public C1043a(Context context) {
            this.f3469a = context.getApplicationContext();
            if (VERSION.SDK_INT >= 14) {
                this.f3471c = new Builder(context);
            }
        }

        public C1043a m3237a(int i) {
            if (VERSION.SDK_INT >= 14) {
                this.f3471c.setProgress(100, i, false);
            }
            return this;
        }

        public C1043a m3239a(CharSequence charSequence) {
            if (VERSION.SDK_INT >= 14) {
                this.f3471c.setContentText(charSequence);
            }
            this.f3473e = charSequence.toString();
            return this;
        }

        public C1043a m3242b(CharSequence charSequence) {
            if (VERSION.SDK_INT >= 14) {
                this.f3471c.setContentTitle(charSequence);
            }
            this.f3472d = charSequence.toString();
            return this;
        }

        public C1043a m3243c(CharSequence charSequence) {
            if (VERSION.SDK_INT >= 16) {
                this.f3471c.setStyle(new BigTextStyle().bigText(charSequence));
            }
            return this;
        }

        public C1043a m3238a(PendingIntent pendingIntent) {
            if (VERSION.SDK_INT >= 14) {
                this.f3471c.setContentIntent(pendingIntent);
            }
            this.f3470b.contentIntent = pendingIntent;
            return this;
        }

        public C1043a m3240a(boolean z) {
            if (VERSION.SDK_INT >= 14) {
                this.f3471c.setAutoCancel(z);
            }
            Notification notification;
            if (z) {
                notification = this.f3470b;
                notification.flags |= 16;
            } else {
                notification = this.f3470b;
                notification.flags &= -17;
            }
            return this;
        }

        public C1043a m3241b(int i) {
            if (VERSION.SDK_INT >= 14) {
                this.f3471c.setSmallIcon(i);
            }
            this.f3470b.icon = i;
            return this;
        }

        public C1043a m3244d(CharSequence charSequence) {
            if (VERSION.SDK_INT >= 14) {
                this.f3471c.setTicker(charSequence);
            }
            this.f3470b.tickerText = charSequence;
            return this;
        }

        public Notification m3236a() {
            if (VERSION.SDK_INT >= 16) {
                return this.f3471c.build();
            }
            return this.f3471c.getNotification();
        }
    }

    protected static void m3246a(Context context, DroiUpdateResponse droiUpdateResponse, boolean z, File file) {
        C1041o.m3235a("m01", droiUpdateResponse.m3193a(), C1041o.f3452c, System.currentTimeMillis());
        Intent intent = new Intent(context, DroiUpdateDialogActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("response", droiUpdateResponse);
        if (z) {
            bundle.putString("file", file.getAbsolutePath());
        } else {
            bundle.putString("file", (String) null);
        }
        bundle.putBoolean("manual", C1042p.f3467c);
        intent.putExtras(bundle);
        intent.addFlags(33554432);
        context.startActivity(intent);
    }

    protected static void m3247b(Context context, DroiUpdateResponse droiUpdateResponse, boolean z, File file) {
        C1041o.m3235a("m01", droiUpdateResponse.m3193a(), C1041o.f3451b, System.currentTimeMillis());
        ((NotificationManager) context.getSystemService(MessageObj.CATEGORY_NOTI)).notify("update", 0, C1044q.m3248c(context, droiUpdateResponse, z, file).m3236a());
    }

    protected static C1043a m3245a(Context context) {
        CharSequence charSequence = context.getString(C1050d.m3292a(context).m3295c("droi_downloading")) + C1047b.m3277k(context);
        C1043a c1043a = new C1043a(context);
        c1043a.m3242b(charSequence).m3244d(charSequence).m3241b(context.getApplicationContext().getApplicationInfo().icon).m3240a(true);
        return c1043a;
    }

    protected static C1043a m3248c(Context context, DroiUpdateResponse droiUpdateResponse, boolean z, File file) {
        CharSequence string;
        Intent intent;
        CharSequence k = C1047b.m3277k(context);
        CharSequence charSequence = droiUpdateResponse.m3194a(context, z) + context.getString(C1050d.m3292a(context).m3295c("droi_update_content")) + droiUpdateResponse.getContent();
        if (z) {
            string = context.getString(C1050d.m3292a(context).m3295c("droi_dialog_installapk"));
            intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        } else {
            String string2 = context.getString(C1050d.m3292a(context).m3295c("droi_update_title"));
            Intent intent2 = new Intent(context, DroiUpdateDialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("response", droiUpdateResponse);
            bundle.putString("file", (String) null);
            intent2.putExtras(bundle);
            intent2.addFlags(33554432);
            intent = intent2;
            Object obj = string2;
        }
        CharSequence charSequence2 = k + string;
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 134217728);
        C1043a c1043a = new C1043a(context);
        c1043a.m3243c(charSequence).m3242b(k).m3239a(string).m3244d(charSequence2).m3238a(activity).m3241b(context.getApplicationContext().getApplicationInfo().icon).m3240a(true);
        return c1043a;
    }
}
