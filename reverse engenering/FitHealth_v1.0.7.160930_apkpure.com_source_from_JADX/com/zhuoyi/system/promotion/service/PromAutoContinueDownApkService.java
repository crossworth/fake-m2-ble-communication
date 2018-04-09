package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.zhuoyi.system.download.util.DownloadUtils;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.util.constant.Session;

public class PromAutoContinueDownApkService extends IZyService {
    public PromAutoContinueDownApkService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        DownloadUtils.getInstance(this.mContext).autoContinueDownload();
        stopSelf();
    }

    public void onServerAddressReponse(Session session) {
    }
}
