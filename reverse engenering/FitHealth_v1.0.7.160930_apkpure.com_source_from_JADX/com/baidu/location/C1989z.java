package com.baidu.location;

import android.location.Location;
import android.os.Handler;
import android.os.Message;
import com.baidu.location.C1981n.C0529a;
import com.baidu.location.ai.C0503b;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

abstract class C1989z implements an, C1619j {
    public static String dw = null;
    private boolean dA = false;
    private boolean dB = false;
    public C0503b dC = null;
    private boolean dx = true;
    final Handler dy = new C0544b(this);
    public C0529a dz = null;

    public class C0544b extends Handler {
        final /* synthetic */ C1989z f2301a;

        public C0544b(C1989z c1989z) {
            this.f2301a = c1989z;
        }

        public void handleMessage(Message message) {
            if (C1976f.isServing) {
                switch (message.what) {
                    case 21:
                        this.f2301a.mo3707byte(message);
                        return;
                    case 62:
                    case 63:
                        this.f2301a.ab();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    class C2066a extends C1982o {
        final /* synthetic */ C1989z c4;
        String c5;
        String c6;

        public C2066a(C1989z c1989z) {
            this.c4 = c1989z;
            this.c6 = null;
            this.c5 = null;
            this.cP = new ArrayList();
        }

        void mo3704V() {
            this.cL = C1974b.m5924int();
            String f = Jni.m5811f(this.c5);
            ab.m2123a().m2131a(f);
            this.c5 = null;
            if (this.c6 == null) {
                this.c6 = C1979l.m5996w();
            }
            this.cP.add(new BasicNameValuePair("bloc", f));
            if (this.c6 != null) {
                this.cP.add(new BasicNameValuePair("up", this.c6));
            }
            List list = this.cP;
            String str = SocializeProtocolConstants.PROTOCOL_KEY_EXTEND;
            r4 = new Object[2];
            ap.bD();
            r4[0] = ap.g9;
            ap.bD();
            r4[1] = ap.g8;
            list.add(new BasicNameValuePair(str, Jni.m5811f(String.format("&ki=%s&sn=%s", r4))));
            C1975c.m5927char().m5928else();
        }

        void mo3705if(boolean z) {
            Message obtainMessage;
            if (!z || this.cM == null) {
                ab.m2123a().m2134if("network exception");
                obtainMessage = this.c4.dy.obtainMessage(63);
                obtainMessage.obj = "HttpStatus error";
                obtainMessage.sendToTarget();
            } else {
                try {
                    Object bDLocation;
                    String entityUtils = EntityUtils.toString(this.cM, "utf-8");
                    C1989z.dw = entityUtils;
                    ab.m2123a().m2134if(entityUtils);
                    try {
                        bDLocation = new BDLocation(entityUtils);
                        if (bDLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                            C1975c.m5927char().m5930new(bDLocation.getTime());
                        }
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(63);
                    }
                    Message obtainMessage2 = this.c4.dy.obtainMessage(21);
                    obtainMessage2.obj = bDLocation;
                    obtainMessage2.sendToTarget();
                    this.c6 = null;
                } catch (Exception e2) {
                    obtainMessage = this.c4.dy.obtainMessage(63);
                    obtainMessage.obj = "HttpStatus error";
                    obtainMessage.sendToTarget();
                }
            }
            if (this.cP != null) {
                this.cP.clear();
            }
        }

        public void m6286long(String str) {
            this.c5 = str;
            m6032R();
        }
    }

    C1989z() {
    }

    abstract void ab();

    abstract void mo3707byte(Message message);

    public String m6095void(String str) {
        String str2;
        if (this.dz == null || !this.dz.m2201do()) {
            this.dz = C1981n.m6008K().m6018H();
        }
        if (this.dz != null) {
            C1974b.m5914do(an.f2222l, this.dz.m2203if());
        } else {
            C1974b.m5914do(an.f2222l, "cellInfo null...");
        }
        if (this.dC == null || !this.dC.m2156for()) {
            this.dC = ai.bb().ba();
        }
        if (this.dC != null) {
            C1974b.m5914do(an.f2222l, this.dC.m2155else());
        } else {
            C1974b.m5914do(an.f2222l, "wifi list null");
        }
        Location location = null;
        if (C1984s.aH().ay()) {
            location = C1984s.aH().az();
        }
        String f = C1977g.m5942g().m5949f();
        if (ai.bf()) {
            str2 = "&cn=32";
        } else {
            str2 = String.format(Locale.CHINA, "&cn=%d", new Object[]{Integer.valueOf(C1981n.m6008K().m6021M())});
        }
        if (this.dx) {
            String v = C1979l.m5995v();
            if (v != null) {
                str2 = str2 + v;
            }
        }
        str2 = str2 + f;
        if (str != null) {
            str2 = str + str2;
        }
        return C1974b.m5919if(this.dz, this.dC, location, str2, 0);
    }
}
