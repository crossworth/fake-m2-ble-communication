package com.zhuoyi.system.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.zhuoyi.system.network.util.NetworkAddressUtil;
import com.zhuoyi.system.network.util.NetworkAddressUtil.InitServerAddrResponse;
import com.zhuoyi.system.network.util.NetworkConstants;
import com.zhuoyi.system.promotion.util.TimerManager;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.Session;

public abstract class IZyService {
    protected InitServerAddrResponse initServerAddrResponse = new C18481();
    protected Context mContext;
    protected Handler mHandler;
    protected TimerManager mTimerManager;
    protected int serviceId;

    class C18481 implements InitServerAddrResponse {
        C18481() {
        }

        public void onServerAddrResponseSuccess(Session session) {
            IZyService.this.onServerAddressReponse(session);
        }

        public void onServerAddrResponseError() {
            IZyService.this.onServerAddressReponseError();
        }
    }

    public abstract void onServerAddressReponse(Session session);

    public abstract void onStartCommand(Intent intent, int i, int i2);

    public IZyService(int serviceId, Context c, Handler handler) {
        this.mContext = c;
        this.serviceId = serviceId;
        this.mHandler = handler;
        this.mTimerManager = TimerManager.getInstance(this.mContext);
    }

    protected void initService() {
        NetworkAddressUtil.getInstance(this.mContext).initNetworkAddress(this.initServerAddrResponse);
    }

    public void stopSelf() {
        Message msg = new Message();
        msg.what = 3;
        msg.arg1 = this.serviceId;
        this.mHandler.dispatchMessage(msg);
    }

    protected void onServerAddressReponseError() {
        Logger.m3373e(NetworkConstants.TAG, "onServerAddressReponseError");
    }
}
