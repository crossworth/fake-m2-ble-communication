package com.baidu.location.p006h;

import android.util.Log;
import com.tencent.connect.common.Constants;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

class C0465g extends Thread {
    final /* synthetic */ boolean f864a;
    final /* synthetic */ C0335e f865b;

    C0465g(C0335e c0335e, boolean z) {
        this.f865b = c0335e;
        this.f864a = z;
    }

    public void run() {
        Throwable th;
        this.f865b.f133h = C0468j.m1023c();
        this.f865b.mo1747b();
        this.f865b.mo1741a();
        HttpURLConnection httpURLConnection = null;
        int i = this.f865b.f134i;
        while (i > 0) {
            HttpURLConnection httpURLConnection2;
            try {
                URL url = new URL(this.f865b.f133h);
                StringBuffer stringBuffer = new StringBuffer();
                for (Entry entry : this.f865b.f136k.entrySet()) {
                    stringBuffer.append((String) entry.getKey());
                    stringBuffer.append("=");
                    stringBuffer.append(entry.getValue());
                    stringBuffer.append("&");
                }
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
                httpURLConnection2 = (HttpURLConnection) url.openConnection();
                try {
                    httpURLConnection2.setRequestMethod(Constants.HTTP_POST);
                    httpURLConnection2.setDoInput(true);
                    httpURLConnection2.setDoOutput(true);
                    httpURLConnection2.setUseCaches(false);
                    httpURLConnection2.setConnectTimeout(C0458a.f827b);
                    httpURLConnection2.setReadTimeout(C0458a.f827b);
                    httpURLConnection2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    httpURLConnection2.setRequestProperty("Accept-Charset", "UTF-8");
                    httpURLConnection2.setRequestProperty("Accept-Encoding", com.tyd.aidlservice.internal.Constants.GZIP_TAG);
                    httpURLConnection2.setRequestProperty("Host", "loc.map.baidu.com");
                    OutputStream outputStream = httpURLConnection2.getOutputStream();
                    outputStream.write(stringBuffer.toString().getBytes());
                    outputStream.flush();
                    outputStream.close();
                    if (httpURLConnection2.getResponseCode() == 200) {
                        InputStream inputStream = httpURLConnection2.getInputStream();
                        String contentEncoding = httpURLConnection2.getContentEncoding();
                        InputStream gZIPInputStream = (contentEncoding == null || !contentEncoding.contains(com.tyd.aidlservice.internal.Constants.GZIP_TAG)) ? inputStream : new GZIPInputStream(new BufferedInputStream(inputStream));
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = gZIPInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                        gZIPInputStream.close();
                        byteArrayOutputStream.close();
                        this.f865b.f135j = new String(byteArrayOutputStream.toByteArray(), "utf-8");
                        if (this.f864a) {
                            this.f865b.f138m = byteArrayOutputStream.toByteArray();
                        }
                        this.f865b.mo1742a(true);
                        httpURLConnection2.disconnect();
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        if (i > 0) {
                            C0335e.f132o++;
                            this.f865b.f135j = null;
                            this.f865b.mo1742a(false);
                            return;
                        }
                        C0335e.f132o = 0;
                        return;
                    }
                    httpURLConnection2.disconnect();
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    httpURLConnection = httpURLConnection2;
                    i--;
                } catch (Exception e) {
                    Log.d(C0458a.f826a, "NetworkCommunicationException!");
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    httpURLConnection = httpURLConnection2;
                    i--;
                } catch (Error e2) {
                    try {
                        Log.d(C0458a.f826a, "NetworkCommunicationError!");
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        httpURLConnection = httpURLConnection2;
                        i--;
                    } catch (Throwable th2) {
                        httpURLConnection = httpURLConnection2;
                        th = th2;
                    }
                }
            } catch (Exception e3) {
                httpURLConnection2 = httpURLConnection;
                Log.d(C0458a.f826a, "NetworkCommunicationException!");
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                httpURLConnection = httpURLConnection2;
                i--;
            } catch (Error e4) {
                httpURLConnection2 = httpURLConnection;
                Log.d(C0458a.f826a, "NetworkCommunicationError!");
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                httpURLConnection = httpURLConnection2;
                i--;
            } catch (Throwable th3) {
                th = th3;
            }
        }
        if (i > 0) {
            C0335e.f132o = 0;
            return;
        }
        C0335e.f132o++;
        this.f865b.f135j = null;
        this.f865b.mo1742a(false);
        return;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        throw th;
    }
}
