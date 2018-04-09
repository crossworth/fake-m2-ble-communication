package com.umeng.socialize.media;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.weixin.net.WXAuthUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class WeixinExtra {
    public static void isAccessTokenValid(String accessToken, String openid, final UMAuthListener umAuthListener) {
        final String url = "https://api.weixin.qq.com/sns/auth?access_token=" + accessToken + "&openid=" + openid;
        new Thread(new Runnable() {
            public void run() {
                try {
                    final JSONObject jsonObject = new JSONObject(WXAuthUtils.request(url));
                    if (jsonObject != null) {
                        QueuedWork.runInMain(new Runnable() {
                            public void run() {
                                Map<String, String> map = new HashMap();
                                if (jsonObject.optInt("errcode", -1) == 0) {
                                    map.put("result", jsonObject.toString());
                                    umAuthListener.onComplete(SHARE_MEDIA.WEIXIN, 2, map);
                                    return;
                                }
                                umAuthListener.onError(SHARE_MEDIA.WEIXIN, 2, new Throwable(jsonObject.toString()));
                            }
                        });
                    }
                } catch (JSONException e) {
                }
            }
        }).start();
    }
}
