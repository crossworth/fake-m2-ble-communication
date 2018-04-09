package com.droi.sdk.push;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.PushCoreHelper;
import com.droi.sdk.core.priv.CorePriv;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.push.utils.GetDeviceIdCallback;
import com.droi.sdk.utility.Utility;
import java.util.concurrent.atomic.AtomicBoolean;

public class DroiPush {
    private static DroiPush f3177a = null;
    private static String f3178b = null;
    private static GetDeviceIdCallback f3179c = null;
    private static AtomicBoolean f3180d = new AtomicBoolean(false);

    private DroiPush(Context context) {
    }

    static synchronized String m2876a(Context context) {
        String str;
        synchronized (DroiPush.class) {
            try {
                if (TextUtils.isEmpty(f3178b) && !f3180d.get()) {
                    f3180d.set(true);
                    PushCoreHelper.m2597a(context, new C0982b(context));
                }
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
            str = f3178b;
        }
        return str;
    }

    public static boolean addTag(Context context, String[] strArr, boolean z) {
        if (context != null) {
            try {
                return aa.m2958a(context).m2973a(strArr, z);
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return false;
    }

    private static synchronized void m2879b(Context context, String str, DroiError droiError) {
        synchronized (DroiPush.class) {
            if (droiError == null) {
                C1012g.m3141c("Internal error: droiError object is null!");
            } else if (droiError.isOk()) {
                C1012g.m3141c("Get deviceId successful!");
                if (!TextUtils.isEmpty(str)) {
                    f3178b = str.replace("-", "");
                }
                String packageName = context.getPackageName();
                if (packageName != null && packageName.equals(C1015j.m3171f(context))) {
                    ad.m3002d(context);
                }
            } else {
                C1012g.m3141c("Get deviceId failed: " + droiError.toString());
            }
            f3180d.set(false);
        }
    }

    public static boolean clearSilentTime(Context context) {
        boolean z = false;
        if (context != null) {
            try {
                z = aa.m2958a(context).m2968a(context.getPackageName(), false);
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return z;
    }

    public static String getAppId(Context context) {
        if (context == null) {
            throw new RuntimeException("The context passed in getAppId method is null!");
        }
        try {
            return PushCoreHelper.m2596a();
        } catch (Exception e) {
            C1012g.m3139b(e);
            return "";
        }
    }

    public static String getChannel(Context context) {
        if (context == null) {
            throw new RuntimeException("The context passed in getChannel method is null!");
        }
        try {
            return Core.getChannelName(context);
        } catch (Exception e) {
            C1012g.m3139b(e);
            return CorePriv.f2827a;
        }
    }

    public static synchronized void getDeviceId(Context context, GetDeviceIdCallback getDeviceIdCallback) {
        synchronized (DroiPush.class) {
            if (context == null) {
                throw new RuntimeException("The context passed in getDeviceId method is null!");
            }
            try {
                if (TextUtils.isEmpty(f3178b)) {
                    PushCoreHelper.m2597a(context, new C0978a(context, getDeviceIdCallback));
                } else if (getDeviceIdCallback != null) {
                    getDeviceIdCallback.onGetDeviceId(f3178b);
                }
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
    }

    public static boolean getPushEnabled(Context context) {
        if (context != null) {
            try {
                return aa.m2958a(context).m2980e();
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return true;
    }

    public static String getSecret(Context context) {
        if (context == null) {
            throw new RuntimeException("The context passed in getSecret method is null!");
        }
        try {
            return C1015j.m3150a(context, "com.droi.sdk.secret_key");
        } catch (Exception e) {
            C1012g.m3139b(e);
            return "";
        }
    }

    public static int[] getSilentTime(Context context) {
        if (context != null) {
            try {
                return aa.m2958a(context).m2975b(context.getPackageName());
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return null;
    }

    public static String getTags(Context context) {
        try {
            return aa.m2958a(context).m2974b();
        } catch (Exception e) {
            C1012g.m3139b(e);
            return "";
        }
    }

    public static void initialize(Context context) {
        synchronized (DroiPush.class) {
            if (context == null) {
                throw new RuntimeException("The context passed in initialize method is null!");
            } else if (Looper.myLooper() != Looper.getMainLooper()) {
                throw new RuntimeException("'initialize' must be called in MainThread!");
            } else if (f3177a != null) {
            } else {
                try {
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext != null) {
                        if (f3177a == null) {
                            Utility.initialize(applicationContext);
                            f3177a = new DroiPush(context);
                        }
                        String packageName = context.getPackageName();
                        if (packageName == null || packageName.equals(C1015j.m3171f(context))) {
                            ad.m2992a(applicationContext);
                            aa.m2958a(applicationContext).m2988k();
                            C1012g.m3138a("DroiPush initialized: 1.0.004");
                        }
                        return;
                    }
                    C1012g.m3140b("application context in initialize is null!");
                    C1012g.m3138a("DroiPush initialized: 1.0.004");
                } catch (Exception e) {
                    C1012g.m3139b(e);
                }
            }
        }
    }

    public static boolean removeAllTag(Context context) {
        if (context != null) {
            try {
                return aa.m2958a(context).m2965a();
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return false;
    }

    public static boolean removeTag(Context context, String[] strArr) {
        if (context != null) {
            try {
                return aa.m2958a(context).m2972a(strArr);
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return false;
    }

    public static void setMessageHandler(DroiMessageHandler droiMessageHandler) {
        C1005u.m3111a(droiMessageHandler);
    }

    public static boolean setPushEnabled(Context context, Boolean bool) {
        if (context != null) {
            try {
                return aa.m2958a(context).m2971a(bool.booleanValue());
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return false;
    }

    public static boolean setSilentTime(Context context, int i, int i2, int i3, int i4) {
        if (context != null) {
            try {
                return aa.m2958a(context).m2969a(context.getPackageName(), false, i, i2, i3, i4);
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return false;
    }
}
