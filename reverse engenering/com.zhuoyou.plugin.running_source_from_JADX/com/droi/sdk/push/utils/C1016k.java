package com.droi.sdk.push.utils;

public class C1016k implements Comparable {
    public final String f3360a;
    public final String f3361b;

    public C1016k(String str, String str2) {
        this.f3360a = str;
        this.f3361b = str2;
    }

    public int m3174a(C1016k c1016k) {
        String str = c1016k.f3360a;
        String str2 = c1016k.f3361b;
        try {
            String[] split = this.f3360a.split("\\.");
            String[] split2 = str.split("\\.");
            int min = Math.min(split.length, split2.length);
            int i = 0;
            while (i < min) {
                int parseInt = Integer.parseInt(split[i]);
                int parseInt2 = Integer.parseInt(split2[i]);
                if (parseInt != parseInt2) {
                    return parseInt > parseInt2 ? 1 : -1;
                } else {
                    i++;
                }
            }
            return this.f3361b.compareTo(str2);
        } catch (Exception e) {
            return -1;
        }
    }

    public String m3175a() {
        return this.f3361b;
    }

    public String m3176b() {
        return this.f3360a;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return m3174a((C1016k) obj);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof C1016k)) {
            return true;
        }
        C1016k c1016k = (C1016k) obj;
        return (this.f3360a == null || c1016k.f3360a == null) ? (this.f3360a == null && c1016k.f3360a == null) ? (this.f3361b == null || c1016k.f3361b == null) ? this.f3361b == null && c1016k.f3361b == null : this.f3361b.equals(c1016k.f3361b) : false : this.f3360a.equals(c1016k.f3360a) ? (this.f3361b == null || c1016k.f3361b == null) ? this.f3361b == null && c1016k.f3361b == null : this.f3361b.equals(c1016k.f3361b) : false;
    }

    public int hashCode() {
        return (this.f3360a + this.f3361b).hashCode();
    }
}
