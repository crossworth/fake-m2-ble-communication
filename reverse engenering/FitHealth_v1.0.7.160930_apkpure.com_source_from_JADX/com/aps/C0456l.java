package com.aps;

import android.content.Context;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.amap.api.location.core.AMapLocException;
import com.amap.api.location.core.C0188c;
import com.amap.api.location.core.C0189d;
import com.facebook.internal.ServerProtocol;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: NetManager */
public class C0456l {
    private static C0456l f1899a = null;

    private C0456l() {
    }

    public static C0456l m1967a() {
        if (f1899a == null) {
            f1899a = new C0456l();
        }
        return f1899a;
    }

    public String m1974a(Context context, String str, byte[] bArr, String str2) throws AMapLocException {
        GZIPInputStream gZIPInputStream;
        HttpClient httpClient;
        Throwable th;
        BufferedReader bufferedReader;
        Throwable th2;
        Object obj;
        HttpPost httpPost;
        if (TextUtils.isEmpty(str) || bArr == null) {
            return null;
        }
        HttpClient b = C0470t.m2016b(context);
        if (C0456l.m1966a((NetworkInfo) b) == -1) {
            return null;
        }
        HttpPost httpPost2 = null;
        HttpPost httpPost3 = null;
        InputStream inputStream = null;
        GZIPInputStream gZIPInputStream2 = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader2 = null;
        StringBuffer stringBuffer = new StringBuffer();
        String str3 = "";
        InputStream content;
        InputStreamReader inputStreamReader2;
        try {
            b = C0456l.m1969a(context, (NetworkInfo) b);
            try {
                httpPost2 = new HttpPost(str);
                try {
                    String str4;
                    HttpEntity byteArrayEntity = new ByteArrayEntity(bArr);
                    httpPost2.addHeader("Content-Type", "application/x-www-form-urlencoded");
                    httpPost2.addHeader("User-Agent", "AMAP Location SDK Android 1.3.0");
                    httpPost2.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
                    httpPost2.addHeader("Connection", HTTP.CONN_KEEP_ALIVE);
                    httpPost2.addHeader("X-INFO", C0188c.m84a(null).m98a(str2));
                    httpPost2.addHeader("ia", "1");
                    httpPost2.addHeader("key", C0188c.m85a());
                    stringBuffer.delete(0, stringBuffer.length());
                    httpPost2.setEntity(byteArrayEntity);
                    HttpResponse execute = b.execute(httpPost2);
                    int statusCode = execute.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        content = execute.getEntity().getContent();
                        try {
                            str3 = execute.getEntity().getContentType().getValue();
                            CharSequence charSequence = "";
                            int indexOf = str3.indexOf("charset=");
                            if (indexOf != -1) {
                                charSequence = str3.substring(indexOf + 8);
                            }
                            if (TextUtils.isEmpty(charSequence)) {
                                str3 = "UTF-8";
                            } else {
                                CharSequence charSequence2 = charSequence;
                            }
                            if (C0456l.m1971a(execute)) {
                                gZIPInputStream = new GZIPInputStream(content);
                            } else {
                                inputStream = null;
                            }
                            if (gZIPInputStream != null) {
                                try {
                                    inputStreamReader2 = new InputStreamReader(gZIPInputStream, str3);
                                } catch (UnknownHostException e) {
                                    gZIPInputStream2 = gZIPInputStream;
                                    inputStream = content;
                                    httpPost3 = httpPost2;
                                    httpClient = b;
                                    try {
                                        throw new AMapLocException("未知主机 - UnKnowHostException");
                                    } catch (Throwable th3) {
                                        th = th3;
                                        b = httpClient;
                                        httpPost2 = httpPost3;
                                        content = inputStream;
                                        gZIPInputStream = gZIPInputStream2;
                                        inputStreamReader2 = inputStreamReader;
                                        bufferedReader = bufferedReader2;
                                        th2 = th;
                                        if (httpPost2 != null) {
                                            httpPost2.abort();
                                        }
                                        if (b != null) {
                                            b.getConnectionManager().shutdown();
                                        }
                                        if (gZIPInputStream != null) {
                                            try {
                                                gZIPInputStream.close();
                                            } catch (Throwable th4) {
                                            }
                                        }
                                        if (content != null) {
                                            try {
                                                content.close();
                                            } catch (Throwable th5) {
                                                th5.printStackTrace();
                                            }
                                        }
                                        if (inputStreamReader2 != null) {
                                            try {
                                                inputStreamReader2.close();
                                            } catch (Throwable th6) {
                                                th6.printStackTrace();
                                            }
                                        }
                                        if (bufferedReader != null) {
                                            try {
                                                bufferedReader.close();
                                            } catch (Throwable th7) {
                                                th7.printStackTrace();
                                            }
                                        }
                                        throw th2;
                                    }
                                } catch (SocketException e2) {
                                    gZIPInputStream2 = gZIPInputStream;
                                    inputStream = content;
                                    throw new AMapLocException("socket 连接异常 - SocketException");
                                } catch (SocketTimeoutException e3) {
                                    gZIPInputStream2 = gZIPInputStream;
                                    inputStream = content;
                                    throw new AMapLocException("socket 连接超时 - SocketTimeoutException");
                                } catch (ConnectTimeoutException e4) {
                                    gZIPInputStream2 = gZIPInputStream;
                                    inputStream = content;
                                    throw new AMapLocException("http连接失败 - ConnectionException");
                                } catch (Throwable th62) {
                                    th = th62;
                                    inputStreamReader2 = null;
                                    bufferedReader = null;
                                    th2 = th;
                                    if (httpPost2 != null) {
                                        httpPost2.abort();
                                    }
                                    if (b != null) {
                                        b.getConnectionManager().shutdown();
                                    }
                                    if (gZIPInputStream != null) {
                                        gZIPInputStream.close();
                                    }
                                    if (content != null) {
                                        content.close();
                                    }
                                    if (inputStreamReader2 != null) {
                                        inputStreamReader2.close();
                                    }
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    throw th2;
                                }
                            }
                            inputStreamReader2 = new InputStreamReader(content, str3);
                        } catch (UnknownHostException e5) {
                            inputStream = content;
                            httpPost3 = httpPost2;
                            httpClient = b;
                            throw new AMapLocException("未知主机 - UnKnowHostException");
                        } catch (SocketException e6) {
                            inputStream = content;
                            throw new AMapLocException("socket 连接异常 - SocketException");
                        } catch (SocketTimeoutException e7) {
                            inputStream = content;
                            throw new AMapLocException("socket 连接超时 - SocketTimeoutException");
                        } catch (ConnectTimeoutException e8) {
                            inputStream = content;
                            throw new AMapLocException("http连接失败 - ConnectionException");
                        } catch (Throwable th52) {
                            th = th52;
                            gZIPInputStream = null;
                            inputStreamReader2 = null;
                            bufferedReader = null;
                            th2 = th;
                            if (httpPost2 != null) {
                                httpPost2.abort();
                            }
                            if (b != null) {
                                b.getConnectionManager().shutdown();
                            }
                            if (gZIPInputStream != null) {
                                gZIPInputStream.close();
                            }
                            if (content != null) {
                                content.close();
                            }
                            if (inputStreamReader2 != null) {
                                inputStreamReader2.close();
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th2;
                        }
                        try {
                            bufferedReader = new BufferedReader(inputStreamReader2, 2048);
                            try {
                                str4 = "";
                                while (true) {
                                    str4 = bufferedReader.readLine();
                                    if (str4 == null) {
                                        break;
                                    }
                                    stringBuffer.append(str4);
                                }
                                str4 = stringBuffer.toString();
                                stringBuffer.delete(0, stringBuffer.length());
                            } catch (UnknownHostException e9) {
                                bufferedReader2 = bufferedReader;
                                inputStreamReader = inputStreamReader2;
                                gZIPInputStream2 = gZIPInputStream;
                                inputStream = content;
                                httpPost3 = httpPost2;
                                httpClient = b;
                                throw new AMapLocException("未知主机 - UnKnowHostException");
                            } catch (SocketException e10) {
                                bufferedReader2 = bufferedReader;
                                inputStreamReader = inputStreamReader2;
                                gZIPInputStream2 = gZIPInputStream;
                                inputStream = content;
                                throw new AMapLocException("socket 连接异常 - SocketException");
                            } catch (SocketTimeoutException e11) {
                                bufferedReader2 = bufferedReader;
                                inputStreamReader = inputStreamReader2;
                                gZIPInputStream2 = gZIPInputStream;
                                inputStream = content;
                                throw new AMapLocException("socket 连接超时 - SocketTimeoutException");
                            } catch (ConnectTimeoutException e12) {
                                bufferedReader2 = bufferedReader;
                                inputStreamReader = inputStreamReader2;
                                gZIPInputStream2 = gZIPInputStream;
                                inputStream = content;
                                throw new AMapLocException("http连接失败 - ConnectionException");
                            } catch (Throwable th8) {
                                th2 = th8;
                                th2.printStackTrace();
                                throw new AMapLocException("未知的错误");
                            }
                        } catch (UnknownHostException e13) {
                            inputStreamReader = inputStreamReader2;
                            gZIPInputStream2 = gZIPInputStream;
                            inputStream = content;
                            httpPost3 = httpPost2;
                            httpClient = b;
                            throw new AMapLocException("未知主机 - UnKnowHostException");
                        } catch (SocketException e14) {
                            inputStreamReader = inputStreamReader2;
                            gZIPInputStream2 = gZIPInputStream;
                            inputStream = content;
                            throw new AMapLocException("socket 连接异常 - SocketException");
                        } catch (SocketTimeoutException e15) {
                            inputStreamReader = inputStreamReader2;
                            gZIPInputStream2 = gZIPInputStream;
                            inputStream = content;
                            throw new AMapLocException("socket 连接超时 - SocketTimeoutException");
                        } catch (ConnectTimeoutException e16) {
                            inputStreamReader = inputStreamReader2;
                            gZIPInputStream2 = gZIPInputStream;
                            inputStream = content;
                            throw new AMapLocException("http连接失败 - ConnectionException");
                        } catch (Throwable th72) {
                            th = th72;
                            bufferedReader = null;
                            th2 = th;
                            if (httpPost2 != null) {
                                httpPost2.abort();
                            }
                            if (b != null) {
                                b.getConnectionManager().shutdown();
                            }
                            if (gZIPInputStream != null) {
                                gZIPInputStream.close();
                            }
                            if (content != null) {
                                content.close();
                            }
                            if (inputStreamReader2 != null) {
                                inputStreamReader2.close();
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th2;
                        }
                    } else if (statusCode == 404) {
                        throw new AMapLocException("服务器连接失败 - UnknownServiceException");
                    } else {
                        content = null;
                        gZIPInputStream = null;
                        inputStreamReader2 = null;
                        bufferedReader = null;
                        str4 = str3;
                    }
                    if (httpPost2 != null) {
                        httpPost2.abort();
                    }
                    if (b != null) {
                        b.getConnectionManager().shutdown();
                    }
                    if (gZIPInputStream != null) {
                        try {
                            gZIPInputStream.close();
                        } catch (Throwable th9) {
                        }
                    }
                    if (content != null) {
                        try {
                            content.close();
                        } catch (Throwable th522) {
                            th522.printStackTrace();
                        }
                    }
                    if (inputStreamReader2 != null) {
                        try {
                            inputStreamReader2.close();
                        } catch (Throwable th622) {
                            th622.printStackTrace();
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th722) {
                            th722.printStackTrace();
                        }
                    }
                    if (TextUtils.isEmpty(str4)) {
                        return null;
                    }
                    return str4;
                } catch (UnknownHostException e17) {
                    httpPost3 = httpPost2;
                    httpClient = b;
                    throw new AMapLocException("未知主机 - UnKnowHostException");
                } catch (SocketException e18) {
                    throw new AMapLocException("socket 连接异常 - SocketException");
                } catch (SocketTimeoutException e19) {
                    throw new AMapLocException("socket 连接超时 - SocketTimeoutException");
                } catch (ConnectTimeoutException e20) {
                    throw new AMapLocException("http连接失败 - ConnectionException");
                } catch (Throwable th10) {
                    th = th10;
                    content = null;
                    gZIPInputStream = null;
                    inputStreamReader2 = null;
                    bufferedReader = null;
                    th2 = th;
                    if (httpPost2 != null) {
                        httpPost2.abort();
                    }
                    if (b != null) {
                        b.getConnectionManager().shutdown();
                    }
                    if (gZIPInputStream != null) {
                        gZIPInputStream.close();
                    }
                    if (content != null) {
                        content.close();
                    }
                    if (inputStreamReader2 != null) {
                        inputStreamReader2.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th2;
                }
            } catch (UnknownHostException e21) {
                httpClient = b;
                throw new AMapLocException("未知主机 - UnKnowHostException");
            } catch (SocketException e22) {
                httpPost2 = null;
                throw new AMapLocException("socket 连接异常 - SocketException");
            } catch (SocketTimeoutException e23) {
                obj = null;
                throw new AMapLocException("socket 连接超时 - SocketTimeoutException");
            } catch (ConnectTimeoutException e24) {
                obj = null;
                throw new AMapLocException("http连接失败 - ConnectionException");
            } catch (Throwable th11) {
                th = th11;
                httpPost2 = null;
                content = null;
                gZIPInputStream = null;
                inputStreamReader2 = null;
                bufferedReader = null;
                th2 = th;
                if (httpPost2 != null) {
                    httpPost2.abort();
                }
                if (b != null) {
                    b.getConnectionManager().shutdown();
                }
                if (gZIPInputStream != null) {
                    gZIPInputStream.close();
                }
                if (content != null) {
                    content.close();
                }
                if (inputStreamReader2 != null) {
                    inputStreamReader2.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                throw th2;
            }
        } catch (UnknownHostException e25) {
            throw new AMapLocException("未知主机 - UnKnowHostException");
        } catch (SocketException e26) {
            b = null;
            httpPost2 = null;
            throw new AMapLocException("socket 连接异常 - SocketException");
        } catch (SocketTimeoutException e27) {
            httpPost = null;
            obj = null;
            throw new AMapLocException("socket 连接超时 - SocketTimeoutException");
        } catch (ConnectTimeoutException e28) {
            httpPost = null;
            obj = null;
            throw new AMapLocException("http连接失败 - ConnectionException");
        } catch (Throwable th102) {
            th = th102;
            content = inputStream;
            gZIPInputStream = gZIPInputStream2;
            inputStreamReader2 = inputStreamReader;
            bufferedReader = bufferedReader2;
            th2 = th;
            if (httpPost2 != null) {
                httpPost2.abort();
            }
            if (b != null) {
                b.getConnectionManager().shutdown();
            }
            if (gZIPInputStream != null) {
                gZIPInputStream.close();
            }
            if (content != null) {
                content.close();
            }
            if (inputStreamReader2 != null) {
                inputStreamReader2.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            throw th2;
        }
    }

    public String m1976a(byte[] bArr, Context context, JSONObject jSONObject) throws Exception {
        BufferedReader bufferedReader;
        InputStreamReader inputStreamReader;
        GZIPInputStream gZIPInputStream;
        InputStream inputStream;
        HttpPost httpPost;
        Throwable th;
        NetworkInfo b = C0470t.m2016b(context);
        if (C0456l.m1966a(b) == -1) {
            throw new AMapLocException("http连接失败 - ConnectionException");
        }
        HttpPost httpPost2 = null;
        GZIPInputStream gZIPInputStream2 = null;
        BufferedReader bufferedReader2 = null;
        StringBuffer stringBuffer = new StringBuffer();
        Object obj = null;
        int i = 0;
        String str = "";
        InputStreamReader inputStreamReader2 = null;
        InputStream inputStream2 = null;
        HttpClient httpClient = null;
        while (i < 1 && obj == null) {
            HttpClient a;
            HttpPost httpPost3;
            try {
                a = C0456l.m1969a(context, b);
                try {
                    String[] a2 = C0456l.m1972a(jSONObject);
                    httpPost3 = new HttpPost(C0188c.m95j());
                    try {
                        Object obj2;
                        GZIPInputStream gZIPInputStream3;
                        StringBuffer stringBuffer2;
                        InputStream inputStream3;
                        InputStreamReader inputStreamReader3;
                        String str2;
                        String str3 = "UTF-8";
                        HttpEntity byteArrayEntity = new ByteArrayEntity(C0470t.m2013a(bArr));
                        byteArrayEntity.setContentType("application/octet-stream");
                        httpPost3.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
                        httpPost3.addHeader("gzipped", "1");
                        httpPost3.addHeader("X-INFO", a2[2]);
                        httpPost3.addHeader("X-BIZ", a2[3]);
                        httpPost3.addHeader("KEY", a2[1]);
                        httpPost3.addHeader("ec", "1");
                        if (a2[4] != null && a2[4].length() > 0) {
                            httpPost3.addHeader("User-Agent", a2[4]);
                        }
                        String a3 = C0189d.m103a();
                        String a4 = C0189d.m104a(a3, "key=" + a2[1]);
                        httpPost3.addHeader("ts", a3);
                        httpPost3.addHeader("scode", a4);
                        stringBuffer.delete(0, stringBuffer.length());
                        httpPost3.setEntity(byteArrayEntity);
                        HttpResponse execute = a.execute(httpPost3);
                        int statusCode = execute.getStatusLine().getStatusCode();
                        String str4;
                        if (statusCode == 200) {
                            inputStream2 = execute.getEntity().getContent();
                            String value = execute.getEntity().getContentType().getValue();
                            CharSequence charSequence = "";
                            statusCode = value.indexOf("charset=");
                            if (statusCode != -1) {
                                charSequence = value.substring(statusCode + 8);
                            }
                            if (!TextUtils.isEmpty(charSequence)) {
                                CharSequence charSequence2 = charSequence;
                            }
                            if (C0456l.m1971a(execute)) {
                                gZIPInputStream2 = new GZIPInputStream(inputStream2);
                            }
                            if (gZIPInputStream2 != null) {
                                inputStreamReader2 = new InputStreamReader(gZIPInputStream2, str3);
                            } else {
                                inputStreamReader2 = new InputStreamReader(inputStream2, str3);
                            }
                            bufferedReader = new BufferedReader(inputStreamReader2, 2048);
                            try {
                                String str5 = "";
                                while (true) {
                                    str5 = bufferedReader.readLine();
                                    if (str5 == null) {
                                        break;
                                    }
                                    stringBuffer.append(str5);
                                }
                                str3 = stringBuffer.toString();
                                stringBuffer.delete(0, stringBuffer.length());
                                obj2 = 1;
                                str4 = str3;
                                gZIPInputStream3 = gZIPInputStream2;
                                stringBuffer2 = null;
                                inputStream3 = inputStream2;
                                inputStreamReader3 = inputStreamReader2;
                                str2 = str4;
                            } catch (UnknownHostException e) {
                                inputStreamReader = inputStreamReader2;
                                gZIPInputStream = gZIPInputStream2;
                                inputStream = inputStream2;
                                httpPost = httpPost3;
                                httpClient = a;
                            } catch (SocketException e2) {
                                bufferedReader2 = bufferedReader;
                            } catch (SocketTimeoutException e3) {
                                bufferedReader2 = bufferedReader;
                            } catch (ConnectTimeoutException e4) {
                                bufferedReader2 = bufferedReader;
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } else if (statusCode == 404) {
                            throw new AMapLocException("服务器连接失败 - UnknownServiceException");
                        } else {
                            gZIPInputStream3 = gZIPInputStream2;
                            stringBuffer2 = stringBuffer;
                            str4 = str;
                            bufferedReader = bufferedReader2;
                            obj2 = obj;
                            inputStream3 = inputStream2;
                            inputStreamReader3 = inputStreamReader2;
                            str2 = str4;
                        }
                        if (httpPost3 != null) {
                            httpPost3.abort();
                            httpPost3 = null;
                        }
                        if (a != null) {
                            a.getConnectionManager().shutdown();
                            a = null;
                        }
                        if (gZIPInputStream3 != null) {
                            try {
                                gZIPInputStream3.close();
                            } catch (Throwable th3) {
                                th3.printStackTrace();
                            }
                            gZIPInputStream3 = null;
                        }
                        if (inputStream3 != null) {
                            try {
                                inputStream3.close();
                            } catch (Throwable th4) {
                                th4.printStackTrace();
                            }
                            inputStream3 = null;
                        }
                        if (inputStreamReader3 != null) {
                            inputStreamReader3.close();
                            inputStreamReader3 = null;
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                            bufferedReader = null;
                        }
                        i++;
                        stringBuffer = stringBuffer2;
                        gZIPInputStream2 = gZIPInputStream3;
                        httpPost2 = httpPost3;
                        httpClient = a;
                        InputStreamReader inputStreamReader4 = inputStreamReader3;
                        inputStream2 = inputStream3;
                        obj = obj2;
                        bufferedReader2 = bufferedReader;
                        str = str2;
                        inputStreamReader2 = inputStreamReader4;
                    } catch (UnknownHostException e5) {
                        bufferedReader = bufferedReader2;
                        inputStreamReader = inputStreamReader2;
                        gZIPInputStream = gZIPInputStream2;
                        inputStream = inputStream2;
                        httpPost = httpPost3;
                        httpClient = a;
                    } catch (SocketException e6) {
                    } catch (SocketTimeoutException e7) {
                    } catch (ConnectTimeoutException e8) {
                    } catch (Throwable th5) {
                    }
                } catch (UnknownHostException e9) {
                    bufferedReader = bufferedReader2;
                    httpClient = a;
                    inputStreamReader = inputStreamReader2;
                    gZIPInputStream = gZIPInputStream2;
                    inputStream = inputStream2;
                    httpPost = httpPost2;
                } catch (SocketException e10) {
                    httpPost3 = httpPost2;
                } catch (SocketTimeoutException e11) {
                    httpPost3 = httpPost2;
                } catch (ConnectTimeoutException e12) {
                    httpPost3 = httpPost2;
                } catch (Throwable th6) {
                    th = th6;
                    httpPost3 = httpPost2;
                }
            } catch (UnknownHostException e13) {
                bufferedReader = bufferedReader2;
                inputStreamReader = inputStreamReader2;
                gZIPInputStream = gZIPInputStream2;
                inputStream = inputStream2;
                httpPost = httpPost2;
            } catch (SocketException e14) {
                a = httpClient;
                httpPost3 = httpPost2;
            } catch (SocketTimeoutException e15) {
                a = httpClient;
                httpPost3 = httpPost2;
            } catch (ConnectTimeoutException e16) {
                a = httpClient;
                httpPost3 = httpPost2;
            } catch (Throwable th7) {
                th = th7;
                a = httpClient;
                httpPost3 = httpPost2;
            }
        }
        return TextUtils.isEmpty(str) ? null : str;
        throw new AMapLocException("http连接失败 - ConnectionException");
        throw new AMapLocException("socket 连接超时 - SocketTimeoutException");
        try {
            throw new AMapLocException("未知主机 - UnKnowHostException");
        } catch (Throwable th32) {
            a = httpClient;
            httpPost3 = httpPost;
            inputStream2 = inputStream;
            gZIPInputStream2 = gZIPInputStream;
            inputStreamReader2 = inputStreamReader;
            bufferedReader2 = bufferedReader;
            th = th32;
            if (httpPost3 != null) {
                httpPost3.abort();
            }
            if (a != null) {
                a.getConnectionManager().shutdown();
            }
            if (gZIPInputStream2 != null) {
                try {
                    gZIPInputStream2.close();
                } catch (Throwable th8) {
                    th8.printStackTrace();
                }
            }
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (Throwable th82) {
                    th82.printStackTrace();
                }
            }
            if (inputStreamReader2 != null) {
                inputStreamReader2.close();
            }
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            throw th;
        }
        throw new AMapLocException("socket 连接异常 - SocketException");
        throw new AMapLocException("http连接失败 - ConnectionException");
    }

    public static HttpClient m1969a(Context context, NetworkInfo networkInfo) throws Exception {
        Cursor query;
        boolean z;
        String toLowerCase;
        String b;
        int i;
        SchemeRegistry schemeRegistry;
        Throwable th;
        Throwable e;
        boolean z2 = true;
        Cursor cursor = null;
        HttpParams basicHttpParams = new BasicHttpParams();
        if (networkInfo.getType() == 0) {
            String string;
            try {
                query = context.getContentResolver().query(Uri.parse("content://telephony/carriers/preferapn"), null, null, null, null);
                if (query != null) {
                    Object obj;
                    try {
                        if (query.moveToFirst()) {
                            string = query.getString(query.getColumnIndex("apn"));
                            if (string != null) {
                                string = string.toLowerCase(Locale.US);
                                C0470t.m2010a("nm|found apn:", string);
                            }
                            if (string != null && string.contains("ctwap")) {
                                string = C0456l.m1973b();
                                if (TextUtils.isEmpty(string) || string.equals("null")) {
                                    z = false;
                                    obj = null;
                                } else {
                                    z = true;
                                }
                                if (!z) {
                                    try {
                                        string = "10.0.0.200";
                                    } catch (SecurityException e2) {
                                        cursor = query;
                                        try {
                                            if (networkInfo.getExtraInfo() != null) {
                                                toLowerCase = networkInfo.getExtraInfo().toLowerCase(Locale.US);
                                                b = C0456l.m1973b();
                                                if (toLowerCase.indexOf("ctwap") == -1) {
                                                    if (!TextUtils.isEmpty(b)) {
                                                    }
                                                    z2 = false;
                                                    if (!z2) {
                                                        string = "10.0.0.200";
                                                    }
                                                    b = string;
                                                    i = 80;
                                                } else if (toLowerCase.indexOf("wap") != -1) {
                                                    if (!TextUtils.isEmpty(b)) {
                                                    }
                                                    z = false;
                                                    if (!z) {
                                                        string = "10.0.0.200";
                                                    }
                                                    b = string;
                                                    i = 80;
                                                }
                                                if (cursor != null) {
                                                    cursor.close();
                                                }
                                                if (C0456l.m1970a(b, i)) {
                                                    basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                                                }
                                                C0470t.m2009a(basicHttpParams, 30000);
                                                HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                                                schemeRegistry = new SchemeRegistry();
                                                schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                                                return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                                            }
                                            b = string;
                                            i = -1;
                                            if (cursor != null) {
                                                cursor.close();
                                            }
                                            if (C0456l.m1970a(b, i)) {
                                                basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                                            }
                                            C0470t.m2009a(basicHttpParams, 30000);
                                            HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                                            schemeRegistry = new SchemeRegistry();
                                            schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                                            return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                                        } catch (Throwable th2) {
                                            th = th2;
                                            query = cursor;
                                            if (query != null) {
                                                query.close();
                                            }
                                            throw th;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        try {
                                            C0470t.m2008a(e);
                                            if (query != null) {
                                                query.close();
                                            }
                                            b = string;
                                            i = -1;
                                            if (C0456l.m1970a(b, i)) {
                                                basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                                            }
                                            C0470t.m2009a(basicHttpParams, 30000);
                                            HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                                            schemeRegistry = new SchemeRegistry();
                                            schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                                            return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                                        } catch (Throwable th3) {
                                            th = th3;
                                            if (query != null) {
                                                query.close();
                                            }
                                            throw th;
                                        }
                                    }
                                }
                                b = string;
                                i = 80;
                                if (query != null) {
                                    query.close();
                                }
                            } else if (string != null) {
                                if (string.contains("wap")) {
                                    string = C0456l.m1973b();
                                    if (TextUtils.isEmpty(string) || string.equals("null")) {
                                        z = false;
                                        obj = null;
                                    } else {
                                        z = true;
                                    }
                                    if (!z) {
                                        string = "10.0.0.172";
                                    }
                                    b = string;
                                    i = 80;
                                    if (query != null) {
                                        query.close();
                                    }
                                }
                            }
                        }
                    } catch (SecurityException e4) {
                        obj = null;
                        cursor = query;
                        if (networkInfo.getExtraInfo() != null) {
                            toLowerCase = networkInfo.getExtraInfo().toLowerCase(Locale.US);
                            b = C0456l.m1973b();
                            if (toLowerCase.indexOf("ctwap") == -1) {
                                if (TextUtils.isEmpty(b) || b.equals("null")) {
                                    z2 = false;
                                } else {
                                    string = b;
                                }
                                if (z2) {
                                    string = "10.0.0.200";
                                }
                                b = string;
                                i = 80;
                            } else if (toLowerCase.indexOf("wap") != -1) {
                                if (TextUtils.isEmpty(b) || b.equals("null")) {
                                    z = false;
                                } else {
                                    string = b;
                                    z = true;
                                }
                                if (z) {
                                    string = "10.0.0.200";
                                }
                                b = string;
                                i = 80;
                            }
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (C0456l.m1970a(b, i)) {
                                basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                            }
                            C0470t.m2009a(basicHttpParams, 30000);
                            HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                            schemeRegistry = new SchemeRegistry();
                            schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                            return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                        }
                        b = string;
                        i = -1;
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (C0456l.m1970a(b, i)) {
                            basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                        }
                        C0470t.m2009a(basicHttpParams, 30000);
                        HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                        schemeRegistry = new SchemeRegistry();
                        schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                        return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                    } catch (Throwable th4) {
                        e = th4;
                        string = null;
                        C0470t.m2008a(e);
                        if (query != null) {
                            query.close();
                        }
                        b = string;
                        i = -1;
                        if (C0456l.m1970a(b, i)) {
                            basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                        }
                        C0470t.m2009a(basicHttpParams, 30000);
                        HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                        schemeRegistry = new SchemeRegistry();
                        schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                        return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                    }
                }
                i = -1;
                b = null;
                if (query != null) {
                    query.close();
                }
            } catch (SecurityException e5) {
                string = null;
                if (networkInfo.getExtraInfo() != null) {
                    toLowerCase = networkInfo.getExtraInfo().toLowerCase(Locale.US);
                    b = C0456l.m1973b();
                    if (toLowerCase.indexOf("ctwap") == -1) {
                        if (TextUtils.isEmpty(b)) {
                        }
                        z2 = false;
                        if (z2) {
                            string = "10.0.0.200";
                        }
                        b = string;
                        i = 80;
                    } else if (toLowerCase.indexOf("wap") != -1) {
                        if (TextUtils.isEmpty(b)) {
                        }
                        z = false;
                        if (z) {
                            string = "10.0.0.200";
                        }
                        b = string;
                        i = 80;
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (C0456l.m1970a(b, i)) {
                        basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                    }
                    C0470t.m2009a(basicHttpParams, 30000);
                    HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                    schemeRegistry = new SchemeRegistry();
                    schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                    return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
                }
                b = string;
                i = -1;
                if (cursor != null) {
                    cursor.close();
                }
                if (C0456l.m1970a(b, i)) {
                    basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                }
                C0470t.m2009a(basicHttpParams, 30000);
                HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
            } catch (Throwable th42) {
                e = th42;
                query = null;
                string = null;
                C0470t.m2008a(e);
                if (query != null) {
                    query.close();
                }
                b = string;
                i = -1;
                if (C0456l.m1970a(b, i)) {
                    basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
                }
                C0470t.m2009a(basicHttpParams, 30000);
                HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
                schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
            } catch (Throwable th5) {
                th42 = th5;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th42;
            }
        }
        i = -1;
        b = null;
        if (C0456l.m1970a(b, i)) {
            basicHttpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(b, i, HttpHost.DEFAULT_SCHEME_NAME));
        }
        C0470t.m2009a(basicHttpParams, 30000);
        HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
        schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
        return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
    }

    private static boolean m1970a(String str, int i) {
        return (str == null || str.length() <= 0 || i == -1) ? false : true;
    }

    public static int m1966a(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public static String m1968a(TelephonyManager telephonyManager) {
        int i = 0;
        if (telephonyManager != null) {
            i = telephonyManager.getNetworkType();
        }
        return (String) C0446f.f1858l.get(i, "UNKNOWN");
    }

    private static boolean m1971a(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Content-Encoding");
        if (firstHeader == null || !firstHeader.getValue().equalsIgnoreCase("gzip")) {
            return false;
        }
        return true;
    }

    public static String[] m1972a(JSONObject jSONObject) {
        String[] strArr = new String[]{null, null, null, null, null};
        if (jSONObject == null || C0188c.m95j().length() == 0) {
            strArr[0] = "false";
        } else {
            try {
                CharSequence string = jSONObject.getString("key");
                String string2 = jSONObject.getString("X-INFO");
                String string3 = jSONObject.getString("X-BIZ");
                CharSequence string4 = jSONObject.getString("User-Agent");
                if (!(TextUtils.isEmpty(string) || TextUtils.isEmpty(string4))) {
                    strArr[0] = ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
                    strArr[1] = string;
                    strArr[2] = string2;
                    strArr[3] = string3;
                    strArr[4] = string4;
                }
            } catch (JSONException e) {
            }
            if (strArr[0] == null || !strArr[0].equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                strArr[0] = ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
            }
        }
        return strArr;
    }

    private static String m1973b() {
        String defaultHost;
        try {
            defaultHost = Proxy.getDefaultHost();
        } catch (Throwable th) {
            th.printStackTrace();
            defaultHost = null;
        }
        if (defaultHost == null) {
            return "null";
        }
        return defaultHost;
    }

    public String m1975a(byte[] bArr, Context context) throws Exception {
        HttpClient a;
        HttpPost httpPost;
        Reader inputStreamReader;
        HttpPost httpPost2;
        String str;
        BufferedReader bufferedReader;
        InputStream inputStream;
        HttpClient httpClient;
        BufferedReader bufferedReader2;
        Throwable th;
        Reader reader;
        String str2 = "";
        NetworkInfo b = C0470t.m2016b(context);
        if (C0456l.m1966a(b) == -1) {
            return null;
        }
        HttpClient httpClient2 = null;
        HttpPost httpPost3 = null;
        InputStream inputStream2 = null;
        InputStreamReader inputStreamReader2 = null;
        BufferedReader bufferedReader3 = null;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("http://cgicol.amap.com/collection/writedata?ver=v1.0_ali&");
        stringBuffer2.append("zei=").append(C0446f.f1847a);
        stringBuffer2.append("&zsi=").append(C0446f.f1848b);
        int i = 0;
        Object obj = null;
        StringBuffer stringBuffer3 = stringBuffer;
        String str3 = str2;
        while (i < 1 && r1 == null) {
            try {
                a = C0456l.m1969a(context, b);
                try {
                    httpPost = new HttpPost(stringBuffer2.toString());
                    try {
                        stringBuffer3.delete(0, stringBuffer3.length());
                        stringBuffer3.append("application/soap+xml;charset=");
                        stringBuffer3.append("UTF-8");
                        stringBuffer3.delete(0, stringBuffer3.length());
                        httpPost.addHeader("gzipped", "1");
                        HttpEntity byteArrayEntity = new ByteArrayEntity(C0470t.m2013a(bArr));
                        byteArrayEntity.setContentType("application/octet-stream");
                        httpPost.setEntity(byteArrayEntity);
                        HttpResponse execute = a.execute(httpPost);
                        if (execute.getStatusLine().getStatusCode() == 200) {
                            BufferedReader bufferedReader4;
                            InputStream content = execute.getEntity().getContent();
                            try {
                                inputStreamReader = new InputStreamReader(content, "UTF-8");
                            } catch (UnknownHostException e) {
                                httpPost2 = httpPost;
                                str = str3;
                                bufferedReader = bufferedReader3;
                                inputStream = content;
                                httpClient = a;
                                if (httpPost2 != null) {
                                    httpPost2.abort();
                                    httpPost2 = null;
                                }
                                if (httpClient != null) {
                                    httpClient.getConnectionManager().shutdown();
                                    httpClient = null;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (SocketException e2) {
                                str = str3;
                                bufferedReader = bufferedReader3;
                                inputStream = content;
                                if (httpPost == null) {
                                    httpPost.abort();
                                    httpPost2 = null;
                                } else {
                                    httpPost2 = httpPost;
                                }
                                if (a == null) {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                } else {
                                    httpClient = a;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (SocketTimeoutException e3) {
                                str = str3;
                                bufferedReader = bufferedReader3;
                                inputStream = content;
                                if (httpPost == null) {
                                    httpPost.abort();
                                    httpPost2 = null;
                                } else {
                                    httpPost2 = httpPost;
                                }
                                if (a == null) {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                } else {
                                    httpClient = a;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (ConnectTimeoutException e4) {
                                str = str3;
                                bufferedReader = bufferedReader3;
                                inputStream = content;
                                if (httpPost == null) {
                                    httpPost.abort();
                                    httpPost2 = null;
                                } else {
                                    httpPost2 = httpPost;
                                }
                                if (a == null) {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                } else {
                                    httpClient = a;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                inputStream2 = content;
                            }
                            try {
                                bufferedReader4 = new BufferedReader(inputStreamReader, 2048);
                            } catch (UnknownHostException e5) {
                                reader = inputStreamReader;
                                str = str3;
                                httpPost2 = httpPost;
                                bufferedReader = bufferedReader3;
                                inputStream = content;
                                httpClient = a;
                                if (httpPost2 != null) {
                                    httpPost2.abort();
                                    httpPost2 = null;
                                }
                                if (httpClient != null) {
                                    httpClient.getConnectionManager().shutdown();
                                    httpClient = null;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (SocketException e6) {
                                reader = inputStreamReader;
                                str = str3;
                                bufferedReader = bufferedReader3;
                                inputStream = content;
                                if (httpPost == null) {
                                    httpPost2 = httpPost;
                                } else {
                                    httpPost.abort();
                                    httpPost2 = null;
                                }
                                if (a == null) {
                                    httpClient = a;
                                } else {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (SocketTimeoutException e7) {
                                reader = inputStreamReader;
                                str = str3;
                                bufferedReader = bufferedReader3;
                                inputStream = content;
                                if (httpPost == null) {
                                    httpPost2 = httpPost;
                                } else {
                                    httpPost.abort();
                                    httpPost2 = null;
                                }
                                if (a == null) {
                                    httpClient = a;
                                } else {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (ConnectTimeoutException e8) {
                                reader = inputStreamReader;
                                str = str3;
                                bufferedReader = bufferedReader3;
                                inputStream = content;
                                if (httpPost == null) {
                                    httpPost2 = httpPost;
                                } else {
                                    httpPost.abort();
                                    httpPost2 = null;
                                }
                                if (a == null) {
                                    httpClient = a;
                                } else {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                reader = inputStreamReader;
                                inputStream2 = content;
                            }
                            try {
                                String str4 = "";
                                while (true) {
                                    str4 = bufferedReader4.readLine();
                                    if (str4 == null) {
                                        break;
                                    }
                                    stringBuffer3.append(str4);
                                }
                                str3 = stringBuffer3.toString();
                                stringBuffer3.delete(0, stringBuffer3.length());
                                stringBuffer3 = null;
                                obj = 1;
                                inputStream = content;
                                str = str3;
                                bufferedReader = bufferedReader4;
                                inputStreamReader2 = inputStreamReader;
                            } catch (UnknownHostException e9) {
                                inputStream = content;
                                str = str3;
                                bufferedReader = bufferedReader4;
                                httpClient = a;
                                inputStreamReader2 = inputStreamReader;
                                httpPost2 = httpPost;
                                if (httpPost2 != null) {
                                    httpPost2.abort();
                                    httpPost2 = null;
                                }
                                if (httpClient != null) {
                                    httpClient.getConnectionManager().shutdown();
                                    httpClient = null;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (SocketException e10) {
                                inputStream = content;
                                str = str3;
                                bufferedReader = bufferedReader4;
                                inputStreamReader2 = inputStreamReader;
                                if (httpPost == null) {
                                    httpPost.abort();
                                    httpPost2 = null;
                                } else {
                                    httpPost2 = httpPost;
                                }
                                if (a == null) {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                } else {
                                    httpClient = a;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (SocketTimeoutException e11) {
                                inputStream = content;
                                str = str3;
                                bufferedReader = bufferedReader4;
                                inputStreamReader2 = inputStreamReader;
                                if (httpPost == null) {
                                    httpPost.abort();
                                    httpPost2 = null;
                                } else {
                                    httpPost2 = httpPost;
                                }
                                if (a == null) {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                } else {
                                    httpClient = a;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (ConnectTimeoutException e12) {
                                inputStream = content;
                                str = str3;
                                bufferedReader = bufferedReader4;
                                inputStreamReader2 = inputStreamReader;
                                if (httpPost == null) {
                                    httpPost.abort();
                                    httpPost2 = null;
                                } else {
                                    httpPost2 = httpPost;
                                }
                                if (a == null) {
                                    a.getConnectionManager().shutdown();
                                    httpClient = null;
                                } else {
                                    httpClient = a;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                    inputStream = null;
                                }
                                if (inputStreamReader2 != null) {
                                    inputStreamReader2.close();
                                    inputStreamReader2 = null;
                                }
                                if (bufferedReader == null) {
                                    bufferedReader.close();
                                    bufferedReader = null;
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                } else {
                                    i++;
                                    bufferedReader2 = bufferedReader;
                                    str3 = str;
                                    httpClient2 = httpClient;
                                    httpPost3 = httpPost2;
                                    inputStream2 = inputStream;
                                    bufferedReader3 = bufferedReader2;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                bufferedReader3 = bufferedReader4;
                                inputStreamReader2 = inputStreamReader;
                                inputStream2 = content;
                            }
                        } else {
                            str = str3;
                            bufferedReader = bufferedReader3;
                            inputStream = inputStream2;
                        }
                        if (httpPost != null) {
                            httpPost.abort();
                            httpPost2 = null;
                        } else {
                            httpPost2 = httpPost;
                        }
                        if (a != null) {
                            a.getConnectionManager().shutdown();
                            httpClient = null;
                        } else {
                            httpClient = a;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }
                        if (inputStreamReader2 != null) {
                            inputStreamReader2.close();
                            inputStreamReader2 = null;
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                            bufferedReader = null;
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        } else {
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        }
                    } catch (UnknownHostException e13) {
                        httpClient = a;
                        str = str3;
                        bufferedReader = bufferedReader3;
                        inputStream = inputStream2;
                        httpPost2 = httpPost;
                        if (httpPost2 != null) {
                            httpPost2.abort();
                            httpPost2 = null;
                        }
                        if (httpClient != null) {
                            httpClient.getConnectionManager().shutdown();
                            httpClient = null;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }
                        if (inputStreamReader2 != null) {
                            inputStreamReader2.close();
                            inputStreamReader2 = null;
                        }
                        if (bufferedReader == null) {
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        } else {
                            bufferedReader.close();
                            bufferedReader = null;
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        }
                    } catch (SocketException e14) {
                        str = str3;
                        bufferedReader = bufferedReader3;
                        inputStream = inputStream2;
                        if (httpPost == null) {
                            httpPost2 = httpPost;
                        } else {
                            httpPost.abort();
                            httpPost2 = null;
                        }
                        if (a == null) {
                            httpClient = a;
                        } else {
                            a.getConnectionManager().shutdown();
                            httpClient = null;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }
                        if (inputStreamReader2 != null) {
                            inputStreamReader2.close();
                            inputStreamReader2 = null;
                        }
                        if (bufferedReader == null) {
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        } else {
                            bufferedReader.close();
                            bufferedReader = null;
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        }
                    } catch (SocketTimeoutException e15) {
                        str = str3;
                        bufferedReader = bufferedReader3;
                        inputStream = inputStream2;
                        if (httpPost == null) {
                            httpPost2 = httpPost;
                        } else {
                            httpPost.abort();
                            httpPost2 = null;
                        }
                        if (a == null) {
                            httpClient = a;
                        } else {
                            a.getConnectionManager().shutdown();
                            httpClient = null;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }
                        if (inputStreamReader2 != null) {
                            inputStreamReader2.close();
                            inputStreamReader2 = null;
                        }
                        if (bufferedReader == null) {
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        } else {
                            bufferedReader.close();
                            bufferedReader = null;
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        }
                    } catch (ConnectTimeoutException e16) {
                        str = str3;
                        bufferedReader = bufferedReader3;
                        inputStream = inputStream2;
                        if (httpPost == null) {
                            httpPost2 = httpPost;
                        } else {
                            httpPost.abort();
                            httpPost2 = null;
                        }
                        if (a == null) {
                            httpClient = a;
                        } else {
                            a.getConnectionManager().shutdown();
                            httpClient = null;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }
                        if (inputStreamReader2 != null) {
                            inputStreamReader2.close();
                            inputStreamReader2 = null;
                        }
                        if (bufferedReader == null) {
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        } else {
                            bufferedReader.close();
                            bufferedReader = null;
                            i++;
                            bufferedReader2 = bufferedReader;
                            str3 = str;
                            httpClient2 = httpClient;
                            httpPost3 = httpPost2;
                            inputStream2 = inputStream;
                            bufferedReader3 = bufferedReader2;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                    }
                } catch (UnknownHostException e17) {
                    str = str3;
                    bufferedReader = bufferedReader3;
                    inputStream = inputStream2;
                    httpPost2 = httpPost3;
                    httpClient = a;
                    if (httpPost2 != null) {
                        httpPost2.abort();
                        httpPost2 = null;
                    }
                    if (httpClient != null) {
                        httpClient.getConnectionManager().shutdown();
                        httpClient = null;
                    }
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    if (inputStreamReader2 != null) {
                        inputStreamReader2.close();
                        inputStreamReader2 = null;
                    }
                    if (bufferedReader == null) {
                        bufferedReader.close();
                        bufferedReader = null;
                        i++;
                        bufferedReader2 = bufferedReader;
                        str3 = str;
                        httpClient2 = httpClient;
                        httpPost3 = httpPost2;
                        inputStream2 = inputStream;
                        bufferedReader3 = bufferedReader2;
                    } else {
                        i++;
                        bufferedReader2 = bufferedReader;
                        str3 = str;
                        httpClient2 = httpClient;
                        httpPost3 = httpPost2;
                        inputStream2 = inputStream;
                        bufferedReader3 = bufferedReader2;
                    }
                } catch (SocketException e18) {
                    httpPost = httpPost3;
                    str = str3;
                    bufferedReader = bufferedReader3;
                    inputStream = inputStream2;
                    if (httpPost == null) {
                        httpPost.abort();
                        httpPost2 = null;
                    } else {
                        httpPost2 = httpPost;
                    }
                    if (a == null) {
                        a.getConnectionManager().shutdown();
                        httpClient = null;
                    } else {
                        httpClient = a;
                    }
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    if (inputStreamReader2 != null) {
                        inputStreamReader2.close();
                        inputStreamReader2 = null;
                    }
                    if (bufferedReader == null) {
                        bufferedReader.close();
                        bufferedReader = null;
                        i++;
                        bufferedReader2 = bufferedReader;
                        str3 = str;
                        httpClient2 = httpClient;
                        httpPost3 = httpPost2;
                        inputStream2 = inputStream;
                        bufferedReader3 = bufferedReader2;
                    } else {
                        i++;
                        bufferedReader2 = bufferedReader;
                        str3 = str;
                        httpClient2 = httpClient;
                        httpPost3 = httpPost2;
                        inputStream2 = inputStream;
                        bufferedReader3 = bufferedReader2;
                    }
                } catch (SocketTimeoutException e19) {
                    httpPost = httpPost3;
                    str = str3;
                    bufferedReader = bufferedReader3;
                    inputStream = inputStream2;
                    if (httpPost == null) {
                        httpPost.abort();
                        httpPost2 = null;
                    } else {
                        httpPost2 = httpPost;
                    }
                    if (a == null) {
                        a.getConnectionManager().shutdown();
                        httpClient = null;
                    } else {
                        httpClient = a;
                    }
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    if (inputStreamReader2 != null) {
                        inputStreamReader2.close();
                        inputStreamReader2 = null;
                    }
                    if (bufferedReader == null) {
                        bufferedReader.close();
                        bufferedReader = null;
                        i++;
                        bufferedReader2 = bufferedReader;
                        str3 = str;
                        httpClient2 = httpClient;
                        httpPost3 = httpPost2;
                        inputStream2 = inputStream;
                        bufferedReader3 = bufferedReader2;
                    } else {
                        i++;
                        bufferedReader2 = bufferedReader;
                        str3 = str;
                        httpClient2 = httpClient;
                        httpPost3 = httpPost2;
                        inputStream2 = inputStream;
                        bufferedReader3 = bufferedReader2;
                    }
                } catch (ConnectTimeoutException e20) {
                    httpPost = httpPost3;
                    str = str3;
                    bufferedReader = bufferedReader3;
                    inputStream = inputStream2;
                    if (httpPost == null) {
                        httpPost.abort();
                        httpPost2 = null;
                    } else {
                        httpPost2 = httpPost;
                    }
                    if (a == null) {
                        a.getConnectionManager().shutdown();
                        httpClient = null;
                    } else {
                        httpClient = a;
                    }
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    if (inputStreamReader2 != null) {
                        inputStreamReader2.close();
                        inputStreamReader2 = null;
                    }
                    if (bufferedReader == null) {
                        bufferedReader.close();
                        bufferedReader = null;
                        i++;
                        bufferedReader2 = bufferedReader;
                        str3 = str;
                        httpClient2 = httpClient;
                        httpPost3 = httpPost2;
                        inputStream2 = inputStream;
                        bufferedReader3 = bufferedReader2;
                    } else {
                        i++;
                        bufferedReader2 = bufferedReader;
                        str3 = str;
                        httpClient2 = httpClient;
                        httpPost3 = httpPost2;
                        inputStream2 = inputStream;
                        bufferedReader3 = bufferedReader2;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    httpPost = httpPost3;
                }
            } catch (UnknownHostException e21) {
                bufferedReader2 = bufferedReader3;
                inputStream = inputStream2;
                httpPost2 = httpPost3;
                httpClient = httpClient2;
                str = str3;
                bufferedReader = bufferedReader2;
                if (httpPost2 != null) {
                    httpPost2.abort();
                    httpPost2 = null;
                }
                if (httpClient != null) {
                    httpClient.getConnectionManager().shutdown();
                    httpClient = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                if (inputStreamReader2 != null) {
                    inputStreamReader2.close();
                    inputStreamReader2 = null;
                }
                if (bufferedReader == null) {
                    i++;
                    bufferedReader2 = bufferedReader;
                    str3 = str;
                    httpClient2 = httpClient;
                    httpPost3 = httpPost2;
                    inputStream2 = inputStream;
                    bufferedReader3 = bufferedReader2;
                } else {
                    bufferedReader.close();
                    bufferedReader = null;
                    i++;
                    bufferedReader2 = bufferedReader;
                    str3 = str;
                    httpClient2 = httpClient;
                    httpPost3 = httpPost2;
                    inputStream2 = inputStream;
                    bufferedReader3 = bufferedReader2;
                }
            } catch (SocketException e22) {
                httpPost = httpPost3;
                a = httpClient2;
                str = str3;
                bufferedReader = bufferedReader3;
                inputStream = inputStream2;
                if (httpPost == null) {
                    httpPost2 = httpPost;
                } else {
                    httpPost.abort();
                    httpPost2 = null;
                }
                if (a == null) {
                    httpClient = a;
                } else {
                    a.getConnectionManager().shutdown();
                    httpClient = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                if (inputStreamReader2 != null) {
                    inputStreamReader2.close();
                    inputStreamReader2 = null;
                }
                if (bufferedReader == null) {
                    i++;
                    bufferedReader2 = bufferedReader;
                    str3 = str;
                    httpClient2 = httpClient;
                    httpPost3 = httpPost2;
                    inputStream2 = inputStream;
                    bufferedReader3 = bufferedReader2;
                } else {
                    bufferedReader.close();
                    bufferedReader = null;
                    i++;
                    bufferedReader2 = bufferedReader;
                    str3 = str;
                    httpClient2 = httpClient;
                    httpPost3 = httpPost2;
                    inputStream2 = inputStream;
                    bufferedReader3 = bufferedReader2;
                }
            } catch (SocketTimeoutException e23) {
                httpPost = httpPost3;
                a = httpClient2;
                str = str3;
                bufferedReader = bufferedReader3;
                inputStream = inputStream2;
                if (httpPost == null) {
                    httpPost2 = httpPost;
                } else {
                    httpPost.abort();
                    httpPost2 = null;
                }
                if (a == null) {
                    httpClient = a;
                } else {
                    a.getConnectionManager().shutdown();
                    httpClient = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                if (inputStreamReader2 != null) {
                    inputStreamReader2.close();
                    inputStreamReader2 = null;
                }
                if (bufferedReader == null) {
                    i++;
                    bufferedReader2 = bufferedReader;
                    str3 = str;
                    httpClient2 = httpClient;
                    httpPost3 = httpPost2;
                    inputStream2 = inputStream;
                    bufferedReader3 = bufferedReader2;
                } else {
                    bufferedReader.close();
                    bufferedReader = null;
                    i++;
                    bufferedReader2 = bufferedReader;
                    str3 = str;
                    httpClient2 = httpClient;
                    httpPost3 = httpPost2;
                    inputStream2 = inputStream;
                    bufferedReader3 = bufferedReader2;
                }
            } catch (ConnectTimeoutException e24) {
                httpPost = httpPost3;
                a = httpClient2;
                str = str3;
                bufferedReader = bufferedReader3;
                inputStream = inputStream2;
                if (httpPost == null) {
                    httpPost2 = httpPost;
                } else {
                    httpPost.abort();
                    httpPost2 = null;
                }
                if (a == null) {
                    httpClient = a;
                } else {
                    a.getConnectionManager().shutdown();
                    httpClient = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                if (inputStreamReader2 != null) {
                    inputStreamReader2.close();
                    inputStreamReader2 = null;
                }
                if (bufferedReader == null) {
                    i++;
                    bufferedReader2 = bufferedReader;
                    str3 = str;
                    httpClient2 = httpClient;
                    httpPost3 = httpPost2;
                    inputStream2 = inputStream;
                    bufferedReader3 = bufferedReader2;
                } else {
                    bufferedReader.close();
                    bufferedReader = null;
                    i++;
                    bufferedReader2 = bufferedReader;
                    str3 = str;
                    httpClient2 = httpClient;
                    httpPost3 = httpPost2;
                    inputStream2 = inputStream;
                    bufferedReader3 = bufferedReader2;
                }
            } catch (Throwable th7) {
                th = th7;
                httpPost = httpPost3;
                a = httpClient2;
            }
        }
        stringBuffer2.delete(0, stringBuffer2.length());
        if (str3.equals("")) {
            return null;
        }
        return str3;
        if (httpPost != null) {
            httpPost.abort();
        }
        if (a != null) {
            a.getConnectionManager().shutdown();
        }
        if (inputStream2 != null) {
            inputStream2.close();
        }
        if (inputStreamReader2 != null) {
            inputStreamReader2.close();
        }
        if (bufferedReader3 != null) {
            bufferedReader3.close();
        }
        throw th;
    }
}
