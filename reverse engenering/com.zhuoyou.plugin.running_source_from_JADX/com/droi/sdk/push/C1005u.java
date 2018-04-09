package com.droi.sdk.push;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.DroiError;
import com.droi.sdk.push.utils.C1008c;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1013h;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.utility.Utility;
import com.umeng.facebook.share.internal.ShareConstants;

class C1005u {
    private static DroiMessageHandler f3340a = null;
    private static C1005u f3341b;
    private Handler f3342c = null;
    private Looper f3343d = null;
    private int f3344e = 0;
    private Context f3345f;
    private NotificationManager f3346g;
    private Integer[] f3347h;
    private String f3348i = "DROI_TITLE";
    private String f3349j = "DROI_SUMMARY";

    private C1005u(Context context) {
        this.f3345f = context.getApplicationContext();
        this.f3346g = (NotificationManager) this.f3345f.getSystemService(MessageObj.CATEGORY_NOTI);
        if (this.f3343d == null) {
            HandlerThread handlerThread = new HandlerThread("NotificationBuilder");
            handlerThread.start();
            this.f3343d = handlerThread.getLooper();
        }
        this.f3342c = new Handler(this.f3343d);
        this.f3347h = m3114b(this.f3345f);
        if (this.f3347h != null && this.f3347h.length == 2) {
            C1012g.m3138a("tc:ts " + this.f3347h[0] + ":" + this.f3347h[1]);
        }
    }

    private Intent m3103a(String str, String str2) {
        Intent intent = new Intent();
        intent.setAction(str + ".Action.START");
        intent.putExtra("type", 2);
        intent.putExtra("url", str2);
        return intent;
    }

    private Intent m3104a(String str, String str2, String str3) {
        Intent intent = new Intent();
        intent.setAction(str + ".Action.START");
        intent.putExtra("type", 1);
        intent.putExtra("activity", str2);
        intent.putExtra("data", str3);
        return intent;
    }

    private Intent m3105a(String str, String str2, String str3, String str4) {
        if (str == null || str3 == null || str2 == null) {
            return null;
        }
        Intent intent = new Intent();
        intent.setAction(str + ".Action.START");
        intent.putExtra("type", 3);
        Bundle bundle = new Bundle();
        bundle.putString("package", str2);
        bundle.putString("durl", str3);
        if (!(str4 == null || str4.trim().equals(""))) {
            bundle.putString("iurl", str4);
        }
        intent.putExtra("dinfo", bundle);
        return intent;
    }

    private Bitmap m3106a(String str) {
        DroiError droiError = new DroiError();
        return !droiError.isOk() ? null : Utility.getBitmap(str, 0, 0, droiError);
    }

    private RemoteViews m3107a(Context context, C1022z c1022z) {
        RemoteViews remoteViews = null;
        if (context != null) {
            int a = C1013h.m3143a(context, "dp_push_notification_layout");
            if (a > 0) {
                remoteViews = new RemoteViews(context.getPackageName(), a);
                a = C1013h.m3146d(context, "dp_simple_notification");
                int d = C1013h.m3146d(context, "dp_banner_imageview");
                int d2 = C1013h.m3146d(context, "dp_big_imageview");
                if (a > 0) {
                    if (c1022z != C1022z.SIMPLE_VIEW) {
                        remoteViews.setViewVisibility(a, 8);
                    } else {
                        remoteViews.setViewVisibility(a, 0);
                    }
                }
                if (d > 0) {
                    if (c1022z != C1022z.BANNER_VIEW) {
                        remoteViews.setViewVisibility(d, 8);
                    } else {
                        remoteViews.setViewVisibility(d, 0);
                    }
                }
                if (d2 > 0) {
                    if (c1022z != C1022z.BIG_VIEW) {
                        remoteViews.setViewVisibility(d2, 8);
                    } else {
                        remoteViews.setViewVisibility(d2, 0);
                    }
                }
            }
        }
        return remoteViews;
    }

    public static synchronized C1005u m3108a(Context context) {
        C1005u c1005u;
        synchronized (C1005u.class) {
            if (f3341b == null) {
                f3341b = new C1005u(context);
            }
            c1005u = f3341b;
        }
        return c1005u;
    }

