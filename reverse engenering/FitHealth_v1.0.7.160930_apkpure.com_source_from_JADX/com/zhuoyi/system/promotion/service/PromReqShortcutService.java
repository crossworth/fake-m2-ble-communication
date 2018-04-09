package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.DisplayMetrics;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.object.SerApkInfo;
import com.zhuoyi.system.network.protocol.GetShortcutNewReq;
import com.zhuoyi.system.network.protocol.GetShortcutNewResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.promotion.activity.PromCommonShortcutActivity;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.BitmapUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.ResourceIdUtils;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import java.util.Iterator;

public class PromReqShortcutService extends IZyService {
    public static final String TAG = "PromReqShortcutService";
    private float density;
    private StatsPromUtils mStatsPromUtils;
    private DisplayMetrics metrics;

    class C18461 implements NetworkCallback {
        C18461() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            if (result.booleanValue()) {
                try {
                    if (respMessage.head.code == AttributeUitl.getMessageCode(GetShortcutNewResp.class)) {
                        GetShortcutNewResp resp = respMessage.message;
                        if (resp != null) {
                            Logger.debug(resp.toString());
                        }
                        if (resp.getErrorCode() == 0) {
                            PromReqShortcutService.this.handleRespMessage(resp);
                            return;
                        } else {
                            Logger.error("GetPushResp  Error Message" + resp.getErrorMessage());
                            return;
                        }
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            Logger.error("Get shortcut request error;");
        }
    }

    public PromReqShortcutService(int serviceId, Context context, Handler handler) {
        super(serviceId, context, handler);
        this.metrics = new DisplayMetrics();
        this.mStatsPromUtils = StatsPromUtils.getInstance(this.mContext);
        this.metrics = this.mContext.getResources().getDisplayMetrics();
        this.density = this.metrics.density;
        this.mStatsPromUtils = StatsPromUtils.getInstance(this.mContext);
    }

    private void handleRespMessage(GetShortcutNewResp resp) {
        if (resp != null) {
            Logger.debug(TAG, "size()=" + resp.getApkInfoList().size());
            Iterator it = resp.getApkInfoList().iterator();
            while (it.hasNext()) {
                SerApkInfo info = (SerApkInfo) it.next();
                Logger.m3373e(TAG, info.getAppName() + "--" + info.getPackageName());
                if (!PromUtils.getInstance(this.mContext).isShortcutExists(info)) {
                    showShortcut(info);
                    PromUtils.getInstance(this.mContext).saveShortcutInfo(info);
                    this.mStatsPromUtils.addAdDisplayAction(info.getPackageName(), info.getIconId(), 9, 3);
                }
            }
        }
        stopSelf();
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.m3373e(TAG, "onstart");
        initService();
    }

    private Bitmap getShortcut(SerApkInfo serApkInfo) {
        Bitmap bitmap = BitmapUtils.getBitmapByUrl(serApkInfo.getIconUrl());
        if (this.density == 1.0f) {
            bitmap = BitmapUtils.zoomBitmap(bitmap, 48, 48);
        } else if (this.density > 1.0f) {
            bitmap = BitmapUtils.zoomBitmap(bitmap, (int) (this.density * 48.0f), (int) (this.density * 48.0f));
        } else {
            bitmap = BitmapUtils.zoomBitmap(bitmap, 32, 32);
        }
        Logger.m3372d(TAG, "bitmap is " + (bitmap == null ? "null" : "not null"));
        return bitmap;
    }

    private void showShortcut(SerApkInfo serApkInfo) {
        Logger.debug("showShortcut");
        AppInfoUtils.createShortcut(this.mContext, serApkInfo, PromCommonShortcutActivity.class.getCanonicalName(), serApkInfo.getAppName(), getShortcut(serApkInfo), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_default_app_icon"), false, 9);
    }

    public void onServerAddressReponse(Session session) {
        sendShortcutReq(session);
    }

    private void sendShortcutReq(Session session) {
        GetShortcutNewReq req = new GetShortcutNewReq();
        req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        req.setPackageName(this.mContext.getPackageName());
        req.setVersion(AppInfoUtils.getPackageVersionCode(this.mContext));
        req.setSource(1);
        HTTPConnection.getInstance().sendRequest(session.getPromNetworkAddr(), req, new C18461());
    }
}
