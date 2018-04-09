package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;

public class AccountPhoneActivity extends BaseActivity {
    private EditText etCode;
    private EditText etPhone;
    private User user = ((User) DroiUser.getCurrentUser(User.class));

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_account_phone);
        initView();
    }

    private void initView() {
        this.etPhone = (EditText) findViewById(C1680R.id.et_phone);
        this.etCode = (EditText) findViewById(C1680R.id.et_code);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_ok:
                if (this.user.validatePhoneNumber().isOk()) {
                    Tools.makeToast("验证码已发送！");
                    return;
                } else {
                    Tools.makeToast("验证码发送失败！");
                    return;
                }
            case C1680R.id.btn_code_ok:
                if (this.user.confirmPhoneNumberPinCode(this.etCode.getText().toString()).isOk()) {
                    Tools.makeToast("绑定成功！");
                    return;
                } else {
                    Tools.makeToast("绑定失败！");
                    return;
                }
            default:
                return;
        }
    }
}
