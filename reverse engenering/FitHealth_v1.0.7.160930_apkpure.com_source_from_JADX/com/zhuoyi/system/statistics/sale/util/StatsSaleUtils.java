package com.zhuoyi.system.statistics.sale.util;

import android.content.Context;
import android.text.TextUtils;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.protocol.GetSaleStatisticsReq;
import com.zhuoyi.system.network.protocol.GetSaleStatisticsResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.promotion.util.TimerManager;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.statistics.sale.data.StatsSaleDBUtils;
import com.zhuoyi.system.statistics.sale.data.StatsSaleInfo;
import com.zhuoyi.system.statistics.util.StatsUtils;
import com.zhuoyi.system.statistics.util.StatsUtils.TokenCallBack;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import java.util.ArrayList;

public class StatsSaleUtils {
    public static final String TAG = "SaleStatsUtils";
    private static StatsSaleUtils mInstance = null;
    private Context mContext = null;

    private StatsSaleUtils(Context context) {
        this.mContext = context;
    }

    public static StatsSaleUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new StatsSaleUtils(context);
        }
        return mInstance;
    }

    private void sendSaleStatsReqEx(final StatsSaleInfo srcInfo, String token) {
        Logger.debug("sendSaleStatsReqEx");
        GetSaleStatisticsReq req = new GetSaleStatisticsReq();
        req.setTermInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        req.setMac(TerminalInfoUtil.getLocalMacAddress(this.mContext));
        req.setActiveTime(srcInfo.getActiveTime());
        req.setToken(token);
        HTTPConnection.getInstance().sendRequest(Session.getInstance().getStatisNetworkAddr(), req, new NetworkCallback() {
            public void onResponse(Boolean result, ZyCom_Message respMessage) {
                if (!result.booleanValue() || respMessage.head.code != AttributeUitl.getMessageCode(GetSaleStatisticsResp.class)) {
                    Logger.error("GetSaleStatisticsResp error");
                } else if (respMessage.message.getErrorCode() == 0) {
                    srcInfo.setActiveState(2);
                    StatsSaleDBUtils.getInstance(StatsSaleUtils.this.mContext).saveSaleStatsToDB(srcInfo);
                    Logger.debug("GetSaleStatisticsResp success");
                } else {
                    Logger.error("GetSaleStatisticsResp error");
                }
            }
        });
    }

    public void sendSaleStatsReq(final StatsSaleInfo srcInfo) {
        StatsUtils.getInstance(this.mContext).getStatsToken(new TokenCallBack() {
            public void onTokenResponse(String token) {
                if (TextUtils.isEmpty(token)) {
                    Logger.error("sendSaleStatsReq error");
                } else {
                    StatsSaleUtils.this.sendSaleStatsReqEx(srcInfo, token);
                }
            }
        });
    }

    public StatsSaleInfo getSaleStatsFromDB() {
        ArrayList<StatsSaleInfo> infoList = StatsSaleDBUtils.getInstance(this.mContext).querySaleStatsInfoList();
        if (infoList.size() == 0) {
            return null;
        }
        return (StatsSaleInfo) infoList.get(0);
    }

    public void saveSaleStatsToDB(StatsSaleInfo info) {
        ArrayList<StatsSaleInfo> infoList = StatsSaleDBUtils.getInstance(this.mContext).querySaleStatsInfoList();
        if (infoList.size() == 0) {
            StatsSaleDBUtils.getInstance(this.mContext).insertSaleStatsInfo(info);
            return;
        }
        info.setIMSI(((StatsSaleInfo) infoList.get(0)).getIMSI());
        StatsSaleDBUtils.getInstance(this.mContext).updateSaleStatsInfo(info);
    }

    public void startCheckSaleStatsService(long millis) {
        if (millis > 0) {
            TimerManager.getInstance(this.mContext).startTimerByTime(millis, ZyServiceFactory.STAT_SALE_CHECK_SERVICE.getServiceId());
        } else {
            TimerManager.getInstance(this.mContext).startAlermByServiceId(ZyServiceFactory.STAT_SALE_CHECK_SERVICE.getServiceId());
        }
    }
}
