package com.tencent.connect.auth;

import android.annotation.SuppressLint;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.ApiTask;
import com.tencent.connect.common.Constants;
import com.tencent.connect.p010a.C0687a;
import com.tencent.open.SocialConstants;
import com.tencent.open.yyb.TitleBar;
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
import com.umeng.socialize.handler.TwitterPreferences;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthAgent extends BaseApi {
    private IUiListener f4525a;
    private String f4526b;
    private Activity f4527c;
    private IUiListener f4528d = new C17131(this);
    private Handler f4529e = new C06882(this);

    /* compiled from: ProGuard */
    class C06882 extends Handler {
        final /* synthetic */ AuthAgent f2353a;

        C06882(AuthAgent authAgent) {
            this.f2353a = authAgent;
        }

        public void handleMessage(Message message) {
            C1711d.m4638b("openSDK_LOG", "OpenUi, handleMessage msg.what = " + message.what + "");
            if (message.what == 0) {
                int parseInt;
                try {
                    parseInt = Integer.parseInt(((JSONObject) message.obj).getString("ret"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.f2353a.m4650a();
                    parseInt = 0;
                }
                if (parseInt == 0) {
                    this.f2353a.f4525a.onComplete((JSONObject) message.obj);
                    return;
                } else {
                    this.f2353a.m4650a();
                    return;
                }
            }
            this.f2353a.f4525a.onError(new UiError(message.what, (String) message.obj, null));
        }
    }

    /* compiled from: ProGuard */
    class C17131 implements IUiListener {
        final /* synthetic */ AuthAgent f4508a;

        C17131(AuthAgent authAgent) {
            this.f4508a = authAgent;
        }

        public void onError(UiError uiError) {
            C1711d.m4638b("openSDK_LOG", "AuthAgent, EncrytokenListener() onError relogin");
            this.f4508a.m4650a();
        }

        public void onComplete(Object obj) {
            if (obj == null) {
                this.f4508a.m4650a();
            } else {
                String str = null;
                try {
                    str = ((JSONObject) obj).getString(SocialConstants.PARAM_ENCRY_EOKEN);
                } catch (Throwable e) {
                    e.printStackTrace();
                    C1711d.m4637a("openSDK_LOG", "OpenUi, EncrytokenListener() onComplete error", e);
                }
                if (TextUtils.isEmpty(str)) {
                    C1711d.m4638b("openSDK_LOG", "OpenUi, EncrytokenListener() onComplete relogin");
                    this.f4508a.m4650a();
                } else {
                    C1711d.m4638b("openSDK_LOG", "OpenUi, EncrytokenListener() onComplete validToken");
                    this.f4508a.m4652a(str);
                }
            }
            this.f4508a.writeEncryToken(this.f4508a.mContext);
        }

        public void onCancel() {
        }
    }

    /* compiled from: ProGuard */
    private class FeedConfirmListener implements IUiListener {
        IUiListener f4515a;
        final /* synthetic */ AuthAgent f4516b;
        private String f4517c = "sendinstall";
        private String f4518d = "installwording";
        private String f4519e = "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi";

        /* compiled from: ProGuard */
        private abstract class ButtonListener implements OnClickListener {
            Dialog f2357a;
            final /* synthetic */ FeedConfirmListener f2358b;

            ButtonListener(FeedConfirmListener feedConfirmListener, Dialog dialog) {
                this.f2358b = feedConfirmListener;
                this.f2357a = dialog;
            }
        }

        public FeedConfirmListener(AuthAgent authAgent, IUiListener iUiListener) {
            this.f4516b = authAgent;
            this.f4515a = iUiListener;
        }

        public void onComplete(Object obj) {
            int i = 0;
            if (obj != null) {
                JSONObject jSONObject = (JSONObject) obj;
                if (jSONObject != null) {
                    String string;
                    int i2;
                    String str = "";
                    try {
                        if (jSONObject.getInt(this.f4517c) == 1) {
                            i = 1;
                        }
                        string = jSONObject.getString(this.f4518d);
                        i2 = i;
                    } catch (JSONException e) {
                        JSONException jSONException = e;
                        int i3 = 0;
                        JSONException jSONException2 = jSONException;
                        Toast.makeText(this.f4516b.f4527c, "json error", 1);
                        jSONException2.printStackTrace();
                        String str2 = str;
                        i2 = i3;
                        string = str2;
                    }
                    Object decode = URLDecoder.decode(string);
                    Log.d("TAG", " WORDING = " + decode + "xx");
                    if (i2 != 0 && !TextUtils.isEmpty(decode)) {
                        m4646a(decode, this.f4515a, obj);
                    } else if (this.f4515a != null) {
                        this.f4515a.onComplete(obj);
                    }
                }
            } else if (this.f4515a != null) {
                this.f4515a.onComplete(null);
            }
        }

        private void m4646a(String str, final IUiListener iUiListener, final Object obj) {
            PackageInfo packageInfo;
            Drawable drawable = null;
            Dialog dialog = new Dialog(this.f4516b.f4527c);
            dialog.requestWindowFeature(1);
            PackageManager packageManager = this.f4516b.f4527c.getPackageManager();
            try {
                packageInfo = packageManager.getPackageInfo(this.f4516b.f4527c.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
                packageInfo = null;
            }
            if (packageInfo != null) {
                drawable = packageInfo.applicationInfo.loadIcon(packageManager);
            }
            OnClickListener c17141 = new ButtonListener(this, dialog) {
                final /* synthetic */ FeedConfirmListener f4511e;

                public void onClick(View view) {
                    this.f4511e.m4647a();
                    if (this.a != null && this.a.isShowing()) {
                        this.a.dismiss();
                    }
                    if (iUiListener != null) {
                        iUiListener.onComplete(obj);
                    }
                }
            };
            OnClickListener c17152 = new ButtonListener(this, dialog) {
                final /* synthetic */ FeedConfirmListener f4514e;

                public void onClick(View view) {
                    if (this.a != null && this.a.isShowing()) {
                        this.a.dismiss();
                    }
                    if (iUiListener != null) {
                        iUiListener.onComplete(obj);
                    }
                }
            };
            Drawable colorDrawable = new ColorDrawable();
            colorDrawable.setAlpha(0);
            dialog.getWindow().setBackgroundDrawable(colorDrawable);
            dialog.setContentView(m4645a(this.f4516b.f4527c, drawable, str, c17141, c17152));
            dialog.setOnCancelListener(new OnCancelListener(this) {
                final /* synthetic */ FeedConfirmListener f2356c;

                public void onCancel(DialogInterface dialogInterface) {
                    if (iUiListener != null) {
                        iUiListener.onComplete(obj);
                    }
                }
            });
            if (this.f4516b.f4527c != null && !this.f4516b.f4527c.isFinishing()) {
                dialog.show();
            }
        }

        private Drawable m4644a(String str, Context context) {
            Drawable createFromStream;
            IOException e;
            try {
                InputStream open = context.getApplicationContext().getAssets().open(str);
                if (open == null) {
                    return null;
                }
                if (str.endsWith(".9.png")) {
                    Bitmap decodeStream = BitmapFactory.decodeStream(open);
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
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    return createFromStream;
                }
            } catch (IOException e3) {
                IOException iOException = e3;
                createFromStream = null;
                e = iOException;
                e.printStackTrace();
                return createFromStream;
            }
        }

        private View m4645a(Context context, Drawable drawable, String str, OnClickListener onClickListener, OnClickListener onClickListener2) {
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
            LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (BitmapDescriptorFactory.HUE_YELLOW * f), (int) (BitmapDescriptorFactory.HUE_YELLOW * f));
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
            button.setBackgroundDrawable(m4644a("buttonNegt.png", context));
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
            button.setBackgroundDrawable(m4644a("buttonPost.png", context));
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

        protected void m4647a() {
            HttpUtils.requestAsync(this.f4516b.mToken, this.f4516b.f4527c, this.f4519e, this.f4516b.composeActivityParams(), "POST", null);
        }

        public void onError(UiError uiError) {
            if (this.f4515a != null) {
                this.f4515a.onError(uiError);
            }
        }

        public void onCancel() {
            if (this.f4515a != null) {
                this.f4515a.onCancel();
            }
        }
    }

    /* compiled from: ProGuard */
    private class RequestListener implements IRequestListener {
        final /* synthetic */ AuthAgent f4520a;

        public RequestListener(AuthAgent authAgent) {
            this.f4520a = authAgent;
            C1711d.m4638b("openSDK_LOG", "OpenUi, RequestListener()");
        }

        public void onUnknowException(Exception exception) {
            C1711d.m4637a("openSDK_LOG", "OpenUi, RequestListener() onUnknowException", exception);
            Message message = new Message();
            message.what = -6;
            message.obj = exception.getMessage() + "";
            this.f4520a.f4529e.sendMessage(message);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            C1711d.m4637a("openSDK_LOG", "OpenUi, RequestListener() onSocketTimeoutException", socketTimeoutException);
            Message message = new Message();
            message.what = -8;
            message.obj = socketTimeoutException.getMessage() + "";
            this.f4520a.f4529e.sendMessage(message);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            C1711d.m4637a("openSDK_LOG", "OpenUi, RequestListener() onNetworkUnavailableException", networkUnavailableException);
            Message message = new Message();
            message.what = -2;
            message.obj = networkUnavailableException.getMessage() + "";
            this.f4520a.f4529e.sendMessage(message);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            Message message = new Message();
            message.what = -3;
            message.obj = malformedURLException.getMessage() + "";
            this.f4520a.f4529e.sendMessage(message);
        }

        public void onJSONException(JSONException jSONException) {
            C1711d.m4637a("openSDK_LOG", "OpenUi, RequestListener() onJSONException", jSONException);
            Message message = new Message();
            message.what = -4;
            message.obj = jSONException.getMessage() + "";
            this.f4520a.f4529e.sendMessage(message);
        }

        public void onIOException(IOException iOException) {
            C1711d.m4637a("openSDK_LOG", "OpenUi, RequestListener() onIOException", iOException);
            Message message = new Message();
            message.what = -2;
            message.obj = iOException.getMessage() + "";
            this.f4520a.f4529e.sendMessage(message);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            C1711d.m4637a("openSDK_LOG", "OpenUi, RequestListener() onHttpStatusException", httpStatusException);
            Message message = new Message();
            message.what = -9;
            message.obj = httpStatusException.getMessage() + "";
            this.f4520a.f4529e.sendMessage(message);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            C1711d.m4637a("openSDK_LOG", "OpenUi, RequestListener() onConnectTimeoutException", connectTimeoutException);
            Message message = new Message();
            message.what = -7;
            message.obj = connectTimeoutException.getMessage() + "";
            this.f4520a.f4529e.sendMessage(message);
        }

        public void onComplete(JSONObject jSONObject) {
            C1711d.m4638b("openSDK_LOG", "OpenUi, RequestListener() onComplete");
            Message message = new Message();
            message.what = 0;
            message.obj = jSONObject;
            this.f4520a.f4529e.sendMessage(message);
        }
    }

    /* compiled from: ProGuard */
    private class TokenListener implements IUiListener {
        final /* synthetic */ AuthAgent f4521a;
        private IUiListener f4522b;
        private boolean f4523c;
        private Context f4524d;

        public TokenListener(AuthAgent authAgent, Context context, IUiListener iUiListener, boolean z, boolean z2) {
            this.f4521a = authAgent;
            this.f4524d = context;
            this.f4522b = iUiListener;
            this.f4523c = z;
            C1711d.m4638b("openSDK_LOG", "OpenUi, TokenListener()");
        }

        public void onComplete(Object obj) {
            C1711d.m4638b("openSDK_LOG", "OpenUi, TokenListener() onComplete");
            JSONObject jSONObject = (JSONObject) obj;
            try {
                String string = jSONObject.getString("access_token");
                String string2 = jSONObject.getString("expires_in");
                String string3 = jSONObject.getString("openid");
                if (!(string == null || this.f4521a.mToken == null || string3 == null)) {
                    this.f4521a.mToken.setAccessToken(string, string2);
                    this.f4521a.mToken.setOpenId(string3);
                    C0687a.m2310d(this.f4524d, this.f4521a.mToken);
                }
                string = jSONObject.getString(Constants.PARAM_PLATFORM_ID);
                if (string != null) {
                    try {
                        this.f4524d.getSharedPreferences(Constants.PREFERENCE_PF, 0).edit().putString(Constants.PARAM_PLATFORM_ID, string).commit();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        C1711d.m4637a("openSDK_LOG", "OpenUi, TokenListener() onComplete error", e);
                    }
                }
                if (this.f4523c) {
                    CookieSyncManager.getInstance().sync();
                }
            } catch (Throwable e2) {
                e2.printStackTrace();
                C1711d.m4637a("openSDK_LOG", "OpenUi, TokenListener() onComplete error", e2);
            }
            this.f4522b.onComplete(jSONObject);
            C1711d.m4641f().mo2081b();
        }

        public void onError(UiError uiError) {
            C1711d.m4638b("openSDK_LOG", "OpenUi, TokenListener() onError");
            this.f4522b.onError(uiError);
            C1711d.m4641f().mo2081b();
        }

        public void onCancel() {
            C1711d.m4638b("openSDK_LOG", "OpenUi, TokenListener() onCancel");
            this.f4522b.onCancel();
            C1711d.m4641f().mo2081b();
        }
    }

    public AuthAgent(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener) {
        return doLogin(activity, str, iUiListener, false, false);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener, boolean z) {
        return doLogin(activity, str, iUiListener, z, false);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener, boolean z, boolean z2) {
        this.f4526b = str;
        this.f4527c = activity;
        this.f4525a = iUiListener;
        if (!z) {
            String accessToken = this.mToken.getAccessToken();
            String openId = this.mToken.getOpenId();
            String appId = this.mToken.getAppId();
            if (!(TextUtils.isEmpty(accessToken) || TextUtils.isEmpty(openId) || TextUtils.isEmpty(appId))) {
                Intent targetActivityIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
                Intent targetActivityIntent2 = getTargetActivityIntent("com.tencent.open.agent.EncryTokenActivity");
                if (targetActivityIntent2 == null || targetActivityIntent == null || targetActivityIntent.getComponent() == null || targetActivityIntent2.getComponent() == null || !targetActivityIntent.getComponent().getPackageName().equals(targetActivityIntent2.getComponent().getPackageName())) {
                    accessToken = Util.encrypt("tencent&sdk&qazxc***14969%%" + accessToken + appId + openId + "qzone3.4");
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put(SocialConstants.PARAM_ENCRY_EOKEN, accessToken);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    this.f4528d.onComplete(jSONObject);
                } else {
                    targetActivityIntent2.putExtra("oauth_consumer_key", appId);
                    targetActivityIntent2.putExtra("openid", openId);
                    targetActivityIntent2.putExtra("access_token", accessToken);
                    targetActivityIntent2.putExtra(Constants.KEY_ACTION, SocialConstants.ACTION_CHECK_TOKEN);
                    this.mActivityIntent = targetActivityIntent2;
                    if (hasActivityForIntent()) {
                        startAssitActivity(activity, this.f4528d);
                    }
                }
                return 3;
            }
        }
        if (m4653a(activity, z2)) {
            if (z) {
                Util.reportBernoulli(activity, "10785", 0, this.mToken.getAppId());
            }
            C1711d.m4638b("openSDK_LOG", "OpenUi, showUi, return Constants.UI_ACTIVITY");
            return 1;
        }
        this.f4525a = new FeedConfirmListener(this, this.f4525a);
        return m4648a(z2, this.f4525a);
    }

    private int m4648a(boolean z, IUiListener iUiListener) {
        C1711d.m4636a("openSDK_LOG", "OpenUi, showDialog --start");
        CookieSyncManager.createInstance(this.mContext);
        Bundle composeCGIParams = composeCGIParams();
        if (z) {
            composeCGIParams.putString("isadd", "1");
        }
        composeCGIParams.putString("scope", this.f4526b);
        composeCGIParams.putString("client_id", this.mToken.getAppId());
        if (isOEM) {
            composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + SocializeConstants.OP_DIVIDER_MINUS + "android" + SocializeConstants.OP_DIVIDER_MINUS + registerChannel + SocializeConstants.OP_DIVIDER_MINUS + businessId);
        } else {
            composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
        }
        String str = (System.currentTimeMillis() / 1000) + "";
        composeCGIParams.putString("sign", SystemUtils.getAppSignatureMD5(this.mContext, str));
        composeCGIParams.putString(LogColumns.TIME, str);
        composeCGIParams.putString("display", "mobile");
        composeCGIParams.putString("response_type", TwitterPreferences.TOKEN);
        composeCGIParams.putString("redirect_uri", ServerSetting.DEFAULT_REDIRECT_URI);
        composeCGIParams.putString("cancel_display", "1");
        composeCGIParams.putString("switch", "1");
        composeCGIParams.putString("status_userip", Util.getUserIp());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServerSetting.getInstance().getEnvUrl(this.mContext, ServerSetting.DEFAULT_CGI_AUTHORIZE));
        stringBuilder.append(Util.encodeUrl(composeCGIParams));
        String stringBuilder2 = stringBuilder.toString();
        TokenListener tokenListener = new TokenListener(this, this.mContext, iUiListener, true, false);
        C1711d.m4638b("openSDK_LOG", "OpenUi, showDialog TDialog");
        new AuthDialog(this.f4527c, "action_login", stringBuilder2, tokenListener, this.mToken).show();
        return 2;
    }

    private boolean m4653a(Activity activity, boolean z) {
        Intent targetActivityIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
        if (targetActivityIntent != null) {
            Bundle composeCGIParams = composeCGIParams();
            if (z) {
                composeCGIParams.putString("isadd", "1");
            }
            composeCGIParams.putString("scope", this.f4526b);
            composeCGIParams.putString("client_id", this.mToken.getAppId());
            if (isOEM) {
                composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + SocializeConstants.OP_DIVIDER_MINUS + "android" + SocializeConstants.OP_DIVIDER_MINUS + registerChannel + SocializeConstants.OP_DIVIDER_MINUS + businessId);
            } else {
                composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
            }
            composeCGIParams.putString("need_pay", "1");
            composeCGIParams.putString(Constants.KEY_APP_NAME, SystemUtils.getAppName(this.mContext));
            String str = (System.currentTimeMillis() / 1000) + "";
            composeCGIParams.putString("sign", SystemUtils.getAppSignatureMD5(this.mContext, str));
            composeCGIParams.putString(LogColumns.TIME, str);
            targetActivityIntent.putExtra(Constants.KEY_ACTION, "action_login");
            targetActivityIntent.putExtra(Constants.KEY_PARAMS, composeCGIParams);
            this.mActivityIntent = targetActivityIntent;
            if (hasActivityForIntent()) {
                this.f4525a = new FeedConfirmListener(this, this.f4525a);
                startAssitActivity(activity, this.f4525a);
                return true;
            }
        }
        return false;
    }

    private void m4650a() {
        this.mToken.setAccessToken("", "0");
        this.mToken.setOpenId("");
        doLogin(this.f4527c, this.f4526b, this.f4525a, true);
    }

    private void m4652a(String str) {
        C1711d.m4638b("openSDK_LOG", "OpenUi, EncrytokenListener() validToken()");
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("encrytoken", str);
        HttpUtils.requestAsync(this.mToken, this.mContext, "https://openmobile.qq.com/user/user_login_statis", composeCGIParams, "POST", new RequestListener(this));
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
        WebView webView = new WebView(context);
        WebSettings settings = webView.getSettings();
        try {
            Method method = settings.getClass().getMethod("removeJavascriptInterface", new Class[]{String.class});
            if (method != null) {
                method.invoke(webView, new Object[]{"searchBoxJavaBridge_"});
            }
        } catch (Exception e) {
        }
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        accessToken = "<!DOCTYPE HTML><html lang=\"en-US\"><head><meta charset=\"UTF-8\"><title>localStorage Test</title><script type=\"text/javascript\">document.domain = 'qq.com';localStorage[\"" + this.mToken.getOpenId() + "_" + this.mToken.getAppId() + "\"]=\"" + str + "\";</script></head><body></body></html>";
        str = ServerSetting.getInstance().getEnvUrl(context, ServerSetting.DEFAULT_LOCAL_STORAGE_URI);
        webView.loadDataWithBaseURL(str, accessToken, "text/html", "utf-8", str);
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
                            JSONObject parseJson = Util.parseJson(stringExtra);
                            if (iUiListener == this.f4525a) {
                                Object string = parseJson.getString("access_token");
                                Object string2 = parseJson.getString("expires_in");
                                Object string3 = parseJson.getString("openid");
                                if (!(TextUtils.isEmpty(string) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3))) {
                                    this.mToken.setAccessToken(string, string2);
                                    this.mToken.setOpenId(string3);
                                }
                            }
                            iUiListener.onComplete(parseJson);
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
}
