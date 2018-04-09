package com.sina.weibo.sdk.api.share;

import android.content.Context;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;

interface IVersionCheckHandler {
    boolean check(Context context, WeiboMessage weiboMessage);

    boolean check(Context context, WeiboMultiMessage weiboMultiMessage);
}
