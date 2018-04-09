package com.zhuoyi.system.promotion.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.ResourceIdUtils;

public class PromHomeWapScreenActivity extends Activity {
    public static final String TAG = "PromHomeWapScreenActivity";
    private LinearLayout ll_general_loading;
    private int position;
    private PromAppInfo promAppInfo;
    private int source;
    private long startActivityTime;
    private WebView wv_wap_screen;

    class C10622 extends WebChromeClient {
        C10622() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                PromHomeWapScreenActivity.this.ll_general_loading.setVisibility(8);
            }
        }
    }

    class C18381 extends WebViewClient {
        C18381() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.replaceAll(" ", "").contains("about:blank")) {
                return false;
            }
            view.loadUrl(url);
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ResourceIdUtils.getResourceId(getApplicationContext(), "R.layout.zy_prom_home_wap_screen_activity"));
        this.startActivityTime = System.currentTimeMillis();
        Intent i = getIntent();
        if (i != null) {
            this.promAppInfo = (PromAppInfo) i.getSerializableExtra(BundleConstants.BUNDLE_APP_INFO);
            this.position = getIntent().getIntExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, 1);
            this.source = getIntent().getIntExtra(BundleConstants.BUNDLE_APP_INFO_SOURCE, 0);
        }
        if (this.promAppInfo == null) {
            finish();
            return;
        }
        if (this.position == 3) {
            StatsPromUtils.getInstance(getApplicationContext()).addAdClickAction(this.promAppInfo.getPackageName(), this.promAppInfo.getIconId(), this.position, this.source);
        }
        findViews();
        initViews();
        loadData();
    }

    private void findViews() {
        this.wv_wap_screen = (WebView) findViewById(ResourceIdUtils.getResourceId(getApplicationContext(), "R.id.zy_wv_wap_screen"));
        this.ll_general_loading = (LinearLayout) findViewById(ResourceIdUtils.getResourceId(getApplicationContext(), "R.id.zy_ll_general_loading"));
    }

    private void initViews() {
        WebSettings webSettings = this.wv_wap_screen.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        this.wv_wap_screen.setWebViewClient(new C18381());
        this.wv_wap_screen.setWebChromeClient(new C10622());
    }

    protected void loadData() {
        this.wv_wap_screen.loadUrl(this.promAppInfo.getAction());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !this.wv_wap_screen.canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        this.wv_wap_screen.goBack();
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            StatsPromUtils.getInstance(getApplicationContext()).addPromWapDisplayAction(this.promAppInfo.getPackageName(), System.currentTimeMillis() - this.startActivityTime, this.position, this.source);
        } catch (Exception e) {
        }
    }
}
