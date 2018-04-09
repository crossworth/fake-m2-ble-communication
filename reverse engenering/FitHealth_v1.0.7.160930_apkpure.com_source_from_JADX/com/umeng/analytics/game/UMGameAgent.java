package com.umeng.analytics.game;

import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.C0942e;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.UMSocialService;
import p031u.aly.bl;

public class UMGameAgent extends MobclickAgent {
    private static final String f4777a = "Input string is null or empty";
    private static final String f4778b = "Input string must be less than 64 chars";
    private static final String f4779c = "Input value type is negative";
    private static final String f4780d = "The int value for 'Pay Channels' ranges between 1 ~ 99 ";
    private static final C1787c f4781e = new C1787c();
    private static Context f4782f;

    public static void init(Context context) {
        f4781e.m4976a(context);
        f4782f = context.getApplicationContext();
    }

    public static void setTraceSleepTime(boolean z) {
        f4781e.m4980a(z);
    }

    public static void setPlayerLevel(int i) {
        f4781e.m4977a(String.valueOf(i));
    }

    public static void startLevel(String str) {
        if (m4964a(str)) {
            bl.m3594e(f4777a);
        } else if (str.length() > 64) {
            bl.m3594e(f4778b);
        } else {
            f4781e.m4982b(str);
        }
    }

    public static void finishLevel(String str) {
        if (m4964a(str)) {
            bl.m3594e(f4777a);
        } else if (str.length() > 64) {
            bl.m3594e(f4778b);
        } else {
            f4781e.m4984c(str);
        }
    }

    public static void failLevel(String str) {
        if (m4964a(str)) {
            bl.m3594e(f4777a);
        } else if (str.length() > 64) {
            bl.m3594e(f4778b);
        } else {
            f4781e.m4985d(str);
        }
    }

    public static void pay(double d, double d2, int i) {
        if (i <= 0 || i >= 100) {
            bl.m3594e(f4780d);
        } else if (d < 0.0d || d2 < 0.0d) {
            bl.m3594e(f4779c);
        } else {
            f4781e.m4972a(d, d2, i);
        }
    }

    public static void pay(double d, String str, int i, double d2, int i2) {
        if (i2 <= 0 || i2 >= 100) {
            bl.m3594e(f4780d);
        } else if (d < 0.0d || i < 0 || d2 < 0.0d) {
            bl.m3594e(f4779c);
        } else if (m4964a(str)) {
            bl.m3594e(f4777a);
        } else {
            f4781e.m4975a(d, str, i, d2, i2);
        }
    }

    public static void exchange(double d, String str, double d2, int i, String str2) {
        if (d < 0.0d || d2 < 0.0d) {
            bl.m3594e(f4779c);
        } else if (i <= 0 || i >= 100) {
            bl.m3594e(f4780d);
        } else {
            f4781e.m4974a(d, str, d2, i, str2);
        }
    }

    public static void buy(String str, int i, double d) {
        if (m4964a(str)) {
            bl.m3594e(f4777a);
        } else if (i < 0 || d < 0.0d) {
            bl.m3594e(f4779c);
        } else {
            f4781e.m4978a(str, i, d);
        }
    }

    public static void use(String str, int i, double d) {
        if (m4964a(str)) {
            bl.m3594e(f4777a);
        } else if (i < 0 || d < 0.0d) {
            bl.m3594e(f4779c);
        } else {
            f4781e.m4983b(str, i, d);
        }
    }

    public static void bonus(double d, int i) {
        if (d < 0.0d) {
            bl.m3594e(f4779c);
        } else if (i <= 0 || i >= 100) {
            bl.m3594e(f4780d);
        } else {
            f4781e.m4973a(d, i);
        }
    }

    public static void bonus(String str, int i, double d, int i2) {
        if (m4964a(str)) {
            bl.m3594e(f4777a);
        } else if (i < 0 || d < 0.0d) {
            bl.m3594e(f4779c);
        } else if (i2 <= 0 || i2 >= 100) {
            bl.m3594e(f4780d);
        } else {
            f4781e.m4979a(str, i, d, i2);
        }
    }

    private static boolean m4964a(String str) {
        if (str != null && str.trim().length() > 0) {
            return false;
        }
        return true;
    }

    public static void onEvent(String str, String str2) {
        MobclickAgent.onEvent(f4782f, str, str2);
    }

    public static void onSocialEvent(Context context, String str, UMPlatformData... uMPlatformDataArr) {
        if (context == null) {
            bl.m3594e("context is null in onShareEvent");
            return;
        }
        C0942e.f3194e = "4";
        UMSocialService.share(context, str, uMPlatformDataArr);
    }

    public static void onSocialEvent(Context context, UMPlatformData... uMPlatformDataArr) {
        if (context == null) {
            bl.m3594e("context is null in onShareEvent");
            return;
        }
        C0942e.f3194e = "4";
        UMSocialService.share(context, uMPlatformDataArr);
    }
}
