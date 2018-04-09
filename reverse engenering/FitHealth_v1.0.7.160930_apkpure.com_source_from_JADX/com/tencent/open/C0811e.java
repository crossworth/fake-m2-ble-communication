package com.tencent.open;

import android.os.Build.VERSION;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

/* compiled from: ProGuard */
class C0811e extends WebChromeClient {
    final /* synthetic */ C0812h f2749a;

    C0811e(C0812h c0812h) {
        this.f2749a = c0812h;
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.i("WebConsole", consoleMessage.message() + " -- From  111 line " + consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
        if (VERSION.SDK_INT > 7) {
            this.f2749a.onConsoleMessage(consoleMessage == null ? "" : consoleMessage.message());
        }
        return true;
    }

    public void onConsoleMessage(String str, int i, String str2) {
        Log.i("WebConsole", str + " -- From 222 line " + i + " of " + str2);
        if (VERSION.SDK_INT == 7) {
            this.f2749a.onConsoleMessage(str);
        }
    }
}
