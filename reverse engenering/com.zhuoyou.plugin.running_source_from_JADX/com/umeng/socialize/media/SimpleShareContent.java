package com.umeng.socialize.media;

import com.umeng.socialize.ShareContent;

public class SimpleShareContent {
    private UMImage f4876a;
    private String f4877b;
    private String f4878c;
    private String f4879d;
    private UMVideo f4880e;
    private UMusic f4881f;

    public SimpleShareContent(ShareContent shareContent) {
        this.f4877b = shareContent.mText;
        this.f4878c = shareContent.mTitle;
        this.f4879d = shareContent.mTargetUrl;
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMImage)) {
            this.f4876a = (UMImage) shareContent.mMedia;
        }
    }

    public void setTitle(String str) {
        this.f4878c = str;
    }

    public String getTitle() {
        return this.f4878c;
    }

    public void setText(String str) {
        this.f4877b = str;
    }

    public String getText() {
        return this.f4877b;
    }

    public void setImage(UMImage uMImage) {
        this.f4876a = uMImage;
    }

    public UMImage getImage() {
        return this.f4876a;
    }

    public void setTargeturl(String str) {
        this.f4879d = str;
    }

    public String getTargeturl() {
        return this.f4879d;
    }

    public void setMusic(UMusic uMusic) {
        this.f4881f = uMusic;
    }

    public UMusic getMusic() {
        return this.f4881f;
    }

    public void setVideo(UMVideo uMVideo) {
        this.f4880e = uMVideo;
    }

    public UMVideo getVideo() {
        return this.f4880e;
    }
}
