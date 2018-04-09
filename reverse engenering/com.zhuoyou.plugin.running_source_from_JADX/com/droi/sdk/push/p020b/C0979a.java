package com.droi.sdk.push.p020b;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.push.ag;
import com.droi.sdk.push.utils.C1011f;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1013h;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.utility.DroiDownloadFile;
import com.droi.sdk.utility.DroiDownloadFile.DroiDownloadFileEventListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class C0979a {
    private static C0979a f3242a = null;
    private Context f3243b;
    private NotificationManager f3244c;
    private Map f3245d = new HashMap();
    private String f3246e = C1011f.m3134a();
    private Map f3247f = new HashMap();
    private String[] f3248g = new String[100];
    private int f3249h = -1;
    private final DroiDownloadFileEventListener f3250i = new C0980b(this);

    private C0979a(Context context) {
        C1012g.m3138a("initTask");
        this.f3243b = context.getApplicationContext();
        this.f3244c = (NotificationManager) this.f3243b.getSystemService(MessageObj.CATEGORY_NOTI);
    }

    private Bitmap m3008a() {
        PackageManager packageManager = this.f3243b.getPackageManager();
        try {
            return ((BitmapDrawable) packageManager.getApplicationInfo(this.f3243b.getPackageName(), 0).loadIcon(packageManager)).getBitmap();
        } catch (Exception e) {
            C1012g.m3137a(e);
            return null;
        }
    }

    public static C0979a m3009a(Context context) {
        synchronized (C0979a.class) {
            if (f3242a == null) {
                f3242a = new C0979a(context);
            }
        }
        return f3242a;
    }

    private void m3013a(String str, int i, PendingIntent pendingIntent) {
        C0981c c0981c = (C0981c) this.f3245d.get(str);
        if (c0981c != null) {
            Bitmap a;
            int d;
            int d2;
            Notification notification = c0981c.f3256e;
            if (notification == null) {
                notification = new Builder(this.f3243b).setTicker(null).setSmallIcon(C1013h.m3145c(this.f3243b, "ic_launcher")).setContentText(null).setContentTitle(null).build();
                RemoteViews remoteViews = new RemoteViews(this.f3243b.getPackageName(), C1013h.m3143a(this.f3243b, "dp_download_notification_layout"));
                a = m3008a();
                d = C1013h.m3146d(this.f3243b, "dp_download_icon");
                d2 = C1013h.m3146d(this.f3243b, "dp_download_title");
                remoteViews.setImageViewBitmap(d, a);
                remoteViews.setTextViewText(d2, c0981c.m3021a());
                notification.contentView = remoteViews;
                notification.flags = 32;
                c0981c.f3256e = notification;
            }
            Notification notification2 = notification;
            int d3;
            if (i < 0) {
                d3 = C1013h.m3146d(this.f3243b, "dp_download_text");
                int d4 = C1013h.m3146d(this.f3243b, "dp_progress_group");
                d = C1013h.m3144b(this.f3243b, "dp_download_fail_text");
                RemoteViews remoteViews2 = notification2.contentView;
                if (d4 > 0 && d3 > 0) {
                    remoteViews2.setViewVisibility(d4, 8);
                    remoteViews2.setViewVisibility(d3, 0);
                    CharSequence charSequence = "Download failed!";
                    if (d > 0) {
                        charSequence = this.f3243b.getString(d);
                    }
                    remoteViews2.setTextViewText(d3, charSequence);
                }
                notification2.flags = 16;
                notification2.contentIntent = pendingIntent;
                Toast.makeText(this.f3243b, "Download failed!", 0).show();
            } else if (i >= 100) {
                this.f3244c.cancel(str, (int) c0981c.f3252a);
                Toast.makeText(this.f3243b, "Download successfully, click to install!", 0).show();
                return;
            } else {
                RemoteViews remoteViews3 = new RemoteViews(this.f3243b.getPackageName(), C1013h.m3143a(this.f3243b, "dp_download_notification_layout"));
                a = m3008a();
                d = C1013h.m3146d(this.f3243b, "dp_download_icon");
                d2 = C1013h.m3146d(this.f3243b, "dp_download_title");
                remoteViews3.setImageViewBitmap(d, a);
                remoteViews3.setTextViewText(d2, c0981c.m3021a());
                d3 = C1013h.m3146d(this.f3243b, "dp_download_text");
                d = C1013h.m3146d(this.f3243b, "dp_progress_group");
                d2 = C1013h.m3146d(this.f3243b, "dp_download_progressbar");
                int d5 = C1013h.m3146d(this.f3243b, "dp_download_progress");
                remoteViews3.setViewVisibility(d, 0);
                remoteViews3.setViewVisibility(d3, 8);
                remoteViews3.setTextViewText(d5, i + "%");
                remoteViews3.setProgressBar(d2, 100, i, false);
                notification2.contentView = remoteViews3;
            }
            this.f3244c.notify(str, (int) c0981c.f3252a, notification2);
        }
    }

    private void m3014a(String str, long j) {
        if (C1015j.m3168d(str) && j >= 0) {
            if (this.f3247f.containsKey(str)) {
                this.f3247f.put(str, Long.valueOf(j));
                return;
            }
            int i = (this.f3249h + 1) % 100;
            if (i < this.f3249h) {
                this.f3247f.remove(this.f3248g[i]);
            }
            this.f3248g[i] = str;
            this.f3247f.put(str, Long.valueOf(j));
            this.f3249h = i;
        }
    }

    private boolean m3017b(String str, long j) {
        boolean z = false;
        if (C1015j.m3168d(str)) {
            File file = new File(str);
            if (file.exists()) {
                z = C1015j.m3157a(this.f3243b, file);
                if (z && j > 0) {
                    ag.m3007a(this.f3243b, j, "m01", 9, 1, -1, "DROIPUSH");
                }
            }
        } else {
            C1012g.m3138a("Invalid app file path: " + str);
        }
        return z;
    }

    public int m3019a(long j, int i, String str, String str2) {
        if (!C1015j.m3168d(str)) {
            C1012g.m3138a("Download url is invalid, abort!");
            return -1;
        } else if (!C1015j.m3168d(str2)) {
            C1012g.m3138a("Compound name is invalid, abort!");
            return -1;
        } else if (this.f3245d.containsKey(str)) {
            return 0;
        } else {
            String substring;
            String str3 = null;
            int indexOf = str2.indexOf("|");
            if (indexOf >= 0) {
                substring = str2.substring(0, indexOf);
                str3 = str2.substring(indexOf + 1, str2.length());
                str2 = substring;
            }
            if (!C1015j.m3168d(str2)) {
                C1012g.m3138a("Invalid package name in message: " + j);
                return -1;
            } else if (C1015j.m3172g(this.f3243b)) {
                substring = C1011f.m3134a();
                File file = new File(substring);
                if (!file.exists()) {
                    file.mkdirs();
                }
                try {
                    if (m3017b(substring + "/" + C1015j.m3153a(str), j)) {
                        return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                    }
                    C0981c c0981c = new C0981c();
                    c0981c.f3252a = j;
                    c0981c.f3253b = i;
                    c0981c.f3254c = str3;
                    c0981c.f3255d = str2;
                    this.f3245d.put(str, c0981c);
                    try {
                        return DroiDownloadFile.instance().downloadFile(str, substring + "/" + C1015j.m3153a(str), this.f3250i);
                    } catch (Exception e) {
                        C1012g.m3139b(e);
                        this.f3245d.remove(str);
                        return -1;
                    }
                } catch (Exception e2) {
                    C1012g.m3137a(e2);
                    return -1;
                }
            } else {
                int b = C1013h.m3144b(this.f3243b, "dp_nosdcard_text");
                substring = "SD card not avaliable, check the sdcard and permission";
                if (b > 0) {
                    Toast.makeText(this.f3243b, this.f3243b.getString(b), 1).show();
                } else {
                    C1012g.m3140b("download failed: " + substring);
                }
                return -1;
            }
        }
    }

    public long m3020a(String str) {
        Long l = (Long) this.f3247f.get(str);
        return l == null ? -1 : l.longValue();
    }
}
