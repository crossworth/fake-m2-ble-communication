package com.tencent.open.web.security;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import com.tencent.open.p036a.C1314f;

/* compiled from: ProGuard */
public class C1346a extends InputConnectionWrapper {
    public static String f4238a;
    public static boolean f4239b = false;
    public static boolean f4240c = false;

    public C1346a(InputConnection inputConnection, boolean z) {
        super(inputConnection, z);
    }

    public boolean setComposingText(CharSequence charSequence, int i) {
        f4240c = true;
        f4238a = charSequence.toString();
        C1314f.m3864a("openSDK_LOG.CaptureInputConnection", "-->setComposingText: " + charSequence.toString());
        return super.setComposingText(charSequence, i);
    }

    public boolean commitText(CharSequence charSequence, int i) {
        f4240c = true;
        f4238a = charSequence.toString();
        C1314f.m3864a("openSDK_LOG.CaptureInputConnection", "-->commitText: " + charSequence.toString());
        return super.commitText(charSequence, i);
    }

    public boolean sendKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            C1314f.m3870c("openSDK_LOG.CaptureInputConnection", "sendKeyEvent");
            f4238a = String.valueOf((char) keyEvent.getUnicodeChar());
            f4240c = true;
            C1314f.m3867b("openSDK_LOG.CaptureInputConnection", "s: " + f4238a);
        }
        C1314f.m3867b("openSDK_LOG.CaptureInputConnection", "-->sendKeyEvent: " + f4238a);
        return super.sendKeyEvent(keyEvent);
    }
}
