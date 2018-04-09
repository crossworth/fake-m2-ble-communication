package com.umeng.socialize.shareboard;

import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;

public final class SnsPlatform {
    public String mGrayIcon;
    public String mIcon;
    public int mIndex;
    public String mKeyword;
    public SHARE_MEDIA mPlatform;
    public String mShowWord;

    static class C0972a {
        static Map<SHARE_MEDIA, SnsPlatform> f3331a = new HashMap();

        C0972a() {
            C0972a.m3227a();
        }

        static SnsPlatform m3226a(SHARE_MEDIA share_media) {
            return (SnsPlatform) f3331a.get(share_media);
        }

        private static void m3227a() {
            SnsPlatform snsPlatform = new SnsPlatform();
            snsPlatform.mPlatform = SHARE_MEDIA.YNOTE;
            snsPlatform.mIcon = "umeng_socialize_ynote";
            snsPlatform.mGrayIcon = "umeng_socialize_ynote_gray";
            snsPlatform.mShowWord = "ynote_showword";
            f3331a.put(SHARE_MEDIA.YNOTE, snsPlatform);
        }
    }

    public SnsPlatform(String str) {
        this.mKeyword = str;
        this.mPlatform = SHARE_MEDIA.convertToEmun(str);
    }
}
