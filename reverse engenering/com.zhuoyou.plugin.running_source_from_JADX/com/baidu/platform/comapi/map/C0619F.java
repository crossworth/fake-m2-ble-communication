package com.baidu.platform.comapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import com.baidu.mapapi.UIMsg.m_AppUI;

class C0619F extends Handler {
    final /* synthetic */ C0618E f1988a;

    C0619F(C0618E c0618e) {
        this.f1988a = c0618e;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (this.f1988a.f1987f != null && this.f1988a.f1987f.f2053g != null && ((Long) message.obj).longValue() == this.f1988a.f1987f.f2054h) {
            if (message.what == m_AppUI.MSG_APP_SAVESCREEN) {
                for (C0482k c0482k : this.f1988a.f1987f.f2052f) {
                    Bitmap bitmap = null;
                    if (message.arg2 == 1) {
                        int[] iArr = new int[(C0618E.f1982a * C0618E.f1983b)];
                        int[] iArr2 = new int[(C0618E.f1982a * C0618E.f1983b)];
                        if (this.f1988a.f1987f.f2053g != null) {
                            int[] a = this.f1988a.f1987f.f2053g.m2212a(iArr, C0618E.f1982a, C0618E.f1983b);
                            for (int i = 0; i < C0618E.f1983b; i++) {
                                for (int i2 = 0; i2 < C0618E.f1982a; i2++) {
                                    int i3 = a[(C0618E.f1982a * i) + i2];
                                    iArr2[(((C0618E.f1983b - i) - 1) * C0618E.f1982a) + i2] = ((i3 & -16711936) | ((i3 << 16) & 16711680)) | ((i3 >> 16) & 255);
                                }
                            }
                            bitmap = Bitmap.createBitmap(iArr2, C0618E.f1982a, C0618E.f1983b, Config.ARGB_8888);
                        } else {
                            return;
                        }
                    }
                    c0482k.mo1771a(bitmap);
                }
            } else if (message.what == 39) {
                if (this.f1988a.f1987f != null) {
                    if (message.arg1 == 100) {
                        this.f1988a.f1987f.m1950A();
                    } else if (message.arg1 == 200) {
                        this.f1988a.f1987f.m1960K();
                    } else if (message.arg1 == 1) {
                        if (this.f1988a.f1986e != null) {
                            this.f1988a.f1986e.m2057a();
                        }
                    } else if (message.arg1 == 0) {
                        if (this.f1988a.f1986e != null) {
                            this.f1988a.f1986e.m2057a();
                        }
                    } else if (message.arg1 == 2) {
                        for (C0482k c0482k2 : this.f1988a.f1987f.f2052f) {
                            c0482k2.mo1782c();
                        }
                    }
                    if (!this.f1988a.f1987f.f2055i && C0618E.f1983b > 0 && C0618E.f1982a > 0 && this.f1988a.f1987f.m1988b(0, 0) != null) {
                        this.f1988a.f1987f.f2055i = true;
                        for (C0482k c0482k22 : this.f1988a.f1987f.f2052f) {
                            c0482k22.mo1778b();
                        }
                    }
                    for (C0482k c0482k222 : this.f1988a.f1987f.f2052f) {
                        c0482k222.mo1770a();
                    }
                }
            } else if (message.what == 41) {
                if (this.f1988a.f1987f == null) {
                    return;
                }
                if (this.f1988a.f1987f.f2057l || this.f1988a.f1987f.f2058m) {
                    for (C0482k c0482k2222 : this.f1988a.f1987f.f2052f) {
                        c0482k2222.mo1780b(this.f1988a.f1987f.m1953D());
                    }
                }
            } else if (message.what == 999) {
                for (C0482k c0482k22222 : this.f1988a.f1987f.f2052f) {
                    c0482k22222.mo1787e();
                }
            } else if (message.what == 50) {
                for (C0482k c0482k222222 : this.f1988a.f1987f.f2052f) {
                    if (message.arg1 == 0) {
                        c0482k222222.mo1777a(false);
                    } else if (message.arg1 == 1) {
                        c0482k222222.mo1777a(true);
                    }
                }
            }
        }
    }
}
