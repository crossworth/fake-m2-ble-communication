package com.zhuoyou.plugin.add;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.BuildConfig;
import com.zhuoyou.plugin.running.SharePopupWindow;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.share.AuthorizeActivity;
import com.zhuoyou.plugin.share.ShareTask;
import com.zhuoyou.plugin.share.ShareToWeixin;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.protocol.HTTP;

public class HeartRateShareActivity extends Activity implements OnClickListener {
    private static final int TIMELINE_SUPPORTED_VERSION = 553779201;
    private static int select = 0;
    private long clickStime = 0;
    private ImageView close;
    private String count;
    private String date;
    private boolean isQQInstalled = false;
    private boolean isWBInstalled = false;
    private boolean isWXInstalled = false;
    private OnClickListener itemsOnClick = new C11271();
    private SharePopupWindow mPopupWindow;
    private RequestListener mRequestListener = new C18702();
    private RelativeLayout share_content;
    private TextView share_count;
    private TextView share_date;
    private ImageView share_more;
    private ImageView share_qq;
    private ImageView share_quan;
    private TextView share_time;
    private ImageView share_weixin;
    private String time;
    private UMShareListener umShareListener = new C18713();
    private Weibo weibo = Weibo.getInstance();

    class C11271 implements OnClickListener {
        C11271() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.share_weibo:
                    if (HeartRateShareActivity.this.weibo.isSessionValid()) {
                        HeartRateShareActivity.select = 1;
                        SharePopupWindow.mInstance.getWeiboView().setImageResource(R.drawable.share_wb_select);
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClass(HeartRateShareActivity.this, AuthorizeActivity.class);
                    HeartRateShareActivity.this.startActivity(intent);
                    return;
                case R.id.share:
                    if (HeartRateShareActivity.select <= 0) {
                        Tools.makeToast(HeartRateShareActivity.this.getResources().getString(R.string.select_platform));
                        return;
                    } else if (SharePopupWindow.mInstance != null) {
                        HeartRateShareActivity.this.share2weibo(SharePopupWindow.mInstance.getShareContent(), Tools.getScreenShot(SharePopupWindow.mInstance.getShareFileName()));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    class C18702 implements RequestListener {

        class C11281 implements Runnable {
            C11281() {
            }

            public void run() {
                Tools.makeToast(HeartRateShareActivity.this.getResources().getString(R.string.share_success));
            }
        }

        C18702() {
        }

        public void onComplete(String response) {
            HeartRateShareActivity.this.runOnUiThread(new C11281());
        }

        public void onError(final WeiboException e) {
            HeartRateShareActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Tools.makeToast(e.getStatusMessage());
                }
            });
        }

