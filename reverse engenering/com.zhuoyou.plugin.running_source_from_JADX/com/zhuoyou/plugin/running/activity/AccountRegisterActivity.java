package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.AccountLoginActivity.EventFinish;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import org.greenrobot.eventbus.EventBus;

public class AccountRegisterActivity extends BaseActivity {
    public static final String KEY_REGIST_NAME = "key_regist_name";
    private ProgressDialog dialog;
    private EditText etName;
    private EditText etPasswd;
    private EditText etPasswdAgain;

    class C16911 implements DroiCallback<Boolean> {
        C16911() {
        }

        public void result(Boolean aBoolean, DroiError droiError) {
            Tools.hideProgressDialog(AccountRegisterActivity.this.dialog);
            if (droiError.isOk()) {
                SPUtils.setSyncedData(true);
                EventBus.getDefault().post(new EventFinish());
                AccountRegisterActivity.this.startActivity(new Intent(AccountRegisterActivity.this, MainActivity.class));
                AccountRegisterActivity.this.finish();
                return;
            }
            Tools.makeToast(Tools.getDroiError(droiError));
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_account_register);
        this.dialog = Tools.getProgressDialog(this);
        initView();
    }

    private void initView() {
        this.etName = (EditText) findViewById(C1680R.id.user_name);
        this.etPasswd = (EditText) findViewById(C1680R.id.user_password);
        this.etPasswdAgain = (EditText) findViewById(C1680R.id.user_password_again);
        this.etName.setText(getIntent().getStringExtra(KEY_REGIST_NAME));
        this.etName.setSelection(this.etName.getText().length());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_register:
                regist();
                return;
            default:
                return;
        }
    }

    private void regist() {
        String name = this.etName.getText().toString();
        String passwd = this.etPasswd.getText().toString();
        String passwdAgain = this.etPasswdAgain.getText().toString();
        if (name.length() < 6) {
            this.etName.requestFocus();
            this.etName.setError(getString(C1680R.string.account_name_is_short));
        } else if (passwd.length() < 6) {
            this.etPasswd.requestFocus();
            this.etPasswd.setError(getString(C1680R.string.account_password_is_short));
        } else if (passwd.equals(passwdAgain)) {
            User user = new User();
            user.setUserId(name);
            user.setPassword(passwd);
            Tools.showProgressDialog(this.dialog, getString(C1680R.string.account_registing));
            user.signUpInBackground(new C16911());
        } else {
            this.etPasswdAgain.requestFocus();
            this.etPasswdAgain.setError(getString(C1680R.string.account_password_is_different));
        }
    }
}
