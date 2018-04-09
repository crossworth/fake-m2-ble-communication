package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

public class UMVideo extends BaseMediaObject {
    private UMImage f5478i;
    private String f5479j;
    private String f5480k;
    private String f5481l;
    private String f5482m;
    private int f5483n;

    public int getDuration() {
        return this.f5483n;
    }

    public void setDuration(int i) {
        this.f5483n = i;
    }

    public UMVideo(String str) {
        super(str);
    }

    public String getLowBandUrl() {
        return this.f5479j;
    }

    public String getLowBandDataUrl() {
        return this.f5480k;
    }

    public void setLowBandDataUrl(String str) {
        this.f5480k = str;
    }

    public String getHighBandDataUrl() {
        return this.f5481l;
    }

    public void setHighBandDataUrl(String str) {
        this.f5481l = str;
    }

    public String getH5Url() {
        return this.f5482m;
    }

    public void setH5Url(String str) {
        this.f5482m = str;
    }

    public void setLowBandUrl(String str) {
        this.f5479j = str;
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

    public void setThumb(UMImage uMImage) {
        this.f5478i = uMImage;
    }

    public byte[] toByte() {
        if (this.f5478i != null) {
            return this.f5478i.toByte();
        }
        return null;
    }

    public String toString() {
        return "UMVedio [media_url=" + this.a + ", qzone_title=" + this.b + ", qzone_thumb=" + this.c + "media_url=" + this.a + ", qzone_title=" + this.b + ", qzone_thumb=" + this.c + "]";
    }

    public boolean isMultiMedia() {
        return true;
    }

    public UMImage getThumbImage() {
        return this.f5478i;
    }
}
