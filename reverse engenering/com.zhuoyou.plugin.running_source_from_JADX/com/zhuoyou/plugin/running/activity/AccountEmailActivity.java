package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;

public class AccountEmailActivity extends BaseActivity {
    private EditText etCode;
    private EditText etEmail;
    private User user = ((User) DroiUser.getCurrentUser(User.class));

    class C16881 implements DroiCallback<Boolean> {
        C16881() {
        }

        public void result(Boolean aBoolean, DroiError droiError) {
            if (AccountEmailActivity.this.user.isEmailVerified()) {
                Tools.makeToast("邮箱已认证");
                Log.i("zhuqichao", "邮箱已认证");
                return;
            }
            AccountEmailActivity.this.user.refreshValidationStatusInBackground(this);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_account_email);
        initView();
    }

    private void initView() {
        this.etEmail = (EditText) findViewById(C1680R.id.et_email);
        this.etCode = (EditText) findViewById(C1680R.id.et_code);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_ok:
                this.user.setEmail(this.etEmail.getText().toString());
                this.user.save();
                if (this.user.validateEmail().isOk()) {
                    Tools.makeToast("验证码已发送！");
                    this.user.refreshValidationStatusInBackground(new C16881());
                    return;
                }
                Tools.makeToast("验证码发送失败！");
                return;
            default:
                return;
        }
    }
}
