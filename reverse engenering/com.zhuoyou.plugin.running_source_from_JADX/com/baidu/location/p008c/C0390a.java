package com.baidu.location.p008c;

import java.util.ArrayList;

public class C0390a<T> extends ArrayList<T> {
    private int f443a = 0;

    public C0390a(int i) {
        this.f443a = i;
    }

    public boolean add(T t) {
        synchronized (this) {
            if (size() == this.f443a) {
                remove(0);
            }
            add(size(), t);
        }
        return true;
    }

    public void clear() {
        synchronized (this) {
            if (size() <= 3) {
                return;
            }
            int size = size() / 2;
            while (true) {
                int i = size - 1;
                if (size > 0) {
                    remove(0);
                    size = i;
                } else {
                    return;
                }
            }
        }
    }
}
