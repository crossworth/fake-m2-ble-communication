package com.droi.sdk.core;

import android.content.Context;
import android.net.Uri;
import android.support.v4.media.session.PlaybackStateCompat;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.C0944p;
import com.droi.sdk.core.priv.C0944p.C0943a;
import com.droi.sdk.core.priv.CorePriv;
import com.droi.sdk.core.priv.FileDescriptorHelper;
import com.droi.sdk.core.priv.PersistSettings;
import com.droi.sdk.internal.DroiLog;
import com.tencent.connect.common.Constants;
import com.tyd.aidlservice.internal.Tutil;
import com.umeng.facebook.internal.ServerProtocol;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;
import no.nordicsemi.android.dfu.DfuBaseService;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.Okio;
import okio.Source;
import okio.Timeout;
import org.json.JSONException;
import org.json.JSONObject;

public class DroiHttpRequest {
    public static long f2568a = 0;
    private static final String f2569b = "DROI_HTTP_REQUEST";
    private static DroiHttpRequest f2570c;
    private static String f2571d;
    private Object f2572e = new Object();
    private C0829f f2573f;

    class C08232 extends DroiRunnable {
        final /* synthetic */ DroiHttpRequest f2535a;

        C08232(DroiHttpRequest droiHttpRequest) {
            this.f2535a = droiHttpRequest;
        }

        public void run() {
            C0827d a = this.f2535a.m2536a(true, null, CorePriv.f2828b);
            if (a == null) {
                DroiLog.m2870e(DroiHttpRequest.f2569b, "set uid fail due to server unreachable.");
                return;
            }
            C0826c f = a.m2523f();
            if (f == null) {
                DroiLog.m2870e(DroiHttpRequest.f2569b, "set uid fail due to no available server .");
                return;
            }
            C0829f c0829f = new C0829f();
            synchronized (this.f2535a.f2572e) {
                Tutil.SetFakeKlKey(Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l());
                int a2 = this.f2535a.m2532a(f, c0829f, false, new AtomicInteger(0));
            }
            if (c0829f.m2529c()) {
                this.f2535a.f2573f = c0829f;
                if (this.f2535a.f2573f.m2529c()) {
                    this.f2535a.f2573f.m2530d();
                }
            }
            if (a2 != 0) {
                DroiLog.m2870e(DroiHttpRequest.f2569b, "set uid fail due to validate fail.");
            }
        }
    }

    public static class Request {
        private String f2536a;
        private Uri f2537b;
        private HashMap<String, String> f2538c;
        private byte[] f2539d;
        private boolean f2540e;
        private boolean f2541f;
        private String f2542g;

        public static Request make(String str, byte[] bArr) {
            Request request = new Request();
            request.f2536a = str;
            request.f2537b = null;
            request.f2539d = bArr;
            request.f2540e = true;
            request.f2541f = true;
            request.f2542g = Constants.HTTP_POST;
            request.f2538c = new HashMap();
            return request;
        }

        public String addHeader(String str, String str2) {
            return (String) this.f2538c.put(str, str2);
        }

        public String getMethod() {
            return this.f2542g;
        }

        public String getResource() {
            return this.f2536a;
        }

        public boolean isEnableEncrypt() {
            return this.f2541f;
        }

        public boolean isEnableGZip() {
            return this.f2540e;
        }

        public void setEnableEncrypt(boolean z) {
            this.f2541f = z;
        }

        public void setEnableGZip(boolean z) {
            this.f2540e = z;
        }

        public void setMethod(String str) {
            this.f2542g = str;
        }
    }

    public static class Response {
        private int f2543a = 0;
        private int f2544b = -1;
        private int f2545c = -1;
        private String f2546d = null;
        private byte[] f2547e;

        public byte[] getData() {
            return this.f2547e;
        }

        public String getDrid() {
            return this.f2546d;
        }

        public int getDroiStatusCode() {
            return this.f2545c;
        }

        public int getErrorCode() {
            return this.f2544b;
        }

        public int getStatusCode() {
            return this.f2543a;
        }
    }

    private static class C0824a implements Interceptor {
        private int f2548a;

        public C0824a(int i) {
            this.f2548a = i;
        }

