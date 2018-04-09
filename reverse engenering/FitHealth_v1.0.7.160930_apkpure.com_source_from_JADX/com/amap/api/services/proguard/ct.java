package com.amap.api.services.proguard;

import android.os.Build.VERSION;
import com.umeng.socialize.common.SocializeConstants;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import org.apache.http.conn.ssl.SSLSocketFactory;

/* compiled from: HttpUrlUtil */
public class ct {
    private static cu f1517a;
    private int f1518b;
    private int f1519c;
    private boolean f1520d;
    private SSLContext f1521e;
    private Proxy f1522f;
    private volatile boolean f1523g;
    private long f1524h;
    private long f1525i;
    private HostnameVerifier f1526j;

    /* compiled from: HttpUrlUtil */
    class C03861 implements HostnameVerifier {
        final /* synthetic */ ct f1516a;

        C03861(ct ctVar) {
            this.f1516a = ctVar;
        }

        public boolean verify(String str, SSLSession sSLSession) {
            return HttpsURLConnection.getDefaultHostnameVerifier().verify("*.amap.com", sSLSession);
        }
    }

    public static void m1557a(cu cuVar) {
        f1517a = cuVar;
    }

    ct(int i, int i2, Proxy proxy, boolean z) {
        this.f1523g = false;
        this.f1524h = -1;
        this.f1525i = 0;
        this.f1526j = new C03861(this);
        this.f1518b = i;
        this.f1519c = i2;
        this.f1522f = proxy;
        this.f1520d = z;
        if (z) {
            try {
                SSLContext instance = SSLContext.getInstance(SSLSocketFactory.TLS);
                instance.init(null, null, null);
                this.f1521e = instance;
            } catch (Throwable th) {
                be.m1340a(th, "HttpUtil", "HttpUtil");
            }
        }
    }

    ct(int i, int i2, Proxy proxy) {
        this(i, i2, proxy, false);
    }

    void m1562a(long j) {
        this.f1525i = j;
    }

