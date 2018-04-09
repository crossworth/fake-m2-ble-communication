package com.umeng.socialize.media;

import com.umeng.socialize.ShareContent;

public class SimpleShareContent {
    private UMImage f3319a;
    private String f3320b;
    private String f3321c;
    private String f3322d;
    private UMVideo f3323e;
    private UMusic f3324f;

    public SimpleShareContent(ShareContent shareContent) {
        this.f3320b = shareContent.mText;
        this.f3321c = shareContent.mTitle;
        this.f3322d = shareContent.mTargetUrl;
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMImage)) {
            this.f3319a = (UMImage) shareContent.mMedia;
        }
    }

    public void setTitle(String str) {
        this.f3321c = str;
    }

    public String getTitle() {
        return this.f3321c;
    }

    public void setText(String str) {
        this.f3320b = str;
    }

    public String getText() {
        return this.f3320b;
    }

    public void setImage(UMImage uMImage) {
        this.f3319a = uMImage;
    }

    public UMImage getImage() {
        return this.f3319a;
    }

    public void setTargeturl(String str) {
        this.f3322d = str;
    }

    public String getTargeturl() {
        return this.f3322d;
    }

    public void setMusic(UMusic uMusic) {
        this.f3324f = uMusic;
    }

    public UMusic getMusic() {
        return this.f3324f;
    }

    public void setVideo(UMVideo uMVideo) {
        this.f3323e = uMVideo;
    }

    public UMVideo getVideo() {
        return this.f3323e;
    }
}
