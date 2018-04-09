package com.umeng.socialize;

import android.app.Dialog;

public class Config {
    public static String Descriptor = "com.umeng.share";
    public static String EntityKey = null;
    public static String EntityName = null;
    public static boolean IsToastTip = true;
    public static int LinkedInProfileScope = 0;
    public static int LinkedInShareCode = 0;
    public static boolean OpenEditor = true;
    public static String QQAPPNAME = "";
    public static int QQWITHQZONE = 0;
    public static String REDIRECT_URL = "http://sns.whalecloud.com";
    public static String SessionId = null;
    public static boolean ShareLocation = true;
    public static String UID = null;
    public static int UseCocos = 0;
    public static boolean WBBYQQ = true;
    private static String f3213a = "";
    public static String appName = null;
    private static String f3214b = "";
    public static int connectionTimeOut = 30000;
    public static Dialog dialog = null;
    public static boolean dialogSwitch = true;
    public static float imageSize = 3072.0f;
    public static boolean isIntentShareFB = false;
    public static boolean isloadUrl = false;
    public static final boolean mEncrypt = true;
    public static int readSocketTimeOut = 30000;

    public static String getAdapterSDKVersion() {
        return f3214b;
    }

    public static String getAdapterSDK() {
        return f3213a;
    }

    public static void setAdapterSDKInfo(String str, String str2) {
        f3213a = str;
        f3214b = str2;
    }
}
