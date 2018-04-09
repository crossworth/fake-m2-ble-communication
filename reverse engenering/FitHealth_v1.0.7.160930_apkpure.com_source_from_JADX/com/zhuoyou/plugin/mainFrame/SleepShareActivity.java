package com.zhuoyou.plugin.mainFrame;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
import com.zhuoyou.plugin.running.BuildConfig;
import com.zhuoyou.plugin.running.SharePopupWindow;
import com.zhuoyou.plugin.running.SleepItem;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.share.AuthorizeActivity;
import com.zhuoyou.plugin.share.ShareTask;
import com.zhuoyou.plugin.share.ShareToWeixin;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.protocol.HTTP;

public class SleepShareActivity extends Activity implements OnClickListener {
    private static final int TIMELINE_SUPPORTED_VERSION = 553779201;
    public static Handler mHandler = new C13153();
    private static int select = 0;
    private Bitmap bmp = null;
    private boolean isQQInstalled = false;
    private boolean isWBInstalled = false;
    private boolean isWXInstalled = false;
    private SleepItem item;
    private OnClickListener itemsOnClick = new C13111();
    private Context mContext;
    private TextView mDeepSleep;
    private ImageView mMore;
    private SharePopupWindow mPopupWindow;
    private RequestListener mRequestListener = new C18932();
    private ScrollView mScreenshot;
    private ImageView mShare_qq;
    private ImageView mShare_quan;
    private ImageView mShare_weixin;
    private TextView mSleepDate;
    private TextView mSleepHour;
    private TextView mSleepMin;
    private ImageView mUser_logo;
    private TextView mUser_name;
    private TextView mWakeSleep;
    private Resources res;
    private UMShareListener umShareListener = new C18944();
    private Weibo weibo = Weibo.getInstance();

    class C13111 implements OnClickListener {
        C13111() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.share_weibo:
                    if (SleepShareActivity.this.weibo.isSessionValid()) {
                        SleepShareActivity.select = 1;
                        SharePopupWindow.mInstance.getWeiboView().setImageResource(R.drawable.share_wb_select);
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClass(SleepShareActivity.this, AuthorizeActivity.class);
                    SleepShareActivity.this.startActivity(intent);
                    return;
                case R.id.share:
                    if (SleepShareActivity.select <= 0) {
                        Toast.makeText(SleepShareActivity.this.mContext, R.string.select_platform, 0).show();
                        return;
                    } else if (SharePopupWindow.mInstance != null) {
                        SleepShareActivity.this.share2weibo(SharePopupWindow.mInstance.getShareContent(), Tools.getScreenShot(SharePopupWindow.mInstance.getShareFileName()));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    static class C13153 extends Handler {
        C13153() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SleepShareActivity.select = 1;
                    return;
                default:
                    return;
            }
        }
    }

    class C18932 implements RequestListener {

        class C13121 implements Runnable {
            C13121() {
            }

            public void run() {
                Toast.makeText(SleepShareActivity.this, R.string.share_success, 0).show();
            }
        }

        C18932() {
        }

        public void onComplete(String response) {
            SleepShareActivity.this.runOnUiThread(new C13121());
        }

