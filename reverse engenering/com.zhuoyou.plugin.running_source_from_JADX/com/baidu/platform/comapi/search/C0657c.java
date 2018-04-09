package com.baidu.platform.comapi.search;

import android.os.Message;
import com.baidu.mapapi.UIMsg.d_ResultType;

class C0657c {
    private static final String f2147a = C0657c.class.getSimpleName();
    private C0518b f2148b = null;
    private C0658d f2149c = null;

    C0657c() {
    }

    public void m2102a() {
        this.f2149c = null;
    }

    public void m2103a(Message message) {
        if (message.what != 2000 || this.f2148b == null) {
            return;
        }
        if (message.arg2 == 0) {
            String b;
            switch (message.arg1) {
                case 7:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(7);
                        if (b == null || b.equals("")) {
                            this.f2148b.mo1803b(null);
                            return;
                        } else {
                            this.f2148b.mo1803b(b);
                            return;
                        }
                    }
                    return;
                case 10:
                    if (message.arg2 == 0) {
                        this.f2148b.mo1816o(this.f2149c.m2130b(message.arg1));
                        return;
                    }
                    return;
                case 11:
                case 21:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(11);
                        if (b == null || b.equals("")) {
                            this.f2148b.mo1802a(null);
                            return;
                        } else {
                            this.f2148b.mo1802a(b);
                            return;
                        }
                    }
                    return;
                case 14:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(message.arg1);
                        if (b == null || b.trim().length() <= 0) {
                            this.f2148b.mo1810i(null);
                            return;
                        } else {
                            this.f2148b.mo1810i(b);
                            return;
                        }
                    }
                    return;
                case 15:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(message.arg1);
                        if (b != null && b.trim().length() > 0) {
                            this.f2148b.mo1811j(b);
                            break;
                        } else {
                            this.f2148b.mo1811j(null);
                            break;
                        }
                    }
                    break;
                case 18:
                    if (message.arg2 == 0) {
                        b = "";
                        b = this.f2149c.m2130b(18);
                        if (b == null || b.equals("")) {
                            this.f2148b.mo1814m(null);
                            return;
                        } else {
                            this.f2148b.mo1814m(b);
                            return;
                        }
                    }
                    return;
                case 23:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(23);
                        if (b == null || b.equals("")) {
                            this.f2148b.mo1804c(null);
                            return;
                        } else {
                            this.f2148b.mo1804c(b);
                            return;
                        }
                    }
                    return;
                case 30:
                    break;
                case 31:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(message.arg1);
                        if (b == null || b.equals("")) {
                            this.f2148b.mo1809h(null);
                            return;
                        } else {
                            this.f2148b.mo1809h(b);
                            return;
                        }
                    }
                    return;
                case 34:
                    b = this.f2149c.m2130b(message.arg1);
                    if (b == null || b.trim().length() <= 0) {
                        this.f2148b.mo1812k(null);
                        return;
                    } else {
                        this.f2148b.mo1812k(b);
                        return;
                    }
                case 35:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(35);
                        if (b.equals("")) {
                            this.f2148b.mo1813l(null);
                            return;
                        } else {
                            this.f2148b.mo1813l(b);
                            return;
                        }
                    }
                    return;
                case 44:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(44);
                        if (b == null || b.equals("")) {
                            this.f2148b.mo1813l(null);
                            return;
                        } else {
                            this.f2148b.mo1813l(b);
                            return;
                        }
                    }
                    return;
                case 45:
                    if (message.arg2 == 0) {
                        this.f2148b.mo1802a(this.f2149c.m2130b(45));
                        return;
                    }
                    return;
                case 46:
                    if (message.arg2 == 0) {
                        this.f2148b.mo1805d(this.f2149c.m2130b(message.arg1));
                        return;
                    }
                    return;
                case 51:
                    if (message.arg2 == 0) {
                        this.f2148b.mo1808g(this.f2149c.m2130b(message.arg1));
                        return;
                    }
                    return;
                case 500:
                case 514:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b(message.arg1);
                        if (b == null || b.trim().length() <= 0) {
                            this.f2148b.mo1806e(null);
                            return;
                        } else {
                            this.f2148b.mo1806e(b);
                            return;
                        }
                    }
                    return;
                case d_ResultType.SUGGESTION_SEARCH /*506*/:
                    if (message.arg2 == 0) {
                        b = this.f2149c.m2130b((int) d_ResultType.SUGGESTION_SEARCH);
                        if (b == null || b.equals("")) {
                            this.f2148b.mo1815n(null);
                            return;
                        } else {
                            this.f2148b.mo1815n(b);
                            return;
                        }
                    }
                    return;
                case 801:
                    if (message.arg2 == 0) {
                        b = "";
                        b = this.f2149c.m2130b(message.arg1);
                        if (b == null || b.equals("")) {
                            this.f2148b.mo1807f(null);
                            return;
                        } else {
                            this.f2148b.mo1807f(b);
                            return;
                        }
                    }
                    return;
                default:
                    return;
            }
            if (message.arg2 == 0) {
                b = this.f2149c.m2130b(message.arg1);
                if (b == null || b.trim().length() <= 0) {
                    this.f2148b.mo1817p(null);
                } else {
                    this.f2148b.mo1817p(b);
                }
            }
        } else if (3 == message.arg2 || 108 == message.arg2 || 100 == message.arg2) {
            this.f2148b.mo1801a(11);
        } else if (105 == message.arg2 || 106 == message.arg2 || 200 == message.arg2 || 230 == message.arg2) {
            this.f2148b.mo1801a(500);
        } else if (107 == message.arg2) {
            this.f2148b.mo1801a(107);
        } else {
            this.f2148b.mo1801a(message.arg2);
        }
    }

    public void m2104a(C0518b c0518b) {
        this.f2148b = c0518b;
    }

    public void m2105a(C0658d c0658d) {
        this.f2149c = c0658d;
    }
}
