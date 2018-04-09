package com.umeng.socialize.media;

import android.content.Context;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.PlatformConfig.SinaWeibo;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;

public class SinaExtra {
    public static void JudgeAccessToken(Context context, String AccessToken, final UMAuthListener listener) {
        WeiboParameters params = new WeiboParameters(((SinaWeibo) PlatformConfig.getPlatform(SHARE_MEDIA.SINA)).appKey);
        params.put("access_token", AccessToken);
        new AsyncWeiboRunner(context).requestAsync("https://api.weibo.com/oauth2/get_token_info", params, Constants.HTTP_POST, new RequestListener() {
            public void onComplete(String s) {
                Map<String, String> map = new HashMap();
                map.put("result", s);
                listener.onComplete(SHARE_MEDIA.SINA, 2, map);
            }

            public void onWeiboException(WeiboException e) {
                listener.onError(SHARE_MEDIA.SINA, 2, new Throwable(e));
            }
        });
    }
}
