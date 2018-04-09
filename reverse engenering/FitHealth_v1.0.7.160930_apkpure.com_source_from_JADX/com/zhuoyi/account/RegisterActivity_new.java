package com.zhuoyi.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.zhuoyi.account.util.PhoneNumUtils;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class RegisterActivity_new extends BaseActivity {
    public static String REGEX = "";
    public static String TEMP_TOAKEN = "";
    public static int count = 0;
    public static IAccountListener mAccountListener = null;
    private static final String strRes = "android.provider.Telephony.SMS_RECEIVED";
    private Handler handlerl = new C10301();
    private boolean isFromLogin = false;
    private String jsonStringer = null;
    private CheckBox licence_checkbox_id;
    private TextView licence_text_id;
    private EditText login_code;
    private EditText login_name;
    private EditText login_security_code;
    private AccountManager mAccountManager;
    private UserLoginTask mAuthTask = null;
    private Boolean mConfirmCredentials = Boolean.valueOf(false);
    private ProgressDialog mProgressDialog = null;
    protected boolean mRequestNewAccount = true;
    private Button register_btn;
    private Button register_get_security_code_btn;
    private SmsReceiver smsReceiver;
    private TimeCount time;
    private User user = new User();

    class C10301 extends Handler {
        C10301() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Context context;
            Resources resources;
            switch (msg.what) {
                case 0:
                    RegisterActivity_new.this.dismissProgressbar();
                    context = RegisterActivity_new.this;
                    resources = RegisterActivity_new.this.getResources();
                    RegisterActivity_new.this.mMyResource;
                    Toast.makeText(context, resources.getText(MyResource.getString("zy_tip_network_wrong")), 0).show();
                    return;
                case 2:
                    RegisterActivity_new.this.dismissProgressbar();
                    context = RegisterActivity_new.this;
                    resources = RegisterActivity_new.this.getResources();
                    RegisterActivity_new.this.mMyResource;
                    Toast.makeText(context, resources.getText(MyResource.getString("zy_tip_register_wrong")), 0).show();
                    return;
                case 3:
                    context = RegisterActivity_new.this;
                    RegisterActivity_new.this.mMyResource;
                    Toast.makeText(context, MyResource.getString("zy_tip_register_completed"), 1).show();
                    return;
                case 4:
                    context = RegisterActivity_new.this;
                    resources = RegisterActivity_new.this.getResources();
                    RegisterActivity_new.this.mMyResource;
                    Toast.makeText(context, resources.getText(MyResource.getString("zy_server_exception")), 0).show();
                    return;
                default:
                    return;
            }
        }
    }

    class C10312 implements OnFocusChangeListener {
        C10312() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
            }
        }
    }

    class C10323 implements OnFocusChangeListener {
        C10323() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
            }
        }
    }

    class C10334 implements OnFocusChangeListener {
        C10334() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
        }
    }

    class C10345 implements OnClickListener {
        C10345() {
        }

        public void onClick(View v) {
            if (Build.MANUFACTURER.toLowerCase().equals("koobee")) {
                RegisterActivity_new.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(UrlConstant.ZHUOYOU_LICENCE_URL)));
            } else if (Build.MANUFACTURER.toLowerCase().equals("minte")) {
                RegisterActivity_new.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(UrlConstant.ZHUOYOU_LICENCE_URL)));
            } else {
                RegisterActivity_new.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(UrlConstant.ZHUOYOU_LICENCE_URL)));
            }
        }
    }

    class C10356 implements OnClickListener {
        C10356() {
        }

        public void onClick(View v) {
            String userName = RegisterActivity_new.this.login_name.getText().toString();
            EditText access$1100;
            Resources resources;
            if (TextUtils.isEmpty(userName)) {
                RegisterActivity_new.this.login_name.requestFocus();
                access$1100 = RegisterActivity_new.this.login_name;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                access$1100.setError(resources.getText(MyResource.getString("zy_tip_username_none")).toString());
            } else if (!PhoneNumUtils.isPhoneNumberValid(userName)) {
                RegisterActivity_new.this.login_name.requestFocus();
                access$1100 = RegisterActivity_new.this.login_name;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                access$1100.setError(resources.getText(MyResource.getString("zy_tip_username_must_phonenum")).toString());
            } else if (GetPublicParams.getAvailableNetWorkType(RegisterActivity_new.this) == -1) {
                RegisterActivity_new.this.handlerl.sendEmptyMessage(0);
            } else {
                new GetRegNum(userName).execute(new Object[0]);
                RegisterActivity_new.this.time.start();
            }
        }
    }

    class C10367 implements OnClickListener {
        C10367() {
        }

        public void onClick(View v) {
            String userName = RegisterActivity_new.this.login_name.getText().toString();
            String userPasswd = RegisterActivity_new.this.login_code.getText().toString();
            String securityCode = RegisterActivity_new.this.login_security_code.getText().toString();
            EditText access$1100;
            Resources resources;
            if (TextUtils.isEmpty(userName)) {
                RegisterActivity_new.this.login_name.requestFocus();
                access$1100 = RegisterActivity_new.this.login_name;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                access$1100.setError(resources.getText(MyResource.getString("zy_tip_username_none")).toString());
            } else if (!PhoneNumUtils.isPhoneNumberValid(userName)) {
                RegisterActivity_new.this.login_name.requestFocus();
                access$1100 = RegisterActivity_new.this.login_name;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                access$1100.setError(resources.getText(MyResource.getString("zy_tip_username_must_phonenum")).toString());
            } else if (TextUtils.isEmpty(userPasswd)) {
                r3 = RegisterActivity_new.this;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                Toast.makeText(r3, resources.getText(MyResource.getString("zy_tip_password_none")), 1).show();
                RegisterActivity_new.this.login_code.requestFocus();
                access$1100 = RegisterActivity_new.this.login_code;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                access$1100.setError(resources.getText(MyResource.getString("zy_tip_password_none")).toString());
            } else if (RegisterActivity_new.this.valid(userPasswd)) {
                r3 = RegisterActivity_new.this;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                Toast.makeText(r3, resources.getText(MyResource.getString("zy_tip_password_valid")), 1).show();
                RegisterActivity_new.this.login_code.requestFocus();
                access$1100 = RegisterActivity_new.this.login_code;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                access$1100.setError(resources.getText(MyResource.getString("zy_tip_password_valid")).toString());
            } else if (userPasswd.length() < 6 || userPasswd.length() > 20) {
                RegisterActivity_new.this.login_code.requestFocus();
                access$1100 = RegisterActivity_new.this.login_code;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                access$1100.setError(resources.getText(MyResource.getString("zy_tip_password_not_right_length")).toString());
            } else if (TextUtils.isEmpty(securityCode) || securityCode.length() != 6) {
                r3 = RegisterActivity_new.this.getBaseContext();
                RegisterActivity_new.this.mMyResource;
                Toast.makeText(r3, MyResource.getString("zy_reg_security_code"), 0).show();
            } else if (RegisterActivity_new.this.licence_checkbox_id.isChecked()) {
                RegisterActivity_new registerActivity_new = RegisterActivity_new.this;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                registerActivity_new.showProgressbar(resources.getText(MyResource.getString("zy_tip_register_now")).toString());
                if (GetPublicParams.getAvailableNetWorkType(RegisterActivity_new.this) == -1) {
                    RegisterActivity_new.this.handlerl.sendEmptyMessage(0);
                } else {
                    new RegisterUser(userName, userPasswd, securityCode).execute(new Object[0]);
                }
            } else {
                r3 = RegisterActivity_new.this;
                resources = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                Toast.makeText(r3, resources.getText(MyResource.getString("zy_licence_tip")), 1).show();
            }
        }
    }

    class C10378 implements OnCancelListener {
        C10378() {
        }

        public void onCancel(DialogInterface dialog) {
            if (RegisterActivity_new.this.mAuthTask != null) {
                RegisterActivity_new.this.mAuthTask.cancel(true);
            }
        }
    }

    public class GetRegNum extends AsyncTask<Object, Object, String> {
        String uid = null;

        public GetRegNum(String uid) {
            this.uid = uid;
        }

        protected String doInBackground(Object... params) {
            String result = null;
            Map<String, String> rawParams = new HashMap();
            rawParams.put("uid", this.uid);
            rawParams.put("codetype", "userreg");
            rawParams.put("sign", "");
            rawParams.put("sign", MD5Util.md5(this.uid + "userreg" + UrlConstant.SIGNKEY));
            try {
                result = HttpOperation.postRequest(UrlConstant.ZHUOYOUREGISTER, rawParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            Exception e;
            super.onPostExecute(result);
            if (TextUtils.isEmpty(result)) {
                RegisterActivity_new.this.time.onFinish();
                RegisterActivity_new.this.time.cancel();
                Context applicationContext = RegisterActivity_new.this.getApplicationContext();
                RegisterActivity_new.this.mMyResource;
                Toast.makeText(applicationContext, MyResource.getString("zy_server_exception"), 0).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject jSONObject;
                try {
                    int re = jsonObject.getInt("result");
                    if (re == 0) {
                        RegisterActivity_new.TEMP_TOAKEN = jsonObject.getString(TwitterPreferences.TOKEN);
                        RegisterActivity_new.REGEX = (String) jsonObject.get("smspattern");
                    } else if (re < 0) {
                        RegisterActivity_new.this.time.onFinish();
                        RegisterActivity_new.this.time.cancel();
                    }
                    String desc = jsonObject.getString(SocialConstants.PARAM_APP_DESC);
                    if (!TextUtils.isEmpty(desc)) {
                        Toast.makeText(RegisterActivity_new.this.getApplicationContext(), desc.trim(), 0).show();
                    }
                    jSONObject = jsonObject;
                } catch (Exception e2) {
                    e = e2;
                    jSONObject = jsonObject;
                    e.printStackTrace();
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
            }
        }
    }

    public class RegisterUser extends AsyncTask<Object, Object, String> {
        String securityCode = "";
        String userName = "";
        String userPasswd = "";

        public RegisterUser(String userName, String userPasswd, String securityCode) {
            this.userName = userName;
            this.userPasswd = userPasswd;
            this.securityCode = securityCode;
        }

        protected String doInBackground(Object... params) {
            String result = null;
            if (TextUtils.isEmpty(RegisterActivity_new.TEMP_TOAKEN)) {
                return null;
            }
            Map<String, String> rawParams = new HashMap();
            String devinfo = DeviceUtil.getDeviceInfo(RegisterActivity_new.this.getApplicationContext());
            String signString = MD5Util.md5(RegisterActivity_new.TEMP_TOAKEN + this.userPasswd + "randreg" + devinfo + this.securityCode + UrlConstant.SIGNKEY);
            rawParams.put(TwitterPreferences.TOKEN, RegisterActivity_new.TEMP_TOAKEN);
            rawParams.put("passwd", this.userPasswd);
            rawParams.put("regtype", "randreg");
            rawParams.put("randcode", this.securityCode);
            rawParams.put("sign", signString);
            rawParams.put("devinfo", devinfo);
            try {
                result = HttpOperation.postRequest(UrlConstant.ZHUOYOU_REGISTER_SIGNUP, rawParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            RegisterActivity_new.this.dismissProgressbar();
            if (!TextUtils.isEmpty(result)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int mResult = jsonObject.getInt("result");
                    if (mResult == 0) {
                        RegisterActivity_new.this.showProgress();
                        RegisterActivity_new.this.handlerl.sendEmptyMessage(3);
                        RegisterActivity_new.this.mAuthTask = new UserLoginTask(this.userName, this.userPasswd);
                        RegisterActivity_new.this.mAuthTask.execute(new Void[0]);
                    } else if (mResult < 0) {
                        String desc;
                        if (jsonObject.has(SocialConstants.PARAM_APP_DESC)) {
                            desc = jsonObject.getString(SocialConstants.PARAM_APP_DESC);
                        } else {
                            Resources resources = RegisterActivity_new.this.getResources();
                            RegisterActivity_new.this.mMyResource;
                            desc = resources.getString(MyResource.getString("zy_tip_register_wrong"));
                        }
                        Toast.makeText(RegisterActivity_new.this, desc, 0).show();
                    } else {
                        RegisterActivity_new.this.handlerl.sendEmptyMessage(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (TextUtils.isEmpty(RegisterActivity_new.TEMP_TOAKEN)) {
                r4 = RegisterActivity_new.this.getApplicationContext();
                r5 = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                Toast.makeText(r4, r5.getText(MyResource.getString("zy_tip_code_wrong")), 0).show();
            } else {
                r4 = RegisterActivity_new.this.getApplicationContext();
                r5 = RegisterActivity_new.this.getResources();
                RegisterActivity_new.this.mMyResource;
                Toast.makeText(r4, r5.getText(MyResource.getString("zy_server_exception")), 0).show();
            }
        }
    }

    private class SmsReceiver extends BroadcastReceiver {
        private SmsReceiver() {
        }

        public void onReceive(Context _context, Intent _intent) {
            if (RegisterActivity_new.strRes.equals(_intent.getAction())) {
                StringBuilder sb = new StringBuilder();
                Bundle bundle = _intent.getExtras();
                if (bundle != null) {
                    int i;
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    SmsMessage[] msg = new SmsMessage[pdus.length];
                    for (i = 0; i < pdus.length; i++) {
                        msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    for (SmsMessage curMsg : msg) {
                        sb.append(curMsg.getDisplayMessageBody());
                    }
                    if (!TextUtils.isEmpty(RegisterActivity_new.REGEX)) {
                        Matcher matcher = Pattern.compile(RegisterActivity_new.REGEX).matcher(sb.toString());
                        if (matcher.find()) {
                            for (i = 1; i <= matcher.groupCount(); i++) {
                                RegisterActivity_new.this.login_security_code.setText(matcher.group(i));
                                RegisterActivity_new.this.time.cancel();
                                Button access$400 = RegisterActivity_new.this.register_get_security_code_btn;
                                RegisterActivity_new registerActivity_new = RegisterActivity_new.this;
                                RegisterActivity_new.this.mMyResource;
                                access$400.setText(registerActivity_new.getText(MyResource.getString("zy_account_register_security_code")));
                                RegisterActivity_new.this.register_get_security_code_btn.setClickable(true);
                            }
                        }
                    }
                }
            }
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            Button access$400 = RegisterActivity_new.this.register_get_security_code_btn;
            RegisterActivity_new registerActivity_new = RegisterActivity_new.this;
            RegisterActivity_new.this.mMyResource;
            access$400.setText(registerActivity_new.getText(MyResource.getString("zy_account_register_security_code_again")));
            RegisterActivity_new.this.register_get_security_code_btn.setClickable(true);
        }

        public void onTick(long millisUntilFinished) {
            RegisterActivity_new.this.register_get_security_code_btn.setClickable(false);
            Button access$400 = RegisterActivity_new.this.register_get_security_code_btn;
            StringBuilder append = new StringBuilder().append(millisUntilFinished / 1000);
            Resources resources = RegisterActivity_new.this.getResources();
            RegisterActivity_new.this.mMyResource;
            access$400.setText(append.append(resources.getString(MyResource.getString("zy_second"))).toString());
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {
        private String loginResult;
        private String password;
        private String username;

        public UserLoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        protected String doInBackground(Void... params) {
            Map<String, String> loginParams = new HashMap();
            String passwdMD5 = MD5Util.md5(this.password);
            String devinfo = DeviceUtil.getDeviceInfo(RegisterActivity_new.this.getApplicationContext());
            loginParams.put("uid", this.username);
            loginParams.put("passwd", passwdMD5);
            loginParams.put("utype", "zhuoyou");
            loginParams.put("devinfo", devinfo);
            loginParams.put("sign", MD5Util.md5(this.username + passwdMD5 + "zhuoyou" + devinfo + UrlConstant.SIGNKEY));
            this.loginResult = HttpOperation.postRequest(UrlConstant.LOGIN, loginParams);
            try {
                JSONObject jsonObject = new JSONObject(this.loginResult);
                int result = jsonObject.getInt("result");
                if (result == 0) {
                    RegisterActivity_new.this.user.setUsername(jsonObject.has("nickname") ? jsonObject.getString("nickname") : this.username);
                    RegisterActivity_new.this.user.setPassword(passwdMD5);
                    RegisterActivity_new.this.user.setTOKEN(jsonObject.getString(TwitterPreferences.TOKEN));
                    RegisterActivity_new.this.user.setUID(jsonObject.has("username") ? jsonObject.getString("username") : this.username);
                    RegisterActivity_new.this.user.setOpenid(jsonObject.getString("openid"));
                    RegisterActivity_new.this.user.setExpires_in(jsonObject.getString("expire"));
                    RegisterActivity_new.this.user.setRecode(jsonObject.getInt("score"));
                    RegisterActivity_new.this.user.setLogoUrl(jsonObject.has("avatar") ? jsonObject.getString("avatar") : null);
                }
                RegisterActivity_new.this.user.setRegtype("zhuoyou");
                RegisterActivity_new.this.user.setResult(result);
                RegisterActivity_new.this.user.setDesc(jsonObject.getString(SocialConstants.PARAM_APP_DESC));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.loginResult;
        }

        protected void onPostExecute(String authToken) {
            RegisterActivity_new.this.onAuthenticationResult(RegisterActivity_new.this.user);
        }

        protected void onCancelled() {
            RegisterActivity_new.this.onAuthenticationCancel();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyResource myResource = this.mMyResource;
        setBarTitle(getString(MyResource.getString("zy_account_register")));
        myResource = this.mMyResource;
        setContentView(MyResource.getLayout("zy_layout_register_new"));
        this.mAccountManager = AccountManager.get(this);
        this.smsReceiver = new SmsReceiver();
        this.time = new TimeCount(TimeManager.UNIT_MINUTE, 1000);
        registerReceiver(this.smsReceiver, new IntentFilter(strRes));
        setViews();
    }

    private void setViews() {
        MyResource myResource = this.mMyResource;
        this.login_name = (EditText) findViewById(MyResource.getId("zy_login_name"));
        myResource = this.mMyResource;
        this.login_code = (EditText) findViewById(MyResource.getId("zy_login_code"));
        myResource = this.mMyResource;
        this.login_security_code = (EditText) findViewById(MyResource.getId("zy_login_security_code"));
        myResource = this.mMyResource;
        this.register_btn = (Button) findViewById(MyResource.getId("zy_register_btn"));
        myResource = this.mMyResource;
        this.register_get_security_code_btn = (Button) findViewById(MyResource.getId("zy_register_get_security_code_btn"));
        myResource = this.mMyResource;
        this.licence_checkbox_id = (CheckBox) findViewById(MyResource.getId("zy_licence_checkbox_id"));
        myResource = this.mMyResource;
        this.licence_text_id = (TextView) findViewById(MyResource.getId("zy_licence_text_id"));
        TextView textView = this.licence_text_id;
        Resources resources = getResources();
        MyResource myResource2 = this.mMyResource;
        textView.setText(Html.fromHtml(resources.getString(MyResource.getString("zy_licence"))));
        try {
            Intent intent = getIntent();
            if (intent != null) {
                String userName = intent.getStringExtra("username");
                String password = intent.getStringExtra("password");
                if (!TextUtils.isEmpty(userName)) {
                    this.login_name.setText(userName);
                }
                if (!TextUtils.isEmpty(password)) {
                    this.login_code.setText(password);
                }
                this.isFromLogin = intent.getBooleanExtra("isFromLogin", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.login_name.setOnFocusChangeListener(new C10312());
        this.login_code.setOnFocusChangeListener(new C10323());
        this.login_security_code.setOnFocusChangeListener(new C10334());
        this.licence_text_id.setOnClickListener(new C10345());
        this.register_get_security_code_btn.setOnClickListener(new C10356());
        this.login_name.setFocusable(true);
        this.register_btn.setOnClickListener(new C10367());
    }

    private void showProgress() {
        showDialog(0);
    }

    private void hideProgress() {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    protected Dialog onCreateDialog(int id, Bundle args) {
        ProgressDialog dialog = new ProgressDialog(this);
        MyResource myResource = this.mMyResource;
        dialog.setMessage(getString(MyResource.getString("zy_authenticate_login")));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new C10378());
        this.mProgressDialog = dialog;
        return dialog;
    }

    private void finishConfirmCredentials(boolean result) {
        this.mAccountManager.setPassword(new Account(this.user.getUsername(), "com.zhuoyou.account.android.samplesync"), this.user.getPassword());
        Intent intent = new Intent();
        intent.putExtra("booleanResult", result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(-1, intent);
        finish();
    }

    private void finishLogin(String authToken) {
        try {
            this.jsonStringer = null;
            this.jsonStringer = new JSONStringer().object().key("username").value(TextUtils.isEmpty(this.user.getUsername()) ? this.login_name.getText().toString().trim() : this.user.getUsername()).key("password").value(TextUtils.isEmpty(this.user.getPassword()) ? MD5Util.md5(this.login_security_code.getText().toString().trim()) : this.user.getPassword()).key("UID").value(this.user.getUID()).key("openid").value(this.user.getOpenid()).key("OpenKey").value(this.user.getOpenKey()).key("TOKEN").value(this.user.getTOKEN()).key("regtype").value(this.user.getRegtype()).key("expires_in").value(this.user.getExpires_in()).key("recode").value(TextUtils.isEmpty(new StringBuilder().append(this.user.getRecode()).append("").toString()) ? 0 : (long) this.user.getRecode()).key("logoUrl").value(TextUtils.isEmpty(this.user.getLogoUrl()) ? null : this.user.getLogoUrl()).endObject().toString();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        if (AuthenticatorActivity.instance != null) {
            AuthenticatorActivity.mAccountListener = null;
            AuthenticatorActivity.instance.finish();
        }
        finish();
    }

    public void onAuthenticationResult(User user) {
        boolean success;
        String authToken = null;
        if (user != null) {
            authToken = user.getTOKEN();
        }
        if (authToken == null || authToken.length() <= 0) {
            success = false;
        } else {
            success = true;
        }
        this.mAuthTask = null;
        hideProgress();
        if (!success) {
            String desc = user.getDesc();
            if (TextUtils.isEmpty(desc)) {
                Resources resources = getResources();
                MyResource myResource = this.mMyResource;
                desc = resources.getString(MyResource.getString("zy_login_server_exception"));
            }
            Toast.makeText(getApplicationContext(), desc.trim(), 1).show();
            finish();
        } else if (TextUtils.isEmpty(user.getUsername())) {
            MyResource myResource2 = this.mMyResource;
            Toast.makeText(this, MyResource.getString("zy_tip_login_fail"), 0).show();
        } else if (this.mConfirmCredentials.booleanValue()) {
            finishConfirmCredentials(success);
        } else {
            finishLogin(authToken);
        }
    }

    public void onAuthenticationCancel() {
        this.mAuthTask = null;
        hideProgress();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onDestroy() {
        if (this.smsReceiver != null) {
            unregisterReceiver(this.smsReceiver);
        }
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

    private boolean valid(String str) {
        return !str.matches("^[\\da-zA-Z]*$");
    }

    public void finish() {
        super.finish();
    }
}
