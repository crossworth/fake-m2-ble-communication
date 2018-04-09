package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.app.Permissions;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.CloudAPI.Response;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.SplashConfig;
import com.zhuoyou.plugin.running.tools.AnimUtils;
import com.zhuoyou.plugin.running.tools.JsonUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.StatusBarUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import java.util.List;

public class SplashActivity extends BaseActivity {
    private static final int MSG_TO_LOGIN = 4097;
    private static final int MSG_TO_START_ANIM = 4098;
    private static final int REQUEST_CODE_LOGIN = 4099;
    private static final int REQUEST_CODE_PERMISSION = 4098;
    private static final int TIME_SHOW_CUSTON_SPLASH = 3000;
    private static final int TIME_TO_LOGIN = 1500;
    private static final int TIME_TO_START_ANIM = 1000;
    private SplashConfig config;
    private ImageView customSplash;
    private ProgressDialog dialog;
    private ImageView imgSplash;
    private Handler mHandler = new C18132();

    class C18121 implements DroiCallback<Response> {
        C18121() {
        }

        public void result(Response response, DroiError droiError) {
            if (!droiError.isOk() || response == null) {
                Log.i("zhuqichao", "获取闪屏失败：error=" + droiError);
                return;
            }
            SplashActivity.this.config = JsonUtils.getSplashConfig(response.result);
            ImageLoader.getInstance().loadImage(SplashActivity.this.config.imgUrl, Tools.getImageOptions(), null);
        }
    }

    class C18132 extends Handler {
        C18132() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4097:
                    SplashActivity.this.gotoMain();
                    return;
                case 4098:
                    if (SplashActivity.this.config == null || TextUtils.isEmpty(SplashActivity.this.config.imgUrl) || !ImageLoader.getInstance().getDiskCache().get(SplashActivity.this.config.imgUrl).exists()) {
                        SplashActivity.this.imgSplash.setVisibility(0);
                        AnimUtils.playAnimList(SplashActivity.this.imgSplash.getDrawable());
                        sendEmptyMessageDelayed(4097, 1500);
                        return;
                    }
                    SplashActivity.this.customSplash.setVisibility(0);
                    Tools.displayImage(SplashActivity.this.customSplash, SplashActivity.this.config.imgUrl, Tools.getImageOptionsAnim());
                    sendEmptyMessageDelayed(4097, SplashActivity.this.config.showTime > 0 ? (long) SplashActivity.this.config.showTime : 3000);
                    return;
                default:
                    return;
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(1024);
        getWindow().getDecorView().setSystemUiVisibility(2);
        StatusBarUtils.StatusBarLightMode(this);
        StatusBarUtils.transparencyBar(this, true);
        setContentView((int) C1680R.layout.activity_splash);
        this.dialog = Tools.getProgressDialog(this);
        this.imgSplash = (ImageView) findViewById(C1680R.id.img_splash);
        this.customSplash = (ImageView) findViewById(C1680R.id.custom_splash);
        BaasHelper.getSplashConfig(new C18121());
        gotoMainWithPermission();
    }

    public void onClick(View view) {
        Log.i("zhuqichao", "Splash.onClick()");
        switch (view.getId()) {
            case C1680R.id.custom_splash:
                if (this.config != null && SPUtils.isSyncedData() && this.config.action == 1 && !TextUtils.isEmpty(this.config.url)) {
                    this.mHandler.removeMessages(4097);
                    gotoMain();
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.config.url)));
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void gotoMainWithPermission() {
        List<String> list = Permissions.getPermissionList(this);
        if (list.size() > 0) {
            String[] array = new String[list.size()];
            list.toArray(array);
            PermissionActivity.startActivityForResult(this, 4098, array);
        } else if (getIntent().getBooleanExtra("from_logout", false)) {
            gotoMain();
        } else {
            this.mHandler.sendEmptyMessageDelayed(4098, 1000);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 4098:
                if (resultCode == -1) {
                    TheApp.getInstance().initBaaS();
                    this.mHandler.sendEmptyMessageDelayed(4098, 1000);
                    return;
                }
                finish();
                return;
            default:
                return;
        }
    }

    private void gotoMain() {
        getWindow().addFlags(2048);
        DroiUser user = DroiUser.getCurrentUser();
        Intent intent;
        if (user == null || !user.isAuthorized() || user.isAnonymous()) {
            Tools.clearUserData();
            intent = new Intent(this, AccountLoginActivity.class);
            if (!(user == null || user.isAnonymous())) {
                intent.putExtra(AccountLoginActivity.KEY_LOGIN_NAME, user.getUserId());
            }
            startActivity(intent);
            finish();
        } else if (SPUtils.isSyncedData()) {
            intent = new Intent(this, MainActivity.class);
            intent.putExtras(getIntent());
            intent.setFlags(67108864);
            String accesstoken = getIntent().getStringExtra("accesstoken");
            int accesstokenexpiretime = getIntent().getIntExtra("accesstokenexpiretime", -1);
            String openid = getIntent().getStringExtra("openid");
            String from = getIntent().getStringExtra("from");
            startActivity(intent);
            finish();
        } else {
            Tools.getDataAndGotoMain(this, this.dialog);
        }
    }

    public void onBackPressed() {
    }

    protected void onDestroy() {
        super.onDestroy();
        this.imgSplash.setImageResource(0);
        System.gc();
    }
}
