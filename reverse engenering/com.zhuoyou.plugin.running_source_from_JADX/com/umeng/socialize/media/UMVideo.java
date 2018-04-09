package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

public class UMVideo extends BaseMediaObject {
    private String f4950e;
    private String f4951f;
    private String f4952g;
    private String f4953h;
    private int f4954i;

    public int getDuration() {
        return this.f4954i;
    }

    public void setDuration(int i) {
        this.f4954i = i;
    }

    public UMVideo(String str) {
        super(str);
    }

    public String getLowBandUrl() {
        return this.f4950e;
    }

    public String getLowBandDataUrl() {
        return this.f4951f;
    }

    public void setLowBandDataUrl(String str) {
        this.f4951f = str;
    }

    public String getHighBandDataUrl() {
        return this.f4952g;
    }

    public void setHighBandDataUrl(String str) {
        this.f4952g = str;
    }

    public String getH5Url() {
        return this.f4953h;
    }

    public void setH5Url(String str) {
        this.f4953h = str;
    }

    public void setLowBandUrl(String str) {
        this.f4950e = str;
    }

    public MediaType getMediaType() {
        return MediaType.VEDIO;
    }

    public final Map<String, Object> toUrlExtraParams() {
        Map<String, Object> hashMap = new HashMap();
        if (isUrlMedia()) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.a);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
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
        return "UMVedio [media_url=" + this.a + ", qzone_title=" + this.b + ", qzone_thumb=" + "media_url=" + this.a + ", qzone_title=" + this.b + ", qzone_thumb=" + "]";
    }
}
