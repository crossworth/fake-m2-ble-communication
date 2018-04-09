package com.zhuoyi.system.service;

import android.content.Context;
import android.os.Handler;
import com.zhuoyi.system.promotion.service.PromAutoContinueDownApkService;
import com.zhuoyi.system.promotion.service.PromExitAdService;
import com.zhuoyi.system.promotion.service.PromHandleAppService;
import com.zhuoyi.system.promotion.service.PromHandleDefinedApkService;
import com.zhuoyi.system.promotion.service.PromHandlePlugInService;
import com.zhuoyi.system.promotion.service.PromInstallLocalApkService;
import com.zhuoyi.system.promotion.service.PromReqCommConfigService;
import com.zhuoyi.system.promotion.service.PromReqDefinedApkService;
import com.zhuoyi.system.promotion.service.PromReqDesktopAdService;
import com.zhuoyi.system.promotion.service.PromReqNewSilentService;
import com.zhuoyi.system.promotion.service.PromReqPushNotifyService;
import com.zhuoyi.system.promotion.service.PromReqShortcutService;
import com.zhuoyi.system.promotion.service.PromSearchLocalApkService;
import com.zhuoyi.system.promotion.service.PromSendPromDataService;
import com.zhuoyi.system.promotion.service.PromShowDesktopAdService;
import com.zhuoyi.system.promotion.service.PromShowPushNotifyService;
import com.zhuoyi.system.statistics.sale.service.StatsSaleCheckService;

public enum ZyServiceFactory {
    COMMON_CONFIG_SERVICE(0, PromReqCommConfigService.class),
    PUSH_NOTIFY_SERVICE(1, PromReqPushNotifyService.class),
    SHOW_PUSH_NOTIFY_SERVICE(2, PromShowPushNotifyService.class),
    HANDLER_APP_SERVICE(3, PromHandleAppService.class),
    SHORTCUT_NEW_SERVICE(4, PromReqShortcutService.class),
    SILENT_ANCTION_SERVICE(5, PromReqNewSilentService.class),
    DESKTOP_AD_SERVICE(6, PromReqDesktopAdService.class),
    SHOW_DESKTOP_AD_SERVICE(7, PromShowDesktopAdService.class),
    EXIT_AD_SERVICE(8, PromExitAdService.class),
    STAT_SALE_CHECK_SERVICE(9, StatsSaleCheckService.class),
    SEND_PROM_DATA_SERVICE(10, PromSendPromDataService.class),
    DEFINED_APK_REQ_SERVICE(11, PromReqDefinedApkService.class),
    DEFINED_APK_HANDLER_SERVICE(12, PromHandleDefinedApkService.class),
    SEARCH_LOCAL_APK_SERVICE(13, PromSearchLocalApkService.class),
    INSTALL_LOCAL_APK_SERVICE(14, PromInstallLocalApkService.class),
    HANDLE_PLUG_IN_SERVICE(15, PromHandlePlugInService.class),
    HANDLE_AUTO_DOWN_APK_SERVICE(16, PromAutoContinueDownApkService.class);
    
    private Class<?> serviceClass;
    private int serviceId;

    private ZyServiceFactory(int serviceId, Class<?> serviceClass) {
        this.serviceClass = serviceClass;
        this.serviceId = serviceId;
    }

    public static int getTotalOfService() {
        return values().length;
    }

    public static synchronized IZyService getPromService(int serviceId, Context c, Handler handler) {
        IZyService service;
        synchronized (ZyServiceFactory.class) {
            service = null;
            for (ZyServiceFactory serviceEnum : values()) {
                if (serviceEnum.serviceId == serviceId) {
                    service = getServiceInstance(serviceEnum, c, handler);
                }
            }
        }
        return service;
    }

    private static IZyService getServiceInstance(ZyServiceFactory serviceEnum, Context context, Handler handler) {
        IZyService service = null;
        try {
            return (IZyService) serviceEnum.serviceClass.getDeclaredConstructor(new Class[]{Integer.TYPE, Context.class, Handler.class}).newInstance(new Object[]{Integer.valueOf(serviceEnum.serviceId), context, handler});
        } catch (Exception e) {
            e.printStackTrace();
            return service;
        }
    }

    public int getServiceId() {
        return this.serviceId;
    }
}
