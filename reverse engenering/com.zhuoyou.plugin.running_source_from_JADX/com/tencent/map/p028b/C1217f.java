package com.tencent.map.p028b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import com.baidu.mapapi.map.WeightedLatLng;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.tencent.map.p026a.p027a.C1191b;
import com.tencent.map.p026a.p027a.C1192c;
import com.tencent.map.p026a.p027a.C1193d;
import com.tencent.map.p028b.C1198b.C1196a;
import com.tencent.map.p028b.C1206d.C1204b;
import com.tencent.map.p028b.C1206d.C1205c;
import com.tencent.map.p028b.C1210e.C1208b;
import com.tencent.map.p028b.C1210e.C1209c;
import com.tencent.map.p028b.C1221g.C1216c;
import com.tencent.map.p028b.C1221g.C1220b;
import com.umeng.socialize.common.SocializeConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public final class C1217f implements C1196a, C1205c, C1209c, C1216c {
    private static boolean f3833t = false;
    private static C1217f f3834u = null;
    private C1193d f3835A;
    private int f3836B;
    private int f3837C;
    private int f3838D;
    private String f3839E;
    private String f3840F;
    private String f3841G;
    private String f3842H;
    private String f3843I;
    private String f3844J;
    private boolean f3845K;
    private boolean f3846L;
    private long f3847M;
    private Handler f3848N;
    private Runnable f3849O;
    private final BroadcastReceiver f3850P;
    private long f3851a;
    private Context f3852b;
    private C1210e f3853c;
    private C1206d f3854d;
    private C1221g f3855e;
    private int f3856f;
    private int f3857g;
    private C1201c f3858h;
    private C1198b f3859i;
    private C1191b f3860j;
    private int f3861k;
    private int f3862l;
    private int f3863m;
    private byte[] f3864n;
    private byte[] f3865o;
    private boolean f3866p;
    private C1215c f3867q;
    private C1214b f3868r;
    private C1213a f3869s;
    private long f3870v;
    private C1208b f3871w;
    private C1204b f3872x;
    private C1220b f3873y;
    private C1193d f3874z;

    class C12111 implements Runnable {
        private /* synthetic */ C1217f f3822a;

        C12111(C1217f c1217f) {
            this.f3822a = c1217f;
        }

        public final void run() {
            if (System.currentTimeMillis() - this.f3822a.f3847M >= 8000) {
                if (this.f3822a.f3855e.m3617b() && this.f3822a.f3855e.m3618c()) {
                    this.f3822a.f3855e.m3615a(0);
                } else {
                    this.f3822a.m3576d();
                }
            }
        }
    }

    class C12122 extends BroadcastReceiver {
        private /* synthetic */ C1217f f3823a;

        C12122(C1217f c1217f) {
            this.f3823a = c1217f;
        }

        public final void onReceive(Context context, Intent intent) {
            if (!intent.getBooleanExtra("noConnectivity", false) && this.f3823a.f3867q != null) {
                this.f3823a.f3867q.sendEmptyMessage(256);
            }
        }
    }

    class C1213a extends Thread {
        private C1208b f3824a = null;
        private C1204b f3825b = null;
        private C1220b f3826c = null;
        private /* synthetic */ C1217f f3827d;

        C1213a(C1217f c1217f, C1208b c1208b, C1204b c1204b, C1220b c1220b) {
            this.f3827d = c1217f;
            if (c1208b != null) {
                this.f3824a = (C1208b) c1208b.clone();
            }
            if (c1204b != null) {
                this.f3825b = (C1204b) c1204b.clone();
            }
            if (c1220b != null) {
                this.f3826c = (C1220b) c1220b.clone();
            }
        }

        public final void run() {
            if (!C1217f.f3833t) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) this.f3827d.f3852b.getSystemService("phone");
                    this.f3827d.f3839E = telephonyManager.getDeviceId();
                    this.f3827d.f3840F = telephonyManager.getSubscriberId();
                    this.f3827d.f3841G = telephonyManager.getLine1Number();
                    Pattern compile = Pattern.compile("[0-9a-zA-Z+-]*");
                    this.f3827d.f3839E = this.f3827d.f3839E == null ? "" : this.f3827d.f3839E;
                    if (compile.matcher(this.f3827d.f3839E).matches()) {
                        this.f3827d.f3839E = this.f3827d.f3839E == null ? "" : this.f3827d.f3839E;
                    } else {
                        this.f3827d.f3839E = "";
                    }
                    this.f3827d.f3840F = this.f3827d.f3840F == null ? "" : this.f3827d.f3840F;
                    if (compile.matcher(this.f3827d.f3840F).matches()) {
                        this.f3827d.f3840F = this.f3827d.f3840F == null ? "" : this.f3827d.f3840F;
                    } else {
                        this.f3827d.f3840F = "";
                    }
                    this.f3827d.f3841G = this.f3827d.f3841G == null ? "" : this.f3827d.f3841G;
                    if (compile.matcher(this.f3827d.f3841G).matches()) {
                        this.f3827d.f3841G = this.f3827d.f3841G == null ? "" : this.f3827d.f3841G;
                    } else {
                        this.f3827d.f3841G = "";
                    }
                } catch (Exception e) {
                }
                C1217f.f3833t = true;
                this.f3827d.f3839E = this.f3827d.f3839E == null ? "" : this.f3827d.f3839E;
                this.f3827d.f3840F = this.f3827d.f3840F == null ? "" : this.f3827d.f3840F;
                this.f3827d.f3841G = this.f3827d.f3841G == null ? "" : this.f3827d.f3841G;
                this.f3827d.f3843I = C1224j.m3631a(this.f3827d.f3839E == null ? "0123456789ABCDEF" : this.f3827d.f3839E);
            }
            String a = this.f3827d.f3857g == 4 ? C1223i.m3625a(this.f3826c) : "[]";
            String a2 = C1223i.m3623a(this.f3825b, this.f3827d.f3854d.m3529b());
            String a3 = C1223i.m3626a(this.f3827d.f3839E, this.f3827d.f3840F, this.f3827d.f3841G, this.f3827d.f3842H, this.f3827d.f3845K);
            String a4 = (this.f3824a == null || !this.f3824a.m3530a()) ? "{}" : C1223i.m3624a(this.f3824a);
            this.f3827d.f3867q.sendMessage(this.f3827d.f3867q.obtainMessage(16, (("{\"version\":\"1.1.8\",\"address\":" + this.f3827d.f3862l) + ",\"source\":203,\"access_token\":\"" + this.f3827d.f3843I + "\",\"app_name\":" + "\"" + this.f3827d.f3844J + "\",\"bearing\":1") + ",\"attribute\":" + a3 + ",\"location\":" + a4 + ",\"cells\":" + a2 + ",\"wifis\":" + a + "}"));
        }
    }

    class C1214b extends Thread {
        private String f3828a = null;
        private String f3829b = null;
        private String f3830c = null;
        private /* synthetic */ C1217f f3831d;

        public C1214b(C1217f c1217f, String str) {
            this.f3831d = c1217f;
            this.f3828a = str;
            this.f3829b = (c1217f.f3838D == 0 ? "http://lstest.map.soso.com/loc?c=1" : "http://lbs.map.qq.com/loc?c=1") + "&mars=" + c1217f.f3863m;
        }

        private String m3550a(byte[] bArr, String str) {
            this.f3831d.f3847M = System.currentTimeMillis();
            StringBuffer stringBuffer = new StringBuffer();
            try {
                stringBuffer.append(new String(bArr, str));
                return stringBuffer.toString();
            } catch (Exception e) {
                return null;
            }
        }

        public final void run() {
            Message message = new Message();
            message.what = 8;
            try {
                byte[] a = C1224j.m3632a(this.f3828a.getBytes());
                this.f3831d.f3866p = true;
                C1229n a2 = C1198b.m3505a(this.f3829b, "SOSO MAP LBS SDK", a);
                this.f3831d.f3866p = false;
                this.f3830c = m3550a(C1224j.m3633b(a2.f3927a), a2.f3928b);
                if (this.f3830c != null) {
                    message.arg1 = 0;
                    message.obj = this.f3830c;
                } else {
                    message.arg1 = 1;
                }
            } catch (Exception e) {
                int i = 0;
                while (true) {
                    i++;
                    if (i > 3) {
                        break;
                    }
                    try {
                        C1214b.sleep(1000);
                        byte[] a3 = C1224j.m3632a(this.f3828a.getBytes());
                        this.f3831d.f3866p = true;
                        C1229n a4 = C1198b.m3505a(this.f3829b, "SOSO MAP LBS SDK", a3);
                        this.f3831d.f3866p = false;
                        this.f3830c = m3550a(C1224j.m3633b(a4.f3927a), a4.f3928b);
                        if (this.f3830c != null) {
                            message.arg1 = 0;
                            message.obj = this.f3830c;
                        } else {
                            message.arg1 = 1;
                        }
                    } catch (Exception e2) {
                    }
                }
                this.f3831d.f3866p = false;
                message.arg1 = 1;
            }
            C1217f.m3585j(this.f3831d);
            this.f3831d.f3867q.sendMessage(message);
        }
    }

    class C1215c extends Handler {
        private /* synthetic */ C1217f f3832a;

        public C1215c(C1217f c1217f) {
            this.f3832a = c1217f;
            super(Looper.getMainLooper());
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    C1217f.m3561a(this.f3832a, (C1208b) message.obj);
                    return;
                case 2:
                    C1217f.m3560a(this.f3832a, (C1204b) message.obj);
                    return;
                case 3:
                    C1217f.m3562a(this.f3832a, (C1220b) message.obj);
                    return;
                case 4:
                    C1217f.m3558a(this.f3832a, message.arg1);
                    return;
                case 5:
                    C1217f.m3568b(this.f3832a, message.arg1);
                    return;
                case 6:
                    C1217f.m3559a(this.f3832a, (Location) message.obj);
                    return;
                case 8:
                    if (message.arg1 == 0) {
                        this.f3832a.m3564a((String) message.obj);
                        return;
                    } else if (this.f3832a.f3871w == null || !this.f3832a.f3871w.m3530a()) {
                        this.f3832a.m3579e();
                        return;
                    } else {
                        return;
                    }
                case 16:
                    if (message.obj != null) {
                        C1217f.m3563a(this.f3832a, (String) message.obj);
                        this.f3832a.f3869s = null;
                        return;
                    }
                    return;
                case 256:
                    if (this.f3832a.f3836B == 1) {
                        this.f3832a.m3576d();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private C1217f() {
        this.f3851a = 5000;
        this.f3852b = null;
        this.f3853c = null;
        this.f3854d = null;
        this.f3855e = null;
        this.f3856f = 1024;
        this.f3857g = 4;
        this.f3858h = null;
        this.f3859i = null;
        this.f3860j = null;
        this.f3864n = new byte[0];
        this.f3865o = new byte[0];
        this.f3866p = false;
        this.f3867q = null;
        this.f3868r = null;
        this.f3869s = null;
        this.f3870v = -1;
        this.f3871w = null;
        this.f3872x = null;
        this.f3873y = null;
        this.f3874z = null;
        this.f3835A = null;
        this.f3836B = 0;
        this.f3837C = 0;
        this.f3838D = 1;
        this.f3839E = "";
        this.f3840F = "";
        this.f3841G = "";
        this.f3842H = "";
        this.f3843I = "";
        this.f3844J = "";
        this.f3845K = false;
        this.f3846L = false;
        this.f3847M = 0;
        this.f3848N = null;
        this.f3849O = new C12111(this);
        this.f3850P = new C12122(this);
        this.f3853c = new C1210e();
        this.f3854d = new C1206d();
        this.f3855e = new C1221g();
    }

    public static synchronized C1217f m3556a() {
        C1217f c1217f;
        synchronized (C1217f.class) {
            if (f3834u == null) {
                f3834u = new C1217f();
            }
            c1217f = f3834u;
        }
        return c1217f;
    }

    private static ArrayList<C1192c> m3557a(JSONArray jSONArray) throws Exception {
        int length = jSONArray.length();
        ArrayList<C1192c> arrayList = new ArrayList();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            arrayList.add(new C1192c(jSONObject.getString("name"), jSONObject.getString("addr"), jSONObject.getString("catalog"), jSONObject.getDouble("dist"), Double.parseDouble(jSONObject.getString(ParamKey.LATITUDE)), Double.parseDouble(jSONObject.getString(ParamKey.LONGITUDE))));
        }
        return arrayList;
    }

    static /* synthetic */ void m3558a(C1217f c1217f, int i) {
        if (i == 0) {
            c1217f.f3871w = null;
        }
        c1217f.f3856f = i == 0 ? 1 : 2;
        if (c1217f.f3860j != null) {
            c1217f.f3860j.mo2209a(c1217f.f3856f);
        }
    }

    static /* synthetic */ void m3559a(C1217f c1217f, Location location) {
        if (location == null || location.getLatitude() > 359.0d || location.getLongitude() > 359.0d) {
            if (c1217f.f3871w == null || !c1217f.f3871w.m3530a()) {
                c1217f.m3579e();
            } else {
                c1217f.m3570b(true);
            }
        }
        c1217f.f3874z = new C1193d();
        c1217f.f3874z.f3747z = 0;
        c1217f.f3874z.f3746y = 0;
        c1217f.f3874z.f3723b = C1223i.m3620a(location.getLatitude(), 6);
        c1217f.f3874z.f3724c = C1223i.m3620a(location.getLongitude(), 6);
        if (c1217f.f3871w != null && c1217f.f3871w.m3530a()) {
            c1217f.f3874z.f3726e = C1223i.m3620a((double) c1217f.f3871w.m3531b().getAccuracy(), 1);
            c1217f.f3874z.f3725d = C1223i.m3620a(c1217f.f3871w.m3531b().getAltitude(), 1);
            c1217f.f3874z.f3727f = C1223i.m3620a((double) c1217f.f3871w.m3531b().getSpeed(), 1);
            c1217f.f3874z.f3728g = C1223i.m3620a((double) c1217f.f3871w.m3531b().getBearing(), 1);
            c1217f.f3874z.f3722a = 0;
        }
        c1217f.f3874z.f3745x = true;
        if (!(c1217f.f3862l == 0 || c1217f.f3835A == null || c1217f.f3836B != 0)) {
            if ((c1217f.f3862l == 3 || c1217f.f3862l == 4) && c1217f.f3862l == c1217f.f3835A.f3747z) {
                c1217f.f3874z.f3730i = c1217f.f3835A.f3730i;
                c1217f.f3874z.f3731j = c1217f.f3835A.f3731j;
                c1217f.f3874z.f3732k = c1217f.f3835A.f3732k;
                c1217f.f3874z.f3733l = c1217f.f3835A.f3733l;
                c1217f.f3874z.f3734m = c1217f.f3835A.f3734m;
                c1217f.f3874z.f3735n = c1217f.f3835A.f3735n;
                c1217f.f3874z.f3736o = c1217f.f3835A.f3736o;
                c1217f.f3874z.f3737p = c1217f.f3835A.f3737p;
                c1217f.f3874z.f3747z = 3;
            }
            if (c1217f.f3862l == 4 && c1217f.f3862l == c1217f.f3835A.f3747z && c1217f.f3835A.f3744w != null) {
                c1217f.f3874z.f3744w = new ArrayList();
                Iterator it = c1217f.f3835A.f3744w.iterator();
                while (it.hasNext()) {
                    c1217f.f3874z.f3744w.add(new C1192c((C1192c) it.next()));
                }
                c1217f.f3874z.f3747z = 4;
            }
            if (c1217f.f3862l == 7 && c1217f.f3862l == c1217f.f3835A.f3747z) {
                c1217f.f3874z.f3747z = 7;
                c1217f.f3874z.f3729h = c1217f.f3835A.f3729h;
                c1217f.f3874z.f3730i = c1217f.f3835A.f3730i;
                if (c1217f.f3835A.f3729h == 0) {
                    c1217f.f3874z.f3731j = c1217f.f3835A.f3731j;
                    c1217f.f3874z.f3732k = c1217f.f3835A.f3732k;
                    c1217f.f3874z.f3733l = c1217f.f3835A.f3733l;
                    c1217f.f3874z.f3734m = c1217f.f3835A.f3734m;
                    c1217f.f3874z.f3735n = c1217f.f3835A.f3735n;
                    c1217f.f3874z.f3736o = c1217f.f3835A.f3736o;
                    c1217f.f3874z.f3737p = c1217f.f3835A.f3737p;
                } else {
                    c1217f.f3874z.f3738q = c1217f.f3835A.f3738q;
                    c1217f.f3874z.f3739r = c1217f.f3835A.f3739r;
                    c1217f.f3874z.f3740s = c1217f.f3835A.f3740s;
                    c1217f.f3874z.f3741t = c1217f.f3835A.f3741t;
                    c1217f.f3874z.f3742u = c1217f.f3835A.f3742u;
                    c1217f.f3874z.f3743v = c1217f.f3835A.f3743v;
                }
            }
        }
        if (c1217f.f3836B != 0 || c1217f.f3835A != null) {
            if (c1217f.f3836B != 0) {
                c1217f.f3874z.f3746y = c1217f.f3836B;
            }
            if (System.currentTimeMillis() - c1217f.f3870v >= c1217f.f3851a && c1217f.f3860j != null && c1217f.f3861k == 1) {
                c1217f.f3860j.mo2210a(c1217f.f3874z);
                c1217f.f3870v = System.currentTimeMillis();
            }
        }
    }

    static /* synthetic */ void m3560a(C1217f c1217f, C1204b c1204b) {
        c1217f.f3872x = c1204b;
        if (c1217f.f3855e != null && c1217f.f3855e.m3617b() && c1217f.f3855e.m3618c()) {
            c1217f.f3855e.m3615a(0);
            return;
        }
        if (c1217f.f3837C > 0 && !C1223i.m3627a(c1204b.f3787a, c1204b.f3788b, c1204b.f3789c, c1204b.f3790d, c1204b.f3791e)) {
            c1217f.f3837C--;
        }
        c1217f.m3576d();
    }

    static /* synthetic */ void m3561a(C1217f c1217f, C1208b c1208b) {
        if (c1208b != null) {
            c1217f.f3871w = c1208b;
            if (c1217f.f3861k != 1 || c1217f.f3871w == null || !c1217f.f3871w.m3530a()) {
                return;
            }
            if (c1217f.f3863m == 0) {
                c1217f.m3570b(false);
            } else if (c1217f.f3863m == 1 && c1217f.f3859i != null) {
                C1198b c1198b = c1217f.f3859i;
                double latitude = c1217f.f3871w.m3531b().getLatitude();
                double longitude = c1217f.f3871w.m3531b().getLongitude();
                Context context = c1217f.f3852b;
                c1198b.m3509a(latitude, longitude, (C1196a) c1217f);
            }
        }
    }

    static /* synthetic */ void m3562a(C1217f c1217f, C1220b c1220b) {
        if (c1220b != null) {
            c1217f.f3873y = c1220b;
            c1217f.m3576d();
        }
    }

    static /* synthetic */ void m3563a(C1217f c1217f, String str) {
        if (C1223i.m3630c(str)) {
            if (c1217f.f3861k != 0 || c1217f.f3860j == null) {
                String b = c1217f.f3858h == null ? null : (c1217f.f3872x == null || c1217f.f3873y == null) ? null : c1217f.f3858h.m3515b(c1217f.f3872x.f3788b, c1217f.f3872x.f3789c, c1217f.f3872x.f3790d, c1217f.f3872x.f3791e, c1217f.f3873y.m3607a());
                if (b != null) {
                    c1217f.m3564a(b);
                    return;
                }
                if (!(c1217f.f3858h == null || c1217f.f3872x == null || c1217f.f3873y == null)) {
                    c1217f.f3858h.m3513a(c1217f.f3872x.f3788b, c1217f.f3872x.f3789c, c1217f.f3872x.f3790d, c1217f.f3872x.f3791e, c1217f.f3873y.m3607a());
                }
                if (!c1217f.f3866p) {
                    if (c1217f.f3868r != null) {
                        c1217f.f3868r.interrupt();
                    }
                    c1217f.f3868r = null;
                    c1217f.f3868r = new C1214b(c1217f, str);
                    c1217f.f3868r.start();
                    return;
                }
                return;
            }
            byte[] bytes;
            try {
                bytes = str.getBytes();
            } catch (Exception e) {
                bytes = null;
            }
            c1217f.f3860j.mo2211a(bytes, 0);
        } else if (c1217f.f3837C > 0) {
            c1217f.f3837C--;
        } else if (c1217f.f3861k == 0 && c1217f.f3860j != null) {
            c1217f.f3860j.mo2211a(null, -1);
        } else if (c1217f.f3861k == 1 && c1217f.f3860j != null) {
            c1217f.f3874z = new C1193d();
            c1217f.f3836B = 3;
            c1217f.f3874z.f3746y = 3;
            c1217f.f3874z.f3747z = -1;
            c1217f.f3860j.mo2210a(c1217f.f3874z);
        }
    }

    private void m3564a(String str) {
        int i = 0;
        try {
            double d;
            this.f3874z = new C1193d();
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.getJSONObject(SocializeConstants.KEY_LOCATION);
            this.f3874z.f3722a = 1;
            this.f3874z.f3723b = C1223i.m3620a(jSONObject2.getDouble(ParamKey.LATITUDE), 6);
            this.f3874z.f3724c = C1223i.m3620a(jSONObject2.getDouble(ParamKey.LONGITUDE), 6);
            this.f3874z.f3725d = C1223i.m3620a(jSONObject2.getDouble("altitude"), 1);
            this.f3874z.f3726e = C1223i.m3620a(jSONObject2.getDouble("accuracy"), 1);
            this.f3874z.f3745x = this.f3863m == 1;
            String string = jSONObject.getString("bearing");
            int i2 = -100;
            if (string != null && string.split(",").length > 1) {
                i = Integer.parseInt(string.split(",")[1]);
            }
            if (this.f3872x != null) {
                i2 = this.f3872x.f3792f;
            }
            C1193d c1193d = this.f3874z;
            double d2 = this.f3874z.f3726e;
            if (i >= 6) {
                d = 40.0d;
            } else if (i == 5) {
                d = 60.0d;
            } else if (i == 4) {
                d = 70.0d;
            } else if (i == 3) {
                d = 90.0d;
            } else if (i == 2) {
                d = 110.0d;
            } else {
                i2 = (i2 < -72 || i != 0) ? d2 <= 100.0d ? ((int) (((d2 - WeightedLatLng.DEFAULT_INTENSITY) / 10.0d) + WeightedLatLng.DEFAULT_INTENSITY)) * 10 : (d2 <= 100.0d || d2 > 800.0d) ? ((int) ((0.8d * d2) / 10.0d)) * 10 : ((int) ((0.85d * d2) / 10.0d)) * 10 : ((int) ((0.45d * d2) / 10.0d)) * 10;
                d = (double) i2;
            }
            c1193d.f3726e = d;
            this.f3874z.f3747z = 0;
            if ((this.f3862l == 3 || this.f3862l == 4) && this.f3863m == 1) {
                jSONObject2 = jSONObject.getJSONObject("details").getJSONObject("subnation");
                this.f3874z.m3497a(jSONObject2.getString("name"));
                this.f3874z.f3734m = jSONObject2.getString("town");
                this.f3874z.f3735n = jSONObject2.getString("village");
                this.f3874z.f3736o = jSONObject2.getString("street");
                this.f3874z.f3737p = jSONObject2.getString("street_no");
                this.f3874z.f3747z = 3;
                this.f3874z.f3729h = 0;
            }
            if (this.f3862l == 4 && this.f3863m == 1) {
                this.f3874z.f3744w = C1217f.m3557a(jSONObject.getJSONObject("details").getJSONArray("poilist"));
                this.f3874z.f3747z = 4;
            }
            if (this.f3862l == 7 && this.f3863m == 1) {
                jSONObject2 = jSONObject.getJSONObject("details");
                i = jSONObject2.getInt("stat");
                jSONObject2 = jSONObject2.getJSONObject("subnation");
                if (i == 0) {
                    this.f3874z.m3497a(jSONObject2.getString("name"));
                    this.f3874z.f3734m = jSONObject2.getString("town");
                    this.f3874z.f3735n = jSONObject2.getString("village");
                    this.f3874z.f3736o = jSONObject2.getString("street");
                    this.f3874z.f3737p = jSONObject2.getString("street_no");
                } else if (i == 1) {
                    this.f3874z.f3730i = jSONObject2.getString("nation");
                    this.f3874z.f3738q = jSONObject2.getString("admin_level_1");
                    this.f3874z.f3739r = jSONObject2.getString("admin_level_2");
                    this.f3874z.f3740s = jSONObject2.getString("admin_level_3");
                    this.f3874z.f3741t = jSONObject2.getString("locality");
                    this.f3874z.f3742u = jSONObject2.getString("sublocality");
                    this.f3874z.f3743v = jSONObject2.getString("route");
                }
                this.f3874z.f3729h = i;
                this.f3874z.f3747z = 7;
            }
            this.f3874z.f3746y = 0;
            this.f3835A = new C1193d(this.f3874z);
            this.f3836B = 0;
            if (this.f3858h != null) {
                this.f3858h.m3514a(str);
            }
        } catch (Exception e) {
            this.f3874z = new C1193d();
            this.f3874z.f3747z = -1;
            this.f3874z.f3746y = 2;
            this.f3836B = 2;
        }
        if (this.f3860j != null && this.f3861k == 1) {
            if (this.f3871w == null || !this.f3871w.m3530a()) {
                this.f3860j.mo2210a(this.f3874z);
                this.f3870v = System.currentTimeMillis();
            }
        }
    }

    static /* synthetic */ void m3568b(C1217f c1217f, int i) {
        int i2 = 3;
        if (i == 3) {
            i2 = 4;
        }
        c1217f.f3857g = i2;
        if (c1217f.f3860j != null) {
            c1217f.f3860j.mo2209a(c1217f.f3857g);
        }
    }

    private void m3570b(boolean z) {
        if (this.f3871w != null && this.f3871w.m3530a()) {
            Location b = this.f3871w.m3531b();
            this.f3874z = new C1193d();
            this.f3874z.f3723b = C1223i.m3620a(b.getLatitude(), 6);
            this.f3874z.f3724c = C1223i.m3620a(b.getLongitude(), 6);
            this.f3874z.f3725d = C1223i.m3620a(b.getAltitude(), 1);
            this.f3874z.f3726e = C1223i.m3620a((double) b.getAccuracy(), 1);
            this.f3874z.f3727f = C1223i.m3620a((double) b.getSpeed(), 1);
            this.f3874z.f3728g = C1223i.m3620a((double) b.getBearing(), 1);
            this.f3874z.f3722a = 0;
            this.f3874z.f3745x = false;
            if (z) {
                this.f3874z.f3746y = 1;
            } else {
                this.f3874z.f3746y = 0;
            }
            this.f3874z.f3747z = 0;
            this.f3835A = new C1193d(this.f3874z);
            this.f3836B = 0;
            if (System.currentTimeMillis() - this.f3870v >= this.f3851a && this.f3860j != null && this.f3861k == 1) {
                this.f3860j.mo2210a(this.f3874z);
                this.f3870v = System.currentTimeMillis();
            }
        }
    }

    private void m3576d() {
        if (this.f3869s == null) {
            this.f3869s = new C1213a(this, this.f3871w, this.f3872x, this.f3873y);
            this.f3869s.start();
        }
    }

    private void m3579e() {
        this.f3874z = new C1193d();
        this.f3836B = 1;
        this.f3874z.f3746y = 1;
        this.f3874z.f3747z = -1;
        this.f3874z.f3722a = 1;
        if (this.f3860j != null && this.f3861k == 1) {
            this.f3860j.mo2210a(this.f3874z);
        }
    }

    static /* synthetic */ void m3585j(C1217f c1217f) {
    }

    public final void mo2153a(double d, double d2) {
        synchronized (this.f3865o) {
            Message obtainMessage = this.f3867q.obtainMessage(6);
            Location location = new Location("Deflect");
            location.setLatitude(d);
            location.setLongitude(d2);
            obtainMessage.obj = location;
            this.f3867q.sendMessage(obtainMessage);
        }
    }

    public final void mo2154a(int i) {
        synchronized (this.f3865o) {
            this.f3867q.sendMessage(this.f3867q.obtainMessage(4, i, 0));
        }
    }

    public final void mo2155a(C1204b c1204b) {
        synchronized (this.f3865o) {
            this.f3867q.sendMessage(this.f3867q.obtainMessage(2, c1204b));
        }
    }

    public final void mo2156a(C1208b c1208b) {
        synchronized (this.f3865o) {
            this.f3867q.sendMessage(this.f3867q.obtainMessage(1, c1208b));
        }
    }

    public final void mo2157a(C1220b c1220b) {
        synchronized (this.f3865o) {
            this.f3867q.sendMessage(this.f3867q.obtainMessage(3, c1220b));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m3602a(android.content.Context r9, com.tencent.map.p026a.p027a.C1191b r10) {
        /*
        r8 = this;
        r2 = 1;
        r1 = 0;
        r3 = r8.f3864n;
        monitor-enter(r3);
        if (r9 == 0) goto L_0x0009;
    L_0x0007:
        if (r10 != 0) goto L_0x000c;
    L_0x0009:
        monitor-exit(r3);
        r0 = r1;
    L_0x000b:
        return r0;
    L_0x000c:
        r0 = r8.f3844J;	 Catch:{ all -> 0x00e5 }
        r0 = com.tencent.map.p028b.C1223i.m3628a(r0);	 Catch:{ all -> 0x00e5 }
        if (r0 != 0) goto L_0x0017;
    L_0x0014:
        monitor-exit(r3);	 Catch:{ all -> 0x00e5 }
        r0 = r1;
        goto L_0x000b;
    L_0x0017:
        r0 = new com.tencent.map.b.f$c;	 Catch:{ all -> 0x00e5 }
        r0.<init>(r8);	 Catch:{ all -> 0x00e5 }
        r8.f3867q = r0;	 Catch:{ all -> 0x00e5 }
        r0 = new android.os.Handler;	 Catch:{ all -> 0x00e5 }
        r4 = android.os.Looper.getMainLooper();	 Catch:{ all -> 0x00e5 }
        r0.<init>(r4);	 Catch:{ all -> 0x00e5 }
        r8.f3848N = r0;	 Catch:{ all -> 0x00e5 }
        r8.f3852b = r9;	 Catch:{ all -> 0x00e5 }
        r8.f3860j = r10;	 Catch:{ all -> 0x00e5 }
        r0 = com.tencent.map.p028b.C1227l.m3644a();	 Catch:{ all -> 0x00e5 }
        r4 = r8.f3852b;	 Catch:{ all -> 0x00e5 }
        r4 = r4.getApplicationContext();	 Catch:{ all -> 0x00e5 }
        r0.m3648a(r4);	 Catch:{ all -> 0x00e5 }
        r0 = "connectivity";
        r0 = r9.getSystemService(r0);	 Catch:{ Exception -> 0x00e8 }
        r0 = (android.net.ConnectivityManager) r0;	 Catch:{ Exception -> 0x00e8 }
        if (r0 == 0) goto L_0x0054;
    L_0x0044:
        r4 = r0.getActiveNetworkInfo();	 Catch:{ Exception -> 0x00e8 }
        if (r4 == 0) goto L_0x0054;
    L_0x004a:
        r0 = r0.getActiveNetworkInfo();	 Catch:{ Exception -> 0x00e8 }
        r0 = r0.isRoaming();	 Catch:{ Exception -> 0x00e8 }
        r8.f3845K = r0;	 Catch:{ Exception -> 0x00e8 }
    L_0x0054:
        r0 = r8.f3852b;	 Catch:{ Exception -> 0x00e8 }
        r4 = r8.f3850P;	 Catch:{ Exception -> 0x00e8 }
        r5 = new android.content.IntentFilter;	 Catch:{ Exception -> 0x00e8 }
        r6 = "android.net.conn.CONNECTIVITY_CHANGE";
        r5.<init>(r6);	 Catch:{ Exception -> 0x00e8 }
        r0.registerReceiver(r4, r5);	 Catch:{ Exception -> 0x00e8 }
    L_0x0062:
        r0 = r8.f3860j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.m3490a();	 Catch:{ all -> 0x00e5 }
        r8.f3861k = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.f3860j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.m3494b();	 Catch:{ all -> 0x00e5 }
        r8.f3862l = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.f3860j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.m3495c();	 Catch:{ all -> 0x00e5 }
        r8.f3863m = r0;	 Catch:{ all -> 0x00e5 }
        r4 = -1;
        r8.f3870v = r4;	 Catch:{ all -> 0x00e5 }
        r0 = r8.f3862l;	 Catch:{ all -> 0x00e5 }
        r4 = 7;
        if (r0 != r4) goto L_0x0086;
    L_0x0083:
        r0 = 0;
        r8.f3862l = r0;	 Catch:{ all -> 0x00e5 }
    L_0x0086:
        r0 = 0;
        r8.f3846L = r0;	 Catch:{ all -> 0x00e5 }
        r0 = 1;
        r8.f3838D = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.f3853c;	 Catch:{ all -> 0x00e5 }
        r4 = r8.f3852b;	 Catch:{ all -> 0x00e5 }
        r0 = r0.m3549a(r8, r4);	 Catch:{ all -> 0x00e5 }
        r4 = r8.f3854d;	 Catch:{ all -> 0x00e5 }
        r5 = r8.f3852b;	 Catch:{ all -> 0x00e5 }
        r4 = r4.m3528a(r5, r8);	 Catch:{ all -> 0x00e5 }
        r5 = r8.f3855e;	 Catch:{ all -> 0x00e5 }
        r6 = r8.f3852b;	 Catch:{ all -> 0x00e5 }
        r7 = 1;
        r5 = r5.m3616a(r6, r8, r7);	 Catch:{ all -> 0x00e5 }
        r6 = com.tencent.map.p028b.C1201c.m3510a();	 Catch:{ all -> 0x00e5 }
        r8.f3858h = r6;	 Catch:{ all -> 0x00e5 }
        r6 = com.tencent.map.p028b.C1198b.m3504a();	 Catch:{ all -> 0x00e5 }
        r8.f3859i = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f3871w = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f3872x = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f3873y = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f3874z = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f3835A = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f3836B = r6;	 Catch:{ all -> 0x00e5 }
        r6 = r8.f3858h;	 Catch:{ all -> 0x00e5 }
        if (r6 == 0) goto L_0x00cc;
    L_0x00c7:
        r6 = r8.f3858h;	 Catch:{ all -> 0x00e5 }
        r6.m3516b();	 Catch:{ all -> 0x00e5 }
    L_0x00cc:
        r6 = 1;
        r8.f3837C = r6;	 Catch:{ all -> 0x00e5 }
        if (r0 == 0) goto L_0x00d9;
    L_0x00d1:
        r0 = r8.f3863m;	 Catch:{ all -> 0x00e5 }
        if (r0 != 0) goto L_0x00d9;
    L_0x00d5:
        monitor-exit(r3);	 Catch:{ all -> 0x00e5 }
        r0 = r2;
        goto L_0x000b;
    L_0x00d9:
        if (r4 != 0) goto L_0x00dd;
    L_0x00db:
        if (r5 == 0) goto L_0x00e1;
    L_0x00dd:
        monitor-exit(r3);
        r0 = r2;
        goto L_0x000b;
    L_0x00e1:
        monitor-exit(r3);
        r0 = r1;
        goto L_0x000b;
    L_0x00e5:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x00e8:
        r0 = move-exception;
        goto L_0x0062;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.f.a(android.content.Context, com.tencent.map.a.a.b):boolean");
    }

    public final boolean m3603a(String str, String str2) {
        boolean z;
        synchronized (this.f3864n) {
            if (C1195a.m3500a().m3501a(str, str2)) {
                this.f3844J = str;
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public final void m3604b() {
        synchronized (this.f3864n) {
            try {
                if (this.f3860j != null) {
                    this.f3860j = null;
                    this.f3848N.removeCallbacks(this.f3849O);
                    this.f3852b.unregisterReceiver(this.f3850P);
                    this.f3853c.m3548a();
                    this.f3854d.m3527a();
                    this.f3855e.m3614a();
                }
            } catch (Exception e) {
            }
        }
    }

    public final void mo2158b(int i) {
        synchronized (this.f3865o) {
            this.f3867q.sendMessage(this.f3867q.obtainMessage(5, i, 0));
        }
    }
}
