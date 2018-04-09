package com.tencent.connect.p010a;

import android.content.Context;
import com.tencent.connect.auth.QQToken;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;
import com.tencent.stat.common.StatConstants;
import com.tencent.utils.OpenConfig;

/* compiled from: ProGuard */
public class C0687a {
    public static boolean m2307a(Context context, QQToken qQToken) {
        return OpenConfig.getInstance(context, qQToken.getAppId()).getBoolean("Common_ta_enable");
    }

    public static void m2308b(Context context, QQToken qQToken) {
        if (C0687a.m2307a(context, qQToken)) {
            StatConfig.setEnableStatService(true);
        } else {
            StatConfig.setEnableStatService(false);
        }
    }

    public static void m2309c(Context context, QQToken qQToken) {
        C0687a.m2308b(context, qQToken);
        String str = "Aqc" + qQToken.getAppId();
        StatConfig.setAutoExceptionCaught(false);
        StatConfig.setEnableSmartReporting(true);
        StatConfig.setSendPeriodMinutes(1440);
        StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
        StatConfig.setStatReportUrl("http://cgi.connect.qq.com/qqconnectutil/sdk");
        StatService.startStatService(context, str, StatConstants.VERSION);
    }

    public static void m2310d(Context context, QQToken qQToken) {
        C0687a.m2308b(context, qQToken);
        if (qQToken.getOpenId() != null) {
            StatService.reportQQ(context, qQToken.getOpenId());
        }
    }

    public static void m2306a(Context context, QQToken qQToken, String str, String... strArr) {
        C0687a.m2308b(context, qQToken);
        StatService.trackCustomEvent(context, str, strArr);
    }
}
