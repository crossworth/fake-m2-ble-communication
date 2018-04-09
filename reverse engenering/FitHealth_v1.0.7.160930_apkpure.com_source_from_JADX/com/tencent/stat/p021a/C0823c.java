package com.tencent.stat.p021a;

import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.Arrays;
import java.util.Properties;

public class C0823c {
    String f2837a;
    String[] f2838b;
    Properties f2839c = null;

    public C0823c(String str, String[] strArr, Properties properties) {
        this.f2837a = str;
        this.f2838b = strArr;
        this.f2839c = properties;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0823c)) {
            return false;
        }
        C0823c c0823c = (C0823c) obj;
        boolean z = this.f2837a.equals(c0823c.f2837a) && Arrays.equals(this.f2838b, c0823c.f2838b);
        return this.f2839c != null ? z && this.f2839c.equals(c0823c.f2839c) : z && c0823c.f2839c == null;
    }

    public int hashCode() {
        int i = 0;
        if (this.f2837a != null) {
            i = this.f2837a.hashCode();
        }
        if (this.f2838b != null) {
            i ^= Arrays.hashCode(this.f2838b);
        }
        return this.f2839c != null ? i ^ this.f2839c.hashCode() : i;
    }

    public String toString() {
        String str = this.f2837a;
        String str2 = "";
        if (this.f2838b != null) {
            String str3 = this.f2838b[0];
            for (int i = 1; i < this.f2838b.length; i++) {
                str3 = str3 + SeparatorConstants.SEPARATOR_ADS_ID + this.f2838b[i];
            }
            str2 = "[" + str3 + "]";
        }
        if (this.f2839c != null) {
            str2 = str2 + this.f2839c.toString();
        }
        return str + str2;
    }
}
