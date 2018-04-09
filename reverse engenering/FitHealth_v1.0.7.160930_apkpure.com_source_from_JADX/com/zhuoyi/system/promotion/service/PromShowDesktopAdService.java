package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.zhuoyi.system.network.object.AdInfo;
import com.zhuoyi.system.network.object.Html5Info;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.constant.Session;

public class PromShowDesktopAdService extends IZyService {
    public static final String TAG = "PromShowDskTAdService";

    public PromShowDesktopAdService(int serviceId, Context context, Handler handler) {
        super(serviceId, context, handler);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.m3373e(TAG, "onStartCommand");
        if (intent != null) {
            int adType = intent.getIntExtra(BundleConstants.BUNDLE_DESKTOP_AD_TYPE, 0);
            int desktopAdId = intent.getIntExtra(BundleConstants.BUNDLE_DESKTOP_AD_ID, -1);
            if (adType == 1) {
                Logger.m3373e(TAG, "type = PROM_DESKTOP_AD_TYPE_IMAGE");
                if (desktopAdId > 0) {
                    AdInfo adInfo = PromDBUtils.getInstance(this.mContext).queryDesktopImageAdById(desktopAdId);
                    if (adInfo != null) {
                        Logger.m3373e(TAG, "Prom Desktop Ad id = " + desktopAdId);
                        PromUtils.getInstance(this.mContext).showDesktopAdImage(adInfo);
                        PromDBUtils.getInstance(this.mContext).deleteDesktopAdImageById(desktopAdId);
                    }
                }
            } else if (adType == 2) {
                Logger.m3373e(TAG, "type = PROM_DESKTOP_AD_TYPE_HTML5");
                if (desktopAdId > 0) {
                    Html5Info html5Info = PromDBUtils.getInstance(this.mContext).queryDesktopHtmlAdById(desktopAdId);
                    if (html5Info != null) {
                        Logger.m3373e(TAG, "Prom desktop html ad id : " + desktopAdId);
                        PromUtils.getInstance(this.mContext).showDesktopAdHtml5(html5Info);
                        PromDBUtils.getInstance(this.mContext).deleteDesktopHtml5ById(desktopAdId);
                    }
                }
            }
        }
        stopSelf();
    }

    public void onServerAddressReponse(Session session) {
    }
}
