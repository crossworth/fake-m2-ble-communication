package com.baidu.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.login.widget.ToolTipPopup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public final class LocationClient implements C1619j, an {
    public static String PREF_FILE_NAME = "pref_key";
    public static String PREF_KEY_NAME = "access_key";
    private static final int hA = 9;
    private static final int hC = 7;
    private static final int hI = 5;
    private static final int hJ = 12;
    private static final int hK = 6;
    private static final int hL = 2;
    private static final int hO = 6000;
    private static final int hV = 11;
    private static final int hY = 4;
    private static final int hg = 10;
    private static final String hi = "baidu_location_Client";
    private static final int hl = 1;
    private static final int hq = 1000;
    private static final int hu = 3;
    private static final String hv = "sign";
    private static final int hw = 8;
    private static final String hy = "key";
    private boolean hB = false;
    private final Messenger hD = new Messenger(this.hh);
    private Context hE = null;
    private Messenger hF = null;
    private long hG = 0;
    private LocationClientOption hH = new LocationClientOption();
    private boolean hM = false;
    private long hN = 0;
    private long hP = 0;
    private ServiceConnection hQ = new C04921(this);
    private String hR;
    private boolean hS = false;
    private boolean hT = false;
    private BDLocation hU = null;
    private String hW = null;
    private String hX = null;
    private ArrayList hZ = null;
    private boolean hf = false;
    private C0493a hh = new C0493a();
    private final Object hj = new Object();
    private BDErrorReport hk = null;
    private C0494b hm = null;
    private Boolean hn = Boolean.valueOf(false);
    private long ho = 0;
    private C1986u hp = null;
    private long hr = 0;
    private Boolean hs = Boolean.valueOf(false);
    private boolean ht = false;
    private BDLocationListener hx = null;
    private boolean hz = false;

    class C04921 implements ServiceConnection {
        final /* synthetic */ LocationClient f2102a;

        C04921(LocationClient locationClient) {
            this.f2102a = locationClient;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.f2102a.hF = new Messenger(iBinder);
            if (this.f2102a.hF != null) {
                this.f2102a.hM = true;
                Log.d("baidu_location_client", "baidu location connected ...");
                try {
                    Message obtain = Message.obtain(null, 11);
                    obtain.replyTo = this.f2102a.hD;
                    obtain.setData(this.f2102a.bG());
                    this.f2102a.hF.send(obtain);
                    this.f2102a.hM = true;
                    if (this.f2102a.hH != null) {
                        this.f2102a.hh.obtainMessage(4).sendToTarget();
                    }
                } catch (Exception e) {
                }
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.f2102a.hF = null;
            this.f2102a.hM = false;
        }
    }

    private class C0493a extends Handler {
        final /* synthetic */ LocationClient f2103a;

        private C0493a(LocationClient locationClient) {
            this.f2103a = locationClient;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.f2103a.bI();
                    return;
                case 2:
                    this.f2103a.bJ();
                    return;
                case 3:
                    this.f2103a.m5839k(message);
                    return;
                case 4:
                    this.f2103a.bM();
                    return;
                case 5:
                    this.f2103a.m5843n(message);
                    return;
                case 6:
                    this.f2103a.m5838j(message);
                    return;
                case 7:
                    this.f2103a.bH();
                    return;
                case 8:
                    this.f2103a.m5846o(message);
                    return;
                case 9:
                    this.f2103a.m5828i(message);
                    return;
                case 10:
                    this.f2103a.m5842m(message);
                    return;
                case 11:
                    this.f2103a.bK();
                    return;
                case 12:
                    this.f2103a.bL();
                    return;
                case 21:
                    this.f2103a.m5830if(message, 21);
                    return;
                case 26:
                    this.f2103a.m5830if(message, 26);
                    return;
                case 27:
                    this.f2103a.m5840l(message);
                    return;
                case 54:
                    if (this.f2103a.hH.f2118void) {
                        this.f2103a.hf = true;
                        return;
                    }
                    return;
                case 55:
                    if (this.f2103a.hH.f2118void) {
                        this.f2103a.hf = false;
                        return;
                    }
                    return;
                case 204:
                    this.f2103a.m5816byte(false);
                    return;
                case 205:
                    this.f2103a.m5816byte(true);
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    private class C0494b implements Runnable {
        final /* synthetic */ LocationClient f2104a;

        private C0494b(LocationClient locationClient) {
            this.f2104a = locationClient;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r3 = this;
            r0 = r3.f2104a;
            r1 = r0.hj;
            monitor-enter(r1);
            r0 = r3.f2104a;	 Catch:{ all -> 0x0036 }
            r2 = 0;
            r0.hB = r2;	 Catch:{ all -> 0x0036 }
            r0 = r3.f2104a;	 Catch:{ all -> 0x0036 }
            r0 = r0.hF;	 Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x001d;
        L_0x0015:
            r0 = r3.f2104a;	 Catch:{ all -> 0x0036 }
            r0 = r0.hD;	 Catch:{ all -> 0x0036 }
            if (r0 != 0) goto L_0x001f;
        L_0x001d:
            monitor-exit(r1);	 Catch:{ all -> 0x0036 }
        L_0x001e:
            return;
        L_0x001f:
            r0 = r3.f2104a;	 Catch:{ all -> 0x0036 }
            r0 = r0.hZ;	 Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x0034;
        L_0x0027:
            r0 = r3.f2104a;	 Catch:{ all -> 0x0036 }
            r0 = r0.hZ;	 Catch:{ all -> 0x0036 }
            r0 = r0.size();	 Catch:{ all -> 0x0036 }
            r2 = 1;
            if (r0 >= r2) goto L_0x0039;
        L_0x0034:
            monitor-exit(r1);	 Catch:{ all -> 0x0036 }
            goto L_0x001e;
        L_0x0036:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0036 }
            throw r0;
        L_0x0039:
            r0 = r3.f2104a;	 Catch:{ all -> 0x0036 }
            r0 = r0.hh;	 Catch:{ all -> 0x0036 }
            r2 = 4;
            r0 = r0.obtainMessage(r2);	 Catch:{ all -> 0x0036 }
            r0.sendToTarget();	 Catch:{ all -> 0x0036 }
            monitor-exit(r1);	 Catch:{ all -> 0x0036 }
            goto L_0x001e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.LocationClient.b.run():void");
        }
    }

    public LocationClient(Context context) {
        this.hE = context;
        this.hH = new LocationClientOption();
        this.hp = new C1986u(this.hE, this);
    }

    public LocationClient(Context context, LocationClientOption locationClientOption) {
        this.hE = context;
        this.hH = locationClientOption;
        this.hp = new C1986u(this.hE, this);
    }

    private Bundle bF() {
        if (this.hH == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("num", this.hH.f2105a);
        bundle.putFloat("distance", this.hH.f2109do);
        bundle.putBoolean("extraInfo", this.hH.f2113if);
        return bundle;
    }

    private Bundle bG() {
        if (this.hH == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("packName", this.hX);
        bundle.putString("prodName", this.hH.f2116new);
        bundle.putString("coorType", this.hH.f2117try);
        bundle.putString("addrType", this.hH.f2108char);
        bundle.putBoolean("openGPS", this.hH.f2107case);
        bundle.putBoolean("location_change_notify", this.hH.f2118void);
        bundle.putInt("scanSpan", this.hH.f2114int);
        bundle.putInt("timeOut", this.hH.f2115long);
        bundle.putInt("priority", this.hH.f2112goto);
        bundle.putBoolean("map", this.hs.booleanValue());
        bundle.putBoolean("import", this.hn.booleanValue());
        return bundle;
    }

    private void bH() {
        if (this.hF != null) {
            Message obtain = Message.obtain(null, 25);
            try {
                obtain.replyTo = this.hD;
                obtain.setData(bF());
                this.hF.send(obtain);
                this.ho = System.currentTimeMillis();
                this.hS = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void bI() {
        if (!this.hM) {
            C1974b.m5925new();
            this.hX = this.hE.getPackageName();
            this.hW = this.hX + "_bdls_v2.9";
            Intent intent = new Intent(this.hE, C1976f.class);
            intent.putExtra("key", this.hR);
            intent.putExtra(hv, C0533q.m2206a(this.hE));
            if (this.hH == null) {
                this.hH = new LocationClientOption();
            }
            try {
                this.hE.bindService(intent, this.hQ, 1);
            } catch (Exception e) {
                e.printStackTrace();
                this.hM = false;
            }
        }
    }

    private void bJ() {
        if (this.hM && this.hF != null) {
            Message obtain = Message.obtain(null, 12);
            obtain.replyTo = this.hD;
            try {
                this.hF.send(obtain);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.hE.unbindService(this.hQ);
            synchronized (this.hj) {
                try {
                    if (this.hB) {
                        this.hh.removeCallbacks(this.hm);
                        this.hB = false;
                    }
                } catch (Exception e2) {
                }
            }
            this.hp.aQ();
            this.hF = null;
            C1974b.m5926try();
            this.hM = false;
        }
    }

    private void bK() {
        if (this.hF != null) {
            Message obtain = Message.obtain(null, 22);
            try {
                obtain.replyTo = this.hD;
                this.hF.send(obtain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void bL() {
        Message obtain = Message.obtain(null, 28);
        try {
            obtain.replyTo = this.hD;
            this.hF.send(obtain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bM() {
        if (this.hF != null) {
            if ((System.currentTimeMillis() - this.hG > 3000 || !this.hH.f2118void) && (!this.ht || System.currentTimeMillis() - this.hP > 20000)) {
                Message obtain = Message.obtain(null, 22);
                try {
                    obtain.replyTo = this.hD;
                    this.hF.send(obtain);
                    this.hN = System.currentTimeMillis();
                    this.hz = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            synchronized (this.hj) {
                if (!(this.hH == null || this.hH.f2114int < 1000 || this.hB)) {
                    if (this.hm == null) {
                        this.hm = new C0494b();
                    }
                    this.hh.postDelayed(this.hm, (long) this.hH.f2114int);
                    this.hB = true;
                }
            }
        }
    }

    private void m5813byte(int i) {
        Iterator it;
        if (i == 26) {
            if (this.hS) {
                it = this.hZ.iterator();
                while (it.hasNext()) {
                    ((BDLocationListener) it.next()).onReceivePoi(this.hU);
                }
                this.hS = false;
            }
        } else if (this.hz || ((this.hH.f2118void && this.hU.getLocType() == 61) || this.hU.getLocType() == 66 || this.hU.getLocType() == 67 || this.ht)) {
            it = this.hZ.iterator();
            while (it.hasNext()) {
                ((BDLocationListener) it.next()).onReceiveLocation(this.hU);
            }
            if (this.hU.getLocType() != 66 && this.hU.getLocType() != 67) {
                this.hz = false;
                this.hP = System.currentTimeMillis();
            }
        }
    }

    private void m5816byte(boolean z) {
        if (this.hk != null) {
            this.hk.onReportResult(z);
        }
        this.hk = null;
        this.hr = 0;
    }

    private boolean m5818case(int i) {
        if (this.hF == null || !this.hM) {
            return false;
        }
        try {
            this.hF.send(Message.obtain(null, i));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void m5828i(Message message) {
        if (message != null && message.obj != null) {
            this.hp.m6080do((BDNotifyListener) message.obj);
        }
    }

    private void m5830if(Message message, int i) {
        Bundle data = message.getData();
        data.setClassLoader(BDLocation.class.getClassLoader());
        this.hU = (BDLocation) data.getParcelable("locStr");
        if (this.hU.getLocType() == 61) {
            this.hG = System.currentTimeMillis();
        }
        m5813byte(i);
    }

    private void m5838j(Message message) {
        if (message != null && message.obj != null) {
            BDLocationListener bDLocationListener = (BDLocationListener) message.obj;
            if (this.hZ != null && this.hZ.contains(bDLocationListener)) {
                this.hZ.remove(bDLocationListener);
            }
        }
    }

    private void m5839k(Message message) {
        if (message != null && message.obj != null) {
            LocationClientOption locationClientOption = (LocationClientOption) message.obj;
            if (!this.hH.equals(locationClientOption)) {
                if (this.hH.f2114int != locationClientOption.f2114int) {
                    try {
                        synchronized (this.hj) {
                            if (this.hB) {
                                this.hh.removeCallbacks(this.hm);
                                this.hB = false;
                            }
                            if (locationClientOption.f2114int >= 1000 && !this.hB) {
                                if (this.hm == null) {
                                    this.hm = new C0494b();
                                }
                                this.hh.postDelayed(this.hm, (long) locationClientOption.f2114int);
                                this.hB = true;
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                this.hH = new LocationClientOption(locationClientOption);
                if (this.hF != null) {
                    try {
                        Message obtain = Message.obtain(null, 15);
                        obtain.replyTo = this.hD;
                        obtain.setData(bG());
                        this.hF.send(obtain);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    private void m5840l(Message message) {
        Bundle data = message.getData();
        data.setClassLoader(BDLocation.class.getClassLoader());
        BDLocation bDLocation = (BDLocation) data.getParcelable("locStr");
        if (this.hx == null) {
            return;
        }
        if (this.hH == null || !this.hH.isDisableCache() || bDLocation.getLocType() != 65) {
            this.hx.onReceiveLocation(bDLocation);
        }
    }

    private void m5842m(Message message) {
        if (message != null && message.obj != null) {
            this.hp.m6081for((BDNotifyListener) message.obj);
        }
    }

    private void m5843n(Message message) {
        if (message != null && message.obj != null) {
            BDLocationListener bDLocationListener = (BDLocationListener) message.obj;
            if (this.hZ == null) {
                this.hZ = new ArrayList();
            }
            this.hZ.add(bDLocationListener);
        }
    }

    private void m5846o(Message message) {
        if (message != null && message.obj != null) {
            this.hx = (BDLocationListener) message.obj;
        }
    }

    public void cancleError() {
        m5818case(202);
    }

    public BDLocation getLastKnownLocation() {
        return this.hU;
    }

    public LocationClientOption getLocOption() {
        return this.hH;
    }

    public String getVersion() {
        return C1619j.f4487for;
    }

    public boolean isStarted() {
        return this.hM;
    }

    public boolean notifyError() {
        return m5818case(201);
    }

    public void registerLocationListener(BDLocationListener bDLocationListener) {
        Message obtainMessage = this.hh.obtainMessage(5);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public void registerNotify(BDNotifyListener bDNotifyListener) {
        Message obtainMessage = this.hh.obtainMessage(9);
        obtainMessage.obj = bDNotifyListener;
        obtainMessage.sendToTarget();
    }

    public void registerNotifyLocationListener(BDLocationListener bDLocationListener) {
        Message obtainMessage = this.hh.obtainMessage(8);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public void removeNotifyEvent(BDNotifyListener bDNotifyListener) {
        Message obtainMessage = this.hh.obtainMessage(10);
        obtainMessage.obj = bDNotifyListener;
        obtainMessage.sendToTarget();
    }

    public int reportErrorWithInfo(BDErrorReport bDErrorReport) {
        if (this.hF == null || !this.hM) {
            return 1;
        }
        if (bDErrorReport == null) {
            return 2;
        }
        if (System.currentTimeMillis() - this.hr < 50000 && this.hk != null) {
            return 4;
        }
        Bundle errorInfo = bDErrorReport.getErrorInfo();
        if (errorInfo == null) {
            return 3;
        }
        try {
            Message obtain = Message.obtain(null, 203);
            obtain.replyTo = this.hD;
            obtain.setData(errorInfo);
            this.hF.send(obtain);
            this.hk = bDErrorReport;
            this.hr = System.currentTimeMillis();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public int requestLocation() {
        if (this.hF == null || this.hD == null) {
            return 1;
        }
        if (this.hZ == null || this.hZ.size() < 1) {
            return 2;
        }
        if (System.currentTimeMillis() - this.hN < 1000) {
            return 6;
        }
        this.hh.obtainMessage(4).sendToTarget();
        return 0;
    }

    public void requestNotifyLocation() {
        this.hh.obtainMessage(11).sendToTarget();
    }

    public int requestOfflineLocation() {
        if (this.hF == null || this.hD == null) {
            return 1;
        }
        if (this.hZ == null || this.hZ.size() < 1) {
            return 2;
        }
        this.hh.obtainMessage(12).sendToTarget();
        return 0;
    }

    public int requestPoi() {
        if (this.hF == null || this.hD == null) {
            return 1;
        }
        if (this.hZ == null || this.hZ.size() < 1) {
            return 2;
        }
        if (System.currentTimeMillis() - this.ho < ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME) {
            return 6;
        }
        if (this.hH.f2105a < 1) {
            return 7;
        }
        this.hh.obtainMessage(7).sendToTarget();
        return 0;
    }

    public void setAK(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = Pattern.compile("[&=]").matcher(str).replaceAll("");
        }
        this.hR = str;
        Context context = this.hE;
        String str2 = PREF_FILE_NAME;
        Context context2 = this.hE;
        context.getSharedPreferences(str2, 0).edit().putString(PREF_KEY_NAME, this.hR).commit();
    }

    public void setForBaiduMap(boolean z) {
        this.hs = Boolean.valueOf(z);
    }

    public void setLocOption(LocationClientOption locationClientOption) {
        Object locationClientOption2;
        if (locationClientOption == null) {
            locationClientOption2 = new LocationClientOption();
        }
        Message obtainMessage = this.hh.obtainMessage(3);
        obtainMessage.obj = locationClientOption2;
        obtainMessage.sendToTarget();
    }

    public void start() {
        this.hh.obtainMessage(1).sendToTarget();
    }

    public void stop() {
        bJ();
    }

    public void unRegisterLocationListener(BDLocationListener bDLocationListener) {
        Message obtainMessage = this.hh.obtainMessage(6);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public boolean updateLocation(Location location) {
        if (this.hF == null || this.hD == null || location == null) {
            return false;
        }
        try {
            Message obtain = Message.obtain(null, 57);
            obtain.obj = location;
            this.hF.send(obtain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
