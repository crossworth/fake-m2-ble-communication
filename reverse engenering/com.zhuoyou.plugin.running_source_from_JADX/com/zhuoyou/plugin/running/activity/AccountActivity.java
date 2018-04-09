package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;

public class AccountActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!AccountActivity.class.desiredAssertionStatus());
    private TextView tvPhone;
    private User user = ((User) DroiUser.getCurrentUser(User.class));

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_account);
        initView();
    }

    private void initView() {
        TextView etAccount = (TextView) findViewById(C1680R.id.tv_userid);
        this.tvPhone = (TextView) findViewById(C1680R.id.tv_bind_phone);
        if ($assertionsDisabled || etAccount != null) {
            etAccount.setText(this.user.getUserId());
            this.tvPhone.setText(this.user.getPhoneNumber());
            return;
        }
        throw new AssertionError();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_edit_passwd:
                startActivity(new Intent(this, AccountEditPwdActivity.class));
                return;
            case C1680R.id.btn_bind_phone:
                startActivity(new Intent(this, AccountPhoneActivity.class));
                return;
            case C1680R.id.btn_bind_email:
                startActivity(new Intent(this, AccountEmailActivity.class));
                return;
            default:
                return;
        }
    }
}
