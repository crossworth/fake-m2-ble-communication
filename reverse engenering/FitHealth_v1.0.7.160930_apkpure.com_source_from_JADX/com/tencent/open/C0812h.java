package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebChromeClient;

/* compiled from: ProGuard */
public abstract class C0812h extends Dialog {
    protected C0803a jsBridge;
    @SuppressLint({"NewApi"})
    protected final WebChromeClient mChromeClient = new C0811e(this);

    protected abstract void onConsoleMessage(String str);

    public C0812h(Context context) {
        super(context);
    }

    public C0812h(Context context, int i) {
        super(context, i);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.jsBridge = new C0803a();
    }
}
