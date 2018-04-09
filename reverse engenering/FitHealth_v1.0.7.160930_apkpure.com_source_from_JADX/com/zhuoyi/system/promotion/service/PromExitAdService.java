package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.protocol.GetPushInAppReq;
import com.zhuoyi.system.network.protocol.GetPushInAppResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;

public class PromExitAdService extends IZyService {

    class C18391 implements NetworkCallback {
        C18391() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            if (result.booleanValue() && respMessage.head.code == AttributeUitl.getMessageCode(GetPushInAppResp.class)) {
                PromExitAdService.this.handlerResponse(respMessage.message);
                return;
            }
            PromExitAdService.this.stopSelf();
            Logger.error("GetPushInAppResp error");
        }
    }

    public PromExitAdService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        initService();
    }

    public void onServerAddressReponse(Session session) {
        GetPushInAppReq req = new GetPushInAppReq();
        req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        req.setPackageName(this.mContext.getPackageName());
        HTTPConnection.getInstance().sendRequest(session.getPromNetworkAddr(), req, new C18391());
    }

    private void handlerResponse(GetPushInAppResp resp) {
        if (resp.getErrorCode() == 0) {
            Logger.debug("sendExitAdReq request SUCCESS and exit ad size =" + resp.getPushInAppInfoList().size());
            if (resp.getPushInAppInfoList().size() > 0) {
                PromDBUtils.getInstance(this.mContext).deleteAllExitAdLogInfo();
                PromDBUtils.getInstance(this.mContext).addExitAdInfo(resp.getPushInAppInfoList());
            }
        } else {
            Logger.error("GetPushInAppResp error");
        }
        stopSelf();
    }
}
