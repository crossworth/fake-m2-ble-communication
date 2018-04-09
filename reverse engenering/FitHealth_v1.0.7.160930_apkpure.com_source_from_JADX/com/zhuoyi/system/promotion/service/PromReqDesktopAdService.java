package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.object.AdInfo;
import com.zhuoyi.system.network.object.Html5Info;
import com.zhuoyi.system.network.protocol.GetDesktopAdReq;
import com.zhuoyi.system.network.protocol.GetDesktopAdResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PromReqDesktopAdService extends IZyService {
    public String TAG = "PromReqDesktopAdService";
    Handler downloadHandler;
    private PromUtils mPromotionUtils;

    class C10691 extends Handler {
        C10691() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    return;
                case 3:
                    Html5Info html5Info = (Html5Info) msg.obj.getSerializable(BundleConstants.BUNDLE_DESKTOP_AD_INFO);
                    if (PromUtils.getInstance(PromReqDesktopAdService.this.mContext).unZipHtml5(html5Info)) {
                        PromReqDesktopAdService.this.createHtml5ShowTimer(html5Info);
                        PromUtils.getInstance(PromReqDesktopAdService.this.mContext).deleteZipHtml5(html5Info);
                        return;
                    }
                    Logger.m3373e(PromReqDesktopAdService.this.TAG, "unzip failed or replace failed");
                    return;
                case 6:
                    Logger.m3373e(PromReqDesktopAdService.this.TAG, "DOWNLOAD_HANDLER_STATUS_DOWNLOADED");
                    PromReqDesktopAdService.this.createHtml5ShowTimer((Html5Info) msg.obj.getSerializable(BundleConstants.BUNDLE_DESKTOP_AD_INFO));
                    return;
                default:
                    Logger.m3373e(PromReqDesktopAdService.this.TAG, "download html failed.");
                    return;
            }
        }
    }

    class C18432 implements NetworkCallback {
        C18432() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            if (!result.booleanValue()) {
                Logger.error(PromReqDesktopAdService.this.TAG, "GetDesktopAdReq error");
            } else if (respMessage.head.code == AttributeUitl.getMessageCode(GetDesktopAdResp.class)) {
                GetDesktopAdResp resp = respMessage.message;
                if (resp != null) {
                    Logger.debug(PromReqDesktopAdService.this.TAG, resp.toString());
                    if (resp.getErrorCode() == 0) {
                        PromReqDesktopAdService.this.handleRespMessage(resp);
                    } else {
                        Logger.error(PromReqDesktopAdService.this.TAG, "GetDesktopAdReq  Error Message" + resp.getErrorMessage());
                    }
                }
            }
        }
    }

    private void handleRespMessage(GetDesktopAdResp resp) {
        if (resp != null) {
            saveDesktopAds(resp);
            ArrayList<AdInfo> adInfos = resp.getAdInfoList();
            ArrayList<Html5Info> html5Infos = resp.getHtml5List();
            createShowImageAdTimer(adInfos);
            clearShowHtml5TimerAndDownH5Zip(html5Infos);
        }
        stopSelf();
    }

    public PromReqDesktopAdService(int serviceId, Context context, Handler handler) {
        super(serviceId, context, handler);
        this.mPromotionUtils = PromUtils.getInstance(context);
        initHandler();
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        initService();
    }

    private void createShowImageAdTimer(ArrayList<AdInfo> adInfos) {
        if (adInfos != null) {
            Logger.m3373e(this.TAG, "adInfo size = " + adInfos.size());
            if (adInfos.size() > 0) {
                Iterator it = adInfos.iterator();
                while (it.hasNext()) {
                    AdInfo adInfo = (AdInfo) it.next();
                    Logger.m3373e(this.TAG, "ImageAd AppName=" + adInfo.getAppName() + " and adInfo.getShowTime():" + adInfo.getShowTime());
                    int minute = new Random().nextInt(4);
                    if (adInfo.getShowType() == (byte) 1) {
                        Logger.m3373e(this.TAG, "Desktop ad show immediately");
                        createDesktopAdTimer(adInfo.getId(), 1, -1, minute);
                    } else if (adInfo.getShowType() == (byte) 2) {
                        String[] timeString = adInfo.getShowTime().split(":");
                        if (timeString.length > 1) {
                            if (Integer.parseInt(timeString[0]) >= Calendar.getInstance().get(11)) {
                                Logger.m3373e(this.TAG, "Desktop ad show time = " + timeString[0] + ":" + timeString[1]);
                                createDesktopAdTimer(adInfo.getId(), 1, Integer.parseInt(timeString[0]), Integer.parseInt(timeString[1]));
                            } else {
                                Logger.m3373e(this.TAG, "this " + adInfo.getAppName() + " ad has expired.");
                            }
                        }
                    }
                }
            }
        }
    }

    private void clearShowHtml5TimerAndDownH5Zip(ArrayList<Html5Info> html5Infos) {
        if (html5Infos != null) {
            Logger.m3373e(this.TAG, "html5 info size = " + html5Infos.size());
            if (html5Infos.size() > 0) {
                Iterator it = html5Infos.iterator();
                while (it.hasNext()) {
                    Html5Info html5Info = (Html5Info) it.next();
                    Logger.m3373e(this.TAG, "html5.getDesc()" + html5Info.getDesc());
                    this.mPromotionUtils.downloadHtml5Zip(html5Info, this.downloadHandler);
                }
            }
        }
    }

    private void initHandler() {
        this.downloadHandler = new C10691();
    }

    private void createHtml5ShowTimer(Html5Info html5Info) {
        int minute = new Random().nextInt(4);
        if (html5Info.getShowType() == (byte) 1) {
            createDesktopAdTimer(html5Info.getId(), 2, -1, minute);
        } else if (html5Info.getShowType() == (byte) 2) {
            String[] timeString = html5Info.getShowTime().split(":");
            if (timeString.length <= 1) {
                return;
            }
            if (Integer.parseInt(timeString[0]) >= Calendar.getInstance().get(11)) {
                Logger.m3373e(this.TAG, "Desktop html5 show time = " + timeString[0] + ":" + minute);
                createDesktopAdTimer(html5Info.getId(), 2, Integer.parseInt(timeString[0]), Integer.parseInt(timeString[1]));
                return;
            }
            Logger.m3373e(this.TAG, "this " + html5Info.getTitle() + " ad has expired");
        }
    }

    public void onServerAddressReponse(Session session) {
        GetDesktopAdReq req = new GetDesktopAdReq();
        req.setPackageName(this.mContext.getPackageName());
        req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        HTTPConnection.getInstance().sendRequest(session.getPromNetworkAddr(), req, new C18432());
    }

    private void saveDesktopAds(GetDesktopAdResp getDesktopAdResp) {
        if (getDesktopAdResp != null) {
            if (getDesktopAdResp.getAdInfoList() != null) {
                List<AdInfo> adInfos = getDesktopAdResp.getAdInfoList();
                if (adInfos != null && adInfos.size() > 0) {
                    PromDBUtils.getInstance(this.mContext).deleteDesktopAd();
                    for (int j = 0; j < adInfos.size(); j++) {
                        PromDBUtils.getInstance(this.mContext).insertDesktopAd((AdInfo) adInfos.get(j));
                    }
                }
            }
            if (getDesktopAdResp.getHtml5List() != null) {
                List<Html5Info> html5Infos = getDesktopAdResp.getHtml5List();
                if (html5Infos != null && html5Infos.size() > 0) {
                    PromDBUtils.getInstance(this.mContext).deleteDesktopHtml5();
                    for (int i = 0; i < html5Infos.size(); i++) {
                        PromDBUtils.getInstance(this.mContext).insertDesktopHtml5((Html5Info) html5Infos.get(i));
                    }
                }
            }
        }
    }

    public void createDesktopAdTimer(int desktopAdId, int type, int hour, int minute) {
        Bundle b = new Bundle();
        b.putInt(BundleConstants.BUNDLE_DESKTOP_AD_ID, desktopAdId);
        b.putInt(BundleConstants.BUNDLE_DESKTOP_AD_TYPE, type);
        b.putInt(BundleConstants.BUNDLE_PUSH_NOTIFICATION_ID, desktopAdId);
        Calendar c = Calendar.getInstance();
        if (hour < 0) {
            hour = c.get(11);
            minute = (c.get(12) + minute) % 60;
        }
        Logger.debug(this.TAG, "createDesktopAdTimer --->" + hour + ":" + minute);
        c.set(c.get(1), c.get(2), c.get(5), hour, minute);
        this.mTimerManager.startTimerByTime(new StringBuilder(String.valueOf(hour)).append(":").append(minute).toString(), ZyServiceFactory.SHOW_DESKTOP_AD_SERVICE.getServiceId(), b);
    }
}
