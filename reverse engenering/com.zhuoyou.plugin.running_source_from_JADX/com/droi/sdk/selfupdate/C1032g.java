package com.droi.sdk.selfupdate;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.C1044q.C1043a;
import com.droi.sdk.selfupdate.util.C1046a;
import com.droi.sdk.selfupdate.util.C1047b;
import com.droi.sdk.selfupdate.util.C1049c;
import com.droi.sdk.selfupdate.util.C1049c.C1036b;
import com.droi.sdk.selfupdate.util.C1051e;
import java.io.File;

public class C1032g {
    public static Context f3423a;
    protected static Context f3424b;
    private static Handler f3425c;
    private static C1032g f3426d;
    private DroiUpdateListener f3427e = null;
    private boolean f3428f = false;

    static class C1030a implements Runnable {
        Context f3418a;
        String f3419b = null;
        int f3420c;
        DroiInappUpdateListener f3421d;

        public C1030a(Context context, String str, int i, DroiInappUpdateListener droiInappUpdateListener) {
            this.f3418a = context;
            this.f3419b = str;
            this.f3420c = i;
            this.f3421d = droiInappUpdateListener;
        }

        public void run() {
            DroiInappUpdateResponse b = C1024a.m3196b(C1045s.m3250a(this.f3418a, this.f3419b, this.f3420c));
            if (b == null) {
                DroiLog.m2871i("DroiUpdateImpl", "CheckInappUpdate:TIMEOUT");
                if (this.f3421d != null) {
                    this.f3421d.onUpdateReturned(3, null);
                }
            } else if (b.m3182a() != 0) {
                DroiLog.m2871i("DroiUpdateImpl", "CheckInappUpdate:ERROR");
                if (this.f3421d != null) {
                    this.f3421d.onUpdateReturned(2, b);
                }
            } else if (b.m3183b()) {
                DroiLog.m2871i("DroiUpdateImpl", "CheckInappUpdate:YES");
                if (this.f3421d != null) {
                    this.f3421d.onUpdateReturned(1, b);
                }
            } else {
                DroiLog.m2871i("DroiUpdateImpl", "CheckInappUpdate:NO_UPDATE");
                if (this.f3421d != null) {
                    this.f3421d.onUpdateReturned(0, b);
                }
            }
        }
    }

    static class C1031b implements Runnable {
        Context f3422a;

        public C1031b(Context context) {
            this.f3422a = context;
        }

        public void run() {
            DroiUpdateResponse a = C1024a.m3195a(C1045s.m3249a(this.f3422a));
            if (a == null) {
                DroiLog.m2871i("DroiUpdateImpl", "CheckUpdate:TIMEOUT");
                C1032g.m3206b(3, null);
            } else if (a.getErrorCode() != 0) {
                DroiLog.m2871i("DroiUpdateImpl", "CheckUpdate:ERROR");
                C1032g.m3206b(2, a);
            } else if (a.getUpdateType() != 0) {
                DroiLog.m2871i("DroiUpdateImpl", "CheckUpdate:YES");
                C1032g.m3206b(1, a);
            } else {
                DroiLog.m2871i("DroiUpdateImpl", "CheckUpdate:NO");
                C1032g.m3206b(0, a);
            }
        }
    }

