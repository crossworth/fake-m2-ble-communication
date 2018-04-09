package com.tencent.open.p036a;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: ProGuard */
public class C1315g implements Iterable<String> {
    private ConcurrentLinkedQueue<String> f4128a;
    private AtomicInteger f4129b;

    public C1315g() {
        this.f4128a = null;
        this.f4129b = null;
        this.f4128a = new ConcurrentLinkedQueue();
        this.f4129b = new AtomicInteger(0);
    }

    public int m3876a(String str) {
        int length = str.length();
        this.f4128a.add(str);
        return this.f4129b.addAndGet(length);
    }

    public void m3877a(Writer writer, char[] cArr) throws IOException {
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

    public int m3875a() {
        return this.f4129b.get();
    }

    public void m3878b() {
        this.f4128a.clear();
        this.f4129b.set(0);
    }

    public Iterator<String> iterator() {
        return this.f4128a.iterator();
    }
}
