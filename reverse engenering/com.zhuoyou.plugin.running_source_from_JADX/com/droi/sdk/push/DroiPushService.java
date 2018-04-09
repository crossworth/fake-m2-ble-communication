package com.droi.sdk.push;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Pair;
import com.droi.btlib.connection.MessageObj;
import com.droi.sdk.push.data.C0987b;
import com.droi.sdk.push.p019a.C0974a;
import com.droi.sdk.push.p019a.C0975b;
import com.droi.sdk.push.p019a.C0977d;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.droi.sdk.push.utils.C1016k;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class DroiPushService extends Service {
    private static C0987b f3185b;
    private static Looper f3186c = null;
    private static ExecutorService f3187d = null;
    private static C0975b f3188e = null;
    private static C0977d f3189f = null;
    protected PendingIntent f3190a;
    private Context f3191g;
    private WakeLock f3192h;
    private C1001p f3193i;
    private C1002q f3194j;
    private HashMap f3195k;
    private boolean f3196l = false;
    private C1000o f3197m = null;
    private boolean f3198n = false;
    private C1016k f3199o = null;

    private C0975b m2886a(byte[] bArr) {
        return new C0999m(this, bArr);
    }

    private C0977d m2887a(Context context, String str) {
        return new C0998l(this, context, str);
    }

    private String m2889a(String str) {
        this.f3195k = f3185b.m3038b();
        if (this.f3195k != null) {
            Pair pair = (Pair) this.f3195k.get(str);
            if (pair != null) {
                return (String) pair.first;
            }
        }
        return null;
    }

    private synchronized void m2890a(Context context) {
        if (f3185b == null) {
            f3185b = C0987b.m3029a(context);
        }
        if (f3186c == null) {
            HandlerThread handlerThread = new HandlerThread("DroiPush");
            handlerThread.start();
            f3186c = handlerThread.getLooper();
        }
        this.f3193i = new C1001p(this, f3186c);
        if (this.f3194j == null) {
            this.f3194j = new C1002q(this.f3191g);
        }
        if (f3187d == null) {
            f3187d = Executors.newFixedThreadPool(5);
        }
    }

    private void m2891a(Context context, String str, String str2, long j, String str3, String str4) {
        C1012g.m3141c("getShortLinkMessage: " + j);
        if (j < 0) {
            C1012g.m3140b("getShortLinkMessage: msgId invalid(" + j + ")");
        } else if (str3 == null || str3.length() == 0) {
            C1012g.m3140b("getShortLinkMessage: url invalid(" + str3 + ")");
        } else {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("osType", 1);
                jSONObject.put("timeStamp", System.currentTimeMillis() + "");
                jSONObject.put("deviceId", str2);
                jSONObject.put("sdkVer", 2);
                jSONObject.put(MessageObj.MSGID, j + "");
                String jSONObject2 = jSONObject.toString();
                Runnable a = ad.m2990a(context, str3, str, jSONObject2, str4, str2, new C0995i(this, str));
                if (a != null) {
                    f3187d.execute(a);
                }
            } catch (Exception e) {
                C1012g.m3137a(e);
            }
        }
    }

    private void m2892a(Intent intent) {
        String stringExtra = intent.getStringExtra("app_id");
        String stringExtra2 = intent.getStringExtra("secret");
        String stringExtra3 = intent.getStringExtra("package");
        if (C1015j.m3168d(stringExtra) && C1015j.m3168d(stringExtra2) && C1015j.m3168d(stringExtra3)) {
            m2899a(stringExtra, stringExtra2, stringExtra3);
            if (f3185b != null) {
                f3185b.m3036a(stringExtra, stringExtra2, stringExtra3);
            }
        }
    }

    private void m2893a(Bundle bundle) {
        C1012g.m3141c("dealwithReceivedMessage is called...");
        if (bundle != null) {
            String string = bundle.getString("app_id");
            if (!C1015j.m3168d(string)) {
                return;
            }
            String str;
            byte[] byteArray;
            C1004t c1004t;
            if ("00000000000000000000000000000000".equals(string)) {
                C1012g.m3141c("global control message");
                str = "bc3a2c45";
                byteArray = bundle.getByteArray("push_message_byte");
                if (byteArray != null) {
                    str = ad.m2991a(byteArray, str);
                    if (C1015j.m3168d(str)) {
                        c1004t = new C1004t(str);
                        c1004t.f3329c = "00000000000000000000000000000000";
                        m2917a(c1004t);
                        return;
                    }
                    return;
                }
                return;
            }
            C1012g.m3138a("app owned message!");
            String a = m2889a(string);
            str = m2901b(string);
            if (C1015j.m3168d(a) && C1015j.m3168d(str)) {
                long j = bundle.getLong("msg_id", -1);
                byteArray = bundle.getByteArray("push_message_byte");
                String a2 = byteArray != null ? ad.m2991a(byteArray, a) : bundle.getString("push_message_string");
                C1012g.m3141c("pushMsg: " + a2);
                if (!C1015j.m3168d(a2)) {
                    return;
                }
                if (a2.startsWith("http://") || a2.startsWith("https://")) {
                    C1012g.m3141c("receive short link message - " + j);
                    String a3 = DroiPush.m2876a(this.f3191g);
                    if (a3 != null) {
                        m2891a(this.f3191g, string, a3, j, a2, a);
                        return;
                    }
                    return;
                }
                c1004t = new C1004t(string, a2);
                c1004t.m3094a(str);
                if (c1004t.m3098e()) {
                    C1012g.m3141c("app control message - " + c1004t.f3327a);
                    m2917a(c1004t);
                    return;
                } else if (c1004t.f3336j) {
                    C1012g.m3141c("app normal message - " + c1004t.f3327a);
                    ad.m2993a(this.f3191g, c1004t);
                    m2908c(c1004t);
                    return;
                } else {
                    return;
                }
            }
            C1012g.m3140b("secret or package name not found!");
        }
    }

    private void m2897a(C0974a c0974a) {
        if (c0974a != null && c0974a.m2927g() != null && c0974a.m2927g().length != 0) {
            C1012g.m3141c("onPushMessage: " + c0974a.m2928h());
            switch (c0974a.m2924d()) {
                case 32:
                    Message obtainMessage = this.f3193i.obtainMessage(1);
                    Bundle bundle = new Bundle();
                    bundle.putLong("msg_id", c0974a.m2928h());
                    bundle.putString("app_id", c0974a.m2923c());
                    bundle.putByteArray("push_message_byte", c0974a.m2922b());
                    obtainMessage.setData(bundle);
                    this.f3193i.sendMessage(obtainMessage);
                    return;
                default:
                    return;
            }
        }
    }

    private void m2898a(String str, C1004t c1004t) {
        C1012g.m3141c("start to broadcast message!");
        if (C1015j.m3168d(str) && C1015j.m3168d(c1004t.f3335i)) {
            Intent intent = new Intent("com.droi.sdk.push.action.DATA");
            intent.putExtra("msg", c1004t.f3335i);
            intent.putExtra("app_id", str);
            if (VERSION.SDK_INT >= 12) {
                intent.addFlags(32);
            }
            C1012g.m3141c("send message completed: " + c1004t.f3327a);
            sendBroadcast(intent);
            return;
        }
        C1012g.m3141c("invalid message, cancel!");
    }

    private void m2899a(String str, String str2, String str3) {
        if (C1015j.m3168d(str) && C1015j.m3168d(str2) && C1015j.m3168d(str3)) {
            Pair pair = new Pair(str2, str3);
            if (this.f3195k != null && !this.f3195k.containsKey(str)) {
                this.f3195k.put(str, pair);
            }
        }
    }

    private String m2901b(String str) {
        this.f3195k = f3185b.m3038b();
        if (this.f3195k != null) {
            Pair pair = (Pair) this.f3195k.get(str);
            if (pair != null) {
                return (String) pair.second;
            }
        }
        return null;
    }

    private void m2902b(Context context) {
        String appId = DroiPush.getAppId(context);
        String secret = DroiPush.getSecret(context);
        String a = DroiPush.m2876a(this.f3191g);
        if (a != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("osType", 1);
                jSONObject.put("timeStamp", System.currentTimeMillis() + "");
                jSONObject.put("sdkVer", 2);
                jSONObject.put("deviceId", a);
                String jSONObject2 = jSONObject.toString();
                Runnable a2 = ad.m2990a(context, "http://push_service.droibaas.com:2500/device/ip", appId, jSONObject2, secret, a, new C0996j(this));
                if (a2 != null) {
                    f3187d.execute(a2);
                }
            } catch (Exception e) {
                C1012g.m3137a(e);
            }
        }
    }

    private void m2904b(C1004t c1004t) {
        if (c1004t != null) {
            String a = m2889a(c1004t.f3329c);
            String b = m2901b(c1004t.f3329c);
            if (C1015j.m3168d(a) && C1015j.m3168d(b)) {
                c1004t.m3094a(b);
                String str = c1004t.f3329c;
                long j = c1004t.f3327a;
                String a2 = DroiPush.m2876a(this.f3191g);
                if (a2 != null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("osType", 1);
                        jSONObject.put("timeStamp", System.currentTimeMillis() + "");
                        jSONObject.put("deviceId", a2);
                        jSONObject.put("sdkVer", 2);
                        jSONObject.put(MessageObj.MSGID, j + "");
                        Runnable a3 = ad.m2990a(this.f3191g, "http://push_service.droibaas.com:2500/device/message/status", str, jSONObject.toString(), a, a2, new C0997k(this, c1004t));
                        if (a3 != null) {
                            f3187d.execute(a3);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        C1012g.m3137a(e);
                        return;
                    }
                }
                return;
            }
            f3185b.m3034a(c1004t.f3337k);
        }
    }

    private synchronized void m2905b(boolean z) {
        m2920c();
        m2913e();
        m2920c();
        if (z) {
            m2919b();
        }
        if (f3189f != null) {
            f3189f.m2955c();
            f3189f = null;
        }
        if (f3188e != null) {
            f3188e.m2945g();
            f3188e = null;
        }
        if (f3185b != null) {
            f3185b.m3042e();
            f3185b = null;
        }
        if (f3186c != null) {
            f3186c.quit();
            f3186c = null;
        }
        if (f3187d != null) {
            f3187d.shutdownNow();
            f3187d = null;
        }
        if (this.f3195k != null) {
            this.f3195k.clear();
            this.f3195k = null;
        }
    }

    private void m2908c(C1004t c1004t) {
        if (c1004t != null && c1004t.m3100g()) {
            C1012g.m3141c("loadPushMessage is called - " + c1004t.f3327a);
            boolean a = aa.m2958a(this.f3191g).m2967a(c1004t.f3338l);
            boolean e = aa.m2958a(this.f3191g).m2981e(c1004t.f3338l);
            boolean g = aa.m2958a(this.f3191g).m2983g();
            C1012g.m3141c("isAppRecovered: " + a);
            C1012g.m3141c("isAppSilent: " + e);
            C1012g.m3141c("isDeviceRecovered: " + g);
            if (!a || e || !g) {
                C1012g.m3141c("loadPushMessage: msg can not be shown - " + c1004t.f3327a);
            } else if (c1004t.m3099f()) {
                m2898a(c1004t.f3329c, c1004t);
                if (c1004t.m3096c()) {
                    f3185b.m3034a(c1004t.f3337k);
                } else if (c1004t.m3097d()) {
                    f3185b.m3035a(c1004t.f3337k, System.currentTimeMillis());
                }
            }
        }
    }

    private void m2911d(C1004t c1004t) {
        if (c1004t != null) {
            C1012g.m3141c("receive wap message: " + c1004t.f3327a);
            Message obtainMessage = this.f3193i.obtainMessage(2);
            obtainMessage.obj = c1004t;
            this.f3193i.sendMessage(obtainMessage);
        }
    }

    private void m2913e() {
        ((NotificationManager) getSystemService(MessageObj.CATEGORY_NOTI)).cancelAll();
    }

    private void m2914e(C1004t c1004t) {
        if (c1004t != null) {
            if ("00000000000000000000000000000000".equals(c1004t.f3329c)) {
                C1012g.m3141c("WapClient: msg from wap is control type - " + c1004t.f3327a);
                m2917a(c1004t);
            } else if (this.f3194j.m3071a(c1004t.f3329c, c1004t.f3327a)) {
                C1012g.m3141c("WapClient: msg from wap exist - " + c1004t.f3327a);
            } else if (c1004t.f3336j) {
                c1004t.m3094a(m2901b(c1004t.f3329c));
                ad.m2993a(this.f3191g, c1004t);
                m2908c(c1004t);
            }
        }
    }

    private void m2915f() {
        List<C1004t> a = f3185b.m3031a();
        if (a != null) {
            for (C1004t c1004t : a) {
                if (c1004t.m3100g()) {
                    c1004t.m3094a(m2901b(c1004t.f3329c));
                    if (c1004t.m3101h()) {
                        m2904b(c1004t);
                    } else if (c1004t.m3102i()) {
                        m2908c(c1004t);
                    }
                }
                if (!c1004t.m3100g()) {
                    f3185b.m3034a(c1004t.f3337k);
                }
            }
        }
    }

    protected void m2916a() {
        AlarmManager alarmManager = (AlarmManager) getSystemService("alarm");
        this.f3190a = PendingIntent.getBroadcast(this, 0, new Intent(this, TickAlarmReceiver.class), 134217728);
        alarmManager.setRepeating(0, System.currentTimeMillis(), (long) 300000, this.f3190a);
    }

    public void m2917a(C1004t c1004t) {
        if (c1004t != null && c1004t.m3098e()) {
            C1012g.m3141c("parseContorlMsg is called - " + c1004t.f3327a);
            int i = c1004t.f3333g;
            String str = c1004t.f3334h;
            switch (i) {
                case 1:
                    C1012g.m3141c("parseContorlMsg: ss setting!");
                    aa.m2958a(this.f3191g).m2966a(c1004t);
                    return;
                case 2:
                    C1012g.m3141c("parseContorlMsg: hb setting!");
                    aa.m2958a(this.f3191g).m2984g(str);
                    i = aa.m2958a(this.f3191g).m2985h();
                    if (f3188e != null) {
                        f3188e.m2936a(i);
                        return;
                    }
                    return;
                case 3:
                    C1012g.m3141c("parseContorlMsg: fo setting!");
                    aa.m2958a(this.f3191g).m2964a(c1004t.f3338l, c1004t.f3329c, str);
                    return;
                case 4:
                    C1012g.m3141c("parseContorlMsg: lb setting!");
                    m2902b(this.f3191g);
                    return;
                default:
                    return;
            }
        }
    }

    protected synchronized void m2918a(boolean z) {
        String a = DroiPush.m2876a(this.f3191g);
        if (a != null) {
            if (C1015j.m3167d(this.f3191g)) {
                if (f3188e != null) {
                    f3188e.m2945g();
                    f3188e = null;
                }
                if (f3189f != null) {
                    if (z) {
                        f3189f.m2955c();
                    }
                }
                f3189f = m2887a(this.f3191g, a);
                f3189f.m2954b();
            } else {
                if (f3189f != null) {
                    f3189f.m2955c();
                    f3189f = null;
                }
                if (f3188e != null) {
                    if (z) {
                        try {
                            f3188e.m2945g();
                        } catch (Exception e) {
                        }
                    }
                }
                try {
                    f3188e = m2886a(C1015j.m3169e(a));
                    f3188e.m2936a(aa.m2958a(this.f3191g).m2985h());
                    f3188e.m2944f();
                } catch (Exception e2) {
                }
            }
        }
    }

    protected void m2919b() {
        ((AlarmManager) getSystemService("alarm")).cancel(this.f3190a);
    }

    protected void m2920c() {
        if (this.f3192h != null && this.f3192h.isHeld()) {
            this.f3192h.release();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.f3191g = getApplicationContext();
        DroiPush.initialize(getApplicationContext());
        this.f3196l = true;
        this.f3199o = ad.m2998b(this.f3191g);
    }

    public void onDestroy() {
        m2905b(true);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        String packageName = getPackageName();
        Pair c = ad.m3001c(getApplicationContext());
        if (c == null || packageName.equals(c.first)) {
            boolean b;
            String stringExtra;
            if (!this.f3198n) {
                b = ad.m3000b(this.f3199o);
                if (this.f3197m == null && !b) {
                    this.f3197m = new C1000o(this, this);
                    this.f3197m.start();
                    this.f3198n = true;
                    b = true;
                    if (!b || this.f3198n) {
                        if (this.f3196l) {
                            m2890a(this.f3191g);
                            m2916a();
                            this.f3192h = ((PowerManager) getSystemService("power")).newWakeLock(1, "OnlineService");
                            this.f3196l = false;
                        }
                        this.f3195k = f3185b.m3038b();
                        if (intent == null) {
                            return 1;
                        }
                        stringExtra = intent.getStringExtra("CMD");
                        if (C1015j.m3168d(stringExtra)) {
                            if ("NETWORK_CHANGE".equals(stringExtra)) {
                                m2918a(true);
                            } else if ("TICK".equals(stringExtra)) {
                                if (!(this.f3192h == null || this.f3192h.isHeld())) {
                                    this.f3192h.acquire();
                                }
                                if (this.f3193i != null) {
                                    this.f3193i.sendMessage(this.f3193i.obtainMessage(3));
                                }
                            } else if ("HEART_BEAT".equals(stringExtra)) {
                                if ("RESET_UDP".equals(stringExtra) || "RESET_TCP".equals(stringExtra)) {
                                    if (!(this.f3192h == null || this.f3192h.isHeld())) {
                                        this.f3192h.acquire();
                                    }
                                    m2918a(true);
                                    m2892a(intent);
                                }
                            } else if (f3188e != null) {
                                f3188e.m2936a(aa.m2958a(this.f3191g).m2985h());
                            }
                        }
                        return 1;
                    }
                    stopSelf();
                    return 2;
                }
            }
            b = false;
            if (b) {
            }
            if (this.f3196l) {
                m2890a(this.f3191g);
                m2916a();
                this.f3192h = ((PowerManager) getSystemService("power")).newWakeLock(1, "OnlineService");
                this.f3196l = false;
            }
            this.f3195k = f3185b.m3038b();
            if (intent == null) {
                return 1;
            }
            stringExtra = intent.getStringExtra("CMD");
            if (C1015j.m3168d(stringExtra)) {
                if ("NETWORK_CHANGE".equals(stringExtra)) {
                    m2918a(true);
                } else if ("TICK".equals(stringExtra)) {
                    this.f3192h.acquire();
                    if (this.f3193i != null) {
                        this.f3193i.sendMessage(this.f3193i.obtainMessage(3));
                    }
                } else if ("HEART_BEAT".equals(stringExtra)) {
                    this.f3192h.acquire();
                    m2918a(true);
                    m2892a(intent);
                } else if (f3188e != null) {
                    f3188e.m2936a(aa.m2958a(this.f3191g).m2985h());
                }
            }
            return 1;
        }
        stopSelf();
        return 2;
    }
}
