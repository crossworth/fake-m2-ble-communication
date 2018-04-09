package com.tencent.stat.p039a;

import java.util.Arrays;
import java.util.Properties;

public class C1368c {
    String f4353a;
    String[] f4354b;
    Properties f4355c = null;

    public C1368c(String str, String[] strArr, Properties properties) {
        this.f4353a = str;
        this.f4354b = strArr;
        this.f4355c = properties;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1368c)) {
            return false;
        }
        C1368c c1368c = (C1368c) obj;
        boolean z = this.f4353a.equals(c1368c.f4353a) && Arrays.equals(this.f4354b, c1368c.f4354b);
        return this.f4355c != null ? z && this.f4355c.equals(c1368c.f4355c) : z && c1368c.f4355c == null;
    }

    public int hashCode() {
        int i = 0;
        if (this.f4353a != null) {
            i = this.f4353a.hashCode();
        }
        if (this.f4354b != null) {
            i ^= Arrays.hashCode(this.f4354b);
        }
        return this.f4355c != null ? i ^ this.f4355c.hashCode() : i;
    }

    public String toString() {
        String str = this.f4353a;
        String str2 = "";
        if (this.f4354b != null) {
            String str3 = this.f4354b[0];
            for (int i = 1; i < this.f4354b.length; i++) {
                str3 = str3 + "," + this.f4354b[i];
            }
            str2 = "[" + str3 + "]";
        }
        if (this.f4355c != null) {
            str2 = str2 + this.f4355c.toString();
        }
        return str + str2;
    }
}
