package com.umeng.socialize.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.facebook.internal.AnalyticsEvents;
import com.umeng.facebook.internal.ServerProtocol;
import com.umeng.socialize.Config;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.net.stats.AuthStatsRequest;
import com.umeng.socialize.net.stats.ShareStatsRequest;
import com.umeng.socialize.net.stats.StatsAPIs;
import com.umeng.socialize.net.stats.UserInfoStatsRequest;
import com.umeng.socialize.utils.Log;

public class SocialAnalytics {
    private static SocializeClient f4922a = new SocializeClient();

    public static void log(final Context context, final String str, final String str2, final UMediaObject uMediaObject) {
        new Thread(new Runnable() {
            public void run() {
                SocializeRequest c1582a = new C1582a(context, str, str2);
                c1582a.m4493a(uMediaObject);
                C1583b c1583b = (C1583b) SocialAnalytics.f4922a.execute(c1582a);
                if (c1583b == null || !c1583b.isOk()) {
                    Log.m4545d(" fail to send log");
                } else {
                    Log.m4545d(" send log succeed");
                }
            }
        }).start();
    }

    public static void authstart(Context context, SHARE_MEDIA share_media, String str, boolean z, String str2) {
        final Context context2 = context;
        final SHARE_MEDIA share_media2 = share_media;
        final boolean z2 = z;
        final String str3 = str;
        final String str4 = str2;
        new Thread(new Runnable() {
            public void run() {
                AuthStatsRequest authStatsRequest = new AuthStatsRequest(context2, SocializeReseponse.class);
                authStatsRequest.addStringParams(AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, share_media2.getauthstyle(z2));
                authStatsRequest.addStringParams("platform", share_media2.toString().toLowerCase());
                authStatsRequest.addStringParams("version", str3);
                authStatsRequest.addStringParams("tag", str4);
                StatsAPIs.authStatsStart(authStatsRequest);
            }
        }).start();
    }

    public static void authendt(Context context, String str, String str2, String str3, String str4) {
        final Context context2 = context;
        final String str5 = str2;
        final String str6 = str3;
        final String str7 = str;
        final String str8 = str4;
        new Thread(new Runnable() {
            public void run() {
                AuthStatsRequest authStatsRequest = new AuthStatsRequest(context2, SocializeReseponse.class);
                authStatsRequest.addStringParams("result", str5);
                if (!TextUtils.isEmpty(str6)) {
                    authStatsRequest.addStringParams("errormsg", str6);
                }
                authStatsRequest.addStringParams("platform", str7);
                authStatsRequest.addStringParams("tag", str8);
                StatsAPIs.authStatsEnd(authStatsRequest);
            }
        }).start();
    }

    public static void sharestart(Context context, SHARE_MEDIA share_media, String str, boolean z, int i, String str2) {
        final Context context2 = context;
        final SHARE_MEDIA share_media2 = share_media;
        final boolean z2 = z;
        final String str3 = str;
        final int i2 = i;
        final String str4 = str2;
        new Thread(new Runnable() {
            public void run() {
                ShareStatsRequest shareStatsRequest = new ShareStatsRequest(context2, SocializeReseponse.class);
                shareStatsRequest.addStringParams(AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, share_media2.getsharestyle(z2));
                shareStatsRequest.addStringParams("platform", share_media2.toString().toLowerCase());
                shareStatsRequest.addStringParams("version", str3);
                shareStatsRequest.addStringParams("sharetype", String.valueOf(i2));
                shareStatsRequest.addStringParams("tag", str4);
                if (share_media2 == SHARE_MEDIA.QQ) {
                    if (Config.isUmengQQ.booleanValue()) {
                        shareStatsRequest.addStringParams("isumeng", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                    } else {
                        shareStatsRequest.addStringParams("isumeng", "false");
                    }
                }
                if (share_media2 == SHARE_MEDIA.SINA) {
                    if (Config.isUmengSina.booleanValue()) {
                        shareStatsRequest.addStringParams("isumeng", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                    } else {
                        shareStatsRequest.addStringParams("isumeng", "false");
                    }
                }
                if (share_media2 == SHARE_MEDIA.WEIXIN || share_media2 == SHARE_MEDIA.WEIXIN_CIRCLE || share_media2 == SHARE_MEDIA.WEIXIN_FAVORITE) {
                    if (Config.isUmengWx.booleanValue()) {
                        shareStatsRequest.addStringParams("isumeng", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                    } else {
                        shareStatsRequest.addStringParams("isumeng", "false");
                    }
                }
                StatsAPIs.shareStatsStart(shareStatsRequest);
            }
        }).start();
    }

    public static void shareend(Context context, String str, String str2, String str3, String str4) {
        final Context context2 = context;
        final String str5 = str2;
        final String str6 = str3;
        final String str7 = str;
        final String str8 = str4;
        new Thread(new Runnable() {
            public void run() {
                ShareStatsRequest shareStatsRequest = new ShareStatsRequest(context2, SocializeReseponse.class);
                shareStatsRequest.addStringParams("result", str5);
                if (!TextUtils.isEmpty(str6)) {
                    shareStatsRequest.addStringParams("errormsg", str6);
                }
                shareStatsRequest.addStringParams("platform", str7);
                shareStatsRequest.addStringParams("tag", str8);
                StatsAPIs.shareStatsEnd(shareStatsRequest);
            }
        }).start();
    }

    public static void getInfostart(final Context context, final SHARE_MEDIA share_media, final String str, final String str2) {
        new Thread(new Runnable() {
            public void run() {
                UserInfoStatsRequest userInfoStatsRequest = new UserInfoStatsRequest(context, SocializeReseponse.class);
                userInfoStatsRequest.addStringParams("platform", share_media.toString().toLowerCase());
                userInfoStatsRequest.addStringParams("version", str);
                userInfoStatsRequest.addStringParams("tag", str2);
                StatsAPIs.userInfoStatsStart(userInfoStatsRequest);
            }
        }).start();
    }

    public static void getInfoendt(Context context, String str, String str2, String str3, String str4) {
        final Context context2 = context;
        final String str5 = str2;
        final String str6 = str3;
        final String str7 = str4;
        final String str8 = str;
        new Thread(new Runnable() {
            public void run() {
                UserInfoStatsRequest userInfoStatsRequest = new UserInfoStatsRequest(context2, SocializeReseponse.class);
                userInfoStatsRequest.addStringParams("result", str5);
                if (!TextUtils.isEmpty(str6)) {
                    userInfoStatsRequest.addStringParams("errormsg", str6);
                }
                userInfoStatsRequest.addStringParams("tag", str7);
                userInfoStatsRequest.addStringParams("platform", str8);
                StatsAPIs.userInfoStatsEnd(userInfoStatsRequest);
            }
        }).start();
    }
}
