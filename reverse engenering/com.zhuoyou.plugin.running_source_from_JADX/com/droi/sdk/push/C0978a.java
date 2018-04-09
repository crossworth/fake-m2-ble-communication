package com.droi.sdk.push;

import android.content.Context;
import android.text.TextUtils;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.push.utils.GetDeviceIdCallback;

final class C0978a implements DroiCallback {
    final /* synthetic */ Context f3224a;
    final /* synthetic */ GetDeviceIdCallback f3225b;

    C0978a(Context context, GetDeviceIdCallback getDeviceIdCallback) {
        this.f3224a = context;
        this.f3225b = getDeviceIdCallback;
    }

    public void m2956a(String str, DroiError droiError) {
        if (droiError != null && droiError.isOk()) {
            if (!TextUtils.isEmpty(str)) {
                DroiPush.f3178b = str.replace("-", "");
            }
            String packageName = this.f3224a.getPackageName();
            if (packageName != null && packageName.equals(C1015j.m3171f(this.f3224a))) {
                ad.m3002d(this.f3224a);
            }
        } else if (droiError != null) {
            C1012g.m3140b("Get deviceId failed, error: " + droiError.toString());
        } else {
            C1012g.m3140b("Get deviceId failed, error: droiError is null");
        }
        if (this.f3225b != null) {
            this.f3225b.onGetDeviceId(DroiPush.f3178b);
        }
    }

    public /* synthetic */ void result(Object obj, DroiError droiError) {
        m2956a((String) obj, droiError);
    }
}
