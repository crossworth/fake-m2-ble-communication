package com.umeng.socialize.media;

import com.umeng.socialize.ShareContent;

public class TwitterShareContent extends SimpleShareContent {
    public boolean isHasPic = false;

    public TwitterShareContent(ShareContent shareContent) {
        super(shareContent);
        if (shareContent.mMedia == null || !(shareContent.mMedia instanceof UMImage)) {
            this.isHasPic = false;
        } else {
            this.isHasPic = true;
        }
    }
}
