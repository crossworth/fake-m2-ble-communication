package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;

public class AccountEditPwdActivity extends BaseActivity {
    private ProgressDialog dialog;
    private EditText etNewPaaswd;
    private EditText etNewPaaswdAgain;
    private EditText etOldPaaswd;

    class C16871 implements DroiCallback<Boolean> {
        C16871() {
        }

        public void result(Boolean aBoolean, DroiError droiError) {
            Tools.hideProgressDialog(AccountEditPwdActivity.this.dialog);
            if (droiError.isOk()) {
                Tools.makeToast((int) C1680R.string.account_edit_passwd_success);
                Tools.logout(AccountEditPwdActivity.this);
                return;
            }
            Tools.makeToast(Tools.getDroiError(droiError));
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_account_edit_pwd);
        this.dialog = Tools.getProgressDialog(this);
        initView();
    }

    private void initView() {
        this.etOldPaaswd = (EditText) findViewById(C1680R.id.user_old_passwd);
        this.etNewPaaswd = (EditText) findViewById(C1680R.id.user_new_password);
        this.etNewPaaswdAgain = (EditText) findViewById(C1680R.id.user_new_password_again);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_ok:
                editPasswd();
                return;
            default:
                return;
        }
    }

    private void editPasswd() {
        String oldPasswd = this.etOldPaaswd.getText().toString();
        String newPasswd = this.etNewPaaswd.getText().toString();
        String newPasswdAgain = this.etNewPaaswdAgain.getText().toString();
        if (TextUtils.isEmpty(oldPasswd)) {
            this.etOldPaaswd.requestFocus();
            this.etOldPaaswd.setError(getString(C1680R.string.account_input_old_paaswd));
        } else if (newPasswd.length() < 6) {
            this.etNewPaaswd.requestFocus();
            this.etNewPaaswd.setError(getString(C1680R.string.account_password_is_short));
        } else if (newPasswd.equals(newPasswdAgain)) {
            User user = (User) DroiUser.getCurrentUser(User.class);
            Tools.showProgressDialog(this.dialog, getString(C1680R.string.account_editing_passwd));
            user.changePasswordInBackground(oldPasswd, newPasswd, new C16871());
        } else {
            this.etNewPaaswdAgain.requestFocus();
            this.etNewPaaswdAgain.setError(getString(C1680R.string.account_password_is_different));
        }
    }
}
