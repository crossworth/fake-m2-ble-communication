package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.protocol.GetCommonConfigReq;
import com.zhuoyi.system.network.protocol.GetCommonConfigResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.util.PromConstants;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.TimeFormater;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PromReqCommConfigService extends IZyService {
    public static final String TAG = "PromReqCommConfigService";

    class C18411 implements NetworkCallback {
        C18411() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            GetCommonConfigResp resp = null;
            Logger.debug(PromReqCommConfigService.TAG, "HTTPConnection response" + result);
            if (result.booleanValue() && respMessage.head.code == AttributeUitl.getMessageCode(GetCommonConfigResp.class)) {
                resp = respMessage.message;
            } else {
                Logger.error("GetCommonConfigreq on reponse error");
            }
            PromReqCommConfigService.this.onCommConfigResp(resp);
        }
    }

    public PromReqCommConfigService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
    }

    private void onCommConfigResp(GetCommonConfigResp resp) {
        long millis = 0;
        long time = System.currentTimeMillis();
        if (resp != null) {
            Logger.debug(TAG, "next time=" + TimeFormater.formatTime(resp.getNextReqTime() - time) + ";real=" + TimeFormater.formatTime(resp.getRelativeTime()));
            if (resp.getNextReqTime() - time > resp.getRelativeTime() || resp.getNextReqTime() - time <= 0) {
                millis = resp.getRelativeTime() + time;
            } else {
                millis = resp.getNextReqTime();
            }
            if (resp.getAdSwitch() == (byte) 1) {
                Logger.m3373e(TAG, "switch is open.");
                if (resp.getInstallLocalApkSwitch().equals("1")) {
                    PromUtils.getInstance(this.mContext).startSearchLocalApkTimer();
                } else {
                    PromUtils.getInstance(this.mContext).stopSearchLocalApkTimer();
                }
                String firstTime = PromDBUtils.getInstance(this.mContext).queryCfgValueByKey(PromDBUtils.PROM_KEY_FIRST_REQ_TIME);
                if (TextUtils.isEmpty(firstTime) && !TextUtils.isEmpty(resp.getCurTime())) {
                    PromDBUtils.getInstance(this.mContext).insertCfg(PromDBUtils.PROM_KEY_FIRST_REQ_TIME, resp.getCurTime());
                    firstTime = resp.getCurTime();
                }
                Logger.m3373e(TAG, "firstTime=" + firstTime + "; curTime=" + resp.getCurTime() + "; min=" + resp.getRelativeActiveTime());
                long j = 0;
                long j2 = 0;
                long relActiveTimeMills = 0;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                    j2 = sdf.parse(firstTime).getTime();
                    j = sdf.parse(resp.getCurTime()).getTime();
                    relActiveTimeMills = (long) ((Integer.parseInt(resp.getRelativeActiveTime()) * 60) * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Logger.debug(TAG, "curTimeMills=" + j + "; firstTimeMills=" + j2 + "; relActiveTimeMills=" + relActiveTimeMills);
                if (relActiveTimeMills == 0 || (j2 != 0 && j - j2 >= relActiveTimeMills)) {
                    Logger.debug(TAG, "start all!");
                    PromUtils.getInstance(this.mContext).startAllServices(resp.getReserved4());
                }
            } else {
                Logger.m3373e(TAG, "switch is closed.");
            }
        } else {
            Logger.m3373e(TAG, "GetCommonConfigResp is null and restart the service after " + PromConstants.PROM_REQ_FAILED_LOOP_REQ_INTERVAL + " min");
        }
        if (millis <= 0) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            c.add(12, PromConstants.PROM_REQ_FAILED_LOOP_REQ_INTERVAL);
            millis = c.getTimeInMillis();
        }
        Logger.m3373e(TAG, "next request time =" + TimeFormater.formatTime(millis));
        this.mTimerManager.startTimerByTime(millis, this.serviceId);
        stopSelf();
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.debug(TAG, "onStartCommand ");
        initService();
    }

    public void onServerAddressReponse(Session session) {
        GetCommonConfigReq req = new GetCommonConfigReq();
        req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        req.setPackageName(this.mContext.getPackageName());
        if (TextUtils.isEmpty(PromDBUtils.getInstance(this.mContext).queryCfgValueByKey(PromDBUtils.PROM_KEY_FIRST_REQ_TIME))) {
            req.setIsFirstReq("1");
        } else {
            req.setIsFirstReq("0");
        }
        HTTPConnection.getInstance().sendRequest(session.getPromNetworkAddr(), req, new C18411());
    }

    public void onServerAddressReponseError() {
        super.onServerAddressReponseError();
        onCommConfigResp(null);
    }
}
