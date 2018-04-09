package com.tencent.map.p013b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import com.amap.api.maps.model.WeightedLatLng;
import com.tencent.map.p011a.p012a.C0715b;
import com.tencent.map.p011a.p012a.C0716c;
import com.tencent.map.p011a.p012a.C0717d;
import com.tencent.map.p013b.C0722b.C0720a;
import com.tencent.map.p013b.C0731e.C0728a;
import com.tencent.map.p013b.C0731e.C0729b;
import com.tencent.map.p013b.C0735f.C0732a;
import com.tencent.map.p013b.C0735f.C0733b;
import com.tencent.map.p013b.C0745m.C0742a;
import com.tencent.map.p013b.C0745m.C0743b;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public final class C1722n implements C0720a, C0729b, C0732a, C0742a {
    private static boolean f4560t = false;
    private static C1722n f4561u = null;
    private C0717d f4562A;
    private int f4563B;
    private int f4564C;
    private int f4565D;
    private String f4566E;
    private String f4567F;
    private String f4568G;
    private String f4569H;
    private String f4570I;
    private String f4571J;
    private boolean f4572K;
    private boolean f4573L;
    private long f4574M;
    private Handler f4575N;
    private Runnable f4576O;
    private final BroadcastReceiver f4577P;
    private long f4578a;
    private Context f4579b;
    private C0731e f4580c;
    private C0745m f4581d;
    private C0735f f4582e;
    private int f4583f;
    private int f4584g;
    private C0725c f4585h;
    private C0722b f4586i;
    private C0715b f4587j;
    private int f4588k;
    private int f4589l;
    private int f4590m;
    private byte[] f4591n;
    private byte[] f4592o;
    private boolean f4593p;
    private C0746a f4594q;
    private C0747b f4595r;
    private C0748c f4596s;
    private long f4597v;
    private C0728a f4598w;
    private C0743b f4599x;
    private C0733b f4600y;
    private C0717d f4601z;

    class C0746a extends Handler {
        private /* synthetic */ C1722n f2614a;

        public C0746a(C1722n c1722n) {
            this.f2614a = c1722n;
            super(Looper.getMainLooper());
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    C1722n.m4686a(this.f2614a, (C0728a) message.obj);
                    return;
                case 2:
                    C1722n.m4688a(this.f2614a, (C0743b) message.obj);
                    return;
                case 3:
                    C1722n.m4687a(this.f2614a, (C0733b) message.obj);
                    return;
                case 4:
                    C1722n.m4684a(this.f2614a, message.arg1);
                    return;
                case 5:
                    C1722n.m4694b(this.f2614a, message.arg1);
                    return;
                case 6:
                    C1722n.m4685a(this.f2614a, (Location) message.obj);
                    return;
                case 8:
                    if (message.arg1 == 0) {
                        this.f2614a.m4690a((String) message.obj);
                        return;
                    } else if (this.f2614a.f4598w == null || !this.f2614a.f4598w.m2428a()) {
                        this.f2614a.m4705e();
                        return;
                    } else {
                        return;
                    }
                case 16:
                    if (message.obj != null) {
                        C1722n.m4689a(this.f2614a, (String) message.obj);
                        this.f2614a.f4596s = null;
                        return;
                    }
                    return;
                case 256:
                    if (this.f2614a.f4563B == 1) {
                        this.f2614a.m4702d();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    class C0747b extends Thread {
        private String f2615a = null;
        private String f2616b = null;
        private String f2617c = null;
        private /* synthetic */ C1722n f2618d;

        public C0747b(C1722n c1722n, String str) {
            this.f2618d = c1722n;
            this.f2615a = str;
            this.f2616b = (c1722n.f4565D == 0 ? "http://lstest.map.soso.com/loc?c=1" : "http://lbs.map.qq.com/loc?c=1") + "&mars=" + c1722n.f4590m;
        }

        private String m2482a(byte[] bArr, String str) {
            this.f2618d.f4574M = System.currentTimeMillis();
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
                byte[] a = C0752r.m2485a(this.f2615a.getBytes());
                this.f2618d.f4593p = true;
                C0755u a2 = C0722b.m2406a(this.f2616b, "SOSO MAP LBS SDK", a);
                this.f2618d.f4593p = false;
                this.f2617c = m2482a(C0752r.m2486b(a2.f2630a), a2.f2631b);
                if (this.f2617c != null) {
                    message.arg1 = 0;
                    message.obj = this.f2617c;
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
                        C0747b.sleep(1000);
                        byte[] a3 = C0752r.m2485a(this.f2615a.getBytes());
                        this.f2618d.f4593p = true;
                        C0755u a4 = C0722b.m2406a(this.f2616b, "SOSO MAP LBS SDK", a3);
                        this.f2618d.f4593p = false;
                        this.f2617c = m2482a(C0752r.m2486b(a4.f2630a), a4.f2631b);
                        if (this.f2617c != null) {
                            message.arg1 = 0;
                            message.obj = this.f2617c;
                        } else {
                            message.arg1 = 1;
                        }
                    } catch (Exception e2) {
                    }
                }
                this.f2618d.f4593p = false;
                message.arg1 = 1;
            }
            C1722n.m4711j(this.f2618d);
            this.f2618d.f4594q.sendMessage(message);
        }
    }

    class C0748c extends Thread {
        private C0728a f2619a = null;
        private C0743b f2620b = null;
        private C0733b f2621c = null;
        private /* synthetic */ C1722n f2622d;

        C0748c(C1722n c1722n, C0728a c0728a, C0743b c0743b, C0733b c0733b) {
            this.f2622d = c1722n;
            if (c0728a != null) {
                this.f2619a = (C0728a) c0728a.clone();
            }
            if (c0743b != null) {
                this.f2620b = (C0743b) c0743b.clone();
            }
            if (c0733b != null) {
                this.f2621c = (C0733b) c0733b.clone();
            }
        }

        public final void run() {
            if (!C1722n.f4560t) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) this.f2622d.f4579b.getSystemService("phone");
                    this.f2622d.f4566E = telephonyManager.getDeviceId();
                    this.f2622d.f4567F = telephonyManager.getSubscriberId();
                    this.f2622d.f4568G = telephonyManager.getLine1Number();
                    Pattern compile = Pattern.compile("[0-9a-zA-Z+-]*");
                    this.f2622d.f4566E = this.f2622d.f4566E == null ? "" : this.f2622d.f4566E;
                    if (compile.matcher(this.f2622d.f4566E).matches()) {
                        this.f2622d.f4566E = this.f2622d.f4566E == null ? "" : this.f2622d.f4566E;
                    } else {
                        this.f2622d.f4566E = "";
                    }
                    this.f2622d.f4567F = this.f2622d.f4567F == null ? "" : this.f2622d.f4567F;
                    if (compile.matcher(this.f2622d.f4567F).matches()) {
                        this.f2622d.f4567F = this.f2622d.f4567F == null ? "" : this.f2622d.f4567F;
                    } else {
                        this.f2622d.f4567F = "";
                    }
                    this.f2622d.f4568G = this.f2622d.f4568G == null ? "" : this.f2622d.f4568G;
                    if (compile.matcher(this.f2622d.f4568G).matches()) {
                        this.f2622d.f4568G = this.f2622d.f4568G == null ? "" : this.f2622d.f4568G;
                    } else {
                        this.f2622d.f4568G = "";
                    }
                } catch (Exception e) {
                }
                C1722n.f4560t = true;
                this.f2622d.f4566E = this.f2622d.f4566E == null ? "" : this.f2622d.f4566E;
                this.f2622d.f4567F = this.f2622d.f4567F == null ? "" : this.f2622d.f4567F;
                this.f2622d.f4568G = this.f2622d.f4568G == null ? "" : this.f2622d.f4568G;
                this.f2622d.f4570I = C0752r.m2484a(this.f2622d.f4566E == null ? "0123456789ABCDEF" : this.f2622d.f4566E);
            }
            String a = this.f2622d.f4584g == 4 ? C0753s.m2491a(this.f2621c) : "[]";
            String a2 = C0753s.m2492a(this.f2620b, this.f2622d.f4581d.m2481b());
            String a3 = C0753s.m2493a(this.f2622d.f4566E, this.f2622d.f4567F, this.f2622d.f4568G, this.f2622d.f4569H, this.f2622d.f4572K);
            String a4 = (this.f2619a == null || !this.f2619a.m2428a()) ? "{}" : C0753s.m2490a(this.f2619a);
            this.f2622d.f4594q.sendMessage(this.f2622d.f4594q.obtainMessage(16, (("{\"version\":\"1.1.8\",\"address\":" + this.f2622d.f4589l) + ",\"source\":203,\"access_token\":\"" + this.f2622d.f4570I + "\",\"app_name\":" + "\"" + this.f2622d.f4571J + "\",\"bearing\":1") + ",\"attribute\":" + a3 + ",\"location\":" + a4 + ",\"cells\":" + a2 + ",\"wifis\":" + a + "}"));
        }
    }

    private C1722n() {
        this.f4578a = 5000;
        this.f4579b = null;
        this.f4580c = null;
        this.f4581d = null;
        this.f4582e = null;
        this.f4583f = 1024;
        this.f4584g = 4;
        this.f4585h = null;
        this.f4586i = null;
        this.f4587j = null;
        this.f4591n = new byte[0];
        this.f4592o = new byte[0];
        this.f4593p = false;
        this.f4594q = null;
        this.f4595r = null;
        this.f4596s = null;
        this.f4597v = -1;
        this.f4598w = null;
        this.f4599x = null;
        this.f4600y = null;
        this.f4601z = null;
        this.f4562A = null;
        this.f4563B = 0;
        this.f4564C = 0;
        this.f4565D = 1;
        this.f4566E = "";
        this.f4567F = "";
        this.f4568G = "";
        this.f4569H = "";
        this.f4570I = "";
        this.f4571J = "";
        this.f4572K = false;
        this.f4573L = false;
        this.f4574M = 0;
        this.f4575N = null;
        this.f4576O = new C0749o(this);
        this.f4577P = new C0750p(this);
        this.f4580c = new C0731e();
        this.f4581d = new C0745m();
        this.f4582e = new C0735f();
    }

    public static synchronized C1722n m4682a() {
        C1722n c1722n;
        synchronized (C1722n.class) {
            if (f4561u == null) {
                f4561u = new C1722n();
            }
            c1722n = f4561u;
        }
        return c1722n;
    }

    private static ArrayList<C0716c> m4683a(JSONArray jSONArray) throws Exception {
        int length = jSONArray.length();
        ArrayList<C0716c> arrayList = new ArrayList();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            arrayList.add(new C0716c(jSONObject.getString("name"), jSONObject.getString("addr"), jSONObject.getString("catalog"), jSONObject.getDouble("dist"), Double.parseDouble(jSONObject.getString("latitude")), Double.parseDouble(jSONObject.getString("longitude"))));
        }
        return arrayList;
    }

    static /* synthetic */ void m4684a(C1722n c1722n, int i) {
        if (i == 0) {
            c1722n.f4598w = null;
        }
        c1722n.f4583f = i == 0 ? 1 : 2;
        if (c1722n.f4587j != null) {
            c1722n.f4587j.mo2138a(c1722n.f4583f);
        }
    }

    static /* synthetic */ void m4685a(C1722n c1722n, Location location) {
        if (location == null || location.getLatitude() > 359.0d || location.getLongitude() > 359.0d) {
            if (c1722n.f4598w == null || !c1722n.f4598w.m2428a()) {
                c1722n.m4705e();
            } else {
                c1722n.m4696b(true);
            }
        }
        c1722n.f4601z = new C0717d();
        c1722n.f4601z.f2492z = 0;
        c1722n.f4601z.f2491y = 0;
        c1722n.f4601z.f2468b = C0753s.m2487a(location.getLatitude(), 6);
        c1722n.f4601z.f2469c = C0753s.m2487a(location.getLongitude(), 6);
        if (c1722n.f4598w != null && c1722n.f4598w.m2428a()) {
            c1722n.f4601z.f2471e = C0753s.m2487a((double) c1722n.f4598w.m2429b().getAccuracy(), 1);
            c1722n.f4601z.f2470d = C0753s.m2487a(c1722n.f4598w.m2429b().getAltitude(), 1);
            c1722n.f4601z.f2472f = C0753s.m2487a((double) c1722n.f4598w.m2429b().getSpeed(), 1);
            c1722n.f4601z.f2473g = C0753s.m2487a((double) c1722n.f4598w.m2429b().getBearing(), 1);
            c1722n.f4601z.f2467a = 0;
        }
        c1722n.f4601z.f2490x = true;
        if (!(c1722n.f4589l == 0 || c1722n.f4562A == null || c1722n.f4563B != 0)) {
            if ((c1722n.f4589l == 3 || c1722n.f4589l == 4) && c1722n.f4589l == c1722n.f4562A.f2492z) {
                c1722n.f4601z.f2475i = c1722n.f4562A.f2475i;
                c1722n.f4601z.f2476j = c1722n.f4562A.f2476j;
                c1722n.f4601z.f2477k = c1722n.f4562A.f2477k;
                c1722n.f4601z.f2478l = c1722n.f4562A.f2478l;
                c1722n.f4601z.f2479m = c1722n.f4562A.f2479m;
                c1722n.f4601z.f2480n = c1722n.f4562A.f2480n;
                c1722n.f4601z.f2481o = c1722n.f4562A.f2481o;
                c1722n.f4601z.f2482p = c1722n.f4562A.f2482p;
                c1722n.f4601z.f2492z = 3;
            }
            if (c1722n.f4589l == 4 && c1722n.f4589l == c1722n.f4562A.f2492z && c1722n.f4562A.f2489w != null) {
                c1722n.f4601z.f2489w = new ArrayList();
                Iterator it = c1722n.f4562A.f2489w.iterator();
                while (it.hasNext()) {
                    c1722n.f4601z.f2489w.add(new C0716c((C0716c) it.next()));
                }
                c1722n.f4601z.f2492z = 4;
            }
            if (c1722n.f4589l == 7 && c1722n.f4589l == c1722n.f4562A.f2492z) {
                c1722n.f4601z.f2492z = 7;
                c1722n.f4601z.f2474h = c1722n.f4562A.f2474h;
                c1722n.f4601z.f2475i = c1722n.f4562A.f2475i;
                if (c1722n.f4562A.f2474h == 0) {
                    c1722n.f4601z.f2476j = c1722n.f4562A.f2476j;
                    c1722n.f4601z.f2477k = c1722n.f4562A.f2477k;
                    c1722n.f4601z.f2478l = c1722n.f4562A.f2478l;
                    c1722n.f4601z.f2479m = c1722n.f4562A.f2479m;
                    c1722n.f4601z.f2480n = c1722n.f4562A.f2480n;
                    c1722n.f4601z.f2481o = c1722n.f4562A.f2481o;
                    c1722n.f4601z.f2482p = c1722n.f4562A.f2482p;
                } else {
                    c1722n.f4601z.f2483q = c1722n.f4562A.f2483q;
                    c1722n.f4601z.f2484r = c1722n.f4562A.f2484r;
                    c1722n.f4601z.f2485s = c1722n.f4562A.f2485s;
                    c1722n.f4601z.f2486t = c1722n.f4562A.f2486t;
                    c1722n.f4601z.f2487u = c1722n.f4562A.f2487u;
                    c1722n.f4601z.f2488v = c1722n.f4562A.f2488v;
                }
            }
        }
        if (c1722n.f4563B != 0 || c1722n.f4562A != null) {
            if (c1722n.f4563B != 0) {
                c1722n.f4601z.f2491y = c1722n.f4563B;
            }
            if (System.currentTimeMillis() - c1722n.f4597v >= c1722n.f4578a && c1722n.f4587j != null && c1722n.f4588k == 1) {
                c1722n.f4587j.mo2139a(c1722n.f4601z);
                c1722n.f4597v = System.currentTimeMillis();
            }
        }
    }

    static /* synthetic */ void m4686a(C1722n c1722n, C0728a c0728a) {
        if (c0728a != null) {
            c1722n.f4598w = c0728a;
            if (c1722n.f4588k != 1 || c1722n.f4598w == null || !c1722n.f4598w.m2428a()) {
                return;
            }
            if (c1722n.f4590m == 0) {
                c1722n.m4696b(false);
            } else if (c1722n.f4590m == 1 && c1722n.f4586i != null) {
                C0722b c0722b = c1722n.f4586i;
                double latitude = c1722n.f4598w.m2429b().getLatitude();
                double longitude = c1722n.f4598w.m2429b().getLongitude();
                Context context = c1722n.f4579b;
                c0722b.m2410a(latitude, longitude, (C0720a) c1722n);
            }
        }
    }

    static /* synthetic */ void m4687a(C1722n c1722n, C0733b c0733b) {
        if (c0733b != null) {
            c1722n.f4600y = c0733b;
            c1722n.m4702d();
        }
    }

    static /* synthetic */ void m4688a(C1722n c1722n, C0743b c0743b) {
        c1722n.f4599x = c0743b;
        if (c1722n.f4582e != null && c1722n.f4582e.m2461b() && c1722n.f4582e.m2462c()) {
            c1722n.f4582e.m2459a(0);
            return;
        }
        if (c1722n.f4564C > 0 && !C0753s.m2494a(c0743b.f2582a, c0743b.f2583b, c0743b.f2584c, c0743b.f2585d, c0743b.f2586e)) {
            c1722n.f4564C--;
        }
        c1722n.m4702d();
    }

    static /* synthetic */ void m4689a(C1722n c1722n, String str) {
        if (C0753s.m2497c(str)) {
            if (c1722n.f4588k != 0 || c1722n.f4587j == null) {
                String b = c1722n.f4585h == null ? null : (c1722n.f4599x == null || c1722n.f4600y == null) ? null : c1722n.f4585h.m2416b(c1722n.f4599x.f2583b, c1722n.f4599x.f2584c, c1722n.f4599x.f2585d, c1722n.f4599x.f2586e, c1722n.f4600y.m2450a());
                if (b != null) {
                    c1722n.m4690a(b);
                    return;
                }
                if (!(c1722n.f4585h == null || c1722n.f4599x == null || c1722n.f4600y == null)) {
                    c1722n.f4585h.m2414a(c1722n.f4599x.f2583b, c1722n.f4599x.f2584c, c1722n.f4599x.f2585d, c1722n.f4599x.f2586e, c1722n.f4600y.m2450a());
                }
                if (!c1722n.f4593p) {
                    if (c1722n.f4595r != null) {
                        c1722n.f4595r.interrupt();
                    }
                    c1722n.f4595r = null;
                    c1722n.f4595r = new C0747b(c1722n, str);
                    c1722n.f4595r.start();
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
            c1722n.f4587j.mo2140a(bytes, 0);
        } else if (c1722n.f4564C > 0) {
            c1722n.f4564C--;
        } else if (c1722n.f4588k == 0 && c1722n.f4587j != null) {
            c1722n.f4587j.mo2140a(null, -1);
        } else if (c1722n.f4588k == 1 && c1722n.f4587j != null) {
            c1722n.f4601z = new C0717d();
            c1722n.f4563B = 3;
            c1722n.f4601z.f2491y = 3;
            c1722n.f4601z.f2492z = -1;
            c1722n.f4587j.mo2139a(c1722n.f4601z);
        }
    }

    private void m4690a(String str) {
        int i = 0;
        try {
            double d;
            this.f4601z = new C0717d();
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.getJSONObject("location");
            this.f4601z.f2467a = 1;
            this.f4601z.f2468b = C0753s.m2487a(jSONObject2.getDouble("latitude"), 6);
            this.f4601z.f2469c = C0753s.m2487a(jSONObject2.getDouble("longitude"), 6);
            this.f4601z.f2470d = C0753s.m2487a(jSONObject2.getDouble(DataBaseContants.ALTITUDE), 1);
            this.f4601z.f2471e = C0753s.m2487a(jSONObject2.getDouble(DataBaseContants.ACCURACY), 1);
            this.f4601z.f2490x = this.f4590m == 1;
            String string = jSONObject.getString("bearing");
            int i2 = -100;
            if (string != null && string.split(SeparatorConstants.SEPARATOR_ADS_ID).length > 1) {
                i = Integer.parseInt(string.split(SeparatorConstants.SEPARATOR_ADS_ID)[1]);
            }
            if (this.f4599x != null) {
                i2 = this.f4599x.f2587f;
            }
            C0717d c0717d = this.f4601z;
            double d2 = this.f4601z.f2471e;
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
            c0717d.f2471e = d;
            this.f4601z.f2492z = 0;
            if ((this.f4589l == 3 || this.f4589l == 4) && this.f4590m == 1) {
                jSONObject2 = jSONObject.getJSONObject("details").getJSONObject("subnation");
                this.f4601z.m2398a(jSONObject2.getString("name"));
                this.f4601z.f2479m = jSONObject2.getString("town");
                this.f4601z.f2480n = jSONObject2.getString("village");
                this.f4601z.f2481o = jSONObject2.getString("street");
                this.f4601z.f2482p = jSONObject2.getString("street_no");
                this.f4601z.f2492z = 3;
                this.f4601z.f2474h = 0;
            }
            if (this.f4589l == 4 && this.f4590m == 1) {
                this.f4601z.f2489w = C1722n.m4683a(jSONObject.getJSONObject("details").getJSONArray("poilist"));
                this.f4601z.f2492z = 4;
            }
            if (this.f4589l == 7 && this.f4590m == 1) {
                jSONObject2 = jSONObject.getJSONObject("details");
                i = jSONObject2.getInt("stat");
                jSONObject2 = jSONObject2.getJSONObject("subnation");
                if (i == 0) {
                    this.f4601z.m2398a(jSONObject2.getString("name"));
                    this.f4601z.f2479m = jSONObject2.getString("town");
                    this.f4601z.f2480n = jSONObject2.getString("village");
                    this.f4601z.f2481o = jSONObject2.getString("street");
                    this.f4601z.f2482p = jSONObject2.getString("street_no");
                } else if (i == 1) {
                    this.f4601z.f2475i = jSONObject2.getString("nation");
                    this.f4601z.f2483q = jSONObject2.getString("admin_level_1");
                    this.f4601z.f2484r = jSONObject2.getString("admin_level_2");
                    this.f4601z.f2485s = jSONObject2.getString("admin_level_3");
                    this.f4601z.f2486t = jSONObject2.getString("locality");
                    this.f4601z.f2487u = jSONObject2.getString("sublocality");
                    this.f4601z.f2488v = jSONObject2.getString("route");
                }
                this.f4601z.f2474h = i;
                this.f4601z.f2492z = 7;
            }
            this.f4601z.f2491y = 0;
            this.f4562A = new C0717d(this.f4601z);
            this.f4563B = 0;
            if (this.f4585h != null) {
                this.f4585h.m2415a(str);
            }
        } catch (Exception e) {
            this.f4601z = new C0717d();
            this.f4601z.f2492z = -1;
            this.f4601z.f2491y = 2;
            this.f4563B = 2;
        }
        if (this.f4587j != null && this.f4588k == 1) {
            if (this.f4598w == null || !this.f4598w.m2428a()) {
                this.f4587j.mo2139a(this.f4601z);
                this.f4597v = System.currentTimeMillis();
            }
        }
    }

    static /* synthetic */ void m4694b(C1722n c1722n, int i) {
        int i2 = 3;
        if (i == 3) {
            i2 = 4;
        }
        c1722n.f4584g = i2;
        if (c1722n.f4587j != null) {
            c1722n.f4587j.mo2138a(c1722n.f4584g);
        }
    }

    private void m4696b(boolean z) {
        if (this.f4598w != null && this.f4598w.m2428a()) {
            Location b = this.f4598w.m2429b();
            this.f4601z = new C0717d();
            this.f4601z.f2468b = C0753s.m2487a(b.getLatitude(), 6);
            this.f4601z.f2469c = C0753s.m2487a(b.getLongitude(), 6);
            this.f4601z.f2470d = C0753s.m2487a(b.getAltitude(), 1);
            this.f4601z.f2471e = C0753s.m2487a((double) b.getAccuracy(), 1);
            this.f4601z.f2472f = C0753s.m2487a((double) b.getSpeed(), 1);
            this.f4601z.f2473g = C0753s.m2487a((double) b.getBearing(), 1);
            this.f4601z.f2467a = 0;
            this.f4601z.f2490x = false;
            if (z) {
                this.f4601z.f2491y = 1;
            } else {
                this.f4601z.f2491y = 0;
            }
            this.f4601z.f2492z = 0;
            this.f4562A = new C0717d(this.f4601z);
            this.f4563B = 0;
            if (System.currentTimeMillis() - this.f4597v >= this.f4578a && this.f4587j != null && this.f4588k == 1) {
                this.f4587j.mo2139a(this.f4601z);
                this.f4597v = System.currentTimeMillis();
            }
        }
    }

    private void m4702d() {
        if (this.f4596s == null) {
            this.f4596s = new C0748c(this, this.f4598w, this.f4599x, this.f4600y);
            this.f4596s.start();
        }
    }

    private void m4705e() {
        this.f4601z = new C0717d();
        this.f4563B = 1;
        this.f4601z.f2491y = 1;
        this.f4601z.f2492z = -1;
        this.f4601z.f2467a = 1;
        if (this.f4587j != null && this.f4588k == 1) {
            this.f4587j.mo2139a(this.f4601z);
        }
    }

    static /* synthetic */ void m4711j(C1722n c1722n) {
    }

    public final void mo2097a(double d, double d2) {
        synchronized (this.f4592o) {
            Message obtainMessage = this.f4594q.obtainMessage(6);
            Location location = new Location("Deflect");
            location.setLatitude(d);
            location.setLongitude(d2);
            obtainMessage.obj = location;
            this.f4594q.sendMessage(obtainMessage);
        }
    }

    public final void mo2098a(int i) {
        synchronized (this.f4592o) {
            this.f4594q.sendMessage(this.f4594q.obtainMessage(4, i, 0));
        }
    }

    public final void mo2099a(C0728a c0728a) {
        synchronized (this.f4592o) {
            this.f4594q.sendMessage(this.f4594q.obtainMessage(1, c0728a));
        }
    }

    public final void mo2100a(C0733b c0733b) {
        synchronized (this.f4592o) {
            this.f4594q.sendMessage(this.f4594q.obtainMessage(3, c0733b));
        }
    }

    public final void mo2101a(C0743b c0743b) {
        synchronized (this.f4592o) {
            this.f4594q.sendMessage(this.f4594q.obtainMessage(2, c0743b));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean m4728a(android.content.Context r9, com.tencent.map.p011a.p012a.C0715b r10) {
        /*
        r8 = this;
        r2 = 1;
        r1 = 0;
        r3 = r8.f4591n;
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
        r0 = r8.f4571J;	 Catch:{ all -> 0x00e5 }
        r0 = com.tencent.map.p013b.C0753s.m2495a(r0);	 Catch:{ all -> 0x00e5 }
        if (r0 != 0) goto L_0x0017;
    L_0x0014:
        monitor-exit(r3);	 Catch:{ all -> 0x00e5 }
        r0 = r1;
        goto L_0x000b;
    L_0x0017:
        r0 = new com.tencent.map.b.n$a;	 Catch:{ all -> 0x00e5 }
        r0.<init>(r8);	 Catch:{ all -> 0x00e5 }
        r8.f4594q = r0;	 Catch:{ all -> 0x00e5 }
        r0 = new android.os.Handler;	 Catch:{ all -> 0x00e5 }
        r4 = android.os.Looper.getMainLooper();	 Catch:{ all -> 0x00e5 }
        r0.<init>(r4);	 Catch:{ all -> 0x00e5 }
        r8.f4575N = r0;	 Catch:{ all -> 0x00e5 }
        r8.f4579b = r9;	 Catch:{ all -> 0x00e5 }
        r8.f4587j = r10;	 Catch:{ all -> 0x00e5 }
        r0 = com.tencent.map.p013b.C0754t.m2498a();	 Catch:{ all -> 0x00e5 }
        r4 = r8.f4579b;	 Catch:{ all -> 0x00e5 }
        r4 = r4.getApplicationContext();	 Catch:{ all -> 0x00e5 }
        r0.m2502a(r4);	 Catch:{ all -> 0x00e5 }
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
        r8.f4572K = r0;	 Catch:{ Exception -> 0x00e8 }
    L_0x0054:
        r0 = r8.f4579b;	 Catch:{ Exception -> 0x00e8 }
        r4 = r8.f4577P;	 Catch:{ Exception -> 0x00e8 }
        r5 = new android.content.IntentFilter;	 Catch:{ Exception -> 0x00e8 }
        r6 = "android.net.conn.CONNECTIVITY_CHANGE";
        r5.<init>(r6);	 Catch:{ Exception -> 0x00e8 }
        r0.registerReceiver(r4, r5);	 Catch:{ Exception -> 0x00e8 }
    L_0x0062:
        r0 = r8.f4587j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.m2391a();	 Catch:{ all -> 0x00e5 }
        r8.f4588k = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.f4587j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.m2395b();	 Catch:{ all -> 0x00e5 }
        r8.f4589l = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.f4587j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.m2396c();	 Catch:{ all -> 0x00e5 }
        r8.f4590m = r0;	 Catch:{ all -> 0x00e5 }
        r4 = -1;
        r8.f4597v = r4;	 Catch:{ all -> 0x00e5 }
        r0 = r8.f4589l;	 Catch:{ all -> 0x00e5 }
        r4 = 7;
        if (r0 != r4) goto L_0x0086;
    L_0x0083:
        r0 = 0;
        r8.f4589l = r0;	 Catch:{ all -> 0x00e5 }
    L_0x0086:
        r0 = 0;
        r8.f4573L = r0;	 Catch:{ all -> 0x00e5 }
        r0 = 1;
        r8.f4565D = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.f4580c;	 Catch:{ all -> 0x00e5 }
        r4 = r8.f4579b;	 Catch:{ all -> 0x00e5 }
        r0 = r0.m2447a(r8, r4);	 Catch:{ all -> 0x00e5 }
        r4 = r8.f4581d;	 Catch:{ all -> 0x00e5 }
        r5 = r8.f4579b;	 Catch:{ all -> 0x00e5 }
        r4 = r4.m2480a(r5, r8);	 Catch:{ all -> 0x00e5 }
        r5 = r8.f4582e;	 Catch:{ all -> 0x00e5 }
        r6 = r8.f4579b;	 Catch:{ all -> 0x00e5 }
        r7 = 1;
        r5 = r5.m2460a(r6, r8, r7);	 Catch:{ all -> 0x00e5 }
        r6 = com.tencent.map.p013b.C0725c.m2411a();	 Catch:{ all -> 0x00e5 }
        r8.f4585h = r6;	 Catch:{ all -> 0x00e5 }
        r6 = com.tencent.map.p013b.C0722b.m2405a();	 Catch:{ all -> 0x00e5 }
        r8.f4586i = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f4598w = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f4599x = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f4600y = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f4601z = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f4562A = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.f4563B = r6;	 Catch:{ all -> 0x00e5 }
        r6 = r8.f4585h;	 Catch:{ all -> 0x00e5 }
        if (r6 == 0) goto L_0x00cc;
    L_0x00c7:
        r6 = r8.f4585h;	 Catch:{ all -> 0x00e5 }
        r6.m2417b();	 Catch:{ all -> 0x00e5 }
    L_0x00cc:
        r6 = 1;
        r8.f4564C = r6;	 Catch:{ all -> 0x00e5 }
        if (r0 == 0) goto L_0x00d9;
    L_0x00d1:
        r0 = r8.f4590m;	 Catch:{ all -> 0x00e5 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.n.a(android.content.Context, com.tencent.map.a.a.b):boolean");
    }

    public final boolean m4729a(String str, String str2) {
        boolean z;
        synchronized (this.f4591n) {
            if (C0719a.m2401a().m2402a(str, str2)) {
                this.f4571J = str;
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public final void m4730b() {
        synchronized (this.f4591n) {
            try {
                if (this.f4587j != null) {
                    this.f4587j = null;
                    this.f4575N.removeCallbacks(this.f4576O);
                    this.f4579b.unregisterReceiver(this.f4577P);
                    this.f4580c.m2446a();
                    this.f4581d.m2479a();
                    this.f4582e.m2458a();
                }
            } catch (Exception e) {
            }
        }
    }

    public final void mo2102b(int i) {
        synchronized (this.f4592o) {
            this.f4594q.sendMessage(this.f4594q.obtainMessage(5, i, 0));
        }
    }
}
