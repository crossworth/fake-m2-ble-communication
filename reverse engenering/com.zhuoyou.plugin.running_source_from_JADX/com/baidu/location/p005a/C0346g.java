package com.baidu.location.p005a;

import android.location.Location;
import android.os.Handler;
import android.os.Message;
import com.baidu.location.BDLocation;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p012f.C0441a;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0448d;
import com.baidu.location.p012f.C0451g;
import com.baidu.location.p012f.C0454h;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Locale;

public abstract class C0346g {
    public static String f226c = null;
    public C0451g f227a = null;
    public C0441a f228b = null;
    final Handler f229d = new C0344a(this);
    private boolean f230e = true;
    private boolean f231f = false;
    private String f232g = null;

    public class C0344a extends Handler {
        final /* synthetic */ C0346g f222a;

        public C0344a(C0346g c0346g) {
            this.f222a = c0346g;
        }

        public void handleMessage(Message message) {
            if (C0455f.isServing) {
                switch (message.what) {
                    case 21:
                        this.f222a.mo1745a(message);
                        return;
                    case 62:
                    case 63:
                        this.f222a.mo1744a();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    class C0345b extends C0335e {
        String f223a;
        String f224b;
        final /* synthetic */ C0346g f225c;

        public C0345b(C0346g c0346g) {
            this.f225c = c0346g;
            this.f223a = null;
            this.f224b = null;
            this.k = new HashMap();
        }

        public void mo1741a() {
            this.h = C0468j.m1023c();
            String encodeTp4 = Jni.encodeTp4(this.f224b);
            this.f224b = null;
            if (this.f223a == null) {
                this.f223a = C0362s.m360b();
            }
            this.k.put("bloc", encodeTp4);
            if (this.f223a != null) {
                this.k.put("up", this.f223a);
            }
            StringBuffer stringBuffer = new StringBuffer(512);
            if ((C0468j.f905g || C0468j.f906h) && this.f225c.f232g != null) {
                stringBuffer.append(String.format(Locale.CHINA, "&ki=%s", new Object[]{this.f225c.f232g}));
            }
            if (stringBuffer.length() > 0) {
                this.k.put(SocializeProtocolConstants.PROTOCOL_KEY_EXTEND, Jni.encode(stringBuffer.toString()));
            }
            this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
        }

        public void m261a(String str) {
            this.f224b = str;
            m204e();
        }

        public void mo1742a(boolean z) {
            Message obtainMessage;
            if (!z || this.j == null) {
                obtainMessage = this.f225c.f229d.obtainMessage(63);
                obtainMessage.obj = "HttpStatus error";
                obtainMessage.sendToTarget();
            } else {
                try {
                    BDLocation bDLocation;
                    String str = this.j;
                    C0346g.f226c = str;
                    try {
                        bDLocation = new BDLocation(str);
                        bDLocation.setOperators(C0443b.m855a().m875h());
                        if (C0352j.m308a().m317f()) {
                            bDLocation.setDirection(C0352j.m308a().m319h());
                        }
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(0);
                    }
                    this.f223a = null;
                    if (bDLocation.getLocType() == 0 && bDLocation.getLatitude() == Double.MIN_VALUE && bDLocation.getLongitude() == Double.MIN_VALUE) {
                        obtainMessage = this.f225c.f229d.obtainMessage(63);
                        obtainMessage.obj = "HttpStatus error";
                        obtainMessage.sendToTarget();
                    } else {
                        Message obtainMessage2 = this.f225c.f229d.obtainMessage(21);
                        obtainMessage2.obj = bDLocation;
                        obtainMessage2.sendToTarget();
                    }
                } catch (Exception e2) {
                    obtainMessage = this.f225c.f229d.obtainMessage(63);
                    obtainMessage.obj = "HttpStatus error";
                    obtainMessage.sendToTarget();
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    public String m264a(String str) {
        if (this.f232g == null) {
            this.f232g = C0347h.m269b(C0455f.getServiceContext());
        }
        if (this.f228b == null || !this.f228b.m841a()) {
            this.f228b = C0443b.m855a().m873f();
        }
        if (this.f227a == null || !this.f227a.m945f()) {
            this.f227a = C0454h.m948a().m964m();
        }
        Location g = C0448d.m886a().m925i() ? C0448d.m886a().m923g() : null;
        if ((this.f228b == null || this.f228b.m844c()) && ((this.f227a == null || this.f227a.m933a() == 0) && g == null)) {
            return null;
        }
        return C0468j.m1013a(this.f228b, this.f227a, g, m267b(), 0);
    }

    public abstract void mo1744a();

    public abstract void mo1745a(Message message);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String m267b() {
        /*
        r8 = this;
        r7 = 1;
        r6 = 0;
        r0 = com.baidu.location.p005a.C0332a.m176a();
        r1 = r0.m188d();
        r0 = com.baidu.location.p012f.C0454h.m952h();
        if (r0 == 0) goto L_0x005d;
    L_0x0010:
        r0 = "&cn=32";
    L_0x0012:
        r2 = r8.f230e;
        if (r2 == 0) goto L_0x0076;
    L_0x0016:
        r8.f230e = r6;
        r2 = com.baidu.location.p012f.C0454h.m948a();
        r2 = r2.m967p();
        r3 = android.text.TextUtils.isEmpty(r2);
        if (r3 != 0) goto L_0x0045;
    L_0x0026:
        r3 = "02:00:00:00:00:00";
        r3 = r2.equals(r3);
        if (r3 != 0) goto L_0x0045;
    L_0x002e:
        r3 = ":";
        r4 = "";
        r2 = r2.replace(r3, r4);
        r3 = java.util.Locale.CHINA;
        r4 = "%s&mac=%s";
        r5 = 2;
        r5 = new java.lang.Object[r5];
        r5[r6] = r0;
        r5[r7] = r2;
        r0 = java.lang.String.format(r3, r4, r5);
    L_0x0045:
        r2 = android.os.Build.VERSION.SDK_INT;
        r3 = 17;
        if (r2 <= r3) goto L_0x004b;
    L_0x004b:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r0 = r2.append(r0);
        r0 = r0.append(r1);
        r0 = r0.toString();
        return r0;
    L_0x005d:
        r0 = java.util.Locale.CHINA;
        r2 = "&cn=%d";
        r3 = new java.lang.Object[r7];
        r4 = com.baidu.location.p012f.C0443b.m855a();
        r4 = r4.m872e();
        r4 = java.lang.Integer.valueOf(r4);
        r3[r6] = r4;
        r0 = java.lang.String.format(r0, r2, r3);
        goto L_0x0012;
    L_0x0076:
        r2 = r8.f231f;
        if (r2 != 0) goto L_0x004b;
    L_0x007a:
        r2 = com.baidu.location.p005a.C0362s.m367f();
        if (r2 == 0) goto L_0x0091;
    L_0x0080:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r0 = r3.append(r0);
        r0 = r0.append(r2);
        r0 = r0.toString();
    L_0x0091:
        r8.f231f = r7;
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.g.b():java.lang.String");
    }
}
