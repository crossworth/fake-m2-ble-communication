package com.baidu.platform.comapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import com.baidu.mapapi.UIMsg.m_AppUI;

class C0639j extends Handler {
    final /* synthetic */ C0638i f2097a;

    C0639j(C0638i c0638i) {
        this.f2097a = c0638i;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (this.f2097a.f2096g == null || ((Long) message.obj).longValue() != this.f2097a.f2096g.f2054h) {
            return;
        }
        if (message.what == m_AppUI.MSG_APP_SAVESCREEN) {
            for (C0482k c0482k : this.f2097a.f2096g.f2052f) {
                Bitmap bitmap = null;
                if (message.arg2 == 1) {
                    int[] iArr = new int[(this.f2097a.f2093d * this.f2097a.f2094e)];
                    int[] iArr2 = new int[(this.f2097a.f2093d * this.f2097a.f2094e)];
                    if (this.f2097a.f2096g.f2053g != null) {
                        int[] a = this.f2097a.f2096g.f2053g.m2212a(iArr, this.f2097a.f2093d, this.f2097a.f2094e);
                        for (int i = 0; i < this.f2097a.f2094e; i++) {
                            for (int i2 = 0; i2 < this.f2097a.f2093d; i2++) {
                                int i3 = a[(this.f2097a.f2093d * i) + i2];
                                iArr2[(((this.f2097a.f2094e - i) - 1) * this.f2097a.f2093d) + i2] = ((i3 & -16711936) | ((i3 << 16) & 16711680)) | ((i3 >> 16) & 255);
                            }
                        }
                        bitmap = Bitmap.createBitmap(iArr2, this.f2097a.f2093d, this.f2097a.f2094e, Config.ARGB_8888);
                    } else {
                        return;
                    }
                }
                c0482k.mo1771a(bitmap);
            }
        } else if (message.what == 39) {
            if (this.f2097a.f2096g != null) {
                if (message.arg1 == 100) {
                    this.f2097a.f2096g.m1950A();
                } else if (message.arg1 == 200) {
                    this.f2097a.f2096g.m1960K();
                } else if (message.arg1 == 1) {
                    this.f2097a.requestRender();
                } else if (message.arg1 == 0) {
                    this.f2097a.requestRender();
                    if (!(this.f2097a.f2096g.m1997c() || this.f2097a.getRenderMode() == 0)) {
                        this.f2097a.setRenderMode(0);
                    }
                } else if (message.arg1 == 2) {
                    for (C0482k c0482k2 : this.f2097a.f2096g.f2052f) {
                        c0482k2.mo1782c();
                    }
                }
                if (!this.f2097a.f2096g.f2055i && this.f2097a.f2094e > 0 && this.f2097a.f2093d > 0 && this.f2097a.f2096g.m1988b(0, 0) != null) {
                    this.f2097a.f2096g.f2055i = true;
                    for (C0482k c0482k22 : this.f2097a.f2096g.f2052f) {
                        c0482k22.mo1778b();
                    }
                }
                for (C0482k c0482k222 : this.f2097a.f2096g.f2052f) {
                    c0482k222.mo1770a();
                }
            }
        } else if (message.what == 41) {
            if (this.f2097a.f2096g == null) {
                return;
            }
            if (this.f2097a.f2096g.f2057l || this.f2097a.f2096g.f2058m) {
                for (C0482k c0482k2222 : this.f2097a.f2096g.f2052f) {
                    c0482k2222.mo1780b(this.f2097a.f2096g.m1953D());
                }
            }
        } else if (message.what == 999) {
            for (C0482k c0482k22222 : this.f2097a.f2096g.f2052f) {
                c0482k22222.mo1787e();
            }
        } else if (message.what == 50) {
            for (C0482k c0482k222222 : this.f2097a.f2096g.f2052f) {
                if (message.arg1 == 0) {
                    c0482k222222.mo1777a(false);
                } else if (message.arg1 == 1) {
                    c0482k222222.mo1777a(true);
                }
            }
        }
    }
}
