package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.nearby.UploadInfo;
import com.zhuoyi.system.util.constant.SeparatorConstants;

/* compiled from: NearbyUpdateHandler */
public class C2054t extends C1972b<UploadInfo, Integer> {
    private Context f5568h;
    private UploadInfo f5569i;

    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public C2054t(Context context, UploadInfo uploadInfo) {
        super(context, uploadInfo);
        this.f5568h = context;
        this.f5569i = uploadInfo;
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=").append(as.m1215f(this.f5568h));
        stringBuffer.append("&userid=").append(this.f5569i.getUserID());
        LatLonPoint point = this.f5569i.getPoint();
        stringBuffer.append("&location=").append(((float) ((int) (point.getLongitude() * 1000000.0d))) / 1000000.0f).append(SeparatorConstants.SEPARATOR_ADS_ID).append(((float) ((int) (point.getLatitude() * 1000000.0d))) / 1000000.0f);
        stringBuffer.append("&coordtype=").append(this.f5569i.getCoordType());
        return stringBuffer.toString();
    }

    protected Integer mo3703d(String str) throws AMapException {
        return Integer.valueOf(0);
    }

    public String mo1759g() {
        return C0389h.m1587b() + "/nearby/data/create";
    }
}
