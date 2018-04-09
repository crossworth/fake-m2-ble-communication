package com.droi.sdk.core;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;
import com.droi.sdk.core.CloudStorageDBHelper.RestfulObject;
import com.droi.sdk.core.priv.C0908j;
import com.droi.sdk.core.priv.C0944p;
import com.droi.sdk.core.priv.C0945q;
import com.droi.sdk.core.priv.CorePriv;
import com.droi.sdk.core.priv.DroiGroupRelation;
import com.droi.sdk.core.priv.PersistSettings;
import com.droi.sdk.internal.DroiDeviceInfoCollector;
import com.droi.sdk.internal.DroiLog;
import com.tyd.aidlservice.internal.Constants;
import com.tyd.aidlservice.internal.Tutil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Core {
    public static final String f2399a = "Core";
    public static final String f2400b = "com.droi.sdk.coreservice";
    static Core f2401c = null;
    static C0877b f2402d = null;
    private static final String f2403e = "com.droi.openplatform.service.domain.socket_name";
    private static final String f2404f = "com.droi.openplatform.service.global.locker.name";
    private static final String f2405g = "com.droi.sdk.application_id";
    private static final String f2406h = "com.droi.sdk.platform_key";
    private static final String f2407i = "com.droi.sdk.channel_name";
    private static final String f2408j = "com.droi.sdk.server";
    private static final int f2409o = 65537;
    private Context f2410k = null;
    private String f2411l = "";
    private boolean f2412m = false;
    private C0788b f2413n = null;

    class C07842 extends DroiRunnable {
        final /* synthetic */ Core f2385a;

        C07842(Core core) {
            this.f2385a = core;
        }

        public void run() {
            long[] a = C0944p.m2790a();
            if (!(a == null || (a[0] == 0 && a[1] == 0))) {
                this.f2385a.m2453a(a[0], a[1], true);
            }
            if (C0944p.m2802e(this.f2385a.f2410k)) {
                SocketLock socketLock = new SocketLock(Core.f2404f);
                while (true) {
                    if (socketLock.timedLock(5000)) {
                        C0785a d = Core.m2463e();
                        if (d == null) {
                            this.f2385a.m2464f();
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            while (Core.m2463e() == null && SystemClock.elapsedRealtime() - elapsedRealtime < StatisticConfig.MIN_UPLOAD_INTERVAL) {
                                try {
                                    Thread.sleep(50);
                                } catch (Exception e) {
                                    DroiLog.m2873w(Core.f2399a, e);
                                } catch (Throwable th) {
                                    socketLock.release();
                                }
                            }
                            if (SystemClock.elapsedRealtime() - elapsedRealtime >= StatisticConfig.MIN_UPLOAD_INTERVAL) {
                                DroiLog.m2870e(Core.f2399a, "Connect to local server fail.");
                            }
                        } else {
                            this.f2385a.m2453a(d.f2386a, d.f2387b, false);
                        }
                        socketLock.release();
                        this.f2385a.f2412m = true;
                        Log.i(Core.f2399a, "DroiBaaS Android SDK initialization finished.");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        if (Core.getActivity() != null) {
                            DroiDeviceInfoCollector.postDeviceInfo(this.f2385a.f2410k, CorePriv.getDeviceId(), Core.getDroiAppId(), true);
                        } else {
                            Log.w("Analytics", "getActivity() == null");
                        }
                    } else {
                        DroiLog.m2870e(Core.f2399a, "try lock fail.");
                    }
                }
            } else {
                Core.getDroiDeviceIdInternal();
                this.f2385a.f2412m = true;
                Log.i(Core.f2399a, "DroiBaaS Android SDK initialization finished.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e22) {
                    e22.printStackTrace();
                }
                if (Core.getActivity() != null) {
                    DroiDeviceInfoCollector.postDeviceInfo(this.f2385a.f2410k, CorePriv.getDeviceId(), Core.getDroiAppId(), true);
                } else {
                    Log.w("Analytics", "getActivity() == null");
                }
            }
        }
    }

    private static class C0785a {
        public long f2386a;
        public long f2387b;
        public String f2388c;

        private C0785a() {
        }

        public static C0785a m2447a(String str, String str2) {
            C0785a c0785a = new C0785a();
            String[] split = str.split(",");
            c0785a.f2386a = Long.parseLong(split[0]);
            c0785a.f2387b = Long.parseLong(split[1]);
            c0785a.f2388c = str2;
            return c0785a;
        }
    }

    class C0788b extends Thread {
        LocalServerSocket f2394a = null;
        long f2395b = 0;
        long f2396c = 0;
        volatile boolean f2397d = false;
        final /* synthetic */ Core f2398e;

        class C07872 implements Runnable {
            final /* synthetic */ C0788b f2393a;

            C07872(C0788b c0788b) {
                this.f2393a = c0788b;
            }

            public void run() {
                DroiLog.m2868d(Core.f2399a, "Try to fetch devcie id.");
                long[] b = Core.m2460b();
                if (b[0] != 0 && b[1] != 0) {
                    this.f2393a.f2396c = b[0];
                    this.f2393a.f2395b = b[1];
                    DroiLog.m2868d(Core.f2399a, "Device id GET!! " + this.f2393a.f2396c + ", " + this.f2393a.f2395b);
                }
            }
        }

        C0788b(Core core) {
            this.f2398e = core;
        }

        public void m2448a() {
            if (this.f2394a != null) {
                try {
                    this.f2394a.close();
                } catch (Exception e) {
                    DroiLog.m2873w(Core.f2399a, e);
                }
            }
        }

        public void run() {
            try {
                long[] b = Core.m2460b();
                this.f2396c = b[0];
                this.f2395b = b[1];
                this.f2394a = new LocalServerSocket(Core.f2403e);
                DroiLog.m2868d(Core.f2399a, "LocalServerSocket is at " + CorePriv.getContext().getPackageName() + ", " + this.f2396c + ", " + this.f2395b);
                final AtomicInteger atomicInteger = new AtomicInteger(0);
                final String uuid = UUID.randomUUID().toString();
                final TaskDispatcher dispatcher = TaskDispatcher.getDispatcher("fetchid");
                Runnable c07861 = new Runnable(this) {
                    final /* synthetic */ C0788b f2392d;

                    public void run() {
                        DroiLog.m2868d(Core.f2399a, "Try to fetch devcie id.");
                        long[] b = Core.m2460b();
                        if (b[0] == 0 || b[1] == 0) {
                            int i = atomicInteger.get() + 1;
                            if (i < 4) {
                                atomicInteger.set(i);
                                DroiLog.m2868d(Core.f2399a, "Still Can not fetch id, post task to get device id.");
                                dispatcher.enqueueTask(this, uuid, 5000);
                                return;
                            }
                            return;
                        }
                        this.f2392d.f2396c = b[0];
                        this.f2392d.f2395b = b[1];
                        DroiLog.m2868d(Core.f2399a, "Device id GET!! " + this.f2392d.f2396c + ", " + this.f2392d.f2395b);
                    }
                };
                Runnable c07872 = new C07872(this);
                if (this.f2396c == 0 || this.f2395b == 0) {
                    DroiLog.m2868d(Core.f2399a, "Can not fetch id, post task to get device id.");
                    dispatcher.enqueueTask(c07861, uuid);
                }
                while (!this.f2397d) {
                    LocalSocket accept = this.f2394a.accept();
                    if (accept != null) {
                        DroiLog.m2871i(Core.f2399a, "Client connected.");
                        if (this.f2396c == 0 || this.f2395b == 0) {
                            DroiLog.m2874w(Core.f2399a, "No device id, trigger task to fetch.");
                            dispatcher.enqueueTask(c07872, uuid);
                        }
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
                        bufferedWriter.write(String.valueOf(this.f2396c) + "," + String.valueOf(this.f2395b) + "\n");
                        bufferedWriter.write(Core.getCoreServiceVersion() + "\n");
                        bufferedWriter.flush();
                        DroiLog.m2868d(Core.f2399a, "Client connection done.");
                    }
                }
                this.f2394a.close();
                DroiLog.m2868d(Core.f2399a, "LocalSocketServer stopped");
            } catch (Exception e) {
                Exception exception = e;
                String message = exception.getMessage();
                if (message == null) {
                    message = exception.toString();
                }
                DroiLog.m2870e(getClass().getName(), message);
            }
            DroiLog.m2868d(Core.f2399a, "LocalSocketServer was terminated");
        }
    }

    protected Core(Context context, String str, String str2) {
        this.f2410k = context;
        this.f2411l = str;
        CorePriv.f2828b = str;
        CorePriv.f2829c = str2;
        DroiHttpRequest.initialize(context);
        if (checkPermission(context)) {
            try {
                TaskDispatcher.initialize();
            } catch (Exception e) {
            }
            f2402d.m2626b();
            DroiTask.create(new C07842(this)).runInBackground(TaskDispatcher.getDispatcher("init").name());
        }
    }

    private static Bundle m2450a(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
        } catch (NameNotFoundException e) {
            DroiLog.m2868d(f2399a, "Metadata not found");
            return null;
        }
    }

    static C0877b m2451a() {
        return f2402d;
    }

    private static String m2452a(int i) {
        Locale locale = Locale.getDefault();
        String str = locale.getLanguage() + "_" + locale.getCountry();
        Object obj = -1;
        switch (str.hashCode()) {
            case 115861276:
                if (str.equals("zh_CN")) {
                    obj = 1;
                    break;
                }
                break;
            case 115861812:
                if (str.equals("zh_TW")) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                switch (i) {
                    case 65537:
                        return "目前為沙盒模式";
                }
                break;
            case 1:
                break;
        }
        switch (i) {
            case 65537:
                return "目前为沙盒模式";
            default:
                break;
        }
        switch (i) {
            case 65537:
                return "The app is in the sandbox mode.";
            default:
                return "(" + i + ")";
        }
    }

    private void m2453a(long j, long j2, boolean z) {
        if (j != 0 || j2 != 0) {
            PersistSettings instance = PersistSettings.instance(PersistSettings.CONFIG);
            long j3 = instance.getLong(PersistSettings.KEY_UID_U, 0);
            long j4 = instance.getLong(PersistSettings.KEY_UID_L, 0);
            if (z || (j3 == 0 && j4 == 0)) {
                long GetKlKeyUID_u = Tutil.GetKlKeyUID_u();
                long GetKlKeyUID_l = Tutil.GetKlKeyUID_l();
                if (!(j == GetKlKeyUID_u && j2 == GetKlKeyUID_l)) {
                    DroiHttpRequest.instance().m2555a(j, j2);
                }
                if (!(j3 == j && j4 == j2)) {
                    instance.setLong(PersistSettings.KEY_UID_U, j);
                    instance.setLong(PersistSettings.KEY_UID_L, j2);
                }
                if (z && instance.getInt(PersistSettings.KEY_UID_FROM_FREEMEOS, 0) != 1) {
                    instance.setInt(PersistSettings.KEY_UID_FROM_FREEMEOS, 1);
                }
            }
        }
    }

    private static void m2454a(Context context, String str) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final AtomicReference atomicReference = new AtomicReference(null);
        final AtomicReference atomicReference2 = new AtomicReference(null);
        final AtomicReference atomicReference3 = new AtomicReference(null);
        int nextInt = new Random().nextInt(1000) + MessageHandler.WHAT_ITEM_SELECTED;
        final String str2 = str;
        TaskDispatcher.getDispatcher(TaskDispatcher.MainThreadName).enqueueTask(new Runnable() {
            public void run() {
                int i = atomicInteger.get();
                if (i == 1 || i == 3) {
                    WindowManager windowManager = (WindowManager) atomicReference.get();
                    View view = (View) atomicReference2.get();
                    if (!(windowManager == null || view == null)) {
                        windowManager.removeView(view);
                    }
                    if (i == 1 && Core.getActivity() != atomicReference3.get()) {
                        atomicInteger.set(2);
                        Core.m2459b(str2, atomicReference3, atomicReference, atomicReference2, atomicInteger, this);
                        return;
                    }
                    return;
                }
                Core.m2459b(str2, atomicReference3, atomicReference, atomicReference2, atomicInteger, this);
            }
        }, nextInt);
    }

    private static void m2459b(String str, AtomicReference<Activity> atomicReference, AtomicReference<WindowManager> atomicReference2, AtomicReference<View> atomicReference3, AtomicInteger atomicInteger, Runnable runnable) {
        LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = 51;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.height = -1;
        layoutParams.width = -1;
        layoutParams.flags = 408;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 0;
        Context activity = getActivity();
        if (activity != null) {
            atomicReference.set(activity);
            View textView = new TextView(activity);
            textView.setGravity(17);
            textView.setBackgroundColor(Color.argb(180, 0, 0, 0));
            textView.setTextColor(-1);
            textView.setText(str);
            textView.setTextSize(32.0f);
            WindowManager windowManager = (WindowManager) activity.getSystemService("window");
            if (windowManager != null) {
                windowManager.addView(textView, layoutParams);
                atomicReference2.set(windowManager);
                atomicReference3.set(textView);
                atomicInteger.set(atomicInteger.get() + 1);
                TaskDispatcher.getDispatcher(TaskDispatcher.MainThreadName).enqueueTask(runnable, 2000);
            }
        }
    }

    static long[] m2460b() {
        String deviceId = CorePriv.getDeviceId();
        if (deviceId != null) {
            UUID fromString = UUID.fromString(deviceId);
            return new long[]{fromString.getMostSignificantBits(), fromString.getLeastSignificantBits()};
        }
        long j;
        long[] jArr;
        boolean e = C0944p.m2802e(CorePriv.getContext());
        DroiHttpRequest instance = DroiHttpRequest.instance();
        PersistSettings persistSettings = null;
        PersistSettings instance2 = PersistSettings.instance(PersistSettings.CONFIG);
        if (e) {
            persistSettings = PersistSettings.instance(PersistSettings.GLOBAL_CONFIG);
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = 0;
        if (e) {
            j = persistSettings.getLong(PersistSettings.KEY_UID_U, 0);
            j2 = persistSettings.getLong(PersistSettings.KEY_UID_L, 0);
        } else {
            j = 0;
        }
        Object obj = null;
        long[] a = C0944p.m2790a();
        int i = instance2.getInt(PersistSettings.KEY_UID_FROM_FREEMEOS, 0);
        if (a == null) {
            a = instance.m2556a();
            if (i == 0) {
                instance2.setInt(PersistSettings.KEY_UID_FROM_FREEMEOS, 2);
                jArr = a;
            }
            jArr = a;
        } else {
            if (i == 0) {
                instance2.setInt(PersistSettings.KEY_UID_FROM_FREEMEOS, 1);
            }
            jArr = a;
        }
        Object obj2 = null;
        if (j == 0 || r2 == 0) {
            long j3 = instance2.getLong(PersistSettings.KEY_UID_U, 0);
            j = instance2.getLong(PersistSettings.KEY_UID_L, 0);
            if (!(j3 == 0 || j == 0)) {
                obj2 = 1;
            }
            if (e) {
                obj = 1;
                j2 = j;
                j = j3;
            } else {
                j2 = j;
                j = j3;
            }
        }
        if (j == 0 || j2 == 0) {
            j = jArr[0];
            j2 = jArr[1];
            if (!(obj2 != null || j == 0 || j2 == 0)) {
                instance2.setLong(PersistSettings.KEY_UID_U, j);
                instance2.setLong(PersistSettings.KEY_UID_L, j2);
            }
        } else if (!((j == jArr[0] && j2 == jArr[1]) || j == 0 || j2 == 0)) {
            instance.m2555a(j, j2);
        }
        if (!(obj == null || j == 0 || j2 == 0)) {
            persistSettings.setLong(PersistSettings.KEY_UID_U, j);
            persistSettings.setLong(PersistSettings.KEY_UID_L, j2);
        }
        DroiLog.m2871i(f2399a, "generate uid time: " + (SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
        return new long[]{j, j2};
    }

    public static boolean checkPermission(Context context) {
        return true;
    }

    public static void deinitialize() {
        if (f2401c != null) {
            f2401c = null;
        }
    }

    private static C0785a m2463e() {
        LocalSocket localSocket;
        Throwable th;
        C0785a c0785a = null;
        try {
            localSocket = new LocalSocket();
            try {
                localSocket.connect(new LocalSocketAddress(f2403e));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
                DroiLog.m2868d(f2399a, "Server connected");
                String readLine = bufferedReader.readLine();
                String readLine2 = bufferedReader.readLine();
                DroiLog.m2868d(f2399a, "Server connection finished. " + readLine);
                c0785a = C0785a.m2447a(readLine, readLine2);
                if (localSocket != null) {
                    try {
                        localSocket.close();
                    } catch (Exception e) {
                        DroiLog.m2873w(f2399a, e);
                    }
                }
            } catch (Exception e2) {
                if (localSocket != null) {
                    try {
                        localSocket.close();
                    } catch (Exception e3) {
                        DroiLog.m2873w(f2399a, e3);
                    }
                }
                return c0785a;
            } catch (Throwable th2) {
                th = th2;
                if (localSocket != null) {
                    try {
                        localSocket.close();
                    } catch (Exception e32) {
                        DroiLog.m2873w(f2399a, e32);
                    }
                }
                throw th;
            }
        } catch (Exception e4) {
            localSocket = c0785a;
            if (localSocket != null) {
                localSocket.close();
            }
            return c0785a;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            localSocket = c0785a;
            th = th4;
            if (localSocket != null) {
                localSocket.close();
            }
            throw th;
        }
        return c0785a;
    }

    private void m2464f() {
        if (this.f2413n == null) {
            DroiLog.m2868d(f2399a, "Create server");
            this.f2413n = new C0788b(this);
            this.f2413n.start();
        }
    }

    public static Activity getActivity() {
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            Map map = (Map) declaredField.get(invoke);
            if (map == null) {
                return null;
            }
            for (Object invoke2 : map.values()) {
                Class cls2 = invoke2.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(invoke2)) {
                    declaredField = cls2.getDeclaredField("activity");
                    declaredField.setAccessible(true);
                    return (Activity) declaredField.get(invoke2);
                }
            }
            return null;
        } catch (Exception e) {
            DroiLog.m2870e(f2399a, "get current activity fail. will not pop up sandbox window");
        }
    }

    public static String getChannelName(Context context) {
        if (CorePriv.f2831e != null) {
            return CorePriv.f2831e;
        }
        if (CorePriv.f2830d != null) {
            return CorePriv.f2830d;
        }
        if (context == null) {
            return CorePriv.f2827a;
        }
        Bundle a = m2450a(context);
        String str = CorePriv.f2827a;
        if (a != null && a.containsKey(f2407i)) {
            str = a.getString(f2407i);
        }
        CorePriv.f2830d = str;
        return str;
    }

    public static String getCoreServiceVersion() {
        return C0945q.f3082a;
    }

    static String getDroiAppId() {
        return f2401c == null ? null : f2401c.f2411l;
    }

    static String getDroiDeviceId() {
        PersistSettings persistSettings = null;
        if (f2401c == null) {
            Log.d("test", "SDK is not initialize");
            return null;
        }
        if (!f2401c.f2412m) {
            Log.d("test", "SDK is not ready. Waiting...");
            long elapsedRealtime = SystemClock.elapsedRealtime();
            while (SystemClock.elapsedRealtime() - elapsedRealtime < 5000 && !f2401c.f2412m) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (f2401c.f2412m) {
            String deviceId = CorePriv.getDeviceId();
            if (deviceId != null) {
                return deviceId;
            }
            C0785a e2 = m2463e();
            if (e2 == null || e2.f2387b == 0 || e2.f2386a == 0) {
                return null;
            }
            if (C0944p.m2802e(CorePriv.getContext())) {
                persistSettings = PersistSettings.instance(PersistSettings.GLOBAL_CONFIG);
            }
            PersistSettings instance = PersistSettings.instance(PersistSettings.CONFIG);
            if (!(instance.getLong(PersistSettings.KEY_UID_U, 0) == e2.f2386a && instance.getLong(PersistSettings.KEY_UID_L, 0) == e2.f2387b) && instance.getLong(PersistSettings.KEY_UID_U, 0) == 0 && instance.getLong(PersistSettings.KEY_UID_L, 0) == 0) {
                instance.setLong(PersistSettings.KEY_UID_U, e2.f2386a);
                instance.setLong(PersistSettings.KEY_UID_L, e2.f2387b);
            }
            if (!(persistSettings == null || (persistSettings.getLong(PersistSettings.KEY_UID_U, 0) == e2.f2386a && persistSettings.getLong(PersistSettings.KEY_UID_L, 0) == e2.f2387b))) {
                persistSettings.setLong(PersistSettings.KEY_UID_U, e2.f2386a);
                persistSettings.setLong(PersistSettings.KEY_UID_L, e2.f2387b);
            }
            return CorePriv.getDeviceId();
        }
        Log.d("test", "SDK is still not ready");
        return null;
    }

    private static String getDroiDeviceIdInternal() {
        if (f2401c == null) {
            Log.d("test", "SDK is not initialize");
            return null;
        }
        String deviceId = CorePriv.getDeviceId();
        if (deviceId != null) {
            return deviceId;
        }
        m2460b();
        return CorePriv.getDeviceId();
    }

    public static Date getTimestamp() {
        return new Date(new Date().getTime() + DroiHttpRequest.f2568a);
    }

    public static synchronized void initialize(Context context) {
        synchronized (Core.class) {
            Log.i(f2399a, "DroiBaaS Android SDK initializing. Version: 1.0.3512");
            Bundle a = m2450a(context.getApplicationContext());
            if (a == null) {
                throw new RuntimeException("Can not get metadatas. Need to set \"com.droi.sdk.application_id\"");
            }
            String string = a.getString(f2405g);
            String string2 = a.getString(f2406h);
            if (string == null) {
                throw new RuntimeException("Application id is null. Need to set \"com.droi.sdk.application_id\"");
            } else if (string2 == null) {
                throw new RuntimeException("Platform key is null. Need to set \"com.droi.sdk.platform_key\"");
            } else {
                if (C0944p.m2799d(string)) {
                    String string3 = a.getString(f2408j);
                    if (string3 != null) {
                        Constants.IP_LIST_URL = string3;
                    }
                }
                Context applicationContext = context.getApplicationContext();
                CorePriv.setServiceContext(applicationContext);
                C0908j.m2689a(applicationContext);
                PersistSettings.initialize(applicationContext);
                DroiObject.registerCustomClass(DroiUser.class);
                DroiObject.registerCustomClass(DroiGroup.class);
                DroiObject.registerCustomClass(DroiFile.class);
                DroiObject.registerCustomClass(DroiGroupRelation.class);
                DroiObject.registerCustomClass(RestfulObject.class);
                try {
                    TaskDispatcher.initialize();
                } catch (Exception e) {
                }
                if (f2401c != null) {
                    Log.i(f2399a, "DroiBaaS Android SDK had initialized.");
                } else {
                    try {
                        Class.forName("com.tyd.aidlservice.internal.Tutil");
                    } catch (ClassNotFoundException e2) {
                        Log.e(f2399a, "Try to load Tutil fail. (not found)");
                    }
                    f2402d = new C0877b(applicationContext);
                    if (C0944p.m2799d(string)) {
                        String a2 = m2452a(65537);
                        Log.i(f2399a, a2);
                        m2454a(context, a2);
                    }
                    f2401c = new Core(applicationContext, string, string2);
                }
            }
        }
    }

    public static void setChannelName(String str) {
        CorePriv.f2831e = str;
    }
}
