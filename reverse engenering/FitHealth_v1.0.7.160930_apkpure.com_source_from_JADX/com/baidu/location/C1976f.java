package com.baidu.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.util.Log;

public final class C1976f extends Service implements an, C1619j {
    private static Context gQ = null;
    static C0522a gR = null;
    public static boolean isServing = false;
    private HandlerThread gP;
    Messenger gS = null;
    private Looper gT;

    public class C0522a extends Handler {
        final /* synthetic */ C1976f f2257a;

        public C0522a(C1976f c1976f, Looper looper) {
            this.f2257a = c1976f;
            super(looper);
        }

        public void handleMessage(Message message) {
            if (C1976f.isServing) {
                switch (message.what) {
                    case 11:
                        this.f2257a.m5934e(message);
                        break;
                    case 12:
                        this.f2257a.m5931d(message);
                        break;
                    case 15:
                        this.f2257a.m5935f(message);
                        break;
                    case 22:
                        C2065y.ag().m6282case(message);
                        break;
                    case 25:
                        C2062p.an().m6267else(message);
                        break;
                    case 28:
                        ao.bz().m5887h(message);
                        break;
                    case 41:
                        C2065y.ag().ae();
                        break;
                    case 57:
                        this.f2257a.m5940void(message);
                        break;
                    case an.f2220j /*110*/:
                        C1988x.aU().aV();
                        break;
                    case an.f2210case /*111*/:
                        C1988x.aU().aX();
                        break;
                    case 201:
                        ab.m2123a().m2132do();
                        break;
                    case 202:
                        ab.m2123a().m2133if();
                        break;
                    case 203:
                        ab.m2123a().m2130a(message);
                        break;
                    case 206:
                        aq.b2().m5908if(C1976f.getServiceContext(), message);
                        break;
                    case 207:
                        al.m5878int(C1976f.getServiceContext());
                        break;
                }
            }
            if (message.what == 0) {
                this.f2257a.bx();
            }
            if (message.what == 1) {
                this.f2257a.bw();
            }
            super.handleMessage(message);
        }
    }

    private void bw() {
        C1981n.m6008K().m6022O();
        C1984s.aH().aD();
        C2065y.ag().af();
        C1988x.aU().aX();
        C1979l.m5993t();
        isServing = false;
    }

    private void bx() {
        isServing = true;
        Thread.setDefaultUncaughtExceptionHandler(new C1987v(this));
        C1981n.m6008K().m6019J();
        ai.bb().a9();
        C1984s.aH().aB();
        C1980m.m6000D().m6006G();
        C1618d.m4603new().m4606do();
        C2065y.ag().aj();
    }

    private void m5931d(Message message) {
        C1977g.m5942g().m5946do(message);
    }

    private void m5934e(Message message) {
        C1977g.m5942g().m5956new(message);
        C1985t.aK().aM();
    }

    private void m5935f(Message message) {
        C1977g.m5942g().m5955int(message);
    }

    public static Handler getHandler() {
        return gR;
    }

    public static Context getServiceContext() {
        return gQ;
    }

    private void m5940void(Message message) {
        if (message != null && message.obj == null) {
        }
    }

    public IBinder onBind(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            ap.bD();
            ap.g9 = extras.getString("key");
            ap.bD();
            ap.g8 = extras.getString("sign");
        }
        return this.gS.getBinder();
    }

    public void onCreate() {
        gQ = this;
        this.gP = af.m2142a();
        this.gT = this.gP.getLooper();
        gR = new C0522a(this, this.gT);
        this.gS = new Messenger(gR);
        gR.sendEmptyMessage(0);
        Log.d(an.f2222l, "baidu location service start1 ..." + Process.myPid());
    }

    public void onDestroy() {
        ai.bb().bg();
        C1618d.m4603new().m4609int();
        C1980m.m6000D().m6004C();
        gR.sendEmptyMessage(1);
        Log.d(an.f2222l, "baidu location service stop ...");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }
}
