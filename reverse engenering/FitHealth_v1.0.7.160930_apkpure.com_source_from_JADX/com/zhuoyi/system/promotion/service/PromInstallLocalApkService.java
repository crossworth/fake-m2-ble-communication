package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.zhuoyi.system.promotion.util.constants.ExtraName;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.Session;
import com.zhuoyi.system.util.model.MyPackageInfo;

public class PromInstallLocalApkService extends IZyService {
    public PromInstallLocalApkService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.error("PromInstallLocalApkService onStartCommand");
        if (intent != null) {
            try {
                MyPackageInfo pInfo = (MyPackageInfo) intent.getSerializableExtra(ExtraName.MYPACKAGEINFO);
                AppInfoUtils.installApp(this.mContext, pInfo.getApkPath(), pInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stopSelf();
    }

    public void onServerAddressReponse(Session session) {
    }
}