    private void m3110a(View view, C1020y c1020y) {
        if (view != null && c1020y != null) {
            c1020y.mo1935a(view);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    m3110a(viewGroup.getChildAt(i), c1020y);
                }
            }
        }
    }

    static void m3111a(DroiMessageHandler droiMessageHandler) {
        f3340a = droiMessageHandler;
    }

    @TargetApi(16)
    private Notification m3112b(Context context, C0991f c0991f) {
        Notification notification = null;
        C1012g.m3141c("buildSimpleNotification: " + c0991f.f3281a);
        if (c0991f.m3052a(context)) {
            Builder builder = new Builder(context);
            builder.setContentTitle(c0991f.f3283c);
            builder.setContentText(c0991f.f3284d);
            builder.setTicker(c0991f.f3285e);
            int c = C1015j.m3163c(context);
            if (c > 0) {
                builder.setSmallIcon(c);
            }
            RemoteViews a = m3107a(context, C1022z.SIMPLE_VIEW);
            if (a != null) {
                int d = C1013h.m3146d(context, "dp_notify_icon");
                int d2 = C1013h.m3146d(context, "dp_notify_title");
                int d3 = C1013h.m3146d(context, "dp_notify_text");
                if (d > 0 && d2 > 0 && d3 > 0) {
                    if (c0991f.f3286f != null) {
                        Bitmap a2 = m3106a(c0991f.f3286f);
                        if (a2 != null) {
                            a.setImageViewBitmap(d, a2);
                        } else {
                            a.setImageViewResource(d, c);
                        }
                    } else {
                        a.setImageViewResource(d, c);
                    }
                    a.setTextViewText(d2, c0991f.f3283c);
                    a.setTextViewText(d3, c0991f.f3284d);
                    if (!(this.f3347h[0] == null || this.f3347h[1] == null)) {
                        a.setTextColor(d2, this.f3347h[0].intValue());
                        a.setTextColor(d3, this.f3347h[1].intValue());
                    }
                    PendingIntent e = m3118e(context, c0991f);
                    if (e != null) {
                        builder.setContentIntent(e);
                    }
                    e = m3117d(context, c0991f);
                    if (e != null) {
                        builder.setDeleteIntent(e);
                    }
                    notification = builder.build();
                    notification.contentView = a;
                    m3119a(context, notification, c0991f);
                    if (VERSION.SDK_INT >= 16 && c0991f.f3287g != null && c0991f.f3287g.length() > 0) {
                        RemoteViews a3 = m3107a(context, C1022z.BIG_VIEW);
                        if (a3 != null) {
                            c = C1013h.m3146d(context, "dp_big_imageview");
                            Bitmap a4 = m3106a(c0991f.f3287g);
                            if (a4 != null) {
                                a3.setImageViewBitmap(c, a4);
                                notification.bigContentView = a3;
                            }
                        }
                    }
                }
            } else {
                C1012g.m3142d("DroiPushHandler notification layout not found");
            }
        }
        return notification;
    }

    private Integer[] m3114b(Context context) {
        Integer[] numArr = new Integer[]{null, null};
        Builder builder = new Builder(this.f3345f);
        builder.setContentTitle(this.f3348i);
        builder.setContentText(this.f3349j);
        Notification build = builder.build();
        if (!(build == null || build.contentView == null)) {
            View view = (ViewGroup) build.contentView.apply(this.f3345f, new FrameLayout(this.f3345f));
            if (view == null) {
                return numArr;
            }
            TextView textView = (TextView) view.findViewById(16908310);
            TextView textView2 = (TextView) view.findViewById(16908304);
            if (textView == null || textView2 == null) {
                m3110a(view, new C1021x(this, numArr));
            } else {
                numArr[0] = Integer.valueOf(textView.getCurrentTextColor());
                numArr[1] = Integer.valueOf(textView2.getCurrentTextColor());
                return numArr;
            }
        }
        return numArr;
    }

    @TargetApi(16)
    private Notification m3115c(Context context, C0991f c0991f) {
        Notification notification = null;
        if (c0991f.m3052a(context)) {
            Builder builder = new Builder(context);
            int c = C1015j.m3163c(context);
            if (c > 0) {
                builder.setSmallIcon(c);
            }
            builder.setContentTitle(c0991f.f3283c);
            builder.setContentText(c0991f.f3284d);
            builder.setTicker(c0991f.f3285e);
            if (c0991f.f3286f != null) {
                RemoteViews a = m3107a(context, C1022z.BANNER_VIEW);
                if (a != null) {
                    int d = C1013h.m3146d(context, "dp_banner_imageview");
                    if (d > 0) {
                        Bitmap a2 = m3106a(c0991f.f3286f);
                        if (a2 != null) {
                            a.setImageViewBitmap(d, a2);
                            PendingIntent e = m3118e(context, c0991f);
                            if (e != null) {
                                builder.setContentIntent(e);
                            }
                            e = m3117d(context, c0991f);
                            if (e != null) {
                                builder.setDeleteIntent(e);
                            }
                            notification = builder.build();
                            if (a != null) {
                                notification.contentView = a;
                            }
                            m3119a(context, notification, c0991f);
                            if (VERSION.SDK_INT >= 16 && c0991f.f3287g != null && c0991f.f3287g.length() > 0) {
                                RemoteViews a3 = m3107a(context, C1022z.BIG_VIEW);
                                if (a3 != null) {
                                    c = C1013h.m3146d(context, "dp_big_imageview");
                                    Bitmap a4 = m3106a(c0991f.f3287g);
                                    if (a4 != null) {
                                        a3.setImageViewBitmap(c, a4);
                                        notification.bigContentView = a3;
                                    }
                                }
                            }
                        } else {
                            C1012g.m3142d("DroiPushHandler: bitmap does not exist");
                        }
                    }
                } else {
                    C1012g.m3142d("DroiPushHandler notification layout not found");
                }
            } else {
                C1012g.m3142d("DroiPushHandler: image url in notification is null");
            }
        }
        return notification;
    }

    private void m3116c(C0991f c0991f) {
        if (c0991f != null) {
            C1012g.m3141c("showNotification: " + c0991f.f3281a);
            switch (c0991f.f3282b) {
                case 1:
                case 2:
                    C1012g.m3141c("showNotification: non custom message - " + c0991f.f3281a);
                    Notification a = m3120a(this.f3345f, c0991f);
                    if (a != null) {
                        ag.m3007a(this.f3345f, c0991f.f3281a, "m01", 1, 1, -1, "DROIPUSH");
                        this.f3346g.notify(String.valueOf(c0991f.f3281a), (int) c0991f.f3281a, a);
                        return;
                    }
                    return;
                case 4:
                    String str = c0991f.f3284d;
                    if (!TextUtils.isEmpty(str)) {
                        str = C1008c.m3126a(str);
                    }
                    C1012g.m3141c("showNotification: custom message - " + str);
                    f3340a.onHandleCustomMessage(this.f3345f, str);
                    return;
                default:
                    C1012g.m3140b("Illegal message type: " + c0991f.f3282b);
                    return;
            }
        }
    }

    private PendingIntent m3117d(Context context, C0991f c0991f) {
        Intent intent = new Intent("com.droi.sdk.push.action.REMOVE_NOTIFICATION");
        intent.setPackage(context.getPackageName());
        intent.putExtra(MessageObj.MSGID, c0991f.f3281a);
        return PendingIntent.getBroadcast(context, c0991f.m3048a(), intent, 134217728);
    }

    private PendingIntent m3118e(Context context, C0991f c0991f) {
        String packageName = context.getPackageName();
        Intent a = c0991f.f3292l == 1 ? m3104a(packageName, c0991f.f3293m, c0991f.f3294n) : c0991f.f3292l == 2 ? m3103a(packageName, c0991f.f3293m) : c0991f.f3292l == 3 ? m3105a(packageName, c0991f.f3294n, c0991f.f3293m, c0991f.f3295o) : null;
        if (a == null) {
            return null;
        }
        a.putExtra(MessageObj.MSGID, c0991f.f3281a);
        a.putExtra(ShareConstants.WEB_DIALOG_PARAM_ID, c0991f.m3048a());
        return PendingIntent.getBroadcast(context, c0991f.m3048a(), a, 134217728);
    }

    Notification m3119a(Context context, Notification notification, C0991f c0991f) {
        if (!(notification == null || c0991f == null)) {
            PendingIntent e = m3118e(context, c0991f);
            if (e != null) {
                notification.contentIntent = e;
            }
            e = m3117d(context, c0991f);
            if (e != null) {
                notification.deleteIntent = e;
            }
            if (c0991f.f3288h) {
                notification.flags = 1;
                notification.ledARGB = -16711936;
                notification.ledOnMS = 300;
                notification.ledOffMS = 300;
            }
            if (c0991f.f3289i) {
                notification.flags |= 16;
            } else {
                notification.flags |= 2;
            }
            if (c0991f.f3291k) {
                notification.defaults |= 1;
            }
            if (c0991f.f3290j) {
                notification.defaults |= 2;
            }
        }
        return notification;
    }

    Notification m3120a(Context context, C0991f c0991f) {
        switch (c0991f.f3282b) {
            case 1:
                return m3112b(context, c0991f);
            case 2:
                return m3115c(context, c0991f);
            default:
                return null;
        }
    }

    void m3121a(long j) {
        if (this.f3346g != null) {
            if (j > 0) {
                ag.m3007a(this.f3345f, j, "m01", 3, 1, -1, "DROIPUSH");
            }
            this.f3346g.cancel(String.valueOf(j), (int) j);
        }
    }

    void m3122a(Intent intent, Context context) {
        if (intent != null && context != null) {
            this.f3342c.post(new C1018v(this, context, intent));
        }
    }

    void m3123a(C0991f c0991f) {
        if (c0991f.m3055d()) {
            C1012g.m3141c("onReceivePushMessage: contain image - " + c0991f.f3281a);
            c0991f.m3050a(new C1019w(this));
            c0991f.m3054c();
            return;
        }
        C1012g.m3141c("onReceivePushMessage: do not contain image - " + c0991f.f3281a);
        if (c0991f.m3056e()) {
            m3124b(c0991f);
        }
    }

    void m3124b(C0991f c0991f) {
        int i = this.f3344e + 1;
        this.f3344e = i;
        this.f3344e = i % ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        c0991f.m3049a(this.f3344e);
        m3116c(c0991f);
    }
}
