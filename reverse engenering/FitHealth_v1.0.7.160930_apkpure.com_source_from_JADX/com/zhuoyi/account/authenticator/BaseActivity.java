package com.zhuoyi.account.authenticator;

import android.accounts.AccountAuthenticatorActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhuoyi.account.util.MyResource;

public class BaseActivity extends AccountAuthenticatorActivity {
    BaseActivity baseActivity = null;
    ProgressDialog mDialog = null;
    protected MyResource mMyResource = new MyResource(this);

    class C10521 implements OnClickListener {
        C10521() {
        }

        public void onClick(View v) {
            BaseActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyResource myResource = this.mMyResource;
        super.setContentView(MyResource.getLayout("zy_base_activity_with_titlebar"));
        setTitle(null);
        setLeftButton("", new C10521());
        this.mDialog = new ProgressDialog(this);
        this.mDialog.setProgressStyle(0);
        this.mDialog.setIndeterminate(true);
        this.mDialog.setCancelable(false);
        this.baseActivity = this;
    }

    protected void showProgressbar(String content) {
        this.mDialog.setMessage(content);
        if (!this.mDialog.isShowing() && this.baseActivity != null && !this.baseActivity.isFinishing()) {
            this.mDialog.show();
        }
    }

    protected void dismissProgressbar() {
        if (this.baseActivity != null && !this.baseActivity.isFinishing() && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    public void setContentView(int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(this);
        MyResource myResource = this.mMyResource;
        ((LinearLayout) findViewById(MyResource.getId("zy_layout_content"))).addView(inflater.inflate(layoutResID, null));
    }

    public void setLeftButtonEnable() {
        MyResource myResource = this.mMyResource;
        ((ImageView) findViewById(MyResource.getId("zy_left_button"))).setVisibility(0);
    }

    public void setLeftButton(String title, OnClickListener listener) {
        MyResource myResource = this.mMyResource;
        ((ImageView) findViewById(MyResource.getId("zy_left_button"))).setOnClickListener(listener);
    }

    public void setLeftButton(int strId, OnClickListener listener) {
        MyResource myResource = this.mMyResource;
        ((ImageView) findViewById(MyResource.getId("zy_left_button"))).setOnClickListener(listener);
    }

    public void setBarTitle(String title) {
        MyResource myResource = this.mMyResource;
        ((TextView) findViewById(MyResource.getId("zy_base_title"))).setText(title);
    }
}
