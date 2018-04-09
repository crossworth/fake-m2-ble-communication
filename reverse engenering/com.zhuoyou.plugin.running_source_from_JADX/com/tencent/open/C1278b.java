package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import com.tencent.open.p036a.C1314f;

/* compiled from: ProGuard */
public abstract class C1278b extends Dialog {
    private static final String TAG = "openSDK_LOG.JsDialog";
    protected C1317a jsBridge;
    @SuppressLint({"NewApi"})
    protected final WebChromeClient mChromeClient = new C13181(this);

    /* compiled from: ProGuard */
    class C13181 extends WebChromeClient {
        final /* synthetic */ C1278b f4132a;

        C13181(C1278b c1278b) {
            this.f4132a = c1278b;
        }

        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (consoleMessage == null) {
                return false;
            }
            C1314f.m3870c(C1278b.TAG, "WebChromeClient onConsoleMessage" + consoleMessage.message() + " -- From  111 line " + consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
            if (VERSION.SDK_INT > 7) {
                this.f4132a.onConsoleMessage(consoleMessage == null ? "" : consoleMessage.message());
            }
            return true;
        }

        public void onConsoleMessage(String str, int i, String str2) {
            C1314f.m3870c(C1278b.TAG, "WebChromeClient onConsoleMessage" + str + " -- From 222 line " + i + " of " + str2);
            if (VERSION.SDK_INT == 7) {
                this.f4132a.onConsoleMessage(str);
            }
        }
    }

    protected abstract void onConsoleMessage(String str);

    public C1278b(Context context) {
        super(context);
    }

    public C1278b(Context context, int i) {
        super(context, i);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.jsBridge = new C1317a();
    }
}
