package com.tencent.tauth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.avatar.QQAvatar;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.LocationApi;
import com.tencent.open.SocialApi;
import com.tencent.open.TaskGuide;
import com.tencent.open.yyb.AppbarAgent;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.utils.SystemUtils;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class Tencent {
    private AppbarAgent mAgent;
    private Context mContext;
    private LocationApi mLocationApi;
    private QQAuth mQQAuth;

    private Tencent(String str, Context context) {
        this.mContext = context;
        this.mQQAuth = QQAuth.createInstance(str, context);
    }

    public static Tencent createInstance(String str, Context context) {
        Tencent tencent = new Tencent(str, context);
        if (!checkManifestConfig(context, str)) {
            return null;
        }
        C1711d.m4636a("openSDK_LOG", "createInstance()  --end");
        return tencent;
    }

    private static boolean checkManifestConfig(Context context, String str) {
        String str2;
        try {
            context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
            try {
                context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
                return true;
            } catch (NameNotFoundException e) {
                str2 = "没有在AndroidManifest.xml中检测到com.tencent.connect.common.AssistActivity,请加上com.tencent.connect.common.AssistActivity,详细信息请查看官网文档." + "\n配置示例如下: \n<activity\n     android:name=\"com.tencent.connect.common.AssistActivity\"\n     android:screenOrientation=\"portrait\"\n     android:theme=\"@android:style/Theme.Translucent.NoTitleBar\"\n     android:configChanges=\"orientation|keyboardHidden\">\n</activity>";
                C1711d.m4641f();
                C1711d.m4640d("AndroidManifest.xml 没有检测到com.tencent.connect.common.AssistActivity", str2);
                return false;
            }
        } catch (NameNotFoundException e2) {
            str2 = ("没有在AndroidManifest.xml中检测到com.tencent.tauth.AuthActivity,请加上com.tencent.open.AuthActivity,并配置<data android:scheme=\"tencent" + str + "\" />,详细信息请查看官网文档.") + "\n配置示例如下: \n<activity\n     android:name=\"com.tencent.connect.util.AuthActivity\"\n     android:noHistory=\"true\"\n     android:launchMode=\"singleTask\">\n<intent-filter>\n    <action android:name=\"android.intent.action.VIEW\" />\n     <category android:name=\"android.intent.category.DEFAULT\" />\n    <category android:name=\"android.intent.category.BROWSABLE\" />\n    <data android:scheme=\"tencent" + str + "\" />\n" + "</intent-filter>\n" + "</activity>";
            C1711d.m4641f();
            C1711d.m4640d("AndroidManifest.xml 没有检测到com.tencent.tauth.AuthActivity", str2);
            return false;
        }
    }

    public int login(Activity activity, String str, IUiListener iUiListener) {
        return this.mQQAuth.login(activity, str, iUiListener);
    }

    public int loginWithOEM(Activity activity, String str, IUiListener iUiListener, String str2, String str3, String str4) {
        return this.mQQAuth.loginWithOEM(activity, str, iUiListener, str2, str3, str4);
    }

    public void logout(Context context) {
        this.mQQAuth.getQQToken().setAccessToken(null, "0");
        this.mQQAuth.getQQToken().setOpenId(null);
    }

    public int reAuth(Activity activity, String str, IUiListener iUiListener) {
        return this.mQQAuth.reAuth(activity, str, iUiListener);
    }

    public int ask(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).ask(activity, bundle, iUiListener);
        return 0;
    }

    public int reactive(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).reactive(activity, bundle, iUiListener);
        return 0;
    }

    public int searchNearby(Activity activity, Bundle bundle, IUiListener iUiListener) {
        if (this.mLocationApi == null) {
            this.mLocationApi = new LocationApi(activity, this.mQQAuth.getQQToken());
        }
        this.mLocationApi.searchNearby(activity, bundle, iUiListener);
        return 0;
    }

    public int deleteLocation(Activity activity, Bundle bundle, IUiListener iUiListener) {
        if (this.mLocationApi == null) {
            this.mLocationApi = new LocationApi(activity, this.mQQAuth.getQQToken());
        }
        this.mLocationApi.deleteLocation(activity, bundle, iUiListener);
        return 0;
    }

    public int brag(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).brag(activity, bundle, iUiListener);
        return 0;
    }

    public int challenge(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).challenge(activity, bundle, iUiListener);
        return 0;
    }

    public int gift(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).gift(activity, bundle, iUiListener);
        return 0;
    }

    public int invite(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).invite(activity, bundle, iUiListener);
        return 0;
    }

    public int story(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).story(activity, bundle, iUiListener);
        return 0;
    }

    public void setAvatar(Activity activity, Bundle bundle, IUiListener iUiListener) {
        String string = bundle.getString("picture");
        new QQAvatar(this.mContext, this.mQQAuth.getQQToken()).setAvatar(activity, Uri.parse(string), iUiListener, bundle.getInt("exitAnim"));
    }

    public void setAvatar(Activity activity, Bundle bundle, IUiListener iUiListener, int i, int i2) {
        bundle.putInt("exitAnim", i2);
        activity.overridePendingTransition(i, 0);
        setAvatar(activity, bundle, iUiListener);
    }

    public void requestAsync(String str, Bundle bundle, String str2, IRequestListener iRequestListener, Object obj) {
        HttpUtils.requestAsync(this.mQQAuth.getQQToken(), this.mContext, str, bundle, str2, iRequestListener);
    }

    public JSONObject request(String str, Bundle bundle, String str2) throws IOException, JSONException, NetworkUnavailableException, HttpStatusException {
        return HttpUtils.request(this.mQQAuth.getQQToken(), this.mContext, str, bundle, str2);
    }

    public void shareToQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new QQShare(activity, this.mQQAuth.getQQToken()).shareToQQ(activity, bundle, iUiListener);
    }

    public void shareToQzone(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new QzoneShare(activity, this.mQQAuth.getQQToken()).shareToQzone(activity, bundle, iUiListener);
    }

    public void grade(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).grade(activity, bundle, iUiListener);
    }

    public void voice(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new SocialApi(activity, this.mQQAuth.getQQToken()).voice(activity, bundle, iUiListener);
    }

    public void showTaskGuideWindow(Activity activity, Bundle bundle, IUiListener iUiListener) {
        new TaskGuide(activity, this.mQQAuth.getQQToken()).showTaskGuideWindow(activity, bundle, iUiListener);
    }

    public void startAppbar(Activity activity, String str) {
        if (this.mAgent == null) {
            this.mAgent = new AppbarAgent(activity, this.mQQAuth.getQQToken());
        }
        this.mAgent.startAppbar(str);
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        return false;
    }

    public boolean isSessionValid() {
        return this.mQQAuth.isSessionValid();
    }

    public String getAppId() {
        return this.mQQAuth.getQQToken().getAppId();
    }

    public String getAccessToken() {
        return this.mQQAuth.getQQToken().getAccessToken();
    }

    public long getExpiresIn() {
        return this.mQQAuth.getQQToken().getExpireTimeInSecond();
    }

    public String getOpenId() {
        return this.mQQAuth.getQQToken().getOpenId();
    }

    public void setAccessToken(String str, String str2) {
        C1711d.m4636a("openSDK_LOG", "setAccessToken(), expiresIn = " + str2 + "");
        this.mQQAuth.setAccessToken(str, str2);
    }

    public void setOpenId(String str) {
        C1711d.m4636a("openSDK_LOG", "setOpenId() --start");
        this.mQQAuth.setOpenId(this.mContext, str);
        C1711d.m4636a("openSDK_LOG", "setOpenId() --end");
    }

    public boolean isReady() {
        return isSessionValid() && getOpenId() != null;
    }

    public QQToken getQQToken() {
        return this.mQQAuth.getQQToken();
    }

    public boolean isSupportSSOLogin(Activity activity) {
        if (SystemUtils.getAppVersionName(activity, Constants.MOBILEQQ_PACKAGE_NAME) == null) {
            Toast.makeText(activity, "没有安装手Q", 0).show();
            return false;
        } else if (SystemUtils.checkMobileQQ(activity)) {
            Toast.makeText(activity, "已安装的手Q版本支持SSO登陆", 0).show();
            return true;
        } else {
            Toast.makeText(activity, "已安装的手Q版本不支持SSO登陆", 0).show();
            return false;
        }
    }
}
