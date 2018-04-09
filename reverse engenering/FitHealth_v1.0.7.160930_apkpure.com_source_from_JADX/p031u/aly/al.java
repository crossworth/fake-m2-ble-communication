package p031u.aly;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.C0919a;
import com.umeng.analytics.C0920b;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;

/* compiled from: NetworkHelper */
public class al {
    private String f3515a;
    private String f3516b = "10.0.0.172";
    private int f3517c = 80;
    private Context f3518d;
    private aj f3519e;

    public al(Context context) {
        this.f3518d = context;
        this.f3515a = m3439a(context);
    }

    public void m3443a(aj ajVar) {
        this.f3519e = ajVar;
    }

    private void m3440a() {
        bl.m3576b("constructURLS");
        String b = C1527x.m3942a(this.f3518d).m3950b().m3934b("");
        String a = C1527x.m3942a(this.f3518d).m3950b().m3929a("");
        if (!TextUtils.isEmpty(b)) {
            C0919a.f3109f = C0920b.m3067b(b);
        }
        if (!TextUtils.isEmpty(a)) {
            C0919a.f3110g = C0920b.m3067b(a);
        }
        C0919a.f3112i = new String[]{C0919a.f3109f, C0919a.f3110g};
        if (bj.m3543q(this.f3518d)) {
            C0919a.f3112i = new String[]{C0919a.f3110g, C0919a.f3109f};
        } else {
            int b2 = aw.m5107a(this.f3518d).m5116b();
            if (b2 != -1) {
                if (b2 == 0) {
                    C0919a.f3112i = new String[]{C0919a.f3109f, C0919a.f3110g};
                } else if (b2 == 1) {
                    C0919a.f3112i = new String[]{C0919a.f3110g, C0919a.f3109f};
                }
            }
        }
        bl.m3576b("constructURLS list size:" + C0919a.f3112i.length);
    }

    public byte[] m3444a(byte[] bArr) {
        byte[] bArr2 = null;
        for (String a : C0919a.f3112i) {
            bArr2 = m3441a(bArr, a);
            if (bArr2 != null) {
                if (this.f3519e != null) {
                    this.f3519e.mo2758c();
                }
                return bArr2;
            }
            if (this.f3519e != null) {
                this.f3519e.mo2759d();
            }
        }
        return bArr2;
    }

    private boolean m3442b() {
        if (this.f3518d.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.f3518d.getPackageName()) != 0) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.f3518d.getSystemService("connectivity");
            if (!bj.m3518a(this.f3518d, "android.permission.ACCESS_NETWORK_STATE")) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (!(activeNetworkInfo == null || activeNetworkInfo.getType() == 1)) {
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (extraInfo != null && (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap"))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] m3441a(byte[] bArr, String str) {
        HttpURLConnection httpURLConnection;
        Throwable e;
        try {
            if (this.f3519e != null) {
                this.f3519e.mo2756a();
            }
            if (m3442b()) {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection(new Proxy(Type.HTTP, new InetSocketAddress(this.f3516b, this.f3517c)));
            } else {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            }
            InputStream inputStream;
            try {
                httpURLConnection.setRequestProperty("X-Umeng-UTC", String.valueOf(System.currentTimeMillis()));
                httpURLConnection.setRequestProperty("X-Umeng-Sdk", this.f3515a);
                httpURLConnection.setRequestProperty("Msg-Type", "envelope/json");
                httpURLConnection.setRequestProperty("Content-Type", "envelope/json");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                if (Integer.parseInt(VERSION.SDK) < 8) {
                    System.setProperty("http.keepAlive", "false");
                }
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(bArr);
                outputStream.flush();
                outputStream.close();
                if (this.f3519e != null) {
                    this.f3519e.mo2757b();
                }
                int responseCode = httpURLConnection.getResponseCode();
                Object headerField = httpURLConnection.getHeaderField("Content-Type");
                if (TextUtils.isEmpty(headerField) || !headerField.equalsIgnoreCase("application/thrift")) {
                    headerField = null;
                } else {
                    headerField = 1;
                }
                if (responseCode != 200 || r0 == null) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return null;
                }
                bl.m3582c("Send message to " + str);
                inputStream = httpURLConnection.getInputStream();
                byte[] b = bk.m3565b(inputStream);
                bk.m3567c(inputStream);
                if (httpURLConnection == null) {
                    return b;
                }
                httpURLConnection.disconnect();
                return b;
            } catch (Exception e2) {
                e = e2;
                try {
                    bl.m3596e("IOException,Failed to send message.", e);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return null;
                } catch (Throwable th) {
                    e = th;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                bk.m3567c(inputStream);
            }
        } catch (Exception e3) {
            e = e3;
            httpURLConnection = null;
            bl.m3596e("IOException,Failed to send message.", e);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable th3) {
            e = th3;
            httpURLConnection = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw e;
        }
    }

    private String m3439a(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Android");
        stringBuffer.append("/");
        stringBuffer.append(C0919a.f3106c);
        stringBuffer.append(" ");
        try {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(bj.m3501B(context));
            stringBuffer2.append("/");
            stringBuffer2.append(bj.m3528d(context));
            stringBuffer2.append(" ");
            stringBuffer2.append(Build.MODEL);
            stringBuffer2.append("/");
            stringBuffer2.append(VERSION.RELEASE);
            stringBuffer2.append(" ");
            stringBuffer2.append(bk.m3557a(AnalyticsConfig.getAppkey(context)));
            stringBuffer.append(URLEncoder.encode(stringBuffer2.toString(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
