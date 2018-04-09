package com.droi.sdk.selfupdate;

import android.content.Context;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.util.C1047b;
import java.io.File;

public class DroiUpdate {
    protected static C1032g f3382a;

    public static void initialize(Context context) {
        f3382a = C1032g.m3199a(context);
    }

    public static void update(Context context) {
        if (f3382a != null) {
            f3382a.m3215a(context, false);
        }
    }

    public static void manualUpdate(Context context) {
        if (f3382a != null) {
            f3382a.m3215a(context, true);
        }
    }

    public static void inappUpdate(Context context, String str, int i, DroiInappUpdateListener droiInappUpdateListener) {
        if (f3382a != null) {
            f3382a.m3214a(context, str, i, droiInappUpdateListener);
        }
    }

    public static void downloadInappUpdateFile(DroiInappUpdateResponse droiInappUpdateResponse, String str, DroiInappDownloadListener droiInappDownloadListener) {
        if (f3382a != null) {
            f3382a.m3216a(droiInappUpdateResponse, str, droiInappDownloadListener);
        }
    }

    public static void setUpdateOnlyWifi(boolean z) {
        C1042p.f3465a = z;
    }

    public static void setUpdateAutoPopup(boolean z) {
        C1042p.f3466b = z;
    }

    public static void setUpdateUIStyle(int i) {
        DroiLog.m2871i("DroiUpdate", "style：" + i);
        if (!(i == 0 || i == 1 || i == 2)) {
            i = 2;
        }
        C1042p.f3468d = i;
    }

    public static void setUpdateListener(DroiUpdateListener droiUpdateListener) {
        if (f3382a != null) {
            f3382a.m3217a(droiUpdateListener);
        }
    }

    public static void setDefault() {
        setUpdateListener(null);
        setUpdateAutoPopup(true);
        setUpdateOnlyWifi(true);
        setUpdateUIStyle(2);
    }

    public static void showUpdateDialog(Context context, DroiUpdateResponse droiUpdateResponse) {
        if (f3382a != null) {
            f3382a.m3211a(context, droiUpdateResponse, 0);
        }
    }

    public static void showUpdateNotification(Context context, DroiUpdateResponse droiUpdateResponse) {
        if (f3382a != null) {
            f3382a.m3211a(context, droiUpdateResponse, 1);
        }
    }

    public static void isUpdateIgnore(Context context, DroiUpdateResponse droiUpdateResponse) {
        if (f3382a != null) {
            f3382a.m3219b(context, droiUpdateResponse);
        }
    }

    public static void setUpdateIgnore(Context context, DroiUpdateResponse droiUpdateResponse) {
        if (f3382a != null) {
            f3382a.m3221c(context, droiUpdateResponse);
        }
    }

    public static File getDownloadedFile(Context context, DroiUpdateResponse droiUpdateResponse) {
        if (f3382a != null) {
            return f3382a.m3209a(context, droiUpdateResponse);
        }
        return null;
    }

    public static void installApp(Context context, File file, int i) {
        if (f3382a != null) {
            C1032g.m3203a(context, file, i);
        }
    }

    public static void downloadApp(Context context, DroiUpdateResponse droiUpdateResponse, DroiDownloadListener droiDownloadListener) {
        if (f3382a != null) {
            f3382a.m3212a(context, droiUpdateResponse, droiDownloadListener);
        }
    }

    public static void setTestChannel(Context context, String str) {
        C1047b.m3269d(context, str);
    }
}
