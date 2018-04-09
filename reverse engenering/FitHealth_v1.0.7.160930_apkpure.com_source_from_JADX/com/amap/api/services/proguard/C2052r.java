package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;

/* compiled from: NearbyDeleteHandler */
public class C2052r extends C1972b<String, Integer> {
    private Context f5564h;
    private String f5565i;

    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public C2052r(Context context, String str) {
        super(context, str);
        this.f5564h = context;
        this.f5565i = str;
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=").append(as.m1215f(this.f5564h));
        stringBuffer.append("&userid=").append(this.f5565i);
        return stringBuffer.toString();
    }

    protected Integer mo3703d(String str) throws AMapException {
        return Integer.valueOf(0);
    }

    public String mo1759g() {
        return C0389h.m1587b() + "/nearby/data/delete";
    }
}
