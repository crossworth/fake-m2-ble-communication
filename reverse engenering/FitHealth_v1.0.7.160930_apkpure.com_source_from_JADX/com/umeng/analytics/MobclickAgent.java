package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.social.C0942e;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.UMSocialService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.microedition.khronos.opengles.GL10;
import p031u.aly.bl;

public class MobclickAgent {
    private static final String f3094a = "input map is null";
    private static final C1784d f3095b = new C1784d();

    public enum EScenarioType {
        E_UM_NORMAL(0),
        E_UM_GAME(1),
        E_UM_ANALYTICS_OEM(224),
        E_UM_GAME_OEM(225);
        
        private int f3093a;

        private EScenarioType(int i) {
            this.f3093a = i;
        }

        public int toValue() {
            return this.f3093a;
        }
    }

    public static class UMAnalyticsConfig {
        public String mAppkey;
        public String mChannelId;
        public Context mContext;
        public boolean mIsCrashEnable;
        public EScenarioType mType;

        private UMAnalyticsConfig() {
            this.mAppkey = null;
            this.mChannelId = null;
            this.mIsCrashEnable = true;
            this.mType = EScenarioType.E_UM_NORMAL;
            this.mContext = null;
        }

        public UMAnalyticsConfig(Context context, String str, String str2) {
            this(context, str, str2, null, true);
        }

        public UMAnalyticsConfig(Context context, String str, String str2, EScenarioType eScenarioType) {
            this(context, str, str2, eScenarioType, true);
        }

        public UMAnalyticsConfig(Context context, String str, String str2, EScenarioType eScenarioType, boolean z) {
            this.mAppkey = null;
            this.mChannelId = null;
            this.mIsCrashEnable = true;
            this.mType = EScenarioType.E_UM_NORMAL;
            this.mContext = null;
            this.mContext = context;
            this.mAppkey = str;
            this.mChannelId = str2;
            this.mIsCrashEnable = z;
            if (eScenarioType != null) {
                this.mType = eScenarioType;
                return;
            }
            switch (AnalyticsConfig.getVerticalType(context)) {
                case 0:
                    this.mType = EScenarioType.E_UM_NORMAL;
                    return;
                case 1:
                    this.mType = EScenarioType.E_UM_GAME;
                    return;
                case 224:
                    this.mType = EScenarioType.E_UM_ANALYTICS_OEM;
                    return;
                case 225:
                    this.mType = EScenarioType.E_UM_GAME_OEM;
                    return;
                default:
                    return;
            }
        }
    }

    public static void startWithConfigure(UMAnalyticsConfig uMAnalyticsConfig) {
        if (uMAnalyticsConfig != null) {
            f3095b.m4944a(uMAnalyticsConfig);
        }
    }

    public static void setLocation(double d, double d2) {
        f3095b.m4933a(d, d2);
    }

    public static void setLatencyWindow(long j) {
        f3095b.m4934a(j);
    }

    public static void enableEncrypt(boolean z) {
        f3095b.m4963f(z);
    }

    public static void setCatchUncaughtExceptions(boolean z) {
        f3095b.m4950a(z);
    }

    public static void setWrapper(String str, String str2) {
        f3095b.m4947a(str, str2);
    }

    public static void setSecret(Context context, String str) {
        f3095b.m4954b(context, str);
    }

    public static void setScenarioType(Context context, EScenarioType eScenarioType) {
        f3095b.m4937a(context, eScenarioType);
    }

    public static void setSessionContinueMillis(long j) {
        f3095b.m4952b(j);
    }

    public static void setEnableEventBuffer(boolean z) {
        f3095b.m4957b(z);
    }

    public static C1784d getAgent() {
        return f3095b;
    }

    public static void setCheckDevice(boolean z) {
        f3095b.m4961d(z);
    }

    public static void setOpenGLContext(GL10 gl10) {
        f3095b.m4949a(gl10);
    }

    public static void openActivityDurationTrack(boolean z) {
        f3095b.m4959c(z);
    }

    public static void onPageStart(String str) {
        if (TextUtils.isEmpty(str)) {
            bl.m3594e("pageName is null or empty");
        } else {
            f3095b.m4946a(str);
        }
    }

    public static void onPageEnd(String str) {
        if (TextUtils.isEmpty(str)) {
            bl.m3594e("pageName is null or empty");
        } else {
            f3095b.m4955b(str);
        }
    }

    public static void setDebugMode(boolean z) {
        f3095b.m4962e(z);
    }

    public static void onPause(Context context) {
        f3095b.m4953b(context);
    }

    public static void onResume(Context context) {
        if (context == null) {
            bl.m3594e("unexpected null context in onResume");
        } else {
            f3095b.m4935a(context);
        }
    }

    public static void reportError(Context context, String str) {
        f3095b.m4938a(context, str);
    }

    public static void reportError(Context context, Throwable th) {
        f3095b.m4942a(context, th);
    }

    public static void onEvent(Context context, List<String> list, int i, String str) {
        f3095b.m4943a(context, (List) list, i, str);
    }

    public static void onEvent(Context context, String str) {
        f3095b.m4939a(context, str, null, -1, 1);
    }

    public static void onEvent(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            bl.m3582c("label is null or empty");
        } else {
            f3095b.m4939a(context, str, str2, -1, 1);
        }
    }

    public static void onEvent(Context context, String str, Map<String, String> map) {
        if (map == null) {
            bl.m3594e(f3094a);
            return;
        }
        f3095b.m4941a(context, str, new HashMap(map), -1);
    }

    public static void onEventValue(Context context, String str, Map<String, String> map, int i) {
        Map hashMap;
        if (map == null) {
            hashMap = new HashMap();
        } else {
            hashMap = new HashMap(map);
        }
        hashMap.put("__ct__", Integer.valueOf(i));
        f3095b.m4941a(context, str, hashMap, -1);
    }

    public static void onSocialEvent(Context context, String str, UMPlatformData... uMPlatformDataArr) {
        if (context == null) {
            bl.m3594e("context is null in onShareEvent");
            return;
        }
        C0942e.f3194e = "3";
        UMSocialService.share(context, str, uMPlatformDataArr);
    }

    public static void onSocialEvent(Context context, UMPlatformData... uMPlatformDataArr) {
        if (context == null) {
            bl.m3594e("context is null in onShareEvent");
            return;
        }
        C0942e.f3194e = "3";
        UMSocialService.share(context, uMPlatformDataArr);
    }

    public static void onKillProcess(Context context) {
        f3095b.m4960d(context);
    }

    public static void onProfileSignIn(String str) {
        onProfileSignIn("_adhoc", str);
    }

    public static void onProfileSignIn(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            bl.m3588d("uid is null");
        } else if (str2.length() > 64) {
            bl.m3588d("uid is Illegal(length bigger then  legitimate length).");
        } else if (TextUtils.isEmpty(str)) {
            f3095b.m4956b("_adhoc", str2);
        } else if (str.length() > 32) {
            bl.m3588d("provider is Illegal(length bigger then  legitimate length).");
        } else {
            f3095b.m4956b(str, str2);
        }
    }

    public static void onProfileSignOff() {
        f3095b.m4951b();
    }
}
