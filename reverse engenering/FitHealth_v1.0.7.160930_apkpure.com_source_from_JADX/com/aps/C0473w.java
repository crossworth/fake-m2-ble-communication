package com.aps;

import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.List;

public final class C0473w {
    private boolean f1952a = false;
    private String f1953b = "";
    private boolean f1954c = false;
    private double f1955d = 0.0d;
    private double f1956e = 0.0d;

    protected C0473w(List list, String str, String str2, String str3) {
        this.f1953b = str3;
        m2021d();
    }

    private void m2021d() {
        int i;
        boolean z;
        String substring;
        String[] split;
        int i2 = 0;
        String str = this.f1953b;
        if (str != null && str.length() > 8) {
            int i3 = 0;
            for (i = 1; i < str.length() - 3; i++) {
                i3 ^= str.charAt(i);
            }
            if (Integer.toHexString(i3).equalsIgnoreCase(str.substring(str.length() - 2, str.length()))) {
                z = true;
                if (z) {
                    substring = this.f1953b.substring(0, this.f1953b.length() - 3);
                    i = 0;
                    while (i2 < substring.length()) {
                        if (substring.charAt(i2) == ',') {
                            i++;
                        }
                        i2++;
                    }
                    split = substring.split(SeparatorConstants.SEPARATOR_ADS_ID, i + 1);
                    if (split.length < 6) {
                        if (!(split[2].equals("") || split[split.length - 3].equals("") || split[split.length - 2].equals("") || split[split.length - 1].equals(""))) {
                            Integer.valueOf(split[2]).intValue();
                            this.f1955d = Double.valueOf(split[split.length - 3]).doubleValue();
                            this.f1956e = Double.valueOf(split[split.length - 2]).doubleValue();
                            this.f1954c = true;
                        }
                    } else {
                        return;
                    }
                }
                this.f1952a = this.f1954c;
            }
        }
        z = false;
        if (z) {
            substring = this.f1953b.substring(0, this.f1953b.length() - 3);
            i = 0;
            while (i2 < substring.length()) {
                if (substring.charAt(i2) == ',') {
                    i++;
                }
                i2++;
            }
            split = substring.split(SeparatorConstants.SEPARATOR_ADS_ID, i + 1);
            if (split.length < 6) {
                Integer.valueOf(split[2]).intValue();
                this.f1955d = Double.valueOf(split[split.length - 3]).doubleValue();
                this.f1956e = Double.valueOf(split[split.length - 2]).doubleValue();
                this.f1954c = true;
            } else {
                return;
            }
        }
        this.f1952a = this.f1954c;
    }

    protected final boolean m2022a() {
        return this.f1952a;
    }

    protected final double m2023b() {
        return this.f1955d;
    }

    protected final double m2024c() {
        return this.f1956e;
    }
}
