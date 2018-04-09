package com.umeng.socialize.media;

import android.os.Parcel;
import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

public class UMusic extends BaseMediaObject {
    private String f4955e;
    private String f4956f;
    private String f4957g;
    private String f4958h;
    private int f4959i;

    public int getDuration() {
        return this.f4959i;
    }

    public void setDuration(int i) {
        this.f4959i = i;
    }

    public String getLowBandUrl() {
        return this.f4958h;
    }

    public void setLowBandUrl(String str) {
        this.f4958h = str;
    }

    public UMusic(String str) {
        super(str);
    }

    public String getHighBandDataUrl() {
        return this.f4956f;
    }

    public void setHighBandDataUrl(String str) {
        this.f4956f = str;
    }

    public String getH5Url() {
        return this.f4957g;
    }

    public void setH5Url(String str) {
        this.f4957g = str;
    }

    public MediaType getMediaType() {
        return MediaType.MUSIC;
    }

    protected UMusic(Parcel parcel) {
        super(parcel);
    }

    public final Map<String, Object> toUrlExtraParams() {
        Map<String, Object> hashMap = new HashMap();
        if (isUrlMedia()) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.a);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, this.b);
        }
        return hashMap;
    }

    public byte[] toByte() {
        if (this.d != null) {
            return this.d.toByte();
        }
        return null;
    }

    public String toString() {
        return "UMusic [title=" + this.b + "media_url=" + this.a + ", qzone_title=" + this.b + ", qzone_thumb=" + "]";
    }

    public UMImage getThumbImage() {
        return this.d;
    }

    public String getLowBandDataUrl() {
        return this.f4955e;
    }

    public void setLowBandDataUrl(String str) {
        this.f4955e = str;
    }
}
