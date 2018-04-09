package com.zhuoyi.system.promotion.util;

import com.zhuoyi.system.config.ZySDKConfig;
import com.zhuoyi.system.util.constant.FileConstants;

public class PromConstants {
    public static final String PROM_AD_IMAGES_PATH = ("/sdcard/" + FileConstants.FILE_ROOT + "/prom/ads");
    public static final int PROM_AD_INFO_ACTION_TYPE_APK = 1;
    public static final int PROM_AD_INFO_ACTION_TYPE_LIST = 3;
    public static final int PROM_AD_INFO_ACTION_TYPE_WAP = 2;
    public static final int PROM_AD_INFO_AD_TYPE_IMAGE = 1;
    public static final int PROM_AD_INFO_AD_TYPE_IMAGE_AND_TEXT = 3;
    public static final int PROM_AD_INFO_AD_TYPE_TEXT = 2;
    public static final int PROM_AD_INFO_STATUS_DISPLAY = 1;
    public static final int PROM_AD_INFO_STATUS_NOT_DISPLAY = 2;
    public static final int PROM_ALLOW_FAILED_TIME = 5;
    public static final String PROM_APP_ICONS_PATH = ("/sdcard/" + FileConstants.FILE_ROOT + "/prom/appIcons");
    public static final int PROM_BUNDLE_WAP_AD_FROM_DESKTOP = 2;
    public static final int PROM_BUNDLE_WAP_AD_FROM_NOTIFY = 1;
    public static final String PROM_CFG_REQ_FAILED_COUNT = "prom_cfg_req_failed_count";
    public static final int PROM_DESKTOP_AD_SHOW_TYPE_IMMEDIATELE = 1;
    public static final int PROM_DESKTOP_AD_SHOW_TYPE_TIME = 2;
    public static final int PROM_DESKTOP_AD_TYPE_HTML5 = 2;
    public static final int PROM_DESKTOP_AD_TYPE_IMAGE = 1;
    public static final String PROM_HTML5_INFO_ACTION_URL = "actionUrl";
    public static final String PROM_HTML5_INFO_AD_ID = "id";
    public static final String PROM_HTML5_INFO_APP_NAME = "appName";
    public static final String PROM_HTML5_INFO_ICON_ID = "iconId";
    public static final String PROM_HTML5_INFO_ICON_URL = "iconUrl";
    public static final String PROM_HTML5_INFO_MD5 = "md5";
    public static final String PROM_HTML5_INFO_PACKAGE_NAME = "packageName";
    public static final String PROM_HTML5_INFO_VERSION_CODE = "versionCode";
    public static final String PROM_HTML5_REPLACEMENT_STR = "com.zhuoyi.html5";
    public static final int PROM_NOTIFICATION_ID = 20001;
    public static final String PROM_RECEIVER_FILTER_PACKAGE_INSTALL = "prom_receiver_filter_package_install";
    public static final String PROM_RECEIVER_FILTER_PACKAGE_REMOVE = "prom_receiver_filter_package_remove";
    public static final int PROM_REQ_FAILED_LOOP_REQ_INTERVAL = getLoopTime();
    public static final int PROM_REQ_PROM_MSG_CODE = 20001;
    public static final int PROM_REQ_RICH_MEDIA_CODE = 20007;
    public static final int PROM_REQ_SHORTCUT_CODE = 20002;
    public static final int PROM_SEARCH_LOCAL_APP_NOTIFICATION_ID = 20002;
    public static final int PROM_SEND_BOOKMART_CODE = 20006;
    public static final int PROM_SEND_COMMON_REQ_CODE = 20006;
    public static final int PROM_SEND_PROM_DATA_CODE = 20003;
    public static final int PROM_SEND_SILENT_ACTION_CODE = 20004;
    public static final int PROM_SEND_SILENT_UPDATE_CODE = 20005;
    public static final long PROM_SHOW_NOTIFY_INTERVAL = 28800000;
    public static final int PROM_TAB_PAGE_INFO_TYPE_AD_LIST = 4;
    public static final int PROM_TAB_PAGE_INFO_TYPE_APP_LIST = 3;

    private static int getLoopTime() {
        if (ZySDKConfig.getInstance().isDebugMode()) {
            return 1;
        }
        return 70;
    }
}
