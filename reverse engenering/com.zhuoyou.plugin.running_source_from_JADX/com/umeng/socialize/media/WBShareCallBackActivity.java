package com.umeng.socialize.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.SinaSsoHandler;
import com.umeng.socialize.utils.Log;

public class WBShareCallBackActivity extends Activity implements Response {
    private final String TAG = WBShareCallBackActivity.class.getSimpleName();
    protected SinaSsoHandler sinaSsoHandler = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.m4551i("create sina callback activity");
        this.sinaSsoHandler = (SinaSsoHandler) UMShareAPI.get(getApplicationContext()).getHandler(SHARE_MEDIA.SINA);
        Log.m4549e(this.TAG, "handleid=" + this.sinaSsoHandler);
        this.sinaSsoHandler.onCreate(this, PlatformConfig.getPlatform(SHARE_MEDIA.SINA));
        if (getIntent() != null) {
            handleIntent(getIntent());
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        UMShareAPI api = UMShareAPI.get(getApplicationContext());
        Log.m4549e(this.TAG, "handleid=" + this.sinaSsoHandler);
        this.sinaSsoHandler = (SinaSsoHandler) api.getHandler(SHARE_MEDIA.SINA);
        this.sinaSsoHandler.onCreate(this, PlatformConfig.getPlatform(SHARE_MEDIA.SINA));
        handleIntent(intent);
    }

    protected void handleIntent(Intent intent) {
        this.sinaSsoHandler.getmWeiboShareAPI().handleWeiboResponse(intent, this);
    }

    public void onResponse(BaseResponse baseResponse) {
        if (this.sinaSsoHandler != null) {
            this.sinaSsoHandler.onResponse(baseResponse);
        }
        finish();
    }
}
