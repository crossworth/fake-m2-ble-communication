package com.tencent.open.p019a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.tencent.connect.common.Constants;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.OpenConfig;
import com.tencent.utils.ServerSetting;
import com.tencent.utils.Util;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Random;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;

/* compiled from: ProGuard */
public class C0799b {
    private static C0799b f2722a = null;
    private long f2723b = 0;
    private int f2724c = 3;
    private boolean f2725d = false;
    private Random f2726e = new Random();
    private C0802d f2727f;
    private ArrayList<C0797a> f2728g = new ArrayList();
    private ArrayList<C0797a> f2729h = new ArrayList();

    public static C0799b m2544a() {
        if (f2722a == null) {
            f2722a = new C0799b();
        }
        return f2722a;
    }

    public void m2556a(Context context, String str, long j, long j2, long j3, int i, String str2) {
        m2557a(context, str, j, j2, j3, i, str2, "", null);
    }

    public void m2557a(Context context, String str, long j, long j2, long j3, int i, String str2, String str3, String str4) {
        if (str4 == null) {
            str4 = "1000067";
        }
        if (this.f2727f == null) {
            this.f2727f = new C0802d(context);
        }
        if (m2549a(context, i)) {
            m2547a(context, str, j, j2, j3, i, str2, str3);
            if (!this.f2725d) {
                if (m2553b(context)) {
                    m2546a(context, str4);
                } else if (m2555c(context)) {
                    m2546a(context, str4);
                }
            }
        }
    }

    private boolean m2549a(Context context, int i) {
        if (this.f2726e.nextInt(100) < m2551b(context, i)) {
            Log.i("cgi_report_debug", "ReportManager availableForFrequency = ture");
            return true;
        }
        Log.i("cgi_report_debug", "ReportManager availableForFrequency = false");
        return false;
    }

