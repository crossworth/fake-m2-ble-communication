package com.umeng.socialize.handler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.PlatformConfig.QQZone;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.utils.Log;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class UMTencentSSOHandler extends UMSSOHandler {
    private static final String PUBLIC_ACCOUNT = "100424468";
    private static final String TAG = "UMTencentSSOHandler";
    protected static Map<String, String> mImageCache = new HashMap();
    public QQZone config = null;
    protected UMAuthListener mAuthListener;
    protected UMAuthListener mAuthListenerBackup;
    protected String mImageUrl = null;
    protected ProgressDialog mProgressDialog = null;
    protected UMShareListener mShareListener;
    protected Tencent mTencent;

    protected interface ObtainAppIdListener {
        void onComplete();
    }

    public interface ObtainImageUrlListener {
        void onComplete(String str);
    }

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        this.config = (QQZone) p;
        Log.m4546d("appid", "appid qq:" + this.config.appId);
        this.mTencent = Tencent.createInstance(this.config.appId, context);
        if (this.mTencent == null) {
            Log.m4549e(TAG, "Tencent变量初始化失败，请检查你的app id跟AndroidManifest.xml文件中AuthActivity的scheme是否填写正确");
            throw new SocializeException("Tencent变量初始化失败，请检查你的app id跟AndroidManifest.xml文件中AuthActivity的scheme是否填写正确.");
        }
    }

    protected Bundle parseOauthData(Object response) {
        Bundle bundle = new Bundle();
        if (response != null) {
            String jsonStr = response.toString().trim();
            if (!TextUtils.isEmpty(jsonStr)) {
                JSONObject json = null;
                try {
                    json = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (json != null) {
                    bundle.putString("auth_time", json.optString("auth_time", ""));
                    bundle.putString("pay_token", json.optString("pay_token", ""));
                    bundle.putString(Constants.PARAM_PLATFORM_ID, json.optString(Constants.PARAM_PLATFORM_ID, ""));
                    bundle.putString("ret", String.valueOf(json.optInt("ret", -1)));
                    bundle.putString("sendinstall", json.optString("sendinstall", ""));
                    bundle.putString("page_type", json.optString("page_type", ""));
                    bundle.putString("appid", json.optString("appid", ""));
                    bundle.putString("openid", json.optString("openid", ""));
                    bundle.putString("uid", json.optString("openid", ""));
                    bundle.putString("expires_in", json.optString("expires_in", ""));
                    bundle.putString("pfkey", json.optString("pfkey", ""));
                    bundle.putString("access_token", json.optString("access_token", ""));
                }
            }
        }
        return bundle;
    }

    protected String getAppName() {
        if (!TextUtils.isEmpty(Config.QQAPPNAME)) {
            return Config.QQAPPNAME;
        }
        String appName = "";
        if (getContext() == null) {
            return appName;
        }
        CharSequence sequence = getContext().getApplicationInfo().loadLabel(getContext().getPackageManager());
        if (TextUtils.isEmpty(sequence)) {
            return appName;
        }
        return sequence.toString();
    }

    public void getBitmapUrl(UMediaObject uMediaObjects, String usid, ObtainImageUrlListener listener) {
        UMediaObject media = uMediaObjects;
        long startTime = System.currentTimeMillis();
        UMImage image = null;
        if (media instanceof UMImage) {
            image = (UMImage) media;
        }
        if (image != null) {
            String imageCachePath = (String) mImageCache.get(image.asFileImage().toString());
            if (!TextUtils.isEmpty(imageCachePath)) {
                this.mImageUrl = imageCachePath;
                Log.m4552i(TAG, "obtain image url form cache..." + this.mImageUrl);
            }
        }
        Log.m4552i(TAG, "doInBackground end...");
        listener.onComplete(this.mImageUrl);
    }

    private void setImageUrl(String localPath, String urlPath) {
        if (!TextUtils.isEmpty(urlPath)) {
            mImageCache.put(localPath, urlPath);
            this.mImageUrl = urlPath;
        }
    }

    protected boolean validTencent() {
        return this.mTencent != null && this.mTencent.getAppId().equals(this.config.appId);
    }
}
