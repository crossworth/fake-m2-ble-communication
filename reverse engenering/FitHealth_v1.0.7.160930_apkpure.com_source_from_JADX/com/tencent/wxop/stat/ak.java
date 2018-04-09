package com.tencent.wxop.stat;

import android.content.Context;
import com.facebook.internal.ServerProtocol;
import com.tencent.p004a.p005a.p006a.p007a.C0668g;
import com.tencent.p004a.p005a.p006a.p007a.C0669h;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.p022a.C0873d;
import com.tencent.wxop.stat.p023b.C0877b;
import com.tencent.wxop.stat.p023b.C0881f;
import com.tencent.wxop.stat.p023b.C0882g;
import com.tencent.wxop.stat.p023b.C0885l;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

class ak {
    private static C0877b cx = C0885l.av();
    private static ak dj = null;
    private static Context dk = null;
    private long cv = 0;
    DefaultHttpClient dg = null;
    C0881f dh = null;
    StringBuilder di = new StringBuilder(4096);

    private ak(Context context) {
        try {
            dk = context.getApplicationContext();
            this.cv = System.currentTimeMillis() / 1000;
            this.dh = new C0881f();
            if (C0894c.m2949k()) {
                try {
                    Logger.getLogger("org.apache.http.wire").setLevel(Level.FINER);
                    Logger.getLogger("org.apache.http.headers").setLevel(Level.FINER);
                    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
                    System.setProperty("org.apache.commons.logging.simplelog.showdatetime", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                    System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
                    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
                    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");
                } catch (Throwable th) {
                }
            }
            HttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, false);
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 10000);
            this.dg = new DefaultHttpClient(basicHttpParams);
            this.dg.setKeepAliveStrategy(new al(this));
        } catch (Throwable th2) {
            cx.m2852b(th2);
        }
    }

    static ak m2844Z(Context context) {
        if (dj == null) {
            synchronized (ak.class) {
                if (dj == null) {
                    dj = new ak(context);
                }
            }
        }
        return dj;
    }

    static Context aB() {
        return dk;
    }

    static void m2845j(Context context) {
        dk = context.getApplicationContext();
    }

    final void m2846a(C0873d c0873d, aj ajVar) {
        m2848b(Arrays.asList(new String[]{c0873d.af()}), ajVar);
    }

    final void m2847a(List<?> list, aj ajVar) {
        int i = 0;
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            list.get(0);
            Throwable th;
            try {
                int i2;
                String str;
                this.di.delete(0, this.di.length());
                this.di.append("[");
                String str2 = "rc4";
                for (i2 = 0; i2 < size; i2++) {
                    this.di.append(list.get(i2).toString());
                    if (i2 != size - 1) {
                        this.di.append(SeparatorConstants.SEPARATOR_ADS_ID);
                    }
                }
                this.di.append("]");
                String stringBuilder = this.di.toString();
                size = stringBuilder.length();
                String str3 = C0894c.m2967y() + "/?index=" + this.cv;
                this.cv++;
                if (C0894c.m2949k()) {
                    cx.m2851b("[" + str3 + "]Send request(" + size + "bytes), content:" + stringBuilder);
                }
                HttpPost httpPost = new HttpPost(str3);
                httpPost.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
                httpPost.setHeader("Connection", HTTP.CONN_KEEP_ALIVE);
                httpPost.removeHeaders(HttpHeaders.CACHE_CONTROL);
                HttpHost V = C0898g.m3012r(dk).m3015V();
                httpPost.addHeader("Content-Encoding", str2);
                if (V == null) {
                    this.dg.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
                } else {
                    if (C0894c.m2949k()) {
                        cx.m2855e("proxy:" + V.toHostString());
                    }
                    httpPost.addHeader("X-Content-Encoding", str2);
                    this.dg.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, V);
                    httpPost.addHeader("X-Online-Host", C0894c.al);
                    httpPost.addHeader(HttpHeaders.ACCEPT, "*/*");
                    httpPost.addHeader("Content-Type", "json");
                }
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream(size);
                byte[] bytes = stringBuilder.getBytes("UTF-8");
                int length = bytes.length;
                if (size > C0894c.aA) {
                    i = 1;
                }
                if (i != 0) {
                    httpPost.removeHeaders("Content-Encoding");
                    str = str2 + ",gzip";
                    httpPost.addHeader("Content-Encoding", str);
                    if (V != null) {
                        httpPost.removeHeaders("X-Content-Encoding");
                        httpPost.addHeader("X-Content-Encoding", str);
                    }
                    byteArrayOutputStream.write(new byte[4]);
                    GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    gZIPOutputStream.write(bytes);
                    gZIPOutputStream.close();
                    bytes = byteArrayOutputStream.toByteArray();
                    ByteBuffer.wrap(bytes, 0, 4).putInt(length);
                    if (C0894c.m2949k()) {
                        cx.m2855e("before Gzip:" + length + " bytes, after Gzip:" + bytes.length + " bytes");
                    }
                }
                httpPost.setEntity(new ByteArrayEntity(C0882g.m2863b(bytes)));
                HttpResponse execute = this.dg.execute(httpPost);
                HttpEntity entity = execute.getEntity();
                size = execute.getStatusLine().getStatusCode();
                long contentLength = entity.getContentLength();
                if (C0894c.m2949k()) {
                    cx.m2851b("http recv response status code:" + size + ", content length:" + contentLength);
                }
                if (contentLength <= 0) {
                    cx.m2854d("Server response no data.");
                    if (ajVar != null) {
                        ajVar.mo2148B();
                    }
                    EntityUtils.toString(entity);
                    return;
                }
                if (contentLength > 0) {
                    InputStream content = entity.getContent();
                    DataInputStream dataInputStream = new DataInputStream(content);
                    bytes = new byte[((int) entity.getContentLength())];
                    dataInputStream.readFully(bytes);
                    content.close();
                    dataInputStream.close();
                    Header firstHeader = execute.getFirstHeader("Content-Encoding");
                    if (firstHeader != null) {
                        if (firstHeader.getValue().equalsIgnoreCase("gzip,rc4")) {
                            bytes = C0882g.m2864c(C0885l.m2890b(bytes));
                        } else if (firstHeader.getValue().equalsIgnoreCase("rc4,gzip")) {
                            bytes = C0885l.m2890b(C0882g.m2864c(bytes));
                        } else if (firstHeader.getValue().equalsIgnoreCase("gzip")) {
                            bytes = C0885l.m2890b(bytes);
                        } else if (firstHeader.getValue().equalsIgnoreCase("rc4")) {
                            bytes = C0882g.m2864c(bytes);
                        }
                    }
                    str = new String(bytes, "UTF-8");
                    if (C0894c.m2949k()) {
                        cx.m2851b("http get response data:" + str);
                    }
                    JSONObject jSONObject = new JSONObject(str);
                    if (size == 200) {
                        try {
                            stringBuilder = jSONObject.optString(DeviceInfo.TAG_MID);
                            if (C0669h.m2239e(stringBuilder)) {
                                if (C0894c.m2949k()) {
                                    cx.m2851b("update mid:" + stringBuilder);
                                }
                                C0668g.m2230a(dk).m2231b(stringBuilder);
                            }
                            if (!jSONObject.isNull("cfg")) {
                                C0894c.m2935a(dk, jSONObject.getJSONObject("cfg"));
                            }
                            if (!jSONObject.isNull("ncts")) {
                                i2 = jSONObject.getInt("ncts");
                                i = (int) (((long) i2) - (System.currentTimeMillis() / 1000));
                                if (C0894c.m2949k()) {
                                    cx.m2851b("server time:" + i2 + ", diff time:" + i);
                                }
                                C0885l.m2884Q(dk);
                                C0885l.m2888a(dk, i);
                            }
                        } catch (Throwable th2) {
                            cx.m2853c(th2);
                        }
                        if (ajVar != null) {
                            if (jSONObject.optInt("ret") == 0) {
                                ajVar.ah();
                            } else {
                                cx.error("response error data.");
                                ajVar.mo2148B();
                            }
                        }
                    } else {
                        cx.error("Server response error code:" + size + ", error:" + new String(bytes, "UTF-8"));
                        if (ajVar != null) {
                            ajVar.mo2148B();
                        }
                    }
                    content.close();
                } else {
                    EntityUtils.toString(entity);
                }
                byteArrayOutputStream.close();
                th2 = null;
                if (th2 != null) {
                    cx.m2850a(th2);
                    if (ajVar != null) {
                        try {
                            ajVar.mo2148B();
                        } catch (Throwable th3) {
                            cx.m2852b(th3);
                        }
                    }
                    if (th2 instanceof OutOfMemoryError) {
                        System.gc();
                        this.di = null;
                        this.di = new StringBuilder(2048);
                    }
                    C0898g.m3012r(dk).m3014I();
                }
            } catch (Throwable th4) {
            }
        }
    }

    final void m2848b(List<?> list, aj ajVar) {
        if (this.dh != null) {
            this.dh.m2861a(new am(this, list, ajVar));
        }
    }
}
