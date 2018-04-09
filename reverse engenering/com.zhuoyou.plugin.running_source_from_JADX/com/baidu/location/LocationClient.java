package com.baidu.location;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import com.baidu.location.p005a.C0338b;
import com.baidu.location.p005a.C0338b.C0329b;
import com.baidu.location.p005a.C0347h;
import com.baidu.location.p010d.C0407a;
import java.util.ArrayList;
import java.util.Iterator;

public final class LocationClient implements C0329b {
    private static final int MIN_REQUEST_SPAN = 1000;
    private static final int MSG_REG_LISTENER = 5;
    private static final int MSG_REG_NOTIFY_LISTENER = 8;
    private static final int MSG_REMOVE_NOTIFY = 10;
    private static final int MSG_REQ_LOC = 4;
    private static final int MSG_REQ_NOTIFY_LOC = 11;
    private static final int MSG_REQ_OFFLINE_LOC = 12;
    private static final int MSG_REQ_POI = 7;
    private static final int MSG_RIGSTER_NOTIFY = 9;
    private static final int MSG_SET_OPT = 3;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final int MSG_UNREG_LISTENER = 6;
    private static final String mTAG = "baidu_location_Client";
    private BDLocationListener NotifyLocationListenner = null;
    private boolean clientFirst = false;
    private Boolean firstConnected = Boolean.valueOf(true);
    private boolean inDoorState = false;
    private boolean isScheduled = false;
    private boolean isStop = true;
    private boolean isWaitingForLocation = false;
    private boolean isWaitingLocTag = false;
    private long lastReceiveGpsTime = 0;
    private long lastReceiveLocationTime = 0;
    private Boolean mConfig_map = Boolean.valueOf(false);
    private Boolean mConfig_preimport = Boolean.valueOf(false);
    private ServiceConnection mConnection = new C0378b(this);
    private Context mContext = null;
    private boolean mDebugByDev;
    private boolean mGpsStatus = false;
    private C0327a mHandler = new C0327a();
    private boolean mIsStarted = false;
    private String mKey;
    private BDLocation mLastLocation = null;
    private long mLastRequestTime = 0;
    private ArrayList<BDLocationListener> mLocationListeners = null;
    private final Object mLock = new Object();
    private final Messenger mMessenger = new Messenger(this.mHandler);
    private C0407a mNotifyCache = null;
    private LocationClientOption mOption = new LocationClientOption();
    private String mPackName = null;
    private C0328b mScheduledRequest = null;
    private Messenger mServer = null;
    private C0338b mloc = null;
    private boolean serverFirst = false;
    private String serviceName = null;

    private class C0327a extends Handler {
        final /* synthetic */ LocationClient f108a;

