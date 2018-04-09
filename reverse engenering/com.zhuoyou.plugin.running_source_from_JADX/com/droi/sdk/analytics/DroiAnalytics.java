package com.droi.sdk.analytics;

import android.app.Application;
import android.content.Context;
import java.util.Map;

public class DroiAnalytics {
    private static C0770f f2268a;

    public static boolean enableActivityLifecycleCallbacks(Application application) {
        if (f2268a != null) {
            return f2268a.m2365a(application);
        }
        C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        return false;
    }

    public static void initialize(Context context) {
        C0753a.m2312a("DroiAnalytics", "Start initialize DroiAnalytics");
        f2268a = C0770f.m2352a(context);
    }

    public static void onCalculateEvent(Context context, String str, Map<String, String> map, int i) {
        if (f2268a != null) {
            f2268a.m2368b(context, str, map, i);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void onError(Context context, Exception exception) {
        if (f2268a != null) {
            f2268a.m2359a(context, exception);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void onError(Context context, String str) {
        if (f2268a != null) {
            f2268a.m2370c(context, str);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void onEvent(Context context, String str) {
        if (f2268a != null) {
            f2268a.m2371d(context, str);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void onEvent(Context context, String str, Map<String, String> map) {
        if (f2268a != null) {
            f2268a.m2361a(context, str, map, 1);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void onFragmentEnd(Context context, String str) {
        if (f2268a != null) {
            f2268a.m2367b(context, str);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void onFragmentStart(Context context, String str) {
        if (f2268a != null) {
            f2268a.m2360a(context, str);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void onPause(Context context) {
        if (C0756d.f2275c) {
            C0753a.m2314c("DroiAnalytics", "You had enableActivityLifecycleCallbacks");
        } else if (f2268a != null) {
            f2268a.m2369c(context);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void onResume(Context context) {
        if (!C0756d.f2275c) {
            if (f2268a != null) {
                f2268a.m2366b(context);
            } else {
                C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
            }
        }
    }

    public static void setCrashReport(boolean z) {
        if (f2268a != null) {
            f2268a.m2363a(z);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void setDefaultSendPolicy(SendPolicy sendPolicy) {
        if (f2268a != null) {
            f2268a.m2362a(sendPolicy);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }

    public static void setScheduleConfig(boolean z, int i) {
        if (f2268a != null) {
            f2268a.m2364a(z, i);
        } else {
            C0753a.m2315d("DroiAnalytics", "Please initialize DroiAnalytics!");
        }
    }
}
