package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.p021a.p022a.p023a.p024a.C1146g;
import com.tencent.p021a.p022a.p023a.p024a.C1147h;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.common.C1436e;
import com.tencent.wxop.stat.common.C1437f;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.StatLogger;
import com.tencent.wxop.stat.p040a.C1416e;
import com.tyd.aidlservice.internal.Constants;
import com.umeng.facebook.internal.ServerProtocol;
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
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

class C1454i {
    private static StatLogger f4821d = C1442k.m4416b();
    private static C1454i f4822e = null;
    private static Context f4823f = null;
    DefaultHttpClient f4824a = null;
    C1436e f4825b = null;
    StringBuilder f4826c = new StringBuilder(4096);
    private long f4827g = 0;

    private C1454i(Context context) {
        try {
            f4823f = context.getApplicationContext();
            this.f4827g = System.currentTimeMillis() / 1000;
            this.f4825b = new C1436e();
            if (StatConfig.isDebugEnable()) {
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
            this.f4824a = new DefaultHttpClient(basicHttpParams);
            this.f4824a.setKeepAliveStrategy(new C1455j(this));
        } catch (Throwable th2) {
            f4821d.m4375e(th2);
        }
    }

    static Context m4483a() {
        return f4823f;
    }

    static void m4484a(Context context) {
        f4823f = context.getApplicationContext();
    }

    private void m4485a(JSONObject jSONObject) {
        try {
            String optString = jSONObject.optString(DeviceInfo.TAG_MID);
            if (C1147h.m3341c(optString)) {
                if (StatConfig.isDebugEnable()) {
                    f4821d.m4376i("update mid:" + optString);
                }
                C1146g.m3331E(f4823f).m3333a(optString);
            }
            if (!jSONObject.isNull("cfg")) {
                StatConfig.m4227a(f4823f, jSONObject.getJSONObject("cfg"));
            }
            if (!jSONObject.isNull("ncts")) {
                int i = jSONObject.getInt("ncts");
                int currentTimeMillis = (int) (((long) i) - (System.currentTimeMillis() / 1000));
                if (StatConfig.isDebugEnable()) {
                    f4821d.m4376i("server time:" + i + ", diff time:" + currentTimeMillis);
                }
                C1442k.m4448z(f4823f);
                C1442k.m4412a(f4823f, currentTimeMillis);
            }
        } catch (Throwable th) {
            f4821d.m4378w(th);
        }
    }

    static C1454i m4486b(Context context) {
        if (f4822e == null) {
            synchronized (C1454i.class) {
                if (f4822e == null) {
                    f4822e = new C1454i(context);
                }
            }
        }
        return f4822e;
    }

    void m4487a(C1416e c1416e, C1429h c1429h) {
        m4489b(Arrays.asList(new String[]{c1416e.m4273g()}), c1429h);
    }

    void m4488a(List<?> list, C1429h c1429h) {
        int i = 0;
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            list.get(0);
            Throwable th;
            try {
                String str;
                this.f4826c.delete(0, this.f4826c.length());
                this.f4826c.append("[");
                String str2 = "rc4";
                for (int i2 = 0; i2 < size; i2++) {
                    this.f4826c.append(list.get(i2).toString());
                    if (i2 != size - 1) {
                        this.f4826c.append(",");
                    }
                }
                this.f4826c.append("]");
                String stringBuilder = this.f4826c.toString();
                size = stringBuilder.length();
                String str3 = StatConfig.getStatReportUrl() + "/?index=" + this.f4827g;
                this.f4827g++;
                if (StatConfig.isDebugEnable()) {
                    f4821d.m4376i("[" + str3 + "]Send request(" + size + "bytes), content:" + stringBuilder);
                }
                HttpPost httpPost = new HttpPost(str3);
                httpPost.addHeader("Accept-Encoding", Constants.GZIP_TAG);
                httpPost.setHeader("Connection", "Keep-Alive");
                httpPost.removeHeaders("Cache-Control");
                HttpHost a = C1428a.m4298a(f4823f).m4305a();
                httpPost.addHeader(Constants.HTTP_HEADER_CONTENT_ENCODING, str2);
                if (a == null) {
                    this.f4824a.getParams().removeParameter("http.route.default-proxy");
                } else {
                    if (StatConfig.isDebugEnable()) {
                        f4821d.m4373d("proxy:" + a.toHostString());
                    }
                    httpPost.addHeader("X-Content-Encoding", str2);
                    this.f4824a.getParams().setParameter("http.route.default-proxy", a);
                    httpPost.addHeader("X-Online-Host", StatConfig.f4544k);
                    httpPost.addHeader("Accept", "*/*");
                    httpPost.addHeader("Content-Type", "json");
                }
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream(size);
                byte[] bytes = stringBuilder.getBytes("UTF-8");
                int length = bytes.length;
                if (size > StatConfig.f4548o) {
                    i = 1;
                }
                if (i != 0) {
                    httpPost.removeHeaders(Constants.HTTP_HEADER_CONTENT_ENCODING);
                    str = str2 + ",gzip";
                    httpPost.addHeader(Constants.HTTP_HEADER_CONTENT_ENCODING, str);
                    if (a != null) {
                        httpPost.removeHeaders("X-Content-Encoding");
                        httpPost.addHeader("X-Content-Encoding", str);
                    }
                    byteArrayOutputStream.write(new byte[4]);
                    GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    gZIPOutputStream.write(bytes);
                    gZIPOutputStream.close();
                    bytes = byteArrayOutputStream.toByteArray();
                    ByteBuffer.wrap(bytes, 0, 4).putInt(length);
                    if (StatConfig.isDebugEnable()) {
                        f4821d.m4373d("before Gzip:" + length + " bytes, after Gzip:" + bytes.length + " bytes");
                    }
                }
                httpPost.setEntity(new ByteArrayEntity(C1437f.m4390a(bytes)));
                HttpResponse execute = this.f4824a.execute(httpPost);
                HttpEntity entity = execute.getEntity();
                size = execute.getStatusLine().getStatusCode();
                long contentLength = entity.getContentLength();
                if (StatConfig.isDebugEnable()) {
                    f4821d.m4376i("http recv response status code:" + size + ", content length:" + contentLength);
                }
                if (contentLength <= 0) {
                    f4821d.m4374e((Object) "Server response no data.");
                    if (c1429h != null) {
                        c1429h.mo2226b();
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
                    Header firstHeader = execute.getFirstHeader(Constants.HTTP_HEADER_CONTENT_ENCODING);
                    if (firstHeader != null) {
                        if (firstHeader.getValue().equalsIgnoreCase("gzip,rc4")) {
                            bytes = C1437f.m4392b(C1442k.m4414a(bytes));
                        } else if (firstHeader.getValue().equalsIgnoreCase("rc4,gzip")) {
                            bytes = C1442k.m4414a(C1437f.m4392b(bytes));
                        } else if (firstHeader.getValue().equalsIgnoreCase(Constants.GZIP_TAG)) {
                            bytes = C1442k.m4414a(bytes);
                        } else if (firstHeader.getValue().equalsIgnoreCase("rc4")) {
                            bytes = C1437f.m4392b(bytes);
                        }
                    }
                    str = new String(bytes, "UTF-8");
                    if (StatConfig.isDebugEnable()) {
                        f4821d.m4376i("http get response data:" + str);
                    }
                    JSONObject jSONObject = new JSONObject(str);
                    if (size == 200) {
                        m4485a(jSONObject);
                        if (c1429h != null) {
                            if (jSONObject.optInt("ret") == 0) {
                                c1429h.mo2225a();
                            } else {
                                f4821d.error((Object) "response error data.");
                                c1429h.mo2226b();
                            }
                        }
                    } else {
                        f4821d.error("Server response error code:" + size + ", error:" + new String(bytes, "UTF-8"));
                        if (c1429h != null) {
                            c1429h.mo2226b();
                        }
                    }
                    content.close();
                } else {
                    EntityUtils.toString(entity);
                }
                byteArrayOutputStream.close();
                th = null;
                if (th != null) {
                    f4821d.error(th);
                    if (c1429h != null) {
                        try {
                            c1429h.mo2226b();
                        } catch (Throwable th2) {
                            f4821d.m4375e(th2);
                        }
                    }
                    if (th instanceof OutOfMemoryError) {
                        System.gc();
                        this.f4826c = null;
                        this.f4826c = new StringBuilder(2048);
                    }
                    C1428a.m4298a(f4823f).m4309d();
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }

    void m4489b(List<?> list, C1429h c1429h) {
        if (this.f4825b != null) {
            this.f4825b.m4388a(new C1456k(this, list, c1429h));
        }
    }
}
