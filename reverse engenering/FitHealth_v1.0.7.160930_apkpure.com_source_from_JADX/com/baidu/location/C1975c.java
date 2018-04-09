package com.baidu.location;

import java.text.SimpleDateFormat;

class C1975c implements an, C1619j {
    private static C1975c bh = null;
    private long be = 0;
    public long bf = 0;
    private long bg = 0;
    public boolean bi = false;

    private C1975c() {
    }

    public static C1975c m5927char() {
        if (bh == null) {
            bh = new C1975c();
        }
        return bh;
    }

    public void m5928else() {
        if (!this.bi) {
            this.be = System.currentTimeMillis();
        }
    }

    public long m5929int(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime();
        } catch (Exception e) {
            return -1;
        }
    }

    public void m5930new(String str) {
        if (!this.bi) {
            this.bg = System.currentTimeMillis();
            long j = (this.bg - this.be) / 2;
            if (j <= 3000 && j >= 0) {
                long j2 = m5929int(str);
                if (j2 > 0) {
                    this.bf = (j + j2) - System.currentTimeMillis();
                    this.bi = false;
                }
            }
        }
    }
}
