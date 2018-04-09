package com.baidu.location.p012f;

import java.util.List;

class C0450f {
    public static String f795a = null;
    public int f796b = 0;
    private boolean f797c = false;
    private String f798d = "";
    private boolean f799e = false;
    private double f800f = 0.0d;
    private double f801g = 0.0d;

    public C0450f(List<String> list, String str, String str2, String str3) {
        this.f798d = str3;
        m927d();
    }

    private boolean m926a(String str) {
        if (str == null || str.length() <= 8) {
            return false;
        }
        int i = 0;
        for (int i2 = 1; i2 < str.length() - 3; i2++) {
            i ^= str.charAt(i2);
        }
        return Integer.toHexString(i).equalsIgnoreCase(str.substring(str.length() + -2, str.length()));
    }

    private void m927d() {
        int i = 0;
        if (m926a(this.f798d)) {
            String substring = this.f798d.substring(0, this.f798d.length() - 3);
            int i2 = 0;
            while (i < substring.length()) {
                if (substring.charAt(i) == ',') {
                    i2++;
                }
                i++;
            }
            String[] split = substring.split(",", i2 + 1);
            if (split.length >= 6) {
                if (!(split[2].equals("") || split[split.length - 3].equals("") || split[split.length - 2].equals("") || split[split.length - 1].equals(""))) {
                    try {
                        this.f800f = Double.valueOf(split[split.length - 3]).doubleValue();
                        this.f801g = Double.valueOf(split[split.length - 2]).doubleValue();
                    } catch (Exception e) {
                    }
                    this.f799e = true;
                }
            } else {
                return;
            }
        }
        this.f797c = this.f799e;
    }

    public boolean m928a() {
        return this.f797c;
    }

    public double m929b() {
        return this.f800f;
    }

    public double m930c() {
        return this.f801g;
    }
}
