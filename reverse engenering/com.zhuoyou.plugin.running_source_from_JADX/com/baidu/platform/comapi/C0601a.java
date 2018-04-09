package com.baidu.platform.comapi;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.VersionInfo;
import com.baidu.platform.comapi.util.C0668c;
import com.baidu.platform.comapi.util.C0671f;
import com.baidu.platform.comapi.util.PermissionCheck;
import com.baidu.platform.comapi.util.PermissionCheck.C0600c;
import com.baidu.platform.comapi.util.PermissionCheck.C0663b;
import com.baidu.platform.comjni.engine.AppEngine;
import com.baidu.platform.comjni.engine.C0672a;
import com.baidu.vi.C0684b;
import com.baidu.vi.VMsg;

public class C0601a implements C0600c {
    private static final String f1894a = C0601a.class.getSimpleName();
    private static C0601a f1895f;
    private static int f1896g = -100;
    private Context f1897b;
    private Handler f1898c;
    private C0605d f1899d;
    private int f1900e;

    static {
        NativeLoader.getInstance().loadLibrary(VersionInfo.getKitName());
        AppEngine.InitClass();
    }

    private C0601a() {
    }

    public static C0601a m1853a() {
        if (f1895f == null) {
            f1895f = new C0601a();
        }
        return f1895f;
    }

    private void m1854f() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        if (this.f1897b != null && this.f1899d != null) {
            this.f1897b.registerReceiver(this.f1899d, intentFilter);
        }
    }

    private void m1855g() {
        if (this.f1899d != null && this.f1897b != null) {
            this.f1897b.unregisterReceiver(this.f1899d);
        }
    }

    public void m1856a(Context context) {
        this.f1897b = context;
    }

    public void m1857a(Message message) {
        if (message.what == 2012) {
            Intent intent;
            if (message.arg1 == 0) {
                intent = new Intent(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
            } else {
                intent = new Intent(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
                intent.putExtra("error_code", message.arg1);
            }
            this.f1897b.sendBroadcast(intent);
            return;
        }
        if (message.arg2 == 3) {
            this.f1897b.sendBroadcast(new Intent(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR));
        }
        if (message.arg2 == 2 || message.arg2 == 404 || message.arg2 == 5 || message.arg2 == 8) {
            this.f1897b.sendBroadcast(new Intent(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR));
        }
    }

    public void mo1830a(C0663b c0663b) {
        if (c0663b != null) {
            if (c0663b.f2161a == 0) {
                C0671f.f2194A = c0663b.f2165e;
                C0671f.m2168a(c0663b.f2162b, c0663b.f2163c);
            } else {
                Log.e("baidumapsdk", "Authentication Error " + c0663b.toString());
            }
            if (this.f1898c != null && c0663b.f2161a != f1896g) {
                f1896g = c0663b.f2161a;
                Message.obtain(this.f1898c, 2012, c0663b.f2161a, c0663b.f2161a, null).sendToTarget();
            }
        }
    }

    public void m1859b() {
        if (this.f1900e == 0) {
            if (this.f1897b == null) {
                throw new IllegalStateException("you have not supplyed the global app context info from SDKInitializer.initialize(Context) function.");
            }
            C0684b.m2310a(this.f1897b);
            VMsg.init();
            AppEngine.InitEngine(this.f1897b, C0671f.m2166a());
            AppEngine.StartSocketProc();
            this.f1899d = new C0605d();
            m1854f();
            C0668c.m2150a(this.f1897b);
        }
        this.f1900e++;
    }

    public boolean m1860c() {
        if (this.f1897b == null) {
            throw new IllegalStateException("you have not supplyed the global app context info from SDKInitializer.initialize(Context) function.");
        }
        C0672a.m2189a(2000, this.f1898c);
        this.f1898c = new C0603b(this);
        C0671f.m2171b(this.f1897b);
        C0671f.m2170b();
        C0671f.m2176e();
        PermissionCheck.init(this.f1897b);
        PermissionCheck.setPermissionCheckResultListener(this);
        PermissionCheck.permissionCheck();
        return true;
    }

    public void m1861d() {
        this.f1900e--;
        if (this.f1900e == 0) {
            m1855g();
            VMsg.destroy();
            C0672a.m2187a();
            AppEngine.UnInitEngine();
        }
    }

    public Context m1862e() {
        if (this.f1897b != null) {
            return this.f1897b;
        }
        throw new IllegalStateException("you have not supplyed the global app context info from SDKInitializer.initialize(Context) function.");
    }
}
