package com.baidu.location;

import android.os.Message;
import android.os.Messenger;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;

class ab {
    private static ab f2142goto;
    private boolean f2143a;
    private String f2144byte;
    private String f2145case;
    private long f2146char;
    private String f2147do;
    private String f2148else;
    private long f2149for;
    private long f2150if;
    private String f2151int;
    public String f2152new;
    private C2057a f2153try;

    class C2057a extends C1982o {
        final /* synthetic */ ab c7;
        String c8;
        Messenger c9;
        boolean da;

        public C2057a(ab abVar) {
            this.c7 = abVar;
            this.da = false;
            this.c8 = null;
            this.c9 = null;
            this.cP = new ArrayList();
        }

        void mo3704V() {
            this.cL = C1974b.m5912do();
            if (this.c7.f2145case == null) {
                this.c7.f2145case = Jni.m5811f("none");
            }
            this.cP.add(new BasicNameValuePair("erpt[0]", this.c7.f2145case));
            if (this.c7.f2147do == null) {
                this.c7.f2147do = "none";
            }
            this.cP.add(new BasicNameValuePair("erpt[1]", Jni.m5811f(this.c7.f2147do)));
            if (this.c8 == null) {
                this.c8 = "none";
            }
            this.cP.add(new BasicNameValuePair("erpt[2]", Jni.m5811f(this.c8)));
            StringBuffer stringBuffer = new StringBuffer(512);
            stringBuffer.append("&t1=");
            stringBuffer.append(this.c7.f2150if);
            stringBuffer.append("&t2=");
            stringBuffer.append(this.c7.f2146char);
            String aE = C1984s.aH().aE();
            if (aE != null) {
                stringBuffer.append(aE);
            }
            this.cP.add(new BasicNameValuePair("erpt[3]", Jni.m5811f(stringBuffer.toString())));
            this.c7.f2145case = null;
            this.c7.f2147do = null;
            this.c7.f2146char = 0;
        }

        void mo3705if(boolean z) {
            if (this.cP != null) {
                this.cP.clear();
            }
            try {
                this.c9.send(z ? Message.obtain(null, 205) : Message.obtain(null, 204));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.da = false;
        }

        public void m6255try(Message message) {
            this.c9 = message.replyTo;
            if (this.da) {
                try {
                    this.c9.send(Message.obtain(null, 204));
                    return;
                } catch (Exception e) {
                    return;
                }
            }
            this.da = true;
            this.c8 = message.getData().getString("errInfo");
            m6032R();
        }
    }

    private ab() {
        this.f2151int = null;
        this.f2144byte = null;
        this.f2148else = null;
        this.f2152new = null;
        this.f2143a = false;
        this.f2145case = null;
        this.f2147do = null;
        this.f2153try = null;
        this.f2146char = 0;
        this.f2149for = 0;
        this.f2150if = 0;
        this.f2153try = new C2057a(this);
    }

    public static ab m2123a() {
        if (f2142goto == null) {
            f2142goto = new ab();
        }
        return f2142goto;
    }

    public void m2130a(Message message) {
        if (this.f2145case == null || this.f2147do == null) {
            this.f2145case = this.f2151int;
            this.f2147do = this.f2144byte;
        }
        this.f2153try.m6255try(message);
    }

    public void m2131a(String str) {
        this.f2148else = str;
        this.f2143a = true;
        this.f2149for = System.currentTimeMillis();
    }

    public void m2132do() {
        this.f2145case = this.f2151int;
        this.f2147do = this.f2144byte;
        this.f2146char = System.currentTimeMillis();
    }

    public void m2133if() {
        this.f2145case = null;
        this.f2147do = null;
        this.f2146char = 0;
    }

    public void m2134if(String str) {
        if (this.f2143a) {
            this.f2151int = this.f2148else;
            this.f2143a = false;
            this.f2150if = this.f2149for;
        }
        this.f2144byte = str;
    }
}
