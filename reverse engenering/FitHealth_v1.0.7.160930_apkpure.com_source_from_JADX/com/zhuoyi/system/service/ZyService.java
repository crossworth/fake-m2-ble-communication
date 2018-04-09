package com.zhuoyi.system.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.util.Logger;

public class ZyService extends Service {
    public static final String TAG = "ZyService";
    private Handler mHandler;
    private IZyService[] zyServices = new IZyService[ZyServiceFactory.getTotalOfService()];

    class C10791 extends Handler {
        C10791() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    Logger.debug(ZyService.TAG, "stop service and service id = " + msg.arg1);
                    ZyService.this.zyServices[msg.arg1] = null;
                    if (ZyService.this.promServiceIsNull()) {
                        ZyService.this.stopSelf();
                        Logger.debug(ZyService.TAG, "stop all service ");
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void initHandler() {
        this.mHandler = new C10791();
    }

    private boolean promServiceIsNull() {
        for (IZyService mIZyService : this.zyServices) {
            if (mIZyService != null) {
                return false;
            }
        }
        return true;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        initHandler();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || intent.getExtras() == null) {
            Logger.m3373e(TAG, "intent is null");
            if (promServiceIsNull()) {
                stopSelf();
                Logger.debug(TAG, "stop all service ");
            }
        } else {
            int serviceId = intent.getExtras().getInt(BundleConstants.BUNDLE_KEY_SERVICE_ID, 0);
            Logger.m3373e(TAG, "start serviceId = " + serviceId);
            if (this.zyServices[serviceId] == null) {
                this.zyServices[serviceId] = ZyServiceFactory.getPromService(serviceId, getApplicationContext(), this.mHandler);
            }
            if (serviceId >= 0 && serviceId < this.zyServices.length && this.zyServices[serviceId] != null) {
                this.zyServices[serviceId].onStartCommand(intent, flags, startId);
            }
        }
        return 2;
    }
}
