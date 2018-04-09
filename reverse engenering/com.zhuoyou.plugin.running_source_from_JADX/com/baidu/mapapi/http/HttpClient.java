package com.baidu.mapapi.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.baidu.platform.comapi.util.C0671f;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    HttpURLConnection f965a;
    private Context f966b;
    private String f967c = null;
    private String f968d = null;
    private int f969e;
    private int f970f;
    private String f971g;
    private ProtoResultCallback f972h;

    public enum HttpStateError {
        NETWORK_ERROR,
        INNER_ERROR
    }

    public static abstract class ProtoResultCallback {
        public abstract void onFailed(HttpStateError httpStateError);

        public abstract void onSuccess(String str);
    }

    public HttpClient(Context context, String str, ProtoResultCallback protoResultCallback) {
        this.f966b = context;
        this.f971g = str;
        this.f972h = protoResultCallback;
    }

    private HttpURLConnection m1056a() {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.f967c).openConnection();
            httpURLConnection.setRequestMethod(this.f971g);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(this.f969e);
            httpURLConnection.setReadTimeout(this.f970f);
            return httpURLConnection;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAuthToken() {
        return C0671f.f2194A;
    }

    public static String getPhoneInfo() {
        return C0671f.m2172c();
    }

    protected boolean checkNetwork() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.f966b.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void request(String str) {
        InputStream inputStream;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        Throwable th;
        Throwable th2;
        InputStream inputStream2 = null;
        this.f967c = str;
        if (checkNetwork()) {
            this.f965a = m1056a();
            if (this.f965a == null) {
                this.f972h.onFailed(HttpStateError.INNER_ERROR);
            } else if (TextUtils.isEmpty(this.f967c)) {
                this.f972h.onFailed(HttpStateError.INNER_ERROR);
            } else {
                try {
                    this.f965a.connect();
                    try {
                        inputStream = this.f965a.getInputStream();
                        try {
                            if (200 == this.f965a.getResponseCode()) {
                                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                                try {
                                    StringBuffer stringBuffer = new StringBuffer();
                                    while (true) {
                                        int read = bufferedReader.read();
                                        if (read == -1) {
                                            break;
                                        }
                                        stringBuffer.append((char) read);
                                    }
                                    this.f968d = stringBuffer.toString();
                                } catch (Exception e) {
                                    bufferedReader2 = bufferedReader;
                                    try {
                                        this.f972h.onFailed(HttpStateError.INNER_ERROR);
                                        bufferedReader2.close();
                                        inputStream.close();
                                        if (this.f965a == null) {
                                            this.f965a.disconnect();
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        bufferedReader = bufferedReader2;
                                        inputStream2 = inputStream;
                                        th2 = th;
                                        bufferedReader.close();
                                        inputStream2.close();
                                        if (this.f965a != null) {
                                            this.f965a.disconnect();
                                        }
                                        throw th2;
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    inputStream2 = inputStream;
                                    th2 = th;
                                    bufferedReader.close();
                                    inputStream2.close();
                                    if (this.f965a != null) {
                                        this.f965a.disconnect();
                                    }
                                    throw th2;
                                }
                            }
                            bufferedReader = null;
                            if (!(inputStream == null || bufferedReader == null)) {
                                bufferedReader.close();
                                inputStream.close();
                            }
                            if (this.f965a != null) {
                                this.f965a.disconnect();
                            }
                            this.f972h.onSuccess(this.f968d);
                        } catch (Exception e2) {
                            this.f972h.onFailed(HttpStateError.INNER_ERROR);
                            bufferedReader2.close();
                            inputStream.close();
                            if (this.f965a == null) {
                                this.f965a.disconnect();
                            }
                        } catch (Throwable th32) {
                            th = th32;
                            bufferedReader = null;
                            inputStream2 = inputStream;
                            th2 = th;
                            bufferedReader.close();
                            inputStream2.close();
                            if (this.f965a != null) {
                                this.f965a.disconnect();
                            }
                            throw th2;
                        }
                    } catch (Exception e3) {
                        inputStream = null;
                        this.f972h.onFailed(HttpStateError.INNER_ERROR);
                        if (!(inputStream == null || bufferedReader2 == null)) {
                            bufferedReader2.close();
                            inputStream.close();
                        }
                        if (this.f965a == null) {
                            this.f965a.disconnect();
                        }
                    } catch (Throwable th5) {
                        th2 = th5;
                        bufferedReader = null;
                        if (!(inputStream2 == null || bufferedReader == null)) {
                            bufferedReader.close();
                            inputStream2.close();
                        }
                        if (this.f965a != null) {
                            this.f965a.disconnect();
                        }
                        throw th2;
                    }
                } catch (Exception e4) {
                }
            }
        } else {
            this.f972h.onFailed(HttpStateError.NETWORK_ERROR);
        }
    }

    public void setMaxTimeOut(int i) {
        this.f969e = i;
    }

    public void setReadTimeOut(int i) {
        this.f970f = i;
    }
}
