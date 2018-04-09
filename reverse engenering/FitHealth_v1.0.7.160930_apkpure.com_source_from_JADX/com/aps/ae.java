package com.aps;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ae {
    protected File f1706a;
    protected int[] f1707b;
    private ArrayList f1708c;
    private boolean f1709d = false;

    protected ae(File file, ArrayList arrayList, int[] iArr) {
        this.f1706a = file;
        this.f1708c = arrayList;
        this.f1707b = iArr;
    }

    protected final void m1729a(boolean z) {
        this.f1709d = z;
    }

    public byte[] m1730a() {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        Iterator it = this.f1708c.iterator();
        while (it.hasNext()) {
            byte[] bArr = (byte[]) it.next();
            try {
                dataOutputStream.writeInt(bArr.length);
                dataOutputStream.write(bArr);
            } catch (IOException e) {
            }
        }
        try {
            byteArrayOutputStream.close();
            dataOutputStream.close();
        } catch (IOException e2) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    protected final boolean m1731b() {
        return this.f1709d;
    }

    protected final int m1732c() {
        if (this.f1708c == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.f1708c.size(); i2++) {
            i += this.f1708c.get(i2) != null ? ((byte[]) this.f1708c.get(i2)).length : 0;
        }
        return i;
    }
}
