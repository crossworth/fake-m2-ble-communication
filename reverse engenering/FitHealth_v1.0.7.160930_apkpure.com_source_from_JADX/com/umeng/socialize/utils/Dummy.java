package com.umeng.socialize.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Dummy {

    final class C09821 implements InvocationHandler {
        C09821() {
        }

        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            return null;
        }
    }

    public static <T> T get(Class<T> cls, T t) {
        if (t != null) {
            return t;
        }
        if (cls.isInterface()) {
            return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new C09821());
        }
        try {
            return cls.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
