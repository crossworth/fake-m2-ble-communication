package com.zhuoyi.account.authenticator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
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
import android.widget.TextView;
import android.widget.Toast;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.demo.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.open.SocialConstants;
import com.tencent.sample.activitys.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.handler.TwitterPreferences;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyi.account.FindCodeActivity_new;
import com.zhuoyi.account.FindCodeMailActivity;
import com.zhuoyi.account.IAccountListener;
import com.zhuoyi.account.RegisterActivity_new;
import com.zhuoyi.account.RegisterMailBoxActivity;
import com.zhuoyi.account.ZyAccount;
import com.zhuoyi.account.constant.UrlConstant;
import com.zhuoyi.account.model.User;
import com.zhuoyi.account.netutil.HttpOperation;
import com.zhuoyi.account.util.DeviceUtil;
import com.zhuoyi.account.util.GetPublicParams;
import com.zhuoyi.account.util.MD5Util;
import com.zhuoyi.account.util.MyResource;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class AuthenticatorActivity extends BaseActivity {
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
    public static final String PARAM_CONFIRM_CREDENTIALS = "confirmCredentials";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_USERNAME = "username";
    public static AuthenticatorActivity instance;
    public static IAccountListener mAccountListener;
    public static QQAuth mQQAuth;
    public static String mSinaAppid;
    public static Tencent mTencent;
    public static String mTencentAppid;
    AuthInfo authInfo = null;
    private LinearLayout code_linearLayout;
    private TextView forget_code_btn;
    private String jsonStringer;
    private Button login_btn_qq;
    private Button login_btn_sina;
    private AccountManager mAccountManager;
    private WeiboAuthListener mAuthListener;
    private UserLoginTask mAuthTask = null;
    private Boolean mConfirmCredentials = Boolean.valueOf(false);
    private UserInfo mInfo;
    private String mPassword;
    private EditText mPasswordEdit;
    private ProgressDialog mProgressDialog = null;
    protected boolean mRequestNewAccount = false;
    private String mUsername;
    private EditText mUsernameEdit;
    private LinearLayout name_linearLayout;
    private TextView register_btn;
    private User user = new User();

    class C10421 implements OnFocusChangeListener {
        C10421() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
        }
    }

    class C10432 implements OnFocusChangeListener {
        C10432() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                EditText access$100 = AuthenticatorActivity.this.mPasswordEdit;
                Resources resources = AuthenticatorActivity.this.getResources();
                MyResource myResource = AuthenticatorActivity.this.mMyResource;
                access$100.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(MyResource.getDrawable("zy_input_icon_click")), null);
                return;
            }
            access$100 = AuthenticatorActivity.this.mPasswordEdit;
            resources = AuthenticatorActivity.this.getResources();
            myResource = AuthenticatorActivity.this.mMyResource;
            access$100.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(MyResource.getDrawable("zy_input_icon")), null);
        }
    }

    class C10443 implements OnClickListener {
        C10443() {
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(AuthenticatorActivity.this, RegisterMailBoxActivity.class);
            RegisterMailBoxActivity.mAccountListener = AuthenticatorActivity.mAccountListener;
            intent.putExtra("isFromLogin", true);
            intent.putExtra("username", AuthenticatorActivity.this.mUsernameEdit.getText() + "");
            AuthenticatorActivity.this.startActivity(intent);
        }
    }

    class C10454 implements OnClickListener {
        C10454() {
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(AuthenticatorActivity.this, FindCodeMailActivity.class);
            FindCodeMailActivity.mAccountListener = AuthenticatorActivity.mAccountListener;
            intent.putExtra("isFromLogin", true);
            intent.putExtra("username", AuthenticatorActivity.this.mUsernameEdit.getText() + "");
            AuthenticatorActivity.this.startActivity(intent);
        }
    }

    class C10465 implements OnClickListener {
        C10465() {
        }

        public void onClick(View v) {
            AuthenticatorActivity.this.onClickLogin();
        }
    }

    class C10476 implements OnClickListener {
        C10476() {
        }

        public void onClick(View v) {
            if (AuthenticatorActivity.this.authInfo != null) {
                new WeiboAuth(AuthenticatorActivity.this, AuthenticatorActivity.this.authInfo).anthorize(AuthenticatorActivity.this.mAuthListener);
            }
        }
    }

    class C10487 implements OnCancelListener {
        C10487() {
        }

        public void onCancel(DialogInterface dialog) {
            if (AuthenticatorActivity.this.mAuthTask != null) {
                AuthenticatorActivity.this.mAuthTask.cancel(true);
            }
        }
    }

    class MyDialog extends Dialog {
        private String type;

        class C10501 implements OnClickListener {
            C10501() {
            }

            public void onClick(View v) {
                Intent intent = new Intent();
                if (MyDialog.this.type.equals("register")) {
                    intent.setClass(AuthenticatorActivity.this, RegisterMailBoxActivity.class);
                    RegisterMailBoxActivity.mAccountListener = AuthenticatorActivity.mAccountListener;
                } else {
                    intent.setClass(AuthenticatorActivity.this, FindCodeMailActivity.class);
                    FindCodeMailActivity.mAccountListener = AuthenticatorActivity.mAccountListener;
                }
                intent.putExtra("isFromLogin", true);
                intent.putExtra("username", AuthenticatorActivity.this.mUsernameEdit.getText() + "");
                AuthenticatorActivity.this.startActivity(intent);
                MyDialog.this.dismiss();
            }
        }

        class C10512 implements OnClickListener {
            C10512() {
            }

            public void onClick(View v) {
                Intent intent = new Intent();
                if (MyDialog.this.type.equals("register")) {
                    intent.setClass(AuthenticatorActivity.this, RegisterActivity_new.class);
                    RegisterActivity_new.mAccountListener = AuthenticatorActivity.mAccountListener;
                } else {
                    intent.setClass(AuthenticatorActivity.this, FindCodeActivity_new.class);
                    FindCodeActivity_new.mAccountListener = AuthenticatorActivity.mAccountListener;
                }
                intent.putExtra("isFromLogin", true);
                intent.putExtra("username", AuthenticatorActivity.this.mUsernameEdit.getText() + "");
                AuthenticatorActivity.this.startActivity(intent);
                MyDialog.this.dismiss();
            }
        }

        public MyDialog(Context context, int theme, String type) {
            super(context, theme);
            this.type = type;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(1);
            MyResource myResource = AuthenticatorActivity.this.mMyResource;
            setContentView(MyResource.getLayout("zy_layout_dialog"));
            myResource = AuthenticatorActivity.this.mMyResource;
            TextView textView = (TextView) findViewById(MyResource.getId("dialog_title"));
            AuthenticatorActivity authenticatorActivity = AuthenticatorActivity.this;
            MyResource myResource2 = AuthenticatorActivity.this.mMyResource;
            textView.setText(authenticatorActivity.getString(MyResource.getString("zy_choose_findcode")));
            myResource = AuthenticatorActivity.this.mMyResource;
            Button btnMail = (Button) findViewById(MyResource.getId("tip_dialog_mail_button"));
            myResource = AuthenticatorActivity.this.mMyResource;
            Button btnPhone = (Button) findViewById(MyResource.getId("tip_dialog_phone_button"));
            btnMail.setOnClickListener(new C10501());
            btnPhone.setOnClickListener(new C10512());
        }
    }

    public class OtherUserLoginTask extends AsyncTask<Void, Void, String> {
        private String data;
        private String loginResult;
        private String utype;

        public OtherUserLoginTask(String data, String utype) {
            this.data = data;
            this.utype = utype;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(Void... params) {
            String str = null;
            Map<String, String> loginParams = new HashMap();
            String devinfo = DeviceUtil.getDeviceInfo(AuthenticatorActivity.this.getApplicationContext());
            String signString = MD5Util.md5(AuthenticatorActivity.this.user.getUID() + AuthenticatorActivity.this.user.getTOKEN() + this.utype + this.data + devinfo + UrlConstant.SIGNKEY);
            loginParams.put("uid", AuthenticatorActivity.this.user.getUID());
            loginParams.put("passwd", AuthenticatorActivity.this.user.getTOKEN());
            loginParams.put("utype", this.utype);
            loginParams.put("data", this.data);
            loginParams.put("sign", signString);
            loginParams.put("devinfo", devinfo);
            this.loginResult = HttpOperation.postRequest(UrlConstant.AUTH, loginParams);
            try {
                JSONObject jsonObject = new JSONObject(this.loginResult);
                int result = jsonObject.getInt("result");
                if (result == 0) {
                    User access$700 = AuthenticatorActivity.this.user;
                    String string = jsonObject.has("nickname") ? jsonObject.getString("nickname") : TextUtils.isEmpty(AuthenticatorActivity.this.user.getUsername()) ? this.utype : AuthenticatorActivity.this.user.getUsername();
                    access$700.setUsername(string);
                    access$700 = AuthenticatorActivity.this.user;
                    if (jsonObject.has(TwitterPreferences.TOKEN)) {
                        string = jsonObject.getString(TwitterPreferences.TOKEN);
                    } else {
                        string = null;
                    }
                    access$700.setTOKEN(string);
                    access$700 = AuthenticatorActivity.this.user;
                    if (jsonObject.has("openid")) {
                        string = jsonObject.getString("openid");
                    } else {
                        string = null;
                    }
                    access$700.setOpenid(string);
                    AuthenticatorActivity.this.user.setRecode(jsonObject.has("score") ? jsonObject.getInt("score") : 0);
                    access$700 = AuthenticatorActivity.this.user;
                    if (jsonObject.has("avatar")) {
                        string = jsonObject.getString("avatar");
                    } else {
                        string = null;
                    }
                    access$700.setLogoUrl(string);
                    access$700 = AuthenticatorActivity.this.user;
                    if (jsonObject.has(SocializeProtocolConstants.PROTOCOL_KEY_GENDER)) {
                        string = jsonObject.getString(SocializeProtocolConstants.PROTOCOL_KEY_GENDER);
                    } else {
                        string = null;
                    }
                    access$700.setGender(string);
                }
                AuthenticatorActivity.this.user.setRegtype(this.utype);
                AuthenticatorActivity.this.user.setResult(result);
                User access$7002 = AuthenticatorActivity.this.user;
                if (jsonObject.has(SocialConstants.PARAM_APP_DESC)) {
                    str = jsonObject.getString(SocialConstants.PARAM_APP_DESC);
                }
                access$7002.setDesc(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.loginResult;
        }

        protected void onPostExecute(String authToken) {
            if (AuthenticatorActivity.this.user.getResult() == 0) {
                AuthenticatorActivity.this.onAuthenticationResult(AuthenticatorActivity.this.user);
            } else if (AuthenticatorActivity.this.user.getResult() < 0 || TextUtils.isEmpty(authToken)) {
                AuthenticatorActivity.this.hideProgress();
                String desc = AuthenticatorActivity.this.user.getDesc();
                if (desc != null) {
                    if (desc.equals("用户不存在")) {
                        desc = "user does not exist";
                    }
                    Toast.makeText(AuthenticatorActivity.this, desc, 1).show();
                    return;
                }
                Context context = AuthenticatorActivity.this;
                MyResource myResource = AuthenticatorActivity.this.mMyResource;
                Toast.makeText(context, MyResource.getString("zy_tip_login_fail"), 1).show();
            }
        }

        protected void onCancelled() {
            AuthenticatorActivity.this.onAuthenticationCancel();
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {
        private String loginResult;
        private String password;
        private String username;
        private String utype = "mail";

        public UserLoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            AuthenticatorActivity.this.showProgress();
        }

        protected String doInBackground(Void... params) {
            String str = null;
            Map<String, String> loginParams = new HashMap();
            String passwdMD5 = MD5Util.md5(this.password);
            String devinfo = DeviceUtil.getDeviceInfo(AuthenticatorActivity.this.getApplicationContext());
            loginParams.put("uid", this.username);
            loginParams.put("passwd", passwdMD5);
            loginParams.put("utype", this.utype);
            loginParams.put("devinfo", devinfo);
            loginParams.put("sign", MD5Util.md5(this.username + passwdMD5 + this.utype + devinfo + UrlConstant.SIGNKEY));
            this.loginResult = null;
            this.loginResult = HttpOperation.postRequest(UrlConstant.LOGIN, loginParams);
            Log.i("chenxin", "loginResult:" + this.loginResult);
            try {
                JSONObject jsonObject = new JSONObject(this.loginResult);
                int result = jsonObject.getInt("result");
                if (result == 0) {
                    String string;
                    AuthenticatorActivity.this.user.setUsername(jsonObject.has("nickname") ? jsonObject.getString("nickname") : this.username);
                    AuthenticatorActivity.this.user.setPassword(passwdMD5);
                    User access$700 = AuthenticatorActivity.this.user;
                    if (jsonObject.has(TwitterPreferences.TOKEN)) {
                        string = jsonObject.getString(TwitterPreferences.TOKEN);
                    } else {
                        string = null;
                    }
                    access$700.setTOKEN(string);
                    AuthenticatorActivity.this.user.setUID(jsonObject.has("username") ? jsonObject.getString("username") : this.username);
                    access$700 = AuthenticatorActivity.this.user;
                    if (jsonObject.has("openid")) {
                        string = jsonObject.getString("openid");
                    } else {
                        string = null;
                    }
                    access$700.setOpenid(string);
                    access$700 = AuthenticatorActivity.this.user;
                    if (jsonObject.has("expire")) {
                        string = jsonObject.getString("expire");
                    } else {
                        string = null;
                    }
                    access$700.setExpires_in(string);
                    AuthenticatorActivity.this.user.setRecode(jsonObject.has("score") ? jsonObject.getInt("score") : 0);
                    access$700 = AuthenticatorActivity.this.user;
                    if (jsonObject.has("avatar")) {
                        string = jsonObject.getString("avatar");
                    } else {
                        string = null;
                    }
                    access$700.setLogoUrl(string);
                }
                AuthenticatorActivity.this.user.setRegtype("zhuoyou");
                AuthenticatorActivity.this.user.setResult(result);
                User access$7002 = AuthenticatorActivity.this.user;
                if (jsonObject.has(SocialConstants.PARAM_APP_DESC)) {
                    str = jsonObject.getString(SocialConstants.PARAM_APP_DESC);
                }
                access$7002.setDesc(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.loginResult;
        }

        protected void onPostExecute(String authToken) {
            AuthenticatorActivity.this.onAuthenticationResult(AuthenticatorActivity.this.user);
        }

        protected void onCancelled() {
            AuthenticatorActivity.this.onAuthenticationCancel();
        }
    }

    class C18359 implements IUiListener {
        C18359() {
        }

        public void onError(UiError e) {
            AuthenticatorActivity.this.hideProgress();
            Util.toastMessage(AuthenticatorActivity.this, e.errorMessage);
        }

        public void onComplete(Object response) {
            String str = null;
            JSONObject json = (JSONObject) response;
            if (response != null) {
                try {
                    AuthenticatorActivity.this.user.setUsername(json.has("nickname") ? json.getString("nickname") : null);
                    User access$700 = AuthenticatorActivity.this.user;
                    if (json.has("figureurl_qq_2")) {
                        str = json.getString("figureurl_qq_2");
                    }
                    access$700.setLogoUrl(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            new OtherUserLoginTask(response.toString(), "openqq").execute(new Void[0]);
        }

        public void onCancel() {
            AuthenticatorActivity.this.hideProgress();
        }
    }

    private class AuthListener implements WeiboAuthListener {
        private AuthListener() {
        }

        public void onComplete(Bundle values) {
            AuthenticatorActivity.this.showProgress();
            Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
            if (accessToken != null && accessToken.isSessionValid()) {
                AccessTokenKeeper.writeAccessToken(AuthenticatorActivity.this.getApplicationContext(), accessToken);
                final String uid = accessToken.getUid();
                final String token = accessToken.getToken();
                AuthenticatorActivity.this.user.setUID(uid);
                AuthenticatorActivity.this.user.setExpires_in(String.valueOf(accessToken.getExpiresTime()));
                AuthenticatorActivity.this.user.setTOKEN(token);
                new Thread() {
                    public void run() {
                        StringBuilder sBuilder = new StringBuilder();
                        sBuilder.append("https://api.weibo.com/2/users/show.json?").append("source=").append(AuthenticatorActivity.mSinaAppid).append("&uid=").append(uid).append("&access_token=").append(token);
                        String jsonString = null;
                        try {
                            jsonString = HttpOperation.getRequest(sBuilder.toString());
                            if (jsonString != null) {
                                try {
                                    JSONObject response1 = new JSONObject(jsonString);
                                    AuthenticatorActivity.this.user.setUsername(response1.getString("screen_name"));
                                    AuthenticatorActivity.this.user.setLogoUrl(response1.getString(SocializeProtocolConstants.PROTOCOL_KEY_FRIENDS_ICON));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        new OtherUserLoginTask(jsonString, "openweibo").execute(new Void[0]);
                    }
                }.start();
            }
        }

        public void onWeiboException(WeiboException e) {
            Toast.makeText(AuthenticatorActivity.this, e.getMessage(), 0).show();
        }

        public void onCancel() {
            Context context = AuthenticatorActivity.this;
            MyResource myResource = AuthenticatorActivity.this.mMyResource;
            Toast.makeText(context, MyResource.getString("zy_weibosdk_demo_toast_auth_canceled"), 0).show();
        }
    }

    private class BaseUiListener implements IUiListener {
        private BaseUiListener() {
        }

        public void onComplete(Object response) {
            String str = null;
            JSONObject values = (JSONObject) response;
            try {
                String string;
                AuthenticatorActivity.this.user.setExpires_in(values.has("expires_in") ? values.getString("expires_in") : null);
                User access$700 = AuthenticatorActivity.this.user;
                if (values.has("access_token")) {
                    string = values.getString("access_token");
                } else {
                    string = null;
                }
                access$700.setTOKEN(string);
                access$700 = AuthenticatorActivity.this.user;
                if (values.has("openid")) {
                    string = values.getString("openid");
                } else {
                    string = null;
                }
                access$700.setUID(string);
                User access$7002 = AuthenticatorActivity.this.user;
                if (values.has("pfkey")) {
                    str = values.getString("pfkey");
                }
                access$7002.setOpenKey(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            doComplete(values);
        }

        protected void doComplete(JSONObject values) {
        }

        public void onError(UiError e) {
            AuthenticatorActivity.this.hideProgress();
            Util.toastMessage(AuthenticatorActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        public void onCancel() {
            AuthenticatorActivity.this.hideProgress();
            Activity activity = AuthenticatorActivity.this;
            AuthenticatorActivity authenticatorActivity = AuthenticatorActivity.this;
            MyResource myResource = AuthenticatorActivity.this.mMyResource;
            Util.toastMessage(activity, authenticatorActivity.getText(MyResource.getString("zy_weibosdk_demo_toast_auth_canceled")).toString());
            Util.dismissDialog();
        }
    }

    class C20188 extends BaseUiListener {
        C20188() {
            super();
        }

        protected void doComplete(JSONObject values) {
            AuthenticatorActivity.this.updateUserInfo();
        }

        public void onError(UiError e) {
            AuthenticatorActivity.this.hideProgress();
            Activity activity = AuthenticatorActivity.this;
            AuthenticatorActivity authenticatorActivity = AuthenticatorActivity.this;
            MyResource myResource = AuthenticatorActivity.this.mMyResource;
            Util.toastMessage(activity, authenticatorActivity.getText(MyResource.getString("zy_weibosdk_demo_toast_auth_failed")).toString());
        }
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        instance = this;
        this.mAccountManager = AccountManager.get(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mTencentAppid = bundle.getString(ZyAccount.TENCENT_APP_ID);
        mSinaAppid = bundle.getString(ZyAccount.SINA_APP_ID);
        Context ctxContext = getApplicationContext();
        try {
            if (mQQAuth == null) {
                mQQAuth = QQAuth.createInstance(mTencentAppid, ctxContext);
            }
            if (mTencent == null) {
                mTencent = Tencent.createInstance(mTencentAppid, ctxContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.authInfo = new AuthInfo(this, mSinaAppid, "https://api.weibo.com/oauth2/default.html", "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");
        this.mAuthListener = new AuthListener();
        MyResource myResource = this.mMyResource;
        setBarTitle(getString(MyResource.getString("zy_account_login")));
        this.mUsername = intent.getStringExtra("username");
        this.mRequestNewAccount = this.mUsername == null;
        myResource = this.mMyResource;
        setContentView(MyResource.getLayout("zy_layout_login_new"));
        setViews();
        if (!TextUtils.isEmpty(this.mUsername)) {
            this.mUsernameEdit.setText(this.mUsername);
        }
    }

    private void setViews() {
        MyResource myResource = this.mMyResource;
        this.register_btn = (TextView) findViewById(MyResource.getId("zy_register_btn"));
        myResource = this.mMyResource;
        this.forget_code_btn = (TextView) findViewById(MyResource.getId("zy_forget_code_btn"));
        myResource = this.mMyResource;
        this.login_btn_qq = (Button) findViewById(MyResource.getId("zy_login_btn_qq"));
        myResource = this.mMyResource;
        this.login_btn_sina = (Button) findViewById(MyResource.getId("zy_login_btn_sina"));
        myResource = this.mMyResource;
        this.mUsernameEdit = (EditText) findViewById(MyResource.getId("zy_login_name"));
        myResource = this.mMyResource;
        this.mPasswordEdit = (EditText) findViewById(MyResource.getId("zy_login_code"));
        myResource = this.mMyResource;
        this.name_linearLayout = (LinearLayout) findViewById(MyResource.getId("zy_name_linearLayout"));
        myResource = this.mMyResource;
        this.code_linearLayout = (LinearLayout) findViewById(MyResource.getId("zy_code_linearLayout"));
        this.mUsernameEdit.setFocusable(true);
        this.mUsernameEdit.setOnFocusChangeListener(new C10421());
        this.mPasswordEdit.setOnFocusChangeListener(new C10432());
        this.register_btn.setOnClickListener(new C10443());
        this.forget_code_btn.setOnClickListener(new C10454());
        this.login_btn_qq.setOnClickListener(new C10465());
        this.login_btn_sina.setOnClickListener(new C10476());
    }

    protected Dialog onCreateDialog(int id, Bundle args) {
        ProgressDialog dialog = new ProgressDialog(this);
        MyResource myResource = this.mMyResource;
        dialog.setMessage(getString(MyResource.getString("zy_authenticate_login")));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new C10487());
        this.mProgressDialog = dialog;
        return dialog;
    }

    public void handleLogin(View view) {
        if (this.mRequestNewAccount) {
            this.mUsername = this.mUsernameEdit.getText().toString();
        }
        this.mPassword = this.mPasswordEdit.getText().toString();
        EditText editText;
        Resources resources;
        MyResource myResource;
        if (TextUtils.isEmpty(this.mUsername)) {
            this.mUsernameEdit.requestFocus();
            editText = this.mUsernameEdit;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_tip_username_none")).toString());
        } else if (TextUtils.isEmpty(this.mPassword)) {
            this.mPasswordEdit.requestFocus();
            editText = this.mPasswordEdit;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_tip_password_none")).toString());
        } else if (this.mPassword.length() < 6 || this.mPassword.length() > 20) {
            this.mPasswordEdit.requestFocus();
            editText = this.mPasswordEdit;
            resources = getResources();
            myResource = this.mMyResource;
            editText.setError(resources.getText(MyResource.getString("zy_tip_password_not_right_length")).toString());
        } else if (GetPublicParams.getAvailableNetWorkType(this) == -1) {
            MyResource myResource2 = this.mMyResource;
            Toast.makeText(this, MyResource.getString("zy_tip_network_wrong"), 0).show();
        } else {
            this.mAuthTask = new UserLoginTask(this.mUsername, this.mPassword);
            this.mAuthTask.execute(new Void[0]);
        }
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
        Object obj = null;
        try {
            this.jsonStringer = null;
            JSONStringer key = new JSONStringer().object().key("username").value(TextUtils.isEmpty(this.user.getUsername()) ? this.mUsernameEdit.getText().toString().trim() : this.user.getUsername()).key("password").value(TextUtils.isEmpty(this.user.getPassword()) ? this.mPasswordEdit.getText().toString().trim() : this.user.getPassword()).key("UID").value(this.user.getUID()).key("openid").value(this.user.getOpenid()).key("OpenKey").value(this.user.getOpenKey()).key("TOKEN").value(this.user.getTOKEN()).key("regtype").value(this.user.getRegtype()).key("expires_in").value(this.user.getExpires_in()).key("recode").value(TextUtils.isEmpty(new StringBuilder().append(this.user.getRecode()).append("").toString()) ? 100 : (long) this.user.getRecode()).key("logoUrl");
            if (!TextUtils.isEmpty(this.user.getLogoUrl())) {
                obj = this.user.getLogoUrl();
            }
            this.jsonStringer = key.value(obj).endObject().toString();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        hideProgress();
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
        if (!success) {
            hideProgress();
            String desc = user.getDesc();
            if (TextUtils.isEmpty(desc)) {
                Resources resources = getResources();
                MyResource myResource = this.mMyResource;
                user.setDesc(resources.getString(MyResource.getString("zy_tip_login_fail")));
            } else if (desc.equals("用户不存在")) {
                desc = "user does not exist";
            } else if (desc.contains("密码错误") && desc.contains("4")) {
                desc = "Password is mistake, enter the wrong 4 times that account will lock";
            } else if (desc.contains("密码错误") && desc.contains("3")) {
                desc = "Password is mistake, enter the wrong 3 times that account will lock";
            } else if (desc.contains("密码错误") && desc.contains("2")) {
                desc = "Password is mistake, enter the wrong 2 times that account will lock";
            } else if (desc.contains("密码错误") && desc.contains("1")) {
                desc = "Password is mistake, enter the wrong 1 times that account will lock";
            } else if (desc.contains("已被锁定")) {
                desc = "Your account has been locked. Please try again later.";
            }
            Toast.makeText(getApplicationContext(), desc, 1).show();
        } else if (TextUtils.isEmpty(user.getUsername())) {
            MyResource myResource2 = this.mMyResource;
            Toast.makeText(this, MyResource.getString("zy_tip_login_fail"), 0).show();
            hideProgress();
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

    private CharSequence getMessage() {
        if (TextUtils.isEmpty(this.mUsername)) {
            return "If no username, then we ask the user to log in using an appropriate service.";
        }
        if (TextUtils.isEmpty(this.mPassword)) {
            return "We have an account but no password";
        }
        return null;
    }

    private void showProgress() {
        showDialog(0);
    }

    private void hideProgress() {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }
    }

    public void finish() {
        super.finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mAccountListener != null) {
            if (TextUtils.isEmpty(this.jsonStringer)) {
                mAccountListener.onCancel();
            } else {
                mAccountListener.onSuccess(this.jsonStringer);
                this.jsonStringer = null;
            }
            mAccountListener = null;
        }
    }

    private void onClickLogin() {
        if (mTencent == null) {
            return;
        }
        if (mQQAuth != null) {
            IUiListener listener = new C20188();
            try {
                showProgress();
                mTencent.setAccessToken(this.user.getTOKEN(), this.user.getExpires_in());
                mTencent.setOpenId(this.user.getOpenid());
                mTencent.login(this, "all", listener);
                return;
            } catch (Exception e) {
                hideProgress();
                e.printStackTrace();
                return;
            }
        }
        updateUserInfo();
        MyResource myResource = this.mMyResource;
        Util.toastMessage(this, getText(MyResource.getString("zy_weibosdk_demo_toast_auth_canceled_again")).toString());
    }

    private void updateUserInfo() {
        if (mQQAuth != null) {
            IUiListener listener = new C18359();
            this.mInfo = new UserInfo(this, mQQAuth.getQQToken());
            this.mInfo.getUserInfo(listener);
            return;
        }
        hideProgress();
        MyResource myResource = this.mMyResource;
        Toast.makeText(this, getString(MyResource.getString("zy_weibosdk_demo_toast_auth_canceled_again")), 0).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
