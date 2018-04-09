package com.umeng.socialize.media;

import android.os.Parcel;
import android.text.TextUtils;

public abstract class BaseMediaObject implements UMediaObject {
    protected String f4935a = "";
    protected String f4936b = "";
    protected String f4937c = "";
    protected UMImage f4938d;
    public String mText = null;

    public void setThumb(UMImage uMImage) {
        this.f4938d = uMImage;
    }

    public BaseMediaObject(String str) {
        this.f4935a = str;
    }

    public String getDescription() {
        return this.f4937c;
    }

    public void setDescription(String str) {
        this.f4937c = str;
    }

    public String toUrl() {
        return this.f4935a;
    }

    public UMImage getThumbImage() {
        return this.f4938d;
    }

    public boolean isUrlMedia() {
        if (TextUtils.isEmpty(this.f4935a)) {
            return false;
        }
        return true;
    }

    public String getTitle() {
        return this.f4936b;
    }

    public void setTitle(String str) {
        this.f4936b = str;
    }

    protected BaseMediaObject(Parcel parcel) {
        if (parcel != null) {
            this.f4935a = parcel.readString();
            this.f4936b = parcel.readString();
        }
    }

    public String toString() {
        return "BaseMediaObject [media_url=" + this.f4935a + ", qzone_title=" + this.f4936b + ", qzone_thumb=" + "]";
    }
}
