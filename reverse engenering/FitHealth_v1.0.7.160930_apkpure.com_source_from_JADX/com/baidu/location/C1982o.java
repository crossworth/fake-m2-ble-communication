package com.baidu.location;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.util.Log;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpProtocolParams;

abstract class C1982o implements an, C1619j {
    private static String cJ = "10.0.0.172";
    private static int cK = 4;
    private static int cQ = 80;
    public String cL = null;
    public HttpEntity cM = null;
    private boolean cN = false;
    public int cO = 3;
    public List cP = null;

    class C05311 extends Thread {
        final /* synthetic */ C1982o f2278a;

        C05311(C1982o c1982o) {
            this.f2278a = c1982o;
        }

        public void run() {
            this.f2278a.mo3704V();
            int i = this.f2278a.cO;
            this.f2278a.m6023P();
            int i2 = i;
            HttpEntity httpEntity = null;
            while (i2 > 0) {
                HttpPost httpPost;
                Object obj;
                try {
                    httpPost = new HttpPost(this.f2278a.cL);
                    try {
                        httpEntity = new UrlEncodedFormEntity(this.f2278a.cP, "utf-8");
                        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                        httpPost.setHeader(HttpHeaders.ACCEPT_CHARSET, "UTF-8;");
                        httpPost.setEntity(httpEntity);
                        HttpClient defaultHttpClient = new DefaultHttpClient();
                        defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.valueOf(an.f2195I));
                        defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Integer.valueOf(an.f2195I));
                        HttpProtocolParams.setUseExpectContinue(defaultHttpClient.getParams(), false);
                        if ((C1982o.cK == 1 || C1982o.cK == 4) && (this.f2278a.cO - i2) % 2 == 0) {
                            defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(C1982o.cJ, C1982o.cQ, HttpHost.DEFAULT_SCHEME_NAME));
                        }
                        HttpResponse execute = defaultHttpClient.execute(httpPost);
                        if (execute.getStatusLine().getStatusCode() == 200) {
                            this.f2278a.cM = execute.getEntity();
                            this.f2278a.mo3705if(true);
                            break;
                        }
                        httpPost.abort();
                        i2--;
                        obj = httpPost;
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                    Object obj2 = httpEntity;
                    httpPost.abort();
                    Log.d(an.f2222l, "NetworkCommunicationException!");
                    i2--;
                    obj = httpPost;
                }
            }
            if (i2 <= 0) {
                this.f2278a.cM = null;
                this.f2278a.mo3705if(false);
            }
            this.f2278a.cN = false;
        }
    }

    C1982o() {
    }

    private void m6023P() {
        cK = m6026T();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int m6026T() {
        /*
        r10 = this;
        r7 = 1;
        r6 = 4;
        r1 = 0;
        r9 = com.baidu.location.C1976f.getServiceContext();
        r0 = "connectivity";
        r0 = r9.getSystemService(r0);	 Catch:{ SecurityException -> 0x00be, Exception -> 0x00ca }
        r0 = (android.net.ConnectivityManager) r0;	 Catch:{ SecurityException -> 0x00be, Exception -> 0x00ca }
        if (r0 != 0) goto L_0x0013;
    L_0x0011:
        r0 = r6;
    L_0x0012:
        return r0;
    L_0x0013:
        r8 = r0.getActiveNetworkInfo();	 Catch:{ SecurityException -> 0x00be, Exception -> 0x00ca }
        if (r8 == 0) goto L_0x001f;
    L_0x0019:
        r0 = r8.isAvailable();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r0 != 0) goto L_0x0021;
    L_0x001f:
        r0 = r6;
        goto L_0x0012;
    L_0x0021:
        r0 = r8.getType();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r0 != r7) goto L_0x0029;
    L_0x0027:
        r0 = 3;
        goto L_0x0012;
    L_0x0029:
        r0 = "content://telephony/carriers/preferapn";
        r1 = android.net.Uri.parse(r0);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        r0 = r9.getContentResolver();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r1 == 0) goto L_0x00b6;
    L_0x003d:
        r0 = r1.moveToFirst();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r0 == 0) goto L_0x00b6;
    L_0x0043:
        r0 = "apn";
        r0 = r1.getColumnIndex(r0);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        r0 = r1.getString(r0);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r0 == 0) goto L_0x0081;
    L_0x004f:
        r2 = r0.toLowerCase();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        r3 = "ctwap";
        r2 = r2.contains(r3);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r2 == 0) goto L_0x0081;
    L_0x005b:
        r0 = android.net.Proxy.getDefaultHost();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r0 == 0) goto L_0x007e;
    L_0x0061:
        r2 = "";
        r2 = r0.equals(r2);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r2 != 0) goto L_0x007e;
    L_0x0069:
        r2 = "null";
        r2 = r0.equals(r2);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r2 != 0) goto L_0x007e;
    L_0x0071:
        cJ = r0;	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        r0 = 80;
        cQ = r0;	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r1 == 0) goto L_0x007c;
    L_0x0079:
        r1.close();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
    L_0x007c:
        r0 = r7;
        goto L_0x0012;
    L_0x007e:
        r0 = "10.0.0.200";
        goto L_0x0071;
    L_0x0081:
        if (r0 == 0) goto L_0x00b6;
    L_0x0083:
        r0 = r0.toLowerCase();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        r2 = "wap";
        r0 = r0.contains(r2);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r0 == 0) goto L_0x00b6;
    L_0x008f:
        r0 = android.net.Proxy.getDefaultHost();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r0 == 0) goto L_0x00b3;
    L_0x0095:
        r2 = "";
        r2 = r0.equals(r2);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r2 != 0) goto L_0x00b3;
    L_0x009d:
        r2 = "null";
        r2 = r0.equals(r2);	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r2 != 0) goto L_0x00b3;
    L_0x00a5:
        cJ = r0;	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        r0 = 80;
        cQ = r0;	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
        if (r1 == 0) goto L_0x00b0;
    L_0x00ad:
        r1.close();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
    L_0x00b0:
        r0 = r7;
        goto L_0x0012;
    L_0x00b3:
        r0 = "10.0.0.172";
        goto L_0x00a5;
    L_0x00b6:
        if (r1 == 0) goto L_0x00bb;
    L_0x00b8:
        r1.close();	 Catch:{ SecurityException -> 0x00ce, Exception -> 0x00ca }
    L_0x00bb:
        r0 = 2;
        goto L_0x0012;
    L_0x00be:
        r0 = move-exception;
        r0 = r1;
    L_0x00c0:
        r0 = com.baidu.location.C1982o.m6028if(r9, r0);	 Catch:{ Exception -> 0x00c6 }
        goto L_0x0012;
    L_0x00c6:
        r0 = move-exception;
        r0 = r6;
        goto L_0x0012;
    L_0x00ca:
        r0 = move-exception;
        r0 = r6;
        goto L_0x0012;
    L_0x00ce:
        r0 = move-exception;
        r0 = r8;
        goto L_0x00c0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.o.T():int");
    }

    private static int m6028if(Context context, NetworkInfo networkInfo) {
        String toLowerCase;
        if (!(networkInfo == null || networkInfo.getExtraInfo() == null)) {
            toLowerCase = networkInfo.getExtraInfo().toLowerCase();
            if (toLowerCase != null) {
                String defaultHost;
                if (toLowerCase.startsWith("cmwap") || toLowerCase.startsWith("uniwap") || toLowerCase.startsWith("3gwap")) {
                    defaultHost = Proxy.getDefaultHost();
                    if (defaultHost == null || defaultHost.equals("") || defaultHost.equals("null")) {
                        defaultHost = "10.0.0.172";
                    }
                    cJ = defaultHost;
                    return 1;
                } else if (toLowerCase.startsWith("ctwap")) {
                    defaultHost = Proxy.getDefaultHost();
                    if (defaultHost == null || defaultHost.equals("") || defaultHost.equals("null")) {
                        defaultHost = "10.0.0.200";
                    }
                    cJ = defaultHost;
                    return 1;
                } else if (toLowerCase.startsWith("cmnet") || toLowerCase.startsWith("uninet") || toLowerCase.startsWith("ctnet") || toLowerCase.startsWith("3gnet")) {
                    return 2;
                }
            }
        }
        toLowerCase = Proxy.getDefaultHost();
        if (toLowerCase == null || toLowerCase.length() <= 0) {
            return 2;
        }
        if ("10.0.0.172".equals(toLowerCase.trim())) {
            cJ = "10.0.0.172";
            return 1;
        } else if (!"10.0.0.200".equals(toLowerCase.trim())) {
            return 2;
        } else {
            cJ = "10.0.0.200";
            return 1;
        }
    }

    public static boolean m6030if(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            return connectivityManager.getActiveNetworkInfo() != null ? connectivityManager.getActiveNetworkInfo().isAvailable() : false;
        } catch (Exception e) {
            return false;
        }
    }

    public void m6032R() {
        new C05311(this).start();
    }

    abstract void mo3704V();

    abstract void mo3705if(boolean z);
}
