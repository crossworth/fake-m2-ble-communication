package com.zhuoyou.plugin.running.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.BaasHelper.QQCallback;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;
import org.json.JSONObject;
import twitter4j.BASE64Encoder;

public class QQHealthActivity extends BaseActivity {
    private static final String QQ_PKG_NAME = "com.tencent.mobileqq";
    private static final String QQ_PROFIX = "mqqapi://forward/url?src_type=web&version=1&url_prefix=";
    private static final String QQ_URL = "http://yundong.qq.com/sport/home?_wv=2172899&asyncMode=1&adtag=yundong.outside.zhuoyijiankang";
    private MyAlertDialog dialog;
    private String format = "yyyy-MM-dd  HH:mm:ss";
    private boolean ifUpdating = false;
    private Handler mHandler = new Handler();
    private TextView tvTime;
    private TextView tvUser;

    class C17951 implements OnClickListener {
        C17951() {
        }

        public void onClick(int witch) {
            SPUtils.clearQQInfo(DroiUser.getCurrentUser().getUserId());
            QQHealthActivity.this.finish();
        }
    }

    class C17972 implements QQCallback {

        class C17961 implements Runnable {
            C17961() {
            }

            public void run() {
                QQHealthActivity.this.tvTime.setText(Tools.formatTime(System.currentTimeMillis(), QQHealthActivity.this.format));
            }
        }

        C17972() {
        }

        public void callback(boolean success, int code, JSONObject obj) {
            Log.i("chenxinX", "code:" + code);
            QQHealthActivity.this.ifUpdating = false;
            if (success) {
                QQHealthActivity.this.mHandler.post(new C17961());
            } else if (code == -73) {
                Tools.makeToast((int) C1680R.string.qq_pw_check_fail);
                SPUtils.clearQQInfo(DroiUser.getCurrentUser().getUserId());
                QQHealthActivity.this.finish();
            } else {
                Tools.makeToast(QQHealthActivity.this, (int) C1680R.string.qq_health_update_fail);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_qq_health);
        this.tvTime = (TextView) findViewById(C1680R.id.tv_last_time);
        this.tvUser = (TextView) findViewById(C1680R.id.tv_user_name);
        long time = SPUtils.getUploadQQTime(DroiUser.getCurrentUser().getUserId());
        this.tvUser.setText(SPUtils.getQQuserName(DroiUser.getCurrentUser().getUserId()));
        if (time == 0) {
            this.tvTime.setText("从未同步过");
        } else {
            this.tvTime.setText(Tools.formatTime(time, this.format));
        }
        this.dialog = new MyAlertDialog(this);
        this.dialog.setTitle((int) C1680R.string.qq_unbundle_account);
        this.dialog.setMessage((int) C1680R.string.qq_unbundle_account_messages);
        this.dialog.setLeftButton((int) C1680R.string.qq_health_unbundle_button, new C17951());
        this.dialog.setRightButton((int) C1680R.string.string_cancel, null);
        updateQQData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_unbund:
                this.dialog.show();
                return;
            case C1680R.id.btn_upload:
                updateQQData();
                return;
            case C1680R.id.btn_to_qq:
                String url = QQ_URL;
                if (Tools.isAppInstalled("com.tencent.mobileqq")) {
                    url = QQ_PROFIX + BASE64Encoder.encode(QQ_URL.getBytes());
                }
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return;
            default:
                return;
        }
    }

    private void updateQQData() {
        if (this.ifUpdating) {
            Tools.makeToast((Context) this, (int) C1680R.string.qq_in_update);
            return;
        }
        this.ifUpdating = true;
        BaasHelper.updateQQHealthStep(new C17972());
    }
}
