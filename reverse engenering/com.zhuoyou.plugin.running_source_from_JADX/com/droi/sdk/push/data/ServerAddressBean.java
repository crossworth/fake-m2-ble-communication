package com.droi.sdk.push.data;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerAddressBean implements Serializable {
    private String[][] f3267a;

    public ServerAddressBean(ArrayList arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            int size = arrayList.size();
            this.f3267a = new String[size][];
            for (int i = 0; i < size; i++) {
                ArrayList arrayList2 = (ArrayList) arrayList.get(i);
                if (arrayList2.isEmpty()) {
                    this.f3267a[i][0] = "";
                } else {
                    arrayList2.toArray(this.f3267a[i]);
                }
            }
        }
    }

    public String[] getAddress(String str) {
        if (this.f3267a == null || TextUtils.isEmpty(str)) {
            return null;
        }
        for (int i = 0; i < this.f3267a.length; i++) {
            if (str.equals(this.f3267a[i][0])) {
                return (String[]) Arrays.copyOfRange(this.f3267a[i], 1, this.f3267a[i].length);
            }
        }
        return null;
    }
}
