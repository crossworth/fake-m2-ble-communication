package com.umeng.socialize;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.C0976a;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareAction {
    private ShareContent f3215a = new ShareContent();
    private String f3216b = null;
    private SHARE_MEDIA f3217c = null;
    private UMShareListener f3218d = null;
    private ShareBoardlistener f3219e = null;
    private Activity f3220f;
    private List<SHARE_MEDIA> f3221g = null;
    private List<SnsPlatform> f3222h = new ArrayList();
    private List<ShareContent> f3223i = new ArrayList();
    private List<UMShareListener> f3224j = new ArrayList();
    private int f3225k = 80;
    private View f3226l = null;
    private ShareBoardlistener f3227m = new C1803b(this);
    private ShareBoardlistener f3228n = new C1807c(this);

    public ShareAction(Activity activity) {
        if (activity != null) {
            this.f3220f = (Activity) new WeakReference(activity).get();
        }
    }

    public void openBoard() {
    }

    public ShareContent getShareContent() {
        return this.f3215a;
    }

    public String getFrom() {
        return this.f3216b;
    }

    public SHARE_MEDIA getPlatform() {
        return this.f3217c;
    }

    public ShareAction setPlatform(SHARE_MEDIA share_media) {
        this.f3217c = share_media;
        return this;
    }

    public ShareAction setCallback(UMShareListener uMShareListener) {
        this.f3218d = uMShareListener;
        return this;
    }

    public ShareAction setShareboardclickCallback(ShareBoardlistener shareBoardlistener) {
        this.f3219e = shareBoardlistener;
        return this;
    }

    public ShareAction setShareContent(ShareContent shareContent) {
        this.f3215a = shareContent;
        return this;
    }

    public ShareAction setDisplayList(SHARE_MEDIA... share_mediaArr) {
        this.f3221g = Arrays.asList(share_mediaArr);
        this.f3222h.clear();
        for (SHARE_MEDIA toSnsPlatform : this.f3221g) {
            this.f3222h.add(toSnsPlatform.toSnsPlatform());
        }
        return this;
    }

    public ShareAction setListenerList(UMShareListener... uMShareListenerArr) {
        this.f3224j = Arrays.asList(uMShareListenerArr);
        return this;
    }

    public ShareAction setContentList(ShareContent... shareContentArr) {
        if (shareContentArr == null || Arrays.asList(shareContentArr).size() == 0) {
            ShareContent shareContent = new ShareContent();
            shareContent.mText = "友盟分享";
            this.f3223i.add(shareContent);
        } else {
            this.f3223i = Arrays.asList(shareContentArr);
        }
        return this;
    }

    public ShareAction addButton(String str, String str2, String str3, String str4) {
        this.f3222h.add(SHARE_MEDIA.createSnsPlatform(str, str2, str3, str4, 0));
        return this;
    }

    public ShareAction withText(String str) {
        this.f3215a.mText = str;
        return this;
    }

    public ShareAction withTitle(String str) {
        this.f3215a.mTitle = str;
        return this;
    }

    public ShareAction withTargetUrl(String str) {
        this.f3215a.mTargetUrl = str;
        return this;
    }

    public ShareAction withMedia(UMImage uMImage) {
        this.f3215a.mMedia = uMImage;
        return this;
    }

    public ShareAction withMedia(UMEmoji uMEmoji) {
        this.f3215a.mMedia = uMEmoji;
        return this;
    }

    public ShareAction withFollow(String str) {
        this.f3215a.mFollow = str;
        return this;
    }

    public ShareAction withExtra(UMImage uMImage) {
        this.f3215a.mExtra = uMImage;
        return this;
    }

    public ShareAction withMedia(UMusic uMusic) {
        this.f3215a.mMedia = uMusic;
        return this;
    }

    public ShareAction withMedia(UMVideo uMVideo) {
        this.f3215a.mMedia = uMVideo;
        return this;
    }

    public ShareAction withShareBoardDirection(View view, int i) {
        this.f3225k = i;
        this.f3226l = view;
        return this;
    }

    public void share() {
        UMShareAPI.get(this.f3220f).doShare(this.f3220f, this, this.f3218d);
    }

    public void open() {
        C0976a c0976a;
        if (this.f3222h.size() != 0) {
            Map hashMap = new HashMap();
            hashMap.put("listener", this.f3218d);
            hashMap.put("content", this.f3215a);
            c0976a = new C0976a(this.f3220f, this.f3222h);
            if (this.f3219e == null) {
                c0976a.m3231a(this.f3228n);
            } else {
                c0976a.m3231a(this.f3219e);
            }
            c0976a.setFocusable(true);
            c0976a.setBackgroundDrawable(new BitmapDrawable());
            if (this.f3226l == null) {
                this.f3226l = this.f3220f.getWindow().getDecorView();
            }
            c0976a.showAtLocation(this.f3226l, this.f3225k, 0, 0);
            return;
        }
        this.f3222h.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        this.f3222h.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        this.f3222h.add(SHARE_MEDIA.SINA.toSnsPlatform());
        this.f3222h.add(SHARE_MEDIA.QQ.toSnsPlatform());
        hashMap = new HashMap();
        hashMap.put("listener", this.f3218d);
        hashMap.put("content", this.f3215a);
        c0976a = new C0976a(this.f3220f, this.f3222h);
        if (this.f3223i.size() == 0) {
            if (this.f3219e == null) {
                c0976a.m3231a(this.f3227m);
            } else {
                c0976a.m3231a(this.f3219e);
            }
        } else if (this.f3219e == null) {
            c0976a.m3231a(this.f3228n);
        } else {
            c0976a.m3231a(this.f3219e);
        }
        c0976a.setFocusable(true);
        c0976a.setBackgroundDrawable(new BitmapDrawable());
        c0976a.showAtLocation(this.f3220f.getWindow().getDecorView(), 80, 0, 0);
    }
}
