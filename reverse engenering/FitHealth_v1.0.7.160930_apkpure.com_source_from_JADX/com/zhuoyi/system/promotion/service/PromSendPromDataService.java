package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.Session;

public class PromSendPromDataService extends IZyService {
    public PromSendPromDataService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.debug("PromSendPromDataService onStartCommand");
        StatsPromUtils.getInstance(this.mContext).sendAdLogInfoReq();
        StatsPromUtils.getInstance(this.mContext).sendDownloadResult();
        stopSelf();
    }

    public void onServerAddressReponse(Session session) {
    }
}
