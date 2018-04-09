package com.umeng.socialize.media;

import android.net.Uri;
import android.text.TextUtils;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhoto.Builder;
import com.facebook.share.model.SharePhotoContent;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.utils.Log;

public class FaceBookShareContent extends SimpleShareContent {
    private String mCaption = "";
    private String mDescription = "";
    private ShareContent mShareContent = null;
    private UMediaObject mShareMedia = null;
    private String mTargetUrl = "";
    private String mTitle = "";

    public FaceBookShareContent(ShareContent shareContent) {
        super(shareContent);
        this.mShareContent = shareContent;
        this.mShareMedia = shareContent.mMedia;
    }

    private void buildShareContent() {
        if (TextUtils.isEmpty(this.mShareContent.mText) && this.mShareMedia != null && (this.mShareMedia instanceof UMImage)) {
            buildImageShare();
        } else if (!TextUtils.isEmpty(this.mShareContent.mText)) {
            buildTextImageShare();
        } else if (this.mShareMedia != null && (this.mShareMedia instanceof UMusic)) {
            Log.m3251e("", "FB 目前不支持音乐分享");
        } else if (this.mShareMedia == null || !(this.mShareMedia instanceof UMVideo)) {
            Log.m3248d("", "### FB 目前仅仅支持图片、文本、图文分享...");
        } else {
            Log.m3251e("", "FB 目前不支持视频分享");
        }
    }

    private SharePhotoContent buildImageShare() {
        SharePhoto sharePhoto;
        UMImage image = this.mShareMedia;
        if (image.isUrlMedia()) {
            sharePhoto = new Builder().setBitmap(image.asBitmap()).build();
        } else {
            sharePhoto = new Builder().setImageUrl(Uri.fromFile(image.asFileImage())).build();
        }
        return new SharePhotoContent.Builder().addPhoto(sharePhoto).build();
    }

    private ShareLinkContent buildTextImageShare() {
        ShareLinkContent.Builder builder = new ShareLinkContent.Builder();
        builder.setContentDescription(this.mShareContent.mText);
        if (TextUtils.isEmpty(this.mTitle)) {
            builder.setContentTitle(this.mTitle);
        }
        if (TextUtils.isEmpty(this.mTargetUrl)) {
            Log.m3260w("", "###请设置targetUrl");
        } else {
            builder.setContentUrl(Uri.parse(this.mTargetUrl));
        }
        if (this.mShareMedia != null) {
            Uri imageUri;
            UMImage image = this.mShareMedia;
            if (image.isUrlMedia()) {
                imageUri = Uri.parse(image.toUrl());
            } else {
                imageUri = Uri.fromFile(image.asFileImage());
            }
            builder.setImageUrl(imageUri);
        }
        return builder.build();
    }
}
