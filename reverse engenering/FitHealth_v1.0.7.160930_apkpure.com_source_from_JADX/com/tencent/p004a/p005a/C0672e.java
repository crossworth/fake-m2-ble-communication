package com.tencent.p004a.p005a;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: ProGuard */
public class C0672e implements Iterable<String> {
    private ConcurrentLinkedQueue<String> f2328a;
    private AtomicInteger f2329b;

    public C0672e() {
        this.f2328a = null;
        this.f2329b = null;
        this.f2328a = new ConcurrentLinkedQueue();
        this.f2329b = new AtomicInteger(0);
    }

    public int m2251a(String str) {
        int length = str.length();
        this.f2328a.add(str);
        return this.f2329b.addAndGet(length);
    }

    public void m2252a(Writer writer, char[] cArr) throws IOException {
        if (writer != null && cArr != null && cArr.length != 0) {
            int length = cArr.length;
            Iterator it = iterator();
            int i = 0;
            int i2 = length;
            while (it.hasNext()) {
                String str = (String) it.next();
                int length2 = str.length();
                int i3 = 0;
                while (length2 > 0) {
                    int i4 = i2 > length2 ? length2 : i2;
                    str.getChars(i3, i3 + i4, cArr, i);
                    i2 -= i4;
                    i += i4;
                    length2 -= i4;
                    i4 += i3;
                    if (i2 == 0) {
                        writer.write(cArr, 0, length);
                        i = 0;
                        i2 = length;
                        i3 = i4;
                    } else {
                        i3 = i4;
                    }
                }
            }
            if (i > 0) {
                writer.write(cArr, 0, i);
            }
            writer.flush();
        }
    }

    public int m2250a() {
        return this.f2329b.get();
    }

    public void m2253b() {
        this.f2328a.clear();
        this.f2329b.set(0);
    }

    public Iterator<String> iterator() {
        return this.f2328a.iterator();
    }
}
