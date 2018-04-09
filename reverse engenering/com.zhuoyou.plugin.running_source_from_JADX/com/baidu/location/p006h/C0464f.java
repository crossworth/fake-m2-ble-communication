package com.baidu.location.p006h;

import android.util.Log;
import com.tencent.connect.common.Constants;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class C0464f extends Thread {
    final /* synthetic */ C0335e f863a;

    C0464f(C0335e c0335e) {
        this.f863a = c0335e;
    }

    public void run() {
        Throwable th;
        this.f863a.f133h = C0468j.m1023c();
        this.f863a.mo1747b();
        this.f863a.mo1741a();
        HttpURLConnection httpURLConnection = null;
        int i = this.f863a.f134i;
        while (i > 0) {
            HttpURLConnection httpURLConnection2;
            try {
                httpURLConnection2 = (HttpURLConnection) new URL(this.f863a.f133h).openConnection();
                try {
                    httpURLConnection2.setRequestMethod(Constants.HTTP_GET);
                    httpURLConnection2.setDoInput(true);
                    httpURLConnection2.setDoOutput(true);
                    httpURLConnection2.setUseCaches(false);
                    httpURLConnection2.setConnectTimeout(C0458a.f827b);
                    httpURLConnection2.setReadTimeout(C0458a.f827b);
                    httpURLConnection2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    httpURLConnection2.setRequestProperty("Accept-Charset", "UTF-8");
                    if (httpURLConnection2.getResponseCode() == 200) {
                        InputStream inputStream = httpURLConnection2.getInputStream();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                        inputStream.close();
                        byteArrayOutputStream.close();
                        this.f863a.f135j = new String(byteArrayOutputStream.toByteArray(), "utf-8");
                        this.f863a.mo1742a(true);
                        httpURLConnection2.disconnect();
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        if (i > 0) {
                            C0335e.f132o++;
                            this.f863a.f135j = null;
                            this.f863a.mo1742a(false);
                            return;
                        }
                        C0335e.f132o = 0;
                        return;
                    }
                    httpURLConnection2.disconnect();
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    i--;
                    httpURLConnection = httpURLConnection2;
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                httpURLConnection2 = httpURLConnection;
                try {
                    Log.d(C0458a.f826a, "NetworkCommunicationException!");
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    i--;
                    httpURLConnection = httpURLConnection2;
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    httpURLConnection = httpURLConnection2;
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        }
        if (i > 0) {
            C0335e.f132o = 0;
            return;
        }
        C0335e.f132o++;
        this.f863a.f135j = null;
        this.f863a.mo1742a(false);
        return;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        throw th;
    }
}
