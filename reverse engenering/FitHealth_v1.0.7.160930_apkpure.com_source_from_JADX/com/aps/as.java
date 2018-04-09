package com.aps;

import android.location.GpsSatellite;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public final class as implements Listener, NmeaListener {
    private long f1761a = 0;
    private long f1762b = 0;
    private boolean f1763c = false;
    private List f1764d = new ArrayList();
    private String f1765e = null;
    private String f1766f = null;
    private String f1767g = null;
    private /* synthetic */ C0475y f1768h;

    protected as(C0475y c0475y) {
        this.f1768h = c0475y;
    }

    public final void m1813a(String str) {
        if (System.currentTimeMillis() - this.f1762b > 400 && this.f1763c && this.f1764d.size() > 0) {
            try {
                C0473w c0473w = new C0473w(this.f1764d, this.f1765e, null, this.f1767g);
                if (c0473w.m2022a()) {
                    this.f1768h.f1998O = C0475y.m2026a(this.f1768h, c0473w, this.f1768h.f1995L);
                    if (this.f1768h.f1998O > 0) {
                        C0475y.m2043b(this.f1768h, String.format(Locale.CHINA, "&nmea=%.1f|%.1f&g_tp=%d", new Object[]{Double.valueOf(c0473w.m2024c()), Double.valueOf(c0473w.m2023b()), Integer.valueOf(this.f1768h.f1998O)}));
                    }
                } else {
                    this.f1768h.f1998O = 0;
                }
            } catch (Exception e) {
                this.f1768h.f1998O = 0;
            }
            this.f1764d.clear();
            this.f1767g = null;
            this.f1766f = null;
            this.f1765e = null;
            this.f1763c = false;
        }
        if (str.startsWith("$GPGGA")) {
            this.f1763c = true;
            this.f1765e = str.trim();
        } else if (str.startsWith("$GPGSV")) {
            this.f1764d.add(str.trim());
        } else if (str.startsWith("$GPGSA")) {
            this.f1767g = str.trim();
        }
        this.f1762b = System.currentTimeMillis();
    }

    public final void onGpsStatusChanged(int i) {
        int i2 = 0;
        try {
            if (this.f1768h.f2007s != null) {
                switch (i) {
                    case 2:
                        this.f1768h.f1997N = 0;
                        return;
                    case 4:
                        if (C0475y.f1974a || System.currentTimeMillis() - this.f1761a >= 10000) {
                            if (this.f1768h.f1993J == null) {
                                this.f1768h.f1993J = this.f1768h.f2007s.getGpsStatus(null);
                            } else {
                                this.f1768h.f2007s.getGpsStatus(this.f1768h.f1993J);
                            }
                            this.f1768h.f1994K = 0;
                            this.f1768h.f1995L = 0;
                            this.f1768h.f1996M = new HashMap();
                            int i3 = 0;
                            int i4 = 0;
                            for (GpsSatellite gpsSatellite : this.f1768h.f1993J.getSatellites()) {
                                i3++;
                                if (gpsSatellite.usedInFix()) {
                                    i4++;
                                }
                                if (gpsSatellite.getSnr() > 0.0f) {
                                    i2++;
                                }
                                if (gpsSatellite.getSnr() >= ((float) C0475y.f1971X)) {
                                    this.f1768h.f1995L = this.f1768h.f1995L + 1;
                                }
                            }
                            if (this.f1768h.f2001m == -1 || ((i4 >= 4 && this.f1768h.f2001m < 4) || (i4 < 4 && this.f1768h.f2001m >= 4))) {
                                this.f1768h.f2001m = i4;
                                if (i4 < 4) {
                                    if (this.f1768h.f2008t != null) {
                                        this.f1768h.f2008t.m1810w();
                                    }
                                } else if (this.f1768h.f2008t != null) {
                                    this.f1768h.f2008t.m1809v();
                                }
                            }
                            this.f1768h.f1997N = i2;
                            this.f1768h.m2027a(this.f1768h.f1996M);
                            if (!C0475y.f1974a) {
                                if ((i4 > 3 || i3 > 15) && this.f1768h.f2007s.getLastKnownLocation("gps") != null) {
                                    this.f1761a = System.currentTimeMillis();
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        } catch (Exception e) {
        }
    }

    public final void onNmeaReceived(long j, String str) {
        try {
            if (C0475y.f1974a && str != null && !str.equals("") && str.length() >= 9 && str.length() <= 150) {
                this.f1768h.f1989F.sendMessage(this.f1768h.f1989F.obtainMessage(1, str));
            }
        } catch (Exception e) {
        }
    }
}
