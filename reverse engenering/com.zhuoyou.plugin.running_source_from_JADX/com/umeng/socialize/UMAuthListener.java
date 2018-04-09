package com.umeng.socialize;

import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;

public interface UMAuthListener {
    public static final int ACTION_AUTHORIZE = 0;
    public static final int ACTION_DELETE = 1;
    public static final int ACTION_GET_PROFILE = 2;
    public static final UMAuthListener dummy = new C15701();

    static class C15701 implements UMAuthListener {
        C15701() {
        }

        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
        }

        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    }

    void onCancel(SHARE_MEDIA share_media, int i);

    void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map);

    void onError(SHARE_MEDIA share_media, int i, Throwable th);
}
