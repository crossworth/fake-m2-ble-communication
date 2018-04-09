package com.zhuoyou.plugin.running.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.droi.sdk.core.DroiUser;
import com.tencent.tauth.Tencent;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.tools.Tools.MyIUListener;
import com.zhuoyou.plugin.running.tools.Tools.QQLoginCallback;

public class QQBandActivity extends BaseActivity {
    private MyIUListener listener;
    private Tencent mTencent;

    class C17941 implements QQLoginCallback {
        C17941() {
        }

        public void callback(boolean loginSuccess, int code) {
            Log.i("chenxinX", "QQLoginCallback:" + loginSuccess + " code:" + code + " userName:" + SPUtils.getQQuserName(DroiUser.getCurrentUser().getUserId()));
            if (loginSuccess) {
                Intent intent = new Intent(QQBandActivity.this, QQHealthActivity.class);
                intent.setFlags(67108864);
                QQBandActivity.this.startActivity(intent);
                QQBandActivity.this.finish();
                return;
            }
            Tools.makeToast(QQBandActivity.this, (int) C1680R.string.qq_health_login_fail);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_qq_band);
        this.mTencent = Tencent.createInstance(TheApp.QQ_APPID, this);
        this.listener = new MyIUListener(new C17941(), this.mTencent, this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, this.listener);
        if (resultCode == -1) {
            Tencent.handleResultData(data, this.listener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.bt_qq_band:
                this.mTencent.login((Activity) this, "all", this.listener);
                return;
            default:
                return;
        }
    }
}
