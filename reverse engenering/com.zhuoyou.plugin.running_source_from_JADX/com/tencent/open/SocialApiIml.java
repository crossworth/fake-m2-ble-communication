package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.C1337e.C1280a;
import com.tencent.open.p035c.C1333b;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.OpenConfig;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class SocialApiIml extends BaseApi {
    private Activity f3986a;

    /* compiled from: ProGuard */
    protected class C1282a implements IUiListener {
        C1283b f3973a;
        final /* synthetic */ SocialApiIml f3974b;

        public C1282a(SocialApiIml socialApiIml, C1283b c1283b) {
            this.f3974b = socialApiIml;
            this.f3973a = c1283b;
        }

        public void onComplete(Object obj) {
            boolean z = false;
            if (obj != null) {
                try {
                    z = ((JSONObject) obj).getBoolean("check_result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.f3974b.m3735b();
            if (z) {
                this.f3974b.m3725a(this.f3974b.f3986a, this.f3973a.f3975a, this.f3973a.f3976b, this.f3973a.f3977c, this.f3973a.f3979e);
                return;
            }
            C1337e.m3929a(this.f3973a.f3977c.getString(SocialConstants.PARAM_IMG_DATA));
            this.f3974b.m3729a(this.f3974b.f3986a, this.f3973a.f3976b, this.f3973a.f3977c, this.f3973a.f3978d, this.f3973a.f3979e);
        }

        public void onError(UiError uiError) {
            this.f3974b.m3735b();
            C1337e.m3929a(this.f3973a.f3977c.getString(SocialConstants.PARAM_IMG_DATA));
            this.f3974b.m3729a(this.f3974b.f3986a, this.f3973a.f3976b, this.f3973a.f3977c, this.f3973a.f3978d, this.f3973a.f3979e);
        }

        public void onCancel() {
            this.f3974b.m3735b();
            C1337e.m3929a(this.f3973a.f3977c.getString(SocialConstants.PARAM_IMG_DATA));
        }
    }

    /* compiled from: ProGuard */
    private static class C1283b {
        Intent f3975a;
        String f3976b;
        Bundle f3977c;
        String f3978d;
        IUiListener f3979e;
    }

    /* compiled from: ProGuard */
    private class C1284c implements IUiListener {
        final /* synthetic */ SocialApiIml f3980a;
        private IUiListener f3981b;
        private String f3982c;
        private String f3983d;
        private Bundle f3984e;
        private Activity f3985f;

        C1284c(SocialApiIml socialApiIml, Activity activity, IUiListener iUiListener, String str, String str2, Bundle bundle) {
            this.f3980a = socialApiIml;
            this.f3981b = iUiListener;
            this.f3982c = str;
            this.f3983d = str2;
            this.f3984e = bundle;
        }

        public void onComplete(Object obj) {
            CharSequence string;
            CharSequence charSequence = null;
            try {
                string = ((JSONObject) obj).getString(SocialConstants.PARAM_ENCRY_EOKEN);
            } catch (Throwable e) {
                e.printStackTrace();
                C1314f.m3868b("openSDK_LOG.SocialApiIml", "OpenApi, EncrytokenListener() onComplete error", e);
                string = charSequence;
            }
            this.f3984e.putString("encrytoken", string);
            this.f3980a.m3730a(this.f3980a.f3986a, this.f3982c, this.f3984e, this.f3983d, this.f3981b);
            if (TextUtils.isEmpty(string)) {
                C1314f.m3867b("openSDK_LOG.SocialApiIml", "The token get from qq or qzone is empty. Write temp token to localstorage.");
                this.f3980a.writeEncryToken(this.f3985f);
            }
        }

        public void onError(UiError uiError) {
            C1314f.m3867b("openSDK_LOG.SocialApiIml", "OpenApi, EncryptTokenListener() onError" + uiError.errorMessage);
            this.f3981b.onError(uiError);
        }

        public void onCancel() {
            this.f3981b.onCancel();
        }
    }

    public SocialApiIml(QQToken qQToken) {
        super(qQToken);
    }

    public SocialApiIml(QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
    }

    public void gift(Activity activity, Bundle bundle, IUiListener iUiListener) {
        m3728a(activity, SocialConstants.ACTION_GIFT, bundle, iUiListener);
    }

    public void ask(Activity activity, Bundle bundle, IUiListener iUiListener) {
        m3728a(activity, SocialConstants.ACTION_ASK, bundle, iUiListener);
    }

    private void m3728a(Activity activity, String str, Bundle bundle, IUiListener iUiListener) {
        this.f3986a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_FRIEND_CHOOSER);
        if (agentIntentWithTarget == null) {
            C1314f.m3870c("openSDK_LOG.SocialApiIml", "--askgift--friend chooser not found");
            agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_ASK_GIFT);
        }
        bundle.putAll(composeActivityParams());
        if (SocialConstants.ACTION_ASK.equals(str)) {
            bundle.putString("type", "request");
        } else if (SocialConstants.ACTION_GIFT.equals(str)) {
            bundle.putString("type", SocialConstants.TYPE_FREEGIFT);
        }
        m3726a(activity, agentIntentWithTarget, str, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), "http://qzs.qq.com/open/mobile/request/sdk_request.html?"), iUiListener, false);
    }

    public void invite(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3986a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_FRIEND_CHOOSER);
        if (agentIntentWithTarget == null) {
            C1314f.m3870c("openSDK_LOG.SocialApiIml", "--invite--friend chooser not found");
            agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_INVITE);
        }
        bundle.putAll(composeActivityParams());
        Activity activity2 = activity;
        m3726a(activity2, agentIntentWithTarget, SocialConstants.ACTION_INVITE, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_INVITE), iUiListener, false);
    }

    public void story(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3986a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_STORY);
        bundle.putAll(composeActivityParams());
        Activity activity2 = activity;
        m3726a(activity2, agentIntentWithTarget, SocialConstants.ACTION_STORY, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_SEND_STORY), iUiListener, false);
    }

    private void m3726a(Activity activity, Intent intent, String str, Bundle bundle, String str2, IUiListener iUiListener, boolean z) {
        Object obj = null;
        C1314f.m3870c("openSDK_LOG.SocialApiIml", "-->handleIntent action = " + str + ", activityIntent = null ? " + (intent == null));
        if (intent != null) {
            m3725a(activity, intent, str, bundle, iUiListener);
            return;
        }
        OpenConfig instance = OpenConfig.getInstance(Global.getContext(), this.mToken.getAppId());
        if (z || instance.getBoolean("C_LoginH5")) {
            obj = 1;
        }
        if (obj != null) {
            m3729a(activity, str, bundle, str2, iUiListener);
        } else {
            handleDownloadLastestQQ(activity, bundle, iUiListener);
        }
    }

    private void m3725a(Activity activity, Intent intent, String str, Bundle bundle, IUiListener iUiListener) {
        C1314f.m3870c("openSDK_LOG.SocialApiIml", "-->handleIntentWithAgent action = " + str);
        intent.putExtra(Constants.KEY_ACTION, str);
        intent.putExtra(Constants.KEY_PARAMS, bundle);
        UIListenerManager.getInstance().setListenerWithRequestcode(Constants.REQUEST_SOCIAL_API, iUiListener);
        startAssitActivity(activity, intent, (int) Constants.REQUEST_SOCIAL_API);
    }

    private void m3729a(Activity activity, String str, Bundle bundle, String str2, IUiListener iUiListener) {
        C1314f.m3870c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5 action = " + str);
        Intent targetActivityIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
        Object c1284c = new C1284c(this, activity, iUiListener, str, str2, bundle);
        Intent targetActivityIntent2 = getTargetActivityIntent("com.tencent.open.agent.EncryTokenActivity");
        if (targetActivityIntent2 == null || targetActivityIntent == null || targetActivityIntent.getComponent() == null || targetActivityIntent2.getComponent() == null || !targetActivityIntent.getComponent().getPackageName().equals(targetActivityIntent2.getComponent().getPackageName())) {
            C1314f.m3870c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5--token activity not found");
            String encrypt = Util.encrypt("tencent&sdk&qazxc***14969%%" + this.mToken.getAccessToken() + this.mToken.getAppId() + this.mToken.getOpenId() + "qzone3.4");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(SocialConstants.PARAM_ENCRY_EOKEN, encrypt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            c1284c.onComplete(jSONObject);
            return;
        }
        targetActivityIntent2.putExtra("oauth_consumer_key", this.mToken.getAppId());
        targetActivityIntent2.putExtra("openid", this.mToken.getOpenId());
        targetActivityIntent2.putExtra("access_token", this.mToken.getAccessToken());
        targetActivityIntent2.putExtra(Constants.KEY_ACTION, SocialConstants.ACTION_CHECK_TOKEN);
        if (hasActivityForIntent(targetActivityIntent2)) {
            C1314f.m3870c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5--found token activity");
            UIListenerManager.getInstance().setListenerWithRequestcode(Constants.REQUEST_SOCIAL_H5, c1284c);
            startAssitActivity(activity, targetActivityIntent2, (int) Constants.REQUEST_SOCIAL_H5);
        }
    }

    private void m3730a(Context context, String str, Bundle bundle, String str2, IUiListener iUiListener) {
        C1314f.m3864a("openSDK_LOG.SocialApiIml", "OpenUi, showDialog --start");
        CookieSyncManager.createInstance(context);
        bundle.putString("oauth_consumer_key", this.mToken.getAppId());
        if (this.mToken.isSessionValid()) {
            bundle.putString("access_token", this.mToken.getAccessToken());
        }
        String openId = this.mToken.getOpenId();
        if (openId != null) {
            bundle.putString("openid", openId);
        }
        try {
            bundle.putString(Constants.PARAM_PLATFORM_ID, Global.getContext().getSharedPreferences(Constants.PREFERENCE_PF, 0).getString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF));
        } catch (Exception e) {
            e.printStackTrace();
            bundle.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(HttpUtils.encodeUrl(bundle));
        String stringBuilder2 = stringBuilder.toString();
        C1314f.m3867b("openSDK_LOG.SocialApiIml", "OpenUi, showDialog TDialog");
        if (SocialConstants.ACTION_CHALLENGE.equals(str) || SocialConstants.ACTION_BRAG.equals(str)) {
            C1314f.m3867b("openSDK_LOG.SocialApiIml", "OpenUi, showDialog PKDialog");
            new PKDialog(this.f3986a, str, stringBuilder2, iUiListener, this.mToken).show();
            return;
        }
        new TDialog(this.f3986a, str, stringBuilder2, iUiListener, this.mToken).show();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void writeEncryToken(Context context) {
        String str = "tencent&sdk&qazxc***14969%%";
        String accessToken = this.mToken.getAccessToken();
        String appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        String str2 = "qzone3.4";
        if (accessToken == null || accessToken.length() <= 0 || appId == null || appId.length() <= 0 || openId == null || openId.length() <= 0) {
            str = null;
        } else {
            str = Util.encrypt(str + accessToken + appId + openId + str2);
        }
        C1333b c1333b = new C1333b(context);
        WebSettings settings = c1333b.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        accessToken = "<!DOCTYPE HTML><html lang=\"en-US\"><head><meta charset=\"UTF-8\"><title>localStorage Test</title><script type=\"text/javascript\">document.domain = 'qq.com';localStorage[\"" + this.mToken.getOpenId() + "_" + this.mToken.getAppId() + "\"]=\"" + str + "\";</script></head><body></body></html>";
        str = ServerSetting.getInstance().getEnvUrl(context, ServerSetting.DEFAULT_LOCAL_STORAGE_URI);
        c1333b.loadDataWithBaseURL(str, accessToken, "text/html", "utf-8", str);
    }

    protected Intent getTargetActivityIntent(String str) {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, str);
        Intent intent2 = new Intent();
        intent2.setClassName("com.tencent.mobileqq", str);
        Intent intent3 = new Intent();
        intent3.setClassName(Constants.PACKAGE_QQ_PAD, str);
        if (Util.isTablet(Global.getContext()) && SystemUtils.isActivityExist(Global.getContext(), intent3)) {
            return intent3;
        }
        if (SystemUtils.isActivityExist(Global.getContext(), intent2) && SystemUtils.compareQQVersion(Global.getContext(), "4.7") >= 0) {
            return intent2;
        }
        if (!SystemUtils.isActivityExist(Global.getContext(), intent) || SystemUtils.compareVersion(SystemUtils.getAppVersionName(Global.getContext(), Constants.PACKAGE_QZONE), "4.2") < 0) {
            return null;
        }
        if (SystemUtils.isAppSignatureValid(Global.getContext(), intent.getComponent().getPackageName(), Constants.SIGNATRUE_QZONE)) {
            return intent;
        }
        return null;
    }

    private void m3735b() {
        if (!this.f3986a.isFinishing() && this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    protected boolean m3738a() {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, SocialConstants.ACTIVITY_CHECK_FUNCTION);
        return SystemUtils.isActivityExist(Global.getContext(), intent);
    }

    protected void m3737a(Activity activity, String str, IUiListener iUiListener) {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.AgentActivity");
        intent.putExtra(Constants.KEY_ACTION, "action_check");
        Bundle bundle = new Bundle();
        bundle.putString("apiName", str);
        intent.putExtra(Constants.KEY_PARAMS, bundle);
        UIListenerManager.getInstance().setListenerWithRequestcode(Constants.REQUEST_SOCIAL_API, iUiListener);
        startAssitActivity(activity, intent, (int) Constants.REQUEST_SOCIAL_API);
    }

    private C1283b m3724a(Bundle bundle, String str, String str2, IUiListener iUiListener) {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.AgentActivity");
        C1283b c1283b = new C1283b();
        c1283b.f3975a = intent;
        c1283b.f3977c = bundle;
        c1283b.f3978d = str2;
        c1283b.f3979e = iUiListener;
        c1283b.f3976b = str;
        return c1283b;
    }

    public void reactive(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3986a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_FRIEND_CHOOSER);
        if (agentIntentWithTarget == null) {
            agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_REACTIVE);
        }
        bundle.putAll(composeActivityParams());
        String envUrl = ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_REACTIVE);
        if (agentIntentWithTarget == null && m3738a()) {
            this.mProgressDialog = new ProgressDialog(activity);
            this.mProgressDialog.setMessage("请稍候...");
            this.mProgressDialog.show();
            bundle.putString("type", SocialConstants.TYPE_REACTIVE);
            m3737a(activity, SocialConstants.ACTION_REACTIVE, new C1282a(this, m3724a(bundle, SocialConstants.ACTION_REACTIVE, envUrl, iUiListener)));
            return;
        }
        bundle.putString(SocialConstants.PARAM_SEND_IMG, bundle.getString("img"));
        bundle.putString("type", SocialConstants.TYPE_REACTIVE);
        bundle.remove("img");
        m3726a(activity, agentIntentWithTarget, SocialConstants.ACTION_REACTIVE, bundle, envUrl, iUiListener, false);
    }

    public void brag(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3986a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_BRAG);
        bundle.putAll(composeActivityParams());
        Activity activity2 = activity;
        m3726a(activity2, agentIntentWithTarget, SocialConstants.ACTION_BRAG, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_BRAG), iUiListener, false);
    }

    public void challenge(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3986a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_CHALLENGE);
        bundle.putAll(composeActivityParams());
        Activity activity2 = activity;
        m3726a(activity2, agentIntentWithTarget, SocialConstants.ACTION_CHALLENGE, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_BRAG), iUiListener, false);
    }

    public void grade(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3986a = activity;
        bundle.putAll(composeActivityParams());
        bundle.putString("version", Util.getAppVersion(activity));
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_GRADE);
        String str = "http://qzs.qq.com/open/mobile/rate/sdk_rate.html?";
        if (agentIntentWithTarget == null && m3738a()) {
            this.mProgressDialog = new ProgressDialog(activity);
            this.mProgressDialog.setMessage("请稍候...");
            this.mProgressDialog.show();
            m3737a(activity, SocialConstants.ACTION_GRADE, new C1282a(this, m3724a(bundle, SocialConstants.ACTION_GRADE, str, iUiListener)));
            return;
        }
        m3726a(activity, agentIntentWithTarget, SocialConstants.ACTION_GRADE, bundle, str, iUiListener, true);
    }

    public void voice(final Activity activity, final Bundle bundle, final IUiListener iUiListener) {
        this.f3986a = activity;
        bundle.putAll(composeActivityParams());
        bundle.putString("version", Util.getAppVersion(activity));
        if (!C1337e.m3930a()) {
            C1314f.m3870c("openSDK_LOG.SocialApiIml", "voice no SDCard");
            iUiListener.onError(new UiError(-12, Constants.MSG_NO_SDCARD, Constants.MSG_NO_SDCARD));
        } else if (!bundle.containsKey(SocialConstants.PARAM_IMG_DATA) || ((Bitmap) bundle.getParcelable(SocialConstants.PARAM_IMG_DATA)) == null) {
            m3727a(activity, bundle, iUiListener);
        } else {
            this.mProgressDialog = new ProgressDialog(activity);
            this.mProgressDialog.setMessage("请稍候...");
            this.mProgressDialog.show();
            new C1337e(new C1280a(this) {
                final /* synthetic */ SocialApiIml f3972d;

                public void mo2204a(String str) {
                    bundle.remove(SocialConstants.PARAM_IMG_DATA);
                    if (!TextUtils.isEmpty(str)) {
                        bundle.putString(SocialConstants.PARAM_IMG_DATA, str);
                    }
                    this.f3972d.m3727a(activity, bundle, iUiListener);
                }

                public void mo2205b(String str) {
                    bundle.remove(SocialConstants.PARAM_IMG_DATA);
                    iUiListener.onError(new UiError(-5, Constants.MSG_IMAGE_ERROR, Constants.MSG_IMAGE_ERROR));
                    this.f3972d.m3735b();
                }
            }).execute(new Bitmap[]{r0});
        }
    }

    private void m3727a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_VOICE);
        String envUrl = ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_VOICE);
        if (agentIntentWithTarget == null && m3738a()) {
            if (this.mProgressDialog == null || !this.mProgressDialog.isShowing()) {
                this.mProgressDialog = new ProgressDialog(activity);
                this.mProgressDialog.setTitle("请稍候");
                this.mProgressDialog.show();
            }
            m3737a(activity, SocialConstants.ACTION_VOICE, new C1282a(this, m3724a(bundle, SocialConstants.ACTION_VOICE, envUrl, iUiListener)));
            return;
        }
        m3726a(activity, agentIntentWithTarget, SocialConstants.ACTION_VOICE, bundle, envUrl, iUiListener, true);
    }
}
