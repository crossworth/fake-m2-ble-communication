package com.umeng.socialize;

import android.app.Dialog;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.umeng.facebook.internal.AnalyticsEvents;

public class Config {
    public static String Descriptor = "com.umeng.share";
    public static String EntityKey = null;
    public static String EntityName = "share";
    public static boolean IsToastTip = true;
    public static int KaKaoLoginType = 0;
    public static int LinkedInProfileScope = 0;
    public static int LinkedInShareCode = 0;
    public static String MORE_TITLE = "分享";
    public static boolean OpenEditor = true;
    public static String QQAPPNAME = "";
    public static int QQWITHQZONE = 2;
    public static String REDIRECT_URL = "http://sns.whalecloud.com";
    public static String SessionId = null;
    public static String UID = null;
    public static int UseCocos = 0;
    private static String f4882a = "";
    public static String appName = null;
    private static String f4883b = "";
    public static int connectionTimeOut = m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    public static Dialog dialog = null;
    public static boolean dialogSwitch = true;
    public static float imageSize = 3072.0f;
    public static boolean isIntentShareFB = false;
    public static boolean isLoadImgByCompress = true;
    public static Boolean isUmengQQ = Boolean.valueOf(true);
    public static Boolean isUmengSina = Boolean.valueOf(true);
    public static Boolean isUmengWx = Boolean.valueOf(true);
    public static boolean isloadUrl = false;
    public static final boolean mEncrypt = true;
    public static int readSocketTimeOut = m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    public static String shareType = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE;
    public static boolean showShareBoardOnTop = false;
    public static Dialog wxdialog = null;

    public static String getAdapterSDKVersion() {
        return f4883b;
    }

    public static String getAdapterSDK() {
        return f4882a;
    }
}