    void m1564b(long j) {
        this.f1524h = j;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void m1563a(java.lang.String r11, java.util.Map<java.lang.String, java.lang.String> r12, java.util.Map<java.lang.String, java.lang.String> r13, com.amap.api.services.proguard.cs.C0385a r14) {
        /*
        r10 = this;
        r1 = 0;
        r0 = 1;
        r8 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r3 = 0;
        r2 = 0;
        r4 = 0;
        if (r14 != 0) goto L_0x0038;
    L_0x0009:
        if (r1 == 0) goto L_0x000e;
    L_0x000b:
        r2.close();	 Catch:{ IOException -> 0x0014, Throwable -> 0x0020 }
    L_0x000e:
        if (r1 == 0) goto L_0x0013;
    L_0x0010:
        r4.disconnect();	 Catch:{ Throwable -> 0x002c }
    L_0x0013:
        return;
    L_0x0014:
        r0 = move-exception;
        r0.printStackTrace();
        r2 = "HttpUrlUtil";
        r3 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r2, r3);
        goto L_0x000e;
    L_0x0020:
        r0 = move-exception;
        r0.printStackTrace();
        r2 = "HttpUrlUtil";
        r3 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r2, r3);
        goto L_0x000e;
    L_0x002c:
        r0 = move-exception;
        r0.printStackTrace();
        r1 = "HttpUrlUtil";
        r2 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r1, r2);
        goto L_0x0013;
    L_0x0038:
        r2 = m1556a(r13);	 Catch:{ Throwable -> 0x01af, all -> 0x01a5 }
        r4 = new java.lang.StringBuffer;	 Catch:{ Throwable -> 0x01af, all -> 0x01a5 }
        r4.<init>();	 Catch:{ Throwable -> 0x01af, all -> 0x01a5 }
        r4.append(r11);	 Catch:{ Throwable -> 0x01af, all -> 0x01a5 }
        if (r2 == 0) goto L_0x004f;
    L_0x0046:
        r5 = "?";
        r5 = r4.append(r5);	 Catch:{ Throwable -> 0x01af, all -> 0x01a5 }
        r5.append(r2);	 Catch:{ Throwable -> 0x01af, all -> 0x01a5 }
    L_0x004f:
        r2 = r4.toString();	 Catch:{ Throwable -> 0x01af, all -> 0x01a5 }
        r4 = 0;
        r2 = r10.m1561a(r2, r12, r4);	 Catch:{ Throwable -> 0x01af, all -> 0x01a5 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r4.<init>();	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r5 = "bytes=";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r6 = r10.f1525i;	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r4 = r4.append(r6);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r5 = "-";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r5 = "RANGE";
        r2.setRequestProperty(r5, r4);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r2.connect();	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r5 = r2.getResponseCode();	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r5 == r4) goto L_0x010d;
    L_0x0083:
        r4 = r0;
    L_0x0084:
        r6 = 206; // 0xce float:2.89E-43 double:1.02E-321;
        if (r5 == r6) goto L_0x0110;
    L_0x0088:
        r0 = r0 & r4;
        if (r0 == 0) goto L_0x00b4;
    L_0x008b:
        r0 = new com.amap.api.services.proguard.ar;	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r3.<init>();	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r4 = "网络异常原因：";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r4 = r2.getResponseMessage();	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r4 = " 网络异常状态码：";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r3 = r3.append(r5);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r0.<init>(r3);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r14.mo1771a(r0);	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
    L_0x00b4:
        r1 = r2.getInputStream();	 Catch:{ Throwable -> 0x01b3, all -> 0x0120 }
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = new byte[r0];	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
    L_0x00bc:
        r3 = java.lang.Thread.interrupted();	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        if (r3 != 0) goto L_0x012c;
    L_0x00c2:
        r3 = r10.f1523g;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        if (r3 != 0) goto L_0x012c;
    L_0x00c6:
        r3 = 0;
        r4 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r3 = r1.read(r0, r3, r4);	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        if (r3 <= 0) goto L_0x012c;
    L_0x00cf:
        r4 = r10.f1524h;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r6 = -1;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x00df;
    L_0x00d7:
        r4 = r10.f1525i;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r6 = r10.f1524h;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 >= 0) goto L_0x012c;
    L_0x00df:
        if (r3 != r8) goto L_0x0113;
    L_0x00e1:
        r4 = r10.f1525i;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r14.mo1772a(r0, r4);	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
    L_0x00e6:
        r4 = r10.f1525i;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r6 = (long) r3;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r4 = r4 + r6;
        r10.f1525i = r4;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        goto L_0x00bc;
    L_0x00ed:
        r0 = move-exception;
        r9 = r2;
        r2 = r1;
        r1 = r9;
    L_0x00f1:
        r14.mo1771a(r0);	 Catch:{ all -> 0x01a9 }
        if (r2 == 0) goto L_0x00f9;
    L_0x00f6:
        r2.close();	 Catch:{ IOException -> 0x0168, Throwable -> 0x0174 }
    L_0x00f9:
        if (r1 == 0) goto L_0x0013;
    L_0x00fb:
        r1.disconnect();	 Catch:{ Throwable -> 0x0100 }
        goto L_0x0013;
    L_0x0100:
        r0 = move-exception;
        r0.printStackTrace();
        r1 = "HttpUrlUtil";
        r2 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r1, r2);
        goto L_0x0013;
    L_0x010d:
        r4 = r3;
        goto L_0x0084;
    L_0x0110:
        r0 = r3;
        goto L_0x0088;
    L_0x0113:
        r4 = new byte[r3];	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r5 = 0;
        r6 = 0;
        java.lang.System.arraycopy(r0, r5, r4, r6, r3);	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r6 = r10.f1525i;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        r14.mo1772a(r4, r6);	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        goto L_0x00e6;
    L_0x0120:
        r0 = move-exception;
    L_0x0121:
        if (r1 == 0) goto L_0x0126;
    L_0x0123:
        r1.close();	 Catch:{ IOException -> 0x0181, Throwable -> 0x018d }
    L_0x0126:
        if (r2 == 0) goto L_0x012b;
    L_0x0128:
        r2.disconnect();	 Catch:{ Throwable -> 0x0199 }
    L_0x012b:
        throw r0;
    L_0x012c:
        r0 = r10.f1523g;	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        if (r0 == 0) goto L_0x014c;
    L_0x0130:
        r14.mo1774c();	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
    L_0x0133:
        if (r1 == 0) goto L_0x0138;
    L_0x0135:
        r1.close();	 Catch:{ IOException -> 0x0150, Throwable -> 0x015c }
    L_0x0138:
        if (r2 == 0) goto L_0x0013;
    L_0x013a:
        r2.disconnect();	 Catch:{ Throwable -> 0x013f }
        goto L_0x0013;
    L_0x013f:
        r0 = move-exception;
        r0.printStackTrace();
        r1 = "HttpUrlUtil";
        r2 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r1, r2);
        goto L_0x0013;
    L_0x014c:
        r14.mo1773b();	 Catch:{ Throwable -> 0x00ed, all -> 0x0120 }
        goto L_0x0133;
    L_0x0150:
        r0 = move-exception;
        r0.printStackTrace();
        r1 = "HttpUrlUtil";
        r3 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r1, r3);
        goto L_0x0138;
    L_0x015c:
        r0 = move-exception;
        r0.printStackTrace();
        r1 = "HttpUrlUtil";
        r3 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r1, r3);
        goto L_0x0138;
    L_0x0168:
        r0 = move-exception;
        r0.printStackTrace();
        r2 = "HttpUrlUtil";
        r3 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r2, r3);
        goto L_0x00f9;
    L_0x0174:
        r0 = move-exception;
        r0.printStackTrace();
        r2 = "HttpUrlUtil";
        r3 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r0, r2, r3);
        goto L_0x00f9;
    L_0x0181:
        r1 = move-exception;
        r1.printStackTrace();
        r3 = "HttpUrlUtil";
        r4 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r1, r3, r4);
        goto L_0x0126;
    L_0x018d:
        r1 = move-exception;
        r1.printStackTrace();
        r3 = "HttpUrlUtil";
        r4 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r1, r3, r4);
        goto L_0x0126;
    L_0x0199:
        r1 = move-exception;
        r1.printStackTrace();
        r2 = "HttpUrlUtil";
        r3 = "makeDownloadGetRequest";
        com.amap.api.services.proguard.be.m1340a(r1, r2, r3);
        goto L_0x012b;
    L_0x01a5:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0121;
    L_0x01a9:
        r0 = move-exception;
        r9 = r1;
        r1 = r2;
        r2 = r9;
        goto L_0x0121;
    L_0x01af:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00f1;
    L_0x01b3:
        r0 = move-exception;
        r9 = r2;
        r2 = r1;
        r1 = r9;
        goto L_0x00f1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.services.proguard.ct.a(java.lang.String, java.util.Map, java.util.Map, com.amap.api.services.proguard.cs$a):void");
    }

    cy m1559a(String str, Map<String, String> map, Map<String, String> map2) throws ar {
        try {
            String a = m1556a((Map) map2);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str);
            if (a != null) {
                stringBuffer.append("?").append(a);
            }
            HttpURLConnection a2 = m1561a(stringBuffer.toString(), (Map) map, false);
            a2.connect();
            return m1555a(a2);
        } catch (ConnectException e) {
            throw new ar("http连接失败 - ConnectionException");
        } catch (MalformedURLException e2) {
            throw new ar("url异常 - MalformedURLException");
        } catch (UnknownHostException e3) {
            throw new ar("未知主机 - UnKnowHostException");
        } catch (SocketException e4) {
            throw new ar("socket 连接异常 - SocketException");
        } catch (SocketTimeoutException e5) {
            throw new ar("socket 连接超时 - SocketTimeoutException");
        } catch (IOException e6) {
            throw new ar("IO 操作异常 - IOException");
        } catch (Throwable th) {
            th.printStackTrace();
            ar arVar = new ar("未知的错误");
        }
    }

    cy m1560a(String str, Map<String, String> map, byte[] bArr) throws ar {
        try {
            HttpURLConnection a = m1561a(str, (Map) map, true);
            if (bArr != null && bArr.length > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(a.getOutputStream());
                dataOutputStream.write(bArr);
                dataOutputStream.close();
            }
            a.connect();
            return m1555a(a);
        } catch (ConnectException e) {
            e.printStackTrace();
            throw new ar("http连接失败 - ConnectionException");
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            throw new ar("url异常 - MalformedURLException");
        } catch (UnknownHostException e3) {
            e3.printStackTrace();
            throw new ar("未知主机 - UnKnowHostException");
        } catch (SocketException e4) {
            e4.printStackTrace();
            throw new ar("socket 连接异常 - SocketException");
        } catch (SocketTimeoutException e5) {
            e5.printStackTrace();
            throw new ar("socket 连接超时 - SocketTimeoutException");
        } catch (IOException e6) {
            e6.printStackTrace();
            throw new ar("IO 操作异常 - IOException");
        } catch (Throwable th) {
            be.m1340a(th, "HttpUrlUtil", "makePostReqeust");
            ar arVar = new ar("未知的错误");
        }
    }

    HttpURLConnection m1561a(String str, Map<String, String> map, boolean z) throws IOException {
        HttpURLConnection httpURLConnection;
        aw.m1242a();
        URL url = new URL(str);
        if (this.f1522f != null) {
            URLConnection openConnection = url.openConnection(this.f1522f);
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        if (this.f1520d) {
            httpURLConnection = (HttpsURLConnection) openConnection;
            ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(this.f1521e.getSocketFactory());
            ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(this.f1526j);
        } else {
            httpURLConnection = (HttpURLConnection) openConnection;
        }
        if (VERSION.SDK != null && VERSION.SDK_INT > 13) {
            httpURLConnection.setRequestProperty("Connection", "close");
        }
        m1558a(map, httpURLConnection);
        if (z) {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
        } else {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
        }
        return httpURLConnection;
    }

    private cy m1555a(HttpURLConnection httpURLConnection) throws ar, IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream pushbackInputStream;
        IOException e;
        Throwable th;
        InputStream gZIPInputStream;
        InputStream inputStream;
        PushbackInputStream pushbackInputStream2 = null;
        InputStream inputStream2;
        try {
            Map headerFields = httpURLConnection.getHeaderFields();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                throw new ar("网络异常原因：" + httpURLConnection.getResponseMessage() + " 网络异常状态码：" + responseCode);
            }
            byte[] bArr;
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                inputStream2 = httpURLConnection.getInputStream();
                try {
                    pushbackInputStream = new PushbackInputStream(inputStream2, 2);
                } catch (IOException e2) {
                    e = e2;
                    pushbackInputStream = null;
                    try {
                        throw e;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    pushbackInputStream = null;
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable th4) {
                            be.m1340a(th4, "HttpUrlUtil", "parseResult");
                        }
                    }
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (Throwable th5) {
                            be.m1340a(th5, "HttpUrlUtil", "parseResult");
                        }
                    }
                    if (pushbackInputStream2 != null) {
                        try {
                            pushbackInputStream2.close();
                        } catch (Throwable th6) {
                            be.m1340a(th6, "HttpUrlUtil", "parseResult");
                        }
                    }
                    if (pushbackInputStream != null) {
                        try {
                            pushbackInputStream.close();
                        } catch (Throwable th62) {
                            be.m1340a(th62, "HttpUrlUtil", "parseResult");
                        }
                    }
                    if (httpURLConnection != null) {
                        try {
                            httpURLConnection.disconnect();
                        } catch (Throwable th622) {
                            be.m1340a(th622, "HttpUrlUtil", "parseResult");
                        }
                    }
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                pushbackInputStream = null;
                inputStream2 = null;
                throw e;
            } catch (Throwable th7) {
                th = th7;
                pushbackInputStream = null;
                inputStream2 = null;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (pushbackInputStream2 != null) {
                    pushbackInputStream2.close();
                }
                if (pushbackInputStream != null) {
                    pushbackInputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
            try {
                bArr = new byte[2];
                pushbackInputStream.read(bArr);
                pushbackInputStream.unread(bArr);
                if (bArr[0] == (byte) 31 && bArr[1] == (byte) -117) {
                    gZIPInputStream = new GZIPInputStream(pushbackInputStream);
                } else {
                    gZIPInputStream = pushbackInputStream;
                }
            } catch (IOException e4) {
                e = e4;
                inputStream = pushbackInputStream;
                pushbackInputStream = null;
                gZIPInputStream = inputStream;
                throw e;
            } catch (Throwable th8) {
                th = th8;
                inputStream = pushbackInputStream;
                pushbackInputStream = null;
                gZIPInputStream = inputStream;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (pushbackInputStream2 != null) {
                    pushbackInputStream2.close();
                }
                if (pushbackInputStream != null) {
                    pushbackInputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
            try {
                bArr = new byte[1024];
                while (true) {
                    int read = gZIPInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                if (f1517a != null) {
                    f1517a.mo1760a();
                }
                cy cyVar = new cy();
                cyVar.f1530a = byteArrayOutputStream.toByteArray();
                cyVar.f1531b = headerFields;
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th42) {
                        be.m1340a(th42, "HttpUrlUtil", "parseResult");
                    }
                }
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (Throwable th52) {
                        be.m1340a(th52, "HttpUrlUtil", "parseResult");
                    }
                }
                if (pushbackInputStream != null) {
                    try {
                        pushbackInputStream.close();
                    } catch (Throwable th9) {
                        be.m1340a(th9, "HttpUrlUtil", "parseResult");
                    }
                }
                if (gZIPInputStream != null) {
                    try {
                        gZIPInputStream.close();
                    } catch (Throwable th6222) {
                        be.m1340a(th6222, "HttpUrlUtil", "parseResult");
                    }
                }
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable th62222) {
                        be.m1340a(th62222, "HttpUrlUtil", "parseResult");
                    }
                }
                return cyVar;
            } catch (IOException e5) {
                e = e5;
                inputStream = pushbackInputStream;
                pushbackInputStream = gZIPInputStream;
                gZIPInputStream = inputStream;
                throw e;
            } catch (Throwable th10) {
                th = th10;
                inputStream = pushbackInputStream;
                pushbackInputStream = gZIPInputStream;
                gZIPInputStream = inputStream;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (inputStream2 != null) {
                    inputStream2.close();
                }
                if (pushbackInputStream2 != null) {
                    pushbackInputStream2.close();
                }
                if (pushbackInputStream != null) {
                    pushbackInputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            pushbackInputStream = null;
            inputStream2 = null;
            byteArrayOutputStream = null;
            throw e;
        } catch (Throwable th11) {
            th = th11;
            pushbackInputStream = null;
            inputStream2 = null;
            byteArrayOutputStream = null;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (inputStream2 != null) {
                inputStream2.close();
            }
            if (pushbackInputStream2 != null) {
                pushbackInputStream2.close();
            }
            if (pushbackInputStream != null) {
                pushbackInputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    private void m1558a(Map<String, String> map, HttpURLConnection httpURLConnection) {
        if (map != null) {
            for (String str : map.keySet()) {
                httpURLConnection.addRequestProperty(str, (String) map.get(str));
            }
        }
        try {
            httpURLConnection.addRequestProperty("csid", UUID.randomUUID().toString().replaceAll(SocializeConstants.OP_DIVIDER_MINUS, "").toLowerCase());
        } catch (Throwable th) {
            be.m1340a(th, "HttpUrlUtil", "addHeaders");
        }
        httpURLConnection.setConnectTimeout(this.f1518b);
        httpURLConnection.setReadTimeout(this.f1519c);
    }

    static String m1556a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (str2 == null) {
                str2 = "";
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(URLEncoder.encode(str));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(str2));
        }
        return stringBuilder.toString();
    }
}
