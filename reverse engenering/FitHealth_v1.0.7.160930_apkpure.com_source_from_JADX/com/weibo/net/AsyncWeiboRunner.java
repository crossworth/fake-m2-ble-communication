package com.weibo.net;

import android.content.Context;
import java.io.IOException;

public class AsyncWeiboRunner {
    private Weibo mWeibo;

    public interface RequestListener {
        void onComplete(String str);

        void onError(WeiboException weiboException);

        void onIOException(IOException iOException);
    }

    public AsyncWeiboRunner(Weibo weibo) {
        this.mWeibo = weibo;
    }

    public void request(Context context, String url, WeiboParameters params, String httpMethod, RequestListener listener) {
        final Context context2 = context;
        final String str = url;
        final WeiboParameters weiboParameters = params;
        final String str2 = httpMethod;
        final RequestListener requestListener = listener;
        new Thread() {
            public void run() {
                try {
                    requestListener.onComplete(AsyncWeiboRunner.this.mWeibo.request(context2, str, weiboParameters, str2, AsyncWeiboRunner.this.mWeibo.getAccessToken()));
                } catch (WeiboException e) {
                    requestListener.onError(e);
                }
            }
        }.run();
    }
}
