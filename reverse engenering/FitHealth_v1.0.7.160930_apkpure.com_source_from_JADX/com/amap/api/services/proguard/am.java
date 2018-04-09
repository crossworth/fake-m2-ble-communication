package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.interfaces.INearbySearch;
import com.amap.api.services.nearby.NearbySearch.NearbyListener;
import com.amap.api.services.nearby.NearbySearch.NearbyQuery;
import com.amap.api.services.nearby.NearbySearchResult;
import com.amap.api.services.nearby.UploadInfo;
import com.amap.api.services.nearby.UploadInfoCallback;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/* compiled from: NearbySearchCore */
public class am implements INearbySearch {
    private static long f4309e = 0;
    private List<NearbyListener> f4310a = new ArrayList();
    private String f4311b;
    private Context f4312c;
    private C0407q f4313d;
    private ExecutorService f4314f;
    private LatLonPoint f4315g = null;
    private String f4316h = null;
    private boolean f4317i = false;
    private Timer f4318j = new Timer();
    private UploadInfoCallback f4319k;
    private TimerTask f4320l;

    /* compiled from: NearbySearchCore */
    class C03451 extends Thread {
        final /* synthetic */ am f1287a;

        public void run() {
            /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.f1287a;
            r0 = r0.f4313d;
            r1 = r0.obtainMessage();
            r0 = 8;
            r1.arg1 = r0;
            r0 = r4.f1287a;
            r0 = r0.f4310a;
            r1.obj = r0;
            r0 = r4.f1287a;	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            r0.m4396a();	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            r1.what = r0;	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            r0 = r4.f1287a;
            r0 = r0.f4313d;
            if (r0 == 0) goto L_0x0030;
        L_0x0027:
            r0 = r4.f1287a;
            r0 = r0.f4313d;
            r0.sendMessage(r1);
        L_0x0030:
            return;
        L_0x0031:
            r0 = move-exception;
            r2 = r0.getErrorCode();	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            r1.what = r2;	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            r2 = "NearbySearch";	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            r3 = "clearUserInfoAsyn";	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            com.amap.api.services.proguard.C0390i.m1594a(r0, r2, r3);	 Catch:{ AMapException -> 0x0031, all -> 0x0051 }
            r0 = r4.f1287a;
            r0 = r0.f4313d;
            if (r0 == 0) goto L_0x0030;
        L_0x0047:
            r0 = r4.f1287a;
            r0 = r0.f4313d;
            r0.sendMessage(r1);
            goto L_0x0030;
        L_0x0051:
            r0 = move-exception;
            r2 = r4.f1287a;
            r2 = r2.f4313d;
            if (r2 == 0) goto L_0x0063;
        L_0x005a:
            r2 = r4.f1287a;
            r2 = r2.f4313d;
            r2.sendMessage(r1);
        L_0x0063:
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.api.services.proguard.am.1.run():void");
        }

        C03451(am amVar) {
            this.f1287a = amVar;
        }
    }

    /* compiled from: NearbySearchCore */
    private class C0348a extends TimerTask {
        final /* synthetic */ am f1292a;

        private C0348a(am amVar) {
            this.f1292a = amVar;
        }

        public void run() {
            try {
                if (this.f1292a.f4319k != null) {
                    int b = this.f1292a.m4401b(this.f1292a.f4319k.OnUploadInfoCallback());
                    Message obtainMessage = this.f1292a.f4313d.obtainMessage();
                    obtainMessage.arg1 = 10;
                    obtainMessage.obj = this.f1292a.f4310a;
                    obtainMessage.what = b;
                    this.f1292a.f4313d.sendMessage(obtainMessage);
                }
            } catch (Throwable th) {
                C0390i.m1594a(th, "NearbySearch", "UpdateDataTask");
            }
        }
    }

    public am(Context context) {
        this.f4312c = context.getApplicationContext();
        this.f4313d = C0407q.m1654a();
    }