    private void m2547a(Context context, String str, long j, long j2, long j3, int i, String str2, String str3) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - j;
        Log.i("cgi_report_debug", "ReportManager updateDB url=" + str + ",resultCode=" + i + ",timeCost=" + elapsedRealtime + ",reqSize=" + j2 + ",rspSize=" + j3);
        int b = 100 / m2551b(context, i);
        if (b <= 0) {
            b = 1;
        } else if (b > 100) {
            b = 100;
        }
        this.f2727f.m2563a(m2545a(context), b + "", str, i, elapsedRealtime, j2, j3, str3);
    }

    private int m2551b(Context context, int i) {
        int i2;
        if (i == 0) {
            i2 = OpenConfig.getInstance(context, null).getInt("Common_CGIReportFrequencySuccess");
            Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencySuccess     config_value:" + i2);
            if (i2 == 0) {
                i2 = 10;
            }
            Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencySuccess     result_value:" + i2);
        } else {
            i2 = OpenConfig.getInstance(context, null).getInt("Common_CGIReportFrequencyFailed");
            Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencyFailed     config_value:" + i2);
            if (i2 == 0) {
                i2 = 100;
            }
            Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencyFailed     result_value:" + i2);
        }
        return i2;
    }

    private String m2545a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                Log.e("cgi_report_debug", "ReportManager getAPN failed:ConnectivityManager == null");
                return "no_net";
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                Log.e("cgi_report_debug", "ReportManager getAPN failed:NetworkInfo == null");
                return "no_net";
            } else if (activeNetworkInfo.getTypeName().toUpperCase().equals("WIFI")) {
                Log.i("cgi_report_debug", "ReportManager getAPN type = wifi");
                return "wifi";
            } else {
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (extraInfo == null) {
                    Log.e("cgi_report_debug", "ReportManager getAPN failed:extraInfo == null");
                    return "mobile_unknow";
                }
                extraInfo = extraInfo.toLowerCase();
                Log.i("cgi_report_debug", "ReportManager getAPN type = " + extraInfo);
                return extraInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "unknow";
        }
    }

    private boolean m2553b(Context context) {
        long j = OpenConfig.getInstance(context, null).getLong("Common_CGIReportTimeinterval");
        Log.d("OpenConfig_test", "config 5:Common_CGIReportTimeinterval     config_value:" + j);
        if (j == 0) {
            j = 1200;
        }
        Log.d("OpenConfig_test", "config 5:Common_CGIReportTimeinterval     result_value:" + j);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        if (this.f2723b == 0 || j + this.f2723b <= currentTimeMillis) {
            this.f2723b = currentTimeMillis;
            Log.i("cgi_report_debug", "ReportManager availableForTime = ture");
            return true;
        }
        Log.i("cgi_report_debug", "ReportManager availableForTime = false");
        return false;
    }

    private boolean m2555c(Context context) {
        int i = OpenConfig.getInstance(context, null).getInt("Common_CGIReportMaxcount");
        Log.d("OpenConfig_test", "config 6:Common_CGIReportMaxcount     config_value:" + i);
        if (i == 0) {
            i = 20;
        }
        Log.d("OpenConfig_test", "config 6:Common_CGIReportMaxcount     result_value:" + i);
        if (this.f2727f.m2567e() >= i) {
            Log.i("cgi_report_debug", "ReportManager availableForCount = ture");
            return true;
        }
        Log.i("cgi_report_debug", "ReportManager availableForCount = false");
        return false;
    }

    private void m2546a(Context context, String str) {
        int i;
        Log.i("cgi_report_debug", "ReportManager doUpload start");
        this.f2725d = true;
        this.f2728g = this.f2727f.m2565c();
        this.f2727f.m2564b();
        this.f2729h = this.f2727f.m2566d();
        this.f2727f.m2562a();
        Bundle bundle = new Bundle();
        bundle.putString("appid", str);
        bundle.putString("releaseversion", "QQConnect_SDK_Android_1_7");
        bundle.putString("device", Build.DEVICE);
        bundle.putString("qua", Constants.SDK_QUA);
        bundle.putString("key", "apn,frequency,commandid,resultcode,tmcost,reqsize,rspsize,detail,deviceinfo");
        for (i = 0; i < this.f2728g.size(); i++) {
            bundle.putString(i + "_1", ((C0797a) this.f2728g.get(i)).m2533a());
            bundle.putString(i + "_2", ((C0797a) this.f2728g.get(i)).m2534b());
            bundle.putString(i + "_3", ((C0797a) this.f2728g.get(i)).m2535c());
            bundle.putString(i + "_4", ((C0797a) this.f2728g.get(i)).m2536d());
            bundle.putString(i + "_5", ((C0797a) this.f2728g.get(i)).m2537e());
            bundle.putString(i + "_6", ((C0797a) this.f2728g.get(i)).m2538f());
            bundle.putString(i + "_7", ((C0797a) this.f2728g.get(i)).m2539g());
            bundle.putString(i + "_8", ((C0797a) this.f2728g.get(i)).m2540h());
            bundle.putString(i + "_9", C0800c.m2559b(context) + ((C0797a) this.f2728g.get(i)).m2541i());
        }
        for (i = this.f2728g.size(); i < this.f2729h.size() + this.f2728g.size(); i++) {
            int size = i - this.f2728g.size();
            bundle.putString(i + "_1", ((C0797a) this.f2729h.get(size)).m2533a());
            bundle.putString(i + "_2", ((C0797a) this.f2729h.get(size)).m2534b());
            bundle.putString(i + "_3", ((C0797a) this.f2729h.get(size)).m2535c());
            bundle.putString(i + "_4", ((C0797a) this.f2729h.get(size)).m2536d());
            bundle.putString(i + "_5", ((C0797a) this.f2729h.get(size)).m2537e());
            bundle.putString(i + "_6", ((C0797a) this.f2729h.get(size)).m2538f());
            bundle.putString(i + "_7", ((C0797a) this.f2729h.get(size)).m2539g());
            bundle.putString(i + "_8", ((C0797a) this.f2729h.get(size)).m2540h());
            bundle.putString(i + "_9", C0800c.m2559b(context) + ((C0797a) this.f2729h.get(size)).m2541i());
        }
        m2548a(context, ServerSetting.DEFAULT_URL_REPORT, "POST", bundle);
    }

    private void m2548a(final Context context, final String str, String str2, final Bundle bundle) {
        new Thread(this) {
            final /* synthetic */ C0799b f2721d;

            public void run() {
                int i;
                ConnectTimeoutException connectTimeoutException;
                SocketTimeoutException socketTimeoutException;
                Exception exception;
                Log.i("cgi_report_debug", "ReportManager doUploadItems Thread start, url = " + str);
                this.f2721d.f2724c = OpenConfig.getInstance(context, null).getInt("Common_HttpRetryCount");
                C0799b c0799b = this.f2721d;
                if (this.f2721d.f2724c == 0) {
                    i = 3;
                } else {
                    i = this.f2721d.f2724c;
                }
                c0799b.f2724c = i;
                boolean z = false;
                int i2 = 0;
                do {
                    i2++;
                    Log.i("cgi_report_debug", "ReportManager doUploadItems Thread request count = " + i2);
                    try {
                        HttpClient httpClient = HttpUtils.getHttpClient(context, null, str);
                        HttpUriRequest httpPost = new HttpPost(str);
                        httpPost.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
                        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                        httpPost.setEntity(new ByteArrayEntity(Util.encodeUrl(bundle).getBytes()));
                        if (httpClient.execute(httpPost).getStatusLine().getStatusCode() != 200) {
                            Log.e("cgi_report_debug", "ReportManager doUploadItems : HttpStatuscode != 200");
                            break;
                        }
                        try {
                            Log.i("cgi_report_debug", "ReportManager doUploadItems Thread success");
                            z = true;
                            break;
                        } catch (ConnectTimeoutException e) {
                            connectTimeoutException = e;
                            z = true;
                        } catch (SocketTimeoutException e2) {
                            socketTimeoutException = e2;
                            z = true;
                            socketTimeoutException.printStackTrace();
                            if (i2 >= this.f2721d.f2724c) {
                                this.f2721d.f2725d = false;
                                Log.i("cgi_report_debug", "ReportManager doUploadItems Thread end, url = " + str);
                                if (z) {
                                    Log.e("cgi_report_debug", "ReportManager doUploadItems Thread request failed");
                                    this.f2721d.f2727f.m2561a(this.f2721d.f2728g);
                                }
                                Log.i("cgi_report_debug", "ReportManager doUploadItems Thread request success");
                                return;
                            }
                        } catch (Exception e3) {
                            exception = e3;
                            z = true;
                        }
                    } catch (ConnectTimeoutException e4) {
                        connectTimeoutException = e4;
                        connectTimeoutException.printStackTrace();
                        Log.e("cgi_report_debug", "ReportManager doUploadItems : ConnectTimeoutException");
                        if (i2 >= this.f2721d.f2724c) {
                            this.f2721d.f2725d = false;
                            Log.i("cgi_report_debug", "ReportManager doUploadItems Thread end, url = " + str);
                            if (z) {
                                Log.i("cgi_report_debug", "ReportManager doUploadItems Thread request success");
                                return;
                            }
                            Log.e("cgi_report_debug", "ReportManager doUploadItems Thread request failed");
                            this.f2721d.f2727f.m2561a(this.f2721d.f2728g);
                        }
                    } catch (SocketTimeoutException e5) {
                        socketTimeoutException = e5;
                        socketTimeoutException.printStackTrace();
                        if (i2 >= this.f2721d.f2724c) {
                            this.f2721d.f2725d = false;
                            Log.i("cgi_report_debug", "ReportManager doUploadItems Thread end, url = " + str);
                            if (z) {
                                Log.e("cgi_report_debug", "ReportManager doUploadItems Thread request failed");
                                this.f2721d.f2727f.m2561a(this.f2721d.f2728g);
                            }
                            Log.i("cgi_report_debug", "ReportManager doUploadItems Thread request success");
                            return;
                        }
                    } catch (Exception e6) {
                        exception = e6;
                    }
                } while (i2 >= this.f2721d.f2724c);
                this.f2721d.f2725d = false;
                Log.i("cgi_report_debug", "ReportManager doUploadItems Thread end, url = " + str);
                if (z) {
                    Log.i("cgi_report_debug", "ReportManager doUploadItems Thread request success");
                    return;
                }
                Log.e("cgi_report_debug", "ReportManager doUploadItems Thread request failed");
                this.f2721d.f2727f.m2561a(this.f2721d.f2728g);
                exception.printStackTrace();
                Log.e("cgi_report_debug", "ReportManager doUploadItems : Exception");
                this.f2721d.f2725d = false;
                Log.i("cgi_report_debug", "ReportManager doUploadItems Thread end, url = " + str);
                if (z) {
                    Log.i("cgi_report_debug", "ReportManager doUploadItems Thread request success");
                    return;
                }
                Log.e("cgi_report_debug", "ReportManager doUploadItems Thread request failed");
                this.f2721d.f2727f.m2561a(this.f2721d.f2728g);
            }
        }.start();
    }
}
