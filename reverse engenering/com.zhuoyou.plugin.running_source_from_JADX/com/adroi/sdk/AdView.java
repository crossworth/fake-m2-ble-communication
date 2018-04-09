package com.adroi.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.adroi.sdk.p000a.C0277b;
import com.adroi.sdk.p000a.C0278c;

public final class AdView extends RelativeLayout {
    private static Class<?> f12c;
    private static Context f13d;
    long f14a = System.currentTimeMillis();
    public AdSize adSize;
    private ViewGroup f15b;
    public Handler handler = new Handler(Looper.getMainLooper());

    private class C0274a implements Callback {
        final /* synthetic */ AdView f10a;
        private final AdViewListener f11b;

        public C0274a(AdView adView, AdViewListener adViewListener, AdView adView2) {
            this.f10a = adView;
            this.f11b = adViewListener;
        }

        public boolean handleMessage(Message message) {
            C0278c.m43c("AdViewCallback msg.what:" + message.what + ",msg:" + message.getData().getString("method"));
            try {
                Bundle data = message.getData();
                String string = data.getString("method");
                if ("onAdReady".equals(string)) {
                    this.f11b.onAdReady();
                } else if ("onAdShow".equals(string)) {
                    this.f11b.onAdShow();
                } else if ("onAdClick".equals(string)) {
                    this.f11b.onAdClick();
                } else if ("onAdFailed".equals(string)) {
                    String string2 = data.getString("rq_reason");
                    C0278c.m30a("M_ON_AD_FAILED:reason:" + string2);
                    this.f11b.onAdFailed(string2);
                } else if ("onAdDismissed".equals(string)) {
                    this.f11b.onAdDismissed();
                } else if ("onAdSwitch".equals(string)) {
                    this.f11b.onAdSwitch();
                }
            } catch (Throwable e) {
                C0278c.m41b(e);
            }
            return false;
        }
    }

    public static void preLoad(Context context) {
        try {
            if (f12c == null) {
                f12c = C0277b.m25a(context, "com.adroi.sdk.remote.AdView");
            }
            C0278c.m30a("preLoad init");
            if (C0277b.m27a() != null) {
                f12c.getDeclaredMethod("onPreLoad", new Class[]{Context.class, String.class}).invoke(null, new Object[]{context, r0});
                return;
            }
            f12c.getDeclaredMethod("onPreLoad", new Class[]{Context.class}).invoke(null, new Object[]{context});
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public static void setAppId(Context context, String str) {
        Editor edit = context.getSharedPreferences("adroi_poly_config", 0).edit();
        edit.putString("adroiid", str);
        edit.commit();
    }

    public AdView(Activity activity, AdSize adSize, String str) {
        super(activity);
        this.adSize = adSize;
        f13d = activity;
        try {
            if (f12c == null) {
                f12c = C0277b.m25a(f13d, "com.adroi.sdk.remote.AdView");
                C0278c.m30a("get remote class :REMOTE_AD_VIEW");
            }
            if (f13d == null) {
                throw new Exception("must invoke preLoad() at first");
            }
            this.f15b = (ViewGroup) f12c.getConstructor(new Class[]{Context.class, Integer.TYPE, String.class}).newInstance(new Object[]{activity, Integer.valueOf(adSize.getValue()), str});
            addView(this.f15b, new LayoutParams(-1, -1));
            C0278c.m30a("remoteClass invoke ----");
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public void setListener(AdViewListener adViewListener) {
        if (adViewListener == null) {
            throw new IllegalArgumentException();
        }
        try {
            f12c.getMethod("setListener", new Class[]{Callback.class}).invoke(this.f15b, new Object[]{new C0274a(this, adViewListener, this)});
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public void onDesroyAd() {
        C0278c.m30a("adview ondestroy ad!");
        try {
            this.handler.removeCallbacksAndMessages(null);
            if (f12c == null) {
                f12c = C0277b.m25a(f13d, "com.adroi.sdk.remote.AdView");
            }
            if (this.f15b != null) {
                f12c.getDeclaredMethod("onDestroyAd", new Class[0]).invoke(this.f15b, new Object[0]);
                removeView(this.f15b);
            }
            this.f15b = null;
            f12c = null;
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public void showInterstialAd() {
        if (this.adSize != AdSize.InterstitialAd) {
            C0278c.m30a("showInterstialAd type confict! adsize =" + this.adSize + ":AdSize.InterstitialAd=" + AdSize.InterstitialAd);
            System.err.println("only interstial ad can use this");
            System.exit(-1);
        }
        try {
            C0278c.m30a("invoke remote function!");
            if (f12c == null) {
                f12c = C0277b.m25a(f13d, "com.adroi.sdk.remote.AdView");
            }
            if (this.f15b != null && f12c != null) {
                f12c.getMethod("showInterstialAd", new Class[0]).invoke(this.f15b, new Object[0]);
            }
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public void showInterstialAdByPopup() {
        if (this.adSize != AdSize.InterstitialAd) {
            C0278c.m30a("showInterstialAd type confict! adsize =" + this.adSize + ":AdSize.InterstitialAd=" + AdSize.InterstitialAd);
            System.err.println("only interstial ad can use this");
            System.exit(-1);
        }
        try {
            C0278c.m30a("invoke remote function!");
            if (f12c == null) {
                f12c = C0277b.m25a(f13d, "com.adroi.sdk.remote.AdView");
            }
            if (this.f15b != null && f12c != null) {
                f12c.getMethod("showInterstialAdByPopup", new Class[0]).invoke(this.f15b, new Object[0]);
            }
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public static void setAdSize(String str, int i, int i2) {
        try {
            if (f12c == null) {
                f12c = C0277b.m25a(f13d, "com.adroi.sdk.remote.AdView");
            }
            f12c.getDeclaredMethod("setAdSize", new Class[]{String.class, Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2)});
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public static void setAdType(String str, int i) {
        try {
            if (f12c == null) {
                f12c = C0277b.m25a(f13d, "com.adroi.sdk.remote.AdView");
            }
            f12c.getDeclaredMethod("setAdType", new Class[]{String.class, Integer.TYPE}).invoke(null, new Object[]{str, Integer.valueOf(i)});
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public static void setAdCapacity(String str, int i) {
        try {
            if (f12c == null) {
                f12c = C0277b.m25a(f13d, "com.adroi.sdk.remote.AdView");
            }
            f12c.getDeclaredMethod("setAdCapacity", new Class[]{String.class, Integer.TYPE}).invoke(null, new Object[]{str, Integer.valueOf(i)});
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public static void setDebug(boolean z) {
        try {
            if (f12c == null) {
                f12c = C0277b.m25a(f13d, "com.adroi.sdk.remote.AdView");
            }
            f12c.getDeclaredMethod("setDebug", new Class[]{Boolean.TYPE}).invoke(null, new Object[]{Boolean.valueOf(z)});
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }
}
