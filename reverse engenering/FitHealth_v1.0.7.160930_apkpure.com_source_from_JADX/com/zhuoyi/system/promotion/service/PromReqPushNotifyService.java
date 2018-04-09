package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.network.protocol.GetPushReq;
import com.zhuoyi.system.network.protocol.GetPushResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import java.util.ArrayList;
import java.util.Iterator;

public class PromReqPushNotifyService extends IZyService {
    public String TAG = "PromReqPNService";

    class C18451 implements NetworkCallback {
        C18451() {
        }

        public void onResponse(Boolean result, ZyCom_Message obj) {
            if (result.booleanValue()) {
                try {
                    if (obj.head.code == AttributeUitl.getMessageCode(GetPushResp.class)) {
                        GetPushResp resp = obj.message;
                        if (resp != null) {
                            Logger.debug(PromReqPushNotifyService.this.TAG, resp.toString());
                        }
                        if (resp.getErrorCode() == 0) {
                            PromReqPushNotifyService.this.savePushNotifys(resp);
                        } else {
                            Logger.error(PromReqPushNotifyService.this.TAG, "GetPushResp  Error Message" + resp.getErrorMessage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PromReqPushNotifyService.this.stopSelf();
        }
    }

    public PromReqPushNotifyService(int serviceId, Context context, Handler handler) {
        super(serviceId, context, handler);
    }

    public void onServerAddressReponse(Session session) {
        GetPushReq req = new GetPushReq();
        req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        req.setPackageName(this.mContext.getPackageName());
        HTTPConnection.getInstance().sendRequest(session.getPromNetworkAddr(), req, new C18451());
    }

    private void savePushNotifys(GetPushResp resp) {
        if (resp != null && resp.getPromAppInfos() != null) {
            ArrayList<PromAppInfo> infos = resp.getPromAppInfos();
            Logger.debug(this.TAG, "Ad infos.size()=" + infos.size());
            logPromAppInfo(infos);
            if (resp.getAdSwitch() == (byte) 0) {
                Logger.debug(this.TAG, "push switch is closed.");
                this.mTimerManager.clearPushNotifyTimer(new int[0]);
            } else if (infos.size() > 0) {
                PromDBUtils.getInstance(this.mContext).deletePushNofity();
                this.mTimerManager.clearPushNotifyTimer(new int[0]);
                for (int j = 0; j < infos.size(); j++) {
                    PromDBUtils.getInstance(this.mContext).insertPushNotify((PromAppInfo) infos.get(j));
                }
                setShowPushNotifyTimer(infos);
            }
        }
    }

    private void setShowPushNotifyTimer(ArrayList<PromAppInfo> infos) {
        Iterator it = infos.iterator();
        while (it.hasNext()) {
            PromAppInfo info = (PromAppInfo) it.next();
            Logger.debug("DisplayTime = " + info.getDisplayTime());
            String timeString = info.getDisplayTime();
            if (!TextUtils.isEmpty(timeString)) {
                Bundle b = new Bundle();
                b.putInt(BundleConstants.BUNDLE_PUSH_NOTIFICATION_ID, info.getId());
                b.putInt(BundleConstants.BUNDLE_NOTIFICATION_FROM, 2);
                this.mTimerManager.startTimerByTime(timeString, ZyServiceFactory.SHOW_PUSH_NOTIFY_SERVICE.getServiceId(), b);
            }
        }
    }

    private void logPromAppInfo(ArrayList<PromAppInfo> infos) {
        if (infos.size() > 0) {
            Iterator it = infos.iterator();
            while (it.hasNext()) {
                Logger.debug(this.TAG, "PromAppInfo" + ((PromAppInfo) it.next()).toString());
            }
        }
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.debug(this.TAG, "onstart!!");
        initService();
    }
}