    C1032g(Context context) {
        m3218b(context);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static com.droi.sdk.selfupdate.C1032g m3199a(android.content.Context r3) {
        /*
        r1 = com.droi.sdk.selfupdate.C1032g.class;
        monitor-enter(r1);
        r0 = f3426d;	 Catch:{ all -> 0x001e }
        if (r0 != 0) goto L_0x001a;
    L_0x0007:
        if (r3 != 0) goto L_0x0013;
    L_0x0009:
        r0 = "DroiUpdateImpl";
        r2 = "unexpected null context";
        android.util.Log.e(r0, r2);	 Catch:{ all -> 0x001e }
        r0 = 0;
        monitor-exit(r1);	 Catch:{ all -> 0x001e }
    L_0x0012:
        return r0;
    L_0x0013:
        r0 = new com.droi.sdk.selfupdate.g;	 Catch:{ all -> 0x001e }
        r0.<init>(r3);	 Catch:{ all -> 0x001e }
        f3426d = r0;	 Catch:{ all -> 0x001e }
    L_0x001a:
        monitor-exit(r1);	 Catch:{ all -> 0x001e }
        r0 = f3426d;
        goto L_0x0012;
    L_0x001e:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001e }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.selfupdate.g.a(android.content.Context):com.droi.sdk.selfupdate.g");
    }

    protected void m3218b(Context context) {
        Log.i("DroiUpdateImpl", "DroiUpdate initializing:1.0.008");
        f3423a = context;
        m3220c(context);
        m3208d(context);
    }

    protected void m3220c(Context context) {
        String c = C1047b.m3266c(context);
        if (c != null) {
            String h = C1047b.m3274h(context);
            String d = C1047b.m3268d(context);
            if (d != null && d.equalsIgnoreCase(h)) {
                C1041o.m3235a("m01", c, C1041o.f3463n, System.currentTimeMillis());
                C1047b.m3265b(context, "");
                C1047b.m3267c(context, "");
                C1047b.m3261a(context, 0);
            }
        }
    }

    protected void m3215a(Context context, boolean z) {
        if (context == null) {
            try {
                Log.e("DroiUpdateImpl", "unexpected null context");
                return;
            } catch (Exception e) {
                DroiLog.m2869e("DroiUpdateImpl", e);
                return;
            }
        }
        DroiLog.m2871i("DroiUpdateImpl", "doUpdate,isManualUpdate:" + z);
        f3425c = new C1033h(this, context.getMainLooper(), context);
        DroiLog.m2871i("DroiUpdateImpl", "doWifi");
        if (z || C1047b.m3276j(context) || !C1042p.f3465a) {
            DroiLog.m2871i("DroiUpdateImpl", "isUpdating:" + this.f3428f);
            if (this.f3428f) {
                C1032g.m3206b(5, (DroiUpdateResponse) null);
                return;
            }
            f3424b = context;
            C1042p.f3467c = z;
            this.f3428f = true;
            DroiLog.m2871i("DroiUpdateImpl", "CheckUpdate");
            new Thread(new C1031b(context.getApplicationContext())).start();
            return;
        }
        C1032g.m3206b(4, (DroiUpdateResponse) null);
    }

    protected void m3217a(DroiUpdateListener droiUpdateListener) {
        this.f3427e = droiUpdateListener;
    }

    private static void m3206b(int i, DroiUpdateResponse droiUpdateResponse) {
        Message message = new Message();
        message.what = i;
        message.obj = droiUpdateResponse;
        f3425c.sendMessage(message);
    }

    protected void m3211a(Context context, DroiUpdateResponse droiUpdateResponse, int i) {
        boolean z = false;
        DroiLog.m2871i("DroiUpdateImpl", "handle");
        try {
            File a = m3209a(context, droiUpdateResponse);
            if (a != null) {
                z = true;
            }
            if (C1042p.f3467c) {
                DroiLog.m2871i("DroiUpdateImpl", "ManualUpdate");
                C1044q.m3246a(context, droiUpdateResponse, z, a);
            } else if (m3219b(context, droiUpdateResponse)) {
                DroiLog.m2871i("DroiUpdateImpl", "Ignore");
                this.f3428f = false;
            } else {
                DroiLog.m2871i("DroiUpdateImpl", "updateType:" + droiUpdateResponse.getUpdateType());
                switch (droiUpdateResponse.getUpdateType()) {
                    case 1:
                        switch (i) {
                            case 0:
                                C1044q.m3246a(context, droiUpdateResponse, z, a);
                                return;
                            case 1:
                                C1044q.m3247b(context, droiUpdateResponse, z, a);
                                return;
                            case 2:
                                C1044q.m3247b(context, droiUpdateResponse, z, a);
                                C1044q.m3246a(context, droiUpdateResponse, z, a);
                                return;
                            default:
                                return;
                        }
                    case 2:
                        C1044q.m3246a(context, droiUpdateResponse, z, a);
                        return;
                    case 3:
                        m3213a(context, droiUpdateResponse, z, a);
                        return;
                    default:
                        return;
                }
            }
        } catch (Exception e) {
            DroiLog.m2869e("DroiUpdateImpl", e);
        }
    }

    protected File m3209a(Context context, DroiUpdateResponse droiUpdateResponse) {
        try {
            File b = C1032g.m3205b(context, droiUpdateResponse.getNewMd5());
            if (b.exists() && droiUpdateResponse.getNewMd5().equalsIgnoreCase(C1051e.m3296a(b))) {
                return b;
            }
            return null;
        } catch (Exception e) {
            DroiLog.m2869e("DroiUpdateImpl", e);
            return null;
        }
    }

    protected void m3213a(Context context, DroiUpdateResponse droiUpdateResponse, boolean z, File file) {
        if (z && droiUpdateResponse.getNewMd5().equalsIgnoreCase(C1051e.m3296a(file))) {
            this.f3428f = false;
            C1032g.m3203a(context, file, 3);
            return;
        }
        m3212a(context, droiUpdateResponse, new C1034i(this, context));
    }

    protected boolean m3219b(Context context, DroiUpdateResponse droiUpdateResponse) {
        String b = C1047b.m3264b(context);
        DroiLog.m2871i("DroiUpdateImpl", "ignoreMd5:" + b);
        DroiLog.m2871i("DroiUpdateImpl", "response.newMd5:" + droiUpdateResponse.getNewMd5());
        if (droiUpdateResponse.getNewMd5() == null || !droiUpdateResponse.getNewMd5().equalsIgnoreCase(b)) {
            return false;
        }
        return true;
    }

    protected void m3221c(Context context, DroiUpdateResponse droiUpdateResponse) {
        if (droiUpdateResponse == null) {
            C1047b.m3262a(context, "");
        } else {
            C1047b.m3262a(context, droiUpdateResponse.getNewMd5());
        }
    }

    protected void m3210a(int i, Context context, DroiUpdateResponse droiUpdateResponse, File file) {
        DroiLog.m2871i("DroiUpdateImpl", "listenDialogAction");
        switch (i) {
            case 1:
                DroiLog.m2871i("DroiUpdateImpl", "UpdateDialogAction.OK");
                C1041o.m3235a("m01", droiUpdateResponse.m3193a(), C1041o.f3454e, System.currentTimeMillis());
                m3202a(context, droiUpdateResponse, file);
                return;
            case 2:
                DroiLog.m2871i("DroiUpdateImpl", "UpdateDialogAction.CANCEL");
                this.f3428f = false;
                C1041o.m3235a("m01", droiUpdateResponse.m3193a(), C1041o.f3456g, System.currentTimeMillis());
                return;
            case 3:
                DroiLog.m2871i("DroiUpdateImpl", "UpdateDialogAction.IGNORE");
                this.f3428f = false;
                C1041o.m3235a("m01", droiUpdateResponse.m3193a(), C1041o.f3455f, System.currentTimeMillis());
                m3221c(context, droiUpdateResponse);
                return;
            default:
                return;
        }
    }

    private void m3202a(Context context, DroiUpdateResponse droiUpdateResponse, File file) {
        DroiLog.m2871i("DroiUpdateImpl", "updateDialogPressOk");
        if (droiUpdateResponse.getUpdateType() == 4 && C1046a.m3255a(context, "com.droi.market")) {
            DroiLog.m2871i("DroiUpdateImpl", "UpdateType.MARKET");
            Intent intent = new Intent("com.zhuoyi.appDetailInfo");
            intent.putExtra("auto_download", droiUpdateResponse);
            intent.putExtra("packname", context.getPackageName());
            intent.putExtra("from_path", "ExternelInstall");
            context.startActivity(intent);
        } else if (file == null) {
            DroiLog.m2871i("DroiUpdateImpl", "file == null");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(MessageObj.CATEGORY_NOTI);
            C1043a a = C1044q.m3245a(context);
            if (droiUpdateResponse.getUpdateType() != 3) {
                notificationManager.notify("update", 0, a.m3236a());
            }
            m3212a(context, droiUpdateResponse, new C1035j(this, context, notificationManager, a, droiUpdateResponse));
        } else {
            this.f3428f = false;
            C1032g.m3203a(context, file, droiUpdateResponse.getUpdateType());
        }
    }

    protected static void m3203a(Context context, File file, int i) {
        DroiLog.m2871i("DroiUpdateImpl", "startInstall");
        if (file == null || !file.exists()) {
            DroiLog.m2871i("DroiUpdateImpl", "file == null || !file.exists()");
        } else if (i == 3) {
            DroiLog.m2871i("DroiUpdateImpl", "backgroundInstallAPK");
            boolean z = false;
            if (C1046a.m3257b(context)) {
                DroiLog.m2871i("DroiUpdateImpl", "SystemApp try backgroundInstallAPK");
                z = C1046a.m3258b(file);
            }
            if (!z) {
                DroiLog.m2871i("DroiUpdateImpl", "backgroundInstallAPK failed, try backgroundInstallAPKRoot");
                z = C1046a.m3256a(file);
            }
            if (!z) {
                DroiLog.m2871i("DroiUpdateImpl", "backgroundInstall all failed, normalInstallAPK");
                C1046a.m3254a(context, file);
            }
        } else {
            DroiLog.m2871i("DroiUpdateImpl", "normalInstallAPK");
            C1046a.m3254a(context, file);
        }
    }

    private static File m3205b(Context context, String str) {
        return new File(C1047b.m3259a(context), str + ".apk");
    }

    private static File m3207c(Context context, String str) {
        return new File(C1047b.m3259a(context), str + ".patch");
    }

    protected void m3212a(Context context, DroiUpdateResponse droiUpdateResponse, DroiDownloadListener droiDownloadListener) {
        DroiLog.m2871i("DroiUpdateImpl", "startDownload");
        try {
            String patchUrl;
            String absolutePath;
            if (droiUpdateResponse.isDeltaUpdate()) {
                patchUrl = droiUpdateResponse.getPatchUrl();
                absolutePath = C1032g.m3207c(context, droiUpdateResponse.getPatchMd5()).getAbsolutePath();
            } else {
                patchUrl = droiUpdateResponse.getFileUrl();
                absolutePath = C1032g.m3205b(context, droiUpdateResponse.getNewMd5()).getAbsolutePath();
            }
            C1036b c1037k = new C1037k(this, context, droiUpdateResponse, droiDownloadListener);
            DroiLog.m2871i("DroiUpdateImpl", "downloadFile");
            C1049c.m3286a().m3290a(patchUrl, absolutePath, c1037k);
        } catch (Exception e) {
            DroiLog.m2869e("DroiUpdateImpl", e);
            this.f3428f = false;
            C1041o.m3235a("m01", droiUpdateResponse.m3193a(), C1041o.f3461l, System.currentTimeMillis());
            if (droiDownloadListener != null) {
                droiDownloadListener.onFailed(0);
            }
        }
    }

    private boolean m3208d(Context context) {
        Exception e;
        boolean z;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4101);
            if (packageInfo.activities != null) {
                int i = 0;
                z = false;
                while (i < packageInfo.activities.length) {
                    try {
                        if ("com.droi.sdk.selfupdate.DroiUpdateDialogActivity".equals(packageInfo.activities[i].name)) {
                            z = true;
                        }
                        i++;
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
            } else {
                z = false;
            }
            if (z) {
                return z;
            }
            Log.w("DroiUpdateImpl", "Please add Activity in AndroidManifest!");
            return false;
        } catch (Exception e3) {
            Exception exception = e3;
            z = false;
            e = exception;
            e.printStackTrace();
            return z;
        }
    }

    protected void m3214a(Context context, String str, int i, DroiInappUpdateListener droiInappUpdateListener) {
        new Thread(new C1030a(context.getApplicationContext(), str, i, droiInappUpdateListener)).start();
    }

    protected void m3216a(DroiInappUpdateResponse droiInappUpdateResponse, String str, DroiInappDownloadListener droiInappDownloadListener) {
        if (droiInappUpdateResponse == null) {
            Log.i("DroiUpdateImpl", "response is null");
        } else if (str == null || str.isEmpty()) {
            Log.i("DroiUpdateImpl", "path is null or empty");
        } else if (droiInappUpdateResponse.m3182a() != 0) {
            Log.i("DroiUpdateImpl", "response is error");
        } else {
            try {
                C1049c.m3286a().m3290a(droiInappUpdateResponse.getFileUrl(), str, new C1038l(this, droiInappDownloadListener, droiInappUpdateResponse));
            } catch (Exception e) {
                DroiLog.m2869e("DroiUpdateImpl", e);
                DroiLog.m2871i("DroiUpdateImpl", "downloadInappUpdateFile:exception:DOWNLOAD_FAILED");
                droiInappDownloadListener.onFailed(2);
            }
        }
    }
}
