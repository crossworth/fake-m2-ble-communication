package com.zhuoyi.account;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.tencent.open.SocialConstants;
import com.zhuoyi.account.authenticator.BaseActivity;
import com.zhuoyi.account.constant.UrlConstant;
import com.zhuoyi.account.netutil.HttpOperation;
import com.zhuoyi.account.util.DeviceUtil;
import com.zhuoyi.account.util.GetPublicParams;
import com.zhuoyi.account.util.MD5Util;
import com.zhuoyi.account.util.MyResource;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FindCodeMailActivity extends BaseActivity {
    public static IAccountListener mAccountListener;
    private Button mButton_findcode;
    private EditText mEditText_mail;
    private LinearLayout mLinearLayout_mail;
    private String userName;

    class C10271 implements OnFocusChangeListener {
        C10271() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                LinearLayout access$100 = FindCodeMailActivity.this.mLinearLayout_mail;
                Resources resources = FindCodeMailActivity.this.getResources();
                FindCodeMailActivity.this.mMyResource;
                access$100.setBackgroundDrawable(resources.getDrawable(MyResource.getDrawable("zy_input_di_click")));
                return;
            }
            access$100 = FindCodeMailActivity.this.mLinearLayout_mail;
            resources = FindCodeMailActivity.this.getResources();
            FindCodeMailActivity.this.mMyResource;
            access$100.setBackgroundDrawable(resources.getDrawable(MyResource.getDrawable("zy_input_di_normal")));
        }
    }

    class C10282 implements OnClickListener {
        C10282() {
        }

        public void onClick(View v) {
            FindCodeMailActivity.this.submit();
        }
    }

    public class FindcodeMail extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            String result = "";
            String name = params[0];
            String codetype = "findpasswd";
            Map<String, String> rawParams = new HashMap();
            String devinfo = DeviceUtil.getDeviceInfo(FindCodeMailActivity.this.getApplicationContext());
            String signString = MD5Util.md5(name + codetype + devinfo + UrlConstant.SIGNKEY);
            rawParams.put("mail", name);
            rawParams.put("codetype", codetype);
            rawParams.put("devinfo", devinfo);
            rawParams.put("sign", signString);
            if (isZh()) {
                rawParams.put("health", "zh");
            } else {
                rawParams.put("health", "en");
            }
            try {
                result = HttpOperation.postRequest(UrlConstant.ZHUOYOU_REGISTER_MAILBOX, rawParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        private boolean isZh() {
            if (FindCodeMailActivity.this.getResources().getConfiguration().locale.getLanguage().endsWith("zh")) {
                return true;
            }
            return false;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            FindCodeMailActivity.this.dismissProgressbar();
            if (TextUtils.isEmpty(result)) {
                Context applicationContext = FindCodeMailActivity.this.getApplicationContext();
                Resources resources = FindCodeMailActivity.this.getResources();
                FindCodeMailActivity.this.mMyResource;
                Toast.makeText(applicationContext, resources.getText(MyResource.getString("zy_server_exception")), 0).show();
                return;
            }
            try {
                Log.i("123", "result = " + result);
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getInt("result") == 0) {
                    applicationContext = FindCodeMailActivity.this.getApplicationContext();
                    resources = FindCodeMailActivity.this.getResources();
                    FindCodeMailActivity.this.mMyResource;
                    Toast.makeText(applicationContext, resources.getText(MyResource.getString("zy_find_pass_success")), 0).show();
                    FindCodeMailActivity.this.finish();
                    return;
                }
                String desc;
                if (jsonObject.has(SocialConstants.PARAM_APP_DESC)) {
                    desc = jsonObject.getString(SocialConstants.PARAM_APP_DESC);
                } else {
                    Resources resources2 = FindCodeMailActivity.this.getResources();
                    FindCodeMailActivity.this.mMyResource;
                    desc = resources2.getString(MyResource.getString("zy_find_pass_fail"));
                }
                Toast.makeText(FindCodeMailActivity.this.getApplicationContext(), desc, 0).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyResource myResource = this.mMyResource;
        setBarTitle(getString(MyResource.getString("zy_find_pass")));
        myResource = this.mMyResource;
        setContentView(MyResource.getLayout("zy_layout_findcode_mail_new"));
        Intent intent = getIntent();
        if (intent != null) {
            this.userName = intent.getStringExtra("username");
        }
        setView();
    }

    private void setView() {
        MyResource myResource = this.mMyResource;
        this.mEditText_mail = (EditText) findViewById(MyResource.getId("zy_findcode_mail_name"));
        myResource = this.mMyResource;
        this.mLinearLayout_mail = (LinearLayout) findViewById(MyResource.getId("zy_findcode_mail_name_linearLayout"));
        myResource = this.mMyResource;
        this.mButton_findcode = (Button) findViewById(MyResource.getId("zy_findcode_mail_btn"));
        if (!TextUtils.isEmpty(this.userName)) {
            this.mEditText_mail.setText(this.userName);
        }
        this.mEditText_mail.setOnFocusChangeListener(new C10271());
        this.mButton_findcode.setOnClickListener(new C10282());
    }

    public void submit() {
        String name = this.mEditText_mail.getText().toString();
        EditText editText;
        Resources resources;
        MyResource myResource;
        if (TextUtils.isEmpty(name)) {
            this.mEditText_mail.requestFocus();
            editText = this.mEditText_mail;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_register_mailbox_none")).toString());
        } else if (!RegisterMailBoxActivity.checkEmail(name)) {
            this.mEditText_mail.requestFocus();
            editText = this.mEditText_mail;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_register_mailbox_format_error")).toString());
        } else if (GetPublicParams.getAvailableNetWorkType(this) == -1) {
            r1 = getResources();
            r2 = this.mMyResource;
            Toast.makeText(this, r1.getText(MyResource.getString("zy_tip_network_wrong")), 0).show();
        } else {
            r1 = getResources();
            r2 = this.mMyResource;
            showProgressbar(r1.getText(MyResource.getString("zy_tip_reset_password_now")).toString());
            new FindcodeMail().execute(new String[]{name});
        }
    }
}
