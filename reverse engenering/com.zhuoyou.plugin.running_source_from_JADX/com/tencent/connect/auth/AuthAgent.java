package com.tencent.connect.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.connect.p025a.C1148a;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.p037b.C1322d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.ThreadManager;
import com.tencent.open.utils.Util;
import com.tencent.open.yyb.TitleBar;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.facebook.GraphResponse;
import com.umeng.socialize.handler.TwitterPreferences;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthAgent extends BaseApi {
    public static final String SECURE_LIB_ARM64_FILE_NAME = "libwbsafeedit_64";
    public static final String SECURE_LIB_ARM_FILE_NAME = "libwbsafeedit";
    public static String SECURE_LIB_FILE_NAME = null;
    public static String SECURE_LIB_NAME = null;
    public static final String SECURE_LIB_X86_64_FILE_NAME = "libwbsafeedit_x86_64";
    public static final String SECURE_LIB_X86_FILE_NAME = "libwbsafeedit_x86";
    private IUiListener f3545a;
    private String f3546b;
    private WeakReference<Activity> f3547c;

    /* compiled from: ProGuard */
    private class CheckLoginListener implements IUiListener {
        IUiListener f3523a;
        final /* synthetic */ AuthAgent f3524b;

        public CheckLoginListener(AuthAgent authAgent, IUiListener iUiListener) {
            this.f3524b = authAgent;
            this.f3523a = iUiListener;
        }

        public void onComplete(Object obj) {
            if (obj == null) {
                C1314f.m3872e("openSDK_LOG.AuthAgent", "CheckLoginListener response data is null");
                return;
            }
            JSONObject jSONObject = (JSONObject) obj;
            try {
                int i = jSONObject.getInt("ret");
                Object string = i == 0 ? GraphResponse.SUCCESS_KEY : jSONObject.getString("msg");
                if (this.f3523a != null) {
                    this.f3523a.onComplete(new JSONObject().put("ret", i).put("msg", string));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                C1314f.m3872e("openSDK_LOG.AuthAgent", "CheckLoginListener response data format error");
            }
        }

        public void onError(UiError uiError) {
            if (this.f3523a != null) {
                this.f3523a.onError(uiError);
            }
        }

        public void onCancel() {
            if (this.f3523a != null) {
                this.f3523a.onCancel();
            }
        }
    }

    /* compiled from: ProGuard */
    private class FeedConfirmListener implements IUiListener {
        IUiListener f3536a;
        final /* synthetic */ AuthAgent f3537b;
        private final String f3538c = "sendinstall";
        private final String f3539d = "installwording";
        private final String f3540e = "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi";

        /* compiled from: ProGuard */
        private abstract class ButtonListener implements OnClickListener {
            Dialog f3525d;
            final /* synthetic */ FeedConfirmListener f3526e;

            ButtonListener(FeedConfirmListener feedConfirmListener, Dialog dialog) {
                this.f3526e = feedConfirmListener;
                this.f3525d = dialog;
            }
        }

        public FeedConfirmListener(AuthAgent authAgent, IUiListener iUiListener) {
            this.f3537b = authAgent;
            this.f3536a = iUiListener;
        }

        public void onComplete(Object obj) {
            Object obj2;
            Object obj3 = null;
            if (obj != null) {
                JSONObject jSONObject = (JSONObject) obj;
                if (jSONObject != null) {
                    String string;
                    Object obj4;
                    String str = "";
                    try {
                        if (jSONObject.getInt("sendinstall") == 1) {
                            obj3 = 1;
                        }
                        string = jSONObject.getString("installwording");
                        obj4 = obj3;
                    } catch (JSONException e) {
                        obj2 = null;
                        C1314f.m3871d("openSDK_LOG.AuthAgent", "FeedConfirmListener onComplete There is no value for sendinstall.");
                        String str2 = str;
                        obj4 = obj2;
                        string = str2;
                    }
                    obj2 = URLDecoder.decode(string);
                    C1314f.m3864a("openSDK_LOG.AuthAgent", " WORDING = " + obj2 + "xx");
                    if (obj4 != null && !TextUtils.isEmpty(obj2)) {
                        m3351a(obj2, this.f3536a, obj);
                    } else if (this.f3536a != null) {
                        this.f3536a.onComplete(obj);
                    }
                }
            }
        }

        private void m3351a(String str, final IUiListener iUiListener, final Object obj) {
            Drawable drawable = null;
            Activity activity = (Activity) this.f3537b.f3547c.get();
            if (activity != null) {
                PackageInfo packageInfo;
                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(1);
                PackageManager packageManager = activity.getPackageManager();
                try {
                    packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                    packageInfo = null;
                }
                if (packageInfo != null) {
                    drawable = packageInfo.applicationInfo.loadIcon(packageManager);
                }
                OnClickListener c11511 = new ButtonListener(this, dialog) {
                    final /* synthetic */ FeedConfirmListener f3529c;

                    public void onClick(View view) {
                        this.f3529c.m3352a();
                        if (this.d != null && this.d.isShowing()) {
                            this.d.dismiss();
                        }
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                };
                OnClickListener c11522 = new ButtonListener(this, dialog) {
                    final /* synthetic */ FeedConfirmListener f3532c;

                    public void onClick(View view) {
                        if (this.d != null && this.d.isShowing()) {
                            this.d.dismiss();
                        }
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                };
                Drawable colorDrawable = new ColorDrawable();
                colorDrawable.setAlpha(0);
                dialog.getWindow().setBackgroundDrawable(colorDrawable);
                dialog.setContentView(m3350a(activity, drawable, str, c11511, c11522));
                dialog.setOnCancelListener(new OnCancelListener(this) {
                    final /* synthetic */ FeedConfirmListener f3535c;

                    public void onCancel(DialogInterface dialogInterface) {
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                });
                if (activity != null && !activity.isFinishing()) {
                    dialog.show();
                }
            }
        }

        private Drawable m3349a(String str, Context context) {
            IOException e;
            Drawable createFromStream;
            try {
                InputStream open = context.getApplicationContext().getAssets().open(str);
                if (open == null) {
                    return null;
                }
                if (str.endsWith(".9.png")) {
                    Bitmap decodeStream;
                    try {
                        decodeStream = BitmapFactory.decodeStream(open);
                    } catch (OutOfMemoryError e2) {
                        e2.printStackTrace();
                        decodeStream = null;
                    }
                    if (decodeStream == null) {
                        return null;
                    }
                    byte[] ninePatchChunk = decodeStream.getNinePatchChunk();
                    NinePatch.isNinePatchChunk(ninePatchChunk);
                    return new NinePatchDrawable(decodeStream, ninePatchChunk, new Rect(), null);
                }
                createFromStream = Drawable.createFromStream(open, str);
                try {
                    open.close();
                    return createFromStream;
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                    return createFromStream;
                }
            } catch (IOException e4) {
                IOException iOException = e4;
                createFromStream = null;
                e = iOException;
                e.printStackTrace();
                return createFromStream;
            }
        }

        private View m3350a(Context context, Drawable drawable, String str, OnClickListener onClickListener, OnClickListener onClickListener2) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            View relativeLayout = new RelativeLayout(context);
            View imageView = new ImageView(context);
            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setId(1);
            int i = (int) (14.0f * f);
            i = (int) (18.0f * f);
            int i2 = (int) (6.0f * f);
            int i3 = (int) (18.0f * f);
            LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (60.0f * f), (int) (60.0f * f));
            layoutParams.addRule(9);
            layoutParams.setMargins(0, i, i2, i3);
            relativeLayout.addView(imageView, layoutParams);
            imageView = new TextView(context);
            imageView.setText(str);
            imageView.setTextSize(14.0f);
            imageView.setGravity(3);
            imageView.setIncludeFontPadding(false);
            imageView.setPadding(0, 0, 0, 0);
            imageView.setLines(2);
            imageView.setId(5);
            imageView.setMinWidth((int) (185.0f * f));
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(1, 1);
            layoutParams2.addRule(6, 1);
            int i4 = (int) (TitleBar.SHAREBTN_RIGHT_MARGIN * f);
            layoutParams2.setMargins(0, 0, (int) (5.0f * f), 0);
            relativeLayout.addView(imageView, layoutParams2);
            imageView = new View(context);
            imageView.setBackgroundColor(Color.rgb(214, 214, 214));
            imageView.setId(3);
            layoutParams2 = new RelativeLayout.LayoutParams(-2, 2);
            layoutParams2.addRule(3, 1);
            layoutParams2.addRule(5, 1);
            layoutParams2.addRule(7, 5);
            layoutParams2.setMargins(0, 0, 0, (int) (12.0f * f));
            relativeLayout.addView(imageView, layoutParams2);
            imageView = new LinearLayout(context);
            layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(5, 1);
            layoutParams2.addRule(7, 5);
            layoutParams2.addRule(3, 3);
            View button = new Button(context);
            button.setText("跳过");
            button.setBackgroundDrawable(m3349a("buttonNegt.png", context));
            button.setTextColor(Color.rgb(36, 97, 131));
            button.setTextSize(TitleBar.BACKBTN_LEFT_MARGIN);
            button.setOnClickListener(onClickListener2);
            button.setId(4);
            LayoutParams layoutParams3 = new LinearLayout.LayoutParams(0, (int) (45.0f * f));
            layoutParams3.rightMargin = (int) (14.0f * f);
            layoutParams3.leftMargin = (int) (4.0f * f);
            layoutParams3.weight = 1.0f;
            imageView.addView(button, layoutParams3);
            button = new Button(context);
            button.setText("确定");
            button.setTextSize(TitleBar.BACKBTN_LEFT_MARGIN);
            button.setTextColor(Color.rgb(255, 255, 255));
            button.setBackgroundDrawable(m3349a("buttonPost.png", context));
            button.setOnClickListener(onClickListener);
            layoutParams3 = new LinearLayout.LayoutParams(0, (int) (45.0f * f));
            layoutParams3.weight = 1.0f;
            layoutParams3.rightMargin = (int) (4.0f * f);
            imageView.addView(button, layoutParams3);
            relativeLayout.addView(imageView, layoutParams2);
            LayoutParams layoutParams4 = new FrameLayout.LayoutParams((int) (279.0f * f), (int) (163.0f * f));
            relativeLayout.setPadding((int) (14.0f * f), 0, (int) (12.0f * f), (int) (12.0f * f));
            relativeLayout.setLayoutParams(layoutParams4);
            relativeLayout.setBackgroundColor(Color.rgb(247, 251, 247));
            Drawable paintDrawable = new PaintDrawable(Color.rgb(247, 251, 247));
            paintDrawable.setCornerRadius(f * 5.0f);
            relativeLayout.setBackgroundDrawable(paintDrawable);
            return relativeLayout;
        }

        protected void m3352a() {
            Bundle g = this.f3537b.composeActivityParams();
            Activity activity = (Activity) this.f3537b.f3547c.get();
            if (activity != null) {
                HttpUtils.requestAsync(this.f3537b.mToken, activity, "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi", g, Constants.HTTP_POST, null);
            }
        }

        public void onError(UiError uiError) {
            if (this.f3536a != null) {
                this.f3536a.onError(uiError);
            }
        }

        public void onCancel() {
            if (this.f3536a != null) {
                this.f3536a.onCancel();
            }
        }
    }

    /* compiled from: ProGuard */
    private class TokenListener implements IUiListener {
        final /* synthetic */ AuthAgent f3541a;
        private final IUiListener f3542b;
        private final boolean f3543c;
        private final Context f3544d;

        public TokenListener(AuthAgent authAgent, Context context, IUiListener iUiListener, boolean z, boolean z2) {
            this.f3541a = authAgent;
            this.f3544d = context;
            this.f3542b = iUiListener;
            this.f3543c = z;
            C1314f.m3867b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener()");
        }

        public void onComplete(Object obj) {
            C1314f.m3867b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onComplete");
            JSONObject jSONObject = (JSONObject) obj;
            try {
                String string = jSONObject.getString("access_token");
                String string2 = jSONObject.getString("expires_in");
                String string3 = jSONObject.getString("openid");
                if (!(string == null || this.f3541a.mToken == null || string3 == null)) {
                    this.f3541a.mToken.setAccessToken(string, string2);
                    this.f3541a.mToken.setOpenId(string3);
                    C1148a.m3348d(this.f3544d, this.f3541a.mToken);
                }
                string = jSONObject.getString(Constants.PARAM_PLATFORM_ID);
                if (string != null) {
                    try {
                        this.f3544d.getSharedPreferences(Constants.PREFERENCE_PF, 0).edit().putString(Constants.PARAM_PLATFORM_ID, string).commit();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        C1314f.m3868b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onComplete error", e);
                    }
                }
                if (this.f3543c) {
                    CookieSyncManager.getInstance().sync();
                }
            } catch (Throwable e2) {
                e2.printStackTrace();
                C1314f.m3868b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onComplete error", e2);
            }
            this.f3542b.onComplete(jSONObject);
            this.f3541a.releaseResource();
            C1314f.m3866b();
        }

        public void onError(UiError uiError) {
            C1314f.m3867b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onError");
            this.f3542b.onError(uiError);
            C1314f.m3866b();
        }

        public void onCancel() {
            C1314f.m3867b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onCancel");
            this.f3542b.onCancel();
            C1314f.m3866b();
        }
    }

    static {
        SECURE_LIB_FILE_NAME = SECURE_LIB_ARM_FILE_NAME;
        SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
        String str = Build.CPU_ABI;
        if (str == null || str.equals("")) {
            SECURE_LIB_FILE_NAME = SECURE_LIB_ARM_FILE_NAME;
            SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
            C1314f.m3870c("openSDK_LOG.AuthAgent", "is arm(default) architecture");
        } else if (str.equalsIgnoreCase("arm64-v8a")) {
            SECURE_LIB_FILE_NAME = SECURE_LIB_ARM64_FILE_NAME;
            SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
            C1314f.m3870c("openSDK_LOG.AuthAgent", "is arm64-v8a architecture");
        } else if (str.equalsIgnoreCase("x86")) {
            SECURE_LIB_FILE_NAME = SECURE_LIB_X86_FILE_NAME;
            SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
            C1314f.m3870c("openSDK_LOG.AuthAgent", "is x86 architecture");
        } else if (str.equalsIgnoreCase("x86_64")) {
            SECURE_LIB_FILE_NAME = SECURE_LIB_X86_64_FILE_NAME;
            SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
            C1314f.m3870c("openSDK_LOG.AuthAgent", "is x86_64 architecture");
        } else {
            SECURE_LIB_FILE_NAME = SECURE_LIB_ARM_FILE_NAME;
            SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
            C1314f.m3870c("openSDK_LOG.AuthAgent", "is arm(default) architecture");
        }
    }

    public AuthAgent(QQToken qQToken) {
        super(qQToken);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener) {
        return doLogin(activity, str, iUiListener, false, null);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener, boolean z, Fragment fragment) {
        this.f3546b = str;
        this.f3547c = new WeakReference(activity);
        this.f3545a = iUiListener;
        if (m3355a(activity, fragment, z)) {
            C1314f.m3870c("openSDK_LOG.AuthAgent", "OpenUi, showUi, return Constants.UI_ACTIVITY");
            C1322d.m3896a().m3899a(this.mToken.getOpenId(), this.mToken.getAppId(), "2", "1", "5", "0", "0", "0");
            return 1;
        }
        C1322d.m3896a().m3899a(this.mToken.getOpenId(), this.mToken.getAppId(), "2", "1", "5", "1", "0", "0");
        C1314f.m3871d("openSDK_LOG.AuthAgent", "doLogin startActivity fail show dialog.");
        this.f3545a = new FeedConfirmListener(this, this.f3545a);
        return m3353a(z, this.f3545a);
    }

    public void releaseResource() {
        this.f3547c = null;
        this.f3545a = null;
    }

    private int m3353a(boolean z, IUiListener iUiListener) {
        C1314f.m3870c("openSDK_LOG.AuthAgent", "OpenUi, showDialog -- start");
        CookieSyncManager.createInstance(Global.getContext());
        Bundle composeCGIParams = composeCGIParams();
        if (z) {
            composeCGIParams.putString("isadd", "1");
        }
        composeCGIParams.putString("scope", this.f3546b);
        composeCGIParams.putString("client_id", this.mToken.getAppId());
        if (isOEM) {
            composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
        } else {
            composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
        }
        String str = (System.currentTimeMillis() / 1000) + "";
        composeCGIParams.putString("sign", SystemUtils.getAppSignatureMD5(Global.getContext(), str));
        composeCGIParams.putString(LogColumns.TIME, str);
        composeCGIParams.putString("display", "mobile");
        composeCGIParams.putString("response_type", TwitterPreferences.TOKEN);
        composeCGIParams.putString("redirect_uri", ServerSetting.DEFAULT_REDIRECT_URI);
        composeCGIParams.putString("cancel_display", "1");
        composeCGIParams.putString("switch", "1");
        composeCGIParams.putString("status_userip", Util.getUserIp());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_CGI_AUTHORIZE));
        stringBuilder.append(HttpUtils.encodeUrl(composeCGIParams));
        final String stringBuilder2 = stringBuilder.toString();
        final IUiListener tokenListener = new TokenListener(this, Global.getContext(), iUiListener, true, false);
        C1314f.m3867b("openSDK_LOG.AuthAgent", "OpenUi, showDialog TDialog");
        ThreadManager.executeOnSubThread(new Runnable(this) {
            final /* synthetic */ AuthAgent f3522c;

            public void run() {
                SystemUtils.extractSecureLib(AuthAgent.SECURE_LIB_FILE_NAME, AuthAgent.SECURE_LIB_NAME, 3);
                final Activity activity = (Activity) this.f3522c.f3547c.get();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable(this) {
                        final /* synthetic */ C11501 f3519b;

                        public void run() {
                            AuthDialog authDialog = new AuthDialog(activity, SystemUtils.ACTION_LOGIN, stringBuilder2, tokenListener, this.f3519b.f3522c.mToken);
                            if (activity != null && !activity.isFinishing()) {
                                authDialog.show();
                            }
                        }
                    });
                }
            }
        });
        C1314f.m3870c("openSDK_LOG.AuthAgent", "OpenUi, showDialog -- end");
        return 2;
    }

    private boolean m3355a(Activity activity, Fragment fragment, boolean z) {
        C1314f.m3870c("openSDK_LOG.AuthAgent", "startActionActivity() -- start");
        Intent targetActivityIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
        if (targetActivityIntent != null) {
            Bundle composeCGIParams = composeCGIParams();
            if (z) {
                composeCGIParams.putString("isadd", "1");
            }
            composeCGIParams.putString("scope", this.f3546b);
            composeCGIParams.putString("client_id", this.mToken.getAppId());
            if (isOEM) {
                composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
            } else {
                composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
            }
            composeCGIParams.putString("need_pay", "1");
            composeCGIParams.putString(Constants.KEY_APP_NAME, SystemUtils.getAppName(Global.getContext()));
            targetActivityIntent.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_LOGIN);
            targetActivityIntent.putExtra(Constants.KEY_PARAMS, composeCGIParams);
            if (hasActivityForIntent(targetActivityIntent)) {
                this.f3545a = new FeedConfirmListener(this, this.f3545a);
                UIListenerManager.getInstance().setListenerWithRequestcode(11101, this.f3545a);
                if (fragment != null) {
                    C1314f.m3867b("openSDK_LOG.AuthAgent", "startAssitActivity fragment");
                    startAssitActivity(fragment, targetActivityIntent, 11101);
                } else {
                    C1314f.m3867b("openSDK_LOG.AuthAgent", "startAssitActivity activity");
                    startAssitActivity(activity, targetActivityIntent, 11101);
                }
                C1314f.m3870c("openSDK_LOG.AuthAgent", "startActionActivity() -- end, found activity for loginIntent");
                C1322d.m3896a().m3897a(0, "LOGIN_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
                return true;
            }
        }
        C1322d.m3896a().m3897a(1, "LOGIN_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "startActionActivity fail");
        C1314f.m3870c("openSDK_LOG.AuthAgent", "startActionActivity() -- end, no target activity for loginIntent");
        return false;
    }

    protected void m3363a(IUiListener iUiListener) {
        C1314f.m3870c("openSDK_LOG.AuthAgent", "reportDAU() -- start");
        String str = "tencent&sdk&qazxc***14969%%";
        String str2 = "qzone3.4";
        Object accessToken = this.mToken.getAccessToken();
        Object openId = this.mToken.getOpenId();
        Object appId = this.mToken.getAppId();
        Object obj = "";
        if (!(TextUtils.isEmpty(accessToken) || TextUtils.isEmpty(openId) || TextUtils.isEmpty(appId))) {
            obj = Util.encrypt(str + accessToken + appId + openId + str2);
        }
        if (TextUtils.isEmpty(obj)) {
            C1314f.m3872e("openSDK_LOG.AuthAgent", "reportDAU -- encrytoken is null");
            return;
        }
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("encrytoken", obj);
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://openmobile.qq.com/user/user_login_statis", composeCGIParams, Constants.HTTP_POST, null);
        C1314f.m3870c("openSDK_LOG.AuthAgent", "reportDAU() -- end");
    }

    protected void m3364b(IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("reqType", "checkLogin");
        IRequestListener tempRequestListener = new TempRequestListener(new CheckLoginListener(this, iUiListener));
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://openmobile.qq.com/v3/user/get_info", composeCGIParams, Constants.HTTP_GET, tempRequestListener);
    }
}
