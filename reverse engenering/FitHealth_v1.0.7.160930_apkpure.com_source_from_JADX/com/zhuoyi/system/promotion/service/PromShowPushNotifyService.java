package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.zhuoyi.system.network.object.ApkInfoNew;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.network.util.NetworkUtils;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.Session;

public class PromShowPushNotifyService extends IZyService {
    public static final String TAG = "PromShowPNService";

    public PromShowPushNotifyService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
        Logger.debug(TAG, "PromShowPService created");
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.m3373e(TAG, "onStartCommand ");
        if (intent != null) {
            int notifyId = intent.getIntExtra(BundleConstants.BUNDLE_PUSH_NOTIFICATION_ID, -1);
            int fromType = intent.getIntExtra(BundleConstants.BUNDLE_NOTIFICATION_FROM, 3);
            Logger.m3372d(TAG, "notifyId=" + notifyId);
            if (notifyId != -1) {
                PromAppInfo adInfo;
                if (fromType == 2) {
                    adInfo = PromDBUtils.getInstance(this.mContext).queryPushNotifyById(notifyId);
                    if (adInfo != null) {
                        Logger.m3372d(TAG, "PromAppInfo_push: " + adInfo.toString());
                        if (NetworkUtils.isShowPush(this.mContext, adInfo.getReserved1())) {
                            StatsPromUtils.getInstance(this.mContext).addNotifyDisplayAction(adInfo);
                            adInfo.setPosition((short) 3);
                            PromUtils.getInstance(this.mContext).showPushNotify(adInfo);
                            PromDBUtils.getInstance(this.mContext).deletePushNofityById(notifyId);
                        } else {
                            adInfo.setReserved1("todyTime=" + NetworkUtils.getPushDisplayTime("00:01") + ";" + adInfo.getReserved1());
                            PromDBUtils.getInstance(this.mContext).updateAdInfo(adInfo);
                            Logger.m3372d(TAG, "Current net type is gprs or have no net, so Don't show push!");
                        }
                    } else {
                        Logger.m3372d(TAG, "PromAppInfo is null from DB");
                    }
                } else if (fromType == 1) {
                    ApkInfoNew info = (ApkInfoNew) intent.getExtras().getSerializable(BundleConstants.BUNDLE_APP_INFO);
                    if (info != null) {
                        Logger.m3372d(TAG, "PromAppInfo_silent: " + info.toString());
                        adInfo = info.switchToPromAppInfo(this.mContext);
                        if (NetworkUtils.isShowPush(this.mContext, adInfo.getReserved1())) {
                            StatsPromUtils.getInstance(this.mContext).addSilentNotifyAction(info);
                            adInfo.setPosition((short) 18);
                            PromUtils.getInstance(this.mContext).showPushNotify(adInfo);
                        } else {
                            if (PromDBUtils.getInstance(this.mContext).queryPushNotifyById(adInfo.getId()) == null) {
                                PromDBUtils.getInstance(this.mContext).insertPushNotify(adInfo);
                            } else {
                                PromDBUtils.getInstance(this.mContext).updateAdInfo(adInfo);
                            }
                            Logger.m3372d(TAG, "Current net type is gprs or have no net, so Don't show push and save adInfo!");
                        }
                    }
                } else if (fromType == 3) {
                    Logger.m3372d(TAG, "fromType_net: " + fromType);
                    for (PromAppInfo adInfo2 : PromDBUtils.getInstance(this.mContext).queryAllPushNotify()) {
                        if (adInfo2 != null) {
                            Logger.m3372d(TAG, "PromAppInfo_net: " + adInfo2.toString());
                            if (!NetworkUtils.isShowPush(this.mContext, adInfo2.getReserved1())) {
                                if (TextUtils.isEmpty(NetworkUtils.getDataFromPromAppInfoReserved1(adInfo2.getReserved1(), "todyTime"))) {
                                    adInfo2.setReserved1("todyTime=" + NetworkUtils.getPushDisplayTime("00:01") + ";" + adInfo2.getReserved1());
                                    PromDBUtils.getInstance(this.mContext).updateAdInfo(adInfo2);
                                }
                                Logger.m3372d(TAG, "Current net type is gprs or have no net, so Don't show push!");
                            } else if (NetworkUtils.isYesterday(adInfo2.getReserved1())) {
                                PromDBUtils.getInstance(this.mContext).deletePushNofityById(adInfo2.getId());
                            } else {
                                if (System.currentTimeMillis() > NetworkUtils.getPushDisplayTime(adInfo2.getDisplayTime())) {
                                    if ("silent".equals(NetworkUtils.getDataFromPromAppInfoReserved1(adInfo2.getReserved1(), "pushFrom"))) {
                                        StatsPromUtils.getInstance(this.mContext).addSilentNotifyAction(adInfo2);
                                        adInfo2.setPosition((short) 18);
                                    } else {
                                        StatsPromUtils.getInstance(this.mContext).addNotifyDisplayAction(adInfo2);
                                        adInfo2.setPosition((short) 3);
                                    }
                                    PromUtils.getInstance(this.mContext).showPushNotify(adInfo2);
                                    PromDBUtils.getInstance(this.mContext).deletePushNofityById(adInfo2.getId());
                                } else {
                                    Logger.m3372d(TAG, "The push's display time isn't comming so do nothing!");
                                }
                            }
                        } else {
                            Logger.m3372d(TAG, "Push info is null!");
                        }
                    }
                }
            }
            this.mTimerManager.clearPushNotifyTimer(notifyId);
        }
        stopSelf();
    }

    public void onServerAddressReponse(Session session) {
    }
}
