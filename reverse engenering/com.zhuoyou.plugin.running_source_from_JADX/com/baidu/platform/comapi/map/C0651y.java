package com.baidu.platform.comapi.map;

import android.os.Message;
import com.baidu.mapapi.UIMsg.m_AppUI;

class C0651y {
    private static final String f2133a = C0651y.class.getSimpleName();
    private C0499x f2134b;

    C0651y() {
    }

    void m2084a(Message message) {
        if (message.what == m_AppUI.V_WM_VDATAENGINE) {
            switch (message.arg1) {
                case -1:
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 12:
                case 101:
                case 102:
                    if (this.f2134b != null) {
                        this.f2134b.mo1792a(message.arg1, message.arg2);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    void m2085a(C0499x c0499x) {
        this.f2134b = c0499x;
    }

    void m2086b(C0499x c0499x) {
        this.f2134b = null;
    }
}
