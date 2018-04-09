package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

public class ShareActivity extends Activity implements OnClickListener {
    private static final int TIMELINE_SUPPORTED_VERSION = 553779201;
    public static Handler mHandler = new C14113();
    private static int select = 0;
    private Bitmap bmp = null;
    private int cal;
    private String data;
    private float distance;
    private String food;
    private boolean isQQInstalled = false;
    private boolean isWBInstalled = false;
    private boolean isWXInstalled = false;
    private OnClickListener itemsOnClick = new C14071();
    private Context mContext;
    private TextView mData;
    private ImageView mMore;
    private Typeface mNumberTP;
    private PersonalGoal mPersonalGoal;
    private SharePopupWindow mPopupWindow;
    private RequestListener mRequestListener = new C19002();
    private ScrollView mScreenshot;
    private TextView mShare_cal;
    private TextView mShare_distance;
    private TextView mShare_food;
    private ImageView mShare_qq;
    private ImageView mShare_quan;
    private TextView mShare_step;
    private ImageView mShare_weixin;
    private ImageView mTargetState;
    private ImageView mUser_logo;
    private TextView mUser_name;
    private int step;
    private UMShareListener umShareListener = new C19014();
    private Weibo weibo = Weibo.getInstance();

    class C14071 implements OnClickListener {
        C14071() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.share_weibo:
                    if (ShareActivity.this.weibo.isSessionValid()) {
                        ShareActivity.select = 1;
                        SharePopupWindow.mInstance.getWeiboView().setImageResource(R.drawable.share_wb_select);
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClass(ShareActivity.this, AuthorizeActivity.class);
                    ShareActivity.this.startActivity(intent);
                    return;
                case R.id.share:
                    if (ShareActivity.select <= 0) {
                        Toast.makeText(ShareActivity.this.mContext, R.string.select_platform, 0).show();
                        return;
                    } else if (SharePopupWindow.mInstance != null) {
                        ShareActivity.this.share2weibo(SharePopupWindow.mInstance.getShareContent(), Tools.getScreenShot(SharePopupWindow.mInstance.getShareFileName()));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    static class C14113 extends Handler {
        C14113() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ShareActivity.select = 1;
                    return;
                default:
                    return;
            }
        }
    }

    class C19002 implements RequestListener {

        class C14081 implements Runnable {
            C14081() {
            }

            public void run() {
                Toast.makeText(ShareActivity.this, R.string.share_success, 0).show();
            }
        }

        C19002() {
        }

        public void onComplete(String response) {
            ShareActivity.this.runOnUiThread(new C14081());
        }

        public void onError(final WeiboException e) {
            ShareActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(ShareActivity.this, e.getStatusMessage(), 0).show();
                }
            });
        }

        public void onIOException(final IOException e) {
            ShareActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(ShareActivity.this, e.getMessage(), 0).show();
                }
            });
        }
    }

    class C19014 implements UMShareListener {
        C19014() {
        }

        public void onResult(SHARE_MEDIA platform) {
        }

        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.i("chenxin", "onError");
        }

        public void onCancel(SHARE_MEDIA platform) {
            Log.i("chenxin", "onCancel");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        this.mContext = this;
        this.mNumberTP = RunningApp.getCustomNumberFont();
        Intent intent = getIntent();
        if (intent != null) {
            Log.i("gchk", intent.getIntExtra("steps", 0) + "");
            Log.i("gchk", intent.getIntExtra("cals", 0) + "");
            Log.i("gchk", intent.getFloatExtra("km", 0.0f) + "");
            Log.i("gchk", intent.getStringExtra("date"));
            this.data = intent.getStringExtra("date");
            this.step = intent.getIntExtra("steps", 0);
            this.cal = intent.getIntExtra("cals", 0);
            this.distance = intent.getFloatExtra("km", 0.0f);
        }
        getShareAppStatus();
        initData();
        initView();
    }

    public void initData() {
        this.mPersonalGoal = Tools.getPersonalGoal();
    }

    private void initView() {
        this.mScreenshot = (ScrollView) findViewById(R.id.screenshot);
        this.mUser_logo = (ImageView) findViewById(R.id.user_logo);
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
        this.mUser_name = (TextView) findViewById(R.id.user_name);
        if (!Tools.getUsrName(this).equals("")) {
            this.mUser_name.setText(Tools.getUsrName(this));
        } else if (Tools.getLoginName(this).equals("")) {
            this.mUser_name.setText(R.string.username);
        } else {
            this.mUser_name.setText(Tools.getLoginName(this));
        }
        this.mTargetState = (ImageView) findViewById(R.id.target_state);
        if (this.mPersonalGoal.mGoalSteps <= this.step) {
            this.mTargetState.setImageResource(R.drawable.share_complete_target);
        } else {
            this.mTargetState.setImageResource(R.drawable.share_no_complete_target);
        }
        this.mData = (TextView) findViewById(R.id.data);
        this.mData.setText(this.data);
        this.mShare_step = (TextView) findViewById(R.id.share_step);
        this.mShare_step.setText(this.step + "");
        this.mShare_step.setTypeface(this.mNumberTP);
        this.mShare_distance = (TextView) findViewById(R.id.share_distance);
        this.mShare_distance.setText(getResources().getString(R.string.walk) + " " + String.valueOf(this.distance) + " " + getResources().getString(R.string.kilometre));
        this.mShare_cal = (TextView) findViewById(R.id.share_cal);
        this.mShare_cal.setText(this.cal + "");
        this.mShare_cal.setTypeface(this.mNumberTP);
        this.mShare_food = (TextView) findViewById(R.id.share_food);
        this.mShare_food.setText("≈" + CalTools.getResultFromCal(this.mContext, this.cal));
        this.mShare_weixin = (ImageView) findViewById(R.id.share_weixin);
        this.mShare_weixin.setOnClickListener(this);
        this.mShare_quan = (ImageView) findViewById(R.id.share_quan);
        this.mShare_quan.setOnClickListener(this);
        this.mShare_qq = (ImageView) findViewById(R.id.share_qq);
        this.mShare_qq.setOnClickListener(this);
        this.mMore = (ImageView) findViewById(R.id.share_more);
        this.mMore.setOnClickListener(this);
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
                        getScreenShot(fileName);
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
            case R.id.img_back:
                finish();
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
