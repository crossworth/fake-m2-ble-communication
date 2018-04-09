package com.tencent.wxop.stat.p040a;

import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

public class C1419c {
    public String f4605a;
    public JSONArray f4606b;
    public JSONObject f4607c = null;

    public C1419c(String str, String[] strArr, Properties properties) {
        this.f4605a = str;
        if (properties != null) {
            this.f4607c = new JSONObject(properties);
        } else if (strArr != null) {
            this.f4606b = new JSONArray();
            for (Object put : strArr) {
                this.f4606b.put(put);
            }
        } else {
            this.f4607c = new JSONObject();
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1419c)) {
            return false;
        }
        return toString().equals(((C1419c) obj).toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append(this.f4605a).append(",");
        if (this.f4606b != null) {
            stringBuilder.append(this.f4606b.toString());
        }
        if (this.f4607c != null) {
            stringBuilder.append(this.f4607c.toString());
        }
        return stringBuilder.toString();
    }
}
