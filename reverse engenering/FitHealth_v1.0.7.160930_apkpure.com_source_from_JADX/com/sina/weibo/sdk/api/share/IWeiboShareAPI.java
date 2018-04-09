package com.sina.weibo.sdk.api.share;

import android.content.Intent;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Request;
import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
import com.sina.weibo.sdk.exception.WeiboShareException;

public interface IWeiboShareAPI {
    boolean checkEnvironment(boolean z) throws WeiboShareException;

    int getWeiboAppSupportAPI();

    boolean handleWeiboRequest(Intent intent, Request request);

    boolean handleWeiboResponse(Intent intent, Response response);

    boolean isWeiboAppInstalled();

    boolean isWeiboAppSupportAPI();

    boolean launchWeibo();

    boolean registerApp();

    void registerWeiboDownloadListener(IWeiboDownloadListener iWeiboDownloadListener);

    boolean sendRequest(BaseRequest baseRequest);

    boolean sendResponse(BaseResponse baseResponse);
}
