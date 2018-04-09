package com.facebook.media;

import android.net.Uri;
import android.text.TextUtils;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent.Builder;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.media.SimpleShareContent;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.utils.Log;

public class FacebookShareContent extends SimpleShareContent {
    public static final int LINK = 3;
    public static final int PICTURE = 1;
    public static final int VIDEO = 2;
    private int Share_Type = 0;

    public FacebookShareContent(ShareContent shareContent) {
        super(shareContent);
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMVideo)) {
            setVideo((UMVideo) shareContent.mMedia);
        }
    }

    public int getShare_Type() {
        if (getImage() != null && getTargeturl() != null) {
            this.Share_Type = 1;
        } else if (getImage() != null) {
            this.Share_Type = 1;
        } else if (getVideo() != null) {
            this.Share_Type = 2;
        } else if (getTargeturl() != null) {
            this.Share_Type = 3;
        }
        return this.Share_Type;
    }

    public com.facebook.share.model.ShareContent getContent() {
        switch (getShare_Type()) {
            case 1:
                if (TextUtils.isEmpty(getText())) {
                    return new Builder().addPhoto(new SharePhoto.Builder().setBitmap(getImage().asBitmap()).build()).build();
                }
                ShareLinkContent.Builder builder = new ShareLinkContent.Builder();
                builder.setContentDescription(getText());
                if (!TextUtils.isEmpty(getTitle())) {
                    builder.setContentTitle(getTitle());
                }
                if (TextUtils.isEmpty(getTargeturl())) {
                    Log.m3260w("", "###请设置targetUrl");
                } else {
                    builder.setContentUrl(Uri.parse(getTargeturl()));
                }
                if (getImage() != null) {
                    builder.setImageUrl(Uri.parse(getImage().toUrl()));
                }
                return builder.build();
            case 2:
                return new ShareVideoContent.Builder().setVideo(new ShareVideo.Builder().setLocalUrl(Uri.parse(getVideo().toString())).build()).build();
            case 3:
                return ((ShareLinkContent.Builder) new ShareLinkContent.Builder().setContentUrl(Uri.parse(getTargeturl()))).build();
            default:
                return null;
        }
    }
}
