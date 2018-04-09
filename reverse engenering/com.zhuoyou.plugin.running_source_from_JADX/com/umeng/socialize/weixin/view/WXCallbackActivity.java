package com.umeng.socialize.weixin.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.UMWXHandler;
import com.umeng.socialize.utils.Log;

public abstract class WXCallbackActivity extends Activity implements IWXAPIEventHandler {
    private final String TAG = WXCallbackActivity.class.getSimpleName();
    protected UMWXHandler mWxHandler = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.m4551i("create wx callback activity");
        this.mWxHandler = (UMWXHandler) UMShareAPI.get(getApplicationContext()).getHandler(SHARE_MEDIA.WEIXIN);
        Log.m4549e(this.TAG, "handleid=" + this.mWxHandler);
        this.mWxHandler.onCreate(getApplicationContext(), PlatformConfig.getPlatform(SHARE_MEDIA.WEIXIN));
        handleIntent(getIntent());
    }

    protected void handleIntent(Intent intent) {
        this.mWxHandler.getWXApi().handleIntent(intent, this);
    }

    protected void onNewIntent(Intent paramIntent) {
        Log.m4546d(this.TAG, "### WXCallbackActivity   onNewIntent");
        super.onNewIntent(paramIntent);
        setIntent(paramIntent);
        this.mWxHandler = (UMWXHandler) UMShareAPI.get(getApplicationContext()).getHandler(SHARE_MEDIA.WEIXIN);
        Log.m4549e(this.TAG, "handleid=" + this.mWxHandler);
        this.mWxHandler.onCreate(getApplicationContext(), PlatformConfig.getPlatform(SHARE_MEDIA.WEIXIN));
        handleIntent(paramIntent);
    }

    public void onResp(BaseResp resp) {
        if (!(this.mWxHandler == null || resp == null)) {
            try {
                this.mWxHandler.getWXEventHandler().onResp(resp);
            } catch (Exception e) {
            }
        }
        finish();
    }

    public void onReq(BaseReq req) {
        if (this.mWxHandler != null) {
            this.mWxHandler.getWXEventHandler().onReq(req);
        }
        finish();
    }
}
