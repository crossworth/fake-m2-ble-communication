package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.core.OAuthProvider;
import com.droi.sdk.core.OAuthProvider.AuthProvider;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import org.greenrobot.eventbus.Subscribe;

public class AccountLoginActivity extends BaseActivity {
    public static final String KEY_LOGIN_NAME = "key_login_name";
    private OAuthProvider authProvider;
    private ProgressDialog dialog;
    private EditText etName;
    private EditText etPasswd;

    class C16891 implements DroiCallback<DroiUser> {
        C16891() {
        }

        public void result(DroiUser droiUser, DroiError droiError) {
            Log.i("zhuqichao", "droiError=" + droiError);
            Tools.hideProgressDialog(AccountLoginActivity.this.dialog);
            if (droiError.isOk()) {
                Tools.getDataAndGotoMain(AccountLoginActivity.this, AccountLoginActivity.this.dialog);
            } else {
                Tools.makeToast(Tools.getDroiError(droiError));
            }
        }
    }

    class C16902 implements DroiCallback<DroiUser> {
        C16902() {
        }

        public void result(DroiUser droiUser, DroiError droiError) {
            if (droiError.isOk()) {
                Log.i("zhuqichao", "userid=" + droiUser.getUserId());
                Log.i("zhuqichao", "userEmail=" + droiUser.getEmail());
                Log.i("zhuqichao", "userPassword=" + droiUser.getPassword());
                Log.i("zhuqichao", "userPhoneNumber=" + droiUser.getPhoneNumber());
                return;
            }
            Log.i("zhuqichao", "登录失败：error=" + droiError);
        }
    }

    public static class EventFinish {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_account_login);
        this.dialog = Tools.getProgressDialog(this);
        initView();
        if (SPUtils.isFirstInApp() && getResources().getConfiguration().locale.getLanguage().equals("zh")) {
            startActivity(new Intent(this, GuideActivity.class));
            SPUtils.setFirstInApp(false);
        }
    }

    private void initView() {
        this.etName = (EditText) findViewById(C1680R.id.user_name);
        this.etPasswd = (EditText) findViewById(C1680R.id.user_password);
        this.etName.setText(getIntent().getStringExtra(KEY_LOGIN_NAME));
        this.etName.setSelection(this.etName.getText().length());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_register:
                Intent intent = new Intent(this, AccountRegisterActivity.class);
                intent.putExtra(AccountRegisterActivity.KEY_REGIST_NAME, this.etName.getText().toString());
                startActivity(intent);
                return;
            case C1680R.id.btn_login:
                login();
                return;
            case C1680R.id.btn_login_qq:
                this.authProvider = OAuthProvider.createAuthProvider(AuthProvider.QQ, this);
                loginOAuthAsync();
                return;
            case C1680R.id.btn_login_wechat:
                this.authProvider = OAuthProvider.createAuthProvider(AuthProvider.Weixin, this);
                loginOAuthAsync();
                return;
            case C1680R.id.btn_login_weibo:
                this.authProvider = OAuthProvider.createAuthProvider(AuthProvider.Sina, this);
                loginOAuthAsync();
                return;
            default:
                return;
        }
    }

    private void login() {
        String name = this.etName.getText().toString();
        String passwd = this.etPasswd.getText().toString();
        if (TextUtils.isEmpty(name)) {
            this.etName.requestFocus();
            this.etName.setError(getString(C1680R.string.account_name_is_notnull));
        } else if (TextUtils.isEmpty(passwd)) {
            this.etPasswd.requestFocus();
            this.etPasswd.setError(getString(C1680R.string.account_password_is_notnull));
        } else {
            Tools.showProgressDialog(this.dialog, getString(C1680R.string.account_logining));
            DroiUser.loginInBackground(name, passwd, User.class, new C16891());
        }
    }

    private void loginOAuthAsync() {
        DroiUser.loginOAuthAsync(this, this.authProvider, new C16902());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.authProvider.handleActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void onEventMainThread(EventFinish event) {
        finish();
    }
}
