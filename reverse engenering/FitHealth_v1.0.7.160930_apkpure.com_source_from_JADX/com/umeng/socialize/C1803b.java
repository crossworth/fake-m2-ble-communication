package com.umeng.socialize;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/* compiled from: ShareAction */
class C1803b implements ShareBoardlistener {
    final /* synthetic */ ShareAction f4820a;

    C1803b(ShareAction shareAction) {
        this.f4820a = shareAction;
    }

    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
        this.f4820a.setPlatform(share_media);
        this.f4820a.share();
    }
}
