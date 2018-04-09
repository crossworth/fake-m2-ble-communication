package com.umeng.socialize;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/* compiled from: ShareAction */
class C1807c implements ShareBoardlistener {
    final /* synthetic */ ShareAction f4821a;

    C1807c(ShareAction shareAction) {
        this.f4821a = shareAction;
    }

    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
        int indexOf = this.f4821a.f3221g.indexOf(share_media);
        int size = this.f4821a.f3223i.size();
        if (size != 0) {
            ShareContent shareContent;
            if (indexOf < size) {
                shareContent = (ShareContent) this.f4821a.f3223i.get(indexOf);
            } else {
                shareContent = (ShareContent) this.f4821a.f3223i.get(size - 1);
            }
            this.f4821a.f3215a = shareContent;
        }
        size = this.f4821a.f3224j.size();
        if (size != 0) {
            if (indexOf < size) {
                this.f4821a.f3218d = (UMShareListener) this.f4821a.f3224j.get(indexOf);
            } else {
                this.f4821a.f3218d = (UMShareListener) this.f4821a.f3224j.get(size - 1);
            }
        }
        this.f4821a.setPlatform(share_media);
        this.f4821a.share();
    }
}
