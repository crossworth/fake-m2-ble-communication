package com.baidu.location.p013g;

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
import com.baidu.location.C0455f;
import com.baidu.location.LLSInterface;
import com.baidu.location.p005a.C0332a;
import com.baidu.location.p005a.C0340c;
import com.baidu.location.p005a.C0347h;
import com.baidu.location.p005a.C0351i;
import com.baidu.location.p005a.C0354l;
import com.baidu.location.p005a.C0360r;
import com.baidu.location.p005a.C0362s;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p007b.C0365a;
import com.baidu.location.p007b.C0367b;
import com.baidu.location.p007b.C0370d;
import com.baidu.location.p007b.C0372e;
import com.baidu.location.p007b.C0373f;
import com.baidu.location.p007b.C0376g;
import com.baidu.location.p008c.C0397b;
import com.baidu.location.p011e.C0411a;
import com.baidu.location.p011e.C0426h;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0448d;
import com.baidu.location.p012f.C0454h;
import com.droi.btlib.service.BtManagerService;
import twitter4j.HttpResponseCode;

public class C0457a extends Service implements LLSInterface {
    static C0456a f820a = null;
    private static long f821f = 0;
    Messenger f822b = null;
    private Looper f823c;
    private HandlerThread f824d;
    private boolean f825e = false;

    public class C0456a extends Handler {
        final /* synthetic */ C0457a f819a;

        public C0456a(C0457a c0457a, Looper looper) {
            this.f819a = c0457a;
            super(looper);
        }

        public void handleMessage(Message message) {
            if (C0455f.isServing) {
                switch (message.what) {
                    case 11:
                        this.f819a.m969a(message);
                        break;
                    case 12:
                        this.f819a.m973b(message);
                        break;
                    case 15:
                        this.f819a.m977c(message);
                        break;
                    case 22:
                        C0351i.m280c().m297b(message);
                        break;
                    case 28:
                        C0351i.m280c().m296a(true);
                        break;
                    case 41:
                        C0351i.m280c().m305i();
                        break;
                    case 110:
                        C0397b.m541a().m583c();
                        break;
                    case 111:
                        C0397b.m541a().m584d();
                        break;
                    case BtManagerService.CLASSIC_CMD_GET_STEP /*112*/:
                        C0397b.m541a().m582b();
                        break;
                    case HttpResponseCode.FOUND /*302*/:
                        C0397b.m541a().m585e();
                        break;
                    case HttpResponseCode.UNAUTHORIZED /*401*/:
                        try {
                            message.getData();
                            break;
                        } catch (Exception e) {
                            break;
                        }
                    case 405:
                        byte[] byteArray = message.getData().getByteArray("errorid");
                        if (byteArray != null && byteArray.length > 0) {
                            String str = new String(byteArray);
                            break;
                        }
                }
            }
            if (message.what == 1) {
                this.f819a.m979d();
            }
            if (message.what == 0) {
                this.f819a.m976c();
            }
            super.handleMessage(message);
        }
    }

    public static Handler m968a() {
        return f820a;
    }

    private void m969a(Message message) {
        Log.d("baidu_location_service", "baidu location service register ...");
        C0332a.m176a().m180a(message);
        C0426h.m767a();
        C0372e.m404a().m422d();
        C0354l.m326b().mo1746c();
    }

    public static long m972b() {
        return f821f;
    }

    private void m973b(Message message) {
        C0332a.m176a().m184b(message);
    }

    private void m976c() {
        C0347h.m268a().m270a(C0455f.getServiceContext());
        C0448d.m886a().m918b();
        C0443b.m855a().m869b();
        C0454h.m948a().m954b();
        C0459b.m980a();
        C0351i.m280c().m300d();
        C0411a.m637a().m657b();
        C0370d.m393a().m396b();
        C0372e.m404a().m420b();
        C0373f.m423a().m427b();
        C0365a.m373a().m378b();
    }

    private void m977c(Message message) {
        C0332a.m176a().m186c(message);
    }

    private void m979d() {
        C0454h.m948a().m955c();
        C0426h.m767a().m789n();
        C0448d.m886a().m921e();
        C0376g.m428a().m437c();
        C0372e.m404a().m421c();
        C0370d.m393a().m397c();
        C0367b.m381a().m389c();
        C0365a.m373a().m379c();
        C0443b.m855a().m870c();
        C0351i.m280c().m301e();
        C0397b.m541a().m584d();
        C0362s.m365e();
        C0332a.m176a().m183b();
        C0340c.m222a().m250b();
        try {
            if (f820a != null) {
                f820a.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        f820a = null;
        Log.d("baidu_location_service", "baidu location service has stoped ...");
        if (!this.f825e) {
            Process.killProcess(Process.myPid());
        }
    }

    public double getVersion() {
        return 7.010000228881836d;
    }

    public IBinder onBind(Intent intent) {
        Bundle extras = intent.getExtras();
        boolean z = false;
        if (extras != null) {
            C0459b.f838g = extras.getString("key");
            C0459b.f837f = extras.getString("sign");
            this.f825e = extras.getBoolean("kill_process");
            z = extras.getBoolean("cache_exception");
        }
        if (!z) {
            Thread.setDefaultUncaughtExceptionHandler(C0373f.m423a());
        }
        return this.f822b.getBinder();
    }

    public void onCreate(Context context) {
        f821f = System.currentTimeMillis();
        this.f824d = C0360r.m345a();
        this.f823c = this.f824d.getLooper();
        f820a = new C0456a(this, this.f823c);
        this.f822b = new Messenger(f820a);
        f820a.sendEmptyMessage(0);
        Log.d("baidu_location_service", "baidu location service start1 ..." + Process.myPid());
    }

    public void onDestroy() {
        try {
            f820a.sendEmptyMessage(1);
        } catch (Exception e) {
            Log.d("baidu_location_service", "baidu location service stop exception...");
            Process.killProcess(Process.myPid());
        }
        Log.d("baidu_location_service", "baidu location service stop ...");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    public boolean onUnBind(Intent intent) {
        return false;
    }
}