        public void onError(final WeiboException e) {
            SleepShareActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(SleepShareActivity.this, e.getStatusMessage(), 0).show();
                }
            });
        }

        public void onIOException(final IOException e) {
            SleepShareActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(SleepShareActivity.this, e.getMessage(), 0).show();
                }
            });
        }
    }

    class C18944 implements UMShareListener {
        C18944() {
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
        setContentView(R.layout.sleepshare_layout);
        this.mContext = this;
        this.item = (SleepItem) getIntent().getSerializableExtra("item");
        getShareAppStatus();
        initView();
        initData();
    }

    public void initData() {
        int headIndex = Tools.getHead(this);
        if (headIndex == 10000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/custom");
            this.mUser_logo.setImageBitmap(this.bmp);
        } else if (headIndex == 1000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
            this.mUser_logo.setImageBitmap(this.bmp);
        } else {
            this.mUser_logo.setImageResource(Tools.selectByIndex(headIndex));
        }
        if (!Tools.getUsrName(this).equals("")) {
            this.mUser_name.setText(Tools.getUsrName(this));
        } else if (Tools.getLoginName(this).equals("")) {
            this.mUser_name.setText(R.string.username);
        } else {
            this.mUser_name.setText(Tools.getLoginName(this));
        }
        int sleepHour = 0;
        int sleepMin = 0;
        if (this.item != null) {
            sleepHour = this.item.getmSleepT() / 3600;
            sleepMin = (this.item.getmSleepT() % 3600) / 60;
            int deepMin = (this.item.getmDSleepT() % 3600) / 60;
            int lightHour = this.item.getmWSleepT() / 3600;
            int lightMin = (this.item.getmWSleepT() % 3600) / 60;
            this.mDeepSleep.setText(this.mContext.getString(R.string.share_deep) + " " + (this.item.getmDSleepT() / 3600) + this.mContext.getString(R.string.hour) + deepMin + this.mContext.getString(R.string.sleep_minutes) + "，" + this.mContext.getString(R.string.share_light) + " " + lightHour + this.mContext.getString(R.string.hour) + lightMin + this.mContext.getString(R.string.sleep_minutes));
            this.mWakeSleep.setText(this.mContext.getString(R.string.gosleep) + " " + this.item.getmStartT() + "，" + this.mContext.getString(R.string.wakeup) + " " + this.item.getmEndT());
        }
        this.mSleepHour.setText("" + sleepHour);
        this.mSleepMin.setText("" + sleepMin);
        this.mSleepDate.setText((this.item.getEndCal().get(2) + 1) + this.mContext.getString(R.string.pop_mouth) + this.item.getEndCal().get(5) + this.mContext.getString(R.string.pop_date));
    }

    private void initView() {
        this.mScreenshot = (ScrollView) findViewById(R.id.screenshot);
        this.mUser_logo = (ImageView) findViewById(R.id.user_logo);
        this.mUser_name = (TextView) findViewById(R.id.user_name);
        this.mSleepHour = (TextView) findViewById(R.id.sleep_duration_hour);
        this.mSleepMin = (TextView) findViewById(R.id.sleep_duration_min);
        this.mSleepDate = (TextView) findViewById(R.id.sleep_date);
        this.mDeepSleep = (TextView) findViewById(R.id.sleep_deeptime);
        this.mWakeSleep = (TextView) findViewById(R.id.sleep_wakesleep_time);
        this.mShare_weixin = (ImageView) findViewById(R.id.share_weixin);
        this.mShare_weixin.setOnClickListener(this);
        this.mShare_quan = (ImageView) findViewById(R.id.share_quan);
        this.mShare_quan.setOnClickListener(this);
        this.mShare_qq = (ImageView) findViewById(R.id.share_qq);
        this.mShare_qq.setOnClickListener(this);
        this.mMore = (ImageView) findViewById(R.id.share_more);
        this.mMore.setOnClickListener(this);
        this.res = getResources();
    }

    private void getScreenShot(String fileName) {
        int h = 0;
        for (int i = 0; i < this.mScreenshot.getChildCount(); i++) {
            h += this.mScreenshot.getChildAt(i).getHeight();
        }
        Bitmap bmp = Bitmap.createBitmap(this.mScreenshot.getWidth(), h, Config.RGB_565);
        this.mScreenshot.draw(new Canvas(bmp));
        Tools.saveBitmapToFile(bmp, fileName);
        bmp.recycle();
    }

    public void onClick(View v) {
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        switch (v.getId()) {
            case R.id.back_m:
                finish();
                return;
            case R.id.share_weixin:
                getScreenShot(fileName);
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.FACEBOOK).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                } else if (!this.isWXInstalled) {
                    Toast.makeText(this.mContext, R.string.install_weixin, 0).show();
                    return;
                } else if (ShareToWeixin.api.isWXAppSupportAPI()) {
                    ShareToWeixin.SharetoWX(this.mContext, false, fileName);
                    return;
                } else {
                    Toast.makeText(this.mContext, R.string.weixin_no_support, 0).show();
                    return;
                }
            case R.id.share_quan:
                getScreenShot(fileName);
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
                    Toast.makeText(this.mContext, R.string.install_weixin, 0).show();
                    return;
                } else if (ShareToWeixin.api.getWXAppSupportAPI() >= 553779201) {
                    ShareToWeixin.SharetoWX(this.mContext, true, fileName);
                    return;
                } else {
                    Toast.makeText(this.mContext, R.string.weixin_no_support_quan, 0).show();
                    return;
                }
            case R.id.share_qq:
                getScreenShot(fileName);
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.WHATSAPP).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                } else if (this.isQQInstalled) {
                    shareToQQ(fileName);
                    return;
                } else {
                    Toast.makeText(this.mContext, R.string.install_qq, 0).show();
                    return;
                }
            case R.id.share_more:
                getScreenShot(fileName);
                if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.INSTAGRAM).withTitle(getResources().getString(R.string.app_name)).withMedia(new UMImage((Context) this, new File(Tools.getScreenShot(fileName)))).setCallback(this.umShareListener).share();
                    return;
                }
                this.mPopupWindow = new SharePopupWindow(this, this.itemsOnClick, fileName);
                this.mPopupWindow.setInputMethodMode(1);
                this.mPopupWindow.setSoftInputMode(16);
                this.mPopupWindow.showAtLocation(findViewById(R.id.main), 81, 0, 0);
                return;
            default:
                return;
        }
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

    protected void onDestroy() {
        super.onDestroy();
        if (this.bmp != null) {
            this.bmp.recycle();
            this.bmp = null;
            System.gc();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
