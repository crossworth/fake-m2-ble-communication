package com.zhuoyi.system.statistics.sale.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.zhuoyi.system.config.ZySDKConfig;
import com.zhuoyi.system.promotion.util.TimerManager;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.statistics.sale.data.StatsSaleDBUtils;
import com.zhuoyi.system.statistics.sale.data.StatsSaleInfo;
import com.zhuoyi.system.statistics.sale.util.StatsSaleConst;
import com.zhuoyi.system.statistics.sale.util.StatsSaleUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatsSaleCheckService extends IZyService {
    public static final String TAG = "CheckSaleStatsService";
    private StatsSaleInfo mSaleStatsInfo = null;

    public StatsSaleCheckService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.m3374i(TAG, "onStartCommand");
        run();
    }

    public void sendData() {
        StatsSaleUtils.getInstance(this.mContext).sendSaleStatsReq(this.mSaleStatsInfo);
    }

    public void startNextService() {
        Logger.m3374i(TAG, "startNextService");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(12, 5);
        TimerManager.getInstance(this.mContext).startTimerByTime(c.getTimeInMillis(), ZyServiceFactory.STAT_SALE_CHECK_SERVICE.getServiceId());
    }

    public void run() {
        Logger.m3374i(TAG, "run");
        boolean isImeActive = true;
        for (String appid : ZySDKConfig.SaleDelayAppIds) {
            if (appid.equals(TerminalInfoUtil.getAppId(this.mContext))) {
                isImeActive = false;
                Logger.m3372d(TAG, "need imsi to active");
                break;
            }
        }
        String imsi = "";
        if (!isImeActive) {
            imsi = TerminalInfoUtil.getPhoneImsi(this.mContext);
            if (TextUtils.isEmpty(imsi)) {
                startNextService();
                stopSelf();
                return;
            }
            Logger.m3373e(TAG, "imsi=" + imsi);
        }
        this.mSaleStatsInfo = getSaleStatsFromDB();
        if (this.mSaleStatsInfo == null) {
            initSaleStatsInfo(imsi);
        }
        Logger.m3374i(TAG, "getActiveState()=" + this.mSaleStatsInfo.getActiveState());
        Logger.m3374i(TAG, "getStayTime()=" + this.mSaleStatsInfo.getStayTime());
        switch (this.mSaleStatsInfo.getActiveState()) {
            case 0:
                this.mSaleStatsInfo.setStayTime(this.mSaleStatsInfo.getStayTime() + 300000);
                long activeTime = 0;
                if (!isImeActive) {
                    activeTime = ZySDKConfig.getInstance().isDebugMode() ? 600000 : StatsSaleConst.CHECK_SALESTATS_ACTIVE_TIME;
                }
                if (this.mSaleStatsInfo.getStayTime() < activeTime) {
                    StatsSaleDBUtils.getInstance(this.mContext).saveSaleStatsToDB(this.mSaleStatsInfo);
                    startNextService();
                    break;
                }
                this.mSaleStatsInfo.setActiveState(1);
                this.mSaleStatsInfo.setActiveTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
                StatsSaleDBUtils.getInstance(this.mContext).saveSaleStatsToDB(this.mSaleStatsInfo);
                sendData();
                break;
            case 1:
                sendData();
                break;
            case 2:
                break;
            default:
                startNextService();
                break;
        }
        stopSelf();
    }

    private void initSaleStatsInfo(String imsi) {
        this.mSaleStatsInfo = new StatsSaleInfo();
        this.mSaleStatsInfo.setIMSI(imsi);
        this.mSaleStatsInfo.setActiveState(0);
        this.mSaleStatsInfo.setActiveTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        this.mSaleStatsInfo.setStayTime(TimeManager.UNIT_MINUTE);
        StatsSaleDBUtils.getInstance(this.mContext).saveSaleStatsToDB(this.mSaleStatsInfo);
    }

    public void onServerAddressReponse(Session session) {
    }

    public StatsSaleInfo getSaleStatsFromDB() {
        ArrayList<StatsSaleInfo> infoList = StatsSaleDBUtils.getInstance(this.mContext).querySaleStatsInfoList();
        if (infoList.size() == 0) {
            return null;
        }
        return (StatsSaleInfo) infoList.get(0);
    }
}
