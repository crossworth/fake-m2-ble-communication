package com.adroi.sdk;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import com.adroi.sdk.p000a.C0277b;
import com.adroi.sdk.p000a.C0278c;
import com.umeng.facebook.share.internal.ShareConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public final class NativeAds {
    private static Class<?> f22d;
    private static Context f23e;
    private JSONArray f24a = new JSONArray();
    private boolean f25b = false;
    private Object f26c;
    public Handler handler = new Handler(Looper.getMainLooper());

    private class C0275a implements Callback {
        final /* synthetic */ NativeAds f20a;
        private final NativeAdsListener f21b;

        public C0275a(NativeAds nativeAds, NativeAdsListener nativeAdsListener) {
            this.f20a = nativeAds;
            this.f21b = nativeAdsListener;
        }

        public boolean handleMessage(Message message) {
            C0278c.m43c("AdViewCallback msg.what:" + message.what + ",msg:" + message.getData().getString("method"));
            try {
                Bundle data = message.getData();
                String string = data.getString("method");
                if ("onAdReady".equals(string)) {
                    String string2 = data.getString("rq_data");
                    C0278c.m30a("native obj =" + string2);
                    this.f20a.f24a = new JSONObject(string2).getJSONArray("nativeAds");
                    C0278c.m30a("native array =" + this.f20a.f24a.toString());
                    if (this.f20a.f24a.length() > 0) {
                        this.f20a.f25b = true;
                        this.f20a.handler.post(new C0279a(this));
                    } else {
                        this.f21b.onAdFailed(new JSONObject().put("eid", 2).put("info", "ad convert failed!"));
                    }
                } else if ("onAdFailed".equals(string)) {
                    this.f21b.onAdFailed(new JSONObject(data.getString("rq_reason")));
                }
            } catch (Throwable e) {
                C0278c.m41b(e);
            }
            return false;
        }
    }

    private String m21a(String str) {
        try {
            if (this.f25b) {
                return this.f24a.getJSONObject(0).getString(str);
            }
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
        return "";
    }

    public String getAccount() {
        return m21a("account");
    }

    public ImageView getAdIcon(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(C0277b.m29b());
        imageView.setLayoutParams(new LayoutParams(m20a(context, (float) (((double) C0277b.m29b().getWidth()) / 1.5d)), m20a(context, (float) (((double) C0277b.m29b().getHeight()) / 1.5d))));
        return imageView;
    }

    public String getAppName() {
        return m21a("appname");
    }

    public String getId() {
        return m21a(ShareConstants.WEB_DIALOG_PARAM_ID);
    }

    public String getTitle() {
        return m21a("title");
    }

    public String getDesc1() {
        return m21a("desc1");
    }

    public String getDesc2() {
        return m21a("desc2");
    }

    public String getImgUrl() {
        return m21a("imgurl");
    }

    public String getLogoUrl() {
        return m21a("logourl");
    }

    public int getWidth() {
        try {
            return Integer.parseInt(m21a("w"));
        } catch (Throwable e) {
            C0278c.m41b(e);
            return 0;
        }
    }

    public int getHeight() {
        try {
            return Integer.parseInt(m21a("h"));
        } catch (Throwable e) {
            C0278c.m41b(e);
            return 0;
        }
    }

    public NativeAds(Context context, String str) {
        f23e = context;
        try {
            if (f22d == null) {
                f22d = C0277b.m25a(context, "com.adroi.sdk.remote.NativeObject");
                C0278c.m30a("get remote class :REMOTE_AD_VIEW");
            }
            if (context == null) {
                throw new Exception("must invoke preLoad() at first");
            }
            this.f26c = f22d.getConstructor(new Class[]{Context.class, String.class}).newInstance(new Object[]{context, str});
            C0278c.m30a("remoteClass invoke ----");
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public void setListener(NativeAdsListener nativeAdsListener) {
        if (nativeAdsListener == null) {
            throw new IllegalArgumentException();
        }
        try {
            f22d.getMethod("setListener", new Class[]{Callback.class}).invoke(this.f26c, new Object[]{new C0275a(this, nativeAdsListener)});
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public void setAdImpression(View view) {
        if (this.f24a != null) {
            C0278c.m30a("setAdImpression");
            String id = getId();
            try {
                if (this.f26c != null && f22d != null) {
                    f22d.getMethod("setAdImpression", new Class[]{Context.class, String.class, View.class}).invoke(this.f26c, new Object[]{f23e, id, view});
                }
            } catch (Throwable e) {
                C0278c.m32a(e);
            }
        }
    }

    public void setAdClick(View view) {
        if (this.f24a != null) {
            C0278c.m30a("setAdClick");
            String id = getId();
            try {
                if (this.f26c != null && f22d != null) {
                    f22d.getMethod("setNativeAdClick", new Class[]{Context.class, String.class, View.class}).invoke(this.f26c, new Object[]{f23e, id, view});
                }
            } catch (Throwable e) {
                C0278c.m32a(e);
            }
        }
    }

    public void reFresh() {
        if (this.f24a != null) {
            C0278c.m30a("reFresh");
            try {
                if (this.f26c != null && f22d != null) {
                    f22d.getMethod("reFresh", new Class[0]).invoke(this.f26c, new Object[0]);
                }
            } catch (Throwable e) {
                C0278c.m32a(e);
            }
        }
    }

    public static void setAdSize(String str, int i, int i2) {
        try {
            if (f22d == null) {
                f22d = C0277b.m25a(f23e, "com.adroi.sdk.remote.NativeObject");
            }
            f22d.getDeclaredMethod("setAdSize", new Class[]{String.class, Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2)});
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    public void onDestroy() {
        try {
            if (f23e != null) {
                if (f22d == null) {
                    f22d = C0277b.m25a(f23e, "com.adroi.sdk.remote.NativeObject");
                }
                if (this.f26c != null) {
                    f22d.getDeclaredMethod("onDestroy", new Class[0]).invoke(this.f26c, new Object[0]);
                }
            }
        } catch (Throwable e) {
            C0278c.m41b(e);
        }
    }

    private static int m20a(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }
}