    public synchronized void addNearbyListener(NearbyListener nearbyListener) {
        try {
            this.f4310a.add(nearbyListener);
        } catch (Throwable th) {
            C0390i.m1594a(th, "NearbySearch", "addNearbyListener");
        }
    }

    public synchronized void removeNearbyListener(NearbyListener nearbyListener) {
        if (nearbyListener != null) {
            try {
                this.f4310a.remove(nearbyListener);
            } catch (Throwable th) {
                C0390i.m1594a(th, "NearbySearch", "removeNearbyListener");
            }
        }
    }

    public void clearUserInfoAsyn() {
        new C03451(this).start();
    }

    private int m4396a() throws AMapException {
        AMapException e;
        try {
            if (this.f4317i) {
                throw new AMapException(AMapException.AMAP_CLIENT_UPLOADAUTO_STARTED_ERROR);
            } else if (m4400a(this.f4311b)) {
                C0394o.m1652a(this.f4312c);
                return ((Integer) new C2052r(this.f4312c, this.f4311b).m4358a()).intValue();
            } else {
                throw new AMapException(AMapException.AMAP_CLIENT_USERID_ILLEGAL);
            }
        } catch (AMapException e2) {
            throw e2;
        } catch (Throwable th) {
            e2 = new AMapException(AMapException.AMAP_CLIENT_UNKNOWN_ERROR);
        }
    }

    public void setUserID(String str) {
        this.f4311b = str;
    }

    public synchronized void startUploadNearbyInfoAuto(UploadInfoCallback uploadInfoCallback, int i) {
        if (i < 7000) {
            i = 7000;
        }
        try {
            this.f4319k = uploadInfoCallback;
            if (this.f4317i && this.f4320l != null) {
                this.f4320l.cancel();
            }
            this.f4317i = true;
            this.f4320l = new C0348a();
            this.f4318j.schedule(this.f4320l, 0, (long) i);
        } catch (Throwable th) {
            C0390i.m1594a(th, "NearbySearch", "startUploadNearbyInfoAuto");
        }
    }

    public synchronized void stopUploadNearbyInfoAuto() {
        try {
            if (this.f4320l != null) {
                this.f4320l.cancel();
            }
        } catch (Throwable th) {
            C0390i.m1594a(th, "NearbySearch", "stopUploadNearbyInfoAuto");
        }
        this.f4317i = false;
        this.f4320l = null;
    }

    private int m4397a(UploadInfo uploadInfo) {
        if (this.f4317i) {
            return AMapException.CODE_AMAP_CLIENT_UPLOADAUTO_STARTED_ERROR;
        }
        return m4401b(uploadInfo);
    }

