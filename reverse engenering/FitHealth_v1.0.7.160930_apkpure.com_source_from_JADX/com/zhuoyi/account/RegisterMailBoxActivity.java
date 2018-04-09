package com.zhuoyi.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.open.SocialConstants;
import com.umeng.socialize.handler.TwitterPreferences;
import com.zhuoyi.account.authenticator.AuthenticatorActivity;
import com.zhuoyi.account.authenticator.BaseActivity;
import com.zhuoyi.account.constant.UrlConstant;
import com.zhuoyi.account.model.User;
import com.zhuoyi.account.netutil.HttpOperation;
import com.zhuoyi.account.util.DeviceUtil;
import com.zhuoyi.account.util.GetPublicParams;
import com.zhuoyi.account.util.MD5Util;
import com.zhuoyi.account.util.MyResource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class RegisterMailBoxActivity extends BaseActivity {
    public static IAccountListener mAccountListener;
    private boolean isFromLogin = false;
    private String jsonStringer = null;
    private AccountManager mAccountManager;
    private Button mButton_register_login;
    private CheckBox mCheckBox_licence;
    private boolean mConfirmCredentials = false;
    private EditText mEditText_register_code;
    private EditText mEditText_register_name;
    private LinearLayout mLinearLayout_register_code;
    private LinearLayout mLinearLayout_register_name;
    private TextView mTextView_licence;
    private User user = new User();
    private String userName;

    class C10381 implements OnFocusChangeListener {
        C10381() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                LinearLayout access$100 = RegisterMailBoxActivity.this.mLinearLayout_register_name;
                Resources resources = RegisterMailBoxActivity.this.getResources();
                RegisterMailBoxActivity.this.mMyResource;
                access$100.setBackgroundDrawable(resources.getDrawable(MyResource.getDrawable("zy_input_di_click")));
                return;
            }
            access$100 = RegisterMailBoxActivity.this.mLinearLayout_register_name;
            resources = RegisterMailBoxActivity.this.getResources();
            RegisterMailBoxActivity.this.mMyResource;
            access$100.setBackgroundDrawable(resources.getDrawable(MyResource.getDrawable("zy_input_di_normal")));
        }
    }

    class C10392 implements OnFocusChangeListener {
        C10392() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                LinearLayout access$400 = RegisterMailBoxActivity.this.mLinearLayout_register_code;
                Resources resources = RegisterMailBoxActivity.this.getResources();
                RegisterMailBoxActivity.this.mMyResource;
                access$400.setBackgroundDrawable(resources.getDrawable(MyResource.getDrawable("zy_input_di_click")));
                return;
            }
            access$400 = RegisterMailBoxActivity.this.mLinearLayout_register_code;
            resources = RegisterMailBoxActivity.this.getResources();
            RegisterMailBoxActivity.this.mMyResource;
            access$400.setBackgroundDrawable(resources.getDrawable(MyResource.getDrawable("zy_input_di_normal")));
        }
    }

    class C10403 implements OnClickListener {
        C10403() {
        }

        public void onClick(View v) {
            if (Build.MANUFACTURER.toLowerCase().equals("koobee")) {
                RegisterMailBoxActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(UrlConstant.ZHUOYOU_LICENCE_URL)));
            } else if (Build.MANUFACTURER.toLowerCase().equals("minte")) {
                RegisterMailBoxActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(UrlConstant.ZHUOYOU_LICENCE_URL)));
            } else {
                RegisterMailBoxActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(UrlConstant.ZHUOYOU_LICENCE_URL)));
            }
        }
    }

    class C10414 implements OnClickListener {
        C10414() {
        }

        public void onClick(View v) {
            RegisterMailBoxActivity.this.registerSubmit();
        }
    }

    public class MailboxRegister extends AsyncTask<String, Void, String> {
        private String name;
        private String pass;

        public MailboxRegister(String name, String pass) {
            this.name = name;
            this.pass = pass;
        }

        protected String doInBackground(String... params) {
            String result = "";
            String codetype = "userreg";
            Map<String, String> rawParams = new HashMap();
            String devinfo = DeviceUtil.getDeviceInfo(RegisterMailBoxActivity.this.getApplicationContext());
            String signString = MD5Util.md5(this.name + codetype + devinfo + UrlConstant.SIGNKEY);
            rawParams.put("mail", this.name);
            rawParams.put("codetype", codetype);
            rawParams.put("devinfo", devinfo);
            rawParams.put("sign", signString);
            rawParams.put("passwd", this.pass);
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
            if (RegisterMailBoxActivity.this.getResources().getConfiguration().locale.getLanguage().endsWith("zh")) {
                return true;
            }
            return false;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            RegisterMailBoxActivity.this.dismissProgressbar();
            if (TextUtils.isEmpty(result)) {
                Context applicationContext = RegisterMailBoxActivity.this.getApplicationContext();
                Resources resources = RegisterMailBoxActivity.this.getResources();
                RegisterMailBoxActivity.this.mMyResource;
                Toast.makeText(applicationContext, resources.getText(MyResource.getString("zy_server_exception")), 0).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getInt("result") == 0) {
                    RegisterMailBoxActivity registerMailBoxActivity = RegisterMailBoxActivity.this;
                    resources = RegisterMailBoxActivity.this.getResources();
                    RegisterMailBoxActivity.this.mMyResource;
                    registerMailBoxActivity.showProgressbar(resources.getText(MyResource.getString("zy_register_success_and_login")).toString());
                    RegisterMailBoxActivity.this.finish();
                    return;
                }
                String desc;
                if (jsonObject.has(SocialConstants.PARAM_APP_DESC)) {
                    desc = jsonObject.getString(SocialConstants.PARAM_APP_DESC);
                } else {
                    Resources resources2 = RegisterMailBoxActivity.this.getResources();
                    RegisterMailBoxActivity.this.mMyResource;
                    desc = resources2.getString(MyResource.getString("zy_register_fail"));
                }
                Log.i("chenxin", "desc:" + desc);
                if (desc.equals("邮箱已经存在")) {
                    desc = "Email address already exists";
                }
                Toast.makeText(RegisterMailBoxActivity.this, desc, 0).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class UserLoginMailbox extends AsyncTask<String, Void, String> {
        String name;
        String passMd5;
        String utype = "mail";

        protected String doInBackground(String... params) {
            String result = "";
            this.name = params[0];
            this.passMd5 = MD5Util.md5(params[1]);
            String devinfo = DeviceUtil.getDeviceInfo(RegisterMailBoxActivity.this.getApplicationContext());
            String signString = MD5Util.md5(this.name + this.passMd5 + this.utype + devinfo + UrlConstant.SIGNKEY);
            Map<String, String> rawParams = new HashMap();
            rawParams.put("uid", this.name);
            rawParams.put("passwd", this.passMd5);
            rawParams.put("utype", this.utype);
            rawParams.put("devinfo", devinfo);
            rawParams.put("sign", signString);
            try {
                result = HttpOperation.postRequest(UrlConstant.LOGIN, rawParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            RegisterMailBoxActivity.this.dismissProgressbar();
            if (TextUtils.isEmpty(result)) {
                Context applicationContext = RegisterMailBoxActivity.this.getApplicationContext();
                Resources resources = RegisterMailBoxActivity.this.getResources();
                RegisterMailBoxActivity.this.mMyResource;
                Toast.makeText(applicationContext, resources.getText(MyResource.getString("zy_server_exception")), 0).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                int mResult = jsonObject.getInt("result");
                if (mResult == 0) {
                    RegisterMailBoxActivity.this.user.setUsername(jsonObject.has("nickname") ? jsonObject.getString("nickname") : this.name);
                    RegisterMailBoxActivity.this.user.setPassword(this.passMd5);
                    RegisterMailBoxActivity.this.user.setTOKEN(jsonObject.getString(TwitterPreferences.TOKEN));
                    RegisterMailBoxActivity.this.user.setUID(jsonObject.has("username") ? jsonObject.getString("username") : this.name);
                    RegisterMailBoxActivity.this.user.setOpenid(jsonObject.getString("openid"));
                    RegisterMailBoxActivity.this.user.setExpires_in(jsonObject.getString("expire"));
                    RegisterMailBoxActivity.this.user.setRecode(jsonObject.getInt("score"));
                    RegisterMailBoxActivity.this.user.setLogoUrl(jsonObject.has("avatar") ? jsonObject.getString("avatar") : null);
                    RegisterMailBoxActivity.this.user.setRegtype(this.utype);
                    RegisterMailBoxActivity.this.user.setResult(mResult);
                    RegisterMailBoxActivity.this.user.setDesc(jsonObject.getString(SocialConstants.PARAM_APP_DESC));
                    RegisterMailBoxActivity.this.finishLogin();
                    return;
                }
                applicationContext = RegisterMailBoxActivity.this.getApplicationContext();
                resources = RegisterMailBoxActivity.this.getResources();
                RegisterMailBoxActivity.this.mMyResource;
                Toast.makeText(applicationContext, resources.getText(MyResource.getString("zy_tip_login_fail")), 0).show();
                RegisterMailBoxActivity.this.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyResource myResource = this.mMyResource;
        setBarTitle(getString(MyResource.getString("zy_register_mailbox")));
        myResource = this.mMyResource;
        setContentView(MyResource.getLayout("zy_layout_register_mailbox_new"));
        this.mAccountManager = AccountManager.get(this);
        try {
            Intent intent = getIntent();
            if (intent != null) {
                this.userName = intent.getStringExtra("username");
                this.isFromLogin = intent.getBooleanExtra("isFromLogin", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setViews();
    }

    private void setViews() {
        MyResource myResource = this.mMyResource;
        this.mEditText_register_name = (EditText) findViewById(MyResource.getId("zy_register_mailbox_name"));
        myResource = this.mMyResource;
        this.mEditText_register_code = (EditText) findViewById(MyResource.getId("zy_register_mailbox_code"));
        myResource = this.mMyResource;
        this.mButton_register_login = (Button) findViewById(MyResource.getId("zy_register_mailbox_btn"));
        myResource = this.mMyResource;
        this.mLinearLayout_register_name = (LinearLayout) findViewById(MyResource.getId("zy_register_mailbox_name_linearLayout"));
        myResource = this.mMyResource;
        this.mLinearLayout_register_code = (LinearLayout) findViewById(MyResource.getId("zy_register_mailbox_code_linearLayout"));
        myResource = this.mMyResource;
        this.mCheckBox_licence = (CheckBox) findViewById(MyResource.getId("zy_licence_checkbox_id"));
        myResource = this.mMyResource;
        this.mTextView_licence = (TextView) findViewById(MyResource.getId("zy_licence_text_id"));
        TextView textView = this.mTextView_licence;
        Resources resources = getResources();
        MyResource myResource2 = this.mMyResource;
        textView.setText(Html.fromHtml(resources.getString(MyResource.getString("zy_licence"))));
        if (!TextUtils.isEmpty(this.userName)) {
            this.mEditText_register_name.setText(this.userName);
        }
        LinearLayout linearLayout = this.mLinearLayout_register_name;
        resources = getResources();
        myResource2 = this.mMyResource;
        linearLayout.setBackgroundDrawable(resources.getDrawable(MyResource.getDrawable("zy_input_di_click")));
        this.mEditText_register_name.setOnFocusChangeListener(new C10381());
        this.mEditText_register_code.setOnFocusChangeListener(new C10392());
        this.mTextView_licence.setOnClickListener(new C10403());
        this.mButton_register_login.setOnClickListener(new C10414());
    }

    private void registerSubmit() {
        String userName = this.mEditText_register_name.getText().toString();
        String code = this.mEditText_register_code.getText().toString();
        EditText editText;
        Resources resources;
        MyResource myResource;
        if (TextUtils.isEmpty(userName)) {
            this.mEditText_register_name.requestFocus();
            editText = this.mEditText_register_name;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_register_mailbox_none")).toString());
        } else if (!checkEmail(userName)) {
            this.mEditText_register_name.requestFocus();
            editText = this.mEditText_register_name;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_register_mailbox_format_error")).toString());
        } else if (TextUtils.isEmpty(code)) {
            this.mEditText_register_code.requestFocus();
            editText = this.mEditText_register_code;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_tip_password_none")).toString());
        } else if (valid(code)) {
            this.mEditText_register_code.requestFocus();
            editText = this.mEditText_register_code;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_tip_password_valid")).toString());
        } else if (code.length() < 6 || code.length() > 20) {
            this.mEditText_register_code.requestFocus();
            editText = this.mEditText_register_code;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_tip_password_not_right_length")).toString());
        } else if (!this.mCheckBox_licence.isChecked()) {
            r2 = getResources();
            r3 = this.mMyResource;
            Toast.makeText(this, r2.getText(MyResource.getString("zy_licence_tip")), 1).show();
        } else if (GetPublicParams.getAvailableNetWorkType(this) == -1) {
            r2 = getResources();
            r3 = this.mMyResource;
            Toast.makeText(this, r2.getText(MyResource.getString("zy_tip_network_wrong")), 0).show();
        } else {
            r2 = getResources();
            r3 = this.mMyResource;
            showProgressbar(r2.getText(MyResource.getString("zy_tip_register_now")).toString());
            new MailboxRegister(userName, code).execute(new String[]{userName, code});
        }
    }

    private void finishLogin() {
        if (this.mConfirmCredentials) {
            finishConfirmCredentials(true);
            return;
        }
        this.jsonStringer = null;
        try {
            this.jsonStringer = new JSONStringer().object().key("username").value(TextUtils.isEmpty(this.user.getUsername()) ? this.mEditText_register_name.getText() + "" : this.user.getUsername()).key("password").value(TextUtils.isEmpty(this.user.getPassword()) ? MD5Util.md5(this.mEditText_register_code.getText() + "") : this.user.getPassword()).key("UID").value(this.user.getUID()).key("openid").value(this.user.getOpenid()).key("OpenKey").value(this.user.getOpenKey()).key("TOKEN").value(this.user.getTOKEN()).key("regtype").value(this.user.getRegtype()).key("expires_in").value(this.user.getExpires_in()).key("recode").value(TextUtils.isEmpty(new StringBuilder().append(this.user.getRecode()).append("").toString()) ? 0 : (long) this.user.getRecode()).key("logoUrl").value(TextUtils.isEmpty(this.user.getLogoUrl()) ? null : this.user.getLogoUrl()).endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (AuthenticatorActivity.instance != null) {
            AuthenticatorActivity.mAccountListener = null;
            AuthenticatorActivity.instance.finish();
        }
        finish();
    }

    private void finishConfirmCredentials(boolean result) {
        this.mAccountManager.setPassword(new Account(this.user.getUsername(), "com.zhuoyou.account.android.samplesync"), this.user.getPassword());
        Intent intent = new Intent();
        intent.putExtra("booleanResult", result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(-1, intent);
        finish();
    }

    public static boolean checkEmail(String email) {
        try {
            return Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$").matcher(email).matches();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean valid(String str) {
        return !str.matches("^[\\da-zA-Z]*$");
    }

    protected void onDestroy() {
        if (mAccountListener != null) {
            if (!TextUtils.isEmpty(this.jsonStringer)) {
                mAccountListener.onSuccess(this.jsonStringer);
                this.jsonStringer = null;
            } else if (!this.isFromLogin) {
                mAccountListener.onCancel();
            }
            mAccountListener = null;
        }
        super.onDestroy();
    }
}