        public void onIOException(final IOException e) {
            HeartRateShareActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Tools.makeToast(e.getMessage());
                }
            });
        }
    }

    class C18713 implements UMShareListener {
        C18713() {
        }

        public void onResult(SHARE_MEDIA platform) {
        }

        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        public void onCancel(SHARE_MEDIA platform) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heartrate_share_layout);
        this.count = getIntent().getStringExtra(DataBaseContants.HEART_RATE_COUNT);
        this.date = getIntent().getStringExtra("heart_rate_date");
        this.time = getIntent().getStringExtra(DataBaseContants.HEART_RATE_TIME);
        initView();
        getShareAppStatus();
    }

    private void initView() {
        this.share_content = (RelativeLayout) findViewById(R.id.share_content);
        this.close = (ImageView) findViewById(R.id.close);
        this.close.setOnClickListener(this);
        this.share_count = (TextView) findViewById(R.id.share_count);
        this.share_date = (TextView) findViewById(R.id.share_date);
        this.share_time = (TextView) findViewById(R.id.share_time);
        this.share_weixin = (ImageView) findViewById(R.id.share_weixin);
        this.share_weixin.setOnClickListener(this);
        this.share_quan = (ImageView) findViewById(R.id.share_quan);
        this.share_quan.setOnClickListener(this);
        this.share_qq = (ImageView) findViewById(R.id.share_qq);
        this.share_qq.setOnClickListener(this);
        this.share_more = (ImageView) findViewById(R.id.share_more);
        this.share_more.setOnClickListener(this);
        this.share_count.setText(this.count);
        this.share_date.setText(this.date);
        this.share_time.setText(this.time);
    }

    public void onClick(View v) {
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + ".jpg";
        switch (v.getId()) {
            case R.id.share_weixin:
                this.close.setVisibility(8);
                getScreenShot(this.share_content, fileName);
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.FACEBOOK).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                } else if (!this.isWXInstalled) {
                    Tools.makeToast(getResources().getString(R.string.install_weixin));
                    return;
                } else if (ShareToWeixin.api.isWXAppSupportAPI()) {
                    ShareToWeixin.SharetoWX(this, false, fileName);
                    return;
                } else {
                    Tools.makeToast(getResources().getString(R.string.weixin_no_support));
                    return;
                }
            case R.id.share_quan:
                this.close.setVisibility(8);
                getScreenShot(this.share_content, fileName);
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    try {
                        getPackageManager().getPackageInfo("com.twitter.android", 0);
                        Uri uri = Uri.fromFile(new File(Tools.getScreenShot(fileName)));
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                        intent.setType("image/jpeg");
                        intent.putExtra("android.intent.extra.STREAM", uri);
                        startActivity(intent);
                        return;
                    } catch (Exception e) {
                        Toast.makeText(this, "Please install twitter client", 1).show();
                        e.printStackTrace();
                        return;
                    }
                } else if (!this.isWXInstalled) {
                    Tools.makeToast(getResources().getString(R.string.install_weixin));
                    return;
                } else if (ShareToWeixin.api.getWXAppSupportAPI() >= 553779201) {
                    ShareToWeixin.SharetoWX(this, true, fileName);
                    return;
                } else {
                    Tools.makeToast(getResources().getString(R.string.weixin_no_support_quan));
                    return;
                }
            case R.id.share_qq:
                this.close.setVisibility(8);
                getScreenShot(this.share_content, fileName);
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.WHATSAPP).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                } else if (this.isQQInstalled) {
                    shareToQQ(fileName);
                    return;
                } else {
                    Tools.makeToast(getResources().getString(R.string.install_qq));
                    return;
                }
            case R.id.share_more:
                this.close.setVisibility(8);
                getScreenShot(this.share_content, fileName);
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.INSTAGRAM).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                } else if (System.currentTimeMillis() - this.clickStime > 1000) {
                    this.clickStime = System.currentTimeMillis();
                    this.mPopupWindow = new SharePopupWindow(this, this.itemsOnClick, fileName);
                    this.mPopupWindow.setInputMethodMode(1);
                    this.mPopupWindow.setSoftInputMode(16);
                    this.mPopupWindow.showAtLocation(findViewById(R.id.main), 81, 0, 0);
                    return;
                } else {
                    return;
                }
            case R.id.close:
                finish();
                return;
            default:
                return;
        }
    }

    private void getScreenShot(View mScreenshot, String fileName) {
        int h = mScreenshot.getHeight();
        if (mScreenshot instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) mScreenshot;
            h = 0;
            for (int i = 0; i < scrollView.getChildCount(); i++) {
                h += scrollView.getChildAt(i).getHeight();
            }
        }
        Bitmap bmp = Bitmap.createBitmap(mScreenshot.getWidth(), h, Config.RGB_565);
        mScreenshot.draw(new Canvas(bmp));
        Tools.saveBitmapToFile(bmp, fileName);
        bmp.recycle();
    }

    private void getShareAppStatus() {
        PackageManager pm = getPackageManager();
        Intent filterIntent = new Intent("android.intent.action.SEND", null);
        filterIntent.addCategory("android.intent.category.DEFAULT");
        filterIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        List<ResolveInfo> resolveInfos = new ArrayList();
        resolveInfos.addAll(pm.queryIntentActivities(filterIntent, 0));
        for (int i = 0; i < resolveInfos.size(); i++) {
            String mPackageName = ((ResolveInfo) resolveInfos.get(i)).activityInfo.packageName;
            if (mPackageName.equals(WXApp.WXAPP_PACKAGE_NAME)) {
                this.isWXInstalled = true;
            }
            if (mPackageName.equals("com.sina.weibo")) {
                this.isWBInstalled = true;
            }
            if (mPackageName.equals(Constants.MOBILEQQ_PACKAGE_NAME)) {
                this.isQQInstalled = true;
            }
        }
    }

    private void shareToQQ(String fileName) {
        ComponentName cp = new ComponentName(Constants.MOBILEQQ_PACKAGE_NAME, "com.tencent.mobileqq.activity.JumpActivity");
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setComponent(cp);
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(Tools.getScreenShot(fileName))));
        startActivity(intent);
    }

    private void share2weibo(String content, String picpath) {
        new Thread(new ShareTask(this, picpath, content, this.mRequestListener)).start();
        if (this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
    }
}