        private C0327a(LocationClient locationClient) {
            this.f108a = locationClient;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.f108a.onStart();
                    return;
                case 2:
                    this.f108a.onStop();
                    return;
                case 3:
                    this.f108a.onSetOption(message);
                    return;
                case 4:
                    this.f108a.onRequestLocation();
                    return;
                case 5:
                    this.f108a.onRegisterListener(message);
                    return;
                case 6:
                    this.f108a.onUnRegisterListener(message);
                    return;
                case 7:
                    return;
                case 8:
                    this.f108a.onRegisterNotifyLocationListener(message);
                    return;
                case 9:
                    this.f108a.onRegisterNotify(message);
                    return;
                case 10:
                    this.f108a.onRemoveNotifyEvent(message);
                    return;
                case 11:
                    this.f108a.onRequestNotifyLocation();
                    return;
                case 12:
                    this.f108a.onRequestOffLineLocation();
                    return;
                case 21:
                    Bundle data = message.getData();
                    data.setClassLoader(BDLocation.class.getClassLoader());
                    BDLocation bDLocation = (BDLocation) data.getParcelable("locStr");
                    if (this.f108a.serverFirst || !this.f108a.clientFirst || bDLocation.getLocType() != 66) {
                        if (this.f108a.serverFirst || !this.f108a.clientFirst) {
                            if (!this.f108a.serverFirst) {
                                this.f108a.serverFirst = true;
                            }
                            this.f108a.onNewLocation(message, 21);
                            return;
                        }
                        this.f108a.serverFirst = true;
                        return;
                    }
                    return;
                case 26:
                    this.f108a.onNewLocation(message, 26);
                    return;
                case 27:
                    this.f108a.onNewNotifyLocation(message);
                    return;
                case 54:
                    if (this.f108a.mOption.location_change_notify) {
                        this.f108a.mGpsStatus = true;
                        return;
                    }
                    return;
                case 55:
                    if (this.f108a.mOption.location_change_notify) {
                        this.f108a.mGpsStatus = false;
                        return;
                    }
                    return;
                case 701:
                    this.f108a.sendFirstLoc((BDLocation) message.obj);
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    private class C0328b implements Runnable {
        final /* synthetic */ LocationClient f109a;

        private C0328b(LocationClient locationClient) {
            this.f109a = locationClient;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r6 = this;
            r0 = r6.f109a;
            r1 = r0.mLock;
            monitor-enter(r1);
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r2 = 0;
            r0.isScheduled = r2;	 Catch:{ all -> 0x0036 }
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r0 = r0.mServer;	 Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x001d;
        L_0x0015:
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r0 = r0.mMessenger;	 Catch:{ all -> 0x0036 }
            if (r0 != 0) goto L_0x001f;
        L_0x001d:
            monitor-exit(r1);	 Catch:{ all -> 0x0036 }
        L_0x001e:
            return;
        L_0x001f:
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r0 = r0.mLocationListeners;	 Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x0034;
        L_0x0027:
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r0 = r0.mLocationListeners;	 Catch:{ all -> 0x0036 }
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
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r0 = r0.isWaitingLocTag;	 Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x006f;
        L_0x0041:
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r0 = r0.mScheduledRequest;	 Catch:{ all -> 0x0036 }
            if (r0 != 0) goto L_0x0055;
        L_0x0049:
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r2 = new com.baidu.location.LocationClient$b;	 Catch:{ all -> 0x0036 }
            r3 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r2.<init>(r3);	 Catch:{ all -> 0x0036 }
            r0.mScheduledRequest = r2;	 Catch:{ all -> 0x0036 }
        L_0x0055:
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r0 = r0.mHandler;	 Catch:{ all -> 0x0036 }
            r2 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r2 = r2.mScheduledRequest;	 Catch:{ all -> 0x0036 }
            r3 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r3 = r3.mOption;	 Catch:{ all -> 0x0036 }
            r3 = r3.scanSpan;	 Catch:{ all -> 0x0036 }
            r4 = (long) r3;	 Catch:{ all -> 0x0036 }
            r0.postDelayed(r2, r4);	 Catch:{ all -> 0x0036 }
            monitor-exit(r1);	 Catch:{ all -> 0x0036 }
            goto L_0x001e;
        L_0x006f:
            r0 = r6.f109a;	 Catch:{ all -> 0x0036 }
            r0 = r0.mHandler;	 Catch:{ all -> 0x0036 }
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
        this.mContext = context;
        this.mOption = new LocationClientOption();
    }

    public LocationClient(Context context, LocationClientOption locationClientOption) {
        this.mContext = context;
        this.mOption = locationClientOption;
    }

    private void callListeners(int i) {
        if (this.mLastLocation.getCoorType() == null) {
            this.mLastLocation.setCoorType(this.mOption.coorType);
        }
        if (this.isWaitingForLocation || ((this.mOption.location_change_notify && this.mLastLocation.getLocType() == 61) || this.mLastLocation.getLocType() == 66 || this.mLastLocation.getLocType() == 67 || this.inDoorState || this.mLastLocation.getLocType() == 161)) {
            if (this.mLocationListeners != null) {
                Iterator it = this.mLocationListeners.iterator();
                while (it.hasNext()) {
                    ((BDLocationListener) it.next()).onReceiveLocation(this.mLastLocation);
                }
            }
            if (this.mLastLocation.getLocType() != 66 && this.mLastLocation.getLocType() != 67) {
                this.isWaitingForLocation = false;
                this.lastReceiveLocationTime = System.currentTimeMillis();
            }
        }
    }

    public static BDLocation getBDLocationInCoorType(BDLocation bDLocation, String str) {
        BDLocation bDLocation2 = new BDLocation(bDLocation);
        double[] coorEncrypt = Jni.coorEncrypt(bDLocation.getLongitude(), bDLocation.getLatitude(), str);
        bDLocation2.setLatitude(coorEncrypt[1]);
        bDLocation2.setLongitude(coorEncrypt[0]);
        return bDLocation2;
    }

    private Bundle getOptionBundle() {
        if (this.mOption == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("packName", this.mPackName);
        bundle.putString("prodName", this.mOption.prodName);
        bundle.putString("coorType", this.mOption.coorType);
        bundle.putString("addrType", this.mOption.addrType);
        bundle.putBoolean("openGPS", this.mOption.openGps);
        bundle.putBoolean("location_change_notify", this.mOption.location_change_notify);
        bundle.putInt("scanSpan", this.mOption.scanSpan);
        bundle.putBoolean("enableSimulateGps", this.mOption.enableSimulateGps);
        bundle.putInt("timeOut", this.mOption.timeOut);
        bundle.putInt("priority", this.mOption.priority);
        bundle.putBoolean("map", this.mConfig_map.booleanValue());
        bundle.putBoolean("import", this.mConfig_preimport.booleanValue());
        bundle.putBoolean("needDirect", this.mOption.mIsNeedDeviceDirect);
        bundle.putBoolean("isneedaptag", this.mOption.isNeedAptag);
        bundle.putBoolean("isneedpoiregion", this.mOption.isNeedPoiRegion);
        bundle.putBoolean("isneedregular", this.mOption.isNeedRegular);
        bundle.putBoolean("isneedaptagd", this.mOption.isNeedAptagd);
        bundle.putBoolean("isneedaltitude", this.mOption.isNeedAltitude);
        bundle.putInt("autoNotifyMaxInterval", this.mOption.getAutoNotifyMaxInterval());
        bundle.putInt("autoNotifyMinTimeInterval", this.mOption.getAutoNotifyMinTimeInterval());
        bundle.putInt("autoNotifyMinDistance", this.mOption.getAutoNotifyMinDistance());
        bundle.putFloat("autoNotifyLocSensitivity", this.mOption.getAutoNotifyLocSensitivity());
        return bundle;
    }

    private void onNewLocation(Message message, int i) {
        if (this.mIsStarted) {
            try {
                Bundle data = message.getData();
                data.setClassLoader(BDLocation.class.getClassLoader());
                this.mLastLocation = (BDLocation) data.getParcelable("locStr");
                if (this.mLastLocation.getLocType() == 61) {
                    this.lastReceiveGpsTime = System.currentTimeMillis();
                }
                callListeners(i);
            } catch (Exception e) {
            }
        }
    }

    private void onNewNotifyLocation(Message message) {
        try {
            Bundle data = message.getData();
            data.setClassLoader(BDLocation.class.getClassLoader());
            BDLocation bDLocation = (BDLocation) data.getParcelable("locStr");
            if (this.NotifyLocationListenner == null) {
                return;
            }
            if (this.mOption == null || !this.mOption.isDisableCache() || bDLocation.getLocType() != 65) {
                this.NotifyLocationListenner.onReceiveLocation(bDLocation);
            }
        } catch (Exception e) {
        }
    }

    private void onRegisterListener(Message message) {
        if (message != null && message.obj != null) {
            BDLocationListener bDLocationListener = (BDLocationListener) message.obj;
            if (this.mLocationListeners == null) {
                this.mLocationListeners = new ArrayList();
            }
            if (!this.mLocationListeners.contains(bDLocationListener)) {
                this.mLocationListeners.add(bDLocationListener);
            }
        }
    }

    private void onRegisterNotify(Message message) {
        if (message != null && message.obj != null) {
            BDNotifyListener bDNotifyListener = (BDNotifyListener) message.obj;
            if (this.mNotifyCache == null) {
                this.mNotifyCache = new C0407a(this.mContext, this);
            }
            this.mNotifyCache.m631a(bDNotifyListener);
        }
    }

    private void onRegisterNotifyLocationListener(Message message) {
        if (message != null && message.obj != null) {
            this.NotifyLocationListenner = (BDLocationListener) message.obj;
        }
    }

    private void onRemoveNotifyEvent(Message message) {
        if (message != null && message.obj != null) {
            BDNotifyListener bDNotifyListener = (BDNotifyListener) message.obj;
            if (this.mNotifyCache != null) {
                this.mNotifyCache.m634c(bDNotifyListener);
            }
        }
    }

    private void onRequestLocation() {
        if (this.mServer != null) {
            if ((System.currentTimeMillis() - this.lastReceiveGpsTime > 3000 || !this.mOption.location_change_notify || this.isWaitingLocTag) && (!this.inDoorState || System.currentTimeMillis() - this.lastReceiveLocationTime > 20000 || this.isWaitingLocTag)) {
                Message obtain = Message.obtain(null, 22);
                if (this.isWaitingLocTag) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isWaitingLocTag", this.isWaitingLocTag);
                    this.isWaitingLocTag = false;
                    obtain.setData(bundle);
                }
                try {
                    obtain.replyTo = this.mMessenger;
                    this.mServer.send(obtain);
                    this.mLastRequestTime = System.currentTimeMillis();
                    this.isWaitingForLocation = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            synchronized (this.mLock) {
                if (!(this.mOption == null || this.mOption.scanSpan < 1000 || this.isScheduled)) {
                    if (this.mScheduledRequest == null) {
                        this.mScheduledRequest = new C0328b();
                    }
                    this.mHandler.postDelayed(this.mScheduledRequest, (long) this.mOption.scanSpan);
                    this.isScheduled = true;
                }
            }
        }
    }

    private void onRequestNotifyLocation() {
        if (this.mServer != null) {
            Message obtain = Message.obtain(null, 22);
            try {
                obtain.replyTo = this.mMessenger;
                this.mServer.send(obtain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onRequestOffLineLocation() {
        Message obtain = Message.obtain(null, 28);
        try {
            obtain.replyTo = this.mMessenger;
            this.mServer.send(obtain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSetOption(Message message) {
        this.isWaitingLocTag = false;
        if (message != null && message.obj != null) {
            LocationClientOption locationClientOption = (LocationClientOption) message.obj;
            if (!this.mOption.optionEquals(locationClientOption)) {
                if (this.mOption.scanSpan != locationClientOption.scanSpan) {
                    try {
                        synchronized (this.mLock) {
                            if (this.isScheduled) {
                                this.mHandler.removeCallbacks(this.mScheduledRequest);
                                this.isScheduled = false;
                            }
                            if (locationClientOption.scanSpan >= 1000 && !this.isScheduled) {
                                if (this.mScheduledRequest == null) {
                                    this.mScheduledRequest = new C0328b();
                                }
                                this.mHandler.postDelayed(this.mScheduledRequest, (long) locationClientOption.scanSpan);
                                this.isScheduled = true;
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                this.mOption = new LocationClientOption(locationClientOption);
                if (this.mServer != null) {
                    try {
                        Message obtain = Message.obtain(null, 15);
                        obtain.replyTo = this.mMessenger;
                        obtain.setData(getOptionBundle());
                        this.mServer.send(obtain);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    private void onStart() {
        if (!this.mIsStarted) {
            if (this.firstConnected.booleanValue()) {
                new C0404c(this).start();
                this.firstConnected = Boolean.valueOf(false);
            }
            this.mPackName = this.mContext.getPackageName();
            this.serviceName = this.mPackName + "_bdls_v2.9";
            Intent intent = new Intent(this.mContext, C0455f.class);
            try {
                intent.putExtra("debug_dev", this.mDebugByDev);
            } catch (Exception e) {
            }
            if (this.mOption == null) {
                this.mOption = new LocationClientOption();
            }
            intent.putExtra("cache_exception", this.mOption.isIgnoreCacheException);
            intent.putExtra("kill_process", this.mOption.isIgnoreKillProcess);
            try {
                this.mContext.bindService(intent, this.mConnection, 1);
            } catch (Exception e2) {
                e2.printStackTrace();
                this.mIsStarted = false;
            }
        }
    }

    private void onStop() {
        if (this.mIsStarted && this.mServer != null) {
            Message obtain = Message.obtain(null, 12);
            obtain.replyTo = this.mMessenger;
            try {
                this.mServer.send(obtain);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.mContext.unbindService(this.mConnection);
            } catch (Exception e2) {
            }
            synchronized (this.mLock) {
                try {
                    if (this.isScheduled) {
                        this.mHandler.removeCallbacks(this.mScheduledRequest);
                        this.isScheduled = false;
                    }
                } catch (Exception e3) {
                }
            }
            if (this.mNotifyCache != null) {
                this.mNotifyCache.m632a();
            }
            this.mServer = null;
            this.isWaitingLocTag = false;
            this.inDoorState = false;
            this.mIsStarted = false;
            this.clientFirst = false;
            this.serverFirst = false;
        }
    }

    private void onUnRegisterListener(Message message) {
        if (message != null && message.obj != null) {
            BDLocationListener bDLocationListener = (BDLocationListener) message.obj;
            if (this.mLocationListeners != null && this.mLocationListeners.contains(bDLocationListener)) {
                this.mLocationListeners.remove(bDLocationListener);
            }
        }
    }

    private void sendFirstLoc(BDLocation bDLocation) {
        if (!this.isStop) {
            this.mLastLocation = bDLocation;
            if (!this.serverFirst && bDLocation.getLocType() == 161) {
                this.clientFirst = true;
            }
            if (this.mLocationListeners != null) {
                Iterator it = this.mLocationListeners.iterator();
                while (it.hasNext()) {
                    ((BDLocationListener) it.next()).onReceiveLocation(bDLocation);
                }
            }
        }
    }

    private boolean sendServerMessage(int i) {
        if (this.mServer == null || !this.mIsStarted) {
            return false;
        }
        try {
            this.mServer.send(Message.obtain(null, i));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getAccessKey() {
        try {
            this.mKey = C0347h.m269b(this.mContext);
            if (TextUtils.isEmpty(this.mKey)) {
                throw new IllegalStateException("please setting key from Manifest.xml");
            }
            return String.format("KEY=%s", new Object[]{this.mKey});
        } catch (Exception e) {
            return null;
        }
    }

    public BDLocation getLastKnownLocation() {
        return this.mLastLocation;
    }

    public LocationClientOption getLocOption() {
        return this.mOption;
    }

    public String getVersion() {
        return "7.0.1";
    }

    public boolean isStarted() {
        return this.mIsStarted;
    }

    public void onReceiveLocation(BDLocation bDLocation) {
        if ((!this.serverFirst || this.clientFirst) && bDLocation != null) {
            Message obtainMessage = this.mHandler.obtainMessage(701);
            obtainMessage.obj = bDLocation;
            obtainMessage.sendToTarget();
        }
    }

    public void registerLocationListener(BDLocationListener bDLocationListener) {
        if (bDLocationListener == null) {
            throw new IllegalStateException("please set a non-null listener");
        }
        Message obtainMessage = this.mHandler.obtainMessage(5);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public void registerNotify(BDNotifyListener bDNotifyListener) {
        Message obtainMessage = this.mHandler.obtainMessage(9);
        obtainMessage.obj = bDNotifyListener;
        obtainMessage.sendToTarget();
    }

    public void registerNotifyLocationListener(BDLocationListener bDLocationListener) {
        Message obtainMessage = this.mHandler.obtainMessage(8);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public void removeNotifyEvent(BDNotifyListener bDNotifyListener) {
        Message obtainMessage = this.mHandler.obtainMessage(10);
        obtainMessage.obj = bDNotifyListener;
        obtainMessage.sendToTarget();
    }

    public int requestLocation() {
        if (this.mServer == null || this.mMessenger == null) {
            return 1;
        }
        if (this.mLocationListeners == null || this.mLocationListeners.size() < 1) {
            return 2;
        }
        if (System.currentTimeMillis() - this.mLastRequestTime < 1000) {
            return 6;
        }
        this.isWaitingLocTag = true;
        Message obtainMessage = this.mHandler.obtainMessage(4);
        obtainMessage.arg1 = 0;
        obtainMessage.sendToTarget();
        return 0;
    }

    public void requestNotifyLocation() {
        this.mHandler.obtainMessage(11).sendToTarget();
    }

    public int requestOfflineLocation() {
        if (this.mServer == null || this.mMessenger == null) {
            return 1;
        }
        if (this.mLocationListeners == null || this.mLocationListeners.size() < 1) {
            return 2;
        }
        this.mHandler.obtainMessage(12).sendToTarget();
        return 0;
    }

    public void setLocOption(LocationClientOption locationClientOption) {
        if (locationClientOption == null) {
            locationClientOption = new LocationClientOption();
        }
        if (locationClientOption.getAutoNotifyMaxInterval() > 0) {
            locationClientOption.setScanSpan(0);
            locationClientOption.setLocationNotify(true);
        }
        Message obtainMessage = this.mHandler.obtainMessage(3);
        obtainMessage.obj = locationClientOption;
        obtainMessage.sendToTarget();
    }

    public void start() {
        this.isStop = false;
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    public boolean startIndoorMode() {
        boolean sendServerMessage = sendServerMessage(110);
        if (sendServerMessage) {
            this.inDoorState = true;
        }
        return sendServerMessage;
    }

    public void stop() {
        this.isStop = true;
        this.mHandler.obtainMessage(2).sendToTarget();
        this.mloc = null;
    }

    public boolean stopIndoorMode() {
        boolean sendServerMessage = sendServerMessage(111);
        if (sendServerMessage) {
            this.inDoorState = false;
        }
        return sendServerMessage;
    }

    public void unRegisterLocationListener(BDLocationListener bDLocationListener) {
        if (bDLocationListener == null) {
            throw new IllegalStateException("please set a non-null listener");
        }
        Message obtainMessage = this.mHandler.obtainMessage(6);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public boolean updateLocation(Location location) {
        if (this.mServer == null || this.mMessenger == null || location == null) {
            return false;
        }
        try {
            Message obtain = Message.obtain(null, 57);
            obtain.obj = location;
            this.mServer.send(obtain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
