package com.baidu.platform.comapi.radar;

import android.os.Message;
import com.baidu.mapapi.UIMsg.m_AppUI;

class C0655d {
    private C0514c f2140a = null;
    private C0653a f2141b = null;

    C0655d() {
    }

    public void m2097a() {
        this.f2140a = null;
    }

    public void m2098a(Message message) {
        if (message.what == m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT && this.f2140a != null) {
            switch (message.arg1) {
                case 30001:
                    this.f2140a.onGetUploadResult(message.arg2);
                    return;
                case 30002:
                    if (message.arg2 == 0) {
                        this.f2140a.onGetNearByResult(this.f2141b.m2095c(), message.arg2);
                        return;
                    }
                    this.f2140a.onGetNearByResult(null, message.arg2);
                    return;
                case 30003:
                    this.f2140a.onGetClearInfoResult(message.arg2);
                    return;
                default:
                    return;
            }
        }
    }

    public void m2099a(C0653a c0653a) {
        this.f2141b = c0653a;
    }

    public void m2100a(C0514c c0514c) {
        this.f2140a = c0514c;
    }

    public void m2101b() {
        this.f2141b = null;
    }
}
