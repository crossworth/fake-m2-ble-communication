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
import com.tencent.utils.AsynLoadImg;
import com.tencent.utils.AsynLoadImgBack;
import com.tencent.utils.SystemUtils;
import com.tencent.utils.TemporaryStorage;
import com.tencent.utils.Util;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.io.File;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QQShare extends BaseApi {
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final int SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1;
    public static final int SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2;
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_KEY_TYPE = "req_type";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final int SHARE_TO_QQ_TYPE_AUDIO = 2;
    public static final int SHARE_TO_QQ_TYPE_DEFAULT = 1;
    public static final int SHARE_TO_QQ_TYPE_IMAGE = 5;

    public QQShare(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public void shareToQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        String string = bundle.getString("imageUrl");
        String string2 = bundle.getString("title");
        String string3 = bundle.getString("summary");
        String string4 = bundle.getString("targetUrl");
        String string5 = bundle.getString("imageLocalUrl");
        int i = bundle.getInt("req_type", 1);
        if (Util.hasSDCard() || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
            if (i == 5) {
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) < 0) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_QQ_VERSION_ERROR, null));
                    C1711d.m4636a("openSDK_LOG", "shareToQQ() both null--end");
                    return;
                } else if (!Util.fileExists(string5)) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                    C1711d.m4636a("openSDK_LOG", "shareToQQ()--end非法的图片地址!");
                    return;
                }
            }
            if (i != 5) {
                if (TextUtils.isEmpty(string4) || !(string4.startsWith("http://") || string4.startsWith("https://"))) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_ERROR, null));
                    C1711d.m4636a("openSDK_LOG", "shareToQQ() targetUrl error--end");
                    return;
                } else if (TextUtils.isEmpty(string2)) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_TITLE_NULL_ERROR, null));
                    C1711d.m4636a("openSDK_LOG", "shareToQQ() title null--end");
                    return;
                }
            }
            if (TextUtils.isEmpty(string) || string.startsWith("http://") || string.startsWith("https://") || new File(string).exists()) {
                if (!TextUtils.isEmpty(string2) && string2.length() > 45) {
                    bundle.putString("title", Util.subString(string2, 45, null, null));
                }
                if (!TextUtils.isEmpty(string3) && string3.length() > 60) {
                    bundle.putString("summary", Util.subString(string3, 60, null, null));
                }
                if (Util.isMobileQQSupportShare(activity)) {
                    m4671a(activity, bundle, iUiListener);
                } else {
                    m4674c(activity, bundle, iUiListener);
                }
                C1711d.m4636a("openSDK_LOG", "shareToQQ() --end");
                return;
            }
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
            C1711d.m4636a("openSDK_LOG", "shareToQQ() image url error--end");
            return;
        }
        iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
        C1711d.m4636a("openSDK_LOG", "shareToQzone() sdcard is null--end");
    }

    private void m4671a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        C1711d.m4636a("openSDK_LOG", "shareToMobileQQ() --start");
        String string = bundle.getString("imageUrl");
        final String string2 = bundle.getString("title");
        final String string3 = bundle.getString("summary");
        if (TextUtils.isEmpty(string)) {
            m4673b(activity, bundle, iUiListener);
        } else if (!Util.isValidUrl(string)) {
            bundle.putString("imageUrl", null);
            if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) < 0) {
                m4673b(activity, bundle, iUiListener);
            } else {
                r2 = bundle;
                r5 = iUiListener;
                r6 = activity;
                C0713a.m2382a((Context) activity, string, new AsynLoadImgBack(this) {
                    final /* synthetic */ QQShare f4545f;

                    public void saved(int i, String str) {
                        if (i == 0) {
                            r2.putString("imageLocalUrl", str);
                        } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                            if (r5 != null) {
                                r5.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                                C1711d.m4636a(SystemUtils.QQ_SHARE_CALLBACK_ACTION, Constants.MSG_SHARE_GETIMG_ERROR);
                                return;
                            }
                            return;
                        }
                        this.f4545f.m4673b(r6, r2, r5);
                    }

                    public void batchSaved(int i, ArrayList<String> arrayList) {
                    }
                });
            }
        } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
            if (iUiListener != null) {
                iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
                Log.v(SystemUtils.QQ_SHARE_CALLBACK_ACTION, Constants.MSG_SHARE_NOSD_ERROR);
                return;
            }
            return;
        } else if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) >= 0) {
            m4673b(activity, bundle, iUiListener);
        } else {
            r2 = bundle;
            r5 = iUiListener;
            r6 = activity;
            new AsynLoadImg(activity).save(string, new AsynLoadImgBack(this) {
                final /* synthetic */ QQShare f4551f;

                public void saved(int i, String str) {
                    if (i == 0) {
                        r2.putString("imageLocalUrl", str);
                    } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                        if (r5 != null) {
                            r5.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                            C1711d.m4636a(SystemUtils.QQ_SHARE_CALLBACK_ACTION, Constants.MSG_SHARE_GETIMG_ERROR);
                            return;
                        }
                        return;
                    }
                    this.f4551f.m4673b(r6, r2, r5);
                }

                public void batchSaved(int i, ArrayList<String> arrayList) {
                }
            });
        }
        C1711d.m4636a("openSDK_LOG", "shareToMobileQQ() --start");
    }

    private void m4673b(Activity activity, Bundle bundle, IUiListener iUiListener) {
        C1711d.m4636a("openSDK_LOG", "doShareToQQ() --start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_fri?src_type=app&version=1&file_type=news");
        Object string = bundle.getString("imageUrl");
        Object string2 = bundle.getString("title");
        Object string3 = bundle.getString("summary");
        Object string4 = bundle.getString("targetUrl");
        Object string5 = bundle.getString("audio_url");
        int i = bundle.getInt("req_type", 1);
        int i2 = bundle.getInt("cflag", 0);
        Object string6 = bundle.getString("share_qq_ext_str");
        String applicationLable = Util.getApplicationLable(activity);
        if (applicationLable == null) {
            applicationLable = bundle.getString("appName");
        }
        Object string7 = bundle.getString("imageLocalUrl");
        Object appId = this.mToken.getAppId();
        Object openId = this.mToken.getOpenId();
        Log.v(SystemUtils.QQ_SHARE_CALLBACK_ACTION, "openId:" + openId);
        if (!TextUtils.isEmpty(string)) {
            stringBuffer.append("&image_url=" + Base64.encodeToString(string.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(string7)) {
            stringBuffer.append("&file_data=" + Base64.encodeToString(string7.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(string2)) {
            stringBuffer.append("&title=" + Base64.encodeToString(string2.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(string3)) {
            stringBuffer.append("&description=" + Base64.encodeToString(string3.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=" + appId);
        }
        if (!TextUtils.isEmpty(string4)) {
            stringBuffer.append("&url=" + Base64.encodeToString(string4.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(applicationLable)) {
            if (applicationLable.length() > 20) {
                applicationLable = applicationLable.substring(0, 20) + com.zhuoyou.plugin.bluetooth.data.Util.TEXT_POSTFIX;
            }
            stringBuffer.append("&app_name=" + Base64.encodeToString(applicationLable.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(openId)) {
            stringBuffer.append("&open_id=" + Base64.encodeToString(openId.getBytes(), 2));
        }
        if (!TextUtils.isEmpty(string5)) {
            stringBuffer.append("&audioUrl=" + Base64.encodeToString(string5.getBytes(), 2));
        }
        stringBuffer.append("&req_type=" + Base64.encodeToString(String.valueOf(i).getBytes(), 2));
        if (!TextUtils.isEmpty(string6)) {
            stringBuffer.append("&share_qq_ext_str=" + Base64.encodeToString(string6.getBytes(), 2));
        }
        stringBuffer.append("&cflag=" + Base64.encodeToString(String.valueOf(i2).getBytes(), 2));
        Log.v(SystemUtils.QQ_SHARE_CALLBACK_ACTION, stringBuffer.toString());
        C0687a.m2306a(this.mContext, this.mToken, "requireApi", "shareToNativeQQ");
        this.mActivityIntent = new Intent("android.intent.action.VIEW");
        this.mActivityIntent.setData(Uri.parse(stringBuffer.toString()));
        if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_6_0) >= 0) {
            Object obj = TemporaryStorage.set(SystemUtils.QQ_SHARE_CALLBACK_ACTION, iUiListener);
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

    private void m4674c(Activity activity, Bundle bundle, IUiListener iUiListener) {
        Object obj = TemporaryStorage.set(SystemUtils.QQ_SHARE_CALLBACK_ACTION, iUiListener);
        if (obj != null) {
            ((IUiListener) obj).onCancel();
        }
        C1711d.m4636a("openSDK_LOG", "shareToH5QQ() --start");
        StringBuffer stringBuffer = new StringBuffer("http://openmobile.qq.com/api/check?page=shareindex.html&style=9");
        if (bundle == null) {
            bundle = new Bundle();
        }
        stringBuffer = m4670a(stringBuffer, bundle);
        C0687a.m2306a(this.mContext, this.mToken, "requireApi", "shareToH5QQ");
        if (!(Util.openBrowser(activity, stringBuffer.toString()) || iUiListener == null)) {
            iUiListener.onError(new UiError(-6, Constants.MSG_OPEN_BROWSER_ERROR, null));
        }
        C1711d.m4636a("openSDK_LOG", "shareToH5QQ() --end");
    }

    private StringBuffer m4670a(StringBuffer stringBuffer, Bundle bundle) {
        C1711d.m4636a("openSDK_LOG", "fillShareToQQParams() --start");
        String str = com.zhuoyou.plugin.bluetooth.data.Util.TEXT_POSTFIX;
        bundle.putString("action", SystemUtils.QQ_SHARE_CALLBACK_ACTION);
        bundle.putString(MessageObj.APPID, this.mToken.getAppId());
        bundle.putString("sdkp", "a");
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        String str2 = "content";
        if (bundle.containsKey(str2) && bundle.getString(str2).length() > 40) {
            bundle.putString(str2, bundle.getString(str2).substring(0, 40) + str);
        }
        str2 = "summary";
        if (bundle.containsKey(str2) && bundle.getString(str2).length() > 80) {
            bundle.putString(str2, bundle.getString(str2).substring(0, 80) + str);
        }
        stringBuffer.append("&" + Util.encodeUrl(bundle).replaceAll("\\+", "%20"));
        C1711d.m4636a("openSDK_LOG", "fillShareToQQParams() --end");
        return stringBuffer;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }
}