        public void m2511a(int i) {
            this.f2548a = i;
        }

        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response proceed = chain.proceed(chain.request());
            if (proceed.body() == null || PersistSettings.instance(PersistSettings.DEV_CONFIG).getBoolean(PersistSettings.KEY_DISABLE_ENCRYPT, false)) {
                return proceed;
            }
            String header = proceed.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_CONTENT_ENCODING);
            if (header == null || header.isEmpty()) {
                return proceed;
            }
            if (!header.contains(com.tyd.aidlservice.internal.Constants.DROI_TAG) && !header.contains("droi")) {
                return proceed;
            }
            Source c0825b = new C0825b(proceed.body().source(), proceed.headers(), this.f2548a, header.contains(com.tyd.aidlservice.internal.Constants.GZIP_TAG));
            Builder newBuilder = proceed.newBuilder();
            newBuilder.body(new RealResponseBody(proceed.headers(), Okio.buffer(c0825b)));
            newBuilder.removeHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_CONTENT_ENCODING);
            return newBuilder.build();
        }
    }

    private static class C0825b implements Source {
        private final Source f2549a;
        private final Headers f2550b;
        private Buffer f2551c = null;
        private int f2552d;
        private boolean f2553e;

        public C0825b(Source source, Headers headers, int i, boolean z) {
            this.f2549a = source;
            this.f2550b = headers;
            this.f2552d = i;
            this.f2553e = z;
        }

        public void close() throws IOException {
            this.f2549a.close();
            if (this.f2551c != null) {
                this.f2551c.close();
            }
        }

        public long read(Buffer buffer, long j) throws IOException {
            if (this.f2551c == null) {
                Buffer buffer2 = new Buffer();
                this.f2551c = new Buffer();
                do {
                    try {
                    } catch (Throwable th) {
                        FileDescriptorHelper.closeQuietly(this.f2549a);
                        if (buffer2 != null) {
                            buffer2.close();
                        }
                    }
                } while (this.f2549a.read(buffer2, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) > 0);
                byte[] readByteArray = buffer2.readByteArray();
                byte[] bArr = new byte[readByteArray.length];
                int AesDecrypt = Tutil.AesDecrypt(readByteArray, bArr, this.f2552d);
                if (AesDecrypt < 0) {
                    throw new IOException("IO error.");
                }
                if (this.f2553e) {
                    GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(bArr, 0, AesDecrypt));
                    readByteArray = new byte[8192];
                    while (true) {
                        int read = gZIPInputStream.read(readByteArray);
                        if (read <= 0) {
                            break;
                        }
                        this.f2551c.write(readByteArray, 0, read);
                    }
                    gZIPInputStream.close();
                } else {
                    this.f2551c.write(bArr, 0, AesDecrypt);
                }
                FileDescriptorHelper.closeQuietly(this.f2549a);
                if (buffer2 != null) {
                    buffer2.close();
                }
            }
            return this.f2551c.read(buffer, j);
        }

        public Timeout timeout() {
            return null;
        }
    }

    private static class C0826c {
        public String f2554a;
        public String f2555b;
        public String f2556c;
        public String f2557d;

        private C0826c() {
        }

        public boolean m2512a() {
            return (this.f2554a == null || this.f2554a.isEmpty()) ? false : true;
        }

        public void m2513b() {
            this.f2554a = "";
        }

        public JSONObject m2514c() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("IP", this.f2554a);
                jSONObject.put("Port", this.f2555b);
                jSONObject.put("Name", this.f2556c);
                jSONObject.put("Weight", this.f2557d);
            } catch (Exception e) {
            }
            return jSONObject;
        }
    }

    private static class C0827d {
        private static final byte[] f2558g = new byte[]{(byte) -109, (byte) 34, (byte) -58, (byte) 89, (byte) 91, (byte) -9, (byte) 123, (byte) -77};
        private C0826c[] f2559a;
        private String f2560b;
        private Date f2561c;
        private Date f2562d;
        private String f2563e;
        private int f2564f;

        private C0827d() {
        }

        public static C0827d m2515a() {
            C0827d c0827d = null;
            String string = PersistSettings.instance(PersistSettings.CONFIG).getString(PersistSettings.KEY_IPLIST, c0827d);
            if (string != null) {
                byte[] b = C0944p.m2794b(C0944p.m2793b(string), f2558g);
                if (b != null) {
                    try {
                        c0827d = C0827d.m2516a(new JSONObject(new String(b)));
                    } catch (JSONException e) {
                    }
                }
            }
            return c0827d;
        }

        public static C0827d m2516a(JSONObject jSONObject) {
            try {
                int i = jSONObject.getInt("Total");
                if (i == 0) {
                    return null;
                }
                C0827d c0827d = new C0827d();
                c0827d.f2560b = jSONObject.getString("Version");
                long j = jSONObject.getLong("Timestamp");
                long j2 = jSONObject.getLong("Expire");
                long j3 = j2 - j;
                long time = new Date().getTime();
                if (!jSONObject.has("LocalCache")) {
                    DroiHttpRequest.f2568a = j - time;
                    j2 = time + j3;
                }
                c0827d.f2561c = new Date(j);
                c0827d.f2562d = new Date(j2);
                if (jSONObject.has("ZoneCode")) {
                    c0827d.f2563e = jSONObject.getString("ZoneCode");
                }
                if (jSONObject.has("Order")) {
                    c0827d.f2564f = jSONObject.getInt("Order");
                } else {
                    c0827d.f2564f = 0;
                }
                c0827d.f2559a = new C0826c[i];
                JSONObject jSONObject2 = jSONObject.getJSONObject("List");
                for (int i2 = 0; i2 < i; i2++) {
                    JSONObject jSONObject3 = jSONObject2.getJSONObject(String.valueOf(i2));
                    C0826c[] c0826cArr = c0827d.f2559a;
                    C0826c c0826c = new C0826c();
                    c0826cArr[i2] = c0826c;
                    c0826c.f2554a = jSONObject3.getString("IP");
                    c0826c.f2555b = jSONObject3.getString("Port");
                    c0826c.f2556c = jSONObject3.getString("Name");
                    c0826c.f2557d = jSONObject3.getString("Weight");
                }
                return c0827d;
            } catch (Exception e) {
                return null;
            }
        }

        public void m2519b() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("Total", String.valueOf(this.f2559a.length));
                jSONObject.put("Version", String.valueOf(this.f2560b));
                jSONObject.put("Timestamp", String.valueOf(this.f2561c.getTime()));
                if (this.f2562d != null) {
                    jSONObject.put("Expire", String.valueOf(this.f2562d.getTime()));
                }
                if (this.f2563e != null) {
                    jSONObject.put("ZoneCode", this.f2563e);
                }
                JSONObject jSONObject2 = new JSONObject();
                for (int i = 0; i < this.f2559a.length; i++) {
                    jSONObject2.put(String.valueOf(i), this.f2559a[0].m2514c());
                }
                jSONObject.put("List", jSONObject2);
                jSONObject.put("Order", this.f2564f);
                jSONObject.put("LocalCache", true);
                PersistSettings.instance(PersistSettings.CONFIG).setString(PersistSettings.KEY_IPLIST, C0944p.m2791b(C0944p.m2789a(jSONObject.toString().getBytes(), f2558g)));
            } catch (Exception e) {
                DroiLog.m2873w(DroiHttpRequest.f2569b, e);
            }
        }

        public boolean m2520c() {
            if (this.f2559a == null || this.f2559a.length <= 0 || this.f2562d == null) {
                return false;
            }
            if (this.f2563e == null || this.f2563e.length() == 0) {
                DroiLog.m2870e(DroiHttpRequest.f2569b, "app id is not valid.");
                return false;
            }
            long time = new Date().getTime();
            long time2 = this.f2562d.getTime();
            if (time >= time2) {
                DroiLog.m2870e(DroiHttpRequest.f2569b, "Now: " + time + ", expired: " + time2);
                return false;
            }
            for (C0826c a : this.f2559a) {
                if (!a.m2512a()) {
                    return false;
                }
            }
            return true;
        }

        public void m2521d() {
            DroiLog.m2868d(DroiHttpRequest.f2569b, "Invalidate ip list");
            this.f2562d = null;
            m2519b();
        }

        public void m2522e() {
            if (this.f2559a == null) {
                this.f2564f = 0;
                return;
            }
            this.f2564f++;
            if (this.f2564f >= this.f2559a.length) {
                m2521d();
                this.f2564f = 0;
            }
        }

        public C0826c m2523f() {
            if (this.f2559a == null || this.f2559a.length == 0) {
                return null;
            }
            if (this.f2564f >= this.f2559a.length) {
                this.f2564f = 0;
            }
            DroiLog.m2868d(DroiHttpRequest.f2569b, "server order: " + this.f2564f);
            return this.f2559a[this.f2564f];
        }
    }

    static class C0828e {
        C0828e() {
        }

        public static OkHttpClient m2524a() {
            System.setProperty("http.keepAlive", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            return new OkHttpClient.Builder().connectionPool(new ConnectionPool(5, 5000, TimeUnit.MILLISECONDS)).addNetworkInterceptor(new C0824a(Tutil.GetKlKeyType())).connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build();
        }
    }

    private static class C0829f {
        private boolean f2565a;
        private long f2566b;
        private long f2567c;

        private C0829f() {
        }

        public static C0829f m2525a() {
            String string = PersistSettings.instance(PersistSettings.CONFIG).getString(PersistSettings.KEY_KL_TIMESTAMP, null);
            if (string == null) {
                return new C0829f();
            }
            String[] split = string.split("\n");
            try {
                C0829f c0829f = new C0829f();
                c0829f.f2565a = split[0].equals("1");
                c0829f.f2566b = Long.parseLong(split[1]);
                c0829f.f2567c = Long.parseLong(split[2]);
                return c0829f;
            } catch (Exception e) {
                DroiLog.m2869e(DroiHttpRequest.f2569b, e);
                return new C0829f();
            }
        }

        public void m2527a(long j) {
            if (j > 10000) {
                DroiHttpRequest.f2568a = j - new Date().getTime();
            }
            this.f2566b = j;
            this.f2567c = this.f2566b - System.currentTimeMillis();
            this.f2565a = true;
        }

        public void m2528b() {
            this.f2565a = false;
            PersistSettings.instance(PersistSettings.CONFIG).remove(PersistSettings.KEY_KL_TIMESTAMP);
        }

        public boolean m2529c() {
            return this.f2565a;
        }

        public void m2530d() {
            Locale locale = Locale.ENGLISH;
            String str = "%d\n%d\n%d";
            Object[] objArr = new Object[3];
            objArr[0] = Integer.valueOf(this.f2565a ? 1 : 0);
            objArr[1] = Long.valueOf(this.f2566b);
            objArr[2] = Long.valueOf(this.f2567c);
            PersistSettings.instance(PersistSettings.CONFIG).setString(PersistSettings.KEY_KL_TIMESTAMP, String.format(locale, str, objArr));
            DroiLog.m2868d(DroiHttpRequest.f2569b, "timestamp saved.");
        }

        public long m2531e() {
            return System.currentTimeMillis() + this.f2567c;
        }
    }

    private DroiHttpRequest() {
    }

    private int m2532a(C0826c c0826c, C0829f c0829f, boolean z, AtomicInteger atomicInteger) {
        byte[] bArr = new byte[4];
        new SecureRandom().nextBytes(bArr);
        int i = ByteBuffer.wrap(bArr).getInt();
        byte[] GenKeyValidation = Tutil.GenKeyValidation(i);
        if (GenKeyValidation == null || GenKeyValidation.length <= 0) {
            return com.tyd.aidlservice.internal.Constants.KD_GENKEYVALIDATION_ERROR;
        }
        OkHttpClient a = C0828e.m2524a();
        okhttp3.Request.Builder post = new okhttp3.Request.Builder().url(m2541a(c0826c)).post(RequestBody.create(MediaType.parse(DfuBaseService.MIME_TYPE_OCTET_STREAM), GenKeyValidation));
        okhttp3.Request build = post.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_ID, m2539a(Tutil.GetKlKeyID(), Tutil.GetKlKeyType(), Tutil.GetKlKeyVersion(), 1, Tutil.RsaKeyVersion(), GenKeyValidation.length, Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l())).build();
        DroiLog.m2868d(f2569b, "[Validate] URL: " + m2541a(c0826c));
        try {
            okhttp3.Response execute = a.newCall(build).execute();
            if (!execute.isSuccessful()) {
                return com.tyd.aidlservice.internal.Constants.KD_STATUSCODE_ERROR;
            }
            GenKeyValidation = execute.body().bytes();
            String header = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_REQUEST_ID);
            try {
                int ChkKeyValidationCorrect;
                String header2 = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_STATUS);
                DroiLog.m2868d(f2569b, "[Validate] status: " + header2);
                int parseInt = Integer.parseInt(header2);
                atomicInteger.set(parseInt);
                if (parseInt == 1) {
                    ChkKeyValidationCorrect = Tutil.ChkKeyValidationCorrect(Tutil.GetKlKeyType(), i, GenKeyValidation);
                } else if (parseInt == 2) {
                    try {
                        ChkKeyValidationCorrect = Tutil.ChkKeyValidationFailed(Tutil.GetKlKeyType(), i, Integer.parseInt(execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_OTP)), GenKeyValidation);
                    } catch (Exception e) {
                        DroiLog.m2873w(f2569b, e);
                        DroiLog.m2870e(f2569b, "Drid: " + header);
                        return com.tyd.aidlservice.internal.Constants.KD_DROI_XOR_PARSE_ERROR;
                    }
                } else {
                    DroiLog.m2870e(f2569b, "Drid: " + header);
                    return com.tyd.aidlservice.internal.Constants.KD_DROISTATUS_ERROR;
                }
                if (ChkKeyValidationCorrect == 0 && z) {
                    m2552e();
                }
                try {
                    String header3 = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_TS);
                    if (header3 == null) {
                        return ChkKeyValidationCorrect;
                    }
                    m2543a(header3, c0829f);
                    return ChkKeyValidationCorrect;
                } catch (Exception e2) {
                    DroiLog.m2873w(f2569b, e2);
                    DroiLog.m2870e(f2569b, "Drid: " + header);
                    return -10004;
                }
            } catch (Exception e22) {
                DroiLog.m2873w(f2569b, e22);
                DroiLog.m2870e(f2569b, "Drid: " + header);
                return -10004;
            }
        } catch (Exception e222) {
            DroiLog.m2873w(f2569b, e222);
            if (c0826c != null) {
                c0826c.m2513b();
            }
            return com.tyd.aidlservice.internal.Constants.KD_NETWORK_ERROR;
        }
    }

    private C0827d m2535a(String str) {
        OkHttpClient a = C0828e.m2524a();
        String str2 = com.tyd.aidlservice.internal.Constants.IP_LIST_URL;
        if (str != null) {
            str2 = str2 + "?appid=" + str;
        }
        try {
            String string = a.newCall(new okhttp3.Request.Builder().url(str2).get().build()).execute().body().string();
            DroiLog.m2868d(f2569b, "INDATA: " + str2);
            DroiLog.m2868d(f2569b, "IPLIST: " + string);
            return C0827d.m2516a(new JSONObject(string));
        } catch (Exception e) {
            DroiLog.m2873w(f2569b, e);
            return null;
        }
    }

    private C0827d m2536a(boolean z, AtomicInteger atomicInteger, String str) {
        if (str == null) {
            DroiLog.m2870e(f2569b, "No application id defined");
            if (atomicInteger == null) {
                return null;
            }
            atomicInteger.set(com.tyd.aidlservice.internal.Constants.APP_ID_ERROR);
            return null;
        }
        C0827d a = C0827d.m2515a();
        if (a == null || !a.m2520c() || (a.f2563e == null && str != null)) {
            a = m2535a(str);
            if (a == null || !a.m2520c()) {
                if (atomicInteger != null) {
                    if (a == null || !(a.f2563e == null || a.f2563e.length() == 0)) {
                        atomicInteger.set(com.tyd.aidlservice.internal.Constants.IP_LIST_ERROR);
                    } else {
                        atomicInteger.set(com.tyd.aidlservice.internal.Constants.APP_ID_INVALID);
                    }
                }
                DroiLog.m2870e(f2569b, "Pick ip fail. not valid.");
                return null;
            }
            a.m2519b();
        }
        if (a != null) {
            return a;
        }
        if (atomicInteger == null) {
            return null;
        }
        atomicInteger.set(com.tyd.aidlservice.internal.Constants.IP_LIST_ERROR);
        return null;
    }

    private String m2539a(long j, int i, int i2, int i3, int i4, int i5, long j2, long j3) {
        return j + "," + i + "," + i2 + "," + i3 + "," + i4 + "," + i5 + "," + C0944p.m2785a(j2) + "," + C0944p.m2785a(j3) + "," + PersistSettings.instance(PersistSettings.CONFIG).getInt(PersistSettings.KEY_UID_FROM_FREEMEOS, 2);
    }

    private String m2540a(Request request, C0826c c0826c) {
        return request.f2537b != null ? request.f2537b.toString() : m2542a(c0826c.f2554a, c0826c.f2555b, request.getResource());
    }

    private String m2541a(C0826c c0826c) {
        return m2542a(c0826c.f2554a, c0826c.f2555b, com.tyd.aidlservice.internal.Constants.VALIDATE_RESOURCE);
    }

    private String m2542a(String str, String str2, String str3) {
        return "http://" + str + ":" + str2 + str3;
    }

    private void m2543a(String str, C0829f c0829f) {
        byte[] Base64Decode = Tutil.Base64Decode(str);
        byte[] bArr = new byte[Base64Decode.length];
        if (Tutil.AesDecrypt(Base64Decode, bArr, 1) > 0) {
            c0829f.m2527a(ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).getLong());
        }
    }

    private boolean m2544a(C0829f c0829f) {
        return c0829f == null || !c0829f.f2565a;
    }

    private byte[] m2545a(byte[] bArr, String str, int i) {
        int length = bArr == null ? 0 : bArr.length;
        Object obj = new byte[(length + 16)];
        System.arraycopy(Tutil.stringToBytesWithTermination(str), 0, obj, 0, 16);
        if (length > 0) {
            System.arraycopy(bArr, 0, obj, 16, length);
        }
        byte[] bArr2 = new byte[Tutil.AesEncryptMemSize(obj.length)];
        return Tutil.AesEncrypt(obj, bArr2, i) <= 0 ? null : bArr2;
    }

    private int m2546b() {
        Tutil.SetKlKeyInvalid();
        return m2553f();
    }

    private byte[] m2548b(C0829f c0829f) {
        Object obj = new byte[7];
        new SecureRandom().nextBytes(obj);
        Object array = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(c0829f.m2531e()).array();
        Object obj2 = new byte[(array.length + obj.length)];
        System.arraycopy(array, 0, obj2, 0, array.length);
        System.arraycopy(obj, 0, obj2, array.length, obj.length);
        return obj2;
    }

    private String m2550c() {
        return f2571d + File.separator + com.tyd.aidlservice.internal.Constants.KLKEY_FILE_NAME;
    }

    private int m2551d() {
        FileInputStream fileInputStream;
        Exception e;
        Exception e2;
        Throwable th;
        int i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_NOT_FOUND_ERROR;
        File file = new File(m2550c());
        Tutil.KlKeyFree();
        if (!file.exists()) {
            return i;
        }
        if (file.length() <= 0) {
            return com.tyd.aidlservice.internal.Constants.KLKEY_FILE_EMPTY_ERROR;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[((int) file.length())];
                i = ((long) fileInputStream.read(bArr)) != file.length() ? com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR : Tutil.KlKeyAlloc(bArr) != 0 ? com.tyd.aidlservice.internal.Constants.KLKEY_ALLOC_ERROR : 0;
                if (fileInputStream == null) {
                    return i;
                }
                try {
                    fileInputStream.close();
                    return i;
                } catch (Exception e3) {
                    DroiLog.m2873w(f2569b, e3);
                    return com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                }
            } catch (FileNotFoundException e4) {
                e2 = e4;
                try {
                    DroiLog.m2873w(f2569b, e2);
                    if (fileInputStream != null) {
                        return i;
                    }
                    try {
                        fileInputStream.close();
                        return i;
                    } catch (Exception e32) {
                        DroiLog.m2873w(f2569b, e32);
                        return com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e5) {
                            DroiLog.m2873w(f2569b, e5);
                        }
                    }
                    throw th;
                }
            } catch (IOException e6) {
                e32 = e6;
                DroiLog.m2873w(f2569b, e32);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e322) {
                        DroiLog.m2873w(f2569b, e322);
                        return com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                    }
                }
                return com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
            }
        } catch (FileNotFoundException e7) {
            e2 = e7;
            fileInputStream = null;
            DroiLog.m2873w(f2569b, e2);
            if (fileInputStream != null) {
                return i;
            }
            fileInputStream.close();
            return i;
        } catch (IOException e8) {
            e322 = e8;
            fileInputStream = null;
            DroiLog.m2873w(f2569b, e322);
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    private int m2552e() {
        Exception e;
        Throwable th;
        DroiLog.m2868d(f2569b, "store key");
        int i = 0;
        File file = new File(m2550c());
        byte[] KlKeyGet = Tutil.KlKeyGet();
        if (KlKeyGet == null) {
            i = com.tyd.aidlservice.internal.Constants.KLKEY_EMPTY_ERROR;
        } else {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(KlKeyGet);
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (Exception e2) {
                            DroiLog.m2873w(f2569b, e2);
                            i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                        }
                    }
                } catch (FileNotFoundException e3) {
                    e2 = e3;
                    try {
                        DroiLog.m2873w(f2569b, e2);
                        i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_NOT_FOUND_ERROR;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (Exception e22) {
                                DroiLog.m2873w(f2569b, e22);
                                i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                            }
                        }
                        DroiLog.m2868d(f2569b, "store key done");
                        return i;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (Exception e4) {
                                DroiLog.m2873w(f2569b, e4);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e22 = e5;
                    DroiLog.m2873w(f2569b, e22);
                    if (fileOutputStream == null) {
                        i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                    } else {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                        } catch (Exception e222) {
                            DroiLog.m2873w(f2569b, e222);
                            i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                        }
                    }
                    DroiLog.m2868d(f2569b, "store key done");
                    return i;
                }
            } catch (FileNotFoundException e6) {
                e222 = e6;
                fileOutputStream = null;
                DroiLog.m2873w(f2569b, e222);
                i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_NOT_FOUND_ERROR;
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                DroiLog.m2868d(f2569b, "store key done");
                return i;
            } catch (IOException e7) {
                e222 = e7;
                fileOutputStream = null;
                DroiLog.m2873w(f2569b, e222);
                if (fileOutputStream == null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                } else {
                    i = com.tyd.aidlservice.internal.Constants.KLKEY_FILE_IO_ERROR;
                }
                DroiLog.m2868d(f2569b, "store key done");
                return i;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                throw th;
            }
        }
        DroiLog.m2868d(f2569b, "store key done");
        return i;
    }

    private int m2553f() {
        int e = m2552e();
        DroiLog.m2868d(f2569b, "Free key");
        Tutil.KlKeyFree();
        return e;
    }

    private boolean m2554g() {
        boolean z = (!Tutil.GetKlKeyIsValid() || Tutil.GetKlKeyType() == 0) && !(m2551d() == 0 && Tutil.GetKlKeyIsValid());
        DroiLog.m2868d(f2569b, "isKeyInvalid: " + z);
        return z;
    }

    public static void initialize(Context context) {
        f2571d = context.getFilesDir().getPath();
    }

    public static synchronized DroiHttpRequest instance() {
        DroiHttpRequest droiHttpRequest;
        synchronized (DroiHttpRequest.class) {
            if (f2571d == null) {
                throw new RuntimeException("DroiHttpRequest do not initialized.");
            }
            if (f2570c == null) {
                f2570c = new DroiHttpRequest();
            }
            droiHttpRequest = f2570c;
        }
        return droiHttpRequest;
    }

    boolean m2555a(long j, long j2) {
        if (PersistSettings.instance(PersistSettings.DEV_CONFIG).getBoolean(PersistSettings.KEY_DISABLE_ENCRYPT, false)) {
            return true;
        }
        if (m2554g()) {
            if (!NetworkUtils.isWifiOrMobileAvailable(NetworkUtils.getNetworkState(CorePriv.getContext()))) {
                return false;
            }
            TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(TaskDispatcher.MainThreadName);
            DroiRunnable c08232 = new C08232(this);
            if (dispatcher == TaskDispatcher.currentTaskDispatcher()) {
                DroiTask.create(c08232).runAndWait("TaskDispatcher_DroiBackgroundThread");
            } else {
                c08232.run();
            }
        }
        Tutil.setKlKeyUID_u(j);
        Tutil.setKlKeyUID_l(j2);
        m2552e();
        return true;
    }

    long[] m2556a() {
        if (PersistSettings.instance(PersistSettings.DEV_CONFIG).getBoolean(PersistSettings.KEY_DISABLE_ENCRYPT, false)) {
            return new long[]{0, 0};
        }
        DroiLog.m2868d(f2569b, "Fetching DeviceID: " + m2554g());
        if (!NetworkUtils.isWifiOrMobileAvailable(NetworkUtils.getNetworkState(CorePriv.getContext()))) {
            return new long[]{0, 0};
        }
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(TaskDispatcher.MainThreadName);
        final AtomicReference atomicReference = new AtomicReference(new long[]{0, 0});
        DroiRunnable c08221 = new DroiRunnable(this) {
            final /* synthetic */ DroiHttpRequest f2534b;

            public void run() {
                C0827d a = this.f2534b.m2536a(true, null, CorePriv.f2828b);
                if (a != null) {
                    C0826c f = a.m2523f();
                    if (f != null) {
                        int a2;
                        C0829f c0829f = new C0829f();
                        synchronized (this.f2534b.f2572e) {
                            if (this.f2534b.m2554g()) {
                                Tutil.SetFakeKlKey(Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l());
                            }
                            a2 = this.f2534b.m2532a(f, c0829f, true, new AtomicInteger(0));
                        }
                        if (c0829f.m2529c()) {
                            this.f2534b.f2573f = c0829f;
                            if (this.f2534b.f2573f.m2529c()) {
                                this.f2534b.f2573f.m2530d();
                            }
                        }
                        if (a2 == 0) {
                            atomicReference.set(new long[]{Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l()});
                        }
                    }
                }
            }
        };
        if (dispatcher == TaskDispatcher.currentTaskDispatcher()) {
            DroiTask.create(c08221).runAndWait("TaskDispatcher_DroiBackgroundThread");
        } else {
            c08221.run();
        }
        return (long[]) atomicReference.get();
    }

    byte[] packUdpPackets(byte[] bArr, boolean z, DroiError droiError) {
        if (droiError == null) {
            droiError = new DroiError();
        }
        byte[] EncapsulateSecUDP = Tutil.EncapsulateSecUDP(bArr, z, false);
        if (EncapsulateSecUDP == null) {
            droiError.setCode(DroiError.ERROR);
        }
        return EncapsulateSecUDP;
    }

    public Response request(Request request) {
        ResponseBody responseBody;
        Response response = new Response();
        if (NetworkUtils.isWifiOrMobileAvailable(NetworkUtils.getNetworkState(CorePriv.getContext()))) {
            Object obj = null;
            int i = 0;
            while (i < 2) {
                Object obj2;
                boolean z = PersistSettings.instance(PersistSettings.DEV_CONFIG).getBoolean(PersistSettings.KEY_DISABLE_ENCRYPT, false);
                if (z) {
                    request.setEnableGZip(false);
                }
                boolean z2 = !z && request.f2541f;
                String str = (String) request.f2538c.get("X-Droi-AppID");
                if (z2) {
                    if (str == null) {
                        response.f2544b = com.tyd.aidlservice.internal.Constants.APP_ID_ERROR;
                        return response;
                    } else if (C0943a.m2782a(str) == (short) -1) {
                        response.f2544b = com.tyd.aidlservice.internal.Constants.APP_ID_ERROR;
                        return response;
                    }
                }
                AtomicInteger atomicInteger = new AtomicInteger();
                C0827d a = m2536a(z2, atomicInteger, str);
                if (a == null) {
                    response.f2544b = atomicInteger.get();
                    obj2 = obj;
                } else {
                    C0826c f = a.m2523f();
                    if (f == null) {
                        response.f2544b = com.tyd.aidlservice.internal.Constants.IP_LIST_ERROR;
                        if (a != null) {
                            a.m2521d();
                        }
                        obj2 = obj;
                    } else {
                        byte[] compressDeflater;
                        Object obj3;
                        C0829f a2;
                        AtomicInteger atomicInteger2;
                        int i2;
                        int GetKlKeyType;
                        OkHttpClient a3;
                        okhttp3.Request.Builder builder;
                        String a4;
                        HashMap b;
                        byte[] bArr;
                        byte[] b2;
                        byte[] bArr2;
                        int i3;
                        okhttp3.Response execute;
                        String header;
                        String header2;
                        Object obj4;
                        String header3;
                        C0829f c0829f;
                        byte[] c = request.f2539d;
                        if (request.isEnableGZip() && c != null) {
                            compressDeflater = Tutil.compressDeflater(c);
                            if (compressDeflater.length < c.length) {
                                obj3 = 1;
                                if (z2) {
                                    a2 = this.f2573f != null ? C0829f.m2525a() : this.f2573f;
                                    atomicInteger2 = new AtomicInteger(0);
                                    i2 = 0;
                                    synchronized (this.f2572e) {
                                        if (m2554g()) {
                                            Tutil.SetFakeKlKey(Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l());
                                            i2 = m2532a(f, a2, true, atomicInteger2);
                                            if (i2 == 0 && a2.m2529c()) {
                                                a2.m2530d();
                                            }
                                        }
                                    }
                                    if (i2 != 0) {
                                        m2546b();
                                        response.f2544b = i2;
                                        response.f2545c = atomicInteger2.get();
                                        if (response.f2545c != -22 || response.f2545c == -23 || response.f2545c == -21) {
                                            a.f2563e = null;
                                            a.m2521d();
                                        } else {
                                            a.m2522e();
                                        }
                                        obj2 = obj;
                                    } else if (m2544a(a2)) {
                                        this.f2573f = a2;
                                    } else {
                                        synchronized (this.f2572e) {
                                            i2 = m2532a(f, a2, true, atomicInteger2);
                                        }
                                        if (i2 != 0) {
                                            try {
                                                m2546b();
                                                response.f2544b = i2;
                                                response.f2545c = atomicInteger2.get();
                                                if (response.f2545c != -22 || response.f2545c == -23 || response.f2545c == -21) {
                                                    a.f2563e = null;
                                                    a.m2521d();
                                                } else {
                                                    a.m2522e();
                                                }
                                                obj2 = obj;
                                            } catch (Exception e) {
                                                DroiLog.m2873w(f2569b, e);
                                            }
                                        } else if (a2.m2529c()) {
                                            this.f2573f = a2;
                                            this.f2573f.m2530d();
                                        }
                                    }
                                }
                                GetKlKeyType = Tutil.GetKlKeyType();
                                a3 = C0828e.m2524a();
                                builder = new okhttp3.Request.Builder();
                                a4 = m2540a(request, f);
                                builder.url(a4);
                                if (z2) {
                                    builder.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_ID, m2539a(Tutil.GetKlKeyID(), GetKlKeyType, Tutil.GetKlKeyVersion(), 1, Tutil.RsaKeyVersion(), c != null ? 0 : c.length, Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l()));
                                }
                                b = request.f2538c;
                                if (z2) {
                                    bArr = null;
                                } else {
                                    b2 = m2548b(this.f2573f);
                                    bArr2 = new byte[Tutil.AesEncryptMemSize(b2.length)];
                                    if (Tutil.AesEncrypt(b2, bArr2, 1) >= 0) {
                                        m2546b();
                                        response.f2544b = com.tyd.aidlservice.internal.Constants.ENC_ERROR;
                                        obj2 = obj;
                                    } else {
                                        bArr = bArr2;
                                    }
                                }
                                for (String str2 : b.keySet()) {
                                    builder.addHeader(str2, (String) b.get(str2));
                                }
                                str2 = com.tyd.aidlservice.internal.Constants.DROI_TAG;
                                if (obj3 != null) {
                                    str2 = str2 + "-gzip";
                                }
                                builder.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_CONTENT_ENCODING, str2);
                                builder.addHeader("Accept-Encoding", com.tyd.aidlservice.internal.Constants.GZIP_TAG);
                                if (a.f2563e != null) {
                                    builder.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_ZC, a.f2563e);
                                }
                                str2 = null;
                                if (z2) {
                                    str2 = Tutil.Base64Encode(bArr);
                                    if (str2.charAt(str2.length() - 1) == '\n') {
                                        str2 = str2.substring(0, str2.length() - 1);
                                    }
                                    builder.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_TS, str2);
                                }
                                if (z2) {
                                    bArr2 = compressDeflater;
                                } else {
                                    bArr2 = m2545a(compressDeflater, str2, GetKlKeyType);
                                    if (bArr2 == null) {
                                        m2546b();
                                        response.f2544b = com.tyd.aidlservice.internal.Constants.ENC_ERROR;
                                        obj2 = obj;
                                    }
                                }
                                builder.method(request.getMethod(), bArr2 != null ? RequestBody.create(null, new byte[0]) : RequestBody.create(MediaType.parse(DfuBaseService.MIME_TYPE_OCTET_STREAM), bArr2));
                                responseBody = null;
                                i3 = -99;
                                DroiLog.m2868d(f2569b, "http url: " + a4);
                                execute = a3.newCall(builder.build()).execute();
                                responseBody = execute.body();
                                i2 = execute.code();
                                header = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_REQUEST_ID);
                                response.f2546d = header;
                                header2 = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_STATUS);
                                if (header2 != null) {
                                    i3 = Integer.parseInt(header2);
                                }
                                if (request.f2539d != null) {
                                    DroiLog.m2868d(f2569b, "    data: " + new String(request.f2539d));
                                }
                                DroiLog.m2868d(f2569b, "  status: " + i2);
                                DroiLog.m2868d(f2569b, "      zc: " + a.f2563e);
                                if (i2 == 200) {
                                    response.f2543a = i2;
                                    response.f2544b = -1006;
                                    response.f2545c = i3;
                                    a.m2522e();
                                    DroiLog.m2870e(f2569b, "Drid: " + header);
                                    if (responseBody != null) {
                                        responseBody.close();
                                    }
                                    obj2 = obj;
                                } else {
                                    bArr2 = i3 < 0 ? responseBody.bytes() : null;
                                    if (responseBody != null) {
                                        responseBody.close();
                                    }
                                    response.f2543a = execute.code();
                                    if (!z2) {
                                        if (i3 >= 0) {
                                            DroiLog.m2870e(f2569b, "Drid: " + header);
                                            response.f2544b = com.tyd.aidlservice.internal.Constants.DROI_STATUS_ERROR;
                                            response.f2545c = i3;
                                            switch (i3) {
                                                case com.tyd.aidlservice.internal.Constants.X_DROI_STAT_ZONE_EXPIRED_INVALID /*-23*/:
                                                case com.tyd.aidlservice.internal.Constants.X_DROI_STAT_ZONECODE_EXPIRED /*-22*/:
                                                case com.tyd.aidlservice.internal.Constants.X_DROI_STAT_ZONECODE_MISSING /*-21*/:
                                                    a.f2563e = null;
                                                    a.m2521d();
                                                    i3 = 1;
                                                    obj2 = obj;
                                                    break;
                                                case -14:
                                                case -11:
                                                case -3:
                                                    i3 = 1;
                                                    obj2 = obj;
                                                    break;
                                                case -8:
                                                    obj4 = null;
                                                    obj2 = obj;
                                                    break;
                                                case -7:
                                                case -6:
                                                    if (obj == null) {
                                                        Tutil.SetKlKeyInvalid();
                                                        i3 = 1;
                                                        i2 = 1;
                                                        break;
                                                    }
                                                    obj4 = null;
                                                    obj2 = obj;
                                                    break;
                                                case -5:
                                                    this.f2573f.m2528b();
                                                    i3 = 1;
                                                    obj2 = obj;
                                                    break;
                                                default:
                                                    obj4 = 1;
                                                    obj2 = obj;
                                                    Tutil.SetKlKeyInvalid();
                                                    break;
                                            }
                                            if (obj4 != null) {
                                                return response;
                                            }
                                        } else {
                                            header3 = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_TS);
                                            if (header3 != null) {
                                                c0829f = new C0829f();
                                                m2543a(header3, c0829f);
                                                if (c0829f.f2565a) {
                                                    this.f2573f = c0829f;
                                                }
                                            }
                                        }
                                    }
                                    DroiLog.m2868d(f2569b, "  result: " + new String(bArr2));
                                    response.f2547e = bArr2;
                                    response.f2544b = 0;
                                    response.f2545c = 0;
                                    return response;
                                }
                            }
                        }
                        obj3 = null;
                        compressDeflater = c;
                        if (z2) {
                            if (this.f2573f != null) {
                            }
                            atomicInteger2 = new AtomicInteger(0);
                            i2 = 0;
                            synchronized (this.f2572e) {
                                if (m2554g()) {
                                    Tutil.SetFakeKlKey(Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l());
                                    i2 = m2532a(f, a2, true, atomicInteger2);
                                    a2.m2530d();
                                }
                            }
                            if (i2 != 0) {
                                m2546b();
                                response.f2544b = i2;
                                response.f2545c = atomicInteger2.get();
                                if (response.f2545c != -22) {
                                }
                                a.f2563e = null;
                                a.m2521d();
                                obj2 = obj;
                            } else if (m2544a(a2)) {
                                this.f2573f = a2;
                            } else {
                                synchronized (this.f2572e) {
                                    i2 = m2532a(f, a2, true, atomicInteger2);
                                }
                                if (i2 != 0) {
                                    m2546b();
                                    response.f2544b = i2;
                                    response.f2545c = atomicInteger2.get();
                                    if (response.f2545c != -22) {
                                    }
                                    a.f2563e = null;
                                    a.m2521d();
                                    obj2 = obj;
                                } else if (a2.m2529c()) {
                                    this.f2573f = a2;
                                    this.f2573f.m2530d();
                                }
                            }
                        }
                        GetKlKeyType = Tutil.GetKlKeyType();
                        a3 = C0828e.m2524a();
                        builder = new okhttp3.Request.Builder();
                        a4 = m2540a(request, f);
                        builder.url(a4);
                        if (z2) {
                            if (c != null) {
                            }
                            builder.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_ID, m2539a(Tutil.GetKlKeyID(), GetKlKeyType, Tutil.GetKlKeyVersion(), 1, Tutil.RsaKeyVersion(), c != null ? 0 : c.length, Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l()));
                        }
                        b = request.f2538c;
                        if (z2) {
                            bArr = null;
                        } else {
                            b2 = m2548b(this.f2573f);
                            bArr2 = new byte[Tutil.AesEncryptMemSize(b2.length)];
                            if (Tutil.AesEncrypt(b2, bArr2, 1) >= 0) {
                                bArr = bArr2;
                            } else {
                                m2546b();
                                response.f2544b = com.tyd.aidlservice.internal.Constants.ENC_ERROR;
                                obj2 = obj;
                            }
                        }
                        for (String str22 : b.keySet()) {
                            builder.addHeader(str22, (String) b.get(str22));
                        }
                        str22 = com.tyd.aidlservice.internal.Constants.DROI_TAG;
                        if (obj3 != null) {
                            str22 = str22 + "-gzip";
                        }
                        builder.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_CONTENT_ENCODING, str22);
                        builder.addHeader("Accept-Encoding", com.tyd.aidlservice.internal.Constants.GZIP_TAG);
                        if (a.f2563e != null) {
                            builder.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_ZC, a.f2563e);
                        }
                        str22 = null;
                        if (z2) {
                            str22 = Tutil.Base64Encode(bArr);
                            if (str22.charAt(str22.length() - 1) == '\n') {
                                str22 = str22.substring(0, str22.length() - 1);
                            }
                            builder.addHeader(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_TS, str22);
                        }
                        if (z2) {
                            bArr2 = compressDeflater;
                        } else {
                            bArr2 = m2545a(compressDeflater, str22, GetKlKeyType);
                            if (bArr2 == null) {
                                m2546b();
                                response.f2544b = com.tyd.aidlservice.internal.Constants.ENC_ERROR;
                                obj2 = obj;
                            }
                        }
                        if (bArr2 != null) {
                        }
                        builder.method(request.getMethod(), bArr2 != null ? RequestBody.create(null, new byte[0]) : RequestBody.create(MediaType.parse(DfuBaseService.MIME_TYPE_OCTET_STREAM), bArr2));
                        responseBody = null;
                        i3 = -99;
                        try {
                            DroiLog.m2868d(f2569b, "http url: " + a4);
                            execute = a3.newCall(builder.build()).execute();
                            responseBody = execute.body();
                            i2 = execute.code();
                            header = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_REQUEST_ID);
                            response.f2546d = header;
                            header2 = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_STATUS);
                            if (header2 != null) {
                                i3 = Integer.parseInt(header2);
                            }
                            if (request.f2539d != null) {
                                DroiLog.m2868d(f2569b, "    data: " + new String(request.f2539d));
                            }
                            DroiLog.m2868d(f2569b, "  status: " + i2);
                            DroiLog.m2868d(f2569b, "      zc: " + a.f2563e);
                            if (i2 == 200) {
                                if (i3 < 0) {
                                }
                                if (responseBody != null) {
                                    responseBody.close();
                                }
                                response.f2543a = execute.code();
                                if (!z2) {
                                    if (i3 >= 0) {
                                        header3 = execute.header(com.tyd.aidlservice.internal.Constants.HTTP_HEADER_DROI_TS);
                                        if (header3 != null) {
                                            c0829f = new C0829f();
                                            m2543a(header3, c0829f);
                                            if (c0829f.f2565a) {
                                                this.f2573f = c0829f;
                                            }
                                        }
                                    } else {
                                        DroiLog.m2870e(f2569b, "Drid: " + header);
                                        response.f2544b = com.tyd.aidlservice.internal.Constants.DROI_STATUS_ERROR;
                                        response.f2545c = i3;
                                        switch (i3) {
                                            case com.tyd.aidlservice.internal.Constants.X_DROI_STAT_ZONE_EXPIRED_INVALID /*-23*/:
                                            case com.tyd.aidlservice.internal.Constants.X_DROI_STAT_ZONECODE_EXPIRED /*-22*/:
                                            case com.tyd.aidlservice.internal.Constants.X_DROI_STAT_ZONECODE_MISSING /*-21*/:
                                                a.f2563e = null;
                                                a.m2521d();
                                                i3 = 1;
                                                obj2 = obj;
                                                break;
                                            case -14:
                                            case -11:
                                            case -3:
                                                i3 = 1;
                                                obj2 = obj;
                                                break;
                                            case -8:
                                                obj4 = null;
                                                obj2 = obj;
                                                break;
                                            case -7:
                                            case -6:
                                                if (obj == null) {
                                                    obj4 = null;
                                                    obj2 = obj;
                                                    break;
                                                }
                                                Tutil.SetKlKeyInvalid();
                                                i3 = 1;
                                                i2 = 1;
                                                break;
                                            case -5:
                                                this.f2573f.m2528b();
                                                i3 = 1;
                                                obj2 = obj;
                                                break;
                                            default:
                                                obj4 = 1;
                                                obj2 = obj;
                                                Tutil.SetKlKeyInvalid();
                                                break;
                                        }
                                        if (obj4 != null) {
                                            return response;
                                        }
                                    }
                                }
                                DroiLog.m2868d(f2569b, "  result: " + new String(bArr2));
                                response.f2547e = bArr2;
                                response.f2544b = 0;
                                response.f2545c = 0;
                                return response;
                            }
                            response.f2543a = i2;
                            response.f2544b = -1006;
                            response.f2545c = i3;
                            a.m2522e();
                            DroiLog.m2870e(f2569b, "Drid: " + header);
                            if (responseBody != null) {
                                responseBody.close();
                            }
                            obj2 = obj;
                        } catch (Exception e2) {
                            DroiLog.m2873w(f2569b, e2);
                            response.f2544b = com.tyd.aidlservice.internal.Constants.NETWORK_ERROR;
                            response.f2545c = i3;
                            a.m2522e();
                            if (responseBody != null) {
                                responseBody.close();
                            }
                            obj2 = obj;
                        } catch (Throwable th) {
                            if (responseBody != null) {
                                responseBody.close();
                            }
                        }
                    }
                }
                i++;
                obj = obj2;
            }
            return response;
        }
        response.f2544b = com.tyd.aidlservice.internal.Constants.NO_NETWORK;
        return response;
    }

    byte[] unpackUdpPackets(byte[] bArr, int i, DroiError droiError) {
        if (droiError == null) {
            droiError = new DroiError();
        }
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        byte[] DecapsulateSecUDP = Tutil.DecapsulateSecUDP(bArr, i, atomicInteger);
        int i2 = atomicInteger.get();
        if (i2 != 0) {
            droiError.setCode(i2);
        }
        return DecapsulateSecUDP;
    }

    DroiError validate() {
        DroiError droiError = new DroiError();
        if (m2554g() || this.f2573f == null || !this.f2573f.m2529c()) {
            AtomicInteger atomicInteger = new AtomicInteger();
            C0827d a = m2536a(true, atomicInteger, CorePriv.f2828b);
            int i;
            if (a == null) {
                i = atomicInteger.get();
                if (i == com.tyd.aidlservice.internal.Constants.APP_ID_ERROR || i == com.tyd.aidlservice.internal.Constants.APP_ID_INVALID) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("Invalid application id.");
                } else {
                    droiError.setCode(DroiError.SERVER_NOT_REACHABLE);
                }
            } else {
                C0826c f = a.m2523f();
                if (f == null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("No available server");
                } else {
                    C0829f c0829f = new C0829f();
                    AtomicInteger atomicInteger2 = new AtomicInteger(0);
                    synchronized (this.f2572e) {
                        if (m2554g()) {
                            Tutil.SetFakeKlKey(Tutil.GetKlKeyUID_u(), Tutil.GetKlKeyUID_l());
                        }
                        i = m2532a(f, c0829f, false, atomicInteger2);
                    }
                    if (c0829f.m2529c()) {
                        this.f2573f = c0829f;
                        if (this.f2573f.m2529c()) {
                            this.f2573f.m2530d();
                        }
                    }
                    if (i != 0) {
                        droiError.setCode(i);
                        if (atomicInteger2.get() < 0) {
                            droiError.setAppendedMessage("DroiStatus: " + atomicInteger2.get());
                        }
                    } else {
                        m2552e();
                    }
                }
            }
        }
        return droiError;
    }
}