    private boolean m4400a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile("^[a-z0-9A-Z_-]{1,32}$").matcher(str).find();
    }

    private int m4401b(UploadInfo uploadInfo) {
        try {
            C0394o.m1652a(this.f4312c);
            if (uploadInfo == null) {
                return AMapException.CODE_AMAP_CLIENT_NEARBY_NULL_RESULT;
            }
            long time = new Date().getTime();
            if (time - f4309e < 6500) {
                return AMapException.CODE_AMAP_CLIENT_UPLOAD_TOO_FREQUENT;
            }
            f4309e = time;
            String userID = uploadInfo.getUserID();
            if (!m4400a(userID)) {
                return AMapException.CODE_AMAP_CLIENT_USERID_ILLEGAL;
            }
            if (TextUtils.isEmpty(this.f4316h)) {
                this.f4316h = userID;
            }
            if (!userID.equals(this.f4316h)) {
                return AMapException.CODE_AMAP_CLIENT_USERID_ILLEGAL;
            }
            LatLonPoint point = uploadInfo.getPoint();
            if (point == null || point.equals(this.f4315g)) {
                return AMapException.CODE_AMAP_CLIENT_UPLOAD_LOCATION_ERROR;
            }
            Integer num = (Integer) new C2054t(this.f4312c, uploadInfo).m4358a();
            this.f4315g = point.copy();
            return 1000;
        } catch (AMapException e) {
            return e.getErrorCode();
        } catch (Throwable th) {
            return AMapException.CODE_AMAP_CLIENT_UNKNOWN_ERROR;
        }
    }

    public void uploadNearbyInfoAsyn(final UploadInfo uploadInfo) {
        if (this.f4314f == null) {
            this.f4314f = Executors.newSingleThreadExecutor();
        }
        this.f4314f.submit(new Runnable(this) {
            final /* synthetic */ am f1289b;

            public void run() {
                try {
                    Message obtainMessage = this.f1289b.f4313d.obtainMessage();
                    obtainMessage.arg1 = 10;
                    obtainMessage.obj = this.f1289b.f4310a;
                    obtainMessage.what = this.f1289b.m4397a(uploadInfo);
                    this.f1289b.f4313d.sendMessage(obtainMessage);
                } catch (Throwable th) {
                    C0390i.m1594a(th, "NearbySearch", "uploadNearbyInfoAsyn");
                }
            }
        });
    }

    public void searchNearbyInfoAsyn(final NearbyQuery nearbyQuery) {
        new Thread(this) {
            final /* synthetic */ am f1291b;

            public void run() {
                /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r4 = this;
                r0 = r4.f1291b;
                r0 = r0.f4313d;
                r1 = r0.obtainMessage();
                r0 = 9;
                r1.arg1 = r0;
                r0 = new com.amap.api.services.proguard.q$f;
                r0.<init>();
                r2 = r4.f1291b;
                r2 = r2.f4310a;
                r0.f1560a = r2;
                r1.obj = r0;
                r2 = r4.f1291b;	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r3 = r2;	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r2 = r2.searchNearbyInfo(r3);	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r0.f1561b = r2;	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r1.what = r0;	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r0 = r4.f1291b;
                r0 = r0.f4313d;
                if (r0 == 0) goto L_0x003c;
            L_0x0033:
                r0 = r4.f1291b;
                r0 = r0.f4313d;
                r0.sendMessage(r1);
            L_0x003c:
                return;
            L_0x003d:
                r0 = move-exception;
                r2 = r0.getErrorCode();	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r1.what = r2;	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r2 = "NearbySearch";	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r3 = "searchNearbyInfoAsyn";	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                com.amap.api.services.proguard.C0390i.m1594a(r0, r2, r3);	 Catch:{ AMapException -> 0x003d, all -> 0x005d }
                r0 = r4.f1291b;
                r0 = r0.f4313d;
                if (r0 == 0) goto L_0x003c;
            L_0x0053:
                r0 = r4.f1291b;
                r0 = r0.f4313d;
                r0.sendMessage(r1);
                goto L_0x003c;
            L_0x005d:
                r0 = move-exception;
                r2 = r4.f1291b;
                r2 = r2.f4313d;
                if (r2 == 0) goto L_0x006f;
            L_0x0066:
                r2 = r4.f1291b;
                r2 = r2.f4313d;
                r2.sendMessage(r1);
            L_0x006f:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.amap.api.services.proguard.am.3.run():void");
            }
        }.start();
    }

    public NearbySearchResult searchNearbyInfo(NearbyQuery nearbyQuery) throws AMapException {
        AMapException e;
        try {
            C0394o.m1652a(this.f4312c);
            return (NearbySearchResult) new C2053s(this.f4312c, nearbyQuery).m4358a();
        } catch (AMapException e2) {
            throw e2;
        } catch (Throwable th) {
            C0390i.m1594a(th, "NearbySearch", "searchNearbyInfo");
            e2 = new AMapException(AMapException.AMAP_CLIENT_UNKNOWN_ERROR);
        }
    }

    public synchronized void destroy() {
        try {
            this.f4318j.cancel();
        } catch (Throwable th) {
            C0390i.m1594a(th, "NearbySearch", "destryoy");
        }
    }
}
