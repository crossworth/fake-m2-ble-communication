package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject.MediaType;
import java.util.Map;

public class UMWebPage extends BaseMediaObject {
    private String f5484i = "";
    private UMImage f5485j = null;

    public UMWebPage(String str) {
        super(str);
        this.d = str;
    }

    public void setTargetUrl(String str) {
        super.setTargetUrl(str);
        this.a = str;
    }

    public String getDescription() {
        return this.f5484i;
    }

    public void setDescription(String str) {
        this.f5484i = str;
    }

    public void setThumb(UMImage uMImage) {
        this.f5485j = uMImage;
    }

    public UMImage getThumbImage() {
        return this.f5485j;
    }

    public MediaType getMediaType() {
        return MediaType.WEBPAGE;
    }

    public Map<String, Object> toUrlExtraParams() {
        return null;
    }

    public byte[] toByte() {
        return null;
    }

    public boolean isMultiMedia() {
        return true;
    }

    public String toString() {
        return "UMWebPage [mDescription=" + this.f5484i + ", mMediaTitle=" + this.b + ", mMediaThumb=" + this.c + ", mMediaTargetUrl=" + this.d + ", mLength=" + this.g + "]";
    }
}
