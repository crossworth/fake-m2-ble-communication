package com.zhuoyi.account;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;
import com.zhuoyi.account.authenticator.AuthenticatorActivity;
import com.zhuoyi.account.model.User;
import com.zhuoyi.account.util.MyResource;
import com.zhuoyi.accoutn.dao.UserInfo;

public class ZyAccount {
    public static final String CALL_BACK_KEY = "callBackListener";
    public static final String SINA_APP_ID = "sina_app_id";
    public static final String TENCENT_APP_ID = "tencent_app_id";
    private Context mContext;
    private MyResource mMyResource;
    private String mPackageName;
    private ProgressDialog mProgressDialog;
    private String mSinaAppId;
    private String mTencentAppId;
    private User user = new User();

    public ZyAccount(Context context, String tencentAppId, String sinaAppId) {
        if (context != null) {
            this.mContext = context;
            this.mTencentAppId = tencentAppId;
            this.mSinaAppId = sinaAppId;
            this.mPackageName = this.mContext.getPackageName();
            this.mMyResource = new MyResource(context);
            this.mProgressDialog = new ProgressDialog(this.mContext);
            ProgressDialog progressDialog = this.mProgressDialog;
            Resources resources = this.mContext.getResources();
            MyResource myResource = this.mMyResource;
            progressDialog.setMessage(resources.getString(MyResource.getString("zy_user_center_loading")));
            this.mProgressDialog.setIndeterminate(true);
            this.mProgressDialog.setCancelable(true);
        }
    }

    public void register(IAccountListener accountListener) {
        Intent intent = new Intent(this.mContext.getApplicationContext(), RegisterActivity_new.class);
        intent.putExtra("isFromLogin", false);
        intent.setFlags(268435456);
        if (accountListener != null) {
            RegisterActivity_new.mAccountListener = accountListener;
        }
        this.mContext.startActivity(intent);
    }

    public void registerMailbox(IAccountListener accountListener) {
        Intent intent = new Intent(this.mContext.getApplicationContext(), RegisterMailBoxActivity.class);
        intent.putExtra("isFromLogin", false);
        intent.setFlags(268435456);
        if (accountListener != null) {
            RegisterMailBoxActivity.mAccountListener = accountListener;
        }
        this.mContext.startActivity(intent);
    }

    public void hasAccountService(Context context) {
        boolean flag = false;
        for (AuthenticatorDescription authenticatorDescription : AccountManager.get(context).getAuthenticatorTypes()) {
            if (authenticatorDescription.type.equals("com.zhuoyou.account.android.samplesync")) {
                flag = true;
                break;
            }
        }
        if (flag) {
            Toast.makeText(context.getApplicationContext(), "AccountService已启用", 0).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "AccountService不可用", 0).show();
        }
    }

    public void login(IAccountListener accountListener) {
        Intent intent = new Intent(this.mContext.getApplicationContext(), AuthenticatorActivity.class);
        intent.setFlags(268435456);
        Bundle bundle = new Bundle();
        bundle.putString(TENCENT_APP_ID, this.mTencentAppId);
        bundle.putString(SINA_APP_ID, this.mSinaAppId);
        intent.putExtras(bundle);
        if (accountListener != null) {
            AuthenticatorActivity.mAccountListener = accountListener;
        }
        this.mContext.startActivity(intent);
    }

    public String getUserInfo() {
        if (this.mContext == null) {
            return null;
        }
        return UserInfo.getUserInfo(this.mContext).toString();
    }
}
