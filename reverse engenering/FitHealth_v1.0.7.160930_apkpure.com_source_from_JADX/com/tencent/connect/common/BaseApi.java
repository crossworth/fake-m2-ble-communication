package com.tencent.connect.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.C0806b;
import com.tencent.open.C1730g;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.utils.ServerSetting;
import com.tencent.utils.SystemUtils;
import com.tencent.utils.Util;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public abstract class BaseApi {
    protected static final String ACTION_CHECK_TOKEN = "action_check_token";
    protected static final String ACTIVITY_AGENT = "com.tencent.open.agent.AgentActivity";
    protected static final String ACTIVITY_ENCRY_TOKEN = "com.tencent.open.agent.EncryTokenActivity";
    protected static final String DEFAULT_PF = "openmobile_android";
    private static final String KEY_REQUEST_CODE = "key_request_code";
    private static final int MSG_COMPLETE = 0;
    protected static final String PARAM_ENCRY_EOKEN = "encry_token";
    protected static final String PLATFORM = "desktop_m_qq";
    protected static final String PREFERENCE_PF = "pfStore";
    private static final String TAG = BaseApi.class.getName();
    protected static final String VERSION = "android";
    public static String businessId = null;
    public static String installChannel = null;
    public static boolean isOEM = false;
    public static String registerChannel = null;
    protected static int sRequestCode = 1000;
    protected Intent mActivityIntent;
    protected Context mContext;
    protected ProgressDialog mProgressDialog;
    protected QQAuth mQQAuth;
    protected List<ApiTask> mTaskList;
    protected QQToken mToken;
    protected IUiListener mUiListener;

    /* compiled from: ProGuard */
    public class ApiTask {
        public IUiListener mListener;
        public int mRequestCode;

        public ApiTask(int i, IUiListener iUiListener) {
            this.mRequestCode = i;
            this.mListener = iUiListener;
        }
    }

    /* compiled from: ProGuard */
    public class TempRequestListener implements IRequestListener {
        private Handler mHandler;
        private IUiListener mListener;

        public TempRequestListener(IUiListener iUiListener) {
            this.mListener = iUiListener;
            this.mHandler = new Handler(BaseApi.this.mContext.getMainLooper(), BaseApi.this) {
                public void handleMessage(Message message) {
                    if (message.what == 0) {
                        TempRequestListener.this.mListener.onComplete((JSONObject) message.obj);
                    } else {
                        TempRequestListener.this.mListener.onError(new UiError(message.what, (String) message.obj, null));
                    }
                }
            };
        }

        public void onComplete(JSONObject jSONObject) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = jSONObject;
            obtainMessage.what = 0;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onIOException(IOException iOException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = iOException.getMessage();
            obtainMessage.what = -2;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = malformedURLException.getMessage();
            obtainMessage.what = -3;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onJSONException(JSONException jSONException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = jSONException.getMessage();
            obtainMessage.what = -4;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = connectTimeoutException.getMessage();
            obtainMessage.what = -7;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = socketTimeoutException.getMessage();
            obtainMessage.what = -8;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = networkUnavailableException.getMessage();
            obtainMessage.what = -10;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = httpStatusException.getMessage();
            obtainMessage.what = -9;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onUnknowException(Exception exception) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = exception.getMessage();
            obtainMessage.what = -6;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public BaseApi(Context context, QQAuth qQAuth, QQToken qQToken) {
        this.mTaskList = null;
        this.mActivityIntent = null;
        this.mUiListener = null;
        this.mContext = context;
        this.mQQAuth = qQAuth;
        this.mToken = qQToken;
        this.mTaskList = new ArrayList();
    }

    public BaseApi(Context context, QQToken qQToken) {
        this(context, null, qQToken);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        IUiListener iUiListener = null;
        for (ApiTask apiTask : this.mTaskList) {
            if (apiTask.mRequestCode == i) {
                iUiListener = apiTask.mListener;
                this.mTaskList.remove(apiTask);
                break;
            }
        }
        if (iUiListener != null) {
            if (i2 == -1) {
                int intExtra = intent.getIntExtra(Constants.KEY_ERROR_CODE, 0);
                if (intExtra == 0) {
                    String stringExtra = intent.getStringExtra(Constants.KEY_RESPONSE);
                    if (stringExtra != null) {
                        try {
                            iUiListener.onComplete(Util.parseJson(stringExtra));
                        } catch (Throwable e) {
                            iUiListener.onError(new UiError(-4, Constants.MSG_JSON_ERROR, stringExtra));
                            C1711d.m4637a("openSDK_LOG", "OpenUi, onActivityResult, json error", e);
                        }
                    } else {
                        C1711d.m4638b("openSDK_LOG", "OpenUi, onActivityResult, onComplete");
                        iUiListener.onComplete(new JSONObject());
                    }
                } else {
                    C1711d.m4640d("openSDK_LOG", "OpenUi, onActivityResult, onError = " + intExtra + "");
                    iUiListener.onError(new UiError(intExtra, intent.getStringExtra(Constants.KEY_ERROR_MSG), intent.getStringExtra(Constants.KEY_ERROR_DETAIL)));
                }
            } else {
                C1711d.m4638b("openSDK_LOG", "OpenUi, onActivityResult, Constants.ACTIVITY_CANCEL");
                iUiListener.onCancel();
            }
            C1711d.m4641f().mo2081b();
        }
    }

    Intent getActivityIntent() {
        return this.mActivityIntent;
    }

    protected Bundle composeCGIParams() {
        Bundle bundle = new Bundle();
        bundle.putString("format", "json");
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        bundle.putString("status_version", VERSION.SDK);
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        if (this.mToken != null && this.mToken.isSessionValid()) {
            bundle.putString("access_token", this.mToken.getAccessToken());
            bundle.putString("oauth_consumer_key", this.mToken.getAppId());
            bundle.putString("openid", this.mToken.getOpenId());
        }
        bundle.putString("appid_for_getting_config", this.mToken.getAppId());
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("pfStore", 0);
        if (isOEM) {
            bundle.putString(Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + SocializeConstants.OP_DIVIDER_MINUS + VERSION + SocializeConstants.OP_DIVIDER_MINUS + registerChannel + SocializeConstants.OP_DIVIDER_MINUS + businessId);
        } else {
            bundle.putString(Constants.PARAM_PLATFORM_ID, sharedPreferences.getString(Constants.PARAM_PLATFORM_ID, "openmobile_android"));
        }
        return bundle;
    }

    protected Bundle composeActivityParams() {
        Bundle bundle = new Bundle();
        bundle.putString("appid", this.mToken.getAppId());
        if (this.mToken.isSessionValid()) {
            bundle.putString(Constants.PARAM_KEY_STR, this.mToken.getAccessToken());
            bundle.putString(Constants.PARAM_KEY_TYPE, "0x80");
        }
        String openId = this.mToken.getOpenId();
        if (openId != null) {
            bundle.putString("hopenid", openId);
        }
        bundle.putString(Constants.PARAM_PLATFORM, "androidqz");
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("pfStore", 0);
        if (isOEM) {
            bundle.putString(Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + SocializeConstants.OP_DIVIDER_MINUS + VERSION + SocializeConstants.OP_DIVIDER_MINUS + registerChannel + SocializeConstants.OP_DIVIDER_MINUS + businessId);
        } else {
            bundle.putString(Constants.PARAM_PLATFORM_ID, sharedPreferences.getString(Constants.PARAM_PLATFORM_ID, "openmobile_android"));
            bundle.putString(Constants.PARAM_PLATFORM_ID, "openmobile_android");
        }
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        return bundle;
    }

    private Intent getAssitIntent() {
        return new Intent(this.mContext, AssistActivity.class);
    }

    protected void startAssitActivity(Activity activity, IUiListener iUiListener) {
        AssistActivity.setApiObject(this);
        int i = sRequestCode;
        sRequestCode = i + 1;
        this.mActivityIntent.putExtra("key_request_code", i);
        this.mTaskList.add(new ApiTask(i, iUiListener));
        activity.startActivity(getAssitIntent());
    }

    protected boolean hasActivityForIntent() {
        if (this.mActivityIntent != null) {
            return SystemUtils.isActivityExist(this.mContext, this.mActivityIntent);
        }
        return false;
    }

    protected Intent getTargetActivityIntent(String str) {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, str);
        Intent intent2 = new Intent();
        intent2.setClassName(Constants.PACKAGE_QQ, str);
        if (SystemUtils.isActivityExist(this.mContext, intent2)) {
            return intent2;
        }
        if (!SystemUtils.isActivityExist(this.mContext, intent) || SystemUtils.compareVersion(SystemUtils.getAppVersionName(this.mContext, Constants.PACKAGE_QZONE), "3.4") < 0) {
            return null;
        }
        if (SystemUtils.isAppSignatureValid(this.mContext, intent.getComponent().getPackageName(), Constants.SIGNATRUE_QZONE)) {
            return intent;
        }
        return null;
    }

    protected void handleDownloadLastestQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        final Bundle bundle2 = new Bundle(bundle);
        final C0806b c0806b = new C0806b(activity);
        c0806b.m2575a("请安装最新版QQ");
        c0806b.m2577b("无法使用该功能，是否下载QQ手机版？");
        c0806b.m2579d("下载");
        c0806b.setCancelable(true);
        c0806b.m2578c("取消");
        final Activity activity2 = activity;
        final IUiListener iUiListener2 = iUiListener;
        final Activity activity3 = activity;
        c0806b.m2574a(new OnClickListener() {

            /* compiled from: ProGuard */
            class C17181 implements IUiListener {
                C17181() {
                }

                public void onError(UiError uiError) {
                    C1711d.m4638b(BaseApi.TAG, "-->handleDownloadLastestQQ onError =" + uiError.toString());
                    if (BaseApi.this.mProgressDialog != null) {
                        BaseApi.this.mProgressDialog.dismiss();
                    }
                    iUiListener2.onError(uiError);
                }

                public void onComplete(Object obj) {
                    C1711d.m4638b(BaseApi.TAG, "-->handleDownloadLastestQQ onComplete =" + obj);
                    if (BaseApi.this.mProgressDialog != null) {
                        BaseApi.this.mProgressDialog.dismiss();
                    }
                    String str = null;
                    try {
                        str = ((JSONObject) obj).getString("fixpackageurl");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    activity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }

                public void onCancel() {
                    C1711d.m4638b(BaseApi.TAG, "-->handleDownloadLastestQQ onCancel");
                    if (BaseApi.this.mProgressDialog != null) {
                        BaseApi.this.mProgressDialog.dismiss();
                    }
                    iUiListener2.onCancel();
                }
            }

            public void onClick(View view) {
                c0806b.dismiss();
                BaseApi.this.showProgressDialog(activity2, "", "正在获取下载地址...");
                bundle2.putString("getinfo_mask", "1");
                IRequestListener c1730g = new C1730g(new C17181());
                bundle2.putString("appid", Constants.QQ_APPID);
                HttpUtils.requestAsync(BaseApi.this.mToken, BaseApi.this.mContext, ServerSetting.getInstance().getEnvUrl(activity3, ServerSetting.CGI_FETCH_QQ_URL), bundle2, "GET", c1730g);
            }
        });
        c0806b.m2576b(new OnClickListener() {
            public void onClick(View view) {
                c0806b.dismiss();
            }
        });
        c0806b.show();
    }

    protected void showProgressDialog(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            str = "请稍候";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "正在加载...";
        }
        this.mProgressDialog = ProgressDialog.show(context, str, str2);
        this.mProgressDialog.setCancelable(true);
    }

    protected Intent getAgentIntent() {
        return getTargetActivityIntent(ACTIVITY_AGENT);
    }

    protected Intent getAgentIntentWithTarget(String str) {
        Intent intent = new Intent();
        Intent targetActivityIntent = getTargetActivityIntent(str);
        if (targetActivityIntent == null || targetActivityIntent.getComponent() == null) {
            return null;
        }
        intent.setClassName(targetActivityIntent.getComponent().getPackageName(), ACTIVITY_AGENT);
        return intent;
    }
}
