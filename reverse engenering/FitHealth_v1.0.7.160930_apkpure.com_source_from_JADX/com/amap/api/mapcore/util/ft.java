package com.amap.api.mapcore.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* compiled from: HttpUrlUtil */
class ft implements HostnameVerifier {
    final /* synthetic */ fs f665a;

    ft(fs fsVar) {
        this.f665a = fsVar;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify("*.amap.com", sSLSession);
    }
}
