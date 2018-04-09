package com.tencent.utils;

import java.util.HashMap;

/* compiled from: ProGuard */
public class TemporaryStorage {
    private static HashMap<String, Object> f2992a = new HashMap();

    public static Object set(String str, Object obj) {
        return f2992a.put(str, obj);
    }

    public static Object get(String str) {
        return f2992a.remove(str);
    }
}
