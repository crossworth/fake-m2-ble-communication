package com.umeng.socialize.media;

import android.os.Parcel;
import android.text.TextUtils;

public abstract class BaseMediaObject implements UMediaObject {
    protected String f4834a = "";
    protected String f4835b = "";
    protected String f4836c = "";
    protected String f4837d = "";
    protected String f4838e = "";
    protected String f4839f = "";
    protected int f4840g = 0;
    protected String f4841h = "";
    public String mText = null;

    public BaseMediaObject(String str) {
        this.f4834a = str;
    }

    public String getDescription() {
        return this.f4841h;
    }

    public void setDescription(String str) {
        this.f4841h = str;
    }

    public String toUrl() {
        return this.f4834a;
    }

    public void setMediaUrl(String str) {
        this.f4834a = str;
    }

    public boolean isUrlMedia() {
        if (TextUtils.isEmpty(this.f4834a)) {
            return false;
        }
        return true;
    }

    public String getTitle() {
        return this.f4835b;
    }

    public void setTitle(String str) {
        this.f4835b = str;
    }

    public String getThumb() {
        return this.f4836c;
    }

    public void setThumb(String str) {
        this.f4836c = str;
    }

    public void setTargetUrl(String str) {
        this.f4837d = str;
    }

    public String getTargetUrl() {
        return this.f4837d;
    }

    protected BaseMediaObject(Parcel parcel) {
        if (parcel != null) {
            this.f4834a = parcel.readString();
            this.f4835b = parcel.readString();
            this.f4836c = parcel.readString();
            this.f4837d = parcel.readString();
        }
    }

    public String toString() {
        return "BaseMediaObject [media_url=" + this.f4834a + ", qzone_title=" + this.f4835b + ", qzone_thumb=" + this.f4836c + "]";
    }
}
