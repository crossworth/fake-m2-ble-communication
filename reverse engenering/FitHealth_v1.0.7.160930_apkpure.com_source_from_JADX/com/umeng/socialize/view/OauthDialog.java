package com.umeng.socialize.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.socialize.Config;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.utils.AesHelper;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.URLBuilder;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OauthDialog extends Dialog {
    private static final String f3365a = "OauthDialog";
    private final ResContainer f3366b;
    private WebView f3367c;
    private View f3368d;
    private View f3369e;
    private CheckBox f3370f;
    private int f3371g = 0;
    private Bundle f3372h;
    private String f3373i = "error";
    private Context f3374j;
    private Activity f3375k;
    private SHARE_MEDIA f3376l;
    private Set<String> f3377m;
    private C0984a f3378n;
    private Handler f3379o = new C1004a(this);

    static class C0984a {
        private UMAuthListener f3362a = null;
        private SHARE_MEDIA f3363b;
        private int f3364c;

        public C0984a(UMAuthListener uMAuthListener, SHARE_MEDIA share_media) {
            this.f3362a = uMAuthListener;
            this.f3363b = share_media;
        }

        public void m3265a(Exception exception) {
            if (this.f3362a != null) {
                this.f3362a.onError(this.f3363b, this.f3364c, exception);
            }
        }

        public void m3264a(Bundle bundle) {
            if (this.f3362a != null) {
                this.f3362a.onComplete(this.f3363b, this.f3364c, m3262b(bundle));
            }
        }

        public void m3263a() {
            if (this.f3362a != null) {
                this.f3362a.onCancel(this.f3363b, this.f3364c);
            }
        }

        private Map<String, String> m3262b(Bundle bundle) {
            if (bundle == null || bundle.isEmpty()) {
                return null;
            }
            Set<String> keySet = bundle.keySet();
            Map<String, String> hashMap = new HashMap();
            for (String str : keySet) {
                hashMap.put(str, bundle.getString(str));
            }
            return hashMap;
        }
    }

    private class C1828b extends WebViewClient {
        final /* synthetic */ OauthDialog f4846b;

        private C1828b(OauthDialog oauthDialog) {
            this.f4846b = oauthDialog;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Log.m3254i(OauthDialog.f3365a, "shouldOverrideUrlLoading current : " + str);
            if (DeviceConfig.isNetworkAvailable(this.f4846b.f3374j)) {
                if (str.contains("?ud_get=")) {
                    str = this.f4846b.m3271a(str);
                }
                if (str.contains(this.f4846b.f3373i)) {
                    m5007a(str);
                }
                return super.shouldOverrideUrlLoading(webView, str);
            }
            Toast.makeText(this.f4846b.f3374j, "抱歉,您的网络不可用...", 0).show();
            return true;
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            Log.m3251e(OauthDialog.f3365a, "onReceivedError: " + str2 + "\nerrCode: " + i + " description:" + str);
            if (this.f4846b.f3369e.getVisibility() == 0) {
                this.f4846b.f3369e.setVisibility(8);
            }
            super.onReceivedError(webView, i, str, str2);
            SocializeUtils.safeCloseDialog(this.f4846b);
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.cancel();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (str.contains("?ud_get=")) {
                str = this.f4846b.m3271a(str);
            }
            if (!str.contains("access_key") || !str.contains("access_secret")) {
                super.onPageStarted(webView, str, bitmap);
            } else if (str.contains(this.f4846b.f3373i)) {
                m5007a(str);
            }
        }

        public void onPageFinished(WebView webView, String str) {
            this.f4846b.f3379o.sendEmptyMessage(1);
            super.onPageFinished(webView, str);
            if (this.f4846b.f3371g == 0 && str.contains(this.f4846b.f3373i)) {
                m5007a(str);
            }
        }

        private void m5007a(String str) {
            Log.m3248d(OauthDialog.f3365a, "OauthDialog " + str);
            this.f4846b.f3371g = 1;
            this.f4846b.f3372h = SocializeUtils.parseUrl(str);
            if (this.f4846b.isShowing()) {
                SocializeUtils.safeCloseDialog(this.f4846b);
            }
        }
    }

    public OauthDialog(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        String str = null;
        super(activity, ResContainer.get(activity).style("umeng_socialize_popup_dialog"));
        this.f3374j = activity.getApplicationContext();
        this.f3366b = ResContainer.get(this.f3374j);
        this.f3375k = activity;
        this.f3376l = share_media;
        this.f3378n = new C0984a(uMAuthListener, share_media);
        setOwnerActivity(activity);
        LayoutInflater layoutInflater = (LayoutInflater) this.f3375k.getSystemService("layout_inflater");
        int layout = this.f3366b.layout("umeng_socialize_oauth_dialog");
        int id = this.f3366b.id("umeng_socialize_follow");
        int id2 = this.f3366b.id("umeng_socialize_follow_check");
        this.f3368d = layoutInflater.inflate(layout, null);
        View findViewById = this.f3368d.findViewById(id);
        this.f3370f = (CheckBox) this.f3368d.findViewById(id2);
        int i = (this.f3377m == null || this.f3377m.size() <= 0) ? 0 : 1;
        if (share_media == SHARE_MEDIA.SINA || share_media == SHARE_MEDIA.TENCENT) {
            layout = 1;
        } else {
            layout = 0;
        }
        if (i == 0 || r2 == 0) {
            findViewById.setVisibility(8);
        } else {
            findViewById.setVisibility(0);
        }
        i = this.f3366b.id("progress_bar_parent");
        layout = this.f3366b.id("umeng_socialize_title_bar_leftBt");
        id2 = this.f3366b.id("umeng_socialize_title_bar_rightBt");
        int id3 = this.f3366b.id("umeng_socialize_title_bar_middleTv");
        int id4 = this.f3366b.id("umeng_socialize_titlebar");
        this.f3369e = this.f3368d.findViewById(i);
        this.f3369e.setVisibility(0);
        ((Button) this.f3368d.findViewById(layout)).setOnClickListener(new C1005b(this));
        this.f3368d.findViewById(id2).setVisibility(8);
        TextView textView = (TextView) this.f3368d.findViewById(id3);
        if (share_media.toString().equals("SINA")) {
            str = "微博";
        } else if (share_media.toString().equals("RENREN")) {
            str = "人人网";
        } else if (share_media.toString().equals("DOUBAN")) {
            str = "豆瓣";
        } else if (share_media.toString().equals("TENCENT")) {
            str = "腾讯微博";
        }
        textView.setText("授权" + str);
        m3272a();
        View c1006c = new C1006c(this, this.f3374j, findViewById, this.f3368d.findViewById(id4), SocializeUtils.dip2Px(this.f3374j, 200.0f));
        c1006c.addView(this.f3368d, -1, -1);
        setContentView(c1006c);
        LayoutParams attributes = getWindow().getAttributes();
        if (SocializeUtils.isFloatWindowStyle(this.f3374j)) {
            int[] floatWindowSize = SocializeUtils.getFloatWindowSize(this.f3374j);
            attributes.width = floatWindowSize[0];
            attributes.height = floatWindowSize[1];
            i = this.f3366b.style("umeng_socialize_dialog_anim_fade");
        } else {
            attributes.height = -1;
            attributes.width = -1;
            i = this.f3366b.style("umeng_socialize_dialog_animations");
        }
        attributes.gravity = 17;
        getWindow().getAttributes().windowAnimations = i;
    }

    public void setWaitUrl(String str) {
        this.f3373i = str;
    }

    private String m3269a(SHARE_MEDIA share_media) {
        URLBuilder uRLBuilder = new URLBuilder(this.f3374j);
        uRLBuilder.setHost(SocializeClient.BASE_URL).setPath("share/auth/").setAppkey(SocializeUtils.getAppkey(this.f3374j)).setEntityKey(Config.EntityKey).withMedia(share_media).withOpId("10").withSessionId(Config.SessionId).withUID(Config.UID);
        return uRLBuilder.toEncript();
    }

    private boolean m3272a() {
        this.f3367c = (WebView) this.f3368d.findViewById(this.f3366b.id("webView"));
        this.f3367c.setWebViewClient(m3274b());
        this.f3367c.setWebChromeClient(new C1009f(this));
        this.f3367c.requestFocusFromTouch();
        this.f3367c.setVerticalScrollBarEnabled(false);
        this.f3367c.setHorizontalScrollBarEnabled(false);
        this.f3367c.setScrollBarStyle(0);
        this.f3367c.getSettings().setCacheMode(2);
        WebSettings settings = this.f3367c.getSettings();
        settings.setJavaScriptEnabled(true);
        if (VERSION.SDK_INT >= 8) {
            settings.setPluginState(PluginState.ON);
        }
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        settings.setUseWideViewPort(true);
        if (VERSION.SDK_INT >= 8) {
            settings.setLoadWithOverviewMode(true);
            settings.setDatabaseEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setGeolocationEnabled(true);
            settings.setAppCacheEnabled(true);
        }
        if (VERSION.SDK_INT >= 11) {
            try {
                Method declaredMethod = WebSettings.class.getDeclaredMethod("setDisplayZoomControls", new Class[]{Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(settings, new Object[]{Boolean.valueOf(false)});
            } catch (Exception e) {
            }
        }
        try {
            if (this.f3376l == SHARE_MEDIA.RENREN) {
                CookieSyncManager.createInstance(this.f3374j);
                CookieManager.getInstance().removeAllCookie();
            }
        } catch (Exception e2) {
        }
        return true;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    private WebViewClient m3274b() {
        Object obj = 1;
        Object obj2 = null;
        try {
            if (WebViewClient.class.getMethod("onReceivedSslError", new Class[]{WebView.class, SslErrorHandler.class, SslError.class}) == null) {
                obj = null;
            }
            obj2 = obj;
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e2) {
        }
        if (obj2 != null) {
            Log.m3254i(f3365a, "has method onReceivedSslError : ");
            return new C2017g(this);
        }
        Log.m3254i(f3365a, "has no method onReceivedSslError : ");
        return new C1828b();
    }

    private String m3271a(String str) {
        try {
            String[] split = str.split("ud_get=");
            split[1] = AesHelper.decryptNoPadding(split[1], "UTF-8").trim();
            str = split[0] + split[1];
        } catch (Exception e) {
            Log.m3251e(f3365a, "### AuthWebViewClient解密失败");
            e.printStackTrace();
        }
        return str;
    }

    public void show() {
        super.show();
        this.f3372h = null;
        this.f3367c.loadUrl(m3269a(this.f3376l));
    }

    public void dismiss() {
        if (this.f3372h == null) {
            this.f3378n.m3263a();
        } else if (TextUtils.isEmpty(this.f3372h.getString("uid"))) {
            this.f3378n.m3265a(new SocializeException("unfetch usid..."));
        } else {
            Log.m3248d(f3365a, "### dismiss ");
            this.f3378n.m3264a(this.f3372h);
        }
        super.dismiss();
    }
}
