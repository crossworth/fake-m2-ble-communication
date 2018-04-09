package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.p010a.C0687a;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.AsynLoadImgBack;
import com.tencent.utils.SystemUtils;
import com.tencent.utils.TemporaryStorage;
import com.tencent.utils.Util;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.net.URLEncoder;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QzoneShare extends BaseApi {
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final String SHARE_TO_QZONE_KEY_TYPE = "req_type";
    public static final int SHARE_TO_QZONE_TYPE_IMAGE = 5;
    public static final int SHARE_TO_QZONE_TYPE_IMAGE_TEXT = 1;
    public static final int SHARE_TO_QZONE_TYPE_NO_TYPE = 0;
    private boolean f4556a = true;
    private boolean f4557b = false;
    private boolean f4558c = false;
    private boolean f4559d = false;

    public QzoneShare(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public void shareToQzone(final Activity activity, final Bundle bundle, final IUiListener iUiListener) {
        if (bundle == null) {
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_NULL_ERROR, null));
            return;
        }
        String string = bundle.getString("title");
        String string2 = bundle.getString("summary");
        Object string3 = bundle.getString("targetUrl");
        ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
        Object applicationLable = Util.getApplicationLable(activity);
        if (applicationLable == null) {
            applicationLable = bundle.getString("appName");
        } else if (applicationLable.length() > 20) {
            applicationLable = applicationLable.substring(0, 20) + com.zhuoyou.plugin.bluetooth.data.Util.TEXT_POSTFIX;
        }
        int i = bundle.getInt("req_type");
        switch (i) {
            case 1:
                this.f4556a = true;
                this.f4557b = false;
                this.f4558c = true;
                this.f4559d = false;
                break;
            case 5:
                iUiListener.onError(new UiError(-5, Constants.MSG_SHARE_TYPE_ERROR, null));
                C1711d.m4636a("openSDK_LOG", "shareToQzone() error--end暂不支持纯图片分享到空间，建议使用图文分享");
                return;
            default:
                if (!Util.isEmpty(string) || !Util.isEmpty(string2)) {
                    this.f4556a = true;
                } else if (stringArrayList == null || stringArrayList.size() == 0) {
                    string = "来自" + applicationLable + "的分享";
                    this.f4556a = true;
                } else {
                    this.f4556a = false;
                }
                this.f4557b = false;
                this.f4558c = true;
                this.f4559d = false;
                break;
        }
        if (Util.hasSDCard() || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
            String str;
            if (this.f4556a) {
                if (TextUtils.isEmpty(string3)) {
                    iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_TARGETURL_NULL_ERROR, null));
                    C1711d.m4636a("openSDK_LOG", "shareToQzone() targetUrl null error--end");
                    return;
                } else if (!Util.isValidUrl(string3)) {
                    iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_TARGETURL_ERROR, null));
                    C1711d.m4636a("openSDK_LOG", "shareToQzone() targetUrl error--end");
                    return;
                }
            }
            if (this.f4557b) {
                bundle.putString("title", "");
                bundle.putString("summary", "");
            } else if (this.f4558c && Util.isEmpty(string)) {
                iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_TITLE_NULL_ERROR, null));
                C1711d.m4636a("openSDK_LOG", "shareToQzone() title is null--end");
                return;
            } else {
                if (!Util.isEmpty(string) && string.length() > 200) {
                    bundle.putString("title", Util.subString(string, 200, null, null));
                }
                if (!Util.isEmpty(string2) && string2.length() > 600) {
                    bundle.putString("summary", Util.subString(string2, 600, null, null));
                }
            }
            if (!TextUtils.isEmpty(applicationLable)) {
                bundle.putString("appName", applicationLable);
            }
            if (stringArrayList != null && (stringArrayList == null || stringArrayList.size() != 0)) {
                for (int i2 = 0; i2 < stringArrayList.size(); i2++) {
                    str = (String) stringArrayList.get(i2);
                    if (!(Util.isValidUrl(str) || Util.isValidPath(str))) {
                        stringArrayList.remove(i2);
                    }
                }
                if (stringArrayList.size() == 0) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                    C1711d.m4636a("openSDK_LOG", "shareToQzone() MSG_PARAM_IMAGE_URL_FORMAT_ERROR--end");
                    return;
                }
                bundle.putStringArrayList("imageUrl", stringArrayList);
            } else if (this.f4559d) {
                iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_ERROR, null));
                C1711d.m4636a("openSDK_LOG", "shareToQzone() imageUrl is null--end");
                return;
            }
            if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_6_0) >= 0) {
                C0713a.m2383a((Context) activity, stringArrayList, new AsynLoadImgBack(this) {
                    final /* synthetic */ QzoneShare f4555d;

                    public void saved(int i, String str) {
                    }

                    public void batchSaved(int i, ArrayList<String> arrayList) {
                        if (i == 0) {
                            bundle.putStringArrayList("imageUrl", arrayList);
                        }
                        this.f4555d.m4676a(activity, bundle, iUiListener);
                    }
                });
            } else if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_2_0) < 0 || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_6_0) >= 0) {
                m4677a((Context) activity, bundle, iUiListener);
            } else {
                QQShare qQShare = new QQShare(activity, this.mToken);
                if (stringArrayList != null && stringArrayList.size() > 0) {
                    str = (String) stringArrayList.get(0);
                    if (i != 5 || Util.fileExists(str)) {
                        bundle.putString("imageLocalUrl", str);
                    } else {
                        iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_MUST_BE_LOCAL, null));
                        C1711d.m4636a("openSDK_LOG", "shareToQzone()手Q版本过低，纯图分享不支持网路图片");
                        return;
                    }
                }
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
                    bundle.putInt("cflag", 1);
                }
                qQShare.shareToQQ(activity, bundle, iUiListener);
            }
            C1711d.m4636a("openSDK_LOG", "shareToQzone() --end");
            return;
        }
        iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
        C1711d.m4636a("openSDK_LOG", "shareToQzone() sdcard is null--end");
    }

    private void m4676a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        C1711d.m4636a("openSDK_LOG", "doShareToQQ() --start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_qzone?src_type=app&version=1&file_type=news");
        ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
        Object string = bundle.getString("title");
        Object string2 = bundle.getString("summary");
        Object string3 = bundle.getString("targetUrl");
        String string4 = bundle.getString("audio_url");
        int i = bundle.getInt("req_type", 1);
        Object string5 = bundle.getString("appName");
        int i2 = bundle.getInt("cflag", 0);
        String string6 = bundle.getString("share_qq_ext_str");
        CharSequence appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        Log.v(SystemUtils.QQ_SHARE_CALLBACK_ACTION, "openId:" + openId);
        if (stringArrayList != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            int size = stringArrayList.size() > 9 ? 9 : stringArrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                stringBuffer2.append(URLEncoder.encode((String) stringArrayList.get(i3)));
                if (i3 != size - 1) {
                    stringBuffer2.append(";");
                }
            }
            stringBuffer.append("&image_url=" + Base64.encodeToString(stringBuffer2.toString().getBytes(), 2));
        }
        if (!TextUtils.isEmpty(string)) {
            stringBuffer.append("&title=" + Base64.encodeToString(string.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(string2)) {
            stringBuffer.append("&description=" + Base64.encodeToString(string2.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=" + appId);
        }
        if (!TextUtils.isEmpty(string3)) {
            stringBuffer.append("&url=" + Base64.encodeToString(string3.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(string5)) {
            stringBuffer.append("&app_name=" + Base64.encodeToString(string5.getBytes(), 2));
        }
        if (!Util.isEmpty(openId)) {
            stringBuffer.append("&open_id=" + Base64.encodeToString(openId.getBytes(), 2));
        }
        if (!Util.isEmpty(string4)) {
            stringBuffer.append("&audioUrl=" + Base64.encodeToString(string4.getBytes(), 2));
        }
        stringBuffer.append("&req_type=" + Base64.encodeToString(String.valueOf(i).getBytes(), 2));
        if (!Util.isEmpty(string6)) {
            stringBuffer.append("&share_qq_ext_str=" + Base64.encodeToString(string6.getBytes(), 2));
        }
        stringBuffer.append("&cflag=" + Base64.encodeToString(String.valueOf(i2).getBytes(), 2));
        Log.v(SystemUtils.QQ_SHARE_CALLBACK_ACTION, stringBuffer.toString());
        C0687a.m2306a(this.mContext, this.mToken, "requireApi", "shareToNativeQQ");
        this.mActivityIntent = new Intent("android.intent.action.VIEW");
        this.mActivityIntent.setData(Uri.parse(stringBuffer.toString()));
        if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_6_0) >= 0) {
            Object obj = TemporaryStorage.set(SystemUtils.QZONE_SHARE_CALLBACK_ACTION, iUiListener);
            if (obj != null) {
                ((IUiListener) obj).onCancel();
            }
            if (hasActivityForIntent()) {
                activity.startActivityForResult(this.mActivityIntent, 0);
            }
        } else if (hasActivityForIntent()) {
            startAssitActivity(activity, iUiListener);
        }
        C1711d.m4636a("openSDK_LOG", "doShareToQQ() --end");
    }

    private void m4677a(Context context, Bundle bundle, IUiListener iUiListener) {
        Object obj = TemporaryStorage.set(SystemUtils.QZONE_SHARE_CALLBACK_ACTION, iUiListener);
        if (obj != null) {
            ((IUiListener) obj).onCancel();
        }
        C1711d.m4636a("openSDK_LOG", "shareToH5Qzone() --start");
        StringBuffer stringBuffer = new StringBuffer("http://openmobile.qq.com/api/check2?page=qzshare.html&loginpage=loginindex.html&logintype=qzone");
        if (bundle == null) {
            bundle = new Bundle();
        }
        stringBuffer = m4675a(stringBuffer, bundle);
        C0687a.m2306a(this.mContext, this.mToken, "requireApi", "shareToH5QQ");
        if (!(Util.openBrowser(context, stringBuffer.toString()) || iUiListener == null)) {
            iUiListener.onError(new UiError(-6, Constants.MSG_OPEN_BROWSER_ERROR, null));
        }
        C1711d.m4636a("openSDK_LOG", "shareToH5QQ() --end");
    }

    private StringBuffer m4675a(StringBuffer stringBuffer, Bundle bundle) {
        C1711d.m4636a("openSDK_LOG", "fillShareToQQParams() --start");
        ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
        Object string = bundle.getString("appName");
        int i = bundle.getInt("req_type", 1);
        String string2 = bundle.getString("title");
        String string3 = bundle.getString("summary");
        bundle.putString(MessageObj.APPID, this.mToken.getAppId());
        bundle.putString("sdkp", "a");
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        String str = com.zhuoyou.plugin.bluetooth.data.Util.TEXT_POSTFIX;
        if (!Util.isEmpty(string2) && string2.length() > 40) {
            bundle.putString("title", string2.substring(0, 40) + com.zhuoyou.plugin.bluetooth.data.Util.TEXT_POSTFIX);
        }
        if (!Util.isEmpty(string3) && string3.length() > 80) {
            bundle.putString("summary", string3.substring(0, 80) + com.zhuoyou.plugin.bluetooth.data.Util.TEXT_POSTFIX);
        }
        if (!TextUtils.isEmpty(string)) {
            bundle.putString("site", string);
        }
        if (stringArrayList != null) {
            int size = stringArrayList.size();
            String[] strArr = new String[size];
            for (int i2 = 0; i2 < size; i2++) {
                strArr[i2] = (String) stringArrayList.get(i2);
            }
            bundle.putStringArray("imageUrl", strArr);
        }
        bundle.putString("type", String.valueOf(i));
        stringBuffer.append("&" + Util.encodeUrl(bundle).replaceAll("\\+", "%20"));
        C1711d.m4636a("openSDK_LOG", "fillShareToQQParams() --end");
        return stringBuffer;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }
}
