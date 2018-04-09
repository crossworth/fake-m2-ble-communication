package com.umeng.socialize.media;

import android.os.Parcel;
import android.text.TextUtils;
import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

public class UMusic extends BaseMediaObject {
    private String f5486i = "未知";
    private String f5487j = "未知";
    private UMImage f5488k;
    private String f5489l;
    private String f5490m;
    private String f5491n;
    private String f5492o;
    private int f5493p;

    public int getDuration() {
        return this.f5493p;
    }

    public void setDuration(int i) {
        this.f5493p = i;
    }

    public String getLowBandUrl() {
        return this.f5492o;
    }

    public void setLowBandUrl(String str) {
        this.f5492o = str;
    }

    public UMusic(String str) {
        super(str);
    }

    public String getHighBandDataUrl() {
        return this.f5490m;
    }

    public void setHighBandDataUrl(String str) {
        this.f5490m = str;
    }

    public String getH5Url() {
        return this.f5491n;
    }

    public void setH5Url(String str) {
        this.f5491n = str;
    }

    public MediaType getMediaType() {
        return MediaType.MUSIC;
    }

    protected UMusic(Parcel parcel) {
        super(parcel);
        this.f5486i = parcel.readString();
        this.f5487j = parcel.readString();
    }

    public final Map<String, Object> toUrlExtraParams() {
        Map<String, Object> hashMap = new HashMap();
        if (isUrlMedia()) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.a);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, this.f5486i);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_AUTHOR, this.f5487j);
        }
        return hashMap;
    }

    public void setTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.f5486i = str;
        }
    }

    public void setAuthor(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.f5487j = str;
        }
    }

    public String getTitle() {
        return this.f5486i;
    }

    public String getAuthor() {
        return this.f5487j;
    }

    public void setThumb(UMImage uMImage) {
        this.f5488k = uMImage;
    }

    public byte[] toByte() {
        if (this.f5488k != null) {
            return this.f5488k.toByte();
        }
        return null;
    }

    public String toString() {
        return "UMusic [title=" + this.f5486i + ", author=" + this.f5487j + "media_url=" + this.a + ", qzone_title=" + this.b + ", qzone_thumb=" + this.c + "]";
    }

    public boolean isMultiMedia() {
        return true;
    }

    public UMImage getThumbImage() {
        return this.f5488k;
    }

    public String getLowBandDataUrl() {
        return this.f5489l;
    }

    public void setLowBandDataUrl(String str) {
        this.f5489l = str;
    }
}
