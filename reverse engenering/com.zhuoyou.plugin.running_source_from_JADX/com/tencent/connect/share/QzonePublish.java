package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.p025a.C1148a;
import com.tencent.open.TDialog;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.p037b.C1322d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QzonePublish extends BaseApi {
    public static final String PUBLISH_TO_QZONE_APP_NAME = "appName";
    public static final String PUBLISH_TO_QZONE_IMAGE_URL = "imageUrl";
    public static final String PUBLISH_TO_QZONE_KEY_TYPE = "req_type";
    public static final String PUBLISH_TO_QZONE_SUMMARY = "summary";
    public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD = 3;
    public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO = 4;
    public static final String PUBLISH_TO_QZONE_VIDEO_DURATION = "videoDuration";
    public static final String PUBLISH_TO_QZONE_VIDEO_PATH = "videoPath";
    public static final String PUBLISH_TO_QZONE_VIDEO_SIZE = "videoSize";

    public QzonePublish(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void publishToQzone(Activity activity, Bundle bundle, final IUiListener iUiListener) {
        int i = 0;
        C1314f.m3870c("openSDK_LOG.QzonePublish", "publishToQzone() -- start");
        if (bundle == null) {
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_NULL_ERROR, null));
            C1314f.m3872e("openSDK_LOG.QzonePublish", "-->publishToQzone, params is null");
            C1322d.m3896a().m3897a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_NULL_ERROR);
        } else if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_9_5) < 0) {
            iUiListener.onError(new UiError(-15, Constants.MSG_PARAM_VERSION_TOO_LOW, null));
            C1314f.m3872e("openSDK_LOG.QzonePublish", "-->publishToQzone, this is not support below qq 5.9.5");
            C1322d.m3896a().m3897a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "publicToQzone, this is not support below qq 5.9.5");
            new TDialog(activity, "", getCommonDownloadQQUrl(""), null, this.mToken).show();
        } else {
            String string = bundle.getString("summary");
            ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
            Object applicationLable = Util.getApplicationLable(activity);
            if (applicationLable == null) {
                applicationLable = bundle.getString("appName");
            } else if (applicationLable.length() > 20) {
                applicationLable = applicationLable.substring(0, 20) + com.droi.btlib.service.Util.TEXT_POSTFIX;
            }
            if (!TextUtils.isEmpty(applicationLable)) {
                bundle.putString("appName", applicationLable);
            }
            bundle.putString("summary", string);
            int i2 = bundle.getInt("req_type");
            if (i2 == 3) {
                if (stringArrayList != null && stringArrayList.size() > 0) {
                    while (i < stringArrayList.size()) {
                        if (!Util.fileExists((String) stringArrayList.get(i))) {
                            stringArrayList.remove(i);
                        }
                        i++;
                    }
                    bundle.putStringArrayList("imageUrl", stringArrayList);
                }
                m3459a(activity, bundle, iUiListener);
                C1314f.m3870c("openSDK_LOG.QzonePublish", "publishToQzone() --end");
            } else if (i2 == 4) {
                string = bundle.getString(PUBLISH_TO_QZONE_VIDEO_PATH);
                if (Util.fileExists(string)) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    final Bundle bundle2 = bundle;
                    final Activity activity2 = activity;
                    final IUiListener iUiListener2 = iUiListener;
                    mediaPlayer.setOnPreparedListener(new OnPreparedListener(this) {
                        final /* synthetic */ QzonePublish f3674e;

                        public void onPrepared(MediaPlayer mediaPlayer) {
                            long length = new File(string).length();
                            int duration = mediaPlayer.getDuration();
                            bundle2.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, string);
                            bundle2.putInt(QzonePublish.PUBLISH_TO_QZONE_VIDEO_DURATION, duration);
                            bundle2.putLong(QzonePublish.PUBLISH_TO_QZONE_VIDEO_SIZE, length);
                            this.f3674e.m3459a(activity2, bundle2, iUiListener2);
                            C1314f.m3870c("openSDK_LOG.QzonePublish", "publishToQzone() --end");
                        }
                    });
                    mediaPlayer.setOnErrorListener(new OnErrorListener(this) {
                        final /* synthetic */ QzonePublish f3676b;

                        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                            C1314f.m3872e("openSDK_LOG.QzonePublish", "publishToQzone() mediaplayer onError()");
                            iUiListener.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
                            return false;
                        }
                    });
                    try {
                        mediaPlayer.setDataSource(string);
                        mediaPlayer.prepareAsync();
                        return;
                    } catch (Exception e) {
                        C1314f.m3872e("openSDK_LOG.QzonePublish", "publishToQzone() exception(s) occurred when preparing mediaplayer");
                        iUiListener.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
                        return;
                    }
                }
                C1314f.m3872e("openSDK_LOG.QzonePublish", "publishToQzone() video url invalid");
                iUiListener.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
            } else {
                iUiListener.onError(new UiError(-5, Constants.MSG_SHARE_TYPE_ERROR, null));
                C1314f.m3872e("openSDK_LOG.QzonePublish", "publishToQzone() error--end请选择支持的分享类型");
                C1322d.m3896a().m3897a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "publishToQzone() 请选择支持的分享类型");
            }
        }
    }

    private void m3459a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        C1314f.m3870c("openSDK_LOG.QzonePublish", "doPublishToQzone() --start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://qzone/publish?src_type=app&version=1&file_type=news");
        ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
        Object string = bundle.getString("summary");
        int i = bundle.getInt("req_type", 3);
        Object string2 = bundle.getString("appName");
        String string3 = bundle.getString(PUBLISH_TO_QZONE_VIDEO_PATH);
        int i2 = bundle.getInt(PUBLISH_TO_QZONE_VIDEO_DURATION);
        long j = bundle.getLong(PUBLISH_TO_QZONE_VIDEO_SIZE);
        Object appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        C1314f.m3864a("openSDK_LOG.QzonePublish", "openId:" + openId);
        String str = "";
        if (3 == i && stringArrayList != null) {
            String str2 = Constants.VIA_SHARE_TYPE_PUBLISHMOOD;
            StringBuffer stringBuffer2 = new StringBuffer();
            int size = stringArrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                stringBuffer2.append(URLEncoder.encode((String) stringArrayList.get(i3)));
                if (i3 != size - 1) {
                    stringBuffer2.append(";");
                }
            }
            stringBuffer.append("&image_url=" + Base64.encodeToString(Util.getBytesUTF8(stringBuffer2.toString()), 2));
            str = str2;
        }
        if (4 == i) {
            str = Constants.VIA_SHARE_TYPE_PUBLISHVIDEO;
            stringBuffer.append("&videoPath=" + Base64.encodeToString(Util.getBytesUTF8(string3), 2));
            stringBuffer.append("&videoDuration=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i2)), 2));
            stringBuffer.append("&videoSize=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(j)), 2));
        }
        String str3 = str;
        if (!TextUtils.isEmpty(string)) {
            stringBuffer.append("&description=" + Base64.encodeToString(Util.getBytesUTF8(string), 2));
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=" + appId);
        }
        if (!TextUtils.isEmpty(string2)) {
            stringBuffer.append("&app_name=" + Base64.encodeToString(Util.getBytesUTF8(string2), 2));
        }
        if (!Util.isEmpty(openId)) {
            stringBuffer.append("&open_id=" + Base64.encodeToString(Util.getBytesUTF8(openId), 2));
        }
        stringBuffer.append("&req_type=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i)), 2));
        C1314f.m3864a("openSDK_LOG.QzonePublish", "doPublishToQzone, url: " + stringBuffer.toString());
        C1148a.m3344a(Global.getContext(), this.mToken, "requireApi", "shareToNativeQQ");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(stringBuffer.toString()));
        intent.putExtra("pkg_name", activity.getPackageName());
        if (hasActivityForIntent(intent)) {
            startAssistActivity(activity, 10104, intent, false);
            C1322d.m3896a().m3897a(0, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent success");
            C1322d.m3896a().m3900a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QZONE, Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE, "3", "1", str3, "0", "1", "0");
        } else {
            C1314f.m3872e("openSDK_LOG.QzonePublish", "doPublishToQzone() target activity not found");
            C1322d.m3896a().m3897a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent fail");
            C1322d.m3896a().m3900a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QZONE, Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE, "3", "1", str3, "0", "1", "0");
        }
        C1314f.m3870c("openSDK_LOG", "doPublishToQzone() --end");
    }
}
