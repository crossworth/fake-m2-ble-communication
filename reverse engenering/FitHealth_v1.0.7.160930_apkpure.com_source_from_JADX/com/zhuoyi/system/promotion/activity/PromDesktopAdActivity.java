package com.zhuoyi.system.promotion.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.zhuoyi.system.network.object.AdInfo;
import com.zhuoyi.system.network.object.Html5Info;
import com.zhuoyi.system.network.util.NetworkUtils;
import com.zhuoyi.system.promotion.util.PromConstants;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.FileUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.ResourceIdUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public class PromDesktopAdActivity extends Activity {
    private static final String TAG = "PromDesktopAdActivity";
    private AdInfo adInfo;
    private int adType = 0;
    private JSONObject h5DownloadInfo;
    private Html5Info html5Info;
    OnClickListener imageAdClickListener = new C10551();
    private Intent intent;
    private ImageView iv_ad;
    private ImageView iv_close;
    private Handler mHandler = new Handler();
    private MediaPlayer mMediaPlayer;
    private RelativeLayout rl_main;
    private TextView tv_desc;
    private TextView tv_title;
    private WebView wv_wap_screen;

    class C10551 implements OnClickListener {
        C10551() {
        }

        public void onClick(View v) {
            Logger.m3373e(PromDesktopAdActivity.TAG, "click image ad.");
            if (PromDesktopAdActivity.this.adInfo != null) {
                StatsPromUtils.getInstance(PromDesktopAdActivity.this.getApplicationContext()).addAdClickAction(PromDesktopAdActivity.this.adInfo.getPackageName(), PromDesktopAdActivity.this.adInfo.getIconId(), 12, 0);
                Intent intent = PromUtils.getInstance(PromDesktopAdActivity.this.getApplicationContext()).clickPromAppInfoListener(PromDesktopAdActivity.this.adInfo.thisToPromAppInfo(), 12);
                if (intent == null) {
                    Logger.m3373e(PromDesktopAdActivity.TAG, "intent is null;");
                } else if (PromDesktopAdActivity.this.adInfo.getActionType() != (byte) 1 || intent.getSerializableExtra(BundleConstants.BUNDLE_APP_INFO) == null) {
                    Logger.m3373e(PromDesktopAdActivity.TAG, "startActivity image ad.");
                    PromDesktopAdActivity.this.startActivity(intent);
                } else {
                    Logger.m3373e(PromDesktopAdActivity.TAG, "startService image ad.");
                    PromDesktopAdActivity.this.startService(intent);
                }
                PromDesktopAdActivity.this.finish();
            }
        }
    }

    class C10563 extends WebChromeClient {
        C10563() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100 && PromDesktopAdActivity.this.wv_wap_screen != null) {
                PromDesktopAdActivity.this.showWebView();
            }
        }

        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }
    }

    class C10574 implements OnClickListener {
        C10574() {
        }

        public void onClick(View v) {
            PromDesktopAdActivity.this.finish();
        }
    }

    class C10585 extends TimerTask {
        C10585() {
        }

        public void run() {
            PromDesktopAdActivity.this.finish();
        }
    }

    class C10596 implements OnClickListener {
        C10596() {
        }

        public void onClick(View v) {
            PromDesktopAdActivity.this.finish();
        }
    }

    public final class MyJavaScriptInterface {

        class C10601 implements Runnable {
            C10601() {
            }

            public void run() {
                PromDesktopAdActivity.this.finish();
            }
        }

        class C10612 implements Runnable {
            C10612() {
            }

            public void run() {
                int infoType = 0;
                try {
                    infoType = PromDesktopAdActivity.this.h5DownloadInfo.getInt("infoType");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (infoType == 1) {
                    PromUtils.getInstance(PromDesktopAdActivity.this.getApplicationContext()).downloadHtml5Apk(PromDesktopAdActivity.this.h5DownloadInfo);
                    PromDesktopAdActivity.this.finish();
                } else if (infoType == 2) {
                    PromUtils.getInstance(PromDesktopAdActivity.this.getApplicationContext()).showWap(PromDesktopAdActivity.this.h5DownloadInfo);
                    PromDesktopAdActivity.this.finish();
                }
            }
        }

        public void finish() {
            PromDesktopAdActivity.this.mHandler.post(new C10601());
        }

        public void onClickEvent() {
            PromDesktopAdActivity.this.addClickAction();
            PromDesktopAdActivity.this.mHandler.post(new C10612());
        }

        public void download(String downloadInfo) {
            Logger.m3373e(PromDesktopAdActivity.TAG, "click download and dowloadInfo = " + downloadInfo);
            onClickEvent();
        }

        public void wap(String wapInfo) {
            Logger.m3373e(PromDesktopAdActivity.TAG, "click html and wapInfo = " + wapInfo);
            onClickEvent();
        }

        public void install(String apkPath) {
            PromDesktopAdActivity.this.addClickAction();
        }
    }

    class ShowImageAdTask extends AsyncTask<Void, Void, Bitmap> {
        ShowImageAdTask() {
        }

        protected Bitmap doInBackground(Void... params) {
            Logger.m3373e(PromDesktopAdActivity.TAG, "doInBackground and url = " + PromDesktopAdActivity.this.adInfo.getUrl());
            File imagePathFile = new File(PromConstants.PROM_AD_IMAGES_PATH);
            if (!imagePathFile.exists()) {
                imagePathFile.mkdirs();
            }
            File f = new File(imagePathFile, PromDesktopAdActivity.this.adInfo.getIconId());
            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            if (bitmap != null) {
                return bitmap;
            }
            try {
                FileUtils.copyStream(new URL(PromDesktopAdActivity.this.adInfo.getUrl()).openStream(), new FileOutputStream(f));
                return BitmapFactory.decodeFile(f.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                Logger.m3373e(PromDesktopAdActivity.TAG, "onPostExecute download bitmap success.");
                PromDesktopAdActivity.this.showAdView(result);
                PromDesktopAdActivity.this.showMusic();
                return;
            }
            Logger.m3373e(PromDesktopAdActivity.TAG, "onPostExecute download bitmap failed.");
            PromDesktopAdActivity.this.finish();
        }
    }

    class C18372 extends WebViewClient {
        C18372() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.replaceAll(" ", "").contains("about:blank")) {
                return false;
            }
            view.loadUrl(url);
            return true;
        }

        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        Logger.m3373e(TAG, "oncreate");
        requestWindowFeature(1);
        getWindow().setFlags(128, 128);
        super.onCreate(savedInstanceState);
        this.intent = getIntent();
        if (this.intent != null) {
            this.adType = this.intent.getIntExtra(BundleConstants.BUNDLE_DESKTOP_AD_TYPE, 0);
        }
        findViews();
        if (this.adType == 1) {
            Logger.m3373e(TAG, "PROM_DESKTOP_AD_TYPE_IMAGE");
            this.adInfo = (AdInfo) this.intent.getSerializableExtra(BundleConstants.BUNDLE_DESKTOP_AD_INFO);
            if (this.adInfo == null) {
                Logger.m3373e(TAG, "adInfo == null");
                finish();
            } else if (NetworkUtils.isNetworkAvailable(getApplicationContext())) {
                new ShowImageAdTask().execute(new Void[0]);
            } else {
                Logger.m3373e(TAG, "Network not available");
                finish();
            }
        } else if (this.adType == 2) {
            Logger.m3373e(TAG, "PROM_DESKTOP_AD_TYPE_HTML5");
            createWebView();
        }
    }

    private void findViews() {
        this.rl_main = new RelativeLayout(this);
        this.rl_main.setLayoutParams(new LayoutParams(-1, -1));
        this.rl_main.setBackgroundResource(17170445);
    }

    private void showMusic() {
        this.mMediaPlayer = new MediaPlayer();
        AudioManager audioManager = (AudioManager) getSystemService("audio");
        Uri alert = RingtoneManager.getDefaultUri(2);
        Logger.m3373e(TAG, "audioManager.getStreamVolume(AudioManager.STREAM_ALARM):" + audioManager.getStreamVolume(4));
        if (alert == null || audioManager.getStreamVolume(1) == 0) {
            Logger.m3373e(TAG, "doesn't show music");
            return;
        }
        try {
            this.mMediaPlayer.setDataSource(this, alert);
            this.mMediaPlayer.setLooping(false);
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.start();
        } catch (Exception e) {
            Logger.m3373e(TAG, e.getMessage());
        }
    }

    private void createWebView() {
        this.html5Info = (Html5Info) this.intent.getSerializableExtra(BundleConstants.BUNDLE_DESKTOP_AD_INFO);
        if (this.html5Info == null) {
            Logger.m3373e(TAG, "html is null");
            finish();
            return;
        }
        try {
            String downloadInfo = this.html5Info.getDownloadInfo();
            if (!TextUtils.isEmpty(downloadInfo)) {
                this.h5DownloadInfo = new JSONObject(downloadInfo);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        showMusic();
        this.wv_wap_screen = new WebView(getApplicationContext());
        this.wv_wap_screen.setBackgroundColor(0);
        this.wv_wap_screen.setScrollContainer(false);
        this.wv_wap_screen.setVerticalScrollBarEnabled(false);
        this.wv_wap_screen.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = this.wv_wap_screen.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setLightTouchEnabled(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.wv_wap_screen.setWebViewClient(new C18372());
        this.wv_wap_screen.setWebChromeClient(new C10563());
        this.wv_wap_screen.addJavascriptInterface(new MyJavaScriptInterface(), "zy");
        try {
            String path = new StringBuilder(String.valueOf(PromUtils.getInstance(getApplicationContext()).getDesktopAdPath())).append("/").append(this.html5Info.getId()).append("/").append(this.html5Info.getId()).append(".html").toString();
            Logger.m3373e(TAG, "path=" + path);
            this.wv_wap_screen.loadData(FileUtils.readFile(new File(path)), "text/html", "UTF-8");
            autoClose(this.html5Info.getRemainTime());
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    private void showWebView() {
        try {
            StatsPromUtils.getInstance(getApplicationContext()).addAdDisplayAction(this.h5DownloadInfo.getString(PromConstants.PROM_HTML5_INFO_PACKAGE_NAME), 0, 11, 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LayoutParams params1 = new LayoutParams(-2, -2);
        params1.addRule(13);
        this.rl_main.addView(this.wv_wap_screen, params1);
        addCloseView();
        setContentView(this.rl_main);
    }

    private void showAdView(Bitmap bitmap) {
        StatsPromUtils.getInstance(getApplicationContext()).addAdDisplayAction(this.adInfo.getPackageName(), this.adInfo.getIconId(), 12, 0);
        if (this.adInfo.getType() == (byte) 1) {
            this.iv_ad = new ImageView(this);
            this.iv_ad.setScaleType(ScaleType.FIT_XY);
            this.iv_ad.setImageBitmap(bitmap);
            this.rl_main.addView(this.iv_ad, -1, -1);
            addCloseView();
        } else {
            RelativeLayout rl = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(ResourceIdUtils.getResourceId(getApplicationContext(), "R.layout.zy_prom_desktop_ad_layout"), null);
            this.tv_title = (TextView) rl.findViewById(ResourceIdUtils.getResourceId(getApplicationContext(), "R.id.zy_tv_title"));
            this.tv_desc = (TextView) rl.findViewById(ResourceIdUtils.getResourceId(getApplicationContext(), "R.id.zy_tv_app_desc"));
            this.iv_ad = (ImageView) rl.findViewById(ResourceIdUtils.getResourceId(getApplicationContext(), "R.id.zy_iv_bg"));
            this.iv_close = (ImageView) rl.findViewById(ResourceIdUtils.getResourceId(getApplicationContext(), "R.id.zy_iv_close"));
            this.tv_title.setText(this.adInfo.getTitle());
            this.tv_desc.setText(this.adInfo.getDesc());
            this.iv_ad.setImageBitmap(bitmap);
            this.iv_close.setOnClickListener(new C10574());
            LayoutParams params1 = new LayoutParams(-2, -2);
            params1.addRule(13);
            this.rl_main.addView(rl, params1);
        }
        Logger.m3373e(TAG, "adInfo.getDesc()=" + this.adInfo.getDesc() + "====adInfo.getIconUrl()" + this.adInfo.getIconUrl());
        this.iv_ad.setOnClickListener(this.imageAdClickListener);
        Logger.m3373e(TAG, "adInfo.getRemainTime() = " + this.adInfo.getRemainTime());
        autoClose(this.adInfo.getRemainTime());
        setContentView(this.rl_main);
    }

    private void autoClose(int remainTime) {
        if (remainTime > 0) {
            new Timer().schedule(new C10585(), (long) (remainTime * 1000));
        }
    }

    private void addCloseView() {
        LayoutParams params1 = new LayoutParams(-2, -2);
        params1.addRule(11);
        params1.addRule(10);
        this.iv_close = new ImageView(this);
        this.iv_close.setImageResource(ResourceIdUtils.getResourceId(getApplicationContext(), "R.drawable.zy_btn_close"));
        this.iv_close.setOnClickListener(new C10596());
        this.rl_main.addView(this.iv_close, params1);
    }

    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
        }
    }

    private void addClickAction() {
        if (this.html5Info != null) {
            StatsPromUtils.getInstance(getApplicationContext()).addAdClickAction(getPackageName(), this.html5Info.getId(), 11, 0);
        }
    }
}
