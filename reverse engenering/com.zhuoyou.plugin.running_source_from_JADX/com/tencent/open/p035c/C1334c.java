package com.tencent.open.p035c;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.web.security.C1346a;
import com.tencent.open.web.security.SecureJsInterface;

/* compiled from: ProGuard */
public class C1334c extends C1333b {
    public static boolean f4174a;
    private KeyEvent f4175b;
    private C1346a f4176c;

    public C1334c(Context context) {
        super(context);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        C1314f.m3867b("openSDK_LOG.SecureWebView", "-->dispatchKeyEvent, is device support: " + f4174a);
        if (!f4174a) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyEvent.getAction() != 0) {
            return super.dispatchKeyEvent(keyEvent);
        }
        switch (keyEvent.getKeyCode()) {
            case 4:
                return super.dispatchKeyEvent(keyEvent);
            case 66:
                return super.dispatchKeyEvent(keyEvent);
            case 67:
                C1346a.f4239b = true;
                return super.dispatchKeyEvent(keyEvent);
            default:
                if (keyEvent.getUnicodeChar() == 0) {
                    return super.dispatchKeyEvent(keyEvent);
                }
                if (SecureJsInterface.isPWDEdit) {
                    int unicodeChar = keyEvent.getUnicodeChar();
                    if ((unicodeChar >= 33 && unicodeChar <= 95) || (unicodeChar >= 97 && unicodeChar <= 125)) {
                        this.f4175b = new KeyEvent(0, 17);
                        return super.dispatchKeyEvent(this.f4175b);
                    }
                }
                return super.dispatchKeyEvent(keyEvent);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        C1314f.m3867b("openSDK_LOG.SecureWebView", "-->onKeyDown, is device support: " + f4174a);
        if (!f4174a) {
            return super.onKeyDown(i, keyEvent);
        }
        if (keyEvent.getAction() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        switch (keyEvent.getKeyCode()) {
            case 4:
                return super.onKeyDown(i, keyEvent);
            case 66:
                return super.onKeyDown(i, keyEvent);
            case 67:
                C1346a.f4239b = true;
                return super.onKeyDown(i, keyEvent);
            default:
                if (keyEvent.getUnicodeChar() == 0) {
                    return super.onKeyDown(i, keyEvent);
                }
                if (SecureJsInterface.isPWDEdit) {
                    int unicodeChar = keyEvent.getUnicodeChar();
                    if ((unicodeChar >= 33 && unicodeChar <= 95) || (unicodeChar >= 97 && unicodeChar <= 125)) {
                        this.f4175b = new KeyEvent(0, 17);
                        return super.onKeyDown(this.f4175b.getKeyCode(), this.f4175b);
                    }
                }
                return super.onKeyDown(i, keyEvent);
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        C1314f.m3870c("openSDK_LOG.SecureWebView", "-->create input connection, is edit: " + SecureJsInterface.isPWDEdit);
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        C1314f.m3864a("openSDK_LOG.SecureWebView", "-->onCreateInputConnection, inputConn is " + onCreateInputConnection);
        if (onCreateInputConnection != null) {
            f4174a = true;
            this.f4176c = new C1346a(super.onCreateInputConnection(editorInfo), false);
            return this.f4176c;
        }
        f4174a = false;
        return onCreateInputConnection;
    }
}
