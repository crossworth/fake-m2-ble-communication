package com.tencent.open.p037b;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.OpenConfig;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.ThreadManager;
import com.tencent.open.utils.Util;
import com.tyd.aidlservice.internal.Constants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class C1331g {
    protected static C1331g f4162a;
    protected Random f4163b = new Random();
    protected List<Serializable> f4164c = Collections.synchronizedList(new ArrayList());
    protected List<Serializable> f4165d = Collections.synchronizedList(new ArrayList());
    protected HandlerThread f4166e = null;
    protected Handler f4167f;
    protected Executor f4168g = ThreadManager.newSerialExecutor();
    protected Executor f4169h = ThreadManager.newSerialExecutor();

    /* compiled from: ProGuard */
    class C13284 implements Runnable {
        final /* synthetic */ C1331g f4155a;

        C13284(C1331g c1331g) {
            this.f4155a = c1331g;
        }

        public void run() {
            Object obj = null;
            Bundle c = this.f4155a.m3916c();
            if (c != null) {
                int i = OpenConfig.getInstance(Global.getContext(), null).getInt("Common_HttpRetryCount");
                int i2 = i == 0 ? 3 : i;
                C1314f.m3867b("openSDK_LOG.ReportManager", "-->doReportCgi, retryCount: " + i2);
                i = 0;
                do {
                    i++;
                    try {
                        HttpClient httpClient = HttpUtils.getHttpClient(Global.getContext(), null, ServerSetting.DEFAULT_URL_REPORT);
                        HttpUriRequest httpPost = new HttpPost(ServerSetting.DEFAULT_URL_REPORT);
                        httpPost.addHeader("Accept-Encoding", Constants.GZIP_TAG);
                        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                        httpPost.setEntity(new ByteArrayEntity(Util.getBytesUTF8(HttpUtils.encodeUrl(c))));
                        int statusCode = httpClient.execute(httpPost).getStatusLine().getStatusCode();
                        C1314f.m3867b("openSDK_LOG.ReportManager", "-->doReportCgi, statusCode: " + statusCode);
                        if (statusCode == 200) {
                            C1324f.m3903a().m3906b("report_cgi");
                            obj = 1;
                        }
                    } catch (Throwable e) {
                        try {
                            C1314f.m3868b("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception", e);
                            continue;
                        } catch (Throwable e2) {
                            C1314f.m3868b("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception out.", e2);
                            return;
                        }
                    } catch (Throwable e3) {
                        C1314f.m3868b("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception", e3);
                        continue;
                    } catch (Throwable e22) {
                        C1314f.m3868b("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception", e22);
                    }
                    if (obj == null) {
                        C1324f.m3903a().m3905a("report_cgi", this.f4155a.f4164c);
                    }
                    this.f4155a.f4164c.clear();
                } while (i < i2);
                if (obj == null) {
                    C1324f.m3903a().m3905a("report_cgi", this.f4155a.f4164c);
                }
                this.f4155a.f4164c.clear();
            }
        }
    }

    /* compiled from: ProGuard */
    class C13295 implements Runnable {
        final /* synthetic */ C1331g f4156a;

        C13295(C1331g c1331g) {
            this.f4156a = c1331g;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r18 = this;
            r0 = r18;
            r2 = r0.f4156a;	 Catch:{ Exception -> 0x00a3 }
            r14 = r2.m3917d();	 Catch:{ Exception -> 0x00a3 }
            if (r14 != 0) goto L_0x000b;
        L_0x000a:
            return;
        L_0x000b:
            r2 = "openSDK_LOG.ReportManager";
            r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a3 }
            r3.<init>();	 Catch:{ Exception -> 0x00a3 }
            r4 = "-->doReportVia, params: ";
            r3 = r3.append(r4);	 Catch:{ Exception -> 0x00a3 }
            r4 = r14.toString();	 Catch:{ Exception -> 0x00a3 }
            r3 = r3.append(r4);	 Catch:{ Exception -> 0x00a3 }
            r3 = r3.toString();	 Catch:{ Exception -> 0x00a3 }
            com.tencent.open.p036a.C1314f.m3864a(r2, r3);	 Catch:{ Exception -> 0x00a3 }
            r11 = com.tencent.open.p037b.C1323e.m3901a();	 Catch:{ Exception -> 0x00a3 }
            r10 = 0;
            r3 = 0;
            r8 = android.os.SystemClock.elapsedRealtime();	 Catch:{ Exception -> 0x00a3 }
            r6 = 0;
            r4 = 0;
            r2 = 0;
        L_0x0036:
            r10 = r10 + 1;
            r12 = com.tencent.open.utils.Global.getContext();	 Catch:{ ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, JSONException -> 0x00cb, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x00e5, IOException -> 0x0104, Exception -> 0x010f }
            r13 = "http://appsupport.qq.com/cgi-bin/appstage/mstats_batch_report";
            r15 = "POST";
            r15 = com.tencent.open.utils.HttpUtils.openUrl2(r12, r13, r15, r14);	 Catch:{ ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, JSONException -> 0x00cb, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x00e5, IOException -> 0x0104, Exception -> 0x010f }
            r12 = r15.response;	 Catch:{ ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, JSONException -> 0x00cb, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x00e5, IOException -> 0x0104, Exception -> 0x010f }
            r12 = com.tencent.open.utils.Util.parseJson(r12);	 Catch:{ ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, JSONException -> 0x00cb, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x00e5, IOException -> 0x0104, Exception -> 0x010f }
            r13 = "ret";
            r12 = r12.getInt(r13);	 Catch:{ JSONException -> 0x00ad, ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x00e5, IOException -> 0x0104, Exception -> 0x010f }
        L_0x0050:
            if (r12 == 0) goto L_0x005a;
        L_0x0052:
            r12 = r15.response;	 Catch:{ ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, JSONException -> 0x00cb, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x00e5, IOException -> 0x0104, Exception -> 0x010f }
            r12 = android.text.TextUtils.isEmpty(r12);	 Catch:{ ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, JSONException -> 0x00cb, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x00e5, IOException -> 0x0104, Exception -> 0x010f }
            if (r12 != 0) goto L_0x005c;
        L_0x005a:
            r3 = 1;
            r10 = r11;
        L_0x005c:
            r12 = r15.reqSize;	 Catch:{ ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, JSONException -> 0x00cb, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x00e5, IOException -> 0x0104, Exception -> 0x010f }
            r4 = r15.rspSize;	 Catch:{ ConnectTimeoutException -> 0x00b0, SocketTimeoutException -> 0x00c0, JSONException -> 0x00cb, NetworkUnavailableException -> 0x00d2, HttpStatusException -> 0x012b, IOException -> 0x0104, Exception -> 0x010f }
            r6 = r12;
        L_0x0061:
            if (r10 < r11) goto L_0x0036;
        L_0x0063:
            r10 = r2;
            r13 = r3;
            r16 = r8;
            r8 = r4;
            r4 = r16;
        L_0x006a:
            r0 = r18;
            r2 = r0.f4156a;	 Catch:{ Exception -> 0x00a3 }
            r3 = "mapp_apptrace_sdk";
            r11 = 0;
            r12 = 0;
            r2.m3911a(r3, r4, r6, r8, r10, r11, r12);	 Catch:{ Exception -> 0x00a3 }
            if (r13 == 0) goto L_0x0118;
        L_0x0077:
            r2 = com.tencent.open.p037b.C1324f.m3903a();	 Catch:{ Exception -> 0x00a3 }
            r3 = "report_via";
            r2.m3906b(r3);	 Catch:{ Exception -> 0x00a3 }
        L_0x0080:
            r0 = r18;
            r2 = r0.f4156a;	 Catch:{ Exception -> 0x00a3 }
            r2 = r2.f4165d;	 Catch:{ Exception -> 0x00a3 }
            r2.clear();	 Catch:{ Exception -> 0x00a3 }
            r2 = "openSDK_LOG.ReportManager";
            r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a3 }
            r3.<init>();	 Catch:{ Exception -> 0x00a3 }
            r4 = "-->doReportVia, uploadSuccess: ";
            r3 = r3.append(r4);	 Catch:{ Exception -> 0x00a3 }
            r3 = r3.append(r13);	 Catch:{ Exception -> 0x00a3 }
            r3 = r3.toString();	 Catch:{ Exception -> 0x00a3 }
            com.tencent.open.p036a.C1314f.m3867b(r2, r3);	 Catch:{ Exception -> 0x00a3 }
            goto L_0x000a;
        L_0x00a3:
            r2 = move-exception;
            r3 = "openSDK_LOG.ReportManager";
            r4 = "-->doReportVia, exception in serial executor.";
            com.tencent.open.p036a.C1314f.m3868b(r3, r4, r2);
            goto L_0x000a;
        L_0x00ad:
            r12 = move-exception;
            r12 = -4;
            goto L_0x0050;
        L_0x00b0:
            r2 = move-exception;
            r2 = r10;
            r8 = android.os.SystemClock.elapsedRealtime();	 Catch:{ Exception -> 0x00a3 }
            r12 = 0;
            r6 = 0;
            r4 = -7;
            r10 = r2;
            r2 = r4;
            r4 = r6;
            r6 = r12;
            goto L_0x0061;
        L_0x00c0:
            r2 = move-exception;
            r8 = android.os.SystemClock.elapsedRealtime();	 Catch:{ Exception -> 0x00a3 }
            r6 = 0;
            r4 = 0;
            r2 = -8;
            goto L_0x0061;
        L_0x00cb:
            r2 = move-exception;
            r6 = 0;
            r4 = 0;
            r2 = -4;
            goto L_0x0061;
        L_0x00d2:
            r2 = move-exception;
            r0 = r18;
            r2 = r0.f4156a;	 Catch:{ Exception -> 0x00a3 }
            r2 = r2.f4165d;	 Catch:{ Exception -> 0x00a3 }
            r2.clear();	 Catch:{ Exception -> 0x00a3 }
            r2 = "openSDK_LOG.ReportManager";
            r3 = "doReportVia, NetworkUnavailableException.";
            com.tencent.open.p036a.C1314f.m3867b(r2, r3);	 Catch:{ Exception -> 0x00a3 }
            goto L_0x000a;
        L_0x00e5:
            r10 = move-exception;
            r16 = r10;
            r10 = r3;
            r3 = r16;
        L_0x00eb:
            r3 = r3.getMessage();	 Catch:{ Exception -> 0x0129 }
            r11 = "http status code error:";
            r12 = "";
            r3 = r3.replace(r11, r12);	 Catch:{ Exception -> 0x0129 }
            r2 = java.lang.Integer.parseInt(r3);	 Catch:{ Exception -> 0x0129 }
        L_0x00fb:
            r13 = r10;
            r10 = r2;
            r16 = r8;
            r8 = r4;
            r4 = r16;
            goto L_0x006a;
        L_0x0104:
            r2 = move-exception;
            r6 = 0;
            r4 = 0;
            r2 = com.tencent.open.utils.HttpUtils.getErrorCodeFromException(r2);	 Catch:{ Exception -> 0x00a3 }
            goto L_0x0061;
        L_0x010f:
            r2 = move-exception;
            r6 = 0;
            r4 = 0;
            r2 = -6;
            r10 = r11;
            goto L_0x0061;
        L_0x0118:
            r2 = com.tencent.open.p037b.C1324f.m3903a();	 Catch:{ Exception -> 0x00a3 }
            r3 = "report_via";
            r0 = r18;
            r4 = r0.f4156a;	 Catch:{ Exception -> 0x00a3 }
            r4 = r4.f4165d;	 Catch:{ Exception -> 0x00a3 }
            r2.m3905a(r3, r4);	 Catch:{ Exception -> 0x00a3 }
            goto L_0x0080;
        L_0x0129:
            r3 = move-exception;
            goto L_0x00fb;
        L_0x012b:
            r6 = move-exception;
            r10 = r3;
            r3 = r6;
            r6 = r12;
            goto L_0x00eb;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.g.5.run():void");
        }
    }

    public static synchronized C1331g m3907a() {
        C1331g c1331g;
        synchronized (C1331g.class) {
            if (f4162a == null) {
                f4162a = new C1331g();
            }
            c1331g = f4162a;
        }
        return c1331g;
    }

    private C1331g() {
        if (this.f4166e == null) {
            this.f4166e = new HandlerThread("opensdk.report.handlerthread", 10);
            this.f4166e.start();
        }
        if (this.f4166e.isAlive() && this.f4166e.getLooper() != null) {
            this.f4167f = new Handler(this, this.f4166e.getLooper()) {
                final /* synthetic */ C1331g f4143a;

                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 1000:
                            this.f4143a.m3915b();
                            break;
                        case 1001:
                            this.f4143a.m3918e();
                            break;
                    }
                    super.handleMessage(message);
                }
            };
        }
    }

    public void m3909a(final Bundle bundle, String str, final boolean z) {
        if (bundle != null) {
            C1314f.m3864a("openSDK_LOG.ReportManager", "-->reportVia, bundle: " + bundle.toString());
            if (m3914a("report_via", str) || z) {
                this.f4168g.execute(new Runnable(this) {
                    final /* synthetic */ C1331g f4146c;

                    public void run() {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("uin", com.tencent.connect.common.Constants.DEFAULT_UIN);
                            bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, C1321c.m3892b(Global.getContext()));
                            bundle.putString("imsi", C1321c.m3893c(Global.getContext()));
                            bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID, C1321c.m3894d(Global.getContext()));
                            bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_MAC, C1321c.m3889a());
                            bundle.putString("platform", "1");
                            bundle.putString("os_ver", VERSION.RELEASE);
                            bundle.putString("position", Util.getLocation(Global.getContext()));
                            bundle.putString("network", C1319a.m3884a(Global.getContext()));
                            bundle.putString("language", C1321c.m3891b());
                            bundle.putString("resolution", C1321c.m3890a(Global.getContext()));
                            bundle.putString("apn", C1319a.m3885b(Global.getContext()));
                            bundle.putString("model_name", Build.MODEL);
                            bundle.putString("timezone", TimeZone.getDefault().getID());
                            bundle.putString("sdk_ver", com.tencent.connect.common.Constants.SDK_VERSION);
                            bundle.putString("qz_ver", Util.getAppVersionName(Global.getContext(), com.tencent.connect.common.Constants.PACKAGE_QZONE));
                            bundle.putString("qq_ver", Util.getVersionName(Global.getContext(), "com.tencent.mobileqq"));
                            bundle.putString("qua", Util.getQUA3(Global.getContext(), Global.getPackageName()));
                            bundle.putString("packagename", Global.getPackageName());
                            bundle.putString("app_ver", Util.getAppVersionName(Global.getContext(), Global.getPackageName()));
                            if (bundle != null) {
                                bundle.putAll(bundle);
                            }
                            this.f4146c.f4165d.add(new C1320b(bundle));
                            int size = this.f4146c.f4165d.size();
                            int i = OpenConfig.getInstance(Global.getContext(), null).getInt("Agent_ReportTimeInterval");
                            if (i == 0) {
                                i = 10000;
                            }
                            if (this.f4146c.m3913a("report_via", size) || z) {
                                this.f4146c.m3918e();
                                this.f4146c.f4167f.removeMessages(1001);
                            } else if (!this.f4146c.f4167f.hasMessages(1001)) {
                                Message obtain = Message.obtain();
                                obtain.what = 1001;
                                this.f4146c.f4167f.sendMessageDelayed(obtain, (long) i);
                            }
                        } catch (Throwable e) {
                            C1314f.m3868b("openSDK_LOG.ReportManager", "--> reporVia, exception in sub thread.", e);
                        }
                    }
                });
            }
        }
    }

    public void m3910a(String str, long j, long j2, long j3, int i) {
        m3911a(str, j, j2, j3, i, "", false);
    }

    public void m3911a(String str, long j, long j2, long j3, int i, String str2, boolean z) {
        C1314f.m3864a("openSDK_LOG.ReportManager", "-->reportCgi, command: " + str + " | startTime: " + j + " | reqSize:" + j2 + " | rspSize: " + j3 + " | responseCode: " + i + " | detail: " + str2);
        if (m3914a("report_cgi", "" + i) || z) {
            final long j4 = j;
            final String str3 = str;
            final String str4 = str2;
            final int i2 = i;
            final long j5 = j2;
            final long j6 = j3;
            final boolean z2 = z;
            this.f4169h.execute(new Runnable(this) {
                final /* synthetic */ C1331g f4154h;

                public void run() {
                    int i = 1;
                    try {
                        long elapsedRealtime = SystemClock.elapsedRealtime() - j4;
                        Bundle bundle = new Bundle();
                        String a = C1319a.m3884a(Global.getContext());
                        bundle.putString("apn", a);
                        bundle.putString("appid", "1000067");
                        bundle.putString("commandid", str3);
                        bundle.putString("detail", str4);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("network=").append(a).append('&');
                        stringBuilder.append("sdcard=").append(Environment.getExternalStorageState().equals("mounted") ? 1 : 0).append('&');
                        stringBuilder.append("wifi=").append(C1319a.m3888e(Global.getContext()));
                        bundle.putString("deviceInfo", stringBuilder.toString());
                        int a2 = 100 / this.f4154h.m3908a(i2);
                        if (a2 > 0) {
                            if (a2 > 100) {
                                i = 100;
                            } else {
                                i = a2;
                            }
                        }
                        bundle.putString("frequency", i + "");
                        bundle.putString("reqSize", j5 + "");
                        bundle.putString("resultCode", i2 + "");
                        bundle.putString("rspSize", j6 + "");
                        bundle.putString("timeCost", elapsedRealtime + "");
                        bundle.putString("uin", com.tencent.connect.common.Constants.DEFAULT_UIN);
                        this.f4154h.f4164c.add(new C1320b(bundle));
                        int size = this.f4154h.f4164c.size();
                        i = OpenConfig.getInstance(Global.getContext(), null).getInt("Agent_ReportTimeInterval");
                        if (i == 0) {
                            i = 10000;
                        }
                        if (this.f4154h.m3913a("report_cgi", size) || z2) {
                            this.f4154h.m3915b();
                            this.f4154h.f4167f.removeMessages(1000);
                        } else if (!this.f4154h.f4167f.hasMessages(1000)) {
                            Message obtain = Message.obtain();
                            obtain.what = 1000;
                            this.f4154h.f4167f.sendMessageDelayed(obtain, (long) i);
                        }
                    } catch (Throwable e) {
                        C1314f.m3868b("openSDK_LOG.ReportManager", "--> reportCGI, exception in sub thread.", e);
                    }
                }
            });
        }
    }

    protected void m3915b() {
        this.f4169h.execute(new C13284(this));
    }

    protected boolean m3914a(String str, String str2) {
        boolean z = true;
        boolean z2 = false;
        C1314f.m3867b("openSDK_LOG.ReportManager", "-->availableFrequency, report: " + str + " | ext: " + str2);
        if (!TextUtils.isEmpty(str)) {
            int i;
            int a;
            if (str.equals("report_cgi")) {
                try {
                    a = m3908a(Integer.parseInt(str2));
                    if (this.f4163b.nextInt(100) >= a) {
                        z = false;
                    }
                    z2 = z;
                    i = a;
                } catch (Exception e) {
                }
            } else if (str.equals("report_via")) {
                a = C1323e.m3902a(str2);
                if (this.f4163b.nextInt(100) < a) {
                    z2 = true;
                    i = a;
                } else {
                    i = a;
                }
            } else {
                i = 100;
            }
            C1314f.m3867b("openSDK_LOG.ReportManager", "-->availableFrequency, result: " + z2 + " | frequency: " + i);
        }
        return z2;
    }

    protected boolean m3913a(String str, int i) {
        int i2 = 5;
        int i3;
        if (str.equals("report_cgi")) {
            i3 = OpenConfig.getInstance(Global.getContext(), null).getInt("Common_CGIReportMaxcount");
            if (i3 != 0) {
                i2 = i3;
            }
        } else if (str.equals("report_via")) {
            i3 = OpenConfig.getInstance(Global.getContext(), null).getInt("Agent_ReportBatchCount");
            if (i3 != 0) {
                i2 = i3;
            }
        } else {
            i2 = 0;
        }
        C1314f.m3867b("openSDK_LOG.ReportManager", "-->availableCount, report: " + str + " | dataSize: " + i + " | maxcount: " + i2);
        if (i >= i2) {
            return true;
        }
        return false;
    }

    protected int m3908a(int i) {
        int i2;
        if (i == 0) {
            i2 = OpenConfig.getInstance(Global.getContext(), null).getInt("Common_CGIReportFrequencySuccess");
            if (i2 == 0) {
                return 10;
            }
            return i2;
        }
        i2 = OpenConfig.getInstance(Global.getContext(), null).getInt("Common_CGIReportFrequencyFailed");
        if (i2 == 0) {
            return 100;
        }
        return i2;
    }

    protected Bundle m3916c() {
        if (this.f4164c.size() == 0) {
            return null;
        }
        C1320b c1320b = (C1320b) this.f4164c.get(0);
        if (c1320b == null) {
            C1314f.m3867b("openSDK_LOG.ReportManager", "-->prepareCgiData, the 0th cgireportitem is null.");
            return null;
        }
        String str = (String) c1320b.f4134a.get("appid");
        Collection a = C1324f.m3903a().m3904a("report_cgi");
        if (a != null) {
            this.f4164c.addAll(a);
        }
        C1314f.m3867b("openSDK_LOG.ReportManager", "-->prepareCgiData, mCgiList size: " + this.f4164c.size());
        if (this.f4164c.size() == 0) {
            return null;
        }
        Bundle bundle = new Bundle();
        try {
            bundle.putString("appid", str);
            bundle.putString("releaseversion", com.tencent.connect.common.Constants.SDK_VERSION_REPORT);
            bundle.putString("device", Build.DEVICE);
            bundle.putString("qua", com.tencent.connect.common.Constants.SDK_QUA);
            bundle.putString("key", "apn,frequency,commandid,resultcode,tmcost,reqsize,rspsize,detail,touin,deviceinfo");
            for (int i = 0; i < this.f4164c.size(); i++) {
                c1320b = (C1320b) this.f4164c.get(i);
                bundle.putString(i + "_1", (String) c1320b.f4134a.get("apn"));
                bundle.putString(i + "_2", (String) c1320b.f4134a.get("frequency"));
                bundle.putString(i + "_3", (String) c1320b.f4134a.get("commandid"));
                bundle.putString(i + "_4", (String) c1320b.f4134a.get("resultCode"));
                bundle.putString(i + "_5", (String) c1320b.f4134a.get("timeCost"));
                bundle.putString(i + "_6", (String) c1320b.f4134a.get("reqSize"));
                bundle.putString(i + "_7", (String) c1320b.f4134a.get("rspSize"));
                bundle.putString(i + "_8", (String) c1320b.f4134a.get("detail"));
                bundle.putString(i + "_9", (String) c1320b.f4134a.get("uin"));
                bundle.putString(i + "_10", C1321c.m3895e(Global.getContext()) + "&" + ((String) c1320b.f4134a.get("deviceInfo")));
            }
            C1314f.m3864a("openSDK_LOG.ReportManager", "-->prepareCgiData, end. params: " + bundle.toString());
            return bundle;
        } catch (Throwable e) {
            C1314f.m3868b("openSDK_LOG.ReportManager", "-->prepareCgiData, exception.", e);
            return null;
        }
    }

    protected Bundle m3917d() {
        Collection a = C1324f.m3903a().m3904a("report_via");
        if (a != null) {
            this.f4165d.addAll(a);
        }
        C1314f.m3867b("openSDK_LOG.ReportManager", "-->prepareViaData, mViaList size: " + this.f4165d.size());
        if (this.f4165d.size() == 0) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (Serializable serializable : this.f4165d) {
            JSONObject jSONObject = new JSONObject();
            C1320b c1320b = (C1320b) serializable;
            for (String str : c1320b.f4134a.keySet()) {
                try {
                    Object obj = (String) c1320b.f4134a.get(str);
                    if (obj == null) {
                        obj = "";
                    }
                    jSONObject.put(str, obj);
                } catch (Throwable e) {
                    C1314f.m3868b("openSDK_LOG.ReportManager", "-->prepareViaData, put bundle to json array exception", e);
                }
            }
            jSONArray.put(jSONObject);
        }
        C1314f.m3864a("openSDK_LOG.ReportManager", "-->prepareViaData, JSONArray array: " + jSONArray.toString());
        Bundle bundle = new Bundle();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("data", jSONArray);
            bundle.putString("data", jSONObject2.toString());
            return bundle;
        } catch (Throwable e2) {
            C1314f.m3868b("openSDK_LOG.ReportManager", "-->prepareViaData, put bundle to json array exception", e2);
            return null;
        }
    }

    protected void m3918e() {
        this.f4168g.execute(new C13295(this));
    }

    public void m3912a(String str, String str2, Bundle bundle, boolean z) {
        final Bundle bundle2 = bundle;
        final String str3 = str;
        final boolean z2 = z;
        final String str4 = str2;
        ThreadManager.executeOnSubThread(new Runnable(this) {
            final /* synthetic */ C1331g f4161e;

            public void run() {
                Object obj = null;
                if (bundle2 == null) {
                    C1314f.m3872e("openSDK_LOG.ReportManager", "-->httpRequest, params is null!");
                    return;
                }
                String encode;
                HttpUriRequest httpGet;
                int a = C1323e.m3901a();
                int i = a == 0 ? 3 : a;
                C1314f.m3867b("openSDK_LOG.ReportManager", "-->httpRequest, retryCount: " + i);
                HttpClient httpClient = HttpUtils.getHttpClient(Global.getContext(), null, str3);
                String encodeUrl = HttpUtils.encodeUrl(bundle2);
                if (z2) {
                    encode = URLEncoder.encode(encodeUrl);
                } else {
                    encode = encodeUrl;
                }
                if (str4.toUpperCase().equals(com.tencent.connect.common.Constants.HTTP_GET)) {
                    StringBuffer stringBuffer = new StringBuffer(str3);
                    stringBuffer.append(encode);
                    httpGet = new HttpGet(stringBuffer.toString());
                } else if (str4.toUpperCase().equals(com.tencent.connect.common.Constants.HTTP_POST)) {
                    HttpPost httpPost = new HttpPost(str3);
                    httpPost.setEntity(new ByteArrayEntity(Util.getBytesUTF8(encode)));
                    Object obj2 = httpPost;
                } else {
                    C1314f.m3872e("openSDK_LOG.ReportManager", "-->httpRequest unkonw request method return.");
                    return;
                }
                httpGet.addHeader("Accept-Encoding", Constants.GZIP_TAG);
                httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
                a = 0;
                do {
                    a++;
                    try {
                        int statusCode = httpClient.execute(httpGet).getStatusLine().getStatusCode();
                        C1314f.m3867b("openSDK_LOG.ReportManager", "-->httpRequest, statusCode: " + statusCode);
                        if (statusCode != 200) {
                            C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest : HttpStatuscode != 200");
                            break;
                        }
                        int i2;
                        try {
                            C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread success");
                            i2 = 1;
                            break;
                        } catch (ConnectTimeoutException e) {
                            i2 = 1;
                        } catch (SocketTimeoutException e2) {
                            i2 = 1;
                            C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest SocketTimeoutException");
                            continue;
                            if (a >= i) {
                                if (obj == 1) {
                                    C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request failed");
                                } else {
                                    C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request success");
                                }
                            }
                        } catch (Exception e3) {
                            i2 = 1;
                        }
                    } catch (ConnectTimeoutException e4) {
                        try {
                            C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest ConnectTimeoutException");
                            continue;
                            if (a >= i) {
                                if (obj == 1) {
                                    C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request failed");
                                } else {
                                    C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request success");
                                }
                            }
                        } catch (Exception e5) {
                            C1314f.m3867b("openSDK_LOG.ReportManager", "-->httpRequest, exception in serial executor.");
                            return;
                        }
                    } catch (SocketTimeoutException e6) {
                        C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest SocketTimeoutException");
                        continue;
                        if (a >= i) {
                            if (obj == 1) {
                                C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request success");
                            } else {
                                C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request failed");
                            }
                        }
                    } catch (Exception e7) {
                    }
                } while (a >= i);
                if (obj == 1) {
                    C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request success");
                } else {
                    C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request failed");
                }
                C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Exception");
                if (obj == 1) {
                    C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request success");
                } else {
                    C1314f.m3867b("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Thread request failed");
                }
            }
        });
    }
}
